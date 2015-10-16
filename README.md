# clj-options

A Clojure library for working with stock option derivatives, a.k.a. options.

Why?  Because OpenGamma is a fantastic, generic library for handling options in java.  All kinds of options, both exotic and vanilla.
I just wanted a library I could use that allowed me to do some simple quant work on vanilla options, in Clojure, without requiring a
PhD in astrophysics.

This library wraps OpenGamma, and exposes only the stuff you need.  Put in some data available from your broker, get out 
implied volatility and the greeks.

# WARNING
This library comes with no warranty of any kind.  User of the library assumes all risk, financial and otherwise.

If you blow up your account using this library, it is your responsibility and the author is not liable.

If the math is wrong, or interpreted incorrectly, and you blow up your account, it is your responsibility and the author is not liable.

## Dependencies

clj-options relies on the following libraries:

- Joda Time for time/date handling.
- OpenGamma for handling all the math heavy lifting.
- joptions for simplifying access to JQuantLib.  joptions is provided here as a subproject.

## Usage

```
user>  (require '[clj-options.equity :as equity])
user>  (require '[clj-time.core :as dtt])
user>  (require '[clj-time.coerce :as dtc])
user>  (use '[clojure.pprint])

user>  (let [contract (equity/->OptionContract
                   (dtt/today)                         ; today's date
                   (dtt/plus (dtt/today) (dtt/days 30)) ; expiration date
                   equity/OPTION-TYPE-CALL
                   115.0
                   100.48
                   1.58
                   nil
                   )]
           (equity/calculate-american-exercise contract)
         )

{:contract-price 1.58,
 :implied-volatility 0.5187008616664563,
 :delta 0.2023395224636707,
 :gamma 0.018867609640569494,
 :vega 8.121224102539303,
 :theta -25.621630280871198,
 :vomma 12.811928087667539,
 :vanna 0.5337311051138213,
 :volga 12.811928087667539}

```


## License

Copyright © 2015 Palm Valley Software

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
