using System;
using System.Linq;
using System.Data;
using System.Diagnostics;
using Init.SIGePro.Collection;
using Init.SIGePro.Manager;
using Init.SIGePro.Data;
using Init.SIGePro.Verticalizzazioni;
using PersonalLib2.Data;
using System.Runtime.InteropServices;
using Init.SIGePro.Exceptions.SIT;
using System.Collections.Generic;
using log4net;
using Init.Utils;
using Init.SIGePro.Sit.ValidazioneFormale;
using Init.SIGePro.Sit.Manager;
using Init.SIGePro.Sit.Data;
using Init.SIGePro.Manager.Utils.Extensions;

namespace Init.SIGePro.Sit
{
	public class SIT_CORE : SitBase
	{
		// 26/04/2012 - Il campo CAP è stato escluso da tutte le ricerche in quanto pur essendo popolato nello stradario
		// nelle tabelle esposte dal sit è sempre valorizzato a "0". 

		private enum NomiCampi
		{
			CodiceVia,
			Civico,
			Esponente,
			CodCivico,
			//Cap,
			Fabbricato,
			Sezione,
			Foglio,
			Particella,
			Scala,
			Interno,
			EsponenteInterno,
			DataFine
		}

		private static class NomiQualificatiCampi
		{
			public const string CodiceVia = "AE_CIVICO_CON_CIVKEY.COD_VIA";
			public const string Civico = "AE_CIVICO_CON_CIVKEY.NUMERO";
			public const string Esponente = "AE_CIVICO_CON_CIVKEY.ESPONENTE";
			public const string CodCivico = "AE_CIVICO_CON_CIVKEY.CIVKEY";
			//public const string Cap = "AE_CIVICO_CON_CIVKEY.CAP";
			public const string Fabbricato = "AE_CIVICO_CON_CIVKEY.ID_EDIFICIO";
			public const string Sezione = "AE_CIVICO_CON_CIVKEY.G_SEZIONE";
			public const string Foglio = "AE_CIVICO_CON_CIVKEY.G_FOGLIO";
			public const string Particella = "AE_CIVICO_CON_CIVKEY.G_NUMERO";
			public const string Scala = "AE_INTERNO_CON_INTKEY.SCALA";
			public const string Interno = "AE_INTERNO_CON_INTKEY.NUMERO";
			public const string EsponenteInterno = "AE_INTERNO_CON_INTKEY.ESPONENTE";
			public const string DataFine = "AE_CIVICO_CON_CIVKEY.DATA_FINE";
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



		public SIT_CORE(): base( new ValidazioneFormaleTramiteCodiceCivicoService() )
		{
			this._registroNomiCampi = new Dictionary<NomiCampi, string>{
				{ NomiCampi.CodiceVia , NomiQualificatiCampi.CodiceVia},
				{ NomiCampi.Civico , NomiQualificatiCampi.Civico},
				{ NomiCampi.Esponente , NomiQualificatiCampi.Esponente},
				{ NomiCampi.CodCivico, NomiQualificatiCampi.CodCivico},
				//{ NomiCampi.Cap , NomiQualificatiCampi.Cap},
				{ NomiCampi.Fabbricato , NomiQualificatiCampi.Fabbricato},
				{ NomiCampi.Sezione , NomiQualificatiCampi.Sezione},
				{ NomiCampi.Foglio , NomiQualificatiCampi.Foglio},
				{ NomiCampi.Particella , NomiQualificatiCampi.Particella},
				{ NomiCampi.Scala , NomiQualificatiCampi.Scala},
				{ NomiCampi.Interno , NomiQualificatiCampi.Interno},
				{ NomiCampi.EsponenteInterno , NomiQualificatiCampi.EsponenteInterno},
				{ NomiCampi.DataFine , NomiQualificatiCampi.DataFine}
			};

		}

		Dictionary<NomiCampi, string> _registroNomiCampi;
		string _connectionString = "";
		string _provider = "";
		string _dbLink = "";
		string _owner = "";
		string _commandText = "";
		ILog _log = LogManager.GetLogger(typeof(SIT_CORE));

		#region Utility
		public override void SetupVerticalizzazione()
		{
			GetParametriFromVertSITCORE();
		}

		/// <summary>
		/// Metodo usato per leggere i parametri della verticalizzazione SIT_CORE
		/// </summary>
		private void GetParametriFromVertSITCORE()
		{
			try
			{
				VerticalizzazioneSitCore pSitCore;
				DataBase dDataBase = this.Database;

				pSitCore = new VerticalizzazioneSitCore(this.IdComuneAlias, this.Software);

				if (pSitCore.Attiva)
				{
					_owner = pSitCore.Owner;
					_dbLink = pSitCore.Dblink;
					_connectionString = pSitCore.Connectionstring;
					_provider = pSitCore.Provider;

					ProviderType provider = (ProviderType)Enum.Parse(typeof(ProviderType), _provider, false);
					InternalDatabaseConnection = new DataBase(_connectionString, provider);
				}
				else
					throw new Exception("La verticalizzazione SIT_CORE non è attiva.\r\n");
			}
			catch (Exception ex)
			{
				throw new Exception("Errore generato durante la lettura della verticalizzazione SIT_CORE. Metodo: GetParametriFromVertSITCORE, modulo: SitCore. " + ex.Message + "\r\n");
			}
		}

		private string GetErroreDiValidazione(NomiCampi sField)
		{
			switch (sField)
			{
				case NomiCampi.Sezione:
					return "La sezione " + DataSit.Sezione + " non è valida per i dati inseriti";

				case NomiCampi.Foglio:
					return "Il foglio " + DataSit.Foglio + " non è valido per i dati inseriti";

				case NomiCampi.Particella:
					return "La particella " + DataSit.Particella + " non è valida per i dati inseriti";

				//		case NomiCampi.Cap:
				//			return "Il CAP " + DataSit.CAP + " non è valido per i dati inseriti";

				case NomiCampi.Civico:
					return "Il civico " + DataSit.Civico + " non è valido per i dati inseriti";

				case NomiCampi.Esponente:
					return "L'esponente " + DataSit.Esponente + " non è valido per i dati inseriti";

				case NomiCampi.Fabbricato:
					return "Il fabbricato " + DataSit.Fabbricato + " non è valido per i dati inseriti";

				case NomiCampi.Scala:
					return "La scala " + DataSit.Scala + " non è valida per i dati inseriti";

				case NomiCampi.Interno:
					return "L'interno " + DataSit.Interno + " non è valido per i dati inseriti";

				case NomiCampi.EsponenteInterno:
					return "L'esponente dell'interno " + DataSit.EsponenteInterno + " non è valido per i dati inseriti";
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
			}

			return sValue;
		}

