UPDATE mercatipresenze_d SET mercatipresenze_d.FK_AUTORIZZAZIONI_ID = (SELECT FK_IDAUT_ATTUALE FROM autorizzazioni_concessioni
      WHERE 
	autorizzazioni_concessioni.idcomune=mercatipresenze_d.idcomune AND
	autorizzazioni_concessioni.FK_IDAUT_COLLEGATA=mercatipresenze_d.FK_AUTORIZZAZIONI_ID  ) WHERE  EXISTS (SELECT 1
             FROM autorizzazioni_concessioni
      WHERE 
	autorizzazioni_concessioni.idcomune=mercatipresenze_d.idcomune AND
	autorizzazioni_concessioni.FK_IDAUT_COLLEGATA=mercatipresenze_d.FK_AUTORIZZAZIONI_ID);

ALTER TABLE autorizzazioni_subentri ADD codiceoccupante NUMBER(6,0); 
	
ALTER TABLE autorizzazioni ADD codiceoccupante NUMBER(6,0);

ALTER TABLE autorizzazioni_subentri ADD CONSTRAINT fk_autsub_ana_occupante FOREIGN KEY(idcomune,codiceoccupante) REFERENCES anagrafe(idcomune,codiceanagrafe);
ALTER TABLE autorizzazioni ADD CONSTRAINT fk_aut_ana_occupante FOREIGN KEY(idcomune,codiceoccupante) REFERENCES anagrafe(idcomune,codiceanagrafe);

CREATE TABLE MERCATI_D_DISABILITATI (IDCOMUNE VARCHAR2(6 CHAR) NOT NULL, ID NUMBER(10,0) NOT NULL, DALLA_DATA DATE NOT NULL, ALLA_DATA DATE NOT NULL, FK_CODICEMERCATO NUMBER(4,0) NOT NULL, FK_IDPOSTEGGIO NUMBER(6,0) NOT NULL, NOTE VARCHAR2(4000 CHAR) NOT NULL );
ALTER TABLE MERCATI_D_DISABILITATI ADD CONSTRAINT PK_MERCDDISABILITATI PRIMARY KEY(IDCOMUNE,ID);
ALTER TABLE MERCATI_D_DISABILITATI ADD CONSTRAINT FK_MERCDDISABILITATI_001 FOREIGN KEY(IDCOMUNE,FK_CODICEMERCATO) REFERENCES MERCATI(IDCOMUNE,CODICEMERCATO);
ALTER TABLE MERCATI_D_DISABILITATI ADD CONSTRAINT FK_MERCDDISABILITATI_002 FOREIGN KEY(IDCOMUNE,FK_IDPOSTEGGIO) REFERENCES MERCATI_D(IDCOMUNE,IDPOSTEGGIO);
CREATE INDEX IDX_MERCDDISABILITATI_001 ON MERCATI_D_DISABILITATI (IDCOMUNE,FK_CODICEMERCATO);
CREATE INDEX IDX_MERCDDISABILITATI_002 ON MERCATI_D_DISABILITATI (IDCOMUNE,FK_IDPOSTEGGIO);
CREATE INDEX IDX_MERCDDISABILITATI_003 ON MERCATI_D_DISABILITATI (IDCOMUNE,FK_CODICEMERCATO,DALLA_DATA,ALLA_DATA);

ALTER TABLE tipologiaregistri ADD flag_manifestazioni NUMBER(1,0) DEFAULT 0 NOT NULL;


MERGE INTO tipologiaregistri t1
USING
(     SELECT
idcomune,
tr_id,
rnum
  FROM
(
SELECT
tipologiaregistri.idcomune,
tipologiaregistri.tr_id,
ROW_NUMBER() OVER(
PARTITION BY tipologiaregistri.idcomune, tipologiaregistri.tr_id ORDER BY tipologiaregistri.idcomune ASC, tipologiaregistri.tr_id ASC
) AS rnum                                
FROM
tipologiaregistri
 INNER JOIN autorizzazioni ON
                    tipologiaregistri.idcomune = autorizzazioni.idcomune AND
                    tipologiaregistri.tr_id = autorizzazioni.FKIDREGISTRO    
                  INNER JOIN
                    autorizzazioni_concessioni ON
                    autorizzazioni_concessioni.idcomune = autorizzazioni.idcomune AND
                    autorizzazioni_concessioni.FK_IDAUT_ATTUALE = autorizzazioni.id WHERE tipologiaregistri.flag_manifestazioni = 0

) where rnum=1
) t2

ON (
        t1.idcomune = t2.idcomune
        AND t1.tr_id = t2.tr_id
)
WHEN MATCHED THEN
UPDATE SET t1.flag_manifestazioni=1;


MERGE INTO tipologiaregistri t1
USING
(     SELECT
idcomune,
tr_id,
rnum
  FROM
(
SELECT
tipologiaregistri.idcomune,
tipologiaregistri.tr_id,
ROW_NUMBER() OVER(
PARTITION BY tipologiaregistri.idcomune, tipologiaregistri.tr_id ORDER BY tipologiaregistri.idcomune ASC, tipologiaregistri.tr_id ASC
) AS rnum                                
FROM
tipologiaregistri
 INNER JOIN autorizzazioni ON
                    tipologiaregistri.idcomune = autorizzazioni.idcomune AND
                    tipologiaregistri.tr_id = autorizzazioni.FKIDREGISTRO    
                  INNER JOIN
                    autorizzazioni_concessioni ON
                    autorizzazioni_concessioni.idcomune = autorizzazioni.idcomune AND
                    autorizzazioni_concessioni.FK_IDAUT_COLLEGATA = autorizzazioni.id WHERE tipologiaregistri.flag_manifestazioni = 0

) where rnum=1
) t2

