using System;
using System.Collections;
using System.Collections.Specialized;
using System.Data;
using System.Data.OleDb;
using System.Data.SqlClient;
using System.Diagnostics;
using System.Text;
using Init.SIGePro.Authentication;
using Init.SIGePro.Collection;
using Init.SIGePro.Data;
using Init.Utils;
using PersonalLib2.Data;
using log4net;

namespace Init.SIGePro.Manager.Logic.Visura
{
	/// <summary>
	/// Descrizione di riepilogo per VisuraPraticheManager.
	/// </summary>
	public class VisuraPraticheManager
	{
		ILog m_logger = LogManager.GetLogger(typeof(VisuraPraticheManager));
		DataBase m_database;
		string m_idComune;

		public VisuraPraticheManager(AuthenticationInfo authInfo)
			: this(authInfo.CreateDatabase(), authInfo.IdComune)
		{
		}

		public VisuraPraticheManager(DataBase db, string idComune)
		{
			m_database = db;
			m_idComune = idComune;
		}



		/// <summary>
		/// Ritorna il tipo di provider utilizzato dal database correntemente in uso
		/// </summary>
		/// <returns>tipo di provider utilizzato dal database correntemente in uso</returns>
		protected ProviderType GetProviderType()
		{
			if (m_database.Connection is OleDbConnection)
			{
				OleDbConnection oleCnn = (OleDbConnection)m_database.Connection;
				string provider = oleCnn.Provider.ToUpper();

				if (provider == "ORAOLEDB.ORACLE.1" || provider == "MSDAORA.1")
					return ProviderType.OracleClient;

				if (provider == "SQLOLEDB.1")
					return ProviderType.SqlClient;

				throw new InvalidOperationException("Il provider " + provider + " non è supportato");
			}
			else
			{
				string connectionTypeName = m_database.Connection.GetType().ToString();
				if (connectionTypeName.ToUpper() == "SYSTEM.DATA.ORACLECLIENT.ORACLECONNECTION")
					return ProviderType.OracleClient;

				if (connectionTypeName.ToUpper() == "SYSTEM.DATA.SQLCLIENT.SQLCONNECTION")
					return ProviderType.SqlClient;

				if (connectionTypeName.ToUpper() == "MYSQL.DATA.MYSQLCLIENT.MYSQLCONNECTION")
					return ProviderType.MySqlClient;

				throw new InvalidOperationException("Il provider " + connectionTypeName + " non è supportato");
			}
		}



		#region generazione della query per la lista pratiche


