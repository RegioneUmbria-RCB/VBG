ALTER TABLE tmp_esportazioni MODIFY codicecomune VARCHAR(5) NULL;

CREATE TABLE mercatidyn2modellit (
  idcomune       VARCHAR(6) NOT NULL,
  codicemercato  DECIMAL(4,0)      NOT NULL,
  fk_d2mt_id     DECIMAL(10,0)     NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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


CREATE TABLE mercatidyn2dati (
  idcomune            VARCHAR(6) DEFAULT 0 NOT NULL,
  codicemercato       decimal(4,0)      NOT NULL,
  fk_d2c_id           decimal(10,0)     NOT NULL,
  valore              longtext             NULL,
  indice              decimal(2,0)      DEFAULT 0 NOT NULL,
  valoredecodificato  longtext             NULL,
  indice_molteplicita decimal(2,0)      DEFAULT 0 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


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


ALTER TABLE TIPIMOVIMENTO ADD (FLAG_FO_RICHIAMA_SIT NUMERIC(1,0));
