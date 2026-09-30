ALTER TABLE MERCATIPRESENZE_T MODIFY NOTE VARCHAR(4000);


CREATE TABLE mercati_ddyn2modellit (
  idcomune       	VARCHAR(6) NOT NULL,
  idposteggio  		NUMERIC(6,0)      NOT NULL,
  fk_d2mt_id     	NUMERIC(10,0)     NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE mercati_ddyn2modellit
  ADD CONSTRAINT mercati_ddyn2modellit_pk PRIMARY KEY (
    idcomune,
    idposteggio,
    fk_d2mt_id
  );

ALTER TABLE mercati_ddyn2modellit
  ADD CONSTRAINT fk_merc_dd2dmt_dyn2modt FOREIGN KEY (
    idcomune,
    fk_d2mt_id
  ) REFERENCES dyn2_modellit (
    idcomune,
    id
  );
  

ALTER TABLE mercati_ddyn2modellit
  ADD CONSTRAINT fk_merc_ddyn2modelli_md FOREIGN KEY (
    idcomune,
    idposteggio
  ) REFERENCES mercati_d (
    idcomune,
    idposteggio
  );


CREATE TABLE mercati_ddyn2dati (
  idcomune            	VARCHAR(6) DEFAULT 0 NOT NULL,
  idposteggio       	NUMERIC(6,0)      NOT NULL,
  fk_d2c_id           	NUMERIC(10,0)     NOT NULL,
  valore              	longtext             NULL,
  indice              	NUMERIC(2,0)      DEFAULT 0 NOT NULL,
  valoredecodificato  	longtext             NULL,
  indice_molteplicita 	NUMERIC(2,0)      DEFAULT 0 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


ALTER TABLE mercati_ddyn2dati
  ADD CONSTRAINT mercati_ddyn2dati_pk PRIMARY KEY (
    idcomune,
    idposteggio,
    fk_d2c_id,
    indice,
    indice_molteplicita
  );
  

ALTER TABLE mercati_ddyn2dati
  ADD CONSTRAINT fk_mercdd2d_dyn2campi FOREIGN KEY (
    idcomune,
    fk_d2c_id
  ) REFERENCES dyn2_campi (
    idcomune,
    id
  );
  

ALTER TABLE mercati_ddyn2dati
  ADD CONSTRAINT fk_mercddyn2dati_merc FOREIGN KEY (
    idcomune,
    idposteggio
  ) REFERENCES mercati_d (
    idcomune,
    idposteggio
  );
  
  
ALTER TABLE MERCATIPRESENZE_T ADD FLAG_DOMENICA NUMERIC(1,0);

ALTER TABLE ISTANZE ADD TIPO_PROT_FALLITA VARCHAR(70);

ALTER TABLE pay_posizioni_debitorie ADD COLUMN FLAG_OTF NUMERIC(1,0) NULL;

alter table mercati_responsabili modify id NUMERIC(10,0);

ALTER TABLE MERCATIPRESENZE_D MODIFY ID NUMERIC(10,0);

alter table CONFIGURAZIONE add URL_LOGO_BACKOFFICE VARCHAR(200);

ALTER TABLE MERCATI_CFG_CONTI ADD FK_CODICEMERCATO NUMERIC(4,0);
ALTER TABLE MERCATI_CFG_CONTI ADD CONSTRAINT FK_CODICEMERCATO FOREIGN KEY (IDCOMUNE, FK_CODICEMERCATO) REFERENCES MERCATI(IDCOMUNE,CODICEMERCATO);
