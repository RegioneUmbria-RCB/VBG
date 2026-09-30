package it.paevolution.fileconverter2;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig {

    @Bean
    ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {

	MessageDispatcherServlet servlet = new MessageDispatcherServlet();
	servlet.setApplicationContext(applicationContext);
	servlet.setTransformWsdlLocations(true);
	return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    @Bean(name = "fileconverter")
    DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema fileconverterSchema) {

	DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
	wsdl11Definition.setPortTypeName("fileconverter");
	wsdl11Definition.setLocationUri("/ws");
	wsdl11Definition.setTargetNamespace("http://gruppoinit.it/fileconverter/definitions");
	wsdl11Definition.setSchema(fileconverterSchema);
	return wsdl11Definition;
    }

    @Bean
    public XsdSchema fileconverterSchema() {

	return new SimpleXsdSchema(new ClassPathResource("fileconverter.xsd"));
    }
}
