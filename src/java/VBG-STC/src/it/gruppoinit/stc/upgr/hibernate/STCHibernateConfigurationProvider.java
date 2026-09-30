package it.gruppoinit.stc.upgr.hibernate;

import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;
import org.springframework.stereotype.Component;
// §§§BEGIN§§§
import it.gruppoinit.upgr.hibernate.IHibernateConfigurationProvider;

// §§§END§§§
@Component("hibernateConfigProvider")
public class STCHibernateConfigurationProvider /* §§§BEGIN§§§ */implements IHibernateConfigurationProvider /* §§§END§§§ */{

    @Autowired
    private ApplicationContext applicationContext;

    /* §§§BEGIN§§§ */@Override
    /* §§§END§§§ */
    public Configuration getHibernateConfiguration() {

	AnnotationSessionFactoryBean sfts = (AnnotationSessionFactoryBean) applicationContext.getBean("&sessionFactory");
	return sfts.getConfiguration();
    }
}
