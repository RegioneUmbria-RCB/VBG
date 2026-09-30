using PersonalLib2.Data;
using SIGePro.Manager.VerticalizzazioniBase;
using System;
using System.Data;
using VBG.Backend.SIT.AppLogic.Data;
using VBG.Backend.SIT.AppLogic.Exceptions;
using VBG.Backend.SIT.AppLogic.Manager;
using VBG.Backend.SIT.AppLogic.ValidazioneFormale;
using VBG.Backend.SIT.Verticalizzazioni;

namespace VBG.Backend.SIT.AppLogic
{
    /// <summary>
    /// Classe specifica per il Sit Core
    /// </summary>
    [SitImplementation("SIT_DEFAULT")]
    public class SitDefault : SitBase
    {
        public SitDefault()
            : base(new ValidazioneFormaleTramiteCodiceCivicoService())
        {
        }

        private string sConnectionString = "";
        private string sProvider = "";
        private string sCommandText = "";

        #region Utility
        public override void SetupVerticalizzazione(IVerticalizzazioniFactory verticalizzazioniFactory)
        {
            try
            {
                var pSitDefault = verticalizzazioniFactory.Create<VerticalizzazioneSitDefault>(this.IdComuneAlias, this.Software);

                if (pSitDefault.Attiva)
                {
                    this.sConnectionString = pSitDefault.Connectionstring;
                    this.sProvider = pSitDefault.Provider;

                    var provider = (ProviderType)Enum.Parse(typeof(ProviderType), this.sProvider, false);
                    this.InternalDatabaseConnection = new DataBase(this.sConnectionString, provider);
                }
                else
                    throw new Exception("La verticalizzazione SIT_DEFAULT non è attiva.\r\n");

            }
            catch (Exception ex)
            {
                throw new Exception("Errore generato durante la lettura della verticalizzazione SIT_DEFAULT. Metodo: GetParametriFromVertSITDEFAULT, modulo: SitDefault. " + ex.Message + "\r\n");
            }
        }

        private string GetMessageValidate(string sField)
        {
            var sValue = string.Empty;

            switch (sField)
            {
                case "G_SEZIONE":
                    sValue = "La sezione " + this.DataSit.Sezione + " non è valida per i dati inseriti";
                    break;
                case "G_FOGLIO":
                    sValue = "Il foglio " + this.DataSit.Foglio + " non è valido per i dati inseriti";
                    break;
                case "G_NUMERO":
                    sValue = "La particella " + this.DataSit.Particella + " non è valida per i dati inseriti";
                    break;
                case "CAP":
                    sValue = "Il CAP " + this.DataSit.CAP + " non è valido per i dati inseriti";
                    break;
                case "AE_CIVICO_CON_CIVKEY.NUMERO":
                    sValue = "Il civico " + this.DataSit.Civico + " non è valido per i dati inseriti";
                    break;
                case "AE_CIVICO_CON_CIVKEY.ESPONENTE":
                    sValue = "L'esponente " + this.DataSit.Esponente + " non è valido per i dati inseriti";
                    break;
                case "ID_EDIFICIO":
                    sValue = "Il fabbricato " + this.DataSit.Fabbricato + " non è valido per i dati inseriti";
                    break;
                case "SCALA":
                    sValue = "La scala " + this.DataSit.Scala + " non è valida per i dati inseriti";
                    break;
                case "AE_INTERNO_CON_INTKEY.NUMERO":
                    sValue = "L'interno " + this.DataSit.Interno + " non è valido per i dati inseriti";
                    break;
                case "AE_INTERNO_CON_INTKEY.ESPONENTE":
                    sValue = "L'esponente dell'interno " + this.DataSit.EsponenteInterno + " non è valido per i dati inseriti";
                    break;
            }

            return sValue;
        }

        private string GetMessageList(string sField)
        {
            var sValue = string.Empty;

            switch (sField)
            {
                case "G_FOGLIO":
                    sValue = "Non è possibile ottenere la lista dei fogli per insufficienza di dati: il catasto e la sezione devono essere forniti";
                    break;
                case "G_NUMERO":
                    sValue = "Non è possibile ottenere la lista delle particelle per insufficienza di dati: il catasto ed il fabbricato/sezione,foglio devono essere forniti";
                    break;
                case "G_SEZIONE":
                    sValue = "Non è possibile ottenere la lista delle sezioni per insufficienza di dati: il catasto deve essere fornito";
                    break;
                case "AE_CIVICO_CON_CIVKEY.NUMERO":
                    sValue = "Non è possibile ottenere la lista dei civici per insufficienza di dati: il fabbricato/via devono essere forniti";
                    break;
                case "AE_CIVICO_CON_CIVKEY.ESPONENTE":
                    sValue = "Non è possibile ottenere la lista degli esponenti per insufficienza di dati: il fabbricato/via ed il civico devono essere forniti";
                    break;
                case "ID_EDIFICIO":
                    sValue = "Non è possibile ottenere la lista dei fabbricati per insufficienza di dati: la via/dati catastali devono essere forniti";
                    break;
                case "SCALA":
                    sValue = "Non è possibile ottenere la lista delle scale per insufficienza di dati: il codice civico/fabbricato,civico,esponente deve essere fornito";
                    break;
                case "AE_INTERNO_CON_INTKEY.NUMERO":
                    sValue = "Non è possibile ottenere la lista degli interni per insufficienza di dati: il codice civico/fabbricato,civico,esponente devono essere forniti";
                    break;
                case "AE_INTERNO_CON_INTKEY.ESPONENTE":
                    sValue = "Non è possibile ottenere la lista degli esponenti degli interni per insufficienza di dati: il codice civico,interno/fabbricato,civico,esponente,interno devono essere forniti";
                    break;
            }

            return sValue;
        }

        private string GetAeCivicoConCivkey(string sField, System.Data.IDataReader pDataReader, TipoQuery eTipoQuery)
        {
            var sRetVal = string.Empty;

            if ((sField == "CAP") || (sField == "COD_VIA"))
            {
                if (string.IsNullOrEmpty(this.DataSit.Scala) && string.IsNullOrEmpty(this.DataSit.Interno) && string.IsNullOrEmpty(this.DataSit.EsponenteInterno))
                    sRetVal = this.GetElemento(sField, "AE_CIVICO_CON_CIVKEY", pDataReader, eTipoQuery);
                else
                    sRetVal = this.GetElemento(sField, "AE_CIVICO_CON_CIVKEY,AE_INTERNO_CON_INTKEY", pDataReader, eTipoQuery);
            }
            else
            {
                if (string.IsNullOrEmpty(this.DataSit.TipoCatasto))
                {
                    if (string.IsNullOrEmpty(this.DataSit.Scala) && string.IsNullOrEmpty(this.DataSit.Interno) && string.IsNullOrEmpty(this.DataSit.EsponenteInterno))
                        sRetVal = this.GetElemento(sField, "AE_CIVICO_CON_CIVKEY", pDataReader, eTipoQuery);
                    else
                        sRetVal = this.GetElemento(sField, "AE_CIVICO_CON_CIVKEY,AE_INTERNO_CON_INTKEY", pDataReader, eTipoQuery);
                }
                else
                {
                    if (this.DataSit.TipoCatasto == "F")
                    {
                        if (string.IsNullOrEmpty(this.DataSit.Scala) && string.IsNullOrEmpty(this.DataSit.Interno) && string.IsNullOrEmpty(this.DataSit.EsponenteInterno))
                            sRetVal = this.GetElemento(sField, "AE_CIVICO_CON_CIVKEY", pDataReader, eTipoQuery);
                        else
                            sRetVal = this.GetElemento(sField, "AE_CIVICO_CON_CIVKEY,AE_INTERNO_CON_INTKEY", pDataReader, eTipoQuery);
                    }
                    else
                    {
                        if ((sField == "G_SEZIONE") || (sField == "G_FOGLIO") || (sField == "G_NUMERO"))
                        {
                            if (this.IsCampiToponomasticaImmobileVuoti())
                                sRetVal = this.GetElemento(sField, "AE_CIVICO_CON_CIVKEY", pDataReader, eTipoQuery);
                            else
                                throw new CatastoException(this.GetMessageValidate("AE_CIVICO_CON_CIVKEY." + sField));
                        }
                        else
                            throw new CatastoException(this.GetMessageValidate("AE_CIVICO_CON_CIVKEY." + sField));
                    }
                }
            }

            return sRetVal;
        }

