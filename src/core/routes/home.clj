(ns core.routes.home
  (:require [compojure.core :refer :all]
            [core.views.layout :as layout]
            [core.models.user :as user]
            [noir.session :as sessoin]))

(defn home [& [params]]
  (layout/render "home.html" params))

(defroutes
  home-routes
  (GET "/" [] (home)))
