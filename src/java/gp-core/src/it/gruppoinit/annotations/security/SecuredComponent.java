package it.gruppoinit.annotations.security;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation per legare il componente che la utilizza ad aspetti legati alla sicurezza
 * 
 * @author riccardob
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface SecuredComponent {

    /**
     * La chiave del bundle dal quale recuperare la descrizione del componente
     */
    String key();

    /**
     * La chiave del bundle dal quale recuperare le informazioni di aiuto del componente
     */
    String helpKey() default "";

    /**
     * Se il componente viene presentato da una voce di menù oppure richiamato da link di altri componenti <br />
     * Valore default <b>true</b>
     */
    boolean rootElement() default true;

    /**
     * a quale menù viene agganciato per essere visualizzato il valore predefinito è 1
     */
    int parentMenuId() default 1;

    /**
     * L'ordine di visualizzazione all'interno del parentMenuId().<br />
     * Il valore predefinito è 0 e se non specificato l'ordine di presentazione sarà casuale.
     */
    int order() default 0;

    /**
     * Se <b>rootElement() = false</b> allora definisce quale classe viene rappresentata come genitore dell componente. <br />
     * Ad esempio, nel caso di Movimentiistanze che viene visto come componente figlio di Istanze secondo questa
     * alberatura
     * 
     * <pre>
     * 	--> Istanze
     * 		|
     * 		--> Movimentiistanze
     * </pre>
     * 
     * Avremo la seguente notazione in Movimentiistanze
     * 
     * <pre>
     * @SecuredComponent(rootElement=false, parentClass=it.gruppoinit.pal.gp.core.domain.Istanze.class) 
     * Movimentiistanze
     * </pre>
     * 
     */
    Class<?> parentClass() default SecuredComponent.class;
}
