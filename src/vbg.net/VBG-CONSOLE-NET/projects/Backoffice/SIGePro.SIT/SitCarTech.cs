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
using System.Collections.Generic;
using Init.SIGePro.Sit.ValidazioneFormale;
using Init.SIGePro.Sit.Manager;

namespace Init.SIGePro.Sit
{
	/// <summary>
	/// Classe specifica per il Sit CarTech
	/// </summary>
	public class SIT_CTC : SitBase
	{
		public SIT_CTC()
			: base(new ValidazioneFormaleTramiteCodiceCivicoService())
		{
		}

		private string _server = string.Empty;
		private string _db = string.Empty;
		private string _schema = string.Empty;
		private string _commandText = string.Empty;

		#region Utility
		/// <summary>
		/// Inizializzazione dei dati della verticalizzazione
		/// </summary>
		public override void SetupVerticalizzazione()
		{
			try
			{
				VerticalizzazioneSitCtc verticalizzazione = new VerticalizzazioneSitCtc(this.IdComuneAlias, this.Software);

				if (verticalizzazione.Attiva)
				{
					_schema = verticalizzazione.Schema;
					_db = verticalizzazione.Database;
					_server = verticalizzazione.Server;

					var connectionString = verticalizzazione.Connectionstring;
					var providerName = verticalizzazione.Provider;

					ProviderType provider = (ProviderType)Enum.Parse(typeof(ProviderType), providerName, false);
					InternalDatabaseConnection = new DataBase(connectionString, provider);
				}
				else
					throw new Exception("La verticalizzazione SIT_CARTECH non è attiva.\r\n");
			}
			catch (Exception ex)
			{
				throw new Exception("Errore generato durante la lettura della verticalizzazione SIT_CARTECH. Metodo: GetParametriFromVertSITCTC, modulo: SitCore. " + ex.Message + "\r\n");
			}
		}


		private string GetMessaggioValidazioneFallita(string field)
		{
			switch (field)
			{
				case "TipoImmobile":
					return "Il tipo catasto " + DataSit.TipoCatasto + " non è valido per i dati inseriti";
				case "Foglio":
					return "Il foglio " + DataSit.Foglio + " non è valido per i dati inseriti";
				case "Numero":
					return "La particella " + DataSit.Particella + " non è valida per i dati inseriti";
				case "Subalterno":
					return "Il subalterno " + DataSit.Sub + " non è valido per i dati inseriti";
				case "Civico":
					return "Il civico " + DataSit.Civico + " non è valido per i dati inseriti";
				case "Suffisso":
					return "L'esponente " + DataSit.Esponente + " non è valido per i dati inseriti";
			}

			return String.Empty;
		}

		private string GetMessaggioLetturaListaFallita(string field)
		{
			switch (field)
			{
				case "Foglio":
					return "Non è possibile ottenere la lista dei fogli per insufficienza di dati: il catasto deve essere fornito";
				case "Numero":
					return "Non è possibile ottenere la lista delle particelle per insufficienza di dati: il catasto ed il foglio devono essere forniti";
				case "Subalterno":
					return "Non è possibile ottenere la lista dei sub per insufficienza di dati: il catasto, il foglio e la particella devono essere forniti";
				case "Civico":
					return "Non è possibile ottenere la lista dei civici per insufficienza di dati: la via deve essere fornita";
				case "Suffisso":
					return "Non è possibile ottenere la lista degli esponenti per insufficienza di dati: la via ed il civico devono essere forniti";
			}

			return String.Empty;
		}
		
		private string GetViewCivici(string field, IDataReader dataReader, TipoQuery tipoQuery)
		{
			string rVal = string.Empty;

			if (field == "CodiceStrada")
			{
				if (string.IsNullOrEmpty(DataSit.Sub))
					rVal = GetElemento(field, "View_Civici", dataReader, tipoQuery);
				else
					rVal = GetElemento(field, "View_Civici,View_Subalterni", dataReader, tipoQuery);
			}
			else
			{
				if (string.IsNullOrEmpty(DataSit.TipoCatasto))
				{
					if (string.IsNullOrEmpty(DataSit.Sub))
						rVal = GetElemento(field, "View_Civici", dataReader, tipoQuery);
					else
						rVal = GetElemento(field, "View_Civici,View_Subalterni", dataReader, tipoQuery);
				}
				else
				{
					if (this.DataSit.TipoCatasto == "F")
					{
						if (string.IsNullOrEmpty(DataSit.Sub))
							rVal = GetElemento(field, "View_Civici", dataReader, tipoQuery);
						else
							rVal = GetElemento(field, "View_Civici,View_Subalterni", dataReader, tipoQuery);
					}
					else
						throw new CatastoException(GetMessaggioValidazioneFallita(field));
				}
			}

			return rVal;
		}

