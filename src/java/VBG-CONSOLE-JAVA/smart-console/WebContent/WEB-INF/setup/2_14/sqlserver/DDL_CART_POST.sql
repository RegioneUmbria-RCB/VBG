SET IMPLICIT_TRANSACTIONS ON
GO
alter table stp_endo_tipo2 add constraint fk_stpendo2_stipole2 foreign key (idcomune, codice_tipologia_endo) references stp_tipologie_endo2(idcomune, id)
GO
COMMIT
GO
ALTER TABLE FORMEGIURIDICHE ADD CONSTRAINT FK_FGIURIDICHE_RIFORMEG FOREIGN KEY (CODICECCIAA) REFERENCES RI_FORMEGIURIDICHE(CODICE)
GO
COMMIT
GO