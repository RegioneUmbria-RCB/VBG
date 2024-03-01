--------------------------------------------------------
--  File creato - lunedì-luglio-17-2023   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table MESSAGGI
--------------------------------------------------------

  CREATE TABLE "APPIO_GW"."MESSAGGI" 
   (	"ID" NUMBER(10,0), 
	"IDCOMUNE" VARCHAR2(6 BYTE), 
	"CODICECOMUNE" VARCHAR2(6 BYTE), 
	"SOFTWARE" VARCHAR2(2 BYTE), 
	"TIME_TO_LIVE" NUMBER(6,0) DEFAULT 3600, 
	"SUBJECT" VARCHAR2(120 BYTE), 
	"MARKDOWN" CLOB, 
	"AMOUNT" NUMBER(10,0), 
	"NOTICE_NUMBER" VARCHAR2(17 BYTE), 
	"INVALID_AFTER_DUE_DATE" NUMBER(1,0), 
	"NRE" VARCHAR2(15 BYTE), 
	"IUP" VARCHAR2(16 BYTE), 
	"PRESCRIBER_FISCAL_CODE" VARCHAR2(16 BYTE), 
	"DUE_DATE" TIMESTAMP (6), 
	"EMAIL" VARCHAR2(320 BYTE), 
	"FISCAL_CODE" VARCHAR2(16 BYTE), 
	"ID_TRANSAZIONE" VARCHAR2(100 BYTE), 
	"SENDER_ALLOWED" NUMBER(1,0), 
	"PREFERRED_LANGUAGES" VARCHAR2(200 BYTE), 
	"CREATED_AT" TIMESTAMP (6), 
	"FK_SERVIZI" NUMBER(10,0), 
	"MESSAGE_ID" VARCHAR2(36 BYTE), 
	"EMAIL_NOTIFICATION" VARCHAR2(50 BYTE), 
	"WEBHOOK_NOTIFICATION" VARCHAR2(50 BYTE), 
	"STATUS" VARCHAR2(50 BYTE), 
	"FK_STATO_MESSAGGIO" NUMBER(10,0)
   );
--------------------------------------------------------
--  DDL for Table PROBLEM
--------------------------------------------------------

  CREATE TABLE "APPIO_GW"."PROBLEM" 
   (	"MESSAGGI_ID" NUMBER(10,0), 
	"TYPE" VARCHAR2(200 BYTE), 
	"TITLE" VARCHAR2(200 BYTE), 
	"STATUS" NUMBER(3,0), 
	"DETAIL" VARCHAR2(200 BYTE), 
	"INSTANCE" VARCHAR2(200 BYTE)
   );
--------------------------------------------------------
--  DDL for Table SERVIZI
--------------------------------------------------------

  CREATE TABLE "APPIO_GW"."SERVIZI" 
   (	"ID" NUMBER(10,0), 
	"ID_SERVIZIO" VARCHAR2(50 BYTE), 
	"IDCOMUNE" VARCHAR2(6 BYTE), 
	"CODICECOMUNE" VARCHAR2(6 BYTE), 
	"SOFTWARE" VARCHAR2(2 BYTE), 
	"NOME_SERVIZIO" VARCHAR2(50 BYTE), 
	"DIPARTIMENTO" VARCHAR2(50 BYTE), 
	"ENTE" VARCHAR2(50 BYTE), 
	"CODICE_FISCALE_ENTE" VARCHAR2(25 BYTE), 
	"CHIAVE_PRIMARIA" VARCHAR2(32 BYTE), 
	"CHIAVE_SECONDARIA" VARCHAR2(32 BYTE), 
	"FK_TIPO_CONNETTORE" NUMBER(10,0), 
	"CLIENT_ID" VARCHAR2(32 BYTE), 
	"CLIENT_SECRET" VARCHAR2(32 BYTE), 
	"CLIENT_REGISTRATION_ID" VARCHAR2(32 BYTE)
   );
--------------------------------------------------------
--  DDL for Table STATO_MESSAGGIO
--------------------------------------------------------

  CREATE TABLE "APPIO_GW"."STATO_MESSAGGIO" 
   (	"ID" NUMBER(10,0), 
	"NOME" VARCHAR2(200 BYTE)
   );
--------------------------------------------------------
--  DDL for Table TIPO_CONNETTORE
--------------------------------------------------------

  CREATE TABLE "APPIO_GW"."TIPO_CONNETTORE" 
   (	"ID" NUMBER(10,0), 
	"NOME" VARCHAR2(200 BYTE)
   );
