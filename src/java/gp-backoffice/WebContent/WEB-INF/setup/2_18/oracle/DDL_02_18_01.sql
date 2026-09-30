DELETE FROM FO_ARJ_DOMANDE_STEPS_ESEGUITI;
DELETE FROM FO_ARJ_DOMANDE_ONERI;
DELETE FROM FO_ARJ_DOMANDE;
DELETE FROM FO_ARJ_STEPS;

ALTER TABLE FO_ARJ_STEPS DROP CONSTRAINT FK_FASNOMESTEP_FASBNOMESTEP;
ALTER TABLE FO_ARJ_STEPS_PARAMS_BASE DROP CONSTRAINT FK_FOARJSTEPPARMBASE_STEPBASE;
ALTER TABLE FO_ARJ_DOMANDE_STEPS_ESEGUITI DROP CONSTRAINT STEPSESEGUITI_FOARJSTEPSBA;

DROP TABLE FO_ARJ_STEPS_BASE;

CREATE TABLE FO_ARJ_STEPS_BASE
  (
    NOME_STEP              VARCHAR2(50 BYTE) NOT NULL ENABLE,
    LINK_FUNZIONE          VARCHAR2(125 BYTE) NOT NULL ENABLE,
    TITOLO                 VARCHAR2(150 BYTE) NOT NULL ENABLE,
    DESCRIZIONE            VARCHAR2(4000 BYTE),
    FLAG_OBBLIGATORIO      NUMBER(1,0),
    DIPENDE_DA_STEP        VARCHAR2(50 BYTE),
    INCOMPATIBILE_CON_STEP VARCHAR2(50 BYTE),
    ORDINE_DEFAULT         NUMBER(4,0) NOT NULL ENABLE,
    PRIMARY KEY (NOME_STEP)
  );
 
ALTER TABLE FO_ARJ_STEPS ADD CONSTRAINT FK_FASNOMESTEP_FASBNOMESTEP FOREIGN KEY (FK_NOME_STEP_BASE) REFERENCES FO_ARJ_STEPS_BASE (NOME_STEP) ENABLE;

ALTER TABLE FO_ARJ_DOMANDE_STEPS_ESEGUITI ADD CONSTRAINT STEPSESEGUITI_FOARJSTEPSBA FOREIGN KEY (FK_FO_ARJ_STEPS_BASE) REFERENCES FO_ARJ_STEPS_BASE (NOME_STEP) ENABLE;

ALTER TABLE FO_ARJ_STEPS_PARAMS_BASE ADD CONSTRAINT FK_FOARJSTEPPARMBASE_STEPBASE FOREIGN KEY (FK_FO_ARJ_STEP_BASE) REFERENCES FO_ARJ_STEPS_BASE (NOME_STEP) ENABLE; 

ALTER TABLE FO_ARJ_STEPS MODIFY ORDINE NUMBER(4,0);

insert into FO_ARJ_STEPS_BASE (NOME_STEP,LINK_FUNZIONE,TITOLO,DESCRIZIONE,FLAG_OBBLIGATORIO,DIPENDE_DA_STEP,INCOMPATIBILE_CON_STEP,ORDINE_DEFAULT) values ('BENVENUTO','../nuovaistanzabenvenuto/view.htm','Benvenuto','Le pagine che seguono rappresentano il percorso guidato per la presentazione on line di una istanza.<br />Il percorso per inviare la pratica on line è composto da una serie di step (schede).<br />In fondo a destra si trova la numerazione delle pagine e in nero viene evidenziato lo step che si sta compilando.',0,null,null,10);

