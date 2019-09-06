using System;
using System.Collections.Generic;
using System.Text;
using PersonalLib2.Data;
using System.Data;
using Init.SIGePro.Data;
using System.IO;
using System.Xml.Serialization;
using log4net;

namespace Init.SIGePro.Manager.Logic.DatiDinamici.Statistiche
{
	public partial class StatisticheIstanzeMgr
	{
		ILog _log = LogManager.GetLogger(typeof(StatisticheIstanzeMgr));

		StatisticheDatiDinamiciQueryGenerator m_generatoreQueryDatiDinamici = null;
		string m_idComune, m_software;
		DataBase m_database;

		public StatisticheIstanzeMgr(string idComune, string software, DataBase database)
		{
			m_generatoreQueryDatiDinamici = new StatisticheDatiDinamiciQueryGenerator(idComune, database, "istanzedyn2dati", "VW_STATISTICHEISTANZE.CODICEISTANZA", "CODICEISTANZA");
			m_idComune = idComune;
			m_database = database;
			m_software = software;
		}

		public List<Istanze> GeneraReport(ParametriStatisticaIstanze filtri)
		{
			/*
			 * ISTANZE.DATA between :dalladata and :alladata
			 * codiceResponsabile like :responsabile			 
			 * codicerichiedente like :richiedente
			 * codiceprofessionista like :tecnico
			 * MetriQuadrati between :metriquadratida and :metriquadratia
			 * ISTANZE.CODICEINTERVENTO = :codiceintervento
			 * ISTANZE.CODICEPROCEDURA= :codiceprocedura
			 * 
			 * -> Filtro tipoInformazione e dettaglioinformazione 
			 * 	Metto in join anche le tabelle ISTANZEATTIVITA,ATTIVITA,SETTORI
			 *  (la condizione è )
			 *		ISTANZEATTIVITA.IDCOMUNE=ISTANZE.IDCOMUNE and 
			 *		ISTANZEATTIVITA.CODICEISTANZA=ISTANZE.CODICEISTANZA and 
			 *		ATTIVITA.IDCOMUNE=ISTANZEATTIVITA.IDCOMUNE and 
			 *		ATTIVITA.CODICEISTAT=ISTANZEATTIVITA.CODICEATTIVITA and 
			 *		SETTORI.IDCOMUNE=ATTIVITA.IDCOMUNE and 
			 *		SETTORI.CODICESETTORE=ATTIVITA.CODICESETTORE
			 *  e filtro per 
			 *		SETTORI.CodiceSettore like :dettaglioinformazione and
			 *		ISTANZEATTIVITA.CodiceAttivita like :tipoInformazione
			 * 
			 * 
			 * -> filtro Zonizzazione 
			 * Metto in join la tabella ISTANZEAREE
			 * (la condizione è)
			 *		ISTANZEAREE.IDCOMUNE = ISTANZE.IDCOMUNE AND 
			 *		ISTANZEAREE.CODICEISTANZA = ISTANZE.CODICEISTANZA
			 * e filtro per
			 *		STANZEAREE.CODICEAREA like :Zonizazione
			 * 
			 * 
			 * -> filtro Registro
			 * Metto in join la tabella AUTORIZZAZIONI
			 * (la condizione è)
			 *		AUTORIZZAZIONI.IDCOMUNE = ISTANZE.IDCOMUNE AND 
			 *		AUTORIZZAZIONI.FKIDISTANZA = ISTANZE.CODICEISTANZA
			 * e filtro per 
			 *		AUTORIZZAZIONI.FKIDREGISTRO like :Registro
			 * 
			 * 
			 * -> filtro CodiceStato
			 * se il filtro è "APERTE" oppure "CHIUSE"
			 *		metto in join la tabella STATIISTANZA
			 *		(la condizione è)
			 *			STATIISTANZA.IDCOMUNE=ISTANZE.IDCOMUNE and 
			 *			STATIISTANZA.SOFTWARE=ISTANZE.SOFTWARE and 
			 *			STATIISTANZA.CODICESTATO=ISTANZE.CHIUSURA
			 *		e filtro per 
			 *			STATIISTANZA.FKCODCOMPORTAMENTO = 0 ( se "APERTE" ) oppure
			 *			STATIISTANZA.FKCODCOMPORTAMENTO <> 0 ( se "CHIUSE" ) 
			 *	altrimenti non metto in join altre tabelle e filtro per
			 *			ISTANZE.CHIUSURA like :CodiceStato
			 * 
			 * 
			 * // TODO: Gestire il conteggio
			 */

			List<IDbDataParameter> listaParameteri = new List<IDbDataParameter>();

            string select = "select distinct VW_STATISTICHEISTANZE.* ";
			string from = "FROM VW_STATISTICHEISTANZE ";
			string where = "WHERE 1=1 ";
			string whereFiltro = " AND f_idcomune= " + m_database.Specifics.QueryParameterName("IdComune") +
									" AND f_software = " + m_database.Specifics.QueryParameterName("Software");

			listaParameteri.Add(m_database.CreateParameter("IdComune", m_idComune));
			listaParameteri.Add(m_database.CreateParameter("Software", m_software));



			// data
			whereFiltro += " and f_data between " + m_database.Specifics.QueryParameterName("DallaData") + " AND " + m_database.Specifics.QueryParameterName("AllaData");
			listaParameteri.Add(m_database.CreateParameter("DallaData", filtri.Data.Inizio.GetValueOrDefault(new DateTime(1900, 01, 01))));
			listaParameteri.Add(m_database.CreateParameter("AllaData", filtri.Data.Fine.GetValueOrDefault(new DateTime(2099, 12, 31))));



			// responsabile
			if (filtri.Operatore.HasValue)
			{
				whereFiltro += " and f_responsabile = " + m_database.Specifics.QueryParameterName("CodiceResponsabile");
				listaParameteri.Add(m_database.CreateParameter("CodiceResponsabile", filtri.Operatore.Value));
			}


			// codicerichiedente
			if (filtri.Richiedente.HasValue)
			{
				whereFiltro += @" and ( ( vw_statisticheistanze.codiceistanza in 
										  (
											SELECT 
												ir.CodiceIstanza 
											FROM 
												IstanzeRichiedenti ir
											WHERE 
												ir.idcomune=" + m_database.Specifics.QueryParameterName("IdComuneRichiedenti") +
											" and ir.codiceRichiedente=" + m_database.Specifics.QueryParameterName("CodiceRichiedente") + @"
										  ) 
										) or vw_statisticheistanze.codicerichiedente =" + m_database.Specifics.QueryParameterName("CodiceRichiedenteIstanza") +
										" or vw_statisticheistanze.codicetitolarelegale =" + m_database.Specifics.QueryParameterName("CodiceRichiedenteTL") +
									" )";
				listaParameteri.Add(m_database.CreateParameter("IdComuneRichiedenti", m_idComune));
				listaParameteri.Add(m_database.CreateParameter("CodiceRichiedente", filtri.Richiedente.Value));
				listaParameteri.Add(m_database.CreateParameter("CodiceRichiedenteIstanza", filtri.Richiedente.Value));
				listaParameteri.Add(m_database.CreateParameter("CodiceRichiedenteTL", filtri.Richiedente.Value));
			}



