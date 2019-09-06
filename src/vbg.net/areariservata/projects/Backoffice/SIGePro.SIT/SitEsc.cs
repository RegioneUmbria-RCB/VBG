using System;
using System.Collections.Generic;
using System.Data;
using Init.SIGePro.Sit.Data;
using Init.SIGePro.Exceptions.SIT;
using Init.SIGePro.Sit.Manager;
using Init.SIGePro.Sit.ValidazioneFormale;
using Init.SIGePro.Verticalizzazioni;
using log4net;
using PersonalLib2.Data;

namespace Init.SIGePro.Sit
{
	/// <summary>
	/// Classe specifica per il Sit Esc
	/// </summary>
	public class SIT_ESC : SitBase
	{
		ILog _logger = LogManager.GetLogger(typeof(SIT_ESC));

		public SIT_ESC()
			: base(new ValidazioneFormaleTramiteCodiceCivicoService())
		{
		}

		VerticalizzazioneSitEsc _verticalizzazione;
		string _tableOwnler;
		string sCommandText;

		#region Utility
		public override void SetupVerticalizzazione()
		{
			GetParametriFromVertSITESC();
		}


		/// <summary>
		/// Metodo usato per leggere i parametri della verticalizzazione SIT_CORE
		/// </summary>
		private void GetParametriFromVertSITESC()
		{
			try
			{
				DataBase dDataBase = this.Database;

				_verticalizzazione = new VerticalizzazioneSitEsc(this.IdComuneAlias, this.Software);

				if (_verticalizzazione.Attiva)
				{
					var connectionString = _verticalizzazione.Connectionstring;
					var providerString = _verticalizzazione.Provider;
					_tableOwnler = "cpg_dbintegrato_esc";

					ProviderType provider = (ProviderType)Enum.Parse(typeof(ProviderType), providerString, false);
					InternalDatabaseConnection = new DataBase(connectionString, provider);
				}
				else
					throw new Exception("La verticalizzazione SIT_ESC non è attiva.\r\n");
			}
			catch (Exception ex)
			{
				_logger.ErrorFormat("SIT_ESC.GetParametriFromVertSITESC: errore durante la lettura dei dati-> {0}", ex.ToString());

				throw new Exception("Errore generato durante la lettura della verticalizzazione SIT_ESC. Metodo: GetParametriFromVertSITCORE, modulo: SitCore. " + ex.Message + "\r\n");
			}
		}

		private string GetMessageValidate(string sField)
		{
			string sValue = string.Empty;

			switch (sField)
			{
				case "IMMOBILE":
					sValue = "L'unità immobiliare " + DataSit.UI + " non è valido per i dati inseriti";
					break;
				case "PK_SEQU_FABBRICATO":
					sValue = "Il fabbricato " + DataSit.Fabbricato + " non è valido per i dati inseriti";
					break;
				case "FOGLIO":
					sValue = "Il foglio " + DataSit.Foglio + " non è valido per i dati inseriti";
					break;
				case "CPG_FOGLIO_PART_CT.FOGLIO":
					sValue = "Il foglio " + DataSit.Foglio + " non è valido per i dati inseriti";
					break;
				case "CPG_SIT_SUB.FOGLIO":
					sValue = "Il foglio " + DataSit.Foglio + " non è valido per i dati inseriti";
					break;
				case "CPG_FOGLIO_PART_CT.NUMERO":
					sValue = "La particella " + DataSit.Particella + " non è valida per i dati inseriti";
					break;
				case "CPG_SIT_SUB.NUMERO":
					sValue = "La particella " + DataSit.Particella + " non è valida per i dati inseriti";
					break;
				case "SUBALTERNO":
					sValue = "Il subalterno " + DataSit.Sub + " non è valido per i dati inseriti";
					break;
				case "CPG_FOGLIO_PART_CT.SUBALTERNO":
					sValue = "Il subalterno " + DataSit.Sub + " non è valido per i dati inseriti";
					break;
				case "CPG_SIT_SUB.SUBALTERNO":
					sValue = "Il subalterno " + DataSit.Sub + " non è valido per i dati inseriti";
					break;
				case "NUMERO":
					sValue = "Il civico " + DataSit.Civico + " non è valido per i dati inseriti";
					break;
				case "ESP":
					sValue = "L'esponente " + DataSit.Esponente + " non è valido per i dati inseriti";
					break;
				case "CAP":
					sValue = "Il CAP " + DataSit.CAP + " non è valido per i dati inseriti";
					break;
				case "DESCR_LOCALITA":
					sValue = "La frazione " + DataSit.Frazione + " non è valida per i dati inseriti";
					break;
				case "CIRCOSCRIZIONE":
					sValue = "La circoscrizione " + DataSit.Circoscrizione + " non è valida per i dati inseriti";
					break;
			}

			return sValue;
		}

		private string GetMessageList(string sField)
		{
			string sValue = string.Empty;

			switch (sField)
			{
				case "FOGLIO":
					sValue = "Non è possibile ottenere la lista dei fogli per insufficienza di dati: il catasto deve essere fornito";
					break;
				case "CPG_FOGLIO_PART_CT.FOGLIO":
					sValue = "Non è possibile ottenere la lista dei fogli per insufficienza di dati: il catasto deve essere fornito";
					break;
				case "CPG_SIT_SUB.FOGLIO":
					sValue = "Non è possibile ottenere la lista dei fogli per insufficienza di dati: il catasto deve essere fornito";
					break;
				case "CPG_FOGLIO_PART_CT.NUMERO":
					sValue = "Non è possibile ottenere la lista delle particelle per insufficienza di dati: il catasto ed il fabbricato/foglio devono essere forniti";
					break;
				case "CPG_SIT_SUB.NUMERO":
					sValue = "Non è possibile ottenere la lista delle particelle per insufficienza di dati: il catasto ed il fabbricato/foglio devono essere forniti";
					break;
				case "SUBALTERNO":
					sValue = "Non è possibile ottenere la lista dei sub per insufficienza di dati: il catasto ed il fabbricato/foglio,particella devono essere forniti";
					break;
				case "CPG_FOGLIO_PART_CT.SUBALTERNO":
					sValue = "Non è possibile ottenere la lista dei sub per insufficienza di dati: il catasto ed il fabbricato/foglio,particella devono essere forniti";
					break;
				case "CPG_SIT_SUB.SUBALTERNO":
					sValue = "Non è possibile ottenere la lista dei sub per insufficienza di dati: il catasto ed il fabbricato/foglio,particella devono essere forniti";
					break;
				case "NUMERO":
					sValue = "Non è possibile ottenere la lista dei civici per insufficienza di dati: il fabbricato/via devono essere forniti";
					break;
				case "ESP":
					sValue = "Non è possibile ottenere la lista degli esponenti per insufficienza di dati: il fabbricato/via ed il civico devono essere forniti";
					break;
				case "IMMOBILE":
					sValue = "Non è possibile ottenere la lista delle unità immobiliari per insufficienza di dati: il catasto, il fabbricato/foglio,particella,sub devono essere forniti";
					break;
				case "PK_SEQU_FABBRICATO":
					sValue = "Non è possibile ottenere la lista dei fabbricati per insufficienza di dati: la via/foglio,particella devono essere forniti";
					break;
			}

			return sValue;
		}


		private string GetCpgSitSub(string sField, IDataReader pDataReader, TipoQuery eTipoQuery)
		{
			string sRetVal = string.Empty;

			if (string.IsNullOrEmpty(DataSit.TipoCatasto))
			{
				if (string.IsNullOrEmpty(DataSit.CodVia) && string.IsNullOrEmpty(DataSit.Civico) && string.IsNullOrEmpty(DataSit.Esponente) && string.IsNullOrEmpty(DataSit.CAP) && string.IsNullOrEmpty(DataSit.Circoscrizione) && string.IsNullOrEmpty(DataSit.Frazione))
				{
					if (string.IsNullOrEmpty(DataSit.Fabbricato) && string.IsNullOrEmpty(DataSit.CodCivico))
						sRetVal = GetElemento(sField, "CPG_SIT_SUB", pDataReader, eTipoQuery);
					else
						sRetVal = GetElemento(sField, "CPG_SIT_SUB,CPG_SIT_CIVICI_FABBRICATI", pDataReader, eTipoQuery);
				}
				else
					sRetVal = GetElemento(sField, "CPG_SIT_SUB,CPG_SIT_CIVICI,CPG_SIT_CIVICI_FABBRICATI", pDataReader, eTipoQuery);

				if (eTipoQuery == TipoQuery.Validazione)
				{
					if (string.IsNullOrEmpty(sRetVal))
					{
						if ((sField == "SUBALTERNO") || (sField == "FOGLIO") || (sField == "NUMERO"))
							sRetVal = GetElemento(sField, "CPG_SIT_FOGLIO_PART_CT", pDataReader, eTipoQuery);
					}
				}
				else
				{
					string sRetValCT = "";
					if ((sField == "SUBALTERNO") || (sField == "FOGLIO") || (sField == "NUMERO"))
						sRetValCT = GetElemento(sField, "CPG_SIT_FOGLIO_PART_CT", pDataReader, eTipoQuery);

					if (!string.IsNullOrEmpty(sRetVal))
					{
						if (!string.IsNullOrEmpty(sRetValCT))
							if (sRetVal != sRetValCT)
								sRetVal = string.Empty;
					}
					else
						if (!string.IsNullOrEmpty(sRetValCT))
							sRetVal = sRetValCT;
				}
			}
			else
			{
				if (this.DataSit.TipoCatasto == "F")
				{
					if (string.IsNullOrEmpty(DataSit.CodVia) && string.IsNullOrEmpty(DataSit.Civico) && string.IsNullOrEmpty(DataSit.Esponente) && string.IsNullOrEmpty(DataSit.CAP) && string.IsNullOrEmpty(DataSit.Circoscrizione) && string.IsNullOrEmpty(DataSit.Frazione))
					{
						if (string.IsNullOrEmpty(DataSit.Fabbricato) && string.IsNullOrEmpty(DataSit.CodCivico))
							sRetVal = GetElemento(sField, "CPG_SIT_SUB", pDataReader, eTipoQuery);
						else
							sRetVal = GetElemento(sField, "CPG_SIT_SUB,CPG_SIT_CIVICI_FABBRICATI", pDataReader, eTipoQuery);
					}
					else
						sRetVal = GetElemento(sField, "CPG_SIT_SUB,CPG_SIT_CIVICI,CPG_SIT_CIVICI_FABBRICATI", pDataReader, eTipoQuery);

				}
				else
				{
					if ((sField == "SUBALTERNO") || (sField == "FOGLIO") || (sField == "NUMERO"))
						if (IsCampiToponomasticaImmobileVuoti())
							sRetVal = GetElemento(sField, "CPG_SIT_FOGLIO_PART_CT", pDataReader, eTipoQuery);
						else
							throw new CatastoException(GetMessageValidate("CPG_SIT_FOGLIO_PART_CT." + sField));
					else
						throw new CatastoException(GetMessageValidate("CPG_SIT_FOGLIO_PART_CT." + sField));
				}
			}

			return sRetVal;
		}

