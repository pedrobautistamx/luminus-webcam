(ns my-app.handler
  (:require [compojure.core :refer [routes wrap-routes]]
            [my-app.layout :refer [error-page]]
            [my-app.routes.home :refer [home-routes]]
            [compojure.route :as route]
            [my-app.env :refer [defaults]]
            [mount.core :as mount]
            [my-app.middleware :as middleware]))

(mount/defstate init-app
  :start ((or (:init defaults) identity))
  :stop  ((or (:stop defaults) identity)))

(mount/defstate app
  :start
  (middleware/wrap-base
    (routes
      (-> #'home-routes
          (wrap-routes middleware/wrap-csrf)
          (wrap-routes middleware/wrap-formats))
      (route/not-found
        (:body
          (error-page {:status 404
                       :title "page not found"}))))))
