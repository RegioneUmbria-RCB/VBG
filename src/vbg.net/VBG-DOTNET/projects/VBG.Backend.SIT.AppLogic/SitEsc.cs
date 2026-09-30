//using log4net;
//using PersonalLib2.Data;
//using SIGePro.Manager.VerticalizzazioniBase;
//using System;
//using System.Collections.Generic;
//using System.Data;
//using VBG.Backend.SIT.AppLogic.Data;
//using VBG.Backend.SIT.AppLogic.Exceptions;
//using VBG.Backend.SIT.AppLogic.Manager;
//using VBG.Backend.SIT.AppLogic.ValidazioneFormale;
//using VBG.Backend.SIT.Verticalizzazioni;


//namespace VBG.Backend.SIT.AppLogic
//{
//    /// <summary>
//    /// Classe specifica per il Sit Esc
//    /// </summary>
//    [SitImplementation("SIT_ESC")]
//    public class SitEsc : SitBase
//    {
//        private readonly ILog _logger = LogManager.GetLogger(typeof(SitEsc));

//        public SitEsc()
//            : base(new ValidazioneFormaleTramiteCodiceCivicoService())
//        {
//        }

//        private string _tableOwnler;
//        private string sCommandText;

//        #region Utility
//        public override void SetupVerticalizzazione(IVerticalizzazioniFactory verticalizzazioniFactory)
//        {
//            try
//            {
//                var dDataBase = this.Database;

//                var verticalizzazione = verticalizzazioniFactory.Create<VerticalizzazioneSitEsc>(this.IdComuneAlias, this.Software);

//                if (verticalizzazione.Attiva)
//                {
//                    var connectionString = verticalizzazione.Connectionstring;
//                    var providerString = verticalizzazione.Provider;
//                    this._tableOwnler = "cpg_dbintegrato_esc";

//                    var provider = (ProviderType)Enum.Parse(typeof(ProviderType), providerString, false);
//                    this.InternalDatabaseConnection = new DataBase(connectionString, provider);
//                }
//                else
//                    throw new Exception("La verticalizzazione SIT_ESC non è attiva.\r\n");
//            }
//            catch (Exception ex)
//            {
//                this._logger.ErrorFormat("SIT_ESC.GetParametriFromVertSITESC: errore durante la lettura dei dati-> {0}", ex.ToString());

//                throw new Exception("Errore generato durante la lettura della verticalizzazione SIT_ESC. Metodo: GetParametriFromVertSITCORE, modulo: SitCore. " + ex.Message + "\r\n");
//            }
//        }

//        private string GetMessageValidate(string sField)
//        {
//            var sValue = string.Empty;

//            switch (sField)
//            {
//                case "IMMOBILE":
//                    sValue = "L'unità immobiliare " + this.DataSit.UI + " non è valido per i dati inseriti";
//                    break;
//                case "PK_SEQU_FABBRICATO":
//                    sValue = "Il fabbricato " + this.DataSit.Fabbricato + " non è valido per i dati inseriti";
//                    break;
//                case "FOGLIO":
//                    sValue = "Il foglio " + this.DataSit.Foglio + " non è valido per i dati inseriti";
//                    break;
//                case "CPG_FOGLIO_PART_CT.FOGLIO":
//                    sValue = "Il foglio " + this.DataSit.Foglio + " non è valido per i dati inseriti";
//                    break;
//                case "CPG_SIT_SUB.FOGLIO":
//                    sValue = "Il foglio " + this.DataSit.Foglio + " non è valido per i dati inseriti";
//                    break;
//                case "CPG_FOGLIO_PART_CT.NUMERO":
//                    sValue = "La particella " + this.DataSit.Particella + " non è valida per i dati inseriti";
//                    break;
//                case "CPG_SIT_SUB.NUMERO":
//                    sValue = "La particella " + this.DataSit.Particella + " non è valida per i dati inseriti";
//                    break;
//                case "SUBALTERNO":
//                    sValue = "Il subalterno " + this.DataSit.Sub + " non è valido per i dati inseriti";
//                    break;
//                case "CPG_FOGLIO_PART_CT.SUBALTERNO":
//                    sValue = "Il subalterno " + this.DataSit.Sub + " non è valido per i dati inseriti";
//                    break;
//                case "CPG_SIT_SUB.SUBALTERNO":
//                    sValue = "Il subalterno " + this.DataSit.Sub + " non è valido per i dati inseriti";
//                    break;
//                case "NUMERO":
//                    sValue = "Il civico " + this.DataSit.Civico + " non è valido per i dati inseriti";
//                    break;
//                case "ESP":
//                    sValue = "L'esponente " + this.DataSit.Esponente + " non è valido per i dati inseriti";
//                    break;
//                case "CAP":
//                    sValue = "Il CAP " + this.DataSit.CAP + " non è valido per i dati inseriti";
//                    break;
//                case "DESCR_LOCALITA":
//                    sValue = "La frazione " + this.DataSit.Frazione + " non è valida per i dati inseriti";
//                    break;
//                case "CIRCOSCRIZIONE":
//                    sValue = "La circoscrizione " + this.DataSit.Circoscrizione + " non è valida per i dati inseriti";
//                    break;
//            }

//            return sValue;
//        }

//        private string GetMessageList(string sField)
//        {
//            var sValue = string.Empty;

//            switch (sField)
//            {
//                case "FOGLIO":
//                    sValue = "Non è possibile ottenere la lista dei fogli per insufficienza di dati: il catasto deve essere fornito";
//                    break;
//                case "CPG_FOGLIO_PART_CT.FOGLIO":
//                    sValue = "Non è possibile ottenere la lista dei fogli per insufficienza di dati: il catasto deve essere fornito";
//                    break;
//                case "CPG_SIT_SUB.FOGLIO":
//                    sValue = "Non è possibile ottenere la lista dei fogli per insufficienza di dati: il catasto deve essere fornito";
//                    break;
//                case "CPG_FOGLIO_PART_CT.NUMERO":
//                    sValue = "Non è possibile ottenere la lista delle particelle per insufficienza di dati: il catasto ed il fabbricato/foglio devono essere forniti";
//                    break;
//                case "CPG_SIT_SUB.NUMERO":
//                    sValue = "Non è possibile ottenere la lista delle particelle per insufficienza di dati: il catasto ed il fabbricato/foglio devono essere forniti";
//                    break;
//                case "SUBALTERNO":
//                    sValue = "Non è possibile ottenere la lista dei sub per insufficienza di dati: il catasto ed il fabbricato/foglio,particella devono essere forniti";
//                    break;
//                case "CPG_FOGLIO_PART_CT.SUBALTERNO":
//                    sValue = "Non è possibile ottenere la lista dei sub per insufficienza di dati: il catasto ed il fabbricato/foglio,particella devono essere forniti";
//                    break;
//                case "CPG_SIT_SUB.SUBALTERNO":
//                    sValue = "Non è possibile ottenere la lista dei sub per insufficienza di dati: il catasto ed il fabbricato/foglio,particella devono essere forniti";
//                    break;
//                case "NUMERO":
//                    sValue = "Non è possibile ottenere la lista dei civici per insufficienza di dati: il fabbricato/via devono essere forniti";
//                    break;
//                case "ESP":
//                    sValue = "Non è possibile ottenere la lista degli esponenti per insufficienza di dati: il fabbricato/via ed il civico devono essere forniti";
//                    break;
//                case "IMMOBILE":
//                    sValue = "Non è possibile ottenere la lista delle unità immobiliari per insufficienza di dati: il catasto, il fabbricato/foglio,particella,sub devono essere forniti";
//                    break;
//                case "PK_SEQU_FABBRICATO":
//                    sValue = "Non è possibile ottenere la lista dei fabbricati per insufficienza di dati: la via/foglio,particella devono essere forniti";
//                    break;
//            }

//            return sValue;
//        }


//        private string GetCpgSitSub(string sField, IDataReader pDataReader, TipoQuery eTipoQuery)
//        {
//            var sRetVal = string.Empty;

//            if (string.IsNullOrEmpty(this.DataSit.TipoCatasto))
//            {
//                if (string.IsNullOrEmpty(this.DataSit.CodVia) && string.IsNullOrEmpty(this.DataSit.Civico) && string.IsNullOrEmpty(this.DataSit.Esponente) && string.IsNullOrEmpty(this.DataSit.CAP) && string.IsNullOrEmpty(this.DataSit.Circoscrizione) && string.IsNullOrEmpty(this.DataSit.Frazione))
//                {
//                    if (string.IsNullOrEmpty(this.DataSit.Fabbricato) && string.IsNullOrEmpty(this.DataSit.CodCivico))
//                        sRetVal = this.GetElemento(sField, "CPG_SIT_SUB", pDataReader, eTipoQuery);
//                    else
//                        sRetVal = this.GetElemento(sField, "CPG_SIT_SUB,CPG_SIT_CIVICI_FABBRICATI", pDataReader, eTipoQuery);
//                }
//                else
//                    sRetVal = this.GetElemento(sField, "CPG_SIT_SUB,CPG_SIT_CIVICI,CPG_SIT_CIVICI_FABBRICATI", pDataReader, eTipoQuery);

//                if (eTipoQuery == TipoQuery.Validazione)
//                {
//                    if (string.IsNullOrEmpty(sRetVal))
//                    {
//                        if ((sField == "SUBALTERNO") || (sField == "FOGLIO") || (sField == "NUMERO"))
//                            sRetVal = this.GetElemento(sField, "CPG_SIT_FOGLIO_PART_CT", pDataReader, eTipoQuery);
//                    }
//                }
//                else
//                {
//                    var sRetValCT = "";
//                    if ((sField == "SUBALTERNO") || (sField == "FOGLIO") || (sField == "NUMERO"))
//                        sRetValCT = this.GetElemento(sField, "CPG_SIT_FOGLIO_PART_CT", pDataReader, eTipoQuery);

//                    if (!string.IsNullOrEmpty(sRetVal))
//                    {
//                        if (!string.IsNullOrEmpty(sRetValCT))
//                            if (sRetVal != sRetValCT)
//                                sRetVal = string.Empty;
//                    }
//                    else
//                        if (!string.IsNullOrEmpty(sRetValCT))
//                            sRetVal = sRetValCT;
//                }
//            }
//            else
//            {
//                if (this.DataSit.TipoCatasto == "F")
//                {
//                    if (string.IsNullOrEmpty(this.DataSit.CodVia) && string.IsNullOrEmpty(this.DataSit.Civico) && string.IsNullOrEmpty(this.DataSit.Esponente) && string.IsNullOrEmpty(this.DataSit.CAP) && string.IsNullOrEmpty(this.DataSit.Circoscrizione) && string.IsNullOrEmpty(this.DataSit.Frazione))
//                    {
//                        if (string.IsNullOrEmpty(this.DataSit.Fabbricato) && string.IsNullOrEmpty(this.DataSit.CodCivico))
//                            sRetVal = this.GetElemento(sField, "CPG_SIT_SUB", pDataReader, eTipoQuery);
//                        else
//                            sRetVal = this.GetElemento(sField, "CPG_SIT_SUB,CPG_SIT_CIVICI_FABBRICATI", pDataReader, eTipoQuery);
//                    }
//                    else
//                        sRetVal = this.GetElemento(sField, "CPG_SIT_SUB,CPG_SIT_CIVICI,CPG_SIT_CIVICI_FABBRICATI", pDataReader, eTipoQuery);

//                }
//                else
//                {
//                    if ((sField == "SUBALTERNO") || (sField == "FOGLIO") || (sField == "NUMERO"))
//                        if (this.IsCampiToponomasticaImmobileVuoti())
//                            sRetVal = this.GetElemento(sField, "CPG_SIT_FOGLIO_PART_CT", pDataReader, eTipoQuery);
//                        else
//                            throw new CatastoException(this.GetMessageValidate("CPG_SIT_FOGLIO_PART_CT." + sField));
//                    else
//                        throw new CatastoException(this.GetMessageValidate("CPG_SIT_FOGLIO_PART_CT." + sField));
//                }
//            }

//            return sRetVal;
//        }

//        private string GetCpgSitCiviciFabbricati(string sField, IDataReader pDataReader, TipoQuery eTipoQuery)
//        {
//            var sRetVal = string.Empty;

//            if (string.IsNullOrEmpty(this.DataSit.TipoCatasto))
//            {
//                if (string.IsNullOrEmpty(this.DataSit.CodVia) && string.IsNullOrEmpty(this.DataSit.Civico) && string.IsNullOrEmpty(this.DataSit.Esponente) && string.IsNullOrEmpty(this.DataSit.CAP) && string.IsNullOrEmpty(this.DataSit.Circoscrizione) && string.IsNullOrEmpty(this.DataSit.Frazione))
//                {
//                    if (string.IsNullOrEmpty(this.DataSit.UI) && string.IsNullOrEmpty(this.DataSit.Sub))
//                        sRetVal = this.GetElemento(sField, "CPG_SIT_CIVICI_FABBRICATI", pDataReader, eTipoQuery);
//                    else
//                        sRetVal = this.GetElemento(sField, "CPG_SIT_CIVICI_FABBRICATI,CPG_SIT_SUB", pDataReader, eTipoQuery);
//                }
//                else
//                {
//                    if (string.IsNullOrEmpty(this.DataSit.UI) && string.IsNullOrEmpty(this.DataSit.Sub))
//                        sRetVal = this.GetElemento(sField, "CPG_SIT_CIVICI_FABBRICATI,CPG_SIT_CIVICI", pDataReader, eTipoQuery);
//                    else
//                        sRetVal = this.GetElemento(sField, "CPG_SIT_CIVICI_FABBRICATI,CPG_SIT_CIVICI,CPG_SIT_SUB", pDataReader, eTipoQuery);
//                }