		/// <summary>
		/// Ottiene una lista di pratiche in base ai criteri di ricerca passati nella classe <see cref="RichiestaListaPratiche"/>
		/// </summary>
		/// <param name="richiesta">Parametri di ricerca</param>
		/// <returns>Lista di pratiche corrispondenti ai criteri di ricerca</returns>
		public ListaPratiche GetListaPratiche(RichiestaListaPratiche richiesta)
		{
			// IdComune e software devono essere sempre specificati
			if (String.IsNullOrEmpty( richiesta.CodEnte ) )
				throw new Exception("Codice ente non specificato");

			int limiteRecords = richiesta.LimiteRecords.GetValueOrDefault(-1);

			StringCollection filtriListaPratiche = GetFiltriRicercaSoggettiInListaPratiche(richiesta);

			StringCollection filtriSoggettiCollegati = GetFiltriRicercaSoggettiInSoggettiCollegati(richiesta);

			bool usaTabellaSoggettiCollegati = filtriSoggettiCollegati.Count > 0;
			bool outerJoinSuSoggettiCollegati = NecessariaOuterJoinSuSoggettiCollegati(richiesta);

			bool usaTabellaIstanzeMappali = !String.IsNullOrEmpty(richiesta.TipoCatasto) ||
													!String.IsNullOrEmpty(richiesta.Foglio) ||
													!String.IsNullOrEmpty(richiesta.Particella) ||
													!String.IsNullOrEmpty(richiesta.Subalterno);

			string filtriStatoIstanza = GetFiltriStatoIstanza(richiesta.CodSportello);

			bool usaTabellaIstanzeStradario = !String.IsNullOrEmpty(richiesta.CodViario);
			bool usaTabellaAutorizzazioni = !String.IsNullOrEmpty(richiesta.NumeroAutorizzazione);

			string parteInizialeSelect = " from vw_listapratiche ";

			string selectSoggettiCollegati = @"select distinct
														ISTANZERICHIEDENTI.IDCOMUNE,
														ISTANZERICHIEDENTI.CODICEISTANZA
													From
														ISTANZERICHIEDENTI, ANAGRAFE
													Where
														ANAGRAFE.IDCOMUNE=ISTANZERICHIEDENTI.IDCOMUNE and 
														ANAGRAFE.CODICEANAGRAFE=ISTANZERICHIEDENTI.CODICERICHIEDENTE and";

			string whereConSoggettiCollegati = @"SOGGETTICOLLEGATI.IDCOMUNE=VW_LISTAPRATICHE.IDCOMUNE and SOGGETTICOLLEGATI.CODICEISTANZA=VW_LISTAPRATICHE.IDPRATICA";
			string whereIstanzeMappali = @"ISTANZEMAPPALI.IDCOMUNE=VW_LISTAPRATICHE.IDCOMUNE and ISTANZEMAPPALI.FKCODICEISTANZA=VW_LISTAPRATICHE.IDPRATICA";
			string whereIstanzeAutorizzazioni = @"AUTORIZZAZIONI.IDCOMUNE=VW_LISTAPRATICHE.IDCOMUNE and AUTORIZZAZIONI.FKIDISTANZA=VW_LISTAPRATICHE.IDPRATICA";
			string whereIstanzeStradario = @"ISTANZESTRADARIO.IDCOMUNE=VW_LISTAPRATICHE.IDCOMUNE and ISTANZESTRADARIO.CODICEISTANZA=VW_LISTAPRATICHE.IDPRATICA";


			if (usaTabellaSoggettiCollegati)
				filtriSoggettiCollegati.Add("ANAGRAFE.IDCOMUNE='" + SafeString(richiesta.CodEnte) + "'");

			// Generazione degli altri filtri della lista pratiche
			filtriListaPratiche.Add("VW_LISTAPRATICHE.IDCOMUNE='" + SafeString(richiesta.CodEnte) + "'");

			if (!StringChecker.IsStringEmpty(richiesta.IdPratica))
				filtriListaPratiche.Add("VW_LISTAPRATICHE.idpratica like '" + SafeString(richiesta.IdPratica) + "%'");

			if (!StringChecker.IsStringEmpty(richiesta.CodSportello))
				filtriListaPratiche.Add("VW_LISTAPRATICHE.SOFTWARE ='" + SafeString(richiesta.CodSportello) + "'");

			if (!StringChecker.IsStringEmpty(richiesta.NumeroPratica))
				filtriListaPratiche.Add("VW_LISTAPRATICHE.NUMEROPRATICA ='" + SafeString(richiesta.NumeroPratica) + "'");

			if (!StringChecker.IsStringEmpty(richiesta.NumeroProtocollo))
				filtriListaPratiche.Add("VW_LISTAPRATICHE.NUMEROPROTOCOLLO ='" + SafeString(richiesta.NumeroProtocollo) + "'");

			// TODO: prima era un valore datetime, ora arriva come stringa
			// fare tutti i controlli del caso ed eventualmente castarlo a data
			if (richiesta.DataProtocolloSpecified)
				filtriListaPratiche.Add("VW_LISTAPRATICHE.DATAPROTOCOLLO ='" + richiesta.DataProtocollo.ToString("dd/MM/yyyy") + "'");


			if (!StringChecker.IsStringEmpty(richiesta.Indirizzo))
				filtriListaPratiche.Add("VW_LISTAPRATICHE.PR_INDIRIZZO ='" + SafeString(richiesta.Indirizzo) + "'");

			if (!StringChecker.IsStringEmpty(richiesta.CodCivico))
				filtriListaPratiche.Add("VW_LISTAPRATICHE.PR_CODCIVICO ='" + SafeString(richiesta.CodCivico) + "'");

			if (!StringChecker.IsStringEmpty(richiesta.Civico))
				filtriListaPratiche.Add("VW_LISTAPRATICHE.PR_CIVICO ='" + SafeString(richiesta.Civico) + "'");

			if (!StringChecker.IsStringEmpty(richiesta.Foglio))
				filtriListaPratiche.Add("VW_LISTAPRATICHE.FOGLIO ='" + SafeString(richiesta.Foglio) + "'");

			if (!StringChecker.IsStringEmpty(richiesta.Particella))
				filtriListaPratiche.Add("VW_LISTAPRATICHE.PARTICELLA ='" + SafeString(richiesta.Particella) + "'");

			if (!StringChecker.IsStringEmpty(richiesta.Subalterno))
				filtriListaPratiche.Add("VW_LISTAPRATICHE.SUB ='" + SafeString(richiesta.Subalterno) + "'");

			if (!StringChecker.IsStringEmpty(richiesta.Stato))
				filtriListaPratiche.Add("VW_LISTAPRATICHE.CODSTATOPRATICA ='" + SafeString(richiesta.Stato) + "'");

			if (!StringChecker.IsStringEmpty(richiesta.Oggetto))
				filtriListaPratiche.Add("VW_LISTAPRATICHE.OGGETTOU like '" + SafeString(richiesta.Oggetto).ToUpper() + "%'");

			if (!StringChecker.IsStringEmpty(richiesta.AnnoIstanza))
				filtriListaPratiche.Add("VW_LISTAPRATICHE.ANNOPRESENTAZIONE = '" + SafeString(richiesta.AnnoIstanza) + "'");

			if (!StringChecker.IsStringEmpty(richiesta.MeseIstanza))
				filtriListaPratiche.Add("VW_LISTAPRATICHE.MESEPRESENTAZIONE = '" + SafeString(richiesta.MeseIstanza) + "'");

			if (!StringChecker.IsStringEmpty(richiesta.CodiceIntervento))
				filtriListaPratiche.Add("VW_LISTAPRATICHE.CODICEINTERVENTO = '" + SafeString(richiesta.CodiceIntervento) + "'");

			if (!StringChecker.IsStringEmpty(filtriStatoIstanza))
				filtriListaPratiche.Add("VW_LISTAPRATICHE.CODSTATOPRATICA in (" + filtriStatoIstanza + ")");


			if (usaTabellaAutorizzazioni)
				filtriListaPratiche.Add("AUTORIZZAZIONI.AUTORIZNUMERO = '" + SafeString(richiesta.NumeroAutorizzazione) + "'");




			if (usaTabellaIstanzeStradario)
			{
				filtriListaPratiche.Add("ISTANZESTRADARIO.CODICESTRADARIO = '" + SafeString(richiesta.CodViario) + "'");

				if (!StringChecker.IsStringEmpty(richiesta.Civico))
					filtriListaPratiche.Add("ISTANZESTRADARIO.CIVICO = '" + SafeString(richiesta.Civico) + "'");
			}




			if (usaTabellaIstanzeMappali)
			{
				if (!StringChecker.IsStringEmpty(richiesta.TipoCatasto))
					filtriListaPratiche.Add("ISTANZEMAPPALI.CODICECATASTO = '" + SafeString(richiesta.TipoCatasto) + "'");

				if (!StringChecker.IsStringEmpty(richiesta.Foglio))
					filtriListaPratiche.Add("ISTANZEMAPPALI.FOGLIO = '" + SafeString(richiesta.Foglio) + "'");

				if (!StringChecker.IsStringEmpty(richiesta.Particella))
					filtriListaPratiche.Add("ISTANZEMAPPALI.PARTICELLA = '" + SafeString(richiesta.Particella) + "'");

				if (!StringChecker.IsStringEmpty(richiesta.Subalterno))
					filtriListaPratiche.Add("ISTANZEMAPPALI.SUB = '" + SafeString(richiesta.Subalterno) + "'");
			}


			// Generazione della query
			StringBuilder sbSql = new StringBuilder();

			sbSql.Append(parteInizialeSelect);

			if (usaTabellaSoggettiCollegati)
			{
				if (outerJoinSuSoggettiCollegati)
					sbSql.Append(" left outer join ");
				else
					sbSql.Append(" left join ");

				sbSql.AppendFormat("({0}{1}) SOGGETTICOLLEGATI on {2}", selectSoggettiCollegati,
																		 JoinFilters(filtriSoggettiCollegati, "AND"),
																		 whereConSoggettiCollegati);
			}


			if (usaTabellaAutorizzazioni)
			{
					sbSql.Append(" left join AUTORIZZAZIONI on ");
					sbSql.Append(whereIstanzeAutorizzazioni);
			}

			if (usaTabellaIstanzeMappali)
			{
					sbSql.Append(" left join IstanzeMappali on ");
					sbSql.Append(whereIstanzeMappali);
			}

			if (usaTabellaIstanzeStradario)
			{
				sbSql.Append(" join IstanzeStradario on ");
				sbSql.Append(whereIstanzeStradario);
			}

			sbSql.Append(" where 1 = 1 ");

			if (filtriListaPratiche.Count > 0)
				sbSql.Append(" AND ");

			sbSql.Append(JoinFilters(filtriListaPratiche, "AND"));
			sbSql.Append(" order by IDPRATICA DESC");

			m_logger.DebugFormat("VisuraPraticheManager->GetListaPratiche: Filtri di ricerca della visura pratiche: \r\n{0}", sbSql.ToString());

			string sqlTmp = sbSql.ToString();


			bool closeCnn = false;

			if (m_database.Connection.State == ConnectionState.Closed)
			{
				closeCnn = true;
				m_database.Connection.Open();
			}

			try
			{
				if (limiteRecords > 0)
				{
					m_logger.DebugFormat("VisuraPraticheManager->GetListaPratiche:Limite records {0}", limiteRecords);

					var sqlCount = "select count(*) " + sqlTmp;
					var intCount = 0;

					using (IDbCommand cmd = m_database.CreateCommand(sqlCount))
					{
						var objCount = cmd.ExecuteScalar();

						if (objCount != DBNull.Value)
							intCount = Convert.ToInt32(objCount);
					}

					if (intCount > limiteRecords)
					{
						m_logger.DebugFormat("VisuraPraticheManager->GetListaPratiche:Limite records superato: Limite={0}, records restituiti={1}", limiteRecords, intCount);

						return new ListaPratiche
						{
							LimiteRecordsSuperato = true
						};
					}
				}

				sqlTmp = "select distinct vw_listapratiche.* " + sqlTmp;

				using (IDbCommand cmd = m_database.CreateCommand(sqlTmp))
				{
					ArrayList results = new ArrayList();

					using (IDataReader reader = cmd.ExecuteReader())
					{
						while (reader.Read())
						{
							IdentificativoPratica datiPratica = new IdentificativoPratica();
							datiPratica.DatiPratica = new DatiPratica();
							datiPratica.Localizzazione = new Localizzazione();
							datiPratica.RifCatastale = new RifCatastale();
							datiPratica.Soggetto = new Soggetto();

							datiPratica.DatiPratica.CodSportelloBack = reader["Software"].ToString();
							datiPratica.DatiPratica.DesSportelloBack = reader["DescSoftware"].ToString();
							datiPratica.DatiPratica.CodStatoPratica = reader["CodStatoPratica"].ToString();
							datiPratica.DatiPratica.DataPresentazione = String.IsNullOrEmpty(reader["DataPresentazione"].ToString()) ? String.Empty : Convert.ToDateTime(reader["DataPresentazione"]).ToString("dd/MM/yyyy");
							datiPratica.DatiPratica.DataProtocollo = String.IsNullOrEmpty(reader["DataProtocollo"].ToString()) ? String.Empty : Convert.ToDateTime(reader["DataProtocollo"]).ToString("dd/MM/yyyy");
							datiPratica.DatiPratica.DescrizioneIntervento = reader["descrizioneintervento"].ToString();
							datiPratica.DatiPratica.IdPratica = reader["IdPratica"].ToString();
							datiPratica.DatiPratica.NumeroPratica = reader["NumeroPratica"].ToString();

							datiPratica.DatiPratica.Zonizzazione = reader["ZONIZZAZIONE"].ToString();
							datiPratica.DatiPratica.DescrizioneProcedura = reader["PROCEDURA"].ToString();

							if (!reader.IsDBNull(reader.GetOrdinal("NumeroProtocollo")))
								datiPratica.DatiPratica.NumeroProtocollo = reader["NumeroProtocollo"].ToString();

							if (!reader.IsDBNull(reader.GetOrdinal("Oggetto")))
								datiPratica.DatiPratica.Oggetto = reader["Oggetto"].ToString();

							datiPratica.DatiPratica.ResponsabileProcedimento = reader["responsabile"].ToString();
							datiPratica.DatiPratica.StatoPratica = reader["StatoPratica"].ToString();


							if (!reader.IsDBNull(reader.GetOrdinal("pr_civico")))
								datiPratica.Localizzazione.Civico = reader["pr_civico"].ToString();

							if (!reader.IsDBNull(reader.GetOrdinal("pr_codcivico")))
								datiPratica.Localizzazione.CodCivico = reader["pr_codcivico"].ToString();

							if (!reader.IsDBNull(reader.GetOrdinal("pr_codviario")))
								datiPratica.Localizzazione.CodViario = reader["pr_codviario"].ToString();

							if (!reader.IsDBNull(reader.GetOrdinal("pr_Indirizzo")))
								datiPratica.Localizzazione.Indirizzo = reader["pr_Indirizzo"].ToString();


							if (!reader.IsDBNull(reader.GetOrdinal("Foglio")))
								datiPratica.RifCatastale.Foglio = reader["Foglio"].ToString();

							if (!reader.IsDBNull(reader.GetOrdinal("Particella")))
								datiPratica.RifCatastale.Particella = reader["Particella"].ToString();

							if (!reader.IsDBNull(reader.GetOrdinal("Sub")))
								datiPratica.RifCatastale.Subalterno = reader["Sub"].ToString();

							if (!reader.IsDBNull(reader.GetOrdinal("TipoCatasto")))
								datiPratica.RifCatastale.TipoCatasto = reader["TipoCatasto"].ToString();

							if (!reader.IsDBNull(reader.GetOrdinal("Cap")))
								datiPratica.Soggetto.Cap = reader["Cap"].ToString();

							if (!reader.IsDBNull(reader.GetOrdinal("Citta")))
								datiPratica.Soggetto.Citta = reader["Citta"].ToString();

							if (!reader.IsDBNull(reader.GetOrdinal("codicefiscale")))
								datiPratica.Soggetto.CodFiscale = reader["codicefiscale"].ToString();

							if (!reader.IsDBNull(reader.GetOrdinal("Indirizzo")))
								datiPratica.Soggetto.Indirizzo = reader["Indirizzo"].ToString();

							if (!reader.IsDBNull(reader.GetOrdinal("Localita")))
								datiPratica.Soggetto.Localita = reader["Localita"].ToString();

							if (!reader.IsDBNull(reader.GetOrdinal("Nominativo")))
								datiPratica.Soggetto.Nominativo = reader["Nominativo"].ToString();

							if (!reader.IsDBNull(reader.GetOrdinal("PartitaIva")))
								datiPratica.Soggetto.PartitaIva = reader["PartitaIva"].ToString();

							if (!reader.IsDBNull(reader.GetOrdinal("Provincia")))
								datiPratica.Soggetto.Provincia = reader["Provincia"].ToString();

							if (!reader.IsDBNull(reader.GetOrdinal("TipoRapporto")))
								datiPratica.Soggetto.TipoRapporto = reader["TipoRapporto"].ToString();

							results.Add(datiPratica);
						}
					}

					ListaPratiche retVal = new ListaPratiche();

					retVal.EnteTitolare = new EnteTitolare();
					retVal.EnteTitolare.Sportello = new Sportello();

					retVal.EnteTitolare.Ente = new Ente();

					retVal.EnteTitolare.Ente.CodEnte = richiesta.CodEnte;
					retVal.EnteTitolare.Ente.DesEnte = "";

					if (results.Count > 0)
					{
						retVal.Pratiche = new IdentificativoPratica[results.Count];
						results.CopyTo(retVal.Pratiche);

						retVal.EnteTitolare.Sportello.CodSportello = retVal.Pratiche[0].DatiPratica.CodSportelloBack;
						retVal.EnteTitolare.Sportello.DesSportello = retVal.Pratiche[0].DatiPratica.DesSportelloBack;
					}

					return retVal;
				}

			}
			finally
			{
				if (closeCnn)
					m_database.Connection.Close();
			}
		}

