(ns luminus-vending-machine.routes.services
  (:require [ring.util.http-response :refer :all]
            [compojure.api.sweet :refer :all]
            [schema.core :as s]))

(def coins-value {:nicle 1/20 :quarter 1/4 :dime 1/10 :half_d 1/2 :dolar 1})
(def amount-inserted-ref (ref 0.0M))

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
        (alter amount-inserted-ref +
                     ((keyword coin) coins-value))
              (ok  @amount-inserted-ref)))))
