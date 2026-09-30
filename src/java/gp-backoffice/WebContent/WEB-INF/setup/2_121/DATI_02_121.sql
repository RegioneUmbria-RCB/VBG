INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('AREA_RISERVATA', 'DOL_URL_TERMINI_E_CONDIZIONI', 'Link al file che contiene i termini e condizioni di servizio. Es. per jesi è https://www.comune.jesi.an.it/documents/20119/338749/Termini_condizioni_di_servizio.pdf/9bb0b6d9-d68e-7628-0cc3-ea53146c22d0?t=1715244298881');

INSERT INTO pay_connector_config_params (CONFIG_PARAM,DESCRIZIONE,CODICE_CONNETTORE) VALUES ('CODICE_ABI_ENTE_CREDITORE','Codice Abi dell''ente creditore', null);

UPDATE CLMENU_JAVA SET DESCRIZIONE ='Carica pratiche ZIP' WHERE id=1041;
INSERT INTO verticalizzazioniparametribase (MODULO,PARAMETRO,DESCRIZIONE) VALUES ('COMPORTAMENTI_MERCATI','APP_AMB_GG_FILTRO_RIC_PAGAM','Parametro che permette di specificare il numero dei giorni a partire dai quali ricercare le pendenze. Questo parametro è usato per limitare la ricerca nell''app ambulanti dei pagamenti. Il valore 180 ad esempio prenderà in considerazione i pagamenti delle giornate a partire da 180 giorni ad oggi');
