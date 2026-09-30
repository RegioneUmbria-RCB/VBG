package it.gruppoinit.pal.gp.core.dao.helper;

import org.hibernate.SessionFactory;
import org.springframework.aop.TargetSource;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SessionFactoryTargetSource implements TargetSource, ApplicationContextAware {

    private DataSourceContainer dataSourceContainer;
    private ApplicationContext applicationContext;

    public DataSourceContainer getDataSourceContainer() {

	return dataSourceContainer;
    }

    public void setDataSourceContainer(DataSourceContainer dataSourceContainer) {

	this.dataSourceContainer = dataSourceContainer;
    }

    @Override
    public Object getTarget() throws Exception {

	SessionFactoryWrapper sfw = dataSourceContainer.getSessionFactoryWrapper(ORMHelper.getHibernateSFKeyUrl(), ORMHelper.getIdcomuneAlias());
	SessionFactory sf = sfw.getSessionFactory();
	return sf;
    }

    @Override
    public Class<SessionFactory> getTargetClass() {

	return SessionFactory.class;
    }

    @Override
    public boolean isStatic() {

	return false;
    }

    @Override
    public void releaseTarget(Object arg0) throws Exception {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

	this.applicationContext = applicationContext;
    }
    
    public SessionFactoryWrapper getSessionFactoryWrapper(){
	return dataSourceContainer.getSessionFactoryWrapper(ORMHelper.getHibernateSFKeyUrl(), ORMHelper.getIdcomuneAlias());
    }
}
