using System;
using System.Data;
using System.Diagnostics;
using Init.SIGePro.Collection;
using Init.SIGePro.Manager;
using Init.SIGePro.Sit.Data;
using Init.SIGePro.Verticalizzazioni;
using PersonalLib2.Data;
using System.Runtime.InteropServices;
using Init.SIGePro.Exceptions.SIT;
using Init.SIGePro.Sit.ValidazioneFormale;
using Init.SIGePro.Sit.Manager;
using Init.SIGePro.Manager.DTO;
using System.Collections.Generic;

namespace Init.SIGePro.Sit
{
    /// <summary>
    /// Classe specifica per il Sit 7Dbtl
    /// </summary>
    public class SIT_7DBTL : SitBase
    {
		public SIT_7DBTL()
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
        public override void SetupVerticalizzazione()
        {
            GetParametriFromVertSIT7DBTL();
        }

        /// <summary>
        /// Metodo usato per leggere i parametri della verticalizzazione SIT_7DBTL
        /// </summary>
        private void GetParametriFromVertSIT7DBTL()
        {
            try
            {
				var verticalizzazione = new VerticalizzazioneSit7dbtl(this.IdComuneAlias, this.Software);

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

                ProviderType provider = (ProviderType)Enum.Parse(typeof(ProviderType), _provider, false);
                InternalDatabaseConnection = new DataBase(_connectionString, provider);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore generato durante la lettura della verticalizzazione SIT_7DBTL. Metodo: GetParametriFromVertSIT7DBTL, modulo: Sit7DBTL. " + ex.Message + "\r\n");
            }
        }

        private string GetOwner(TipoViste tipoViste)
        {
            if (_ownerTabelle.IndexOf('\\') != -1)
            {
				var owners = _ownerTabelle.Split('\\');

                switch (tipoViste)
                {
                    case TipoViste.Toponomastica:
						return owners[0];

                    case TipoViste.Catasto:
						return owners[1];
                }
            }
            
            return _ownerTabelle;
        }

        private string GetMessaggioDiValidazioneFallita(string nomeCampo)
        {
            string sValue = string.Empty;

            switch (nomeCampo)
            {
                case "SEZIONE":
                    sValue = "La sezione " + DataSit.Sezione + " non è valida per i dati inseriti";
                    break;
                case "FOGLIO":
                    sValue = "Il foglio " + DataSit.Foglio + " non è valido per i dati inseriti";
                    break;
                case "NUMERO":
                    sValue = "La particella " + DataSit.Particella + " non è valida per i dati inseriti";
                    break;
                case "CAP":
                    sValue = "Il CAP " + DataSit.CAP + " non è valido per i dati inseriti";
                    break;
                case "CIVICO":
                    sValue = "Il civico " + DataSit.Civico + " non è valido per i dati inseriti";
                    break;
                case "LETT_CIVICO":
                    sValue = "L'esponente " + DataSit.Esponente + " non è valido per i dati inseriti";
                    break;
                case "ID_EDIFICIO":
                    sValue = "Il fabbricato " + DataSit.Fabbricato + " non è valido per i dati inseriti";
                    break;
                case "SUBALTERNO":
                    sValue = "Il subalterno " + DataSit.Sub + " non è valido per i dati inseriti";
                    break;
                case "ID_IMMOBILE":
                    sValue = "L'unità immobiliare " + DataSit.UI + " non è valida per i dati inseriti";
                    break;
                case "INTERNO":
                    sValue = "L'interno " + DataSit.Interno + " non è valido per i dati inseriti";
                    break;
                case "LETT_INTERNO":
                    sValue = "L'esponente dell'interno " + DataSit.EsponenteInterno + " non è valido per i dati inseriti";
                    break;
            }

            return sValue;
        }

        private string GetMessaggioDiLetturaListaFallita(string sField)
        {
            string sValue = string.Empty;

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
            if(nomeCampo == "CAP")
				return GetElemento(nomeCampo, "AE_CIVICO_P", tipoQuery);

			if (nomeCampo == "COD_VIA")
			{
				if (string.IsNullOrEmpty(DataSit.Fabbricato) && string.IsNullOrEmpty(DataSit.Sezione) && 
					string.IsNullOrEmpty(DataSit.Foglio) && string.IsNullOrEmpty(DataSit.Particella))
					return GetElemento(nomeCampo, "TO_V_CIVICI_SIT_V", tipoQuery);

				return GetElemento(nomeCampo, "AE_CIVICO_P", tipoQuery);
			}

            if (string.IsNullOrEmpty(DataSit.TipoCatasto) || (this.DataSit.TipoCatasto == "F"))
            {
                switch (nomeCampo)
                {
                    case "CIVICO":
                    case "LETT_CIVICO":
                        if (string.IsNullOrEmpty(DataSit.Fabbricato) && string.IsNullOrEmpty(DataSit.Sezione) && string.IsNullOrEmpty(DataSit.Foglio) && string.IsNullOrEmpty(DataSit.Particella))
                            return GetElemento(nomeCampo, "TO_V_CIVICI_SIT_V",  tipoQuery);

                        switch (nomeCampo)
                        {
                            case "CIVICO":
                                nomeCampo = "NUMERO";
                                break;
                            case "LETT_CIVICO":
                                nomeCampo = "ESPONENTE";
                                break;
                        }
                        return GetElemento(nomeCampo, "AE_CIVICO_P",  tipoQuery);
                            
                    case "ID_EDIFICIO":
                        return GetElemento(nomeCampo, "AE_CIVICO_P",  tipoQuery);

                    default:
                        return GetElemento(nomeCampo, "TO_V_CIVICI_SIT_V",  tipoQuery);
                }
            }
            else
            {
                throw new CatastoException(GetMessaggioDiValidazioneFallita(nomeCampo));
            }

        }

        private string GetDbtl(string nomeCampo, TipoQuery tipoQuery)
        {
            if (string.IsNullOrEmpty(DataSit.TipoCatasto))
                throw new CatastoException("Non è possibile tramite le viste validare " + nomeCampo + " (occorre specificare il catasto)",true);

			if (this.DataSit.TipoCatasto == "F")
            {
				if (nomeCampo == "SUBALTERNO" || nomeCampo == "ID_IMMOBILE")
					return GetElemento(nomeCampo, "DW_V_S3_UIU_INIT",  tipoQuery);

                if (string.IsNullOrEmpty(DataSit.CodVia) && string.IsNullOrEmpty(DataSit.Civico) && string.IsNullOrEmpty(DataSit.Esponente) && string.IsNullOrEmpty(DataSit.CAP) && string.IsNullOrEmpty(DataSit.Fabbricato))
                    return GetElemento(nomeCampo, "DW_V_S3_UIU_INIT",  tipoQuery);
                        
				if( nomeCampo== "SEZIONE" )
					nomeCampo = "G_SEZIONE";

				if( nomeCampo == "FOGLIO")
                    nomeCampo = "G_FOGLIO";

				if( nomeCampo == "NUMERO")
                    nomeCampo = "G_NUMERO";

                return GetElemento(nomeCampo, "AE_CIVICO_P",  tipoQuery);
            }

			
            if (nomeCampo != "ID_IMMOBILE")
            {
                if (IsCampiToponomasticaImmobileVuoti())
                    return GetElemento(nomeCampo, "DW_V_S3_PARTICELLA_INIT",  tipoQuery);

                throw new CatastoException(GetMessaggioDiValidazioneFallita(nomeCampo));
            }

            
			return String.Empty;
        }