		private string GetAeCivicoConCivkey(NomiCampi campo, TipoQuery tipoQuery)
		{
			try
			{
				if (_log.IsDebugEnabled)
					_log.DebugFormat("GetAeCivicoConCivkey: Lettura del campo {0} con tipo query {1}: dati struttura sit {2}", campo, tipoQuery, StreamUtils.SerializeClass(this.DataSit));

				var datiImmobileValorizzati = !(string.IsNullOrEmpty(DataSit.Scala) && string.IsNullOrEmpty(DataSit.Interno) && string.IsNullOrEmpty(DataSit.EsponenteInterno));

				// Se cerco di leggere il codice via 
				if ( campo == NomiCampi.CodiceVia || UsaCatastoFabbricati(DataSit.TipoCatasto))
				{
					if (!datiImmobileValorizzati)
						return GetElemento(campo, NomiTabelle.AE_CIVICO_CON_CIVKEY, tipoQuery);

					return GetElemento(campo, NomiTabelle.Tutte, tipoQuery);
				}
				

				if (campo == NomiCampi.Sezione || campo == NomiCampi.Foglio || campo == NomiCampi.Particella)
				{
					if (IsCampiToponomasticaImmobileVuoti())
						return GetElemento(campo, NomiTabelle.AE_CIVICO_CON_CIVKEY, tipoQuery);

					throw new CatastoException(GetErroreDiValidazione(campo));
				}

				throw new CatastoException(GetErroreDiValidazione(campo));
			}
			catch (Exception ex)
			{
				_log.ErrorFormat("Errore in GetAeCivicoConCivkey: {0}\r\nStruttura sit: {1}", ex.ToString(), StreamUtils.SerializeClass(this.DataSit));

				throw;
			}
		}

		private bool UsaCatastoFabbricati(string tipoCatasto)
		{
			return string.IsNullOrEmpty(tipoCatasto) || tipoCatasto == TipoCatasto.Fabbricati;
		}

		private string GetAeInternoConIntkey(NomiCampi sField, TipoQuery eTipoQuery)
		{
			try
			{
				if (_log.IsDebugEnabled)
					_log.DebugFormat("GetAeInternoConIntkey: Lettura del campo {0} con tipo query {1}: dati struttura sit {2}", sField, eTipoQuery, StreamUtils.SerializeClass(this.DataSit));

				var altriDatiNonSpecificati = string.IsNullOrEmpty(DataSit.CodVia) && 
											  string.IsNullOrEmpty(DataSit.Civico) && 
											  string.IsNullOrEmpty(DataSit.Esponente) && 
											  string.IsNullOrEmpty(DataSit.CAP) && 
											  string.IsNullOrEmpty(DataSit.Fabbricato) && 
											  string.IsNullOrEmpty(DataSit.Sezione) && 
											  string.IsNullOrEmpty(DataSit.Foglio) && 
											  string.IsNullOrEmpty(DataSit.Particella);

				if (UsaCatastoFabbricati(DataSit.TipoCatasto))
				{
					if (altriDatiNonSpecificati)
						return GetElemento(sField, NomiTabelle.AE_INTERNO_CON_INTKEY, eTipoQuery);

					return GetElemento(sField, NomiTabelle.Tutte, eTipoQuery);
				}

				throw new CatastoException(GetErroreDiValidazione(sField));
			}
			catch (Exception ex)
			{
				_log.ErrorFormat("Errore in GetAeInternoConIntkey: {0}\r\nStruttura sit: {1}", ex.ToString(), StreamUtils.SerializeClass(this.DataSit));

				throw;
			}

		}

		private RetSit ElencoAeCivicoConCivkey(NomiCampi campoDaLeggere)
		{
			try
			{
				if (_log.IsDebugEnabled)
					_log.DebugFormat("ElencoAeCivicoConCivkey: Lettura del campo {0}  dati struttura sit {1}", campoDaLeggere, StreamUtils.SerializeClass(this.DataSit));


				var isCampoMappali = campoDaLeggere == NomiCampi.Sezione ||
									 campoDaLeggere == NomiCampi.Foglio ||
									 campoDaLeggere == NomiCampi.Particella;

				if (isCampoMappali)
				{
					if (string.IsNullOrEmpty(DataSit.TipoCatasto))
						throw new CatastoException(GetMessaggioDiErrore(campoDaLeggere));

					if (this.DataSit.TipoCatasto == TipoCatasto.Fabbricati)
					{
						if (string.IsNullOrEmpty(DataSit.Scala) && string.IsNullOrEmpty(DataSit.Interno) && string.IsNullOrEmpty(DataSit.EsponenteInterno))
							return GetElenco(campoDaLeggere, NomiTabelle.AE_CIVICO_CON_CIVKEY);

						return GetElenco(campoDaLeggere, NomiTabelle.Tutte);
					}

					if (IsCampiToponomasticaImmobileVuoti())
						return GetElenco(campoDaLeggere, NomiTabelle.AE_CIVICO_CON_CIVKEY);

					return new RetSit(true);
				}

				if ( UsaCatastoFabbricati(DataSit.TipoCatasto) )
				{
					if (string.IsNullOrEmpty(DataSit.Scala) && string.IsNullOrEmpty(DataSit.Interno) && string.IsNullOrEmpty(DataSit.EsponenteInterno))
						return GetElenco(campoDaLeggere, NomiTabelle.AE_CIVICO_CON_CIVKEY);

					return GetElenco(campoDaLeggere, NomiTabelle.Tutte);
				}

				return new RetSit(true);
			}
			catch (Exception ex)
			{
				_log.ErrorFormat("Errore in ElencoAeCivicoConCivkey: {0}\r\nStruttura sit: {1}", ex.ToString(), StreamUtils.SerializeClass(this.DataSit));

				throw;
			}
		}

