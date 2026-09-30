CREATE TABLE MAIL_CONFIG_COMUNI(
                IDCOMUNE VARCHAR2(6 CHAR) NOT NULL,
                ID NUMBER(10,0) NOT NULL,
                FK_CODICECOMUNE VARCHAR2(6 CHAR),
FK_MAILCONFIG_ID NUMBER(5,0));

ALTER TABLE MAIL_CONFIG_COMUNI ADD CONSTRAINT PK_MAIL_CONFIG_COMUNI PRIMARY KEY(IDCOMUNE, ID);
ALTER TABLE MAIL_CONFIG_COMUNI ADD CONSTRAINT FK_COMUNI FOREIGN KEY( FK_CODICECOMUNE) REFERENCES COMUNI(CODICECOMUNE);
ALTER TABLE MAIL_CONFIG_COMUNI ADD CONSTRAINT FK_MAILCONFIG FOREIGN KEY( IDCOMUNE, FK_MAILCONFIG_ID) REFERENCES MAIL_CONFIG(IDCOMUNE, ID);

insert into mail_config_comuni (idcomune, id, fk_codicecomune, fk_mailconfig_id)
(
    select 
        mail_config.idcomune as idcomune,
        (select nvl(max(id),0) from mail_config_comuni where mail_config_comuni.idcomune = mail_config.idcomune) + rownum as id,
        comuniassociati.codicecomune as fk_codicecomune,
        mail_config.id as fk_mailconfig_id
        
    from 
        mail_config
            
            left outer join mail_config_comuni on
                mail_config_comuni.idcomune = mail_config.idcomune and
                mail_config_comuni.fk_mailconfig_id = mail_config.id
                
            inner join comuniassociati on
                comuniassociati.idcomune = mail_config.idcomune            
    
    where 
        mail_config_comuni.idcomune is null 
)
;


alter table ruoli modify ruolo varchar2(100 char);
CREATE TABLE COMUNIASSOCIATIESCLUSIONI(
                IDCOMUNE VARCHAR2(6 CHAR),
                CODICECOMUNE VARCHAR2(5 CHAR),
                SOFTWARE VARCHAR2(2 CHAR)
);

ALTER TABLE COMUNIASSOCIATIESCLUSIONI ADD CONSTRAINT COMUNIASSOCIATIESCLUSIONI_PK PRIMARY KEY (IDCOMUNE, CODICECOMUNE, SOFTWARE);
ALTER TABLE COMUNIASSOCIATIESCLUSIONI ADD CONSTRAINT FK_COMASSESCL_COMASS FOREIGN KEY(IDCOMUNE,CODICECOMUNE) REFERENCES COMUNIASSOCIATI (IDCOMUNE,CODICECOMUNE);

ALTER TABLE TIPIMOVIMENTO_COMUNICAZIONI ADD FK_MAILCONFIGID NUMBER(5,0);
ALTER TABLE TIPIMOVIMENTO_COMUNICAZIONI ADD CONSTRAINT FK_TIPIMOVCOM_MAILCONFIG FOREIGN KEY(IDCOMUNE,FK_MAILCONFIGID) REFERENCES MAIL_CONFIG(IDCOMUNE,ID);


alter table boll_gest_dettaglio add data_scadenza date;

UPDATE boll_gest_dettaglio SET boll_gest_dettaglio.data_scadenza = (SELECT boll_gest_testata.data_scadenza
                                  FROM boll_gest_testata 
                                  WHERE boll_gest_dettaglio.idcomune = boll_gest_testata.idcomune and
                                  BOLL_GEST_DETTAGLIO.FK_BOLLGEST_ID = boll_gest_testata.id ) WHERE boll_gest_dettaglio.data_scadenza is null AND EXISTS (SELECT 1
            FROM boll_gest_testata 
            WHERE  boll_gest_dettaglio.idcomune = boll_gest_testata.idcomune and
                                  boll_gest_dettaglio.FK_BOLLGEST_ID = boll_gest_testata.id);

ALTER TABLE AREEDETTAGLI ADD (KM_DA NUMBER(8,3) );
ALTER TABLE AREEDETTAGLI ADD (KM_A NUMBER(8,3) );

ALTER TABLE MERCATI_FORMULE_CALCOLO ADD NOTE VARCHAR2(4000 CHAR);
