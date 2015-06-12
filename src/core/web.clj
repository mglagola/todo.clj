(ns core.web
  (:require [core.models.todo :refer [create-todo-table-if-needed]]
            [core.models.user :refer [create-user-table-if-needed]]
            [core.handler :refer [app]]
            [ring.adapter.jetty :as ring])
  (:gen-class))

(defn start [port]
  (ring/run-jetty app {:port port
                               :join? false}))
(defn -main []
  (create-user-table-if-needed)
  (create-todo-table-if-needed)
  (let [port (Integer. (or (System/getenv "PORT") "8080"))]
    (start port)))