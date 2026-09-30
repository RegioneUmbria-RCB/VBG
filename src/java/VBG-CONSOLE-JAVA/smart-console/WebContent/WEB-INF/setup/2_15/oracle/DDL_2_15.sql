
ALTER TABLE STP_TIPOLOGIE_ENDO2  ADD FKIDAZIONE NUMBER(4, 0) ;

ALTER TABLE STP_TIPOLOGIE_ENDO2  ADD CONSTRAINT FK_STP_TIPOL_ENDO2_AZIONI FOREIGN KEY (FKIDAZIONE) REFERENCES AZIONI(AZ_ID);


ALTER TABLE AMMINISTRAZIONI  ADD CODICE_CART VARCHAR2(10);


ALTER TABLE AMMINISTRAZIONI ADD TIPOMOVIMENTO_CART VARCHAR2(8) ;


ALTER TABLE AMMINISTRAZIONI ADD CONSTRAINT FK_AMMINIST_TIPO_MOV_CART FOREIGN KEY (IDCOMUNE,TIPOMOVIMENTO_CART) REFERENCES TIPIMOVIMENTO(IDCOMUNE,TIPOMOVIMENTO);

CREATE TABLE STP_MODALITA_APERTURA 
(
  ID VARCHAR2(100) NOT NULL, 
  DESCRIZIONE VARCHAR2(200) NOT NULL,
  TIPO_SCHEDA VARCHAR2(4)
);


ALTER TABLE STP_MODALITA_APERTURA ADD CONSTRAINT PK_STP_MOD_APERTURA PRIMARY KEY (ID);


ALTER TABLE NATURAENDO ADD FK_MOD_APERTURA_SCHEDA1 VARCHAR2(100);
ALTER TABLE NATURAENDO ADD FK_MOD_APERTURA_SCHEDA2 VARCHAR2(100) ;

ALTER TABLE NATURAENDO ADD CONSTRAINT FK_STP_MOD_APER_NATURA_ENDO2 FOREIGN KEY(FK_MOD_APERTURA_SCHEDA2) REFERENCES STP_MODALITA_APERTURA(ID); 

ALTER TABLE NATURAENDO ADD CONSTRAINT FK_STP_MOD_APER_NATURA_ENDO1 FOREIGN KEY(FK_MOD_APERTURA_SCHEDA1) REFERENCES STP_MODALITA_APERTURA(ID); 

ALTER TABLE PROTOCOLLO_MODALITAINVIO ADD IDCOMUNE VARCHAR2(6);
 
UPDATE PROTOCOLLO_MODALITAINVIO SET IDCOMUNE = ICOMUNE;
 
ALTER TABLE PROTOCOLLO_MODALITAINVIO DROP CONSTRAINT PROTOCOLLO_MODALITAINVIO_PK;
 
ALTER TABLE PROTOCOLLO_MODALITAINVIO MODIFY (IDCOMUNE NOT NULL);
 
ALTER TABLE PROTOCOLLO_MODALITAINVIO ADD CONSTRAINT PK_PROTOCOLLO_MODALITAINVIO PRIMARY KEY (IDCOMUNE,CODICE);

ALTER TABLE PROTOCOLLO_MODALITAINVIO DROP COLUMN ICOMUNE;

ALTER TABLE STP_ENDO_TIPO2 ADD FK_MODAPERTURAID VARCHAR2(100);
ALTER TABLE STP_ENDO_TIPO2 ADD CONSTRAINT FK_STPMODAPERTURA_ID FOREIGN KEY(FK_MODAPERTURAID) REFERENCES STP_MODALITA_APERTURA(ID);
comment on column stp_endo_tipo2.fk_modaperturaid  is 'Chiave esterna verso la tabella stp_modalita_apertura.  Una volta scaricata la scheda di spiegazione viene salvata nella colonna il riferimento a schedaregionale.modalitaapertura. Il valore serve al frontend per verificare se l''azione è una scia o meno';

