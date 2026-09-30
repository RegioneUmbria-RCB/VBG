INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('COMPORTAMENTI_ISTANZE', 'ELAB_MOVIMENTI_DOPO_DATA_MOV', 'Nell''elaborazione al salvataggio di un movimento vengono rielaborati solamente i movimenti / contromovimenti con data posteriore al movimenti salvato inserito. Valori possibili S o N (predefinito N)');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('STC', 'NODI_NON_SOVR_DESC_ATTIVITA', 'Quando arriva una attivita'' la descrizione del movimento viene impostata con la descrizione passata dal nodo mittente. Questa impostazione permette di evitare questo comportamento e continuare ad usare la descrizione del movimento. Il valore prende una lista di nodi mittenti per i quali non sovrascrivere il valore separati da virgola. (Es: 500,239) ');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('RFC239','NOTIFICA_183_VIA_SEM_ID_NODO','Indicare l''identificativo del nodo mittente che invia in 183 via SEM. ATTENZIONE!!! DEVONO essere attivate le credenziali di inoltro 183 via SEM');

INSERT INTO VERTICALIZZAZIONIBASE (MODULO, DESCRIZIONE, FLAG_GESTCOMUNE) VALUES  ('PARAMETRI_SISTEMA', 'VERTICALIZZAZIONE PER IMPOSTARE / SOVRASCRIVERE ALCUNI PARAMETRI DI SISTEMA', '0');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO,PARAMETRO,DESCRIZIONE) VALUES ('PARAMETRI_SISTEMA','OVERRIDE_URL_GENERA_ALLEGATO','Serve per impostare la url di generazione allegato .asp, indicare l''url completo es: http://server:port/app/GeneraAllegato.asp');
UPDATE COMUNI SET CODICEISTAT='219' WHERE CODICECOMUNE='Z114';
UPDATE COMUNI SET CODICEISTAT='959' WHERE CODICECOMUNE='Z122';
Insert into COMUNI (CODICECOMUNE,COMUNE,SIGLAPROVINCIA,PROVINCIA,REGIONE,CAP,CF,CODICEISTAT,CODICEISTATREGIONE,CODICESTATOESTERO) values ('Z734','PALAU','EE','STATIESTERI',null,null,'Z734','720',null,'720');
Insert into COMUNI (CODICECOMUNE,COMUNE,SIGLAPROVINCIA,PROVINCIA,REGIONE,CAP,CF,CODICEISTAT,CODICEISTATREGIONE,CODICESTATOESTERO) values ('Z907','SUD SUDAN, REPUBBLICA DEL','EE','STATIESTERI',null,null,'Z907','467',null,'467');

update CLMENU_JAVA set LINK_STANDARD='documentidafirmare/documentiMessiAllaFirmaList.htm?software=SOFTWARE' where id=1019;

UPDATE VERTICALIZZAZIONIPARAMETRIBASE SET DESCRIZIONE = 'Indica il limite in bytes dei file caricabili per ogni domanda. Il valore di default è 52428800 bytes (50 Mb). (OBSOLETO - GESTITO IN FO_ARCONFIGURAZIONE)' WHERE modulo='AREA_RISERVATA' and PARAMETRO='DIMENSIONE_MASSIMA_ALLEGATI';

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE   (modulo, parametro, descrizione ) VALUES ('FVG_SUAP_IN_RETE','ALLINEA_SCHEDE','Può assumere i valori 0,1,2. 0 (NULL): gestione dell''inserimento delle schede dinamiche nell''istanza utilizzando la logica della mappature; 1: il sistema verifica se la scheda inviata dal SOL esiste sul backoffice (controllo per nome scheda) in caso positivo utilizzerà la scheda trovata per l''inserimento delle schede dinamiche nell''istanza; 2: il sistema applica la logica per il valore "1", se la ricerca nel backoffice da esisto negativo il NODO NLA SUAP INRETE si occuperà di inserire la scheda mancante sul backoffice andandola a recuperare dalla CONSOLE. Questa opzione necessita della presenza del parametro ALIAS_CONSOLE_SOL ' );
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE   (modulo, parametro, descrizione ) VALUES ('FVG_SUAP_IN_RETE','URL_CONSOLE_SOL','Indica l''indirizzo della console SOL (Es. http://devel9:8080 . Se non popolato il nodo NLA SUAP INRETE considerà come indirizzo lo stesso del backoffice in cui sta inserendo la pratica' );
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE   (modulo, parametro, descrizione ) VALUES ('FVG_SUAP_IN_RETE','ALIAS_CONSOLE_SOL','Indica l''alias associato all''installazione della console SOL. Obbligario per effettuare chiamate da parte di NLA SUAP INRETE al console' );


INSERT INTO LAYOUTTESTIBASE (CODICETESTO, TESTO, SOFTWARE) VALUES ('AREA_RISERVATA.FVG_INTESTAZIONE_LISTA_MODULI', '<div class="alert alert-info">            Procedere alla compilazione delle schede sottostanti, ogni scheda compilata correttamente sarà evidenziata con una spunta verde.<br />            Il pulsante <b>"Crea pdf modulo"</b> verrà abilitato una volta compilate tutte le schede e consentirà di allegare il modulo alla domanda che si sta presentando. <br />            Il pulsante <b>"Anteprima"</b> permetterà la visualizzazione del modello senza i dati di compilazione.        </div>', 'TT');

INSERT INTO verticalizzazioniparametribase (modulo,parametro,descrizione) VALUES ('STC','LISTA_NODI_IA_NON_PROTOCOLLA','Rappresenta la lista degli idnodi ( separata da virgola ) che in fase di INSERIMENTO_ATTIVITA_NLA non devono protocollare. I valori vanno specificati separati da virgola 410,420,430');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE   (modulo, parametro, descrizione ) VALUES ('FVG_SUAP_IN_RETE','NOME_FILE_GENERA_PRATICA','Contiene i nomi che può avere il file xml contenente le informazioni per generare la pratica. Possono essere inseriti più nomi separati di virgole.');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE   (modulo, parametro, descrizione ) VALUES ('FVG_SUAP_IN_RETE','PREFISSO_COD_PRATICA_TEL','Indica la stringa utilizzata come prefisso del campo Codice Pratica Telematica (istanze.codicepraticatel) dell''istanza.');


INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('AREA_RISERVATA', 'ASMART_CROSS_LOGIN_URL', 'AIDA SMART: Url da chiamare per effettuare l''operazione di cross login');
INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('AREA_RISERVATA', 'ASMART_URL_NUOVA_DOMANDA', 'AIDA SMART: Url di cui iniziare il processo di presentazione di una nuova domanda. Es. https://aida2015.comune.livorno.it/aida-smart/areariservata/reserved/NuovaIstanza.aspx?idcomune={ALIAS}&software={SOFTWARE}');
INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('AREA_RISERVATA', 'ASMART_URL_ISTANZE_IN_SOSPESO', 'AIDA SMART: Url per accedere alle istanze in sospeso. Es. https://aida2015.comune.livorno.it/aida-smart/areariservata/reserved/IstanzeInSospeso.aspx?idcomune={ALIAS}&software={SOFTWARE}');
