UPDATE mercatipresenze_d
        INNER JOIN
    autorizzazioni_concessioni
      ON 
	autorizzazioni_concessioni.idcomune=mercatipresenze_d.idcomune AND
	autorizzazioni_concessioni.FK_IDAUT_COLLEGATA=mercatipresenze_d.FK_AUTORIZZAZIONI_ID 
 SET    mercatipresenze_d.FK_AUTORIZZAZIONI_ID = autorizzazioni_concessioni.FK_IDAUT_ATTUALE
    WHERE mercatipresenze_d.FK_AUTORIZZAZIONI_ID=autorizzazioni_concessioni.FK_IDAUT_COLLEGATA;
    
ALTER TABLE autorizzazioni_subentri ADD codiceoccupante NUMERIC(6,0); 
ALTER TABLE autorizzazioni ADD codiceoccupante NUMERIC(6,0);

ALTER TABLE autorizzazioni_subentri ADD CONSTRAINT fk_autsub_ana_occupante FOREIGN KEY(idcomune,codiceoccupante) REFERENCES anagrafe(idcomune,codiceanagrafe);
ALTER TABLE autorizzazioni ADD CONSTRAINT fk_aut_ana_occupante FOREIGN KEY(idcomune,codiceoccupante) REFERENCES anagrafe(idcomune,codiceanagrafe);

CREATE TABLE MERCATI_D_DISABILITATI (IDCOMUNE VARCHAR(6) NOT NULL, ID DECIMAL(10,0) NOT NULL, DALLA_DATA DATE NOT NULL, ALLA_DATA DATE NOT NULL, FK_CODICEMERCATO DECIMAL(4,0) NOT NULL, FK_IDPOSTEGGIO DECIMAL(6,0) NOT NULL, NOTE VARCHAR(4000) NOT NULL ) ENGINE=INNODB DEFAULT CHARSET=utf8;
ALTER TABLE MERCATI_D_DISABILITATI ADD CONSTRAINT PK_MERCDDISABILITATI PRIMARY KEY(IDCOMUNE,ID);
ALTER TABLE MERCATI_D_DISABILITATI ADD CONSTRAINT FK_MERCDDISABILITATI_001 FOREIGN KEY(IDCOMUNE,FK_CODICEMERCATO) REFERENCES MERCATI(IDCOMUNE,CODICEMERCATO);
ALTER TABLE MERCATI_D_DISABILITATI ADD CONSTRAINT FK_MERCDDISABILITATI_002 FOREIGN KEY(IDCOMUNE,FK_IDPOSTEGGIO) REFERENCES MERCATI_D(IDCOMUNE,IDPOSTEGGIO);
CREATE INDEX IDX_MERCDDISABILITATI_001 ON MERCATI_D_DISABILITATI (IDCOMUNE,FK_CODICEMERCATO);
CREATE INDEX IDX_MERCDDISABILITATI_002 ON MERCATI_D_DISABILITATI (IDCOMUNE,FK_IDPOSTEGGIO);
CREATE INDEX IDX_MERCDDISABILITATI_003 ON MERCATI_D_DISABILITATI (IDCOMUNE,FK_CODICEMERCATO,DALLA_DATA,ALLA_DATA);


ALTER TABLE tipologiaregistri ADD flag_manifestazioni NUMERIC(1,0) DEFAULT 0 NOT NULL;


UPDATE tipologiaregistri
   INNER JOIN autorizzazioni ON
    tipologiaregistri.idcomune = autorizzazioni.idcomune AND
    tipologiaregistri.tr_id = autorizzazioni.FKIDREGISTRO    
  INNER JOIN
    autorizzazioni_concessioni ON
    autorizzazioni_concessioni.idcomune = autorizzazioni.idcomune AND
    autorizzazioni_concessioni.FK_IDAUT_ATTUALE = autorizzazioni.id  SET  tipologiaregistri.flag_manifestazioni=1
 WHERE tipologiaregistri.flag_manifestazioni=0;
 