			// codice professionista
			if (filtri.Tecnico.HasValue)
			{
				whereFiltro += " AND f_professionista = " + m_database.Specifics.QueryParameterName("Tecnico");
				listaParameteri.Add(m_database.CreateParameter("Tecnico", filtri.Tecnico));
			}

			/*
			// Metri quadrati
			whereFiltro += " AND f_metriquadrati BETWEEN " + m_database.Specifics.QueryParameterName("MetriQuadriDa") + " AND " + m_database.Specifics.QueryParameterName("MetriQuadriA");
			listaParameteri.Add( m_database.CreateParameter( "MetriQuadriDa" , filtri.MetriQuadri.Inizio.GetValueOrDefault( 0 ) ) );
			listaParameteri.Add( m_database.CreateParameter( "MetriQuadriA" , filtri.MetriQuadri.Fine.GetValueOrDefault( 9999 )) );
			*/


			// CodiceIntervento -> Questa è una stringa nella query
			if (filtri.TipologiaIntervento.HasValue)
				whereFiltro += " and f_codiceintervento in (" + GetListaInterventi(filtri.TipologiaIntervento.Value) + ")";



			// CodiceProcedura
			if (filtri.TipoProcedura.HasValue)
			{
				whereFiltro += " and f_procedura = " + m_database.Specifics.QueryParameterName("CodiceProcedura");
				listaParameteri.Add(m_database.CreateParameter("CodiceProcedura", filtri.TipoProcedura));
			}



			// Tipo informazione / Dettaglio informazione
			// Le tabelle coinvolte sono già presenti nella join

			if (!String.IsNullOrEmpty(filtri.DettaglioInformazione))
			{
				whereFiltro += " and f_dettaglioinformazioni = " + m_database.Specifics.QueryParameterName("DettaglioInformazione");
				listaParameteri.Add(m_database.CreateParameter("DettaglioInformazione", filtri.DettaglioInformazione));
			}

			if (!String.IsNullOrEmpty(filtri.TipoInformazione))
			{
				whereFiltro += " and f_tipoinformazioni = " + m_database.Specifics.QueryParameterName("TipoInformazione");
				listaParameteri.Add(m_database.CreateParameter("TipoInformazione", filtri.TipoInformazione));
			}



			// Zonizzazione
			if (filtri.Zonizzazione.HasValue)
			{
				whereFiltro += " and f_zonizzazione = " + m_database.Specifics.QueryParameterName("Zonizzazione");
				listaParameteri.Add(m_database.CreateParameter("Zonizzazione", filtri.Zonizzazione));
			}


			// Registro
			if (filtri.Registro.HasValue)
			{
				whereFiltro += " and f_registro like " + m_database.Specifics.QueryParameterName("Registro");
				listaParameteri.Add(m_database.CreateParameter("Registro", filtri.Registro));
			}


