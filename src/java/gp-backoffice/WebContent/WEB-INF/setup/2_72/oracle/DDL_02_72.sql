alter table tmp_esportazioni modify codicecomune null;

CREATE TABLE mercatidyn2modellit (
  idcomune       VARCHAR2(6) NOT NULL,
  codicemercato  NUMBER(4,0)      NOT NULL,
  fk_d2mt_id     NUMBER(10,0)     NOT NULL
);

ALTER TABLE mercatidyn2modellit
  ADD CONSTRAINT mercatidyn2modellit_pk PRIMARY KEY (
    idcomune,
    codicemercato,
    fk_d2mt_id
  );

ALTER TABLE mercatidyn2modellit
  ADD CONSTRAINT fk_mercd2dmt_dyn2modt FOREIGN KEY (
    idcomune,
    fk_d2mt_id
  ) REFERENCES dyn2_modellit (
    idcomune,
    id
  );
  

ALTER TABLE mercatidyn2modellit
  ADD CONSTRAINT fk_merc_dyn2modelli_merc FOREIGN KEY (
    idcomune,
    codicemercato
  ) REFERENCES mercati (
    idcomune,
    codicemercato
  );
  

COMMENT ON COLUMN mercatidyn2modellit.codicemercato IS 'Fk su MERCATI.CODICEMERCATO';
COMMENT ON COLUMN mercatidyn2modellit.fk_d2mt_id IS 'DYN2_MODELLIT.ID';


CREATE TABLE mercatidyn2dati (
  idcomune            VARCHAR2(6) DEFAULT 0 NOT NULL,
  codicemercato       NUMBER(4,0)      NOT NULL,
  fk_d2c_id           NUMBER(10,0)     NOT NULL,
  valore              CLOB             NULL,
  indice              NUMBER(2,0)      DEFAULT 0 NOT NULL,
  valoredecodificato  CLOB             NULL,
  indice_molteplicita NUMBER(2,0)      DEFAULT 0 NOT NULL
);


ALTER TABLE mercatidyn2dati
  ADD CONSTRAINT mercatidyn2dati_pk PRIMARY KEY (
    idcomune,
    codicemercato,
    fk_d2c_id,
    indice,
    indice_molteplicita
  );
  

ALTER TABLE mercatidyn2dati
  ADD CONSTRAINT fk_mercd2d_dyn2campi FOREIGN KEY (
    idcomune,
    fk_d2c_id
  ) REFERENCES dyn2_campi (
    idcomune,
    id
  );
  

ALTER TABLE mercatidyn2dati
  ADD CONSTRAINT fk_merc_dyn2dati_merc FOREIGN KEY (
    idcomune,
    codicemercato
  ) REFERENCES mercati (
    idcomune,
    codicemercato
  );

COMMENT ON COLUMN mercatidyn2dati.codicemercato IS 'FK su MERCATI.CODICEMERCATO';
COMMENT ON COLUMN mercatidyn2dati.fk_d2c_id IS 'FK su DYN2_CAMPI.ID';

ALTER TABLE TIPIMOVIMENTO ADD (FLAG_FO_RICHIAMA_SIT NUMBER(1,0));