insert into FO_ARJ_STEPS_BASE (NOME_STEP,LINK_FUNZIONE,TITOLO,DESCRIZIONE,FLAG_OBBLIGATORIO,DIPENDE_DA_STEP,INCOMPATIBILE_CON_STEP,ORDINE_DEFAULT) values ('INFORMATIVA','../nuovaistanzainformativa/view.htm','Informativa privacy','INFORMATIVA AI SENSI DELL''ART. 13 DEL DECRETO LEGISLATIVO N. 196/2003, "CODICE IN MATERIA DI PROTEZIONE DEI DATI PERSONALI"
 
Si informa, ai sensi dell''art. 13 del D. Lgs. n. 196 del 30 giugno 2003 ("Codice in materia di protezione dei dati personali"), che il Comune di Ponteratto, in qualità di "Titolare" del trattamento, è tenuto a fornirle informazioni in merito all''utilizzo dei suoi dati personali.
Per trattamento si intende qualunque operazione o complesso di operazioni concernenti "la raccolta, la registrazione, l''organizzazione, la conservazione, la consultazione, l''elaborazione, la modificazione, la selezione, l''estrazione, il raffronto, l''utilizzo,l''interconnessione, il blocco, la comunicazione, la diffusione, la cancellazione e la distruzione di dati, anche se non registrati in una banca dati".
La raccolta dei suoi dati personali viene effettuata registrando i dati da lei stesso forniti, in qualità di interessato, al momento della iscrizione al sistema di autenticazione federato.
 
I dati personali sono trattati per le finalità previste dal procedimento. Per garantire l''efficienza del servizio la informiamo inoltre che i dati potrebbero essere utilizzati per effettuare prove tecniche e di verifica.
In relazione alle finalità descritte, il trattamento dei dati personali avviene mediante strumenti manuali, informatici e telematici con logiche strettamente correlate alle finalità sopra evidenziate e, comunque, in modo da garantire la sicurezza e la riservatezza dei dati stessi. Adempiute le finalità prefissate, i dati verranno cancellati o trasformati in forma anonima.
 
Ai sensi dell''art. 13, 1° comma lett. b) e c), si evidenzia che il trattamento dei dati da parte del Comune è essenziale per l''adempimento degli obblighi di legge e che, pertanto, il mancato conferimento di tali dati impedisce l''utilizzo del servizio offerto on line.
I suoi dati personali potranno essere conosciuti esclusivamente dagli operatori dell''ufficio del SUAP del Comune di Ponteratto e da tutti gli operatori degli altri uffici comunali interessati dal procedimento, individuati quali Incaricati del trattamento.
Si precisa inoltre che i dati verranno comunicati a terzi esclusivamente in adempimento di specifici obblighi di legge, ovvero qualora tale comunicazione risulti necessaria o funzionale alla gestione del servizio, previa designazione in qualità di Responsabili del trattamento e garantendo il medesimo livello di protezione.
 
Si informa, infine, che l''art. 7 del Decreto Legislativo n. 196/2003, riportato di seguito integralmente, attribuisce all''interessato specifici diritti a garanzia della corretta acquisizione e del corretto utilizzo dei dati trattati ed in particolare:
L''interessato ha diritto di ottenere la conferma dell''esistenza o meno di dati personali che lo riguardano, anche se non ancora registrati, e la loro comunicazione in forma intelligibile.L''interessato ha diritto di ottenere l''indicazione: 
dell''origine dei dati personali; 
delle finalità e modalità del trattamento; 
della logica applicata in caso di trattamento effettuato con l''ausilio di strumenti elettronici; 
degli estremi identificativi del titolare, dei responsabili e del rappresentante designato ai sensi dell''articolo 5, comma 2; 
dei soggetti o delle categorie di soggetti ai quali i dati personali possono essere comunicati o che possono venirne a conoscenza in qualità di rappresentante designato nel territorio dello Stato, di responsabili o incaricati. 
L''interessato ha diritto di ottenere:l''aggiornamento, la rettificazione ovvero, quando vi ha interesse, l''integrazione dei dati; 
la cancellazione, la trasformazione in forma anonima o il blocco dei dati trattati in violazione di legge, compresi quelli di cui non è necessaria la conservazione in relazione agli scopi per i quali i dati sono stati raccolti o successivamente trattati; ',0,null,null,20);

insert into FO_ARJ_STEPS_BASE (NOME_STEP,LINK_FUNZIONE,TITOLO,DESCRIZIONE,FLAG_OBBLIGATORIO,DIPENDE_DA_STEP,INCOMPATIBILE_CON_STEP,ORDINE_DEFAULT) values ('CONFERMA','../nuovaistanzaconferma/view.htm','Conferma','...',0,null,'INVIO',130);

