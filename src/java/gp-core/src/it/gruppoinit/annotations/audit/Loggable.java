package it.gruppoinit.annotations.audit;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author riccardob
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface Loggable {

    /**
     * Indica la feature per la quale si sta loggando
     * 
     * @return
     */
    String featureName();
}
