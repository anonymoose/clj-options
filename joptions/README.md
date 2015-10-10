# JOptions

Java library that makes it easy to consume JQuantLib in clojure.

Because sometimes you just want IV for an American option and you don't want to get your PhD first.

This does what I need it to do, but might provide a nice shell for doing other interesting work with JQuantLib.

## Setup 

JQuantLib doesn't have any maven capability that I can find.  So I have packaged the jar that we use here.

From this directory, run maven and install the jar file locally.  You only need to do this once.
```
mkdir local_repo
mvn deploy:deploy-file -Dfile=lib/jquantlib-0.2.4.jar -DartifactId=jquantlib -Dversion=0.2.4 -DgroupId=jquantlib -Dpackaging=jar -Durl=file:local_repo
```

Now you can work on the joptions code and build with maven as usual.
```
mvn clean
mvn package
mvn install
```

If you are working on the with clj-options, joptions is now installed and lein should be able to recognize it.


## Usage

```
        AmericanEquityOption option = new AmericanEquityOption();
        option.setTodaysDate(new Date(15, Month.May, 1998));
        option.setSettlementDate(new Date(17, Month.May, 1998));
        option.setMaturityDate(new Date(17, Month.May, 1999));
        option.setOptionType("Put");
        option.setStrike(40.0);
        option.setUnderlying(36.0);

        option.calculateExercise();

        System.out.println("NPV = " + option.getPresentValue());
        System.out.println("IV  = " + option.getImpliedVolatility());

```

## License

Copyright © 2015 Palm Valley Software

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
