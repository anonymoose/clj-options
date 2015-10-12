package com.pvs.joptions;

import org.jquantlib.QL;
import org.jquantlib.daycounters.Actual365Fixed;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.instruments.Option;
import org.jquantlib.time.Date;
import org.jquantlib.time.calendars.Target;

/**
 * Base class that handles most get/set bits.  Override calculateExercise and getOption to make a new subclass.
 *
 * @author anonymoose
 */
public abstract class BaseEquityOption<T extends EquityOptionCalculator> {
    protected final Target calendar;
    protected DayCounter dayCounter = new Actual365Fixed();
    protected Date todaysDate;
    protected Date settlementDate;
    protected Date maturityDate;
    protected Double riskFreeRate = .00273;  //interpolated LIBOR rate (between EDU3 and EDV3)
    protected Double strike = 0.0;
    protected Double underlying = 0.0;
    protected Double dividendYield = 0.0;
    protected Option.Type optionType = Option.Type.Put;

    public BaseEquityOption() {
        calendar = new Target();
    }



    public abstract void calculateExercise();

    public abstract T getOption();

    /**
     * Get the price of the option based on running the BSM.
     * Must be called after calculateExercise
     * @return
     */
    public Double getPresentValue() {
        return getOption().NPV();
    }

    /**
     * Get the implied volatility of the option based on running the BSM.
     * Must be called after calculateExercise
     * @return
     */
    public Double getImpliedVolatility() {
        return getOption().impliedVolatility(getPresentValue());
    }

    public Double getTheta() {
        return getOption().theta();
    }

    public Double getDelta() {
        return getOption().delta();
    }

    public Double getDeltaForward() {
        return getOption().deltaForward();
    }

    public Double getElasticity() {
        return getOption().elasticity();
    }

    public Double getGamma() {
        return getOption().gamma();
    }

    public Double getItmCashProbability() {
        return getOption().itmCashProbability();
    }

    public Double getRho() {
        return getOption().rho();
    }

    public Double getStrikeSensitivity() {
        return getOption().strikeSensitivity();
    }

    public Double getThetaPerDay() {
        return getOption().thetaPerDay();
    }

    public Double getVega() {
        return getOption().vega();
    }

    public java.util.Date getTodaysDate() {
        return todaysDate.longDate();
    }

    public void setTodaysDate(java.util.Date todaysDate) {
        this.todaysDate = new Date(todaysDate);
    }

    public void setTodaysDate(Date todaysDate) {
        this.todaysDate = todaysDate;
    }

    public java.util.Date getSettlementDate() {
        return settlementDate.longDate();
    }

    public void setSettlementDate(java.util.Date settlementDate) {
        this.settlementDate = new Date(settlementDate);
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

    public java.util.Date getMaturityDate() {
        return maturityDate.longDate();
    }

    public void setMaturityDate(java.util.Date maturityDate) {
        this.maturityDate = new Date(maturityDate);
    }

    public void setMaturityDate(Date maturityDate) {
        this.maturityDate = maturityDate;
    }
}