insert into FO_ARJ_STEPS_BASE (NOME_STEP,LINK_FUNZIONE,TITOLO,DESCRIZIONE,FLAG_OBBLIGATORIO,DIPENDE_DA_STEP,INCOMPATIBILE_CON_STEP,ORDINE_DEFAULT) values ('LOCALIZZAZIONE','../nuovaistanzalocalizzazione/view.htm','Localizzazione','È possibile in questa sezione inserire nel campo "Indirizzo" la localizzazione dell''intervento. <br />Selezionare un indirizzo compreso nello stradario comunale dopo aver digitato nel campo "Denominazione" almeno due lettere della localizzazione, quindi completare l''indirizzo indicando il civico ed eventuali note.<br />Se l''intervento ricade in una zona ancora non censita digitare "NON DEFINITO" nel campo "Denominazione" e nel campo "Note" immettere una localizzazione di massima (frazione, zona, etc.)',0,null,null,60);

insert into FO_ARJ_STEPS_BASE (NOME_STEP,LINK_FUNZIONE,TITOLO,DESCRIZIONE,FLAG_OBBLIGATORIO,DIPENDE_DA_STEP,INCOMPATIBILE_CON_STEP,ORDINE_DEFAULT) values ('INTERVENTO','../nuovaistanzaintervento/view.htm','Intervento','In questa sezione è possibile individuare l''attività della propria azienda secondo la classificazione adottata dallo sportello unico del comune',0,null,null,30);

insert into FO_ARJ_STEPS_BASE (NOME_STEP,LINK_FUNZIONE,TITOLO,DESCRIZIONE,FLAG_OBBLIGATORIO,DIPENDE_DA_STEP,INCOMPATIBILE_CON_STEP,ORDINE_DEFAULT) values ('ALLEGATI','../nuovaistanzaallegati/view.htm','Allegati','Gli allegati che riportano il simbolo (<b>*</b>) sono obbligatori. Sarà cura del richiedente o della persona incaricata allegare i documenti proposti nell''elenco. <br />Il bottone "Sfoglia" permette di cercare il documento da caricare , una volta selezionato cliccare sulla voce "Carica". <br />',0,'INTERVENTO',null,80);

insert into FO_ARJ_STEPS_BASE (NOME_STEP,LINK_FUNZIONE,TITOLO,DESCRIZIONE,FLAG_OBBLIGATORIO,DIPENDE_DA_STEP,INCOMPATIBILE_CON_STEP,ORDINE_DEFAULT) values ('SCHEDE','../nuovaistanzaschede/view.htm','Schede','...',0,'INTERVENTO',null,90);

insert into FO_ARJ_STEPS_BASE (NOME_STEP,LINK_FUNZIONE,TITOLO,DESCRIZIONE,FLAG_OBBLIGATORIO,DIPENDE_DA_STEP,INCOMPATIBILE_CON_STEP,ORDINE_DEFAULT) values ('PROCEDIMENTI','../nuovaistanzaprocedimenti/view.htm','Procedimenti','In questa sezione è possibile attivare gli endoprocedimenti utili al completamento dell''istanza.<br />Gli endo-procedimenti già selezionati sono proposti dal sistema in base alla compilazione degli step precedenti.',0,'INTERVENTO',null,70);