		private string GetViewSubalterni(string field, IDataReader dataReader, TipoQuery tipoQuery)
		{
			string rVal = string.Empty;

			if (string.IsNullOrEmpty(DataSit.TipoCatasto))
			{
				if (string.IsNullOrEmpty(DataSit.CodVia) &&
					string.IsNullOrEmpty(DataSit.Civico) &&
					string.IsNullOrEmpty(DataSit.Esponente) &&
					string.IsNullOrEmpty(DataSit.CodCivico))
				{
					rVal = GetElemento(field, "View_Subalterni", dataReader, tipoQuery);
				}
				else
				{
					rVal = GetElemento(field, "View_Subalterni,View_Civici", dataReader, tipoQuery);
				}
			}
			else
			{
				if (this.DataSit.TipoCatasto == "F")
				{
					if (string.IsNullOrEmpty(DataSit.CodVia) &&
						string.IsNullOrEmpty(DataSit.Civico) &&
						string.IsNullOrEmpty(DataSit.Esponente) &&
						string.IsNullOrEmpty(DataSit.CodCivico))
					{
						rVal = GetElemento(field, "View_Subalterni", dataReader, tipoQuery);
					}
					else
					{
						rVal = GetElemento(field, "View_Subalterni,View_Civici", dataReader, tipoQuery);
					}
				}
				else
				{
					if (IsCampiToponomasticaImmobileVuoti())
						rVal = GetElemento(field, "View_Subalterni", dataReader, tipoQuery);
					else
						throw new CatastoException(GetMessaggioValidazioneFallita(field));
				}
			}

			return rVal;
		}

		private RetSit ElencoViewCivici(string field, IDataReader dataReader)
		{
			if (string.IsNullOrEmpty(DataSit.TipoCatasto) || 
				this.DataSit.TipoCatasto == "F")
			{
				if (string.IsNullOrEmpty(DataSit.Sub))
					return GetElenco(field, "View_Civici", dataReader);
				else
					return GetElenco(field, "View_Civici,View_Subalterni", dataReader);
			}
			
			return new RetSit(true);
		}

		private RetSit ElencoViewSubalterni(string field, IDataReader dataReader)
		{

			if (string.IsNullOrEmpty(DataSit.TipoCatasto))
				throw new CatastoException(GetMessaggioLetturaListaFallita(field));
						
			if (this.DataSit.TipoCatasto == "F")
			{
				if (string.IsNullOrEmpty(DataSit.CodVia) &&
					string.IsNullOrEmpty(DataSit.Civico) &&
					string.IsNullOrEmpty(DataSit.Esponente) &&
					string.IsNullOrEmpty(DataSit.CodCivico))
				{
					return GetElenco(field, "View_Subalterni", dataReader);
				}

				return GetElenco(field, "View_Subalterni,View_Civici", dataReader);
			}


			if (IsCampiToponomasticaImmobileVuoti())
				return GetElenco(field, "View_Subalterni", dataReader);
					
			return new RetSit(true);			
		}

		private string GetCompleteTableName(string tables)
		{
			var completeTableName = string.Empty;
			var tablesArray = tables.Split(new Char[] { ',' });

			foreach (string elem in tablesArray)
			{
				var tmpTableName = elem;

				if (!String.IsNullOrEmpty(_schema))
					tmpTableName = _schema + "." + tmpTableName;
				if (!String.IsNullOrEmpty(_db))
					tmpTableName = _db + "." + tmpTableName;
				if (!String.IsNullOrEmpty(_server))
					tmpTableName = _server + "." + tmpTableName;

				tmpTableName += " as " + elem;

				completeTableName += tmpTableName + ",";
			}

			completeTableName = completeTableName.Remove(completeTableName.Length - 1);

			return completeTableName;
		}
		