CREATE OR REPLACE VIEW VW_I_ATTIVITAAUTORIZZAZIONI AS Select I_ATTIVITA.ID as I_IDATTIVITA, AUTORIZZAZIONI.AUTORIZNUMERO,AUTORIZZAZIONI.AUTORIZDATA, AUTORIZZAZIONI.AUTORIZDATAREGISTR, AUTORIZZAZIONI.AUTORIZRESPONSABILE, AUTORIZZAZIONI.FKIDREGISTRO,AUTORIZZAZIONI.FKIDISTANZA, AUTORIZZAZIONI.IDCOMUNE,AUTORIZZAZIONI.CODICEMOVIMENTO, AUTORIZZAZIONI.ID,AUTORIZZAZIONI.DATA_CESSAZIONE From I_ATTIVITA, AUTORIZZAZIONI, ISTANZE Where ISTANZE.IDCOMUNE = I_ATTIVITA.IDCOMUNE and ISTANZE.FK_IDI_ATTIVITA = I_ATTIVITA.ID and AUTORIZZAZIONI.IDCOMUNE = ISTANZE.IDCOMUNE and AUTORIZZAZIONI.FKIDISTANZA = ISTANZE.CODICEISTANZA;

DROP VIEW VW_ALBEROPROC;

CREATE VIEW VW_ALBEROPROC AS SELECT alberoproc.idcomune,
  alberoproc.software,
  alberoproc.sc_id,
  alberoproc.sc_codice,
  Alberoproc.Sc_Padre,
  Decode(Alberoproc_L1.Sc_Descrizione,Null,'',Alberoproc_L1.Sc_Descrizione)
  || Decode(Alberoproc_L2.Sc_Descrizione,Null,'',' - ' || Alberoproc_L2.Sc_Descrizione)
  || Decode(Alberoproc_L3.Sc_Descrizione,Null,'',' - ' || Alberoproc_L3.Sc_Descrizione)
  || Decode(Alberoproc_L4.Sc_Descrizione,Null,'',' - ' || Alberoproc_L4.Sc_Descrizione)
  || Decode(Alberoproc_L5.Sc_Descrizione,Null,'',' - ' || Alberoproc_L5.Sc_Descrizione)
  || Decode(Alberoproc_L6.Sc_Descrizione,Null,'',' - ' || Alberoproc_L6.Sc_Descrizione)
  || Decode(Alberoproc_L7.Sc_Descrizione,Null,'',' - ' || Alberoproc_L7.Sc_Descrizione)
  || DECODE(alberoproc_l8.sc_descrizione,NULL,'',' - ' || alberoproc_l8.sc_descrizione)
  As Sc_Descrizione,
  Decode(Alberoproc_L2.Sc_Descrizione,Null,'',Alberoproc_L1.Sc_Descrizione)
  || Decode(Alberoproc_L3.Sc_Descrizione,Null,'',' - ' || Alberoproc_L2.Sc_Descrizione)
  || Decode(Alberoproc_L4.Sc_Descrizione,Null,'',' - ' || Alberoproc_L3.Sc_Descrizione)
  || Decode(Alberoproc_L5.Sc_Descrizione,Null,'',' - ' || Alberoproc_L4.Sc_Descrizione) 
  || Decode(Alberoproc_L6.Sc_Descrizione,Null,'',' - ' || Alberoproc_L5.Sc_Descrizione) 
  || Decode(Alberoproc_L7.Sc_Descrizione,Null,'',' - ' || Alberoproc_L6.Sc_Descrizione) 
  || Decode(Alberoproc_L8.Sc_Descrizione,Null,'',' - ' || Alberoproc_L7.Sc_Descrizione) 
  AS sc_descrizionePadre,
  Alberoproc.Sc_Descrizione As Sc_Descrizionebreve,
  Alberoproc_L1.Sc_Descrizione As Descr_L1,
  Alberoproc_L2.Sc_Descrizione As Descr_L2,
  alberoproc_l3.sc_descrizione AS descr_l3,
  Alberoproc_L4.Sc_Descrizione As Descr_L4,
  alberoproc_l5.sc_descrizione AS descr_l5,
  Alberoproc_L6.Sc_Descrizione As Descr_L6,
  Alberoproc_L7.Sc_Descrizione As Descr_L7,
  alberoproc_l8.sc_descrizione AS descr_l8,
  alberoproc.sc_ordine AS ordine0,
  NVL(alberoproc_l1.sc_ordine,0) AS ordine1,
  NVL(alberoproc_l2.sc_ordine,0) AS ordine2,
  NVL(alberoproc_l3.sc_ordine,0) AS ordine3,
  Nvl(Alberoproc_L4.Sc_Ordine,0)  As Ordine4,
  Nvl(Alberoproc_L5.Sc_Ordine,0)  As Ordine5,
  Nvl(Alberoproc_L6.Sc_Ordine,0)  As Ordine6,
  Nvl(Alberoproc_L7.Sc_Ordine,0)  As Ordine7,
  Nvl(Alberoproc_L8.Sc_Ordine,0)  As Ordine8,
  Decode(Alberoproc_L8.Fkcodicemercato,Null,Decode(Alberoproc_L7.Fkcodicemercato,Null,Decode(Alberoproc_L6.Fkcodicemercato,Null,Decode(Alberoproc_L5.Fkcodicemercato,Null,Decode(Alberoproc_L4.Fkcodicemercato,Null,Decode(Alberoproc_L3.Fkcodicemercato,Null,Decode(Alberoproc_L2.Fkcodicemercato,Null,Alberoproc_L1.Fkcodicemercato,Alberoproc_L2.Fkcodicemercato),Alberoproc_L3.Fkcodicemercato),Alberoproc_L4.Fkcodicemercato),Alberoproc_L5.Fkcodicemercato),Alberoproc_L6.Fkcodicemercato),Alberoproc_L7.Fkcodicemercato),Alberoproc_L8.Fkcodicemercato) As Fkcodicemercato,
  DECODE(alberoproc_l8.fkidmercatiuso,NULL,DECODE(alberoproc_l7.fkidmercatiuso,NULL,DECODE(alberoproc_l6.fkidmercatiuso,NULL,DECODE(alberoproc_l5.fkidmercatiuso,NULL,DECODE(alberoproc_l4.fkidmercatiuso,NULL,DECODE(alberoproc_l3.fkidmercatiuso,NULL,DECODE(alberoproc_l2.fkidmercatiuso,NULL,alberoproc_l1.fkidmercatiuso,alberoproc_l2.fkidmercatiuso),alberoproc_l3.fkidmercatiuso),alberoproc_l4.fkidmercatiuso),alberoproc_l5.fkidmercatiuso),alberoproc_l6.fkidmercatiuso),alberoproc_l7.fkidmercatiuso),alberoproc_l8.fkidmercatiuso)  AS fkidmercatiuso,
  alberoproc.sc_attivo,
  alberoproc.progressivoistanze