		private RetSit ElencoAeInternoConIntkey(NomiCampi sField)
		{
			try
			{
				if (_log.IsDebugEnabled)
					_log.DebugFormat("ElencoAeInternoConIntkey: Lettura del campo {0}  dati struttura sit {1}", sField, StreamUtils.SerializeClass(this.DataSit));

				if ( UsaCatastoFabbricati(DataSit.TipoCatasto) )
				{
					if (string.IsNullOrEmpty(DataSit.CodVia) && string.IsNullOrEmpty(DataSit.Civico) && string.IsNullOrEmpty(DataSit.Esponente) && string.IsNullOrEmpty(DataSit.CAP) && string.IsNullOrEmpty(DataSit.Fabbricato) && string.IsNullOrEmpty(DataSit.Sezione) && string.IsNullOrEmpty(DataSit.Foglio) && string.IsNullOrEmpty(DataSit.Particella))
						return GetElenco(sField, NomiTabelle.AE_INTERNO_CON_INTKEY);

					return GetElenco(sField, NomiTabelle.Tutte);
				}

				return new RetSit(true);
			}
			catch (Exception ex)
			{
				_log.ErrorFormat("Errore in GetAeCivicoConCivkey: {0}\r\nStruttura sit: {1}", ex.ToString(), StreamUtils.SerializeClass(this.DataSit));

				throw;
			}
		}
		
		private string GetCompleteTableName(string nomiTabelle)
		{
			string rVal = string.Empty;
			string[] listaNomiTabelle = nomiTabelle.Split(new Char[] { ',' });

			foreach (string nomeTabella in listaNomiTabelle)
			{
				string sTmpTableName = nomeTabella;

				if (!String.IsNullOrEmpty(_owner))
					sTmpTableName = _owner + "." + sTmpTableName;

				if (!String.IsNullOrEmpty(_dbLink))
					sTmpTableName = sTmpTableName + (_dbLink.StartsWith("@") ? _dbLink : "@" + _dbLink);

				rVal += sTmpTableName + ",";
			}

			rVal = rVal.Remove(rVal.Length - 1);

			return rVal;
		}

		private string GetQuery(NomiCampi nomeCampo, string nomeTabella, TipoQuery tipoQuery)
		{
			var sql = string.Empty;
			var campoSelect = _registroNomiCampi[nomeCampo];
			var nomiCompletiTabelle = GetCompleteTableName(nomeTabella);
			var sqlSelect = "Select distinct " + Trim(campoSelect) + " from " + nomiCompletiTabelle + " where ";

			var provider = InternalDatabaseConnection.Specifics;

			switch (nomeTabella)
			{
				case NomiTabelle.AE_CIVICO_CON_CIVKEY:
					switch (tipoQuery)
					{
						case TipoQuery.Elenco:
							sql = sqlSelect +
								(((this.DataSit.CodVia != "") && (nomeCampo != NomiCampi.CodiceVia)) ? Trim(NomiCampi.CodiceVia) + " = " + RightTrim(this.DataSit.CodVia) + " and " : "") +
								(((this.DataSit.Civico != "") && (nomeCampo != NomiCampi.Civico)) ? Trim(NomiCampi.Civico) + " = " + RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Civico) ? this.DataSit.Civico : "-1") + " and " : "") +
								(((this.DataSit.Esponente != "") && (nomeCampo != NomiCampi.Esponente)) ? Trim(NomiCampi.Esponente) + " = '" + RightTrim(this.DataSit.Esponente) + "' and " : "") +
								(((this.DataSit.CodCivico != "") && (nomeCampo != NomiCampi.CodCivico)) ? Trim(NomiCampi.CodCivico) + " = '" + RightTrim(this.DataSit.CodCivico) + "' and " : "") +
								(((this.DataSit.Fabbricato != "") && (nomeCampo != NomiCampi.Fabbricato)) ? Trim(NomiCampi.Fabbricato) + " = " + RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Fabbricato) ? this.DataSit.Fabbricato : "-1") + " and " : "") +
								(((this.DataSit.Sezione != "") && (nomeCampo != NomiCampi.Sezione)) ? Trim(NomiCampi.Sezione) + " = '" + RightTrim(this.DataSit.Sezione) + "' and " : "") +
								(((this.DataSit.Foglio != "") && (nomeCampo != NomiCampi.Foglio)) ? Trim(NomiCampi.Foglio) + " = '" + RightTrim(this.DataSit.Foglio) + "' and " : "") +
								(((this.DataSit.Particella != "") && (nomeCampo != NomiCampi.Particella)) ? Trim(NomiCampi.Particella) + " = '" + RightTrim(this.DataSit.Particella) + "' and " : "");								
							break;

