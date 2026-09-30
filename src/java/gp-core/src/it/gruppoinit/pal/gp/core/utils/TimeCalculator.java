package it.gruppoinit.pal.gp.core.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Esempio di Uso
 * 
 * <pre>
 * 
 * public static void main(String[] args) throws InterruptedException {
 * 
 *     TimeCalculator t = new TimeCalculator("bocci");
 *     for (int i = 0; i < 10; i++) {
 * 	Thread.sleep(100 * i);
 * 	System.out.println(t.getTimeElapsed());
 *     }
 *     System.out.println(t.tempistiche());
 * }
 * </pre>
 * 
 * @author riccardo.bocci
 *
 */
public class TimeCalculator {

    private static final String TEMPISTICA = "\n\tTimeCalculator[%s]" + //
					     "\n\tConteggio     : %s chiamate" + //
					     "\n\tTempo minimo  : %s secondi" + //
					     "\n\tTempo massimo : %s secondi" + //
					     "\n\tMedia         : %s secondi";
    private static final String TIME_ELAPSED = "\n\tTimeCalculator[%s]" + //
					       "\n\tElapsed Time in milli seconds: %s\n\tElapsed Time in seconds: %s";
    private long start = System.currentTimeMillis();
    private List<Long> storia = new ArrayList<Long>();
    private String timeCalculatorName = getClass().getName() + "-" + System.currentTimeMillis();

    public TimeCalculator(String calculatorName) {

	super();
	if (!(calculatorName == null || calculatorName.trim().equals(""))) {
	    timeCalculatorName = calculatorName;
	}
    }

    public String getTimeElapsed() {

	long milliseconds = (System.currentTimeMillis() - start);
	storia.add(Long.valueOf(milliseconds));
	resetStartTime();
	double d = 0;
	if (milliseconds > 0) {
	    d = Double.valueOf(milliseconds) / 1000l;
	}
	return String.format(TIME_ELAPSED, timeCalculatorName, milliseconds, d);
    }

    private void resetStartTime() {

	start = System.currentTimeMillis();
    }

    public String tempistiche() {

	int lenght = storia.size();
	long tot = 0L;
	long max = 0L;
	long min = 999999999999999999L;
	for (Iterator<Long> iterator = storia.iterator(); iterator.hasNext();) {
	    long long1 = iterator.next().longValue();
	    if (min > long1) {
		min = long1;
	    }
	    if (max < long1) {
		max = long1;
	    }
	    tot += long1;
	}
	return String.format(TEMPISTICA, timeCalculatorName, lenght, TimeUnit.MILLISECONDS.toSeconds(min), TimeUnit.MILLISECONDS.toSeconds(max),
		TimeUnit.MILLISECONDS.toSeconds(tot / lenght));
    }
}
