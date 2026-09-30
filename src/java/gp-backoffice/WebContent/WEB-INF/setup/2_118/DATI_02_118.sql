INSERT INTO verticalizzazioniparametribase (MODULO, PARAMETRO, DESCRIZIONE) VALUES('AREA_RISERVATA','MAX_RECORDS_RICERCA_PRATICHE','Numero massimo di records restituibili dalla ricerca pratiche (archivio e visura). Default: 200');

INSERT INTO PAY_CONNECTOR_CONFIG_PARAMS(CONFIG_PARAM,DESCRIZIONE,CODICE_CONNETTORE) VALUES ('MIPGE_CODICE_ENTE','CODICE ENTE da specificare nei tracciati',NULL);

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('ASSEGNAZIONE_OPERATORI','ASSEGNAZIONE_CAPIENZA_GRUPPO','Parametro che definisce l''assegnazione dei operatori all''istanza secondo la logica del numero di pratiche assegnabili. S usiamo la logica N per il vecchio modo');



INSERT INTO verticalizzazionibase(MODULO, DESCRIZIONE, FLAG_GESTCOMUNE) VALUES ('SIT_FORLI', 'Parametri di configurazione del sit in uso nel comune di Forlì', 0);

INSERT INTO verticalizzazioniparametribase(MODULO, PARAMETRO, DESCRIZIONE) VALUES ('SIT_FORLI', 'CONNECTIONSTRING_TOPONOMASTICA', 'Connection string (formato Oracle) per il db che contiene le viste della toponomastica');

INSERT INTO verticalizzazioniparametribase(MODULO, PARAMETRO, DESCRIZIONE) VALUES ('SIT_FORLI', 'CONNECTIONSTRING_CATASTO', 'Connection string (formato Oracle) per il db che contiene le viste del catasto');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('PROTOCOLLO_ATTIVO','FORZA_FASCICOL_NOT_AUTOMATICA','Parametro che permette di applicare durante la notifica automatica dei movimenti, laddove è attiva anche la protocollazione del movimento, la fascicolazione contestuale. La fascicolazione sarà eseguita se l''istanza è fascicolata e prenderà i dati di fascicolazione dell''istanza. Valori ammessi S o N (predefinito N)');

INSERT INTO verticalizzazioniparametribase(MODULO, PARAMETRO, DESCRIZIONE) VALUES ('SIT_FORLI', 'URL_CARTOGRAFIA_DA_CIVICO', 'Url per aprire la cartografia a partire da un numero civico (per forlì è https://moka.comune.forli.fc.it/mokaApp/applicazioni/VBG_MET01?query=Numeri%20civici,CIVICO=%27$CIVICO$$ESPONENTE$%27%20AND%20VIA_COD=%27$CODICEVIA$%27)');

INSERT INTO verticalizzazioniparametribase(MODULO, PARAMETRO, DESCRIZIONE) VALUES ('SIT_FORLI', 'URL_CARTOGRAFIA_DA_MAPPALE', 'Url per aprire la cartografia a partire da un mappale (per forlì è https://moka.comune.forli.fc.it/mokaApp/applicazioni/VBG_MET01?query=Particelle%20catastali,FOGLIO=%27$FOGLIO$%27%20AND%20PARTICELLA=%27$PARTICELLA$%27');


INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('PROTOCOLLO_URBI','TIPO_INVIO_PEC','Valori ammessi: 0 e 1 (se non impostato vale 1).0: Invia PEC singola; 1: Invia una PEC per ogni destinatario');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('PROTOCOLLO_URBI','NO_AVVIO_ITER','Se impostato ad S non avvia l''iter del protocollo (disattiva l''avvio dell''iter di WFA).');


INSERT INTO elenchiprofessionalibase(ep_id, ep_descrizione, ep_atribcodice,flag_regionale) VALUES (21,'Albo degli Spedizionieri Doganali',6,0);
INSERT INTO elenchiprofessionalibase(ep_id, ep_descrizione, ep_atribcodice,flag_regionale) VALUES (22,'Ordine dei chimici e fisici',6,0);
INSERT INTO elenchiprofessionalibase(ep_id, ep_descrizione, ep_atribcodice,flag_regionale) VALUES (24,'Collegio dei periti edili',6,0);
 

update alberoproc_oneri set flag_importo_libero=1 where AO_IMPORTOCAUSALE is null or AO_IMPORTOCAUSALE = 0;
update inventarioprocedimentioneri set FLAG_IMPORTO_LIBERO=1 where IMPORTO is null or IMPORTO = 0;

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO,PARAMETRO,DESCRIZIONE) VALUES ('STC','LISTANODI_COPIA_PROTOCOLLO','Riporta la lista dei nodi per i quali copiare i riferimenti di protocollo se stessa direzione. Il comportamento viene sovrascritto da FORZA_PROTOCOLLAZIONE. La lista dei nodi è separata da virgola (,) es. 320,150');

INSERT INTO verticalizzazioniparametribase(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('AREA_RISERVATA', 'ATTIVA_COMPILAZIONE_ONCE_ONLY', 'Se impostato a 1 attiva la precompilazione dei dati once only durante la presentazione domanda (default: 0)');

UPDATE clmenu_java SET menulink_v2='0AF' WHERE id=750;

INSERT INTO PAY_CONNECTOR_CONFIG_PARAMS(CONFIG_PARAM,DESCRIZIONE,CODICE_CONNETTORE) VALUES ('ATTIVA_NOTIFICHE_RABBITMQ','Impostare a 1 per attivare le notifiche verso RabbitMQ. Togliere o impostare a 0 per disattivarle',NULL);

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('PROTOCOLLO_ACARIS','RICERCA_FASCICOLO_PER_OGGETTO','Può assumere uno dei seguenti valori: 1 o 0 ed indica se la ricerca di un FASCICOLO deve essere fatta per oggetto ( valore 1 ) oppure per codice ( valore 0 ). Di default è 0');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('PROTOCOLLO_ACARIS','NUMERO_FASCICOLO','Indica il numero che il fascicolo deve avere in fase di protocollazione. E'' possibile concatenare testo ai seguenti segnaposti [NUMEROISTANZA] o [IDBOLLETTAZIONE] che verranno rispettivamente sostituiti con il numero dell''istanza o con il progressivo della bollettazione. Se non impostato verrà usato il numero dell''istanza');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES ('PROTOCOLLO_ACARIS','INVIA_COPIA_CORTESIA','Solo per le protocollazioni in partenza, ammette i valori 1 o 0. Se attivato ( valore 1 ) il sistema invia una copia di cortesia del documento principale ai destinatari che non hanno un codice IPA valorizzato; se non attivato ( valore 0 o non impostato ) non verrà inviata nessuna copia di cortesia');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO,PARAMETRO,DESCRIZIONE) VALUES('COMPORTAMENTI_ISTANZE','URL_WS_RIEPILOGO_PRATICA','In questa URL va messa l''indirizzo per accedere al web service di rigenerazione riepilogo pratica es: http://devel3.vbg.community/frontoffice/AreaRiservata/webservices/istanze/visura/riepilogo-pratica.asmx?WSDL. Questo parametro, allo stato attuale, genera in automatico il riepilogo dell''istanza solo per le domande appartenenti ad interventi con Pubblica impostato a "Solo Domanda on line"');