        private RetSit ElencoSit7(string nomeCampo)
        {
            if (string.IsNullOrEmpty(DataSit.TipoCatasto) || (this.DataSit.TipoCatasto == "F"))
            {
                switch (nomeCampo)
                {
                    case "CIVICO":
                    case "LETT_CIVICO":
                        if (string.IsNullOrEmpty(DataSit.Fabbricato) && string.IsNullOrEmpty(DataSit.Sezione) && string.IsNullOrEmpty(DataSit.Foglio) && string.IsNullOrEmpty(DataSit.Particella))
	                        return  GetElenco(nomeCampo, "TO_V_CIVICI_SIT_V");
                            
						switch (nomeCampo)
                        {
                            case "CIVICO":
                                nomeCampo = "NUMERO";
                                break;
                            case "LETT_CIVICO":
                                nomeCampo = "ESPONENTE";
                                break;
                        }
						return GetElenco(nomeCampo, "AE_CIVICO_P");
                        
                    case "ID_EDIFICIO":
                        return GetElenco(nomeCampo, "AE_CIVICO_P");
                        
                    default:
						return GetElenco(nomeCampo, "TO_V_CIVICI_SIT_V");
                }
            }
            
            return new RetSit(true);
            
            
        }

        private RetSit ElencoDbtl(string sField)
        {
            if (string.IsNullOrEmpty(DataSit.TipoCatasto))
                throw new CatastoException(GetMessaggioDiLetturaListaFallita(sField));

            if (this.DataSit.TipoCatasto == "F")
            {
                if (sField == "SUBALTERNO" || sField == "ID_IMMOBILE")
                {
                    return GetElenco(sField, "DW_V_S3_UIU_INIT");
                }

                if ((string.IsNullOrEmpty(DataSit.CodVia) && string.IsNullOrEmpty(DataSit.Civico) && string.IsNullOrEmpty(DataSit.Esponente) && string.IsNullOrEmpty(DataSit.CAP) && string.IsNullOrEmpty(DataSit.Fabbricato)) || this._ignoraDatiToponomastici)
                {
                    return GetElenco(sField, "DW_V_S3_UIU_INIT");
                }

                if( sField == "SEZIONE")
                    sField = "G_SEZIONE";

                if (sField == "FOGLIO")
                    sField = "G_FOGLIO";
                        
                if( sField == "NUMERO")
                    sField = "G_NUMERO";
                            
				return GetElenco(sField, "AE_CIVICO_P");
            }

			// Il tipo catasto è terreni i stringa vuota
            if (sField != "ID_IMMOBILE")
            {
                if (IsCampiToponomasticaImmobileVuoti())
                {
                    return GetElenco(sField, "DW_V_S3_PARTICELLA_INIT");
                }
                
				return new RetSit(true);
            }
                    
            return new RetSit(true);
        }


        private string GetCompleteTableName(string nomiTabelle, TipoViste tipoViste)
        {
            string nomeCompletoTabelle = string.Empty;
            string[] listaNomiTabelle = nomiTabelle.Split(new Char[] { ',' });

            foreach (string elem in listaNomiTabelle)
            {
                string tmpNomeTabella = elem.Trim();

                if (!String.IsNullOrEmpty(_ownerTabelle))
                    tmpNomeTabella = GetOwner(tipoViste) + "." + tmpNomeTabella;

                nomeCompletoTabelle += tmpNomeTabella + ",";
            }

            nomeCompletoTabelle = nomeCompletoTabelle.Remove(nomeCompletoTabelle.Length - 1);

            return nomeCompletoTabelle;
        }

