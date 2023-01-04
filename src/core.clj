(ns core
  (:gen-class)
  (:require [compojure.core :as c]
            [clojure.pprint]
            ;;[compojure.route :as route]
            [ring.adapter.jetty :as jetty]
            [muuntaja.middleware :as muuntaja]
            [ring.middleware.defaults :as ring-defaults]))

(defonce server (atom nil))

(defn routes []
  (c/routes
    (c/GET  "/foo/:foo" [foo]
           {:status 200
            :body (str "you asked for " foo)})
   (c/POST "/api/:foo" {:keys [body-params] :as req}
     (clojure.pprint/pprint req)
     {:status 200
      :body {:input (:input body-params)}}))
   (c/POST "/api/v2/:foo" [foo req]
     (clojure.pprint/pprint req)
     {:status 200
      :body {:req req :foo foo}}))

(defn handler [req]
  ((routes) req))

(defn start-jetty! []
  (reset!
   server
   (jetty/run-jetty (-> #'handler
                        ;;muuntaja/wrap-format
                        (ring-defaults/wrap-defaults
                         ring-defaults/api-defaults))
                    {:join? false :port 3456})))

(defn stop-jetty! []
  (.stop @server)
  (reset! server nil))

(defn -main [& _]
  (start-jetty!))

#_(start-jetty!)
#_(stop-jetty!)




