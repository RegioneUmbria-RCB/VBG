using System;
using System.Data;
using System.Diagnostics;
using Init.SIGePro.Collection;
using Init.SIGePro.Manager;
using Init.SIGePro.Data;
using Init.SIGePro.Verticalizzazioni;
using PersonalLib2.Data;
using System.Runtime.InteropServices;
using Init.SIGePro.Exceptions.SIT;
using Init.SIGePro.Sit.ValidazioneFormale;
using Init.SIGePro.Sit.Data;
using Init.SIGePro.Sit.Manager;

namespace Init.SIGePro.Sit
{
	class SIT_INITMAPGUIDE : SitBase
	{
		public SIT_INITMAPGUIDE()
			: base(new ValidazioneFormaleTramiteCodiceCivicoService())
		{
		}

		private string sConnectionString = "";
		private string sProvider = "";
		private string sCommandText = "";

		#region Utility
		public override void SetupVerticalizzazione()
		{
			GetParametriFromVertSITINITMAPGUIDE();
		}

		private void GetParametriFromVertSITINITMAPGUIDE()
		{
			try
			{
				VerticalizzazioneSitInitmapguide pSitInitMapGuide;
				DataBase dDataBase = this.Database;

				pSitInitMapGuide = new VerticalizzazioneSitInitmapguide(this.IdComuneAlias, this.Software);

				if (pSitInitMapGuide.Attiva)
				{
					sConnectionString = pSitInitMapGuide.Connectionstring;
					sProvider = pSitInitMapGuide.Provider;

					ProviderType provider = (ProviderType)Enum.Parse(typeof(ProviderType), sProvider, false);
					InternalDatabaseConnection = new DataBase(sConnectionString, provider);
				}
				else
					throw new Exception("La verticalizzazione SIT_INITMAPGUIDE non è attiva.\r\n");

			}
			catch (Exception ex)
			{
				throw new Exception("Errore generato durante la lettura della verticalizzazione SIT_INITMAPGUIDE. Metodo: GetParametriFromVertSITINITMAPGUIDE, modulo: SitInitMapGuide. " + ex.Message + "\r\n");
			}
		}

		private string GetMessageValidate(string sField)
		{
			string sValue = string.Empty;

			switch (sField)
			{
				case "SEZ":
					sValue = "La sezione " + DataSit.Sezione + " non è valida per i dati inseriti";
					break;
				case "FOGLIO":
					sValue = "Il foglio " + DataSit.Foglio + " non è valido per i dati inseriti";
					break;
				case "NUMERO":
					sValue = "La particella " + DataSit.Particella + " non è valida per i dati inseriti";
					break;
				case "DESCRIZI_1":
					sValue = "La circoscrizione " + DataSit.Frazione + " non è valida per i dati inseriti";
					break;
				case "TEXT":
					sValue = "Il civico " + DataSit.Civico + " non è valido per i dati inseriti";
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
					sValue = "Non è possibile ottenere la lista dei fogli per insufficienza di dati: il catasto e la sezione devono essere forniti";
					break;
				case "NUMERO":
					sValue = "Non è possibile ottenere la lista delle particelle per insufficienza di dati: il catasto, la sezione ed il foglio devono essere forniti";
					break;
				case "SEZ":
					sValue = "Non è possibile ottenere la lista delle sezioni per insufficienza di dati: il catasto deve essere fornito";
					break;
				case "TEXT":
					sValue = "Non è possibile ottenere la lista dei civici per insufficienza di dati: la via deve essere fornita";
					break;
			}

			return sValue;
		}

		private RetSit ElencoCivici_All(string sField, IDataReader pDataReader)
		{
			RetSit pRetSit = null;

            //if (string.IsNullOrEmpty(DataSit.TipoCatasto))
            //    throw new CatastoException(GetMessageList(sField));
            //else
            //{
				if (string.IsNullOrEmpty(DataSit.TipoCatasto) || (this.DataSit.TipoCatasto == "F"))
					pRetSit = GetElenco(sField, "CIVICI_ALL", pDataReader);
				else
					pRetSit = new RetSit(true);
            //}

			return pRetSit;
		}

		private RetSit ElencoTerreni(string sField, IDataReader pDataReader)
		{
			RetSit pRetSit = null;

			if (string.IsNullOrEmpty(DataSit.TipoCatasto))
				throw new CatastoException(GetMessageList(sField));
			else
			{
				if (this.DataSit.TipoCatasto == "F")
					pRetSit = GetElenco(sField, "TERRENI", pDataReader);
				else
				{
					if (IsCampiToponomasticaImmobileVuoti())
						pRetSit = GetElenco(sField, "TERRENI", pDataReader);
					else
						pRetSit = new RetSit(true);
				}
			}

			return pRetSit;
		}

