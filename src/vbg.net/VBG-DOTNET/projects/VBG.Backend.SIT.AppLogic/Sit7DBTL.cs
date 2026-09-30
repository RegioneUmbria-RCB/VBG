using PersonalLib2.Data;
using SIGePro.Manager.VerticalizzazioniBase;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using VBG.Backend.SIT.AppLogic.Data;
using VBG.Backend.SIT.AppLogic.Exceptions;
using VBG.Backend.SIT.AppLogic.Manager;
using VBG.Backend.SIT.AppLogic.ValidazioneFormale;
using VBG.Backend.SIT.Verticalizzazioni;


namespace VBG.Backend.SIT.AppLogic
{
    /// <summary>
    /// Classe specifica per il Sit 7Dbtl
    /// </summary>
    [SitImplementation("SIT_7DBTL")]
    public class Sit7Dbtl : SitBase
    {

        public Sit7Dbtl()
            : base(new ValidazioneFormaleTramiteCodiceCivicoService())
        {
        }

        private string _connectionString = "";
        private string _provider = "";
        private string _ownerTabelle = "";
        private string _lastCommandText = "";
        private string _urlZoomDaCivico = "";
        private string _urlZoomDaMappale = "";
        private bool _ignoraDatiToponomastici = false;

        #region Utility
        public override void SetupVerticalizzazione(IVerticalizzazioniFactory verticalizzazioniFactory)
        {
            try
            {
                var verticalizzazione = verticalizzazioniFactory.Create<VerticalizzazioneSit7dbtl>(this.IdComuneAlias, this.Software);
                if (!verticalizzazione.Attiva)
                {
                    throw new Exception("La verticalizzazione SIT_7DBTL non è attiva.\r\n");
                }

                this._ownerTabelle = verticalizzazione.Owner;
                this._connectionString = verticalizzazione.Connectionstring;
                this._provider = verticalizzazione.Provider;
                this._urlZoomDaCivico = verticalizzazione.UrlZoomDaCivico;
                this._urlZoomDaMappale = verticalizzazione.UrlZoomDaMappale;
                this._ignoraDatiToponomastici = verticalizzazione.IgnoraDatiToponomastica == "1";

                var provider = (ProviderType)Enum.Parse(typeof(ProviderType), this._provider, false);
                this.InternalDatabaseConnection = new DataBase(this._connectionString, provider);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore generato durante la lettura della verticalizzazione SIT_7DBTL. Metodo: GetParametriFromVertSIT7DBTL, modulo: Sit7DBTL. " + ex.Message + "\r\n");
            }
        }

        private string GetOwner(TipoViste tipoViste)
        {
            if (this._ownerTabelle.IndexOf('\\') != -1)
            {
                var owners = this._ownerTabelle.Split('\\');

                switch (tipoViste)
                {
                    case TipoViste.Toponomastica:
                        return owners[0];

                    case TipoViste.Catasto:
                        return owners[1];
                }
            }

            return this._ownerTabelle;
        }

        private string GetMessaggioDiValidazioneFallita(string nomeCampo)
        {
            var sValue = string.Empty;

            switch (nomeCampo)
            {
                case "SEZIONE":
                    sValue = "La sezione " + this.DataSit.Sezione + " non è valida per i dati inseriti";
                    break;
                case "FOGLIO":
                    sValue = "Il foglio " + this.DataSit.Foglio + " non è valido per i dati inseriti";
                    break;
                case "NUMERO":
                    sValue = "La particella " + this.DataSit.Particella + " non è valida per i dati inseriti";
                    break;
                case "CAP":
                    sValue = "Il CAP " + this.DataSit.CAP + " non è valido per i dati inseriti";
                    break;
                case "CIVICO":
                    sValue = "Il civico " + this.DataSit.Civico + " non è valido per i dati inseriti";
                    break;
                case "LETT_CIVICO":
                    sValue = "L'esponente " + this.DataSit.Esponente + " non è valido per i dati inseriti";
                    break;
                case "ID_EDIFICIO":
                    sValue = "Il fabbricato " + this.DataSit.Fabbricato + " non è valido per i dati inseriti";
                    break;
                case "SUBALTERNO":
                    sValue = "Il subalterno " + this.DataSit.Sub + " non è valido per i dati inseriti";
                    break;
                case "ID_IMMOBILE":
                    sValue = "L'unità immobiliare " + this.DataSit.UI + " non è valida per i dati inseriti";
                    break;
                case "INTERNO":
                    sValue = "L'interno " + this.DataSit.Interno + " non è valido per i dati inseriti";
                    break;
                case "LETT_INTERNO":
                    sValue = "L'esponente dell'interno " + this.DataSit.EsponenteInterno + " non è valido per i dati inseriti";
                    break;
            }

            return sValue;
        }

        private string GetMessaggioDiLetturaListaFallita(string sField)
        {
            var sValue = string.Empty;

            switch (sField)
            {
                case "SEZIONE":
                    sValue = "Non è possibile ottenere la lista delle sezioni per insufficienza di dati: il catasto deve essere fornito";
                    break;
                case "FOGLIO":
                    sValue = "Non è possibile ottenere la lista dei fogli per insufficienza di dati: il catasto e la sezione devono essere forniti";
                    break;
                case "NUMERO":
                    sValue = "Non è possibile ottenere la lista delle particelle per insufficienza di dati: il catasto ed il fabbricato/sezione,foglio devono essere forniti";
                    break;
                case "SUBALTERNO":
                    sValue = "Non è possibile ottenere la lista dei subalterni per insufficienza di dati: il catasto ed il fabbricato/sezione,foglio,particella devono essere forniti";
                    break;
                case "ID_IMMOBILE":
                    sValue = "Non è possibile ottenere la lista delle unità immobiliari per insufficienza di dati: il catasto ed il fabbricato/sezione,foglio,particella,sub devono essere forniti";
                    break;
                case "CIVICO":
                    sValue = "Non è possibile ottenere la lista dei civici per insufficienza di dati: il fabbricato/via devono essere forniti";
                    break;
                case "LETT_CIVICO":
                    sValue = "Non è possibile ottenere la lista degli esponenti per insufficienza di dati: il fabbricato/via ed il civico devono essere forniti";
                    break;
                case "ID_EDIFICIO":
                    sValue = "Non è possibile ottenere la lista dei fabbricati per insufficienza di dati: la via/dati catastali devono essere forniti";
                    break;
                case "INTERNO":
                    sValue = "Non è possibile ottenere la lista degli interni per insufficienza di dati: il codice civico/fabbricato,civico,esponente devono essere forniti";
                    break;
                case "LETT_INTERNO":
                    sValue = "Non è possibile ottenere la lista degli esponenti degli interni per insufficienza di dati: il codice civico,interno/fabbricato,civico,esponente,interno devono essere forniti";
                    break;
            }

            return sValue;
        }