//            }
//            else
//            {
//                if (this.DataSit.TipoCatasto == "F")
//                {
//                    if (string.IsNullOrEmpty(this.DataSit.CodVia) && string.IsNullOrEmpty(this.DataSit.Civico) && string.IsNullOrEmpty(this.DataSit.Esponente) && string.IsNullOrEmpty(this.DataSit.CAP) && string.IsNullOrEmpty(this.DataSit.Circoscrizione) && string.IsNullOrEmpty(this.DataSit.Frazione))
//                    {
//                        if (string.IsNullOrEmpty(this.DataSit.UI) && string.IsNullOrEmpty(this.DataSit.Sub))
//                            sRetVal = this.GetElemento(sField, "CPG_SIT_CIVICI_FABBRICATI", pDataReader, eTipoQuery);
//                        else
//                            sRetVal = this.GetElemento(sField, "CPG_SIT_CIVICI_FABBRICATI,CPG_SIT_SUB", pDataReader, eTipoQuery);
//                    }
//                    else
//                    {
//                        if (string.IsNullOrEmpty(this.DataSit.UI) && string.IsNullOrEmpty(this.DataSit.Sub))
//                            sRetVal = this.GetElemento(sField, "CPG_SIT_CIVICI_FABBRICATI,CPG_SIT_CIVICI", pDataReader, eTipoQuery);
//                        else
//                            sRetVal = this.GetElemento(sField, "CPG_SIT_CIVICI_FABBRICATI,CPG_SIT_CIVICI,CPG_SIT_SUB", pDataReader, eTipoQuery);
//                    }
//                }
//                else
//                {
//                    throw new CatastoException(this.GetMessageValidate(sField));
//                }
//            }

//            return sRetVal;
//        }



//        private string GetCpgSitCivici(string sField, IDataReader pDataReader, TipoQuery eTipoQuery)
//        {

//            var sRetVal = string.Empty;

//            if ((sField == "CAP") || (sField == "DESCR_LOCALITA") || (sField == "CIRCOSCRIZIONE") || (sField == "FK_STRADE"))
//            {
//                if (string.IsNullOrEmpty(this.DataSit.UI) && string.IsNullOrEmpty(this.DataSit.Sub))
//                {
//                    if (string.IsNullOrEmpty(this.DataSit.Fabbricato) && string.IsNullOrEmpty(this.DataSit.Foglio) && string.IsNullOrEmpty(this.DataSit.Particella))
//                        sRetVal = this.GetElemento(sField, "CPG_SIT_CIVICI", pDataReader, eTipoQuery);
//                    else
//                        sRetVal = this.GetElemento(sField, "CPG_SIT_CIVICI,CPG_SIT_CIVICI_FABBRICATI", pDataReader, eTipoQuery);
//                }
//                else
//                {
//                    sRetVal = this.GetElemento(sField, "CPG_SIT_CIVICI,CPG_SIT_CIVICI_FABBRICATI,CPG_SIT_SUB", pDataReader, eTipoQuery);
//                }
//            }
//            else
//            {
//                if (string.IsNullOrEmpty(this.DataSit.TipoCatasto))
//                {
//                    if (string.IsNullOrEmpty(this.DataSit.UI) && string.IsNullOrEmpty(this.DataSit.Sub))
//                    {
//                        if (string.IsNullOrEmpty(this.DataSit.Fabbricato) && string.IsNullOrEmpty(this.DataSit.Foglio) && string.IsNullOrEmpty(this.DataSit.Particella))
//                            sRetVal = this.GetElemento(sField, "CPG_SIT_CIVICI", pDataReader, eTipoQuery);
//                        else
//                            sRetVal = this.GetElemento(sField, "CPG_SIT_CIVICI,CPG_SIT_CIVICI_FABBRICATI", pDataReader, eTipoQuery);
//                    }
//                    else
//                        sRetVal = this.GetElemento(sField, "CPG_SIT_CIVICI,CPG_SIT_CIVICI_FABBRICATI,CPG_SIT_SUB", pDataReader, eTipoQuery);
//                }
//                else
//                {
//                    if (this.DataSit.TipoCatasto == "F")
//                    {
//                        if (string.IsNullOrEmpty(this.DataSit.UI) && string.IsNullOrEmpty(this.DataSit.Sub))
//                        {
//                            if (string.IsNullOrEmpty(this.DataSit.Fabbricato) && string.IsNullOrEmpty(this.DataSit.Foglio) && string.IsNullOrEmpty(this.DataSit.Particella))
//                                sRetVal = this.GetElemento(sField, "CPG_SIT_CIVICI", pDataReader, eTipoQuery);
//                            else
//                                sRetVal = this.GetElemento(sField, "CPG_SIT_CIVICI,CPG_SIT_CIVICI_FABBRICATI", pDataReader, eTipoQuery);
//                        }
//                        else
//                            sRetVal = this.GetElemento(sField, "CPG_SIT_CIVICI,CPG_SIT_CIVICI_FABBRICATI,CPG_SIT_SUB", pDataReader, eTipoQuery);
//                    }
//                    else
//                    {
//                        throw new CatastoException(this.GetMessageValidate(sField));
//                    }
//                }
//            }

//            return sRetVal;
//        }

//        private RetSit ElencoCpgSitSub(string sField, IDataReader pDataReader)
//        {
//            RetSit pRetSit = null;

//            if (string.IsNullOrEmpty(this.DataSit.TipoCatasto))
//            {
//                if (sField == "NUMERO")
//                    throw new CatastoException(this.GetMessageList("CPG_SIT_FOGLIO_PART_CT." + sField));
//                else
//                    throw new CatastoException(this.GetMessageList(sField));
//            }
//            else
//            {
//                if (this.DataSit.TipoCatasto == "F")
//                {
//                    if (string.IsNullOrEmpty(this.DataSit.CodVia) && string.IsNullOrEmpty(this.DataSit.Civico) && string.IsNullOrEmpty(this.DataSit.Esponente) && string.IsNullOrEmpty(this.DataSit.CAP) && string.IsNullOrEmpty(this.DataSit.Circoscrizione) && string.IsNullOrEmpty(this.DataSit.Frazione))
//                    {
//                        if (string.IsNullOrEmpty(this.DataSit.Fabbricato) && string.IsNullOrEmpty(this.DataSit.CodCivico))
//                            pRetSit = this.GetElenco(sField, "CPG_SIT_SUB", pDataReader);
//                        else
//                            pRetSit = this.GetElenco(sField, "CPG_SIT_SUB,CPG_SIT_CIVICI_FABBRICATI", pDataReader);
//                    }
//                    else
//                        pRetSit = this.GetElenco(sField, "CPG_SIT_SUB,CPG_SIT_CIVICI,CPG_SIT_CIVICI_FABBRICATI", pDataReader);

//                }
//                else
//                {
//                    if ((sField == "SUBALTERNO") || (sField == "FOGLIO") || (sField == "NUMERO"))
//                        if (this.IsCampiToponomasticaImmobileVuoti())
//                            pRetSit = this.GetElenco(sField, "CPG_SIT_FOGLIO_PART_CT", pDataReader);
//                        else
//                            pRetSit = new RetSit(true);
//                    else
//                        pRetSit = new RetSit(true);
//                }
//            }

//            return pRetSit;
//        }

//        private RetSit ElencoCpgSitCiviciFabbricati(string sField, IDataReader pDataReader)
//        {
//            RetSit pRetSit = null;

//            //if (string.IsNullOrEmpty(DataSit.TipoCatasto))
//            //    throw new CatastoException(GetMessageList(sField));
//            //else
//            //{
//            if (string.IsNullOrEmpty(this.DataSit.TipoCatasto) || (this.DataSit.TipoCatasto == "F"))
//            {
//                if (string.IsNullOrEmpty(this.DataSit.CodVia) && string.IsNullOrEmpty(this.DataSit.Civico) && string.IsNullOrEmpty(this.DataSit.Esponente) && string.IsNullOrEmpty(this.DataSit.CAP) && string.IsNullOrEmpty(this.DataSit.Circoscrizione) && string.IsNullOrEmpty(this.DataSit.Frazione))
//                {
//                    if (string.IsNullOrEmpty(this.DataSit.UI) && string.IsNullOrEmpty(this.DataSit.Sub))
//                        pRetSit = this.GetElenco(sField, "CPG_SIT_CIVICI_FABBRICATI", pDataReader);
//                    else
//                        pRetSit = this.GetElenco(sField, "CPG_SIT_CIVICI_FABBRICATI,CPG_SIT_SUB", pDataReader);
//                }
//                else
//                {
//                    if (string.IsNullOrEmpty(this.DataSit.UI) && string.IsNullOrEmpty(this.DataSit.Sub))
//                        pRetSit = this.GetElenco(sField, "CPG_SIT_CIVICI_FABBRICATI,CPG_SIT_CIVICI", pDataReader);
//                    else
//                        pRetSit = this.GetElenco(sField, "CPG_SIT_CIVICI_FABBRICATI,CPG_SIT_CIVICI,CPG_SIT_SUB", pDataReader);
//                }
//            }
//            else
//            {
//                pRetSit = new RetSit(true);
//            }
//            //}

//            return pRetSit;
//        }

//        private RetSit ElencoCpgSitCivici(string sField, IDataReader pDataReader)
//        {
//            RetSit pRetSit = null;

//            if ((sField == "CAP") || (sField == "DESCR_LOCALITA") || (sField == "CIRCOSCRIZIONE"))
//            {
//                if (string.IsNullOrEmpty(this.DataSit.UI) && string.IsNullOrEmpty(this.DataSit.Sub))
//                {
//                    if (string.IsNullOrEmpty(this.DataSit.Fabbricato) && string.IsNullOrEmpty(this.DataSit.Foglio) && string.IsNullOrEmpty(this.DataSit.Particella))
//                        pRetSit = this.GetElenco(sField, "CPG_SIT_CIVICI", pDataReader);
//                }
//                else
//                {
//                    pRetSit = this.GetElenco(sField, "CPG_SIT_CIVICI,CPG_SIT_CIVICI_FABBRICATI,CPG_SIT_SUB", pDataReader);
//                }
//            }
//            else
//            {
//                //if (string.IsNullOrEmpty(DataSit.TipoCatasto))
//                //    throw new CatastoException(GetMessageList(sField));
//                //else
//                //{
//                if (string.IsNullOrEmpty(this.DataSit.TipoCatasto) || (this.DataSit.TipoCatasto == "F"))
//                {
//                    if (string.IsNullOrEmpty(this.DataSit.UI) && string.IsNullOrEmpty(this.DataSit.Sub))
//                    {
//                        if (string.IsNullOrEmpty(this.DataSit.Fabbricato) && string.IsNullOrEmpty(this.DataSit.Foglio) && string.IsNullOrEmpty(this.DataSit.Particella))
//                            pRetSit = this.GetElenco(sField, "CPG_SIT_CIVICI", pDataReader);
//                        else
//                            pRetSit = this.GetElenco(sField, "CPG_SIT_CIVICI,CPG_SIT_CIVICI_FABBRICATI", pDataReader);
//                    }
//                    else
//                        pRetSit = this.GetElenco(sField, "CPG_SIT_CIVICI,CPG_SIT_CIVICI_FABBRICATI,CPG_SIT_SUB", pDataReader);
//                }
//                else
//                {
//                    pRetSit = new RetSit(true);
//                }
//                //}
//            }

//            return pRetSit;
//        }



//        /// <summary>
//        /// Restituisce il nome completo (OWNER.TABELLA) della tabella o della lista tabelle passato
//        /// </summary>
//        /// <param name="listaTabelle">tabella o lista tabelle separate da virgola che va qualificata con il nome dell'owner</param>
//        /// <returns>tabella o lista tabelle separata da virgola qualificata con il nome owner</returns>
//        private string GetCompleteTableName(string listaTabelle)
//        {
//            var nomeTabelleCompleto = string.Empty;
//            var tabelleList = listaTabelle.Split(new Char[] { ',' });

//            foreach (var tabella in tabelleList)
//            {
//                var tmpNomeTabella = tabella.Trim();

//                if (!String.IsNullOrEmpty(this._tableOwnler))
//                    tmpNomeTabella = this._tableOwnler + "." + tmpNomeTabella;

//                nomeTabelleCompleto += tmpNomeTabella + ",";
//            }

//            nomeTabelleCompleto = nomeTabelleCompleto.Remove(nomeTabelleCompleto.Length - 1);

//            return nomeTabelleCompleto;
//        }



