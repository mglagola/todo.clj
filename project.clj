(defproject
  core "0.1.0-SNAPSHOT"
  :description "A todo app"
  :url "http://github.com/mglagola/todo.clj"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.1.6"]
                 [hiccup "1.0.5"]
                 [ring-server "0.3.1"]
                 [org.clojure/java.jdbc "0.3.7"]
                 [postgresql "9.3-1102.jdbc41"]
                 [selmer "0.8.2"]
                 [lib-noir "0.9.9"]]
  :plugins [[lein-ring "0.8.12"]]
  :ring {:handler core.handler/app
         :init core.handler/init
         :destroy core.handler/destroy}
  :uberjar-name "core-standalone.jar"
  :main ^:skip-aot core.web
  :profiles
  {:uberjar {:aot :all}
   :production
            {:ring
             {:open-browser? false, :stacktraces? false, :auto-reload? false}}
   :dev
            {:dependencies [[ring-mock "0.1.5"] [ring/ring-devel "1.3.1"]]}})
