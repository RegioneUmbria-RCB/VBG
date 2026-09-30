INSERT INTO comuni (
    codicecomune,
    comune,
    siglaprovincia,
    provincia,
    regione,
    cap,
    cf,
    codiceistat,
    codiceistatregione,
    codicestatoestero
) VALUES (
    'M369',
    'ALTO RENO TERME',
    'BO',
    'BOLOGNA',
    'EMILIA ROMAGNA',
    '40046',
    'M369',
    '037062',
    '08',
    NULL
);
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE   (modulo, parametro, descrizione ) VALUES ('FVG_SUAP_IN_RETE','COLLEGA_PRATICHE_SPACCHETTATE','Può assumere i valori 0(NULL) o 1. Se 1 tutte le pratiche create saranno collegate tra loro, con la funzionalità ''ISTANZE COLLEGATE'' ');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE   (modulo, parametro, descrizione ) VALUES ('FVG_SUAP_IN_RETE','SW_RECUPERO_SCHEDE_CONSOLE','Indica il software dell''installazione console da cui recuperare le schede da inserire in fase di allineamento. Se non valorizzato il sistema utilizzerà lo stesso software per cui stiamo inserendo le istanze. Es. se per il software CO popolo il parametro con SS il sistema in caso di allineamento schede andrà a recuperare le informazioni dalla console specificata per il software SS');

INSERT
INTO VERTICALIZZAZIONIPARAMETRIBASE
  (
    modulo,
    parametro,
    descrizione
  )
  VALUES
  (
    'I_ATTIVITA',
    'NON_CONSIDERARE_ISTANZE_COLL',
    'Può assumere i valori 0(null) e 1. Se 1 in caso di creazione di un''attività da un''istanza o collegamento un di un''istanza ad un''attività esistente non verranno prese in considerazione eventuali istanze collegate'
  );

  
INSERT INTO VERTICALIZZAZIONIBASE (MODULO, DESCRIZIONE) VALUES ('PROTOCOLLO_PRISMA', 'Attivare la seguente regola per integrare il protocollo PRISMA, sviluppato da ADS. Tale protocollo utilizza le funzionalità Standard DocArea con estensione ad altre funzionalità, come la fascicolazione, il recupero del titolario, lo smistamento e la lettura di protocolli e allegati.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_PRISMA', 'URL_PROTO_DOCAREA', 'Indicare in questo parametro l''endpoint a cui fa riferimento il web service di protocollazione relativamente alle funzionalità Standard DocArea.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_PRISMA', 'USERNAME', 'Parametro Username relativo alle credenziali per accedere al web service, quest''utente inoltre è indicato in tutte le altre chiamate esposte da tutti i web services.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_PRISMA', 'PASSWORD', 'Parametro Password relativo alle credenziali per accedere al web service');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_PRISMA', 'CODICEENTE', 'Parametro CodiceEnte relativo alle credenziali per accedere al web service');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_PRISMA', 'CODICEAOO', 'E'' il codice dell''Area Organizzativa Omogenea dell''ente configurato nel sistema di protocollo Prisma deve essere comunicato dall''ente stesso.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_PRISMA', 'TIPO_DOCUMENTO_ALLEGATO', '(Facoltativo) Specificare il valore del tipo documento allegato ossia quei documenti allegati al protocollo escluso quello principale. Se non valorizzato prenderà il valore = ''Allegato'' che era il valore fisso che veniva passato in precedenza. Viene valorizzato nel file segnatura.xml dentro Intestazione --> Descrizione --> Documento --> TipoDocumento successivamente a quello principale');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_PRISMA', 'TIPO_DOCUMENTO_PRINCIPALE', '(Facoltativo) Specificare il valore del tipo documento principale. ossia quel documento principale o di richiesta del protocollo.Se non valorizzato prenderà il valore = ''Principale'' che era il valore fisso che veniva passato in precedenza. E'' stato parametrizzato perchè a Piacenza viene richiesto un valore specifico che è ''TRAS''.Viene valorizzato nel file segnatura.xml dentro Intestazione --> Descrizione --> Documento --> TipoDocumento');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_PRISMA', 'APPLICATIVO_PROTOCOLLO', '(Facoltativo) Indica il nome dell''applicativo del protocollo, questa voce sarà inserita dentro il file segnatura.xml dentro l''attributo -nome- di <ApplicativoProtocollo/>. NB. Se lasciato vuoto o non attivato prenderà il valore inserito dentro il parametro CODICEENTE');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_PRISMA', 'UO', '(Facoltativo) Indica l''ufficio di smistamento del protocollo DOCAREA, questa voce sarà inserita dentro il file segnatura.xml dentro il nodo <ApplicativoProtocollo> valorizzando un nuovo parametro con nome -uo-. E'' facoltativo ma il protocollo GS4 di ADS (Piacenza) lo richiede necessariamente.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_PRISMA', 'URL_PEC', 'Indicare in questo parametro l''endpoint a cui fa riferimento il web service di protocollazione relativamente alle funzionalità di invio PEC.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_PRISMA', 'TIPO_REGISTRO', 'Indicare in questo parametro il codice del registro, questo dato serve soprattutto in fase di lettura di un protocollo, visto che il metodo getProtocollo lo richiede espressamente.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_PRISMA', 'URL_ALLEGATI', 'Indicare in questo parametro l''endpoint a cui fa riferimento il web service di protocollazione relativamente alle funzionalità di gestione degli allegati (download, aggiunta....), il web service è denominato Attach Service');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_PRISMA', 'URL_EXTENDED', 'Indicare in questo parametro l''endpoint a cui fa riferimento il web service di protocollazione relativamente alle funzionalità DocArea Extended, tra queste ci sono quelle di fascicolazione e quelle di recupero del titolario.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_PRISMA', 'UO_SMISTAMENTO_MOVIMENTO', 'Indicare in questo parametro l''unità organizzativa che deve essere usata per azionare la funzionalità di Smistamento e Presa in Carico, tale funzionalità si scatenerà solamente durante le protocollazioni da movimento. Se non valorizzato tali funzionalità non verranno azionate.');

