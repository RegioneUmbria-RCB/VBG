namespace Init.SIGePro.Sit
{
    using Init.SIGePro.Exceptions.SIT;
    using Init.SIGePro.Sit.Data;
    using Init.SIGePro.Sit.Manager;
    using Init.SIGePro.Sit.Modena;
    using Init.SIGePro.Sit.Modena.ValidazioneMappaliUrbani;
    using Init.SIGePro.Sit.ValidazioneFormale;
    using Init.SIGePro.Verticalizzazioni;
    using Init.Utils;
    using log4net;
    using PersonalLib2.Data;
    using System;
    using System.Collections.Generic;
    using System.Data;
    using System.Linq;

    public class SIT_MODENA : SitBase
    {
        private enum NomiCampi
        {
            CodiceVia,
            Civico,
            Esponente,
            CodCivico,
            Cap,
            Fabbricato,
            Sezione,
            Foglio,
            Particella,
            Subalterno,
            Scala,
            Interno,
            EsponenteInterno,
            DataFine,
            Frazione,
            Circoscrizione
        }

        private static class NomiQualificatiCampi
        {
            public const string CodiceVia = "AE_CIVICO_CON_CIVKEY.COD_VIA";
            public const string Civico = "AE_CIVICO_CON_CIVKEY.NUMERO";
            public const string Esponente = "AE_CIVICO_CON_CIVKEY.ESPONENTE";
            public const string CodCivico = "AE_CIVICO_CON_CIVKEY.CIVKEY";
            public const string Cap = "AE_CIVICO_CON_CIVKEY.CAP";
            public const string Fabbricato = "AE_CIVICO_CON_CIVKEY.ID_EDIFICIO";
            public const string Sezione = "AE_CIVICO_CON_CIVKEY.G_SEZIONE";
            public const string Foglio = "AE_CIVICO_CON_CIVKEY.G_FOGLIO";
            public const string Particella = "AE_CIVICO_CON_CIVKEY.G_NUMERO";
            public const string Scala = "AE_INTERNO_CON_INTKEY.SCALA";
            public const string Interno = "AE_INTERNO_CON_INTKEY.NUMERO";
            public const string EsponenteInterno = "AE_INTERNO_CON_INTKEY.ESPONENTE";
            public const string DataFine = "AE_CIVICO_CON_CIVKEY.DATA_FINE";
            public const string Frazione = "AE_CIVICO_CON_CIVKEY.ZONA";
            public const string Circoscrizione = "AE_CIVICO_CON_CIVKEY.CIRCOS";
        }

        private static class NomiTabelle
        {
            public const string AE_CIVICO_CON_CIVKEY = "AE_CIVICO_CON_CIVKEY";
            public const string AE_INTERNO_CON_INTKEY = "AE_INTERNO_CON_INTKEY";
            public const string Tutte = "AE_CIVICO_CON_CIVKEY,AE_INTERNO_CON_INTKEY";
        }

        private static class TipoCatasto
        {
            public const string Fabbricati = "F";
            public const string Terreni = "T";
        }

        public SIT_MODENA()
            : base(new ValidazioneFormaleTramiteCodiceCivicoService())
        {
            this._registroNomiCampi = new Dictionary<NomiCampi, string>{
                { NomiCampi.CodiceVia , NomiQualificatiCampi.CodiceVia},
                { NomiCampi.Civico , NomiQualificatiCampi.Civico},
                { NomiCampi.Esponente , NomiQualificatiCampi.Esponente},
                { NomiCampi.CodCivico, NomiQualificatiCampi.CodCivico},
                { NomiCampi.Cap , NomiQualificatiCampi.Cap},
                { NomiCampi.Fabbricato , NomiQualificatiCampi.Fabbricato},
                { NomiCampi.Sezione , NomiQualificatiCampi.Sezione},
                { NomiCampi.Foglio , NomiQualificatiCampi.Foglio},
                { NomiCampi.Particella , NomiQualificatiCampi.Particella},
                { NomiCampi.Scala , NomiQualificatiCampi.Scala},
                { NomiCampi.Interno , NomiQualificatiCampi.Interno},
                { NomiCampi.EsponenteInterno , NomiQualificatiCampi.EsponenteInterno},
                { NomiCampi.DataFine , NomiQualificatiCampi.DataFine},
                { NomiCampi.Frazione , NomiQualificatiCampi.Frazione},
                { NomiCampi.Circoscrizione , NomiQualificatiCampi.Circoscrizione},
            };

        }

        protected VerticalizzazioneSitCore _verticalizzazione;

        private readonly Dictionary<NomiCampi, string> _registroNomiCampi;
        private string _lastCommandText = "";
        private readonly ILog _log = LogManager.GetLogger(typeof(SIT_CORE));

        #region Utility
        public override void SetupVerticalizzazione()
        {
            try
            {

                if (this._verticalizzazione == null)
                {
                    this._verticalizzazione = this.CaricaParametriVerticalizzazione();
                }

                if (!this._verticalizzazione.Attiva)
                {
                    this._verticalizzazione = null;
                    throw new Exception("La verticalizzazione SIT_CORE non è attiva.\r\n");
                }

                var provider = (ProviderType)Enum.Parse(typeof(ProviderType), this._verticalizzazione.Provider, false);
                this.InternalDatabaseConnection = new DataBase(this._verticalizzazione.Connectionstring, provider);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore generato durante la lettura della verticalizzazione SIT_CORE. Metodo: GetParametriFromVertSITCORE, modulo: SitCore. " + ex.Message + "\r\n");
            }
        }

        protected virtual VerticalizzazioneSitCore CaricaParametriVerticalizzazione()
        {
            return new VerticalizzazioneSitCore(this.IdComuneAlias, this.Software);
        }

        /// <summary>
        /// Metodo usato per leggere i parametri della verticalizzazione SIT_CORE
        /// </summary>
        private void GetParametriFromVertSITCORE()
        {

        }

        private string GetErroreDiValidazione(NomiCampi sField)
        {
            switch (sField)
            {
                case NomiCampi.Sezione:
                    return "La sezione " + this.DataSit.Sezione + " non è valida per i dati inseriti";

                case NomiCampi.Foglio:
                    return "Il foglio " + this.DataSit.Foglio + " non è valido per i dati inseriti";

                case NomiCampi.Particella:
                    return "La particella " + this.DataSit.Particella + " non è valida per i dati inseriti";

                //		case NomiCampi.Cap:
                //			return "Il CAP " + DataSit.CAP + " non è valido per i dati inseriti";

                case NomiCampi.Civico:
                    return "Il civico " + this.DataSit.Civico + " non è valido per i dati inseriti";

                case NomiCampi.Esponente:
                    return "L'esponente " + this.DataSit.Esponente + " non è valido per i dati inseriti";

                case NomiCampi.Fabbricato:
                    return "Il fabbricato " + this.DataSit.Fabbricato + " non è valido per i dati inseriti";

                case NomiCampi.Scala:
                    return "La scala " + this.DataSit.Scala + " non è valida per i dati inseriti";

                case NomiCampi.Interno:
                    return "L'interno " + this.DataSit.Interno + " non è valido per i dati inseriti";

                case NomiCampi.EsponenteInterno:
                    return "L'esponente dell'interno " + this.DataSit.EsponenteInterno + " non è valido per i dati inseriti";

                case NomiCampi.Frazione:
                    return "La frazione " + this.DataSit.Frazione + " non è valida per i dati inseriti";

                case NomiCampi.Circoscrizione:
                    return "La circoscrizione " + this.DataSit.Frazione + " non è valida per i dati inseriti";
                case NomiCampi.Subalterno:
                    return $"Il subalterno {this.DataSit.Sub} non è valido per i dati inseriti";
            }

            return String.Empty;
        }

        private string GetMessaggioDiErrore(NomiCampi sField)
        {
            string sValue = string.Empty;

            switch (sField)
            {
                case NomiCampi.Foglio:
                    sValue = "Non è possibile ottenere la lista dei fogli per insufficienza di dati: il catasto e la sezione devono essere forniti";
                    break;
                case NomiCampi.Particella:
                    sValue = "Non è possibile ottenere la lista delle particelle per insufficienza di dati: il catasto ed il fabbricato/sezione,foglio devono essere forniti";
                    break;
                case NomiCampi.Sezione:
                    sValue = "Non è possibile ottenere la lista delle sezioni per insufficienza di dati: il catasto deve essere fornito";
                    break;
                case NomiCampi.Civico:
                    sValue = "Non è possibile ottenere la lista dei civici per insufficienza di dati: il fabbricato/via devono essere forniti";
                    break;
                case NomiCampi.Esponente:
                    sValue = "Non è possibile ottenere la lista degli esponenti per insufficienza di dati: il fabbricato/via ed il civico devono essere forniti";
                    break;
                case NomiCampi.Fabbricato:
                    sValue = "Non è possibile ottenere la lista dei fabbricati per insufficienza di dati: la via/dati catastali devono essere forniti";
                    break;
                case NomiCampi.Scala:
                    sValue = "Non è possibile ottenere la lista delle scale per insufficienza di dati: il codice civico/fabbricato,civico,esponente deve essere fornito";
                    break;
                case NomiCampi.Interno:
                    sValue = "Non è possibile ottenere la lista degli interni per insufficienza di dati: il codice civico/fabbricato,civico,esponente devono essere forniti";
                    break;
                case NomiCampi.EsponenteInterno:
                    sValue = "Non è possibile ottenere la lista degli esponenti degli interni per insufficienza di dati: il codice civico,interno/fabbricato,civico,esponente,interno devono essere forniti";
                    break;
                case NomiCampi.Frazione:
                    sValue = "Non è possibile ottenere la lista delle frazioni per insufficienza di dati: il codice civico,interno/fabbricato,civico,esponente,interno devono essere forniti";
                    break;
                case NomiCampi.Circoscrizione:
                    sValue = "Non è possibile ottenere la lista delle circoscrizioni per insufficienza di dati: il codice civico,interno/fabbricato,civico,esponente,interno devono essere forniti";
                    break;
            }

            return sValue;
        }

        private string GetAeCivicoConCivkey(NomiCampi campo, TipoQuery tipoQuery)
        {
            try
            {
                if (this._log.IsDebugEnabled)
                    this._log.DebugFormat("GetAeCivicoConCivkey: Lettura del campo {0} con tipo query {1}: dati struttura sit {2}", campo, tipoQuery, StreamUtils.SerializeClass(this.DataSit));

                var datiImmobileValorizzati = !(string.IsNullOrEmpty(this.DataSit.Scala) && string.IsNullOrEmpty(this.DataSit.Interno) && string.IsNullOrEmpty(this.DataSit.EsponenteInterno));

                // Se cerco di leggere il codice via 
                if (campo == NomiCampi.CodiceVia || this.UsaCatastoFabbricati(this.DataSit.TipoCatasto))
                {
                    if (!datiImmobileValorizzati)
                        return this.GetElemento(campo, NomiTabelle.AE_CIVICO_CON_CIVKEY, tipoQuery);

                    return this.GetElemento(campo, NomiTabelle.Tutte, tipoQuery);
                }

                if (campo == NomiCampi.Sezione || campo == NomiCampi.Foglio || campo == NomiCampi.Particella)
                {
                    if (this.IsCampiToponomasticaImmobileVuoti())
                        return this.GetElemento(campo, NomiTabelle.AE_CIVICO_CON_CIVKEY, tipoQuery);

                    throw new CatastoException(this.GetErroreDiValidazione(campo));
                }

                throw new CatastoException(this.GetErroreDiValidazione(campo));
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore in GetAeCivicoConCivkey: {0}\r\nStruttura sit: {1}", ex.ToString(), StreamUtils.SerializeClass(this.DataSit));

                throw;
            }
        }

        private bool UsaCatastoFabbricati(string tipoCatasto)
        {
            return string.IsNullOrEmpty(tipoCatasto) || tipoCatasto == TipoCatasto.Fabbricati;
        }

        private string GetAeInternoConIntkey(NomiCampi campo, TipoQuery tipoQuery)
        {
            try
            {
                if (this._log.IsDebugEnabled)
                    this._log.DebugFormat("GetAeInternoConIntkey: Lettura del campo {0} con tipo query {1}: dati struttura sit {2}", campo, tipoQuery, StreamUtils.SerializeClass(this.DataSit));

                var altriDatiNonSpecificati = string.IsNullOrEmpty(this.DataSit.CodVia) &&
                                              string.IsNullOrEmpty(this.DataSit.Civico) &&
                                              string.IsNullOrEmpty(this.DataSit.Esponente) &&
                                              string.IsNullOrEmpty(this.DataSit.CAP) &&
                                              string.IsNullOrEmpty(this.DataSit.Fabbricato) &&
                                              string.IsNullOrEmpty(this.DataSit.Sezione) &&
                                              string.IsNullOrEmpty(this.DataSit.Foglio) &&
                                              string.IsNullOrEmpty(this.DataSit.Particella);

                if (this.UsaCatastoFabbricati(this.DataSit.TipoCatasto))
                {
                    if (altriDatiNonSpecificati)
                        return this.GetElemento(campo, NomiTabelle.AE_INTERNO_CON_INTKEY, tipoQuery);

                    return this.GetElemento(campo, NomiTabelle.Tutte, tipoQuery);
                }

                throw new CatastoException(this.GetErroreDiValidazione(campo));
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore in GetAeInternoConIntkey: {0}\r\nStruttura sit: {1}", ex.ToString(), StreamUtils.SerializeClass(this.DataSit));

                throw;
            }

        }

        private RetSit ElencoAeCivicoConCivkey(NomiCampi campoDaLeggere)
        {
            try
            {
                if (this._log.IsDebugEnabled)
                    this._log.DebugFormat("ElencoAeCivicoConCivkey: Lettura del campo {0}  dati struttura sit {1}", campoDaLeggere, StreamUtils.SerializeClass(this.DataSit));

                var isCampoMappali = campoDaLeggere == NomiCampi.Sezione ||
                                     campoDaLeggere == NomiCampi.Foglio ||
                                     campoDaLeggere == NomiCampi.Particella;

                if (isCampoMappali)
                {
                    if (string.IsNullOrEmpty(this.DataSit.TipoCatasto))
                        throw new CatastoException(this.GetMessaggioDiErrore(campoDaLeggere));

                    if (this.DataSit.TipoCatasto == TipoCatasto.Fabbricati)
                    {
                        if (string.IsNullOrEmpty(this.DataSit.Scala) && string.IsNullOrEmpty(this.DataSit.Interno) && string.IsNullOrEmpty(this.DataSit.EsponenteInterno))
                            return this.GetElenco(campoDaLeggere, NomiTabelle.AE_CIVICO_CON_CIVKEY);

                        return this.GetElenco(campoDaLeggere, NomiTabelle.Tutte);
                    }

                    if (this.IsCampiToponomasticaImmobileVuoti())
                        return this.GetElenco(campoDaLeggere, NomiTabelle.AE_CIVICO_CON_CIVKEY);

                    return new RetSit(true);
                }

                if (this.UsaCatastoFabbricati(this.DataSit.TipoCatasto))
                {
                    if (string.IsNullOrEmpty(this.DataSit.Scala) && string.IsNullOrEmpty(this.DataSit.Interno) && string.IsNullOrEmpty(this.DataSit.EsponenteInterno))
                        return this.GetElenco(campoDaLeggere, NomiTabelle.AE_CIVICO_CON_CIVKEY);

                    return this.GetElenco(campoDaLeggere, NomiTabelle.Tutte);
                }

                return new RetSit(true);
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore in ElencoAeCivicoConCivkey: {0}\r\nStruttura sit: {1}", ex.ToString(), StreamUtils.SerializeClass(this.DataSit));

                throw;
            }
        }

        private RetSit ElencoAeInternoConIntkey(NomiCampi sField)
        {
            try
            {
                if (this._log.IsDebugEnabled)
                    this._log.DebugFormat("ElencoAeInternoConIntkey: Lettura del campo {0}  dati struttura sit {1}", sField, StreamUtils.SerializeClass(this.DataSit));

                if (this.UsaCatastoFabbricati(this.DataSit.TipoCatasto))
                {
                    if (string.IsNullOrEmpty(this.DataSit.CodVia) && string.IsNullOrEmpty(this.DataSit.Civico) && string.IsNullOrEmpty(this.DataSit.Esponente) && string.IsNullOrEmpty(this.DataSit.CAP) && string.IsNullOrEmpty(this.DataSit.Fabbricato) && string.IsNullOrEmpty(this.DataSit.Sezione) && string.IsNullOrEmpty(this.DataSit.Foglio) && string.IsNullOrEmpty(this.DataSit.Particella))
                        return this.GetElenco(sField, NomiTabelle.AE_INTERNO_CON_INTKEY);

                    return this.GetElenco(sField, NomiTabelle.Tutte);
                }

                return new RetSit(true);
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore in GetAeCivicoConCivkey: {0}\r\nStruttura sit: {1}", ex.ToString(), StreamUtils.SerializeClass(this.DataSit));

                throw;
            }
        }

        private string QualificaTabelle(string nomiTabelle)
        {
            var listaNomiTabelle = nomiTabelle.Split(new Char[] { ',' });
            var owner = this._verticalizzazione.Owner;
            var dblink = this._verticalizzazione.Dblink;

            var nomiTabelleQualificati = listaNomiTabelle.
                                            Select(nomeTabella =>
                                            {
                                                if (!String.IsNullOrEmpty(owner))
                                                    nomeTabella = owner + "." + nomeTabella;

                                                if (!String.IsNullOrEmpty(dblink))
                                                    nomeTabella = nomeTabella + (dblink.StartsWith("@") ? dblink : "@" + dblink);

                                                return nomeTabella;
                                            }).
                                            ToArray();

            return String.Join(",", nomiTabelle);
        }

        private string GetQuery(NomiCampi nomeCampo, string nomeTabella, TipoQuery tipoQuery)
        {
            var sql = string.Empty;
            var campoSelect = this._registroNomiCampi[nomeCampo];
            var nomiCompletiTabelle = this.QualificaTabelle(nomeTabella);
            var sqlSelect = "Select distinct " + campoSelect + " from " + nomiCompletiTabelle + " where ";

            var provider = this.InternalDatabaseConnection.Specifics;

            switch (nomeTabella)
            {
                case NomiTabelle.AE_CIVICO_CON_CIVKEY:
                    switch (tipoQuery)
                    {
                        case TipoQuery.Elenco:
                            sql = sqlSelect +
                                (((this.DataSit.CodVia != "") && (nomeCampo != NomiCampi.CodiceVia)) ? this.ApplyRTrimTo(NomiCampi.CodiceVia) + " = " + this.RightTrim(this.DataSit.CodVia) + " and " : "") +
                                (((this.DataSit.Civico != "") && (nomeCampo != NomiCampi.Civico)) ? this.ApplyRTrimTo(NomiCampi.Civico) + " = " + this.RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Civico) ? this.DataSit.Civico : "-1") + " and " : "") +
                                (((this.DataSit.Esponente != "") && (nomeCampo != NomiCampi.Esponente)) ? this.ApplyRTrimTo(NomiCampi.Esponente) + " = '" + this.RightTrim(this.DataSit.Esponente) + "' and " : "") +
                                (((this.DataSit.CodCivico != "") && (nomeCampo != NomiCampi.CodCivico)) ? this.ApplyRTrimTo(NomiCampi.CodCivico) + " = '" + this.RightTrim(this.DataSit.CodCivico) + "' and " : "") +
                                //(((this.DataSit.CAP != "") && (nomeCampo != NomiCampi.Cap)) ? ApplyRTrimTo(NomiCampi.Cap) + " = '" + RightTrim(this.DataSit.CAP) + "' and " : "") +
                                (((this.DataSit.Fabbricato != "") && (nomeCampo != NomiCampi.Fabbricato)) ? this.ApplyRTrimTo(NomiCampi.Fabbricato) + " = " + this.RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Fabbricato) ? this.DataSit.Fabbricato : "-1") + " and " : "") +
                                (((this.DataSit.Sezione != "") && (nomeCampo != NomiCampi.Sezione)) ? this.ApplyRTrimTo(NomiCampi.Sezione) + " = '" + this.RightTrim(this.DataSit.Sezione) + "' and " : "") +
                                (((this.DataSit.Foglio != "") && (nomeCampo != NomiCampi.Foglio)) ? this.ApplyRTrimTo(NomiCampi.Foglio) + " = '" + this.RightTrim(this.DataSit.Foglio) + "' and " : "") +
                                (((this.DataSit.Particella != "") && (nomeCampo != NomiCampi.Particella)) ? this.ApplyRTrimTo(NomiCampi.Particella) + " = '" + this.RightTrim(this.DataSit.Particella) + "' and " : "") +
                                this.ApplyRTrimTo(NomiCampi.DataFine) + " is null order by " + campoSelect;
                            break;
                        case TipoQuery.Validazione:
                            sql = sqlSelect +
                                (((this.DataSit.CodVia != "")) ? this.ApplyRTrimTo(NomiCampi.CodiceVia) + " = " + this.RightTrim(this.DataSit.CodVia) + " and " : "") +
                                (((this.DataSit.Civico != "")) ? this.ApplyRTrimTo(NomiCampi.Civico) + " = " + this.RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Civico) ? this.DataSit.Civico : "-1") + " and " : "") +
                                (((this.DataSit.Esponente != "")) ? this.ApplyRTrimTo(NomiCampi.Esponente) + " = '" + this.RightTrim(this.DataSit.Esponente) + "' and " : "") +
                                (((this.DataSit.CodCivico != "")) ? this.ApplyRTrimTo(NomiCampi.CodCivico) + " = '" + this.RightTrim(this.DataSit.CodCivico) + "' and " : "") +
                                //(((this.DataSit.CAP != "")) ? ApplyRTrimTo(NomiCampi.Cap) + " = '" + RightTrim(this.DataSit.CAP) + "' and " : "") +
                                (((this.DataSit.Fabbricato != "")) ? this.ApplyRTrimTo(NomiCampi.Fabbricato) + " = " + this.RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Fabbricato) ? this.DataSit.Fabbricato : "-1") + " and " : "") +
                                (((this.DataSit.Sezione != "")) ? this.ApplyRTrimTo(NomiCampi.Sezione) + " = '" + this.RightTrim(this.DataSit.Sezione) + "' and " : "") +
                                (((this.DataSit.Foglio != "")) ? this.ApplyRTrimTo(NomiCampi.Foglio) + " = '" + this.RightTrim(this.DataSit.Foglio) + "' and " : "") +
                                (((this.DataSit.Particella != "")) ? this.ApplyRTrimTo(NomiCampi.Particella) + " = '" + this.RightTrim(this.DataSit.Particella) + "' and " : "") +
                                this.ApplyRTrimTo(NomiCampi.DataFine) + " is null order by " + campoSelect;
                            break;
                    }
                    break;
                case NomiTabelle.AE_INTERNO_CON_INTKEY:
                    switch (tipoQuery)
                    {
                        case TipoQuery.Elenco:
                            sql = sqlSelect +
                                (((this.DataSit.Scala != "") && (nomeCampo != NomiCampi.Scala)) ? this.ApplyRTrimTo(NomiCampi.Scala) + " = '" + this.RightTrim(this.DataSit.Scala) + "' and " : "") +
                                (((this.DataSit.Interno != "") && (nomeCampo != NomiCampi.Interno)) ? this.ApplyRTrimTo(NomiCampi.Interno) + " = " + this.RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Interno) ? this.DataSit.Interno : "-1") + " and " : "") +
                                (((this.DataSit.EsponenteInterno != "") && (nomeCampo != NomiCampi.EsponenteInterno)) ? this.ApplyRTrimTo(NomiCampi.EsponenteInterno) + " = '" + this.RightTrim(this.DataSit.EsponenteInterno) + "' and " : "") +
                                (((this.DataSit.CodCivico != "") && (nomeCampo != NomiCampi.CodCivico)) ? this.ApplyRTrimTo(NomiCampi.CodCivico) + " = '" + this.RightTrim(this.DataSit.CodCivico) + "' " : "");
                            break;
                        case TipoQuery.Validazione:
                            sql = sqlSelect +
                                (((this.DataSit.Scala != "")) ? this.ApplyRTrimTo(NomiCampi.Scala) + " = '" + this.RightTrim(this.DataSit.Scala) + "' and " : "") +
                                (((this.DataSit.Interno != "")) ? this.ApplyRTrimTo(NomiCampi.Interno) + " = " + this.RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Interno) ? this.DataSit.Interno : "-1") + " and " : "") +
                                (((this.DataSit.EsponenteInterno != "")) ? this.ApplyRTrimTo(NomiCampi.EsponenteInterno) + " = '" + this.RightTrim(this.DataSit.EsponenteInterno) + "' and " : "") +
                                (((this.DataSit.CodCivico != "")) ? this.ApplyRTrimTo(NomiCampi.CodCivico) + " = '" + this.RightTrim(this.DataSit.CodCivico) + "' " : "");
                            break;
                    }

                    if (sql.EndsWith("and "))
                        sql = sql.Remove(sql.Length - 4, 4) + " order by " + campoSelect;
                    else if (sql.EndsWith("where "))
                        sql = sql.Remove(sql.Length - 6, 6) + " order by " + campoSelect;
                    else
                        sql = sql + " order by " + campoSelect;

                    break;
                case NomiTabelle.Tutte:
                    switch (tipoQuery)
                    {
                        case TipoQuery.Elenco:
                            sql = sqlSelect +
                                "AE_CIVICO_CON_CIVKEY.CIVKEY = AE_INTERNO_CON_INTKEY.CIVKEY and " +
                                (((this.DataSit.CodVia != "") && (nomeCampo != NomiCampi.CodiceVia)) ? this.ApplyRTrimTo(NomiCampi.CodiceVia) + " = " + this.RightTrim(this.DataSit.CodVia) + " and " : "") +
                                (((this.DataSit.Civico != "") && (nomeCampo != NomiCampi.Civico)) ? this.ApplyRTrimTo(NomiCampi.Civico) + " = " + this.RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Civico) ? this.DataSit.Civico : "-1") + " and " : "") +
                                (((this.DataSit.Esponente != "") && (nomeCampo != NomiCampi.Esponente)) ? this.ApplyRTrimTo(NomiCampi.Esponente) + " = '" + this.RightTrim(this.DataSit.Esponente) + "' and " : "") +
                                (((this.DataSit.CodCivico != "") && (nomeCampo != NomiCampi.CodCivico)) ? this.ApplyRTrimTo(NomiCampi.CodCivico) + " = '" + this.RightTrim(this.DataSit.CodCivico) + "' and " : "") +
                                //(((this.DataSit.CAP != "") && (nomeCampo != NomiCampi.Cap)) ? ApplyRTrimTo(NomiCampi.Cap) + " = '" + RightTrim(this.DataSit.CAP) + "' and " : "") +
                                (((this.DataSit.Fabbricato != "") && (nomeCampo != NomiCampi.Fabbricato)) ? this.ApplyRTrimTo(NomiCampi.Fabbricato) + " = " + this.RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Fabbricato) ? this.DataSit.Fabbricato : "-1") + " and " : "") +
                                (((this.DataSit.Sezione != "") && (nomeCampo != NomiCampi.Sezione)) ? this.ApplyRTrimTo(NomiCampi.Sezione) + " = '" + this.RightTrim(this.DataSit.Sezione) + "' and " : "") +
                                (((this.DataSit.Foglio != "") && (nomeCampo != NomiCampi.Foglio)) ? this.ApplyRTrimTo(NomiCampi.Foglio) + " = '" + this.RightTrim(this.DataSit.Foglio) + "' and " : "") +
                                (((this.DataSit.Particella != "") && (nomeCampo != NomiCampi.Particella)) ? this.ApplyRTrimTo(NomiCampi.Particella) + " = '" + this.RightTrim(this.DataSit.Particella) + "' and " : "") +
                                (((this.DataSit.Scala != "") && (nomeCampo != NomiCampi.Scala)) ? this.ApplyRTrimTo(NomiCampi.Scala) + " = '" + this.RightTrim(this.DataSit.Scala) + "' and " : "") +
                                (((this.DataSit.Interno != "") && (nomeCampo != NomiCampi.Interno)) ? this.ApplyRTrimTo(NomiCampi.Interno) + " = " + this.RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Interno) ? this.DataSit.Interno : "-1") + " and " : "") +
                                (((this.DataSit.EsponenteInterno != "") && (nomeCampo != NomiCampi.EsponenteInterno)) ? this.ApplyRTrimTo(NomiCampi.EsponenteInterno) + " = '" + this.RightTrim(this.DataSit.EsponenteInterno) + "' and " : "") +
                                this.ApplyRTrimTo(NomiCampi.DataFine) + " is null order by " + campoSelect;
                            break;
                        case TipoQuery.Validazione:
                            sql = sqlSelect +
                                "AE_CIVICO_CON_CIVKEY.CIVKEY = AE_INTERNO_CON_INTKEY.CIVKEY and " +
                                (((this.DataSit.CodVia != "")) ? this.ApplyRTrimTo(NomiCampi.CodiceVia) + " = " + this.RightTrim(this.DataSit.CodVia) + " and " : "") +
                                (((this.DataSit.Civico != "")) ? this.ApplyRTrimTo(NomiCampi.Civico) + " = " + this.RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Civico) ? this.DataSit.Civico : "-1") + " and " : "") +
                                (((this.DataSit.Esponente != "")) ? this.ApplyRTrimTo(NomiCampi.Esponente) + " = '" + this.RightTrim(this.DataSit.Esponente) + "' and " : "") +
                                (((this.DataSit.CodCivico != "")) ? this.ApplyRTrimTo(NomiCampi.CodCivico) + " = '" + this.RightTrim(this.DataSit.CodCivico) + "' and " : "") +
                                //(((this.DataSit.CAP != "")) ? ApplyRTrimTo(NomiCampi.Cap) + " = '" + RightTrim(this.DataSit.CAP) + "' and " : "") +
                                (((this.DataSit.Fabbricato != "")) ? this.ApplyRTrimTo(NomiCampi.Fabbricato) + " = " + this.RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Fabbricato) ? this.DataSit.Fabbricato : "-1") + " and " : "") +
                                (((this.DataSit.Sezione != "")) ? this.ApplyRTrimTo(NomiCampi.Sezione) + " = '" + this.RightTrim(this.DataSit.Sezione) + "' and " : "") +
                                (((this.DataSit.Foglio != "")) ? this.ApplyRTrimTo(NomiCampi.Foglio) + " = '" + this.RightTrim(this.DataSit.Foglio) + "' and " : "") +
                                (((this.DataSit.Particella != "")) ? this.ApplyRTrimTo(NomiCampi.Particella) + " = '" + this.RightTrim(this.DataSit.Particella) + "' and " : "") +
                                (((this.DataSit.Scala != "")) ? this.ApplyRTrimTo(NomiCampi.Scala) + " = '" + this.RightTrim(this.DataSit.Scala) + "' and " : "") +
                                (((this.DataSit.Interno != "")) ? this.ApplyRTrimTo(NomiCampi.Interno) + " = " + this.RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Interno) ? this.DataSit.Interno : "-1") + " and " : "") +
                                (((this.DataSit.EsponenteInterno != "")) ? this.ApplyRTrimTo(NomiCampi.EsponenteInterno) + " = '" + this.RightTrim(this.DataSit.EsponenteInterno) + "' and " : "") +
                                this.ApplyRTrimTo(NomiCampi.DataFine) + " is null order by " + campoSelect;
                            break;
                    }
                    break;
            }


            return sql;
        }

        private string GetCodCivico(string codVia, string civico, string esponente)
        {
            try
            {
                codVia = codVia.Trim();
                civico = civico.Trim();
                esponente = esponente.Trim();

                this.EnsureConnectionIsOpen();

                var sql = $@"Select distinct 
								Trim(AE_CIVICO_CON_CIVKEY.CIVKEY) 
							from 
								{this.QualificaTabelle("AE_CIVICO_CON_CIVKEY")}
							where 
								RTRIM(AE_CIVICO_CON_CIVKEY.COD_VIA) = {this.InternalDatabaseConnection.QueryParameter("codVia")} and 
								RTRIM(AE_CIVICO_CON_CIVKEY.NUMERO) = {this.InternalDatabaseConnection.QueryParameter("civico")} and 
								RTRIM(AE_CIVICO_CON_CIVKEY.DATA_FINE) is null and 
								RTrim(AE_CIVICO_CON_CIVKEY.ESPONENTE)";

                if (string.IsNullOrEmpty(esponente))
                {
                    sql += " is null";
                }
                else
                {
                    sql += $" = {this.InternalDatabaseConnection.QueryParameter("esponente")}";
                }

                using (IDbCommand cmd = this.InternalDatabaseConnection.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this.InternalDatabaseConnection.CreateParameter("codVia", codVia));
                    cmd.Parameters.Add(this.InternalDatabaseConnection.CreateParameter("civico", civico));

                    if (!String.IsNullOrEmpty(esponente))
                        cmd.Parameters.Add(this.InternalDatabaseConnection.CreateParameter("esponente", esponente));

                    using (IDataReader dr = cmd.ExecuteReader())
                    {
                        while (dr.Read())
                        {
                            return dr[0].ToString();
                        }
                    }

                    return string.Empty;
                }
            }
            finally
            {
                this.CloseInternalConnection();
            }
        }

        private string ApplyRTrimTo(NomiCampi nomeCampo)
        {
            var provider = this.InternalDatabaseConnection.Specifics;

            return provider.RTrimFunction(this._registroNomiCampi[nomeCampo]);
        }

        private string GetElemento(NomiCampi sField, string sTableName, TipoQuery eTipoQuery)
        {
            string sRetVal = "";
            int iCount = 0;
            try
            {
                this.EnsureConnectionIsOpen();
                this._lastCommandText = this.GetQuery(sField, sTableName, eTipoQuery);

                using (IDbCommand pCommand = this.InternalDatabaseConnection.CreateCommand(this._lastCommandText))
                {
                    using (IDataReader pDataReader = pCommand.ExecuteReader())
                    {
                        while (pDataReader.Read())
                        {
                            string dato = this.RightTrim(pDataReader[0].ToString());

                            iCount++;
                            if (iCount == 1)
                                sRetVal = dato;
                            else
                            {
                                sRetVal = "";
                                break;
                            }
                        }
                    }
                }

                return sRetVal;
            }
            finally
            {
                this.CloseInternalConnection();
            }
        }

        private RetSit GetElenco(NomiCampi sField, string sTableName)
        {
            RetSit pRetSit = new RetSit(true);
            try
            {
                this.EnsureConnectionIsOpen();
                this._lastCommandText = this.GetQuery(sField, sTableName, TipoQuery.Elenco);

                using (IDbCommand pCommand = this.InternalDatabaseConnection.CreateCommand(this._lastCommandText))
                {
                    using (IDataReader pDataReader = pCommand.ExecuteReader())
                    {
                        while (pDataReader.Read())
                        {
                            string dato = this.RightTrim(pDataReader[0].ToString());

                            if (!string.IsNullOrEmpty(dato) && !pRetSit.DataCollection.Contains(dato))
                            {
                                pRetSit.DataCollection.Add(dato);
                            }
                        }
                    }
                }
            }
            finally
            {
                this.CloseInternalConnection();
            }

            return pRetSit;
        }

        #endregion



        #region Metodi per ottenere elenchi di elementi catastali o facenti parte dell'indirizzo

        public override RetSit ElencoCivici()
        {
            try
            {
                if (String.IsNullOrEmpty(this.DataSit.Fabbricato) && String.IsNullOrEmpty(this.DataSit.CodVia))
                    return this.RestituisciErroreSit(this.GetMessaggioDiErrore(NomiCampi.Civico), MessageCode.ElencoCivici, false);

                return this.ElencoAeCivicoConCivkey(NomiCampi.Civico);
            }
            catch (CatastoException ex)
            {
                return this.RestituisciErroreSit(ex.Message, MessageCode.ElencoCivici, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione dell'elenco dei civici. Metodo: ElencoCivici, modulo: SitModena. " + ex.Message + "\r\n Query: " + this._lastCommandText);
            }
        }

        public override RetSit ElencoEsponenti()
        {
            RetSit pRetSit;

            try
            {
                if (!String.IsNullOrEmpty(this.DataSit.Civico) && ((!String.IsNullOrEmpty(this.DataSit.Fabbricato)) || (!String.IsNullOrEmpty(this.DataSit.CodVia))))
                {
                    return this.ElencoAeCivicoConCivkey(NomiCampi.Esponente);
                }

                return this.RestituisciErroreSit(this.GetMessaggioDiErrore(NomiCampi.Esponente), MessageCode.ElencoEsponenti, false);

            }
            catch (CatastoException ex)
            {
                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.ElencoEsponenti, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione dell'elenco degli esponenti. Metodo: ElencoEsponenti, modulo: SitModena. " + ex.Message + "\r\n Query: " + this._lastCommandText);
            }


            return pRetSit;
        }

        public override RetSit ElencoScale()
        {
            try
            {
                if (!String.IsNullOrEmpty(this.DataSit.CodCivico) || (!String.IsNullOrEmpty(this.DataSit.Fabbricato) && !String.IsNullOrEmpty(this.DataSit.Civico) && !String.IsNullOrEmpty(this.DataSit.Esponente)))
                {
                    return this.ElencoAeInternoConIntkey(NomiCampi.Scala);
                }

                return this.RestituisciErroreSit(this.GetMessaggioDiErrore(NomiCampi.Scala), MessageCode.ElencoScale, false);
            }
            catch (CatastoException ex)
            {
                return this.RestituisciErroreSit(ex.Message, MessageCode.ElencoScale, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione dell'elenco delle scale. Metodo: ElencoScale, modulo: SitModena. " + ex.Message + "\r\n Query: " + this._lastCommandText);
            }

        }

        public override RetSit ElencoInterni()
        {
            try
            {
                if (!String.IsNullOrEmpty(this.DataSit.CodCivico) || (!String.IsNullOrEmpty(this.DataSit.Fabbricato) && !String.IsNullOrEmpty(this.DataSit.Civico) && !String.IsNullOrEmpty(this.DataSit.Esponente)))
                {
                    return this.ElencoAeInternoConIntkey(NomiCampi.Interno);
                }

                return this.RestituisciErroreSit(this.GetMessaggioDiErrore(NomiCampi.Interno), MessageCode.ElencoInterni, false);
            }
            catch (CatastoException ex)
            {
                return this.RestituisciErroreSit(ex.Message, MessageCode.ElencoInterni, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione dell'elenco degli interni. Metodo: ElencoInterni, modulo: SitModena. " + ex.Message + "\r\n Query: " + this._lastCommandText);
            }
        }

        public override RetSit ElencoEsponentiInterno()
        {
            try
            {
                if ((!String.IsNullOrEmpty(this.DataSit.CodCivico) && !String.IsNullOrEmpty(this.DataSit.Interno)) || (!String.IsNullOrEmpty(this.DataSit.Fabbricato) && !String.IsNullOrEmpty(this.DataSit.Civico) && !String.IsNullOrEmpty(this.DataSit.Esponente) && !String.IsNullOrEmpty(this.DataSit.Interno)))
                {
                    return this.ElencoAeInternoConIntkey(NomiCampi.EsponenteInterno);
                }

                return this.RestituisciErroreSit(this.GetMessaggioDiErrore(NomiCampi.EsponenteInterno), MessageCode.ElencoEsponentiInterno, false);

            }
            catch (CatastoException ex)
            {
                return this.RestituisciErroreSit(ex.Message, MessageCode.ElencoEsponentiInterno, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione dell'elenco degli interni dell'esponente. Metodo: ElencoEsponentiInterno, modulo: SitModena. " + ex.Message + "\r\n Query: " + this._lastCommandText);
            }
        }

        public override RetSit ElencoFabbricati()
        {
            try
            {
                if ((!String.IsNullOrEmpty(this.DataSit.CodVia)) || (!String.IsNullOrEmpty(this.DataSit.Sezione) && !String.IsNullOrEmpty(this.DataSit.Foglio) && !String.IsNullOrEmpty(this.DataSit.Particella)))
                {
                    return this.ElencoAeCivicoConCivkey(NomiCampi.Fabbricato);
                }
                return this.RestituisciErroreSit(this.GetMessaggioDiErrore(NomiCampi.Fabbricato), MessageCode.ElencoFabbricati, false);
            }
            catch (CatastoException ex)
            {
                return this.RestituisciErroreSit(ex.Message, MessageCode.ElencoFabbricati, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione dell'elenco dei fabbricati. Metodo: ElencoFabbricati, modulo: SitModena. " + ex.Message + "\r\n Query: " + this._lastCommandText);
            }

        }

        public override RetSit ElencoSezioni()
        {
            try
            {
                return this.ElencoAeCivicoConCivkey(NomiCampi.Sezione);

            }
            catch (CatastoException ex)
            {
                return this.RestituisciErroreSit(ex.Message, MessageCode.ElencoSezioni, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione dell'elenco delle sezioni. Metodo: ElencoSezioni, modulo: SitModena. " + ex.Message + "\r\n Query: " + this._lastCommandText);
            }

        }

        public override RetSit ElencoFogli()
        {
            try
            {
                if ((!String.IsNullOrEmpty(this.DataSit.Fabbricato)) || (!String.IsNullOrEmpty(this.DataSit.Sezione)))
                {
                    return this.ElencoAeCivicoConCivkey(NomiCampi.Foglio);
                }

                return this.RestituisciErroreSit(this.GetMessaggioDiErrore(NomiCampi.Foglio), MessageCode.ElencoFogli, false);
            }
            catch (CatastoException ex)
            {
                return this.RestituisciErroreSit(ex.Message, MessageCode.ElencoFogli, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione dell'elenco dei fogli. Metodo: ElencoFogli, modulo: SitModena. " + ex.Message + "\r\n Query: " + this._lastCommandText);
            }

        }

        public override RetSit ElencoParticelle()
        {
            try
            {
                if ((!String.IsNullOrEmpty(this.DataSit.Fabbricato)) || (!String.IsNullOrEmpty(this.DataSit.Sezione) && !String.IsNullOrEmpty(this.DataSit.Foglio)))
                {
                    return this.ElencoAeCivicoConCivkey(NomiCampi.Particella);
                }

                return this.RestituisciErroreSit(this.GetMessaggioDiErrore(NomiCampi.Particella), MessageCode.ElencoParticelle, false);
            }
            catch (CatastoException ex)
            {
                return this.RestituisciErroreSit(ex.Message, MessageCode.ElencoParticelle, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione dell'elenco delle particelle. Metodo: ElencoParticelle, modulo: SitModena. " + ex.Message + "\r\n Query: " + this._lastCommandText);
            }

        }

        public override RetSit ElencoCircoscrizioni()
        {
            try
            {
                var codViaPresente = !String.IsNullOrEmpty(this.DataSit.CodVia);
                var civicoPresente = !String.IsNullOrEmpty(this.DataSit.Civico);
                var codCivicoPresente = !String.IsNullOrEmpty(this.DataSit.CodCivico);

                if (codCivicoPresente || (codViaPresente && civicoPresente))
                {
                    return this.ElencoAeCivicoConCivkey(NomiCampi.Circoscrizione);
                }

                return this.RestituisciErroreSit(this.GetMessaggioDiErrore(NomiCampi.Circoscrizione), MessageCode.ElencoCircoscrizioni, false);

            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore durante la ricerca delle circoscrizioni. Metodo: ElencoCircoscrizioni, modulo: SitModena. {0}\r\n Query: {1}", ex.ToString(), this._lastCommandText, ex);
                return this.RestituisciErroreSit(ex.Message, MessageCode.ElencoCircoscrizioni, false);
            }
        }

        public override RetSit ElencoFrazioni()
        {
            try
            {
                var codViaPresente = !String.IsNullOrEmpty(this.DataSit.CodVia);
                var civicoPresente = !String.IsNullOrEmpty(this.DataSit.Civico);
                var codCivicoPresente = !String.IsNullOrEmpty(this.DataSit.CodCivico);

                if (codCivicoPresente || (codViaPresente && civicoPresente))
                {
                    return this.ElencoAeCivicoConCivkey(NomiCampi.Frazione);
                }

                return this.RestituisciErroreSit(this.GetMessaggioDiErrore(NomiCampi.Frazione), MessageCode.ElencoFrazioni, false);

            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore durante la ricerca delle frazioni. Metodo: ElencoFrazioni, modulo: SitModena. {0}\r\n Query: {1}", ex.ToString(), this._lastCommandText, ex);
                return this.RestituisciErroreSit(ex.Message, MessageCode.ElencoFrazioni, false);
            }
        }

        //public override RetSit ElencoSub()
        //{
        //    try
        //    {
        //        if (!String.IsNullOrEmpty(this.DataSit.Foglio) && !String.IsNullOrEmpty(this.DataSit.Particella))
        //        {
        //            var adapter = new RichiestaRicercaMappaliUrbanoAdapter();
        //            var requestXml = adapter.Adatta(_verticalizzazione.CodiceEnte, this.DataSit.Foglio, this.DataSit.Particella);

        //            var srv = new SitServiceWrapper(_verticalizzazione.UrlWsCatasto);
        //            var response = srv.GetDati<RispostaRicercaMappaleUrbanoType>(requestXml, adapter.NomeServizio);

        //            if (response.ErroreRicercaMappaleUrbano != null)
        //            {
        //                return new RetSit(true);
        //            }

        //            var collection = response.S3RispostaConsultUIU.UIURicercata.Select(x => x.IdentificativoParzialeUIU.Subalterno).Where(x => x.ValidaSub(this.DataSit.Foglio, this.DataSit.Particella, _verticalizzazione.CodiceEnte, _verticalizzazione.UrlWsCatasto));
        //            return new RetSit(true, collection);
        //        }

        //        return RestituisciErroreSit(GetMessaggioDiErrore(NomiCampi.Subalterno), MessageCode.ElencoSub, false);
        //    }
        //    catch (CatastoException ex)
        //    {
        //        return RestituisciErroreSit(ex.Message, MessageCode.ElencoParticelle, false);
        //    }
        //}

        #endregion

        #region Metodi per la verifica e la restituzione di un singolo elemento catastale o facente parte dell'indirizzo

        protected override string GetEsponente()
        {
            try
            {
                return this.GetAeCivicoConCivkey(NomiCampi.Esponente, TipoQuery.Elenco);
            }
            catch (CatastoException ex)
            {
                return String.Empty;
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di un esponente. Metodo: GetEsponente, modulo: SitModena. " + ex.Message + "\r\n Query: " + this._lastCommandText);
            }

        }

        protected override RetSit VerificaEsponente()
        {
            try
            {
                string sElem = this.GetAeCivicoConCivkey(NomiCampi.Esponente, TipoQuery.Validazione);

                if (!String.IsNullOrEmpty(sElem))
                {
                    this.DataSit.Esponente = sElem;
                    return new RetSit(true);

                }
                else
                {
                    return this.RestituisciErroreSit(this.GetErroreDiValidazione(NomiCampi.Esponente), MessageCode.EsponenteValidazione, false);
                }
            }
            catch (CatastoException ex)
            {
                return this.RestituisciErroreSit(ex.Message, MessageCode.EsponenteValidazione, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione di un esponente. Metodo: VerificaEsponente, modulo: SitModena. " + ex.Message + "\r\n Query: " + this._lastCommandText);
            }


        }

        protected override string GetScala()
        {
            try
            {
                return this.GetAeInternoConIntkey(NomiCampi.Scala, TipoQuery.Elenco);
            }
            catch (CatastoException ex)
            {
                return String.Empty;
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di una scala. Metodo: GetScala, modulo: SitModena. " + ex.Message + "\r\n Query: " + this._lastCommandText);
            }

        }

        protected override RetSit VerificaScala()
        {
            try
            {
                string sElem = this.GetAeInternoConIntkey(NomiCampi.Scala, TipoQuery.Validazione);
                if (!String.IsNullOrEmpty(sElem))
                {
                    this.DataSit.Scala = sElem;
                    return new RetSit(true);
                }
                else
                {
                    return this.RestituisciErroreSit(this.GetErroreDiValidazione(NomiCampi.Scala), MessageCode.ScalaValidazione, false);
                }
            }
            catch (CatastoException ex)
            {
                return this.RestituisciErroreSit(ex.Message, MessageCode.ScalaValidazione, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione di una scala. Metodo: VerificaScala, modulo: SitModena. " + ex.Message + "\r\n Query: " + this._lastCommandText);
            }


        }

        protected override string GetInterno()
        {
            try
            {
                return this.GetAeInternoConIntkey(NomiCampi.Interno, TipoQuery.Elenco);
            }
            catch (CatastoException ex)
            {
                return String.Empty;
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di un interno. Metodo: GetInterno, modulo: SitModena. " + ex.Message + "\r\n Query: " + this._lastCommandText);
            }

        }

        protected override RetSit VerificaInterno()
        {
            if (!Init.Utils.StringChecker.IsNumeric(this.DataSit.Interno))
                return this.RestituisciErroreSit("Non è possibile validare l'interno " + this.DataSit.Interno + " perchè non è un numero", MessageCode.InternoValidazioneNumero, false);


            try
            {
                string sElem = this.GetAeInternoConIntkey(NomiCampi.Interno, TipoQuery.Validazione);

                if (!String.IsNullOrEmpty(sElem))
                {
                    this.DataSit.Interno = sElem;
                    return new RetSit(true);
                }

                return this.RestituisciErroreSit(this.GetErroreDiValidazione(NomiCampi.Interno), MessageCode.InternoValidazione, false);

            }
            catch (CatastoException ex)
            {
                return this.RestituisciErroreSit(ex.Message, MessageCode.InternoValidazione, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione di un interno. Metodo: VerificaInterno, modulo: SitModena. " + ex.Message + "\r\n Query: " + this._lastCommandText);
            }


        }

        protected override string GetEsponenteInterno()
        {
            try
            {
                return this.GetAeInternoConIntkey(NomiCampi.EsponenteInterno, TipoQuery.Elenco);
            }
            catch (CatastoException ex)
            {
                return String.Empty;
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di un esponente interno. Metodo: GetEsponenteInterno, modulo: SitModena. " + ex.Message + "\r\n Query: " + this._lastCommandText);
            }

        }

        protected override RetSit VerificaEsponenteInterno()
        {
            try
            {
                string sElem = this.GetAeInternoConIntkey(NomiCampi.EsponenteInterno, TipoQuery.Validazione);
                if (!String.IsNullOrEmpty(sElem))
                {
                    this.DataSit.EsponenteInterno = sElem;
                    return new RetSit(true);

                }

                return this.RestituisciErroreSit(this.GetErroreDiValidazione(NomiCampi.EsponenteInterno), MessageCode.EsponenteInternoValidazione, false);
            }
            catch (CatastoException ex)
            {
                return this.RestituisciErroreSit(ex.Message, MessageCode.EsponenteInternoValidazione, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione di un esponente interno. Metodo: VerificaEsponenteInterno, modulo: SitModena. " + ex.Message + "\r\n Query: " + this._lastCommandText);
            }

        }

        protected override RetSit VerificaFabbricato()
        {
            if (!Init.Utils.StringChecker.IsNumeric(this.DataSit.Fabbricato))
                return this.RestituisciErroreSit("Non è possibile validare il fabbricato " + this.DataSit.Fabbricato + " perchè non è un numero", MessageCode.FabbricatoValidazioneNumero, true);

            try
            {
                string sElem = this.GetAeCivicoConCivkey(NomiCampi.Fabbricato, TipoQuery.Validazione);
                if (!String.IsNullOrEmpty(sElem))
                {
                    this.DataSit.Fabbricato = sElem;
                    return new RetSit(true);
                }

                return this.RestituisciErroreSit(this.GetErroreDiValidazione(NomiCampi.Fabbricato), MessageCode.FabbricatoValidazione, false);
            }
            catch (CatastoException ex)
            {
                return this.RestituisciErroreSit(ex.Message, MessageCode.FabbricatoValidazione, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione di un fabbricato. Metodo: VerificaFabbricato, modulo: SitModena. " + ex.Message + "\r\n Query: " + this._lastCommandText);
            }
        }

        protected override string GetSezione()
        {
            try
            {
                return this.GetAeCivicoConCivkey(NomiCampi.Sezione, TipoQuery.Elenco);
            }
            catch (CatastoException ex)
            {
                return String.Empty;
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di una sezione. Metodo: GetSezione, modulo: SitModena. " + ex.Message + "\r\n Query: " + this._lastCommandText);
            }

        }

        protected override RetSit VerificaTipoCatasto()
        {
            if (this.IsCampiToponomasticaImmobileVuoti())
                return new RetSit(true);

            if (this.DataSit.TipoCatasto == "T")
                return this.RestituisciErroreSit("Il tipocatasto " + this.DataSit.TipoCatasto + " non è valido per i dati inseriti", MessageCode.TipoCatastoValidazione, false);

            return new RetSit(true);
        }

        protected override RetSit VerificaSezione()
        {
            try
            {
                string sElem = this.GetAeCivicoConCivkey(NomiCampi.Sezione, TipoQuery.Validazione);
                if (!String.IsNullOrEmpty(sElem))
                {
                    this.DataSit.Sezione = sElem;
                    return new RetSit(true);
                }

                return this.RestituisciErroreSit(this.GetErroreDiValidazione(NomiCampi.Sezione), MessageCode.SezioneValidazione, false);
            }
            catch (CatastoException ex)
            {
                return this.RestituisciErroreSit(ex.Message, MessageCode.SezioneValidazione, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione di una sezione. Metodo: VerificaSezione, modulo: SitModena. " + ex.Message + "\r\n Query: " + this._lastCommandText);
            }
        }

        protected override string GetFoglio()
        {
            try
            {
                return this.GetAeCivicoConCivkey(NomiCampi.Foglio, TipoQuery.Elenco);
            }
            catch (CatastoException ex)
            {
                return String.Empty;
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di un foglio. Metodo: GetFoglio, modulo: SitModena. " + ex.Message + "\r\n Query: " + this._lastCommandText);
            }
        }

        protected override RetSit VerificaFoglio()
        {
            try
            {
                string sElem = this.GetAeCivicoConCivkey(NomiCampi.Foglio, TipoQuery.Validazione);

                if (!String.IsNullOrEmpty(sElem))
                {
                    this.DataSit.Foglio = sElem;
                    return new RetSit(true);
                }

                return this.RestituisciErroreSit(this.GetErroreDiValidazione(NomiCampi.Foglio), MessageCode.FoglioValidazione, false);

            }
            catch (CatastoException ex)
            {
                return this.RestituisciErroreSit(ex.Message, MessageCode.FoglioValidazione, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione di un foglio. Metodo: VerificaFoglio, modulo: SitModena. " + ex.Message + "\r\n Query: " + this._lastCommandText);
            }
        }

        protected override string GetParticella()
        {
            try
            {
                return this.GetAeCivicoConCivkey(NomiCampi.Particella, TipoQuery.Elenco);
            }
            catch (CatastoException ex)
            {
                return String.Empty;
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di una particella. Metodo: GetParticella, modulo: SitModena. " + ex.Message + "\r\n Query: " + this._lastCommandText);
            }
        }

        protected override RetSit VerificaParticella()
        {
            try
            {
                string sElem = this.GetAeCivicoConCivkey(NomiCampi.Particella, TipoQuery.Validazione);
                if (!String.IsNullOrEmpty(sElem))
                {
                    this.DataSit.Particella = sElem;
                    return new RetSit(true);
                }

                return this.RestituisciErroreSit(this.GetErroreDiValidazione(NomiCampi.Particella), MessageCode.ParticellaValidazione, false);
            }
            catch (CatastoException ex)
            {
                return this.RestituisciErroreSit(ex.Message, MessageCode.ParticellaValidazione, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione di una particella. Metodo: VerificaParticella, modulo: SitModena. " + ex.Message + "\r\n Query: " + this._lastCommandText);
            }
        }

        protected override RetSit VerificaSub()
        {
            try
            {
                var adapter = new RichiestaValidazioneMappaliUrbanoAdapter();
                var request = adapter.Adatta(this._verticalizzazione.CodiceEnte, this.DataSit.Foglio, this.DataSit.Particella, this.DataSit.Sub);

                var srv = new SitServiceWrapper(this._verticalizzazione.UrlWsCatasto);
                var response = srv.GetDati<RispostaValidazioneMappaliUrbanoType>(request, adapter.NomeServizio);

                if (!response.MappaliUrbanoValidati.MappaleUrbanoValidato.Valido)
                {
                    this.DataSit.Sub = "";
                    return this.RestituisciErroreSit(this.GetErroreDiValidazione(NomiCampi.Subalterno), MessageCode.SubValidazione, false);
                }

                this.DataSit.Sub = response.MappaliUrbanoValidati.MappaleUrbanoValidato.IdentificativoParzialeUIU.Subalterno;

                return new RetSit(true);
            }
            catch (Exception ex)
            {
                return this.RestituisciErroreSit(ex.ToString(), MessageCode.SubValidazione, false);
            }
        }

        protected override string GetCivico()
        {
            try
            {
                return this.GetAeCivicoConCivkey(NomiCampi.Civico, TipoQuery.Elenco);
            }
            catch (CatastoException ex)
            {
                return String.Empty;
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di un civico. Metodo: GetCivico, modulo: SitModena. " + ex.Message + "\r\n Query: " + this._lastCommandText);
            }
        }

        protected override RetSit VerificaCivico()
        {
            if (!Init.Utils.StringChecker.IsNumeric(this.DataSit.Civico))
                return this.RestituisciErroreSit("Non è possibile validare il civico " + this.DataSit.Civico + " perchè non è un numero", MessageCode.CivicoValidazioneNumero, false);

            try
            {
                string sElem = this.GetAeCivicoConCivkey(NomiCampi.Civico, TipoQuery.Validazione);
                if (!String.IsNullOrEmpty(sElem))
                {
                    this.DataSit.Civico = sElem;
                    return new RetSit(true);
                }

                return this.RestituisciErroreSit(this.GetErroreDiValidazione(NomiCampi.Civico), MessageCode.CivicoValidazione, false);
            }
            catch (CatastoException ex)
            {
                return this.RestituisciErroreSit(ex.Message, MessageCode.CivicoValidazione, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione di un civico. Metodo: VerificaCivico, modulo: SitModena. " + ex.Message + "\r\n Query: " + this._lastCommandText);
            }
        }

        protected override string GetCircoscrizione()
        {
            try
            {
                return this.GetAeCivicoConCivkey(NomiCampi.Circoscrizione, TipoQuery.Elenco);
            }
            catch (CatastoException ex)
            {
                this._log.ErrorFormat(String.Format("Errore durante la restituzione della circoscrizione. Metodo: GetCircoscrizione, modulo: SitModena. {0}\r\n Query: {1}", ex.ToString(), this._lastCommandText));

                return String.Empty;
            }
            catch (Exception ex)
            {
                var errMsg = String.Format("Errore durante la restituzione della circoscrizione. Metodo: GetCircoscrizione, modulo: SitModena. {0}\r\n Query: {1}", ex.ToString(), this._lastCommandText);

                this._log.Error(errMsg);

                throw new Exception(errMsg);
            }
        }

        protected override RetSit VerificaCircoscrizione()
        {
            try
            {
                var circoscrizione = this.GetAeCivicoConCivkey(NomiCampi.Circoscrizione, TipoQuery.Validazione);
                if (!String.IsNullOrEmpty(circoscrizione))
                {
                    this.DataSit.Circoscrizione = circoscrizione;
                    return new RetSit(true);
                }

                return this.RestituisciErroreSit(this.GetErroreDiValidazione(NomiCampi.Circoscrizione), MessageCode.CircoscrizioneValidazione, false);
            }
            catch (CatastoException ex)
            {
                var errMsg = String.Format("Errore durante la validazione di una circoscrizione. Metodo: VerificaCircoscrizione, modulo: SitModena. {0}\r\n Query: {1}", ex.ToString(), this._lastCommandText);

                this._log.Error(errMsg);
                return this.RestituisciErroreSit(ex.Message, MessageCode.CircoscrizioneValidazione, false);
            }
            catch (Exception ex)
            {
                var errMsg = String.Format("Errore durante la validazione di una circoscrizione. Metodo: VerificaCircoscrizione, modulo: SitModena. {0}\r\n Query: {1}", ex.ToString(), this._lastCommandText);

                this._log.Error(errMsg);

                throw new Exception(errMsg);
            }
        }

        protected override string GetFrazione()
        {
            try
            {
                return this.GetAeCivicoConCivkey(NomiCampi.Frazione, TipoQuery.Elenco);
            }
            catch (CatastoException ex)
            {
                this._log.ErrorFormat(String.Format("Errore durante la restituzione della frazione. Metodo: GetFrazione, modulo: SitModena. {0}\r\n Query: {1}", ex.ToString(), this._lastCommandText));

                return String.Empty;
            }
            catch (Exception ex)
            {
                var errMsg = String.Format("Errore durante la restituzione della frazione. Metodo: GetFrazione, modulo: SitModena. {0}\r\n Query: {1}", ex.ToString(), this._lastCommandText);

                this._log.Error(errMsg);

                throw new Exception(errMsg);
            }
        }

        protected override RetSit VerificaFrazione()
        {
            try
            {
                var frazione = this.GetAeCivicoConCivkey(NomiCampi.Frazione, TipoQuery.Validazione);
                if (!String.IsNullOrEmpty(frazione))
                {
                    this.DataSit.Frazione = frazione;
                    return new RetSit(true);
                }

                return this.RestituisciErroreSit(this.GetErroreDiValidazione(NomiCampi.Frazione), MessageCode.FrazioneValidazione, false);
            }
            catch (CatastoException ex)
            {
                var errMsg = String.Format("Errore durante la validazione di una frazione. Metodo: VerificaCircoscrizione, modulo: SitModena. {0}\r\n Query: {1}", ex.ToString(), this._lastCommandText);

                this._log.Error(errMsg);
                return this.RestituisciErroreSit(ex.Message, MessageCode.FrazioneValidazione, false);
            }
            catch (Exception ex)
            {
                var errMsg = String.Format("Errore durante la validazione di una frazione. Metodo: VerificaCircoscrizione, modulo: SitModena. {0}\r\n Query: {1}", ex.ToString(), this._lastCommandText);

                this._log.Error(errMsg);

                throw new Exception(errMsg);
            }
        }

        protected override string GetCAP()
        {
            try
            {
                return this.GetAeCivicoConCivkey(NomiCampi.Cap, TipoQuery.Elenco);
            }
            catch (CatastoException ex)
            {
                return String.Empty;
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di un CAP. Metodo: GetCAP, modulo: SitModena. " + ex.Message + "\r\n Query: " + this._lastCommandText);
            }

        }

        protected override RetSit VerificaCAP()
        {
            try
            {
                return new RetSit(true);
                //string sElem = GetAeCivicoConCivkey(NomiCampi.Cap, TipoQuery.Validazione);
                //if (!String.IsNullOrEmpty(sElem))
                //{
                //    this.DataSit.CAP = sElem;
                //    return new RetSit(true);
                //}

                //return RestituisciErroreSit(GetMessageValidate(NomiCampi.Cap), MessageCode.CAPValidazione, false);

            }
            catch (CatastoException ex)
            {
                return this.RestituisciErroreSit(ex.Message, MessageCode.CAPValidazione, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione di un CAP. Metodo: VerificaCAP, modulo: SitModena. " + ex.Message + "\r\n Query: " + this._lastCommandText);
            }
        }

        protected override string GetCodCivico()
        {
            try
            {
                var codVia = this.DataSit.CodVia;
                var civico = this.DataSit.Civico;
                var esponente = this.DataSit.Esponente;

                if (!String.IsNullOrEmpty(codVia) && !String.IsNullOrEmpty(civico))
                {
                    return this.GetCodCivico(codVia, civico, esponente);
                }

                return this.GetAeCivicoConCivkey(NomiCampi.CodCivico, TipoQuery.Elenco);
            }
            catch (CatastoException ex)
            {
                return String.Empty;
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di un codice civico. Metodo: GetCodCivico, modulo: SitModena. " + ex.Message + "\r\n Query: " + this._lastCommandText);
            }
        }

        protected override string GetCodFabbricato()
        {
            try
            {
                return this.GetAeCivicoConCivkey(NomiCampi.Fabbricato, TipoQuery.Elenco);
            }
            catch (CatastoException ex)
            {
                return String.Empty;
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di un codice fabbricato. Metodo: GetCodFabbricato, modulo: SitModena. " + ex.Message + "\r\n Query: " + this._lastCommandText);
            }
        }

        protected override string GetCodVia()
        {
            try
            {
                return this.GetAeCivicoConCivkey(NomiCampi.CodiceVia, TipoQuery.Elenco);
            }
            catch (CatastoException ex)
            {
                return String.Empty;
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di un codice via. Metodo: GetCodVia, modulo: SitModena. " + ex.Message + "\r\n Query: " + this._lastCommandText);
            }
        }

        protected override string GetSub()
        {
            return base.GetSub();
        }

        #endregion


        public override string[] GetListaCampiGestiti()
        {
            return new string[]{
                SitIntegrationService.NomiCampiSit.Esponente,
                SitIntegrationService.NomiCampiSit.Scala,
                SitIntegrationService.NomiCampiSit.Frazione,
                SitIntegrationService.NomiCampiSit.Circoscrizione,
                SitIntegrationService.NomiCampiSit.Interno,
                SitIntegrationService.NomiCampiSit.EsponenteInterno,
                SitIntegrationService.NomiCampiSit.Fabbricato,
                SitIntegrationService.NomiCampiSit.Sezione,
				//SitIntegrationService.NomiCampiSit.TipoCatasto,
				SitIntegrationService.NomiCampiSit.Foglio,
                SitIntegrationService.NomiCampiSit.Particella,
                SitIntegrationService.NomiCampiSit.Sub,
				//SitMgr.NomiCampiSit.UnitaImmobiliare,
				SitIntegrationService.NomiCampiSit.Civico,
				//SitIntegrationService.NomiCampiSit.Cap,
				SitIntegrationService.NomiCampiSit.CodiceVia,
                SitIntegrationService.NomiCampiSit.CodiceCivico
            };
        }
    }
}