//        /// <summary>
//        /// Restituisce il command text di una query a partire dal nome tabella e dal tipo di query
//        /// </summary>
//        /// <param name="fieldsList">campo o lista di campi separata da virgole da leggere nella query</param>
//        /// <param name="tableName">tabella o lista di tabelle separata da virgole su cui va eseguita la query</param>
//        /// <param name="tipoQuery">Tipo di query</param>
//        /// <returns>Command text della query</returns>
//        private string GetQuery(string fieldsList, string tableName, TipoQuery tipoQuery)
//        {
//            var sQuery = string.Empty;

//            switch (tableName)
//            {
//                case "CPG_SIT_CIVICI":
//                    switch (tipoQuery)
//                    {
//                        case TipoQuery.Elenco:
//                            sQuery = "Select distinct " + fieldsList + " from " + tableName + " where " +
//                                ((!string.IsNullOrEmpty(this.DataSit.CodVia) && (fieldsList != "FK_STRADE")) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("FK_STRADE") + " = '" + this.CodiceComune + this.LeftPad(this.RightTrim(this.DataSit.CodVia), 8, '0') + "' and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.Civico) && (fieldsList != "NUMERO")) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("NUMERO") + " = " + this.RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Civico) ? this.DataSit.Civico : "-1") + " and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.Esponente) && (fieldsList != "ESP")) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("ESP") + " = '" + this.RightTrim(this.DataSit.Esponente) + "' and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.CodCivico) && (fieldsList != "UK_CIVICO")) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("UK_CIVICO") + " = '" + this.RightTrim(this.DataSit.CodCivico) + "' and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.Circoscrizione) && (fieldsList != "CIRCOSCRIZIONE")) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CIRCOSCRIZIONE") + " = '" + this.RightTrim(this.DataSit.Circoscrizione) + "' and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.Frazione) && (fieldsList != "DESCR_LOCALITA")) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("DESCR_LOCALITA") + " = '" + this.RightTrim(this.DataSit.Frazione) + "' and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.CAP) && (fieldsList != "CAP")) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CAP") + " = '" + this.RightTrim(this.DataSit.CAP) + "' and " : "");
//                            break;
//                        case TipoQuery.Validazione:
//                            sQuery = "Select distinct " + fieldsList + " from " + tableName + " where " +
//                                ((!string.IsNullOrEmpty(this.DataSit.CodVia)) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("FK_STRADE") + " = '" + this.CodiceComune + this.LeftPad(this.RightTrim(this.DataSit.CodVia), 8, '0') + "' and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.Civico)) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("NUMERO") + " = " + this.RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Civico) ? this.DataSit.Civico : "-1") + " and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.Esponente)) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("ESP") + " = '" + this.RightTrim(this.DataSit.Esponente) + "' and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.CodCivico)) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("UK_CIVICO") + " = '" + this.RightTrim(this.DataSit.CodCivico) + "' and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.Circoscrizione)) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CIRCOSCRIZIONE") + " = '" + this.RightTrim(this.DataSit.Circoscrizione) + "' and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.Frazione)) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("DESCR_LOCALITA") + " = '" + this.RightTrim(this.DataSit.Frazione) + "' and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.CAP)) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CAP") + " = '" + this.RightTrim(this.DataSit.CAP) + "' and " : "");
//                            break;
//                    }
//                    break;
//                case "CPG_SIT_CIVICI_FABBRICATI":
//                    switch (tipoQuery)
//                    {
//                        case TipoQuery.Elenco:
//                            sQuery = "Select distinct " + fieldsList + " from " + tableName + " where " +
//                                ((!string.IsNullOrEmpty(this.DataSit.Fabbricato) && (fieldsList != "PK_SEQU_FABBRICATO")) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("PK_SEQU_FABBRICATO") + " = " + this.RightTrim(this.DataSit.Fabbricato) + " and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.Foglio) && (fieldsList != "FOGLIO_CU")) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("FOGLIO_CU") + " = '" + this.RightTrim(this.DataSit.Foglio) + "' and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.Particella) && (fieldsList != "PARTICELLA_CU")) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("PARTICELLA_CU") + " = '" + this.RightTrim(this.DataSit.Particella) + "' and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.CodCivico) && (fieldsList != "UK_CIVICO")) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("UK_CIVICO") + " = '" + this.RightTrim(this.DataSit.CodCivico) + "' and " : "");
//                            break;
//                        case TipoQuery.Validazione:
//                            sQuery = "Select distinct " + fieldsList + " from " + tableName + " where " +
//                                ((!string.IsNullOrEmpty(this.DataSit.Fabbricato)) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("PK_SEQU_FABBRICATO") + " = " + this.RightTrim(this.DataSit.Fabbricato) + " and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.Foglio)) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("FOGLIO_CU") + " = '" + this.RightTrim(this.DataSit.Foglio) + "' and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.Particella)) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("PARTICELLA_CU") + " = '" + this.RightTrim(this.DataSit.Particella) + "' and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.CodCivico)) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("UK_CIVICO") + " = '" + this.RightTrim(this.DataSit.CodCivico) + "' and " : "");
//                            break;
//                    }
//                    break;
//                case "CPG_SIT_SUB":
//                    switch (tipoQuery)
//                    {
//                        case TipoQuery.Elenco:
//                            sQuery = "Select distinct " + fieldsList + " from " + tableName + " where " +
//                                ((!string.IsNullOrEmpty(this.DataSit.Foglio) && (fieldsList != "ESC_FOGLIO")) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("ESC_FOGLIO") + " = '" + this.LeftPad(this.RightTrim(this.DataSit.Foglio), 5) + "' and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.Particella) && (fieldsList != "ESC_NUMERO")) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("ESC_NUMERO") + " = '" + this.LeftPad(this.RightTrim(this.DataSit.Particella), 5) + "' and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.UI) && (fieldsList != "IMMOBILE")) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("IMMOBILE") + " = " + this.RightTrim(this.DataSit.UI) + " and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.Sub) && (fieldsList != "SUBALTERNO")) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("SUBALTERNO") + " = '" + this.LeftPad(this.RightTrim(this.DataSit.Sub), 4) + "' and " : "");
//                            break;
//                        case TipoQuery.Validazione:
//                            sQuery = "Select distinct " + fieldsList + " from " + tableName + " where " +
//                                ((!string.IsNullOrEmpty(this.DataSit.Foglio)) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("ESC_FOGLIO") + " = '" + this.LeftPad(this.RightTrim(this.DataSit.Foglio), 5) + "' and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.Particella)) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("ESC_NUMERO") + " = '" + this.LeftPad(this.RightTrim(this.DataSit.Particella), 5) + "' and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.UI)) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("IMMOBILE") + " = " + this.RightTrim(this.DataSit.UI) + " and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.Sub)) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("SUBALTERNO") + " = '" + this.LeftPad(this.RightTrim(this.DataSit.Sub), 4) + "' and " : "");
//                            break;
//                    }
//                    break;
//                case "CPG_SIT_FOGLIO_PART_CT":
//                    switch (tipoQuery)
//                    {
//                        case TipoQuery.Elenco:
//                            sQuery = "Select distinct " + fieldsList + " from " + tableName + " where " +
//                                ((!string.IsNullOrEmpty(this.DataSit.Foglio) && (fieldsList != "FOGLIO")) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("FOGLIO") + " = '" + this.RightTrim(this.DataSit.Foglio) + "' and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.Particella) && (fieldsList != "NUMERO")) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("NUMERO") + " = '" + this.RightTrim(this.DataSit.Particella) + "' and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.UI) && (fieldsList != "IMMOBILE")) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("IMMOBILE") + " = " + this.RightTrim(this.DataSit.UI) + " and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.Sub) && (fieldsList != "SUBALTERNO")) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("SUBALTERNO") + " = '" + this.LeftPad(this.RightTrim(this.DataSit.Sub), 4) + "' and " : "");
//                            break;
//                        case TipoQuery.Validazione:
//                            sQuery = "Select distinct " + fieldsList + " from " + tableName + " where " +
//                                ((!string.IsNullOrEmpty(this.DataSit.Foglio)) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("FOGLIO") + " = '" + this.RightTrim(this.DataSit.Foglio) + "' and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.Particella)) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("NUMERO") + " = '" + this.RightTrim(this.DataSit.Particella) + "' and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.UI)) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("IMMOBILE") + " = " + this.RightTrim(this.DataSit.UI) + " and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.Sub)) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("SUBALTERNO") + " = '" + this.LeftPad(this.RightTrim(this.DataSit.Sub), 4) + "' and " : "");
//                            break;
//                    }
//                    break;
//                case "CAT_PARTICELLE_GAUSS":
//                    switch (tipoQuery)
//                    {
//                        case TipoQuery.Elenco:
//                            sQuery = "select " + fieldsList + " from " + this.GetCompleteTableName(tableName).Split(new Char[] { ',' })[0] + " where esc_foglio = '" + this.LeftPad(this.RightTrim(this.DataSit.Foglio), 5) + "' and esc_particella = '" + this.LeftPad(this.RightTrim(this.DataSit.Particella), 5) + "' and layer = 'PARTICELLE'";
//                            break;
//                    }
//                    break;
//                case "VIEW_URB_SOTTOZONE,CAT_PARTICELLE_GAUSS":
//                case "CAT_PARTICELLE_GAUSS,VIEW_URB_SOTTOZONE":
//                    switch (tipoQuery)
//                    {
//                        case TipoQuery.Elenco:
//                            sQuery = "Select distinct " + fieldsList + " from " +
//                                     this.GetCompleteTableName(tableName).Split(new Char[] { ',' })[0] + " " +
//                                     "where " +
//                                     "SDO_RELATE(" + this.GetCompleteTableName(tableName).Split(new Char[] { ',' })[0] + ".GEOMETRY, " +
//                                     "(select GEOMETRY from " + this.GetCompleteTableName(tableName).Split(new Char[] { ',' })[1] + " where esc_foglio = '" + this.LeftPad(this.RightTrim(this.DataSit.Foglio), 5) + "' and esc_particella = '" + this.LeftPad(this.RightTrim(this.DataSit.Particella), 5) + "' and layer = 'PARTICELLE'),'mask =ANYINTERACT querytype=WINDOW') = 'TRUE'";
//                            break;
//                    }
//                    break;
//                case "CPG_SIT_CIVICI,CPG_SIT_CIVICI_FABBRICATI":
//                case "CPG_SIT_CIVICI_FABBRICATI,CPG_SIT_CIVICI":
//                    switch (tipoQuery)
//                    {
//                        case TipoQuery.Elenco:
//                            sQuery = "Select distinct " + tableName.Split(new Char[] { ',' })[0] + "." + fieldsList + " from " + tableName + " where " +
//                                "CPG_SIT_CIVICI.UK_CIVICO = CPG_SIT_CIVICI_FABBRICATI.UK_CIVICO and " +
//                                ((!string.IsNullOrEmpty(this.DataSit.Fabbricato) && (fieldsList != "PK_SEQU_FABBRICATO")) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI.PK_SEQU_FABBRICATO") + " = " + this.RightTrim(this.DataSit.Fabbricato) + " and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.Foglio) && (fieldsList != "FOGLIO_CU")) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI." + "FOGLIO_CU") + " = '" + this.RightTrim(this.DataSit.Foglio) + "' and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.Particella) && (fieldsList != "PARTICELLA_CU")) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI." + "PARTICELLA_CU") + " = '" + this.RightTrim(this.DataSit.Particella) + "' and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.CodVia) && (fieldsList != "FK_STRADE")) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI.FK_STRADE") + " = '" + this.CodiceComune + this.LeftPad(this.RightTrim(this.DataSit.CodVia), 8, '0') + "' and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.Civico) && (fieldsList != "NUMERO")) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI.NUMERO") + " = " + this.RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Civico) ? this.DataSit.Civico : "-1") + " and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.Esponente) && (fieldsList != "ESP")) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI.ESP") + " = '" + this.RightTrim(this.DataSit.Esponente) + "' and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.CodCivico) && (fieldsList != "UK_CIVICO")) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI.UK_CIVICO") + " = '" + this.RightTrim(this.DataSit.CodCivico) + "' and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.Circoscrizione) && (fieldsList != "CIRCOSCRIZIONE")) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI.CIRCOSCRIZIONE") + " = '" + this.RightTrim(this.DataSit.Circoscrizione) + "' and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.Frazione) && (fieldsList != "DESCR_LOCALITA")) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI.DESCR_LOCALITA") + " = '" + this.RightTrim(this.DataSit.Frazione) + "' and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.CAP) && (fieldsList != "CAP")) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI.CAP") + " = '" + this.RightTrim(this.DataSit.CAP) + "' and " : "");
//                            break;
//                        case TipoQuery.Validazione:
//                            sQuery = "Select distinct " + tableName.Split(new Char[] { ',' })[0] + "." + fieldsList + " from " + tableName + " where " +
//                                "CPG_SIT_CIVICI.UK_CIVICO = CPG_SIT_CIVICI_FABBRICATI.UK_CIVICO and " +
//                                ((!string.IsNullOrEmpty(this.DataSit.Fabbricato)) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI.PK_SEQU_FABBRICATO") + " = " + this.RightTrim(this.DataSit.Fabbricato) + " and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.Foglio)) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI." + "FOGLIO_CU") + " = '" + this.RightTrim(this.DataSit.Foglio) + "' and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.Particella)) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI." + "PARTICELLA_CU") + " = '" + this.RightTrim(this.DataSit.Particella) + "' and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.CodVia)) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI.FK_STRADE") + " = '" + this.CodiceComune + this.LeftPad(this.RightTrim(this.DataSit.CodVia), 8, '0') + "' and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.Civico)) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI.NUMERO") + " = " + this.RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Civico) ? this.DataSit.Civico : "-1") + " and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.Esponente)) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI.ESP") + " = '" + this.RightTrim(this.DataSit.Esponente) + "' and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.CodCivico)) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI.UK_CIVICO") + " = '" + this.RightTrim(this.DataSit.CodCivico) + "' and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.Circoscrizione)) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI.CIRCOSCRIZIONE") + " = '" + this.RightTrim(this.DataSit.Circoscrizione) + "' and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.Frazione)) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI.DESCR_LOCALITA") + " = '" + this.RightTrim(this.DataSit.Frazione) + "' and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.CAP)) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI.CAP") + " = '" + this.RightTrim(this.DataSit.CAP) + "' and " : "");
//                            break;
//                    }
//                    break;
//                case "CPG_SIT_SUB,CPG_SIT_CIVICI_FABBRICATI":
//                case "CPG_SIT_CIVICI_FABBRICATI,CPG_SIT_SUB":
//                    switch (tipoQuery)
//                    {
//                        case TipoQuery.Elenco:
//                            sQuery = "Select distinct " + tableName.Split(new Char[] { ',' })[0] + "." + fieldsList + " from " + tableName + " where " +
//                                "LPAD(CPG_SIT_CIVICI_FABBRICATI.FOGLIO_CU,5,'0') = CPG_SIT_SUB.ESC_FOGLIO and " +
//                                "LPAD(CPG_SIT_CIVICI_FABBRICATI.PARTICELLA_CU,5,'0') = CPG_SIT_SUB.ESC_NUMERO and " +
//                                ((!string.IsNullOrEmpty(this.DataSit.UI) && (fieldsList != "IMMOBILE")) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_SUB.IMMOBILE") + " = " + this.RightTrim(this.DataSit.UI) + " and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.Sub) && (fieldsList != "SUBALTERNO")) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_SUB.SUBALTERNO") + " = '" + this.LeftPad(this.RightTrim(this.DataSit.Sub), 4) + "' and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.Fabbricato) && (fieldsList != "PK_SEQU_FABBRICATO")) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI.PK_SEQU_FABBRICATO") + " = " + this.RightTrim(this.DataSit.Fabbricato) + " and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.Foglio) && (fieldsList != "FOGLIO_CU")) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI.FOGLIO_CU") + " = '" + this.RightTrim(this.DataSit.Foglio) + "' and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.Particella) && (fieldsList != "PARTICELLA_CU")) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI.PARTICELLA_CU") + " = '" + this.RightTrim(this.DataSit.Particella) + "' and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.CodCivico) && (fieldsList != "UK_CIVICO")) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI.UK_CIVICO") + " = '" + this.RightTrim(this.DataSit.CodCivico) + "' and " : "");
//                            break;
//                        case TipoQuery.Validazione:
//                            sQuery = "Select distinct " + tableName.Split(new Char[] { ',' })[0] + "." + fieldsList + " from " + tableName + " where " +
//                                "LPAD(CPG_SIT_CIVICI_FABBRICATI.FOGLIO_CU,5,'0') = CPG_SIT_SUB.ESC_FOGLIO and " +
//                                "LPAD(CPG_SIT_CIVICI_FABBRICATI.PARTICELLA_CU,5,'0') = CPG_SIT_SUB.ESC_NUMERO and " +
//                                ((!string.IsNullOrEmpty(this.DataSit.UI)) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_SUB.IMMOBILE") + " = " + this.RightTrim(this.DataSit.UI) + " and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.Sub)) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_SUB.SUBALTERNO") + " = '" + this.LeftPad(this.RightTrim(this.DataSit.Sub), 4) + "' and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.Fabbricato)) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI.PK_SEQU_FABBRICATO") + " = " + this.RightTrim(this.DataSit.Fabbricato) + " and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.Foglio)) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI.FOGLIO_CU") + " = '" + this.RightTrim(this.DataSit.Foglio) + "' and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.Particella)) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI.PARTICELLA_CU") + " = '" + this.RightTrim(this.DataSit.Particella) + "' and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.CodCivico)) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI.UK_CIVICO") + " = '" + this.RightTrim(this.DataSit.CodCivico) + "' and " : "");
//                            break;
//                    }
//                    break;
//                //case "CPG_SIT_FOGLIO_PART_CT,CPG_SIT_CIVICI_FABBRICATI":
//                //case "CPG_SIT_CIVICI_FABBRICATI,CPG_SIT_FOGLIO_PART_CT":
//                //    switch (eTipoQuery)
//                //    {
//                //        case TipoQuery.Elenco:
//                //            sQuery = "Select distinct " + sTableName.Split(new Char[] { ',' })[0] + "." + sField + " from " + sTableName + " where " +
//                //                "CPG_SIT_CIVICI_FABBRICATI.FOGLIO_CT = CPG_SIT_FOGLIO_PART_CT.FOGLIO and " +
//                //                "CPG_SIT_CIVICI_FABBRICATI.PARTICELLA_CT = CPG_SIT_FOGLIO_PART_CT.NUMERO and " +
//                //                ((!string.IsNullOrEmpty(this.DataSit.UI) && (sField != "IMMOBILE")) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_FOGLIO_PART_CT.IMMOBILE") + " = " + RightTrimSit(this.DataSit.UI) + " and " : "") +
//                //                ((!string.IsNullOrEmpty(this.DataSit.Sub) && (sField != "SUBALTERNO")) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_FOGLIO_PART_CT.SUBALTERNO") + " = '" + LeftPadSit(RightTrimSit(this.DataSit.Sub), 4) + "' and " : "") +
//                //                ((!string.IsNullOrEmpty(this.DataSit.Fabbricato) && (sField != "PK_SEQU_FABBRICATO")) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI.PK_SEQU_FABBRICATO") + " = " + RightTrimSit(this.DataSit.Fabbricato) + " and " : "") +
//                //                ((!string.IsNullOrEmpty(this.DataSit.Foglio) && (sField != GetFieldCatasto("FOGLIO"))) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI.FOGLIO_CT") + " = '" + RightTrimSit(this.DataSit.Foglio) + "' and " : "") +
//                //                ((!string.IsNullOrEmpty(this.DataSit.Particella) && (sField != GetFieldCatasto("PARTICELLA"))) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI.PARTICELLA_CT") + " = '" + RightTrimSit(this.DataSit.Particella) + "' and " : "") +
//                //                ((!string.IsNullOrEmpty(this.DataSit.CodCivico) && (sField != "UK_CIVICO")) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI.UK_CIVICO") + " = '" + RightTrimSit(this.DataSit.CodCivico) + "' and " : "");
//                //            break;
//                //        case TipoQuery.Validazione:
//                //            sQuery = "Select distinct " + sTableName.Split(new Char[] { ',' })[0] + "." + sField + " from " + sTableName + " where " +
//                //                "CPG_SIT_CIVICI_FABBRICATI.FOGLIO_CT = CPG_SIT_FOGLIO_PART_CT.FOGLIO and " +
//                //                "CPG_SIT_CIVICI_FABBRICATI.PARTICELLA_CT = CPG_SIT_FOGLIO_PART_CT.NUMERO and " +
//                //                ((!string.IsNullOrEmpty(this.DataSit.UI)) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_FOGLIO_PART_CT.IMMOBILE") + " = " + RightTrimSit(this.DataSit.UI) + " and " : "") +
//                //                ((!string.IsNullOrEmpty(this.DataSit.Sub)) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_FOGLIO_PART_CT.SUBALTERNO") + " = '" + LeftPadSit(RightTrimSit(this.DataSit.Sub), 4) + "' and " : "") +
//                //                ((!string.IsNullOrEmpty(this.DataSit.Fabbricato)) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI.PK_SEQU_FABBRICATO") + " = " + RightTrimSit(this.DataSit.Fabbricato) + " and " : "") +
//                //                ((!string.IsNullOrEmpty(this.DataSit.Foglio)) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI.FOGLIO_CT") + " = '" + RightTrimSit(this.DataSit.Foglio) + "' and " : "") +
//                //                ((!string.IsNullOrEmpty(this.DataSit.Particella)) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI.PARTICELLA_CT") + " = '" + RightTrimSit(this.DataSit.Particella) + "' and " : "") +
//                //                ((!string.IsNullOrEmpty(this.DataSit.CodCivico)) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI.UK_CIVICO") + " = '" + RightTrimSit(this.DataSit.CodCivico) + "' and " : "");
//                //            break;
//                //    }
//                //    break;
//                case "CPG_SIT_CIVICI,CPG_SIT_CIVICI_FABBRICATI,CPG_SIT_SUB":
//                case "CPG_SIT_CIVICI_FABBRICATI,CPG_SIT_CIVICI,CPG_SIT_SUB":
//                case "CPG_SIT_SUB,CPG_SIT_CIVICI,CPG_SIT_CIVICI_FABBRICATI":
//                    switch (tipoQuery)
//                    {
//                        case TipoQuery.Elenco:
//                            sQuery = "Select distinct " + tableName.Split(new Char[] { ',' })[0] + "." + fieldsList + " from " + tableName + " where " +
//                                "CPG_SIT_CIVICI.UK_CIVICO = CPG_SIT_CIVICI_FABBRICATI.UK_CIVICO and " +
//                                "LPAD(CPG_SIT_CIVICI_FABBRICATI.FOGLIO_CU,5,'0') = CPG_SIT_SUB.ESC_FOGLIO and " +
//                                "LPAD(CPG_SIT_CIVICI_FABBRICATI.PARTICELLA_CU,5,'0') = CPG_SIT_SUB.ESC_NUMERO and " +
//                                ((!string.IsNullOrEmpty(this.DataSit.UI) && (fieldsList != "IMMOBILE")) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_SUB.IMMOBILE") + " = " + this.RightTrim(this.DataSit.UI) + " and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.Sub) && (fieldsList != "SUBALTERNO")) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_SUB.SUBALTERNO") + " = '" + this.LeftPad(this.RightTrim(this.DataSit.Sub), 4) + "' and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.Fabbricato) && (fieldsList != "PK_SEQU_FABBRICATO")) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI.PK_SEQU_FABBRICATO") + " = " + this.RightTrim(this.DataSit.Fabbricato) + " and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.Foglio) && (fieldsList != "FOGLIO_CU")) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI.FOGLIO_CU") + " = '" + this.RightTrim(this.DataSit.Foglio) + "' and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.Particella) && (fieldsList != "PARTICELLA_CU")) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI.PARTICELLA_CU") + " = '" + this.RightTrim(this.DataSit.Particella) + "' and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.CodVia) && (fieldsList != "FK_STRADE")) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI.FK_STRADE") + " = '" + this.CodiceComune + this.LeftPad(this.RightTrim(this.DataSit.CodVia), 8, '0') + "' and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.Civico) && (fieldsList != "NUMERO")) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI.NUMERO") + " = " + this.RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Civico) ? this.DataSit.Civico : "-1") + " and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.Esponente) && (fieldsList != "ESP")) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI.ESP") + " = '" + this.RightTrim(this.DataSit.Esponente) + "' and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.CodCivico) && (fieldsList != "UK_CIVICO")) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI.UK_CIVICO") + " = '" + this.RightTrim(this.DataSit.CodCivico) + "' and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.Circoscrizione) && (fieldsList != "CIRCOSCRIZIONE")) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI.CIRCOSCRIZIONE") + " = '" + this.RightTrim(this.DataSit.Circoscrizione) + "' and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.Frazione) && (fieldsList != "DESCR_LOCALITA")) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI.DESCR_LOCALITA") + " = '" + this.RightTrim(this.DataSit.Frazione) + "' and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.CAP) && (fieldsList != "CAP")) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI.CAP") + " = '" + this.RightTrim(this.DataSit.CAP) + "' and " : "");
//                            break;
//                        case TipoQuery.Validazione:
//                            sQuery = "Select distinct " + tableName.Split(new Char[] { ',' })[0] + "." + fieldsList + " from " + tableName + " where " +
//                                "CPG_SIT_CIVICI.UK_CIVICO = CPG_SIT_CIVICI_FABBRICATI.UK_CIVICO and " +
//                                "LPAD(CPG_SIT_CIVICI_FABBRICATI.FOGLIO_CU,5,'0') = CPG_SIT_SUB.ESC_FOGLIO and " +
//                                "LPAD(CPG_SIT_CIVICI_FABBRICATI.PARTICELLA_CU,5,'0') = CPG_SIT_SUB.ESC_NUMERO and " +
//                                ((!string.IsNullOrEmpty(this.DataSit.UI)) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_SUB.IMMOBILE") + " = " + this.RightTrim(this.DataSit.UI) + " and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.Sub)) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_SUB.SUBALTERNO") + " = '" + this.LeftPad(this.RightTrim(this.DataSit.Sub), 4) + "' and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.Fabbricato)) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI.PK_SEQU_FABBRICATO") + " = " + this.RightTrim(this.DataSit.Fabbricato) + " and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.Foglio)) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI.FOGLIO_CU") + " = '" + this.RightTrim(this.DataSit.Foglio) + "' and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.Particella)) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI.PARTICELLA_CU") + " = '" + this.RightTrim(this.DataSit.Particella) + "' and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.CodVia)) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI.FK_STRADE") + " = '" + this.CodiceComune + this.LeftPad(this.RightTrim(this.DataSit.CodVia), 8, '0') + "' and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.Civico)) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI.NUMERO") + " = " + this.RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Civico) ? this.DataSit.Civico : "-1") + " and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.Esponente)) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI.ESP") + " = '" + this.RightTrim(this.DataSit.Esponente) + "' and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.CodCivico)) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI.UK_CIVICO") + " = '" + this.RightTrim(this.DataSit.CodCivico) + "' and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.Circoscrizione)) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI.CIRCOSCRIZIONE") + " = '" + this.RightTrim(this.DataSit.Circoscrizione) + "' and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.Frazione)) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI.DESCR_LOCALITA") + " = '" + this.RightTrim(this.DataSit.Frazione) + "' and " : "") +
//                                ((!string.IsNullOrEmpty(this.DataSit.CAP)) ? this.InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI.CAP") + " = '" + this.RightTrim(this.DataSit.CAP) + "' and " : "");
//                            break;
//                    }
//                    break;
//                    //case "CPG_SIT_CIVICI,CPG_SIT_CIVICI_FABBRICATI,CPG_SIT_FOGLIO_PART_CT":
//                    //case "CPG_SIT_CIVICI_FABBRICATI,CPG_SIT_CIVICI,CPG_SIT_FOGLIO_PART_CT":
//                    //case "CPG_SIT_FOGLIO_PART_CT,CPG_SIT_CIVICI,CPG_SIT_CIVICI_FABBRICATI":
//                    //    switch (eTipoQuery)
//                    //    {
//                    //        case TipoQuery.Elenco:
//                    //            sQuery = "Select distinct " + sTableName.Split(new Char[] { ',' })[0] + "." + sField + " from " + sTableName + " where " +
//                    //                "CPG_SIT_CIVICI.UK_CIVICO = CPG_SIT_CIVICI_FABBRICATI.UK_CIVICO and " +
//                    //                "CPG_SIT_CIVICI_FABBRICATI.FOGLIO_CT = CPG_SIT_FOGLIO_PART_CT.FOGLIO and " +
//                    //                "CPG_SIT_CIVICI_FABBRICATI.PARTICELLA_CT = CPG_SIT_FOGLIO_PART_CT.NUMERO and " +
//                    //                ((!string.IsNullOrEmpty(this.DataSit.UI) && (sField != "IMMOBILE")) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_FOGLIO_PART_CT.IMMOBILE") + " = " + RightTrimSit(this.DataSit.UI) + " and " : "") +
//                    //                ((!string.IsNullOrEmpty(this.DataSit.Sub) && (sField != "SUBALTERNO")) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_FOGLIO_PART_CT.SUBALTERNO") + " = '" + LeftPadSit(RightTrimSit(this.DataSit.Sub), 4) + "' and " : "") +
//                    //                ((!string.IsNullOrEmpty(this.DataSit.Fabbricato) && (sField != "PK_SEQU_FABBRICATO")) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI.PK_SEQU_FABBRICATO") + " = " + RightTrimSit(this.DataSit.Fabbricato) + " and " : "") +
//                    //                ((!string.IsNullOrEmpty(this.DataSit.Foglio) && (sField != "FOGLIO_CT")) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI.FOGLIO_CT") + " = '" + RightTrimSit(this.DataSit.Foglio) + "' and " : "") +
//                    //                ((!string.IsNullOrEmpty(this.DataSit.Particella) && (sField != "PARTICELLA_CT")) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI.PARTICELLA_CT") + " = '" + RightTrimSit(this.DataSit.Particella) + "' and " : "") +
//                    //                ((!string.IsNullOrEmpty(this.DataSit.CodVia) && (sField != "FK_STRADE")) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_CIVICI.FK_STRADE") + " = '" + CodiceComune + LeftPadSit(RightTrimSit(this.DataSit.CodVia), 8, '0') + "' and " : "") +
//                    //                ((!string.IsNullOrEmpty(this.DataSit.Civico) && (sField != "NUMERO")) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_CIVICI.NUMERO") + " = " + RightTrimSit(Init.Utils.StringChecker.IsNumeric(this.DataSit.Civico) ? this.DataSit.Civico : "-1") + " and " : "") +
//                    //                ((!string.IsNullOrEmpty(this.DataSit.Esponente) && (sField != "ESP")) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_CIVICI.ESP") + " = '" + RightTrimSit(this.DataSit.Esponente) + "' and " : "") +
//                    //                ((!string.IsNullOrEmpty(this.DataSit.CodCivico) && (sField != "UK_CIVICO")) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_CIVICI.UK_CIVICO") + " = '" + RightTrimSit(this.DataSit.CodCivico) + "' and " : "") +
//                    //                ((!string.IsNullOrEmpty(this.DataSit.Circoscrizione) && (sField != "CIRCOSCRIZIONE")) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_CIVICI.CIRCOSCRIZIONE") + " = '" + RightTrimSit(this.DataSit.Circoscrizione) + "' and " : "") +
//                    //                ((!string.IsNullOrEmpty(this.DataSit.Frazione) && (sField != "DESCR_LOCALITA")) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_CIVICI.DESCR_LOCALITA") + " = '" + RightTrimSit(this.DataSit.Frazione) + "' and " : "") +
//                    //                ((!string.IsNullOrEmpty(this.DataSit.CAP) && (sField != "CAP")) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_CIVICI.CAP") + " = '" + RightTrimSit(this.DataSit.CAP) + "' and " : "");
//                    //            break;
//                    //        case TipoQuery.Validazione:
//                    //            sQuery = "Select distinct " + sTableName.Split(new Char[] { ',' })[0] + "." + sField + " from " + sTableName + " where " +
//                    //                "CPG_SIT_CIVICI.UK_CIVICO = CPG_SIT_CIVICI_FABBRICATI.UK_CIVICO and " +
//                    //                "CPG_SIT_CIVICI_FABBRICATI.FOGLIO_CT = CPG_SIT_FOGLIO_PART_CT.FOGLIO and " +
//                    //                "CPG_SIT_CIVICI_FABBRICATI.PARTICELLA_CT = CPG_SIT_FOGLIO_PART_CT.NUMERO and " +
//                    //                ((!string.IsNullOrEmpty(this.DataSit.UI)) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_FOGLIO_PART_CT.IMMOBILE") + " = " + RightTrimSit(this.DataSit.UI) + " and " : "") +
//                    //                ((!string.IsNullOrEmpty(this.DataSit.Sub)) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_FOGLIO_PART_CT.SUBALTERNO") + " = '" + LeftPadSit(RightTrimSit(this.DataSit.Sub), 4) + "' and " : "") +
//                    //                ((!string.IsNullOrEmpty(this.DataSit.Fabbricato)) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI.PK_SEQU_FABBRICATO") + " = " + RightTrimSit(this.DataSit.Fabbricato) + " and " : "") +
//                    //                ((!string.IsNullOrEmpty(this.DataSit.Foglio)) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI.FOGLIO_CT") + " = '" + RightTrimSit(this.DataSit.Foglio) + "' and " : "") +
//                    //                ((!string.IsNullOrEmpty(this.DataSit.Particella)) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI.PARTICELLA_CT") + " = '" + RightTrimSit(this.DataSit.Particella) + "' and " : "") +
//                    //                ((!string.IsNullOrEmpty(this.DataSit.CodVia)) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_CIVICI.FK_STRADE") + " = '" + CodiceComune + LeftPadSit(RightTrimSit(this.DataSit.CodVia), 8, '0') + "' and " : "") +
//                    //                ((!string.IsNullOrEmpty(this.DataSit.Civico)) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_CIVICI.NUMERO") + " = " + RightTrimSit(Init.Utils.StringChecker.IsNumeric(this.DataSit.Civico) ? this.DataSit.Civico : "-1") + " and " : "") +
//                    //                ((!string.IsNullOrEmpty(this.DataSit.Esponente)) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_CIVICI.ESP") + " = '" + RightTrimSit(this.DataSit.Esponente) + "' and " : "") +
//                    //                ((!string.IsNullOrEmpty(this.DataSit.CodCivico)) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_CIVICI.UK_CIVICO") + " = '" + RightTrimSit(this.DataSit.CodCivico) + "' and " : "") +
//                    //                ((!string.IsNullOrEmpty(this.DataSit.Circoscrizione)) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_CIVICI.CIRCOSCRIZIONE") + " = '" + RightTrimSit(this.DataSit.Circoscrizione) + "' and " : "") +
//                    //                ((!string.IsNullOrEmpty(this.DataSit.Frazione)) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_CIVICI.DESCR_LOCALITA") + " = '" + RightTrimSit(this.DataSit.Frazione) + "' and " : "") +
//                    //                ((!string.IsNullOrEmpty(this.DataSit.CAP)) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_CIVICI.CAP") + " = '" + RightTrimSit(this.DataSit.CAP) + "' and " : "");
//                    //            break;
//                    //    }
//                    //    break;
//            }