INSERT INTO VERTICALIZZAZIONIBASE (MODULO, DESCRIZIONE) VALUES ('SIT_JESI', 'Integrazione con il SIT del Comune di Jesi, indicare 1 per attivare il componente e valorizzare il parametro TIPOSIT della regola SIT_ATTIVO a SIT_JESI');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('SIT_JESI', 'URL_WS_BASE', 'Indicare la url base relativamente al servizio rest in ascolto per quanto concerne il Comune di Jesi.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('SIT_JESI', 'PASSWORD', 'Indicare la password direttamente cripata in SHA1 che viene fornita dal fornitore del SIT, tale parametro servirà per tutte le chiamate che verranno fatte verso il web service');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_PRISMA', 'DENOMINAZIONEENTE', 'In questo parametro va indicata la denominazione dell''ente, ad esempio COMUNE DI....Questo parametro, oltre tutto, andrà anche a valorizzare la denominazione del mittente dei protocolli in partenza, che quindi non recupererà alcun dato dai parametri UO e RUOLO delle amministrazioni.');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_ATTIVO', 'GESTIONE_PEC', 'Indica come deve essere gestito l''indirizzo pec delle anagrafiche; se non valorizzato, o valorizzato a 0 questo parametro, allora la logica sarà la stessa di prima, ossia le anagrafiche relative a Richiedente e Titolare Legale avranno come indirizzo PEC il Domicilio Elettronico della pratica, se non presente sarà valorizzato l''indirizzo PEC dell''anagrafica stessa. Se valorizzato a 1 allora verranno recuperati solo gli indirizzi PEC delle anagrafiche, se valorizzato a 2 allora la logica sarà la stessa indicata con il valore 1, con la differenza che, se non presenti, la logica tornerà ad essere la stessa con il valore 0; se valorizzato a 3 invece, la logica sarà la stessa del valore 2, con la differenza che la logica varrà solo per il titolare legale, qualsiasi altra anagrafica infatti, se vuoto l''indirizzo PEC, lo stesso rimarrà vuoto.');

