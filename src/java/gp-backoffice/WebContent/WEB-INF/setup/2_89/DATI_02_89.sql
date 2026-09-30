INSERT INTO PAY_CONNECTOR_CONFIG_PARAMS(CONFIG_PARAM,DESCRIZIONE,CODICE_CONNETTORE) VALUES ('GOV_PAY_URL_API_PENDENZE','Indica la url per invocare le API del servizio pendenze. es /govpay/backend/api/pendenze/rs/basic/v2/pendenze',NULL);

INSERT INTO PAY_CONNECTOR_CONFIG_PARAMS(CONFIG_PARAM,DESCRIZIONE,CODICE_CONNETTORE) VALUES ('GOV_PAY_URL_API_PROFILO','Indica la url per invocare le API del servizio profilo. es /govpay/backend/api/pendenze/rs/basic/v2',NULL);

INSERT INTO PAY_CONNECTOR_CONFIG_PARAMS(CONFIG_PARAM,DESCRIZIONE,CODICE_CONNETTORE) VALUES ('GOV_PAY_URL_API_PAGAMENTI','Indica la url per invocare le API del servizio pagamenti. es /govpay/frontend/api/pagamento/rs/basic/pagamenti',NULL);

INSERT INTO verticalizzazioniparametribase ( modulo, parametro, descrizione) VALUES ( 'NODO_PAGAMENTI', 'CREA_PER_SOGGETTI_COLLEGATI', 'Parametro che in fase di creazione della posizione debitoria da interfaccia (istanzeoneri) determina se creare piu'' posizioni debitorie a fronte di un onere. E'' il caso ad esempio dell''obbligato in solido per la tematica dell'' ufficio sanzioni che prevede che una sanzione sia inoltrata al trasgressore e all''eventuale obbligato in solido. Entrambi i soggetti possono pagare la sanzione ma ognuno deve essere destinatario di un proprio avviso di pagamento. Accetta valori S o N (valore predefinito N cioe'' non attivo)');

INSERT INTO PAY_CONNECTOR_CONFIG_PARAMS(CONFIG_PARAM,DESCRIZIONE,CODICE_CONNETTORE) VALUES ('GOV_PAY_BASE_URL_PATCH_OPS','Indica la base url per le operazioni REST con metodo PATCH. E'' successo che per le operazioni con questo metodo abbiano dovuto configurare nginx con una path di base differente es: https://govway-patch-dev.regione.abruzzo.it invece che https://govway-dev.regione.abruzzo.it',NULL);
