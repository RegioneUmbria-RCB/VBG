package it.gruppoinit.pal.gp.core.dao.helper;

public class TipiScadenzaHelper {

    public static final int FINE_MESE = 0;
    public static final int QUINDICI_DEL_MESE = 1;
    public static final int FINE_MESE_ESCLUSA_PRIMA_RATA = 2;
    public static final int QUINDICI_DEL_MESE_ESCLUSA_PRIMA_RATA = 3;
    public static final int LASCIA_INALTERATO = 4;
    public static final int MESE_SUCCESSIVO_IL_GIORNO_15 = 5;
    public static final int MESE_SUCCESSIVO_IL_GIORNO_15_ESCLUSA_PRIMA_RATA = 6;
    public static final int INIZIO_MESE = 7;
    public static final int FINE_MESE_SUCCESSIVO = 8;
    public static final int SCADENZA_PERIODICA_FISSA = 9;
    /**
     * Mediante una data di riferimento ed un tipo di scadenza calcola la data di scadenza
     * 
     * @param dataRegistrazione
     *            la data di riferimento a partire dalla quale calcolare la scadenza
     * @param tipiScadenza
     *            un tipo di scadenza i cui valori sono presi dalla tabella TIPI_SCADENZA
     * @param periodo
     *            può assumere valore
     *            <DD>/<MM> oppure
     *            <DD>. se SCADENZA_PERIODICA_FISSA valore
     *            <DD>/<MM> -> il risultato sarà la prima data con giorno/mese indicato successiva alla
     *            dataRegistrazione varlore
     *            <DD>-> il risultato sarà la prima data con giorno indicato successiva alla dataRegistrazione
     * @param numeroRata
     *            parametro utilizzato per escludere la prima rata in alcuni casi di
     *            TipoScadenza(FINE_MESE_ESCLUSA_PRIMA_RATA
     *            ,QUINDICI_DEL_MESE_ESCLUSA_PRIMA_RATA,MESE_SUCCESSIVO_IL_GIORNO_15_ESCLUSA_PRIMA_RATA,
     *            FINE_MESE_SUCCESSIVO)
     * @see TipiScadenza
     * @return data di scadenza
     */
    /*
    public static Date trovaScadenza(Date dataRegistrazione, Integer tiposcadenza, String periodo, int numeroRata) {
    
    Calendar calRegistrazione = GregorianCalendar.getInstance();
    calRegistrazione.setTime(dataRegistrazione);
    int mese = calRegistrazione.get(Calendar.MONTH);
    int giorniMese = calRegistrazione.getActualMaximum(Calendar.DAY_OF_MONTH);
    Calendar calProssimoMese = GregorianCalendar.getInstance();
    calProssimoMese.setTime(dataRegistrazione);
    calProssimoMese.add(Calendar.MONTH, 1);
    int fineMeseSuccessivo = calProssimoMese.getActualMaximum(Calendar.DAY_OF_MONTH);
    switch (tiposcadenza.intValue()) {
    case INIZIO_MESE:
        calRegistrazione.set(Calendar.DATE, 1);
        if (calRegistrazione.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
    	// se domenica sposto la scadenza al lunedì seguente
    	calRegistrazione.set(Calendar.DATE, 2);
        } else if (calRegistrazione.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
    	// se sabato sposto la scadenza al lunedì seguente
    	calRegistrazione.set(Calendar.DATE, 3);
        }
        break;
    case FINE_MESE: // setta la scadenza all'ultimo giorno del mese corrente
        calRegistrazione.set(Calendar.DATE, giorniMese);
        break;
    case QUINDICI_DEL_MESE: // setta la scadenza il 15 del mese successivo
        if (calRegistrazione.get(Calendar.DAY_OF_MONTH) <= 15) {
    	calRegistrazione.set(Calendar.DATE, 15);
        } else {
    	calRegistrazione.set(Calendar.MONTH, mese + 1);
    	calRegistrazione.set(Calendar.DATE, 15);
        }
        break;
    case FINE_MESE_ESCLUSA_PRIMA_RATA:
        if (numeroRata != 0) {
    	calRegistrazione.set(Calendar.DATE, giorniMese);
        }
        break;
    case QUINDICI_DEL_MESE_ESCLUSA_PRIMA_RATA:
        if (numeroRata != 0) {
    	if (calRegistrazione.get(Calendar.DAY_OF_MONTH) <= 15) {
    	    calRegistrazione.set(Calendar.DATE, 15);
    	} else {
    	    calRegistrazione.set(Calendar.MONTH, mese + 1);
    	    calRegistrazione.set(Calendar.DATE, 15);
    	}
        }
        break;
    case LASCIA_INALTERATO:
        break;
    case MESE_SUCCESSIVO_IL_GIORNO_15:
        calRegistrazione.set(Calendar.MONTH, mese + 1);
        calRegistrazione.set(Calendar.DATE, 15);
        break;
    case MESE_SUCCESSIVO_IL_GIORNO_15_ESCLUSA_PRIMA_RATA:
        if (numeroRata != 0) {
    	calRegistrazione.set(Calendar.MONTH, mese + 1);
    	calRegistrazione.set(Calendar.DATE, 15);
        }
        break;
    case FINE_MESE_SUCCESSIVO: {
        calProssimoMese.set(Calendar.DATE, fineMeseSuccessivo);
        return calProssimoMese.getTime();
    }
    case SCADENZA_PERIODICA_FISSA: {
       
    }
    default:
        break;
    }
    return calRegistrazione.getTime();
    }
    */
}
