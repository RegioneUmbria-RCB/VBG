/**
 * 
 */
package it.gruppoinit.stc.upgr.hibernate;

// §§§BEGIN§§§
import it.gruppoinit.upgr.hibernate.IHibernateConfigurationProvider;
// §§§END§§§
import org.hibernate.cfg.Configuration;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;

/**
 * @author francol Classe utilizzata in STC per inizializzare la SessionFactory di Hibernate. Implementa l'interfaccia
 *         {@link IHibernateConfigurationProvider} fornendo alla procedura di setup di UPGR4J il riferimento alla
 *         configurazione di Hibernate
 */
public class STCSessionFactoryBean extends AnnotationSessionFactoryBean /* §§§BEGIN§§§ */implements IHibernateConfigurationProvider /* §§§END§§§ */{

    /* §§§BEGIN§§§ */@Override
    /* §§§END§§§ */
    public Configuration getHibernateConfiguration() {

	return this.getConfiguration();
    }
}
