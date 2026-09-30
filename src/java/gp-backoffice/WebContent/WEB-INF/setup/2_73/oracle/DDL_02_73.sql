ALTER TABLE MERCATIPRESENZE_T MODIFY NOTE VARCHAR2(4000);

CREATE TABLE mercati_ddyn2modellit (
  idcomune       	VARCHAR2(6 BYTE) NOT NULL,
  idposteggio  		NUMBER(6,0)      NOT NULL,
  fk_d2mt_id     	NUMBER(10,0)     NOT NULL
);

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
  

COMMENT ON COLUMN mercati_ddyn2modellit.idposteggio IS 'Fk su MERCATI_D.IDPOSTEGGIO';
COMMENT ON COLUMN mercati_ddyn2modellit.fk_d2mt_id IS 'DYN2_MODELLIT.ID';



CREATE TABLE mercati_ddyn2dati (
  idcomune            	VARCHAR2(6 BYTE) DEFAULT 0 NOT NULL,
  idposteggio       	NUMBER(6,0)      NOT NULL,
  fk_d2c_id           	NUMBER(10,0)     NOT NULL,
  valore              	CLOB             NULL,
  indice              	NUMBER(2,0)      DEFAULT 0 NOT NULL,
  valoredecodificato  	CLOB             NULL,
  indice_molteplicita 	NUMBER(2,0)      DEFAULT 0 NOT NULL
);


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

COMMENT ON COLUMN mercati_ddyn2dati.idposteggio IS 'FK su MERCATI_D.IDPOSTEGGIO';
COMMENT ON COLUMN mercati_ddyn2dati.fk_d2c_id IS 'FK su DYN2_CAMPI.ID';
COMMENT ON COLUMN mercati_ddyn2dati.valoredecodificato IS 'Contiene il valore decodificato del campo corrente';

ALTER TABLE MERCATIPRESENZE_T ADD FLAG_DOMENICA NUMBER(1,0);

ALTER TABLE ISTANZE ADD (TIPO_PROT_FALLITA VARCHAR2(70));


ALTER TABLE PAY_POSIZIONI_DEBITORIE ADD (FLAG_OTF NUMBER(1,0) );

COMMENT ON COLUMN PAY_POSIZIONI_DEBITORIE.FLAG_OTF IS 'SE 1 INDICA CHE LA POSIZIONE E'' CARICATA IN MODALITA'' ON THE FLY';


alter table mercati_responsabili modify id number(10,0);

ALTER TABLE MERCATIPRESENZE_D MODIFY ID NUMBER(10,0);

alter table CONFIGURAZIONE add URL_LOGO_BACKOFFICE VARCHAR2(200);


ALTER TABLE MERCATI_CFG_CONTI ADD FK_CODICEMERCATO NUMBER(4,0);
ALTER TABLE MERCATI_CFG_CONTI ADD CONSTRAINT FK_CODICEMERCATO FOREIGN KEY (IDCOMUNE, FK_CODICEMERCATO) REFERENCES MERCATI(IDCOMUNE,CODICEMERCATO);
