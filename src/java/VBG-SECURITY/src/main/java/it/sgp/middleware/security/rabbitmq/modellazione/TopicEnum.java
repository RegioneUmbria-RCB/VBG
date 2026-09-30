package it.sgp.middleware.security.rabbitmq.modellazione;

public enum TopicEnum {

	AUTENTICAZIONE_ACCESSO_LOGIN("autenticazione.accesso.login");
	
    private String value;
    
    private TopicEnum(String value) {

	this.value = value;

    }

    public String getValue() {

	return value;
    }
	
}
