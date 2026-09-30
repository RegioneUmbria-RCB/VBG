ALTER TABLE conti MODIFY CODICESOTTOCONTO VARCHAR(50);

ALTER TABLE commissioniedilizie_t ADD CONSTRAINT FK_COMMEDIT_CONVOCAZIONE FOREIGN KEY(IDCOMUNE,IDCONVOCAZIONE) REFERENCES commedilizie_convocazioni(IDCOMUNE,ID);

ALTER TABLE MERCATI_CONFIGURAZIONE ADD GRAD_INTERVALLO_DATE VARCHAR(50);

ALTER TABLE commissioniedilizie_t ADD odg VARCHAR(4000);

ALTER TABLE commissioniedilizie_t MODIFY note VARCHAR(4000);

ALTER TABLE commedilizie_allegati MODIFY note VARCHAR(4000);

CREATE TABLE commedilizie_tipol_ruoli (
  idcomune VARCHAR(6) NOT NULL,
  fk_commeditipo_id DECIMAL(4,0) NOT NULL,
  fk_ruoli_id DECIMAL(4,0) NOT NULL
) ENGINE=INNODB DEFAULT CHARSET=utf8;



ALTER TABLE commedilizie_tipol_ruoli ADD CONSTRAINT commedilizie_tipol_ruoli_PK primary key (idcomune,fk_commeditipo_id,fk_ruoli_id);

ALTER TABLE commedilizie_tipol_ruoli ADD CONSTRAINT  fk_commeditipor_tipologie foreign key (idcomune, fk_commeditipo_id) references commedilizie_tipologie (idcomune, codcommtipologia);

ALTER TABLE commedilizie_tipol_ruoli ADD CONSTRAINT  fk_commeditipo_ruoli foreign key (idcomune, fk_ruoli_id) references ruoli (idcomune, id) ;

ALTER TABLE BANDI MODIFY FK_AP_SCID NUMERIC(10,0); 

ALTER TABLE BANDI ADD CONSTRAINT FK_BANDI_ALBEROPROC FOREIGN KEY(IDCOMUNE,FK_AP_SCID) REFERENCES ALBEROPROC(IDCOMUNE,SC_ID);

ALTER TABLE BANDI ADD CONSTRAINT FK_BANDI_TIPIBANDO FOREIGN KEY(IDCOMUNE,FK_TB_ID) REFERENCES TIPIBANDO(IDCOMUNE,ID);


CREATE TABLE commedilizie_pareri_tmov (
  idcomune VARCHAR(6) NOT NULL,
  fk_commedpareri_id DECIMAL(4,0) NOT NULL,
  software VARCHAR(2) NOT NULL,
  tipomovimento VARCHAR(8) NOT NULL

 ) ENGINE=INNODB DEFAULT CHARSET=utf8;

ALTER TABLE commedilizie_pareri_tmov ADD CONSTRAINT pk_commediliziepareritmov PRIMARY KEY(idcomune, fk_commedpareri_id, software);

ALTER TABLE commedilizie_pareri_tmov ADD CONSTRAINT fk_commedpareritmov_mov FOREIGN KEY  (IDCOMUNE, TIPOMOVIMENTO) REFERENCES tipimovimento (IDCOMUNE, TIPOMOVIMENTO);

ALTER TABLE commedilizie_pareri_tmov ADD CONSTRAINT fk_commedpareritmov_tpar FOREIGN KEY (idcomune,fk_commedpareri_id) REFERENCES commedilizie_tipopareri(idcomune,codice);

ALTER TABLE commedilizie_pareri_tmov ADD CONSTRAINT fk_commedpareritmov_soft FOREIGN KEY (software) REFERENCES software(codice);



ALTER TABLE tipimovimento_dis ADD CONSTRAINT FK_TIPIMOVDIS_TIPIMOV FOREIGN KEY (IDCOMUNE,TIPOMOVIMENTO) REFERENCES TIPIMOVIMENTO(IDCOMUNE,TIPOMOVIMENTO);


ALTER TABLE PAY_REGISTRAZIONI_CAUSALI ADD MAPPATURA_CLIENT VARCHAR(50); 

UPDATE PAY_REGISTRAZIONI_CAUSALI SET CODICE_VERSAMENTO='CONFIGURAZIONE NON VALIDA' WHERE CODICE_VERSAMENTO IS NULL;
UPDATE PAY_REGISTRAZIONI_CAUSALI SET MAPPATURA_CLIENT = CODICE_VERSAMENTO WHERE MAPPATURA_CLIENT IS NULL;

ALTER TABLE PAY_REGISTRAZIONI_CAUSALI MODIFY MAPPATURA_CLIENT VARCHAR(50) NOT NULL; 

CREATE INDEX IDX_PAYREGISTRAZIONICAUSALI_01 ON PAY_REGISTRAZIONI_CAUSALI(IDCOMUNE,MAPPATURA_CLIENT);

ALTER TABLE pay_dettaglio_importi ADD FK_REG_CAUSALE DECIMAL(6,0);

ALTER TABLE pay_dettaglio_importi ADD CONSTRAINT FK_PAYDETTIMP_PAYREGCAUS FOREIGN KEY (IDCOMUNE, FK_REG_CAUSALE) REFERENCES pay_registrazioni_causali(idcomune,id);

ALTER TABLE pay_registrazioni_contabili MODIFY FK_CAUSALE_REG DECIMAL(6,0) NULL;

ALTER TABLE pay_regcausali_parametri MODIFY CHIAVE  VARCHAR(100);

UPDATE PAY_DETTAGLIO_IMPORTI SET FK_REG_CAUSALE = 
	( SELECT FK_CAUSALE_REG FROM pay_registrazioni_contabili 
		inner join pay_posizioni_debitorie on 		
		 pay_registrazioni_contabili.idcomune=pay_posizioni_debitorie.idcomune and
		 pay_registrazioni_contabili.id=pay_posizioni_debitorie.FK_REGISTRAZIONE_CONTABILE
		where 
			pay_posizioni_debitorie.idcomune=PAY_DETTAGLIO_IMPORTI.idcomune and
			pay_posizioni_debitorie.id=PAY_DETTAGLIO_IMPORTI.FK_POSIZIONE_DEBITORIA
	)
where 	PAY_DETTAGLIO_IMPORTI.fk_reg_causale is null;	

ALTER TABLE CONTI ADD MAPPATURANODOPAG VARCHAR(50);

ALTER TABLE istanze MODIFY DESCRSOGGETTO VARCHAR(256);
ALTER TABLE istanzerichiedenti MODIFY DESCRSOGGETTO VARCHAR(256);

ALTER TABLE pay_profili_enti_creditori MODIFY codiceamministrazione NUMERIC(10,0) NULL;

ALTER TABLE PAY_PROFILI_ENTI_CREDITORI MODIFY CF_CODICE_PROFILO_PSP VARCHAR(25);

alter table boll_cfg_tipo add arrotondamento varchar(25);