		private string GetQuery(string field, string tableNames, TipoQuery tipoQuery)
		{
			string sQuery = string.Empty;

			switch (tableNames)
			{
				case "View_Civici":
					switch (tipoQuery)
					{
						case TipoQuery.Elenco:
							sQuery = "Select distinct " + field + " from " + GetCompleteTableName(tableNames) + " where " +
								(((this.DataSit.CodVia != "") && (field != "CodiceStrada")) ? InternalDatabaseConnection.Specifics.RTrimFunction("CodiceStrada") + " = " + RightTrim(this.DataSit.CodVia) + " and " : "") +
								(((this.DataSit.Civico != "") && (field != "Civico")) ? InternalDatabaseConnection.Specifics.RTrimFunction("Civico") + " = " + RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Civico) ? this.DataSit.Civico : "-1") + " and " : "") +
								(((this.DataSit.Esponente != "") && (field != "Suffisso")) ? InternalDatabaseConnection.Specifics.RTrimFunction("Suffisso") + " = '" + RightTrim(this.DataSit.Esponente) + "' and " : "") +
								(((this.DataSit.CodCivico != "") && (field != "CodiceCivico")) ? InternalDatabaseConnection.Specifics.RTrimFunction("CodiceCivico") + " = '" + RightTrim(this.DataSit.CodCivico) + "' and " : "") +
								(((this.DataSit.Foglio != "") && (field != "Foglio")) ? InternalDatabaseConnection.Specifics.RTrimFunction("Foglio") + " = '" + LeftPad(RightTrim(this.DataSit.Foglio), 5) + "' and " : "") +
								(((this.DataSit.Particella != "") && (field != "Numero")) ? InternalDatabaseConnection.Specifics.RTrimFunction("Numero") + " = '" + LeftPad(RightTrim(this.DataSit.Particella), 5) + "' and " : "");
							break;
						case TipoQuery.Validazione:
							sQuery = "Select distinct " + field + " from " + GetCompleteTableName(tableNames) + " where " +
								(((this.DataSit.CodVia != "")) ? InternalDatabaseConnection.Specifics.RTrimFunction("CodiceStrada") + " = " + RightTrim(this.DataSit.CodVia) + " and " : "") +
								(((this.DataSit.Civico != "")) ? InternalDatabaseConnection.Specifics.RTrimFunction("Civico") + " = " + RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Civico) ? this.DataSit.Civico : "-1") + " and " : "") +
								(((this.DataSit.Esponente != "")) ? InternalDatabaseConnection.Specifics.RTrimFunction("Suffisso") + " = '" + RightTrim(this.DataSit.Esponente) + "' and " : "") +
								(((this.DataSit.CodCivico != "")) ? InternalDatabaseConnection.Specifics.RTrimFunction("CodiceCivico") + " = '" + RightTrim(this.DataSit.CodCivico) + "' and " : "") +
								(((this.DataSit.Foglio != "")) ? InternalDatabaseConnection.Specifics.RTrimFunction("Foglio") + " = '" + LeftPad(RightTrim(this.DataSit.Foglio), 5) + "' and " : "") +
								(((this.DataSit.Particella != "")) ? InternalDatabaseConnection.Specifics.RTrimFunction("Numero") + " = '" + LeftPad(RightTrim(this.DataSit.Particella), 5) + "' and " : "");
							break;
					}
					break;
				case "View_Subalterni":
					switch (tipoQuery)
					{
						case TipoQuery.Elenco:
							sQuery = "Select distinct " + field + " from " + GetCompleteTableName(tableNames) + " where " +
								(((this.DataSit.TipoCatasto != "") && (field != "TipoImmobile")) ? InternalDatabaseConnection.Specifics.RTrimFunction("TipoImmobile") + " = '" + RightTrim(this.DataSit.TipoCatasto) + "' and " : "") +
								(((this.DataSit.Foglio != "") && (field != "Foglio")) ? InternalDatabaseConnection.Specifics.RTrimFunction("Foglio") + " = '" + LeftPad(RightTrim(this.DataSit.Foglio), 5) + "' and " : "") +
								(((this.DataSit.Particella != "") && (field != "Numero")) ? InternalDatabaseConnection.Specifics.RTrimFunction("Numero") + " = '" + LeftPad(RightTrim(this.DataSit.Particella), 5) + "' and " : "") +
								(((this.DataSit.Sub != "") && (field != "Subalterno")) ? InternalDatabaseConnection.Specifics.RTrimFunction("Subalterno") + " = '" + LeftPad(RightTrim(this.DataSit.Sub), 4) + "' and " : "");
							break;
						case TipoQuery.Validazione:
							sQuery = "Select distinct " + field + " from " + GetCompleteTableName(tableNames) + " where " +
								(((this.DataSit.TipoCatasto != "")) ? InternalDatabaseConnection.Specifics.RTrimFunction("TipoImmobile") + " = '" + RightTrim(this.DataSit.TipoCatasto) + "' and " : "") +
								(((this.DataSit.Foglio != "")) ? InternalDatabaseConnection.Specifics.RTrimFunction("Foglio") + " = '" + LeftPad(RightTrim(this.DataSit.Foglio), 5) + "' and " : "") +
								(((this.DataSit.Particella != "")) ? InternalDatabaseConnection.Specifics.RTrimFunction("Numero") + " = '" + LeftPad(RightTrim(this.DataSit.Particella), 5) + "' and " : "") +
								(((this.DataSit.Sub != "")) ? InternalDatabaseConnection.Specifics.RTrimFunction("Subalterno") + " = '" + LeftPad(RightTrim(this.DataSit.Sub), 4) + "' and " : "");
							break;

					}
					break;
				case "View_Civici,View_Subalterni":
				case "View_Subalterni,View_Civici":
					switch (tipoQuery)
					{
						case TipoQuery.Elenco:
							sQuery = "Select distinct " + tableNames.Split(new Char[] { ',' })[0] + "." + field + " from " + GetCompleteTableName(tableNames) + " where " +
								"View_Civici.Foglio = View_Subalterni.Foglio and " +
								"View_Civici.Numero = View_Subalterni.Numero and " +
								(((this.DataSit.CodVia != "") && (field != "CodiceStrada")) ? InternalDatabaseConnection.Specifics.RTrimFunction("View_Civici.CodiceStrada") + " = " + RightTrim(this.DataSit.CodVia) + " and " : "") +
								(((this.DataSit.Civico != "") && (field != "Civico")) ? InternalDatabaseConnection.Specifics.RTrimFunction("View_Civici.Civico") + " = " + RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Civico) ? this.DataSit.Civico : "-1") + " and " : "") +
								(((this.DataSit.Esponente != "") && (field != "Suffisso")) ? InternalDatabaseConnection.Specifics.RTrimFunction("View_Civici.Suffisso") + " = '" + RightTrim(this.DataSit.Esponente) + "' and " : "") +
								(((this.DataSit.CodCivico != "") && (field != "CodiceCivico")) ? InternalDatabaseConnection.Specifics.RTrimFunction("View_Civici.CodiceCivico") + " = '" + RightTrim(this.DataSit.CodCivico) + "' and " : "") +
								(((this.DataSit.TipoCatasto != "") && (field != "TipoImmobile")) ? InternalDatabaseConnection.Specifics.RTrimFunction("TipoImmobile") + " = '" + RightTrim(this.DataSit.TipoCatasto) + "' and " : "") +
								(((this.DataSit.Foglio != "") && (field != "Foglio")) ? InternalDatabaseConnection.Specifics.RTrimFunction("View_Subalterni.Foglio") + " = '" + LeftPad(RightTrim(this.DataSit.Foglio), 5) + "' and " : "") +
								(((this.DataSit.Particella != "") && (field != "Numero")) ? InternalDatabaseConnection.Specifics.RTrimFunction("View_Subalterni.Numero") + " = '" + LeftPad(RightTrim(this.DataSit.Particella), 5) + "' and " : "") +
								(((this.DataSit.Sub != "") && (field != "Subalterno")) ? InternalDatabaseConnection.Specifics.RTrimFunction("View_Subalterni.Subalterno") + " = '" + LeftPad(RightTrim(this.DataSit.Sub), 4) + "' and " : "");
							break;
						case TipoQuery.Validazione:
							sQuery = "Select distinct " + tableNames.Split(new Char[] { ',' })[0] + "." + field + " from " + GetCompleteTableName(tableNames) + " where " +
								"View_Civici.Foglio = View_Subalterni.Foglio and " +
								"View_Civici.Numero = View_Subalterni.Numero and " +
								(((this.DataSit.CodVia != "")) ? InternalDatabaseConnection.Specifics.RTrimFunction("View_Civici.CodiceStrada") + " = " + RightTrim(this.DataSit.CodVia) + " and " : "") +
								(((this.DataSit.Civico != "")) ? InternalDatabaseConnection.Specifics.RTrimFunction("View_Civici.Civico") + " = " + RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Civico) ? this.DataSit.Civico : "-1") + " and " : "") +
								(((this.DataSit.Esponente != "")) ? InternalDatabaseConnection.Specifics.RTrimFunction("View_Civici.Suffisso") + " = '" + RightTrim(this.DataSit.Esponente) + "' and " : "") +
								(((this.DataSit.CodCivico != "")) ? InternalDatabaseConnection.Specifics.RTrimFunction("View_Civici.CodiceCivico") + " = '" + RightTrim(this.DataSit.CodCivico) + "' and " : "") +
								(((this.DataSit.TipoCatasto != "")) ? InternalDatabaseConnection.Specifics.RTrimFunction("TipoImmobile") + " = '" + RightTrim(this.DataSit.TipoCatasto) + "' and " : "") +
								(((this.DataSit.Foglio != "")) ? InternalDatabaseConnection.Specifics.RTrimFunction("View_Subalterni.Foglio") + " = '" + LeftPad(RightTrim(this.DataSit.Foglio), 5) + "' and " : "") +
								(((this.DataSit.Particella != "")) ? InternalDatabaseConnection.Specifics.RTrimFunction("View_Subalterni.Numero") + " = '" + LeftPad(RightTrim(this.DataSit.Particella), 5) + "' and " : "") +
								(((this.DataSit.Sub != "")) ? InternalDatabaseConnection.Specifics.RTrimFunction("View_Subalterni.Subalterno") + " = '" + LeftPad(RightTrim(this.DataSit.Sub), 4) + "' and " : "");
							break;
					}
					break;
			}

			if (sQuery.EndsWith("and "))
				sQuery = sQuery.Remove(sQuery.Length - 4, 4) + " order by " + tableNames.Split(new Char[] { ',' })[0] + "." + field;
			else if (sQuery.EndsWith("where "))
				sQuery = sQuery.Remove(sQuery.Length - 6, 6) + " order by " + tableNames.Split(new Char[] { ',' })[0] + "." + field;
			else
				sQuery = sQuery + " order by " + tableNames.Split(new Char[] { ',' })[0] + "." + field;

			return sQuery;
		}
		
		private string GetQuery(string[] fields, string tableNames, TipoElenco tipoElenco)
		{
			string query = string.Empty;
			string field = string.Empty;

			foreach (string elem in fields)
				field += elem + ",";

			field = field.Remove(field.Length - 1);

			switch (tipoElenco)
			{
				case TipoElenco.Vincoli:
					query = "Select distinct " + field + " from " + GetCompleteTableName(tableNames) + " where " +
						"Tipo = 'V' and " +
						InternalDatabaseConnection.Specifics.RTrimFunction("View_Intersezioni.Foglio") + " = '" + LeftPad(RightTrim(this.DataSit.Foglio), 5) + "' and " +
						InternalDatabaseConnection.Specifics.RTrimFunction("View_Intersezioni.Numero") + " = '" + LeftPad(RightTrim(this.DataSit.Particella), 5) + "' and ";
					break;
				case TipoElenco.Zone:
					query = "Select distinct " + field + " from " + GetCompleteTableName(tableNames) + " where " +
						"Tipo = 'Z' and " +
						InternalDatabaseConnection.Specifics.RTrimFunction("View_Intersezioni.Foglio") + " = '" + LeftPad(RightTrim(this.DataSit.Foglio), 5) + "' and " +
						InternalDatabaseConnection.Specifics.RTrimFunction("View_Intersezioni.Numero") + " = '" + LeftPad(RightTrim(this.DataSit.Particella), 5) + "' and ";
					break;
			}

			if (query.EndsWith("and "))
				query = query.Remove(query.Length - 4, 4);// + " order by " + tableNames.Split(new Char[] { ',' })[0] + "." + fields[0];
			else if (query.EndsWith("where "))
				query = query.Remove(query.Length - 6, 6);// + " order by " + tableNames.Split(new Char[] { ',' })[0] + "." + fields[0];
			
			query += " order by " + tableNames.Split(new Char[] { ',' })[0] + "." + fields[0];

			return query;
		}
		
		private string GetElemento(string field, string tableNames, IDataReader dataReader, TipoQuery tipoQuery)
		{
			var rVal = String.Empty;
			var count = 0;
			
			EnsureConnectionIsOpen();

			_commandText = GetQuery(field, tableNames, tipoQuery);

			using (IDbCommand pCommand = InternalDatabaseConnection.CreateCommand(_commandText))
			{
				dataReader = pCommand.ExecuteReader();

				while (dataReader.Read())
				{
					//Istruzione RightTrimSit usata per togliere eventuali spazi a destra
					string dato = RightTrim(dataReader[field].ToString());

					count++;

					if (count == 1)
					{
						rVal = dato;
					}
					else
					{
						rVal = String.Empty;
						break;
					}
				}

			}

			return rVal;
		}

		private RetSit GetElenco(string field, string tableNames, IDataReader dataReader)
		{
			RetSit rVal = new RetSit(true);
			
			EnsureConnectionIsOpen();

			_commandText = GetQuery(field, tableNames, TipoQuery.Elenco);

			using (IDbCommand pCommand = InternalDatabaseConnection.CreateCommand(_commandText))
			{
				dataReader = pCommand.ExecuteReader();

				while (dataReader.Read())
				{
					//Istruzione RightTrimSit usata per eliminare eventuali spazi a destra
					string dato = RightTrim(dataReader[field].ToString());

					if (!string.IsNullOrEmpty(dato) && !rVal.DataCollection.Contains(dato))
					{
						rVal.DataCollection.Add(dato);
					}
				}
			}

			return rVal;
		}


		private RetSit GetElenco(string[] fields, string tableNames, IDataReader dataReader, TipoElenco tipoElenco)
		{
			var rVal = new RetSit(true);

			EnsureConnectionIsOpen();

			_commandText = GetQuery(fields, tableNames, tipoElenco);

			using (IDbCommand command = InternalDatabaseConnection.CreateCommand(_commandText))
			{
				dataReader = command.ExecuteReader();

				List<string> list = new List<string>();

				int index = 0;

				while (dataReader.Read())
				{
					if (fields.Length == 1)
					{
						string field = fields[0];
						string dato = RightTrim(dataReader[field].ToString());

						if (!list.Contains(dato))
						{
							rVal.DataMap.Add(field + index, dato);
							list.Add(dato);
						}
					}
					else
					{
						foreach (string field in fields)
						{
							string dato = RightTrim(dataReader[field].ToString());
							rVal.DataMap.Add(field + index, dato == "NULL" ? "-" : dato);
						}
					}

					index++;
				}
			}

			return rVal;
		}

		#endregion



		#region Metodi per ottenere elenchi di elementi catastali o facenti parte dell'indirizzo

		public override RetSit ElencoCivici()
		{
			IDataReader dataReader = null;

			try
			{
				if (!String.IsNullOrEmpty(this.DataSit.CodVia))
				{
					return ElencoViewCivici("Civico", dataReader);
				}

				return RestituisciErroreSit(GetMessaggioLetturaListaFallita("Civico"), MessageCode.ElencoCivici, false);
			}
			catch (CatastoException ex)
			{
				return RestituisciErroreSit(ex.Message, MessageCode.ElencoCivici, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione dell'elenco dei civici. Metodo: ElencoCivici, modulo: SitCarTech. " + ex.Message + "\r\n Query: " + _commandText);
			}
			finally
			{
				if (dataReader != null)
					dataReader.Close();

				InternalDatabaseConnection.Connection.Close();
			}
		}
		
		public override RetSit ElencoEsponenti()
		{
			IDataReader dataReader = null;

			try
			{
				if (!String.IsNullOrEmpty(this.DataSit.Civico) && !String.IsNullOrEmpty(this.DataSit.CodVia))
				{
					return ElencoViewCivici("Suffisso", dataReader);
				}

				return RestituisciErroreSit(GetMessaggioLetturaListaFallita("Suffisso"), MessageCode.ElencoEsponenti, false);
			}
			catch (CatastoException ex)
			{
				return RestituisciErroreSit(ex.Message, MessageCode.ElencoEsponenti, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione dell'elenco degli esponenti. Metodo: ElencoEsponenti, modulo: SitCarTech. " + ex.Message + "\r\n Query: " + _commandText);
			}
			finally
			{
				if (dataReader != null)
					dataReader.Close();
				InternalDatabaseConnection.Connection.Close();
			}
		}
		
		public override RetSit ElencoFogli()
		{
			IDataReader dataReader = null;

			try
			{
				return ElencoViewSubalterni("Foglio", dataReader);
			}
			catch (CatastoException ex)
			{
				return RestituisciErroreSit(ex.Message, MessageCode.ElencoFogli, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione dell'elenco dei fogli. Metodo: ElencoFogli, modulo: SitCarTech. " + ex.Message + "\r\n Query: " + _commandText);
			}
			finally
			{
				if (dataReader != null)
					dataReader.Close();

				InternalDatabaseConnection.Connection.Close();
			}
		}

		public override RetSit ElencoParticelle()
		{
			IDataReader dataReader = null;

			try
			{
				if (!String.IsNullOrEmpty(this.DataSit.Foglio))
				{
					return ElencoViewSubalterni("Numero", dataReader);
				}
				
				return RestituisciErroreSit(GetMessaggioLetturaListaFallita("Numero"), MessageCode.ElencoParticelle, false);
			}
			catch (CatastoException ex)
			{
				return RestituisciErroreSit(ex.Message, MessageCode.ElencoParticelle, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione dell'elenco delle particelle. Metodo: ElencoParticelle, modulo: SitCarTech. " + ex.Message + "\r\n Query: " + _commandText);
			}
			finally
			{
				if (dataReader != null)
					dataReader.Close();

				InternalDatabaseConnection.Connection.Close();
			}

		}

		public override RetSit ElencoSub()
		{
			IDataReader dataReader = null;

			try
			{
				if (!String.IsNullOrEmpty(this.DataSit.Foglio) && !String.IsNullOrEmpty(this.DataSit.Particella))
				{
					return ElencoViewSubalterni("Subalterno", dataReader);
				}
				
				return RestituisciErroreSit(GetMessaggioLetturaListaFallita("Subalterno"), MessageCode.ElencoSub, false);
				
			}
			catch (CatastoException ex)
			{
				return RestituisciErroreSit(ex.Message, MessageCode.ElencoSub, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione dell'elenco dei sub. Metodo: ElencoSub, modulo: SitCarTech. " + ex.Message + "\r\n Query: " + _commandText);
			}
			finally
			{
				if (dataReader != null)
					dataReader.Close();

				InternalDatabaseConnection.Connection.Close();
			}
		}
				
		public override RetSit ElencoVincoli()
		{
			IDataReader dataReader = null;

			try
			{
				if (!String.IsNullOrEmpty(this.DataSit.Foglio) && !String.IsNullOrEmpty(this.DataSit.Particella))
				{
					string[] aField = { "CodiceVincolo", "Descrizione", "Articolo_G", "Articolo_N" };
					return GetElenco(aField, "View_Intersezioni", dataReader, TipoElenco.Vincoli);
				}
				
				return RestituisciErroreSit("Non è possibile ottenere la lista dei vincoli per insufficienza di dati:il foglio e la particella devono essere forniti",MessageCode.ElencoVincoli, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione dell'elenco dei vincoli. Metodo: ElencoVincoli, modulo: SitCarTech. " + ex.Message + "\r\n Query: " + _commandText);
			}
			finally
			{
				if (dataReader != null)
					dataReader.Close();

				InternalDatabaseConnection.Connection.Close();
			}

		}

		public override RetSit ElencoZone()
		{
			IDataReader dataReader = null;

			try
			{
				if (!String.IsNullOrEmpty(this.DataSit.Foglio) && !String.IsNullOrEmpty(this.DataSit.Particella))
				{
					string[] aField = { "ZonaOmogenea" };
					return GetElenco(aField, "View_Intersezioni", dataReader, TipoElenco.Zone);
				}
				

				return RestituisciErroreSit("Non è possibile ottenere la lista delle zone per insufficienza di dati:il foglio e la particella devono essere forniti", MessageCode.ElencoZone, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione dell'elenco dei vincoli. Metodo: ElencoZone, modulo: SitCarTech. " + ex.Message + "\r\n Query: " + _commandText);
			}
			finally
			{
				if (dataReader != null)
					dataReader.Close();

				InternalDatabaseConnection.Connection.Close();
			}
		}

		public override RetSit ElencoSottoZone()
		{
			IDataReader dataReader = null;

			try
			{
				if (!String.IsNullOrEmpty(this.DataSit.Foglio) && !String.IsNullOrEmpty(this.DataSit.Particella))
				{
					string[] aField = { "ZonaOmogenea", "ZonaUrbanistica", "Descrizione", "Articolo_G", "Articolo_N" };
					return GetElenco(aField, "View_Intersezioni", dataReader, TipoElenco.Zone);
				}

				return RestituisciErroreSit("Non è possibile ottenere la lista delle sottozone per insufficienza di dati:il foglio, la particella e la zona devono essere forniti", MessageCode.ElencoSottoZone, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione dell'elenco delle sottozone. Metodo: ElencoSottoZone, modulo: SitCarTech. " + ex.Message + "\r\n Query: " + _commandText);
			}
			finally
			{
				if (dataReader != null)
					dataReader.Close();

				InternalDatabaseConnection.Connection.Close();
			}
		}

		#endregion

		#region Metodi per la verifica e la restituzione di un singolo elemento catastale o facente parte dell'indirizzo

		protected override string GetEsponente()
		{
			IDataReader dataReader = null;

			try
			{
				return GetViewCivici("Suffisso", dataReader, TipoQuery.Elenco);
			}
			catch (CatastoException ex)
			{
				return String.Empty; // ???
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione di un esponente. Metodo: GetEsponente, modulo: SitCarTech. " + ex.Message + "\r\n Query: " + _commandText);
			}
			finally
			{
				if (dataReader != null)
					dataReader.Close();

				InternalDatabaseConnection.Connection.Close();
			}
		}

		protected override RetSit VerificaEsponente()
		{
			RetSit rVal;
			IDataReader dataReader = null;

			try
			{
				string sElem = GetViewCivici("Suffisso", dataReader, TipoQuery.Validazione);

				if (!String.IsNullOrEmpty(sElem))
				{
					rVal = new RetSit(true);
					this.DataSit.Esponente = sElem;
				}
				else
				{
					rVal = RestituisciErroreSit(GetMessaggioValidazioneFallita("Suffisso"), MessageCode.EsponenteValidazione, false);
				}
			}
			catch (CatastoException ex)
			{
				rVal = RestituisciErroreSit(ex.Message, MessageCode.EsponenteValidazione, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la validazione di un esponente. Metodo: VerificaEsponente, modulo: SitCarTech. " + ex.Message + "\r\n Query: " + _commandText);
			}
			finally
			{
				if (dataReader != null)
					dataReader.Close();

				InternalDatabaseConnection.Connection.Close();
			}

			return rVal;
		}

		protected override string GetTipoCatasto()
		{
			string sRetVal = "";
			IDataReader pDataReader = null;

			try
			{
				sRetVal = GetViewSubalterni("TipoImmobile", pDataReader, TipoQuery.Elenco);
			}
			catch (CatastoException ex)
			{
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione del tipo catasto. Metodo: GetTipoCatasto, modulo: SitCarTech. " + ex.Message + "\r\n Query: " + _commandText);
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
			RetSit pRetSit;

			IDataReader pDataReader = null;

			try
			{
				string sElem = GetViewSubalterni("TipoImmobile", pDataReader, TipoQuery.Validazione);

				if (!String.IsNullOrEmpty(sElem))
				{
					pRetSit = new RetSit(true);
					this.DataSit.TipoCatasto = sElem;
				}
				else
				{
					pRetSit = RestituisciErroreSit(GetMessaggioValidazioneFallita("TipoImmobile"), MessageCode.TipoCatastoValidazione, false);
				}
			}
			catch (CatastoException ex)
			{
				pRetSit = RestituisciErroreSit(ex.Message, MessageCode.TipoCatastoValidazione, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la validazione del tipo catasto. Metodo: VerificaTipoCatasto, modulo: SitCarTech. " + ex.Message + "\r\n Query: " + _commandText);
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
				sRetVal = GetViewSubalterni("Foglio", pDataReader, TipoQuery.Elenco);
			}
			catch (CatastoException ex)
			{
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione di un foglio. Metodo: GetFoglio, modulo: SitCarTech. " + ex.Message + "\r\n Query: " + _commandText);
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
				string sElem = GetViewSubalterni("Foglio", pDataReader, TipoQuery.Validazione);

				if (!String.IsNullOrEmpty(sElem))
				{
					pRetSit = new RetSit(true);
					this.DataSit.Foglio = sElem;
				}
				else
				{
					this.DataSit.Foglio = LeftPad(this.DataSit.Foglio, 5);
					pRetSit = RestituisciErroreSit(GetMessaggioValidazioneFallita("Foglio"), MessageCode.FoglioValidazione, false);
				}
			}
			catch (CatastoException ex)
			{
				this.DataSit.Foglio = LeftPad(this.DataSit.Foglio, 5);
				pRetSit = RestituisciErroreSit(ex.Message, MessageCode.FoglioValidazione, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la validazione di un foglio. Metodo: VerificaFoglio, modulo: SitCarTech. " + ex.Message + "\r\n Query: " + _commandText);
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
				sRetVal = GetViewSubalterni("Numero", pDataReader, TipoQuery.Elenco);
			}
			catch (CatastoException ex)
			{
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione di una particella. Metodo: GetParticella, modulo: SitCarTech. " + ex.Message + "\r\n Query: " + _commandText);
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
				string sElem = GetViewSubalterni("Numero", pDataReader, TipoQuery.Validazione);

				if (!String.IsNullOrEmpty(sElem))
				{
					pRetSit = new RetSit(true);
					this.DataSit.Particella = sElem;
				}
				else
				{
					this.DataSit.Particella = LeftPad(this.DataSit.Particella, 5);
					pRetSit = RestituisciErroreSit(GetMessaggioValidazioneFallita("Numero"), MessageCode.ParticellaValidazione, false);
				}
			}
			catch (CatastoException ex)
			{
				this.DataSit.Particella = LeftPad(this.DataSit.Particella, 5);
				pRetSit = RestituisciErroreSit(ex.Message, MessageCode.ParticellaValidazione, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la validazione di una particella. Metodo: VerificaParticella, modulo: SitCarTech. " + ex.Message + "\r\n Query: " + _commandText);
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
				sRetVal = GetViewSubalterni("Subalterno", pDataReader, TipoQuery.Elenco);
			}
			catch (CatastoException ex)
			{
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione di un sub. Metodo: GetSub, modulo: SitCarTech. " + ex.Message + "\r\n Query: " + _commandText);
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
				string sElem = GetViewSubalterni("Subalterno", pDataReader, TipoQuery.Validazione);

				if (!String.IsNullOrEmpty(sElem))
				{
					pRetSit = new RetSit(true);
					this.DataSit.Sub = sElem;
				}
				else
				{
					this.DataSit.Sub = LeftPad(this.DataSit.Sub, 4);
					pRetSit = RestituisciErroreSit(GetMessaggioValidazioneFallita("Subalterno"), MessageCode.SubValidazione, false);
				}
			}
			catch (CatastoException ex)
			{
				this.DataSit.Sub = LeftPad(this.DataSit.Sub, 4);
				pRetSit = RestituisciErroreSit(ex.Message, MessageCode.SubValidazione, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la validazione di un sub. Metodo: VerificaSub, modulo: SitCarTech. " + ex.Message + "\r\n Query: " + _commandText);
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
				sRetVal = GetViewCivici("Civico", pDataReader, TipoQuery.Elenco);
			}
			catch (CatastoException ex)
			{
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione di un civico. Metodo: GetCivico, modulo: SitCarTech. " + ex.Message + "\r\n Query: " + _commandText);
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
					string sElem = GetViewCivici("Civico", pDataReader, TipoQuery.Validazione);

					if (!String.IsNullOrEmpty(sElem))
					{
						pRetSit = new RetSit(true);
						this.DataSit.Civico = sElem;
					}
					else
					{
						pRetSit = RestituisciErroreSit(GetMessaggioValidazioneFallita("Civico"), MessageCode.CivicoValidazione, false);
					}
				}
				catch (CatastoException ex)
				{
					pRetSit = RestituisciErroreSit(ex.Message, MessageCode.CivicoValidazione, false);
				}
				catch (Exception ex)
				{
					throw new Exception("Errore durante la validazione di un civico. Metodo: VerificaCivico, modulo: SitCarTech. " + ex.Message + "\r\n Query: " + _commandText);
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
				sRetVal = GetViewCivici("CodiceCivico", pDataReader, TipoQuery.Elenco);
			}
			catch (CatastoException ex)
			{
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione di un codice civico. Metodo: GetCodiceCivico, modulo: SitCarTech. " + ex.Message + "\r\n Query: " + _commandText);
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
				sRetVal = GetViewCivici("CodiceStrada", pDataReader, TipoQuery.Elenco);
			}
			catch (CatastoException ex)
			{
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione di un codice via. Metodo: GetCodVia, modulo: SitCarTech. " + ex.Message + "\r\n Query: " + _commandText);
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
			//    SitMgr.NomiCampiSit.Scala,
			//    SitMgr.NomiCampiSit.Frazione,
			//    SitMgr.NomiCampiSit.Circoscrizione,
			//    SitMgr.NomiCampiSit.Interno,
			//    SitMgr.NomiCampiSit.EsponenteInterno,
			//    SitMgr.NomiCampiSit.Fabbricato,
			//    SitMgr.NomiCampiSit.Sezione,
			    SitIntegrationService.NomiCampiSit.TipoCatasto,
			    SitIntegrationService.NomiCampiSit.Foglio,
			    SitIntegrationService.NomiCampiSit.Particella,
			    SitIntegrationService.NomiCampiSit.Sub,
			//    SitMgr.NomiCampiSit.UnitaImmobiliare,
			    SitIntegrationService.NomiCampiSit.Civico,
			//    SitMgr.NomiCampiSit.Cap,
			    SitIntegrationService.NomiCampiSit.CodiceVia,
			    SitIntegrationService.NomiCampiSit.CodiceCivico
			};
		}
	}


}