		private string GetCpgSitCiviciFabbricati(string sField, IDataReader pDataReader, TipoQuery eTipoQuery)
		{
			string sRetVal = string.Empty;

			if (string.IsNullOrEmpty(DataSit.TipoCatasto))
			{
				if (string.IsNullOrEmpty(DataSit.CodVia) && string.IsNullOrEmpty(DataSit.Civico) && string.IsNullOrEmpty(DataSit.Esponente) && string.IsNullOrEmpty(DataSit.CAP) && string.IsNullOrEmpty(DataSit.Circoscrizione) && string.IsNullOrEmpty(DataSit.Frazione))
				{
					if (string.IsNullOrEmpty(DataSit.UI) && string.IsNullOrEmpty(DataSit.Sub))
						sRetVal = GetElemento(sField, "CPG_SIT_CIVICI_FABBRICATI", pDataReader, eTipoQuery);
					else
						sRetVal = GetElemento(sField, "CPG_SIT_CIVICI_FABBRICATI,CPG_SIT_SUB", pDataReader, eTipoQuery);
				}
				else
				{
					if (string.IsNullOrEmpty(DataSit.UI) && string.IsNullOrEmpty(DataSit.Sub))
						sRetVal = GetElemento(sField, "CPG_SIT_CIVICI_FABBRICATI,CPG_SIT_CIVICI", pDataReader, eTipoQuery);
					else
						sRetVal = GetElemento(sField, "CPG_SIT_CIVICI_FABBRICATI,CPG_SIT_CIVICI,CPG_SIT_SUB", pDataReader, eTipoQuery);
				}

			}
			else
			{
				if (this.DataSit.TipoCatasto == "F")
				{
					if (string.IsNullOrEmpty(DataSit.CodVia) && string.IsNullOrEmpty(DataSit.Civico) && string.IsNullOrEmpty(DataSit.Esponente) && string.IsNullOrEmpty(DataSit.CAP) && string.IsNullOrEmpty(DataSit.Circoscrizione) && string.IsNullOrEmpty(DataSit.Frazione))
					{
						if (string.IsNullOrEmpty(DataSit.UI) && string.IsNullOrEmpty(DataSit.Sub))
							sRetVal = GetElemento(sField, "CPG_SIT_CIVICI_FABBRICATI", pDataReader, eTipoQuery);
						else
							sRetVal = GetElemento(sField, "CPG_SIT_CIVICI_FABBRICATI,CPG_SIT_SUB", pDataReader, eTipoQuery);
					}
					else
					{
						if (string.IsNullOrEmpty(DataSit.UI) && string.IsNullOrEmpty(DataSit.Sub))
							sRetVal = GetElemento(sField, "CPG_SIT_CIVICI_FABBRICATI,CPG_SIT_CIVICI", pDataReader, eTipoQuery);
						else
							sRetVal = GetElemento(sField, "CPG_SIT_CIVICI_FABBRICATI,CPG_SIT_CIVICI,CPG_SIT_SUB", pDataReader, eTipoQuery);
					}
				}
				else
				{
					throw new CatastoException(GetMessageValidate(sField));
				}
			}

			return sRetVal;
		}



		private string GetCpgSitCivici(string sField, IDataReader pDataReader, TipoQuery eTipoQuery)
		{

			string sRetVal = string.Empty;

			if ((sField == "CAP") || (sField == "DESCR_LOCALITA") || (sField == "CIRCOSCRIZIONE") || (sField == "FK_STRADE"))
			{
				if (string.IsNullOrEmpty(DataSit.UI) && string.IsNullOrEmpty(DataSit.Sub))
				{
					if (string.IsNullOrEmpty(DataSit.Fabbricato) && string.IsNullOrEmpty(DataSit.Foglio) && string.IsNullOrEmpty(DataSit.Particella))
						sRetVal = GetElemento(sField, "CPG_SIT_CIVICI", pDataReader, eTipoQuery);
					else
						sRetVal = GetElemento(sField, "CPG_SIT_CIVICI,CPG_SIT_CIVICI_FABBRICATI", pDataReader, eTipoQuery);
				}
				else
				{
					sRetVal = GetElemento(sField, "CPG_SIT_CIVICI,CPG_SIT_CIVICI_FABBRICATI,CPG_SIT_SUB", pDataReader, eTipoQuery);
				}
			}
			else
			{
				if (string.IsNullOrEmpty(DataSit.TipoCatasto))
				{
					if (string.IsNullOrEmpty(DataSit.UI) && string.IsNullOrEmpty(DataSit.Sub))
					{
						if (string.IsNullOrEmpty(DataSit.Fabbricato) && string.IsNullOrEmpty(DataSit.Foglio) && string.IsNullOrEmpty(DataSit.Particella))
							sRetVal = GetElemento(sField, "CPG_SIT_CIVICI", pDataReader, eTipoQuery);
						else
							sRetVal = GetElemento(sField, "CPG_SIT_CIVICI,CPG_SIT_CIVICI_FABBRICATI", pDataReader, eTipoQuery);
					}
					else
						sRetVal = GetElemento(sField, "CPG_SIT_CIVICI,CPG_SIT_CIVICI_FABBRICATI,CPG_SIT_SUB", pDataReader, eTipoQuery);
				}
				else
				{
					if (this.DataSit.TipoCatasto == "F")
					{
						if (string.IsNullOrEmpty(DataSit.UI) && string.IsNullOrEmpty(DataSit.Sub))
						{
							if (string.IsNullOrEmpty(DataSit.Fabbricato) && string.IsNullOrEmpty(DataSit.Foglio) && string.IsNullOrEmpty(DataSit.Particella))
								sRetVal = GetElemento(sField, "CPG_SIT_CIVICI", pDataReader, eTipoQuery);
							else
								sRetVal = GetElemento(sField, "CPG_SIT_CIVICI,CPG_SIT_CIVICI_FABBRICATI", pDataReader, eTipoQuery);
						}
						else
							sRetVal = GetElemento(sField, "CPG_SIT_CIVICI,CPG_SIT_CIVICI_FABBRICATI,CPG_SIT_SUB", pDataReader, eTipoQuery);
					}
					else
					{
						throw new CatastoException(GetMessageValidate(sField));
					}
				}
			}

			return sRetVal;
		}

		private RetSit ElencoCpgSitSub(string sField, IDataReader pDataReader)
		{
			RetSit pRetSit = null;

			if (string.IsNullOrEmpty(DataSit.TipoCatasto))
			{
				if (sField == "NUMERO")
					throw new CatastoException(GetMessageList("CPG_SIT_FOGLIO_PART_CT." + sField));
				else
					throw new CatastoException(GetMessageList(sField));
			}
			else
			{
				if (this.DataSit.TipoCatasto == "F")
				{
					if (string.IsNullOrEmpty(DataSit.CodVia) && string.IsNullOrEmpty(DataSit.Civico) && string.IsNullOrEmpty(DataSit.Esponente) && string.IsNullOrEmpty(DataSit.CAP) && string.IsNullOrEmpty(DataSit.Circoscrizione) && string.IsNullOrEmpty(DataSit.Frazione))
					{
						if (string.IsNullOrEmpty(DataSit.Fabbricato) && string.IsNullOrEmpty(DataSit.CodCivico))
							pRetSit = GetElenco(sField, "CPG_SIT_SUB", pDataReader);
						else
							pRetSit = GetElenco(sField, "CPG_SIT_SUB,CPG_SIT_CIVICI_FABBRICATI", pDataReader);
					}
					else
						pRetSit = GetElenco(sField, "CPG_SIT_SUB,CPG_SIT_CIVICI,CPG_SIT_CIVICI_FABBRICATI", pDataReader);

				}
				else
				{
					if ((sField == "SUBALTERNO") || (sField == "FOGLIO") || (sField == "NUMERO"))
						if (IsCampiToponomasticaImmobileVuoti())
							pRetSit = GetElenco(sField, "CPG_SIT_FOGLIO_PART_CT", pDataReader);
						else
							pRetSit = new RetSit(true);
					else
						pRetSit = new RetSit(true);
				}
			}

			return pRetSit;
		}

		private RetSit ElencoCpgSitCiviciFabbricati(string sField, IDataReader pDataReader)
		{
			RetSit pRetSit = null;

            //if (string.IsNullOrEmpty(DataSit.TipoCatasto))
            //    throw new CatastoException(GetMessageList(sField));
            //else
            //{
				if (string.IsNullOrEmpty(DataSit.TipoCatasto) || (this.DataSit.TipoCatasto == "F"))
				{
					if (string.IsNullOrEmpty(DataSit.CodVia) && string.IsNullOrEmpty(DataSit.Civico) && string.IsNullOrEmpty(DataSit.Esponente) && string.IsNullOrEmpty(DataSit.CAP) && string.IsNullOrEmpty(DataSit.Circoscrizione) && string.IsNullOrEmpty(DataSit.Frazione))
					{
						if (string.IsNullOrEmpty(DataSit.UI) && string.IsNullOrEmpty(DataSit.Sub))
							pRetSit = GetElenco(sField, "CPG_SIT_CIVICI_FABBRICATI", pDataReader);
						else
							pRetSit = GetElenco(sField, "CPG_SIT_CIVICI_FABBRICATI,CPG_SIT_SUB", pDataReader);
					}
					else
					{
						if (string.IsNullOrEmpty(DataSit.UI) && string.IsNullOrEmpty(DataSit.Sub))
							pRetSit = GetElenco(sField, "CPG_SIT_CIVICI_FABBRICATI,CPG_SIT_CIVICI", pDataReader);
						else
							pRetSit = GetElenco(sField, "CPG_SIT_CIVICI_FABBRICATI,CPG_SIT_CIVICI,CPG_SIT_SUB", pDataReader);
					}
				}
				else
				{
					pRetSit = new RetSit(true);
				}
            //}

			return pRetSit;
		}

		private RetSit ElencoCpgSitCivici(string sField, IDataReader pDataReader)
		{
			RetSit pRetSit = null;

			if ((sField == "CAP") || (sField == "DESCR_LOCALITA") || (sField == "CIRCOSCRIZIONE"))
			{
				if (string.IsNullOrEmpty(DataSit.UI) && string.IsNullOrEmpty(DataSit.Sub))
				{
					if (string.IsNullOrEmpty(DataSit.Fabbricato) && string.IsNullOrEmpty(DataSit.Foglio) && string.IsNullOrEmpty(DataSit.Particella))
						pRetSit = GetElenco(sField, "CPG_SIT_CIVICI", pDataReader);
				}
				else
				{
					pRetSit = GetElenco(sField, "CPG_SIT_CIVICI,CPG_SIT_CIVICI_FABBRICATI,CPG_SIT_SUB", pDataReader);
				}
			}
			else
			{
                //if (string.IsNullOrEmpty(DataSit.TipoCatasto))
                //    throw new CatastoException(GetMessageList(sField));
                //else
                //{
					if (string.IsNullOrEmpty(DataSit.TipoCatasto) || (this.DataSit.TipoCatasto == "F"))
					{
						if (string.IsNullOrEmpty(DataSit.UI) && string.IsNullOrEmpty(DataSit.Sub))
						{
							if (string.IsNullOrEmpty(DataSit.Fabbricato) && string.IsNullOrEmpty(DataSit.Foglio) && string.IsNullOrEmpty(DataSit.Particella))
								pRetSit = GetElenco(sField, "CPG_SIT_CIVICI", pDataReader);
							else
								pRetSit = GetElenco(sField, "CPG_SIT_CIVICI,CPG_SIT_CIVICI_FABBRICATI", pDataReader);
						}
						else
							pRetSit = GetElenco(sField, "CPG_SIT_CIVICI,CPG_SIT_CIVICI_FABBRICATI,CPG_SIT_SUB", pDataReader);
					}
					else
					{
						pRetSit = new RetSit(true);
					}
                //}
			}

			return pRetSit;
		}