//            if (sQuery.EndsWith("and "))
//                sQuery = sQuery.Remove(sQuery.Length - 4, 4) + " order by " + tableName.Split(new Char[] { ',' })[0] + "." + fieldsList;
//            else if (sQuery.EndsWith("where "))
//                sQuery = sQuery.Remove(sQuery.Length - 6, 6) + " order by " + tableName.Split(new Char[] { ',' })[0] + "." + fieldsList;
//            else
//                sQuery = sQuery + " order by " + tableName.Split(new Char[] { ',' })[0] + "." + fieldsList;

//            return sQuery;
//        }


//        private string GetQuery(string[] aField, string sTableName, TipoQuery eTipoQuery)
//        {
//            var sQuery = string.Empty;
//            var sField = string.Empty;

//            foreach (var elem in aField)
//                sField += elem + ",";

//            sField = sField.Remove(sField.Length - 1);

//            switch (sTableName)
//            {
//                case "VIEW_URB_SOTTOZONE,CAT_PARTICELLE_GAUSS":
//                    switch (eTipoQuery)
//                    {
//                        case TipoQuery.Elenco:
//                            sQuery = "Select " + sField + " from " +
//                                     this.GetCompleteTableName(sTableName).Split(new Char[] { ',' })[0] + " " +
//                                     "where " +
//                                     "SDO_RELATE(" + this.GetCompleteTableName(sTableName).Split(new Char[] { ',' })[0] + ".GEOMETRY, " +
//                                     "(select GEOMETRY from " + this.GetCompleteTableName(sTableName).Split(new Char[] { ',' })[1] + " where esc_foglio = '" + this.LeftPad(this.RightTrim(this.DataSit.Foglio), 5) + "' and esc_particella = '" + this.LeftPad(this.RightTrim(this.DataSit.Particella), 5) + "' and layer = 'PARTICELLE'),'mask =ANYINTERACT querytype=WINDOW') = 'TRUE'";
//                            break;
//                    }
//                    break;
//            }

