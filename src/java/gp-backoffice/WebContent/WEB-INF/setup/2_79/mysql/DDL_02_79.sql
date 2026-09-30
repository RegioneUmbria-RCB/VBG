CREATE TABLE MAIL_CONFIG_COMUNI(
                IDCOMUNE VARCHAR(6) NOT NULL,
                ID NUMERIC(10,0) NOT NULL,
                FK_CODICECOMUNE VARCHAR(6),
                FK_MAILCONFIG_ID NUMERIC(5,0)
)ENGINE=INNODB DEFAULT CHARSET=utf8;
ALTER TABLE MAIL_CONFIG_COMUNI ADD CONSTRAINT PK_MAIL_CONFIG_COMUNI PRIMARY KEY(IDCOMUNE, ID);
ALTER TABLE MAIL_CONFIG_COMUNI ADD CONSTRAINT FK_COMUNI FOREIGN KEY( FK_CODICECOMUNE) REFERENCES COMUNI(CODICECOMUNE);
ALTER TABLE MAIL_CONFIG_COMUNI ADD CONSTRAINT FK_MAILCONFIG FOREIGN KEY( IDCOMUNE, FK_MAILCONFIG_ID) REFERENCES MAIL_CONFIG(IDCOMUNE, ID);

INSERT INTO mail_config_comuni (idcomune, id, fk_codicecomune, fk_mailconfig_id)
(    
    
    SELECT 
        mail_config.idcomune AS idcomune,
        (SELECT IFNULL(MAX(id),0) FROM mail_config_comuni WHERE mail_config_comuni.idcomune = mail_config.idcomune) + (@rownum:=@rownum+1) AS id,
        comuniassociati.codicecomune AS fk_codicecomune,
        mail_config.id AS fk_mailconfig_id
        
    FROM 
        mail_config
            
            LEFT OUTER JOIN mail_config_comuni ON
                mail_config_comuni.idcomune = mail_config.idcomune AND
                mail_config_comuni.fk_mailconfig_id = mail_config.id
                
            INNER JOIN comuniassociati ON
                comuniassociati.idcomune = mail_config.idcomune       
                
            INNER JOIN (SELECT @rownum:=0) r     
    
    WHERE 
        mail_config_comuni.idcomune IS NULL 
)        
;

alter table ruoli modify ruolo varchar(100 );


CREATE TABLE COMUNIASSOCIATIESCLUSIONI(
                IDCOMUNE VARCHAR(6),
                CODICECOMUNE VARCHAR(5),
                SOFTWARE VARCHAR(2)
)ENGINE=INNODB DEFAULT CHARSET=utf8;
ALTER TABLE COMUNIASSOCIATIESCLUSIONI ADD CONSTRAINT COMUNIASSOCIATIESCLUSIONI_PK PRIMARY KEY (IDCOMUNE,CODICECOMUNE,SOFTWARE);

ALTER TABLE COMUNIASSOCIATIESCLUSIONI ADD CONSTRAINT FK_COMASSESCL_COMASS FOREIGN KEY(IDCOMUNE,CODICECOMUNE) REFERENCES COMUNIASSOCIATI (IDCOMUNE,CODICECOMUNE);


ALTER TABLE TIPIMOVIMENTO_COMUNICAZIONI ADD FK_MAILCONFIGID NUMERIC(5,0);
ALTER TABLE TIPIMOVIMENTO_COMUNICAZIONI ADD CONSTRAINT FK_TIPIMOVCOM_MAILCONFIG FOREIGN KEY(IDCOMUNE,FK_MAILCONFIGID) REFERENCES MAIL_CONFIG(IDCOMUNE,ID);


alter table boll_gest_dettaglio add data_scadenza datetime;

UPDATE boll_gest_dettaglio A 
    JOIN boll_gest_testata B ON A.IDCOMUNE = B.IDCOMUNE AND A.FK_BOLLGEST_ID = B.ID
    SET A.data_scadenza = B.data_scadenza WHERE A.data_scadenza IS NULL;

    
ALTER TABLE AREEDETTAGLI ADD COLUMN KM_DA NUMERIC(8,3);
ALTER TABLE AREEDETTAGLI ADD COLUMN KM_A NUMERIC(8,3);


ALTER TABLE MERCATI_FORMULE_CALCOLO ADD NOTE VARCHAR(4000);

