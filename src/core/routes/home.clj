(ns core.routes.home
  (:require [compojure.core :refer :all]
            [core.views.layout :as layout]
            [core.models.user :as user]
            [core.models.todo :as todo]
            [noir.session :as session]))

(defn home [& [params]]
  (if (nil? (session/get :user))
    (layout/render "home.html" params)
    (layout/render "home.html" (merge params
                                      {:todos (todo/get-todos-by-user (session/get :user))}))))

(defroutes
  home-routes
  (GET "/" [] (home)))
