INSERT INTO pay_connector_config_params (CONFIG_PARAM, DESCRIZIONE, CODICE_CONNETTORE) VALUES  ( 'DELAY_VERIFICA_STATO','Il parametro indica in millisecondi il tempo che deve essere trascorso dalla apertura della posizione debitoria per poter fare la verifica dello stato della posizione debitoria',NULL);

INSERT INTO pay_connector_config_params (CONFIG_PARAM, DESCRIZIONE, CODICE_CONNETTORE) VALUES  ( 'OFFLINE_PAYMENT_METHODS','Il parametro ( valori true o false) indica se nei pagamenti online modello 1 presentare la possibilità di scaricare un documento per il pagamento offline. Attualmente usato da PAGOUMBRIA',NULL);

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PEOPLE', 'TEMPLATE_OGGETTO_PRATICA', 'Se valorizzato, il campo oggetto dellla pratica viene valorizzato con il contenuto di tale parametro andando a sostituire i segnaposto [-LISTA_PROCEDIMENTI-], [-LISTA_INTERVENTI-] e [-OGGETTO_AU-] con le informazioni presenti nel XML della pratica di Accesso Unitario, se non valorizzato il campo oggetto verrà valorizzato con le informazioni di default.');

INSERT INTO CLMENU_JAVA(ID, DESCRIZIONE, PAGINA, MENULINK, SOFTWARE, JSP, SOFTWAREESCLUSI, LINK_STANDARD, TIPO_FUNZIONALITA, MENULINK_V2) VALUES(1049,'Smistamento pratiche AU','peopleprocsportelli/list.htm','00BU','TT','JAVA','AB','peopleprocsportelli/list.htm','S','00BU');
