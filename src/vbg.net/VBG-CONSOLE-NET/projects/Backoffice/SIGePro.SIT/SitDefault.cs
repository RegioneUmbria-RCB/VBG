using System;
using System.Data;
using System.Diagnostics;
using Init.SIGePro.Collection;
using Init.SIGePro.Manager;
using Init.SIGePro.Sit.Data;
using Init.SIGePro.Verticalizzazioni;
using PersonalLib2.Data;
using System.Runtime.InteropServices;
using System.Configuration;
using Init.SIGePro.Exceptions.SIT;
using Init.SIGePro.Sit.ValidazioneFormale;
using Init.SIGePro.Sit.Manager;

namespace Init.SIGePro.Sit
{
	/// <summary>
	/// Classe specifica per il Sit Core
	/// </summary>
	public class SIT_DEFAULT : SitBase
	{
		public SIT_DEFAULT()
			: base(new ValidazioneFormaleTramiteCodiceCivicoService())
		{
		}

		private string sConnectionString = "";
		private string sProvider = "";
		private string sCommandText = "";

		#region Utility
		public override void SetupVerticalizzazione()
		{
			GetParametriFromVertSITDEFAULT();
		}

		private void GetParametriFromVertSITDEFAULT()
		{
			try
			{
				VerticalizzazioneSitDefault pSitDefault;

				pSitDefault = new VerticalizzazioneSitDefault(this.IdComuneAlias, this.Software);

				if (pSitDefault.Attiva)
				{
					sConnectionString = pSitDefault.Connectionstring;
					sProvider = pSitDefault.Provider;

					ProviderType provider = (ProviderType)Enum.Parse(typeof(ProviderType), sProvider, false);
					InternalDatabaseConnection = new DataBase(sConnectionString, provider);
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
            string sValue = string.Empty;

            switch (sField)
            {
                case "G_SEZIONE":
                    sValue = "La sezione " + DataSit.Sezione + " non è valida per i dati inseriti";
                    break;
                case "G_FOGLIO":
                    sValue = "Il foglio " + DataSit.Foglio + " non è valido per i dati inseriti";
                    break;
                case "G_NUMERO":
                    sValue = "La particella " + DataSit.Particella + " non è valida per i dati inseriti";
                    break;
                case "CAP":
                    sValue = "Il CAP " + DataSit.CAP + " non è valido per i dati inseriti";
                    break;
                case "AE_CIVICO_CON_CIVKEY.NUMERO":
                    sValue = "Il civico " + DataSit.Civico + " non è valido per i dati inseriti";
                    break;
                case "AE_CIVICO_CON_CIVKEY.ESPONENTE":
                    sValue = "L'esponente " + DataSit.Esponente + " non è valido per i dati inseriti";
                    break;
                case "ID_EDIFICIO":
                    sValue = "Il fabbricato " + DataSit.Fabbricato + " non è valido per i dati inseriti";
                    break;
                case "SCALA":
                    sValue = "La scala " + DataSit.Scala + " non è valida per i dati inseriti";
                    break;
                case "AE_INTERNO_CON_INTKEY.NUMERO":
                    sValue = "L'interno " + DataSit.Interno + " non è valido per i dati inseriti";
                    break;
                case "AE_INTERNO_CON_INTKEY.ESPONENTE":
                    sValue = "L'esponente dell'interno " + DataSit.EsponenteInterno + " non è valido per i dati inseriti";
                    break;
            }

            return sValue;
		}

		private string GetMessageList(string sField)
		{
			string sValue = string.Empty;

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

		private string GetAeCivicoConCivkey(string sField, IDataReader pDataReader, TipoQuery eTipoQuery)
		{
			string sRetVal = string.Empty;

			if ((sField == "CAP") || (sField == "COD_VIA"))
			{
				if (string.IsNullOrEmpty(DataSit.Scala) && string.IsNullOrEmpty(DataSit.Interno) && string.IsNullOrEmpty(DataSit.EsponenteInterno))
					sRetVal = GetElemento(sField, "AE_CIVICO_CON_CIVKEY", pDataReader, eTipoQuery);
				else
					sRetVal = GetElemento(sField, "AE_CIVICO_CON_CIVKEY,AE_INTERNO_CON_INTKEY", pDataReader, eTipoQuery);
			}
			else
			{
				if (string.IsNullOrEmpty(DataSit.TipoCatasto))
				{
					if (string.IsNullOrEmpty(DataSit.Scala) && string.IsNullOrEmpty(DataSit.Interno) && string.IsNullOrEmpty(DataSit.EsponenteInterno))
						sRetVal = GetElemento(sField, "AE_CIVICO_CON_CIVKEY", pDataReader, eTipoQuery);
					else
						sRetVal = GetElemento(sField, "AE_CIVICO_CON_CIVKEY,AE_INTERNO_CON_INTKEY", pDataReader, eTipoQuery);
				}
				else
				{
					if (this.DataSit.TipoCatasto == "F")
					{
						if (string.IsNullOrEmpty(DataSit.Scala) && string.IsNullOrEmpty(DataSit.Interno) && string.IsNullOrEmpty(DataSit.EsponenteInterno))
							sRetVal = GetElemento(sField, "AE_CIVICO_CON_CIVKEY", pDataReader, eTipoQuery);
						else
							sRetVal = GetElemento(sField, "AE_CIVICO_CON_CIVKEY,AE_INTERNO_CON_INTKEY", pDataReader, eTipoQuery);
					}
					else
					{
						if ((sField == "G_SEZIONE") || (sField == "G_FOGLIO") || (sField == "G_NUMERO"))
						{
							if (IsCampiToponomasticaImmobileVuoti())
								sRetVal = GetElemento(sField, "AE_CIVICO_CON_CIVKEY", pDataReader, eTipoQuery);
							else
								throw new CatastoException(GetMessageValidate("AE_CIVICO_CON_CIVKEY." + sField));
						}
						else
							throw new CatastoException(GetMessageValidate("AE_CIVICO_CON_CIVKEY." + sField));
					}
				}
			}

			return sRetVal;
		}

		private string GetAeInternoConIntkey(string sField, IDataReader pDataReader, TipoQuery eTipoQuery)
		{
			string sRetVal = string.Empty;

			if (string.IsNullOrEmpty(DataSit.TipoCatasto))
			{
				if (string.IsNullOrEmpty(DataSit.CodVia) && string.IsNullOrEmpty(DataSit.Civico) && string.IsNullOrEmpty(DataSit.Esponente) && string.IsNullOrEmpty(DataSit.CAP) && string.IsNullOrEmpty(DataSit.Fabbricato) && string.IsNullOrEmpty(DataSit.Sezione) && string.IsNullOrEmpty(DataSit.Foglio) && string.IsNullOrEmpty(DataSit.Particella))
					sRetVal = GetElemento(sField, "AE_INTERNO_CON_INTKEY", pDataReader, eTipoQuery);
				else
					sRetVal = GetElemento(sField, "AE_INTERNO_CON_INTKEY,AE_CIVICO_CON_CIVKEY", pDataReader, eTipoQuery);
			}
			else
			{
				if (this.DataSit.TipoCatasto == "F")
				{
					if (string.IsNullOrEmpty(DataSit.CodVia) && string.IsNullOrEmpty(DataSit.Civico) && string.IsNullOrEmpty(DataSit.Esponente) && string.IsNullOrEmpty(DataSit.CAP) && string.IsNullOrEmpty(DataSit.Fabbricato) && string.IsNullOrEmpty(DataSit.Sezione) && string.IsNullOrEmpty(DataSit.Foglio) && string.IsNullOrEmpty(DataSit.Particella))
						sRetVal = GetElemento(sField, "AE_INTERNO_CON_INTKEY", pDataReader, eTipoQuery);
					else
						sRetVal = GetElemento(sField, "AE_INTERNO_CON_INTKEY,AE_CIVICO_CON_CIVKEY", pDataReader, eTipoQuery);
				}
				else
					throw new CatastoException(GetMessageValidate("AE_INTERNO_CON_INTKEY." + sField));
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
                if (string.IsNullOrEmpty(DataSit.TipoCatasto))
                    throw new CatastoException(GetMessageList("AE_CIVICO_CON_CIVKEY." + sField));
                else
                {
                    if (this.DataSit.TipoCatasto == "F")
                    {
                        if (string.IsNullOrEmpty(DataSit.Scala) && string.IsNullOrEmpty(DataSit.Interno) && string.IsNullOrEmpty(DataSit.EsponenteInterno))
                            pRetSit = GetElenco(sField, "AE_CIVICO_CON_CIVKEY", pDataReader);
                        else
                            pRetSit = GetElenco(sField, "AE_CIVICO_CON_CIVKEY,AE_INTERNO_CON_INTKEY", pDataReader);
                    }
                    else
                    {
                        if ((sField == "G_SEZIONE") || (sField == "G_FOGLIO") || (sField == "G_NUMERO"))
                        {
                            if (IsCampiToponomasticaImmobileVuoti())
                                pRetSit = GetElenco(sField, "AE_CIVICO_CON_CIVKEY", pDataReader);
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
                if (string.IsNullOrEmpty(DataSit.TipoCatasto) || (this.DataSit.TipoCatasto == "F"))
                {
                    if (string.IsNullOrEmpty(DataSit.Scala) && string.IsNullOrEmpty(DataSit.Interno) && string.IsNullOrEmpty(DataSit.EsponenteInterno))
                        pRetSit = GetElenco(sField, "AE_CIVICO_CON_CIVKEY", pDataReader);
                    else
                        pRetSit = GetElenco(sField, "AE_CIVICO_CON_CIVKEY,AE_INTERNO_CON_INTKEY", pDataReader);
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
            if (string.IsNullOrEmpty(DataSit.TipoCatasto) || (this.DataSit.TipoCatasto == "F"))
            {
                if (string.IsNullOrEmpty(DataSit.CodVia) && string.IsNullOrEmpty(DataSit.Civico) && string.IsNullOrEmpty(DataSit.Esponente) && string.IsNullOrEmpty(DataSit.CAP) && string.IsNullOrEmpty(DataSit.Fabbricato) && string.IsNullOrEmpty(DataSit.Sezione) && string.IsNullOrEmpty(DataSit.Foglio) && string.IsNullOrEmpty(DataSit.Particella))
                    pRetSit = GetElenco(sField, "AE_INTERNO_CON_INTKEY", pDataReader);
                else
                    pRetSit = GetElenco(sField, "AE_INTERNO_CON_INTKEY,AE_CIVICO_CON_CIVKEY", pDataReader);
            }
            else
                pRetSit = new RetSit(true);
            //}

            return pRetSit;
		}

		private string GetQuery(string sField, string sTableName, TipoQuery eTipoQuery)
		{
			//Codice per ottenere sempre un codice via valido
			if (!string.IsNullOrEmpty(DataSit.CodVia))
			{
				if ((DataSit.CodVia != "1") && (DataSit.CodVia != "2") && (DataSit.CodVia != "3"))
				{
					//Random pRnd = new Random();
					//this.DataSit.CodVia = pRnd.Next(1, 4).ToString();
					throw new Exception("Il sit di default gestisce solamente i codici viario 1,2 e 3. Modificare il codice viario sullo stradario");
				}
			}

			string sQuery = string.Empty;

			switch (sTableName)
			{
				case "AE_CIVICO_CON_CIVKEY":
					switch (eTipoQuery)
					{
						case TipoQuery.Elenco:
							sQuery = "Select distinct " + sTableName + "." + sField + " from AE_CIVICO_CON_CIVKEY where " +
								(((!string.IsNullOrEmpty(this.DataSit.CodVia)) && (sField != "COD_VIA")) ? "RTRIM(AE_CIVICO_CON_CIVKEY.COD_VIA)" + " = " + RightTrim(this.DataSit.CodVia) + " and " : "") +
								(((!string.IsNullOrEmpty(this.DataSit.Civico)) && (sField != "NUMERO")) ? "RTRIM(AE_CIVICO_CON_CIVKEY.NUMERO)" + " = " + RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Civico) ? this.DataSit.Civico : "-1") + " and " : "") +
								(((!string.IsNullOrEmpty(this.DataSit.Esponente)) && (sField != "ESPONENTE")) ? "RTRIM(AE_CIVICO_CON_CIVKEY.ESPONENTE)" + " = '" + RightTrim(this.DataSit.Esponente) + "' and " : "") +
								(((!string.IsNullOrEmpty(this.DataSit.CodCivico)) && (sField != "CIVKEY")) ? "RTRIM(AE_CIVICO_CON_CIVKEY.CIVKEY)" + " = '" + RightTrim(this.DataSit.CodCivico) + "' and " : "") +
								(((!string.IsNullOrEmpty(this.DataSit.Fabbricato)) && (sField != "ID_EDIFICIO")) ? "RTRIM(AE_CIVICO_CON_CIVKEY.ID_EDIFICIO)" + " = " + RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Fabbricato) ? this.DataSit.Fabbricato : "-1") + " and " : "") +
								(((!string.IsNullOrEmpty(this.DataSit.Sezione)) && (sField != "G_SEZIONE")) ? "RTRIM(AE_CIVICO_CON_CIVKEY.G_SEZIONE)" + " = '" + RightTrim(this.DataSit.Sezione) + "' and " : "") +
								(((!string.IsNullOrEmpty(this.DataSit.Foglio)) && (sField != "G_FOGLIO")) ? "RTRIM(AE_CIVICO_CON_CIVKEY.G_FOGLIO)" + " = '" + RightTrim(this.DataSit.Foglio) + "' and " : "") +
								(((!string.IsNullOrEmpty(this.DataSit.Particella)) && (sField != "G_NUMERO")) ? "RTRIM(AE_CIVICO_CON_CIVKEY.G_NUMERO)" + " = '" + RightTrim(this.DataSit.Particella) + "' and " : "") +
								"RTRIM(AE_CIVICO_CON_CIVKEY.DATA_FINE)" + " is null order by " + sTableName + "." + sField + "";
							break;
						case TipoQuery.Validazione:
							sQuery = "Select distinct " + sTableName + "." + sField + " from AE_CIVICO_CON_CIVKEY where " +
								(((!string.IsNullOrEmpty(this.DataSit.CodVia))) ? "RTRIM(AE_CIVICO_CON_CIVKEY.COD_VIA)" + " = " + RightTrim(this.DataSit.CodVia) + " and " : "") +
								(((!string.IsNullOrEmpty(this.DataSit.Civico))) ? "RTRIM(AE_CIVICO_CON_CIVKEY.NUMERO)" + " = " + RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Civico) ? this.DataSit.Civico : "-1") + " and " : "") +
								(((!string.IsNullOrEmpty(this.DataSit.Esponente))) ? "RTRIM(AE_CIVICO_CON_CIVKEY.ESPONENTE)" + " = '" + RightTrim(this.DataSit.Esponente) + "' and " : "") +
								(((!string.IsNullOrEmpty(this.DataSit.CodCivico))) ? "RTRIM(AE_CIVICO_CON_CIVKEY.CIVKEY)" + " = '" + RightTrim(this.DataSit.CodCivico) + "' and " : "") +
								(((!string.IsNullOrEmpty(this.DataSit.Fabbricato))) ? "RTRIM(AE_CIVICO_CON_CIVKEY.ID_EDIFICIO)" + " = " + RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Fabbricato) ? this.DataSit.Fabbricato : "-1") + " and " : "") +
								(((!string.IsNullOrEmpty(this.DataSit.Sezione))) ? "RTRIM(AE_CIVICO_CON_CIVKEY.G_SEZIONE)" + " = '" + RightTrim(this.DataSit.Sezione) + "' and " : "") +
								(((!string.IsNullOrEmpty(this.DataSit.Foglio))) ? "RTRIM(AE_CIVICO_CON_CIVKEY.G_FOGLIO)" + " = '" + RightTrim(this.DataSit.Foglio) + "' and " : "") +
								(((!string.IsNullOrEmpty(this.DataSit.Particella))) ? "RTRIM(AE_CIVICO_CON_CIVKEY.G_NUMERO)" + " = '" + RightTrim(this.DataSit.Particella) + "' and " : "") +
								"RTRIM(AE_CIVICO_CON_CIVKEY.DATA_FINE)" + " is null order by " + sTableName + "." + sField;
							break;
					}
					break;
				case "AE_INTERNO_CON_INTKEY":
					switch (eTipoQuery)
					{
						case TipoQuery.Elenco:
							sQuery = "Select distinct " + sTableName + "." + sField + " from AE_INTERNO_CON_INTKEY where " +
								(((!string.IsNullOrEmpty(this.DataSit.Scala)) && (sField != "SCALA")) ? "RTRIM(AE_INTERNO_CON_INTKEY.SCALA)" + " = '" + RightTrim(this.DataSit.Scala) + "' and " : "") +
								(((!string.IsNullOrEmpty(this.DataSit.Interno)) && (sField != "NUMERO")) ? "RTRIM(AE_INTERNO_CON_INTKEY.NUMERO)" + " = " + RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Interno) ? this.DataSit.Interno : "-1") + " and " : "") +
								(((!string.IsNullOrEmpty(this.DataSit.EsponenteInterno)) && (sField != "ESPONENTE")) ? "RTRIM(AE_INTERNO_CON_INTKEY.ESPONENTE)" + " = '" + RightTrim(this.DataSit.EsponenteInterno) + "' and " : "") +
								(((!string.IsNullOrEmpty(this.DataSit.CodCivico)) && (sField != "CIVKEY")) ? "RTRIM(AE_INTERNO_CON_INTKEY.CIVKEY)" + " = '" + RightTrim(this.DataSit.CodCivico) + "' " : "");
							break;
						case TipoQuery.Validazione:
							sQuery = "Select distinct " + sTableName + "." + sField + " from AE_INTERNO_CON_INTKEY where " +
								(((!string.IsNullOrEmpty(this.DataSit.Scala))) ? "RTRIM(AE_INTERNO_CON_INTKEY.SCALA)" + " = '" + RightTrim(this.DataSit.Scala) + "' and " : "") +
								(((!string.IsNullOrEmpty(this.DataSit.Interno))) ? "RTRIM(AE_INTERNO_CON_INTKEY.NUMERO)" + " = " + RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Interno) ? this.DataSit.Interno : "-1") + " and " : "") +
								(((!string.IsNullOrEmpty(this.DataSit.EsponenteInterno))) ? "RTRIM(AE_INTERNO_CON_INTKEY.ESPONENTE)" + " = '" + RightTrim(this.DataSit.EsponenteInterno) + "' and " : "") +
								(((!string.IsNullOrEmpty(this.DataSit.CodCivico))) ? "RTRIM(AE_INTERNO_CON_INTKEY.CIVKEY)" + " = '" + RightTrim(this.DataSit.CodCivico) + "' " : "");
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
								(((!string.IsNullOrEmpty(this.DataSit.CodVia)) && (sField != "COD_VIA")) ? "RTRIM(AE_CIVICO_CON_CIVKEY.COD_VIA)" + " = " + RightTrim(this.DataSit.CodVia) + " and " : "") +
								(((!string.IsNullOrEmpty(this.DataSit.Civico)) && (sField != "NUMERO")) ? "RTRIM(AE_CIVICO_CON_CIVKEY.NUMERO)" + " = " + RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Civico) ? this.DataSit.Civico : "-1") + " and " : "") +
								(((!string.IsNullOrEmpty(this.DataSit.Esponente)) && (sField != "ESPONENTE")) ? "RTRIM(AE_CIVICO_CON_CIVKEY.ESPONENTE)" + " = '" + RightTrim(this.DataSit.Esponente) + "' and " : "") +
								(((!string.IsNullOrEmpty(this.DataSit.CodCivico)) && (sField != "CIVKEY")) ? "RTRIM(AE_CIVICO_CON_CIVKEY.CIVKEY)" + " = '" + RightTrim(this.DataSit.CodCivico) + "' and " : "") +
								(((!string.IsNullOrEmpty(this.DataSit.CAP)) && (sField != "CAP")) ? "RTRIM(AE_CIVICO_CON_CIVKEY.CAP)" + " = " + RightTrim(this.DataSit.CAP) + " and " : "") +
								(((!string.IsNullOrEmpty(this.DataSit.Fabbricato)) && (sField != "ID_EDIFICIO")) ? "RTRIM(AE_CIVICO_CON_CIVKEY.ID_EDIFICIO)" + " = " + RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Fabbricato) ? this.DataSit.Fabbricato : "-1") + " and " : "") +
								(((!string.IsNullOrEmpty(this.DataSit.Sezione)) && (sField != "G_SEZIONE")) ? "RTRIM(AE_CIVICO_CON_CIVKEY.G_SEZIONE)" + " = '" + RightTrim(this.DataSit.Sezione) + "' and " : "") +
								(((!string.IsNullOrEmpty(this.DataSit.Foglio)) && (sField != "G_FOGLIO")) ? "RTRIM(AE_CIVICO_CON_CIVKEY.G_FOGLIO)" + " = '" + RightTrim(this.DataSit.Foglio) + "' and " : "") +
								(((!string.IsNullOrEmpty(this.DataSit.Particella)) && (sField != "G_NUMERO")) ? "RTRIM(AE_CIVICO_CON_CIVKEY.G_NUMERO)" + " = '" + RightTrim(this.DataSit.Particella) + "' and " : "") +
								(((!string.IsNullOrEmpty(this.DataSit.Scala)) && (sField != "SCALA")) ? "RTRIM(AE_INTERNO_CON_INTKEY.SCALA)" + " = '" + RightTrim(this.DataSit.Scala) + "' and " : "") +
								(((!string.IsNullOrEmpty(this.DataSit.Interno)) && (sField != "NUMERO")) ? "RTRIM(AE_INTERNO_CON_INTKEY.NUMERO)" + " = " + RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Interno) ? this.DataSit.Interno : "-1") + " and " : "") +
								(((!string.IsNullOrEmpty(this.DataSit.EsponenteInterno)) && (sField != "ESPONENTE")) ? "RTRIM(AE_INTERNO_CON_INTKEY.ESPONENTE)" + " = '" + RightTrim(this.DataSit.EsponenteInterno) + "' and " : "") +
								"RTRIM(AE_CIVICO_CON_CIVKEY.DATA_FINE)" + " is null order by " + sTableName.Split(new Char[] { ',' })[0] + "." + sField;
							break;
						case TipoQuery.Validazione:
							sQuery = "Select distinct " + sTableName.Split(new Char[] { ',' })[0] + "." + sField + " from AE_CIVICO_CON_CIVKEY,AE_INTERNO_CON_INTKEY where " +
								"AE_CIVICO_CON_CIVKEY.CIVKEY = AE_INTERNO_CON_INTKEY.CIVKEY and " +
								(((!string.IsNullOrEmpty(this.DataSit.CodVia))) ? "RTRIM(AE_CIVICO_CON_CIVKEY.COD_VIA)" + " = " + RightTrim(this.DataSit.CodVia) + " and " : "") +
								(((!string.IsNullOrEmpty(this.DataSit.Civico))) ? "RTRIM(AE_CIVICO_CON_CIVKEY.NUMERO)" + " = " + RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Civico) ? this.DataSit.Civico : "-1") + " and " : "") +
								(((!string.IsNullOrEmpty(this.DataSit.Esponente))) ? "RTRIM(AE_CIVICO_CON_CIVKEY.ESPONENTE)" + " = '" + RightTrim(this.DataSit.Esponente) + "' and " : "") +
								(((!string.IsNullOrEmpty(this.DataSit.CodCivico))) ? "RTRIM(AE_CIVICO_CON_CIVKEY.CIVKEY)" + " = '" + RightTrim(this.DataSit.CodCivico) + "' and " : "") +
								(((!string.IsNullOrEmpty(this.DataSit.CAP))) ? "RTRIM(AE_CIVICO_CON_CIVKEY.CAP)" + " = '" + RightTrim(this.DataSit.CAP) + " and " : "") +
								(((!string.IsNullOrEmpty(this.DataSit.Fabbricato))) ? "RTRIM(AE_CIVICO_CON_CIVKEY.ID_EDIFICIO)" + " = " + RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Fabbricato) ? this.DataSit.Fabbricato : "-1") + " and " : "") +
								(((!string.IsNullOrEmpty(this.DataSit.Sezione))) ? "RTRIM(AE_CIVICO_CON_CIVKEY.G_SEZIONE)" + " = '" + RightTrim(this.DataSit.Sezione) + "' and " : "") +
								(((!string.IsNullOrEmpty(this.DataSit.Foglio))) ? "RTRIM(AE_CIVICO_CON_CIVKEY.G_FOGLIO)" + " = '" + RightTrim(this.DataSit.Foglio) + "' and " : "") +
								(((!string.IsNullOrEmpty(this.DataSit.Particella))) ? "RTRIM(AE_CIVICO_CON_CIVKEY.G_NUMERO)" + " = '" + RightTrim(this.DataSit.Particella) + "' and " : "") +
								(((!string.IsNullOrEmpty(this.DataSit.Scala))) ? "RTRIM(AE_INTERNO_CON_INTKEY.SCALA)" + " = '" + RightTrim(this.DataSit.Scala) + "' and " : "") +
								(((!string.IsNullOrEmpty(this.DataSit.Interno))) ? "RTRIM(AE_INTERNO_CON_INTKEY.NUMERO)" + " = " + RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Interno) ? this.DataSit.Interno : "-1") + " and " : "") +
								(((!string.IsNullOrEmpty(this.DataSit.EsponenteInterno))) ? "RTRIM(AE_INTERNO_CON_INTKEY.ESPONENTE)" + " = '" + RightTrim(this.DataSit.EsponenteInterno) + "' and " : "") +
								"RTRIM(AE_CIVICO_CON_CIVKEY.DATA_FINE)" + " is null order by " + sTableName.Split(new Char[] { ',' })[0] + "." + sField;
							break;
					}
					break;
			}

			return sQuery;
		}

		private string GetElemento(string sField, string sTableName, IDataReader pDataReader, TipoQuery eTipoQuery)
		{
			string sRetVal = "";
			int iCount = 0;
			EnsureConnectionIsOpen();
			sCommandText = GetQuery(sField, sTableName, eTipoQuery);

			IDbCommand pCommand = InternalDatabaseConnection.CreateCommand(sCommandText);
			pDataReader = pCommand.ExecuteReader();

			while (pDataReader.Read())
			{
				string sDato = RightTrim(pDataReader[sField].ToString());

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
			RetSit pRetSit = new RetSit(true);
			EnsureConnectionIsOpen();
			sCommandText = GetQuery(sField, sTableName, TipoQuery.Elenco);

			IDbCommand pCommand = InternalDatabaseConnection.CreateCommand(sCommandText);
			pDataReader = pCommand.ExecuteReader();

			while (pDataReader.Read())
			{
				string sDato = RightTrim(pDataReader[sField].ToString());

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
				if ((!Init.Utils.StringChecker.IsStringEmpty(this.DataSit.Fabbricato)) || (!Init.Utils.StringChecker.IsStringEmpty(this.DataSit.CodVia)))
				{
					pRetSit = ElencoAeCivicoConCivkey("NUMERO", pDataReader);
				}
				else
				{
					pRetSit = RestituisciErroreSit(GetMessageList("AE_CIVICO_CON_CIVKEY.NUMERO"), MessageCode.ElencoCivici, false);
				}
			}
			catch (CatastoException ex)
			{
				pRetSit = RestituisciErroreSit(ex.Message, MessageCode.ElencoCivici, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione dell'elenco dei civici. Metodo: ElencoCivici, modulo: SitDefault. " + ex.Message + "\r\n Query: " + sCommandText);
			}
			finally
			{
				if (pDataReader != null)
					pDataReader.Close();
				InternalDatabaseConnection.Connection.Close();
			}

			return pRetSit;
		}
				
		public override RetSit ElencoEsponenti()
		{
			IDataReader pDataReader = null;
			RetSit pRetSit;

			try
			{
				if (!Init.Utils.StringChecker.IsStringEmpty(this.DataSit.Civico) && ((!Init.Utils.StringChecker.IsStringEmpty(this.DataSit.Fabbricato)) || (!Init.Utils.StringChecker.IsStringEmpty(this.DataSit.CodVia))))
				{
					pRetSit = ElencoAeCivicoConCivkey("ESPONENTE", pDataReader);
				}
				else
				{
					pRetSit = RestituisciErroreSit(GetMessageList("AE_CIVICO_CON_CIVKEY.ESPONENTE"), MessageCode.ElencoEsponenti, false);
				}
			}
			catch (CatastoException ex)
			{
				pRetSit = RestituisciErroreSit(ex.Message, MessageCode.ElencoEsponenti, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione dell'elenco degli esponenti. Metodo: ElencoEsponenti, modulo: SitDefault. " + ex.Message + "\r\n Query: " + sCommandText);
			}
			finally
			{
				if (pDataReader != null)
					pDataReader.Close();
				InternalDatabaseConnection.Connection.Close();
			}

			return pRetSit;
		}

		public override RetSit ElencoColori()
		{
			RetSit pRetSit = RestituisciErroreSit(MessageCode.ElencoColori, true);
			return pRetSit;
		}

		public override RetSit ElencoScale()
		{
			IDataReader pDataReader = null;
			RetSit pRetSit;

			try
			{
				if (!Init.Utils.StringChecker.IsStringEmpty(this.DataSit.CodCivico) || (!Init.Utils.StringChecker.IsStringEmpty(this.DataSit.Fabbricato) && !Init.Utils.StringChecker.IsStringEmpty(this.DataSit.Civico) && !Init.Utils.StringChecker.IsStringEmpty(this.DataSit.Esponente)))
				{
					pRetSit = ElencoAeInternoConIntkey("SCALA", pDataReader);
				}
				else
				{
					pRetSit = RestituisciErroreSit(GetMessageList("SCALA"), MessageCode.ElencoScale, false);
				}
			}
			catch (CatastoException ex)
			{
				pRetSit = RestituisciErroreSit(ex.Message, MessageCode.ElencoScale, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione dell'elenco delle scale. Metodo: ElencoScale, modulo: SitDefault. " + ex.Message + "\r\n Query: " + sCommandText);
			}
			finally
			{
				if (pDataReader != null)
					pDataReader.Close();
				InternalDatabaseConnection.Connection.Close();
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
					pRetSit = ElencoAeInternoConIntkey("NUMERO", pDataReader);
				}
				else
				{
					pRetSit = RestituisciErroreSit(GetMessageList("AE_INTERNO_CON_INTKEY.NUMERO"), MessageCode.ElencoInterni, false);
				}
			}
			catch (CatastoException ex)
			{
				pRetSit = RestituisciErroreSit(ex.Message, MessageCode.ElencoInterni, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione dell'elenco degli interni. Metodo: ElencoInterni, modulo: SitDefault. " + ex.Message + "\r\n Query: " + sCommandText);
			}
			finally
			{
				if (pDataReader != null)
					pDataReader.Close();
				InternalDatabaseConnection.Connection.Close();
			}

			return pRetSit;
		}

		public override RetSit ElencoEsponentiInterno()
		{
			IDataReader pDataReader = null;
			RetSit pRetSit;

			try
			{
				if ((!Init.Utils.StringChecker.IsStringEmpty(this.DataSit.CodCivico) && !Init.Utils.StringChecker.IsStringEmpty(this.DataSit.Interno)) || (!Init.Utils.StringChecker.IsStringEmpty(this.DataSit.Fabbricato) && !Init.Utils.StringChecker.IsStringEmpty(this.DataSit.Civico) && !Init.Utils.StringChecker.IsStringEmpty(this.DataSit.Esponente) && !Init.Utils.StringChecker.IsStringEmpty(this.DataSit.Interno)))
				{
					pRetSit = ElencoAeInternoConIntkey("ESPONENTE", pDataReader);
				}
				else
				{
					pRetSit = RestituisciErroreSit(GetMessageList("AE_INTERNO_CON_INTKEY.NUMERO"), MessageCode.ElencoEsponentiInterno, false);
				}
			}
			catch (CatastoException ex)
			{
				pRetSit = RestituisciErroreSit(ex.Message, MessageCode.ElencoEsponentiInterno, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione dell'elenco degli interni dell'esponente. Metodo: ElencoEsponentiInterno, modulo: SitDefault. " + ex.Message + "\r\n Query: " + sCommandText);
			}
			finally
			{
				if (pDataReader != null)
					pDataReader.Close();
				InternalDatabaseConnection.Connection.Close();
			}

			return pRetSit;
		}
		
		public override RetSit ElencoFabbricati()
		{
			IDataReader pDataReader = null;
			RetSit pRetSit;

			try
			{
				if ((!Init.Utils.StringChecker.IsStringEmpty(this.DataSit.CodVia)) || (!Init.Utils.StringChecker.IsStringEmpty(this.DataSit.Sezione) && !Init.Utils.StringChecker.IsStringEmpty(this.DataSit.Foglio) && !Init.Utils.StringChecker.IsStringEmpty(this.DataSit.Particella)))
				{
					pRetSit = ElencoAeCivicoConCivkey("ID_EDIFICIO", pDataReader);
				}
				else
				{
					pRetSit = RestituisciErroreSit(GetMessageList("ID_EDIFICIO"), MessageCode.ElencoFabbricati, false);
				}
			}
			catch (CatastoException ex)
			{
				pRetSit = RestituisciErroreSit(ex.Message, MessageCode.ElencoFabbricati, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione dell'elenco dei fabbricati. Metodo: ElencoFabbricati, modulo: SitDefault. " + ex.Message + "\r\n Query: " + sCommandText);
			}
			finally
			{
				if (pDataReader != null)
					pDataReader.Close();
				InternalDatabaseConnection.Connection.Close();
			}

			return pRetSit;
		}

		public override RetSit ElencoSezioni()
		{
			IDataReader pDataReader = null;
			RetSit pRetSit;

			try
			{
				pRetSit = ElencoAeCivicoConCivkey("G_SEZIONE", pDataReader);

			}
			catch (CatastoException ex)
			{
				pRetSit = RestituisciErroreSit(ex.Message, MessageCode.ElencoSezioni, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione dell'elenco delle sezioni. Metodo: ElencoSezioni, modulo: SitDefault. " + ex.Message + "\r\n Query: " + sCommandText);
			}
			finally
			{
				if (pDataReader != null)
					pDataReader.Close();
				InternalDatabaseConnection.Connection.Close();
			}

			return pRetSit;
		}

		public override RetSit ElencoFogli()
		{
			IDataReader pDataReader = null;
			RetSit pRetSit;

			try
			{
				if ((!Init.Utils.StringChecker.IsStringEmpty(this.DataSit.Fabbricato)) || (!Init.Utils.StringChecker.IsStringEmpty(this.DataSit.Sezione)))
				{
					pRetSit = ElencoAeCivicoConCivkey("G_FOGLIO", pDataReader);
				}
				else
				{
					pRetSit = RestituisciErroreSit(GetMessageList("G_FOGLIO"), MessageCode.ElencoFogli, false);
				}
			}
			catch (CatastoException ex)
			{
				pRetSit = RestituisciErroreSit(ex.Message, MessageCode.ElencoFogli, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione dell'elenco dei fogli. Metodo: ElencoFogli, modulo: SitDefault. " + ex.Message + "\r\n Query: " + sCommandText);
			}
			finally
			{
				if (pDataReader != null)
					pDataReader.Close();
				InternalDatabaseConnection.Connection.Close();
			}

			return pRetSit;
		}

		public override RetSit ElencoParticelle()
		{
			IDataReader pDataReader = null;
			RetSit pRetSit;

			try
			{
				if ((!Init.Utils.StringChecker.IsStringEmpty(this.DataSit.Fabbricato)) || (!Init.Utils.StringChecker.IsStringEmpty(this.DataSit.Sezione) && !Init.Utils.StringChecker.IsStringEmpty(this.DataSit.Foglio)))
				{
					pRetSit = ElencoAeCivicoConCivkey("G_NUMERO", pDataReader);
				}
				else
				{
					pRetSit = RestituisciErroreSit(GetMessageList("G_NUMERO"), MessageCode.ElencoParticelle, false);

				}
			}
			catch (CatastoException ex)
			{
				pRetSit = RestituisciErroreSit(ex.Message, MessageCode.ElencoParticelle, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione dell'elenco delle particelle. Metodo: ElencoParticelle, modulo: SitDefault. " + ex.Message + "\r\n Query: " + sCommandText);
			}
			finally
			{
				if (pDataReader != null)
					pDataReader.Close();
				InternalDatabaseConnection.Connection.Close();
			}

			return pRetSit;
		}

		
		#endregion

		#region Metodi per la verifica e la restituzione di un singolo elemento catastale o facente parte dell'indirizzo
		
		protected override string GetEsponente()
		{
			string sRetVal = "";
			IDataReader pDataReader = null;

			try
			{
				sRetVal = GetAeCivicoConCivkey("ESPONENTE", pDataReader, TipoQuery.Elenco);
			}
			catch (CatastoException ex)
			{
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione di un esponente. Metodo: GetEsponente, modulo: SitDefault. " + ex.Message + "\r\n Query: " + sCommandText);
			}
			finally
			{
				if (pDataReader != null)
					pDataReader.Close();
				InternalDatabaseConnection.Connection.Close();
			}

			return sRetVal;
		}

		protected override RetSit VerificaEsponente()
		{
			RetSit pRetSit;
			IDataReader pDataReader = null;

			try
			{
				string sElem = GetAeCivicoConCivkey("ESPONENTE", pDataReader, TipoQuery.Validazione);
				if (!Init.Utils.StringChecker.IsStringEmpty(sElem))
				{
					pRetSit = new RetSit(true);
					this.DataSit.Esponente = sElem;
				}
				else
				{
					pRetSit = RestituisciErroreSit(GetMessageValidate("AE_CIVICO_CON_CIVKEY.ESPONENTE"), MessageCode.EsponenteValidazione, false);
				}
			}
			catch (CatastoException ex)
			{
				pRetSit = RestituisciErroreSit(ex.Message, MessageCode.EsponenteValidazione, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la validazione di un esponente. Metodo: VerificaEsponente, modulo: SitDefault. " + ex.Message + "\r\n Query: " + sCommandText);
			}
			finally
			{
				if (pDataReader != null)
					pDataReader.Close();
				InternalDatabaseConnection.Connection.Close();
			}

			return pRetSit;
		}

		protected override string GetScala()
		{
			string sRetVal = "";
			IDataReader pDataReader = null;

			try
			{
				sRetVal = GetAeInternoConIntkey("SCALA", pDataReader, TipoQuery.Elenco);
			}
			catch (CatastoException ex)
			{
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione di una scala. Metodo: GetScala, modulo: SitDefault. " + ex.Message + "\r\n Query: " + sCommandText);
			}
			finally
			{
				if (pDataReader != null)
					pDataReader.Close();
				InternalDatabaseConnection.Connection.Close();
			}

			return sRetVal;
		}

		protected override RetSit VerificaScala()
		{
			RetSit pRetSit;
			IDataReader pDataReader = null;

			try
			{
				string sElem = GetAeInternoConIntkey("SCALA", pDataReader, TipoQuery.Validazione);
				if (!Init.Utils.StringChecker.IsStringEmpty(sElem))
				{
					pRetSit = new RetSit(true);
					this.DataSit.Scala = sElem;
				}
				else
				{
					pRetSit = RestituisciErroreSit(GetMessageValidate("SCALA"), MessageCode.ScalaValidazione, false);
				}
			}
			catch (CatastoException ex)
			{
				pRetSit = RestituisciErroreSit(ex.Message, MessageCode.ScalaValidazione, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la validazione di una scala. Metodo: VerificaScala, modulo: SitDefault. " + ex.Message + "\r\n Query: " + sCommandText);
			}
			finally
			{
				if (pDataReader != null)
					pDataReader.Close();
				InternalDatabaseConnection.Connection.Close();
			}

			return pRetSit;
		}

		protected override string GetInterno()
		{
			string sRetVal = "";
			IDataReader pDataReader = null;

			try
			{
				sRetVal = GetAeInternoConIntkey("NUMERO", pDataReader, TipoQuery.Elenco);
			}
			catch (CatastoException ex)
			{
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione di un interno. Metodo: GetInterno, modulo: SitDefault. " + ex.Message + "\r\n Query: " + sCommandText);
			}
			finally
			{
				if (pDataReader != null)
					pDataReader.Close();
				InternalDatabaseConnection.Connection.Close();
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
					string sElem = GetAeInternoConIntkey("NUMERO", pDataReader, TipoQuery.Validazione);
					if (!Init.Utils.StringChecker.IsStringEmpty(sElem))
					{
						pRetSit = new RetSit(true);
						this.DataSit.Interno = sElem;
					}
					else
					{
						pRetSit = RestituisciErroreSit(GetMessageValidate("AE_INTERNO_CON_INTKEY.NUMERO"), MessageCode.InternoValidazione, false);
					}
				}
				catch (CatastoException ex)
				{
					pRetSit = RestituisciErroreSit(ex.Message, MessageCode.InternoValidazione, false);
				}
				catch (Exception ex)
				{
					throw new Exception("Errore durante la validazione di un interno. Metodo: VerificaInterno, modulo: SitDefault. " + ex.Message + "\r\n Query: " + sCommandText);
				}
				finally
				{
					if (pDataReader != null)
						pDataReader.Close();
					InternalDatabaseConnection.Connection.Close();
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
			IDataReader pDataReader = null;

			try
			{
				sRetVal = GetAeInternoConIntkey("ESPONENTE", pDataReader, TipoQuery.Elenco);
			}
			catch (CatastoException ex)
			{
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione di un esponente interno. Metodo: GetEsponenteInterno, modulo: SitDefault. " + ex.Message + "\r\n Query: " + sCommandText);
			}
			finally
			{
				if (pDataReader != null)
					pDataReader.Close();
				InternalDatabaseConnection.Connection.Close();
			}

			return sRetVal;
		}

		protected override RetSit VerificaEsponenteInterno()
		{
			RetSit pRetSit;

			IDataReader pDataReader = null;

			try
			{
				string sElem = GetAeInternoConIntkey("ESPONENTE", pDataReader, TipoQuery.Validazione);
				if (!Init.Utils.StringChecker.IsStringEmpty(sElem))
				{
					pRetSit = new RetSit(true);
					this.DataSit.EsponenteInterno = sElem;
				}
				else
				{
					pRetSit = RestituisciErroreSit(GetMessageValidate("AE_INTERNO_CON_INTKEY.ESPONENTE"), MessageCode.EsponenteInternoValidazione, false);
				}
			}
			catch (CatastoException ex)
			{
				pRetSit = RestituisciErroreSit(ex.Message, MessageCode.EsponenteInternoValidazione, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la validazione di un esponente interno. Metodo: VerificaEsponenteInterno, modulo: SitDefault. " + ex.Message + "\r\n Query: " + sCommandText);
			}
			finally
			{
				if (pDataReader != null)
					pDataReader.Close();
				InternalDatabaseConnection.Connection.Close();
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
					string sElem = GetAeCivicoConCivkey("ID_EDIFICIO", pDataReader, TipoQuery.Validazione);
					if (!Init.Utils.StringChecker.IsStringEmpty(sElem))
					{
						pRetSit = new RetSit(true);
						this.DataSit.Fabbricato = sElem;
					}
					else
					{
						pRetSit = RestituisciErroreSit(GetMessageValidate("ID_EDIFICIO"), MessageCode.FabbricatoValidazione, false);
					}
				}
				catch (CatastoException ex)
				{
					pRetSit = RestituisciErroreSit(ex.Message, MessageCode.FabbricatoValidazione, false);
				}
				catch (Exception ex)
				{
					throw new Exception("Errore durante la validazione di un fabbricato. Metodo: VerificaFabbricato, modulo: SitDefault. " + ex.Message + "\r\n Query: " + sCommandText);
				}
				finally
				{
					if (pDataReader != null)
						pDataReader.Close();
					InternalDatabaseConnection.Connection.Close();
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
			IDataReader pDataReader = null;

			try
			{
				sRetVal = GetAeCivicoConCivkey("G_SEZIONE", pDataReader, TipoQuery.Elenco);
			}
			catch (CatastoException ex)
			{
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione di una sezione. Metodo: GetSezione, modulo: SitDefault. " + ex.Message + "\r\n Query: " + sCommandText);
			}
			finally
			{
				if (pDataReader != null)
					pDataReader.Close();
				InternalDatabaseConnection.Connection.Close();
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

			IDataReader pDataReader = null;

			try
			{
				string sElem = GetAeCivicoConCivkey("G_SEZIONE", pDataReader, TipoQuery.Validazione);
				if (!Init.Utils.StringChecker.IsStringEmpty(sElem))
				{
					pRetSit = new RetSit(true);
					this.DataSit.Sezione = sElem;
				}
				else
				{
					pRetSit = RestituisciErroreSit(GetMessageValidate("G_SEZIONE"), MessageCode.SezioneValidazione, false);
				}
			}
			catch (CatastoException ex)
			{
				pRetSit = RestituisciErroreSit(ex.Message, MessageCode.SezioneValidazione, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la validazione di una sezione. Metodo: VerificaSezione, modulo: SitDefault. " + ex.Message + "\r\n Query: " + sCommandText);
			}
			finally
			{
				if (pDataReader != null)
					pDataReader.Close();
				InternalDatabaseConnection.Connection.Close();
			}

			return pRetSit;
		}

		protected override string GetFoglio()
		{
			string sRetVal = "";
			IDataReader pDataReader = null;

			try
			{
				sRetVal = GetAeCivicoConCivkey("G_FOGLIO", pDataReader, TipoQuery.Elenco);
			}
			catch (CatastoException ex)
			{
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione di un foglio. Metodo: GetFoglio, modulo: SitDefault. " + ex.Message + "\r\n Query: " + sCommandText);
			}
			finally
			{
				if (pDataReader != null)
					pDataReader.Close();
				InternalDatabaseConnection.Connection.Close();
			}

			return sRetVal;
		}

		protected override RetSit VerificaFoglio()
		{
			RetSit pRetSit;

			IDataReader pDataReader = null;

			try
			{
				string sElem = GetAeCivicoConCivkey("G_FOGLIO", pDataReader, TipoQuery.Validazione);
				if (!Init.Utils.StringChecker.IsStringEmpty(sElem))
				{
					pRetSit = new RetSit(true);
					this.DataSit.Foglio = sElem;
				}
				else
				{
					pRetSit = RestituisciErroreSit(GetMessageValidate("G_FOGLIO"), MessageCode.FoglioValidazione, false);
				}
			}
			catch (CatastoException ex)
			{
				pRetSit = RestituisciErroreSit(ex.Message, MessageCode.FoglioValidazione, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la validazione di un foglio. Metodo: VerificaFoglio, modulo: SitDefault. " + ex.Message + "\r\n Query: " + sCommandText);
			}
			finally
			{
				if (pDataReader != null)
					pDataReader.Close();
				InternalDatabaseConnection.Connection.Close();
			}
			return pRetSit;
		}

		protected override string GetParticella()
		{
			string sRetVal = "";
			IDataReader pDataReader = null;

			try
			{
				sRetVal = GetAeCivicoConCivkey("G_NUMERO", pDataReader, TipoQuery.Elenco);
			}
			catch (CatastoException ex)
			{
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione di una particella. Metodo: GetParticella, modulo: SitDefault. " + ex.Message + "\r\n Query: " + sCommandText);
			}
			finally
			{
				if (pDataReader != null)
					pDataReader.Close();
				InternalDatabaseConnection.Connection.Close();
			}

			return sRetVal;

		}

		protected override RetSit VerificaParticella()
		{
			RetSit pRetSit;

			IDataReader pDataReader = null;

			try
			{
				string sElem = GetAeCivicoConCivkey("G_NUMERO", pDataReader, TipoQuery.Validazione);
				if (!Init.Utils.StringChecker.IsStringEmpty(sElem))
				{
					pRetSit = new RetSit(true);
					this.DataSit.Particella = sElem;
				}
				else
				{
					pRetSit = RestituisciErroreSit(GetMessageValidate("G_NUMERO"), MessageCode.ParticellaValidazione, false);
				}
			}
			catch (CatastoException ex)
			{
				pRetSit = RestituisciErroreSit(ex.Message, MessageCode.ParticellaValidazione, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la validazione di una particella. Metodo: VerificaParticella, modulo: SitDefault. " + ex.Message + "\r\n Query: " + sCommandText);
			}
			finally
			{
				if (pDataReader != null)
					pDataReader.Close();
				InternalDatabaseConnection.Connection.Close();
			}

			return pRetSit;
		}

		protected override string GetCivico()
		{
			string sRetVal = "";
			IDataReader pDataReader = null;

			try
			{
				sRetVal = GetAeCivicoConCivkey("NUMERO", pDataReader, TipoQuery.Elenco);
			}
			catch (CatastoException ex)
			{
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione di un civico. Metodo: GetCivico, modulo: SitDefault. " + ex.Message + "\r\n Query: " + sCommandText);
			}
			finally
			{
				if (pDataReader != null)
					pDataReader.Close();
				InternalDatabaseConnection.Connection.Close();
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
					string sElem = GetAeCivicoConCivkey("NUMERO", pDataReader, TipoQuery.Validazione);
					if (!Init.Utils.StringChecker.IsStringEmpty(sElem))
					{
						pRetSit = new RetSit(true);
						this.DataSit.Civico = sElem;
					}
					else
					{
						pRetSit = RestituisciErroreSit(GetMessageValidate("AE_CIVICO_CON_CIVKEY.NUMERO"), MessageCode.CivicoValidazione, false);
					}
				}
				catch (CatastoException ex)
				{
					pRetSit = RestituisciErroreSit(ex.Message, MessageCode.CivicoValidazione, false);
				}
				catch (Exception ex)
				{
					throw new Exception("Errore durante la validazione di un civico. Metodo: VerificaCivico, modulo: SitDefault. " + ex.Message + "\r\n Query: " + sCommandText);
				}
				finally
				{
					if (pDataReader != null)
						pDataReader.Close();
					InternalDatabaseConnection.Connection.Close();
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
			IDataReader pDataReader = null;

			try
			{
				sRetVal = GetAeCivicoConCivkey("CAP", pDataReader, TipoQuery.Elenco);
			}
			catch (CatastoException ex)
			{
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione di un CAP. Metodo: GetCAP, modulo: SitDefault. " + ex.Message + "\r\n Query: " + sCommandText);
			}
			finally
			{
				if (pDataReader != null)
					pDataReader.Close();
				InternalDatabaseConnection.Connection.Close();
			}

			return sRetVal;
		}

		protected override RetSit VerificaCAP()
		{
			RetSit pRetSit;

			IDataReader pDataReader = null;

			try
			{
				string sElem = GetAeCivicoConCivkey("CAP", pDataReader, TipoQuery.Validazione);
				if (!Init.Utils.StringChecker.IsStringEmpty(sElem))
				{
					pRetSit = new RetSit(true);
					this.DataSit.CAP = sElem;
				}
				else
				{
					pRetSit = RestituisciErroreSit(GetMessageValidate("CAP"), MessageCode.CAPValidazione, false);
				}
			}
			catch (CatastoException ex)
			{
				pRetSit = RestituisciErroreSit(ex.Message, MessageCode.CAPValidazione, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la validazione di un CAP. Metodo: VerificaCAP, modulo: SitDefault. " + ex.Message + "\r\n Query: " + sCommandText);
			}
			finally
			{
				if (pDataReader != null)
					pDataReader.Close();
				InternalDatabaseConnection.Connection.Close();
			}

			return pRetSit;
		}

		protected override string GetCodCivico()
		{
			string sRetVal = "";
			IDataReader pDataReader = null;

			try
			{
				sRetVal = GetAeCivicoConCivkey("CIVKEY", pDataReader, TipoQuery.Elenco);

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
				throw new Exception("Errore durante la restituzione di un codice civico. Metodo: GetCodCivico, modulo: SitDefault. " + ex.Message + "\r\n Query: " + sCommandText);
			}
			finally
			{
				if (pDataReader != null)
					pDataReader.Close();
				InternalDatabaseConnection.Connection.Close();
			}

			return sRetVal;
		}

		protected override string GetCodFabbricato()
		{
			string sRetVal = "";
			IDataReader pDataReader = null;

			try
			{
				sRetVal = GetAeCivicoConCivkey("ID_EDIFICIO", pDataReader, TipoQuery.Elenco);
			}
			catch (CatastoException ex)
			{
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione di un codice fabbricato. Metodo: GetCodFabbricato, modulo: SitDefault. " + ex.Message + "\r\n Query: " + sCommandText);
			}
			finally
			{
				if (pDataReader != null)
					pDataReader.Close();
				InternalDatabaseConnection.Connection.Close();
			}

			return sRetVal;
		}
		
		protected override string GetCodVia()
		{
			string sRetVal = "";
			IDataReader pDataReader = null;

			try
			{
				sRetVal = GetAeCivicoConCivkey("COD_VIA", pDataReader, TipoQuery.Elenco);
			}
			catch (CatastoException ex)
			{
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione di un codice via. Metodo: GetCodVia, modulo: SitDefault. " + ex.Message + "\r\n Query: " + sCommandText);
			}
			finally
			{
				if (pDataReader != null)
					pDataReader.Close();
				InternalDatabaseConnection.Connection.Close();
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
