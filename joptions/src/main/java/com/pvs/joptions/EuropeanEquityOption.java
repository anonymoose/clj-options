package com.pvs.joptions;

import org.jquantlib.QL;
import org.jquantlib.Settings;
import org.jquantlib.daycounters.Actual365Fixed;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.instruments.Option;
import org.jquantlib.time.Date;
import org.jquantlib.time.Month;
import org.jquantlib.time.calendars.Target;

import java.util.ArrayList;
import java.util.List;

/**
 * Entry point into simplified options work with European style options.
 *
 * @author anonymoose
 */
public class EuropeanEquityOption extends BaseEquityOption<EuropeanEquityOptionCalculator> {


    private EuropeanEquityOptionCalculator option;

    public EuropeanEquityOption() {
        super();
    }

    public EuropeanEquityOptionCalculator getOption() {
        QL.require(option != null, "option not calculated.  must call calculateExercise");
        return option;
    }

    public void calculateExercise() {
        Double volatility = 0.2;

        // for now assume no dividends.
        List<Date> divDates = new ArrayList<Date>();
        List<Double> divAmounts = new ArrayList<Double>();

        new Settings().setEvaluationDate(todaysDate);

        option = new EuropeanEquityOptionCalculator(
                optionType, underlying, strike, riskFreeRate, dividendYield, volatility,
                settlementDate, maturityDate,
                divDates, divAmounts,
                calendar, dayCounter);
    }

    public static void main(final String[] args) {
        runRawJquantlib2();
        runTest();
    }

    public static void runTest() {
        EuropeanEquityOption option = new EuropeanEquityOption();
        option.setTodaysDate(new Date(15, Month.May, 1998));
        option.setSettlementDate(new Date(17, Month.May, 1998));
        option.setMaturityDate(new Date(17, Month.May, 1999));
        option.setOptionType("Put");
        option.setStrike(40.0);
        option.setUnderlying(36.0);

        option.calculateExercise();

        System.out.println("NPV = " + option.getPresentValue());
        System.out.println("IV  = " + option.getImpliedVolatility());
        System.out.println("DELTA = " + option.getDelta());
    }

    public static void runRawJquantlib2() {
        Target calendar = new Target();
        Date today = new Date(15, Month.May, 1998);
        Date settlementDate = new Date(17, Month.May, 1998);

        Option.Type type = Option.Type.Put;
        Double strike = 40.0;
        Double underlying = 36.0;
        Double riskFreeRate = 0.06;
        Double volatility = 0.2;
        Double dividendYield = 0.00;

        Date maturityDate = new Date(17, Month.May, 1999);
        DayCounter dc = new Actual365Fixed();

        List<Date> divDates = new ArrayList<Date>();
        List<Double> divAmounts = new ArrayList<Double>();

        new Settings().setEvaluationDate(today);

        final EuropeanEquityOptionCalculator option = new EuropeanEquityOptionCalculator(
                type, underlying, strike, riskFreeRate, dividendYield, volatility,
                settlementDate, maturityDate,
                divDates, divAmounts,
                calendar, dc);

        final double value = option.NPV();
        final double delta = option.delta();
        final double gamma = option.gamma();
        final double theta = option.theta();
        final double vega  = option.vega();
        final double rho   = option.rho();
        final double ivol = option.impliedVolatility(value);


        QL.info(String.format("value       = %13.9f", value));
        QL.info(String.format("delta       = %13.9f", delta));
        QL.info(String.format("gamma       = %13.9f", gamma));
        QL.info(String.format("theta       = %13.9f", theta));
        QL.info(String.format("vega        = %13.9f", vega));
        QL.info(String.format("rho         = %13.9f", rho));
        //
        QL.info(String.format("implied vol = %13.9f", ivol));

    }
}