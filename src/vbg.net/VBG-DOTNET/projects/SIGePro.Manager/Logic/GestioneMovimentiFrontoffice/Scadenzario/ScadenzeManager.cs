using Init.SIGePro.Manager.DTO.Scadenzario;
using Init.SIGePro.Manager.Logic.GestioneStradario;
using Init.SIGePro.Manager.Utils.Extensions;
using log4net;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneMovimentiFrontoffice.Scadenzario
{
    // ATTENZIONE! il 07/03/2012 è stata rimossa la proprietà "password" dalle classi DatiAmministrazione, PartitaIva
    // e CodiceFiscale. Il codice per la lettura delle scadenze è stato modificato in modo da riflettere queste modifiche

    /// <summary>
    /// Descrizione di riepilogo per ScadenzarioManager.
    /// </summary>
    public class ScadenzeManager
    {
        private readonly DataBase _database;
        private readonly string _idComune;
        private readonly ILog _log = LogManager.GetLogger(typeof(ScadenzeManager));


        public ScadenzeManager(DataBase database, string idComune)
        {
            this._database = database;
            this._idComune = idComune;
        }

        /// <summary>
        /// Ottiene una lista di scadenze a partire dai filtri passati
        /// </summary>
        /// <param name="filtri">Filtri di selezione delle scadenze</param>
        /// <returns>Lista di scadenze</returns>
        public IEnumerable<ElementoListaScadenzeDto> GetListaScadenze(RichiestaListaScadenzeDto filtri)
        {
            var rVal = new List<ElementoListaScadenzeDto>();

            rVal.AddRange(this.GetListaScadenzeComeRichiedente(filtri));
            rVal.AddRange(this.GetListaScadenzeComeSoggettoCollegato(filtri));

            return rVal.OrderBy(x => x.DataScadenzaOrderBy);
        }

        private IEnumerable<ElementoListaScadenzeDto> GetListaScadenzeComeRichiedente(RichiestaListaScadenzeDto filtri)
        {
            try
            {
                StringBuilder sb = new StringBuilder();

                sb.Append($@"
select distinct
    movimenti.CODICEMOVIMENTO                                         as CODICESCADENZA
    , istanze.CODICEISTANZA                                           as CODICEISTANZA
    , movimenti.DATA_SCADENZA                                         as DATASCADENZA
    , movimentifatti.DATA                                             as DATAMOVIMENTO
    , COALESCE(MOVIMENTIFATTI.MOVIMENTO,TIPOMOVIMENTOFATTO.MOVIMENTO) as DESCRMOVIMENTO
    , azienda.NOMINATIVO                                              as AZ_NOMINATIVO
    , azienda.NOME                                                    as AZ_NOME
    , COALESCE(azienda.CODICEFISCALE,'')                              as AZ_CODICEFISCALE
    , COALESCE(azienda.PARTITAIVA,'')                                 as AZ_PARTITAIVA
    , anagrafe.NOMINATIVO                                             as RIC_NOMINATIVO
    , anagrafe.NOME                                                   as RIC_NOME
    , COALESCE(anagrafe.CODICEFISCALE,'')                             as RIC_CODICEFISCALE
    , COALESCE(anagrafe.PARTITAIVA,'')                                as RIC_PARTITAIVA
    , tecnico.NOMINATIVO                                              as TEC_NOMINATIVO
    , tecnico.NOME                                                    as TEC_NOME
    , COALESCE(tecnico.CODICEFISCALE,'')                              as TEC_CODICEFISCALE
    , COALESCE(tecnico.PARTITAIVA,'')                                 as TEC_PARTITAIVA
    , COALESCE(movimenti.MOVIMENTO,tipimovimento.MOVIMENTO)           as DESCRMOVIMENTODAFARE
    , statiistanza.STATO                                              as DESCRSTATOISTANZA
    , software.DESCRIZIONE                                            as MODULOSOFTWARE
    , istanze.NUMEROISTANZA                                           as NUMEROISTANZA
    , istanze.UUID                                                    as UUID_ISTANZA
    , movimenti_metadati.VALORE                                       as UUID_MOVIMENTO_DA_FARE
from
    movimenti

        inner join movimenti_metadati on
            movimenti_metadati.idcomune = movimenti.idcomune
            AND movimenti_metadati.CODICEMOVIMENTO = movimenti.codicemovimento
            AND movimenti_metadati.CHIAVE = 'UUID'

        INNER JOIN istanze ON
            istanze.idcomune          = movimenti.idcomune
            AND istanze.codiceistanza = movimenti.codiceistanza

        INNER JOIN tipimovimento ON
            tipimovimento.idcomune          = movimenti.idcomune
            AND tipimovimento.tipomovimento = movimenti.tipomovimento

        INNER JOIN software ON
            software.codice = istanze.software

        INNER JOIN anagrafe ON
            anagrafe.idcomune           = istanze.idcomune
            AND anagrafe.codiceanagrafe = istanze.codicerichiedente

        INNER JOIN statiistanza ON
            statiistanza.idcomune        = istanze.idcomune
            AND statiistanza.software    = istanze.software
            AND statiistanza.codicestato = istanze.chiusura

        LEFT JOIN anagrafe tecnico ON
            tecnico.idcomune           = istanze.idcomune
            AND tecnico.codiceanagrafe = istanze.codiceprofessionista

        LEFT JOIN anagrafe azienda ON
            azienda.idcomune           = istanze.idcomune
            AND azienda.codiceanagrafe = istanze.codicetitolarelegale

        INNER JOIN movimenti_contromovimenti ON
            movimenti_contromovimenti.idcomune                  = movimenti.idcomune
            AND movimenti_contromovimenti.codicecontromovimento = movimenti.codicemovimento

        INNER JOIN movimenti movimentifatti ON
            movimentifatti.idcomune            = movimenti_contromovimenti.idcomune
            AND movimentifatti.codicemovimento = movimenti_contromovimenti.codicemovimento

        INNER JOIN tipimovimento tipomovimentofatto ON
            tipomovimentofatto.idcomune          = movimentifatti.idcomune
            AND tipomovimentofatto.tipomovimento = movimentifatti.tipomovimento

        LEFT JOIN istanzerichiedenti ON
            istanzerichiedenti.idcomune          = istanze.idcomune
            AND istanzerichiedenti.codiceistanza = istanze.codiceistanza

        LEFT JOIN anagrafe soggetti_collegati ON
            soggetti_collegati.idcomune           = istanzerichiedenti.idcomune
            AND soggetti_collegati.codiceanagrafe = istanzerichiedenti.codicerichiedente

        LEFT JOIN tipisoggetto ON
            tipisoggetto.idcomune               = istanzerichiedenti.idcomune
            AND tipisoggetto.codicetiposoggetto = istanzerichiedenti.codicetiposoggetto

        INNER JOIN alberoproc ON
            alberoproc.idcomune  = istanze.idcomune
            AND alberoproc.sc_id = istanze.codiceinterventoproc

        INNER JOIN comuniassociati ON
            comuniassociati.idcomune         = istanze.idcomune
            AND comuniassociati.codicecomune = istanze.codicecomune

        left join amministrazioni on 
            amministrazioni.idcomune = movimenti.idcomune and
            amministrazioni.codiceamministrazione  = movimenti.codiceamministrazione

        LEFT JOIN COMUNIASSOCIATIESCLUSIONI ON 
            COMUNIASSOCIATIESCLUSIONI.IDCOMUNE = ISTANZE.IDCOMUNE AND
            COMUNIASSOCIATIESCLUSIONI.CODICECOMUNE = ISTANZE.CODICECOMUNE AND
            COMUNIASSOCIATIESCLUSIONI.SOFTWARE = ISTANZE.SOFTWARE

where
    COMUNIASSOCIATIESCLUSIONI.IDCOMUNE IS NULL 
    AND COALESCE(comuniassociati.disattivo_fo, 0) = 0
    AND movimenti.idcomune = {this._database.QueryParameter("idcomune")}
    AND movimenti.data is null
    AND COALESCE(movimenti.flag_disabilitato,0) = 0
    AND tipimovimento.flag_soggetti_movimento = 0
    AND tipimovimento.fk_fo_soggettiesterni = {this._database.Specifics.QueryParameterName("foSoggettiEsterni")}
    AND COALESCE(statiistanza.flag_blocca_integr_online,0) = 0
    AND
    (
        COALESCE(alberoproc.flag_unica_domanda,0) = 0
        or
        (
            alberoproc.flag_unica_domanda = 1
            AND
            (
                alberoproc.fine_validita IS NULL
                OR alberoproc.fine_validita    > {this._database.Specifics.Sysdate}
            )
        )
    )");

                var filtriExtra = new List<KeyValuePair<string, string>>();

                if (!String.IsNullOrEmpty(filtri.CodSportello))
                {
                    sb.Append($" and istanze.software = {this._database.Specifics.QueryParameterName("software")} ");
                    filtriExtra.Add(new KeyValuePair<string, string>("software", filtri.CodSportello));
                }

                if (!String.IsNullOrEmpty(filtri.IdPratica))
                {
                    sb.Append($" and istanze.codiceistanza = {this._database.Specifics.QueryParameterName("codiceistanza")} ");
                    filtriExtra.Add(new KeyValuePair<string, string>("codiceistanza", filtri.IdPratica));
                }

                if (!String.IsNullOrEmpty(filtri.NumeroPratica))
                {
                    sb.Append($" and istanze.numeroistanza = {this._database.Specifics.QueryParameterName("numeroistanza")} ");
                    filtriExtra.Add(new KeyValuePair<string, string>("numeroistanza", filtri.NumeroPratica));
                }

                if (!String.IsNullOrEmpty(filtri.NumeroProtocollo))
                {
                    sb.Append($" and istanze.numeroprotocollo = {this._database.Specifics.QueryParameterName("numeroProtocollo")} ");
                    filtriExtra.Add(new KeyValuePair<string, string>("numeroProtocollo", filtri.NumeroProtocollo));
                }

                if (!String.IsNullOrEmpty(filtri.Stato))
                {
                    sb.AppendFormat($" and istanze.chiusura = {this._database.Specifics.QueryParameterName("stato")} ");
                    filtriExtra.Add(new KeyValuePair<string, string>("stato", filtri.Stato));
                }

                if (filtri.DatiAmministrazione != null && !String.IsNullOrEmpty(filtri.DatiAmministrazione.PartitaIva))
                {
                    sb.AppendFormat($" and amministrazioni.partitaiva = {this._database.Specifics.QueryParameterName("ammPartitaIva")} ");
                    filtriExtra.Add(new KeyValuePair<string, string>("ammPartitaIva", filtri.DatiAmministrazione.PartitaIva));
                }

                sb.Append(this.GetSqlSoggetto(filtri));

                var sql = sb.ToString();

                var scadenze = this._database.ExecuteReader(
                        sql,
                        mp =>
                        {
                            mp.AddParameter("idcomune", this._idComune);
                            mp.AddParameter("foSoggettiEsterni", (int)filtri.FiltroFoSoggettiEsterni);

                            filtriExtra.ForEach((item) => mp.AddParameter(item.Key, item.Value));
                        },
                        dr => new ElementoListaScadenzeDto
                        {
                            CodiceScadenza = dr.GetInt("CODICESCADENZA").Value,
                            DataScadenza = dr.GetDateTime("DATASCADENZA"),
                            DatiMovimento = $"{dr.GetDateTime("DATAMOVIMENTO")?.ToString("dd/MM/yyyy")} - {dr.GetString("DESCRMOVIMENTO")}",
                            DatiAzienda = this.GetDatiSoggetto(dr, "AZ_NOMINATIVO", "AZ_NOME", "AZ_CODICEFISCALE", "AZ_PARTITAIVA"),
                            DatiRichiedente = this.GetDatiSoggetto(dr, "RIC_NOMINATIVO", "RIC_NOME", "RIC_CODICEFISCALE", "RIC_PARTITAIVA"),
                            DatiTecnico = this.GetDatiSoggetto(dr, "TEC_NOMINATIVO", "TEC_NOME", "TEC_CODICEFISCALE", "TEC_PARTITAIVA"),
                            DescrMovimentoDaFare = dr.GetString("DESCRMOVIMENTODAFARE"),
                            DescrStatoIstanza = dr.GetString("DESCRSTATOISTANZA"),
                            ModuloSoftware = dr.GetString("MODULOSOFTWARE"),
                            NumeroIstanza = dr.GetString("NUMEROISTANZA"),
                            UuidIstanza = dr.GetString("UUID_ISTANZA"),
                            UuidMovimentoDaFare = dr.GetString("UUID_MOVIMENTO_DA_FARE"),
                            CodiceIstanza = dr.GetInt("CodiceIstanza").Value,
                            Localizzazione = ""
                        });

                if (filtri.FiltroFoSoggettiEsterni == FoTipiSoggettiEsterniEnum.FrontofficeAmministrazioni)
                {
                    foreach (var scadenza in scadenze)
                    {
                        scadenza.Localizzazione = this.GetLocalizzazioneByCodiceIstanza(scadenza.CodiceIstanza);
                    }
                }

                return scadenze;
            }
            catch (Exception ex)
            {
                this._log.Error($"Errore durante la lettura della lista scadenze con i filtri {filtri.ToXmlString()}: {ex}");
                throw;
            }
        }

        private string GetLocalizzazioneByCodiceIstanza(int codiceIstanza)
        {
            FormattableString sql = $@"SELECT 
	                                      istanzestradario.id                                             AS LOC_ID        
                                        , stradario.prefisso                                              AS LOC_PREFISSO
                                        , stradario.descrizione                                           AS LOC_DESCRIZIONE
                                        , istanzestradario.civico                                         AS LOC_CIVICO
                                        , istanzestradario.esponente                                      AS LOC_ESPONENTE
                                        , istanzestradario.colore                                         AS LOC_COLORE
                                        , istanzestradario.km                                             AS LOC_KM
                                        , istanzemappali.sezione                                          AS LOC_SEZIONE
                                        , istanzemappali.foglio                                           AS LOC_FOGLIO
                                        , istanzemappali.particella                                       AS LOC_PARTICELLA
                                        , istanzemappali.sub                                              AS LOC_SUB       
                                    FROM
	                                    istanzestradario      
       
                                           LEFT OUTER JOIN stradario ON
                                                stradario.idcomune = istanzestradario.idcomune AND
                                                stradario.codicestradario = istanzestradario.codicestradario

                                            LEFT OUTER JOIN istanzemappali ON
                                                istanzemappali.idcomune = istanzestradario.idcomune AND
                                                istanzemappali.fkidistanzestradario = istanzestradario.id
            
                                     WHERE
		                                    istanzestradario.primario = 1 AND
	                                        istanzestradario.idcomune = {this._idComune} AND
                                            istanzestradario.codiceistanza = {codiceIstanza} ";

            var results = this._database.ExecuteReader(sql, dr => new DatiLocalizzazione
            {
                Indirizzo = $"{dr.GetString("LOC_PREFISSO")} {dr.GetString("LOC_DESCRIZIONE")}",
                Civico = dr.GetString("LOC_CIVICO"),
                Esponente = dr.GetString("LOC_ESPONENTE"),
                Colore = dr.GetString("LOC_COLORE"),
                Km = dr.GetString("LOC_KM"),
                Sezione = dr.GetString("LOC_SEZIONE"),
                Foglio = dr.GetString("LOC_FOGLIO"),
                Particella = dr.GetString("LOC_PARTICELLA"),
                Sub = dr.GetString("LOC_SUB")
            });

            if (!results.Any())
            {
                return String.Empty;
            }

            return new StradarioFormatter().GeneraStringaIndirizzo(results.First());
        }

        private string EstraiLocalizzazioneDaRecordset(IDataReader dr)
        {
            var id = dr.GetInt("LOC_ID");

            if (!id.HasValue) return string.Empty;

            return new StradarioFormatter().GeneraStringaIndirizzo(new DatiLocalizzazione
            {
                Indirizzo = $"{dr.GetString("LOC_PREFISSO")} {dr.GetString("LOC_DESCRIZIONE")}",
                Civico = dr.GetString("LOC_CIVICO"),
                Esponente = dr.GetString("LOC_ESPONENTE"),
                Colore = dr.GetString("LOC_COLORE"),
                Km = dr.GetString("LOC_KM"),
                Sezione = dr.GetString("LOC_SEZIONE"),
                Foglio = dr.GetString("LOC_FOGLIO"),
                Particella = dr.GetString("LOC_PARTICELLA"),
                Sub = dr.GetString("LOC_SUB")
            });
        }

        private IEnumerable<ElementoListaScadenzeDto> GetListaScadenzeComeSoggettoCollegato(RichiestaListaScadenzeDto filtri)
        {
            // ATTENZIONE! NON è la stessa query di GetListaScadenzeComeRichiedente!!! 

            // Il movimento deve essere effettuabile dall'area riservata e non da un'amministrazione
            if (filtri.FiltroFoSoggettiEsterni == FoTipiSoggettiEsterniEnum.FrontofficeAmministrazioni)
            {
                return Enumerable.Empty<ElementoListaScadenzeDto>();
            }

            try
            {
                StringBuilder sb = new StringBuilder();

                sb.Append($@"
select distinct
    movimenti.CODICEMOVIMENTO                                       as CODICESCADENZA
    , istanze.CODICEISTANZA                                           as CODICEISTANZA
    , movimenti.DATA_SCADENZA                                         as DATASCADENZA
    , movimentifatti.DATA                                                  as DATAMOVIMENTO
    , COALESCE(MOVIMENTIFATTI.MOVIMENTO,TIPOMOVIMENTOFATTO.MOVIMENTO) as DESCRMOVIMENTO
    , azienda.NOMINATIVO                                              as AZ_NOMINATIVO
    , azienda.NOME                                                    as AZ_NOME
    , COALESCE(azienda.CODICEFISCALE,'')                              as AZ_CODICEFISCALE
    , COALESCE(azienda.PARTITAIVA,'')                                 as AZ_PARTITAIVA
    , anagrafe.NOMINATIVO                                             as RIC_NOMINATIVO
    , anagrafe.NOME                                                   as RIC_NOME
    , COALESCE(anagrafe.CODICEFISCALE,'')                             as RIC_CODICEFISCALE
    , COALESCE(anagrafe.PARTITAIVA,'')                                as RIC_PARTITAIVA
    , tecnico.NOMINATIVO                                              as TEC_NOMINATIVO
    , tecnico.NOME                                                    as TEC_NOME
    , COALESCE(tecnico.CODICEFISCALE,'')                              as TEC_CODICEFISCALE
    , COALESCE(tecnico.PARTITAIVA,'')                                 as TEC_PARTITAIVA
    , COALESCE(movimenti.MOVIMENTO,tipimovimento.MOVIMENTO)           as DESCRMOVIMENTODAFARE
    , statiistanza.STATO                                              as DESCRSTATOISTANZA
    , software.DESCRIZIONE                                            as MODULOSOFTWARE
    , istanze.NUMEROISTANZA                                           as NUMEROISTANZA
    , istanze.UUID                                                    as UUID_ISTANZA
    , movimenti_metadati.VALORE                                       as UUID_MOVIMENTO_DA_FARE

from
    movimenti

        inner join movimenti_metadati on
            movimenti_metadati.idcomune = movimenti.idcomune
            AND movimenti_metadati.CODICEMOVIMENTO = movimenti.codicemovimento
            AND movimenti_metadati.CHIAVE = 'UUID'

        INNER JOIN istanze ON
            istanze.idcomune          = movimenti.idcomune
            AND istanze.codiceistanza = movimenti.codiceistanza

        INNER JOIN tipimovimento ON
            tipimovimento.idcomune          = movimenti.idcomune
            AND tipimovimento.tipomovimento = movimenti.tipomovimento

        INNER JOIN tipiprocedure ON
            tipiprocedure.idcomune            = istanze.idcomune
            AND tipiprocedure.codiceprocedura = istanze.codiceprocedura

        INNER JOIN software ON
            software.codice = istanze.software

        INNER JOIN anagrafe ON
            anagrafe.idcomune           = istanze.idcomune
            AND anagrafe.codiceanagrafe = istanze.codicerichiedente

        INNER JOIN statiistanza ON
            statiistanza.idcomune        = istanze.idcomune
            AND statiistanza.software    = istanze.software
            AND statiistanza.codicestato = istanze.chiusura

        LEFT JOIN amministrazioni ON
            amministrazioni.idcomune                  = movimenti.idcomune
            AND amministrazioni.codiceamministrazione = movimenti.codiceamministrazione

        LEFT JOIN inventarioprocedimenti ON
            inventarioprocedimenti.idcomune             = movimenti.idcomune
            AND inventarioprocedimenti.codiceinventario = movimenti.codiceinventario

        LEFT JOIN comuni comuneresidenza ON
            comuneresidenza.codicecomune = anagrafe.comuneresidenza

        LEFT JOIN anagrafe tecnico ON
            tecnico.idcomune           = istanze.idcomune
            AND tecnico.codiceanagrafe = istanze.codiceprofessionista

        LEFT JOIN anagrafe azienda ON
            azienda.idcomune           = istanze.idcomune
            AND azienda.codiceanagrafe = istanze.codicetitolarelegale

        INNER JOIN movimenti_contromovimenti ON
            movimenti_contromovimenti.idcomune                  = movimenti.idcomune
            AND movimenti_contromovimenti.codicecontromovimento = movimenti.codicemovimento

        INNER JOIN movimenti movimentifatti ON
            movimentifatti.idcomune            = movimenti_contromovimenti.idcomune
            AND movimentifatti.codicemovimento = movimenti_contromovimenti.codicemovimento

        INNER JOIN tipimovimento tipomovimentofatto ON
            tipomovimentofatto.idcomune          = movimentifatti.idcomune
            AND tipomovimentofatto.tipomovimento = movimentifatti.tipomovimento

        LEFT JOIN istanzerichiedenti ON
            istanzerichiedenti.idcomune          = istanze.idcomune
            AND istanzerichiedenti.codiceistanza = istanze.codiceistanza

        LEFT JOIN anagrafe soggetti_collegati ON
            soggetti_collegati.idcomune           = istanzerichiedenti.idcomune
            AND soggetti_collegati.codiceanagrafe = istanzerichiedenti.codicerichiedente

        LEFT JOIN tipisoggetto ON
            tipisoggetto.idcomune               = istanzerichiedenti.idcomune
            AND tipisoggetto.codicetiposoggetto = istanzerichiedenti.codicetiposoggetto

        INNER JOIN tipimov_tipisoggetto ON
                tipimov_tipisoggetto.idcomune = istanzerichiedenti.idcomune and
                tipimov_tipisoggetto.fk_idtiposoggetto = istanzerichiedenti.codicetiposoggetto and
                tipimov_tipisoggetto.fk_idtipomovimento = movimenti.tipomovimento

        INNER JOIN alberoproc ON
            alberoproc.idcomune  = istanze.idcomune
            AND alberoproc.sc_id = istanze.codiceinterventoproc

        INNER JOIN comuniassociati ON
            comuniassociati.idcomune         = istanze.idcomune
            AND comuniassociati.codicecomune = istanze.codicecomune

        LEFT JOIN COMUNIASSOCIATIESCLUSIONI ON 
            COMUNIASSOCIATIESCLUSIONI.IDCOMUNE = ISTANZE.IDCOMUNE AND
            COMUNIASSOCIATIESCLUSIONI.CODICECOMUNE = ISTANZE.CODICECOMUNE AND
            COMUNIASSOCIATIESCLUSIONI.SOFTWARE = ISTANZE.SOFTWARE

where
    COMUNIASSOCIATIESCLUSIONI.IDCOMUNE IS NULL 
    AND COALESCE(comuniassociati.disattivo_fo, 0) = 0
    AND movimenti.idcomune = {this._database.QueryParameter("idcomune")}
    AND movimenti.data is null
    AND COALESCE(movimenti.flag_disabilitato,0) = 0
    AND tipimovimento.flag_soggetti_movimento = 1
    AND tipimovimento.fk_fo_soggettiesterni = 1

    AND COALESCE(statiistanza.flag_blocca_integr_online,0) = 0
    AND
    (
        COALESCE(alberoproc.flag_unica_domanda,0) = 0
        or
        (
            alberoproc.flag_unica_domanda = 1
            AND
            (
                alberoproc.fine_validita IS NULL
                OR alberoproc.fine_validita > {this._database.Specifics.Sysdate}
            )
        )
    )");


                var filtriExtra = new List<KeyValuePair<string, string>>();

                if (!String.IsNullOrEmpty(filtri.CodiceFiscaleSoggetto))
                {
                    sb.Append($" and (soggetti_collegati.codicefiscale = {this._database.Specifics.QueryParameterName("codicefiscale1")} " +
                                        $" or soggetti_collegati.partitaiva = {this._database.Specifics.QueryParameterName("codicefiscale2")}) and " +
                                        $" tipisoggetto.flag_fo_mod_pratica = 1 ");

                    filtriExtra.Add(new KeyValuePair<string, string>("codicefiscale1", filtri.CodiceFiscaleSoggetto));
                    filtriExtra.Add(new KeyValuePair<string, string>("codicefiscale2", filtri.CodiceFiscaleSoggetto));
                }

                if (!String.IsNullOrEmpty(filtri.PartitaIvaSoggetto))
                {
                    sb.Append($" and (soggetti_collegati.codicefiscale = {this._database.Specifics.QueryParameterName("partitaiva1")} " +
                                        $" or soggetti_collegati.partitaiva = {this._database.Specifics.QueryParameterName("partitaiva2")}) anc" +
                                        $" tipisoggetto.flag_fo_mod_pratica = 1 ");

                    filtriExtra.Add(new KeyValuePair<string, string>("partitaiva1", filtri.PartitaIvaSoggetto));
                    filtriExtra.Add(new KeyValuePair<string, string>("partitaiva2", filtri.PartitaIvaSoggetto));
                }

                if (!String.IsNullOrEmpty(filtri.CodSportello))
                {
                    sb.Append($" and istanze.software = {this._database.Specifics.QueryParameterName("software")} ");
                    filtriExtra.Add(new KeyValuePair<string, string>("software", filtri.CodSportello));
                }

                if (!String.IsNullOrEmpty(filtri.IdPratica))
                {
                    sb.Append($" and istanze.codiceistanza = {this._database.Specifics.QueryParameterName("codiceistanza")} ");
                    filtriExtra.Add(new KeyValuePair<string, string>("codiceistanza", filtri.IdPratica));
                }

                if (!String.IsNullOrEmpty(filtri.NumeroPratica))
                {
                    sb.Append($" and istanze.numeroistanza = {this._database.Specifics.QueryParameterName("numeroistanza")} ");
                    filtriExtra.Add(new KeyValuePair<string, string>("numeroistanza", filtri.NumeroPratica));
                }

                if (!String.IsNullOrEmpty(filtri.NumeroProtocollo))
                {
                    sb.Append($" and istanze.numeroprotocollo = {this._database.Specifics.QueryParameterName("numeroProtocollo")} ");
                    filtriExtra.Add(new KeyValuePair<string, string>("numeroProtocollo", filtri.NumeroProtocollo));
                }

                if (!String.IsNullOrEmpty(filtri.Stato))
                {
                    sb.AppendFormat($" and istanze.chiusura = {this._database.Specifics.QueryParameterName("stato")} ");
                    filtriExtra.Add(new KeyValuePair<string, string>("stato", filtri.Stato));
                }

                if (filtri.DatiAmministrazione != null && !String.IsNullOrEmpty(filtri.DatiAmministrazione.PartitaIva))
                {
                    sb.AppendFormat($" and amministrazioni.PARTITAIVA = {this._database.Specifics.QueryParameterName("ammPartitaIva")} ");
                    filtriExtra.Add(new KeyValuePair<string, string>("ammPartitaIva", filtri.DatiAmministrazione.PartitaIva));
                }

                var sql = sb.ToString();

                var scadenze = this._database.ExecuteReader(
                        sql,
                        mp =>
                        {
                            mp.AddParameter("idcomune", this._idComune);


                            filtriExtra.ForEach((item) => mp.AddParameter(item.Key, item.Value));
                        },
                        dr => new ElementoListaScadenzeDto
                        {
                            CodiceScadenza = dr.GetInt("CODICESCADENZA").Value,
                            DataScadenza = dr.GetDateTime("DATASCADENZA"),
                            DatiMovimento = $"{dr.GetDateTime("DATAMOVIMENTO")?.ToString("dd/MM/yyyy")} - {dr.GetString("DESCRMOVIMENTO")}",
                            DatiAzienda = this.GetDatiSoggetto(dr, "AZ_NOMINATIVO", "AZ_NOME", "AZ_CODICEFISCALE", "AZ_PARTITAIVA"),
                            DatiRichiedente = this.GetDatiSoggetto(dr, "RIC_NOMINATIVO", "RIC_NOME", "RIC_CODICEFISCALE", "RIC_PARTITAIVA"),
                            DatiTecnico = this.GetDatiSoggetto(dr, "TEC_NOMINATIVO", "TEC_NOME", "TEC_CODICEFISCALE", "TEC_PARTITAIVA"),
                            DescrMovimentoDaFare = dr.GetString("DESCRMOVIMENTODAFARE"),
                            DescrStatoIstanza = dr.GetString("DESCRSTATOISTANZA"),
                            ModuloSoftware = dr.GetString("MODULOSOFTWARE"),
                            NumeroIstanza = dr.GetString("NUMEROISTANZA"),
                            UuidIstanza = dr.GetString("UUID_ISTANZA"),
                            UuidMovimentoDaFare = dr.GetString("UUID_MOVIMENTO_DA_FARE"),
                            CodiceIstanza = dr.GetInt("CodiceIstanza").Value
                        });

                if (filtri.FiltroFoSoggettiEsterni == FoTipiSoggettiEsterniEnum.FrontofficeAmministrazioni)
                {
                    foreach (var scadenza in scadenze)
                    {
                        scadenza.Localizzazione = this.GetLocalizzazioneByCodiceIstanza(scadenza.CodiceIstanza);
                    }
                }

                return scadenze;
            }
            catch (Exception ex)
            {
                this._log.Error($"Errore durante la lettura della lista scadenze con i filtri {filtri.ToXmlString()}: {ex}");
                throw;
            }
        }

        private string GetDatiSoggetto(IDataReader dr, string campoNominativo, string campoNome, string campoCodiceFiscale, string campoPartitaIva)
        {
            var codiceFiscale = dr.GetString(campoCodiceFiscale);
            var nominativo = dr.GetString(campoNominativo);
            var nome = String.IsNullOrEmpty(campoNome) ? "" : dr.GetString(campoNome);
            var partitaIva = dr.GetString(campoPartitaIva);

            var datiAz = new StringBuilder(nominativo);

            if (!String.IsNullOrEmpty(nome))
            {
                datiAz.Append($" {nome}");
            }

            if (!String.IsNullOrEmpty(codiceFiscale))
            {
                datiAz.Append($" [CF: {codiceFiscale}]");
            }

            if (!String.IsNullOrEmpty(partitaIva))
            {
                datiAz.Append(" [P.IVA: {partitaIva}]");
            }

            return datiAz.ToString();
        }


        /// <summary>
        /// Ottiene i dati di una scadenza a partire dall'id univoco della stessa
        /// </summary>
        /// <param name="codiceScadenza">Id univoco della scadenza</param>
        /// <returns>Scadenza</returns>
        public ScadenzaDto GetScadenza(int codiceScadenza)
        {
            try
            {
                var sql = $@"select 
							  vw_scadenzario.AMM_AMMINISTRAZIONE,
							  vw_scadenzario.AMM_PARTITAIVA,
							  vw_scadenzario.AZ_CODICEFISCALE,
							  vw_scadenzario.AZ_NOMINATIVO,
							  vw_scadenzario.AZ_PARTITAIVA,
							  vw_scadenzario.IDCOMUNE,
							  vw_scadenzario.CODICEISTANZA,
							  vw_scadenzario.CODMOVIMENTO,
							  vw_scadenzario.CODMOVIMENTODAFARE,
							  vw_scadenzario.SOFTWARE,
							  vw_scadenzario.CODSTATOISTANZA,
							  vw_scadenzario.DATAMOVIMENTO,
							  vw_scadenzario.DATAPROTOCOLLO,
							  vw_scadenzario.DATASCADENZASTR,
							  vw_scadenzario.DESCRMOVIMENTO,
							  vw_scadenzario.DESCRMOVIMENTODAFARE,
							  vw_scadenzario.DESCRSTATOISTANZA,
							  vw_scadenzario.MODULOSOFTWARE,
							  vw_scadenzario.NUMEROISTANZA,
							  vw_scadenzario.NUMEROPROTOCOLLO,
							  vw_scadenzario.PROCEDIMENTO,
							  vw_scadenzario.PROCEDURA,
							  vw_scadenzario.RESPONSABILE,
							  vw_scadenzario.RIC_CAP,
							  vw_scadenzario.RIC_CITTA,
							  vw_scadenzario.RIC_CODICEFISCALE,
							  vw_scadenzario.RIC_INDIRIZZO,
							  vw_scadenzario.RIC_LOCALITA,
							  vw_scadenzario.RIC_NOMINATIVO,
							  vw_scadenzario.RIC_PARTITAIVA,
							  vw_scadenzario.RIC_PROVINCIA,
							  vw_scadenzario.TEC_CODICEFISCALE,
							  vw_scadenzario.TEC_NOMINATIVO,
							  vw_scadenzario.TEC_PARTITAIVA,
							  vw_scadenzario.CODICEAMMINISTRAZIONE,
							  vw_scadenzario.CODICEINVENTARIO,
							  vw_scadenzario.CODICESCADENZA,
							  istanze.UUID

                            from 
                                vw_scadenzario,
                                istanze
                            where 
                                istanze.idcomune = vw_scadenzario.idcomune AND
                                istanze.codiceistanza = vw_scadenzario.codiceistanza and
                                vw_scadenzario.idcomune={this._database.Specifics.QueryParameterName("idComune")} and 
                                vw_scadenzario.CODICESCADENZA = {this._database.Specifics.QueryParameterName("codiceScadenza")}";

                return this._database.ExecuteReader(sql,
                    mp =>
                    {
                        mp.AddParameter("idComune", this._idComune);
                        mp.AddParameter("codiceScadenza", codiceScadenza);
                    },
                    dr => new ScadenzaDto
                    {
                        CodiceScadenza = dr.GetString("CODICESCADENZA"),
                        AmmAmministrazione = dr.GetString("AMM_AMMINISTRAZIONE"),
                        AmmPartitaiva = dr.GetString("AMM_PARTITAIVA"),
                        DatiAzienda = this.GetDatiSoggetto(dr, "AZ_NOMINATIVO", "", "AZ_CODICEFISCALE", "AZ_PARTITAIVA"),
                        CodEnte = dr.GetString("IDCOMUNE"),
                        CodiceIstanza = dr.GetString("CODICEISTANZA"),
                        CodMovimento = dr.GetString("CODMOVIMENTO"),
                        CodMovimentoDaFare = dr.GetString("CODMOVIMENTODAFARE"),
                        CodSportello = dr.GetString("SOFTWARE"),
                        CodStatoIstanza = dr.GetString("CODSTATOISTANZA"),
                        DatiMovimento = $"{dr.GetString("DATAMOVIMENTO")} - {dr.GetString("DESCRMOVIMENTO")}",
                        DataProtocollo = dr.GetString("DATAPROTOCOLLO"),
                        DataScadenza = dr.GetString("DATASCADENZASTR"),
                        DescrMovimentoDaFare = dr.GetString("DESCRMOVIMENTODAFARE"),
                        DescrStatoIstanza = dr.GetString("DESCRSTATOISTANZA"),
                        ModuloSoftware = dr.GetString("MODULOSOFTWARE"),
                        NumeroIstanza = dr.GetString("NUMEROISTANZA"),
                        NumeroProtocollo = dr.GetString("NUMEROPROTOCOLLO"),
                        Procedimento = dr.GetString("PROCEDIMENTO"),
                        Procedura = dr.GetString("PROCEDURA"),
                        Responsabile = dr.GetString("RESPONSABILE"),
                        DatiRichiedente = this.GetDatiSoggetto(dr, "RIC_NOMINATIVO", "", "RIC_CODICEFISCALE", "RIC_PARTITAIVA"),
                        DatiTecnico = this.GetDatiSoggetto(dr, "TEC_NOMINATIVO", "", "TEC_CODICEFISCALE", "TEC_PARTITAIVA"),
                        CodiceAmministrazione = dr.GetString("CODICEAMMINISTRAZIONE"),
                        CodiceInventario = dr.GetString("CODICEINVENTARIO"),
                        UuidIstanza = dr.GetString("UUID"),

                    }).FirstOrDefault();
            }
            catch (Exception ex)
            {
                this._log.Error($"Errore durante la ricerca del dettaglio della scadenza per l'id {codiceScadenza}: {ex}");
                throw;
            }
        }

        private string GetSqlSoggetto(RichiestaListaScadenzeDto filtri)
        {
            if (String.IsNullOrEmpty(filtri.CodiceFiscaleSoggetto) && String.IsNullOrEmpty(filtri.PartitaIvaSoggetto))
            {
                return "";
            }

            StringBuilder ret = new StringBuilder();

            ret.Append("and ((");

            if (!String.IsNullOrEmpty(filtri.PartitaIvaSoggetto))
            {
                string piva = filtri.PartitaIvaSoggetto;

                var condizioni = new[]
                {
                    this.CreaFiltroPerCodiceFiscalePartitaIva(piva, "anagrafe.PARTITAIVA", "anagrafe.CODICEFISCALE", true),
                    this.CreaFiltroPerCodiceFiscalePartitaIva(piva, "tecnico.PARTITAIVA", "tecnico.CODICEFISCALE", true),
                    this.CreaFiltroPerCodiceFiscalePartitaIva(piva, "azienda.PARTITAIVA", "azienda.CODICEFISCALE", true)
                };

                ret.Append(String.Join(" OR ", condizioni));
            }

            if (!String.IsNullOrEmpty(filtri.CodiceFiscaleSoggetto))
            {
                string cf = filtri.CodiceFiscaleSoggetto;

                var condizioni = new[]
                {
                    this.CreaFiltroPerCodiceFiscalePartitaIva(cf, "anagrafe.PARTITAIVA", "anagrafe.CODICEFISCALE", true),
                    this.CreaFiltroPerCodiceFiscalePartitaIva(cf, "tecnico.PARTITAIVA", "tecnico.CODICEFISCALE", true),
                    this.CreaFiltroPerCodiceFiscalePartitaIva(cf, "azienda.PARTITAIVA", "azienda.CODICEFISCALE", true)
                };

                ret.Append(String.Join(" OR ", condizioni));
            }

            ret.Append(" )");


            // Filtri soggetti che possono gestire la pratica
            // Questo verrà modificato a breve
            var filtroCfPIva = String.IsNullOrEmpty(filtri.CodiceFiscaleSoggetto) ?
                                    filtri.PartitaIvaSoggetto :
                                    filtri.CodiceFiscaleSoggetto;

            ret.Append(
                $" OR (" +
                $"{this.CreaFiltroPerCodiceFiscalePartitaIva(filtroCfPIva, "soggetti_collegati.codicefiscale", "soggetti_collegati.partitaiva", true)}" +
                " AND tipisoggetto.flag_fo_mod_pratica = 1)"
            );

            ret.Append(" )");

            return ret.ToString();
        }

        protected string CreaFiltroPerCodiceFiscalePartitaIva(string valore, /*string password , string campoPassword,*/ string colonna1, string colonna2, bool cercaInEntrambi)
        {
            StringBuilder sb = new StringBuilder();
            sb.Append(" ( ");
            sb.Append($"({colonna1}='{this.SafeString(valore)}')");

            if (cercaInEntrambi)
            {
                sb.Append($" OR ( {colonna2}='{this.SafeString(valore)}')");
            }

            sb.Append(") ");
            return sb.ToString();
        }

        /// <summary>
        /// Ripulisce una stringa per utilizzarla come valore in una queri (x es sostituisce l'apice con il doppio apice)
        /// </summary>
        /// <param name="inVal">valore da ripulire</param>
        /// <returns>Stringa "ripulita"</returns>
        protected string SafeString(string inVal)
        {
            if (inVal == null) return "";
            return inVal.Trim().Replace("'", "''");
        }


    }
}
