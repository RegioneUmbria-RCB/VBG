INSERT INTO tipicontestoesportazione (codice,descrizione) VALUES ('ABB','Esportazione di abbonamenti/borsellino');
INSERT INTO verticalizzazionibase (MODULO,DESCRIZIONE,FLAG_GESTCOMUNE) VALUES ('WSANAGRAFE_PARIX_CLOUD','Se attivato permette di recuperare un''anagrafe persona giuridica dai servizi web di PARIX-GATE-CLOUD, la funzionalità viene attivata nelle anagrafiche richiedenti e tecnici sia IN inserimento che per controlli successivi e durante la presentazione della domanda On-line. Questo modulo va abilitato contestualmente ad un altro modulo WSANAGRAFE_XXX (modulo "principale") dove XXX è CESENA, PIACENZA... a condizione che il modulo "principale" sia sviluppato tenendo conto della compatibilità con l''integrazione PARIX-GATE-CLOUD. Le indicazioni se un modulo "principale" è compatibile con PARIX-GATE-CLOUD saranno scritte nella descrizione del modulo "principale".',0);

INSERT INTO verticalizzazioniparametribase (MODULO,PARAMETRO,DESCRIZIONE) VALUES ('WSANAGRAFE_PARIX_CLOUD','BASIC_AUTH_PASSWORD','Password da utilizzare per l''autenticazione quando il servizio viene chiamato attraverso le PDD del CART');
INSERT INTO verticalizzazioniparametribase (MODULO,PARAMETRO,DESCRIZIONE) VALUES ('WSANAGRAFE_PARIX_CLOUD','BASIC_AUTH_USER','Username da utilizzare per l''autenticazione quando il servizio viene chiamato attraverso le PDD del CART');
INSERT INTO verticalizzazioniparametribase (MODULO,PARAMETRO,DESCRIZIONE) VALUES ('WSANAGRAFE_PARIX_CLOUD','CERCA_SOLO_CF','Se impostato a 1 allora ricerca le anagrafiche PARIX-GATE-CLOUD solamente per codice fiscale impresa');
INSERT INTO verticalizzazioniparametribase (MODULO,PARAMETRO,DESCRIZIONE) VALUES ('WSANAGRAFE_PARIX_CLOUD','JAVA_PATH_CERTIFICATO','Attributi per l''autenticazione client della componente java es caso RFC 63 Regione Toscana. Il valore è composto da più elementi separati dal carattere | (Es. ALIAS|KEYSTORE|TRUSTSTORE|KEYSTOREPWD|TRUSTSTOREPWD). ALIAS rappresenta l''alias del certificato client usato per l''autenticazione. KEYSTORE il path al certificato rilasciato da regione toscana p12. TRUSTSTORE il path al file jks per il trust dei server di regione toscana. KEYSTOREPWD e TRUSTSTOREPWD sono rispettivamente le password per accedere ai due keystore. I path completi del certificato di autenticazione devono trovarsi nel filesystem della macchina che ospita l''applicazione JAVA backend.war.');
INSERT INTO verticalizzazioniparametribase (MODULO,PARAMETRO,DESCRIZIONE) VALUES ('WSANAGRAFE_PARIX_CLOUD','NET_PATH_CERTIFICATO','Path completo del certificato di autenticazione che deve trovarsi nel filesystem della macchina che ospita l''applicazione ASPNET (consigliato: aspnetcertificati). Se il certificato richiede una password specificarla in seguito al path separata da un pipe (es. c:vbgaspnetcertificaticert.p12|password)');
INSERT INTO verticalizzazioniparametribase (MODULO,PARAMETRO,DESCRIZIONE) VALUES ('WSANAGRAFE_PARIX_CLOUD','PASSWORD','Password dell''utente abilitato alla ricerca');
INSERT INTO verticalizzazioniparametribase (MODULO,PARAMETRO,DESCRIZIONE) VALUES ('WSANAGRAFE_PARIX_CLOUD','PROXY_ADDRESS','Url nel formato http[s]://INDIRIZZO:PORTA da utilizzare per effettuare le chiamate al web service di PARIX-GATE-CLOUD');
INSERT INTO verticalizzazioniparametribase (MODULO,PARAMETRO,DESCRIZIONE) VALUES ('WSANAGRAFE_PARIX_CLOUD','SWITCHCONTROL','Parametro che stabilisce se effettuare la ricerca sul servizio nazionale o meno ( "diretto": accesso diretto alò servizio nazionale, "no" non passa mai dal servizio nazionale, ""/" ":passa dal servizio locale ed eventualmente da quello nazionale');
INSERT INTO verticalizzazioniparametribase (MODULO,PARAMETRO,DESCRIZIONE) VALUES ('WSANAGRAFE_PARIX_CLOUD','URL','Url del web service (wsdl) da invocare per ricavare i dati anagrafici.
Es. ER: https://regumbw-api.parix.infocamere.it/parixGate/parixGate/service/gate/parixGate.wsdl
Nel browser del server application aggiungendo alla URL ''?wsdl'' si può verificare se il server raggiunge il servizio.');
INSERT INTO verticalizzazioniparametribase (MODULO,PARAMETRO,DESCRIZIONE) VALUES('WSANAGRAFE_PARIX_CLOUD','USER','E'' l''utente per accedere ai servizi PARIX-GATE-CLOUD, deve essere fornito dal cliente.');
INSERT INTO verticalizzazioniparametribase (MODULO,PARAMETRO,DESCRIZIONE) VALUES ('WSANAGRAFE_PARIX_CLOUD','XSD','Percorso degli xsd utili per la validazione dei risultati restituiti dai ws di PARIX-GATE-CLOUD');
INSERT INTO verticalizzazioniparametribase (MODULO,PARAMETRO,DESCRIZIONE) VALUES ('WSANAGRAFE_PARIX_CLOUD','TARGET_NAMESPACE','Il target namespace presente nel wsdl PARIX-GATE-CLOUD');


INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('PROTOCOLLO_ATTIVO','URL_WS_PROTOCOLLO','Parametro che permette di sovrascrivere l''url del servizio di protocollazione definito tra i parametri della security');

insert into pay_connector_config_params (CONFIG_PARAM, DESCRIZIONE) values ('PAGAMENTI_FUORI_VBG', 'Parametro che serve per capire se elaborare i pagamenti fuori VBG, valore da impostare pagamenti_fuori_vbg');

insert into pay_connector_config_params (CONFIG_PARAM, DESCRIZIONE) values ('PAYER_CLIENT_SECRET', 'Secret dell''api payer');
insert into pay_connector_config_params (CONFIG_PARAM, DESCRIZIONE) values ('PAYER_CLIENT_KEY', 'Chiave dell''api payer');
insert into pay_connector_config_params (CONFIG_PARAM, DESCRIZIONE) values ('ALIAS_SINCRONIZZA_POSIZIONI', 'Parametro che ti permette di determinare gli alias per i quali puoi proseguire nella sincornizzazione delle posizioni debitorie');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('PROTOCOLLO_KIBERNETES','USA_RUOLO_ENTRATA','Usato nella protocollazione in entrata come valore per destinatari amministrazione. Default: 0 (verrà utilizzato il valore del parametro USERNAME). Se impostato a 1 utilizza il ruolo nei Parametri Protocollo dell''amministrazione relativa al parametro CODICEAMMINISTRAZIONE del PROTOCOLLO_ATTIVO');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('PROTOCOLLO_KIBERNETES','USA_RUOLO_USCITA','Usato nella protocollazione in uscita come valore per destinatari amministrazione. Default: 0 (verrà utilizzato il valore del parametro USERNAME). Se impostato a 1 utilizza il ruolo nei Parametri Protocollo dell''amministrazione relativa al parametro CODICEAMMINISTRAZIONE del PROTOCOLLO_ATTIVO');

INSERT INTO comuni (CODICECOMUNE,COMUNE,SIGLAPROVINCIA,PROVINCIA,REGIONE,CAP,CF,CODICEISTAT,CODICEISTATREGIONE,CODICESTATOESTERO) VALUES ('I842','SORBANO','FC','FORLI''','EMILIA ROMAGNA',NULL,'I842','040804','08',NULL);

insert into verticalizzazioniparametribase (MODULO, PARAMETRO, DESCRIZIONE) values ('PROTOCOLLO_AURIGA', 'WS_ALLEGATI_DIMENSIONE_MASSIMA', 'Dimensione massima che si può raggiungere per non inviare zip su sftp');
insert into verticalizzazioniparametribase (MODULO, PARAMETRO, DESCRIZIONE) values ('PROTOCOLLO_AURIGA', 'SFTP_HOST', 'Host SFTP, es. localhost');
insert into verticalizzazioniparametribase (MODULO, PARAMETRO, DESCRIZIONE) values ('PROTOCOLLO_AURIGA', 'SFTP_PORT', 'Porta SFTP, es. 22. Se non valorizzata sarà 22');
insert into verticalizzazioniparametribase (MODULO, PARAMETRO, DESCRIZIONE) values ('PROTOCOLLO_AURIGA', 'SFTP_USER', 'User SFTP, es. test');
insert into verticalizzazioniparametribase (MODULO, PARAMETRO, DESCRIZIONE) values ('PROTOCOLLO_AURIGA', 'SFTP_PASSWORD', 'Password SFTP, es. password');
insert into verticalizzazioniparametribase (MODULO, PARAMETRO, DESCRIZIONE) values ('PROTOCOLLO_AURIGA', 'SFTP_ROOT_PATH', 'Root path facoltativa, potrebbe servire se si dovessero portare gli zip sotto uno specifico path, es. ENGRAMMA_TEST');

INSERT INTO software (CODICE,DESCRIZIONE,MODULOOPZIONALE,DESCRIZIONELUNGA,ACCESSORAPIDO,ORDINE) VALUES('AR','Configurazione servizi SSU per areariservata',1,'Configurazione servizi SSU per areariservata',1,14);

INSERT INTO CITTADINANZA (CODICE, CITTADINANZA, CF, DISABILITATO, FLG_PAESE_COMUNITARIO) VALUES(8723, 'SERBIA', 'Z158', 0, 0);
INSERT INTO CITTADINANZA (CODICE, CITTADINANZA, CF, DISABILITATO, FLG_PAESE_COMUNITARIO) VALUES(8724, 'MONTENEGRO', 'Z159', 0, 0);


INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('PROTOCOLLO_HALLEY2','URLFASCICOLAPROT','Url da inserire per accedere alla funzione FascicolaProtocollo. (https://testsegreteria.halleycih.com/HALLEY/PI0/PIWSCOLFAS.HBL?wsdl)');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('PROTOCOLLO_HALLEY2','URLUTILITY','Url da inserire per accedere alla funzione ConsultaFascicoli e InserisciFascicoli. (https://testsegreteria.halleycih.com/HALLEY/PI0/PIWSHALLEY.HBL?wsdl)');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('AREA_RISERVATA','VERIFICA_FIRMA_SOGG_RIEPILOGO','Se impostato a 1 attiva la verifica della firma dei soggetti nel riepilogo dell''area riservata (default: 0)');

INSERT INTO VERTICALIZZAZIONIBASE(MODULO,DESCRIZIONE,FLAG_GESTCOMUNE) VALUES ('SUAP_XML','Permette la gestione della generazione del file SUAP XML all''interno delle istanze',1);
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('SUAP_XML','URL','Url del servizio che genera il file SUAP XML');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('SUAP_XML','GENERA_SU_INSERIMENTO_ISTANZA','Valori attesi 1 o 0 (DEFAULT): indica se in fase di inserimento istanza il sistema deve generare automaticamente il SUAP XML ed inserirlo tra i documenti dell''istanza (la generazione avviene prima della generazione del riepilogo e dell''eventuale protocollazione ). Per generare il file, impostarlo a 1 ');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('SUAP_XML','VIS_BOTTONE_SU_PROT_MOVIMENTO','Valori attesi 1 o 0 (DEFAULT): indica se mostrare o meno il bottone per la generazione del SUAP XML nella maschera di protocollazione dei movimenti. Per mostrare il bottone, impostarlo a 1');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('SUAP_XML','VALIDA','Valori attesi 1 o 0 (DEFAULT): indica se validare o meno lo schema prima di creare il file xml che rappresenta la pratica. Per effettuare la validazione impostarlo a 1');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('PROTOCOLLO_CIVILIANEXT','MARCA_ALLEGATI','Se impostato a 1, ogni file allegato nella Request viene generato come marcato.');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('STC','INSERISCI_FILE_ASSERZIONE_SAML','Valori attesi S o N (DEFAULT): Nell''inserimento della pratica, se presente il metadato ASSERZIONE_SAML_AUTENTICAZIONE, inserisce un file ASSERZIONE_SAML_AUTENTICAZIONE.txt con il contenuto del metadato.');


UPDATE software SET ordine=0 WHERE codice='TT';
UPDATE software SET ordine=1 WHERE codice='SU';
UPDATE software SET ordine=2 WHERE codice='CO';
UPDATE software SET ordine=3 WHERE codice='CE';
UPDATE software SET ordine=4 WHERE codice='PU';
UPDATE software SET ordine=5 WHERE codice='PE';
UPDATE software SET ordine=6 WHERE codice='SR';
UPDATE software SET ordine=7 WHERE codice='PR';
UPDATE software SET ordine=8 WHERE codice='DL';
UPDATE software SET ordine=9 WHERE codice='CP';
UPDATE software SET ordine=10 WHERE codice='VP';
UPDATE software SET ordine=11 WHERE codice='AA';
UPDATE software SET ordine=12 WHERE codice='PS';
UPDATE software SET ordine=13 WHERE codice='SS';
UPDATE software SET ordine=14 WHERE codice='CI';
UPDATE software SET ordine=15 WHERE codice='DM';
UPDATE software SET ordine=16 WHERE codice='SA';
UPDATE software SET ordine=17 WHERE codice='AM';
UPDATE software SET ordine=18 WHERE codice='CB';
UPDATE software SET ordine=19 WHERE codice='PA';
UPDATE software SET ordine=20 WHERE codice='ED';
UPDATE software SET ordine=21 WHERE codice='SD';
UPDATE software SET ordine=22 WHERE codice='YE';
UPDATE software SET ordine=23 WHERE codice='AS';
UPDATE software SET ordine=24 WHERE codice='IA';
UPDATE software SET ordine=25 WHERE codice='SP';
UPDATE software SET ordine=26 WHERE codice='FI';
UPDATE software SET ordine=27 WHERE codice='LU';
UPDATE software SET ordine=28 WHERE codice='AB';
UPDATE software SET ordine=29 WHERE codice='MT';
UPDATE software SET ordine=30 WHERE codice='PC';
UPDATE software SET ordine=31 WHERE codice='AI';
UPDATE software SET ordine=32 WHERE codice='VE';
UPDATE software SET ordine=33 WHERE codice='VT';
UPDATE software SET ordine=34 WHERE codice='EF';
UPDATE software SET ordine=35 WHERE codice='A1';
UPDATE software SET ordine=36 WHERE codice='P1';
UPDATE software SET ordine=37 WHERE codice='LT';
UPDATE software SET ordine=38 WHERE codice='P2';
UPDATE software SET ordine=39 WHERE codice='P3';
UPDATE software SET ordine=40 WHERE codice='PI';
UPDATE software SET ordine=41 WHERE codice='AG';
UPDATE software SET ordine=42 WHERE codice='GL';
UPDATE software SET ordine=43 WHERE codice='FQ';
UPDATE software SET ordine=44 WHERE codice='ST';
UPDATE software SET ordine=45 WHERE codice='AP';
UPDATE software SET ordine=46 WHERE codice='CW';
UPDATE software SET ordine=47 WHERE codice='GC';
UPDATE software SET ordine=48 WHERE codice='PM';
UPDATE software SET ordine=49 WHERE codice='CS';
UPDATE software SET ordine=50 WHERE codice='SG';
UPDATE software SET ordine=51 WHERE codice='TA';
UPDATE software SET ordine=52 WHERE codice='XS';
UPDATE software SET ordine=53 WHERE codice='XE';
UPDATE software SET ordine=54 WHERE codice='XA';
UPDATE software SET ordine=55 WHERE codice='XR';
UPDATE software SET ordine=56 WHERE codice='CN';
UPDATE software SET ordine=57 WHERE codice='XU';
UPDATE software SET ordine=58 WHERE codice='A2';
UPDATE software SET ordine=59 WHERE codice='IN';
UPDATE software SET ordine=60 WHERE codice='X1';
UPDATE software SET ordine=61 WHERE codice='MA';
UPDATE software SET ordine=62 WHERE codice='X2';
UPDATE software SET ordine=63 WHERE codice='LP';
UPDATE software SET ordine=64 WHERE codice='UM';
UPDATE software SET ordine=65 WHERE codice='SN';
UPDATE software SET ordine=66 WHERE codice='UR';
UPDATE software SET ordine=67 WHERE codice='P4';
UPDATE software SET ordine=68 WHERE codice='S1';
UPDATE software SET ordine=69 WHERE codice='X3';
UPDATE software SET ordine=70 WHERE codice='XB';
UPDATE software SET ordine=71 WHERE codice='T1';
UPDATE software SET ordine=72 WHERE codice='AC';
UPDATE software SET ordine=73 WHERE codice='BP';
UPDATE software SET ordine=74 WHERE codice='CU';
UPDATE software SET ordine=75 WHERE codice='ET';
UPDATE software SET ordine=76 WHERE codice='LC';
UPDATE software SET ordine=77 WHERE codice='MC';
UPDATE software SET ordine=78 WHERE codice='OT';
UPDATE software SET ordine=79 WHERE codice='PP';
UPDATE software SET ordine=80 WHERE codice='PZ';
UPDATE software SET ordine=81 WHERE codice='RI';
UPDATE software SET ordine=82 WHERE codice='S2';
UPDATE software SET ordine=83 WHERE codice='SF';
UPDATE software SET ordine=84 WHERE codice='SI';
UPDATE software SET ordine=85 WHERE codice='TO';
UPDATE software SET ordine=86 WHERE codice='VA';
UPDATE software SET ordine=87 WHERE codice='VU';
UPDATE software SET ordine=88 WHERE codice='X0';
UPDATE software SET ordine=89 WHERE codice='X4';
UPDATE software SET ordine=90 WHERE codice='X5';
UPDATE software SET ordine=91 WHERE codice='X6';
UPDATE software SET ordine=92 WHERE codice='X7';
UPDATE software SET ordine=93 WHERE codice='X8';
UPDATE software SET ordine=94 WHERE codice='XC';
UPDATE software SET ordine=95 WHERE codice='XD';
UPDATE software SET ordine=96 WHERE codice='XI';
UPDATE software SET ordine=97 WHERE codice='XP';
UPDATE software SET ordine=98 WHERE codice='Y1';
UPDATE software SET ordine=99 WHERE codice='Y2';
UPDATE software SET ordine=100 WHERE codice='Y3';
UPDATE software SET ordine=101 WHERE codice='Y5';
UPDATE software SET ordine=102 WHERE codice='Y6';
UPDATE software SET ordine=103 WHERE codice='Y7';
UPDATE software SET ordine=104 WHERE codice='Y8';
UPDATE software SET ordine=105 WHERE codice='Y9';
UPDATE software SET ordine=106 WHERE codice='YA';
UPDATE software SET ordine=107 WHERE codice='YB';
UPDATE software SET ordine=108 WHERE codice='YC';
UPDATE software SET ordine=109 WHERE codice='YD';
UPDATE software SET ordine=110 WHERE codice='YF';
UPDATE software SET ordine=111 WHERE codice='YG';
UPDATE software SET ordine=112 WHERE codice='Z1';
UPDATE software SET ordine=113 WHERE codice='Z2';
UPDATE software SET ordine=114 WHERE codice='Z3';
UPDATE software SET ordine=115 WHERE codice='Z4';
UPDATE software SET ordine=116 WHERE codice='Z5';
UPDATE software SET ordine=117 WHERE codice='Z6';
UPDATE software SET ordine=118 WHERE codice='Z7';
UPDATE software SET ordine=119 WHERE codice='Z8';

