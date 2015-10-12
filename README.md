# clj-options

A Clojure library for working with stock option derivatives, a.k.a. options.

Why?  Because jquantlib is a fantastic, generic library for handling options in java.  All kinds of options, both exotic and vanilla.
I just wanted a library I could use that allowed me to do some simple quant work on vanilla options, in Clojure, without requiring a
PhD in physics.

This library wraps JQuantLib, and exposes only the stuff you need.  Put in some data available from your broker, get out 
implied volatility and the greeks.

# WARNING
This library comes with no warranty of any kind.  User of the library assumes all risk, financial and otherwise.

If you blow up your account using this library, it is your responsibility and the author is not liable.

If the math is wrong, or interpreted incorrectly, and you blow up your account, it is your responsibility and the author is not liable.

## Dependencies

clj-options relies on the following libraries:

- Joda Time for time/date handling.
- JQuantLib for handling all the math heavy lifting.
- joptions for simplifying access to JQuantLib.  joptions is provided here as a subproject.

## Usage

```
user>  (require '[clj-options.equity :as equity])
user>  (require '[clj-time.core :as dtt])
user>  (require '[clj-time.coerce :as dtc])
user>  (use '[clojure.pprint])

user>  (let [contract (equity/->OptionContract
                 (dtt/today)                                ; today's date
                 (dtt/plus (dtt/today) (dtt/days 30))       ; settlement date
                 (dtt/plus (dtt/today) (dtt/days 31))       ; maturity date
                 equity/OPTION-TYPE-PUT                     ; Put option
                 8.0                                        ; Strike price
                 25.0                                       ; Underlying Price
                 )]
           (pprint (equity/calculate-american-exercise contract)))

{:delta -1.1102230246251565E-16,
 :delta-forward -1.1102147207963278E-16,
 :elasticity 0.0,
 :gamma 0.0,
 :rho -2.4333473332522257E-18,
 :theta 2.4247089502618544E-18,
 :theta-per-day 6.643038219895491E-21,
 :vega 0.0,
 :itm-cash-probability 0.9999999999999999,
 :strike-sensitivity 1.1102147207963278E-16,
 :implied-volatility 1.0E-7}


```


## License

Copyright © 2015 Palm Valley Software

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
