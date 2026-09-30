package it.gruppoinit.annotations.cache;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation usata da un metodo di una classe che utilizza meccanismi di CACHE (es.
 * {@link UserSecurityServiceImpl#resetObjectCached()}).<br/>
 * L'annotazione va posta sopra un metodo che svuota gli oggetti posti in cache.<br />
 * Una funzionalità di AdminController cerca le classi con i metodi così annotati e li invoca.
 * 
 * @author riccardob
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface DeletableCacheElements {
}
