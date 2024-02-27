(ns core
  (:require [org.httpkit.server :refer [run-server]]
            web)
  (:gen-class))

(defn -main [& args]
  (let [server (run-server web/handler
                           {:port 3000})]
    (.addShutdownHook (Runtime/getRuntime)
                      (Thread. #(do (println "Shutting down")
                                    (server))))
    (println "Started server")
    @(future)))

(comment
  (-main))