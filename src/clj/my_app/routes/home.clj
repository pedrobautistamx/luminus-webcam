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

(def ruta "/home/pbautista/camara1.png")

   (defn hace-todo [route]

     (let [webcam (Webcam/getDefault) vector (new Dimension 1920 1080)]
        ;  (println "Tipo Vector:" (type vector))
(println "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" route)
         (.setCustomViewSizes webcam (into-array Dimension [vector]))
          (.setViewSize webcam vector)
          (.open webcam false)
          (let [image (.getImage webcam)]
         (let [file (new File ruta)]
         (ImageIO/write image "PNG" file)))
         (.close webcam)
       ;  (about-page)
          )



     {:status 200
      :headers {"Content-Type" "application/json"}
      :body  "Se ha guardado la foto"}



     )
(def ruta2 "/home/pbautista/camara2.png")
(def cam2 "HD Pro Webcam C920 /dev/video1")
;(defn redirect! [loc] (set! (.-location js/window) loc))

; (defn redirect
; [url]
; (-> js/document
; .-location
; (set! url)))


     (defn hace-todo2 [route]

       (let [webcam (Webcam/getWebcamByName cam2) vector (new Dimension 1920 1080)]
          ;  (println "Tipo Vector:" (type vector))
  (println "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" route)
           (.setCustomViewSizes webcam (into-array Dimension [vector]))
            (.setViewSize webcam vector)
            (.open webcam false)
            (let [image (.getImage webcam)]
           (let [file (new File ruta2)]
           (ImageIO/write image "PNG" file)))
           (.close webcam)
         ;  (about-page)

         (redirect "http://localhost:3449/")
            )



       {:status 200
        :headers {"Content-Type" "application/json"}
        :body  "Se ha guardado la foto"}



       )




(defroutes home-routes
  (GET "/" [] (home-page))
  (GET "/about" [] (about-page))
  (GET "/json" [] (my-json))
  (GET "/json2" req (my-json2 req))
  (GET "/cams" req (get-webcams req))
  (GET "/foto" route (hace-todo route ))
  (GET "/foto2" route (hace-todo2 route ))
  )