ON (
        t1.idcomune = t2.idcomune
        AND t1.tr_id = t2.tr_id
)
WHEN MATCHED THEN
UPDATE SET t1.flag_manifestazioni=1;
 

MERGE INTO tipologiaregistri t1
USING
(     SELECT
idcomune,
tr_id,
rnum
  FROM
(
SELECT
tipologiaregistri.idcomune,
tipologiaregistri.tr_id,
ROW_NUMBER() OVER(
PARTITION BY tipologiaregistri.idcomune, tipologiaregistri.tr_id ORDER BY tipologiaregistri.idcomune ASC, tipologiaregistri.tr_id ASC
) AS rnum                                
FROM
tipologiaregistri
    INNER JOIN autorizzazioni ON
tipologiaregistri.idcomune = autorizzazioni.idcomune AND
tipologiaregistri.tr_id = autorizzazioni.FKIDREGISTRO    
 INNER JOIN
mercatipresenze_d ON
mercatipresenze_d.idcomune = autorizzazioni.idcomune AND mercatipresenze_d.FK_AUTORIZZAZIONI_ID = autorizzazioni.id WHERE tipologiaregistri.flag_manifestazioni = 0

) where rnum=1
) t2

ON (
        t1.idcomune = t2.idcomune
        AND t1.tr_id = t2.tr_id
)
WHEN MATCHED THEN
UPDATE SET t1.flag_manifestazioni=1;
 

MERGE INTO tipologiaregistri t1
USING
(     SELECT
idcomune,
tr_id,
rnum
  FROM
(
SELECT
tipologiaregistri.idcomune,
tipologiaregistri.tr_id,
ROW_NUMBER() OVER(
PARTITION BY tipologiaregistri.idcomune, tipologiaregistri.tr_id ORDER BY tipologiaregistri.idcomune ASC, tipologiaregistri.tr_id ASC
) AS rnum                                
FROM
tipologiaregistri
    INNER JOIN autorizzazioni ON
tipologiaregistri.idcomune = autorizzazioni.idcomune AND
tipologiaregistri.tr_id = autorizzazioni.FKIDREGISTRO    
 INNER JOIN
mercatipresenze_d ON
mercatipresenze_d.idcomune = autorizzazioni.idcomune AND mercatipresenze_d.AUT_CONCESSIONARIO = autorizzazioni.id WHERE tipologiaregistri.flag_manifestazioni = 0

) where rnum=1
) t2

ON (
        t1.idcomune = t2.idcomune
        AND t1.tr_id = t2.tr_id
)
WHEN MATCHED THEN
UPDATE SET t1.flag_manifestazioni=1; 



UPDATE AUTORIZZAZIONI SET
AUTORIZZAZIONI.CODICEOCCUPANTE = (SELECT  COALESCE(istanze.codicetitolarelegale, istanze.CODICERICHIEDENTE) AS codiceoccupante
  FROM ISTANZE
  WHERE AUTORIZZAZIONI.idcomune = ISTANZE.idcomune and
        AUTORIZZAZIONI.FKIDISTANZA = ISTANZE.codiceistanza ) WHERE AUTORIZZAZIONI.CODICEOCCUPANTE is null AND EXISTS ( SELECT 1 FROM ISTANZE
  WHERE 
 AUTORIZZAZIONI.idcomune    = ISTANZE.idcomune and
 AUTORIZZAZIONI.FKIDISTANZA = ISTANZE.codiceistanza );


UPDATE AUTORIZZAZIONI_SUBENTRI SET
AUTORIZZAZIONI_SUBENTRI.CODICEOCCUPANTE = (SELECT  COALESCE(istanze.codicetitolarelegale, istanze.CODICERICHIEDENTE) AS codiceoccupante
  FROM ISTANZE
  WHERE AUTORIZZAZIONI_SUBENTRI.idcomune = ISTANZE.idcomune and
        AUTORIZZAZIONI_SUBENTRI.FKIDISTANZA = ISTANZE.codiceistanza ) WHERE AUTORIZZAZIONI_SUBENTRI.CODICEOCCUPANTE is null AND EXISTS ( SELECT 1 FROM ISTANZE
  WHERE 
 AUTORIZZAZIONI_SUBENTRI.idcomune    = ISTANZE.idcomune and
 AUTORIZZAZIONI_SUBENTRI.FKIDISTANZA = ISTANZE.codiceistanza );

ALTER TABLE mercati_configurazione ADD fk_caus_acq_riottenim   number(4,0);
ALTER TABLE mercati_configurazione ADD fk_caus_cess_riottenim   number(4,0);
ALTER TABLE mercati_configurazione ADD CONSTRAINT fk_merconf_conccaus_riotacq FOREIGN KEY(idcomune,fk_caus_acq_riottenim) REFERENCES concessionicausali(idcomune,codicecausale);
ALTER TABLE mercati_configurazione ADD CONSTRAINT fk_merconf_conccaus_riotcess FOREIGN KEY(idcomune,fk_caus_cess_riottenim) REFERENCES concessionicausali(idcomune,codicecausale);

ALTER TABLE mercati_configurazione ADD fk_caus_cessaz_sistema NUMBER(4,0);
ALTER TABLE mercati_configurazione ADD CONSTRAINT fk_merconf_conccaus_cesistema FOREIGN KEY(idcomune,fk_caus_cessaz_sistema) REFERENCES concessionicausali(idcomune,codicecausale);