		private string GetFiltriStatoIstanza(string sportello)
		{
			if (StringChecker.IsStringEmpty(sportello)) return "";

			string sql = "select valore from FO_CONFIGURAZIONE where idcomune='" + m_idComune + "' and software='" + sportello + "' and FK_IDCONFIGURAZIONEBASE='49'";

			bool openConnection = false;

			try
			{

				using (IDbCommand cmd = m_database.CreateCommand(sql))
				{
					if (cmd.Connection.State == ConnectionState.Closed)
					{
						openConnection = true;
						cmd.Connection.Open();
					}

					object obj = cmd.ExecuteScalar();

					if (obj == null || obj == DBNull.Value) return "";

					return obj.ToString();
				}
			}
			finally
			{
				if (openConnection)
					m_database.Connection.Close();
			}
		}


		/// <summary>
		/// Verifica se è necessaria una outer join tra la vista delle istanze e la tabella soggetti collegati.
		/// (Va effettuata una outer join solo se almeno uno dei soggetti ricercati oltre ad avere il flag cercaNeiSoggettiCollegati impostato a true
		/// ha anche uno dei flag cercaComeAzienda,cercaComeRichiedente o cercaComeTecnico attivato)
		/// </summary>
		/// <param name="richiesta"></param>
		/// <returns></returns>
		protected bool NecessariaOuterJoinSuSoggettiCollegati(RichiestaListaPratiche richiesta)
		{
			if (richiesta.CodFiscale != null)
			{
				foreach (RichiestaListaPraticheCodFiscale cf in richiesta.CodFiscale)
				{
					if (!cf.cercaNeiSoggettiCollegati) continue;

					if (cf.cercaComeAzienda || cf.cercaComeRichiedente || cf.cercaComeTecnico) return true;
				}
			}

			if (richiesta.PartitaIva != null)
			{
				foreach (RichiestaListaPratichePartitaIva pi in richiesta.PartitaIva)
				{
					if (!pi.cercaNeiSoggettiCollegati) continue;

					if (pi.cercaComeAzienda || pi.cercaComeRichiedente || pi.cercaComeTecnico) return true;
				}
			}

			return false;
		}


