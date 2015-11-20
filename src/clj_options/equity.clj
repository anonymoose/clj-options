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
     expiration-dt
     option-type
     strike
     underlying
     contract-price
     implied-volatility
     ])


(defrecord OptionContractStatistics
    [contract-price
     implied-volatility
     delta
     gamma
     vega
     theta
     vomma
     vanna
     volga
     itm-probability
     ])

(defmacro unless [pred a b]
  `(if (not ~pred) ~a ~b))

;; usage:

(defn- calculate-generic-exercise
  "Pass in the instance from joptions you want to handle the math.  AmericanEquityOption or EuropeanEquityOption"
  [contract handler]
  (let [contract (doto handler
                   (.setTodaysDate (dtc/to-date (:today-dt contract)))
                   (.setExpirationDate (dtc/to-date (:expiration-dt contract)))
                   (.setOptionType (:option-type contract))
                   (.setStrike (:strike contract))
                   (.setUnderlying (:underlying contract))
                   (.setContractPrice (:contract-price contract))            ; one of these will be null
                   (.setImpliedVolatility (:implied-volatility contract))    ; one of these will be null
                   (.calculate)
                   )]
    (->OptionContractStatistics
     (.getContractPrice contract)
     (.getImpliedVolatility contract)
     (.getDelta contract)
     (.getGamma contract)
     (.getVega contract)
     (.getTheta contract)
     (.getVomma contract)
     (.getVanna contract)
     (.getVolga contract)
     (.getItmProbability contract)
     )
    )
  )


(defn calculate-american-exercise
  "Calculate exercise statistics for an American style options contract."
  [contract]
  (calculate-generic-exercise contract (AmericanEquityOption.))
  )


(defn calculate-european-exercise
  "Calculate exercise statistics for an European style options contract."
  [contract]
  (calculate-generic-exercise contract (EuropeanEquityOption.))
  )


(comment
  ; sample usage
  (require '[clj-time.core :as dtt])
  (require '[clj-time.coerce :as dtc])
  (require '[clj-options.equity :as equity])

  (pprint (let [contract (equity/->OptionContract
                   (dtt/today)                         ; today's date
                   (dtt/plus (dtt/today) (dtt/days 30)) ; expiration date
                   equity/OPTION-TYPE-CALL
                   115.0
                   100.48
                   1.58
                   nil
                   )]
     (equity/calculate-american-exercise contract)
     ))
  )
