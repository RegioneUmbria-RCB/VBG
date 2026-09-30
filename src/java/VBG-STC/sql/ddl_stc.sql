--------------------------------------------------------
--  File created - 22-09-2009   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table ATTIVITA
--------------------------------------------------------

  CREATE TABLE "STC"."ATTIVITA" 
   (	"ID" NUMBER(8,0), 
	"FKIDPRATICHE" NUMBER(8,0), 
	"IDATTIVITA" VARCHAR2(20), 
	"DATAATTIVITA" DATE, 
	"NUMPROTGEN" VARCHAR2(50), 
	"DATAPROTGEN" DATE, 
	"TIPOATTIVITA" VARCHAR2(150), 
	"DATASISTEMA" DATE, 
	"IDPROCEDIMENTO" VARCHAR2(30)
   ) ;
--------------------------------------------------------
--  DDL for Table CONFIGURAZIONE
--------------------------------------------------------

  CREATE TABLE "STC"."CONFIGURAZIONE" 
   (	"IDNODO" NUMBER(8,0), 
	"IDENTE" VARCHAR2(10), 
	"IDSPORTELLO" VARCHAR2(10), 
	"ENTE" VARCHAR2(50), 
	"SPORTELLO" VARCHAR2(100), 
	"WSURL" VARCHAR2(150), 
	"USERID" VARCHAR2(30), 
	"PASSWORD" VARCHAR2(30), 
	"IDDIREZIONE" VARCHAR2(10), 
	"DIREZIONE" VARCHAR2(50)
   ) ;
--------------------------------------------------------
--  DDL for Table ID_TABLE
--------------------------------------------------------

  CREATE TABLE "STC"."ID_TABLE" 
   (	"ID" VARCHAR2(30), 
	"NEXT_ID" NUMBER(9,0)
   ) ;
--------------------------------------------------------
--  DDL for Table MESSAGGIATTIVITA
--------------------------------------------------------

  CREATE TABLE "STC"."MESSAGGIATTIVITA" 
   (	"ID" NUMBER(8,0), 
	"FKIDRICHIESTA" NUMBER(8,0), 
	"FKIDRISPOSTA" NUMBER(8,0)
   ) ;
--------------------------------------------------------
--  DDL for Table MESSAGGIPRATICHE
--------------------------------------------------------

  CREATE TABLE "STC"."MESSAGGIPRATICHE" 
   (	"ID" NUMBER(8,0), 
	"FKIDRICHIESTA" NUMBER(8,0), 
	"FKIDRISPOSTA" NUMBER(8,0)
   ) ;
--------------------------------------------------------
--  DDL for Table PRATICHE
--------------------------------------------------------

  CREATE TABLE "STC"."PRATICHE" 
   (	"ID" NUMBER(8,0), 
	"IDENTE" VARCHAR2(10), 
	"IDSPORTELLO" VARCHAR2(10), 
	"IDPRATICA" VARCHAR2(30), 
	"NUMPRATICA" VARCHAR2(50), 
	"DATAPRATICA" DATE, 
	"NUMPROTGEN" VARCHAR2(50), 
	"DATAPROTGEN" DATE
   ) ;
--------------------------------------------------------
--  DDL for Table SICUREZZA
--------------------------------------------------------

  CREATE TABLE "STC"."SICUREZZA" 
   (	"ID" NUMBER(8,0), 
	"TOKEN" VARCHAR2(32), 
	"SCADENZA" DATE, 
	"FKIDNODO" NUMBER(8,0)
   ) ;
--------------------------------------------------------
--  Constraints for Table ATTIVITA
--------------------------------------------------------

  ALTER TABLE "STC"."ATTIVITA" ADD CONSTRAINT "ATTIVITA_PK" PRIMARY KEY ("ID") ENABLE;
 
  ALTER TABLE "STC"."ATTIVITA" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table CONFIGURAZIONE
