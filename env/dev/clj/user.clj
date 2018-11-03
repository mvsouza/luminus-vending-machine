(ns user
  (:require [luminus-vending-machine.config :refer [env]]
            [clojure.spec.alpha :as s]
            [expound.alpha :as expound]
            [mount.core :as mount]
            [luminus-vending-machine.core :refer [start-app]]))

(alter-var-root #'s/*explain-out* (constantly expound/printer))

(defn start []
  (mount/start-without #'luminus-vending-machine.core/repl-server))

(defn stop []
  (mount/stop-except #'luminus-vending-machine.core/repl-server))

(defn restart []
  (stop)
  (start))


