(ns core.routes.todo
  (:require [compojure.core :refer [defroutes GET POST DELETE context]]
            [noir.session :as session]
            [noir.response :as resp]
            [noir.validation :as vali]
            [noir.util.crypt :as crypt]
            [core.models.todo :as todo]
            [noir.util.route :refer [restricted]]
            [core.views.layout :as layout]
            [core.routes.home :refer [home]]))

(defn logged-out? []
  (nil? (session/get :user)))

(defn valid-todo?
  "Validates a todo-spec to increase the likelihood of being successfully processed by
  the postgresql database"
  [{title :title description :description}]
  (vali/rule (vali/has-value? title)
             [:title "A title is required for a todo"])
  (not (vali/errors? :title)))

(defn handle-todo-deletion [id]
  (if (logged-out?)
    (home {:error "You must be logged in to delete your todos"})
    (if (= (session/get :user) (:user_id (first (todo/get-todo-by-id id))))
      (try
        (todo/delete-todo id)
        (home {:success "Deleted"})
        (catch Exception ex
          (home {:error "Something went wrong deleting your todo"})))
      (home {:error "You're not allowed to delete other people's todos!"}))))

(defn handle-todo-creation [title description]
  (if (logged-out?)
    (home {:error "You must be logged in to create a todo"})
    (let [todo-spec {:title title
                     :description description
                     :user_id (session/get :user)}]
      (if (valid-todo? todo-spec)
        (try
          (todo/create-todo todo-spec)
          ;(resp/redirect "/")
          (home {:success (str "Created " title)})
          (catch Exception ex
            (home {:error "Something went wrong while creating your todo"})))
        (home {:error (first (vali/get-errors :title))})))))

(defroutes
  todo-routes

  (POST "/todo" [title description]
    (handle-todo-creation title description))

  (context ["/todo/:id" :id #"[0-9]+"] [id]
    (DELETE "/delete" []
      (try
        (handle-todo-deletion (Integer/parseInt id))
        (catch Exception ex
          (home {:error "Not a valid todo"}))))))