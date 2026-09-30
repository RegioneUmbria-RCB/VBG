INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO, PARAMETRO, DESCRIZIONE) VALUES ('NODO_PAGAMENTI', 'TIPOMOVIMENTO_DOC_FATTURA', 'Indica il tipo movimento da creare quando è presente il documento della fattura della posizione debitoria associata agli oneri dell''istanza');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO, PARAMETRO, DESCRIZIONE) VALUES ('NODO_PAGAMENTI', 'TIPOMOVIMENTO_DOC_AVVISO', 'indica il tipo movimento da creare quando è presente il documento dell''avviso della posizione debitoria associata agli oneri dell''istanza');

INSERT INTO PAY_CONNECTOR_CONFIG_PARAMS(CONFIG_PARAM,DESCRIZIONE,CODICE_CONNETTORE) VALUES ('GOV_PAY_URL_API_PENDENZE','Indica la url per invocare le API del servizio pendenze. es /govpay/backend/api/pendenze/rs/basic/v2/pendenze',null);

INSERT INTO PAY_CONNECTOR_CONFIG_PARAMS(CONFIG_PARAM,DESCRIZIONE,CODICE_CONNETTORE) VALUES ('GOV_PAY_URL_API_PROFILO','Indica la url per invocare le API del servizio profilo. es /govpay/backend/api/pendenze/rs/basic/v2',null);

INSERT INTO PAY_CONNECTOR_CONFIG_PARAMS(CONFIG_PARAM,DESCRIZIONE,CODICE_CONNETTORE) VALUES ('GOV_PAY_URL_API_PAGAMENTI','Indica la url per invocare le API del servizio pagamenti. es /govpay/frontend/api/pagamento/rs/basic/pagamenti',null);
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('WSANAGRAFE_MAGGIOLI', 'ALIAS', 'E'' l''alias con cui va fatta la chiamata, deve essere concordato con il fornitore e con il Comune, ma in linea di massima è sempre lo stesso che si utilizza per la protocollazione, lo si recupera individuando il valore del parametro in querystring CID presente nella url del ws di protocollazione, oppure nel parametro CODICEAMMINISTRAZIONE presente nella regola che gestisce il protocollo.');

INSERT INTO VERTICALIZZAZIONIBASE(MODULO, DESCRIZIONE, FLAG_GESTCOMUNE) VALUES ('PROTOCOLLO_ACARIS', 'Integrazione con il protocollo DoQui – ACTA mediante i servizi ACARIS', 1);
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_ACARIS', 'APPKEY', 'Stringa alfanumerica che identifica univocamente l''applicativo client');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_ACARIS', 'REPOSITORY', 'Identificativo del repository (Ente) in ACTA');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_ACARIS', 'IDAOO', 'Identificativo dell’AOO in cui è collocato l''utente');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_ACARIS', 'URL', 'Url di base per accedere alla lista dei servizi SOAP di integrazione con il protocollo. Es: http://tst-applogic.paevolution.it/actasrv/');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_ACARIS', 'TITOLARIO', 'ID del titolario da utilizzare');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_ACARIS', 'ANNI_CONSERVAZIONE_CORRENTE','Definisce per quanti anni (numero di anni o durata illimitata = 99)  una struttura aggregativa chiusa deve ancora rimanere in archivio corrente prima di essere trasferita in archivio di deposito.
E'' possibile impostare a zero i termini di conservazione nell’archivio corrente');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_ACARIS', 'ANNI_CONSERVAZIONE_GENERALE', 'Definisce per quanto tempo (numero di anni o durata illimitata = 99) una struttura aggregativa chiusa deve essere conservata in archivio generale.
Il termine di conservazione nell''archivio generale comprende anche il termine di conservazione nell''archivio corrente, ovvero se una struttura aggregativa chiusa ha come termine di conservazione nell''archivio corrente 2 anni e termine di conservazione dell''archivio generale 10 anni, la stessa permane in archivio di deposito solo più per 8 anni.
Non è possibile impostare a zero i termini di conservazione in archivio generale');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_ACARIS', 'GRADO_VITALITA', 'Indica il grado di vitalità dell''informazione; viene utilizzata come metro di misura dell''importanza che un Folder/Fascicolo ha nel repository documentale');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_ACARIS', 'SERIEFASCICOLI', 'Codice identificativo della serie di fascicoli che raccoglierà tutti i fascicoli creati');


INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES('PARAMETRI_SISTEMA','VERIFICA_FIRMA_OGG_INSERITI','Se attivo (1) viene fatta la verifica della firma o meno dell''oggetto da  inserire o modificare poi inserisce una serie di metadati');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_ATTIVO', 'FLUSSI_VER_FIRMA_DOC_PRINC', 'Può essere impostato per verificare che l''eventuale documento principale sia firmato digitalmente. I valori ammessi sono P ( protocolli in partenza ), I ( protocolli interni ), A ( protocolli in arrivo ).
Possono essere specificati più valori senza alcun separatore ( es. API o PAI ). Se lasciato vuoto o non presente allora non verranno effettuati controlli.');

INSERT INTO PAY_CONNECTOR_CONFIG_PARAMS(CONFIG_PARAM,DESCRIZIONE) VALUES('VERSIONE','Se previsto specificare la versione del webservice da invocare');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('AREA_RISERVATA', 'URL_AVVIO_PROCEDIMENTO', 'Url dell''area riservata che punta alla creazione di una nuova istanza di un certo tipo di procedimento. Si può inserire un URL parametrizzato con dei segnaposto che saranno sostituiti a runtime. I segnaposto validi sono {alias}, {software}, {codiceintervento}. Esempio: http://devel3:8080/areariservata/presenta-intervento-locale/{alias}/{software}/{codiceintervento}');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO, PARAMETRO, DESCRIZIONE) VALUES ('COMPORTAMENTO_COMPONENTE_FIRMA', 'CONVERTI_IN_PDF', 'Specificare la lista delle estensioni che devono essere convertite automaticamente in PDF prima di essere firmate digitalmente o messe alla firma. E'' possibile indicare più estensioni separandole con ; ( Es. rtf;doc;docx;....)');