        private string GetSit7(string nomeCampo, TipoQuery tipoQuery)
        {
            if (nomeCampo == "CAP")
                return this.GetElemento(nomeCampo, "AE_CIVICO_P", tipoQuery);

            if (nomeCampo == "COD_VIA")
            {
                if (string.IsNullOrEmpty(this.DataSit.Fabbricato) && string.IsNullOrEmpty(this.DataSit.Sezione) &&
                    string.IsNullOrEmpty(this.DataSit.Foglio) && string.IsNullOrEmpty(this.DataSit.Particella))
                    return this.GetElemento(nomeCampo, "TO_V_CIVICI_SIT_V", tipoQuery);

                return this.GetElemento(nomeCampo, "AE_CIVICO_P", tipoQuery);
            }

            if (string.IsNullOrEmpty(this.DataSit.TipoCatasto) || (this.DataSit.TipoCatasto == "F"))
            {
                switch (nomeCampo)
                {
                    case "CIVICO":
                    case "LETT_CIVICO":
                        if (string.IsNullOrEmpty(this.DataSit.Fabbricato) && string.IsNullOrEmpty(this.DataSit.Sezione) && string.IsNullOrEmpty(this.DataSit.Foglio) && string.IsNullOrEmpty(this.DataSit.Particella))
                            return this.GetElemento(nomeCampo, "TO_V_CIVICI_SIT_V", tipoQuery);

                        switch (nomeCampo)
                        {
                            case "CIVICO":
                                nomeCampo = "NUMERO";
                                break;
                            case "LETT_CIVICO":
                                nomeCampo = "ESPONENTE";
                                break;
                        }
                        return this.GetElemento(nomeCampo, "AE_CIVICO_P", tipoQuery);

                    case "ID_EDIFICIO":
                        return this.GetElemento(nomeCampo, "AE_CIVICO_P", tipoQuery);

                    default:
                        return this.GetElemento(nomeCampo, "TO_V_CIVICI_SIT_V", tipoQuery);
                }
            }
            else
            {
                throw new CatastoException(this.GetMessaggioDiValidazioneFallita(nomeCampo));
            }

        }

        private string GetDbtl(string nomeCampo, TipoQuery tipoQuery)
        {
            if (string.IsNullOrEmpty(this.DataSit.TipoCatasto))
                throw new CatastoException("Non è possibile tramite le viste validare " + nomeCampo + " (occorre specificare il catasto)", true);

            if (this.DataSit.TipoCatasto == "F")
            {
                if (nomeCampo == "SUBALTERNO" || nomeCampo == "ID_IMMOBILE")
                    return this.GetElemento(nomeCampo, "DW_V_S3_UIU_INIT", tipoQuery);

                if (string.IsNullOrEmpty(this.DataSit.CodVia) && string.IsNullOrEmpty(this.DataSit.Civico) && string.IsNullOrEmpty(this.DataSit.Esponente) && string.IsNullOrEmpty(this.DataSit.CAP) && string.IsNullOrEmpty(this.DataSit.Fabbricato))
                    return this.GetElemento(nomeCampo, "DW_V_S3_UIU_INIT", tipoQuery);

                if (nomeCampo == "SEZIONE")
                    nomeCampo = "G_SEZIONE";

                if (nomeCampo == "FOGLIO")
                    nomeCampo = "G_FOGLIO";

                if (nomeCampo == "NUMERO")
                    nomeCampo = "G_NUMERO";

                return this.GetElemento(nomeCampo, "AE_CIVICO_P", tipoQuery);
            }


            if (nomeCampo != "ID_IMMOBILE")
            {
                if (this.IsCampiToponomasticaImmobileVuoti())
                    return this.GetElemento(nomeCampo, "DW_V_S3_PARTICELLA_INIT", tipoQuery);

                throw new CatastoException(this.GetMessaggioDiValidazioneFallita(nomeCampo));
            }


            return String.Empty;
        }

        private RetSit ElencoSit7(string nomeCampo)
        {
            if (string.IsNullOrEmpty(this.DataSit.TipoCatasto) || (this.DataSit.TipoCatasto == "F"))
            {
                switch (nomeCampo)
                {
                    case "CIVICO":
                    case "LETT_CIVICO":
                        if (string.IsNullOrEmpty(this.DataSit.Fabbricato) && string.IsNullOrEmpty(this.DataSit.Sezione) && string.IsNullOrEmpty(this.DataSit.Foglio) && string.IsNullOrEmpty(this.DataSit.Particella))
                            return this.GetElenco(nomeCampo, "TO_V_CIVICI_SIT_V");

                        switch (nomeCampo)
                        {
                            case "CIVICO":
                                nomeCampo = "NUMERO";
                                break;
                            case "LETT_CIVICO":
                                nomeCampo = "ESPONENTE";
                                break;
                        }
                        return this.GetElenco(nomeCampo, "AE_CIVICO_P");

                    case "ID_EDIFICIO":
                        return this.GetElenco(nomeCampo, "AE_CIVICO_P");

                    default:
                        return this.GetElenco(nomeCampo, "TO_V_CIVICI_SIT_V");
                }
            }

            return new RetSit(true);


        }

        private RetSit ElencoDbtl(string sField)
        {
            if (string.IsNullOrEmpty(this.DataSit.TipoCatasto))
                throw new CatastoException(this.GetMessaggioDiLetturaListaFallita(sField));

            if (this.DataSit.TipoCatasto == "F")
            {
                if (sField == "SUBALTERNO" || sField == "ID_IMMOBILE")
                {
                    return this.GetElenco(sField, "DW_V_S3_UIU_INIT");
                }

                if ((string.IsNullOrEmpty(this.DataSit.CodVia) && string.IsNullOrEmpty(this.DataSit.Civico) && string.IsNullOrEmpty(this.DataSit.Esponente) && string.IsNullOrEmpty(this.DataSit.CAP) && string.IsNullOrEmpty(this.DataSit.Fabbricato)) || this._ignoraDatiToponomastici)
                {
                    return this.GetElenco(sField, "DW_V_S3_UIU_INIT");
                }

                if (sField == "SEZIONE")
                    sField = "G_SEZIONE";

                if (sField == "FOGLIO")
                    sField = "G_FOGLIO";

                if (sField == "NUMERO")
                    sField = "G_NUMERO";

                return this.GetElenco(sField, "AE_CIVICO_P");
            }

            // Il tipo catasto è terreni i stringa vuota
            if (sField != "ID_IMMOBILE")
            {
                if (this.IsCampiToponomasticaImmobileVuoti())
                {
                    return this.GetElenco(sField, "DW_V_S3_PARTICELLA_INIT");
                }

                return new RetSit(true);
            }

            return new RetSit(true);
        }


        private string GetCompleteTableName(string nomiTabelle, TipoViste tipoViste)
        {
            var nomeCompletoTabelle = string.Empty;
            var listaNomiTabelle = nomiTabelle.Split(new Char[] { ',' });

            foreach (var elem in listaNomiTabelle)
            {
                var tmpNomeTabella = elem.Trim();

                if (!String.IsNullOrEmpty(this._ownerTabelle))
                    tmpNomeTabella = this.GetOwner(tipoViste) + "." + tmpNomeTabella;

                nomeCompletoTabelle += tmpNomeTabella + ",";
            }

            nomeCompletoTabelle = nomeCompletoTabelle.Remove(nomeCompletoTabelle.Length - 1);

            return nomeCompletoTabelle;
        }

        private string GetQuery(string nomeCampo, string nomeTabella, TipoQuery tipoQuery)
        {
            switch (nomeTabella)
            {
                case "TO_V_CIVICI_SIT_V": return this.BuildQueryForToVCiviciSitV(nomeCampo, nomeTabella, tipoQuery);
                case "AE_CIVICO_P": return this.BuildQueryForAeCivicoP(nomeCampo, nomeTabella, tipoQuery);
                case "DW_V_S3_PARTICELLA_INIT": return this.BuildQueryForDwVS3ParticellaInit(nomeCampo, nomeTabella, tipoQuery);
                case "DW_V_S3_UIU_INIT": return this.BuildQueryForDwVS3UiuInit(nomeCampo, nomeTabella, tipoQuery);
                default: return string.Empty;
            }
        }