        private string GetAeInternoConIntkey(string sField, IDataReader pDataReader, TipoQuery eTipoQuery)
        {
            var sRetVal = string.Empty;

            if (string.IsNullOrEmpty(this.DataSit.TipoCatasto))
            {
                if (string.IsNullOrEmpty(this.DataSit.CodVia) && string.IsNullOrEmpty(this.DataSit.Civico) && string.IsNullOrEmpty(this.DataSit.Esponente) && string.IsNullOrEmpty(this.DataSit.CAP) && string.IsNullOrEmpty(this.DataSit.Fabbricato) && string.IsNullOrEmpty(this.DataSit.Sezione) && string.IsNullOrEmpty(this.DataSit.Foglio) && string.IsNullOrEmpty(this.DataSit.Particella))
                    sRetVal = this.GetElemento(sField, "AE_INTERNO_CON_INTKEY", pDataReader, eTipoQuery);
                else
                    sRetVal = this.GetElemento(sField, "AE_INTERNO_CON_INTKEY,AE_CIVICO_CON_CIVKEY", pDataReader, eTipoQuery);
            }
            else
            {
                if (this.DataSit.TipoCatasto == "F")
                {
                    if (string.IsNullOrEmpty(this.DataSit.CodVia) && string.IsNullOrEmpty(this.DataSit.Civico) && string.IsNullOrEmpty(this.DataSit.Esponente) && string.IsNullOrEmpty(this.DataSit.CAP) && string.IsNullOrEmpty(this.DataSit.Fabbricato) && string.IsNullOrEmpty(this.DataSit.Sezione) && string.IsNullOrEmpty(this.DataSit.Foglio) && string.IsNullOrEmpty(this.DataSit.Particella))
                        sRetVal = this.GetElemento(sField, "AE_INTERNO_CON_INTKEY", pDataReader, eTipoQuery);
                    else
                        sRetVal = this.GetElemento(sField, "AE_INTERNO_CON_INTKEY,AE_CIVICO_CON_CIVKEY", pDataReader, eTipoQuery);
                }
                else
                    throw new CatastoException(this.GetMessageValidate("AE_INTERNO_CON_INTKEY." + sField));
            }

            return sRetVal;
        }

        private RetSit ElencoAeCivicoConCivkey(string sField, IDataReader pDataReader)
        {
            RetSit pRetSit = null;


            //if (string.IsNullOrEmpty(DataSit.TipoCatasto))
            //    throw new CatastoException(GetMessageList("AE_CIVICO_CON_CIVKEY." + sField));
            //else
            //{
            if ((sField == "G_SEZIONE") || (sField == "G_FOGLIO") || (sField == "G_NUMERO"))
            {
                if (string.IsNullOrEmpty(this.DataSit.TipoCatasto))
                    throw new CatastoException(this.GetMessageList("AE_CIVICO_CON_CIVKEY." + sField));
                else
                {
                    if (this.DataSit.TipoCatasto == "F")
                    {
                        if (string.IsNullOrEmpty(this.DataSit.Scala) && string.IsNullOrEmpty(this.DataSit.Interno) && string.IsNullOrEmpty(this.DataSit.EsponenteInterno))
                            pRetSit = this.GetElenco(sField, "AE_CIVICO_CON_CIVKEY", pDataReader);
                        else
                            pRetSit = this.GetElenco(sField, "AE_CIVICO_CON_CIVKEY,AE_INTERNO_CON_INTKEY", pDataReader);
                    }
                    else
                    {
                        if ((sField == "G_SEZIONE") || (sField == "G_FOGLIO") || (sField == "G_NUMERO"))
                        {
                            if (this.IsCampiToponomasticaImmobileVuoti())
                                pRetSit = this.GetElenco(sField, "AE_CIVICO_CON_CIVKEY", pDataReader);
                            else
                                pRetSit = new RetSit(true);
                        }
                        else
                            pRetSit = new RetSit(true);
                    }
                }
            }
            else
            {
                if (string.IsNullOrEmpty(this.DataSit.TipoCatasto) || (this.DataSit.TipoCatasto == "F"))
                {
                    if (string.IsNullOrEmpty(this.DataSit.Scala) && string.IsNullOrEmpty(this.DataSit.Interno) && string.IsNullOrEmpty(this.DataSit.EsponenteInterno))
                        pRetSit = this.GetElenco(sField, "AE_CIVICO_CON_CIVKEY", pDataReader);
                    else
                        pRetSit = this.GetElenco(sField, "AE_CIVICO_CON_CIVKEY,AE_INTERNO_CON_INTKEY", pDataReader);
                }
                else
                {
                    pRetSit = new RetSit(true);
                }
            }
            //}

            return pRetSit;
        }

        private RetSit ElencoAeInternoConIntkey(string sField, IDataReader pDataReader)
        {
            RetSit pRetSit = null;

            //if (string.IsNullOrEmpty(DataSit.TipoCatasto))
            //    throw new CatastoException(GetMessageList("AE_INTERNO_CON_INTKEY." + sField));
            //else
            //{
            if (string.IsNullOrEmpty(this.DataSit.TipoCatasto) || (this.DataSit.TipoCatasto == "F"))
            {
                if (string.IsNullOrEmpty(this.DataSit.CodVia) && string.IsNullOrEmpty(this.DataSit.Civico) && string.IsNullOrEmpty(this.DataSit.Esponente) && string.IsNullOrEmpty(this.DataSit.CAP) && string.IsNullOrEmpty(this.DataSit.Fabbricato) && string.IsNullOrEmpty(this.DataSit.Sezione) && string.IsNullOrEmpty(this.DataSit.Foglio) && string.IsNullOrEmpty(this.DataSit.Particella))
                    pRetSit = this.GetElenco(sField, "AE_INTERNO_CON_INTKEY", pDataReader);
                else
                    pRetSit = this.GetElenco(sField, "AE_INTERNO_CON_INTKEY,AE_CIVICO_CON_CIVKEY", pDataReader);
            }
            else
                pRetSit = new RetSit(true);
            //}

            return pRetSit;
        }