//            if (sQuery.EndsWith("and "))
//                sQuery = sQuery.Remove(sQuery.Length - 4, 4) + " order by " + sTableName.Split(new Char[] { ',' })[0] + "." + aField[0];
//            else if (sQuery.EndsWith("where "))
//                sQuery = sQuery.Remove(sQuery.Length - 6, 6) + " order by " + sTableName.Split(new Char[] { ',' })[0] + "." + aField[0];
//            else
//                sQuery = sQuery + " order by " + sTableName.Split(new Char[] { ',' })[0] + "." + aField[0];

//            return sQuery;
//        }


//        private string GetElemento(string sField, string sTableName, IDataReader pDataReader, TipoQuery eTipoQuery)
//        {
//            var sRetVal = "";
//            var iCount = 0;
//            this.EnsureConnectionIsOpen();
//            this.sCommandText = this.GetQuery(sField, sTableName, eTipoQuery);

//            var pCommand = this.InternalDatabaseConnection.CreateCommand(this.sCommandText);
//            pDataReader = pCommand.ExecuteReader();

//            while (pDataReader.Read())
//            {
//                var sDato = this.RightTrim(pDataReader[sField].ToString());

//                iCount++;
//                if (iCount == 1)
//                    sRetVal = sDato;
//                else
//                {
//                    sRetVal = "";
//                    break;
//                }
//            }
//            pCommand.Dispose();

//            return sRetVal;
//        }


//        private RetSit GetElenco(string sField, string sTableName, IDataReader pDataReader)
//        {
//            var pRetSit = new RetSit(true);
//            this.EnsureConnectionIsOpen();
//            this.sCommandText = this.GetQuery(sField, sTableName, TipoQuery.Elenco);

//            var pCommand = this.InternalDatabaseConnection.CreateCommand(this.sCommandText);
//            pDataReader = pCommand.ExecuteReader();

