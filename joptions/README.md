# JOptions

Java library that makes it easy to consume Opengamma in clojure.

Because sometimes you just want IV for an American option and you don't want to get your PhD first.

This does what I need it to do, but might provide a nice shell for doing other interesting work with Opengamma.


# Build

Now you can work on the joptions code and build with maven as usual.
```
mvn clean
mvn package
mvn install
```

If you are working on the with clj-options, joptions is now installed and lein should be able to recognize it.


## Usage

```
        AmericanEquityOption o = new AmericanEquityOption();
        o.setTodaysDate(new Date());
        GregorianCalendar gc = new GregorianCalendar(2015, Calendar.NOVEMBER, 20);
        o.setExpirationDate(gc.getTime());
        o.setUnderlying(99.67);
        o.setStrike(115.);
        //o.setContractPrice(1.58);
        o.setImpliedVolatility(.4872);
        o.setOptionType("call");
        o.setRiskFreeRate(0.02);
        o.calculate();
        System.out.println(o.getContractPrice());
        System.out.println(o.getImpliedVolatility());

        System.out.println(o.getDelta());
        
```

## License

Copyright © 2015 Palm Valley Software

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
