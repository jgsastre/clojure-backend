(ns core
  (:gen-class)
  (:require
   [clojure.pprint :as pp]
   [compojure.core :as c]
   [ring.middleware.defaults :as ring-defaults]
   [muuntaja.middleware :as mm]
   [ring.adapter.jetty :as jetty]))

(defonce server (atom nil))

#_(defn routes []
    (c/routes
     (c/GET "/" []
       {:status 200
        :body "This is my response"})))

(defn routes []
  (c/routes
   (c/GET "/" [] {:status 200 :body "Index"})
   (c/GET  "/:foo" [foo]
     {:status 200
      :body (str "you asked for " foo)})
   (c/POST "/api" {:keys [body-params] :as _}
     {:status 200
      :body {:body-params body-params}}))
  (c/POST "/api/v2/:foo/:bar/:baz" [_ & others :as req]
    (clojure.pprint/pprint req)
    {:status 200
     :body {:others others :req req}}))

(defn my-middelware [handler]
  (fn [request]
    (pp/pprint request)
    (let [response (handler request)]
      response)))

(defn handler [req]
  ((routes) req))

(defn start-jetty! []
  (reset!
   server
   (jetty/run-jetty  (-> #'handler
                         mm/wrap-format
                         (ring-defaults/wrap-defaults
                          ring-defaults/api-defaults)
                         my-middelware)
                     {:join? false :port 3456})))

(defn stop-jetty! []
  (.stop @server)
  (reset! server nil))

(defn -main [& _]
  (start-jetty!))

#_(start-jetty!)
#_(stop-jetty!)