		/// <summary>
		/// In base alla classe di ricerca ritorna la lista di filtri relativi ai soggetti da applicare alla tabella Soggetticollegati
		/// </summary>
		/// <param name="richiesta">Parametri di ricerca dell'istanza</param>
		/// <returns>Lista di filtri da applicare alla tabella Soggetticollegati</returns>
		protected StringCollection GetFiltriRicercaSoggettiInSoggettiCollegati(RichiestaListaPratiche richiesta)
		{
			StringCollection ret = new StringCollection();

			if (richiesta.CodFiscale != null)
			{
				foreach (RichiestaListaPraticheCodFiscale cf in richiesta.CodFiscale)
				{
					if (!cf.cercaNeiSoggettiCollegati) continue;

					StringBuilder sb = new StringBuilder();
					sb.AppendFormat(" ( ANAGRAFE.CODICEFISCALE='{0}' ", SafeString(cf.Value));

					if (cf.cercaAncheComePartitaIva)
						sb.AppendFormat(" OR ANAGRAFE.PARTITAIVA='{0}' ", SafeString(cf.Value));

					sb.Append(") ");

					ret.Add(sb.ToString());
				}
			}

			if (richiesta.PartitaIva != null)
			{
				foreach (RichiestaListaPratichePartitaIva pi in richiesta.PartitaIva)
				{
					if (!pi.cercaNeiSoggettiCollegati) continue;

					StringBuilder sb = new StringBuilder();
					sb.AppendFormat(" ( ANAGRAFE.PARTITAIVA='{0}' ", SafeString(pi.Value) );

					if (pi.cercaAncheComeCodiceFiscale)
						sb.AppendFormat(" OR ANAGRAFE.CODICEFISCALE='{0}' ", SafeString(pi.Value) );

					sb.Append(") ");

					ret.Add(sb.ToString());
				}
			}
			return ret;
		}



		/// <summary>
		/// In base alla classe di ricerca ritorna la lista di filtri relativi ai soggetti da applicare alla vista delle istanze
		/// </summary>
		/// <param name="richiesta">Parametri di ricerca dell'istanza</param>
		/// <returns>Lista di filtri da applicare alla tabella Soggetticollegati</returns>
		protected StringCollection GetFiltriRicercaSoggettiInListaPratiche(RichiestaListaPratiche richiesta)
		{
			StringCollection ret = new StringCollection();

			if (richiesta.CodFiscale != null)
			{
				foreach (RichiestaListaPraticheCodFiscale cf in richiesta.CodFiscale)
				{
					StringCollection subFilters = new StringCollection();

					// Ricerca come richiedente
					if (cf.cercaComeRichiedente)
						subFilters.Add(CreaFiltroPerCodiceFiscalePartitaIva(cf.Value, cf.cercaAncheComePartitaIva, "CODICEFISCALE", "PARTITAIVA"));

					// Ricerca come tecnico
					if (cf.cercaComeTecnico)
						subFilters.Add(CreaFiltroPerCodiceFiscalePartitaIva(cf.Value, cf.cercaAncheComePartitaIva, "TEC_CODICEFISCALE", "TEC_PARTITAIVA"));

					// Ricerca come azienda
					if (cf.cercaComeAzienda)
						subFilters.Add(CreaFiltroPerCodiceFiscalePartitaIva(cf.Value, cf.cercaAncheComePartitaIva, "AZ_CODICEFISCALE", "AZ_PARTITAIVA"));

					// Ricerca nei soggetti collegati?
					if (cf.cercaNeiSoggettiCollegati)
						subFilters.Add("SOGGETTICOLLEGATI.IDCOMUNE is not null");


					// Unisco i filtri
					if (subFilters.Count > 0)
						ret.Add(JoinFilters(subFilters, "OR"));
				}
			}


			if (richiesta.PartitaIva != null)
			{
				foreach (RichiestaListaPratichePartitaIva pi in richiesta.PartitaIva)
				{
					StringCollection subFilters = new StringCollection();

					// Ricerca come richiedente
					if (pi.cercaComeRichiedente)
						subFilters.Add(CreaFiltroPerCodiceFiscalePartitaIva(pi.Value, pi.cercaAncheComeCodiceFiscale, "PARTITAIVA", "CODICEFISCALE"));

					// Ricerca come tecnico
					if (pi.cercaComeTecnico)
						subFilters.Add(CreaFiltroPerCodiceFiscalePartitaIva(pi.Value, pi.cercaAncheComeCodiceFiscale, "TEC_PARTITAIVA", "TEC_CODICEFISCALE"));

					// Ricerca come azienda
					if (pi.cercaComeAzienda)
						subFilters.Add(CreaFiltroPerCodiceFiscalePartitaIva(pi.Value, pi.cercaAncheComeCodiceFiscale, "AZ_PARTITAIVA", "AZ_CODICEFISCALE"));

					// Unisco i filtri
					if (subFilters.Count > 0)
						ret.Add(JoinFilters(subFilters, "OR"));
				}
			}

			return ret;
		}



		/// <summary>
		/// Unisce una lista di filtri utilizzando l'operatore specificato come secondo parametro
		/// </summary>
		/// <param name="subFilters">Lista di filtri</param>
		/// <param name="operation">operatore da utilizzare per unire i filtri (senza spazi)</param>
		/// <returns>stringa che rappresenta la lista dei filtri</returns>
		protected string JoinFilters(StringCollection subFilters, string operation)
		{
			string strOperation = " " + operation + " ";
			// Unisco i filtri
			if (subFilters.Count > 0)
			{
				StringBuilder cfSb = new StringBuilder();

				cfSb.Append("(");

				foreach (string s in subFilters)
				{
					cfSb.Append(s);
					cfSb.Append(strOperation);
				}

				cfSb.Remove(cfSb.Length - strOperation.Length, strOperation.Length);

				cfSb.Append(")");

				return cfSb.ToString();
			}

			return "";
		}


		protected string CreaFiltroPerCodiceFiscalePartitaIva(string valore, bool cercaInEntrambi, string colonna1, string colonna2)
		{
			StringBuilder sb = new StringBuilder();

			sb.AppendFormat(" ( VW_LISTAPRATICHE.{0} = '{1}'", colonna1, SafeString(valore));

			if (cercaInEntrambi)
				sb.AppendFormat(" OR VW_LISTAPRATICHE.{0} = '{1}'", colonna2, SafeString(valore));

			sb.Append(") ");
			return sb.ToString();
		}


		/// <summary>
		/// Ripulisce una stringa per utilizzarla come valore in una queri (x es sostituisce l'apice con il doppio apice)
		/// </summary>
		/// <param name="inVal">valore da ripulire</param>
		/// <returns>Stringa "ripulita"</returns>
		protected string SafeString(string inVal)
		{
			if (inVal == null) return "";
			return inVal.Trim().Replace("'", "''").Replace("\\","").Replace(";","");
		}
		#endregion


