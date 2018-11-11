(ns luminus-vending-machine.routes.services
  (:require [ring.util.http-response :refer :all]
            [compojure.api.sweet :refer :all]
            [schema.core :as s]))

(def coins-value {:nickel 1/20 :quarter 1/4 :dime 1/10 :half_d 1/2 :dolar 1})

(defn print-list [list]
  (let [[x & xs] list] ((print x " teste\n") (if-some xs print-list))))

(def products-value {:pastelina 1.2 :coquinha 2.5 :cocao 5.0 :tubaina 2.5 :pureza 2.5 })

(def inserted-coins (ref []))

(defn coin-value-fn [k] (k coins-value))

(defn sum-coins-string [coins]
  (eval (conj (map coin-value-fn coins) +)))

(defn vending-machine-balance []
  (sum-coins-string @inserted-coins))

(defapi service-routes
  {:swagger {:ui "/swagger-ui"
             :spec "/swagger.json"
             :data {:info {:version "1.0.0"
                           :title "Sample API"
                           :description "Sample Services"}}}}

  (context "/api" []
    :tags ["Vending machine"]
    (POST "/insert" []
      :return       Double
      :body-params [coin :- String]
      :summary      "coin on body is inserted to the machine."
      (dosync
        (alter inserted-coins conj (keyword coin))
              (ok (vending-machine-balance))))
    (POST "/dispense" []
      :return       Double
      :body-params [product :- String]
      :summary      "product on body is passed to the machine, inorder to dispense it.")
    (GET "/balance" []
      :return       Double
      :summary      "return the total amount of credits inserted."
              (ok (vending-machine-balance)))
    (GET "/coins" []
      :return       String
      :summary      "return all coins inserted into the machine."
      (ok (clojure.string/join ", " (map name @inserted-coins))))))
