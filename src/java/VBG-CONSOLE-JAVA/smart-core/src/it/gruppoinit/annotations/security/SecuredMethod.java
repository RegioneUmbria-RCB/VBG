package it.gruppoinit.annotations.security;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation usata dai metodi di un componente annotato come {@code @SecuredComponent}. <br/>
 * Ogni metodo viene visto come funzionalità del componente.
 * 
 * @author riccardob
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface SecuredMethod {

    /**
     * Definisce i valori che rappresentano un metodo che legge solamente dati (<b>ReadWriteMethod.READ</b>), che scrive
     * dati (<b>ReadWriteMethod.WRITE</b>) o che effettua entrambe le operazioni
     */
    public enum ReadWriteMethod {
	READ, WRITE
    }

    /**
     * La chiave del bundle dal quale recuperare la descrizione della funzionalità.
     */
    String key();

    /**
     * La chiave del bundle dal quale recuperare le informazioni di aiuto della funzionalità.
     */
    String helpKey() default "";

    /**
     * L'ordine di visualizzazione nella lista delle funzionalità.<br />
     * Il valore predefinito è 0 e se non specificato l'ordine di presentazione sarà casuale.
     */
    int order() default 0;

    /**
     * Indica se il metodo viene usato per leggere dati (operazioni readonly) o anche per scrivere dati. <br/>
     * Il valore di default è <b>ReadWriteMethod.READ</b>.<br />
     * Qualificando correttamente i metodi è possibile visualizzare e separare tutte le funzionalità READ-ONLY da quelle
     * che eseguono operazioni sui dati.
     */
    ReadWriteMethod methodType() default ReadWriteMethod.READ;
}
