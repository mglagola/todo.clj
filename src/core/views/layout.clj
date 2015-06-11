(ns core.views.layout
  (:require [selmer.parser :as parser]
            [noir.session :as session]
            [ring.util.response :refer [content-type response]]
            [compojure.response :refer [Renderable]]
            [noir.util.anti-forgery :refer [anti-forgery-field]]))

(def template-dir "core/views/templates/")

(defn utf-8-response [html]
  (content-type (response html) "text/html; charset=utf-8"))

(deftype RenderablePage [template params]
  Renderable
  (render [this request]
    (->> (assoc params
           :context (:context request)
           :user    (session/get :user)
           :anti-forgery (anti-forgery-field))
         (parser/render-file (str template-dir template))
         utf-8-response)))

(defn render [template & [params]]
  (RenderablePage. template params))
