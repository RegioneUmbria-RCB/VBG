package it.gruppoinit.constants;

public class DatamatrixCostanti {

    /**
     * Costanti per data matrix
     */
    public static final String FILLER_DATAMATRIX = "            ";
    public static final String PATTERN_DATAMATRIX = "codfase=NBPA;{0}1P1{1}{2}{3}{4}{5}A";
    public static final String PATTERN_CODELINE = "18{0}12{1}10{2}3896";
    public static final Integer DATAMATRIX_LUNGHEZZA_CAMPO_CF_ENTE = 11;
    public static final Integer DATAMATRIX_LUNGHEZZA_CAMPO_IMPORTO = 10;
    public static final Integer DATAMATRIX_LUNGHEZZA_CAMPO_CAUSALE = 110;
    public static final Integer DATAMATRIX_LUNGHEZZA_CAMPO_ANAGRAFICA_DEBITORE = 40;
    public static final Integer DATAMATRIX_LUNGHEZZA_CAMPO_CF_DEBITORE = 16;
    public static final Integer AVVISO_LUNGHEZZA_CAMPO_CAUSALE = 60;
    public static final Integer AVVISO_LUNGHEZZA_CAMPO_INDIRIZZO_DESTINATARIO = 40;
    // costanti file di properties
    public static final Integer DATAMATRIX_CODELINE_LUNGHEZZA_CODICE = 18;
    public static final Integer DATAMATRIX_CODELINE_LUNGHEZZA_CONTO = 12;
    public static final Integer DATAMATRIX_CODELINE_LUNGHEZZA_IMPORTO = 10;
    public static final Integer DATAMATRIX_CODELINE_LUNGHEZZA_TIPODOC = 3;
    public static final Integer DATAMATRIX_NUM_FILLER = 12;
    public static final String[] DATAMATRIX_CARATTERI_AMMESSI = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P",
	    "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q",
	    "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "&", "'", "-", ".", ":", "_", "," };
}