        private string BuildQueryForToVCiviciSitV(string nomeCampo, string nomeTabella, TipoQuery tipoQuery)
        {
            var whereConditions = new List<string>();

            if (tipoQuery == TipoQuery.Elenco)
            {
                this.AddConditionIfNeeded(whereConditions, this.DataSit.CodVia, nomeCampo, "COD_VIA",
                    () => $"{this.InternalDatabaseConnection.Specifics.RTrimFunction("TO_V_CIVICI_SIT_V.COD_VIA")} = {this.RightTrim(this.DataSit.CodVia)}");

                this.AddConditionIfNeeded(whereConditions, this.DataSit.Civico, nomeCampo, "CIVICO",
                    () => $"{this.InternalDatabaseConnection.Specifics.RTrimFunction("TO_V_CIVICI_SIT_V.CIVICO")} = {this.RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Civico) ? this.DataSit.Civico : "-1")}");

                this.AddConditionIfNeeded(whereConditions, this.DataSit.CodCivico, nomeCampo, "CIV_KEY",
                    () => $"{this.InternalDatabaseConnection.Specifics.RTrimFunction("TO_V_CIVICI_SIT_V.CIV_KEY")} = '{this.RightTrim(this.DataSit.CodCivico)}'");

                this.AddConditionIfNeeded(whereConditions, this.DataSit.Esponente, nomeCampo, "LETT_CIVICO",
                    () => $"{this.InternalDatabaseConnection.Specifics.RTrimFunction("TO_V_CIVICI_SIT_V.LETT_CIVICO")} = '{this.RightTrim(this.DataSit.Esponente)}'");

                this.AddConditionIfNeeded(whereConditions, this.DataSit.Interno, nomeCampo, "INTERNO",
                    () => $"{this.InternalDatabaseConnection.Specifics.RTrimFunction("TO_V_CIVICI_SIT_V.INTERNO")} = '{this.RightTrim(this.DataSit.Interno)}'");

                this.AddConditionIfNeeded(whereConditions, this.DataSit.EsponenteInterno, nomeCampo, "LETT_INTERNO",
                    () => $"{this.InternalDatabaseConnection.Specifics.RTrimFunction("TO_V_CIVICI_SIT_V.LETT_INTERNO")} = '{this.RightTrim(this.DataSit.EsponenteInterno)}'");
            }
            else if (tipoQuery == TipoQuery.Validazione)
            {
                this.AddConditionIfValuePresent(whereConditions, this.DataSit.CodVia,
                    () => $"{this.InternalDatabaseConnection.Specifics.RTrimFunction("TO_V_CIVICI_SIT_V.COD_VIA")} = {this.RightTrim(this.DataSit.CodVia)}");

                this.AddConditionIfValuePresent(whereConditions, this.DataSit.Civico,
                    () => $"{this.InternalDatabaseConnection.Specifics.RTrimFunction("TO_V_CIVICI_SIT_V.CIVICO")} = {this.RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Civico) ? this.DataSit.Civico : "-1")}");

                this.AddConditionIfValuePresent(whereConditions, this.DataSit.CodCivico,
                    () => $"{this.InternalDatabaseConnection.Specifics.RTrimFunction("TO_V_CIVICI_SIT_V.CIV_KEY")} = '{this.RightTrim(this.DataSit.CodCivico)}'");

                this.AddConditionIfValuePresent(whereConditions, this.DataSit.Esponente,
                    () => $"{this.InternalDatabaseConnection.Specifics.RTrimFunction("TO_V_CIVICI_SIT_V.LETT_CIVICO")} = '{this.RightTrim(this.DataSit.Esponente)}'");

                this.AddConditionIfValuePresent(whereConditions, this.DataSit.Interno,
                    () => $"{this.InternalDatabaseConnection.Specifics.RTrimFunction("TO_V_CIVICI_SIT_V.INTERNO")} = '{this.RightTrim(this.DataSit.Interno)}'");

                this.AddConditionIfValuePresent(whereConditions, this.DataSit.EsponenteInterno,
                    () => $"{this.InternalDatabaseConnection.Specifics.RTrimFunction("TO_V_CIVICI_SIT_V.LETT_INTERNO")} = '{this.RightTrim(this.DataSit.EsponenteInterno)}'");
            }

            whereConditions.Add("TO_V_CIVICI_SIT_V.STATO = 'A'");

            return this.BuildSelectQuery(nomeTabella, nomeCampo, TipoViste.Toponomastica, whereConditions);
        }

        private string BuildQueryForAeCivicoP(string nomeCampo, string nomeTabella, TipoQuery tipoQuery)
        {
            var whereConditions = new List<string>();

            if (tipoQuery == TipoQuery.Elenco)
            {
                this.AddConditionIfNeeded(whereConditions, this.DataSit.CodVia, nomeCampo, "COD_VIA",
                    () => $"{this.InternalDatabaseConnection.Specifics.RTrimFunction("AE_CIVICO_P.COD_VIA")} = {this.RightTrim(this.DataSit.CodVia)}");

                this.AddConditionIfNeeded(whereConditions, this.DataSit.Civico, nomeCampo, "NUMERO",
                    () => $"{this.InternalDatabaseConnection.Specifics.RTrimFunction("AE_CIVICO_P.NUMERO")} = {this.RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Civico) ? this.DataSit.Civico : "-1")}");

                this.AddConditionIfNeeded(whereConditions, this.DataSit.CAP, nomeCampo, "CAP",
                    () => $"{this.InternalDatabaseConnection.Specifics.RTrimFunction("AE_CIVICO_P.CAP")} = {this.RightTrim(this.DataSit.CAP)}");

                this.AddConditionIfNeeded(whereConditions, this.DataSit.Fabbricato, nomeCampo, "ID_EDIFICIO",
                    () => $"{this.InternalDatabaseConnection.Specifics.RTrimFunction("AE_CIVICO_P.ID_EDIFICIO")} = {this.RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Fabbricato) ? this.DataSit.Fabbricato : "-1")}");

                this.AddConditionIfNeeded(whereConditions, this.DataSit.Esponente, nomeCampo, "ESPONENTE",
                    () => $"{this.InternalDatabaseConnection.Specifics.RTrimFunction("AE_CIVICO_P.ESPONENTE")} = '{this.RightTrim(this.DataSit.Esponente)}'");

                this.AddConditionIfNeeded(whereConditions, this.DataSit.Sezione, nomeCampo, "G_SEZIONE",
                    () => $"{this.InternalDatabaseConnection.Specifics.RTrimFunction("AE_CIVICO_P.G_SEZIONE")} = '{this.RightTrim(this.DataSit.Sezione)}'");

                this.AddConditionIfNeeded(whereConditions, this.DataSit.Foglio, nomeCampo, "G_FOGLIO",
                    () => $"{this.InternalDatabaseConnection.Specifics.RTrimFunction("AE_CIVICO_P.G_FOGLIO")} = '{this.RightTrim(this.DataSit.Foglio)}'");

                this.AddConditionIfNeeded(whereConditions, this.DataSit.Particella, nomeCampo, "G_NUMERO",
                    () => $"{this.InternalDatabaseConnection.Specifics.RTrimFunction("AE_CIVICO_P.G_NUMERO")} = '{this.RightTrim(this.DataSit.Particella)}'");
            }
            else if (tipoQuery == TipoQuery.Validazione)
            {
                this.AddConditionIfValuePresent(whereConditions, this.DataSit.CodVia,
                    () => $"{this.InternalDatabaseConnection.Specifics.RTrimFunction("AE_CIVICO_P.COD_VIA")} = {this.RightTrim(this.DataSit.CodVia)}");

                this.AddConditionIfValuePresent(whereConditions, this.DataSit.Civico,
                    () => $"{this.InternalDatabaseConnection.Specifics.RTrimFunction("AE_CIVICO_P.NUMERO")} = {this.RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Civico) ? this.DataSit.Civico : "-1")}");

                this.AddConditionIfValuePresent(whereConditions, this.DataSit.CAP,
                    () => $"{this.InternalDatabaseConnection.Specifics.RTrimFunction("AE_CIVICO_P.CAP")} = {this.RightTrim(this.DataSit.CAP)}");

                this.AddConditionIfValuePresent(whereConditions, this.DataSit.Fabbricato,
                    () => $"{this.InternalDatabaseConnection.Specifics.RTrimFunction("AE_CIVICO_P.ID_EDIFICIO")} = {this.RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Fabbricato) ? this.DataSit.Fabbricato : "-1")}");

                this.AddConditionIfValuePresent(whereConditions, this.DataSit.Esponente,
                    () => $"{this.InternalDatabaseConnection.Specifics.RTrimFunction("AE_CIVICO_P.ESPONENTE")} = '{this.RightTrim(this.DataSit.Esponente)}'");

                this.AddConditionIfValuePresent(whereConditions, this.DataSit.Sezione,
                    () => $"{this.InternalDatabaseConnection.Specifics.RTrimFunction("AE_CIVICO_P.G_SEZIONE")} = '{this.RightTrim(this.DataSit.Sezione)}'");

                this.AddConditionIfValuePresent(whereConditions, this.DataSit.Foglio,
                    () => $"{this.InternalDatabaseConnection.Specifics.RTrimFunction("AE_CIVICO_P.G_FOGLIO")} = '{this.RightTrim(this.DataSit.Foglio)}'");

                this.AddConditionIfValuePresent(whereConditions, this.DataSit.Particella,
                    () => $"{this.InternalDatabaseConnection.Specifics.RTrimFunction("AE_CIVICO_P.G_NUMERO")} = '{this.RightTrim(this.DataSit.Particella)}'");
            }

            whereConditions.Add($"{this.InternalDatabaseConnection.Specifics.RTrimFunction("AE_CIVICO_P.DATA_FINE")} is null");

            return this.BuildSelectQuery(nomeTabella, nomeCampo, TipoViste.Toponomastica, whereConditions);
        }