//            while (pDataReader.Read())
//            {
//                var sDato = this.RightTrim(pDataReader[sField].ToString());

//                if (!string.IsNullOrEmpty(sDato) && !pRetSit.DataCollection.Contains(sDato))
//                {
//                    pRetSit.DataCollection.Add(sDato);
//                }
//            }
//            pCommand.Dispose();

//            return pRetSit;
//        }

//        private RetSit GetElenco(string[] aField, string sTableName, IDataReader pDataReader)
//        {
//            var pRetSit = new RetSit(true);
//            this.EnsureConnectionIsOpen();

//            //Ricavo il PK_PARTICELLE
//            this.sCommandText = this.GetQuery("PK_PARTICELLE", "CAT_PARTICELLE_GAUSS", TipoQuery.Elenco);

//            var pCommand = this.InternalDatabaseConnection.CreateCommand(this.sCommandText);
//            pDataReader = pCommand.ExecuteReader();

//            var iCount = 1;
//            var sDato = string.Empty;
//            while (pDataReader.Read())
//            {
//                if (iCount > 1)
//                    throw new Exception("Troppi record");
//                sDato = this.RightTrim(pDataReader["PK_PARTICELLE"].ToString());
//                iCount++;
//            }

//            pCommand.Dispose();

//            this.sCommandText = this.GetQuery(aField, sTableName, TipoQuery.Elenco);

//            pCommand = this.InternalDatabaseConnection.CreateCommand(this.sCommandText);
//            pDataReader = pCommand.ExecuteReader();

//            var list = new List<string>();
//            var iIndex = 0;
//            while (pDataReader.Read())
//            {
//                var sField1 = aField[0];
//                var sDato1 = this.RightTrim(pDataReader[sField1].ToString());
//                //Verifico se per per quel foglio e particella esistono strumenti urbanistici (con relative date di inizio validità e fine validità) uguali
//                //e ne  prendo solo 1
//                if (!list.Contains(sDato1))
//                {
//                    pRetSit.DataMap.Add(sField1 + iIndex, sDato1);
//                    list.Add(sDato1);

//                    //Inizio validità
//                    var sField2 = aField[1];
//                    var sDato2 = this.RightTrim(pDataReader[sField2].ToString());
//                    pRetSit.DataMap.Add(sField2 + iIndex, sDato2);

//                    //Fine validità
//                    var sField3 = aField[2];
//                    var sDato3 = this.RightTrim(pDataReader[sField3].ToString());
//                    pRetSit.DataMap.Add(sField3 + iIndex, sDato3);
//                }
//                var sField4 = aField[3];
//                var sDato4 = this.RightTrim(pDataReader[sField4].ToString());

//                pRetSit.DataMap.Add(sField4 + iIndex, sDato4);

//                var sField5 = aField[4];
//                var sDato5 = this.RightTrim(pDataReader[sField5].ToString());

//                this.sCommandText = "select mdsys.SDO_GEOM.SDO_AREA(the_geom,0.005) as AREA from " +
//                               "(SELECT SDO_GEOM.SDO_INTERSECTION(p.GEOMETRY , s.geometry, 0.005) as the_geom " +
//                               "FROM cpg_dbintegrato_esc.view_urb_sottozone s, cpg_dbintegrato_esc.cat_particelle_gauss p " +
//                               "where p.pk_particelle = " + sDato + " and s.pk_sequ_sottozona = " + sDato5 + ")";

//                var pCommandArea = this.InternalDatabaseConnection.CreateCommand(this.sCommandText);
//                var pDataReaderArea = pCommandArea.ExecuteReader();
//                pDataReaderArea.Read();
//                pRetSit.DataMap.Add("AREA" + iIndex, pDataReaderArea["AREA"].ToString());

//                pCommandArea.Dispose();
//                pDataReaderArea.Close();

//                //File Name
//                var sField6 = aField[5];
//                var sDato6 = this.RightTrim(pDataReader[sField6].ToString());

//                pRetSit.DataMap.Add(sField6 + iIndex, sDato6);

//                iIndex++;
//            }
//            pCommand.Dispose();

//            return pRetSit;
//        }
//        #endregion



//        #region Metodi per ottenere elenchi di elementi catastali o facenti parte dell'indirizzo
//        public override RetSit ElencoCivici()
//        {
//            IDataReader pDataReader = null;
//            RetSit pRetSit;

//            try
//            {
//                if (!String.IsNullOrEmpty(this.DataSit.CodVia) || !String.IsNullOrEmpty(this.DataSit.Fabbricato))
//                {
//                    pRetSit = this.ElencoCpgSitCivici("NUMERO", pDataReader);
//                }
//                else
//                {
//                    pRetSit = this.RestituisciErroreSit(this.GetMessageList("NUMERO"), MessageCode.ElencoCivici, false);
//                }
//            }
//            catch (CatastoException ex)
//            {
//                this._logger.ErrorFormat("SITESC.ElencoCivici: CatastoException durante la lettura dei civici-> {0}", ex.ToString());

//                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.ElencoCivici, false);
//            }
//            catch (Exception ex)
//            {
//                this._logger.ErrorFormat("SITESC.ElencoCivici: Exception durante la lettura dei civici-> {0}", ex.ToString());

//                throw new Exception("Errore durante la restituzione dell'elenco dei civici. Metodo: ElencoCivici, modulo: SitEsc. " + ex.Message + "\r\n Query: " + this.sCommandText);
//            }
//            finally
//            {
//                if (pDataReader != null)
//                    pDataReader.Close();

//                this.InternalDatabaseConnection.Connection.Close();
//            }

//            return pRetSit;
//        }



//        public override RetSit ElencoEsponenti()
//        {
//            IDataReader pDataReader = null;
//            RetSit pRetSit;

//            try
//            {
//                if (!String.IsNullOrEmpty(this.DataSit.Civico) && (!String.IsNullOrEmpty(this.DataSit.CodVia) || !String.IsNullOrEmpty(this.DataSit.Fabbricato)))
//                {
//                    pRetSit = this.ElencoCpgSitCivici("ESP", pDataReader);
//                }
//                else
//                {
//                    pRetSit = this.RestituisciErroreSit(this.GetMessageList("ESP"), MessageCode.ElencoEsponenti, false);
//                }
//            }
//            catch (CatastoException ex)
//            {
//                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.ElencoEsponenti, false);
//            }
//            catch (Exception ex)
//            {
//                throw new Exception("Errore durante la restituzione dell'elenco degli esponenti. Metodo: ElencoEsponenti, modulo: SitEsc. " + ex.Message + "\r\n Query: " + this.sCommandText);
//            }
//            finally
//            {
//                if (pDataReader != null)
//                    pDataReader.Close();
//                this.InternalDatabaseConnection.Connection.Close();
//            }

//            return pRetSit;
//        }

//        public override RetSit ElencoFabbricati()
//        {
//            IDataReader pDataReader = null;
//            RetSit pRetSit;

//            try
//            {
//                if (!String.IsNullOrEmpty(this.DataSit.CodVia) || (!String.IsNullOrEmpty(this.DataSit.Foglio) && !String.IsNullOrEmpty(this.DataSit.Particella)))
//                {
//                    pRetSit = this.ElencoCpgSitCiviciFabbricati("PK_SEQU_FABBRICATO", pDataReader);
//                }
//                else
//                {
//                    pRetSit = this.RestituisciErroreSit(this.GetMessageList("PK_SEQU_FABBRICATO"), MessageCode.ElencoCivici, false);
//                }
//            }
//            catch (CatastoException ex)
//            {
//                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.ElencoFabbricati, false);
//            }
//            catch (Exception ex)
//            {
//                throw new Exception("Errore durante la restituzione dell'elenco dei fabbricati. Metodo: ElencoFabbricati, modulo: SitEsc. " + ex.Message + "\r\n Query: " + this.sCommandText);
//            }
//            finally
//            {
//                if (pDataReader != null)
//                    pDataReader.Close();
//                this.InternalDatabaseConnection.Connection.Close();
//            }

//            return pRetSit;
//        }


//        public override RetSit ElencoFogli()
//        {
//            IDataReader pDataReader = null;
//            RetSit pRetSit;

//            try
//            {
//                pRetSit = this.ElencoCpgSitSub("FOGLIO", pDataReader);

//                char[] charsToTrim = { '0' };

//                for (var iCount = 0; iCount < pRetSit.DataCollection.Count; iCount++)
//                {
//                    pRetSit.DataCollection[iCount] = this.LeftTrim(pRetSit.DataCollection[iCount], charsToTrim);
//                }
//            }
//            catch (CatastoException ex)
//            {
//                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.ElencoFogli, false);
//            }
//            catch (Exception ex)
//            {
//                throw new Exception("Errore durante la restituzione dell'elenco dei fogli. Metodo: ElencoFogli, modulo: SitEsc. " + ex.Message + "\r\n Query: " + this.sCommandText);
//            }
//            finally
//            {
//                if (pDataReader != null)
//                    pDataReader.Close();
//                this.InternalDatabaseConnection.Connection.Close();
//            }

//            return pRetSit;
//        }

//        public override RetSit ElencoParticelle()
//        {
//            IDataReader pDataReader = null;
//            RetSit pRetSit;

//            try
//            {
//                if ((!String.IsNullOrEmpty(this.DataSit.Fabbricato)) || !String.IsNullOrEmpty(this.DataSit.Foglio))
//                {
//                    pRetSit = this.ElencoCpgSitSub("NUMERO", pDataReader);

//                    char[] charsToTrim = { '0' };

//                    for (var iCount = 0; iCount < pRetSit.DataCollection.Count; iCount++)
//                    {
//                        pRetSit.DataCollection[iCount] = this.LeftTrim(pRetSit.DataCollection[iCount], charsToTrim);
//                    }
//                }
//                else
//                {
//                    pRetSit = this.RestituisciErroreSit(this.GetMessageList("CPG_FOGLIO_PART_CT.NUMERO"), MessageCode.ElencoParticelle, false);
//                }
//            }
//            catch (CatastoException ex)
//            {
//                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.ElencoParticelle, false);
//            }
//            catch (Exception ex)
//            {
//                throw new Exception("Errore durante la restituzione dell'elenco delle particella. Metodo: ElencoParticelle, modulo: SitEsc. " + ex.Message + "\r\n Query: " + this.sCommandText);
//            }
//            finally
//            {
//                if (pDataReader != null)
//                    pDataReader.Close();
//                this.InternalDatabaseConnection.Connection.Close();
//            }

//            return pRetSit;
//        }

//        public override RetSit ElencoSub()
//        {
//            IDataReader pDataReader = null;
//            RetSit pRetSit;

//            try
//            {
//                if ((!String.IsNullOrEmpty(this.DataSit.Fabbricato)) || (!String.IsNullOrEmpty(this.DataSit.Foglio) && !String.IsNullOrEmpty(this.DataSit.Particella)))
//                {
//                    pRetSit = this.ElencoCpgSitSub("SUBALTERNO", pDataReader);

//                    char[] charsToTrim = { '0' };

//                    for (var iCount = 0; iCount < pRetSit.DataCollection.Count; iCount++)
//                    {
//                        pRetSit.DataCollection[iCount] = this.LeftTrim(pRetSit.DataCollection[iCount], charsToTrim);
//                    }
//                }
//                else
//                {
//                    pRetSit = this.RestituisciErroreSit(this.GetMessageList("SUBALTERNO"), MessageCode.ElencoCivici, false);
//                }
//            }
//            catch (CatastoException ex)
//            {
//                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.ElencoSub, false);
//            }
//            catch (Exception ex)
//            {
//                throw new Exception("Errore durante la restituzione dell'elenco dei sub. Metodo: ElencoSub, modulo: SitEsc. " + ex.Message + "\r\n Query: " + this.sCommandText);
//            }
//            finally
//            {
//                if (pDataReader != null)
//                    pDataReader.Close();
//                this.InternalDatabaseConnection.Connection.Close();
//            }

//            return pRetSit;
//        }

//        public override RetSit ElencoUI()
//        {
//            IDataReader pDataReader = null;
//            RetSit pRetSit;

//            try
//            {
//                if ((!String.IsNullOrEmpty(this.DataSit.Fabbricato)) || (!String.IsNullOrEmpty(this.DataSit.Foglio) && !String.IsNullOrEmpty(this.DataSit.Particella) && !String.IsNullOrEmpty(this.DataSit.Sub)))
//                {
//                    pRetSit = this.ElencoCpgSitSub("IMMOBILE", pDataReader);
//                }
//                else
//                {
//                    pRetSit = this.RestituisciErroreSit(this.GetMessageList("IMMOBILE"), MessageCode.ElencoCivici, false);
//                }
//            }
//            catch (CatastoException ex)
//            {
//                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.ElencoUI, false);
//            }
//            catch (Exception ex)
//            {
//                throw new Exception("Errore durante la restituzione dell'elenco delle UI. Metodo: ElencoUI, modulo: SitEsc. " + ex.Message + "\r\n Query: " + this.sCommandText);
//            }
//            finally
//            {
//                if (pDataReader != null)
//                    pDataReader.Close();
//                this.InternalDatabaseConnection.Connection.Close();
//            }

