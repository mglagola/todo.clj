(ns core.routes.todo
  (:require [compojure.core :refer [defroutes GET POST context]]
            [noir.session :as session]
            [noir.response :as resp]
            [noir.validation :as vali]
            [noir.util.crypt :as crypt]
            [core.models.todo :as todo]
            [noir.util.route :refer [restricted]]
            [core.views.layout :as layout]
            [core.routes.home :refer [home]]))

(defn valid-todo?
  "Validates a todo-spec to increase the likelihood of being successfully processed by
  the postgresql database"
  [{title :title description :description}]
  (vali/rule (vali/has-value? title)
             [:title "A title is required for a todo"])
  (not (vali/errors? :title)))


(defn handle-todo
  [title description]
  (if (empty? (session/get :user))
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
  todo-route

  (POST "/todo" [title description]
    (handle-todo title description)))