		/// <summary>
		/// Ottiene il dettaglio di una pratica in base ai parametri passati nella richiesta
		/// </summary>
		/// <param name="richiesta">Parametri per individuare una pratica (Vedi <see cref="RichiestaDettaglioPratica"/>)</param>
		/// <returns>Pratica trovata con i criteri di ricerca passati</returns>
		public DettaglioPratiche GetDettaglioPratica(RichiestaDettaglioPratica richiesta)
		{

			DettaglioPratiche retVal = new DettaglioPratiche();



			bool opencnn = false;
			try
			{
				if (m_database.Connection.State == ConnectionState.Closed)
				{
					opencnn = true;
					m_database.Connection.Open();
				}

				retVal.Pratiche = new DettagliPratica();
				retVal.Pratiche.DatiPratica = FillDatiPratica(richiesta.Item);
				retVal.EnteTitolare = FillEnteTitolare(m_idComune, retVal.Pratiche.DatiPratica.CodSportelloBack);

				retVal.Pratiche.Soggetto = FillDatiSoggetto(m_idComune, retVal.Pratiche.DatiPratica.IdPratica);
				retVal.Pratiche.Localizzazione = FillDatiLocalizzazione(m_idComune, retVal.Pratiche.DatiPratica.IdPratica);
				retVal.Pratiche.RifCatastale = FillDatiRifCatastale(m_idComune, retVal.Pratiche.DatiPratica.IdPratica);
				retVal.Pratiche.Movimento = FillDatiMovimenti(m_idComune, retVal.Pratiche.DatiPratica.IdPratica);
				retVal.Pratiche.Procedimento = FillDatiProcedimenti(m_idComune, retVal.Pratiche.DatiPratica.IdPratica);
				retVal.Pratiche.Onere = FillDatiOneri(m_idComune, retVal.Pratiche.DatiPratica.CodSportelloBack, retVal.Pratiche.DatiPratica.IdPratica);
				retVal.Pratiche.Autorizzazioni = FillDatiAutorizzazioni(m_idComune, retVal.Pratiche.DatiPratica.IdPratica);
				//retVal.AltreInfo = FilLDatiAltreInfo();
				//FilLDatiAltreInfo( ds );

				return retVal;
			}
			catch (Exception ex)
			{
				throw;
			}
			finally
			{
				if (opencnn)
					m_database.Connection.Close();
			}
		}




		/// <summary>
		/// Popola i dati relativi alle autorizzazioni per l'istanza passata come parametro
		/// </summary>
		/// <param name="idComune">Codice comune dell'istanza</param>
		/// <param name="idIstanza">Id univoco dell'istanza</param>
		/// <returns>Dati relativi alle autorizzazioni dell'istanza</returns>
		private Autorizzazione[] FillDatiAutorizzazioni(string idComune, string idIstanza)
		{
			string sql = @"SELECT 
							autorizzazioni.autoriznumero AS numero,
							autorizzazioni.autorizdata AS Data,
							tipologiaregistri.tr_descrizione AS Tipologia,
							autorizzazioni.autorizresponsabile AS note
						FROM 
							autorizzazioni,
							tipologiaregistri
						WHERE 
							tipologiaregistri.idcomune = autorizzazioni.idcomune AND 
							tipologiaregistri.tr_id = autorizzazioni.fkidregistro AND 
							autorizzazioni.idcomune = {0} AND 
							autorizzazioni.fkidistanza = {1}";

			sql = String.Format(sql,
								m_database.Specifics.QueryParameterName("Idcomune"),
								m_database.Specifics.QueryParameterName("IdIstanza"));

			ArrayList retVal = new ArrayList();

			int numeroPos = -1, dataPos = -1, tipologiaPos = -1, notePos = -1;

			using (IDbCommand cmd = m_database.CreateCommand(sql))
			{
				SetParameter(cmd, "Idcomune", idComune);
				SetParameter(cmd, "IdIstanza", idIstanza);

				using (IDataReader r = cmd.ExecuteReader())
				{
					while (r.Read())
					{
						if (numeroPos == -1) // Anche gli altri dovrebbero essere a -1
						{
							numeroPos = r.GetOrdinal("numero");
							dataPos = r.GetOrdinal("Data");
							tipologiaPos = r.GetOrdinal("Tipologia");
							notePos = r.GetOrdinal("note");
						}

						Autorizzazione aut = new Autorizzazione();

						if (!r.IsDBNull(numeroPos))
							aut.Numero = r.GetString(numeroPos);

						if (!r.IsDBNull(dataPos))
							aut.DataRilascio = r.GetDateTime(dataPos).ToString("dd/MM/yyyy");

						if (!r.IsDBNull(tipologiaPos))
							aut.Tipologia = r.GetString(tipologiaPos);

						if (!r.IsDBNull(notePos))
							aut.Note = r.GetString(notePos);

						retVal.Add(aut);
					}
				}
			}

			return (Autorizzazione[])retVal.ToArray(typeof(Autorizzazione));
		}




		/// <summary>
		/// Popola i dati relativi all'ente titolare per il comune e software passati
		/// </summary>
		/// <param name="idComune">Codice comune</param>
		/// <param name="software">Software</param>
		/// <returns>Dati relativi all'ente</returns>
		private EnteTitolare FillEnteTitolare(string idComune, string software)
		{
			EnteTitolare et = new EnteTitolare();

			string sqlDenominazione = String.Format("SELECT idcomune,denominazione FROM configurazione where idcomune={0} AND software={1}",
													m_database.Specifics.QueryParameterName("Idcomune"),
													m_database.Specifics.QueryParameterName("software"));

			string sqlSportello = String.Format("SELECT codice, descrizionelunga FROM software where codice={0}",
													m_database.Specifics.QueryParameterName("codice"));

			using (IDbCommand cmd = m_database.CreateCommand(sqlDenominazione))
			{
				SetParameter(cmd, "Idcomune", idComune);
				SetParameter(cmd, "software", software);

				using (IDataReader r = cmd.ExecuteReader())
				{
					et.Ente = new Ente();
					if (r.Read())
					{
						et.Ente.CodEnte = r["idcomune"].ToString();
						et.Ente.DesEnte = r["denominazione"].ToString();
					}
				}
			}

			using (IDbCommand cmd = m_database.CreateCommand(sqlSportello))
			{
				SetParameter(cmd, "codice", software);

				using (IDataReader r = cmd.ExecuteReader())
				{
					et.Sportello = new Sportello();

					if (r.Read())
					{
						et.Sportello.CodSportello = r["codice"].ToString();
						et.Sportello.DesSportello = r["descrizionelunga"].ToString();
					}
				}
			}



			return et;
		}

		private AltreInfo[] FilLDatiAltreInfo()
		{
			throw new NotImplementedException();
		}



