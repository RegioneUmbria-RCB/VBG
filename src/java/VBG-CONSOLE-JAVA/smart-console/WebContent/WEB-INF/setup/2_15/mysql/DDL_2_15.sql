
ALTER TABLE STP_TIPOLOGIE_ENDO2  ADD FKIDAZIONE NUMERIC(4, 0) ;

ALTER TABLE STP_TIPOLOGIE_ENDO2  ADD CONSTRAINT FK_STP_TIPOL_ENDO2_AZIONI FOREIGN KEY (FKIDAZIONE) REFERENCES AZIONI(AZ_ID);


ALTER TABLE AMMINISTRAZIONI  ADD CODICE_CART VARCHAR(10);


ALTER TABLE AMMINISTRAZIONI ADD TIPOMOVIMENTO_CART VARCHAR(8) ;


ALTER TABLE AMMINISTRAZIONI ADD CONSTRAINT FK_AMMINIST_TIPO_MOV_CART FOREIGN KEY (IDCOMUNE,TIPOMOVIMENTO_CART) REFERENCES TIPIMOVIMENTO(IDCOMUNE,TIPOMOVIMENTO);

CREATE TABLE STP_MODALITA_APERTURA 
(
  ID VARCHAR(100) NOT NULL, 
  DESCRIZIONE VARCHAR(200) NOT NULL,
  TIPO_SCHEDA VARCHAR(4)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


ALTER TABLE STP_MODALITA_APERTURA ADD CONSTRAINT PK_STP_MOD_APERTURA PRIMARY KEY (ID);


ALTER TABLE NATURAENDO ADD FK_MOD_APERTURA_SCHEDA1 VARCHAR(100);
ALTER TABLE NATURAENDO ADD FK_MOD_APERTURA_SCHEDA2 VARCHAR(100) ;

ALTER TABLE NATURAENDO ADD CONSTRAINT FK_STP_MOD_APER_NATURA_ENDO2 FOREIGN KEY(FK_MOD_APERTURA_SCHEDA2) REFERENCES STP_MODALITA_APERTURA(ID); 

ALTER TABLE NATURAENDO ADD CONSTRAINT FK_STP_MOD_APER_NATURA_ENDO1 FOREIGN KEY(FK_MOD_APERTURA_SCHEDA1) REFERENCES STP_MODALITA_APERTURA(ID); 

ALTER TABLE PROTOCOLLO_MODALITAINVIO ADD IDCOMUNE VARCHAR(6);
UPDATE PROTOCOLLO_MODALITAINVIO SET IDCOMUNE = ICOMUNE;
ALTER TABLE PROTOCOLLO_MODALITAINVIO DROP PRIMARY KEY;
ALTER TABLE PROTOCOLLO_MODALITAINVIO MODIFY IDCOMUNE varchar(6) NOT NULL;
ALTER TABLE PROTOCOLLO_MODALITAINVIO ADD CONSTRAINT PK_PROTOCOLLO_MODALITAINVIO PRIMARY KEY (IDCOMUNE,CODICE);
ALTER TABLE PROTOCOLLO_MODALITAINVIO DROP COLUMN ICOMUNE;

ALTER TABLE STP_ENDO_TIPO2 ADD FK_MODAPERTURAID VARCHAR(100);
ALTER TABLE STP_ENDO_TIPO2 ADD CONSTRAINT FK_STPMODAPERTURA_ID FOREIGN KEY(FK_MODAPERTURAID) REFERENCES STP_MODALITA_APERTURA(ID);

DROP VIEW IF EXISTS VW_I_ATTIVITAAUTORIZZAZIONI;
CREATE ALGORITHM=MERGE SQL SECURITY DEFINER VIEW VW_I_ATTIVITAAUTORIZZAZIONI AS 
SELECT
  I_ATTIVITA.ID                      AS I_IDATTIVITA,
  AUTORIZZAZIONI.AUTORIZNUMERO       AS AUTORIZNUMERO,
  AUTORIZZAZIONI.AUTORIZDATA         AS AUTORIZDATA,
  AUTORIZZAZIONI.AUTORIZDATAREGISTR  AS AUTORIZDATAREGISTR,
  AUTORIZZAZIONI.AUTORIZRESPONSABILE AS AUTORIZRESPONSABILE,
  AUTORIZZAZIONI.FKIDREGISTRO        AS FKIDREGISTRO,
  AUTORIZZAZIONI.FKIDISTANZA         AS FKIDISTANZA,
  AUTORIZZAZIONI.IDCOMUNE            AS IDCOMUNE,
  AUTORIZZAZIONI.CODICEMOVIMENTO     AS CODICEMOVIMENTO,
  AUTORIZZAZIONI.ID                  AS ID,
  AUTORIZZAZIONI.DATA_CESSAZIONE     AS DATA_CESSAZIONE 
FROM ((I_ATTIVITA
    JOIN AUTORIZZAZIONI)
   JOIN ISTANZE)
WHERE ((ISTANZE.IDCOMUNE = I_ATTIVITA.IDCOMUNE)
       AND (ISTANZE.FK_IDI_ATTIVITA = I_ATTIVITA.ID)
       AND (AUTORIZZAZIONI.IDCOMUNE = ISTANZE.IDCOMUNE)
       AND (AUTORIZZAZIONI.FKIDISTANZA = ISTANZE.CODICEISTANZA));
CREATE OR REPLACE ALGORITHM=MERGE VIEW VW_ALBEROPROC
AS SELECT alberoproc.idcomune,
  alberoproc.software,
  alberoproc.sc_id,
  alberoproc.sc_codice,
  Alberoproc.Sc_Padre,
  concat(
	concat(
		concat(
			concat(
				concat(
					concat(
						concat(	
							concat(
								case isnull(Alberoproc_L1.Sc_Descrizione) when 1 then _utf8'' else concat(Alberoproc_L1.Sc_Descrizione, _utf8' - ' ) end),
									case isnull(Alberoproc_L2.Sc_Descrizione) when 1 then _utf8'' else concat(Alberoproc_L2.Sc_Descrizione, _utf8' - ' ) end),
										case isnull(Alberoproc_L3.Sc_Descrizione) when 1 then _utf8'' else concat(Alberoproc_L3.Sc_Descrizione, _utf8' - ' ) end),
											case isnull(Alberoproc_L4.Sc_Descrizione) when 1 then _utf8'' else concat(Alberoproc_L4.Sc_Descrizione, _utf8' - ' ) end),
												case isnull(Alberoproc_L5.Sc_Descrizione) when 1 then _utf8'' else concat(Alberoproc_L5.Sc_Descrizione, _utf8' - ' ) end),
													case isnull(Alberoproc_L6.Sc_Descrizione) when 1 then _utf8'' else concat(Alberoproc_L6.Sc_Descrizione, _utf8' - ' ) end),
														case isnull(Alberoproc_L7.Sc_Descrizione) when 1 then _utf8'' else concat(Alberoproc_L7.Sc_Descrizione, _utf8' - ' ) end),
															case isnull(Alberoproc_L8.Sc_Descrizione) when 1 then _utf8'' else concat(Alberoproc_L8.Sc_Descrizione, _utf8' - ' ) end)
	As Sc_Descrizione,
	concat(
		concat(
			concat(
				concat(
					concat(
						concat(	
							concat(
								case isnull(Alberoproc_L2.Sc_Descrizione) when 1 then _utf8'' else concat(Alberoproc_L1.Sc_Descrizione, _utf8' - ' ) end),
									case isnull(Alberoproc_L3.Sc_Descrizione) when 1 then _utf8'' else concat(Alberoproc_L2.Sc_Descrizione, _utf8' - ' ) end),
										case isnull(Alberoproc_L4.Sc_Descrizione) when 1 then _utf8'' else concat(Alberoproc_L3.Sc_Descrizione, _utf8' - ' ) end),
											case isnull(Alberoproc_L5.Sc_Descrizione) when 1 then _utf8'' else concat(Alberoproc_L4.Sc_Descrizione, _utf8' - ' ) end),
												case isnull(Alberoproc_L6.Sc_Descrizione) when 1 then _utf8'' else concat(Alberoproc_L5.Sc_Descrizione, _utf8' - ' ) end),
													case isnull(Alberoproc_L7.Sc_Descrizione) when 1 then _utf8'' else concat(Alberoproc_L6.Sc_Descrizione, _utf8' - ' ) end),
														case isnull(Alberoproc_L8.Sc_Descrizione) when 1 then _utf8'' else concat(Alberoproc_L7.Sc_Descrizione, _utf8' - ' ) end)
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
  ifNULL(alberoproc_l1.sc_ordine,0) AS ordine1,
  ifNULL(alberoproc_l2.sc_ordine,0) AS ordine2,
  ifNULL(alberoproc_l3.sc_ordine,0) AS ordine3,
  ifNULL(Alberoproc_L4.Sc_Ordine,0)  As Ordine4,
  ifNULL(Alberoproc_L5.Sc_Ordine,0)  As Ordine5,
  ifNULL(Alberoproc_L6.Sc_Ordine,0)  As Ordine6,
  ifNULL(Alberoproc_L7.Sc_Ordine,0)  As Ordine7,
  ifNULL(Alberoproc_L8.Sc_Ordine,0)  As Ordine8,	
	(case isnull(Alberoproc_L8.Fkcodicemercato) when 1 then 
		(case isnull(Alberoproc_L7.Fkcodicemercato) when 1 then 
			((case isnull(Alberoproc_L6.Fkcodicemercato) when 1 then 
				(((case isnull(Alberoproc_L5.Fkcodicemercato) when 1 then 
					((((case isnull(Alberoproc_L4.Fkcodicemercato) when 1 then 
						(((((case isnull(Alberoproc_L3.Fkcodicemercato) when 1 then 
							((((((case isnull(Alberoproc_L2.Fkcodicemercato) when 1 then 
								(((((((case isnull(Alberoproc_L1.Fkcodicemercato) when 1 then 
									(null)
								else
									Alberoproc_L1.Fkcodicemercato
								end)))))))
							else
								Alberoproc_L2.Fkcodicemercato
							end))))))
						else
							Alberoproc_L3.Fkcodicemercato
						end)))))
					else
						Alberoproc_L4.Fkcodicemercato
					end))))
				else
					Alberoproc_L5.Fkcodicemercato
				end)))
			else
				Alberoproc_L6.Fkcodicemercato
			end))
		else
			Alberoproc_L7.Fkcodicemercato
		end)
	else
		Alberoproc_L8.Fkcodicemercato
	end ) 
	As Fkcodicemercato,
	(case isnull(Alberoproc_L8.fkidmercatiuso) when 1 then 
		(case isnull(Alberoproc_L7.fkidmercatiuso) when 1 then 
			((case isnull(Alberoproc_L6.fkidmercatiuso) when 1 then 
				(((case isnull(Alberoproc_L5.fkidmercatiuso) when 1 then 
					((((case isnull(Alberoproc_L4.fkidmercatiuso) when 1 then 
						(((((case isnull(Alberoproc_L3.fkidmercatiuso) when 1 then 
							((((((case isnull(Alberoproc_L2.fkidmercatiuso) when 1 then 
								(((((((case isnull(Alberoproc_L1.fkidmercatiuso) when 1 then 
									(null)
								else
									Alberoproc_L1.fkidmercatiuso
								end)))))))
							else
								Alberoproc_L2.fkidmercatiuso
							end))))))
						else
							Alberoproc_L3.fkidmercatiuso
						end)))))
					else
						Alberoproc_L4.fkidmercatiuso
					end))))
				else
					Alberoproc_L5.fkidmercatiuso
				end)))
			else
				Alberoproc_L6.fkidmercatiuso
			end))
		else
			Alberoproc_L7.fkidmercatiuso
		end)
	else
		Alberoproc_L8.fkidmercatiuso
	end)
	AS fkidmercatiuso,
	alberoproc.sc_attivo,
	alberoproc.progressivoistanze
From Alberoproc
	Left Join Alberoproc Alberoproc_L1 On Alberoproc_L1.Idcomune = Alberoproc.Idcomune And Alberoproc_L1.Software = Alberoproc.Software And Alberoproc_L1.Sc_Codice = SUBSTRING(Alberoproc.Sc_Codice, 1, 2) And Length(Alberoproc_L1.Sc_Codice)=2
	Left Join Alberoproc Alberoproc_L2 On Alberoproc_L2.Idcomune = Alberoproc_L1.Idcomune And Alberoproc_L2.Software = Alberoproc_L1.Software And Alberoproc_L2.Sc_Codice = SUBSTRING(Alberoproc.Sc_Codice, 1, 4) And Length(Alberoproc_L2.Sc_Codice)=4
	Left Join Alberoproc Alberoproc_L3 On Alberoproc_L3.Idcomune = Alberoproc_L2.Idcomune And Alberoproc_L3.Software = Alberoproc_L2.Software And Alberoproc_L3.Sc_Codice = SUBSTRING(Alberoproc.Sc_Codice, 1, 6) And Length(Alberoproc_L3.Sc_Codice)=6
	Left Join Alberoproc Alberoproc_L4 On Alberoproc_L4.Idcomune = Alberoproc_L3.Idcomune And Alberoproc_L4.Software = Alberoproc_L3.Software And Alberoproc_L4.Sc_Codice = SUBSTRING(Alberoproc.Sc_Codice, 1, 8) And Length(Alberoproc_L4.Sc_Codice)=8
	Left Join Alberoproc Alberoproc_L5 On Alberoproc_L5.Idcomune = Alberoproc_L4.Idcomune And Alberoproc_L5.Software = Alberoproc_L4.Software And Alberoproc_L5.Sc_Codice = SUBSTRING(Alberoproc.Sc_Codice, 1, 10) And Length(Alberoproc_L5.Sc_Codice)=10
	Left Join Alberoproc Alberoproc_L6 On Alberoproc_L6.Idcomune = Alberoproc_L5.Idcomune And Alberoproc_L6.Software = Alberoproc_L5.Software And Alberoproc_L6.Sc_Codice = SUBSTRING(Alberoproc.Sc_Codice, 1, 12) And Length(Alberoproc_L6.Sc_Codice)=12
	Left Join Alberoproc Alberoproc_L7 On Alberoproc_L7.Idcomune = Alberoproc_L6.Idcomune And Alberoproc_L7.Software = Alberoproc_L6.Software And Alberoproc_L7.Sc_Codice = SUBSTRING(Alberoproc.Sc_Codice, 1, 14) And Length(Alberoproc_L7.Sc_Codice)=14
	Left Join Alberoproc Alberoproc_L8 On Alberoproc_L8.Idcomune = Alberoproc_L7.Idcomune And Alberoproc_L8.Software = Alberoproc_L7.Software And Alberoproc_L8.Sc_Codice = SUBSTRING(Alberoproc.Sc_Codice, 1, 16) And Length(Alberoproc_L8.Sc_Codice)=16;	
	
ALTER TABLE FO_DOMANDE MODIFY DATA_ULTIMA_MODIFICA DATETIME;

ALTER TABLE DOCUMENTIISTANZA MODIFY DOCUMENTO VARCHAR(4000) NOT NULL;

ALTER TABLE MOVIMENTI ADD NUMPROT_MITTENTE VARCHAR(30);
ALTER TABLE MOVIMENTI ADD DATA_PROT_MITTENTE DATETIME;

alter table TMP_ST_ONERI modify FK_RCO_ID NUMERIC(10); 
alter table TMP_ST_ONERI modify FK_CO_ID NUMERIC(10);

ALTER TABLE ELENCHIPROFESSIONALIBASE MODIFY EP_DESCRIZIONE VARCHAR(50);

ALTER TABLE MERCATI ADD FLAG_CONSORZIO NUMERIC(1,0);
CREATE TABLE MERCATI_CONSORZI(
IDCOMUNE VARCHAR(6) NOT NULL,
ID NUMERIC(4,0) NOT NULL,
FK_CODICE_MERCATO NUMERIC(4,0) NOT NULL,
FK_ANAG_CONSORZIO NUMERIC(6,0) NOT NULL,
DATA_INIZIO_ESERCIZIO DATETIME,
DATA_FINE_ESERCIZIO DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE MERCATI_CONSORZI ADD CONSTRAINT PK_MERCATICONSORZI PRIMARY KEY(IDCOMUNE, ID);
ALTER TABLE MERCATI_CONSORZI ADD CONSTRAINT FK_MERCATICONSORZI_MERC FOREIGN KEY (IDCOMUNE,FK_CODICE_MERCATO) REFERENCES MERCATI (IDCOMUNE,CODICEMERCATO);
ALTER TABLE MERCATI_CONSORZI ADD CONSTRAINT FK_MERCATICONSORZI_ANAG FOREIGN KEY (IDCOMUNE,FK_ANAG_CONSORZIO) REFERENCES ANAGRAFE (IDCOMUNE,CODICEANAGRAFE);

ALTER TABLE MERCATI_CONTI ADD FLAG_IMPORTOMENSILE NUMERIC(1,0);
ALTER TABLE MERCATI_CONTI ADD PERCENTUALE_CONSORZIO NUMERIC(5,2);

ALTER TABLE MERCATI_D_CONTI ADD FLAG_IMPORTOMENSILE NUMERIC(1,0);
ALTER TABLE MERCATI_D_CONTI ADD PERCENTUALE_CONSORZIO NUMERIC(5,2);

ALTER TABLE ELENCHIPROFESSIONALIBASE ADD EP_ATRIBCODICE NUMERIC(4,0);


CREATE TABLE FO_ARJ_STEPS_TESTATA
(
    IDCOMUNE                       VARCHAR(6),
    ID                             NUMERIC(6,0),
    SOFTWARE                       VARCHAR(2),
    DESCRIZIONE                    VARCHAR(4000),
    CONSTRAINT FO_ARJ_STEPS_TESTATA_PK PRIMARY KEY (IDCOMUNE, ID),
    CONSTRAINT FK_FOARJSTEPSTESTATA_SOFTWARE FOREIGN KEY (SOFTWARE) REFERENCES SOFTWARE (CODICE)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE FO_ARJ_STEPS
(
    IDCOMUNE                       VARCHAR(6),
    ID                             NUMERIC(6,0),
    NOME                           VARCHAR(200),
    TITOLO                         VARCHAR(400),
    DESCRIZIONE                    VARCHAR(4000),
    ORDINE                         NUMERIC(2,0),
    ABILITATO                      NUMERIC(1,0),
      FK_FO_ARJ_STEPS_TESTATA      NUMERIC(6,0),
    CONSTRAINT FO_ARJ_STEPS_PK PRIMARY KEY (IDCOMUNE, ID) ,
    CONSTRAINT FK_FOARJSTEPS_FOARJSTEPSTESTA FOREIGN KEY (IDCOMUNE, FK_FO_ARJ_STEPS_TESTATA) REFERENCES FO_ARJ_STEPS_TESTATA (IDCOMUNE, ID) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE FO_ARJ_STEPS_PARAMS
(
    IDCOMUNE                       VARCHAR(6),
    ID                             NUMERIC(6,0),
    CHIAVE                         VARCHAR(200),
    VALORE                         VARCHAR(400),
      FK_FO_ARJ_STEPS              NUMERIC(6,0),
    CONSTRAINT FO_ARJ_STEPS_PARAMS_PK PRIMARY KEY (IDCOMUNE, ID) ,
    CONSTRAINT FK_FOARJSTEPSPARAMS_FOARJSTEPS FOREIGN KEY (IDCOMUNE, FK_FO_ARJ_STEPS) REFERENCES FO_ARJ_STEPS (IDCOMUNE, ID) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE FO_ARJ_DOMANDE
(
    IDCOMUNE                       VARCHAR(6),
    ID                             NUMERIC(6,0),
    SOFTWARE                       VARCHAR(2),
    CODICEANAGRAFE                 NUMERIC(6,0),
    CODICEOGGETTO                  NUMERIC(10,0),
    CODICEISTANZA                  NUMERIC(6,0),
    DATA_INVIO                     DATE,
    DATA_ULTIMA_MODIFICA           DATE,
    ID_DOMANDA                     VARCHAR(50),
    STEP                                 NUMERIC(2,0),
    CONSTRAINT FO_ARJ_DOMANDE_PK PRIMARY KEY (IDCOMUNE, ID) ,
    CONSTRAINT FK_FOARJDOMANDE_OGG FOREIGN KEY (IDCOMUNE, CODICEOGGETTO) REFERENCES OGGETTI (IDCOMUNE, CODICEOGGETTO) ,
    CONSTRAINT FK_FOARJDOMANDE_ANAGRAFE FOREIGN KEY (IDCOMUNE, CODICEANAGRAFE) REFERENCES ANAGRAFE (IDCOMUNE, CODICEANAGRAFE) ,
    CONSTRAINT FK_FOARJDOMANDE_SOFTWARE FOREIGN KEY (SOFTWARE) REFERENCES SOFTWARE (CODICE)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


ALTER TABLE FO_ARJ_DOMANDE ADD CODICEOGGETTO_RIEPILOGO NUMERIC(10,0);
ALTER TABLE FO_ARJ_DOMANDE ADD CONSTRAINT FK_FOARJDOMANDE_OGGRIEPI_OGG FOREIGN KEY (IDCOMUNE, CODICEOGGETTO_RIEPILOGO) REFERENCES OGGETTI (IDCOMUNE, CODICEOGGETTO);