--------------------------------------------------------
--  DDL for Sequence HIBERNATE_SEQUENCE
--------------------------------------------------------

CREATE SEQUENCE  "APPIO_GW"."HIBERNATE_SEQUENCE"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE   ;
--------------------------------------------------------
--  DDL for Sequence MESSAGES_PKID_SEQUENCE
--------------------------------------------------------
--   CREATE SEQUENCE  "APPIO_GW"."MESSAGES_PKID_SEQUENCE"  MINVALUE 1 MAXVALUE 9999999999 INCREMENT BY 1 START WITH 1 NOCACHE  NOORDER  NOCYCLE   ;

--------------------------------------------------------
--  DDL for Index CLIENT_REGISTRATION_IDX
--------------------------------------------------------

  CREATE UNIQUE INDEX "APPIO_GW"."CLIENT_REGISTRATION_IDX" ON "APPIO_GW"."SERVIZI" ("CLIENT_REGISTRATION_ID");
--------------------------------------------------------
--  DDL for Index TIPO_CONNETTORE_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "APPIO_GW"."TIPO_CONNETTORE_PK" ON "APPIO_GW"."TIPO_CONNETTORE" ("ID");
--------------------------------------------------------
--  DDL for Index SERVIZI_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "APPIO_GW"."SERVIZI_PK" ON "APPIO_GW"."SERVIZI" ("ID");
--------------------------------------------------------
--  DDL for Index IDX_SERVIZI_02
--------------------------------------------------------

  CREATE UNIQUE INDEX "APPIO_GW"."IDX_SERVIZI_02" ON "APPIO_GW"."SERVIZI" ("ID_SERVIZIO");
--------------------------------------------------------
--  DDL for Index MESSAGGI_MESSAGE_ID_IDX
--------------------------------------------------------

  CREATE UNIQUE INDEX "APPIO_GW"."MESSAGGI_MESSAGE_ID_IDX" ON "APPIO_GW"."MESSAGGI" ("MESSAGE_ID");
--------------------------------------------------------
--  DDL for Index PROBLEM_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "APPIO_GW"."PROBLEM_PK" ON "APPIO_GW"."PROBLEM" ("MESSAGGI_ID");
--------------------------------------------------------
--  DDL for Index MESSAGGI_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "APPIO_GW"."MESSAGGI_PK" ON "APPIO_GW"."MESSAGGI" ("ID");
--------------------------------------------------------
--  DDL for Index STATO_MESSAGGIO_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "APPIO_GW"."STATO_MESSAGGIO_PK" ON "APPIO_GW"."STATO_MESSAGGIO" ("ID");
--------------------------------------------------------
--  DDL for Index UKDOA63WLB8QPNVK6YUCGOSQIWS
--------------------------------------------------------

  CREATE UNIQUE INDEX "APPIO_GW"."UKDOA63WLB8QPNVK6YUCGOSQIWS" ON "APPIO_GW"."SERVIZI" ("ID_SERVIZIO", "IDCOMUNE", "CODICECOMUNE", "SOFTWARE");
--------------------------------------------------------
--  DDL for Index IDX_SERVIZI_01
--------------------------------------------------------

  CREATE UNIQUE INDEX "APPIO_GW"."IDX_SERVIZI_01" ON "APPIO_GW"."SERVIZI" ("IDCOMUNE", "CODICECOMUNE", "SOFTWARE");
--------------------------------------------------------
--  DDL for Index IDX_MESSAGGI_01
--------------------------------------------------------

  CREATE UNIQUE INDEX "APPIO_GW"."IDX_MESSAGGI_01" ON "APPIO_GW"."MESSAGGI" ("ID_TRANSAZIONE");
--------------------------------------------------------
--  DDL for Index TIPO_CONNETTORE_NOME_IDX
--------------------------------------------------------

  CREATE UNIQUE INDEX "APPIO_GW"."TIPO_CONNETTORE_NOME_IDX" ON "APPIO_GW"."TIPO_CONNETTORE" ("NOME");