DELETE FROM VERTICALIZZAZIONIPARAMETRI WHERE MODULO='FVG_SUAP_IN_RETE' AND PARAMETRO='SERCH_PATTER_CO_PROC';

DELETE FROM VERTICALIZZAZIONIPARAMETRIBASE WHERE MODULO='FVG_SUAP_IN_RETE' AND PARAMETRO='SERCH_PATTER_CO_PROC';


INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE   (modulo, parametro, descrizione ) VALUES ('FVG_SUAP_IN_RETE','COD_CLASS_INSIEL_PROC','Il parametro è utilizzato, nella fase di inserimento pratica da SUAP, per individuare quali sono i procedimenti di competenza del modulo/software.
Nel parametro va indicato il codice Insiel della classificazione di un procedimento (GetProcedimento :  <listaClassificazioni>
<classificazione><idClassificazione>...</idClassificazione> <classificazione></listaClassificazioni>) che appunto definisce il backoffice di
competenza(Es. Commercio, Edilizia, ect ). Modalità di utilizzo: se per il modulo commercio è configurato il parametro con il valore 123
il sistema riconoscerà come di endoprocedimenti di competenza del backoffice di commercio tutti i procedimenti Insiel che hanno una classificazione 
con <idClassificazione>123</idClassificazione>. I procedimenti presenti nella pratica che non rispettano questo filtro saranno inseriti utilizzando il codice impostato per il procedimento generico.
');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE   (modulo, parametro, descrizione ) VALUES ('FVG_SUAP_IN_RETE','NOME_FILE_COMUNIC_SUAP','Il parametro è utilizzato per riconoscere quale è l’allegato che contiene la comunicazione accompagnatoria che il SUAP fa verso all’ufficio al quale inoltra la pratica con i procedimenti di competenza.
Nel parametro va indicato il nome del file che rappresenta la comunicazione SUAP al backoffice di competenza della/e pratiche inserite. Il parametro avrà questo
pattern: “nomefile.estensione;descrizione_file”, dove “nomefile.estensione” indica il file da recuperare come comunicazione e “descrizione_file” indica
la descrizione che verrà data al file in fase di inserimento pratica.
Modalità di utilizzo: se per il modulo del commercio il parametro sarà configurato come “MESSAGGIOPEC.TXT;Comunicazione suap al commercio” nella pratiche
di competenza del commercio create avremo un documento con nome file :”MESSAGGIOPEC.TXT” e descrizione :”Comunicazione suap al commercio”
');



DELETE FROM VERTICALIZZAZIONIPARAMETRI WHERE MODULO='FVG_SUAP_IN_RETE' AND PARAMETRO='SPACCHETTA_PRATICA_CO';

DELETE FROM VERTICALIZZAZIONIPARAMETRIBASE WHERE MODULO='FVG_SUAP_IN_RETE' AND PARAMETRO='SPACCHETTA_PRATICA_CO';

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE   (modulo, parametro, descrizione ) VALUES ('FVG_SUAP_IN_RETE','SPACCHETTA_PRATICA','Parametro per impostare il comportamento di spacchettamento pratica. Se attivo verrano create N pratiche quanti sono i procedimenti collegati al modulo per cui è attivo il comportamento. Può assumere i valori 1 : ATTIVO o 0(NULL) : NON ATTIVO');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_INSIEL', 'USA_LIVELLI_CLASSIFICA', 'Questo parametro indica se il componente che adatta la richiesta da inviare al protocollo, deve utilizzare il valore indicato nella classifica come codice (valore a 0 o non valorizzato), oppure come livelli (valorizzare a 1), nel secondo caso va configurata una classifica a più livelli, separati da un punto ".", ad esempio 8.1.1.4, 8 è il primo livello, 1 il secondo, 1 il terzo e 4 il quarto, il tutto fino a 8 livelli.');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_INSIEL', 'TIPO_UFFICIO_ITERATTI', 'Inserire in questo parametro il valore necessario a far avviare ITERATTI in fase di protocollazione, di norma viene inserito il parametro DITER');
