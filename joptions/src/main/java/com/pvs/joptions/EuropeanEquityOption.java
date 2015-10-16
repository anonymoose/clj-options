package com.pvs.joptions;

import com.opengamma.analytics.financial.model.option.pricing.analytic.BlackScholesMertonModel;
import com.opengamma.analytics.financial.model.volatility.BlackFormulaRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Entry point into simplified options work with American style options.
 *
 * @author anonymoose
 */
public class EuropeanEquityOption extends BaseEquityOption {
    public EuropeanEquityOption() {
        super();
    }

    @Override
    public void calculate() {

        if (contractPrice == null && impliedVolatility == null) {
            throw new IllegalArgumentException("To find contract price, implied volatility must be known in advance.  To find IV, contract price must be known in advance.  Both are null.");
        }

        BlackScholesMertonModel model = new BlackScholesMertonModel();

        if (contractPrice == null) {
            // @param forward The forward value of the underlying  -> getStrike
            // @param strike The Strike    -> getStrike
            // @param timeToExpiry The time-to-expiry -> getTimeToExpiry
            // @param lognormalVol The log-normal volatility -> impliedVolatility
            // @param isCall True for calls, false for puts  -> isCall()
            contractPrice = BlackFormulaRepository.price(strike, strike, getTimeToExpiry(), impliedVolatility, isCall());
        } else if (impliedVolatility == null) {
            // @param price The <b>forward</b> price -> contractPrice
            // @param forward The forward value of the underlying -> strike
            // @param strike The Strike -> getStrike()
            // @param timeToExpiry The time-to-expiry -> getTimeToExpiry()
            // @param isCall true for call -> isCall()
            impliedVolatility = BlackFormulaRepository.impliedVolatility(contractPrice, getStrike(), getStrike(), getTimeToExpiry(), isCall());
        }

        calculateGreeks();
    }

    public static void main(String[] args) {
        EuropeanEquityOption o = new EuropeanEquityOption();
        o.setTodaysDate(new GregorianCalendar(2015, Calendar.OCTOBER, 16).getTime());
        o.setExpirationDate(new GregorianCalendar(2015, Calendar.NOVEMBER, 20).getTime());
        o.setUnderlying(100.48);
        o.setStrike(115.);
        o.setContractPrice(1.53);
        //o.setImpliedVolatility(20.);
        o.setOptionType("call");
        o.calculate();
        System.out.println(o.getContractPrice());
        System.out.println(o.getImpliedVolatility());
    }

}
