package com.pvs.joptions;

import org.jquantlib.QL;
import org.jquantlib.Settings;
import org.jquantlib.daycounters.Actual365Fixed;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.exercise.AmericanExercise;
import org.jquantlib.exercise.Exercise;
import org.jquantlib.instruments.*;
import org.jquantlib.pricingengines.vanilla.BjerksundStenslandApproximationEngine;
import org.jquantlib.processes.BlackScholesMertonProcess;
import org.jquantlib.quotes.Handle;
import org.jquantlib.quotes.Quote;
import org.jquantlib.quotes.SimpleQuote;
import org.jquantlib.termstructures.BlackVolTermStructure;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.termstructures.volatilities.BlackConstantVol;
import org.jquantlib.termstructures.yieldcurves.FlatForward;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.Month;
import org.jquantlib.time.calendars.Target;

/**
 * Entry point into simplified options work with American style options.
 *
 * @author anonymoose
 */
public class AmericanEquityOption {

    private final Target calendar;
    private Settings settings = new Settings();
    private Date todaysDate;
    private Date settlementDate;
    private Date maturityDate;
    private Double riskFreeRate = .00273;  //interpolated LIBOR rate (between EDU3 and EDV3)
    private Double strike = 0.0;
    private Double underlying = 0.0;
    private Double dividendYield = 0.0;
    private Option.Type optionType = Option.Type.Put;
    private BlackScholesMertonProcess bsmProcess;
    private VanillaOption option;

    public AmericanEquityOption() {
        calendar = new Target();
    }

    public void calculateExercise() {
        QL.require(todaysDate != null, "todaysDate required");
        QL.require(settlementDate != null, "settlementDate required");
        QL.require(maturityDate != null, "maturityDate required");
        QL.require(optionType != null, "option type required");
        QL.require(underlying > 0, "Underlying required");
        QL.require(strike > 0, "strike required");
        QL.require(riskFreeRate > 0, "riskFreeRate required");

        settings.setEvaluationDate(todaysDate);

        final double volatility = 0.2;
        final DayCounter dayCounter = new Actual365Fixed();

        // Define exercise for American Options
        final Exercise americanExercise = new AmericanExercise(settlementDate, maturityDate);

        // bootstrap the yield/dividend/volatility curves
        final Handle<Quote> underlyingH = new Handle<Quote>(new SimpleQuote(underlying));
        final Handle<YieldTermStructure> flatDividendTS = new Handle<YieldTermStructure>(new FlatForward(settlementDate, dividendYield, dayCounter));
        final Handle<YieldTermStructure> flatTermStructure = new Handle<YieldTermStructure>(new FlatForward(settlementDate, riskFreeRate, dayCounter));
        final Handle<BlackVolTermStructure> flatVolTS = new Handle<BlackVolTermStructure>(new BlackConstantVol(settlementDate, calendar, volatility, dayCounter));
        final Payoff payoff = new PlainVanillaPayoff(optionType, strike);

        bsmProcess = new BlackScholesMertonProcess(underlyingH, flatDividendTS, flatTermStructure, flatVolTS);

        option = new VanillaOption(payoff, americanExercise);

        // Bjerksund and Stensland approximation for American
        option.setPricingEngine(new BjerksundStenslandApproximationEngine(bsmProcess));
    }

    public Double getPresentValue() {
        QL.require(option != null, "option not calculated.  must call calculate{American|European}Exercise");
        return option.NPV();
    }

    public Double getImpliedVolatility() {
        QL.require(option != null, "option not calculated.  must call calculate{American|European}Exercise");
        return option.impliedVolatility(getPresentValue(), bsmProcess);
    }

    public static void main(final String[] args) {
        runRawJquantlib();
        runTest();
    }

    public static void runTest() {
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
    }

    public static void runRawJquantlib() {
        final Calendar calendar = new Target();
        final Date todaysDate = new Date(15, Month.May, 1998);
        final Date settlementDate = new Date(17, Month.May, 1998);
        new Settings().setEvaluationDate(todaysDate);

        // our options
        final Option.Type type = Option.Type.Put;
        final double strike = 40.0;
        final double underlying = 36.0;
        /*@Rate*/final double riskFreeRate = 0.00273;
        final double volatility = 0.2;
        final double dividendYield = 0.00;


        final Date maturity = new Date(17, Month.May, 1999);
        final DayCounter dayCounter = new Actual365Fixed();

        // Define exercise for American Options
        final Exercise americanExercise = new AmericanExercise(settlementDate, maturity);

        // bootstrap the yield/dividend/volatility curves
        final Handle<Quote> underlyingH = new Handle<Quote>(new SimpleQuote(underlying));
        final Handle<YieldTermStructure> flatDividendTS = new Handle<YieldTermStructure>(new FlatForward(settlementDate, dividendYield, dayCounter));
        final Handle<YieldTermStructure> flatTermStructure = new Handle<YieldTermStructure>(new FlatForward(settlementDate, riskFreeRate, dayCounter));
        final Handle<BlackVolTermStructure> flatVolTS = new Handle<BlackVolTermStructure>(new BlackConstantVol(settlementDate, calendar, volatility, dayCounter));
        final Payoff payoff = new PlainVanillaPayoff(type, strike);

        final BlackScholesMertonProcess bsmProcess = new BlackScholesMertonProcess(underlyingH, flatDividendTS, flatTermStructure, flatVolTS);

        // American Options
        final VanillaOption americanOption = new VanillaOption(payoff, americanExercise);

        // Bjerksund and Stensland approximation for American
        americanOption.setPricingEngine(new BjerksundStenslandApproximationEngine(bsmProcess));
        double price = americanOption.NPV();
        double iv = americanOption.impliedVolatility(price, bsmProcess);

        System.out.println("NPV = " + price);
        System.out.println("IV  = " + iv);
    }

    public Date getTodaysDate() {
        return todaysDate;
    }

    public void setTodaysDate(Date todaysDate) {
        this.todaysDate = todaysDate;
    }

    public Date getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(Date settlementDate) {
        this.settlementDate = settlementDate;
    }

    public Double getRiskFreeRate() {
        return riskFreeRate;
    }

    public void setRiskFreeRate(Double riskFreeRate) {
        this.riskFreeRate = riskFreeRate;
    }

    public Double getStrike() {
        return strike;
    }

    public void setStrike(Double strike) {
        this.strike = strike;
    }

    public Double getUnderlying() {
        return underlying;
    }

    public void setUnderlying(Double underlying) {
        this.underlying = underlying;
    }

    public Double getDividendYield() {
        return dividendYield;
    }

    public void setDividendYield(Double dividendYield) {
        this.dividendYield = dividendYield;
    }

    public Option.Type getOptionType() {
        return optionType;
    }

    public void setOptionType(String strOptionType) {
        if ("put".equalsIgnoreCase(strOptionType))
            this.optionType = Option.Type.Put;
        else if ("call".equalsIgnoreCase(strOptionType))
            this.optionType = Option.Type.Call;
        else QL.error("Invalid option type " + strOptionType);
    }

    public Date getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(Date maturityDate) {
        this.maturityDate = maturityDate;
    }
}