//            return pRetSit;
//        }

//        public override RetSit ElencoSottoZone()
//        {
//            IDataReader pDataReader = null;
//            RetSit pRetSit;

//            try
//            {
//                if (!String.IsNullOrEmpty(this.DataSit.Foglio) && !String.IsNullOrEmpty(this.DataSit.Particella))
//                {
//                    pRetSit = this.GetElenco("CODICE_SOTTOZONA", "VIEW_URB_SOTTOZONE,CAT_PARTICELLE_GAUSS", pDataReader);
//                }
//                else
//                {
//                    pRetSit = this.RestituisciErroreSit("Non è possibile ottenere la lista delle sottozone per insufficienza di dati:il foglio e la particella devono essere forniti", MessageCode.ElencoSottoZone, false);
//                }
//            }
//            catch (Exception ex)
//            {
//                throw new Exception("Errore durante la restituzione dell'elenco delle sottozone. Metodo: ElencoSottoZone, modulo: SitEsc. " + ex.Message + "\r\n Query: " + this.sCommandText);
//            }
//            finally
//            {
//                if (pDataReader != null)
//                    pDataReader.Close();
//                this.InternalDatabaseConnection.Connection.Close();
//            }

//            return pRetSit;
//        }

//        public override RetSit ElencoDatiUrbanistici()
//        {
//            IDataReader pDataReader = null;
//            RetSit pRetSit;

//            try
//            {
//                if (!String.IsNullOrEmpty(this.DataSit.Foglio) && !String.IsNullOrEmpty(this.DataSit.Particella))
//                {
//                    string[] aField = { "STRUM_URBANISTICO", "INIZIO_VALIDITA", "FINE_VALIDITA", "CODICE_SOTTOZONA", "PK_SEQU_SOTTOZONA", "DOC_FILENAME" };
//                    pRetSit = this.GetElenco(aField, "VIEW_URB_SOTTOZONE,CAT_PARTICELLE_GAUSS", pDataReader);
//                }
//                else
//                {
//                    pRetSit = this.RestituisciErroreSit("Non è possibile ottenere la lista dei dati urbanistici per insufficienza di dati:il foglio e la particella devono essere forniti", MessageCode.ElencoDatiUrbanistici, false);
//                }
//            }
//            catch (Exception ex)
//            {
//                throw new Exception("Errore durante la restituzione dell'elenco dei dati urbanistici. Metodo: ElencoDatiUrbanistici, modulo: SitEsc. " + ex.Message + "\r\n Query: " + this.sCommandText);
//            }
//            finally
//            {
//                if (pDataReader != null)
//                    pDataReader.Close();
//                this.InternalDatabaseConnection.Connection.Close();
//            }

//            return pRetSit;
//        }
//        #endregion

//        #region Metodi per la verifica e la restituzione di un singolo elemento catastale o facente parte dell'indirizzo

//        protected override string GetCAP()
//        {
//            var sRetVal = "";
//            IDataReader pDataReader = null;

//            try
//            {
//                sRetVal = this.GetCpgSitCivici("CAP", pDataReader, TipoQuery.Elenco);
//            }
//            catch (CatastoException)
//            {
//            }
//            catch (Exception ex)
//            {
//                throw new Exception("Errore durante la restituzione di un CAP. Metodo: GetCAP, modulo: SitEsc. " + ex.Message + "\r\n Query: " + this.sCommandText);
//            }
//            finally
//            {
//                if (pDataReader != null)
//                    pDataReader.Close();
//                this.InternalDatabaseConnection.Connection.Close();
//            }

//            return sRetVal;
//        }

//        protected override RetSit VerificaCAP()
//        {
//            RetSit pRetSit;
//            IDataReader pDataReader = null;

//            try
//            {
//                var sElem = this.GetCpgSitCivici("CAP", pDataReader, TipoQuery.Validazione);
//                if (!String.IsNullOrEmpty(sElem))
//                {
//                    pRetSit = new RetSit(true);
//                    this.DataSit.CAP = sElem;
//                }
//                else
//                {
//                    pRetSit = this.RestituisciErroreSit(this.GetMessageValidate("CAP"), MessageCode.CAPValidazione, false);
//                }
//            }
//            catch (CatastoException ex)
//            {
//                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.CAPValidazione, false);
//            }
//            catch (Exception ex)
//            {
//                throw new Exception("Errore durante la validazione di un CAP. Metodo: VerificaCAP, modulo: SitEsc. " + ex.Message + "\r\n Query: " + this.sCommandText);
//            }
//            finally
//            {
//                if (pDataReader != null)
//                    pDataReader.Close();
//                this.InternalDatabaseConnection.Connection.Close();
//            }

//            return pRetSit;
//        }

//        protected override string GetFrazione()
//        {
//            var sRetVal = "";
//            IDataReader pDataReader = null;

//            try
//            {
//                sRetVal = this.GetCpgSitCivici("DESCR_LOCALITA", pDataReader, TipoQuery.Elenco);
//            }
//            catch (CatastoException)
//            {
//            }
//            catch (Exception ex)
//            {
//                throw new Exception("Errore durante la restituzione della località. Metodo: GetFrazione, modulo: SitEsc. " + ex.Message + "\r\n Query: " + this.sCommandText);
//            }
//            finally
//            {
//                if (pDataReader != null)
//                    pDataReader.Close();
//                this.InternalDatabaseConnection.Connection.Close();
//            }

//            return sRetVal;
//        }

//        protected override RetSit VerificaFrazione()
//        {
//            RetSit pRetSit;
//            IDataReader pDataReader = null;

//            try
//            {
//                var sElem = this.GetCpgSitCivici("DESCR_LOCALITA", pDataReader, TipoQuery.Validazione);
//                if (!String.IsNullOrEmpty(sElem))
//                {
//                    pRetSit = new RetSit(true);
//                    this.DataSit.Frazione = sElem;
//                }
//                else
//                {
//                    pRetSit = this.RestituisciErroreSit(this.GetMessageValidate("DESCR_LOCALITA"), MessageCode.FrazioneValidazione, false);
//                }
//            }
//            catch (CatastoException ex)
//            {
//                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.FrazioneValidazione, false);
//            }
//            catch (Exception ex)
//            {
//                throw new Exception("Errore durante la validazione di una località. Metodo: VerificaFrazione, modulo: SitEsc. " + ex.Message + "\r\n Query: " + this.sCommandText);
//            }
//            finally
//            {
//                if (pDataReader != null)
//                    pDataReader.Close();
//                this.InternalDatabaseConnection.Connection.Close();
//            }

//            return pRetSit;
//        }

//        protected override string GetCircoscrizione()
//        {
//            var sRetVal = "";
//            IDataReader pDataReader = null;

//            try
//            {
//                sRetVal = this.GetCpgSitCivici("CIRCOSCRIZIONE", pDataReader, TipoQuery.Elenco);
//            }
//            catch (CatastoException)
//            {
//            }
//            catch (Exception ex)
//            {
//                throw new Exception("Errore durante la restituzione di una circoscrizione. Metodo: GetCircoscrizione, modulo: SitEsc. " + ex.Message + "\r\n Query: " + this.sCommandText);
//            }
//            finally
//            {
//                if (pDataReader != null)
//                    pDataReader.Close();
//                this.InternalDatabaseConnection.Connection.Close();
//            }

//            return sRetVal;
//        }

//        protected override RetSit VerificaCircoscrizione()
//        {
//            RetSit pRetSit;
//            IDataReader pDataReader = null;

//            try
//            {
//                var sElem = this.GetCpgSitCivici("CIRCOSCRIZIONE", pDataReader, TipoQuery.Validazione);
//                if (!String.IsNullOrEmpty(sElem))
//                {
//                    pRetSit = new RetSit(true);
//                    this.DataSit.Circoscrizione = sElem;
//                }
//                else
//                {
//                    pRetSit = this.RestituisciErroreSit(this.GetMessageValidate("CIRCOSCRIZIONE"), MessageCode.CircoscrizioneValidazione, false);
//                }
//            }
//            catch (CatastoException ex)
//            {
//                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.CircoscrizioneValidazione, false);
//            }
//            catch (Exception ex)
//            {
//                throw new Exception("Errore durante la validazione di una circoscrizione. Metodo: VerificaCircoscrizione, modulo: SitEsc. " + ex.Message + "\r\n Query: " + this.sCommandText);
//            }
//            finally
//            {
//                if (pDataReader != null)
//                    pDataReader.Close();
//                this.InternalDatabaseConnection.Connection.Close();
//            }

//            return pRetSit;
//        }



//        protected override string GetCodFabbricato()
//        {
//            var sRetVal = "";
//            IDataReader pDataReader = null;

//            try
//            {
//                sRetVal = this.GetCpgSitCiviciFabbricati("PK_SEQU_FABBRICATO", pDataReader, TipoQuery.Elenco);
//            }
//            catch (CatastoException)
//            {
//            }
//            catch (Exception ex)
//            {
//                throw new Exception("Errore durante la restituzione di un fabbricato. Metodo: GetCodFabbricato, modulo: SitEsc. " + ex.Message + "\r\n Query: " + this.sCommandText);
//            }
//            finally
//            {
//                if (pDataReader != null)
//                    pDataReader.Close();
//                this.InternalDatabaseConnection.Connection.Close();
//            }

//            return sRetVal;
//        }

//        protected override RetSit VerificaFabbricato()
//        {
//            RetSit pRetSit;
//            IDataReader pDataReader = null;

//            try
//            {
//                var sElem = this.GetCpgSitCiviciFabbricati("PK_SEQU_FABBRICATO", pDataReader, TipoQuery.Validazione);
//                if (!String.IsNullOrEmpty(sElem))
//                {
//                    pRetSit = new RetSit(true);
//                    this.DataSit.Fabbricato = sElem;
//                }
//                else
//                {
//                    pRetSit = this.RestituisciErroreSit(this.GetMessageValidate("PK_SEQU_FABBRICATO"), MessageCode.FabbricatoValidazione, false);
//                }
//            }
//            catch (CatastoException ex)
//            {
//                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.FabbricatoValidazione, false);
//            }
//            catch (Exception ex)
//            {
//                throw new Exception("Errore durante la validazione di un fabbricato. Metodo: VerificaFabbricato, modulo: SitEsc. " + ex.Message + "\r\n Query: " + this.sCommandText);
//            }
//            finally
//            {
//                if (pDataReader != null)
//                    pDataReader.Close();
//                this.InternalDatabaseConnection.Connection.Close();
//            }

//            return pRetSit;
//        }


//        protected override RetSit VerificaTipoCatasto()
//        {
//            RetSit pRetSit = null;

//            if (this.IsCampiToponomasticaImmobileVuoti())
//                pRetSit = new RetSit(true);
//            else
//            {
//                if (this.DataSit.TipoCatasto == "T")
//                    pRetSit = this.RestituisciErroreSit("Il tipocatasto " + this.DataSit.TipoCatasto + " non è valido per i dati inseriti", MessageCode.TipoCatastoValidazione, false);
//                else
//                    pRetSit = new RetSit(true);
//            }

//            return pRetSit;
//        }





//        protected override string GetFoglio()
//        {
//            var sRetVal = "";
//            IDataReader pDataReader = null;

//            try
//            {
//                sRetVal = this.GetCpgSitSub("FOGLIO", pDataReader, TipoQuery.Elenco);

//                char[] charsToTrim = { '0' };
//                sRetVal = this.LeftTrim(sRetVal, charsToTrim);
//            }
//            catch (CatastoException)
//            {
//            }
//            catch (Exception ex)
//            {
//                throw new Exception("Errore durante la restituzione di un foglio. Metodo: GetFoglio, modulo: SitEsc. " + ex.Message + "\r\n Query: " + this.sCommandText);
//            }
//            finally
//            {
//                if (pDataReader != null)
//                    pDataReader.Close();
//                this.InternalDatabaseConnection.Connection.Close();
//            }

//            return sRetVal;
//        }

//        protected override RetSit VerificaFoglio()
//        {
//            RetSit pRetSit;
//            IDataReader pDataReader = null;

//            try
//            {
//                var sElem = this.GetCpgSitSub("FOGLIO", pDataReader, TipoQuery.Validazione);
//                if (!String.IsNullOrEmpty(sElem))
//                {
//                    pRetSit = new RetSit(true);

//                    char[] charsToTrim = { '0' };
//                    sElem = this.LeftTrim(sElem, charsToTrim);

//                    this.DataSit.Foglio = sElem;
//                }
//                else
//                {
//                    pRetSit = this.RestituisciErroreSit(this.GetMessageValidate("FOGLIO"), MessageCode.FoglioValidazione, false);
//                }
//            }
//            catch (CatastoException ex)
//            {
//                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.FoglioValidazione, false);
//            }
//            catch (Exception ex)
//            {
//                throw new Exception("Errore durante la validazione di un foglio. Metodo: VerificaFoglio, modulo: SitEsc. " + ex.Message + "\r\n Query: " + this.sCommandText);
//            }
//            finally
//            {
//                if (pDataReader != null)
//                    pDataReader.Close();
//                this.InternalDatabaseConnection.Connection.Close();
//            }

//            return pRetSit;
//        }