			// Stato istanza
			if (filtri.CodiceStato == "APERTE" || filtri.CodiceStato == "CHIUSE")
			{
				from += ", statiistanza";
				where += @" AND statiistanza.idcomune	= vw_statisticheistanza.idcomune AND 
							statiistanza.software		= vw_statisticheistanza.software AND 
							statiistanza.codicestato	= vw_statisticheistanza.chiusura";
				whereFiltro += filtri.CodiceStato == "APERTE" ? " AND statiistanza.fkcodcomportamento = 0" : " AND statiistanza.fkcodcomportamento <> 0";
			}
			else
			{
				whereFiltro += " and f_stato like " + m_database.Specifics.QueryParameterName("CodiceStato");
				listaParameteri.Add(m_database.CreateParameter("CodiceStato", filtri.CodiceStato));
			}

			// Filtri dati dinamici
			QueryStatisticheDatiDinamici queryDd = m_generatoreQueryDatiDinamici.CreaQuery(filtri.FiltriDatiDinamici);

			string whereDatiDinamici = queryDd.CommandText;

			if (whereDatiDinamici != " () ")
			{

				for (int i = 0; i < queryDd.Parameters.Count; i++)
				{
					string stringToMatch = "{" + i + "}";
					string replacement = m_database.Specifics.QueryParameterName(queryDd.Parameters[i].Key);

					whereDatiDinamici = whereDatiDinamici.Replace(stringToMatch, replacement);

					listaParameteri.Add(m_database.CreateParameter(queryDd.Parameters[i].Key, queryDd.Parameters[i].Value));
				}

				whereFiltro += " AND " + whereDatiDinamici;
			}

			string sql = select + " " + from + " " + where + " " + whereFiltro;

			List<Istanze> istanze = PerformQuery(sql, listaParameteri);

			//using (FileStream fs = File.Open(@"c:\temp\istanzaSerializzata.xml", FileMode.Create))
			//{
			//    XmlSerializer xs = new XmlSerializer(istanze.GetType());
			//    xs.Serialize(fs, istanze);
			//}

			return istanze;
			//
			// = m_database.GetClassList(
			//QueryStatisticheDatiDinamici queryDatiDinamici = m_generatoreQueryDatiDinamici.CreaQuery(filtri.FiltriDatiDinamici);
		}

		private List<Istanze> PerformQuery(string sql, List<IDbDataParameter> listaParameteri)
		{
			bool closeCnn = false;

			try
			{
				if (m_database.Connection.State == ConnectionState.Closed)
				{
					m_database.Connection.Open();
					closeCnn = true;
				}

				_log.DebugFormat("Query statistiche istanze: {0}", sql);

				using (IDbCommand cmd = m_database.CreateCommand(sql))
				{
					for (int i = 0; i < listaParameteri.Count; i++)
						cmd.Parameters.Add(listaParameteri[i]);

					Istanze dataClass = new Istanze();
					dataClass.UseForeign = PersonalLib2.Sql.useForeignEnum.Recoursive;

					return m_database.GetClassList(cmd, dataClass, false, true).ToList<Istanze>();
				}

			}
			catch (Exception ex)
			{
				throw;
			}
			finally
			{
				if (closeCnn)
					m_database.Connection.Close();
			}

		}

		private string GetListaInterventi(int codiceIntervento)
		{
			StringBuilder sb = new StringBuilder();
			sb.Append(codiceIntervento);

			AlberoProc nodoRoot = new AlberoProcMgr(m_database).GetById(codiceIntervento, m_idComune);

			AccodaSottonodi(nodoRoot, sb);

			return sb.ToString();
		}

		private void AccodaSottonodi(AlberoProc nodoRoot, StringBuilder sb)
		{
			//nodoRoot.SC_CODICE

			string sql = "select SC_ID from alberoProc where IDCOMUNE = {0} AND SOFTWARE = {1} AND SC_CODICE like {2}";
			sql = String.Format(sql, m_database.Specifics.QueryParameterName("IdComune"),
										m_database.Specifics.QueryParameterName("Software"),
										m_database.Specifics.QueryParameterName("ScCodice"));

			bool closecnn = false;

			if (m_database.Connection.State == ConnectionState.Closed)
			{
				m_database.Connection.Open();
				closecnn = true;
			}
			try
			{
				using (IDbCommand cmd = m_database.CreateCommand(sql))
				{
					cmd.Parameters.Add(m_database.CreateParameter("IdComune", nodoRoot.Idcomune));
					cmd.Parameters.Add(m_database.CreateParameter("Software", nodoRoot.SOFTWARE));
					cmd.Parameters.Add(m_database.CreateParameter("ScCodice", nodoRoot.SC_CODICE + "%"));

					using (IDataReader dr = cmd.ExecuteReader())
					{
						while (dr.Read())
						{
							sb.Append(",");
							sb.Append(dr[0]);
						}
					}
				}
			}
			finally
			{
				if (closecnn)
					m_database.Connection.Close();
			}
		}
	}
}
