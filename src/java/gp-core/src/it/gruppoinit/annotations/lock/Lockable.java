package it.gruppoinit.annotations.lock;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
/**
 * L'interfaccia provvede ad un meccanismo di lock basato su DB LOCK. Per attivare il lock della funzionalità è
 * necessario decorare il metodo da lockare con questa annotazione. Al momento l'aspetto che gestisce il lock è
 * {@link AuditingSuFile}
 * 
 * @see LockTableService
 * @see AuditingSuFile
 * @author riccardob
 *
 */
public @interface Lockable {
}
