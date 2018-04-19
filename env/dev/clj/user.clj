(ns user
  (:require [my-app.config :refer [env]]
            [mount.core :as mount]
            [my-app.core :refer [start-app]]))

(defn start []
  (mount/start-without #'my-app.core/repl-server))

(defn stop []
  (mount/stop-except #'my-app.core/repl-server))

(defn restart []
  (stop)
  (start))