UPDATE tipologiaregistri
   INNER JOIN autorizzazioni ON
    tipologiaregistri.idcomune = autorizzazioni.idcomune AND
    tipologiaregistri.tr_id = autorizzazioni.FKIDREGISTRO    
  INNER JOIN
    autorizzazioni_concessioni ON
    autorizzazioni_concessioni.idcomune = autorizzazioni.idcomune AND
    autorizzazioni_concessioni.FK_IDAUT_COLLEGATA = autorizzazioni.id  SET  tipologiaregistri.flag_manifestazioni=1
 WHERE tipologiaregistri.flag_manifestazioni=0;
 

 
 UPDATE tipologiaregistri
   INNER JOIN autorizzazioni ON
    tipologiaregistri.idcomune = autorizzazioni.idcomune AND
    tipologiaregistri.tr_id = autorizzazioni.FKIDREGISTRO    
  INNER JOIN
    mercatipresenze_d ON
    mercatipresenze_d.idcomune = autorizzazioni.idcomune AND
    mercatipresenze_d.FK_AUTORIZZAZIONI_ID = autorizzazioni.id  SET  tipologiaregistri.flag_manifestazioni=1
 WHERE tipologiaregistri.flag_manifestazioni=0;
 

 
 UPDATE tipologiaregistri
   INNER JOIN autorizzazioni ON
    tipologiaregistri.idcomune = autorizzazioni.idcomune AND
    tipologiaregistri.tr_id = autorizzazioni.FKIDREGISTRO    
  INNER JOIN
    mercatipresenze_d ON
    mercatipresenze_d.idcomune = autorizzazioni.idcomune AND
    mercatipresenze_d.AUT_CONCESSIONARIO = autorizzazioni.id  SET  tipologiaregistri.flag_manifestazioni=1
 WHERE tipologiaregistri.flag_manifestazioni=0;


UPDATE autorizzazioni 
	INNER JOIN istanze ON
autorizzazioni.idcomune=istanze.idcomune AND autorizzazioni.FKIDISTANZA=istanze.codiceistanza
SET autorizzazioni.codiceoccupante=COALESCE(istanze.codicetitolarelegale, istanze.CODICERICHIEDENTE) WHERE autorizzazioni.codiceoccupante IS NULL;


UPDATE autorizzazioni_subentri 
	INNER JOIN istanze ON
autorizzazioni_subentri.idcomune=istanze.idcomune AND autorizzazioni_subentri.FKIDISTANZA=istanze.codiceistanza
SET autorizzazioni_subentri.codiceoccupante=COALESCE(istanze.codicetitolarelegale, istanze.CODICERICHIEDENTE) WHERE autorizzazioni_subentri.codiceoccupante IS NULL;


ALTER TABLE mercati_configurazione ADD fk_caus_acq_riottenim   DECIMAL(4,0);
ALTER TABLE mercati_configurazione ADD fk_caus_cess_riottenim   DECIMAL(4,0);
ALTER TABLE mercati_configurazione ADD CONSTRAINT fk_merconf_conccaus_riotacq FOREIGN KEY(idcomune,fk_caus_acq_riottenim) REFERENCES concessionicausali(idcomune,codicecausale);
ALTER TABLE mercati_configurazione ADD CONSTRAINT fk_merconf_conccaus_riotcess FOREIGN KEY(idcomune,fk_caus_cess_riottenim) REFERENCES concessionicausali(idcomune,codicecausale);

ALTER TABLE mercati_configurazione ADD fk_caus_cessaz_sistema DECIMAL(4,0);
ALTER TABLE mercati_configurazione ADD CONSTRAINT fk_merconf_conccaus_cesistema FOREIGN KEY(idcomune,fk_caus_cessaz_sistema) REFERENCES concessionicausali(idcomune,codicecausale);
