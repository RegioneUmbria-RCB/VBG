package it.gruppoinit.pal.gp.backoffice.upgr.hibernate;

import it.gruppoinit.pal.gp.core.dao.helper.SessionFactoryTargetSource;
import it.gruppoinit.pal.gp.core.dao.helper.SessionFactoryWrapper;
/* §§§BEGIN§§§ */
import it.gruppoinit.upgr.hibernate.IHibernateConfigurationProvider;

/* §§§END§§§ */
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component("hibernateConfigProvider")
public class SigeproHibernateConfigurationProvider /* §§§BEGIN§§§ */implements IHibernateConfigurationProvider /* §§§END§§§ */{

    @Autowired
    private ApplicationContext applicationContext;

    /* §§§BEGIN§§§ */@Override
    /* §§§END§§§ */
    public Configuration getHibernateConfiguration() {

	SessionFactoryTargetSource sfts = (SessionFactoryTargetSource) applicationContext.getBean("sessionFactoryTarget");
	SessionFactoryWrapper wrapper = sfts.getSessionFactoryWrapper();
	return wrapper.getHibernateConfig();
    }
}
