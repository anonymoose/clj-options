(ns clj-options.equity
  (:import [com.pvs.joptions AmericanEquityOption EuropeanEquityOption])
  (:require
   [clj-time.core :as dtt]
   [clj-time.coerce :as dtc]
   ))


(def ^:const OPTION-TYPE-PUT "Put")
(def ^:const OPTION-TYPE-CALL "Call")

(defrecord OptionContract
    [today-dt
     settlement-dt
     maturity-dt
     option-type
     strike
     underlying])


(defrecord OptionStatistics
    [delta
     delta-forward
     elasticity
     gamma
     rho
     theta
     theta-per-day
     vega
     itm-cash-probability
     strike-sensitivity
     implied-volatility
     ])

(defn calculate-american-exercise
  "Calculate exercise statistics for an American style options contract."
  [contract]
  (let [contract (doto (AmericanEquityOption.)
                   (.setTodaysDate (dtc/to-date (:today-dt contract)))
                   (.setSettlementDate (dtc/to-date (:settlement-dt contract)))
                   (.setMaturityDate (dtc/to-date (:maturity-dt contract)))
                   (.setOptionType (:option-type contract))
                   (.setStrike (:strike contract))
                   (.setUnderlying (:underlying contract))
                   (.calculateExercise)
                   )]
    (->OptionStatistics
     (.getDelta contract)
     (.getDeltaForward contract)
     (.getElasticity contract)
     (.getGamma contract)
     (.getRho contract)
     (.getTheta contract)
     (.getThetaPerDay contract)
     (.getVega contract)
     (.getItmCashProbability contract)
     (.getStrikeSensitivity contract)
     (.getImpliedVolatility contract)
     )
    )
  )


(defn calculate-european-exercise
  "Calculate exercise statistics for an European style options contract."
  [contract]
  (let [contract (doto (AmericanEquityOption.)
                   (.setTodaysDate (dtc/to-date (:today-dt contract)))
                   (.setSettlementDate (dtc/to-date (:settlement-dt contract)))
                   (.setMaturityDate (dtc/to-date (:maturity-dt contract)))
                   (.setOptionType (:option-type contract))
                   (.setStrike (:strike contract))
                   (.setUnderlying (:underlying contract))
                   (.calculateExercise)
                   )]
    (->OptionStatistics
     (.getDelta contract)
     (.getDeltaForward contract)
     (.getElasticity contract)
     (.getGamma contract)
     (.getRho contract)
     (.getTheta contract)
     (.getThetaPerDay contract)
     (.getVega contract)
     (.getItmCashProbability contract)
     (.getStrikeSensitivity contract)
     (.getImpliedVolatility contract)
     )
    )
  )




(comment
  ; sample usage
  (require '[clj-time.core :as dtt])
  (require '[clj-time.coerce :as dtc])
  (require '[clj-options.american :as american])

  (let [contract (american/->OptionContract
                  (dtt/today)    ; today's date
                  (dtt/plus (dtt/today) (dtt/days 30)) ; settlement date
                  (dtt/plus (dtt/today) (dtt/days 31)) ; maturity date
                  american/OPTION-TYPE-PUT
                  21.0
                  25.0
                  )]
    (american/calculate-exercise contract)
    )
  )
