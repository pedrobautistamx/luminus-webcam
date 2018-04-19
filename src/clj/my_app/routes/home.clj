(ns my-app.routes.home
  (:require [my-app.layout :as layout]
            [compojure.core :refer [defroutes GET POST]]
            [ring.util.http-response :as response]
            [clojure.data.json :as json]
            [clojure.java.io :as io])
  (:import  [com.github.sarxos.webcam Webcam WebcamResolution]
            [java.awt.image BufferedImage]
            [java.io File]
            [javax.imageio ImageIO]
            [java.awt Dimension]
            [java.util List]))

(defn home-page []
  (layout/render
    "home.html" {:docs (-> "docs/docs.md" io/resource slurp)}))

(defn about-page []
  (layout/render "about.html"))

(defn my-json []
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body "{\"foo\": \"bar\"}"})

(defn my-json2 [req] ;;$_REQUEST

  {:status 200
   :headers {"Content-Type" "application/json"}
   :body (json/write-str (get req :query-params))})

(defn getcams []
  (reduce (fn  [a x] (let [f (Webcam/.getName x)]  ;(println "EL f: " f)
    (conj a f) )) '() (Webcam/getWebcams)))

(defn get-webcams [req]
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body (json/write-str (getcams))})

(defroutes home-routes
  (GET "/" [] (home-page))
  (GET "/about" [] (about-page))
  (GET "/json" [] (my-json))
  (GET "/json2" req (my-json2 req))
  (GET "/cams" req (get-webcams req)))
