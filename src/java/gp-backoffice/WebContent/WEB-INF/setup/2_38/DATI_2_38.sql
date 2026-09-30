INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_URBI', 'AOO', 'Indicare il codice AOO del sistema di protocollo Urbi, questo codice è necessaio in fase di protocollazione, il protocollo infatti deve sapere su quale AOO viene creato, anche il titolario viene restituito il base all''AOO.');
INSERT INTO CLMENU_JAVA(ID,DESCRIZIONE,PAGINA,MENULINK,SOFTWARE,JSP,LAYOUTTESTI,VERTICALIZZAZIONE,SOFTWAREESCLUSI,LINK_STANDARD,TIPO_FUNZIONALITA) VALUES
(1028, 'Gruppi istruttori','gruppiistruttori/list.htm?software=SOFTWARE','0AZOA','*','JAVA',0,NULL,'AB','gruppiistruttori/list.htm?software=SOFTWARE','S');


INSERT INTO verticalizzazionibase (modulo, descrizione, flag_gestcomune) VALUES ('ANTI_CORRUZIONE', 'Modulo per la gestione delle configurazioni della funzionalità anti corruzione e conflitto di interessi. Permette di impostare i parametri come ad esempio: Mail Tipo, Testi tipo ',0);
INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('ANTI_CORRUZIONE', 'TESTO_TIPO_PRESA_IN_CARICO', 'Codice del testo tipo, utilizzato per popolare il pannello di accettazione da parte di un utente');
INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('ANTI_CORRUZIONE', 'MAIL_TIPO_PRESA_IN_CARICO', 'Codice mail tipo, utilizzato per popolare l''email per notificare al responsabile del procedimento la presa in carico di un istanza da parte dell''istruttore designato');
INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('ANTI_CORRUZIONE', 'MAIL_TIPO_RIFIUTO_INCARICO', 'Codice mail tipo, utilizzato per popolare l''email per notificare al responsabile del procedimento il rifiuto da parte dell''istruttore');
INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('ANTI_CORRUZIONE', 'MAIL_TIPO_ASSEGNAZIONE', 'Codice mail tipo, utilizzato per popolare l''email per notificare all'' istruttore l'' assegnazione di un istanza');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_URBI', 'REPLACE_TITOLARIO', 'Indicare la stringa da sostituire relativa alla descrizione delle voci di titolario, la chiamata a getElencoTitolario presenta, sulla descrizione, una descrizione iniziale ripetuta su tutte le voci, che può essere eliminata indicandone il valore in questo parametro, ad esempio, nell''ambiente di test del comune di Acquasparta è sempre presente la descrizione "Titolario COMUNE DI ACQUASPARTA/" e successivamente la voce reale.');


INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_HALLEY', 'DISABILITA_FASCICOLAZIONE', 'Valorizzare a 1 questo parametro se si intende non creare i dati di fascicolo per il protocollo halley, altrimenti, con qualsiasi altro valore, il sistema andrà a gestire e creare anche i dati relativi al fascicolo. Da notare che i dati di fascicolo devono essere specificati sulla segnatura di protocollazione, non è quindi possibile usare la fascicolazione standard indicata nel parametro GESTISCI_FASCICOLAZIONE della verticalizzazione PROTOCOLLO_ATTIVO.');

update comuni set cap='51010' where codicecomune='L522' and comune='UZZANO';

INSERT
INTO verticalizzazioniparametribase
  (
    modulo,
    parametro,
    descrizione
  )
  VALUES
  (
    'STC',
    'NLA_IDNODO_RFC239',
    ' Id del nodo NLA NLA-RFC-239 registrato su STC (utilizzato per individuare le chiamate dell''applicativo che gestisce le comunicazioni secondo l''rfc 239) '
  ) ;

