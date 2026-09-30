ALTER TABLE OGGETTI ADD PERCORSO VARCHAR2(128 CHAR);
ALTER TABLE DOMANDESTC MODIFY NUMEROISTANZA VARCHAR2(35 CHAR);
ALTER TABLE MOVIMENTIALLEGATI ADD FLAG_PUBBLICA NUMBER(1,0);
ALTER TABLE TIPIMOVIMENTO ADD FLAG_PUBBLICAALLEGATI NUMBER(1,0);
CREATE OR REPLACE FORCE VIEW VW_SCADENZARIO
                                                           AS
  SELECT VW_BATCH_SCADENZARIO.IDCOMUNE                      AS IDCOMUNE,
    VW_BATCH_SCADENZARIO.ID                                 AS CODICESCADENZA,
    VW_BATCH_SCADENZARIO.SOFTWARE                           AS software,
    VW_BATCH_SCADENZARIO.CODICEISTANZA                      AS codiceistanza,
    ISTANZE.NUMEROPROTOCOLLO                                AS numeroprotocollo,
    ISTANZE.DATAPROTOCOLLO                                  AS dataprotocollo,
    ISTANZE.NUMEROISTANZA                                   AS NUMEROISTANZA,
    ISTANZE.CHIUSURA                                        AS CODSTATOISTANZA,
    STATIISTANZA.STATO                                      AS DESCRSTATOISTANZA,
    VW_BATCH_SCADENZARIO.CODICEMOVIMENTO                    AS CODMOVIMENTO,
    VW_BATCH_SCADENZARIO.DESCRMOVIMENTO                     AS DESCRMOVIMENTO,
    TO_CHAR(MOVIMENTI.DATA,'DD/MM/YYYY')                    AS DATAMOVIMENTO,
    VW_BATCH_SCADENZARIO.TIPOMOVIMENTODAFARE                AS CODMOVIMENTODAFARE,
    VW_BATCH_SCADENZARIO.DESCRMOVIMENTODAFARE               AS DESCRMOVIMENTODAFARE,
    TO_CHAR(VW_BATCH_SCADENZARIO.DATASCADENZA,'DD/MM/YYYY') AS DATASCADENZASTR,
    VW_BATCH_SCADENZARIO.codiceinventario                   AS codiceinventario,
    VW_BATCH_SCADENZARIO.CODICEAMMINISTRAZIONE              AS CODICEAMMINISTRAZIONE,
    RESPONSABILI.RESPONSABILE                               AS RESPONSABILE,
    INVENTARIOPROCEDIMENTI.PROCEDIMENTO                     AS PROCEDIMENTO,
    TIPIPROCEDURE.PROCEDURA                                 AS PROCEDURA,
    SOFTWARE.DESCRIZIONE                                    AS MODULOSOFTWARE,
    amministrazioni.AMMINISTRAZIONE                         AS AMM_amministrazione,
    AMMINISTRAZIONI.PARTITAIVA                              AS AMM_PARTITAIVA,
    AMMINISTRAZIONI.PASSWORD                                AS AMM_PASSWORD,
    ANAGRAFE.NOMINATIVO
    || ' '
    || ANAGRAFE.NOME               AS RIC_NOMINATIVO,
    NVL(ANAGRAFE.CODICEFISCALE,'') AS RIC_CODICEFISCALE,
    NVL(ANAGRAFE.PARTITAIVA,'')    AS RIC_PARTITAIVA,
    ANAGRAFE.INDIRIZZO             AS RIC_INDIRIZZO,
    ANAGRAFE.CAP                   AS RIC_CAP,
    ANAGRAFE.CITTA                 AS RIC_LOCALITA,
    COMUNERESIDENZA.COMUNE         AS RIC_CITTA,
    COMUNERESIDENZA.PROVINCIA      AS RIC_PROVINCIA,
    ANAGRAFE.PASSWORD              AS RIC_PASSWORD,
    TECNICO.NOMINATIVO
    || ' '
    || TECNICO.NOME               AS TEC_NOMINATIVO,
    NVL(TECNICO.CODICEFISCALE,'') AS TEC_CODICEFISCALE,
    NVL(TECNICO.PARTITAIVA,'')    AS TEC_PARTITAIVA,
    TECNICO.PASSWORD              AS TEC_PASSWORD,
    AZIENDA.NOMINATIVO
    || ' '
    || AZIENDA.NOME               AS AZ_NOMINATIVO,
    NVL(AZIENDA.CODICEFISCALE,'') AS AZ_CODICEFISCALE,
    NVL(AZIENDA.PARTITAIVA,'')    AS AZ_PARTITAIVA,
    AZIENDA.PASSWORD              AS AZ_PASSWORD,
    TIPIMOVIMENTO.FK_FO_SOGGETTIESTERNI
  FROM VW_BATCH_SCADENZARIO,
    ISTANZE,
    RESPONSABILI,
    INVENTARIOPROCEDIMENTI,
    MOVIMENTI,
    TIPIPROCEDURE,
    SOFTWARE,
    ANAGRAFE,
    COMUNI COMUNERESIDENZA,
    ANAGRAFE TECNICO,
    ANAGRAFE AZIENDA,
    STATIISTANZA,
    AMMINISTRAZIONI,
    TIPIMOVIMENTO
  WHERE ISTANZE.IDCOMUNE                        =VW_BATCH_SCADENZARIO.IDCOMUNE
  AND ISTANZE.CODICEISTANZA                     =VW_BATCH_SCADENZARIO.CODICEISTANZA
  AND RESPONSABILI.IDCOMUNE                     =ISTANZE.IDCOMUNE
  AND RESPONSABILI.CODICERESPONSABILE           =ISTANZE.CODICERESPONSABILE
  AND INVENTARIOPROCEDIMENTI.IDCOMUNE(+)        =VW_BATCH_SCADENZARIO.IDCOMUNE
  AND INVENTARIOPROCEDIMENTI.CODICEINVENTARIO(+)=VW_BATCH_SCADENZARIO.CODICEINVENTARIO
  AND MOVIMENTI.IDCOMUNE                        =VW_BATCH_SCADENZARIO.IDCOMUNE
  AND MOVIMENTI.CODICEMOVIMENTO                 =VW_BATCH_SCADENZARIO.CODICEMOVIMENTO
  AND TIPIMOVIMENTO.IDCOMUNE                    =VW_BATCH_SCADENZARIO.IDCOMUNE
  AND TIPIMOVIMENTO.TIPOMOVIMENTO               =VW_BATCH_SCADENZARIO.TIPOMOVIMENTODAFARE
  AND TIPIPROCEDURE.IDCOMUNE                    =ISTANZE.IDCOMUNE
  AND TIPIPROCEDURE.CODICEPROCEDURA             =ISTANZE.CODICEPROCEDURA
  AND SOFTWARE.CODICE                           =VW_BATCH_SCADENZARIO.SOFTWARE
  AND ANAGRAFE.IDCOMUNE                         = ISTANZE.IDCOMUNE
  AND ANAGRAFE.CODICEANAGRAFE                   = ISTANZE.CODICERICHIEDENTE
  AND COMUNERESIDENZA.CODICECOMUNE(+)           = ANAGRAFE.COMUNERESIDENZA
  AND TECNICO.IDCOMUNE(+)                       = ISTANZE.IDCOMUNE
  AND TECNICO.CODICEANAGRAFE(+)                 = ISTANZE.CODICEPROFESSIONISTA
  AND AZIENDA.IDCOMUNE(+)                       = ISTANZE.IDCOMUNE
  AND AZIENDA.CODICEANAGRAFE(+)                 = ISTANZE.CODICETITOLARELEGALE
  AND STATIISTANZA.IDCOMUNE                     = ISTANZE.IDCOMUNE
  AND STATIISTANZA.SOFTWARE                     = ISTANZE.SOFTWARE
  AND STATIISTANZA.CODICESTATO                  = ISTANZE.CHIUSURA
  AND AMMINISTRAZIONI.IDCOMUNE                  = VW_BATCH_SCADENZARIO.IDCOMUNE
  AND AMMINISTRAZIONI.CODICEAMMINISTRAZIONE     = VW_BATCH_SCADENZARIO.CODICEAMMINISTRAZIONE;

