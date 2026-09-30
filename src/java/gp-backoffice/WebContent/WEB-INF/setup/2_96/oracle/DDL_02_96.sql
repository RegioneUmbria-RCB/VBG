CREATE TABLE FO_QUESTIONARIO_GRADIMENTO (
    IDCOMUNE VARCHAR2(6) NOT NULL,
    ID NUMBER(10) NOT NULL,
    fk_codiceistanza NUMBER(6) NOT NULL,
    valutazione NUMBER(1) NOT NULL,
    note VARCHAR2(4000),
    data date not null
);

ALTER TABLE FO_QUESTIONARIO_GRADIMENTO
  ADD CONSTRAINT fo_quest_pk PRIMARY KEY (
    idcomune,
    id
  );

ALTER TABLE FO_QUESTIONARIO_GRADIMENTO
  ADD CONSTRAINT fk_quest_istanze FOREIGN KEY (
    idcomune,
    fk_codiceistanza
  ) REFERENCES istanze (
    idcomune,
    codiceistanza
  );

  ALTER TABLE DOCUMENTIISTANZA MODIFY ID_BASE VARCHAR2(200 CHAR);