        private string GetQuery(string sField, string sTableName, TipoQuery eTipoQuery)
        {
            //Codice per ottenere sempre un codice via valido
            if (!string.IsNullOrEmpty(this.DataSit.CodVia))
            {
                if ((this.DataSit.CodVia != "1") && (this.DataSit.CodVia != "2") && (this.DataSit.CodVia != "3"))
                {
                    //Random pRnd = new Random();
                    //this.DataSit.CodVia = pRnd.Next(1, 4).ToString();
                    throw new Exception("Il sit di default gestisce solamente i codici viario 1,2 e 3. Modificare il codice viario sullo stradario");
                }
            }

            var sQuery = string.Empty;

            switch (sTableName)
            {
                case "AE_CIVICO_CON_CIVKEY":
                    switch (eTipoQuery)
                    {
                        case TipoQuery.Elenco:
                            sQuery = "Select distinct " + sTableName + "." + sField + " from AE_CIVICO_CON_CIVKEY where " +
                                (((!string.IsNullOrEmpty(this.DataSit.CodVia)) && (sField != "COD_VIA")) ? "RTRIM(AE_CIVICO_CON_CIVKEY.COD_VIA)" + " = " + this.RightTrim(this.DataSit.CodVia) + " and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.Civico)) && (sField != "NUMERO")) ? "RTRIM(AE_CIVICO_CON_CIVKEY.NUMERO)" + " = " + this.RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Civico) ? this.DataSit.Civico : "-1") + " and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.Esponente)) && (sField != "ESPONENTE")) ? "RTRIM(AE_CIVICO_CON_CIVKEY.ESPONENTE)" + " = '" + this.RightTrim(this.DataSit.Esponente) + "' and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.CodCivico)) && (sField != "CIVKEY")) ? "RTRIM(AE_CIVICO_CON_CIVKEY.CIVKEY)" + " = '" + this.RightTrim(this.DataSit.CodCivico) + "' and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.Fabbricato)) && (sField != "ID_EDIFICIO")) ? "RTRIM(AE_CIVICO_CON_CIVKEY.ID_EDIFICIO)" + " = " + this.RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Fabbricato) ? this.DataSit.Fabbricato : "-1") + " and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.Sezione)) && (sField != "G_SEZIONE")) ? "RTRIM(AE_CIVICO_CON_CIVKEY.G_SEZIONE)" + " = '" + this.RightTrim(this.DataSit.Sezione) + "' and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.Foglio)) && (sField != "G_FOGLIO")) ? "RTRIM(AE_CIVICO_CON_CIVKEY.G_FOGLIO)" + " = '" + this.RightTrim(this.DataSit.Foglio) + "' and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.Particella)) && (sField != "G_NUMERO")) ? "RTRIM(AE_CIVICO_CON_CIVKEY.G_NUMERO)" + " = '" + this.RightTrim(this.DataSit.Particella) + "' and " : "") +
                                "RTRIM(AE_CIVICO_CON_CIVKEY.DATA_FINE)" + " is null order by " + sTableName + "." + sField + "";
                            break;
                        case TipoQuery.Validazione:
                            sQuery = "Select distinct " + sTableName + "." + sField + " from AE_CIVICO_CON_CIVKEY where " +
                                (((!string.IsNullOrEmpty(this.DataSit.CodVia))) ? "RTRIM(AE_CIVICO_CON_CIVKEY.COD_VIA)" + " = " + this.RightTrim(this.DataSit.CodVia) + " and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.Civico))) ? "RTRIM(AE_CIVICO_CON_CIVKEY.NUMERO)" + " = " + this.RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Civico) ? this.DataSit.Civico : "-1") + " and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.Esponente))) ? "RTRIM(AE_CIVICO_CON_CIVKEY.ESPONENTE)" + " = '" + this.RightTrim(this.DataSit.Esponente) + "' and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.CodCivico))) ? "RTRIM(AE_CIVICO_CON_CIVKEY.CIVKEY)" + " = '" + this.RightTrim(this.DataSit.CodCivico) + "' and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.Fabbricato))) ? "RTRIM(AE_CIVICO_CON_CIVKEY.ID_EDIFICIO)" + " = " + this.RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Fabbricato) ? this.DataSit.Fabbricato : "-1") + " and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.Sezione))) ? "RTRIM(AE_CIVICO_CON_CIVKEY.G_SEZIONE)" + " = '" + this.RightTrim(this.DataSit.Sezione) + "' and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.Foglio))) ? "RTRIM(AE_CIVICO_CON_CIVKEY.G_FOGLIO)" + " = '" + this.RightTrim(this.DataSit.Foglio) + "' and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.Particella))) ? "RTRIM(AE_CIVICO_CON_CIVKEY.G_NUMERO)" + " = '" + this.RightTrim(this.DataSit.Particella) + "' and " : "") +
                                "RTRIM(AE_CIVICO_CON_CIVKEY.DATA_FINE)" + " is null order by " + sTableName + "." + sField;
                            break;
                    }
                    break;
                case "AE_INTERNO_CON_INTKEY":
                    switch (eTipoQuery)
                    {
                        case TipoQuery.Elenco:
                            sQuery = "Select distinct " + sTableName + "." + sField + " from AE_INTERNO_CON_INTKEY where " +
                                (((!string.IsNullOrEmpty(this.DataSit.Scala)) && (sField != "SCALA")) ? "RTRIM(AE_INTERNO_CON_INTKEY.SCALA)" + " = '" + this.RightTrim(this.DataSit.Scala) + "' and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.Interno)) && (sField != "NUMERO")) ? "RTRIM(AE_INTERNO_CON_INTKEY.NUMERO)" + " = " + this.RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Interno) ? this.DataSit.Interno : "-1") + " and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.EsponenteInterno)) && (sField != "ESPONENTE")) ? "RTRIM(AE_INTERNO_CON_INTKEY.ESPONENTE)" + " = '" + this.RightTrim(this.DataSit.EsponenteInterno) + "' and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.CodCivico)) && (sField != "CIVKEY")) ? "RTRIM(AE_INTERNO_CON_INTKEY.CIVKEY)" + " = '" + this.RightTrim(this.DataSit.CodCivico) + "' " : "");
                            break;
                        case TipoQuery.Validazione:
                            sQuery = "Select distinct " + sTableName + "." + sField + " from AE_INTERNO_CON_INTKEY where " +
                                (((!string.IsNullOrEmpty(this.DataSit.Scala))) ? "RTRIM(AE_INTERNO_CON_INTKEY.SCALA)" + " = '" + this.RightTrim(this.DataSit.Scala) + "' and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.Interno))) ? "RTRIM(AE_INTERNO_CON_INTKEY.NUMERO)" + " = " + this.RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Interno) ? this.DataSit.Interno : "-1") + " and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.EsponenteInterno))) ? "RTRIM(AE_INTERNO_CON_INTKEY.ESPONENTE)" + " = '" + this.RightTrim(this.DataSit.EsponenteInterno) + "' and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.CodCivico))) ? "RTRIM(AE_INTERNO_CON_INTKEY.CIVKEY)" + " = '" + this.RightTrim(this.DataSit.CodCivico) + "' " : "");
                            break;
                    }

                    if (sQuery.EndsWith("and "))
                        sQuery = sQuery.Remove(sQuery.Length - 4, 4) + " order by " + sTableName + "." + sField;
                    else if (sQuery.EndsWith("where "))
                        sQuery = sQuery.Remove(sQuery.Length - 6, 6) + " order by " + sTableName + "." + sField;
                    else
                        sQuery = sQuery + " order by " + sTableName + "." + sField;

                    break;
                case "AE_INTERNO_CON_INTKEY,AE_CIVICO_CON_CIVKEY":
                case "AE_CIVICO_CON_CIVKEY,AE_INTERNO_CON_INTKEY":
                    switch (eTipoQuery)
                    {
                        case TipoQuery.Elenco:
                            sQuery = "Select distinct " + sTableName.Split(new Char[] { ',' })[0] + "." + sField + " from AE_CIVICO_CON_CIVKEY,AE_INTERNO_CON_INTKEY where " +
                                "AE_CIVICO_CON_CIVKEY.CIVKEY = AE_INTERNO_CON_INTKEY.CIVKEY and " +
                                (((!string.IsNullOrEmpty(this.DataSit.CodVia)) && (sField != "COD_VIA")) ? "RTRIM(AE_CIVICO_CON_CIVKEY.COD_VIA)" + " = " + this.RightTrim(this.DataSit.CodVia) + " and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.Civico)) && (sField != "NUMERO")) ? "RTRIM(AE_CIVICO_CON_CIVKEY.NUMERO)" + " = " + this.RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Civico) ? this.DataSit.Civico : "-1") + " and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.Esponente)) && (sField != "ESPONENTE")) ? "RTRIM(AE_CIVICO_CON_CIVKEY.ESPONENTE)" + " = '" + this.RightTrim(this.DataSit.Esponente) + "' and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.CodCivico)) && (sField != "CIVKEY")) ? "RTRIM(AE_CIVICO_CON_CIVKEY.CIVKEY)" + " = '" + this.RightTrim(this.DataSit.CodCivico) + "' and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.CAP)) && (sField != "CAP")) ? "RTRIM(AE_CIVICO_CON_CIVKEY.CAP)" + " = " + this.RightTrim(this.DataSit.CAP) + " and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.Fabbricato)) && (sField != "ID_EDIFICIO")) ? "RTRIM(AE_CIVICO_CON_CIVKEY.ID_EDIFICIO)" + " = " + this.RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Fabbricato) ? this.DataSit.Fabbricato : "-1") + " and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.Sezione)) && (sField != "G_SEZIONE")) ? "RTRIM(AE_CIVICO_CON_CIVKEY.G_SEZIONE)" + " = '" + this.RightTrim(this.DataSit.Sezione) + "' and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.Foglio)) && (sField != "G_FOGLIO")) ? "RTRIM(AE_CIVICO_CON_CIVKEY.G_FOGLIO)" + " = '" + this.RightTrim(this.DataSit.Foglio) + "' and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.Particella)) && (sField != "G_NUMERO")) ? "RTRIM(AE_CIVICO_CON_CIVKEY.G_NUMERO)" + " = '" + this.RightTrim(this.DataSit.Particella) + "' and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.Scala)) && (sField != "SCALA")) ? "RTRIM(AE_INTERNO_CON_INTKEY.SCALA)" + " = '" + this.RightTrim(this.DataSit.Scala) + "' and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.Interno)) && (sField != "NUMERO")) ? "RTRIM(AE_INTERNO_CON_INTKEY.NUMERO)" + " = " + this.RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Interno) ? this.DataSit.Interno : "-1") + " and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.EsponenteInterno)) && (sField != "ESPONENTE")) ? "RTRIM(AE_INTERNO_CON_INTKEY.ESPONENTE)" + " = '" + this.RightTrim(this.DataSit.EsponenteInterno) + "' and " : "") +
                                "RTRIM(AE_CIVICO_CON_CIVKEY.DATA_FINE)" + " is null order by " + sTableName.Split(new Char[] { ',' })[0] + "." + sField;
                            break;
                        case TipoQuery.Validazione:
                            sQuery = "Select distinct " + sTableName.Split(new Char[] { ',' })[0] + "." + sField + " from AE_CIVICO_CON_CIVKEY,AE_INTERNO_CON_INTKEY where " +
                                "AE_CIVICO_CON_CIVKEY.CIVKEY = AE_INTERNO_CON_INTKEY.CIVKEY and " +
                                (((!string.IsNullOrEmpty(this.DataSit.CodVia))) ? "RTRIM(AE_CIVICO_CON_CIVKEY.COD_VIA)" + " = " + this.RightTrim(this.DataSit.CodVia) + " and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.Civico))) ? "RTRIM(AE_CIVICO_CON_CIVKEY.NUMERO)" + " = " + this.RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Civico) ? this.DataSit.Civico : "-1") + " and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.Esponente))) ? "RTRIM(AE_CIVICO_CON_CIVKEY.ESPONENTE)" + " = '" + this.RightTrim(this.DataSit.Esponente) + "' and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.CodCivico))) ? "RTRIM(AE_CIVICO_CON_CIVKEY.CIVKEY)" + " = '" + this.RightTrim(this.DataSit.CodCivico) + "' and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.CAP))) ? "RTRIM(AE_CIVICO_CON_CIVKEY.CAP)" + " = '" + this.RightTrim(this.DataSit.CAP) + " and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.Fabbricato))) ? "RTRIM(AE_CIVICO_CON_CIVKEY.ID_EDIFICIO)" + " = " + this.RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Fabbricato) ? this.DataSit.Fabbricato : "-1") + " and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.Sezione))) ? "RTRIM(AE_CIVICO_CON_CIVKEY.G_SEZIONE)" + " = '" + this.RightTrim(this.DataSit.Sezione) + "' and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.Foglio))) ? "RTRIM(AE_CIVICO_CON_CIVKEY.G_FOGLIO)" + " = '" + this.RightTrim(this.DataSit.Foglio) + "' and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.Particella))) ? "RTRIM(AE_CIVICO_CON_CIVKEY.G_NUMERO)" + " = '" + this.RightTrim(this.DataSit.Particella) + "' and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.Scala))) ? "RTRIM(AE_INTERNO_CON_INTKEY.SCALA)" + " = '" + this.RightTrim(this.DataSit.Scala) + "' and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.Interno))) ? "RTRIM(AE_INTERNO_CON_INTKEY.NUMERO)" + " = " + this.RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Interno) ? this.DataSit.Interno : "-1") + " and " : "") +
                                (((!string.IsNullOrEmpty(this.DataSit.EsponenteInterno))) ? "RTRIM(AE_INTERNO_CON_INTKEY.ESPONENTE)" + " = '" + this.RightTrim(this.DataSit.EsponenteInterno) + "' and " : "") +
                                "RTRIM(AE_CIVICO_CON_CIVKEY.DATA_FINE)" + " is null order by " + sTableName.Split(new Char[] { ',' })[0] + "." + sField;
                            break;
                    }
                    break;
            }

            return sQuery;
        }

        private string GetElemento(string sField, string sTableName, IDataReader pDataReader, TipoQuery eTipoQuery)
        {
            var sRetVal = "";
            var iCount = 0;
            this.EnsureConnectionIsOpen();
            this.sCommandText = this.GetQuery(sField, sTableName, eTipoQuery);

            var pCommand = this.InternalDatabaseConnection.CreateCommand(this.sCommandText);
            pDataReader = pCommand.ExecuteReader();

            while (pDataReader.Read())
            {
                var sDato = this.RightTrim(pDataReader[sField].ToString());

                iCount++;
                if (iCount == 1)
                    sRetVal = sDato;
                else
                {
                    sRetVal = "";
                    break;
                }
            }
            pCommand.Dispose();

            return sRetVal;
        }

        private RetSit GetElenco(string sField, string sTableName, IDataReader pDataReader)
        {
            var pRetSit = new RetSit(true);
            this.EnsureConnectionIsOpen();
            this.sCommandText = this.GetQuery(sField, sTableName, TipoQuery.Elenco);

            var pCommand = this.InternalDatabaseConnection.CreateCommand(this.sCommandText);
            pDataReader = pCommand.ExecuteReader();

            while (pDataReader.Read())
            {
                var sDato = this.RightTrim(pDataReader[sField].ToString());

                if (!string.IsNullOrEmpty(sDato) && !pRetSit.DataCollection.Contains(sDato))
                {
                    pRetSit.DataCollection.Add(sDato);
                }
            }
            pCommand.Dispose();

            return pRetSit;
        }

        #endregion



        #region Metodi per ottenere elenchi di elementi catastali o facenti parte dell'indirizzo

        public override RetSit ElencoCivici()
        {
            IDataReader pDataReader = null;
            RetSit pRetSit;

            try
            {
                if ((!String.IsNullOrEmpty(this.DataSit.Fabbricato)) || (!String.IsNullOrEmpty(this.DataSit.CodVia)))
                {
                    pRetSit = this.ElencoAeCivicoConCivkey("NUMERO", pDataReader);
                }
                else
                {
                    pRetSit = this.RestituisciErroreSit(this.GetMessageList("AE_CIVICO_CON_CIVKEY.NUMERO"), MessageCode.ElencoCivici, false);
                }
            }
            catch (CatastoException ex)
            {
                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.ElencoCivici, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione dell'elenco dei civici. Metodo: ElencoCivici, modulo: SitDefault. " + ex.Message + "\r\n Query: " + this.sCommandText);
            }
            finally
            {
                if (pDataReader != null)
                    pDataReader.Close();
                this.InternalDatabaseConnection.Connection.Close();
            }

            return pRetSit;
        }

        public override RetSit ElencoEsponenti()
        {
            System.Data.IDataReader pDataReader = null;
            RetSit pRetSit;

            try
            {
                if (!String.IsNullOrEmpty(this.DataSit.Civico) && ((!String.IsNullOrEmpty(this.DataSit.Fabbricato)) || (!String.IsNullOrEmpty(this.DataSit.CodVia))))
                {
                    pRetSit = this.ElencoAeCivicoConCivkey("ESPONENTE", pDataReader);
                }
                else
                {
                    pRetSit = this.RestituisciErroreSit(this.GetMessageList("AE_CIVICO_CON_CIVKEY.ESPONENTE"), MessageCode.ElencoEsponenti, false);
                }
            }
            catch (CatastoException ex)
            {
                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.ElencoEsponenti, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione dell'elenco degli esponenti. Metodo: ElencoEsponenti, modulo: SitDefault. " + ex.Message + "\r\n Query: " + this.sCommandText);
            }
            finally
            {
                if (pDataReader != null)
                    pDataReader.Close();
                this.InternalDatabaseConnection.Connection.Close();
            }

            return pRetSit;
        }

        public override RetSit ElencoColori()
        {
            var pRetSit = this.RestituisciErroreSit(MessageCode.ElencoColori, true);
            return pRetSit;
        }

        public override RetSit ElencoScale()
        {
            IDataReader pDataReader = null;
            RetSit pRetSit;

            try
            {
                if (!String.IsNullOrEmpty(this.DataSit.CodCivico) || (!String.IsNullOrEmpty(this.DataSit.Fabbricato) && !String.IsNullOrEmpty(this.DataSit.Civico) && !String.IsNullOrEmpty(this.DataSit.Esponente)))
                {
                    pRetSit = this.ElencoAeInternoConIntkey("SCALA", pDataReader);
                }
                else
                {
                    pRetSit = this.RestituisciErroreSit(this.GetMessageList("SCALA"), MessageCode.ElencoScale, false);
                }
            }
            catch (CatastoException ex)
            {
                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.ElencoScale, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione dell'elenco delle scale. Metodo: ElencoScale, modulo: SitDefault. " + ex.Message + "\r\n Query: " + this.sCommandText);
            }
            finally
            {
                if (pDataReader != null)
                    pDataReader.Close();
                this.InternalDatabaseConnection.Connection.Close();
            }

            return pRetSit;
        }

        public override RetSit ElencoInterni()
        {
            IDataReader pDataReader = null;
            RetSit pRetSit;

            try
            {
                if (!String.IsNullOrEmpty(this.DataSit.CodCivico) || (!String.IsNullOrEmpty(this.DataSit.Fabbricato) && !String.IsNullOrEmpty(this.DataSit.Civico) && !String.IsNullOrEmpty(this.DataSit.Esponente)))
                {
                    pRetSit = this.ElencoAeInternoConIntkey("NUMERO", pDataReader);
                }
                else
                {
                    pRetSit = this.RestituisciErroreSit(this.GetMessageList("AE_INTERNO_CON_INTKEY.NUMERO"), MessageCode.ElencoInterni, false);
                }
            }
            catch (CatastoException ex)
            {
                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.ElencoInterni, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione dell'elenco degli interni. Metodo: ElencoInterni, modulo: SitDefault. " + ex.Message + "\r\n Query: " + this.sCommandText);
            }
            finally
            {
                if (pDataReader != null)
                    pDataReader.Close();
                this.InternalDatabaseConnection.Connection.Close();
            }

            return pRetSit;
        }

        public override RetSit ElencoEsponentiInterno()
        {
            IDataReader pDataReader = null;
            RetSit pRetSit;

            try
            {
                if ((!String.IsNullOrEmpty(this.DataSit.CodCivico) && !String.IsNullOrEmpty(this.DataSit.Interno)) || (!String.IsNullOrEmpty(this.DataSit.Fabbricato) && !String.IsNullOrEmpty(this.DataSit.Civico) && !String.IsNullOrEmpty(this.DataSit.Esponente) && !String.IsNullOrEmpty(this.DataSit.Interno)))
                {
                    pRetSit = this.ElencoAeInternoConIntkey("ESPONENTE", pDataReader);
                }
                else
                {
                    pRetSit = this.RestituisciErroreSit(this.GetMessageList("AE_INTERNO_CON_INTKEY.NUMERO"), MessageCode.ElencoEsponentiInterno, false);
                }
            }
            catch (CatastoException ex)
            {
                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.ElencoEsponentiInterno, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione dell'elenco degli interni dell'esponente. Metodo: ElencoEsponentiInterno, modulo: SitDefault. " + ex.Message + "\r\n Query: " + this.sCommandText);
            }
            finally
            {
                if (pDataReader != null)
                    pDataReader.Close();
                this.InternalDatabaseConnection.Connection.Close();
            }

            return pRetSit;
        }

        public override RetSit ElencoFabbricati()
        {
            IDataReader pDataReader = null;
            RetSit pRetSit;

            try
            {
                if ((!String.IsNullOrEmpty(this.DataSit.CodVia)) || (!String.IsNullOrEmpty(this.DataSit.Sezione) && !String.IsNullOrEmpty(this.DataSit.Foglio) && !String.IsNullOrEmpty(this.DataSit.Particella)))
                {
                    pRetSit = this.ElencoAeCivicoConCivkey("ID_EDIFICIO", pDataReader);
                }
                else
                {
                    pRetSit = this.RestituisciErroreSit(this.GetMessageList("ID_EDIFICIO"), MessageCode.ElencoFabbricati, false);
                }
            }
            catch (CatastoException ex)
            {
                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.ElencoFabbricati, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione dell'elenco dei fabbricati. Metodo: ElencoFabbricati, modulo: SitDefault. " + ex.Message + "\r\n Query: " + this.sCommandText);
            }
            finally
            {
                if (pDataReader != null)
                    pDataReader.Close();
                this.InternalDatabaseConnection.Connection.Close();
            }

            return pRetSit;
        }

        public override RetSit ElencoSezioni()
        {
            IDataReader pDataReader = null;
            RetSit pRetSit;

            try
            {
                pRetSit = this.ElencoAeCivicoConCivkey("G_SEZIONE", pDataReader);

            }
            catch (CatastoException ex)
            {
                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.ElencoSezioni, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione dell'elenco delle sezioni. Metodo: ElencoSezioni, modulo: SitDefault. " + ex.Message + "\r\n Query: " + this.sCommandText);
            }
            finally
            {
                if (pDataReader != null)
                    pDataReader.Close();
                this.InternalDatabaseConnection.Connection.Close();
            }

            return pRetSit;
        }

        public override RetSit ElencoFogli()
        {
            IDataReader pDataReader = null;
            RetSit pRetSit;

            try
            {
                if ((!String.IsNullOrEmpty(this.DataSit.Fabbricato)) || (!String.IsNullOrEmpty(this.DataSit.Sezione)))
                {
                    pRetSit = this.ElencoAeCivicoConCivkey("G_FOGLIO", pDataReader);
                }
                else
                {
                    pRetSit = this.RestituisciErroreSit(this.GetMessageList("G_FOGLIO"), MessageCode.ElencoFogli, false);
                }
            }
            catch (CatastoException ex)
            {
                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.ElencoFogli, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione dell'elenco dei fogli. Metodo: ElencoFogli, modulo: SitDefault. " + ex.Message + "\r\n Query: " + this.sCommandText);
            }
            finally
            {
                if (pDataReader != null)
                    pDataReader.Close();
                this.InternalDatabaseConnection.Connection.Close();
            }

            return pRetSit;
        }

        public override RetSit ElencoParticelle()
        {
            IDataReader pDataReader = null;
            RetSit pRetSit;

            try
            {
                if ((!String.IsNullOrEmpty(this.DataSit.Fabbricato)) || (!String.IsNullOrEmpty(this.DataSit.Sezione) && !String.IsNullOrEmpty(this.DataSit.Foglio)))
                {
                    pRetSit = this.ElencoAeCivicoConCivkey("G_NUMERO", pDataReader);
                }
                else
                {
                    pRetSit = this.RestituisciErroreSit(this.GetMessageList("G_NUMERO"), MessageCode.ElencoParticelle, false);

                }
            }
            catch (CatastoException ex)
            {
                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.ElencoParticelle, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione dell'elenco delle particelle. Metodo: ElencoParticelle, modulo: SitDefault. " + ex.Message + "\r\n Query: " + this.sCommandText);
            }
            finally
            {
                if (pDataReader != null)
                    pDataReader.Close();
                this.InternalDatabaseConnection.Connection.Close();
            }

            return pRetSit;
        }


        #endregion

        #region Metodi per la verifica e la restituzione di un singolo elemento catastale o facente parte dell'indirizzo

        protected override string GetEsponente()
        {
            var sRetVal = "";
            IDataReader pDataReader = null;

            try
            {
                sRetVal = this.GetAeCivicoConCivkey("ESPONENTE", pDataReader, TipoQuery.Elenco);
            }
            catch (CatastoException)
            {
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di un esponente. Metodo: GetEsponente, modulo: SitDefault. " + ex.Message + "\r\n Query: " + this.sCommandText);
            }
            finally
            {
                if (pDataReader != null)
                    pDataReader.Close();
                this.InternalDatabaseConnection.Connection.Close();
            }

            return sRetVal;
        }

        protected override RetSit VerificaEsponente()
        {
            RetSit pRetSit;
            IDataReader pDataReader = null;

            try
            {
                var sElem = this.GetAeCivicoConCivkey("ESPONENTE", pDataReader, TipoQuery.Validazione);
                if (!String.IsNullOrEmpty(sElem))
                {
                    pRetSit = new RetSit(true);
                    this.DataSit.Esponente = sElem;
                }
                else
                {
                    pRetSit = this.RestituisciErroreSit(this.GetMessageValidate("AE_CIVICO_CON_CIVKEY.ESPONENTE"), MessageCode.EsponenteValidazione, false);
                }
            }
            catch (CatastoException ex)
            {
                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.EsponenteValidazione, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione di un esponente. Metodo: VerificaEsponente, modulo: SitDefault. " + ex.Message + "\r\n Query: " + this.sCommandText);
            }
            finally
            {
                if (pDataReader != null)
                    pDataReader.Close();
                this.InternalDatabaseConnection.Connection.Close();
            }

            return pRetSit;
        }

        protected override string GetScala()
        {
            var sRetVal = "";
            IDataReader pDataReader = null;

            try
            {
                sRetVal = this.GetAeInternoConIntkey("SCALA", pDataReader, TipoQuery.Elenco);
            }
            catch (CatastoException)
            {
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di una scala. Metodo: GetScala, modulo: SitDefault. " + ex.Message + "\r\n Query: " + this.sCommandText);
            }
            finally
            {
                if (pDataReader != null)
                    pDataReader.Close();
                this.InternalDatabaseConnection.Connection.Close();
            }

            return sRetVal;
        }

        protected override RetSit VerificaScala()
        {
            RetSit pRetSit;
            IDataReader pDataReader = null;

            try
            {
                var sElem = this.GetAeInternoConIntkey("SCALA", pDataReader, TipoQuery.Validazione);
                if (!String.IsNullOrEmpty(sElem))
                {
                    pRetSit = new RetSit(true);
                    this.DataSit.Scala = sElem;
                }
                else
                {
                    pRetSit = this.RestituisciErroreSit(this.GetMessageValidate("SCALA"), MessageCode.ScalaValidazione, false);
                }
            }
            catch (CatastoException ex)
            {
                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.ScalaValidazione, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione di una scala. Metodo: VerificaScala, modulo: SitDefault. " + ex.Message + "\r\n Query: " + this.sCommandText);
            }
            finally
            {
                if (pDataReader != null)
                    pDataReader.Close();
                this.InternalDatabaseConnection.Connection.Close();
            }

            return pRetSit;
        }

        protected override string GetInterno()
        {
            var sRetVal = "";
            IDataReader pDataReader = null;

            try
            {
                sRetVal = this.GetAeInternoConIntkey("NUMERO", pDataReader, TipoQuery.Elenco);
            }
            catch (CatastoException)
            {
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di un interno. Metodo: GetInterno, modulo: SitDefault. " + ex.Message + "\r\n Query: " + this.sCommandText);
            }
            finally
            {
                if (pDataReader != null)
                    pDataReader.Close();
                this.InternalDatabaseConnection.Connection.Close();
            }

            return sRetVal;
        }

        protected override RetSit VerificaInterno()
        {
            RetSit pRetSit;

            if (Init.Utils.StringChecker.IsNumeric(this.DataSit.Interno))
            {
                IDataReader pDataReader = null;

                try
                {
                    var sElem = this.GetAeInternoConIntkey("NUMERO", pDataReader, TipoQuery.Validazione);
                    if (!String.IsNullOrEmpty(sElem))
                    {
                        pRetSit = new RetSit(true);
                        this.DataSit.Interno = sElem;
                    }
                    else
                    {
                        pRetSit = this.RestituisciErroreSit(this.GetMessageValidate("AE_INTERNO_CON_INTKEY.NUMERO"), MessageCode.InternoValidazione, false);
                    }
                }
                catch (CatastoException ex)
                {
                    pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.InternoValidazione, false);
                }
                catch (Exception ex)
                {
                    throw new Exception("Errore durante la validazione di un interno. Metodo: VerificaInterno, modulo: SitDefault. " + ex.Message + "\r\n Query: " + this.sCommandText);
                }
                finally
                {
                    if (pDataReader != null)
                        pDataReader.Close();
                    this.InternalDatabaseConnection.Connection.Close();
                }
            }
            else
            {
                pRetSit = this.RestituisciErroreSit("Non è possibile validare l'interno " + this.DataSit.Interno + " perchè non è un numero", MessageCode.InternoValidazioneNumero, false);
            }

            return pRetSit;
        }

        protected override string GetEsponenteInterno()
        {
            var sRetVal = "";
            IDataReader pDataReader = null;

            try
            {
                sRetVal = this.GetAeInternoConIntkey("ESPONENTE", pDataReader, TipoQuery.Elenco);
            }
            catch (CatastoException)
            {
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di un esponente interno. Metodo: GetEsponenteInterno, modulo: SitDefault. " + ex.Message + "\r\n Query: " + this.sCommandText);
            }
            finally
            {
                if (pDataReader != null)
                    pDataReader.Close();
                this.InternalDatabaseConnection.Connection.Close();
            }

            return sRetVal;
        }

        protected override RetSit VerificaEsponenteInterno()
        {
            RetSit pRetSit;

            IDataReader pDataReader = null;

            try
            {
                var sElem = this.GetAeInternoConIntkey("ESPONENTE", pDataReader, TipoQuery.Validazione);
                if (!String.IsNullOrEmpty(sElem))
                {
                    pRetSit = new RetSit(true);
                    this.DataSit.EsponenteInterno = sElem;
                }
                else
                {
                    pRetSit = this.RestituisciErroreSit(this.GetMessageValidate("AE_INTERNO_CON_INTKEY.ESPONENTE"), MessageCode.EsponenteInternoValidazione, false);
                }
            }
            catch (CatastoException ex)
            {
                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.EsponenteInternoValidazione, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione di un esponente interno. Metodo: VerificaEsponenteInterno, modulo: SitDefault. " + ex.Message + "\r\n Query: " + this.sCommandText);
            }
            finally
            {
                if (pDataReader != null)
                    pDataReader.Close();
                this.InternalDatabaseConnection.Connection.Close();
            }

            return pRetSit;
        }

        protected override RetSit VerificaFabbricato()
        {
            RetSit pRetSit;

            if (Init.Utils.StringChecker.IsNumeric(this.DataSit.Fabbricato))
            {
                IDataReader pDataReader = null;

                try
                {
                    var sElem = this.GetAeCivicoConCivkey("ID_EDIFICIO", pDataReader, TipoQuery.Validazione);
                    if (!String.IsNullOrEmpty(sElem))
                    {
                        pRetSit = new RetSit(true);
                        this.DataSit.Fabbricato = sElem;
                    }
                    else
                    {
                        pRetSit = this.RestituisciErroreSit(this.GetMessageValidate("ID_EDIFICIO"), MessageCode.FabbricatoValidazione, false);
                    }
                }
                catch (CatastoException ex)
                {
                    pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.FabbricatoValidazione, false);
                }
                catch (Exception ex)
                {
                    throw new Exception("Errore durante la validazione di un fabbricato. Metodo: VerificaFabbricato, modulo: SitDefault. " + ex.Message + "\r\n Query: " + this.sCommandText);
                }
                finally
                {
                    if (pDataReader != null)
                        pDataReader.Close();
                    this.InternalDatabaseConnection.Connection.Close();
                }
            }
            else
            {
                pRetSit = this.RestituisciErroreSit("Non è possibile validare il fabbricato " + this.DataSit.Fabbricato + " perchè non è un numero", MessageCode.FabbricatoValidazioneNumero, true);
            }

            return pRetSit;
        }

        protected override string GetSezione()
        {
            var sRetVal = "";
            IDataReader pDataReader = null;

            try
            {
                sRetVal = this.GetAeCivicoConCivkey("G_SEZIONE", pDataReader, TipoQuery.Elenco);
            }
            catch (CatastoException)
            {
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di una sezione. Metodo: GetSezione, modulo: SitDefault. " + ex.Message + "\r\n Query: " + this.sCommandText);
            }
            finally
            {
                if (pDataReader != null)
                    pDataReader.Close();
                this.InternalDatabaseConnection.Connection.Close();
            }

            return sRetVal;

        }

        protected override RetSit VerificaTipoCatasto()
        {
            RetSit pRetSit = null;

            if (this.IsCampiToponomasticaImmobileVuoti())
                pRetSit = new RetSit(true);
            else
            {
                if (this.DataSit.TipoCatasto == "T")
                    pRetSit = this.RestituisciErroreSit("Il tipocatasto " + this.DataSit.TipoCatasto + " non è valido per i dati inseriti", MessageCode.TipoCatastoValidazione, false);
                else
                    pRetSit = new RetSit(true);
            }

            return pRetSit;
        }

        protected override RetSit VerificaSezione()
        {
            RetSit pRetSit;

            IDataReader pDataReader = null;

            try
            {
                var sElem = this.GetAeCivicoConCivkey("G_SEZIONE", pDataReader, TipoQuery.Validazione);
                if (!String.IsNullOrEmpty(sElem))
                {
                    pRetSit = new RetSit(true);
                    this.DataSit.Sezione = sElem;
                }
                else
                {
                    pRetSit = this.RestituisciErroreSit(this.GetMessageValidate("G_SEZIONE"), MessageCode.SezioneValidazione, false);
                }
            }
            catch (CatastoException ex)
            {
                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.SezioneValidazione, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione di una sezione. Metodo: VerificaSezione, modulo: SitDefault. " + ex.Message + "\r\n Query: " + this.sCommandText);
            }
            finally
            {
                if (pDataReader != null)
                    pDataReader.Close();
                this.InternalDatabaseConnection.Connection.Close();
            }

            return pRetSit;
        }

        protected override string GetFoglio()
        {
            var sRetVal = "";
            IDataReader pDataReader = null;

            try
            {
                sRetVal = this.GetAeCivicoConCivkey("G_FOGLIO", pDataReader, TipoQuery.Elenco);
            }
            catch (CatastoException)
            {
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di un foglio. Metodo: GetFoglio, modulo: SitDefault. " + ex.Message + "\r\n Query: " + this.sCommandText);
            }
            finally
            {
                if (pDataReader != null)
                    pDataReader.Close();
                this.InternalDatabaseConnection.Connection.Close();
            }

            return sRetVal;
        }

        protected override RetSit VerificaFoglio()
        {
            RetSit pRetSit;

            IDataReader pDataReader = null;

            try
            {
                var sElem = this.GetAeCivicoConCivkey("G_FOGLIO", pDataReader, TipoQuery.Validazione);
                if (!String.IsNullOrEmpty(sElem))
                {
                    pRetSit = new RetSit(true);
                    this.DataSit.Foglio = sElem;
                }
                else
                {
                    pRetSit = this.RestituisciErroreSit(this.GetMessageValidate("G_FOGLIO"), MessageCode.FoglioValidazione, false);
                }
            }
            catch (CatastoException ex)
            {
                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.FoglioValidazione, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione di un foglio. Metodo: VerificaFoglio, modulo: SitDefault. " + ex.Message + "\r\n Query: " + this.sCommandText);
            }
            finally
            {
                if (pDataReader != null)
                    pDataReader.Close();
                this.InternalDatabaseConnection.Connection.Close();
            }
            return pRetSit;
        }

        protected override string GetParticella()
        {
            var sRetVal = "";
            IDataReader pDataReader = null;

            try
            {
                sRetVal = this.GetAeCivicoConCivkey("G_NUMERO", pDataReader, TipoQuery.Elenco);
            }
            catch (CatastoException)
            {
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di una particella. Metodo: GetParticella, modulo: SitDefault. " + ex.Message + "\r\n Query: " + this.sCommandText);
            }
            finally
            {
                if (pDataReader != null)
                    pDataReader.Close();
                this.InternalDatabaseConnection.Connection.Close();
            }

            return sRetVal;

        }

        protected override RetSit VerificaParticella()
        {
            RetSit pRetSit;

            IDataReader pDataReader = null;

            try
            {
                var sElem = this.GetAeCivicoConCivkey("G_NUMERO", pDataReader, TipoQuery.Validazione);
                if (!String.IsNullOrEmpty(sElem))
                {
                    pRetSit = new RetSit(true);
                    this.DataSit.Particella = sElem;
                }
                else
                {
                    pRetSit = this.RestituisciErroreSit(this.GetMessageValidate("G_NUMERO"), MessageCode.ParticellaValidazione, false);
                }
            }
            catch (CatastoException ex)
            {
                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.ParticellaValidazione, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione di una particella. Metodo: VerificaParticella, modulo: SitDefault. " + ex.Message + "\r\n Query: " + this.sCommandText);
            }
            finally
            {
                if (pDataReader != null)
                    pDataReader.Close();
                this.InternalDatabaseConnection.Connection.Close();
            }

            return pRetSit;
        }

        protected override string GetCivico()
        {
            var sRetVal = "";
            IDataReader pDataReader = null;

            try
            {
                sRetVal = this.GetAeCivicoConCivkey("NUMERO", pDataReader, TipoQuery.Elenco);
            }
            catch (CatastoException)
            {
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di un civico. Metodo: GetCivico, modulo: SitDefault. " + ex.Message + "\r\n Query: " + this.sCommandText);
            }
            finally
            {
                if (pDataReader != null)
                    pDataReader.Close();
                this.InternalDatabaseConnection.Connection.Close();
            }

            return sRetVal;
        }

        protected override RetSit VerificaCivico()
        {
            RetSit pRetSit;

            if (Init.Utils.StringChecker.IsNumeric(this.DataSit.Civico))
            {
                IDataReader pDataReader = null;

                try
                {
                    var sElem = this.GetAeCivicoConCivkey("NUMERO", pDataReader, TipoQuery.Validazione);
                    if (!String.IsNullOrEmpty(sElem))
                    {
                        pRetSit = new RetSit(true);
                        this.DataSit.Civico = sElem;
                    }
                    else
                    {
                        pRetSit = this.RestituisciErroreSit(this.GetMessageValidate("AE_CIVICO_CON_CIVKEY.NUMERO"), MessageCode.CivicoValidazione, false);
                    }
                }
                catch (CatastoException ex)
                {
                    pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.CivicoValidazione, false);
                }
                catch (Exception ex)
                {
                    throw new Exception("Errore durante la validazione di un civico. Metodo: VerificaCivico, modulo: SitDefault. " + ex.Message + "\r\n Query: " + this.sCommandText);
                }
                finally
                {
                    if (pDataReader != null)
                        pDataReader.Close();
                    this.InternalDatabaseConnection.Connection.Close();
                }
            }
            else
            {
                pRetSit = this.RestituisciErroreSit("Non è possibile validare il civico " + this.DataSit.Civico + " perchè non è un numero", MessageCode.CivicoValidazioneNumero, false);
            }

            return pRetSit;
        }

        protected override string GetCAP()
        {
            var sRetVal = "";
            IDataReader pDataReader = null;

            try
            {
                sRetVal = this.GetAeCivicoConCivkey("CAP", pDataReader, TipoQuery.Elenco);
            }
            catch (CatastoException)
            {
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di un CAP. Metodo: GetCAP, modulo: SitDefault. " + ex.Message + "\r\n Query: " + this.sCommandText);
            }
            finally
            {
                if (pDataReader != null)
                    pDataReader.Close();
                this.InternalDatabaseConnection.Connection.Close();
            }

            return sRetVal;
        }

        protected override RetSit VerificaCAP()
        {
            RetSit pRetSit;

            IDataReader pDataReader = null;

            try
            {
                var sElem = this.GetAeCivicoConCivkey("CAP", pDataReader, TipoQuery.Validazione);
                if (!String.IsNullOrEmpty(sElem))
                {
                    pRetSit = new RetSit(true);
                    this.DataSit.CAP = sElem;
                }
                else
                {
                    pRetSit = this.RestituisciErroreSit(this.GetMessageValidate("CAP"), MessageCode.CAPValidazione, false);
                }
            }
            catch (CatastoException ex)
            {
                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.CAPValidazione, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione di un CAP. Metodo: VerificaCAP, modulo: SitDefault. " + ex.Message + "\r\n Query: " + this.sCommandText);
            }
            finally
            {
                if (pDataReader != null)
                    pDataReader.Close();
                this.InternalDatabaseConnection.Connection.Close();
            }

            return pRetSit;
        }

        protected override string GetCodCivico()
        {
            var sRetVal = "";
            IDataReader pDataReader = null;

            try
            {
                sRetVal = this.GetAeCivicoConCivkey("CIVKEY", pDataReader, TipoQuery.Elenco);

                //Gestione particolare quando esistono più indirizzi con lo stesso civico ed esponente settato tranne per uno (esempio 1 e 1/A)
                //if (String.IsNullOrEmpty(this.DataSit.Esponente) && !sRetVal.EndsWith("000") && !String.IsNullOrEmpty(sRetVal))
                //{
                //    sRetVal = sRetVal.Remove(sRetVal.Length - 3) + "000";
                //}
            }
            catch (CatastoException)
            {
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di un codice civico. Metodo: GetCodCivico, modulo: SitDefault. " + ex.Message + "\r\n Query: " + this.sCommandText);
            }
            finally
            {
                if (pDataReader != null)
                    pDataReader.Close();
                this.InternalDatabaseConnection.Connection.Close();
            }

            return sRetVal;
        }

        protected override string GetCodFabbricato()
        {
            var sRetVal = "";
            IDataReader pDataReader = null;

            try
            {
                sRetVal = this.GetAeCivicoConCivkey("ID_EDIFICIO", pDataReader, TipoQuery.Elenco);
            }
            catch (CatastoException)
            {
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di un codice fabbricato. Metodo: GetCodFabbricato, modulo: SitDefault. " + ex.Message + "\r\n Query: " + this.sCommandText);
            }
            finally
            {
                if (pDataReader != null)
                    pDataReader.Close();
                this.InternalDatabaseConnection.Connection.Close();
            }

            return sRetVal;
        }

        protected override string GetCodVia()
        {
            var sRetVal = "";
            IDataReader pDataReader = null;

            try
            {
                sRetVal = this.GetAeCivicoConCivkey("COD_VIA", pDataReader, TipoQuery.Elenco);
            }
            catch (CatastoException)
            {
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di un codice via. Metodo: GetCodVia, modulo: SitDefault. " + ex.Message + "\r\n Query: " + this.sCommandText);
            }
            finally
            {
                if (pDataReader != null)
                    pDataReader.Close();
                this.InternalDatabaseConnection.Connection.Close();
            }

            return sRetVal;
        }

        #endregion

        public override string[] GetListaCampiGestiti()
        {
            return new string[]{
                SitIntegrationService.NomiCampiSit.Esponente,
                SitIntegrationService.NomiCampiSit.Scala,
				//SitMgr.NomiCampiSit.Frazione,
				//SitMgr.NomiCampiSit.Circoscrizione,
				SitIntegrationService.NomiCampiSit.Interno,
                SitIntegrationService.NomiCampiSit.EsponenteInterno,
                SitIntegrationService.NomiCampiSit.Fabbricato,
                SitIntegrationService.NomiCampiSit.Sezione,
                SitIntegrationService.NomiCampiSit.TipoCatasto,
                SitIntegrationService.NomiCampiSit.Foglio,
                SitIntegrationService.NomiCampiSit.Particella,
				//SitMgr.NomiCampiSit.Sub,
				//SitMgr.NomiCampiSit.UnitaImmobiliare,
				SitIntegrationService.NomiCampiSit.Civico,
                SitIntegrationService.NomiCampiSit.Cap,
                SitIntegrationService.NomiCampiSit.CodiceVia,
                SitIntegrationService.NomiCampiSit.CodiceCivico
            };
        }
    }
}