						case TipoQuery.Validazione:
							sql = sqlSelect +
								(((this.DataSit.CodVia != "")) ? Trim(NomiCampi.CodiceVia) + " = " + RightTrim(this.DataSit.CodVia) + " and " : "") +
								(((this.DataSit.Civico != "")) ? Trim(NomiCampi.Civico) + " = " + RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Civico) ? this.DataSit.Civico : "-1") + " and " : "") +
								(((this.DataSit.Esponente != "")) ? Trim(NomiCampi.Esponente) + " = '" + RightTrim(this.DataSit.Esponente) + "' and " : "") +
								(((this.DataSit.CodCivico != "")) ? Trim(NomiCampi.CodCivico) + " = '" + RightTrim(this.DataSit.CodCivico) + "' and " : "") +
								(((this.DataSit.Fabbricato != "")) ? Trim(NomiCampi.Fabbricato) + " = " + RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Fabbricato) ? this.DataSit.Fabbricato : "-1") + " and " : "") +
								(((this.DataSit.Sezione != "")) ? Trim(NomiCampi.Sezione) + " = '" + RightTrim(this.DataSit.Sezione) + "' and " : "") +
								(((this.DataSit.Foglio != "")) ? Trim(NomiCampi.Foglio) + " = '" + RightTrim(this.DataSit.Foglio) + "' and " : "") +
								(((this.DataSit.Particella != "")) ? Trim(NomiCampi.Particella) + " = '" + RightTrim(this.DataSit.Particella) + "' and " : "");
							
							break;
					}

					sql += Trim(NomiCampi.DataFine) + " is null order by " + Trim(campoSelect);

					break;

				case NomiTabelle.AE_INTERNO_CON_INTKEY:
					switch (tipoQuery)
					{
						case TipoQuery.Elenco:
							sql = sqlSelect +
								(((this.DataSit.Scala != "") && (nomeCampo != NomiCampi.Scala)) ? Trim(NomiCampi.Scala) + " = '" + RightTrim(this.DataSit.Scala) + "' and " : "") +
								(((this.DataSit.Interno != "") && (nomeCampo != NomiCampi.Interno)) ? Trim(NomiCampi.Interno) + " = " + RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Interno) ? this.DataSit.Interno : "-1") + " and " : "") +
								(((this.DataSit.EsponenteInterno != "") && (nomeCampo != NomiCampi.EsponenteInterno)) ? Trim(NomiCampi.EsponenteInterno) + " = '" + RightTrim(this.DataSit.EsponenteInterno) + "' and " : "") +
								(((this.DataSit.CodCivico != "") && (nomeCampo != NomiCampi.CodCivico)) ? Trim(NomiCampi.CodCivico) + " = '" + RightTrim(this.DataSit.CodCivico) + "' " : "");
							break;
						case TipoQuery.Validazione:
							sql = sqlSelect +
								(((this.DataSit.Scala != "")) ? Trim(NomiCampi.Scala) + " = '" + RightTrim(this.DataSit.Scala) + "' and " : "") +
								(((this.DataSit.Interno != "")) ? Trim(NomiCampi.Interno) + " = " + RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Interno) ? this.DataSit.Interno : "-1") + " and " : "") +
								(((this.DataSit.EsponenteInterno != "")) ? Trim(NomiCampi.EsponenteInterno) + " = '" + RightTrim(this.DataSit.EsponenteInterno) + "' and " : "") +
								(((this.DataSit.CodCivico != "")) ? Trim(NomiCampi.CodCivico) + " = '" + RightTrim(this.DataSit.CodCivico) + "' " : "");
							break;
					}

					if (sql.EndsWith("and "))
						sql = sql.Remove(sql.Length - 4, 4) + " order by " + Trim(campoSelect);
					else if (sql.EndsWith("where "))
						sql = sql.Remove(sql.Length - 6, 6) + " order by " + Trim(campoSelect);
					else
						sql = sql + " order by " + Trim(campoSelect);

					break;


				case NomiTabelle.Tutte:
					switch (tipoQuery)
					{
						case TipoQuery.Elenco:
							sql = sqlSelect +
								"AE_CIVICO_CON_CIVKEY.CIVKEY = AE_INTERNO_CON_INTKEY.CIVKEY and " +
								(((this.DataSit.CodVia != "") && (nomeCampo != NomiCampi.CodiceVia)) ? Trim(NomiCampi.CodiceVia) + " = " + RightTrim(this.DataSit.CodVia) + " and " : "") +
								(((this.DataSit.Civico != "") && (nomeCampo != NomiCampi.Civico)) ? Trim(NomiCampi.Civico) + " = " + RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Civico) ? this.DataSit.Civico : "-1") + " and " : "") +
								(((this.DataSit.Esponente != "") && (nomeCampo != NomiCampi.Esponente)) ? Trim(NomiCampi.Esponente) + " = '" + RightTrim(this.DataSit.Esponente) + "' and " : "") +
								(((this.DataSit.CodCivico != "") && (nomeCampo != NomiCampi.CodCivico)) ? Trim(NomiCampi.CodCivico) + " = '" + RightTrim(this.DataSit.CodCivico) + "' and " : "") +
								//(((this.DataSit.CAP != "") && (nomeCampo != NomiCampi.Cap)) ? ApplyRTrimTo(NomiCampi.Cap) + " = '" + RightTrim(this.DataSit.CAP) + "' and " : "") +
								(((this.DataSit.Fabbricato != "") && (nomeCampo != NomiCampi.Fabbricato)) ? Trim(NomiCampi.Fabbricato) + " = " + RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Fabbricato) ? this.DataSit.Fabbricato : "-1") + " and " : "") +
								(((this.DataSit.Sezione != "") && (nomeCampo != NomiCampi.Sezione)) ? Trim(NomiCampi.Sezione) + " = '" + RightTrim(this.DataSit.Sezione) + "' and " : "") +
								(((this.DataSit.Foglio != "") && (nomeCampo != NomiCampi.Foglio)) ? Trim(NomiCampi.Foglio) + " = '" + RightTrim(this.DataSit.Foglio) + "' and " : "") +
								(((this.DataSit.Particella != "") && (nomeCampo != NomiCampi.Particella)) ? Trim(NomiCampi.Particella) + " = '" + RightTrim(this.DataSit.Particella) + "' and " : "") +
								(((this.DataSit.Scala != "") && (nomeCampo != NomiCampi.Scala)) ? Trim(NomiCampi.Scala) + " = '" + RightTrim(this.DataSit.Scala) + "' and " : "") +
								(((this.DataSit.Interno != "") && (nomeCampo != NomiCampi.Interno)) ? Trim(NomiCampi.Interno) + " = " + RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Interno) ? this.DataSit.Interno : "-1") + " and " : "") +
								(((this.DataSit.EsponenteInterno != "") && (nomeCampo != NomiCampi.EsponenteInterno)) ? Trim(NomiCampi.EsponenteInterno) + " = '" + RightTrim(this.DataSit.EsponenteInterno) + "' and " : "");
							break;

						case TipoQuery.Validazione:
							sql = sqlSelect +
								"AE_CIVICO_CON_CIVKEY.CIVKEY = AE_INTERNO_CON_INTKEY.CIVKEY and " +
								(((this.DataSit.CodVia != "")) ? Trim(NomiCampi.CodiceVia) + " = " + RightTrim(this.DataSit.CodVia) + " and " : "") +
								(((this.DataSit.Civico != "")) ? Trim(NomiCampi.Civico) + " = " + RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Civico) ? this.DataSit.Civico : "-1") + " and " : "") +
								(((this.DataSit.Esponente != "")) ? Trim(NomiCampi.Esponente) + " = '" + RightTrim(this.DataSit.Esponente) + "' and " : "") +
								(((this.DataSit.CodCivico != "")) ? Trim(NomiCampi.CodCivico) + " = '" + RightTrim(this.DataSit.CodCivico) + "' and " : "") +
								//(((this.DataSit.CAP != "")) ? ApplyRTrimTo(NomiCampi.Cap) + " = '" + RightTrim(this.DataSit.CAP) + "' and " : "") +
								(((this.DataSit.Fabbricato != "")) ? Trim(NomiCampi.Fabbricato) + " = " + RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Fabbricato) ? this.DataSit.Fabbricato : "-1") + " and " : "") +
								(((this.DataSit.Sezione != "")) ? Trim(NomiCampi.Sezione) + " = '" + RightTrim(this.DataSit.Sezione) + "' and " : "") +
								(((this.DataSit.Foglio != "")) ? Trim(NomiCampi.Foglio) + " = '" + RightTrim(this.DataSit.Foglio) + "' and " : "") +
								(((this.DataSit.Particella != "")) ? Trim(NomiCampi.Particella) + " = '" + RightTrim(this.DataSit.Particella) + "' and " : "") +
								(((this.DataSit.Scala != "")) ? Trim(NomiCampi.Scala) + " = '" + RightTrim(this.DataSit.Scala) + "' and " : "") +
								(((this.DataSit.Interno != "")) ? Trim(NomiCampi.Interno) + " = " + RightTrim(Init.Utils.StringChecker.IsNumeric(this.DataSit.Interno) ? this.DataSit.Interno : "-1") + " and " : "") +
								(((this.DataSit.EsponenteInterno != "")) ? Trim(NomiCampi.EsponenteInterno) + " = '" + RightTrim(this.DataSit.EsponenteInterno) + "' and " : "");
							break;

					}

					sql += Trim(NomiCampi.DataFine) + " is null order by " + Trim(campoSelect);

					break;
			}

			var msg = String.Format("Query per interrogazione del sit con tipoQuery={0} : {1}", tipoQuery, sql);

			this._log.DebugFormat(msg);

			Debug.WriteLine(msg);

			return sql;
		}

		private string Trim(NomiCampi nomeCampo)
		{
			return Trim(_registroNomiCampi[nomeCampo]);
		}

		private string Trim(string nomeCampo)
		{
			var provider = InternalDatabaseConnection.Specifics;

			return String.Format(" TRIM({0}) ", nomeCampo);
		}

		private string GetElemento(NomiCampi sField, string sTableName, TipoQuery eTipoQuery)
		{
			string sRetVal = "";
			int iCount = 0;
			try
			{
				EnsureConnectionIsOpen();
				_commandText = GetQuery(sField, sTableName, eTipoQuery);

				using (IDbCommand pCommand = InternalDatabaseConnection.CreateCommand(_commandText))
				{
					using (IDataReader pDataReader = pCommand.ExecuteReader())
					{
						while (pDataReader.Read())
						{
							string dato = pDataReader[0].ToString().Trim();

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
				CloseInternalConnection();
			}
		}

		private RetSit GetElenco(NomiCampi sField, string sTableName)
		{
			RetSit pRetSit = new RetSit(true);
			try
			{
				EnsureConnectionIsOpen();
				_commandText = GetQuery(sField, sTableName, TipoQuery.Elenco);

				using (IDbCommand pCommand = InternalDatabaseConnection.CreateCommand(_commandText))
				{
					using (IDataReader pDataReader = pCommand.ExecuteReader())
					{
						while (pDataReader.Read())
						{
							var dato = pDataReader[0].ToString().Trim();

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
				CloseInternalConnection();
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
					return RestituisciErroreSit(GetMessaggioDiErrore(NomiCampi.Civico), MessageCode.ElencoCivici, false);

				return ElencoAeCivicoConCivkey(NomiCampi.Civico);
			}
			catch (CatastoException ex)
			{
				return RestituisciErroreSit(ex.Message, MessageCode.ElencoCivici, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione dell'elenco dei civici. Metodo: ElencoCivici, modulo: SitCore. " + ex.Message + "\r\n Query: " + _commandText);
			}
		}

		public override RetSit ElencoEsponenti()
		{
			try
			{
				if (String.IsNullOrEmpty(this.DataSit.CodVia) || String.IsNullOrEmpty(this.DataSit.Civico))
				{
					return RestituisciErroreSit(GetMessaggioDiErrore(NomiCampi.Esponente), MessageCode.ElencoEsponenti, false);
				}

				var sql = "select distinct trim(esponente) from " + GetCompleteTableName(NomiTabelle.AE_CIVICO_CON_CIVKEY) + " where " +
								"trim(cod_via) = '" + this.DataSit.CodVia + "' and " +
								"trim(numero) = '" + this.DataSit.Civico + "'";

				if (!String.IsNullOrEmpty(this.DataSit.Esponente))
				{
					sql += "and trim(esponente) ='" + this.DataSit.Esponente.Trim() + "'";
				}

				try
				{
					EnsureConnectionIsOpen();

					this._log.DebugFormat("Ricerca degli esponenti con la query {0}", sql);

					using (var cmd = InternalDatabaseConnection.CreateCommand(sql))
					{
						var values = cmd.SelectAll(x => x[0].ToString())
										.Where(x => !String.IsNullOrEmpty(x));

                        if (values.Count() > 0)
                        {
                            this._log.DebugFormat("Trovati {0} esponenti", values.Count());

                            return new RetSit(true)
                            {
                                DataCollection = values.ToList()
                            };
                        }
					}
				}
				finally
				{
					CloseInternalConnection();
				}

				return ElencoAeCivicoConCivkey(NomiCampi.Esponente);
			}
			catch (CatastoException ex)
			{
				return RestituisciErroreSit(ex.Message, MessageCode.ElencoEsponenti, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione dell'elenco degli esponenti. Metodo: ElencoEsponenti, modulo: SitCore. " + ex.Message + "\r\n Query: " + _commandText);
			}
		}


		public override RetSit ElencoScale()
		{
			try
			{
				if (!String.IsNullOrEmpty(this.DataSit.CodCivico) || (!String.IsNullOrEmpty(this.DataSit.Fabbricato) && !String.IsNullOrEmpty(this.DataSit.Civico) && !String.IsNullOrEmpty(this.DataSit.Esponente)))
				{
					return ElencoAeInternoConIntkey(NomiCampi.Scala);
				}

				return RestituisciErroreSit(GetMessaggioDiErrore(NomiCampi.Scala), MessageCode.ElencoScale, false);
			}
			catch (CatastoException ex)
			{
				return RestituisciErroreSit(ex.Message, MessageCode.ElencoScale, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione dell'elenco delle scale. Metodo: ElencoScale, modulo: SitCore. " + ex.Message + "\r\n Query: " + _commandText);
			}

		}

		public override RetSit ElencoInterni()
		{
			try
			{
				if (!String.IsNullOrEmpty(this.DataSit.CodCivico) || (!String.IsNullOrEmpty(this.DataSit.Fabbricato) && !String.IsNullOrEmpty(this.DataSit.Civico) && !String.IsNullOrEmpty(this.DataSit.Esponente)))
				{
					return ElencoAeInternoConIntkey(NomiCampi.Interno);
				}

				return RestituisciErroreSit(GetMessaggioDiErrore(NomiCampi.Interno), MessageCode.ElencoInterni, false);
			}
			catch (CatastoException ex)
			{
				return RestituisciErroreSit(ex.Message, MessageCode.ElencoInterni, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione dell'elenco degli interni. Metodo: ElencoInterni, modulo: SitCore. " + ex.Message + "\r\n Query: " + _commandText);
			}
		}

		public override RetSit ElencoEsponentiInterno()
		{
			try
			{
				if ((!String.IsNullOrEmpty(this.DataSit.CodCivico) && !String.IsNullOrEmpty(this.DataSit.Interno)) || (!String.IsNullOrEmpty(this.DataSit.Fabbricato) && !String.IsNullOrEmpty(this.DataSit.Civico) && !String.IsNullOrEmpty(this.DataSit.Esponente) && !String.IsNullOrEmpty(this.DataSit.Interno)))
				{
					return ElencoAeInternoConIntkey(NomiCampi.EsponenteInterno);
				}

				return RestituisciErroreSit(GetMessaggioDiErrore(NomiCampi.EsponenteInterno), MessageCode.ElencoEsponentiInterno, false);

			}
			catch (CatastoException ex)
			{
				return RestituisciErroreSit(ex.Message, MessageCode.ElencoEsponentiInterno, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione dell'elenco degli interni dell'esponente. Metodo: ElencoEsponentiInterno, modulo: SitCore. " + ex.Message + "\r\n Query: " + _commandText);
			}

		}


		public override RetSit ElencoFabbricati()
		{
			try
			{
				if ((!String.IsNullOrEmpty(this.DataSit.CodVia)) || (!String.IsNullOrEmpty(this.DataSit.Sezione) && !String.IsNullOrEmpty(this.DataSit.Foglio) && !String.IsNullOrEmpty(this.DataSit.Particella)))
				{
					return ElencoAeCivicoConCivkey(NomiCampi.Fabbricato);
				}
				return RestituisciErroreSit(GetMessaggioDiErrore(NomiCampi.Fabbricato), MessageCode.ElencoFabbricati, false);
			}
			catch (CatastoException ex)
			{
				return RestituisciErroreSit(ex.Message, MessageCode.ElencoFabbricati, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione dell'elenco dei fabbricati. Metodo: ElencoFabbricati, modulo: SitCore. " + ex.Message + "\r\n Query: " + _commandText);
			}

		}


		public override RetSit ElencoSezioni()
		{
			try
			{
				return ElencoAeCivicoConCivkey(NomiCampi.Sezione);

			}
			catch (CatastoException ex)
			{
				return RestituisciErroreSit(ex.Message, MessageCode.ElencoSezioni, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione dell'elenco delle sezioni. Metodo: ElencoSezioni, modulo: SitCore. " + ex.Message + "\r\n Query: " + _commandText);
			}

		}

		public override RetSit ElencoFogli()
		{
			try
			{
				if ((!String.IsNullOrEmpty(this.DataSit.Fabbricato)) || (!String.IsNullOrEmpty(this.DataSit.Sezione)))
				{
					return ElencoAeCivicoConCivkey(NomiCampi.Foglio);
				}

				return RestituisciErroreSit(GetMessaggioDiErrore(NomiCampi.Foglio), MessageCode.ElencoFogli, false);
			}
			catch (CatastoException ex)
			{
				return RestituisciErroreSit(ex.Message, MessageCode.ElencoFogli, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione dell'elenco dei fogli. Metodo: ElencoFogli, modulo: SitCore. " + ex.Message + "\r\n Query: " + _commandText);
			}

		}

		public override RetSit ElencoParticelle()
		{
			try
			{
				if ((!String.IsNullOrEmpty(this.DataSit.Fabbricato)) || (!String.IsNullOrEmpty(this.DataSit.Sezione) && !String.IsNullOrEmpty(this.DataSit.Foglio)))
				{
					return ElencoAeCivicoConCivkey(NomiCampi.Particella);
				}

				return RestituisciErroreSit(GetMessaggioDiErrore(NomiCampi.Particella), MessageCode.ElencoParticelle, false);
			}
			catch (CatastoException ex)
			{
				return RestituisciErroreSit(ex.Message, MessageCode.ElencoParticelle, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione dell'elenco delle particelle. Metodo: ElencoParticelle, modulo: SitCore. " + ex.Message + "\r\n Query: " + _commandText);
			}

		}

        #endregion

        #region Metodi per la verifica e la restituzione di un singolo elemento catastale o facente parte dell'indirizzo

        protected override string GetEsponente()
		{
			try
			{
				return GetAeCivicoConCivkey(NomiCampi.Esponente, TipoQuery.Elenco);
			}
			catch (CatastoException ex)
			{
				return String.Empty;
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione di un esponente. Metodo: GetEsponente, modulo: SitCore. " + ex.Message + "\r\n Query: " + _commandText);
			}

		}

		protected override RetSit VerificaEsponente()
		{
			try
			{
				string sElem = GetAeCivicoConCivkey(NomiCampi.Esponente, TipoQuery.Validazione);

				if (!String.IsNullOrEmpty(sElem))
				{
					this.DataSit.Esponente = sElem;
					return new RetSit(true);

				}
				else
				{
					return RestituisciErroreSit(GetErroreDiValidazione(NomiCampi.Esponente), MessageCode.EsponenteValidazione, false);
				}
			}
			catch (CatastoException ex)
			{
				return RestituisciErroreSit(ex.Message, MessageCode.EsponenteValidazione, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la validazione di un esponente. Metodo: VerificaEsponente, modulo: SitCore. " + ex.Message + "\r\n Query: " + _commandText);
			}


		}

		protected override string GetScala()
		{
			try
			{
				return GetAeInternoConIntkey(NomiCampi.Scala, TipoQuery.Elenco);
			}
			catch (CatastoException ex)
			{
				return String.Empty;
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione di una scala. Metodo: GetScala, modulo: SitCore. " + ex.Message + "\r\n Query: " + _commandText);
			}

		}

		protected override RetSit VerificaScala()
		{
			try
			{
				string sElem = GetAeInternoConIntkey(NomiCampi.Scala, TipoQuery.Validazione);
				if (!String.IsNullOrEmpty(sElem))
				{
					this.DataSit.Scala = sElem;
					return new RetSit(true);
				}
				else
				{
					return RestituisciErroreSit(GetErroreDiValidazione(NomiCampi.Scala), MessageCode.ScalaValidazione, false);
				}
			}
			catch (CatastoException ex)
			{
				return RestituisciErroreSit(ex.Message, MessageCode.ScalaValidazione, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la validazione di una scala. Metodo: VerificaScala, modulo: SitCore. " + ex.Message + "\r\n Query: " + _commandText);
			}


		}

		protected override string GetInterno()
		{
			try
			{
				return GetAeInternoConIntkey(NomiCampi.Interno, TipoQuery.Elenco);
			}
			catch (CatastoException ex)
			{
				return String.Empty;
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione di un interno. Metodo: GetInterno, modulo: SitCore. " + ex.Message + "\r\n Query: " + _commandText);
			}

		}

		protected override RetSit VerificaInterno()
		{
			if (!Init.Utils.StringChecker.IsNumeric(this.DataSit.Interno))
				return RestituisciErroreSit("Non è possibile validare l'interno " + this.DataSit.Interno + " perchè non è un numero", MessageCode.InternoValidazioneNumero, false);


			try
			{
				string sElem = GetAeInternoConIntkey(NomiCampi.Interno, TipoQuery.Validazione);

				if (!String.IsNullOrEmpty(sElem))
				{
					this.DataSit.Interno = sElem;
					return new RetSit(true);
				}

				return RestituisciErroreSit(GetErroreDiValidazione(NomiCampi.Interno), MessageCode.InternoValidazione, false);

			}
			catch (CatastoException ex)
			{
				return RestituisciErroreSit(ex.Message, MessageCode.InternoValidazione, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la validazione di un interno. Metodo: VerificaInterno, modulo: SitCore. " + ex.Message + "\r\n Query: " + _commandText);
			}


		}

		protected override string GetEsponenteInterno()
		{
			try
			{
				return GetAeInternoConIntkey(NomiCampi.EsponenteInterno, TipoQuery.Elenco);
			}
			catch (CatastoException ex)
			{
				return String.Empty;
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione di un esponente interno. Metodo: GetEsponenteInterno, modulo: SitCore. " + ex.Message + "\r\n Query: " + _commandText);
			}

		}

		protected override RetSit VerificaEsponenteInterno()
		{
			try
			{
				string sElem = GetAeInternoConIntkey(NomiCampi.EsponenteInterno, TipoQuery.Validazione);
				if (!String.IsNullOrEmpty(sElem))
				{
					this.DataSit.EsponenteInterno = sElem;
					return new RetSit(true);

				}

				return RestituisciErroreSit(GetErroreDiValidazione(NomiCampi.EsponenteInterno), MessageCode.EsponenteInternoValidazione, false);
			}
			catch (CatastoException ex)
			{
				return RestituisciErroreSit(ex.Message, MessageCode.EsponenteInternoValidazione, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la validazione di un esponente interno. Metodo: VerificaEsponenteInterno, modulo: SitCore. " + ex.Message + "\r\n Query: " + _commandText);
			}

		}

		protected override RetSit VerificaFabbricato()
		{
			if (!Init.Utils.StringChecker.IsNumeric(this.DataSit.Fabbricato))
				return RestituisciErroreSit("Non è possibile validare il fabbricato " + this.DataSit.Fabbricato + " perchè non è un numero", MessageCode.FabbricatoValidazioneNumero, true);

			try
			{
				string sElem = GetAeCivicoConCivkey(NomiCampi.Fabbricato, TipoQuery.Validazione);
				if (!String.IsNullOrEmpty(sElem))
				{
					this.DataSit.Fabbricato = sElem;
					return new RetSit(true);
				}

				return RestituisciErroreSit(GetErroreDiValidazione(NomiCampi.Fabbricato), MessageCode.FabbricatoValidazione, false);
			}
			catch (CatastoException ex)
			{
				return RestituisciErroreSit(ex.Message, MessageCode.FabbricatoValidazione, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la validazione di un fabbricato. Metodo: VerificaFabbricato, modulo: SitCore. " + ex.Message + "\r\n Query: " + _commandText);
			}
		}
		
		protected override string GetSezione()
		{
			try
			{
				return GetAeCivicoConCivkey(NomiCampi.Sezione, TipoQuery.Elenco);
			}
			catch (CatastoException ex)
			{
				return String.Empty;
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione di una sezione. Metodo: GetSezione, modulo: SitCore. " + ex.Message + "\r\n Query: " + _commandText);
			}

		}
		
		protected override RetSit VerificaTipoCatasto()
		{
			if (IsCampiToponomasticaImmobileVuoti())
				return new RetSit(true);

			if (DataSit.TipoCatasto == "T")
				return RestituisciErroreSit("Il tipocatasto " + DataSit.TipoCatasto + " non è valido per i dati inseriti", MessageCode.TipoCatastoValidazione, false);

			return new RetSit(true);
		}

		protected override RetSit VerificaSezione()
		{
			try
			{
				string sElem = GetAeCivicoConCivkey(NomiCampi.Sezione, TipoQuery.Validazione);
				if (!String.IsNullOrEmpty(sElem))
				{
					this.DataSit.Sezione = sElem;
					return new RetSit(true);
				}

				return RestituisciErroreSit(GetErroreDiValidazione(NomiCampi.Sezione), MessageCode.SezioneValidazione, false);
			}
			catch (CatastoException ex)
			{
				return RestituisciErroreSit(ex.Message, MessageCode.SezioneValidazione, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la validazione di una sezione. Metodo: VerificaSezione, modulo: SitCore. " + ex.Message + "\r\n Query: " + _commandText);
			}
		}

		protected override string GetFoglio()
		{
			try
			{
				return GetAeCivicoConCivkey(NomiCampi.Foglio, TipoQuery.Elenco);
			}
			catch (CatastoException ex)
			{
				return String.Empty;
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione di un foglio. Metodo: GetFoglio, modulo: SitCore. " + ex.Message + "\r\n Query: " + _commandText);
			}
		}

		protected override RetSit VerificaFoglio()
		{
			try
			{
				string sElem = GetAeCivicoConCivkey(NomiCampi.Foglio, TipoQuery.Validazione);

				if (!String.IsNullOrEmpty(sElem))
				{
					this.DataSit.Foglio = sElem;
					return new RetSit(true);
				}

				return RestituisciErroreSit(GetErroreDiValidazione(NomiCampi.Foglio), MessageCode.FoglioValidazione, false);

			}
			catch (CatastoException ex)
			{
				return RestituisciErroreSit(ex.Message, MessageCode.FoglioValidazione, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la validazione di un foglio. Metodo: VerificaFoglio, modulo: SitCore. " + ex.Message + "\r\n Query: " + _commandText);
			}
		}

		protected override string GetParticella()
		{
			try
			{
				return GetAeCivicoConCivkey(NomiCampi.Particella, TipoQuery.Elenco);
			}
			catch (CatastoException ex)
			{
				return String.Empty;
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione di una particella. Metodo: GetParticella, modulo: SitCore. " + ex.Message + "\r\n Query: " + _commandText);
			}
		}

		protected override RetSit VerificaParticella()
		{
			try
			{
				string sElem = GetAeCivicoConCivkey(NomiCampi.Particella, TipoQuery.Validazione);
				if (!String.IsNullOrEmpty(sElem))
				{
					this.DataSit.Particella = sElem;
					return new RetSit(true);
				}

				return RestituisciErroreSit(GetErroreDiValidazione(NomiCampi.Particella), MessageCode.ParticellaValidazione, false);
			}
			catch (CatastoException ex)
			{
				return RestituisciErroreSit(ex.Message, MessageCode.ParticellaValidazione, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la validazione di una particella. Metodo: VerificaParticella, modulo: SitCore. " + ex.Message + "\r\n Query: " + _commandText);
			}
		}

		protected override string GetCivico()
		{
			try
			{
				return GetAeCivicoConCivkey(NomiCampi.Civico, TipoQuery.Elenco);
			}
			catch (CatastoException ex)
			{
				return String.Empty;
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione di un civico. Metodo: GetCivico, modulo: SitCore. " + ex.Message + "\r\n Query: " + _commandText);
			}
		}

		protected override RetSit VerificaCivico()
		{
			if (!Init.Utils.StringChecker.IsNumeric(this.DataSit.Civico))
				return RestituisciErroreSit("Non è possibile validare il civico " + this.DataSit.Civico + " perchè non è un numero", MessageCode.CivicoValidazioneNumero, false);

			try
			{
				string sElem = GetAeCivicoConCivkey(NomiCampi.Civico, TipoQuery.Validazione);

				if (!String.IsNullOrEmpty(sElem))
				{
					this.DataSit.Civico = sElem;
					return new RetSit(true);
				}

				return RestituisciErroreSit(GetErroreDiValidazione(NomiCampi.Civico), MessageCode.CivicoValidazione, false);
			}
			catch (CatastoException ex)
			{
				return RestituisciErroreSit(ex.Message, MessageCode.CivicoValidazione, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la validazione di un civico. Metodo: VerificaCivico, modulo: SitCore. " + ex.Message + "\r\n Query: " + _commandText);
			}
		}

		protected override string GetCAP()
		{
			try
			{
				return String.Empty;// GetAeCivicoConCivkey(NomiCampi.Cap, TipoQuery.Elenco);
			}
			catch (CatastoException ex)
			{
				return String.Empty;
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione di un CAP. Metodo: GetCAP, modulo: SitCore. " + ex.Message + "\r\n Query: " + _commandText);
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
				return RestituisciErroreSit(ex.Message, MessageCode.CAPValidazione, false);
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la validazione di un CAP. Metodo: VerificaCAP, modulo: SitCore. " + ex.Message + "\r\n Query: " + _commandText);
			}
		}

		protected override string GetCodCivico()
		{
			try
			{
				// Se ho il civico e il codice via posso cercare direttamente una CIV_KEY. L'esponente va ricercato per match esatto
				if (!String.IsNullOrEmpty(this.DataSit.CodVia) && !String.IsNullOrEmpty(this.DataSit.Civico))
				{
					var sql = "select distinct trim(CIVKEY) from " + GetCompleteTableName(NomiTabelle.AE_CIVICO_CON_CIVKEY) + " where " +
								"trim(cod_via) = '" + this.DataSit.CodVia + "' and " +
								"trim(numero) = '" + this.DataSit.Civico + "' and " +  
								"trim(esponente) ";								

					if (String.IsNullOrEmpty(this.DataSit.Esponente))
					{
						sql += "is null";
					}
					else
					{
						sql += "= '" + this.DataSit.Esponente + "'";
					}

					try
					{
						EnsureConnectionIsOpen();

						this._log.DebugFormat("Ricerca del cod civico con la query {0}", sql);

						using (var cmd = InternalDatabaseConnection.CreateCommand(sql))
						{
							var values = cmd.SelectAll(x => x[0].ToString());

							if (values.Count() == 1)
							{
								this.DataSit.Scala =
								this.DataSit.Interno =
								this.DataSit.EsponenteInterno =
								this.DataSit.Fabbricato =
								this.DataSit.TipoCatasto = 
								this.DataSit.Foglio = 
								this.DataSit.Particella = 
								this.DataSit.Sub = String.Empty;

								return values.ElementAt(0);
							}
						}
					}
					finally
					{
						CloseInternalConnection();
					}
				}

				this._log.Debug("Cod civico non trovato, procedo con la ricerca generica{0}");

				return GetAeCivicoConCivkey(NomiCampi.CodCivico, TipoQuery.Elenco);
			}
			catch (CatastoException ex)
			{
				return String.Empty;
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione di un codice civico. Metodo: GetCodCivico, modulo: SitCore. " + ex.Message + "\r\n Query: " + _commandText);
			}
		}

		protected override string GetCodFabbricato()
		{
			try
			{
				return GetAeCivicoConCivkey(NomiCampi.Fabbricato, TipoQuery.Elenco);
			}
			catch (CatastoException ex)
			{
				return String.Empty;
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione di un codice fabbricato. Metodo: GetCodFabbricato, modulo: SitCore. " + ex.Message + "\r\n Query: " + _commandText);
			}
		}

		protected override string GetCodVia()
		{
			try
			{
				return GetAeCivicoConCivkey(NomiCampi.CodiceVia, TipoQuery.Elenco);
			}
			catch (CatastoException ex)
			{
				return String.Empty;
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la restituzione di un codice via. Metodo: GetCodVia, modulo: SitCore. " + ex.Message + "\r\n Query: " + _commandText);
			}
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
				//SitIntegrationService.NomiCampiSit.EsponenteInterno,
				//SitIntegrationService.NomiCampiSit.Fabbricato,
				SitIntegrationService.NomiCampiSit.Sezione,
				SitIntegrationService.NomiCampiSit.TipoCatasto,
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














