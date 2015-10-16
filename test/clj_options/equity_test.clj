(ns clj-options.equity-test
  (:require [clojure.test :refer :all]
            [clj-options.equity :as equity]
            [clj-time.core :as dtt]
            [clj-time.coerce :as dtc]
            )
  (:use [midje.sweet]
        [clojure.pprint]))

(facts "equity/calculate-american-exercise"
       (let [contract (equity/->OptionContract
                       (dtt/today)                         ; today's date
                       (dtt/plus (dtt/today) (dtt/days 30)) ; expiration date
                       equity/OPTION-TYPE-CALL
                       115.0
                       100.48
                       1.58
                       nil
                       )]
         (:today-dt contract) => (dtt/today)
         (:implied-volatility (equity/calculate-american-exercise contract)) => (roughly 0.5187008616664563)
         (pprint (equity/calculate-american-exercise contract))
         )
       )


(facts "equity/calculate-european-exercise"
              (let [contract (equity/->OptionContract
                       (dtt/today)                         ; today's date
                       (dtt/plus (dtt/today) (dtt/days 30)) ; expiration date
                       equity/OPTION-TYPE-CALL
                       115.0
                       100.48
                       1.58
                       nil
                       )]
         (:today-dt contract) => (dtt/today)
         (:implied-volatility (equity/calculate-european-exercise contract)) => (roughly 0.1201313754026316)
         (pprint (equity/calculate-european-exercise contract))
         )
       )