		/// <summary>
		/// Popola i dati relativi agli oneri per l'istanza passata come parametro 
		/// </summary>
		/// <param name="idComune">Codice comune dell'istanza</param>
		/// <param name="software">Software per cui è stata presentata l'istanza</param>
		/// <param name="idPratica">Id univoco dell'istanza</param>
		/// <returns>Dati relativi agli oneri</returns>
		private Onere[] FillDatiOneri(string idComune, string software, string idPratica)
		{
			ArrayList retVal = new ArrayList();

			string sql = @"SELECT
							ISTANZEONERI.FKIDTIPOCAUSALE AS CodOnere,
							CO_DESCRIZIONE AS DesOnere,
							PREZZO AS Importo,
							DataScadenza,
							DataPagamento
						FROM 
							ISTANZEONERI,
							TIPICAUSALIONERI
						WHERE 
							TIPICAUSALIONERI.IDCOMUNE = ISTANZEONERI.IDCOMUNE AND
							TIPICAUSALIONERI.CO_ID = ISTANZEONERI.FKIDTIPOCAUSALE AND
							ISTANZEONERI.IDCOMUNE = {0} AND
							TIPICAUSALIONERI.SOFTWARE = {1} AND
							ISTANZEONERI.CODICEISTANZA = {2}";

			sql = String.Format(sql,
								m_database.Specifics.QueryParameterName("IDCOMUNE"),
								m_database.Specifics.QueryParameterName("SOFTWARE"),
								m_database.Specifics.QueryParameterName("CODICEISTANZA"));

			using (IDbCommand cmd = m_database.CreateCommand(sql))
			{
				cmd.CommandText = sql;

				SetParameter(cmd, "IDCOMUNE", idComune);
				SetParameter(cmd, "SOFTWARE", software);
				SetParameter(cmd, "CODICEISTANZA", idPratica);

				using (IDataReader r = cmd.ExecuteReader())
				{
					while (r.Read())
					{
						Onere onere = new Onere();
						onere.CodOnere = r["CodOnere"].ToString();
						onere.DesOnere = r["DesOnere"].ToString();
						onere.Importo = r["Importo"].ToString();
						onere.DataScadenza = r["DataScadenza"].ToString();
						onere.DataPagamento = r["DataPagamento"].ToString();

						retVal.Add(onere);
					}
				}
			}

			return (Onere[])retVal.ToArray(typeof(Onere));
		}


		/// <summary>
		/// Popola i dati relativi ai procedimenti dell'istanza passata come parametro 
		/// </summary>
		/// <param name="idComune">Codice comune dell'istanza</param>
		/// <param name="idPratica">Id univoco dell'istanza</param>
		/// <returns>Dati relativi ai procedimenti</returns>
		private Procedimento[] FillDatiProcedimenti(string idComune, string idPratica)
		{
			ArrayList retVal = new ArrayList();

			string sql = @"SELECT
							INVENTARIOPROCEDIMENTI.CODICEINVENTARIO AS CodProcedimento,
							INVENTARIOPROCEDIMENTI.PROCEDIMENTO AS DesProcedimento,
							AMMINISTRAZIONI.AMMINISTRAZIONE
						FROM 
							ISTANZEPROCEDIMENTI,
							INVENTARIOPROCEDIMENTI,
							AMMINISTRAZIONI
						WHERE 
							INVENTARIOPROCEDIMENTI.IDCOMUNE = ISTANZEPROCEDIMENTI.IDCOMUNE AND
							INVENTARIOPROCEDIMENTI.CODICEINVENTARIO = ISTANZEPROCEDIMENTI.CODICEINVENTARIO AND
							AMMINISTRAZIONI.IDCOMUNE = INVENTARIOPROCEDIMENTI.IDCOMUNE AND
							AMMINISTRAZIONI.CODICEAMMINISTRAZIONE = INVENTARIOPROCEDIMENTI.AMMINISTRAZIONE AND
							ISTANZEPROCEDIMENTI.IDCOMUNE = {0} AND
							ISTANZEPROCEDIMENTI.CODICEISTANZA = {1}";

			sql = String.Format(sql,
								m_database.Specifics.QueryParameterName("IDCOMUNE"),
								m_database.Specifics.QueryParameterName("CODICEISTANZA"));

			using (IDbCommand cmd = m_database.CreateCommand(sql))
			{
				cmd.CommandText = sql;

				SetParameter(cmd, "IDCOMUNE", idComune);
				SetParameter(cmd, "CODICEISTANZA", idPratica);

				using (IDataReader r = cmd.ExecuteReader())
				{
					while (r.Read())
					{
						Procedimento procedimento = new Procedimento();
						procedimento.CodProcedimento = r["CodProcedimento"].ToString();
						procedimento.DesProcedimento = r["DesProcedimento"].ToString();
						procedimento.Amministrazione = r["AMMINISTRAZIONE"].ToString();

						retVal.Add(procedimento);
					}
				}
			}

			return (Procedimento[])retVal.ToArray(typeof(Procedimento));
		}


		/// <summary>
		/// Popola i dati relativi ai movimenti dell'istanza passata come parametro 
		/// </summary>
		/// <param name="idComune">Codice comune dell'istanza</param>
		/// <param name="idPratica">Id univoco dell'istanza</param>
		/// <returns>Dati relativi ai movimenti</returns>
		private Movimento[] FillDatiMovimenti(string idComune, string idPratica)
		{
			ArrayList retVal = new ArrayList();

			string sql = @"SELECT 
								DATA as DataMov,
								MOVIMENTi.Movimento,
								Amministrazione,
								Esito,
								Parere,
								Note,
								PubblicaParere,
								tipologiaesito
							FROM
								MOVIMENTI,
								AMMINISTRAZIONI,
								TIPIMOVIMENTO
							WHERE 
								AMMINISTRAZIONI.IDCOMUNE = MOVIMENTI.IDCOMUNE AND
								AMMINISTRAZIONI.CODICEAMMINISTRAZIONE = MOVIMENTI.CODICEAMMINISTRAZIONE AND  
								TIPIMOVIMENTO.IDCOMUNE = MOVIMENTI.IDCOMUNE and
								TIPIMOVIMENTO.TIPOMOVIMENTO = MOVIMENTI.TIPOMOVIMENTO and
								MOVIMENTI.PUBBLICA <> 0 AND
								MOVIMENTI.IDCOMUNE = {0} AND 
								MOVIMENTI.CODICEISTANZA = {1}
							order by 
								MOVIMENTI.DATA,MOVIMENTI.CODICEMOVIMENTO ";

			sql = String.Format(sql,
								m_database.Specifics.QueryParameterName("IDCOMUNE"),
								m_database.Specifics.QueryParameterName("CODICEISTANZA"));

			using (IDbCommand cmd = (IDbCommand)m_database.CreateCommand(sql))
			{
				cmd.CommandText = sql;
				SetParameter(cmd, "IDCOMUNE", idComune);
				SetParameter(cmd, "CODICEISTANZA", idPratica);

				using (IDataReader r = cmd.ExecuteReader())
				{
					while (r.Read())
					{
						Movimento m = new Movimento();

						m.DataMov = r["DataMov"].ToString();
						m.Movimento1 = r["Movimento"].ToString();
						m.Amministrazione = r["Amministrazione"].ToString();

						if (r["tipologiaesito"].ToString() != "0")
							m.Esito = r["Esito"].ToString();
						else
							m.Esito = "";

						if (r["PubblicaParere"].ToString() != "0")
							m.Parere = r["Parere"].ToString();
						else
							m.Parere = "";

						m.Note = r["Note"].ToString();

						retVal.Add(m);
					}
				}
			}

			return (Movimento[])retVal.ToArray(typeof(Movimento));
		}