		/// <summary>
		/// Restituisce il nome completo (OWNER.TABELLA) della tabella o della lista tabelle passato
		/// </summary>
		/// <param name="listaTabelle">tabella o lista tabelle separate da virgola che va qualificata con il nome dell'owner</param>
		/// <returns>tabella o lista tabelle separata da virgola qualificata con il nome owner</returns>
		private string GetCompleteTableName(string listaTabelle)
		{
			var nomeTabelleCompleto = string.Empty;
			var tabelleList = listaTabelle.Split(new Char[] { ',' });

			foreach (var tabella in tabelleList)
			{
				var tmpNomeTabella = tabella.Trim();

				if (!String.IsNullOrEmpty(_tableOwnler))
					tmpNomeTabella = _tableOwnler + "." + tmpNomeTabella;

				nomeTabelleCompleto += tmpNomeTabella + ",";
			}

			nomeTabelleCompleto = nomeTabelleCompleto.Remove(nomeTabelleCompleto.Length - 1);

			return nomeTabelleCompleto;
		}

		

		/// <summary>
		/// Restituisce il command text di una query a partire dal nome tabella e dal tipo di query
		/// </summary>
		/// <param name="fieldsList">campo o lista di campi separata da virgole da leggere nella query</param>
		/// <param name="tableName">tabella o lista di tabelle separata da virgole su cui va eseguita la query</param>
		/// <param name="tipoQuery">Tipo di query</param>
		/// <returns>Command text della query</returns>
		private string GetQuery(string fieldsList, string tableName, TipoQuery tipoQuery)
		{
			string sQuery = string.Empty;

			switch (tableName)
			{
				case "CPG_SIT_CIVICI":
					switch (tipoQuery)
					{
						case TipoQuery.Elenco:
							sQuery = "Select distinct " + fieldsList + " from " + tableName + " where " +
								((!string.IsNullOrEmpty(this.DataSit.CodVia) && (fieldsList != "FK_STRADE")) ? InternalDatabaseConnection.Specifics.RTrimFunction("FK_STRADE") + " = '" + CodiceComune + LeftPad(RightTrim(this.DataSit.CodVia), 8, '0') + "' and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.Civico) && (fieldsList != "NUMERO")) ? InternalDatabaseConnection.Specifics.RTrimFunction("NUMERO") + " = " + RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Civico) ? this.DataSit.Civico : "-1") + " and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.Esponente) && (fieldsList != "ESP")) ? InternalDatabaseConnection.Specifics.RTrimFunction("ESP") + " = '" + RightTrim(this.DataSit.Esponente) + "' and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.CodCivico) && (fieldsList != "UK_CIVICO")) ? InternalDatabaseConnection.Specifics.RTrimFunction("UK_CIVICO") + " = '" + RightTrim(this.DataSit.CodCivico) + "' and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.Circoscrizione) && (fieldsList != "CIRCOSCRIZIONE")) ? InternalDatabaseConnection.Specifics.RTrimFunction("CIRCOSCRIZIONE") + " = '" + RightTrim(this.DataSit.Circoscrizione) + "' and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.Frazione) && (fieldsList != "DESCR_LOCALITA")) ? InternalDatabaseConnection.Specifics.RTrimFunction("DESCR_LOCALITA") + " = '" + RightTrim(this.DataSit.Frazione) + "' and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.CAP) && (fieldsList != "CAP")) ? InternalDatabaseConnection.Specifics.RTrimFunction("CAP") + " = '" + RightTrim(this.DataSit.CAP) + "' and " : "");
							break;
						case TipoQuery.Validazione:
							sQuery = "Select distinct " + fieldsList + " from " + tableName + " where " +
								((!string.IsNullOrEmpty(this.DataSit.CodVia)) ? InternalDatabaseConnection.Specifics.RTrimFunction("FK_STRADE") + " = '" + CodiceComune + LeftPad(RightTrim(this.DataSit.CodVia), 8, '0') + "' and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.Civico)) ? InternalDatabaseConnection.Specifics.RTrimFunction("NUMERO") + " = " + RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Civico) ? this.DataSit.Civico : "-1") + " and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.Esponente)) ? InternalDatabaseConnection.Specifics.RTrimFunction("ESP") + " = '" + RightTrim(this.DataSit.Esponente) + "' and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.CodCivico)) ? InternalDatabaseConnection.Specifics.RTrimFunction("UK_CIVICO") + " = '" + RightTrim(this.DataSit.CodCivico) + "' and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.Circoscrizione)) ? InternalDatabaseConnection.Specifics.RTrimFunction("CIRCOSCRIZIONE") + " = '" + RightTrim(this.DataSit.Circoscrizione) + "' and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.Frazione)) ? InternalDatabaseConnection.Specifics.RTrimFunction("DESCR_LOCALITA") + " = '" + RightTrim(this.DataSit.Frazione) + "' and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.CAP)) ? InternalDatabaseConnection.Specifics.RTrimFunction("CAP") + " = '" + RightTrim(this.DataSit.CAP) + "' and " : "");
							break;
					}
					break;
				case "CPG_SIT_CIVICI_FABBRICATI":
					switch (tipoQuery)
					{
						case TipoQuery.Elenco:
							sQuery = "Select distinct " + fieldsList + " from " + tableName + " where " +
								((!string.IsNullOrEmpty(this.DataSit.Fabbricato) && (fieldsList != "PK_SEQU_FABBRICATO")) ? InternalDatabaseConnection.Specifics.RTrimFunction("PK_SEQU_FABBRICATO") + " = " + RightTrim(this.DataSit.Fabbricato) + " and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.Foglio) && (fieldsList != "FOGLIO_CU")) ? InternalDatabaseConnection.Specifics.RTrimFunction("FOGLIO_CU") + " = '" + RightTrim(this.DataSit.Foglio) + "' and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.Particella) && (fieldsList != "PARTICELLA_CU")) ? InternalDatabaseConnection.Specifics.RTrimFunction("PARTICELLA_CU") + " = '" + RightTrim(this.DataSit.Particella) + "' and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.CodCivico) && (fieldsList != "UK_CIVICO")) ? InternalDatabaseConnection.Specifics.RTrimFunction("UK_CIVICO") + " = '" + RightTrim(this.DataSit.CodCivico) + "' and " : "");
							break;
						case TipoQuery.Validazione:
							sQuery = "Select distinct " + fieldsList + " from " + tableName + " where " +
								((!string.IsNullOrEmpty(this.DataSit.Fabbricato)) ? InternalDatabaseConnection.Specifics.RTrimFunction("PK_SEQU_FABBRICATO") + " = " + RightTrim(this.DataSit.Fabbricato) + " and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.Foglio)) ? InternalDatabaseConnection.Specifics.RTrimFunction("FOGLIO_CU") + " = '" + RightTrim(this.DataSit.Foglio) + "' and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.Particella)) ? InternalDatabaseConnection.Specifics.RTrimFunction("PARTICELLA_CU") + " = '" + RightTrim(this.DataSit.Particella) + "' and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.CodCivico)) ? InternalDatabaseConnection.Specifics.RTrimFunction("UK_CIVICO") + " = '" + RightTrim(this.DataSit.CodCivico) + "' and " : "");
							break;
					}
					break;
				case "CPG_SIT_SUB":
					switch (tipoQuery)
					{
						case TipoQuery.Elenco:
							sQuery = "Select distinct " + fieldsList + " from " + tableName + " where " +
								((!string.IsNullOrEmpty(this.DataSit.Foglio) && (fieldsList != "ESC_FOGLIO")) ? InternalDatabaseConnection.Specifics.RTrimFunction("ESC_FOGLIO") + " = '" + LeftPad(RightTrim(this.DataSit.Foglio), 5) + "' and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.Particella) && (fieldsList != "ESC_NUMERO")) ? InternalDatabaseConnection.Specifics.RTrimFunction("ESC_NUMERO") + " = '" + LeftPad(RightTrim(this.DataSit.Particella), 5) + "' and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.UI) && (fieldsList != "IMMOBILE")) ? InternalDatabaseConnection.Specifics.RTrimFunction("IMMOBILE") + " = " + RightTrim(this.DataSit.UI) + " and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.Sub) && (fieldsList != "SUBALTERNO")) ? InternalDatabaseConnection.Specifics.RTrimFunction("SUBALTERNO") + " = '" + LeftPad(RightTrim(this.DataSit.Sub), 4) + "' and " : "");
							break;
						case TipoQuery.Validazione:
							sQuery = "Select distinct " + fieldsList + " from " + tableName + " where " +
								((!string.IsNullOrEmpty(this.DataSit.Foglio)) ? InternalDatabaseConnection.Specifics.RTrimFunction("ESC_FOGLIO") + " = '" + LeftPad(RightTrim(this.DataSit.Foglio), 5) + "' and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.Particella)) ? InternalDatabaseConnection.Specifics.RTrimFunction("ESC_NUMERO") + " = '" + LeftPad(RightTrim(this.DataSit.Particella), 5) + "' and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.UI)) ? InternalDatabaseConnection.Specifics.RTrimFunction("IMMOBILE") + " = " + RightTrim(this.DataSit.UI) + " and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.Sub)) ? InternalDatabaseConnection.Specifics.RTrimFunction("SUBALTERNO") + " = '" + LeftPad(RightTrim(this.DataSit.Sub), 4) + "' and " : "");
							break;
					}
					break;
				case "CPG_SIT_FOGLIO_PART_CT":
					switch (tipoQuery)
					{
						case TipoQuery.Elenco:
							sQuery = "Select distinct " + fieldsList + " from " + tableName + " where " +
								((!string.IsNullOrEmpty(this.DataSit.Foglio) && (fieldsList != "FOGLIO")) ? InternalDatabaseConnection.Specifics.RTrimFunction("FOGLIO") + " = '" + RightTrim(this.DataSit.Foglio) + "' and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.Particella) && (fieldsList != "NUMERO")) ? InternalDatabaseConnection.Specifics.RTrimFunction("NUMERO") + " = '" + RightTrim(this.DataSit.Particella) + "' and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.UI) && (fieldsList != "IMMOBILE")) ? InternalDatabaseConnection.Specifics.RTrimFunction("IMMOBILE") + " = " + RightTrim(this.DataSit.UI) + " and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.Sub) && (fieldsList != "SUBALTERNO")) ? InternalDatabaseConnection.Specifics.RTrimFunction("SUBALTERNO") + " = '" + LeftPad(RightTrim(this.DataSit.Sub), 4) + "' and " : "");
							break;
						case TipoQuery.Validazione:
							sQuery = "Select distinct " + fieldsList + " from " + tableName + " where " +
								((!string.IsNullOrEmpty(this.DataSit.Foglio)) ? InternalDatabaseConnection.Specifics.RTrimFunction("FOGLIO") + " = '" + RightTrim(this.DataSit.Foglio) + "' and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.Particella)) ? InternalDatabaseConnection.Specifics.RTrimFunction("NUMERO") + " = '" + RightTrim(this.DataSit.Particella) + "' and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.UI)) ? InternalDatabaseConnection.Specifics.RTrimFunction("IMMOBILE") + " = " + RightTrim(this.DataSit.UI) + " and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.Sub)) ? InternalDatabaseConnection.Specifics.RTrimFunction("SUBALTERNO") + " = '" + LeftPad(RightTrim(this.DataSit.Sub), 4) + "' and " : "");
							break;
					}
					break;
				case "CAT_PARTICELLE_GAUSS":
					switch (tipoQuery)
					{
						case TipoQuery.Elenco:
							sQuery = "select " + fieldsList + " from " + GetCompleteTableName(tableName).Split(new Char[] { ',' })[0] + " where esc_foglio = '" + LeftPad(RightTrim(this.DataSit.Foglio), 5) + "' and esc_particella = '" + LeftPad(RightTrim(this.DataSit.Particella), 5) + "' and layer = 'PARTICELLE'";
							break;
					}
					break;
				case "VIEW_URB_SOTTOZONE,CAT_PARTICELLE_GAUSS":
				case "CAT_PARTICELLE_GAUSS,VIEW_URB_SOTTOZONE":
					switch (tipoQuery)
					{
						case TipoQuery.Elenco:
							sQuery = "Select distinct " + fieldsList + " from " +
									 GetCompleteTableName(tableName).Split(new Char[] { ',' })[0] + " " +
									 "where " +
									 "SDO_RELATE(" + GetCompleteTableName(tableName).Split(new Char[] { ',' })[0] + ".GEOMETRY, " +
									 "(select GEOMETRY from " + GetCompleteTableName(tableName).Split(new Char[] { ',' })[1] + " where esc_foglio = '" + LeftPad(RightTrim(this.DataSit.Foglio), 5) + "' and esc_particella = '" + LeftPad(RightTrim(this.DataSit.Particella), 5) + "' and layer = 'PARTICELLE'),'mask =ANYINTERACT querytype=WINDOW') = 'TRUE'";
							break;
					}
					break;
				case "CPG_SIT_CIVICI,CPG_SIT_CIVICI_FABBRICATI":
				case "CPG_SIT_CIVICI_FABBRICATI,CPG_SIT_CIVICI":
					switch (tipoQuery)
					{
						case TipoQuery.Elenco:
							sQuery = "Select distinct " + tableName.Split(new Char[] { ',' })[0] + "." + fieldsList + " from " + tableName + " where " +
								"CPG_SIT_CIVICI.UK_CIVICO = CPG_SIT_CIVICI_FABBRICATI.UK_CIVICO and " +
								((!string.IsNullOrEmpty(this.DataSit.Fabbricato) && (fieldsList != "PK_SEQU_FABBRICATO")) ? InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI.PK_SEQU_FABBRICATO") + " = " + RightTrim(this.DataSit.Fabbricato) + " and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.Foglio) && (fieldsList != "FOGLIO_CU")) ? InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI." + "FOGLIO_CU") + " = '" + RightTrim(this.DataSit.Foglio) + "' and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.Particella) && (fieldsList != "PARTICELLA_CU")) ? InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI." + "PARTICELLA_CU") + " = '" + RightTrim(this.DataSit.Particella) + "' and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.CodVia) && (fieldsList != "FK_STRADE")) ? InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI.FK_STRADE") + " = '" + CodiceComune + LeftPad(RightTrim(this.DataSit.CodVia), 8, '0') + "' and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.Civico) && (fieldsList != "NUMERO")) ? InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI.NUMERO") + " = " + RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Civico) ? this.DataSit.Civico : "-1") + " and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.Esponente) && (fieldsList != "ESP")) ? InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI.ESP") + " = '" + RightTrim(this.DataSit.Esponente) + "' and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.CodCivico) && (fieldsList != "UK_CIVICO")) ? InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI.UK_CIVICO") + " = '" + RightTrim(this.DataSit.CodCivico) + "' and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.Circoscrizione) && (fieldsList != "CIRCOSCRIZIONE")) ? InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI.CIRCOSCRIZIONE") + " = '" + RightTrim(this.DataSit.Circoscrizione) + "' and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.Frazione) && (fieldsList != "DESCR_LOCALITA")) ? InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI.DESCR_LOCALITA") + " = '" + RightTrim(this.DataSit.Frazione) + "' and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.CAP) && (fieldsList != "CAP")) ? InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI.CAP") + " = '" + RightTrim(this.DataSit.CAP) + "' and " : "");
							break;
						case TipoQuery.Validazione:
							sQuery = "Select distinct " + tableName.Split(new Char[] { ',' })[0] + "." + fieldsList + " from " + tableName + " where " +
								"CPG_SIT_CIVICI.UK_CIVICO = CPG_SIT_CIVICI_FABBRICATI.UK_CIVICO and " +
								((!string.IsNullOrEmpty(this.DataSit.Fabbricato)) ? InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI.PK_SEQU_FABBRICATO") + " = " + RightTrim(this.DataSit.Fabbricato) + " and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.Foglio)) ? InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI." + "FOGLIO_CU") + " = '" + RightTrim(this.DataSit.Foglio) + "' and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.Particella)) ? InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI." + "PARTICELLA_CU") + " = '" + RightTrim(this.DataSit.Particella) + "' and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.CodVia)) ? InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI.FK_STRADE") + " = '" + CodiceComune + LeftPad(RightTrim(this.DataSit.CodVia), 8, '0') + "' and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.Civico)) ? InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI.NUMERO") + " = " + RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Civico) ? this.DataSit.Civico : "-1") + " and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.Esponente)) ? InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI.ESP") + " = '" + RightTrim(this.DataSit.Esponente) + "' and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.CodCivico)) ? InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI.UK_CIVICO") + " = '" + RightTrim(this.DataSit.CodCivico) + "' and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.Circoscrizione)) ? InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI.CIRCOSCRIZIONE") + " = '" + RightTrim(this.DataSit.Circoscrizione) + "' and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.Frazione)) ? InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI.DESCR_LOCALITA") + " = '" + RightTrim(this.DataSit.Frazione) + "' and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.CAP)) ? InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI.CAP") + " = '" + RightTrim(this.DataSit.CAP) + "' and " : "");
							break;
					}
					break;
				case "CPG_SIT_SUB,CPG_SIT_CIVICI_FABBRICATI":
				case "CPG_SIT_CIVICI_FABBRICATI,CPG_SIT_SUB":
					switch (tipoQuery)
					{
						case TipoQuery.Elenco:
							sQuery = "Select distinct " + tableName.Split(new Char[] { ',' })[0] + "." + fieldsList + " from " + tableName + " where " +
								"LPAD(CPG_SIT_CIVICI_FABBRICATI.FOGLIO_CU,5,'0') = CPG_SIT_SUB.ESC_FOGLIO and " +
								"LPAD(CPG_SIT_CIVICI_FABBRICATI.PARTICELLA_CU,5,'0') = CPG_SIT_SUB.ESC_NUMERO and " +
								((!string.IsNullOrEmpty(this.DataSit.UI) && (fieldsList != "IMMOBILE")) ? InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_SUB.IMMOBILE") + " = " + RightTrim(this.DataSit.UI) + " and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.Sub) && (fieldsList != "SUBALTERNO")) ? InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_SUB.SUBALTERNO") + " = '" + LeftPad(RightTrim(this.DataSit.Sub), 4) + "' and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.Fabbricato) && (fieldsList != "PK_SEQU_FABBRICATO")) ? InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI.PK_SEQU_FABBRICATO") + " = " + RightTrim(this.DataSit.Fabbricato) + " and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.Foglio) && (fieldsList != "FOGLIO_CU")) ? InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI.FOGLIO_CU") + " = '" + RightTrim(this.DataSit.Foglio) + "' and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.Particella) && (fieldsList != "PARTICELLA_CU")) ? InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI.PARTICELLA_CU") + " = '" + RightTrim(this.DataSit.Particella) + "' and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.CodCivico) && (fieldsList != "UK_CIVICO")) ? InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI.UK_CIVICO") + " = '" + RightTrim(this.DataSit.CodCivico) + "' and " : "");
							break;
						case TipoQuery.Validazione:
							sQuery = "Select distinct " + tableName.Split(new Char[] { ',' })[0] + "." + fieldsList + " from " + tableName + " where " +
								"LPAD(CPG_SIT_CIVICI_FABBRICATI.FOGLIO_CU,5,'0') = CPG_SIT_SUB.ESC_FOGLIO and " +
								"LPAD(CPG_SIT_CIVICI_FABBRICATI.PARTICELLA_CU,5,'0') = CPG_SIT_SUB.ESC_NUMERO and " +
								((!string.IsNullOrEmpty(this.DataSit.UI)) ? InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_SUB.IMMOBILE") + " = " + RightTrim(this.DataSit.UI) + " and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.Sub)) ? InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_SUB.SUBALTERNO") + " = '" + LeftPad(RightTrim(this.DataSit.Sub), 4) + "' and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.Fabbricato)) ? InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI.PK_SEQU_FABBRICATO") + " = " + RightTrim(this.DataSit.Fabbricato) + " and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.Foglio)) ? InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI.FOGLIO_CU") + " = '" + RightTrim(this.DataSit.Foglio) + "' and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.Particella)) ? InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI.PARTICELLA_CU") + " = '" + RightTrim(this.DataSit.Particella) + "' and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.CodCivico)) ? InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI.UK_CIVICO") + " = '" + RightTrim(this.DataSit.CodCivico) + "' and " : "");
							break;
					}
					break;
				//case "CPG_SIT_FOGLIO_PART_CT,CPG_SIT_CIVICI_FABBRICATI":
				//case "CPG_SIT_CIVICI_FABBRICATI,CPG_SIT_FOGLIO_PART_CT":
				//    switch (eTipoQuery)
				//    {
				//        case TipoQuery.Elenco:
				//            sQuery = "Select distinct " + sTableName.Split(new Char[] { ',' })[0] + "." + sField + " from " + sTableName + " where " +
				//                "CPG_SIT_CIVICI_FABBRICATI.FOGLIO_CT = CPG_SIT_FOGLIO_PART_CT.FOGLIO and " +
				//                "CPG_SIT_CIVICI_FABBRICATI.PARTICELLA_CT = CPG_SIT_FOGLIO_PART_CT.NUMERO and " +
				//                ((!string.IsNullOrEmpty(this.DataSit.UI) && (sField != "IMMOBILE")) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_FOGLIO_PART_CT.IMMOBILE") + " = " + RightTrimSit(this.DataSit.UI) + " and " : "") +
				//                ((!string.IsNullOrEmpty(this.DataSit.Sub) && (sField != "SUBALTERNO")) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_FOGLIO_PART_CT.SUBALTERNO") + " = '" + LeftPadSit(RightTrimSit(this.DataSit.Sub), 4) + "' and " : "") +
				//                ((!string.IsNullOrEmpty(this.DataSit.Fabbricato) && (sField != "PK_SEQU_FABBRICATO")) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI.PK_SEQU_FABBRICATO") + " = " + RightTrimSit(this.DataSit.Fabbricato) + " and " : "") +
				//                ((!string.IsNullOrEmpty(this.DataSit.Foglio) && (sField != GetFieldCatasto("FOGLIO"))) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI.FOGLIO_CT") + " = '" + RightTrimSit(this.DataSit.Foglio) + "' and " : "") +
				//                ((!string.IsNullOrEmpty(this.DataSit.Particella) && (sField != GetFieldCatasto("PARTICELLA"))) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI.PARTICELLA_CT") + " = '" + RightTrimSit(this.DataSit.Particella) + "' and " : "") +
				//                ((!string.IsNullOrEmpty(this.DataSit.CodCivico) && (sField != "UK_CIVICO")) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI.UK_CIVICO") + " = '" + RightTrimSit(this.DataSit.CodCivico) + "' and " : "");
				//            break;
				//        case TipoQuery.Validazione:
				//            sQuery = "Select distinct " + sTableName.Split(new Char[] { ',' })[0] + "." + sField + " from " + sTableName + " where " +
				//                "CPG_SIT_CIVICI_FABBRICATI.FOGLIO_CT = CPG_SIT_FOGLIO_PART_CT.FOGLIO and " +
				//                "CPG_SIT_CIVICI_FABBRICATI.PARTICELLA_CT = CPG_SIT_FOGLIO_PART_CT.NUMERO and " +
				//                ((!string.IsNullOrEmpty(this.DataSit.UI)) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_FOGLIO_PART_CT.IMMOBILE") + " = " + RightTrimSit(this.DataSit.UI) + " and " : "") +
				//                ((!string.IsNullOrEmpty(this.DataSit.Sub)) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_FOGLIO_PART_CT.SUBALTERNO") + " = '" + LeftPadSit(RightTrimSit(this.DataSit.Sub), 4) + "' and " : "") +
				//                ((!string.IsNullOrEmpty(this.DataSit.Fabbricato)) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI.PK_SEQU_FABBRICATO") + " = " + RightTrimSit(this.DataSit.Fabbricato) + " and " : "") +
				//                ((!string.IsNullOrEmpty(this.DataSit.Foglio)) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI.FOGLIO_CT") + " = '" + RightTrimSit(this.DataSit.Foglio) + "' and " : "") +
				//                ((!string.IsNullOrEmpty(this.DataSit.Particella)) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI.PARTICELLA_CT") + " = '" + RightTrimSit(this.DataSit.Particella) + "' and " : "") +
				//                ((!string.IsNullOrEmpty(this.DataSit.CodCivico)) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI.UK_CIVICO") + " = '" + RightTrimSit(this.DataSit.CodCivico) + "' and " : "");
				//            break;
				//    }
				//    break;
				case "CPG_SIT_CIVICI,CPG_SIT_CIVICI_FABBRICATI,CPG_SIT_SUB":
				case "CPG_SIT_CIVICI_FABBRICATI,CPG_SIT_CIVICI,CPG_SIT_SUB":
				case "CPG_SIT_SUB,CPG_SIT_CIVICI,CPG_SIT_CIVICI_FABBRICATI":
					switch (tipoQuery)
					{
						case TipoQuery.Elenco:
							sQuery = "Select distinct " + tableName.Split(new Char[] { ',' })[0] + "." + fieldsList + " from " + tableName + " where " +
								"CPG_SIT_CIVICI.UK_CIVICO = CPG_SIT_CIVICI_FABBRICATI.UK_CIVICO and " +
								"LPAD(CPG_SIT_CIVICI_FABBRICATI.FOGLIO_CU,5,'0') = CPG_SIT_SUB.ESC_FOGLIO and " +
								"LPAD(CPG_SIT_CIVICI_FABBRICATI.PARTICELLA_CU,5,'0') = CPG_SIT_SUB.ESC_NUMERO and " +
								((!string.IsNullOrEmpty(this.DataSit.UI) && (fieldsList != "IMMOBILE")) ? InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_SUB.IMMOBILE") + " = " + RightTrim(this.DataSit.UI) + " and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.Sub) && (fieldsList != "SUBALTERNO")) ? InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_SUB.SUBALTERNO") + " = '" + LeftPad(RightTrim(this.DataSit.Sub), 4) + "' and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.Fabbricato) && (fieldsList != "PK_SEQU_FABBRICATO")) ? InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI.PK_SEQU_FABBRICATO") + " = " + RightTrim(this.DataSit.Fabbricato) + " and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.Foglio) && (fieldsList != "FOGLIO_CU")) ? InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI.FOGLIO_CU") + " = '" + RightTrim(this.DataSit.Foglio) + "' and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.Particella) && (fieldsList != "PARTICELLA_CU")) ? InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI.PARTICELLA_CU") + " = '" + RightTrim(this.DataSit.Particella) + "' and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.CodVia) && (fieldsList != "FK_STRADE")) ? InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI.FK_STRADE") + " = '" + CodiceComune + LeftPad(RightTrim(this.DataSit.CodVia), 8, '0') + "' and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.Civico) && (fieldsList != "NUMERO")) ? InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI.NUMERO") + " = " + RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Civico) ? this.DataSit.Civico : "-1") + " and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.Esponente) && (fieldsList != "ESP")) ? InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI.ESP") + " = '" + RightTrim(this.DataSit.Esponente) + "' and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.CodCivico) && (fieldsList != "UK_CIVICO")) ? InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI.UK_CIVICO") + " = '" + RightTrim(this.DataSit.CodCivico) + "' and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.Circoscrizione) && (fieldsList != "CIRCOSCRIZIONE")) ? InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI.CIRCOSCRIZIONE") + " = '" + RightTrim(this.DataSit.Circoscrizione) + "' and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.Frazione) && (fieldsList != "DESCR_LOCALITA")) ? InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI.DESCR_LOCALITA") + " = '" + RightTrim(this.DataSit.Frazione) + "' and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.CAP) && (fieldsList != "CAP")) ? InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI.CAP") + " = '" + RightTrim(this.DataSit.CAP) + "' and " : "");
							break;
						case TipoQuery.Validazione:
							sQuery = "Select distinct " + tableName.Split(new Char[] { ',' })[0] + "." + fieldsList + " from " + tableName + " where " +
								"CPG_SIT_CIVICI.UK_CIVICO = CPG_SIT_CIVICI_FABBRICATI.UK_CIVICO and " +
								"LPAD(CPG_SIT_CIVICI_FABBRICATI.FOGLIO_CU,5,'0') = CPG_SIT_SUB.ESC_FOGLIO and " +
								"LPAD(CPG_SIT_CIVICI_FABBRICATI.PARTICELLA_CU,5,'0') = CPG_SIT_SUB.ESC_NUMERO and " +
								((!string.IsNullOrEmpty(this.DataSit.UI)) ? InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_SUB.IMMOBILE") + " = " + RightTrim(this.DataSit.UI) + " and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.Sub)) ? InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_SUB.SUBALTERNO") + " = '" + LeftPad(RightTrim(this.DataSit.Sub), 4) + "' and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.Fabbricato)) ? InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI.PK_SEQU_FABBRICATO") + " = " + RightTrim(this.DataSit.Fabbricato) + " and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.Foglio)) ? InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI.FOGLIO_CU") + " = '" + RightTrim(this.DataSit.Foglio) + "' and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.Particella)) ? InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI.PARTICELLA_CU") + " = '" + RightTrim(this.DataSit.Particella) + "' and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.CodVia)) ? InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI.FK_STRADE") + " = '" + CodiceComune + LeftPad(RightTrim(this.DataSit.CodVia), 8, '0') + "' and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.Civico)) ? InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI.NUMERO") + " = " + RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Civico) ? this.DataSit.Civico : "-1") + " and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.Esponente)) ? InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI.ESP") + " = '" + RightTrim(this.DataSit.Esponente) + "' and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.CodCivico)) ? InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI.UK_CIVICO") + " = '" + RightTrim(this.DataSit.CodCivico) + "' and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.Circoscrizione)) ? InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI.CIRCOSCRIZIONE") + " = '" + RightTrim(this.DataSit.Circoscrizione) + "' and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.Frazione)) ? InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI.DESCR_LOCALITA") + " = '" + RightTrim(this.DataSit.Frazione) + "' and " : "") +
								((!string.IsNullOrEmpty(this.DataSit.CAP)) ? InternalDatabaseConnection.Specifics.RTrimFunction("CPG_SIT_CIVICI.CAP") + " = '" + RightTrim(this.DataSit.CAP) + "' and " : "");
							break;
					}
					break;
				//case "CPG_SIT_CIVICI,CPG_SIT_CIVICI_FABBRICATI,CPG_SIT_FOGLIO_PART_CT":
				//case "CPG_SIT_CIVICI_FABBRICATI,CPG_SIT_CIVICI,CPG_SIT_FOGLIO_PART_CT":
				//case "CPG_SIT_FOGLIO_PART_CT,CPG_SIT_CIVICI,CPG_SIT_CIVICI_FABBRICATI":
				//    switch (eTipoQuery)
				//    {
				//        case TipoQuery.Elenco:
				//            sQuery = "Select distinct " + sTableName.Split(new Char[] { ',' })[0] + "." + sField + " from " + sTableName + " where " +
				//                "CPG_SIT_CIVICI.UK_CIVICO = CPG_SIT_CIVICI_FABBRICATI.UK_CIVICO and " +
				//                "CPG_SIT_CIVICI_FABBRICATI.FOGLIO_CT = CPG_SIT_FOGLIO_PART_CT.FOGLIO and " +
				//                "CPG_SIT_CIVICI_FABBRICATI.PARTICELLA_CT = CPG_SIT_FOGLIO_PART_CT.NUMERO and " +
				//                ((!string.IsNullOrEmpty(this.DataSit.UI) && (sField != "IMMOBILE")) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_FOGLIO_PART_CT.IMMOBILE") + " = " + RightTrimSit(this.DataSit.UI) + " and " : "") +
				//                ((!string.IsNullOrEmpty(this.DataSit.Sub) && (sField != "SUBALTERNO")) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_FOGLIO_PART_CT.SUBALTERNO") + " = '" + LeftPadSit(RightTrimSit(this.DataSit.Sub), 4) + "' and " : "") +
				//                ((!string.IsNullOrEmpty(this.DataSit.Fabbricato) && (sField != "PK_SEQU_FABBRICATO")) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI.PK_SEQU_FABBRICATO") + " = " + RightTrimSit(this.DataSit.Fabbricato) + " and " : "") +
				//                ((!string.IsNullOrEmpty(this.DataSit.Foglio) && (sField != "FOGLIO_CT")) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI.FOGLIO_CT") + " = '" + RightTrimSit(this.DataSit.Foglio) + "' and " : "") +
				//                ((!string.IsNullOrEmpty(this.DataSit.Particella) && (sField != "PARTICELLA_CT")) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI.PARTICELLA_CT") + " = '" + RightTrimSit(this.DataSit.Particella) + "' and " : "") +
				//                ((!string.IsNullOrEmpty(this.DataSit.CodVia) && (sField != "FK_STRADE")) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_CIVICI.FK_STRADE") + " = '" + CodiceComune + LeftPadSit(RightTrimSit(this.DataSit.CodVia), 8, '0') + "' and " : "") +
				//                ((!string.IsNullOrEmpty(this.DataSit.Civico) && (sField != "NUMERO")) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_CIVICI.NUMERO") + " = " + RightTrimSit(Init.Utils.StringChecker.IsNumeric(this.DataSit.Civico) ? this.DataSit.Civico : "-1") + " and " : "") +
				//                ((!string.IsNullOrEmpty(this.DataSit.Esponente) && (sField != "ESP")) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_CIVICI.ESP") + " = '" + RightTrimSit(this.DataSit.Esponente) + "' and " : "") +
				//                ((!string.IsNullOrEmpty(this.DataSit.CodCivico) && (sField != "UK_CIVICO")) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_CIVICI.UK_CIVICO") + " = '" + RightTrimSit(this.DataSit.CodCivico) + "' and " : "") +
				//                ((!string.IsNullOrEmpty(this.DataSit.Circoscrizione) && (sField != "CIRCOSCRIZIONE")) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_CIVICI.CIRCOSCRIZIONE") + " = '" + RightTrimSit(this.DataSit.Circoscrizione) + "' and " : "") +
				//                ((!string.IsNullOrEmpty(this.DataSit.Frazione) && (sField != "DESCR_LOCALITA")) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_CIVICI.DESCR_LOCALITA") + " = '" + RightTrimSit(this.DataSit.Frazione) + "' and " : "") +
				//                ((!string.IsNullOrEmpty(this.DataSit.CAP) && (sField != "CAP")) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_CIVICI.CAP") + " = '" + RightTrimSit(this.DataSit.CAP) + "' and " : "");
				//            break;
				//        case TipoQuery.Validazione:
				//            sQuery = "Select distinct " + sTableName.Split(new Char[] { ',' })[0] + "." + sField + " from " + sTableName + " where " +
				//                "CPG_SIT_CIVICI.UK_CIVICO = CPG_SIT_CIVICI_FABBRICATI.UK_CIVICO and " +
				//                "CPG_SIT_CIVICI_FABBRICATI.FOGLIO_CT = CPG_SIT_FOGLIO_PART_CT.FOGLIO and " +
				//                "CPG_SIT_CIVICI_FABBRICATI.PARTICELLA_CT = CPG_SIT_FOGLIO_PART_CT.NUMERO and " +
				//                ((!string.IsNullOrEmpty(this.DataSit.UI)) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_FOGLIO_PART_CT.IMMOBILE") + " = " + RightTrimSit(this.DataSit.UI) + " and " : "") +
				//                ((!string.IsNullOrEmpty(this.DataSit.Sub)) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_FOGLIO_PART_CT.SUBALTERNO") + " = '" + LeftPadSit(RightTrimSit(this.DataSit.Sub), 4) + "' and " : "") +
				//                ((!string.IsNullOrEmpty(this.DataSit.Fabbricato)) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI.PK_SEQU_FABBRICATO") + " = " + RightTrimSit(this.DataSit.Fabbricato) + " and " : "") +
				//                ((!string.IsNullOrEmpty(this.DataSit.Foglio)) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI.FOGLIO_CT") + " = '" + RightTrimSit(this.DataSit.Foglio) + "' and " : "") +
				//                ((!string.IsNullOrEmpty(this.DataSit.Particella)) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_CIVICI_FABBRICATI.PARTICELLA_CT") + " = '" + RightTrimSit(this.DataSit.Particella) + "' and " : "") +
				//                ((!string.IsNullOrEmpty(this.DataSit.CodVia)) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_CIVICI.FK_STRADE") + " = '" + CodiceComune + LeftPadSit(RightTrimSit(this.DataSit.CodVia), 8, '0') + "' and " : "") +
				//                ((!string.IsNullOrEmpty(this.DataSit.Civico)) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_CIVICI.NUMERO") + " = " + RightTrimSit(Init.Utils.StringChecker.IsNumeric(this.DataSit.Civico) ? this.DataSit.Civico : "-1") + " and " : "") +
				//                ((!string.IsNullOrEmpty(this.DataSit.Esponente)) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_CIVICI.ESP") + " = '" + RightTrimSit(this.DataSit.Esponente) + "' and " : "") +
				//                ((!string.IsNullOrEmpty(this.DataSit.CodCivico)) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_CIVICI.UK_CIVICO") + " = '" + RightTrimSit(this.DataSit.CodCivico) + "' and " : "") +
				//                ((!string.IsNullOrEmpty(this.DataSit.Circoscrizione)) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_CIVICI.CIRCOSCRIZIONE") + " = '" + RightTrimSit(this.DataSit.Circoscrizione) + "' and " : "") +
				//                ((!string.IsNullOrEmpty(this.DataSit.Frazione)) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_CIVICI.DESCR_LOCALITA") + " = '" + RightTrimSit(this.DataSit.Frazione) + "' and " : "") +
				//                ((!string.IsNullOrEmpty(this.DataSit.CAP)) ? pDataBase.Specifics.RTrimFunction("CPG_SIT_CIVICI.CAP") + " = '" + RightTrimSit(this.DataSit.CAP) + "' and " : "");
				//            break;
				//    }
				//    break;
			}

			if (sQuery.EndsWith("and "))
				sQuery = sQuery.Remove(sQuery.Length - 4, 4) + " order by " + tableName.Split(new Char[] { ',' })[0] + "." + fieldsList;
			else if (sQuery.EndsWith("where "))
				sQuery = sQuery.Remove(sQuery.Length - 6, 6) + " order by " + tableName.Split(new Char[] { ',' })[0] + "." + fieldsList;
			else
				sQuery = sQuery + " order by " + tableName.Split(new Char[] { ',' })[0] + "." + fieldsList;

			return sQuery;
		}


		private string GetQuery(string[] aField, string sTableName, TipoQuery eTipoQuery)
		{
			string sQuery = string.Empty;
			string sField = string.Empty;

			foreach (string elem in aField)
				sField += elem + ",";

			sField = sField.Remove(sField.Length - 1);

			switch (sTableName)
			{
				case "VIEW_URB_SOTTOZONE,CAT_PARTICELLE_GAUSS":
					switch (eTipoQuery)
					{
						case TipoQuery.Elenco:
							sQuery = "Select " + sField + " from " +
									 GetCompleteTableName(sTableName).Split(new Char[] { ',' })[0] + " " +
									 "where " +
									 "SDO_RELATE(" + GetCompleteTableName(sTableName).Split(new Char[] { ',' })[0] + ".GEOMETRY, " +
									 "(select GEOMETRY from " + GetCompleteTableName(sTableName).Split(new Char[] { ',' })[1] + " where esc_foglio = '" + LeftPad(RightTrim(this.DataSit.Foglio), 5) + "' and esc_particella = '" + LeftPad(RightTrim(this.DataSit.Particella), 5) + "' and layer = 'PARTICELLE'),'mask =ANYINTERACT querytype=WINDOW') = 'TRUE'";
							break;
					}
					break;
			}

			if (sQuery.EndsWith("and "))
				sQuery = sQuery.Remove(sQuery.Length - 4, 4) + " order by " + sTableName.Split(new Char[] { ',' })[0] + "." + aField[0];
			else if (sQuery.EndsWith("where "))
				sQuery = sQuery.Remove(sQuery.Length - 6, 6) + " order by " + sTableName.Split(new Char[] { ',' })[0] + "." + aField[0];
			else
				sQuery = sQuery + " order by " + sTableName.Split(new Char[] { ',' })[0] + "." + aField[0];

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

		private RetSit GetElenco(string[] aField, string sTableName, IDataReader pDataReader)
		{
			RetSit pRetSit = new RetSit(true);
			EnsureConnectionIsOpen();

			//Ricavo il PK_PARTICELLE
			sCommandText = GetQuery("PK_PARTICELLE", "CAT_PARTICELLE_GAUSS", TipoQuery.Elenco);

			IDbCommand pCommand = InternalDatabaseConnection.CreateCommand(sCommandText);
			pDataReader = pCommand.ExecuteReader();

			int iCount = 1;
			string sDato = string.Empty;
			while (pDataReader.Read())
			{
				if (iCount > 1)
					throw new Exception("Troppi record");
				sDato = RightTrim(pDataReader["PK_PARTICELLE"].ToString());
				iCount++;
			}

			pCommand.Dispose();

			sCommandText = GetQuery(aField, sTableName, TipoQuery.Elenco);

			pCommand = InternalDatabaseConnection.CreateCommand(sCommandText);
			pDataReader = pCommand.ExecuteReader();

			List<string> list = new List<string>();
			int iIndex = 0;
			while (pDataReader.Read())
			{
				string sField1 = aField[0];
				string sDato1 = RightTrim(pDataReader[sField1].ToString());
				//Verifico se per per quel foglio e particella esistono strumenti urbanistici (con relative date di inizio validità e fine validità) uguali
				//e ne  prendo solo 1
				if (!list.Contains(sDato1))
				{
					pRetSit.DataMap.Add(sField1 + iIndex, sDato1);
					list.Add(sDato1);

					//Inizio validità
					string sField2 = aField[1];
					string sDato2 = RightTrim(pDataReader[sField2].ToString());
					pRetSit.DataMap.Add(sField2 + iIndex, sDato2);

					//Fine validità
					string sField3 = aField[2];
					string sDato3 = RightTrim(pDataReader[sField3].ToString());
					pRetSit.DataMap.Add(sField3 + iIndex, sDato3);
				}
				string sField4 = aField[3];
				string sDato4 = RightTrim(pDataReader[sField4].ToString());

				pRetSit.DataMap.Add(sField4 + iIndex, sDato4);

				string sField5 = aField[4];
				string sDato5 = RightTrim(pDataReader[sField5].ToString());

				sCommandText = "select mdsys.SDO_GEOM.SDO_AREA(the_geom,0.005) as AREA from " +
							   "(SELECT SDO_GEOM.SDO_INTERSECTION(p.GEOMETRY , s.geometry, 0.005) as the_geom " +
							   "FROM cpg_dbintegrato_esc.view_urb_sottozone s, cpg_dbintegrato_esc.cat_particelle_gauss p " +
							   "where p.pk_particelle = " + sDato + " and s.pk_sequ_sottozona = " + sDato5 + ")";

				IDbCommand pCommandArea = InternalDatabaseConnection.CreateCommand(sCommandText);
				IDataReader pDataReaderArea = pCommandArea.ExecuteReader();
				pDataReaderArea.Read();
				pRetSit.DataMap.Add("AREA" + iIndex, pDataReaderArea["AREA"].ToString());

				pCommandArea.Dispose();
				pDataReaderArea.Close();

				//File Name
				string sField6 = aField[5];
				string sDato6 = RightTrim(pDataReader[sField6].ToString());

				pRetSit.DataMap.Add(sField6 + iIndex, sDato6);

				iIndex++;
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
				if (!String.IsNullOrEmpty(this.DataSit.CodVia) || !String.IsNullOrEmpty(this.DataSit.Fabbricato))
				{
					pRetSit = ElencoCpgSitCivici("NUMERO", pDataReader);
				}
				else
				{
					pRetSit = RestituisciErroreSit(GetMessageList("NUMERO"), MessageCode.ElencoCivici, false);
				}
			}
			catch (CatastoException ex)
			{
				_logger.ErrorFormat("SITESC.ElencoCivici: CatastoException durante la lettura dei civici-> {0}", ex.ToString());

				pRetSit = RestituisciErroreSit(ex.Message, MessageCode.ElencoCivici, false);
			}
			catch (Exception ex)
			{
				_logger.ErrorFormat("SITESC.ElencoCivici: Exception durante la lettura dei civici-> {0}", ex.ToString());

				throw new Exception("Errore durante la restituzione dell'elenco dei civici. Metodo: ElencoCivici, modulo: SitEsc. " + ex.Message + "\r\n Query: " + sCommandText);
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
				if (!String.IsNullOrEmpty(this.DataSit.Civico) && (!String.IsNullOrEmpty(this.DataSit.CodVia) || !String.IsNullOrEmpty(this.DataSit.Fabbricato)))
				{
					pRetSit = ElencoCpgSitCivici("ESP", pDataReader);
				}
				else
				{
					pRetSit = RestituisciErroreSit(GetMessageList("ESP"), MessageCode.ElencoEsponenti, false);
				}
			}
			catch (CatastoException ex)
			{
				pRetSit = RestituisciErroreSit(ex.Message, MessageCode.ElencoEsponenti, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione dell'elenco degli esponenti. Metodo: ElencoEsponenti, modulo: SitEsc. " + ex.Message + "\r\n Query: " + sCommandText);
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
				if (!String.IsNullOrEmpty(this.DataSit.CodVia) || (!String.IsNullOrEmpty(this.DataSit.Foglio) && !String.IsNullOrEmpty(this.DataSit.Particella)))
				{
					pRetSit = ElencoCpgSitCiviciFabbricati("PK_SEQU_FABBRICATO", pDataReader);
				}
				else
				{
					pRetSit = RestituisciErroreSit(GetMessageList("PK_SEQU_FABBRICATO"), MessageCode.ElencoCivici, false);
				}
			}
			catch (CatastoException ex)
			{
				pRetSit = RestituisciErroreSit(ex.Message, MessageCode.ElencoFabbricati, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione dell'elenco dei fabbricati. Metodo: ElencoFabbricati, modulo: SitEsc. " + ex.Message + "\r\n Query: " + sCommandText);
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
				pRetSit = ElencoCpgSitSub("FOGLIO", pDataReader);

				char[] charsToTrim = { '0' };

				for (int iCount = 0; iCount < pRetSit.DataCollection.Count; iCount++)
				{
					pRetSit.DataCollection[iCount] = LeftTrim(pRetSit.DataCollection[iCount], charsToTrim);
				}
			}
			catch (CatastoException ex)
			{
				pRetSit = RestituisciErroreSit(ex.Message, MessageCode.ElencoFogli, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione dell'elenco dei fogli. Metodo: ElencoFogli, modulo: SitEsc. " + ex.Message + "\r\n Query: " + sCommandText);
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
				if ((!String.IsNullOrEmpty(this.DataSit.Fabbricato)) || !String.IsNullOrEmpty(this.DataSit.Foglio))
				{
					pRetSit = ElencoCpgSitSub("NUMERO", pDataReader);

					char[] charsToTrim = { '0' };

					for (int iCount = 0; iCount < pRetSit.DataCollection.Count; iCount++)
					{
						pRetSit.DataCollection[iCount] = LeftTrim(pRetSit.DataCollection[iCount], charsToTrim);
					}
				}
				else
				{
					pRetSit = RestituisciErroreSit(GetMessageList("CPG_FOGLIO_PART_CT.NUMERO"), MessageCode.ElencoParticelle, false);
				}
			}
			catch (CatastoException ex)
			{
				pRetSit = RestituisciErroreSit(ex.Message, MessageCode.ElencoParticelle, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione dell'elenco delle particella. Metodo: ElencoParticelle, modulo: SitEsc. " + ex.Message + "\r\n Query: " + sCommandText);
			}
			finally
			{
				if (pDataReader != null)
					pDataReader.Close();
				InternalDatabaseConnection.Connection.Close();
			}

			return pRetSit;
		}

		public override RetSit ElencoSub()
		{
			IDataReader pDataReader = null;
			RetSit pRetSit;

			try
			{
				if ((!String.IsNullOrEmpty(this.DataSit.Fabbricato)) || (!String.IsNullOrEmpty(this.DataSit.Foglio) && !String.IsNullOrEmpty(this.DataSit.Particella)))
				{
					pRetSit = ElencoCpgSitSub("SUBALTERNO", pDataReader);

					char[] charsToTrim = { '0' };

					for (int iCount = 0; iCount < pRetSit.DataCollection.Count; iCount++)
					{
						pRetSit.DataCollection[iCount] = LeftTrim(pRetSit.DataCollection[iCount], charsToTrim);
					}
				}
				else
				{
					pRetSit = RestituisciErroreSit(GetMessageList("SUBALTERNO"), MessageCode.ElencoCivici, false);
				}
			}
			catch (CatastoException ex)
			{
				pRetSit = RestituisciErroreSit(ex.Message, MessageCode.ElencoSub, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione dell'elenco dei sub. Metodo: ElencoSub, modulo: SitEsc. " + ex.Message + "\r\n Query: " + sCommandText);
			}
			finally
			{
				if (pDataReader != null)
					pDataReader.Close();
				InternalDatabaseConnection.Connection.Close();
			}

			return pRetSit;
		}

		public override RetSit ElencoUI()
		{
			IDataReader pDataReader = null;
			RetSit pRetSit;

			try
			{
				if ((!String.IsNullOrEmpty(this.DataSit.Fabbricato)) || (!String.IsNullOrEmpty(this.DataSit.Foglio) && !String.IsNullOrEmpty(this.DataSit.Particella) && !String.IsNullOrEmpty(this.DataSit.Sub)))
				{
					pRetSit = ElencoCpgSitSub("IMMOBILE", pDataReader);
				}
				else
				{
					pRetSit = RestituisciErroreSit(GetMessageList("IMMOBILE"), MessageCode.ElencoCivici, false);
				}
			}
			catch (CatastoException ex)
			{
				pRetSit = RestituisciErroreSit(ex.Message, MessageCode.ElencoUI, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione dell'elenco delle UI. Metodo: ElencoUI, modulo: SitEsc. " + ex.Message + "\r\n Query: " + sCommandText);
			}
			finally
			{
				if (pDataReader != null)
					pDataReader.Close();
				InternalDatabaseConnection.Connection.Close();
			}

			return pRetSit;
		}

		public override RetSit ElencoSottoZone()
		{
			IDataReader pDataReader = null;
			RetSit pRetSit;

			try
			{
				if (!String.IsNullOrEmpty(this.DataSit.Foglio) && !String.IsNullOrEmpty(this.DataSit.Particella))
				{
					pRetSit = GetElenco("CODICE_SOTTOZONA", "VIEW_URB_SOTTOZONE,CAT_PARTICELLE_GAUSS", pDataReader);
				}
				else
				{
					pRetSit = RestituisciErroreSit("Non è possibile ottenere la lista delle sottozone per insufficienza di dati:il foglio e la particella devono essere forniti", MessageCode.ElencoSottoZone, false);
				}
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione dell'elenco delle sottozone. Metodo: ElencoSottoZone, modulo: SitEsc. " + ex.Message + "\r\n Query: " + sCommandText);
			}
			finally
			{
				if (pDataReader != null)
					pDataReader.Close();
				InternalDatabaseConnection.Connection.Close();
			}

			return pRetSit;
		}

		public override RetSit ElencoDatiUrbanistici()
		{
			IDataReader pDataReader = null;
			RetSit pRetSit;

			try
			{
				if (!String.IsNullOrEmpty(this.DataSit.Foglio) && !String.IsNullOrEmpty(this.DataSit.Particella))
				{
					string[] aField = { "STRUM_URBANISTICO", "INIZIO_VALIDITA", "FINE_VALIDITA", "CODICE_SOTTOZONA", "PK_SEQU_SOTTOZONA", "DOC_FILENAME" };
					pRetSit = GetElenco(aField, "VIEW_URB_SOTTOZONE,CAT_PARTICELLE_GAUSS", pDataReader);
				}
				else
				{
					pRetSit = RestituisciErroreSit("Non è possibile ottenere la lista dei dati urbanistici per insufficienza di dati:il foglio e la particella devono essere forniti", MessageCode.ElencoDatiUrbanistici, false);
				}
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione dell'elenco dei dati urbanistici. Metodo: ElencoDatiUrbanistici, modulo: SitEsc. " + ex.Message + "\r\n Query: " + sCommandText);
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

		protected override string GetCAP()
		{
			string sRetVal = "";
			IDataReader pDataReader = null;

			try
			{
				sRetVal = GetCpgSitCivici("CAP", pDataReader, TipoQuery.Elenco);
			}
			catch (CatastoException ex)
			{
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione di un CAP. Metodo: GetCAP, modulo: SitEsc. " + ex.Message + "\r\n Query: " + sCommandText);
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
				string sElem = GetCpgSitCivici("CAP", pDataReader, TipoQuery.Validazione);
				if (!String.IsNullOrEmpty(sElem))
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
				throw new Exception("Errore durante la validazione di un CAP. Metodo: VerificaCAP, modulo: SitEsc. " + ex.Message + "\r\n Query: " + sCommandText);
			}
			finally
			{
				if (pDataReader != null)
					pDataReader.Close();
				InternalDatabaseConnection.Connection.Close();
			}

			return pRetSit;
		}

		protected override string GetFrazione()
		{
			string sRetVal = "";
			IDataReader pDataReader = null;

			try
			{
				sRetVal = GetCpgSitCivici("DESCR_LOCALITA", pDataReader, TipoQuery.Elenco);
			}
			catch (CatastoException ex)
			{
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione della località. Metodo: GetFrazione, modulo: SitEsc. " + ex.Message + "\r\n Query: " + sCommandText);
			}
			finally
			{
				if (pDataReader != null)
					pDataReader.Close();
				InternalDatabaseConnection.Connection.Close();
			}

			return sRetVal;
		}

		protected override RetSit VerificaFrazione()
		{
			RetSit pRetSit;
			IDataReader pDataReader = null;

			try
			{
				string sElem = GetCpgSitCivici("DESCR_LOCALITA", pDataReader, TipoQuery.Validazione);
				if (!String.IsNullOrEmpty(sElem))
				{
					pRetSit = new RetSit(true);
					this.DataSit.Frazione = sElem;
				}
				else
				{
					pRetSit = RestituisciErroreSit(GetMessageValidate("DESCR_LOCALITA"), MessageCode.FrazioneValidazione, false);
				}
			}
			catch (CatastoException ex)
			{
				pRetSit = RestituisciErroreSit(ex.Message, MessageCode.FrazioneValidazione, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la validazione di una località. Metodo: VerificaFrazione, modulo: SitEsc. " + ex.Message + "\r\n Query: " + sCommandText);
			}
			finally
			{
				if (pDataReader != null)
					pDataReader.Close();
				InternalDatabaseConnection.Connection.Close();
			}

			return pRetSit;
		}

		protected override string GetCircoscrizione()
		{
			string sRetVal = "";
			IDataReader pDataReader = null;

			try
			{
				sRetVal = GetCpgSitCivici("CIRCOSCRIZIONE", pDataReader, TipoQuery.Elenco);
			}
			catch (CatastoException ex)
			{
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione di una circoscrizione. Metodo: GetCircoscrizione, modulo: SitEsc. " + ex.Message + "\r\n Query: " + sCommandText);
			}
			finally
			{
				if (pDataReader != null)
					pDataReader.Close();
				InternalDatabaseConnection.Connection.Close();
			}

			return sRetVal;
		}

		protected override RetSit VerificaCircoscrizione()
		{
			RetSit pRetSit;
			IDataReader pDataReader = null;

			try
			{
				string sElem = GetCpgSitCivici("CIRCOSCRIZIONE", pDataReader, TipoQuery.Validazione);
				if (!String.IsNullOrEmpty(sElem))
				{
					pRetSit = new RetSit(true);
					this.DataSit.Circoscrizione = sElem;
				}
				else
				{
					pRetSit = RestituisciErroreSit(GetMessageValidate("CIRCOSCRIZIONE"), MessageCode.CircoscrizioneValidazione, false);
				}
			}
			catch (CatastoException ex)
			{
				pRetSit = RestituisciErroreSit(ex.Message, MessageCode.CircoscrizioneValidazione, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la validazione di una circoscrizione. Metodo: VerificaCircoscrizione, modulo: SitEsc. " + ex.Message + "\r\n Query: " + sCommandText);
			}
			finally
			{
				if (pDataReader != null)
					pDataReader.Close();
				InternalDatabaseConnection.Connection.Close();
			}

			return pRetSit;
		}



		protected override string GetCodFabbricato()
		{
			string sRetVal = "";
			IDataReader pDataReader = null;

			try
			{
				sRetVal = GetCpgSitCiviciFabbricati("PK_SEQU_FABBRICATO", pDataReader, TipoQuery.Elenco);
			}
			catch (CatastoException ex)
			{
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione di un fabbricato. Metodo: GetCodFabbricato, modulo: SitEsc. " + ex.Message + "\r\n Query: " + sCommandText);
			}
			finally
			{
				if (pDataReader != null)
					pDataReader.Close();
				InternalDatabaseConnection.Connection.Close();
			}

			return sRetVal;
		}

		protected override RetSit VerificaFabbricato()
		{
			RetSit pRetSit;
			IDataReader pDataReader = null;

			try
			{
				string sElem = GetCpgSitCiviciFabbricati("PK_SEQU_FABBRICATO", pDataReader, TipoQuery.Validazione);
				if (!String.IsNullOrEmpty(sElem))
				{
					pRetSit = new RetSit(true);
					this.DataSit.Fabbricato = sElem;
				}
				else
				{
					pRetSit = RestituisciErroreSit(GetMessageValidate("PK_SEQU_FABBRICATO"), MessageCode.FabbricatoValidazione, false);
				}
			}
			catch (CatastoException ex)
			{
				pRetSit = RestituisciErroreSit(ex.Message, MessageCode.FabbricatoValidazione, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la validazione di un fabbricato. Metodo: VerificaFabbricato, modulo: SitEsc. " + ex.Message + "\r\n Query: " + sCommandText);
			}
			finally
			{
				if (pDataReader != null)
					pDataReader.Close();
				InternalDatabaseConnection.Connection.Close();
			}

			return pRetSit;
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





		protected override string GetFoglio()
		{
			string sRetVal = "";
			IDataReader pDataReader = null;

			try
			{
				sRetVal = GetCpgSitSub("FOGLIO", pDataReader, TipoQuery.Elenco);

				char[] charsToTrim = { '0' };
				sRetVal = LeftTrim(sRetVal, charsToTrim);
			}
			catch (CatastoException ex)
			{
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione di un foglio. Metodo: GetFoglio, modulo: SitEsc. " + ex.Message + "\r\n Query: " + sCommandText);
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
				string sElem = GetCpgSitSub("FOGLIO", pDataReader, TipoQuery.Validazione);
				if (!String.IsNullOrEmpty(sElem))
				{
					pRetSit = new RetSit(true);

					char[] charsToTrim = { '0' };
					sElem = LeftTrim(sElem, charsToTrim);

					this.DataSit.Foglio = sElem;
				}
				else
				{
					pRetSit = RestituisciErroreSit(GetMessageValidate("FOGLIO"), MessageCode.FoglioValidazione, false);
				}
			}
			catch (CatastoException ex)
			{
				pRetSit = RestituisciErroreSit(ex.Message, MessageCode.FoglioValidazione, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la validazione di un foglio. Metodo: VerificaFoglio, modulo: SitEsc. " + ex.Message + "\r\n Query: " + sCommandText);
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
				sRetVal = GetCpgSitSub("NUMERO", pDataReader, TipoQuery.Elenco);

				char[] charsToTrim = { '0' };
				sRetVal = LeftTrim(sRetVal, charsToTrim);
			}
			catch (CatastoException ex)
			{
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione di una particella. Metodo: GetParticella, modulo: SitEsc. " + ex.Message + "\r\n Query: " + sCommandText);
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
				string sElem = GetCpgSitSub("NUMERO", pDataReader, TipoQuery.Validazione);
				if (!String.IsNullOrEmpty(sElem))
				{
					pRetSit = new RetSit(true);

					char[] charsToTrim = { '0' };
					sElem = LeftTrim(sElem, charsToTrim);

					this.DataSit.Particella = sElem;
				}
				else
				{
					pRetSit = RestituisciErroreSit(GetMessageValidate("CPG_FOGLIO_PART_CT.NUMERO"), MessageCode.ParticellaValidazione, false);
				}
			}
			catch (CatastoException ex)
			{
				pRetSit = RestituisciErroreSit(ex.Message, MessageCode.ParticellaValidazione, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la validazione di una particella. Metodo: VerificaParticella, modulo: SitEsc. " + ex.Message + "\r\n Query: " + sCommandText);
			}
			finally
			{
				if (pDataReader != null)
					pDataReader.Close();
				InternalDatabaseConnection.Connection.Close();
			}

			return pRetSit;
		}

		protected override string GetSub()
		{
			string sRetVal = "";
			IDataReader pDataReader = null;

			try
			{
				sRetVal = GetCpgSitSub("SUBALTERNO", pDataReader, TipoQuery.Elenco);

				char[] charsToTrim = { '0' };
				sRetVal = LeftTrim(sRetVal, charsToTrim);
			}
			catch (CatastoException ex)
			{
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione di un sub. Metodo: GetSub, modulo: SitEsc. " + ex.Message + "\r\n Query: " + sCommandText);
			}
			finally
			{
				if (pDataReader != null)
					pDataReader.Close();
				InternalDatabaseConnection.Connection.Close();
			}

			return sRetVal;
		}

		protected override RetSit VerificaSub()
		{
			RetSit pRetSit;
			IDataReader pDataReader = null;

			try
			{
				string sElem = GetCpgSitSub("SUBALTERNO", pDataReader, TipoQuery.Validazione);
				if (!String.IsNullOrEmpty(sElem))
				{
					pRetSit = new RetSit(true);

					char[] charsToTrim = { '0' };
					sElem = LeftTrim(sElem, charsToTrim);

					this.DataSit.Sub = sElem;
				}
				else
				{
					pRetSit = RestituisciErroreSit(GetMessageValidate("SUBALTERNO"), MessageCode.SubValidazione, false);
				}
			}
			catch (CatastoException ex)
			{
				pRetSit = RestituisciErroreSit(ex.Message, MessageCode.SubValidazione, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la validazione di un sub. Metodo: VerificaSub, modulo: SitEsc. " + ex.Message + "\r\n Query: " + sCommandText);
			}
			finally
			{
				if (pDataReader != null)
					pDataReader.Close();
				InternalDatabaseConnection.Connection.Close();
			}

			return pRetSit;
		}

		protected override string GetUI()
		{
			string sRetVal = "";
			IDataReader pDataReader = null;

			try
			{
				sRetVal = GetCpgSitSub("IMMOBILE", pDataReader, TipoQuery.Elenco);
			}
			catch (CatastoException ex)
			{
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione di un'UI. Metodo: GetUI, modulo: SitEsc. " + ex.Message + "\r\n Query: " + sCommandText);
			}
			finally
			{
				if (pDataReader != null)
					pDataReader.Close();
				InternalDatabaseConnection.Connection.Close();
			}

			return sRetVal;
		}

		protected override RetSit VerificaUI()
		{
			RetSit pRetSit;
			IDataReader pDataReader = null;

			try
			{
				string sElem = GetCpgSitSub("IMMOBILE", pDataReader, TipoQuery.Validazione);
				if (!String.IsNullOrEmpty(sElem))
				{
					pRetSit = new RetSit(true);
					this.DataSit.UI = sElem;
				}
				else
				{
					pRetSit = RestituisciErroreSit(GetMessageValidate("IMMOBILE"), MessageCode.UIValidazione, false);
				}
			}
			catch (CatastoException ex)
			{
				pRetSit = RestituisciErroreSit(ex.Message, MessageCode.UIValidazione, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la validazione di un'UI. Metodo: VerificaUI, modulo: SitEsc. " + ex.Message + "\r\n Query: " + sCommandText);
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
				sRetVal = GetCpgSitCivici("NUMERO", pDataReader, TipoQuery.Elenco);
			}
			catch (CatastoException ex)
			{
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione di un civico. Metodo: GetCivico, modulo: SitEsc. " + ex.Message + "\r\n Query: " + sCommandText);
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
					string sElem = GetCpgSitCivici("NUMERO", pDataReader, TipoQuery.Validazione);
					if (!String.IsNullOrEmpty(sElem))
					{
						pRetSit = new RetSit(true);
						this.DataSit.Civico = sElem;
					}
					else
					{
						pRetSit = RestituisciErroreSit(GetMessageValidate("NUMERO"), MessageCode.CivicoValidazione, false);
					}
				}
				catch (CatastoException ex)
				{
					pRetSit = RestituisciErroreSit(ex.Message, MessageCode.CivicoValidazione, false);
				}
				catch (Exception ex)
				{
					throw new Exception("Errore durante la validazione di un civico. Metodo: VerificaCivico, modulo: SitEsc. " + ex.Message + "\r\n Query: " + sCommandText);
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

		protected override string GetEsponente()
		{
			string sRetVal = "";
			IDataReader pDataReader = null;

			try
			{
				sRetVal = GetCpgSitCivici("ESP", pDataReader, TipoQuery.Elenco);
			}
			catch (CatastoException ex)
			{
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione di un esponente. Metodo: GetEsponente, modulo: SitEsc. " + ex.Message + "\r\n Query: " + sCommandText);
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
				string sElem = GetCpgSitCivici("ESP", pDataReader, TipoQuery.Validazione);
				if (!String.IsNullOrEmpty(sElem))
				{
					pRetSit = new RetSit(true);
					this.DataSit.Esponente = sElem;
				}
				else
				{
					pRetSit = RestituisciErroreSit(GetMessageValidate("ESP"), MessageCode.EsponenteValidazione, false);
				}
			}
			catch (CatastoException ex)
			{
				pRetSit = RestituisciErroreSit(ex.Message, MessageCode.EsponenteValidazione, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la validazione di un esponente. Metodo: VerificaEsponente, modulo: SitEsc. " + ex.Message + "\r\n Query: " + sCommandText);
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
				sRetVal = GetCpgSitCivici("UK_CIVICO", pDataReader, TipoQuery.Elenco);
			}
			catch (CatastoException ex)
			{
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione di un codice civico. Metodo: GetCodCivico, modulo: SitEsc. " + ex.Message + "\r\n Query: " + sCommandText);
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
				sRetVal = GetCpgSitCivici("FK_STRADE", pDataReader, TipoQuery.Elenco);

				if (!string.IsNullOrEmpty(sRetVal))
				{
					//To remove CodiceComune
					sRetVal = sRetVal.Remove(0, 4);

					int iIndex = sRetVal.IndexOfAny(new Char[] { '1', '2', '3', '4', '5', '6', '7', '8', '9' });
					if (iIndex != -1)
						sRetVal = sRetVal.Remove(0, iIndex);
				}
			}
			catch (CatastoException ex)
			{
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione di un codice via. Metodo: GetCodVia, modulo: SitEsc. " + ex.Message + "\r\n Query: " + sCommandText);
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
				SitIntegrationService.NomiCampiSit.Frazione,
				SitIntegrationService.NomiCampiSit.Circoscrizione,
				//SitMgr.NomiCampiSit.Interno,
				//SitMgr.NomiCampiSit.EsponenteInterno,
				SitIntegrationService.NomiCampiSit.Fabbricato,
				//SitMgr.NomiCampiSit.Sezione,
				SitIntegrationService.NomiCampiSit.TipoCatasto,
				SitIntegrationService.NomiCampiSit.Foglio,
				SitIntegrationService.NomiCampiSit.Particella,
				SitIntegrationService.NomiCampiSit.Sub,
				SitIntegrationService.NomiCampiSit.UnitaImmobiliare,
				SitIntegrationService.NomiCampiSit.Civico,
				SitIntegrationService.NomiCampiSit.Cap,
				SitIntegrationService.NomiCampiSit.CodiceVia,
				SitIntegrationService.NomiCampiSit.CodiceCivico
			};
		}
	}
}