--------------------------------------------------------

  ALTER TABLE "STC"."CONFIGURAZIONE" ADD CONSTRAINT "CONFIGURAZIONE_PK" PRIMARY KEY ("IDNODO") ENABLE;
 
  ALTER TABLE "STC"."CONFIGURAZIONE" ADD CONSTRAINT "CONFIGURAZIONE_UK1" UNIQUE ("IDENTE", "IDSPORTELLO") ENABLE;
 
  ALTER TABLE "STC"."CONFIGURAZIONE" MODIFY ("IDNODO" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ID_TABLE
--------------------------------------------------------

  ALTER TABLE "STC"."ID_TABLE" ADD CONSTRAINT "ID_TABLE_PK" PRIMARY KEY ("ID") ENABLE;
 
  ALTER TABLE "STC"."ID_TABLE" MODIFY ("ID" NOT NULL ENABLE);
 
  ALTER TABLE "STC"."ID_TABLE" MODIFY ("NEXT_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table MESSAGGIATTIVITA
--------------------------------------------------------

  ALTER TABLE "STC"."MESSAGGIATTIVITA" ADD CONSTRAINT "MESSAGGIATTIVITA_PK" PRIMARY KEY ("ID") ENABLE;
 
  ALTER TABLE "STC"."MESSAGGIATTIVITA" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table MESSAGGIPRATICHE
--------------------------------------------------------

  ALTER TABLE "STC"."MESSAGGIPRATICHE" ADD CONSTRAINT "MESSAGGIPRATICHE_PK" PRIMARY KEY ("ID") ENABLE;
 
  ALTER TABLE "STC"."MESSAGGIPRATICHE" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table PRATICHE
--------------------------------------------------------

  ALTER TABLE "STC"."PRATICHE" ADD CONSTRAINT "PRATICHE_PK" PRIMARY KEY ("ID") ENABLE;
 
  ALTER TABLE "STC"."PRATICHE" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table SICUREZZA
--------------------------------------------------------

  ALTER TABLE "STC"."SICUREZZA" ADD CONSTRAINT "SICUREZZA_PK" PRIMARY KEY ("ID") ENABLE;
 
  ALTER TABLE "STC"."SICUREZZA" MODIFY ("ID" NOT NULL ENABLE);
 
  ALTER TABLE "STC"."SICUREZZA" MODIFY ("TOKEN" NOT NULL ENABLE);
 
  ALTER TABLE "STC"."SICUREZZA" MODIFY ("FKIDNODO" NOT NULL ENABLE);
--------------------------------------------------------
--  DDL for Index ATTIVITA_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "STC"."ATTIVITA_PK" ON "STC"."ATTIVITA" ("ID") 
  ;
--------------------------------------------------------
--  DDL for Index CONFIGURAZIONE_IDX001
--------------------------------------------------------

  CREATE UNIQUE INDEX "STC"."CONFIGURAZIONE_IDX001" ON "STC"."CONFIGURAZIONE" ("USERID") 
  ;
--------------------------------------------------------
--  DDL for Index CONFIGURAZIONE_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "STC"."CONFIGURAZIONE_PK" ON "STC"."CONFIGURAZIONE" ("IDNODO") 
  ;
--------------------------------------------------------
--  DDL for Index CONFIGURAZIONE_UK1
--------------------------------------------------------

  CREATE UNIQUE INDEX "STC"."CONFIGURAZIONE_UK1" ON "STC"."CONFIGURAZIONE" ("IDENTE", "IDSPORTELLO") 
  ;
--------------------------------------------------------
--  DDL for Index ID_TABLE_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "STC"."ID_TABLE_PK" ON "STC"."ID_TABLE" ("ID") 
  ;
--------------------------------------------------------
--  DDL for Index MESSAGGIATTIVITA_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "STC"."MESSAGGIATTIVITA_PK" ON "STC"."MESSAGGIATTIVITA" ("ID") 
  ;
--------------------------------------------------------
--  DDL for Index MESSAGGIPRATICHE_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "STC"."MESSAGGIPRATICHE_PK" ON "STC"."MESSAGGIPRATICHE" ("ID") 
  ;
--------------------------------------------------------
--  DDL for Index PRATICHE_INDEX1
--------------------------------------------------------

  CREATE INDEX "STC"."PRATICHE_INDEX1" ON "STC"."PRATICHE" ("IDENTE", "IDSPORTELLO", "IDPRATICA") 
  ;
--------------------------------------------------------
--  DDL for Index PRATICHE_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "STC"."PRATICHE_PK" ON "STC"."PRATICHE" ("ID") 
  ;
--------------------------------------------------------
--  DDL for Index SICUREZZA_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "STC"."SICUREZZA_PK" ON "STC"."SICUREZZA" ("ID") 
  ;
--------------------------------------------------------
--  Ref Constraints for Table ATTIVITA
--------------------------------------------------------

  ALTER TABLE "STC"."ATTIVITA" ADD CONSTRAINT "ATTIVITA_PRATICHE_FK1" FOREIGN KEY ("FKIDPRATICHE")
	  REFERENCES "STC"."PRATICHE" ("ID") ENABLE;


--------------------------------------------------------
--  Ref Constraints for Table MESSAGGIATTIVITA
--------------------------------------------------------

  ALTER TABLE "STC"."MESSAGGIATTIVITA" ADD CONSTRAINT "MESSAGGIATTIVITA_ATTIVITA_FK1" FOREIGN KEY ("FKIDRICHIESTA")
	  REFERENCES "STC"."ATTIVITA" ("ID") ENABLE;
 
  ALTER TABLE "STC"."MESSAGGIATTIVITA" ADD CONSTRAINT "MESSAGGIATTIVITA_ATTIVITA_FK2" FOREIGN KEY ("FKIDRISPOSTA")
	  REFERENCES "STC"."ATTIVITA" ("ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table MESSAGGIPRATICHE
--------------------------------------------------------

  ALTER TABLE "STC"."MESSAGGIPRATICHE" ADD CONSTRAINT "MESSAGGIPRATICHE_PRATICHE_FK1" FOREIGN KEY ("FKIDRICHIESTA")
	  REFERENCES "STC"."PRATICHE" ("ID") ENABLE;
 
  ALTER TABLE "STC"."MESSAGGIPRATICHE" ADD CONSTRAINT "MESSAGGIPRATICHE_PRATICHE_FK2" FOREIGN KEY ("FKIDRISPOSTA")
	  REFERENCES "STC"."PRATICHE" ("ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table PRATICHE
--------------------------------------------------------

  ALTER TABLE "STC"."PRATICHE" ADD CONSTRAINT "PRATICHE_CONFIGURAZIONE_FK1" FOREIGN KEY ("IDENTE", "IDSPORTELLO")
	  REFERENCES "STC"."CONFIGURAZIONE" ("IDENTE", "IDSPORTELLO") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table SICUREZZA
--------------------------------------------------------

  ALTER TABLE "STC"."SICUREZZA" ADD CONSTRAINT "FK_SICUREZZA_CONFIGURAZIONE" FOREIGN KEY ("FKIDNODO")
	  REFERENCES "STC"."CONFIGURAZIONE" ("IDNODO") ENABLE;


CREATE OR REPLACE FORCE VIEW "STC"."VW_MESSAGGIPRATICHE" ("ID", "MITT_IDENTE", "MITT_IDSPORTELLO", "MITT_IDPRATICA", "MITT_NUMPRATICA", "DEST_IDENTE", "DEST_IDSPORTELLO", "DEST_IDPRATICA", "DEST_NUMPRATICA")
AS
  SELECT messaggipratiche.id,
    richieste.idente mitt_idente ,
    richieste.idsportello mitt_idsportello,
    richieste.idpratica mitt_idpratica,
    richieste.numpratica mitt_numpratica,
    risposte.idente dest_idente ,
    risposte.idsportello dest_idsportello,
    risposte.idpratica dest_idpratica,
    risposte.numpratica dest_numpratica
  FROM messaggipratiche
  INNER JOIN pratiche richieste
  ON richieste.id= messaggipratiche.fkidrichiesta
  INNER JOIN pratiche risposte
  ON risposte.id= messaggipratiche.fkidrisposta
  ORDER BY messaggipratiche.id;
  
  
CREATE OR REPLACE FORCE VIEW "STC"."VW_MESSAGGITTIVITA" ("ID", "MITT_IDENTE", "MITT_IDSPORTELLO", "MITT_IDPRATICA", "MITT_NUMPRATICA", "ATT_MITT_ID", "ATT_MITT_TIPO", "ATT_MITT_IDPROC", "DEST_IDENTE", "DEST_IDSPORTELLO", "DEST_IDPRATICA", "DEST_NUMPRATICA", "ATT_DEST_ID", "ATT_DEST_TIPO", "ATT_DEST_IDPROC")
AS
  SELECT messaggiattivita.id,
    richieste.idente mitt_idente ,
    richieste.idsportello mitt_idsportello,
    richieste.idpratica mitt_idpratica ,
    richieste.numpratica mitt_numpratica ,
    att_richieste.idattivita att_mitt_id ,
    att_richieste.tipoattivita att_mitt_tipo ,
    att_richieste.idprocedimento att_mitt_idproc ,
    risposte.idente dest_idente ,
    risposte.idsportello dest_idsportello ,
    risposte.idpratica dest_idpratica ,
    risposte.numpratica dest_numpratica,
    att_risposte.idattivita att_dest_id ,
    att_risposte.tipoattivita att_dest_tipo ,
    att_risposte.idprocedimento att_dest_idproc
  FROM messaggiattivita
  INNER JOIN attivita att_richieste
  ON att_richieste.id= messaggiattivita.fkidrichiesta
  INNER JOIN pratiche richieste
  ON richieste.id=att_richieste.fkidpratiche
  INNER JOIN attivita att_risposte
  ON att_risposte.id= messaggiattivita.fkidrisposta
  INNER JOIN pratiche risposte
  ON risposte.id=att_risposte.fkidpratiche
  ORDER BY messaggiattivita.id;