--------------------------------------------------------
--  Constraints for Table PROBLEM
--------------------------------------------------------

  ALTER TABLE "APPIO_GW"."PROBLEM" ADD CONSTRAINT "PROBLEM_PK" PRIMARY KEY ("MESSAGGI_ID");
  ALTER TABLE "APPIO_GW"."PROBLEM" MODIFY ("MESSAGGI_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table TIPO_CONNETTORE
--------------------------------------------------------

  ALTER TABLE "APPIO_GW"."TIPO_CONNETTORE" ADD CONSTRAINT "TIPO_CONNETTORE_NOME_IDX" UNIQUE ("NOME");
  ALTER TABLE "APPIO_GW"."TIPO_CONNETTORE" MODIFY ("NOME" NOT NULL ENABLE);
  ALTER TABLE "APPIO_GW"."TIPO_CONNETTORE" ADD CONSTRAINT "TIPO_CONNETTORE_PK" PRIMARY KEY ("ID");
--------------------------------------------------------
--  Constraints for Table MESSAGGI
--------------------------------------------------------

  ALTER TABLE "APPIO_GW"."MESSAGGI" ADD CONSTRAINT "MESSAGGI_MESSAGE_ID_IDX" UNIQUE ("MESSAGE_ID");
  ALTER TABLE "APPIO_GW"."MESSAGGI" MODIFY ("MESSAGE_ID" NOT NULL ENABLE);
  ALTER TABLE "APPIO_GW"."MESSAGGI" MODIFY ("TIME_TO_LIVE" NOT NULL ENABLE);
  ALTER TABLE "APPIO_GW"."MESSAGGI" MODIFY ("SOFTWARE" NOT NULL ENABLE);
  ALTER TABLE "APPIO_GW"."MESSAGGI" MODIFY ("CODICECOMUNE" NOT NULL ENABLE);
  ALTER TABLE "APPIO_GW"."MESSAGGI" MODIFY ("IDCOMUNE" NOT NULL ENABLE);
  ALTER TABLE "APPIO_GW"."MESSAGGI" ADD CONSTRAINT "MESSAGGI_PK" PRIMARY KEY ("ID");
--------------------------------------------------------
--  Constraints for Table STATO_MESSAGGIO
--------------------------------------------------------

  ALTER TABLE "APPIO_GW"."STATO_MESSAGGIO" ADD CONSTRAINT "STATO_MESSAGGIO_PK" PRIMARY KEY ("ID");
  ALTER TABLE "APPIO_GW"."STATO_MESSAGGIO" MODIFY ("NOME" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table SERVIZI
--------------------------------------------------------

  ALTER TABLE "APPIO_GW"."SERVIZI" ADD CONSTRAINT "CLIENT_REGISTRATION_IDX" UNIQUE ("CLIENT_REGISTRATION_ID");
  ALTER TABLE "APPIO_GW"."SERVIZI" MODIFY ("ID_SERVIZIO" NOT NULL ENABLE);
  ALTER TABLE "APPIO_GW"."SERVIZI" MODIFY ("SOFTWARE" NOT NULL ENABLE);
  ALTER TABLE "APPIO_GW"."SERVIZI" MODIFY ("CODICECOMUNE" NOT NULL ENABLE);
  ALTER TABLE "APPIO_GW"."SERVIZI" MODIFY ("IDCOMUNE" NOT NULL ENABLE);
  ALTER TABLE "APPIO_GW"."SERVIZI" ADD CONSTRAINT "UKDOA63WLB8QPNVK6YUCGOSQIWS" UNIQUE ("ID_SERVIZIO", "IDCOMUNE", "CODICECOMUNE", "SOFTWARE");
  ALTER TABLE "APPIO_GW"."SERVIZI" ADD CONSTRAINT "SERVIZI_PK" PRIMARY KEY ("ID");
--------------------------------------------------------
--  Ref Constraints for Table MESSAGGI
--------------------------------------------------------

  ALTER TABLE "APPIO_GW"."MESSAGGI" ADD CONSTRAINT "MESSAGGI_SERVIZI_FK" FOREIGN KEY ("FK_SERVIZI")
	  REFERENCES "APPIO_GW"."SERVIZI" ("ID") ENABLE;
  ALTER TABLE "APPIO_GW"."MESSAGGI" ADD CONSTRAINT "MESSAGGI_STATO_MESSAGGIO_FK" FOREIGN KEY ("FK_STATO_MESSAGGIO")
	  REFERENCES "APPIO_GW"."STATO_MESSAGGIO" ("ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table PROBLEM
--------------------------------------------------------

  ALTER TABLE "APPIO_GW"."PROBLEM" ADD CONSTRAINT "PROBLEM_MESSAGGI_FK" FOREIGN KEY ("MESSAGGI_ID")
	  REFERENCES "APPIO_GW"."MESSAGGI" ("ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table SERVIZI
--------------------------------------------------------

  ALTER TABLE "APPIO_GW"."SERVIZI" ADD CONSTRAINT "SERVIZI_TIPO_CONN_FK" FOREIGN KEY ("FK_TIPO_CONNETTORE")
	  REFERENCES "APPIO_GW"."TIPO_CONNETTORE" ("ID") ENABLE;