		private string GetCivici_All(string sField, IDataReader pDataReader, TipoQuery eTipoQuery)
		{
			string sRetVal = string.Empty;

			if (sField == "COD_VIA")
			{
				sRetVal = GetElemento(sField, "CIVICI_ALL", pDataReader, eTipoQuery);
			}
			else
			{
				if (string.IsNullOrEmpty(DataSit.TipoCatasto))
					sRetVal = GetElemento(sField, "CIVICI_ALL", pDataReader, eTipoQuery);
				else
				{
					if (this.DataSit.TipoCatasto == "F")
						sRetVal = GetElemento(sField, "CIVICI_ALL", pDataReader, eTipoQuery);
					else
						throw new CatastoException(GetMessageValidate(sField));
				}
			}

			return sRetVal;
		}

		private string GetTerreni(string sField, IDataReader pDataReader, TipoQuery eTipoQuery)
		{
			string sRetVal = string.Empty;

			if (string.IsNullOrEmpty(DataSit.TipoCatasto))
				sRetVal = GetElemento(sField, "TERRENI", pDataReader, eTipoQuery);
			else
			{
				if (this.DataSit.TipoCatasto == "F")
					sRetVal = GetElemento(sField, "TERRENI", pDataReader, eTipoQuery);
				else
				{
					if (IsCampiToponomasticaImmobileVuoti())
						sRetVal = GetElemento(sField, "TERRENI", pDataReader, eTipoQuery);
					else
						throw new CatastoException(GetMessageValidate(sField));
				}
			}

			return sRetVal;
		}