From Alberoproc
Left Join Alberoproc Alberoproc_L1 On Alberoproc_L1.Idcomune = Alberoproc.Idcomune And Alberoproc_L1.Software = Alberoproc.Software And Alberoproc_L1.Sc_Codice = Substr(Alberoproc.Sc_Codice, 0, 2) And Length(Alberoproc_L1.Sc_Codice)=2
Left Join Alberoproc Alberoproc_L2 On Alberoproc_L2.Idcomune = Alberoproc_L1.Idcomune And Alberoproc_L2.Software = Alberoproc_L1.Software And Alberoproc_L2.Sc_Codice = Substr(Alberoproc.Sc_Codice, 0, 4) And Length(Alberoproc_L2.Sc_Codice)=4
Left Join Alberoproc Alberoproc_L3 On Alberoproc_L3.Idcomune = Alberoproc_L2.Idcomune And Alberoproc_L3.Software = Alberoproc_L2.Software And Alberoproc_L3.Sc_Codice = Substr(Alberoproc.Sc_Codice, 0, 6) And Length(Alberoproc_L3.Sc_Codice)=6
Left Join Alberoproc Alberoproc_L4 On Alberoproc_L4.Idcomune = Alberoproc_L3.Idcomune And Alberoproc_L4.Software = Alberoproc_L3.Software And Alberoproc_L4.Sc_Codice = Substr(Alberoproc.Sc_Codice, 0, 8) And Length(Alberoproc_L4.Sc_Codice)=8
Left Join Alberoproc Alberoproc_L5 On Alberoproc_L5.Idcomune = Alberoproc_L4.Idcomune And Alberoproc_L5.Software = Alberoproc_L4.Software And Alberoproc_L5.Sc_Codice = Substr(Alberoproc.Sc_Codice, 0, 10) And Length(Alberoproc_L5.Sc_Codice)=10
Left Join Alberoproc Alberoproc_L6 On Alberoproc_L6.Idcomune = Alberoproc_L5.Idcomune And Alberoproc_L6.Software = Alberoproc_L5.Software And Alberoproc_L6.Sc_Codice = Substr(Alberoproc.Sc_Codice, 0, 12) And Length(Alberoproc_L6.Sc_Codice)=12
Left Join Alberoproc Alberoproc_L7 On Alberoproc_L7.Idcomune = Alberoproc_L6.Idcomune And Alberoproc_L7.Software = Alberoproc_L6.Software And Alberoproc_L7.Sc_Codice = Substr(Alberoproc.Sc_Codice, 0, 14) And Length(Alberoproc_L7.Sc_Codice)=14
Left Join Alberoproc Alberoproc_L8 On Alberoproc_L8.Idcomune = Alberoproc_L7.Idcomune And Alberoproc_L8.Software = Alberoproc_L7.Software And Alberoproc_L8.Sc_Codice = Substr(Alberoproc.Sc_Codice, 0, 16) And Length(Alberoproc_L8.Sc_Codice)=16 WITH READ ONLY;