        private string BuildQueryForDwVS3ParticellaInit(string nomeCampo, string nomeTabella, TipoQuery tipoQuery)
        {
            var whereConditions = new List<string>();

            if (tipoQuery == TipoQuery.Elenco)
            {
                this.AddConditionIfNeeded(whereConditions, this.DataSit.Sezione, nomeCampo, "SEZIONE",
                    () => $"{this.InternalDatabaseConnection.Specifics.RTrimFunction("DW_V_S3_PARTICELLA_INIT.SEZIONE")} = '{this.RightTrim(this.DataSit.Sezione)}'");

                this.AddConditionIfNeeded(whereConditions, this.DataSit.Foglio, nomeCampo, "FOGLIO",
                    () => $"{this.InternalDatabaseConnection.Specifics.RTrimFunction("DW_V_S3_PARTICELLA_INIT.FOGLIO")} = {this.RightTrim(this.DataSit.Foglio)}");

                this.AddConditionIfNeeded(whereConditions, this.DataSit.Particella, nomeCampo, "NUMERO",
                    () => $"{this.InternalDatabaseConnection.Specifics.RTrimFunction("DW_V_S3_PARTICELLA_INIT.NUMERO")} = '{this.LeftPad(this.RightTrim(this.DataSit.Particella), 5)}'");

                this.AddConditionIfNeeded(whereConditions, this.DataSit.Sub, nomeCampo, "SUBALTERNO",
                    () => $"{this.InternalDatabaseConnection.Specifics.RTrimFunction("DW_V_S3_PARTICELLA_INIT.SUBALTERNO")} = '{this.RightTrim(this.DataSit.Sub)}'");
            }
            else if (tipoQuery == TipoQuery.Validazione)
            {
                this.AddConditionIfValuePresent(whereConditions, this.DataSit.Sezione,
                    () => $"{this.InternalDatabaseConnection.Specifics.RTrimFunction("DW_V_S3_PARTICELLA_INIT.SEZIONE")} = '{this.RightTrim(this.DataSit.Sezione)}'");

                this.AddConditionIfValuePresent(whereConditions, this.DataSit.Foglio,
                    () => $"{this.InternalDatabaseConnection.Specifics.RTrimFunction("DW_V_S3_PARTICELLA_INIT.FOGLIO")} = '{this.RightTrim(this.DataSit.Foglio)}'");

                this.AddConditionIfValuePresent(whereConditions, this.DataSit.Particella,
                    () => $"{this.InternalDatabaseConnection.Specifics.RTrimFunction("DW_V_S3_PARTICELLA_INIT.NUMERO")} = '{this.LeftPad(this.RightTrim(this.DataSit.Particella), 5)}'");

                this.AddConditionIfValuePresent(whereConditions, this.DataSit.Sub,
                    () => $"{this.InternalDatabaseConnection.Specifics.RTrimFunction("DW_V_S3_PARTICELLA_INIT.SUBALTERNO")} = '{this.RightTrim(this.DataSit.Sub)}'");
            }

            return this.BuildSelectQuery(nomeTabella, nomeCampo, TipoViste.Catasto, whereConditions);
        }

        private string BuildQueryForDwVS3UiuInit(string nomeCampo, string nomeTabella, TipoQuery tipoQuery)
        {
            var whereConditions = new List<string>();

            if (tipoQuery == TipoQuery.Elenco)
            {
                this.AddConditionIfNeeded(whereConditions, this.DataSit.UI, nomeCampo, "ID_IMMOBILE",
                    () => $"{this.InternalDatabaseConnection.Specifics.RTrimFunction("DW_V_S3_UIU_INIT.ID_IMMOBILE")} = {this.RightTrim(this.DataSit.UI)}");

                this.AddConditionIfNeeded(whereConditions, this.DataSit.Sezione, nomeCampo, "SEZIONE",
                    () => $"{this.InternalDatabaseConnection.Specifics.RTrimFunction("DW_V_S3_UIU_INIT.SEZIONE")} = '{this.RightTrim(this.DataSit.Sezione)}'");

                this.AddConditionIfNeeded(whereConditions, this.DataSit.Foglio, nomeCampo, "FOGLIO",
                    () => $"{this.InternalDatabaseConnection.Specifics.RTrimFunction("DW_V_S3_UIU_INIT.FOGLIO")} = '{this.LeftPad(this.RightTrim(this.DataSit.Foglio), 4)}'");

                this.AddConditionIfNeeded(whereConditions, this.DataSit.Particella, nomeCampo, "NUMERO",
                    () => $"{this.InternalDatabaseConnection.Specifics.RTrimFunction("DW_V_S3_UIU_INIT.NUMERO")} = '{this.LeftPad(this.RightTrim(this.DataSit.Particella), 5)}'");

                this.AddConditionIfNeeded(whereConditions, this.DataSit.Sub, nomeCampo, "SUBALTERNO",
                    () => $"{this.InternalDatabaseConnection.Specifics.RTrimFunction("DW_V_S3_UIU_INIT.SUBALTERNO")} = '{this.LeftPad(this.RightTrim(this.DataSit.Sub), 4)}'");
            }
            else if (tipoQuery == TipoQuery.Validazione)
            {
                this.AddConditionIfValuePresent(whereConditions, this.DataSit.UI,
                    () => $"{this.InternalDatabaseConnection.Specifics.RTrimFunction("DW_V_S3_UIU_INIT.ID_IMMOBILE")} = {this.RightTrim(this.DataSit.UI)}");

                this.AddConditionIfValuePresent(whereConditions, this.DataSit.Sezione,
                    () => $"{this.InternalDatabaseConnection.Specifics.RTrimFunction("DW_V_S3_UIU_INIT.SEZIONE")} = '{this.RightTrim(this.DataSit.Sezione)}'");

                this.AddConditionIfValuePresent(whereConditions, this.DataSit.Foglio,
                    () => $"{this.InternalDatabaseConnection.Specifics.RTrimFunction("DW_V_S3_UIU_INIT.FOGLIO")} = '{this.LeftPad(this.RightTrim(this.DataSit.Foglio), 4)}'");

                this.AddConditionIfValuePresent(whereConditions, this.DataSit.Particella,
                    () => $"{this.InternalDatabaseConnection.Specifics.RTrimFunction("DW_V_S3_UIU_INIT.NUMERO")} = '{this.LeftPad(this.RightTrim(this.DataSit.Particella), 5)}'");

                this.AddConditionIfValuePresent(whereConditions, this.DataSit.Sub,
                    () => $"{this.InternalDatabaseConnection.Specifics.RTrimFunction("DW_V_S3_UIU_INIT.SUBALTERNO")} = '{this.LeftPad(this.RightTrim(this.DataSit.Sub), 4)}'");
            }

            return this.BuildSelectQuery(nomeTabella, nomeCampo, TipoViste.Catasto, whereConditions);
        }