INSERT INTO MAPOGGETTI (NOMETABELLA, NOMECAMPO)  VALUES ('OGGETTI_STORICO', 'CODICEOGGETTO');
INSERT INTO MAPOGGETTI (NOMETABELLA, NOMECAMPO)  VALUES ('OGGETTI_STORICO', 'CODICEOGGETTO_SOSTIT');


INSERT INTO COMUNIASSOCIAZIONI (CODICEASSOCIAZIONE, ASSOCIAZIONE) VALUES ('AC114', 'UNIONE DEI COMUNI FRENTANI');
INSERT INTO COMUNIASSOCIAZIONI (CODICEASSOCIAZIONE, ASSOCIAZIONE) VALUES ('AC107', 'UNIONE DEL SORBARA');
INSERT INTO COMUNIASSOCIAZIONI (CODICEASSOCIAZIONE, ASSOCIAZIONE) VALUES ('AH896', 'UNIONE RENO GALLIERA');
INSERT INTO COMUNIASSOCIAZIONI (CODICEASSOCIAZIONE, ASSOCIAZIONE) VALUES ('AB086', 'UNIONE TERRA DI MEZZO');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('ARCHIVIAZIONE_DOC_LEGALDOC', 'OPERATION', 'parametro obbligatorio che identifica il tipo di richiesta a LegalDoc. Valore di default: conserve');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('ARCHIVIAZIONE_DOC_LEGALDOC', 'BUCKET', 'parametro obbligatorio che identifica il bucket per cui inviare in conservazione');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('ARCHIVIAZIONE_DOC_LEGALDOC', 'POLICY', 'parametro obbligatorio che identifica la policy da specificare nella richiesta di conservazione');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('ARCHIVIAZIONE_DOC_LEGALDOC', 'FILE_EXTENSIONS_REPLACEMENT', 'parametro per specificare una lista di estensioni da sostituire ed i valori da utilizzare. es: .pdf.p7m.p7m,.pdf.p7m;.pdf.p7m.tsd,.pdf.tsd');


UPDATE STATIISTANZA SET FLAG_BLOCCA_INTEGR_ONLINE = 0 WHERE FLAG_BLOCCA_INTEGR_ONLINE is NULL AND FKCODCOMPORTAMENTO != -1;
UPDATE STATIISTANZA SET FLAG_BLOCCA_INTEGR_ONLINE = 1 WHERE FLAG_BLOCCA_INTEGR_ONLINE is NULL AND FKCODCOMPORTAMENTO = -1;

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_URBI', 'GEST_DOWNLOAD_ALLEGATI', 'Parametro che indica se deve essere gestito anche il download degli allegati sulla funzionalità di leggi protocollo, in questo caso valorizzare questo parametro a 1. Il web service APIREST del protocollo URBI SMART ha un modulo a parte per la gestione dei download degli allegati, se l''ente non ha acquistato la licenza di questo modulo da PA DIGITALE la funzionalità di download allegati non deve essere attivata.');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_URBI', 'INVIO_PEC', 'Parametro che sta ad indicare se deve essere utilizzata la funzionalità di invio pec del web service (valore uguale a 1) o meno (qualsiasi valore diverso da 1 o non valorizzato)');

INSERT INTo verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('AREA_RISERVATA', 'INTEGR.NO_UPLOAD_ALLEGATI', 'Se impostato a 1 non permette il caricamento di allegati nelle integrazioni documentali');
INSERT INTo verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('AREA_RISERVATA', 'INTEGR.NO_UPLOAD_RIEPILOGHI_SD', 'Se impostato a 1 non permette il caricamento dei riepiloghi delle schede dinamiche');

update tipimovimento set FLAG_INTEGR_CHECK_FIRMA = 1 where FLAG_INTEGR_CHECK_FIRMA is null and FK_FO_SOGGETTIESTERNI=1;

update tipimovimento set FLAG_INTEGR_CHECK_FIRMA = 0 where FLAG_INTEGR_CHECK_FIRMA is null;