        private string GetQuery(string nomeCampo, string nomeTabella, TipoQuery tipoQuery)
        {
            string query = string.Empty;

            switch (nomeTabella)
            {
                case "TO_V_CIVICI_SIT_V":
                    switch (tipoQuery)
                    {
                        case TipoQuery.Elenco:
                            query = "Select distinct " + nomeTabella + "." + nomeCampo + " from " + GetCompleteTableName(nomeTabella, TipoViste.Toponomastica) + " where " +
                                (((this.DataSit.CodVia != "") && (nomeCampo != "COD_VIA")) ? InternalDatabaseConnection.Specifics.RTrimFunction("TO_V_CIVICI_SIT_V.COD_VIA") + " = " + RightTrim(this.DataSit.CodVia) + " and " : "") +
                                (((this.DataSit.Civico != "") && (nomeCampo != "CIVICO")) ? InternalDatabaseConnection.Specifics.RTrimFunction("TO_V_CIVICI_SIT_V.CIVICO") + " = " + RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Civico) ? this.DataSit.Civico : "-1") + " and " : "") +
                                (((this.DataSit.CodCivico != "") && (nomeCampo != "CIV_KEY")) ? InternalDatabaseConnection.Specifics.RTrimFunction("TO_V_CIVICI_SIT_V.CIV_KEY") + " = '" + RightTrim(this.DataSit.CodCivico) + "' and " : "") +
                                (((this.DataSit.Esponente != "") && (nomeCampo != "LETT_CIVICO")) ? InternalDatabaseConnection.Specifics.RTrimFunction("TO_V_CIVICI_SIT_V.LETT_CIVICO") + " = '" + RightTrim(this.DataSit.Esponente) + "' and " : "") +
                                (((this.DataSit.Interno != "") && (nomeCampo != "INTERNO")) ? InternalDatabaseConnection.Specifics.RTrimFunction("TO_V_CIVICI_SIT_V.INTERNO") + " = '" + RightTrim(this.DataSit.Interno) + "' and " : "") +
                                (((this.DataSit.EsponenteInterno != "") && (nomeCampo != "LETT_INTERNO")) ? InternalDatabaseConnection.Specifics.RTrimFunction("TO_V_CIVICI_SIT_V.LETT_INTERNO") + " = '" + RightTrim(this.DataSit.EsponenteInterno) + "' and " : "") +
                                "TO_V_CIVICI_SIT_V.STATO = 'A'";
                            break;
                        case TipoQuery.Validazione:
                            query = "Select distinct " + nomeTabella + "." + nomeCampo + " from " + GetCompleteTableName(nomeTabella, TipoViste.Toponomastica) + " where " +
                                (((this.DataSit.CodVia != "")) ? InternalDatabaseConnection.Specifics.RTrimFunction("TO_V_CIVICI_SIT_V.COD_VIA") + " = " + RightTrim(this.DataSit.CodVia) + " and " : "") +
                                (((this.DataSit.Civico != "")) ? InternalDatabaseConnection.Specifics.RTrimFunction("TO_V_CIVICI_SIT_V.CIVICO") + " = " + RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Civico) ? this.DataSit.Civico : "-1") + " and " : "") +
                                (((this.DataSit.CodCivico != "")) ? InternalDatabaseConnection.Specifics.RTrimFunction("TO_V_CIVICI_SIT_V.CIV_KEY") + " = '" + RightTrim(this.DataSit.CodCivico) + "' and " : "") +
                                (((this.DataSit.Esponente != "")) ? InternalDatabaseConnection.Specifics.RTrimFunction("TO_V_CIVICI_SIT_V.LETT_CIVICO") + " = '" + RightTrim(this.DataSit.Esponente) + "' and " : "") +
                                (((this.DataSit.Interno != "")) ? InternalDatabaseConnection.Specifics.RTrimFunction("TO_V_CIVICI_SIT_V.INTERNO") + " = '" + RightTrim(this.DataSit.Interno) + "' and " : "") +
                                (((this.DataSit.EsponenteInterno != "")) ? InternalDatabaseConnection.Specifics.RTrimFunction("TO_V_CIVICI_SIT_V.LETT_INTERNO") + " = '" + RightTrim(this.DataSit.EsponenteInterno) + "' and " : "") +
                                "TO_V_CIVICI_SIT_V.STATO = 'A'";
                            break;
                    }
                    if (query.EndsWith("and "))
                        query = query.Remove(query.Length - 4, 4) + " order by " + nomeTabella + "." + nomeCampo;
                    else if (query.EndsWith("where "))
                        query = query.Remove(query.Length - 6, 6) + " order by " + nomeTabella + "." + nomeCampo;
                    else
                        query = query + " order by " + nomeTabella + "." + nomeCampo;

                    break;
                case "AE_CIVICO_P":
                    switch (tipoQuery)
                    {
                        case TipoQuery.Elenco:
                            query = "Select distinct " + nomeTabella + "." + nomeCampo + " from " + GetCompleteTableName(nomeTabella, TipoViste.Toponomastica) + " where " +
                                (((this.DataSit.CodVia != "") && (nomeCampo != "COD_VIA")) ? InternalDatabaseConnection.Specifics.RTrimFunction("AE_CIVICO_P.COD_VIA") + " = " + RightTrim(this.DataSit.CodVia) + " and " : "") +
                                (((this.DataSit.Civico != "") && (nomeCampo != "NUMERO")) ? InternalDatabaseConnection.Specifics.RTrimFunction("AE_CIVICO_P.NUMERO") + " = " + RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Civico) ? this.DataSit.Civico : "-1") + " and " : "") +
                                (((this.DataSit.CAP != "") && (nomeCampo != "CAP")) ? InternalDatabaseConnection.Specifics.RTrimFunction("AE_CIVICO_P.CAP") + " = " + RightTrim(this.DataSit.CAP) + " and " : "") +
                                (((this.DataSit.Fabbricato != "") && (nomeCampo != "ID_EDIFICIO")) ? InternalDatabaseConnection.Specifics.RTrimFunction("AE_CIVICO_P.ID_EDIFICIO") + " = " + RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Fabbricato) ? this.DataSit.Fabbricato : "-1") + " and " : "") +
                                (((this.DataSit.Esponente != "") && (nomeCampo != "ESPONENTE")) ? InternalDatabaseConnection.Specifics.RTrimFunction("AE_CIVICO_P.ESPONENTE") + " = '" + RightTrim(this.DataSit.Esponente) + "' and " : "") +
                                (((this.DataSit.Sezione != "") && (nomeCampo != "G_SEZIONE")) ? InternalDatabaseConnection.Specifics.RTrimFunction("AE_CIVICO_P.G_SEZIONE") + " = '" + RightTrim(this.DataSit.Sezione) + "' and " : "") +
                                (((this.DataSit.Foglio != "") && (nomeCampo != "G_FOGLIO")) ? InternalDatabaseConnection.Specifics.RTrimFunction("AE_CIVICO_P.G_FOGLIO") + " = '" + RightTrim(this.DataSit.Foglio) + "' and " : "") +
                                (((this.DataSit.Particella != "") && (nomeCampo != "G_NUMERO")) ? InternalDatabaseConnection.Specifics.RTrimFunction("AE_CIVICO_P.G_NUMERO") + " = '" + RightTrim(this.DataSit.Particella) + "' and " : "") +
                                InternalDatabaseConnection.Specifics.RTrimFunction("AE_CIVICO_P.DATA_FINE") + " is null order by " + nomeTabella + "." + nomeCampo;
                            break;
                        case TipoQuery.Validazione:
                            query = "Select distinct " + nomeTabella + "." + nomeCampo + " from " + GetCompleteTableName(nomeTabella, TipoViste.Toponomastica) + " where " +
                                (((this.DataSit.CodVia != "")) ? InternalDatabaseConnection.Specifics.RTrimFunction("AE_CIVICO_P.COD_VIA") + " = " + RightTrim(this.DataSit.CodVia) + " and " : "") +
                                (((this.DataSit.Civico != "")) ? InternalDatabaseConnection.Specifics.RTrimFunction("AE_CIVICO_P.NUMERO") + " = " + RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Civico) ? this.DataSit.Civico : "-1") + " and " : "") +
                                (((this.DataSit.CAP != "")) ? InternalDatabaseConnection.Specifics.RTrimFunction("AE_CIVICO_P.CAP") + " = " + RightTrim(this.DataSit.CAP) + " and " : "") +
                                (((this.DataSit.Fabbricato != "")) ? InternalDatabaseConnection.Specifics.RTrimFunction("AE_CIVICO_P.ID_EDIFICIO") + " = " + RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Fabbricato) ? this.DataSit.Fabbricato : "-1") + " and " : "") +
                                (((this.DataSit.Esponente != "")) ? InternalDatabaseConnection.Specifics.RTrimFunction("AE_CIVICO_P.ESPONENTE") + " = '" + RightTrim(this.DataSit.Esponente) + "' and " : "") +
                                (((this.DataSit.Sezione != "")) ? InternalDatabaseConnection.Specifics.RTrimFunction("AE_CIVICO_P.G_SEZIONE") + " = '" + RightTrim(this.DataSit.Sezione) + "' and " : "") +
                                (((this.DataSit.Foglio != "")) ? InternalDatabaseConnection.Specifics.RTrimFunction("AE_CIVICO_P.G_FOGLIO") + " = '" + RightTrim(this.DataSit.Foglio) + "' and " : "") +
                                (((this.DataSit.Particella != "")) ? InternalDatabaseConnection.Specifics.RTrimFunction("AE_CIVICO_P.G_NUMERO") + " = '" + RightTrim(this.DataSit.Particella) + "' and " : "") +
                                InternalDatabaseConnection.Specifics.RTrimFunction("AE_CIVICO_P.DATA_FINE") + " is null order by " + nomeTabella + "." + nomeCampo;
                            break;
                    }
                    break;
                case "DW_V_S3_PARTICELLA_INIT":
                    switch (tipoQuery)
                    {
                        case TipoQuery.Elenco:
                            query = "Select distinct " + nomeTabella + "." + nomeCampo + " from " + GetCompleteTableName(nomeTabella, TipoViste.Catasto) + " where " +
                                (((this.DataSit.Sezione != "") && (nomeCampo != "SEZIONE")) ? InternalDatabaseConnection.Specifics.RTrimFunction("DW_V_S3_PARTICELLA_INIT.SEZIONE") + " = '" + RightTrim(this.DataSit.Sezione) + "' and " : "") +
                                (((this.DataSit.Foglio != "") && (nomeCampo != "FOGLIO")) ? InternalDatabaseConnection.Specifics.RTrimFunction("DW_V_S3_PARTICELLA_INIT.FOGLIO") + " = " + RightTrim(this.DataSit.Foglio) + " and " : "") +
                                (((this.DataSit.Particella != "") && (nomeCampo != "NUMERO")) ? InternalDatabaseConnection.Specifics.RTrimFunction("DW_V_S3_PARTICELLA_INIT.NUMERO") + " = '" + LeftPad(RightTrim(this.DataSit.Particella), 5) + "' and " : "") +
                                (((this.DataSit.Sub != "") && (nomeCampo != "SUBALTERNO")) ? InternalDatabaseConnection.Specifics.RTrimFunction("DW_V_S3_PARTICELLA_INIT.SUBALTERNO") + " = '" + RightTrim(this.DataSit.Sub) + "' and " : "");
                            break;
                        case TipoQuery.Validazione:
                            query = "Select distinct " + nomeTabella + "." + nomeCampo + " from " + GetCompleteTableName(nomeTabella, TipoViste.Catasto) + " where " +
                                (((this.DataSit.Sezione != "")) ? InternalDatabaseConnection.Specifics.RTrimFunction("DW_V_S3_PARTICELLA_INIT.SEZIONE") + " = '" + RightTrim(this.DataSit.Sezione) + "' and " : "") +
                                (((this.DataSit.Foglio != "")) ? InternalDatabaseConnection.Specifics.RTrimFunction("DW_V_S3_PARTICELLA_INIT.FOGLIO") + " = '" + RightTrim(this.DataSit.Foglio) + "' and " : "") +
                                (((this.DataSit.Particella != "")) ? InternalDatabaseConnection.Specifics.RTrimFunction("DW_V_S3_PARTICELLA_INIT.NUMERO") + " = '" + LeftPad(RightTrim(this.DataSit.Particella), 5) + "' and " : "") +
                                (((this.DataSit.Sub != "")) ? InternalDatabaseConnection.Specifics.RTrimFunction("DW_V_S3_PARTICELLA_INIT.SUBALTERNO") + " = '" + RightTrim(this.DataSit.Sub) + "' and " : "");
                            break;
                    }

                    if (query.EndsWith("and "))
                        query = query.Remove(query.Length - 4, 4) + " order by " + nomeTabella + "." + nomeCampo;
                    else if (query.EndsWith("where "))
                        query = query.Remove(query.Length - 6, 6) + " order by " + nomeTabella + "." + nomeCampo;
                    else
                        query = query + " order by " + nomeTabella + "." + nomeCampo;
                    break;

                case "DW_V_S3_UIU_INIT":
                    switch (tipoQuery)
                    {
                        case TipoQuery.Elenco:
                            query = "Select distinct " + nomeTabella + "." + nomeCampo + " from " + GetCompleteTableName(nomeTabella, TipoViste.Catasto) + " where " +
                                (((this.DataSit.UI != "") && (nomeCampo != "ID_IMMOBILE")) ? InternalDatabaseConnection.Specifics.RTrimFunction("DW_V_S3_UIU_INIT.ID_IMMOBILE") + " = " + RightTrim(this.DataSit.UI) + " and " : "") +
                                (((this.DataSit.Sezione != "") && (nomeCampo != "SEZIONE")) ? InternalDatabaseConnection.Specifics.RTrimFunction("DW_V_S3_UIU_INIT.SEZIONE") + " = '" + RightTrim(this.DataSit.Sezione) + "' and " : "") +
                                (((this.DataSit.Foglio != "") && (nomeCampo != "FOGLIO")) ? InternalDatabaseConnection.Specifics.RTrimFunction("DW_V_S3_UIU_INIT.FOGLIO") + " = '" + LeftPad(RightTrim(this.DataSit.Foglio), 4) + "' and " : "") +
                                (((this.DataSit.Particella != "") && (nomeCampo != "NUMERO")) ? InternalDatabaseConnection.Specifics.RTrimFunction("DW_V_S3_UIU_INIT.NUMERO") + " = '" + LeftPad(RightTrim(this.DataSit.Particella), 5) + "' and " : "") +
                                (((this.DataSit.Sub != "") && (nomeCampo != "SUBALTERNO")) ? InternalDatabaseConnection.Specifics.RTrimFunction("DW_V_S3_UIU_INIT.SUBALTERNO") + " = '" + LeftPad(RightTrim(this.DataSit.Sub), 4) + "' and " : "");
                            break;
                        case TipoQuery.Validazione:
                            query = "Select distinct " + nomeTabella + "." + nomeCampo + " from " + GetCompleteTableName(nomeTabella, TipoViste.Catasto) + " where " +
                                (((this.DataSit.UI != "")) ? InternalDatabaseConnection.Specifics.RTrimFunction("DW_V_S3_UIU_INIT.ID_IMMOBILE") + " = " + RightTrim(this.DataSit.UI) + " and " : "") +
                                (((this.DataSit.Sezione != "")) ? InternalDatabaseConnection.Specifics.RTrimFunction("DW_V_S3_UIU_INIT.SEZIONE") + " = '" + RightTrim(this.DataSit.Sezione) + "' and " : "") +
                                (((this.DataSit.Foglio != "")) ? InternalDatabaseConnection.Specifics.RTrimFunction("DW_V_S3_UIU_INIT.FOGLIO") + " = '" + LeftPad(RightTrim(this.DataSit.Foglio), 4) + "' and " : "") +
                                (((this.DataSit.Particella != "")) ? InternalDatabaseConnection.Specifics.RTrimFunction("DW_V_S3_UIU_INIT.NUMERO") + " = '" + LeftPad(RightTrim(this.DataSit.Particella), 5) + "' and " : "") +
                                (((this.DataSit.Sub != "")) ? InternalDatabaseConnection.Specifics.RTrimFunction("DW_V_S3_UIU_INIT.SUBALTERNO") + " = '" + LeftPad(RightTrim(this.DataSit.Sub), 4) + "' and " : "");
                            break;
                    }

                    if (query.EndsWith("and "))
                        query = query.Remove(query.Length - 4, 4) + " order by " + nomeTabella + "." + nomeCampo;
                    else if (query.EndsWith("where "))
                        query = query.Remove(query.Length - 6, 6) + " order by " + nomeTabella + "." + nomeCampo;
                    else
                        query = query + " order by " + nomeTabella + "." + nomeCampo;
                    break;
            }


            return query;
        }

        private string GetElemento(string campo, string nomeTabella, TipoQuery tipoQuery)
        {
			try
			{
				EnsureConnectionIsOpen();

				_lastCommandText = GetQuery(campo, nomeTabella, tipoQuery);

				using (var cmd = InternalDatabaseConnection.CreateCommand(_lastCommandText))
				{
					using (var dr = cmd.ExecuteReader())
					{
						var sRetVal = "";
						var iCount = 0;

						while (dr.Read())
						{
							string dato = RightTrim(dr[campo].ToString());

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
				if (InternalDatabaseConnection.Connection.State == ConnectionState.Open)
					InternalDatabaseConnection.Connection.Close();
			}
        }

        private RetSit GetElenco(string nomeCampo, string nomeTabella)
        {
            RetSit pRetSit = new RetSit(true);

			try
			{
				EnsureConnectionIsOpen();
				_lastCommandText = GetQuery(nomeCampo, nomeTabella, TipoQuery.Elenco);

				using (IDbCommand cmd = InternalDatabaseConnection.CreateCommand(_lastCommandText))
				{
					using (var dr = cmd.ExecuteReader())
					{
						while (dr.Read())
						{
							string sDato = RightTrim(dr[nomeCampo].ToString());

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
				if (InternalDatabaseConnection.Connection.State == ConnectionState.Open)
					InternalDatabaseConnection.Connection.Close();
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
                if ((!Init.Utils.StringChecker.IsStringEmpty(this.DataSit.Fabbricato)) || (!Init.Utils.StringChecker.IsStringEmpty(this.DataSit.CodVia)))
                {
                    pRetSit = ElencoSit7("CIVICO");
                }
                else
                {
                    pRetSit = RestituisciErroreSit(GetMessaggioDiLetturaListaFallita("CIVICO"), MessageCode.ElencoCivici, false);
                }
            }
            catch (CatastoException ex)
            {
                pRetSit = RestituisciErroreSit(ex.Message, MessageCode.ElencoCivici, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione dell'elenco dei civici. Metodo: ElencoCivici, modulo: SitCore. " + ex.Message + "\r\n Query: " + _lastCommandText);
            }

            return pRetSit;
        }

       
        public override RetSit ElencoEsponenti()
        {
            RetSit pRetSit;

            try
            {
                if (!Init.Utils.StringChecker.IsStringEmpty(this.DataSit.Civico) && ((!Init.Utils.StringChecker.IsStringEmpty(this.DataSit.Fabbricato)) || (!Init.Utils.StringChecker.IsStringEmpty(this.DataSit.CodVia))))
                {
                    pRetSit = ElencoSit7("LETT_CIVICO");
                }
                else
                {
                    pRetSit = RestituisciErroreSit(GetMessaggioDiLetturaListaFallita("LETT_CIVICO"), MessageCode.ElencoEsponenti, false);
                }
            }
            catch (CatastoException ex)
            {
                pRetSit = RestituisciErroreSit(ex.Message, MessageCode.ElencoEsponenti, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione dell'elenco degli esponenti. Metodo: ElencoEsponenti, modulo: SitCore. " + ex.Message + "\r\n Query: " + _lastCommandText);
            }

            return pRetSit;
        }

        
        public override RetSit ElencoInterni()
        {
            RetSit pRetSit;

            try
            {
                if (!Init.Utils.StringChecker.IsStringEmpty(this.DataSit.CodCivico) || (!Init.Utils.StringChecker.IsStringEmpty(this.DataSit.Fabbricato) && !Init.Utils.StringChecker.IsStringEmpty(this.DataSit.Civico) && !Init.Utils.StringChecker.IsStringEmpty(this.DataSit.Esponente)))
                {
                    pRetSit = ElencoSit7("INTERNO");
                }
                else
                {
                    pRetSit = RestituisciErroreSit(GetMessaggioDiLetturaListaFallita("INTERNO"), MessageCode.ElencoInterni, false);
                }
            }
            catch (CatastoException ex)
            {
                pRetSit = RestituisciErroreSit(ex.Message, MessageCode.ElencoInterni, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione dell'elenco degli interni. Metodo: ElencoInterni, modulo: SitCore. " + ex.Message + "\r\n Query: " + _lastCommandText);
            }

            return pRetSit;
        }

        public override RetSit ElencoEsponentiInterno()
        {
            RetSit pRetSit;

            try
            {
                if ((!Init.Utils.StringChecker.IsStringEmpty(this.DataSit.CodCivico) && !Init.Utils.StringChecker.IsStringEmpty(this.DataSit.Interno)) || (!Init.Utils.StringChecker.IsStringEmpty(this.DataSit.Fabbricato) && !Init.Utils.StringChecker.IsStringEmpty(this.DataSit.Civico) && !Init.Utils.StringChecker.IsStringEmpty(this.DataSit.Esponente) && !Init.Utils.StringChecker.IsStringEmpty(this.DataSit.Interno)))
                {
                    pRetSit = ElencoSit7("LETT_INTERNO");
                }
                else
                {
                    pRetSit = RestituisciErroreSit(GetMessaggioDiLetturaListaFallita("LETT_INTERNO"), MessageCode.ElencoEsponentiInterno, false);
                }
            }
            catch (CatastoException ex)
            {
                pRetSit = RestituisciErroreSit(ex.Message, MessageCode.ElencoEsponentiInterno, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione dell'elenco degli interni dell'esponente. Metodo: ElencoEsponentiInterno, modulo: SitCore. " + ex.Message + "\r\n Query: " + _lastCommandText);
            }

            return pRetSit;
        }


        public override RetSit ElencoFabbricati()
        {
            RetSit pRetSit;

            try
            {
                if ((!Init.Utils.StringChecker.IsStringEmpty(this.DataSit.CodVia)) || (!Init.Utils.StringChecker.IsStringEmpty(this.DataSit.Sezione) && !Init.Utils.StringChecker.IsStringEmpty(this.DataSit.Foglio) && !Init.Utils.StringChecker.IsStringEmpty(this.DataSit.Particella)))
                {
                    pRetSit = ElencoSit7("ID_EDIFICIO");
                }
                else
                {
                    pRetSit = RestituisciErroreSit(GetMessaggioDiLetturaListaFallita("ID_EDIFICIO"), MessageCode.ElencoFabbricati, false);
                }
            }
            catch (CatastoException ex)
            {
                pRetSit = RestituisciErroreSit(ex.Message, MessageCode.ElencoFabbricati, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione dell'elenco dei fabbricati. Metodo: ElencoFabbricati, modulo: SitCore. " + ex.Message + "\r\n Query: " + _lastCommandText);
            }

            return pRetSit;
        }


        public override RetSit ElencoSezioni()
        {
            RetSit pRetSit;

            try
            {
                pRetSit = ElencoDbtl("SEZIONE");

            }
            catch (CatastoException ex)
            {
                pRetSit = RestituisciErroreSit(ex.Message, MessageCode.ElencoSezioni, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione dell'elenco delle sezioni. Metodo: ElencoSezioni, modulo: SitCore. " + ex.Message + "\r\n Query: " + _lastCommandText);
            }

            return pRetSit;
        }

        public override RetSit ElencoFogli()
        {
            RetSit pRetSit;

            try
            {
                if ((!Init.Utils.StringChecker.IsStringEmpty(this.DataSit.Fabbricato)) || (!Init.Utils.StringChecker.IsStringEmpty(this.DataSit.Sezione)))
                {
                    pRetSit = ElencoDbtl("FOGLIO");

                    char[] charsToTrim = { '0' };

                    for (int iCount = 0; iCount < pRetSit.DataCollection.Count; iCount++)
                    {
                        pRetSit.DataCollection[iCount] = LeftTrim(pRetSit.DataCollection[iCount], charsToTrim);
                    }
                }
                else
                {
                    pRetSit = RestituisciErroreSit(GetMessaggioDiLetturaListaFallita("FOGLIO"), MessageCode.ElencoFogli, false);
                }
            }
            catch (CatastoException ex)
            {
                pRetSit = RestituisciErroreSit(ex.Message, MessageCode.ElencoFogli, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione dell'elenco dei fogli. Metodo: ElencoFogli, modulo: SitCore. " + ex.Message + "\r\n Query: " + _lastCommandText);
            }

            return pRetSit;
        }

        public override RetSit ElencoParticelle()
        {
            RetSit pRetSit;

            try
            {
                if ((!Init.Utils.StringChecker.IsStringEmpty(this.DataSit.Fabbricato)) || (!Init.Utils.StringChecker.IsStringEmpty(this.DataSit.Sezione) && !Init.Utils.StringChecker.IsStringEmpty(this.DataSit.Foglio)))
                {
                    pRetSit = ElencoDbtl("NUMERO");

                    char[] charsToTrim = { '0' };

                    for (int iCount = 0; iCount < pRetSit.DataCollection.Count; iCount++)
                    {
                        pRetSit.DataCollection[iCount] = LeftTrim(pRetSit.DataCollection[iCount], charsToTrim);
                    }
                }
                else
                {
                    pRetSit = RestituisciErroreSit(GetMessaggioDiLetturaListaFallita("NUMERO"), MessageCode.ElencoParticelle, false);

                }
            }
            catch (CatastoException ex)
            {
                pRetSit = RestituisciErroreSit(ex.Message, MessageCode.ElencoParticelle, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione dell'elenco delle particelle. Metodo: ElencoParticelle, modulo: SitCore. " + ex.Message + "\r\n Query: " + _lastCommandText);
            }

            return pRetSit;
        }

        public override RetSit ElencoSub()
        {
            RetSit pRetSit;

            try
            {
                if ((!Init.Utils.StringChecker.IsStringEmpty(this.DataSit.Fabbricato)) || (!Init.Utils.StringChecker.IsStringEmpty(this.DataSit.Sezione) && !Init.Utils.StringChecker.IsStringEmpty(this.DataSit.Foglio) && !Init.Utils.StringChecker.IsStringEmpty(this.DataSit.Particella)))
                {
                    pRetSit = ElencoDbtl("SUBALTERNO");

                    char[] charsToTrim = { '0' };

                    for (int iCount = 0; iCount < pRetSit.DataCollection.Count; iCount++)
                    {
                        pRetSit.DataCollection[iCount] = LeftTrim(pRetSit.DataCollection[iCount], charsToTrim);
                    }
                }
                else
                {
                    pRetSit = RestituisciErroreSit(GetMessaggioDiLetturaListaFallita("SUBALTERNO"), MessageCode.ElencoSub, false);

                }
            }
            catch (CatastoException ex)
            {
                pRetSit = RestituisciErroreSit(ex.Message, MessageCode.ElencoSub, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione dell'elenco dei sub. Metodo: ElencoSub, modulo: Sit7Dbtl. " + ex.Message + "\r\n Query: " + _lastCommandText);
            }

            return pRetSit;
        }

        public override RetSit ElencoUI()
        {
            RetSit pRetSit;

            try
            {
                if ((!Init.Utils.StringChecker.IsStringEmpty(this.DataSit.Fabbricato)) || (!Init.Utils.StringChecker.IsStringEmpty(this.DataSit.Sezione) && !Init.Utils.StringChecker.IsStringEmpty(this.DataSit.Foglio) && !Init.Utils.StringChecker.IsStringEmpty(this.DataSit.Particella) && !Init.Utils.StringChecker.IsStringEmpty(this.DataSit.Sub)))
                {
                    pRetSit = ElencoDbtl("ID_IMMOBILE");
                }
                else
                {
                    pRetSit = RestituisciErroreSit(GetMessaggioDiLetturaListaFallita("ID_IMMOBILE"), MessageCode.ElencoUI, false);

                }
            }
            catch (CatastoException ex)
            {
                pRetSit = RestituisciErroreSit(ex.Message, MessageCode.ElencoUI, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione dell'elenco delle unità immobiliari. Metodo: ElencoUI, modulo: Sit7Dbtl. " + ex.Message + "\r\n Query: " + _lastCommandText);
            }

            return pRetSit;
        }


        #endregion

        #region Metodi per la verifica e la restituzione di un singolo elemento catastale o facente parte dell'indirizzo

        protected override string GetEsponente()
        {
            string sRetVal = "";

            try
            {
                sRetVal = GetSit7("LETT_CIVICO", TipoQuery.Elenco);
            }
            catch (CatastoException ex)
            {
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di un esponente. Metodo: GetEsponente, modulo: Sit7Dbtl. " + ex.Message + "\r\n Query: " + _lastCommandText);
            }

            return sRetVal;
        }

        protected override RetSit VerificaEsponente()
        {
            RetSit pRetSit;
            try
            {
                string sElem = GetSit7("LETT_CIVICO",  TipoQuery.Validazione);
                if (!Init.Utils.StringChecker.IsStringEmpty(sElem))
                {
                    pRetSit = new RetSit(true);
                    this.DataSit.Esponente = sElem;
                }
                else
                {
                    pRetSit = RestituisciErroreSit(GetMessaggioDiValidazioneFallita("LETT_CIVICO"), MessageCode.EsponenteValidazione, false);
                }
            }
            catch (CatastoException ex)
            {
                pRetSit = RestituisciErroreSit(ex.Message, MessageCode.EsponenteValidazione, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione di un esponente. Metodo: VerificaEsponente, modulo: SitCore. " + ex.Message + "\r\n Query: " + _lastCommandText);
            }

            return pRetSit;
        }

        protected override string GetInterno()
        {
            string sRetVal = "";

            try
            {
                sRetVal = GetSit7("INTERNO",  TipoQuery.Elenco);
            }
            catch (CatastoException ex)
            {
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di un interno. Metodo: GetInterno, modulo: SitCore. " + ex.Message + "\r\n Query: " + _lastCommandText);
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
                    string sElem = GetSit7("INTERNO",  TipoQuery.Validazione);
                    if (!Init.Utils.StringChecker.IsStringEmpty(sElem))
                    {
                        pRetSit = new RetSit(true);
                        this.DataSit.Interno = sElem;
                    }
                    else
                    {
                        pRetSit = RestituisciErroreSit(GetMessaggioDiValidazioneFallita("INTERNO"), MessageCode.InternoValidazione, false);
                    }
                }
                catch (CatastoException ex)
                {
                    pRetSit = RestituisciErroreSit(ex.Message, MessageCode.InternoValidazione, false);
                }
                catch (Exception ex)
                {
                    throw new Exception("Errore durante la validazione di un interno. Metodo: VerificaInterno, modulo: SitCore. " + ex.Message + "\r\n Query: " + _lastCommandText);
                }
            }
            else
            {
                pRetSit = RestituisciErroreSit("Non è possibile validare l'interno " + this.DataSit.Interno + " perchè non è un numero", MessageCode.InternoValidazioneNumero, false);
            }

            return pRetSit;
        }

        protected override string GetEsponenteInterno()
        {
            string sRetVal = "";
            try
            {
                sRetVal = GetSit7("LETT_INTERNO",  TipoQuery.Elenco);
            }
            catch (CatastoException ex)
            {
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di un esponente interno. Metodo: GetEsponenteInterno, modulo: SitCore. " + ex.Message + "\r\n Query: " + _lastCommandText);
            }

            return sRetVal;
        }

        protected override RetSit VerificaEsponenteInterno()
        {
            RetSit pRetSit;

            try
            {
                string sElem = GetSit7("LETT_INTERNO",  TipoQuery.Validazione);
                if (!Init.Utils.StringChecker.IsStringEmpty(sElem))
                {
                    pRetSit = new RetSit(true);
                    this.DataSit.EsponenteInterno = sElem;
                }
                else
                {
                    pRetSit = RestituisciErroreSit(GetMessaggioDiValidazioneFallita("LETT_INTERNO"), MessageCode.EsponenteInternoValidazione, false);
                }
            }
            catch (CatastoException ex)
            {
                pRetSit = RestituisciErroreSit(ex.Message, MessageCode.EsponenteInternoValidazione, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione di un esponente interno. Metodo: VerificaEsponenteInterno, modulo: SitCore. " + ex.Message + "\r\n Query: " + _lastCommandText);
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
                    string sElem = GetSit7("ID_EDIFICIO",  TipoQuery.Validazione);
                    if (!Init.Utils.StringChecker.IsStringEmpty(sElem))
                    {
                        pRetSit = new RetSit(true);
                        this.DataSit.Fabbricato = sElem;
                    }
                    else
                    {
                        pRetSit = RestituisciErroreSit(GetMessaggioDiValidazioneFallita("ID_EDIFICIO"), MessageCode.FabbricatoValidazione, false);
                    }
                }
                catch (CatastoException ex)
                {
                    pRetSit = RestituisciErroreSit(ex.Message, MessageCode.FabbricatoValidazione, false);
                }
                catch (Exception ex)
                {
                    throw new Exception("Errore durante la validazione di un fabbricato. Metodo: VerificaFabbricato, modulo: SitCore. " + ex.Message + "\r\n Query: " + _lastCommandText);
                }
            }
            else
            {
                pRetSit = RestituisciErroreSit("Non è possibile validare il fabbricato " + this.DataSit.Fabbricato + " perchè non è un numero", MessageCode.FabbricatoValidazioneNumero, true);
            }

            return pRetSit;
        }


        protected override string GetSezione()
        {
            string sRetVal = "";

			try
            {
                sRetVal = GetDbtl("SEZIONE",  TipoQuery.Elenco);
            }
            catch (CatastoException ex)
            {
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di una sezione. Metodo: GetSezione, modulo: SitCore. " + ex.Message + "\r\n Query: " + _lastCommandText);
            }

            return sRetVal;

        }


        protected override RetSit VerificaTipoCatasto()
        {
            RetSit pRetSit = null;

            if (IsCampiToponomasticaImmobileVuoti())
                pRetSit = new RetSit(true);
            else
            {
                if (DataSit.TipoCatasto == "T")
                    pRetSit = RestituisciErroreSit("Il tipocatasto " + DataSit.TipoCatasto + " non è valido per i dati inseriti", MessageCode.TipoCatastoValidazione, false);
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
                string sElem = GetDbtl("SEZIONE",  TipoQuery.Validazione);
                if (!Init.Utils.StringChecker.IsStringEmpty(sElem))
                {
                    pRetSit = new RetSit(true);
                    this.DataSit.Sezione = sElem;
                }
                else
                {
                    pRetSit = RestituisciErroreSit(GetMessaggioDiValidazioneFallita("SEZIONE"), MessageCode.SezioneValidazione, false);
                }
            }
            catch (CatastoException ex)
            {
                pRetSit = RestituisciErroreSit(ex.Message, MessageCode.SezioneValidazione, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione di una sezione. Metodo: VerificaSezione, modulo: SitCore. " + ex.Message + "\r\n Query: " + _lastCommandText);
            }

            return pRetSit;
        }

        protected override string GetFoglio()
        {
            string sRetVal = "";

            try
            {
                sRetVal = GetDbtl("FOGLIO",  TipoQuery.Elenco);

                char[] charsToTrim = { '0' };
                sRetVal = LeftTrim(sRetVal, charsToTrim);
            }
            catch (CatastoException ex)
            {
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di un foglio. Metodo: GetFoglio, modulo: SitCore. " + ex.Message + "\r\n Query: " + _lastCommandText);
            }


            return sRetVal;
        }

        protected override RetSit VerificaFoglio()
        {
            RetSit pRetSit;

            try
            {
                string sElem = GetDbtl("FOGLIO",  TipoQuery.Validazione);
                if (!Init.Utils.StringChecker.IsStringEmpty(sElem))
                {
                    pRetSit = new RetSit(true);

                    char[] charsToTrim = { '0' };
                    sElem = LeftTrim(sElem, charsToTrim);

                    this.DataSit.Foglio = sElem;
                }
                else
                {
                    pRetSit = RestituisciErroreSit(GetMessaggioDiValidazioneFallita("FOGLIO"), MessageCode.FoglioValidazione, false);
                }
            }
            catch (CatastoException ex)
            {
                pRetSit = RestituisciErroreSit(ex.Message, MessageCode.FoglioValidazione, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione di un foglio. Metodo: VerificaFoglio, modulo: SitCore. " + ex.Message + "\r\n Query: " + _lastCommandText);
            }

            return pRetSit;
        }

        protected override string GetParticella()
        {
            string sRetVal = "";

            try
            {
                sRetVal = GetDbtl("NUMERO",  TipoQuery.Elenco);

                char[] charsToTrim = { '0' };
                sRetVal = LeftTrim(sRetVal, charsToTrim);
            }
            catch (CatastoException ex)
            {
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di una particella. Metodo: GetParticella, modulo: SitCore. " + ex.Message + "\r\n Query: " + _lastCommandText);
            }

            return sRetVal;

        }

        protected override RetSit VerificaParticella()
        {
            RetSit pRetSit;

            try
            {
                string sElem = GetDbtl("NUMERO",  TipoQuery.Validazione);
                if (!Init.Utils.StringChecker.IsStringEmpty(sElem))
                {
                    pRetSit = new RetSit(true);

                    char[] charsToTrim = { '0' };
                    sElem = LeftTrim(sElem, charsToTrim);

                    this.DataSit.Particella = sElem;
                }
                else
                {
                    pRetSit = RestituisciErroreSit(GetMessaggioDiValidazioneFallita("NUMERO"), MessageCode.ParticellaValidazione, false);
                }
            }
            catch (CatastoException ex)
            {
                pRetSit = RestituisciErroreSit(ex.Message, MessageCode.ParticellaValidazione, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione di una particella. Metodo: VerificaParticella, modulo: SitCore. " + ex.Message + "\r\n Query: " + _lastCommandText);
            }

            return pRetSit;
        }

        protected override string GetSub()
        {
            string sRetVal = "";
            try
            {
                sRetVal = GetDbtl("SUBALTERNO",  TipoQuery.Elenco);

                char[] charsToTrim = { '0' };
                sRetVal = LeftTrim(sRetVal, charsToTrim);
            }
            catch (CatastoException ex)
            {
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di un sub. Metodo: GetSub, modulo: Sit7Dbtl. " + ex.Message + "\r\n Query: " + _lastCommandText);
            }

            return sRetVal;
        }

        protected override RetSit VerificaSub()
        {
            RetSit pRetSit;


            try
            {
                string sElem = GetDbtl("SUBALTERNO",  TipoQuery.Validazione);
                if (!Init.Utils.StringChecker.IsStringEmpty(sElem))
                {
                    pRetSit = new RetSit(true);

                    char[] charsToTrim = { '0' };
                    sElem = LeftTrim(sElem, charsToTrim);

                    this.DataSit.Sub = sElem;
                }
                else
                {
                    pRetSit = RestituisciErroreSit(GetMessaggioDiValidazioneFallita("SUBALTERNO"), MessageCode.SubValidazione, false);
                }
            }
            catch (CatastoException ex)
            {
                pRetSit = RestituisciErroreSit(ex.Message, MessageCode.SubValidazione, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione di un subalterno. Metodo: VerificaSub, modulo: Sit7Dbtl. " + ex.Message + "\r\n Query: " + _lastCommandText);
            }


            return pRetSit;
        }

        protected override string GetUI()
        {
            string sRetVal = "";

            try
            {
                sRetVal = GetDbtl("ID_IMMOBILE",  TipoQuery.Elenco);
            }
            catch (CatastoException ex)
            {
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di una unità immobiliare. Metodo: GetUI, modulo: Sit7Dbtl. " + ex.Message + "\r\n Query: " + _lastCommandText);
            }

            return sRetVal;
        }

        protected override RetSit VerificaUI()
        {
            RetSit pRetSit;

            try
            {
                string sElem = GetDbtl("ID_IMMOBILE",  TipoQuery.Validazione);
                if (!Init.Utils.StringChecker.IsStringEmpty(sElem))
                {
                    pRetSit = new RetSit(true);
                    this.DataSit.UI = sElem;
                }
                else
                {
                    pRetSit = RestituisciErroreSit(GetMessaggioDiValidazioneFallita("ID_IMMOBILE"), MessageCode.UIValidazione, false);
                }
            }
            catch (CatastoException ex)
            {
                pRetSit = RestituisciErroreSit(ex.Message, MessageCode.UIValidazione, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione di una unità immobiliare. Metodo: VerificaUI, modulo: Sit7Dbtl. " + ex.Message + "\r\n Query: " + _lastCommandText);
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
                    string sElem = GetSit7("CIVICO",  TipoQuery.Validazione);
                    if (!Init.Utils.StringChecker.IsStringEmpty(sElem))
                    {
                        pRetSit = new RetSit(true);
                        this.DataSit.Civico = sElem;
                    }
                    else
                    {
                        pRetSit = RestituisciErroreSit(GetMessaggioDiValidazioneFallita("CIVICO"), MessageCode.CivicoValidazione, false);
                    }
                }
                catch (CatastoException ex)
                {
                    pRetSit = RestituisciErroreSit(ex.Message, MessageCode.CivicoValidazione, false);
                }
                catch (Exception ex)
                {
                    throw new Exception("Errore durante la validazione di un civico. Metodo: VerificaCivico, modulo: SitCore. " + ex.Message + "\r\n Query: " + _lastCommandText);
                }
            }
            else
            {
                pRetSit = RestituisciErroreSit("Non è possibile validare il civico " + this.DataSit.Civico + " perchè non è un numero", MessageCode.CivicoValidazioneNumero, false);
            }

            return pRetSit;
        }


        protected override string GetCAP()
        {
            string sRetVal = "";

            try
            {
                sRetVal = GetSit7("CAP",  TipoQuery.Elenco);
            }
            catch (CatastoException ex)
            {
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di un CAP. Metodo: GetCAP, modulo: Sit7DBTL. " + ex.Message + "\r\n Query: " + _lastCommandText);
            }

            return sRetVal;
        }

        protected override RetSit VerificaCAP()
        {
            RetSit pRetSit;

            try
            {
                string sElem = GetSit7("CAP",  TipoQuery.Validazione);
                if (!Init.Utils.StringChecker.IsStringEmpty(sElem))
                {
                    pRetSit = new RetSit(true);
                    this.DataSit.CAP = sElem;
                }
                else
                {
                    pRetSit = RestituisciErroreSit(GetMessaggioDiValidazioneFallita("CAP"), MessageCode.CAPValidazione, false);
                }
            }
            catch (CatastoException ex)
            {
                pRetSit = RestituisciErroreSit(ex.Message, MessageCode.CAPValidazione, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione di un CAP. Metodo: VerificaCAP, modulo: SitCore. " + ex.Message + "\r\n Query: " + _lastCommandText);
            }

            return pRetSit;
        }

        protected override string GetCodCivico()
        {
            string sRetVal = "";

            try
            {
                sRetVal = GetSit7("CIV_KEY",  TipoQuery.Elenco);

                //Gestione particolare quando esistono più indirizzi con lo stesso civico ed esponente settato tranne per uno (esempio 1 e 1/A)
                //if (Init.Utils.StringChecker.IsStringEmpty(this.DataSit.Esponente) && !sRetVal.EndsWith("000") && !Init.Utils.StringChecker.IsStringEmpty(sRetVal))
                //{
                //    sRetVal = sRetVal.Remove(sRetVal.Length - 3) + "000";
                //}
            }
            catch (CatastoException ex)
            {
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di un codice civico. Metodo: GetCodCivico, modulo: SitCore. " + ex.Message + "\r\n Query: " + _lastCommandText);
            }

            return sRetVal;
        }

        protected override string GetCodFabbricato()
        {
            string sRetVal = "";
 
            try
            {
                sRetVal = GetSit7("ID_EDIFICIO",  TipoQuery.Elenco);
            }
            catch (CatastoException ex)
            {
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di un codice fabbricato. Metodo: GetCodFabbricato, modulo: SitCore. " + ex.Message + "\r\n Query: " + _lastCommandText);
            }

            return sRetVal;
        }

        protected override string GetCodVia()
        {
            string sRetVal = "";

            try
            {
                sRetVal = GetSit7("COD_VIA",  TipoQuery.Elenco);
            }
            catch (CatastoException ex)
            {
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di un codice via. Metodo: GetCodVia, modulo: SitCore. " + ex.Message + "\r\n Query: " + _lastCommandText);
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
				SitIntegrationService.NomiCampiSit.CodiceCivico
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

    }

    public enum TipoViste { Toponomastica, Catasto }

		
}