//        protected override string GetParticella()
//        {
//            var sRetVal = "";
//            IDataReader pDataReader = null;

//            try
//            {
//                sRetVal = this.GetCpgSitSub("NUMERO", pDataReader, TipoQuery.Elenco);

//                char[] charsToTrim = { '0' };
//                sRetVal = this.LeftTrim(sRetVal, charsToTrim);
//            }
//            catch (CatastoException)
//            {
//            }
//            catch (Exception ex)
//            {
//                throw new Exception("Errore durante la restituzione di una particella. Metodo: GetParticella, modulo: SitEsc. " + ex.Message + "\r\n Query: " + this.sCommandText);
//            }
//            finally
//            {
//                if (pDataReader != null)
//                    pDataReader.Close();
//                this.InternalDatabaseConnection.Connection.Close();
//            }

//            return sRetVal;
//        }

//        protected override RetSit VerificaParticella()
//        {
//            RetSit pRetSit;
//            IDataReader pDataReader = null;

//            try
//            {
//                var sElem = this.GetCpgSitSub("NUMERO", pDataReader, TipoQuery.Validazione);
//                if (!String.IsNullOrEmpty(sElem))
//                {
//                    pRetSit = new RetSit(true);

//                    char[] charsToTrim = { '0' };
//                    sElem = this.LeftTrim(sElem, charsToTrim);

//                    this.DataSit.Particella = sElem;
//                }
//                else
//                {
//                    pRetSit = this.RestituisciErroreSit(this.GetMessageValidate("CPG_FOGLIO_PART_CT.NUMERO"), MessageCode.ParticellaValidazione, false);
//                }
//            }
//            catch (CatastoException ex)
//            {
//                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.ParticellaValidazione, false);
//            }
//            catch (Exception ex)
//            {
//                throw new Exception("Errore durante la validazione di una particella. Metodo: VerificaParticella, modulo: SitEsc. " + ex.Message + "\r\n Query: " + this.sCommandText);
//            }
//            finally
//            {
//                if (pDataReader != null)
//                    pDataReader.Close();
//                this.InternalDatabaseConnection.Connection.Close();
//            }

//            return pRetSit;
//        }

//        protected override string GetSub()
//        {
//            var sRetVal = "";
//            IDataReader pDataReader = null;

//            try
//            {
//                sRetVal = this.GetCpgSitSub("SUBALTERNO", pDataReader, TipoQuery.Elenco);

//                char[] charsToTrim = { '0' };
//                sRetVal = this.LeftTrim(sRetVal, charsToTrim);
//            }
//            catch (CatastoException)
//            {
//            }
//            catch (Exception ex)
//            {
//                throw new Exception("Errore durante la restituzione di un sub. Metodo: GetSub, modulo: SitEsc. " + ex.Message + "\r\n Query: " + this.sCommandText);
//            }
//            finally
//            {
//                if (pDataReader != null)
//                    pDataReader.Close();
//                this.InternalDatabaseConnection.Connection.Close();
//            }

//            return sRetVal;
//        }

//        protected override RetSit VerificaSub()
//        {
//            RetSit pRetSit;
//            IDataReader pDataReader = null;

//            try
//            {
//                var sElem = this.GetCpgSitSub("SUBALTERNO", pDataReader, TipoQuery.Validazione);
//                if (!String.IsNullOrEmpty(sElem))
//                {
//                    pRetSit = new RetSit(true);

//                    char[] charsToTrim = { '0' };
//                    sElem = this.LeftTrim(sElem, charsToTrim);

//                    this.DataSit.Sub = sElem;
//                }
//                else
//                {
//                    pRetSit = this.RestituisciErroreSit(this.GetMessageValidate("SUBALTERNO"), MessageCode.SubValidazione, false);
//                }
//            }
//            catch (CatastoException ex)
//            {
//                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.SubValidazione, false);
//            }
//            catch (Exception ex)
//            {
//                throw new Exception("Errore durante la validazione di un sub. Metodo: VerificaSub, modulo: SitEsc. " + ex.Message + "\r\n Query: " + this.sCommandText);
//            }
//            finally
//            {
//                if (pDataReader != null)
//                    pDataReader.Close();
//                this.InternalDatabaseConnection.Connection.Close();
//            }

//            return pRetSit;
//        }

//        protected override string GetUI()
//        {
//            var sRetVal = "";
//            IDataReader pDataReader = null;

//            try
//            {
//                sRetVal = this.GetCpgSitSub("IMMOBILE", pDataReader, TipoQuery.Elenco);
//            }
//            catch (CatastoException)
//            {
//            }
//            catch (Exception ex)
//            {
//                throw new Exception("Errore durante la restituzione di un'UI. Metodo: GetUI, modulo: SitEsc. " + ex.Message + "\r\n Query: " + this.sCommandText);
//            }
//            finally
//            {
//                if (pDataReader != null)
//                    pDataReader.Close();
//                this.InternalDatabaseConnection.Connection.Close();
//            }

//            return sRetVal;
//        }

//        protected override RetSit VerificaUI()
//        {
//            RetSit pRetSit;
//            IDataReader pDataReader = null;

//            try
//            {
//                var sElem = this.GetCpgSitSub("IMMOBILE", pDataReader, TipoQuery.Validazione);
//                if (!String.IsNullOrEmpty(sElem))
//                {
//                    pRetSit = new RetSit(true);
//                    this.DataSit.UI = sElem;
//                }
//                else
//                {
//                    pRetSit = this.RestituisciErroreSit(this.GetMessageValidate("IMMOBILE"), MessageCode.UIValidazione, false);
//                }
//            }
//            catch (CatastoException ex)
//            {
//                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.UIValidazione, false);
//            }
//            catch (Exception ex)
//            {
//                throw new Exception("Errore durante la validazione di un'UI. Metodo: VerificaUI, modulo: SitEsc. " + ex.Message + "\r\n Query: " + this.sCommandText);
//            }
//            finally
//            {
//                if (pDataReader != null)
//                    pDataReader.Close();
//                this.InternalDatabaseConnection.Connection.Close();
//            }

//            return pRetSit;
//        }

//        protected override string GetCivico()
//        {
//            var sRetVal = "";
//            IDataReader pDataReader = null;

//            try
//            {
//                sRetVal = this.GetCpgSitCivici("NUMERO", pDataReader, TipoQuery.Elenco);
//            }
//            catch (CatastoException)
//            {
//            }
//            catch (Exception ex)
//            {
//                throw new Exception("Errore durante la restituzione di un civico. Metodo: GetCivico, modulo: SitEsc. " + ex.Message + "\r\n Query: " + this.sCommandText);
//            }
//            finally
//            {
//                if (pDataReader != null)
//                    pDataReader.Close();
//                this.InternalDatabaseConnection.Connection.Close();
//            }

//            return sRetVal;
//        }

//        protected override RetSit VerificaCivico()
//        {
//            RetSit pRetSit;

//            if (Init.Utils.StringChecker.IsNumeric(this.DataSit.Civico))
//            {
//                IDataReader pDataReader = null;

//                try
//                {
//                    var sElem = this.GetCpgSitCivici("NUMERO", pDataReader, TipoQuery.Validazione);
//                    if (!String.IsNullOrEmpty(sElem))
//                    {
//                        pRetSit = new RetSit(true);
//                        this.DataSit.Civico = sElem;
//                    }
//                    else
//                    {
//                        pRetSit = this.RestituisciErroreSit(this.GetMessageValidate("NUMERO"), MessageCode.CivicoValidazione, false);
//                    }
//                }
//                catch (CatastoException ex)
//                {
//                    pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.CivicoValidazione, false);
//                }
//                catch (Exception ex)
//                {
//                    throw new Exception("Errore durante la validazione di un civico. Metodo: VerificaCivico, modulo: SitEsc. " + ex.Message + "\r\n Query: " + this.sCommandText);
//                }
//                finally
//                {
//                    if (pDataReader != null)
//                        pDataReader.Close();
//                    this.InternalDatabaseConnection.Connection.Close();
//                }
//            }
//            else
//            {
//                pRetSit = this.RestituisciErroreSit("Non è possibile validare il civico " + this.DataSit.Civico + " perchè non è un numero", MessageCode.CivicoValidazioneNumero, false);
//            }

//            return pRetSit;
//        }

//        protected override string GetEsponente()
//        {
//            var sRetVal = "";
//            IDataReader pDataReader = null;

//            try
//            {
//                sRetVal = this.GetCpgSitCivici("ESP", pDataReader, TipoQuery.Elenco);
//            }
//            catch (CatastoException)
//            {
//            }
//            catch (Exception ex)
//            {
//                throw new Exception("Errore durante la restituzione di un esponente. Metodo: GetEsponente, modulo: SitEsc. " + ex.Message + "\r\n Query: " + this.sCommandText);
//            }
//            finally
//            {
//                if (pDataReader != null)
//                    pDataReader.Close();
//                this.InternalDatabaseConnection.Connection.Close();
//            }

//            return sRetVal;
//        }

//        protected override RetSit VerificaEsponente()
//        {
//            RetSit pRetSit;
//            IDataReader pDataReader = null;

//            try
//            {
//                var sElem = this.GetCpgSitCivici("ESP", pDataReader, TipoQuery.Validazione);
//                if (!String.IsNullOrEmpty(sElem))
//                {
//                    pRetSit = new RetSit(true);
//                    this.DataSit.Esponente = sElem;
//                }
//                else
//                {
//                    pRetSit = this.RestituisciErroreSit(this.GetMessageValidate("ESP"), MessageCode.EsponenteValidazione, false);
//                }
//            }
//            catch (CatastoException ex)
//            {
//                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.EsponenteValidazione, false);
//            }
//            catch (Exception ex)
//            {
//                throw new Exception("Errore durante la validazione di un esponente. Metodo: VerificaEsponente, modulo: SitEsc. " + ex.Message + "\r\n Query: " + this.sCommandText);
//            }
//            finally
//            {
//                if (pDataReader != null)
//                    pDataReader.Close();
//                this.InternalDatabaseConnection.Connection.Close();
//            }

//            return pRetSit;
//        }

//        protected override string GetCodCivico()
//        {
//            var sRetVal = "";
//            IDataReader pDataReader = null;

//            try
//            {
//                sRetVal = this.GetCpgSitCivici("UK_CIVICO", pDataReader, TipoQuery.Elenco);
//            }
//            catch (CatastoException)
//            {
//            }
//            catch (Exception ex)
//            {
//                throw new Exception("Errore durante la restituzione di un codice civico. Metodo: GetCodCivico, modulo: SitEsc. " + ex.Message + "\r\n Query: " + this.sCommandText);
//            }
//            finally
//            {
//                if (pDataReader != null)
//                    pDataReader.Close();
//                this.InternalDatabaseConnection.Connection.Close();
//            }

//            return sRetVal;
//        }



//        protected override string GetCodVia()
//        {
//            var sRetVal = "";
//            IDataReader pDataReader = null;

//            try
//            {
//                sRetVal = this.GetCpgSitCivici("FK_STRADE", pDataReader, TipoQuery.Elenco);

//                if (!string.IsNullOrEmpty(sRetVal))
//                {
//                    //To remove CodiceComune
//                    sRetVal = sRetVal.Remove(0, 4);

//                    var iIndex = sRetVal.IndexOfAny(new Char[] { '1', '2', '3', '4', '5', '6', '7', '8', '9' });
//                    if (iIndex != -1)
//                        sRetVal = sRetVal.Remove(0, iIndex);
//                }
//            }
//            catch (CatastoException)
//            {
//            }
//            catch (Exception ex)
//            {
//                throw new Exception("Errore durante la restituzione di un codice via. Metodo: GetCodVia, modulo: SitEsc. " + ex.Message + "\r\n Query: " + this.sCommandText);
//            }
//            finally
//            {
//                if (pDataReader != null)
//                    pDataReader.Close();
//                this.InternalDatabaseConnection.Connection.Close();
//            }

//            return sRetVal;
//        }



//        #endregion

//        public override string[] GetListaCampiGestiti()
//        {
//            return new string[]{
//                SitIntegrationService.NomiCampiSit.Esponente,
//                SitIntegrationService.NomiCampiSit.Frazione,
//                SitIntegrationService.NomiCampiSit.Circoscrizione,
//				//SitMgr.NomiCampiSit.Interno,
//				//SitMgr.NomiCampiSit.EsponenteInterno,
//				SitIntegrationService.NomiCampiSit.Fabbricato,
//				//SitMgr.NomiCampiSit.Sezione,
//				SitIntegrationService.NomiCampiSit.TipoCatasto,
//                SitIntegrationService.NomiCampiSit.Foglio,
//                SitIntegrationService.NomiCampiSit.Particella,
//                SitIntegrationService.NomiCampiSit.Sub,
//                SitIntegrationService.NomiCampiSit.UnitaImmobiliare,
//                SitIntegrationService.NomiCampiSit.Civico,
//                SitIntegrationService.NomiCampiSit.Cap,
//                SitIntegrationService.NomiCampiSit.CodiceVia,
//                SitIntegrationService.NomiCampiSit.CodiceCivico
//            };
//        }
//    }
//}