        private void AddConditionIfNeeded(List<string> conditions, string value, string nomeCampo, string targetField, Func<string> conditionBuilder)
        {
            if (!string.IsNullOrEmpty(value) && nomeCampo != targetField)
            {
                conditions.Add(conditionBuilder());
            }
        }

        private void AddConditionIfValuePresent(List<string> conditions, string value, Func<string> conditionBuilder)
        {
            if (!string.IsNullOrEmpty(value))
            {
                conditions.Add(conditionBuilder());
            }
        }

        private string BuildSelectQuery(string nomeTabella, string nomeCampo, TipoViste tipoViste, List<string> whereConditions)
        {
            var query = $"Select distinct {nomeTabella}.{nomeCampo} from {this.GetCompleteTableName(nomeTabella, tipoViste)}";

            if (whereConditions.Any())
            {
                query += $" where {string.Join(" and ", whereConditions)}";
            }

            query += $" order by {nomeTabella}.{nomeCampo}";

            return query;
        }

        private string GetElemento(string campo, string nomeTabella, TipoQuery tipoQuery)
        {
            try
            {
                this.EnsureConnectionIsOpen();

                this._lastCommandText = this.GetQuery(campo, nomeTabella, tipoQuery);

                using (var cmd = this.InternalDatabaseConnection.CreateCommand(this._lastCommandText))
                {
                    using (var dr = cmd.ExecuteReader())
                    {
                        var sRetVal = "";
                        var iCount = 0;

                        while (dr.Read())
                        {
                            var dato = this.RightTrim(dr[campo].ToString());

                            iCount++;

                            if (iCount == 1)
                            {
                                sRetVal = dato;
                            }
                            else
                            {
                                sRetVal = "";
                                break;
                            }
                        }

                        return sRetVal;
                    }
                }
            }
            finally
            {
                if (this.InternalDatabaseConnection.Connection.State == ConnectionState.Open)
                    this.InternalDatabaseConnection.Connection.Close();
            }
        }

        private RetSit GetElenco(string nomeCampo, string nomeTabella)
        {
            var pRetSit = new RetSit(true);

            try
            {
                this.EnsureConnectionIsOpen();
                this._lastCommandText = this.GetQuery(nomeCampo, nomeTabella, TipoQuery.Elenco);

                using (var cmd = this.InternalDatabaseConnection.CreateCommand(this._lastCommandText))
                {
                    using (var dr = cmd.ExecuteReader())
                    {
                        while (dr.Read())
                        {
                            var sDato = this.RightTrim(dr[nomeCampo].ToString());

                            if (!string.IsNullOrEmpty(sDato) && !pRetSit.DataCollection.Contains(sDato))
                            {
                                pRetSit.DataCollection.Add(sDato);
                            }
                        }
                    }
                }
            }
            finally
            {
                if (this.InternalDatabaseConnection.Connection.State == ConnectionState.Open)
                    this.InternalDatabaseConnection.Connection.Close();
            }

            return pRetSit;
        }

        #endregion



        #region Metodi per ottenere elenchi di elementi catastali o facenti parte dell'indirizzo




        public override RetSit ElencoCivici()
        {
            RetSit pRetSit;

            try
            {
                if ((!String.IsNullOrEmpty(this.DataSit.Fabbricato)) || (!String.IsNullOrEmpty(this.DataSit.CodVia)))
                {
                    pRetSit = this.ElencoSit7("CIVICO");
                }
                else
                {
                    pRetSit = this.RestituisciErroreSit(this.GetMessaggioDiLetturaListaFallita("CIVICO"), MessageCode.ElencoCivici, false);
                }
            }
            catch (CatastoException ex)
            {
                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.ElencoCivici, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione dell'elenco dei civici. Metodo: ElencoCivici, modulo: SitCore. " + ex.Message + "\r\n Query: " + this._lastCommandText);
            }

            return pRetSit;
        }


        public override RetSit ElencoEsponenti()
        {
            RetSit pRetSit;

            try
            {
                if (!String.IsNullOrEmpty(this.DataSit.Civico) && ((!String.IsNullOrEmpty(this.DataSit.Fabbricato)) || (!String.IsNullOrEmpty(this.DataSit.CodVia))))
                {
                    pRetSit = this.ElencoSit7("LETT_CIVICO");
                }
                else
                {
                    pRetSit = this.RestituisciErroreSit(this.GetMessaggioDiLetturaListaFallita("LETT_CIVICO"), MessageCode.ElencoEsponenti, false);
                }
            }
            catch (CatastoException ex)
            {
                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.ElencoEsponenti, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione dell'elenco degli esponenti. Metodo: ElencoEsponenti, modulo: SitCore. " + ex.Message + "\r\n Query: " + this._lastCommandText);
            }

            return pRetSit;
        }


        public override RetSit ElencoInterni()
        {
            RetSit pRetSit;

            try
            {
                if (!String.IsNullOrEmpty(this.DataSit.CodCivico) || (!String.IsNullOrEmpty(this.DataSit.Fabbricato) && !String.IsNullOrEmpty(this.DataSit.Civico) && !String.IsNullOrEmpty(this.DataSit.Esponente)))
                {
                    pRetSit = this.ElencoSit7("INTERNO");
                }
                else
                {
                    pRetSit = this.RestituisciErroreSit(this.GetMessaggioDiLetturaListaFallita("INTERNO"), MessageCode.ElencoInterni, false);
                }
            }
            catch (CatastoException ex)
            {
                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.ElencoInterni, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione dell'elenco degli interni. Metodo: ElencoInterni, modulo: SitCore. " + ex.Message + "\r\n Query: " + this._lastCommandText);
            }

            return pRetSit;
        }

        public override RetSit ElencoEsponentiInterno()
        {
            RetSit pRetSit;

            try
            {
                if ((!String.IsNullOrEmpty(this.DataSit.CodCivico) && !String.IsNullOrEmpty(this.DataSit.Interno)) || (!String.IsNullOrEmpty(this.DataSit.Fabbricato) && !String.IsNullOrEmpty(this.DataSit.Civico) && !String.IsNullOrEmpty(this.DataSit.Esponente) && !String.IsNullOrEmpty(this.DataSit.Interno)))
                {
                    pRetSit = this.ElencoSit7("LETT_INTERNO");
                }
                else
                {
                    pRetSit = this.RestituisciErroreSit(this.GetMessaggioDiLetturaListaFallita("LETT_INTERNO"), MessageCode.ElencoEsponentiInterno, false);
                }
            }
            catch (CatastoException ex)
            {
                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.ElencoEsponentiInterno, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione dell'elenco degli interni dell'esponente. Metodo: ElencoEsponentiInterno, modulo: SitCore. " + ex.Message + "\r\n Query: " + this._lastCommandText);
            }

            return pRetSit;
        }


