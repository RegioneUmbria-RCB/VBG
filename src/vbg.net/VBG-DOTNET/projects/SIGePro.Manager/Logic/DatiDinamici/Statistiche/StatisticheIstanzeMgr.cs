using Init.SIGePro.Data;
using log4net;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Data;
using System.Text;

namespace Init.SIGePro.Manager.Logic.DatiDinamici.Statistiche
{
    public partial class StatisticheIstanzeMgr
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(StatisticheIstanzeMgr));

        private readonly StatisticheDatiDinamiciQueryGenerator m_generatoreQueryDatiDinamici = null;
        private readonly string m_idComune, m_software;
        private readonly DataBase m_database;

        public StatisticheIstanzeMgr(string idComune, string software, DataBase database)
        {
            this.m_generatoreQueryDatiDinamici = new StatisticheDatiDinamiciQueryGenerator(idComune, database, "istanzedyn2dati", "VW_STATISTICHEISTANZE.CODICEISTANZA", "CODICEISTANZA");
            this.m_idComune = idComune;
            this.m_database = database;
            this.m_software = software;
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
            string whereFiltro = " AND f_idcomune= " + this.m_database.Specifics.QueryParameterName("IdComune") +
                                    " AND f_software = " + this.m_database.Specifics.QueryParameterName("Software");

            listaParameteri.Add(this.m_database.CreateParameter("IdComune", this.m_idComune));
            listaParameteri.Add(this.m_database.CreateParameter("Software", this.m_software));



            // data
            whereFiltro += " and f_data between " + this.m_database.Specifics.QueryParameterName("DallaData") + " AND " + this.m_database.Specifics.QueryParameterName("AllaData");
            listaParameteri.Add(this.m_database.CreateParameter("DallaData", filtri.Data.Inizio.GetValueOrDefault(new DateTime(1900, 01, 01))));
            listaParameteri.Add(this.m_database.CreateParameter("AllaData", filtri.Data.Fine.GetValueOrDefault(new DateTime(2099, 12, 31))));



            // responsabile
            if (filtri.Operatore.HasValue)
            {
                whereFiltro += " and f_responsabile = " + this.m_database.Specifics.QueryParameterName("CodiceResponsabile");
                listaParameteri.Add(this.m_database.CreateParameter("CodiceResponsabile", filtri.Operatore.Value));
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
												ir.idcomune=" + this.m_database.Specifics.QueryParameterName("IdComuneRichiedenti") +
                                            " and ir.codiceRichiedente=" + this.m_database.Specifics.QueryParameterName("CodiceRichiedente") + @"
										  ) 
										) or vw_statisticheistanze.codicerichiedente =" + this.m_database.Specifics.QueryParameterName("CodiceRichiedenteIstanza") +
                                        " or vw_statisticheistanze.codicetitolarelegale =" + this.m_database.Specifics.QueryParameterName("CodiceRichiedenteTL") +
                                    " )";
                listaParameteri.Add(this.m_database.CreateParameter("IdComuneRichiedenti", this.m_idComune));
                listaParameteri.Add(this.m_database.CreateParameter("CodiceRichiedente", filtri.Richiedente.Value));
                listaParameteri.Add(this.m_database.CreateParameter("CodiceRichiedenteIstanza", filtri.Richiedente.Value));
                listaParameteri.Add(this.m_database.CreateParameter("CodiceRichiedenteTL", filtri.Richiedente.Value));
            }



            // codice professionista
            if (filtri.Tecnico.HasValue)
            {
                whereFiltro += " AND f_professionista = " + this.m_database.Specifics.QueryParameterName("Tecnico");
                listaParameteri.Add(this.m_database.CreateParameter("Tecnico", filtri.Tecnico));
            }

            /*
			// Metri quadrati
			whereFiltro += " AND f_metriquadrati BETWEEN " + m_database.Specifics.QueryParameterName("MetriQuadriDa") + " AND " + m_database.Specifics.QueryParameterName("MetriQuadriA");
			listaParameteri.Add( m_database.CreateParameter( "MetriQuadriDa" , filtri.MetriQuadri.Inizio.GetValueOrDefault( 0 ) ) );
			listaParameteri.Add( m_database.CreateParameter( "MetriQuadriA" , filtri.MetriQuadri.Fine.GetValueOrDefault( 9999 )) );
			*/


            // CodiceIntervento -> Questa è una stringa nella query
            if (filtri.TipologiaIntervento.HasValue)
                whereFiltro += " and f_codiceintervento in (" + this.GetListaInterventi(filtri.TipologiaIntervento.Value) + ")";



            // CodiceProcedura
            if (filtri.TipoProcedura.HasValue)
            {
                whereFiltro += " and f_procedura = " + this.m_database.Specifics.QueryParameterName("CodiceProcedura");
                listaParameteri.Add(this.m_database.CreateParameter("CodiceProcedura", filtri.TipoProcedura));
            }



            // Tipo informazione / Dettaglio informazione
            // Le tabelle coinvolte sono già presenti nella join

            if (!String.IsNullOrEmpty(filtri.DettaglioInformazione))
            {
                whereFiltro += " and f_dettaglioinformazioni = " + this.m_database.Specifics.QueryParameterName("DettaglioInformazione");
                listaParameteri.Add(this.m_database.CreateParameter("DettaglioInformazione", filtri.DettaglioInformazione));
            }

            if (!String.IsNullOrEmpty(filtri.TipoInformazione))
            {
                whereFiltro += " and f_tipoinformazioni = " + this.m_database.Specifics.QueryParameterName("TipoInformazione");
                listaParameteri.Add(this.m_database.CreateParameter("TipoInformazione", filtri.TipoInformazione));
            }



            // Zonizzazione
            if (filtri.Zonizzazione.HasValue)
            {
                whereFiltro += " and f_zonizzazione = " + this.m_database.Specifics.QueryParameterName("Zonizzazione");
                listaParameteri.Add(this.m_database.CreateParameter("Zonizzazione", filtri.Zonizzazione));
            }


            // Registro
            if (filtri.Registro.HasValue)
            {
                whereFiltro += " and f_registro like " + this.m_database.Specifics.QueryParameterName("Registro");
                listaParameteri.Add(this.m_database.CreateParameter("Registro", filtri.Registro));
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
                whereFiltro += " and f_stato like " + this.m_database.Specifics.QueryParameterName("CodiceStato");
                listaParameteri.Add(this.m_database.CreateParameter("CodiceStato", filtri.CodiceStato));
            }

            // Filtri dati dinamici
            QueryStatisticheDatiDinamici queryDd = this.m_generatoreQueryDatiDinamici.CreaQuery(filtri.FiltriDatiDinamici);

            string whereDatiDinamici = queryDd.CommandText;

            if (whereDatiDinamici != " () ")
            {

                for (int i = 0; i < queryDd.Parameters.Count; i++)
                {
                    string stringToMatch = "{" + i + "}";
                    string replacement = this.m_database.Specifics.QueryParameterName(queryDd.Parameters[i].Key);

                    whereDatiDinamici = whereDatiDinamici.Replace(stringToMatch, replacement);

                    listaParameteri.Add(this.m_database.CreateParameter(queryDd.Parameters[i].Key, queryDd.Parameters[i].Value));
                }

                whereFiltro += " AND " + whereDatiDinamici;
            }

            string sql = select + " " + from + " " + where + " " + whereFiltro;

            List<Istanze> istanze = this.PerformQuery(sql, listaParameteri);

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
                if (this.m_database.Connection.State == ConnectionState.Closed)
                {
                    this.m_database.Connection.Open();
                    closeCnn = true;
                }

                this._log.DebugFormat("Query statistiche istanze: {0}", sql);

                using (IDbCommand cmd = this.m_database.CreateCommand(sql))
                {
                    for (int i = 0; i < listaParameteri.Count; i++)
                        cmd.Parameters.Add(listaParameteri[i]);

                    Istanze dataClass = new Istanze();
                    dataClass.UseForeign = PersonalLib2.Sql.useForeignEnum.Recoursive;

                    return this.m_database.GetClassList<Istanze>(cmd, new GetClassListFlags
                    {
                        UseForeign = PersonalLib2.Sql.useForeignEnum.Recoursive,
                        SingleRowException = false
                    });
                }

            }
            catch (Exception ex)
            {
                throw;
            }
            finally
            {
                if (closeCnn)
                    this.m_database.Connection.Close();
            }

        }

        private string GetListaInterventi(int codiceIntervento)
        {
            StringBuilder sb = new StringBuilder();
            sb.Append(codiceIntervento);

            AlberoProc nodoRoot = new AlberoProcMgr(this.m_database).GetById(codiceIntervento, this.m_idComune);

            this.AccodaSottonodi(nodoRoot, sb);

            return sb.ToString();
        }

        private void AccodaSottonodi(AlberoProc nodoRoot, StringBuilder sb)
        {
            //nodoRoot.SC_CODICE

            string sql = "select SC_ID from alberoProc where IDCOMUNE = {0} AND SOFTWARE = {1} AND SC_CODICE like {2}";
            sql = String.Format(sql, this.m_database.Specifics.QueryParameterName("IdComune"),
                                        this.m_database.Specifics.QueryParameterName("Software"),
                                        this.m_database.Specifics.QueryParameterName("ScCodice"));

            bool closecnn = false;

            if (this.m_database.Connection.State == ConnectionState.Closed)
            {
                this.m_database.Connection.Open();
                closecnn = true;
            }
            try
            {
                using (IDbCommand cmd = this.m_database.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this.m_database.CreateParameter("IdComune", nodoRoot.Idcomune));
                    cmd.Parameters.Add(this.m_database.CreateParameter("Software", nodoRoot.SOFTWARE));
                    cmd.Parameters.Add(this.m_database.CreateParameter("ScCodice", nodoRoot.SC_CODICE + "%"));

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
                    this.m_database.Connection.Close();
            }
        }
    }
}
