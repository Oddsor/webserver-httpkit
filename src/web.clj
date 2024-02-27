(ns web
  (:require [ring.websocket :as ws]
            [hiccup.core :as hiccup]))

(defn html [body]
  (str "<!DOCTYPE html>"
       (hiccup/html
        [:html
         [:head
          [:meta {:http-equiv "Content-Type" :content "text/html; charset=utf-8"}]
          [:link {:rel "stylesheet"
                  :href "index.min.css"}]
          [:script
           {:src "https://unpkg.com/htmx.org@1.9.10"
            :integrity
            "sha384-D1Kt99CQMDuVetoL1lrYwg5t+9QdHe7NLX/SoJYkXDFfX37iInKRy5xLSi8nO7UC"
            :crossorigin "anonymous"}]
          [:script {:src "https://unpkg.com/hyperscript.org@0.9.12"}]]
         body])))

(defn ws-response [req]
  {::ws/listener {:on-open
                  (fn [socket])
                  :on-pong
                  (fn [socket buffer])
                  :on-error
                  (fn [socket throwable])
                  :on-message
                  (fn [socket message-str])
                  :on-close
                  (fn [socket code reason])}})

;; Web router:
(defn handler [req]
  (if (ws/upgrade-request? req)
    (ws-response req)
    {:status 200
     :body (html
            [:body {:hx-ws "connect:/ws"
                    :style "background-color: #fefefe;"}
             "Hello world!"])}))