        public override RetSit ElencoFabbricati()
        {
            RetSit pRetSit;

            try
            {
                if ((!String.IsNullOrEmpty(this.DataSit.CodVia)) || (!String.IsNullOrEmpty(this.DataSit.Sezione) && !String.IsNullOrEmpty(this.DataSit.Foglio) && !String.IsNullOrEmpty(this.DataSit.Particella)))
                {
                    pRetSit = this.ElencoSit7("ID_EDIFICIO");
                }
                else
                {
                    pRetSit = this.RestituisciErroreSit(this.GetMessaggioDiLetturaListaFallita("ID_EDIFICIO"), MessageCode.ElencoFabbricati, false);
                }
            }
            catch (CatastoException ex)
            {
                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.ElencoFabbricati, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione dell'elenco dei fabbricati. Metodo: ElencoFabbricati, modulo: SitCore. " + ex.Message + "\r\n Query: " + this._lastCommandText);
            }

            return pRetSit;
        }


        public override RetSit ElencoSezioni()
        {
            RetSit pRetSit;

            try
            {
                pRetSit = this.ElencoDbtl("SEZIONE");

            }
            catch (CatastoException ex)
            {
                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.ElencoSezioni, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione dell'elenco delle sezioni. Metodo: ElencoSezioni, modulo: SitCore. " + ex.Message + "\r\n Query: " + this._lastCommandText);
            }

            return pRetSit;
        }

        public override RetSit ElencoFogli()
        {
            RetSit pRetSit;

            try
            {
                if ((!String.IsNullOrEmpty(this.DataSit.Fabbricato)) || (!String.IsNullOrEmpty(this.DataSit.Sezione)))
                {
                    pRetSit = this.ElencoDbtl("FOGLIO");

                    char[] charsToTrim = { '0' };

                    for (var iCount = 0; iCount < pRetSit.DataCollection.Count; iCount++)
                    {
                        pRetSit.DataCollection[iCount] = this.LeftTrim(pRetSit.DataCollection[iCount], charsToTrim);
                    }
                }
                else
                {
                    pRetSit = this.RestituisciErroreSit(this.GetMessaggioDiLetturaListaFallita("FOGLIO"), MessageCode.ElencoFogli, false);
                }
            }
            catch (CatastoException ex)
            {
                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.ElencoFogli, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione dell'elenco dei fogli. Metodo: ElencoFogli, modulo: SitCore. " + ex.Message + "\r\n Query: " + this._lastCommandText);
            }

            return pRetSit;
        }

        public override RetSit ElencoParticelle()
        {
            RetSit pRetSit;

            try
            {
                if ((!String.IsNullOrEmpty(this.DataSit.Fabbricato)) || (!String.IsNullOrEmpty(this.DataSit.Sezione) && !String.IsNullOrEmpty(this.DataSit.Foglio)))
                {
                    pRetSit = this.ElencoDbtl("NUMERO");

                    char[] charsToTrim = { '0' };

                    for (var iCount = 0; iCount < pRetSit.DataCollection.Count; iCount++)
                    {
                        pRetSit.DataCollection[iCount] = this.LeftTrim(pRetSit.DataCollection[iCount], charsToTrim);
                    }
                }
                else
                {
                    pRetSit = this.RestituisciErroreSit(this.GetMessaggioDiLetturaListaFallita("NUMERO"), MessageCode.ElencoParticelle, false);

                }
            }
            catch (CatastoException ex)
            {
                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.ElencoParticelle, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione dell'elenco delle particelle. Metodo: ElencoParticelle, modulo: SitCore. " + ex.Message + "\r\n Query: " + this._lastCommandText);
            }

            return pRetSit;
        }

        public override RetSit ElencoSub()
        {
            RetSit pRetSit;

            try
            {
                if ((!String.IsNullOrEmpty(this.DataSit.Fabbricato)) || (!String.IsNullOrEmpty(this.DataSit.Sezione) && !String.IsNullOrEmpty(this.DataSit.Foglio) && !String.IsNullOrEmpty(this.DataSit.Particella)))
                {
                    pRetSit = this.ElencoDbtl("SUBALTERNO");

                    char[] charsToTrim = { '0' };

                    for (var iCount = 0; iCount < pRetSit.DataCollection.Count; iCount++)
                    {
                        pRetSit.DataCollection[iCount] = this.LeftTrim(pRetSit.DataCollection[iCount], charsToTrim);
                    }
                }
                else
                {
                    pRetSit = this.RestituisciErroreSit(this.GetMessaggioDiLetturaListaFallita("SUBALTERNO"), MessageCode.ElencoSub, false);

                }
            }
            catch (CatastoException ex)
            {
                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.ElencoSub, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione dell'elenco dei sub. Metodo: ElencoSub, modulo: Sit7Dbtl. " + ex.Message + "\r\n Query: " + this._lastCommandText);
            }

            return pRetSit;
        }

        public override RetSit ElencoUI()
        {
            RetSit pRetSit;

            try
            {
                if ((!String.IsNullOrEmpty(this.DataSit.Fabbricato)) || (!String.IsNullOrEmpty(this.DataSit.Sezione) && !String.IsNullOrEmpty(this.DataSit.Foglio) && !String.IsNullOrEmpty(this.DataSit.Particella) && !String.IsNullOrEmpty(this.DataSit.Sub)))
                {
                    pRetSit = this.ElencoDbtl("ID_IMMOBILE");
                }
                else
                {
                    pRetSit = this.RestituisciErroreSit(this.GetMessaggioDiLetturaListaFallita("ID_IMMOBILE"), MessageCode.ElencoUI, false);

                }
            }
            catch (CatastoException ex)
            {
                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.ElencoUI, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione dell'elenco delle unità immobiliari. Metodo: ElencoUI, modulo: Sit7Dbtl. " + ex.Message + "\r\n Query: " + this._lastCommandText);
            }

            return pRetSit;
        }


        #endregion

        #region Metodi per la verifica e la restituzione di un singolo elemento catastale o facente parte dell'indirizzo

        protected override string GetEsponente()
        {
            var sRetVal = "";

            try
            {
                sRetVal = this.GetSit7("LETT_CIVICO", TipoQuery.Elenco);
            }
            catch (CatastoException)
            {
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di un esponente. Metodo: GetEsponente, modulo: Sit7Dbtl. " + ex.Message + "\r\n Query: " + this._lastCommandText);
            }

            return sRetVal;
        }

        protected override RetSit VerificaEsponente()
        {
            RetSit pRetSit;
            try
            {
                var sElem = this.GetSit7("LETT_CIVICO", TipoQuery.Validazione);
                if (!String.IsNullOrEmpty(sElem))
                {
                    pRetSit = new RetSit(true);
                    this.DataSit.Esponente = sElem;
                }
                else
                {
                    pRetSit = this.RestituisciErroreSit(this.GetMessaggioDiValidazioneFallita("LETT_CIVICO"), MessageCode.EsponenteValidazione, false);
                }
            }
            catch (CatastoException ex)
            {
                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.EsponenteValidazione, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione di un esponente. Metodo: VerificaEsponente, modulo: SitCore. " + ex.Message + "\r\n Query: " + this._lastCommandText);
            }

            return pRetSit;
        }

        protected override string GetInterno()
        {
            var sRetVal = "";

            try
            {
                sRetVal = this.GetSit7("INTERNO", TipoQuery.Elenco);
            }
            catch (CatastoException)
            {
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di un interno. Metodo: GetInterno, modulo: SitCore. " + ex.Message + "\r\n Query: " + this._lastCommandText);
            }

            return sRetVal;
        }

