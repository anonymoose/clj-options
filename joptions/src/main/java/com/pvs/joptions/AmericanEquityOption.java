package com.pvs.joptions;


import com.opengamma.analytics.financial.model.option.pricing.analytic.BjerksundStenslandModel;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Entry point into simplified options work with American style options.
 *
 * @author anonymoose
 */
public class AmericanEquityOption extends BaseEquityOption {

    public AmericanEquityOption() {
        super();
    }

    @Override
    public void calculate() {

        if (contractPrice == null && impliedVolatility == null) {
            throw new IllegalArgumentException("To find contract price, implied volatility must be known in advance.  To find IV, contract price must be known in advance.  Both are null.");
        }

        BjerksundStenslandModel model = new BjerksundStenslandModel();

        if (contractPrice == null) {
            // @param s0 The spot          -> getUnderlying
            // @param k The strike         -> getStrike
            // @param r The risk-free rate -> getRiskFreeRate
            // @param b The cost-of-carry  -> getCostOfCarry
            // @param t The time-to-expiry -> getTimeToExpiry
            // @param sigma The volatility -> impliedVolatility
            // @param isCall true for calls
            contractPrice = model.price(getUnderlying(), getStrike(), getRiskFreeRate(), getCostOfCarry(), getTimeToExpiry(), impliedVolatility, isCall());
        } else if (impliedVolatility == null) {
            // @param price The market price of an American option -> contractPrice
            // @param s0 The spot          -> getUnderlying
            // @param k The strike         -> getStrike
            // @param r The risk-free rate -> getRiskFreeRate
            // @param b The cost-of-carry  -> getCostOfCarry
            // @param t The time-to-expiry -> getTimeToExpiry
            impliedVolatility = model.impliedVolatility(contractPrice, getUnderlying(), getStrike(), getRiskFreeRate(), getCostOfCarry(), getTimeToExpiry(), isCall());
        }

        calculateGreeks();
    }

    // #stok.data.options.ContractQuote{:exc "NASDAQ", :sym "AAPL", :expiration-dt "20151030", :option-type "Call", :strike 101.0, :quote [20151015 11.9 11.9 11.9 11.9 1.0 138.0]}
    public static void main(String[] args) {
        AmericanEquityOption o = new AmericanEquityOption();
        o.setTodaysDate(new Date());
        GregorianCalendar gc = new GregorianCalendar(2015, Calendar.OCTOBER, 30);
        o.setExpirationDate(gc.getTime());
        o.setUnderlying(118.);
        o.setStrike(119.);
        o.setContractPrice(0.76);
        //o.setImpliedVolatility(.4872);
        o.setOptionType("call");
        o.setRiskFreeRate(0.02);
        o.calculate();
        System.out.println(o.getContractPrice());
        System.out.println(o.getImpliedVolatility());

        System.out.println(o.getDelta());
    }
}