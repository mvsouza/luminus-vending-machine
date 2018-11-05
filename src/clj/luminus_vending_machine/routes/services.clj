(ns luminus-vending-machine.routes.services
  (:require [ring.util.http-response :refer :all]
            [compojure.api.sweet :refer :all]
            [schema.core :as s]))

(def coins-value {:nickel 1/20 :quarter 1/4 :dime 1/10 :half_d 1/2 :dolar 1})

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

    (GET "/balance" []
      :return       Double
      :summary      "coin on body is inserted to the machine."
              (ok (vending-machine-balance)))))