        protected override RetSit VerificaInterno()
        {
            RetSit pRetSit;

            if (Init.Utils.StringChecker.IsNumeric(this.DataSit.Interno))
            {
                try
                {
                    var sElem = this.GetSit7("INTERNO", TipoQuery.Validazione);
                    if (!String.IsNullOrEmpty(sElem))
                    {
                        pRetSit = new RetSit(true);
                        this.DataSit.Interno = sElem;
                    }
                    else
                    {
                        pRetSit = this.RestituisciErroreSit(this.GetMessaggioDiValidazioneFallita("INTERNO"), MessageCode.InternoValidazione, false);
                    }
                }
                catch (CatastoException ex)
                {
                    pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.InternoValidazione, false);
                }
                catch (Exception ex)
                {
                    throw new Exception("Errore durante la validazione di un interno. Metodo: VerificaInterno, modulo: SitCore. " + ex.Message + "\r\n Query: " + this._lastCommandText);
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
            try
            {
                sRetVal = this.GetSit7("LETT_INTERNO", TipoQuery.Elenco);
            }
            catch (CatastoException)
            {
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di un esponente interno. Metodo: GetEsponenteInterno, modulo: SitCore. " + ex.Message + "\r\n Query: " + this._lastCommandText);
            }

            return sRetVal;
        }

        protected override RetSit VerificaEsponenteInterno()
        {
            RetSit pRetSit;

            try
            {
                var sElem = this.GetSit7("LETT_INTERNO", TipoQuery.Validazione);
                if (!String.IsNullOrEmpty(sElem))
                {
                    pRetSit = new RetSit(true);
                    this.DataSit.EsponenteInterno = sElem;
                }
                else
                {
                    pRetSit = this.RestituisciErroreSit(this.GetMessaggioDiValidazioneFallita("LETT_INTERNO"), MessageCode.EsponenteInternoValidazione, false);
                }
            }
            catch (CatastoException ex)
            {
                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.EsponenteInternoValidazione, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione di un esponente interno. Metodo: VerificaEsponenteInterno, modulo: SitCore. " + ex.Message + "\r\n Query: " + this._lastCommandText);
            }

            return pRetSit;
        }


        protected override RetSit VerificaFabbricato()
        {
            RetSit pRetSit;

            if (Init.Utils.StringChecker.IsNumeric(this.DataSit.Fabbricato))
            {
                try
                {
                    var sElem = this.GetSit7("ID_EDIFICIO", TipoQuery.Validazione);
                    if (!String.IsNullOrEmpty(sElem))
                    {
                        pRetSit = new RetSit(true);
                        this.DataSit.Fabbricato = sElem;
                    }
                    else
                    {
                        pRetSit = this.RestituisciErroreSit(this.GetMessaggioDiValidazioneFallita("ID_EDIFICIO"), MessageCode.FabbricatoValidazione, false);
                    }
                }
                catch (CatastoException ex)
                {
                    pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.FabbricatoValidazione, false);
                }
                catch (Exception ex)
                {
                    throw new Exception("Errore durante la validazione di un fabbricato. Metodo: VerificaFabbricato, modulo: SitCore. " + ex.Message + "\r\n Query: " + this._lastCommandText);
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

            try
            {
                sRetVal = this.GetDbtl("SEZIONE", TipoQuery.Elenco);
            }
            catch (CatastoException)
            {
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di una sezione. Metodo: GetSezione, modulo: SitCore. " + ex.Message + "\r\n Query: " + this._lastCommandText);
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

            try
            {
                var sElem = this.GetDbtl("SEZIONE", TipoQuery.Validazione);
                if (!String.IsNullOrEmpty(sElem))
                {
                    pRetSit = new RetSit(true);
                    this.DataSit.Sezione = sElem;
                }
                else
                {
                    pRetSit = this.RestituisciErroreSit(this.GetMessaggioDiValidazioneFallita("SEZIONE"), MessageCode.SezioneValidazione, false);
                }
            }
            catch (CatastoException ex)
            {
                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.SezioneValidazione, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione di una sezione. Metodo: VerificaSezione, modulo: SitCore. " + ex.Message + "\r\n Query: " + this._lastCommandText);
            }

            return pRetSit;
        }

        protected override string GetFoglio()
        {
            var sRetVal = "";

            try
            {
                sRetVal = this.GetDbtl("FOGLIO", TipoQuery.Elenco);

                char[] charsToTrim = { '0' };
                sRetVal = this.LeftTrim(sRetVal, charsToTrim);
            }
            catch (CatastoException)
            {
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di un foglio. Metodo: GetFoglio, modulo: SitCore. " + ex.Message + "\r\n Query: " + this._lastCommandText);
            }


            return sRetVal;
        }

        protected override RetSit VerificaFoglio()
        {
            RetSit pRetSit;

            try
            {
                var sElem = this.GetDbtl("FOGLIO", TipoQuery.Validazione);
                if (!String.IsNullOrEmpty(sElem))
                {
                    pRetSit = new RetSit(true);

                    char[] charsToTrim = { '0' };
                    sElem = this.LeftTrim(sElem, charsToTrim);

                    this.DataSit.Foglio = sElem;
                }
                else
                {
                    pRetSit = this.RestituisciErroreSit(this.GetMessaggioDiValidazioneFallita("FOGLIO"), MessageCode.FoglioValidazione, false);
                }
            }
            catch (CatastoException ex)
            {
                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.FoglioValidazione, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione di un foglio. Metodo: VerificaFoglio, modulo: SitCore. " + ex.Message + "\r\n Query: " + this._lastCommandText);
            }

            return pRetSit;
        }

        protected override string GetParticella()
        {
            var sRetVal = "";

            try
            {
                sRetVal = this.GetDbtl("NUMERO", TipoQuery.Elenco);

                char[] charsToTrim = { '0' };
                sRetVal = this.LeftTrim(sRetVal, charsToTrim);
            }
            catch (CatastoException)
            {
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di una particella. Metodo: GetParticella, modulo: SitCore. " + ex.Message + "\r\n Query: " + this._lastCommandText);
            }

            return sRetVal;

        }

        protected override RetSit VerificaParticella()
        {
            RetSit pRetSit;

            try
            {
                var sElem = this.GetDbtl("NUMERO", TipoQuery.Validazione);
                if (!String.IsNullOrEmpty(sElem))
                {
                    pRetSit = new RetSit(true);

                    char[] charsToTrim = { '0' };
                    sElem = this.LeftTrim(sElem, charsToTrim);

                    this.DataSit.Particella = sElem;
                }
                else
                {
                    pRetSit = this.RestituisciErroreSit(this.GetMessaggioDiValidazioneFallita("NUMERO"), MessageCode.ParticellaValidazione, false);
                }
            }
            catch (CatastoException ex)
            {
                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.ParticellaValidazione, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione di una particella. Metodo: VerificaParticella, modulo: SitCore. " + ex.Message + "\r\n Query: " + this._lastCommandText);
            }

            return pRetSit;
        }

        protected override string GetSub()
        {
            var sRetVal = "";
            try
            {
                sRetVal = this.GetDbtl("SUBALTERNO", TipoQuery.Elenco);

                char[] charsToTrim = { '0' };
                sRetVal = this.LeftTrim(sRetVal, charsToTrim);
            }
            catch (CatastoException)
            {
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di un sub. Metodo: GetSub, modulo: Sit7Dbtl. " + ex.Message + "\r\n Query: " + this._lastCommandText);
            }

            return sRetVal;
        }

        protected override RetSit VerificaSub()
        {
            RetSit pRetSit;


            try
            {
                var sElem = this.GetDbtl("SUBALTERNO", TipoQuery.Validazione);
                if (!String.IsNullOrEmpty(sElem))
                {
                    pRetSit = new RetSit(true);

                    char[] charsToTrim = { '0' };
                    sElem = this.LeftTrim(sElem, charsToTrim);

                    this.DataSit.Sub = sElem;
                }
                else
                {
                    pRetSit = this.RestituisciErroreSit(this.GetMessaggioDiValidazioneFallita("SUBALTERNO"), MessageCode.SubValidazione, false);
                }
            }
            catch (CatastoException ex)
            {
                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.SubValidazione, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione di un subalterno. Metodo: VerificaSub, modulo: Sit7Dbtl. " + ex.Message + "\r\n Query: " + this._lastCommandText);
            }


            return pRetSit;
        }

        protected override string GetUI()
        {
            var sRetVal = "";

            try
            {
                sRetVal = this.GetDbtl("ID_IMMOBILE", TipoQuery.Elenco);
            }
            catch (CatastoException)
            {
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di una unità immobiliare. Metodo: GetUI, modulo: Sit7Dbtl. " + ex.Message + "\r\n Query: " + this._lastCommandText);
            }

            return sRetVal;
        }

        protected override RetSit VerificaUI()
        {
            RetSit pRetSit;

            try
            {
                var sElem = this.GetDbtl("ID_IMMOBILE", TipoQuery.Validazione);
                if (!String.IsNullOrEmpty(sElem))
                {
                    pRetSit = new RetSit(true);
                    this.DataSit.UI = sElem;
                }
                else
                {
                    pRetSit = this.RestituisciErroreSit(this.GetMessaggioDiValidazioneFallita("ID_IMMOBILE"), MessageCode.UIValidazione, false);
                }
            }
            catch (CatastoException ex)
            {
                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.UIValidazione, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione di una unità immobiliare. Metodo: VerificaUI, modulo: Sit7Dbtl. " + ex.Message + "\r\n Query: " + this._lastCommandText);
            }

            return pRetSit;
        }



        protected override RetSit VerificaCivico()
        {
            RetSit pRetSit;

            if (Init.Utils.StringChecker.IsNumeric(this.DataSit.Civico))
            {
                try
                {
                    var sElem = this.GetSit7("CIVICO", TipoQuery.Validazione);
                    if (!String.IsNullOrEmpty(sElem))
                    {
                        pRetSit = new RetSit(true);
                        this.DataSit.Civico = sElem;
                    }
                    else
                    {
                        pRetSit = this.RestituisciErroreSit(this.GetMessaggioDiValidazioneFallita("CIVICO"), MessageCode.CivicoValidazione, false);
                    }
                }
                catch (CatastoException ex)
                {
                    pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.CivicoValidazione, false);
                }
                catch (Exception ex)
                {
                    throw new Exception("Errore durante la validazione di un civico. Metodo: VerificaCivico, modulo: SitCore. " + ex.Message + "\r\n Query: " + this._lastCommandText);
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

            try
            {
                sRetVal = this.GetSit7("CAP", TipoQuery.Elenco);
            }
            catch (CatastoException)
            {
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di un CAP. Metodo: GetCAP, modulo: Sit7DBTL. " + ex.Message + "\r\n Query: " + this._lastCommandText);
            }

            return sRetVal;
        }

        protected override RetSit VerificaCAP()
        {
            RetSit pRetSit;

            try
            {
                var sElem = this.GetSit7("CAP", TipoQuery.Validazione);
                if (!String.IsNullOrEmpty(sElem))
                {
                    pRetSit = new RetSit(true);
                    this.DataSit.CAP = sElem;
                }
                else
                {
                    pRetSit = this.RestituisciErroreSit(this.GetMessaggioDiValidazioneFallita("CAP"), MessageCode.CAPValidazione, false);
                }
            }
            catch (CatastoException ex)
            {
                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.CAPValidazione, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione di un CAP. Metodo: VerificaCAP, modulo: SitCore. " + ex.Message + "\r\n Query: " + this._lastCommandText);
            }

            return pRetSit;
        }

        protected override string GetCodCivico()
        {
            var sRetVal = "";

            try
            {
                sRetVal = this.GetSit7("CIV_KEY", TipoQuery.Elenco);

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
                throw new Exception("Errore durante la restituzione di un codice civico. Metodo: GetCodCivico, modulo: SitCore. " + ex.Message + "\r\n Query: " + this._lastCommandText);
            }

            return sRetVal;
        }

        protected override string GetCodFabbricato()
        {
            var sRetVal = "";

            try
            {
                sRetVal = this.GetSit7("ID_EDIFICIO", TipoQuery.Elenco);
            }
            catch (CatastoException)
            {
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di un codice fabbricato. Metodo: GetCodFabbricato, modulo: SitCore. " + ex.Message + "\r\n Query: " + this._lastCommandText);
            }

            return sRetVal;
        }

        protected override string GetCodVia()
        {
            var sRetVal = "";

            try
            {
                sRetVal = this.GetSit7("COD_VIA", TipoQuery.Elenco);
            }
            catch (CatastoException)
            {
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di un codice via. Metodo: GetCodVia, modulo: SitCore. " + ex.Message + "\r\n Query: " + this._lastCommandText);
            }

            return sRetVal;
        }

        #endregion

        public override string[] GetListaCampiGestiti()
        {
            return new string[]{
                SitIntegrationService.NomiCampiSit.Esponente,
                SitIntegrationService.NomiCampiSit.Interno,
                SitIntegrationService.NomiCampiSit.EsponenteInterno,
                SitIntegrationService.NomiCampiSit.Fabbricato,
                SitIntegrationService.NomiCampiSit.Sezione,
                SitIntegrationService.NomiCampiSit.TipoCatasto,
                SitIntegrationService.NomiCampiSit.Foglio,
                SitIntegrationService.NomiCampiSit.Particella,
                SitIntegrationService.NomiCampiSit.Sub,
                SitIntegrationService.NomiCampiSit.UnitaImmobiliare,
                SitIntegrationService.NomiCampiSit.Civico,
                SitIntegrationService.NomiCampiSit.Cap,
                SitIntegrationService.NomiCampiSit.CodiceCivico,
            };
        }

        public override BaseDto<SitFeatures.TipoVisualizzazione, string>[] GetVisualizzazioniBackoffice()
        {
            var l = new List<BaseDto<SitFeatures.TipoVisualizzazione, string>>();

            if (!String.IsNullOrEmpty(this._urlZoomDaCivico))
            {
                l.Add(new BaseDto<SitFeatures.TipoVisualizzazione, string>(SitFeatures.TipoVisualizzazione.PuntoDaIndirizzo, this._urlZoomDaCivico));
            }

            if (!String.IsNullOrEmpty(this._urlZoomDaMappale))
            {
                l.Add(new BaseDto<SitFeatures.TipoVisualizzazione, string>(SitFeatures.TipoVisualizzazione.PuntoDaMappale, this._urlZoomDaMappale));
            }

            return l.ToArray();
        }

        public override DettagliVia[] GetListaVie(FiltroRicercaListaVie filtro, string[] codiciComuni)
        {
            var ownerTabelle = String.IsNullOrEmpty(this._ownerTabelle) ? "" : $"{this._ownerTabelle}.";

            var sql = $@"
                select 
	                COD_VIA,
	                NOME_VIA,
	                TIPO_VIA,
	                STATO
                from 
	                {ownerTabelle}TO_VIA_V
                order by 
	                COD_VIA asc";

            return this.InternalDatabaseConnection.ExecuteReader(
                sql,
                mp => { },
                dr => new DettagliVia
                {
                    CodiceComune = null,
                    CodiceViario = dr.GetString("COD_VIA"),
                    Denominazione = dr.GetString("NOME_VIA"),
                    Toponimo = dr.GetString("TIPO_VIA"),
                    Localita = "",
                    DataFineValidita = dr.GetString("STATO") == "SOPPRESSA" ? DateTime.Now : (DateTime?)null
                }).ToArray();
        }

    }

    public enum TipoViste { Toponomastica, Catasto }


}