		/// <summary>
		/// Popola i dati relativi ai riferimenti catastali per l''istanza passata come parametro 
		/// </summary>
		/// <param name="idComune">Codice comune dell'istanza</param>
		/// <param name="idPratica">Id univoco dell'istanza</param>
		/// <returns>Dati relativi ai riferimenti catastali</returns>
		private RifCatastale[] FillDatiRifCatastale(string idComune, string idPratica)
		{
			ArrayList retVal = new ArrayList();

			string sql = @"select 
								'Catasto Urbano' as TipoCatasto,
								Foglio,
								Particella,
								sub AS Subalterno
							From 
								ISTANZEMAPPALI
							where 
								ISTANZEMAPPALI.IDCOMUNE = {0} and
								ISTANZEMAPPALI.FKCODICEISTANZA = {1}";

			sql = String.Format(sql,
								m_database.Specifics.QueryParameterName("IDCOMUNE"),
								m_database.Specifics.QueryParameterName("FKCODICEISTANZA"));

			using (IDbCommand cmd = m_database.CreateCommand(sql))
			{
				cmd.CommandText = sql;
				SetParameter(cmd, "IdComune", idComune);
				SetParameter(cmd, "FKCODICEISTANZA", idPratica);

				using (IDataReader r = cmd.ExecuteReader())
				{
					while (r.Read())
					{
						RifCatastale rc = new RifCatastale();

						rc.TipoCatasto = r["TipoCatasto"].ToString();
						rc.Foglio = r["Foglio"].ToString();
						rc.Particella = r["Particella"].ToString();
						rc.Subalterno = r["Subalterno"].ToString();

						retVal.Add(rc);
					}
				}
			}

			return (RifCatastale[])retVal.ToArray(typeof(RifCatastale));
		}


		/// <summary>
		/// Popola i dati relativi alla localizzazione (stradario) dell''istanza passata come parametro 
		/// </summary>
		/// <param name="idComune">Codice comune dell'istanza</param>
		/// <param name="idPratica">Id univoco dell'istanza</param>
		/// <returns>Dati relativi alla localizzazione</returns>
		private Localizzazione[] FillDatiLocalizzazione(string idComune, string idPratica)
		{
			ArrayList retVal = new ArrayList();

			string sql = @"select 
							STRADARIO.codicestradario AS CodViario,
							STRADARIO.PREFISSO,
							STRADARIO.DESCRIZIONE,
							null AS CodCivico,
							ISTANZESTRADARIO.CIVICO AS Civico
						From 
							ISTANZESTRADARIO,
							STRADARIO
						where 
							STRADARIO.idComune = ISTANZESTRADARIO.idcomune AND
							STRADARIO.codicestradario = ISTANZESTRADARIO.codicestradario AND
							ISTANZESTRADARIO.idcomune = {0} AND
							ISTANZESTRADARIO.CodiceIstanza = {1}";

			sql = String.Format(sql,
								m_database.Specifics.QueryParameterName("IDCOMUNE"),
								m_database.Specifics.QueryParameterName("IdPratica"));

			using (IDbCommand cmd = (IDbCommand)m_database.CreateCommand(sql))
			{
				cmd.CommandText = sql;
				SetParameter(cmd, "IdComune", idComune);
				SetParameter(cmd, "IdPratica", idPratica);

				using (IDataReader reader = cmd.ExecuteReader())
				{
					while (reader.Read())
					{
						Localizzazione row = new Localizzazione();
						row.CodViario = reader["CodViario"].ToString();
						row.Indirizzo = reader["Prefisso"].ToString() + " " + reader["Descrizione"].ToString();
						row.CodCivico = reader["CodCivico"].ToString();
						row.Civico = reader["Civico"].ToString();

						retVal.Add(row);
					}
				}
			}

			return (Localizzazione[])retVal.ToArray(typeof(Localizzazione));
		}



		/// <summary>
		/// Popola i dati relativi ai soggetti dell''istanza passata come parametro 
		/// </summary>
		/// <param name="idComune">Codice comune dell'istanza</param>
		/// <param name="idPratica">Id univoco dell'istanza</param>
		/// <returns>Dati relativi ai soggetti</returns>
		private Soggetto[] FillDatiSoggetto(string idComune, string idPratica)
		{
			string sql = @"select 
						CodiceRichiedente,
						TipoRapporto,
                        TEC_Codice,
                        AZ_Codice
					From 
						vw_listapratiche
					where 
						IdComune = {0} and
						IdPratica = {1}";

			sql = String.Format(sql,
								m_database.Specifics.QueryParameterName("IDCOMUNE"),
								m_database.Specifics.QueryParameterName("IdPratica"));

			ArrayList soggetti = new ArrayList();
			StringPairCollection soggettiCollection = new StringPairCollection();

			using (IDbCommand cmd = m_database.CreateCommand(sql))
			{
				cmd.CommandText = sql;
				SetParameter(cmd, "IdComune", idComune);
				SetParameter(cmd, "IdPratica", idPratica);

				using (IDataReader r = cmd.ExecuteReader())
				{
					while (r.Read())
					{
						soggettiCollection.Add(r["CodiceRichiedente"].ToString(), r["TipoRapporto"].ToString());
						soggettiCollection.Add(r["AZ_Codice"].ToString(), "Azienda Richiedente");
						soggettiCollection.Add(r["TEC_Codice"].ToString(), "Tecnico");
					}
				}
			}

			sql = @"SELECT 
					istanzerichiedenti.codicerichiedente,
					tipisoggetto.tiposoggetto
				FROM 
					istanzerichiedenti,
					tipisoggetto
				WHERE
					tipisoggetto.idcomune = istanzerichiedenti.idcomune AND
					tipisoggetto.codicetiposoggetto = istanzerichiedenti.codicetiposoggetto AND
					istanzerichiedenti.idcomune = {0} AND 
					istanzerichiedenti.codiceistanza = {1}";

			sql = String.Format(sql,
								m_database.Specifics.QueryParameterName("IdComune"),
								m_database.Specifics.QueryParameterName("codiceistanza"));


			using (IDbCommand cmd = m_database.CreateCommand(sql))
			{
				cmd.CommandText = sql;
				SetParameter(cmd, "IdComune", idComune);
				SetParameter(cmd, "codiceistanza", idPratica);

				using (IDataReader reader = cmd.ExecuteReader())
				{
					while (reader.Read())
					{
						soggettiCollection.Add(reader["codicerichiedente"].ToString(), reader["tiposoggetto"].ToString());
					}
				}
			}

			soggetti.AddRange(AddAnagrafeRows(soggettiCollection));

			return (Soggetto[])soggetti.ToArray(typeof(Soggetto));
		}



		/// <summary>
		/// Metodo di appoggio utilizzato per mappare una classe di tipo <see cref="Anagrafe"/> ad una classe di tipo
		/// <see cref="Soggetto"/> utilizzato nel web service di visura
		/// </summary>
		/// <param name="items">Lista di coppie di stringhe che contengono nel primo elemento il codice anagrafe del soggetto
		/// e nel secondo elemento il tipo rapporto del soggetto</param>
		/// <returns>Lista di soggetti corrispondenti ai valori passati</returns>
		private ArrayList AddAnagrafeRows(StringPairCollection items)
		{
			ArrayList retVal = new ArrayList();

			for (int i = 0; i < items.Count; i++)
			{
				StringPair item = items[i];

				if (StringChecker.IsStringEmpty(item.First)) continue;

				Anagrafe anagrafe = GetAnagrafe(item.First);
				Comuni comune = GetComune(anagrafe.COMUNERESIDENZA);

				Soggetto sogg = new Soggetto();
				sogg.CodFiscale = anagrafe.CODICEFISCALE;
				sogg.PartitaIva = anagrafe.PARTITAIVA;
				sogg.Nominativo = anagrafe.NOMINATIVO + " " + anagrafe.NOME;
				sogg.Indirizzo = anagrafe.INDIRIZZO;
				sogg.Cap = anagrafe.CAP;
				sogg.Localita = anagrafe.CITTA;
				sogg.Citta = comune.COMUNE;
				sogg.Provincia = comune.PROVINCIA;
				sogg.TipoRapporto = item.Second;

				retVal.Add(sogg);
			}

			return retVal;
		}


