
INSERT INTO verticalizzazioniparametribase(MODULO, PARAMETRO, DESCRIZIONE)VALUES('FILESYSTEM_API_ALFRESCO', 'ALFRESCO_SOLA_LETTURA', 'Se impostato a ''<b>S</b>'' allora i nuovi file o le modifiche al file esistente non saranno eseguite nel sistema alfresco che viene usato come repository di <b>SOLA LETTURA</b>. Le modifiche ai file o i nuovi inserimenti saranno eseguite secondo altra logica di persistenza (FileSystem o BLOB). Valore Predefinito N.');


INSERT INTO VERTICALIZZAZIONIBASE (MODULO, DESCRIZIONE, FLAG_GESTCOMUNE)  VALUES ('RIC_ANAG_COLL_INTERNAL', 'Componente per le ricerche delle anagrafiche collegate della base dati  applicativa basato su algoritmi interni. Deve essere attivato anche la verticalizzazione RIC_ANAG_COLLEGATE dove deve essere specificato questo come componente di ricerca', 0);

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO, PARAMETRO, DESCRIZIONE) VALUES ('RIC_ANAG_COLL_INTERNAL','STRATEGIA_RICERCA','Determina l''algoritmo di ricerca delle anagrafiche collegate. Se non specificato o <b>DEFAULT</b> allora saranno ricercate le anagrafiche collegate ad una anagrafica passata come riferimento ricercando sui campi ISTANZE.CODICERICHIEDENTE, ISTANZE.CODICETITOLARELEGALE del modulo software attivo.');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('COMPORTAMENTI_MERCATI','APP_VIGILI_BLOCCA_CHIUS_GG','Parametro che permette di specificare se bloccare la chiusura della giornata in caso di posteggi non assegnati. Valori previsti <b>S</b> / <b>N</b> predefinito <b>N</b>');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('COMPORTAMENTI_MERCATI','APP_VIGILI_BLOCCA_CHIUS_GG_MSG','Parametro che permette di specificare il messaggio che viene visualizzato a video dopo il controllo dei posteggi non assegnati nel caso di chiusura della giornata nell''app dei vigili');


UPDATE VERTICALIZZAZIONIPARAMETRIBASE SET DESCRIZIONE='Indica il criterio di ordinamento da applicare alla funzionalità mostra graduatoria spuntisti nel caso <b>NON SIA</b> attivo il flag <b>MERCATI_CONFIGURAZIONE#FLAG_ATTIVA_GRAD_SPUNTISTI</b>.<br/>Se non impostato prende il seguente ordinamento <b>coalesce(nominativogerente,nominativo), coalesce(nomegerente,nome)</b>.<br/>Lista delle colonne disponibili: <ul><li>numpresenze</li><li>dataregditte</li><li>dataanzianita</li><li>autorizdata</li></ul> È possibile usare le funzioni LEAST E COALESCE.<br/> Le colonne da ordinare DEVONO essere separate dal carattere <b>|</b><br />Esempio di uso <p><b>numpresenze DESC| LEAST(dataregditte, COALESCE( dataanzianita, dataregditte) ) ASC | autorizdata</b><br/>La configurazione si applica anche all''ordinamento per presenze nell''app vigili.</p>' WHERE MODULO='COMPORTAMENTI_MERCATI' AND PARAMETRO='CRIT_ORD_GRAD_MERC_PREDEFINITO';


UPDATE VERTICALIZZAZIONIPARAMETRIBASE SET DESCRIZIONE='Indica il criterio di ordinamento da applicare alla funzionalità mostra graduatoria spuntisti nel caso <b>SIA</b> attivo il flag <b>MERCATI_CONFIGURAZIONE#FLAG_ATTIVA_GRAD_SPUNTISTI</b>.<br/>Se non impostato prende il seguente ordinamento <b>numpresenze DESC, dataregditte ASC, autorizzazioni.data_anzianita ASC</b>.<br/>Lista delle colonne disponibili: <ul><li>numpresenze</li><li>dataregditte</li><li>dataanzianita</li><li>autorizdata</li></ul> È possibile usare le funzioni LEAST E COALESCE.<br/> Le colonne da ordinare DEVONO essere separate dal carattere <b>|</b><br />Esempio di uso <p><b>numpresenze DESC| LEAST(dataregditte, COALESCE( dataanzianita, dataregditte) ) ASC | autorizdata ASC</b><br/>La configurazione si applica anche all''ordinamento per presenze nell''app vigili.</p></p>' WHERE MODULO='COMPORTAMENTI_MERCATI' AND PARAMETRO='CRIT_ORD_GRAD_MERC_SE_CFGSPUNT';


INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('COMPORTAMENTI_MERCATI','APP_VIGILI_MSG_CHIUS_GG','Parametro che permette di specificare il messaggio che viene visualizzato a video dopo il controllo dei posteggi non assegnati nel caso di chiusura della giornata nell''app dei vigili. Messaggio predefinito se non specificato <b>&lt;p&gt;E'' stata richiesta la chiusura della giornata in corso, l''operazione bloccherà ulteriori modifiche alla giornata e sarà annullabile solo da un''operatore dell''ente. &lt;/p&gt;&lt;p&gt;Chiudere la giornata corrente?&lt;/p&gt;</b>');


INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('COMPORTAMENTI_MERCATI','APP_VIGILI_VIS_TERMINA_APPELLO','Permette di mostrare o nascondere nell''app vigili il bottone <b>TERMINA APPELLO</b>. Valori possibili <b>S</b> o <b>N</b>. Se non specificato allora il valore predefinito è <b>S</b>');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_ATTIVO', 'ESTENSIONI_NON_AMMESSE', 'Questo parametro determina quali tipologie di file devono essere escluse dalla protocollazione. Indicare le estensioni da escludere dividendole con un punto e virgola senza lasciare spazi. Ad esempio se si vuole escludere i file zip, rar e 7zip indicare: "zip;rar;7z". Il parametro ha impatto su ogni tipologia di protocollazione sia manuale che automatica, in quella manuale verranno comunque esclusi i file presenti in questa lista anche se selezionati');
INSERT INTO PAY_CONNECTOR_CONFIG_PARAMS(CONFIG_PARAM,DESCRIZIONE,CODICE_CONNETTORE) VALUES ('PPAY_USA_SERV_REST_ANNULLA_POS','Nel caso del connettore Piemonte PAY REST determina se usare il servizio di annulla posizione debitoria del connettore non REST - modalità asincrona. Valori possibili (true o false) Il valore predefinito è true e indica che viene usato il servizio rest, mentre false indica che viene usato il servizio classico', NULL);


INSERT INTO PAY_CONNECTOR_CONFIG_PARAMS(CONFIG_PARAM,DESCRIZIONE,CODICE_CONNETTORE) VALUES ('PPAY_USA_SERV_GET_RT_SUPPORT','Nel caso del connettore Piemonte PAY REST determina se usare il servizio di GET RT oppure getDebtPositionData (nuove API). Valori possibili (true o false) Il valore predefinito è true e indica che viene usato il servizio GETRT, mentre false indica che viene usato il servizio getDebtPositiondata ovvero la ricevuta non sarà disponibile', NULL);

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('ABBONAMENTO_POSTEGGI','COM_CHIUSURA_GG_DESCRIZIONE','Contiene il nome che verrà assegnato alla comunicazione specifica per la <b>CHIUSURA DELLA GIORNATA</b> e che verrà visualizzato nel backend. E'' possibile indicare un testo fisso oppure utilizzare anche il segnaposto [-GIORNATA-] che riporterà la data di svolgimento nel formato dd/mm/yyyy. Se non impostato, viene riportato il seguente testo "Chiusura giornata [-GIORNATA-]"');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('ABBONAMENTO_POSTEGGI','COM_CHIUSURA_GG_TEMPLATE','Indicare il codice della mail tipo da utilizzare come oggetto/corpo della mail della comunicazione specifica per la <b>CHIUSURA DELLA GIORNATA</b>. Sarà possibile utilizzare solamente i segnaposti relativi alla sezione "Comunicazioni massive delle manifestazioni" (OBBLIGATORIO SE CONFIGURATO IL PARAMETRO COMUNICAZIONI_MAIL_SENDER)');



UPDATE verticalizzazioniparametri SET PARAMETRO = 'COM_CHIUSURA_GG_DESCRIZIONE' WHERE MODULO ='ABBONAMENTO_POSTEGGI' AND PARAMETRO='COMUNICAZIONI_DESCRIZIONE'; 
UPDATE verticalizzazioniparametri SET PARAMETRO = 'COM_CHIUSURA_GG_TEMPLATE' WHERE MODULO ='ABBONAMENTO_POSTEGGI' AND PARAMETRO='COMUNICAZIONI_MAIL_TEMPLATE'; 



INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('ABBONAMENTO_POSTEGGI','COM_NPD_CRED_INS_DESCRIZIONE','Contiene il nome che verrà assegnato alla comunicazione specifica per la <b>APERTURA POSIZIONE DEBITORIA PER CREDITO INSUFFICIENTE</b> e che verrà visualizzato nel backend. E'' possibile indicare un testo fisso oppure utilizzare anche il segnaposto [-GIORNATA-] che riporterà la data di svolgimento nel formato dd/mm/yyyy. Se non impostato, viene riportato il seguente testo "Notifica apertura posizioni debitorie per credito insufficiente [-GIORNATA-]"');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('ABBONAMENTO_POSTEGGI','COM_NPD_CRED_INS_TEMPLATE','Indicare il codice della mail tipo da utilizzare come oggetto/corpo della mail della comunicazione specifica per la <b>APERTURA POSIZIONE DEBITORIA PER CREDITO INSUFFICIENTE</b>. Sarà possibile utilizzare solamente i segnaposti relativi alla sezione "Comunicazioni massive delle manifestazioni" (OBBLIGATORIO SE CONFIGURATO IL PARAMETRO COMUNICAZIONI_MAIL_SENDER)');


UPDATE verticalizzazioniparametribase SET descrizione=CONCAT('<b>( OBSOLETO NON USARE )</b><br />',descrizione) WHERE MODULO ='ABBONAMENTO_POSTEGGI' AND PARAMETRO='COMUNICAZIONI_DESCRIZIONE'; 
UPDATE verticalizzazioniparametribase SET descrizione=CONCAT('<b>( OBSOLETO NON USARE )</b><br />',descrizione)  WHERE MODULO ='ABBONAMENTO_POSTEGGI' AND PARAMETRO='COMUNICAZIONI_MAIL_TEMPLATE';



INSERT INTO VERTICALIZZAZIONIBASE(MODULO,DESCRIZIONE,FLAG_GESTCOMUNE) VALUES ('FIRMA_REMOTA_INFOCERT','Se attivo sarà possibile effettuare la firma digitale remota con le soluzione infocert',0);

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('FIRMA_REMOTA_INFOCERT','BOX_SIGNATURE_LLX','Ascissa (x) espressa in punti tipografici (pt, 1 pt = 0,35278 mm) dell’angolo in basso a sinistra del box di firma, con origine degli assi posta nell’angolo in basso a sinistra del foglio, per le firme PAdES e PAdES-T');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('FIRMA_REMOTA_INFOCERT','BOX_SIGNATURE_LLY','Ordinata (y) espressa in punti tipografici (pt, 1 pt = 0,35278 mm) dell’angolo in basso a sinistra del box di firma, con origine degli assi posta nell’angolo in basso a sinistra del foglio, per le firme PAdES e PAdES-T');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('FIRMA_REMOTA_INFOCERT','BOX_SIGNATURE_URX','Ascissa (x) espressa in punti tipografici (pt, 1 pt = 0,35278 mm) dell’angolo in alto a destra del box di firma, con origine degli assi posta nell’angolo in basso a sinistra del foglio, per le firme PAdES e PAdES-T');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('FIRMA_REMOTA_INFOCERT','BOX_SIGNATURE_URY','Ordinata (y) espressa in punti tipografici (pt, 1 pt = 0,35278 mm) dell’angolo in alto a destra del box di firma, con origine degli assi posta nell’angolo in basso a sinistra del foglio, per le firme PAdES e PAdES-T');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('FIRMA_REMOTA_INFOCERT','BOX_SIGNATURE_PAGE','Numero di pagina del PDF in cui si intende inserire il box di firma per le firme PAdES e PAdES-T');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('FIRMA_REMOTA_INFOCERT','URL_BASE_SERVIZIO','Nome dell''host dove è esposto il servizio di firma infocert');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('FIRMA_REMOTA_INFOCERT','TIMESTAMP_USERNAME','Nome utente parte delle credenziali per accedere al servizio di Marca Temporale');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('FIRMA_REMOTA_INFOCERT','TIMESTAMP_PASSWORD','Password parte delle credenziali per accedere al servizio di Marca Temporale');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('FIRMA_REMOTA_INFOCERT','DESCRIZIONE','Indica il nome presentato sulla maschera di firma che permette di scegliere il provider di firma in caso ne siano presenti più di uno. Se attivo un solo provider, verrà presentato solo il nome senza la possibilità di scelta ');

