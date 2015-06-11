(ns core.handler
  (:require [compojure.core :refer [defroutes routes]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [hiccup.middleware :refer [wrap-base-url]]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [core.routes.home :refer [home-routes]]
            [core.routes.auth :refer [auth-routes]]
            [selmer.middleware :refer [wrap-error-page]]
            [noir.util.middleware :as noir-middleware]))

(defn init []
  (println "core is starting"))

(defn destroy []
  (println "core is shutting down"))

(defroutes app-routes
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (noir-middleware/app-handler
    [auth-routes
     home-routes
     app-routes]))