		/// <summary>
		/// Legge una classe di tipo <see cref="Anagrafe"/> utilizzando le DataClass di Sigepro
		/// </summary>
		/// <param name="idAnagrafe">Codice dell'anagrafica</param>
		/// <returns>Classe <see cref="Anagrafe"/> popolata con i dati dell'anagrafe con l'id passata</returns>
		private Anagrafe GetAnagrafe(string idAnagrafe)
		{
			Anagrafe anagrafe = new Anagrafe();
			anagrafe.IDCOMUNE = m_idComune;
			anagrafe.CODICEANAGRAFE = idAnagrafe;

			return (Anagrafe)m_database.GetClass(anagrafe);
		}


		/// <summary>
		/// Legge una classe di tipo <see cref="Comuni"/> utilizzando le DataClass di Sigepro
		/// </summary>
		/// <param name="cf">Codice belfiore del comune da cercare</param>
		/// <returns>Classe <see cref="Comuni"/> popolata con i dati del comune corrispondente al codice belfiore passato</returns>
		private Comuni GetComune(string cf)
		{
			Comuni comune = new Comuni();

			if (StringChecker.IsStringEmpty(cf))
				return comune;

			comune.CF = cf;
			return (Comuni)m_database.GetClass(comune);
		}




		/// <summary>
		/// Popola i dati relativi ad una pratica secondo i criteri di ricerca passati come parametro 
		/// </summary>
		/// <param name="request">Criteri di ricerca dell'istanza. Può essere solo del tipo <see cref="RichiestaPerCodiceSportelloENumeroPratica"/>,
		/// <see cref="RichiestaPerNumeroProtocollo"/> e <see cref="RichiestaPerIdPratica"/></param>
		/// <returns>Dati relativi alla pratica cercata</returns>
		private DatiPratica FillDatiPratica(object request)
		{
			DatiPratica retVal = new DatiPratica();
			string idComune = null;

			using (IDbCommand cmd = m_database.CreateCommand())
			{

				cmd.CommandText = @"select 
									IdPratica,
									software AS CodSportelloBack,
									descsoftware AS DesSportelloBack,
									NumeroPratica,
									DataPresentazione,
									NumeroProtocollo,
									DataProtocollo,
									DescrizioneIntervento,
									Oggetto,
									CodStatoPratica,
									StatoPratica,
									responsabile AS ResponsabileProcedimento,
									responsabile_telefono as ResponsabileProc_telefono,
									istruttore,
									istruttore_telefono,
									operatore,
									operatore_telefono
								From 
									vw_listapratiche ";

				#region Identificazione del tipo di richiesta
				Type itemType = request.GetType();

				if (itemType == typeof(RichiestaPerCodiceSportelloENumeroPratica))
				{

					RichiestaPerCodiceSportelloENumeroPratica filter = (RichiestaPerCodiceSportelloENumeroPratica)request;

					idComune = filter.CodEnte;

					cmd.CommandText += String.Format(" where IdComune = {0} and software = {1} and numeroPratica = {2}",
														m_database.Specifics.QueryParameterName("IdComune"),
														m_database.Specifics.QueryParameterName("Software"),
														m_database.Specifics.QueryParameterName("NumeroPratica"));

					SetParameter(cmd, "IdComune", idComune);
					SetParameter(cmd, "Software", filter.CodSportello);
					SetParameter(cmd, "NumeroPratica", filter.NumeroPratica);

				}
				else if (itemType == typeof(RichiestaPerNumeroProtocollo))
				{

					RichiestaPerNumeroProtocollo filter = (RichiestaPerNumeroProtocollo)request;

					idComune = filter.CodEnte;

					cmd.CommandText += String.Format(" where IdComune = {0} and NumeroProtocollo = {1} ",
														m_database.Specifics.QueryParameterName("IdComune"),
														m_database.Specifics.QueryParameterName("NumeroProtocollo"));

					SetParameter(cmd, "IdComune", idComune);
					SetParameter(cmd, "NumeroProtocollo", filter.NumeroProtocollo);

				}
				else if (itemType == typeof(RichiestaPerIdPratica))
				{

					RichiestaPerIdPratica filter = (RichiestaPerIdPratica)request;

					idComune = filter.CodEnte;

					cmd.CommandText += String.Format(" where IdComune = {0} and IdPratica = {1} ",
														m_database.Specifics.QueryParameterName("IDCOMUNE"),
														m_database.Specifics.QueryParameterName("IdPratica"));

					SetParameter(cmd, "IdComune", idComune);
					SetParameter(cmd, "IdPratica", filter.IdPratica);

				}
				else
				{
					throw new Exception("Tipo di richiesta " + request.GetType().Name.ToString() + " non supportato");
				}
				#endregion

				using (IDataReader r = cmd.ExecuteReader())
				{
					while (r.Read())
					{
						retVal.IdPratica = r["IdPratica"].ToString();
						retVal.CodSportelloBack = r["CodSportelloBack"].ToString();
						retVal.DesSportelloBack = r["DesSportelloBack"].ToString();
						retVal.CodStatoPratica = r["CodStatoPratica"].ToString();
						retVal.DataPresentazione = r["DataPresentazione"].ToString();
						retVal.DataProtocollo = r["DataProtocollo"].ToString();
						retVal.DescrizioneIntervento = r["DescrizioneIntervento"].ToString();
						retVal.NumeroPratica = r["NumeroPratica"].ToString();
						retVal.NumeroProtocollo = r["NumeroProtocollo"].ToString();
						retVal.Oggetto = r["Oggetto"].ToString();
						retVal.ResponsabileProcedimento = r["ResponsabileProcedimento"].ToString();

						/*
						 if (r["ResponsabileProc_telefono"] != null && r["ResponsabileProc_telefono"] != DBNull.Value)
							retVal.ResponsabileProcedimento += " - Tel." + r["ResponsabileProc_telefono"].ToString();
						*/
						if (r["Istruttore"] != null && r["Istruttore"] != DBNull.Value)
						{
							retVal.Istruttore = r["Istruttore"].ToString();
							/*
							if (r["Istruttore_telefono"] != null && r["Istruttore_telefono"] != DBNull.Value)
								retVal.Istruttore += " - Tel." + r["Istruttore_telefono"].ToString();
							*/
						}

						if (r["Operatore"] != null && r["Operatore"] != DBNull.Value)
						{
							retVal.Operatore = r["Operatore"].ToString();

							/*if (r["Operatore_telefono"] != null && r["Operatore_telefono"] != DBNull.Value)
								retVal.Operatore += " - Tel." + r["Operatore_telefono"].ToString();
							*/
						}

						retVal.StatoPratica = r["StatoPratica"].ToString();
					}
				}

				return retVal;

			}


		}

		private void SetParameter(IDbCommand cmd, string name, object value)
		{
			IDbDataParameter par = cmd.CreateParameter();
			par.ParameterName = m_database.Specifics.ParameterName(name);
			par.Value = value;
			cmd.Parameters.Add(par);
		}
	}
}
