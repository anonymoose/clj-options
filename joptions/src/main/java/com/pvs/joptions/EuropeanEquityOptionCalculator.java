package com.pvs.joptions;

import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.exercise.AmericanExercise;
import org.jquantlib.exercise.EuropeanExercise;
import org.jquantlib.pricingengines.vanilla.finitedifferences.FDDividendAmericanEngine;
import org.jquantlib.pricingengines.vanilla.finitedifferences.FDDividendEuropeanEngine;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;

import java.util.List;

/**
 * Based on the helper classes provided with jquantlib.  Modified fo fit our environment.
 *
 * Original author: Richard Gomes
 * Modified for inclusing here by anonymoose
 */
public class EuropeanEquityOptionCalculator extends EquityOptionCalculator<FDDividendEuropeanEngine> {

    /**
     * Constructor for European Dividend Options helper class using the finite differences engine
     *
     * @param type is the option call type (Call/Put)
     * @param underlying is the price of the underlying asset
     * @param strike is the strike price at expiration
     * @param r is the risk free rate
     * @param q is the yield rate
     * @param vol is the volatility
     * @param settlementDate is the settlement date
     * @param expirationDate is the expiration date
     * @param dates is a list of dates when dividends are expected to be paid
     * @param dividends is a list of dividends amounts (as a pure value) expected to be paid
     */
    public EuropeanEquityOptionCalculator(
            final Type type,
            final /*@Real*/ double underlying,
            final /*@Real*/ double strike,
            final /*@Rate*/ double r,
            final /*@Rate*/ double q,
            final /*@Volatility*/ double vol,
            final Date settlementDate,
            final Date expirationDate,
            final List<Date> dates,
            final List<Double> dividends) {

        super(FDDividendEuropeanEngine.class,
                type, underlying, strike, r, q, vol,
                settlementDate, new EuropeanExercise(expirationDate),
                dates, dividends);
    }


    /**
     * Constructor for American Dividend Options helper class using the finite differences engine
     *
     * @param type is the option call type (Call/Put)
     * @param underlying is the price of the underlying asset
     * @param strike is the strike price at expiration
     * @param r is the risk free rate
     * @param q is the yield rate
     * @param vol is the volatility
     * @param settlementDate is the settlement date
     * @param expirationDate is the expiration date
     * @param dates is a list of dates when dividends are expected to be paid
     * @param dividends is a list of dividends amounts (as a pure value) expected to be paid
     * @param cal is {@link org.jquantlib.time.Calendar} to be employed
     */
    public EuropeanEquityOptionCalculator(
            final Type type,
            final /*@Real*/ double underlying,
            final /*@Real*/ double strike,
            final /*@Rate*/ double r,
            final /*@Rate*/ double q,
            final /*@Volatility*/ double vol,
            final Date settlementDate,
            final Date expirationDate,
            final List<Date> dates,
            final List<Double> dividends,
            final Calendar cal) {

        super(FDDividendEuropeanEngine.class,
                type, underlying, strike, r, q, vol,
                settlementDate, new EuropeanExercise(expirationDate),
                dates, dividends, cal);
    }


    /**
     * Constructor for American Dividend Options helper class using the finite differences engine
     *
     * @param type is the option call type (Call/Put)
     * @param underlying is the price of the underlying asset
     * @param strike is the strike price at expiration
     * @param r is the risk free rate
     * @param q is the yield rate
     * @param vol is the volatility
     * @param settlementDate is the settlement date
     * @param expirationDate is the expiration date
     * @param dates is a list of dates when dividends are expected to be paid
     * @param dividends is a list of dividends amounts (as a pure value) expected to be paid
     * @param cal is {@link org.jquantlib.time.Calendar} to be employed
     * @param dc is a {@link org.jquantlib.daycounters.DayCounter}
     */
    public EuropeanEquityOptionCalculator(
            final Type type,
            final /*@Real*/ double underlying,
            final /*@Real*/ double strike,
            final /*@Rate*/ double r,
            final /*@Rate*/ double q,
            final /*@Volatility*/ double vol,
            final Date settlementDate,
            final Date expirationDate,
            final List<Date> dates,
            final List<Double> dividends,
            final Calendar cal,
            final DayCounter dc) {

        super(FDDividendEuropeanEngine.class,
                type, underlying, strike, r, q, vol,
                settlementDate, new EuropeanExercise(expirationDate),
                dates, dividends, cal, dc);
    }

}