ALTER TABLE DOCUMENTIISTANZA MODIFY DOCUMENTO VARCHAR2(4000) NOT NULL;

ALTER TABLE MOVIMENTI ADD NUMPROT_MITTENTE VARCHAR2(30);
ALTER TABLE MOVIMENTI ADD DATA_PROT_MITTENTE DATE;
COMMENT ON COLUMN MOVIMENTI.NUMPROT_MITTENTE IS 'Numero protocollo mittente. In caso di comunicazione in ingresso questo campo contiene il numero di protocollo dell''ente che l'' ha inviata';
COMMENT ON COLUMN MOVIMENTI.DATA_PROT_MITTENTE IS 'Data protocollo mittente. In caso di comunicazione in ingresso questo campo contiene la data di protocollo dell''ente che l'' ha inviata';

alter table TMP_ST_ONERI modify FK_RCO_ID number(10); 
alter table TMP_ST_ONERI modify FK_CO_ID number(10);

ALTER TABLE ELENCHIPROFESSIONALIBASE MODIFY EP_DESCRIZIONE VARCHAR2(50);

ALTER TABLE MERCATI ADD FLAG_CONSORZIO NUMBER(1);
CREATE TABLE MERCATI_CONSORZI(
IDCOMUNE VARCHAR2(6) NOT NULL,
ID NUMBER(4) NOT NULL,
FK_CODICE_MERCATO NUMBER(4) NOT NULL,
FK_ANAG_CONSORZIO NUMBER(6) NOT NULL,
DATA_INIZIO_ESERCIZIO DATE,
DATA_FINE_ESERCIZIO DATE
);

ALTER TABLE MERCATI_CONSORZI ADD CONSTRAINT PK_MERCATICONSORZI PRIMARY KEY(IDCOMUNE, ID);
ALTER TABLE MERCATI_CONSORZI ADD CONSTRAINT FK_MERCATICONSORZI_MERC FOREIGN KEY (IDCOMUNE,FK_CODICE_MERCATO) REFERENCES MERCATI (IDCOMUNE,CODICEMERCATO);
ALTER TABLE MERCATI_CONSORZI ADD CONSTRAINT FK_MERCATICONSORZI_ANAG FOREIGN KEY (IDCOMUNE,FK_ANAG_CONSORZIO) REFERENCES ANAGRAFE (IDCOMUNE,CODICEANAGRAFE);

ALTER TABLE MERCATI_CONTI ADD FLAG_IMPORTOMENSILE NUMBER(1);
ALTER TABLE MERCATI_CONTI ADD PERCENTUALE_CONSORZIO NUMBER(5,2);

ALTER TABLE MERCATI_D_CONTI ADD FLAG_IMPORTOMENSILE NUMBER(1);
ALTER TABLE MERCATI_D_CONTI ADD PERCENTUALE_CONSORZIO NUMBER(5,2);

ALTER TABLE ELENCHIPROFESSIONALIBASE ADD EP_ATRIBCODICE NUMBER(4);