CREATE OR REPLACE VIEW vw_listapratiche AS
SELECT istanze.codicecomune ,
    istanze.idcomune ,
    istanze.software                   AS software ,
    software.descrizionelunga          AS descsoftware ,
    istanze.codiceistanza              AS idpratica ,
    istanze.numeroistanza              AS numeropratica ,
    TO_CHAR(istanze.data,'DD/MM/YYYY') AS datapresentazione ,
    TO_CHAR(istanze.data,'YYYY')       AS annopresentazione ,
    TO_CHAR(istanze.data,'MM')         AS mesepresentazione ,
    istanze.numeroprotocollo ,
    TO_CHAR(istanze.dataprotocollo,'DD/MM/YYYY') AS dataprotocollo ,
    istanze.codiceinterventoproc                 AS codiceintervento ,
    vw_alberoproc.sc_descrizione                 AS descrizioneintervento,
    tipiprocedure.codiceprocedura ,
    tipiprocedure.procedura ,
    istanze.lavori        AS oggetto ,
    UPPER(istanze.lavori) AS oggettou ,
    istanze.chiusura      AS codstatopratica ,
    stato                 AS statopratica ,
    responsabili.responsabile ,
    responsabili.telefonolavoro                        AS responsabile_telefono ,
    NVL(stradario.codviario,stradario.codicestradario) AS pr_codviario ,
    stradario.prefisso
    || ' '
    || stradario.descrizione        AS pr_indirizzo ,
    istanzestradario.CODICECIVICO   AS pr_codcivico ,
    istanze.civico                  AS pr_civico ,
    istanze.codicearea              AS codicezonizzazione,
    aree.denominazione              AS zonizzazione ,
    anagrafe.codiceanagrafe         AS CodiceRichiedente ,
    NVL(anagrafe.codicefiscale, '') AS codicefiscale ,
    NVL(anagrafe.partitaiva, '')    AS partitaiva ,
    anagrafe.nominativo
    || ' '
    || anagrafe.nome AS nominativo,
    anagrafe.indirizzo ,
    anagrafe.cap ,
    anagrafe.citta         AS localita ,
    comuneresidenza.comune AS citta ,
    comuneresidenza.provincia ,
    tipisoggetto.tiposoggetto AS tiporapporto ,
    catasto.descrizione       AS tipocatasto ,
    istanzemappali.foglio ,
    istanzemappali.particella ,
    istanzemappali.sub ,
    tecnico.codiceanagrafe AS tec_codice ,
    tecnico.nominativo
    || ' '
    || tecnico.nome                AS tec_nominativo ,
    NVL(tecnico.codicefiscale, '') AS tec_codicefiscale,
    NVL(tecnico.partitaiva, '')    AS tec_partitaiva ,
    azienda.codiceanagrafe         AS az_codice ,
    azienda.nominativo
    || ' '
    || azienda.nome                AS az_nominativo ,
    NVL(azienda.codicefiscale, '') AS az_codicefiscale,
    NVL(azienda.partitaiva, '')    AS az_partitaiva,
    istruttori.responsabile        AS istruttore,
    istruttori.telefonolavoro      AS istruttore_telefono,
    operatori.responsabile         AS operatore,
    operatori.telefonolavoro       AS operatore_telefono
  FROM 
  istanze 
    join software ON software.codice = istanze.software
    join anagrafe ON anagrafe.idcomune = istanze.idcomune
                      AND anagrafe.codiceanagrafe = istanze.codicerichiedente
    join statiistanza ON statiistanza.idcomune = istanze.idcomune
                          AND statiistanza.software = istanze.software
                          AND statiistanza.codicestato = istanze.chiusura
    join vw_alberoproc ON vw_alberoproc.idcomune = istanze.idcomune
                        AND vw_alberoproc.sc_id = istanze.codiceinterventoproc
    left outer join anagrafe tecnico ON tecnico.idcomune = istanze.idcomune 
                                  AND tecnico.codiceanagrafe = istanze.codiceprofessionista
    left outer join anagrafe azienda ON istanze.idcomune = azienda.idcomune
                                  AND istanze.codicetitolarelegale = azienda.codiceanagrafe
    left outer join istanzestradario ON istanze.idcomune = istanzestradario.idcomune
                                  AND istanze.codiceistanza = istanzestradario.codiceistanza 
                                  AND 1 = istanzestradario.primario
                                  left outer join stradario ON stradario.idcomune = istanzestradario.idcomune
                                                        AND stradario.codicestradario = istanzestradario.codicestradario 
    left outer join istanzemappali ON istanze.idcomune = istanzemappali.idcomune 
                              AND istanze.codiceistanza = istanzemappali.fkcodiceistanza
                              AND 1 = istanzemappali.primario
    left outer join responsabili ON istanze.idcomune = responsabili.idcomune
                            AND istanze.codiceresponsabileproc = responsabili.codiceresponsabile
    left outer join comuni comuneresidenza ON anagrafe.comuneresidenza = comuneresidenza.codicecomune
    left outer join tipisoggetto ON istanze.idcomune = tipisoggetto.idcomune
                                  AND istanze.fkcodicesoggetto = tipisoggetto.codicetiposoggetto
    left outer join aree ON istanze.idcomune = aree.idcomune
                         AND istanze.codicearea = aree.codicearea
    left outer join catasto ON istanzemappali.codicecatasto = catasto.codice
    left outer join tipiprocedure ON istanze.idcomune = tipiprocedure.idcomune
                                  AND istanze.codiceprocedura = tipiprocedure.codiceprocedura
    left outer join responsabili istruttori ON istanze.idcomune = istruttori.idcomune            
                                            AND istanze.codiceistruttore = istruttori.codiceresponsabile
    left outer join responsabili operatori ON operatori.idcomune = istanze.idcomune 
                                            AND operatori.codiceresponsabile = istanze.codiceresponsabile;
                                            
CREATE INDEX IDX_MOV_CONTROMOV_001 ON MOVIMENTI_CONTROMOVIMENTI (IDCOMUNE,CODICEMOVIMENTO);
CREATE INDEX IDX_MOV_CONTROMOV_002 ON MOVIMENTI_CONTROMOVIMENTI (IDCOMUNE,CODICECONTROMOVIMENTO);