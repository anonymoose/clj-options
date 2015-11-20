package com.pvs.joptions;

import com.opengamma.analytics.financial.model.volatility.BlackFormulaRepository;
import com.opengamma.util.time.DateUtils;
import com.opengamma.util.time.Expiry;
import org.threeten.bp.Instant;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;

import java.util.Date;

import static com.opengamma.analytics.financial.model.volatility.BlackFormulaRepository.*;

/**
 * Base class that handles most get/set bits.
 *
 * @author anonymoose
 */
public abstract class BaseEquityOption {
    protected Date todaysDate;
    protected Date expirationDate;
    protected Double riskFreeRate = .00273;  //interpolated LIBOR rate (between EDU3 and EDV3)
    protected Double strike = 0.0;
    protected Double underlying = 0.0;
    protected Double dividendYield = 0.0;
    protected Double costOfCarry = 0.0;

    protected String optionType = "put";

    protected Double contractPrice;
    protected Double impliedVolatility;

    Double delta = Double.NaN;
    Double gamma = Double.NaN;
    Double theta = Double.NaN;
    Double vega = Double.NaN;
    Double vomma = Double.NaN;
    Double vanna = Double.NaN;
    Double volga = Double.NaN;
    Double dualDelta = Double.NaN;

    public BaseEquityOption() {
    }

    public abstract void calculate();

    protected void calculateGreeks() {
        Double timeToExpiry = getTimeToExpiry();
        delta = delta(getUnderlying(), getStrike(), timeToExpiry, impliedVolatility, isCall());
        gamma = gamma(getUnderlying(), getStrike(), timeToExpiry, impliedVolatility);
        theta = theta(getUnderlying(), getStrike(), timeToExpiry, impliedVolatility, isCall(), getRiskFreeRate());
        vega = vega(getUnderlying(), getStrike(), timeToExpiry, impliedVolatility);
        vomma = vomma(getUnderlying(), getStrike(), timeToExpiry, impliedVolatility);
        vanna = vanna(getUnderlying(), getStrike(), timeToExpiry, impliedVolatility);
        volga = volga(getUnderlying(), getStrike(), timeToExpiry, impliedVolatility);
        dualDelta = dualDelta(getUnderlying(), getStrike(), timeToExpiry, impliedVolatility, isCall());
    }

    public Double getImpliedVolatility() {
        return impliedVolatility;
    }

    public void setImpliedVolatility(Double impliedVolatility) {
        this.impliedVolatility = impliedVolatility;
    }

    public Double getContractPrice() {
        return contractPrice;
    }

    public void setContractPrice(Double contractPrice) {
        this.contractPrice = contractPrice;
    }

    public Double getTheta() {
        return theta;
    }

    public Double getDelta() {
        return delta;
    }

    public Double getGamma() {
        return gamma;
    }

    public Double getItmProbability() {
        return dualDelta;
    }

    public Double getVega() {
        return vega;
    }

    public Double getVomma() {
        return vomma;
    }

    public Double getVanna() {
        return vanna;
    }

    public Double getVolga() {
        return volga;
    }

    public java.util.Date getTodaysDate() {
        return todaysDate;
    }

    public void setTodaysDate(Date todaysDate) {
        this.todaysDate = todaysDate;
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

    public String getOptionType() {
        return optionType;
    }

    public void setOptionType(String optionType) {
        this.optionType = optionType;
    }

    public java.util.Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    protected Double getTimeToExpiry() {
        ZonedDateTime tDate = convertJavaDate(todaysDate);
        ZonedDateTime eDate = convertJavaDate(expirationDate);
        int daysBetween = DateUtils.getDaysBetween(tDate, eDate);
        return (double) daysBetween / 365.;
    }

    protected Expiry getExpiry() {
        ZonedDateTime zExpirationDate = convertJavaDate(expirationDate);
        return new Expiry(zExpirationDate);
    }

    public Double getCostOfCarry() {
        return costOfCarry;
    }

    public void setCostOfCarry(Double costOfCarry) {
        this.costOfCarry = costOfCarry;
    }

    protected boolean isCall() {
        return optionType.equalsIgnoreCase("call");
    }

    protected ZonedDateTime convertJavaDate(Date d) {
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(d.getTime()), ZoneId.systemDefault());
    }
}