insert into FO_ARJ_STEPS_BASE (NOME_STEP,LINK_FUNZIONE,TITOLO,DESCRIZIONE,FLAG_OBBLIGATORIO,DIPENDE_DA_STEP,INCOMPATIBILE_CON_STEP,ORDINE_DEFAULT) values ('ONERI','../nuovaistanzaoneri/view.htm','Riepilogo diritti/oneri','Questa scheda riepiloga quanti diritti di istruttoria/oneri/bolli devono essere corrisposti con riferimento all''intervento e agli endoprocedimenti attivati.
Per i pagamenti per i quali si possiede già la ricevuta si ricorda che questa deve essere firmata digitalmente. I pagamenti possono essere effettuati mediante bollettino utilizzando le seguenti coordinate: 
<b>c/c postale n. XXXX intestato a Comune di XXXXX- Direzione XXXXXX.</b>',0,'INTERVENTO',null,110);

insert into FO_ARJ_STEPS_BASE (NOME_STEP,LINK_FUNZIONE,TITOLO,DESCRIZIONE,FLAG_OBBLIGATORIO,DIPENDE_DA_STEP,INCOMPATIBILE_CON_STEP,ORDINE_DEFAULT) values ('ALLEGATI_SCHEDE','../nuovaistanzaallegatischede/view.htm','Allegati Schede','...',0,'SCHEDE',null,100);

insert into FO_ARJ_STEPS_BASE (NOME_STEP,LINK_FUNZIONE,TITOLO,DESCRIZIONE,FLAG_OBBLIGATORIO,DIPENDE_DA_STEP,INCOMPATIBILE_CON_STEP,ORDINE_DEFAULT) values ('ANAGRAFE','../nuovaistanzaanagrafesingola/view.htm','Anagrafe','In questa scheda vanno inseriti tutti i soggetti, persone fisiche e giuridiche, coinvolti nella pratica che si sta presentando. I soggetti possono essere l''interessato, l''azienda coinvolta, l''intermediario ...',0,'INTERVENTO','(null)',40);

insert into FO_ARJ_STEPS_BASE (NOME_STEP,LINK_FUNZIONE,TITOLO,DESCRIZIONE,FLAG_OBBLIGATORIO,DIPENDE_DA_STEP,INCOMPATIBILE_CON_STEP,ORDINE_DEFAULT) values ('DOMICILIO_ELETTRONICO','../nuovaistanzadomicilioelettronico/view.htm','Domicilio Elettronico','...',0,'ANAGRAFE','(null)',50);

insert into FO_ARJ_STEPS_BASE (NOME_STEP,LINK_FUNZIONE,TITOLO,DESCRIZIONE,FLAG_OBBLIGATORIO,DIPENDE_DA_STEP,INCOMPATIBILE_CON_STEP,ORDINE_DEFAULT) values ('RIEPILOGO','../nuovaistanzariepilogo/view.htm','Riepilogo Domanda','...',0,null,null,120);

insert into FO_ARJ_STEPS_BASE (NOME_STEP,LINK_FUNZIONE,TITOLO,DESCRIZIONE,FLAG_OBBLIGATORIO,DIPENDE_DA_STEP,INCOMPATIBILE_CON_STEP,ORDINE_DEFAULT) values ('INVIO','../nuovaistanzainviodomanda/view.htm','Invio Domanda','...',0,null,'CONFERMA',140);






COMMENT ON COLUMN FO_ARJ_STEPS_BASE.NOME_STEP
IS
  'Identificativo parlante dello step Es. ANAGRAFICHE';
  
COMMENT ON COLUMN FO_ARJ_STEPS_BASE.LINK_FUNZIONE
IS
  'Specifica l''url alla funzionalita'' dell''applicativo areariservata';
  
COMMENT ON COLUMN FO_ARJ_STEPS_BASE.TITOLO
IS
  'Rappresenta il titolo predefinito della funzionalita''. Puo'' essere sovrascritto in FO_ARJ_STEPS.TITOLO';
  
COMMENT ON COLUMN FO_ARJ_STEPS_BASE.DESCRIZIONE
IS
  'Rappresenta la descrizione predefinita della funzionalita''. Puo'' essere sovrascritta in FO_ARJ_STEPS.DESCRIZIONE';
  
COMMENT ON COLUMN FO_ARJ_STEPS_BASE.FLAG_OBBLIGATORIO
IS
  'Indica se lo step e'' obbligatorio e deve comunque essere sempre presente';
  
COMMENT ON COLUMN FO_ARJ_STEPS_BASE.DIPENDE_DA_STEP
IS
  'Indica se lo step puo'' essere istanziato indipendentemente da altri o deve necessariamente essere inserito dopo un''altro step';
  
COMMENT ON TABLE FO_ARJ_STEPS_BASE
IS
  'Tabella di base che censisce le varie tipologie di step che l''area riservata puo'' gestire';
  