		private string GetQuery(string sField, string sTableName, TipoQuery eTipoQuery)
		{
			string sQuery = string.Empty;

			switch (sTableName)
			{
				case "CIVICI_ALL":
					switch (eTipoQuery)
					{
						case TipoQuery.Elenco:
							sQuery = "Select distinct " + sTableName + "." + sField + " from CIVICI_ALL where " +
								(((!string.IsNullOrEmpty(this.DataSit.CodVia)) && (sField != "COD_VIA")) ? InternalDatabaseConnection.Specifics.RTrimFunction("CIVICI_ALL.COD_VIA") + " = " + RightTrim(this.DataSit.CodVia) + " and " : "") +
								(((!string.IsNullOrEmpty(this.DataSit.Civico)) && (sField != "TEXT")) ? InternalDatabaseConnection.Specifics.RTrimFunction("CIVICI_ALL.TEXT") + " = '" + RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Civico) ? this.DataSit.Civico : "-1") + "' and " : "") +
								(((!string.IsNullOrEmpty(this.DataSit.Circoscrizione)) && (sField != "DESCRIZI_1")) ? InternalDatabaseConnection.Specifics.RTrimFunction("CIVICI_ALL.DESCRIZI_1") + " = '" + RightTrim(this.DataSit.Circoscrizione) + "' and " : "") +
								(((!string.IsNullOrEmpty(this.DataSit.CodCivico)) && (sField != "FEATID")) ? InternalDatabaseConnection.Specifics.RTrimFunction("CIVICI_ALL.FEATID") + " = '" + RightTrim(this.DataSit.CodCivico) + "' and " : "");
							break;
						case TipoQuery.Validazione:
							sQuery = "Select distinct " + sTableName + "." + sField + " from CIVICI_ALL where " +
								(((!string.IsNullOrEmpty(this.DataSit.CodVia))) ? InternalDatabaseConnection.Specifics.RTrimFunction("CIVICI_ALL.COD_VIA") + " = " + RightTrim(this.DataSit.CodVia) + " and " : "") +
								(((!string.IsNullOrEmpty(this.DataSit.Civico))) ? InternalDatabaseConnection.Specifics.RTrimFunction("CIVICI_ALL.TEXT") + " = '" + RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Civico) ? this.DataSit.Civico : "-1") + "' and " : "") +
								(((!string.IsNullOrEmpty(this.DataSit.Circoscrizione))) ? InternalDatabaseConnection.Specifics.RTrimFunction("CIVICI_ALL.DESCRIZI_1") + " = '" + RightTrim(this.DataSit.Circoscrizione) + "' and " : "") +
								(((!string.IsNullOrEmpty(this.DataSit.CodCivico))) ? InternalDatabaseConnection.Specifics.RTrimFunction("CIVICI_ALL.FEATID") + " = " + RightTrim(this.DataSit.CodCivico) + " and " : "");
							break;
					}
					break;
				case "TERRENI":
					switch (eTipoQuery)
					{
						case TipoQuery.Elenco:
							sQuery = "Select distinct " + sTableName + "." + sField + " from TERRENI where " +
								(((!string.IsNullOrEmpty(this.DataSit.Sezione)) && (sField != "SEZ")) ? InternalDatabaseConnection.Specifics.RTrimFunction("TERRENI.SEZ") + " = '" + RightTrim(this.DataSit.Sezione) + "' and " : "") +
								(((!string.IsNullOrEmpty(this.DataSit.Foglio)) && (sField != "FOGLIO")) ? InternalDatabaseConnection.Specifics.RTrimFunction("TERRENI.FOGLIO") + " = " + RightTrim(this.DataSit.Foglio) + " and " : "") +
								(((!string.IsNullOrEmpty(this.DataSit.Particella)) && (sField != "NUMERO")) ? InternalDatabaseConnection.Specifics.RTrimFunction("TERRENI.NUMERO") + " = '" + RightTrim(this.DataSit.Particella) + "' " : "");
							break;
						case TipoQuery.Validazione:
							sQuery = "Select distinct " + sTableName + "." + sField + " from TERRENI where " +
								(((!string.IsNullOrEmpty(this.DataSit.Sezione))) ? InternalDatabaseConnection.Specifics.RTrimFunction("TERRENI.SEZ") + " = '" + RightTrim(this.DataSit.Sezione) + "' and " : "") +
								(((!string.IsNullOrEmpty(this.DataSit.Foglio))) ? InternalDatabaseConnection.Specifics.RTrimFunction("TERRENI.FOGLIO") + " = " + RightTrim(this.DataSit.Foglio) + " and " : "") +
								(((!string.IsNullOrEmpty(this.DataSit.Particella))) ? InternalDatabaseConnection.Specifics.RTrimFunction("TERRENI.NUMERO") + " = '" + RightTrim(this.DataSit.Particella) + "' " : "");
							break;
					}
					break;
			}

			if (sQuery.EndsWith("and "))
				sQuery = sQuery.Remove(sQuery.Length - 4, 4) + " order by " + sTableName.Split(new Char[] { ',' })[0] + "." + sField;
			else if (sQuery.EndsWith("where "))
				sQuery = sQuery.Remove(sQuery.Length - 6, 6) + " order by " + sTableName.Split(new Char[] { ',' })[0] + "." + sField;
			else
				sQuery = sQuery + " order by " + sTableName.Split(new Char[] { ',' })[0] + "." + sField;

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
				//Istruzione RightTrimSit usata per togliere eventuali spazi a destra
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
				//Istruzione RightTrimSit usata per eliminare eventuali spazi a destra
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
				if (!Init.Utils.StringChecker.IsStringEmpty(this.DataSit.CodVia))
				{
					pRetSit = ElencoCivici_All("TEXT", pDataReader);
				}
				else
				{
					pRetSit = RestituisciErroreSit(GetMessageList("TEXT"), MessageCode.ElencoCivici, false);
				}
			}
			catch (CatastoException ex)
			{
				pRetSit = RestituisciErroreSit(ex.Message, MessageCode.ElencoCivici, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione dell'elenco dei civici. Metodo: ElencoCivici, modulo: SitInitMapGuide. " + ex.Message + "\r\n Query: " + sCommandText);
			}
			finally
			{
				if (pDataReader != null)
					pDataReader.Close();
				InternalDatabaseConnection.Connection.Close();
			}

			return pRetSit;
		}

		
		public override RetSit ElencoFrazioni()
		{
			IDataReader pDataReader = null;
			RetSit pRetSit;

			try
			{
				if (!Init.Utils.StringChecker.IsStringEmpty(this.DataSit.Civico) && !Init.Utils.StringChecker.IsStringEmpty(this.DataSit.CodVia))
				{
					pRetSit = ElencoCivici_All("DESCRIZI_1", pDataReader);
				}
				else
				{
					pRetSit = RestituisciErroreSit(GetMessageList("DESCRIZI_1"), MessageCode.ElencoCircoscrizioni, false);
				}
			}
			catch (CatastoException ex)
			{
				pRetSit = RestituisciErroreSit(ex.Message, MessageCode.ElencoFrazioni, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione dell'elenco delle frazioni. Metodo: ElencoFrazioni, modulo: SitInitMapGuide. " + ex.Message + "\r\n Query: " + sCommandText);
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
				pRetSit = ElencoTerreni("SEZ", pDataReader);

			}
			catch (CatastoException ex)
			{
				pRetSit = RestituisciErroreSit(ex.Message, MessageCode.ElencoSezioni, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione dell'elenco delle sezioni. Metodo: ElencoSezioni, modulo: SitInitMapGuide. " + ex.Message + "\r\n Query: " + sCommandText);
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
				if (!Init.Utils.StringChecker.IsStringEmpty(this.DataSit.Sezione))
				{
					pRetSit = ElencoTerreni("FOGLIO", pDataReader);
				}
				else
				{
					pRetSit = RestituisciErroreSit(GetMessageList("FOGLIO"), MessageCode.ElencoFogli, false);
				}
			}
			catch (CatastoException ex)
			{
				pRetSit = RestituisciErroreSit(ex.Message, MessageCode.ElencoFogli, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione dell'elenco dei fogli. Metodo: ElencoFogli, modulo: SitInitMapGuide. " + ex.Message + "\r\n Query: " + sCommandText);
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
				if (!Init.Utils.StringChecker.IsStringEmpty(this.DataSit.Sezione) && !Init.Utils.StringChecker.IsStringEmpty(this.DataSit.Foglio))
				{
					pRetSit = ElencoTerreni("NUMERO", pDataReader);
				}
				else
				{
					pRetSit = RestituisciErroreSit(GetMessageList("NUMERO"), MessageCode.ElencoParticelle, false);
				}
			}
			catch (CatastoException ex)
			{
				pRetSit = RestituisciErroreSit(ex.Message, MessageCode.ElencoParticelle, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione dell'elenco delle particelle. Metodo: ElencoParticelle, modulo: SitInitMapGuide. " + ex.Message + "\r\n Query: " + sCommandText);
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
		protected override string GetFrazione()
		{
			string sRetVal = "";
			IDataReader pDataReader = null;

			try
			{
				sRetVal = GetCivici_All("DESCRIZI_1", pDataReader, TipoQuery.Elenco);
			}
			catch (CatastoException ex)
			{
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione di una frazione. Metodo: GetFrazione, modulo: SitInitMapGuide. " + ex.Message + "\r\n Query: " + sCommandText);
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
				string sElem = GetCivici_All("DESCRIZI_1", pDataReader, TipoQuery.Validazione);
				if (!Init.Utils.StringChecker.IsStringEmpty(sElem))
				{
					pRetSit = new RetSit(true);
					this.DataSit.Frazione = sElem;
				}
				else
				{
					pRetSit = RestituisciErroreSit(GetMessageValidate("DESCRIZI_1"), MessageCode.FrazioneValidazione, false);
				}
			}
			catch (CatastoException ex)
			{
				pRetSit = RestituisciErroreSit(ex.Message, MessageCode.FrazioneValidazione, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la validazione di una frazione. Metodo: VerificaFrazione, modulo: SitInitMapGuide. " + ex.Message + "\r\n Query: " + sCommandText);
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


		protected override string GetSezione()
		{
			string sRetVal = "";
			IDataReader pDataReader = null;

			try
			{
				sRetVal = GetTerreni("SEZ", pDataReader, TipoQuery.Elenco);
			}
			catch (CatastoException ex)
			{
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione di una sezione. Metodo: GetSezione, modulo: SitInitMapGuide. " + ex.Message + "\r\n Query: " + sCommandText);
			}
			finally
			{
				if (pDataReader != null)
					pDataReader.Close();
				InternalDatabaseConnection.Connection.Close();
			}

			return sRetVal;

		}

		protected override RetSit VerificaSezione()
		{
			RetSit pRetSit;

			IDataReader pDataReader = null;

			try
			{
				string sElem = GetTerreni("SEZ", pDataReader, TipoQuery.Validazione);
				if (!Init.Utils.StringChecker.IsStringEmpty(sElem))
				{
					pRetSit = new RetSit(true);
					this.DataSit.Sezione = sElem;
				}
				else
				{
					pRetSit = RestituisciErroreSit(GetMessageValidate("SEZ"), MessageCode.SezioneValidazione, false);
				}
			}
			catch (CatastoException ex)
			{
				pRetSit = RestituisciErroreSit(ex.Message, MessageCode.SezioneValidazione, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la validazione di una sezione. Metodo: VerificaSezione, modulo: SitInitMapGuide. " + ex.Message + "\r\n Query: " + sCommandText);
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
				sRetVal = GetTerreni("FOGLIO", pDataReader, TipoQuery.Elenco);
			}
			catch (CatastoException ex)
			{
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione di un foglio. Metodo: GetFoglio, modulo: SitInitMapGuide. " + ex.Message + "\r\n Query: " + sCommandText);
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
				string sElem = GetTerreni("FOGLIO", pDataReader, TipoQuery.Validazione);
				if (!Init.Utils.StringChecker.IsStringEmpty(sElem))
				{
					pRetSit = new RetSit(true);
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
				throw new Exception("Errore durante la validazione di un foglio. Metodo: VerificaFoglio, modulo: SitInitMapGuide. " + ex.Message + "\r\n Query: " + sCommandText);
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
				sRetVal = GetTerreni("NUMERO", pDataReader, TipoQuery.Elenco);
			}
			catch (CatastoException ex)
			{
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione di una particella. Metodo: GetParticella, modulo: SitInitMapGuide. " + ex.Message + "\r\n Query: " + sCommandText);
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
				string sElem = GetTerreni("NUMERO", pDataReader, TipoQuery.Validazione);
				if (!Init.Utils.StringChecker.IsStringEmpty(sElem))
				{
					pRetSit = new RetSit(true);
					this.DataSit.Particella = sElem;
				}
				else
				{
					pRetSit = RestituisciErroreSit(GetMessageValidate("NUMERO"), MessageCode.ParticellaValidazione, false);
				}
			}
			catch (CatastoException ex)
			{
				pRetSit = RestituisciErroreSit(ex.Message, MessageCode.ParticellaValidazione, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la validazione di una particella. Metodo: VerificaParticella, modulo: SitInitMapGuide. " + ex.Message + "\r\n Query: " + sCommandText);
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
				sRetVal = GetCivici_All("TEXT", pDataReader, TipoQuery.Elenco);
			}
			catch (CatastoException ex)
			{
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione di un civico. Metodo: GetCivico, modulo: SitInitMapGuide. " + ex.Message + "\r\n Query: " + sCommandText);
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
					string sElem = GetCivici_All("TEXT", pDataReader, TipoQuery.Validazione);
					if (!Init.Utils.StringChecker.IsStringEmpty(sElem))
					{
						pRetSit = new RetSit(true);
						this.DataSit.Civico = sElem;
					}
					else
					{
						pRetSit = RestituisciErroreSit(GetMessageValidate("TEXT"), MessageCode.CivicoValidazione, false);
					}
				}
				catch (CatastoException ex)
				{
					pRetSit = RestituisciErroreSit(ex.Message, MessageCode.CivicoValidazione, false);
				}
				catch (Exception ex)
				{
					throw new Exception("Errore durante la validazione di un civico. Metodo: VerificaCivico, modulo: SitInitMapGuide. " + ex.Message + "\r\n Query: " + sCommandText);
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



		protected override string GetCodCivico()
		{
			string sRetVal = "";
			IDataReader pDataReader = null;

			try
			{
				sRetVal = GetCivici_All("FEATID", pDataReader, TipoQuery.Elenco);
			}
			catch (CatastoException ex)
			{
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione di un codice civico. Metodo: GetCodCivico, modulo: SitInitMapGuide. " + ex.Message + "\r\n Query: " + sCommandText);
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
				sRetVal = GetCivici_All("COD_VIA", pDataReader, TipoQuery.Elenco);
			}
			catch (CatastoException ex)
			{
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione di un codice via. Metodo: GetCodVia, modulo: SitInitMapGuide. " + ex.Message + "\r\n Query: " + sCommandText);
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
				//SitMgr.NomiCampiSit.Esponente,
				SitIntegrationService.NomiCampiSit.Frazione,
				//SitMgr.NomiCampiSit.Circoscrizione,
				//SitMgr.NomiCampiSit.Interno,
				//SitMgr.NomiCampiSit.EsponenteInterno,
				//SitMgr.NomiCampiSit.Fabbricato,
				SitIntegrationService.NomiCampiSit.Sezione,
				SitIntegrationService.NomiCampiSit.TipoCatasto,
				SitIntegrationService.NomiCampiSit.Foglio,
				SitIntegrationService.NomiCampiSit.Particella,
				//SitMgr.NomiCampiSit.Sub,
				//SitMgr.NomiCampiSit.UnitaImmobiliare,
				SitIntegrationService.NomiCampiSit.Civico,
				//SitMgr.NomiCampiSit.Cap,
				SitIntegrationService.NomiCampiSit.CodiceVia,
				SitIntegrationService.NomiCampiSit.CodiceCivico
			};
		}
	}
}
