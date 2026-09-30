/**
 * 
 */
package it.gruppoinit.pal.gp.pay.tracciati;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;

/**
 * @author Franco.Leone
 *
 */
public interface RecordProperty<E> extends Serializable {

    public enum PAD_TYPE {
	LEFT,
	RIGHT,
	NONE
    }

    /**
     * nome della proprietà del tracciato
     * @return
     */
    public String getName();

    /**
     * lunghezza massima del valore scritto nel tracciato
     * @return
     */
    public int getLength();

    /**
     * formato per la formattazione del valore come stringa, necessario per date e numeri
     * ma può essere usato anche da valori stringa per posizionare il valore fra apici.
     * Fare riferimento ai formati utilizzati dalle classi {@link SimpleDateFormat}, {@link DecimalFormat}, {@link MessageFormat} 
     * @return
     */
    public String getFormat();

    /**
     * tipo di padding del valore nel tracciato {@link PAD_TYPE}.NONE indica nessun padding 
     * da usare con tracciati non posizionali che utilizzano separatori per separare i campi
     * @return
     */
    public PAD_TYPE getPaddingType();

    /**
     * carattere utilizzato per il padding del valore
     * @return
     */
    public char getPaddingChar();

    /**
     * Se restituisce true il valore viene troncato se supera la lunghezza massima specificata.
     * Se restituisce false il valore che supera la lunghezza massima specificata darà luogo ad una eccezzione
     * @return
     */
    public boolean isTruncate();

    /**
     * restituisce il valore della proprietà così come impostato programmaticamente o parsato dal tracciato
     * @return
     */
    public E getValue();

    /** 
     * imposta il valore della proprietà
     * @param val
     */
    public void setValue(E val);

    /** 
     * imposta il valore della proprietà parsandone il valore dal formato stringa passato in input
     * @param val
     */
    public void readValue(String val);

    /**
     * genera la stringa da scrivere in output nel tracciato record per questa proprietà
     * @return
     */
    public String writeValue();

    /**
     * restituisce la classe che rappresenta il tipo di datiu accettato dalla proprietà del tracciato
     * @return
     */
    public Class<E> getType();
}