CREATE TABLE FO_ARJ_STEPS_TESTATA
(
    IDCOMUNE                           VARCHAR2(6),
    ID                                 NUMBER(6,0),
    SOFTWARE                     	   VARCHAR2(2),
    DESCRIZIONE                        VARCHAR2(4000 CHAR),
    CONSTRAINT FO_ARJ_STEPS_TESTATA_PK PRIMARY KEY (IDCOMUNE, ID) ENABLE,
    CONSTRAINT FK_FOARJSTEPSTESTATA_SOFTWARE FOREIGN KEY (SOFTWARE) REFERENCES SOFTWARE (CODICE) ENABLE
);

CREATE TABLE FO_ARJ_STEPS
(
    IDCOMUNE                           VARCHAR2(6),
    ID                                 NUMBER(6,0),
    NOME                               VARCHAR2(200 CHAR),
    TITOLO                              VARCHAR2(400 CHAR),
    DESCRIZIONE                        VARCHAR2(4000 CHAR),
    ORDINE                             NUMBER(2,0),
    ABILITATO                          NUMBER(1,0),
      FK_FO_ARJ_STEPS_TESTATA    NUMBER(6,0),
    CONSTRAINT FO_ARJ_STEPS_PK PRIMARY KEY (IDCOMUNE, ID) ENABLE,
    CONSTRAINT FK_FOARJSTEPS_FOARJSTEPSTESTA FOREIGN KEY (IDCOMUNE, FK_FO_ARJ_STEPS_TESTATA) REFERENCES FO_ARJ_STEPS_TESTATA (IDCOMUNE, ID) ENABLE
);

CREATE TABLE FO_ARJ_STEPS_PARAMS
(
    IDCOMUNE                           VARCHAR2(6),
    ID                                 NUMBER(6,0),
    CHIAVE                       VARCHAR2(200 CHAR),
    VALORE                              VARCHAR2(400 CHAR),
      FK_FO_ARJ_STEPS                  NUMBER(6,0),
    CONSTRAINT FO_ARJ_STEPS_PARAMS_PK PRIMARY KEY (IDCOMUNE, ID) ENABLE,
    CONSTRAINT FK_FOARJSTEPSPARAMS_FOARJSTEPS FOREIGN KEY (IDCOMUNE, FK_FO_ARJ_STEPS) REFERENCES FO_ARJ_STEPS (IDCOMUNE, ID) ENABLE
);

CREATE TABLE FO_ARJ_DOMANDE
(
    IDCOMUNE                           VARCHAR2(6),
    ID                                 NUMBER(6,0),
    SOFTWARE                     VARCHAR2(2),
    CODICEANAGRAFE               NUMBER(6,0),
    CODICEOGGETTO                NUMBER(10,0),
    CODICEISTANZA                NUMBER(6,0),
    DATA_INVIO                   DATE,
    DATA_ULTIMA_MODIFICA         DATE,
    ID_DOMANDA                   VARCHAR2(50 CHAR),
    STEP                               NUMBER(2,0),
    CONSTRAINT FO_ARJ_DOMANDE_PK PRIMARY KEY (IDCOMUNE, ID) ENABLE,
      CONSTRAINT FK_FOARJDOMANDE_OGG FOREIGN KEY (IDCOMUNE, CODICEOGGETTO) REFERENCES OGGETTI (IDCOMUNE, CODICEOGGETTO) ENABLE,
    CONSTRAINT FK_FOARJDOMANDE_ANAGRAFE FOREIGN KEY (IDCOMUNE, CODICEANAGRAFE) REFERENCES ANAGRAFE (IDCOMUNE, CODICEANAGRAFE) ENABLE,
    CONSTRAINT FK_FOARJDOMANDE_SOFTWARE FOREIGN KEY (SOFTWARE) REFERENCES SOFTWARE (CODICE) ENABLE
);

ALTER TABLE FO_ARJ_DOMANDE ADD CODICEOGGETTO_RIEPILOGO NUMBER(10,0);
ALTER TABLE FO_ARJ_DOMANDE ADD CONSTRAINT FK_FOARJDOMANDE_OGGRIEPI_OGG FOREIGN KEY (IDCOMUNE, CODICEOGGETTO_RIEPILOGO) REFERENCES OGGETTI (IDCOMUNE, CODICEOGGETTO);
