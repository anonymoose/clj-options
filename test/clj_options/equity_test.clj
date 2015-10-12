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
                       (dtt/today)    ; today's date
                       (dtt/plus (dtt/today) (dtt/days 30)) ; settlement date
                       (dtt/plus (dtt/today) (dtt/days 31)) ; maturity date
                       equity/OPTION-TYPE-PUT
                       21.0
                       25.0
                       )]
         (:today-dt contract) => (dtt/today)
         (:implied-volatility (equity/calculate-american-exercise contract)) => (roughly 0.282)
         (pprint (equity/calculate-american-exercise contract))
         )
       )


(facts "equity/calculate-european-exercise"
       (let [contract (equity/->OptionContract
                       (dtt/today)    ; today's date
                       (dtt/plus (dtt/today) (dtt/days 30)) ; settlement date
                       (dtt/plus (dtt/today) (dtt/days 31)) ; maturity date
                       equity/OPTION-TYPE-CALL
                       21.0
                       25.0
                       )]
         (:today-dt contract) => (dtt/today)
         (:implied-volatility (equity/calculate-european-exercise contract)) => (roughly 0.438)
         (pprint (equity/calculate-european-exercise contract))
         )
       )
