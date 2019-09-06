namespace Init.SIGePro.Manager
{
	using System;
	using System.Collections;
	using System.Collections.Generic;
	using System.Configuration;
	using System.Data;
	using System.Linq;
	using System.Text;
	using System.Text.RegularExpressions;
	using Init.SIGePro.Data;
	using Init.SIGePro.Manager.DTO;
	using Init.SIGePro.Manager.DTO.DatiDinamici;
	using Init.SIGePro.Manager.DTO.Endoprocedimenti;
	using Init.SIGePro.Manager.DTO.Interventi;
	using Init.SIGePro.Manager.DTO.Normative;
	using Init.SIGePro.Manager.DTO.Oneri;
	using Init.SIGePro.Manager.DTO.Procedure;
	using Init.SIGePro.Manager.Utils.Extensions;
	using Init.SIGePro.Verticalizzazioni;
	using Init.Utils;
	using log4net;
	using PersonalLib2.Data;

    public enum LivelloAutenticazioneBOEnum
    {
        Anonimo = 0,
        NonIdentificato = 1,
        Identificato = 2
    }

	public enum AmbitoRicerca
	{
		AreaRiservata,
		FrontofficePubblico,
		Backoffice,
		UtenteTesterAreaRiservata
	}

	public enum TipoProcedimentoCart
	{
		ENDO,
		ATTIVITA,
		CATEGORIA
	}

	public class FiltroRicercaFlagPubblica
	{
		public static string Get(AmbitoRicerca ambito)
		{
			switch (ambito)
			{
				case AmbitoRicerca.AreaRiservata:
					return "1,2";
				case AmbitoRicerca.FrontofficePubblico:
					return "1,3";
			}

			return "0,1,2,3";
		}
	}

	public partial class AlberoProcMgr
	{
		private ILog _log = LogManager.GetLogger(typeof(AlberoProcMgr));

        public AlberoProc GetById(int idIntervento, string idComune)
        {
            var filtro = new AlberoProc
            {
                Sc_id = idIntervento,
                Idcomune = idComune
            };

            return (AlberoProc)db.GetClass(filtro);
        }

        public string GetDescrizioneCompletaDaIdIntervento(int idIntervento, string idComune, string software)
        {
            

            var albero = this.GetById(idIntervento, idComune);

            if (albero == null)
                return "";

            var descrizione = Enumerable.Range(0, (albero.SC_CODICE.Length - 2) / 2)
                             .Select(x => 
                             {
                                 var codice = albero.SC_CODICE.Substring(0, (x + 1) * 2);
                                 var ramo = this.GetByScCodice(idComune, software, codice);
                                 return ramo.SC_DESCRIZIONE;
                             });

            return String.Join(" - ", descrizione);
        }

        public AlberoProc GetById(int idIntervento, string idComune, string codiceComune)
        {
            var filtro = new AlberoProc
            {
                Sc_id = idIntervento,
                Idcomune = idComune
            };

            var res = (AlberoProc)db.GetClass(filtro);

            ValorizzaDatiProtocollo(res, codiceComune);

            return res;

        }

        private void ValorizzaDatiProtocollo(AlberoProc albero, string codiceComune)
        {
            var mgr = new AlberoProcProtocolloMgr(db);
            var alberoProto = mgr.GetByCodiceComune(albero.Idcomune, Convert.ToInt32(albero.Sc_id), codiceComune);

            if (alberoProto != null)
            {
                albero.FascicolazioneAutomatica = alberoProto.ScFascautomatica.HasValue ? alberoProto.ScFascautomatica.Value.ToString() : "";
                albero.ClassificaFascicolazione = alberoProto.ScFascclassifica;
                albero.TestoTipoFascicolazione = alberoProto.ScFasccodtesto.HasValue ? alberoProto.ScFasccodtesto.Value.ToString() : "";
                albero.ProtocollazioneAutomatica = alberoProto.ScProtautomatica.HasValue ? alberoProto.ScProtautomatica.Value.ToString() : "";
                albero.ClassificaProtocollazione = alberoProto.ScProtclassifica;
                albero.TestoTipoProtocollazione = alberoProto.ScProtcodtesto.HasValue ? alberoProto.ScProtcodtesto.Value.ToString() : "";
                albero.TipoDocumentoProtocollazione = alberoProto.ScProttipodocumento;
                albero.CodiceAmministrazione = alberoProto.Codiceamministrazione.HasValue ? alberoProto.Codiceamministrazione.Value : (int?)null;
            }
        }

		public AlberoProc GetByScCodice(string idComune, string software, string scCodice)
		{
			return GetByClass(new AlberoProc 
			{ 
				Idcomune = idComune,
				SOFTWARE = software,
				SC_CODICE = scCodice
			});
		}

        /// <summary>
        /// Recupera la classifica controllando i valori dell'albero dei procedimenti del protocollo a ritroso a mano che non vengano trovati valori.
        /// </summary>
        /// <param name="idIntervento"></param>
        /// <param name="idComune"></param>
        /// <param name="software"></param>
        /// <param name="codiceComune"></param>
        /// <returns></returns>
        public string GetClassificaProtocolloFromAlberoProcProtocollo(int idIntervento, string idComune, string software, string codiceComune)
        {
            var albero = this.GetById(idIntervento, idComune, codiceComune);

            if (!String.IsNullOrEmpty(albero.ClassificaProtocollazione))
                return albero.ClassificaProtocollazione;

            var range = albero.GetListaScCodice()
							  .ToArray()
							  .Reverse()
							  .Skip(1);

            foreach (var el in range)
            {
                albero = this.GetByScCodice(idComune, software, el, codiceComune);

                if (!String.IsNullOrEmpty(albero.ClassificaProtocollazione))
                    return albero.ClassificaProtocollazione;
            }

            return "";
        }

        public bool IsProtocollazioneAutomatica(int idIntervento, int source, string idComune, string software, string codiceComune)
        {
            var albero = this.GetById(idIntervento, idComune, codiceComune);

            if (!String.IsNullOrEmpty(albero.ProtocollazioneAutomatica) && Convert.ToInt32(albero.ProtocollazioneAutomatica) != 8)
                return (Convert.ToInt32(albero.ProtocollazioneAutomatica) & source) == source;

            var range = albero.GetListaScCodice()
							  .ToArray()
							  .Reverse()
							  .Skip(1);

            foreach (var el in range)
            {
                albero = this.GetByScCodice(idComune, software, el, codiceComune);

                if (!String.IsNullOrEmpty(albero.ProtocollazioneAutomatica) && Convert.ToInt32(albero.ProtocollazioneAutomatica) != 8)
                    return (Convert.ToInt32(albero.ProtocollazioneAutomatica) & source) == source;
            }

            return false;
        }

        public bool IsFascicolazioneAutomatica(int idIntervento, int source, string idComune, string software, string codiceComune)
        {
            var albero = this.GetById(idIntervento, idComune, codiceComune);

            if (!String.IsNullOrEmpty(albero.FascicolazioneAutomatica) && Convert.ToInt32(albero.FascicolazioneAutomatica) != 8)
                return (Convert.ToInt32(albero.FascicolazioneAutomatica) & source) == source;

            var range = albero.GetListaScCodice()
							  .ToArray()
							  .Reverse()
							  .Skip(1);

            foreach (var el in range)
            {
                albero = this.GetByScCodice(idComune, software, el, codiceComune);

                if (!String.IsNullOrEmpty(albero.FascicolazioneAutomatica) && Convert.ToInt32(albero.FascicolazioneAutomatica) != 8)
                    return (Convert.ToInt32(albero.FascicolazioneAutomatica) & source) == source;
            }

            return false;
        }

        /// <summary>
        /// Recupera la classifica del fascicolo controllando i valori dell'albero dei procedimenti del protocollo a ritroso a mano che non vengano trovati valori.
        /// </summary>
        /// <param name="idIntervento"></param>
        /// <param name="idComune"></param>
        /// <param name="software"></param>
        /// <param name="codiceComune"></param>
        /// <returns></returns>
        public string GetClassificaFascicoloFromAlberoProcProtocollo(int idIntervento, string idComune, string software, string codiceComune)
        {
            var albero = this.GetById(idIntervento, idComune, codiceComune);

            if (!String.IsNullOrEmpty(albero.ClassificaFascicolazione))
                return albero.ClassificaFascicolazione;

            var range = albero.GetListaScCodice()
							  .ToArray()
							  .Reverse()
							  .Skip(1);

            foreach (var el in range)
            {
                albero = this.GetByScCodice(idComune, software, el, codiceComune);

                if (!String.IsNullOrEmpty(albero.ClassificaFascicolazione))
                    return albero.ClassificaFascicolazione;
            }

            return "";
        }

        public string GetTestoTipoProtocolloFromAlberoProcProtocollo(int idIntervento, string idComune, string software, string codiceComune)
        {
            var albero = this.GetById(idIntervento, idComune, codiceComune);

            if (!String.IsNullOrEmpty(albero.TestoTipoProtocollazione))
                return albero.TestoTipoProtocollazione;

            var range = albero.GetListaScCodice()
							  .ToArray()
							  .Reverse()
							  .Skip(1);

            foreach (var el in range)
            {
                albero = this.GetByScCodice(idComune, software, el, codiceComune);

                if (!String.IsNullOrEmpty(albero.TestoTipoProtocollazione))
                    return albero.TestoTipoProtocollazione;
            }

            return "";
        }

        public string GetTestoTipoFascicoloFromAlberoProcProtocollo(int idIntervento, string idComune, string software, string codiceComune)
        {
            var albero = this.GetById(idIntervento, idComune, codiceComune);

            if (!String.IsNullOrEmpty(albero.TestoTipoFascicolazione))
                return albero.TestoTipoFascicolazione;

            var range = albero.GetListaScCodice()
							  .ToArray()
							  .Reverse()
							  .Skip(1);

            foreach (var el in range)
            {
                albero = this.GetByScCodice(idComune, software, el, codiceComune);

                if (!String.IsNullOrEmpty(albero.TestoTipoFascicolazione))
                    return albero.TestoTipoFascicolazione;
            }

            return "";
        }

        public AlberoProc GetByScCodice(string idComune, string software, string scCodice, string codiceComune)
        {
            var res = this.GetByScCodice(idComune, software, scCodice);
            ValorizzaDatiProtocollo(res, codiceComune);

            return res;
        }

		public int? CodiceProceduraDaIdIntervento(string idComune, int codiceIntervento)
		{
			string retVal = String.Empty;

			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				var ramoAlbero = this.GetById(codiceIntervento, idComune);

				return this.CodiceProceduraDaIntervento(ramoAlbero);
			}
			finally
			{
				if (closeCnn)
				{
					db.Connection.Close();
				}
			}
		}

		private int? CodiceProceduraDaIntervento(AlberoProc ramoAlbero)
		{
			string retVal = String.Empty;

			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				var condWhere = ramoAlbero.GetListaScCodice().ToString();

				var sql = @"select 
								FKIDPROCEDURA 
							from 
								alberoproc 
							where 
								idcomune = {0} and 
								software = {1} and 
								SC_CODICE in (" + condWhere + @") and
								FKIDPROCEDURA is not null
							order by 
								SC_CODICE DESC";

				sql = PreparaQueryParametrica(sql, "idComune", "software");

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idComune", ramoAlbero.Idcomune));
					cmd.Parameters.Add(db.CreateParameter("software", ramoAlbero.SOFTWARE));

					using (var dr = cmd.ExecuteReader())
					{
						while (dr.Read())
						{
							var objCodProcedura = dr["fkidprocedura"];

							if (String.IsNullOrEmpty(objCodProcedura.ToString()))
							{
								continue;
							}

							return Convert.ToInt32(objCodProcedura);
						}

						return null;
					}
				}
			}
			finally
			{
				if (closeCnn)
				{
					db.Connection.Close();
				}
			}
		}

		public int? GetCodiceOggettoWorkflowDaIdIntervento(string idComune, int idIntervento)
		{
			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				var ramoAlbero = this.GetById(idIntervento, idComune);

				var listaScId = ramoAlbero.GetListaScCodice();

				var condWhere = listaScId.ToString();

				var sql = @"select 
								codiceoggetto_workflow 
							from 
								alberoproc 
							where 
								alberoproc.idcomune = {0} and 
								alberoproc.software = {1} and 
								alberoproc.SC_CODICE in (" + condWhere + @") and
								alberoproc.codiceoggetto_workflow is not null								
							order by 
								alberoproc.SC_CODICE DESC";

				sql = PreparaQueryParametrica(sql, "idComune", "software");

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idComune", ramoAlbero.Idcomune));
					cmd.Parameters.Add(db.CreateParameter("software", ramoAlbero.SOFTWARE));

					using (var dr = cmd.ExecuteReader())
					{
						if (dr.Read())
						{
							var objCodOggettoWorkflow = dr["codiceoggetto_workflow"];

							if (objCodOggettoWorkflow != null && objCodOggettoWorkflow != DBNull.Value)
							{
								return Convert.ToInt32(objCodOggettoWorkflow);
							}
						}

						return null;
					}
				}
			}
			finally
			{
				if (closeCnn)
				{
					db.Connection.Close();
				}
			}
		}

		public int? GetIdRiepilogoDomandaDaIdIntervento(string idComune, int idIntervento)
		{
			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				var ramoAlbero = this.GetById(idIntervento, idComune);

				var listaScId = ramoAlbero.GetListaScCodice();

				var condWhere = listaScId.ToString();

				var sql = @"SELECT 
								sm_id 
							FROM
								alberoproc, 
								alberoproc_documenti 
							WHERE 
								alberoproc_documenti.idcomune = alberoproc.idcomune AND
								alberoproc_documenti.sm_fkscid = alberoproc.sc_id AND  
								alberoproc.idcomune = {0} AND 
								alberoproc.software = {1} AND
								alberoproc.sc_codice IN (" + condWhere + @") and
								alberoproc_documenti.flg_domandafo = 1 and
								alberoproc_documenti.codiceoggetto IS NOT null
							ORDER BY alberoproc.sc_codice desc";

				sql = PreparaQueryParametrica(sql, "idComune", "software");

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idComune", ramoAlbero.Idcomune));
					cmd.Parameters.Add(db.CreateParameter("software", ramoAlbero.SOFTWARE));

					using (var dr = cmd.ExecuteReader())
					{
						if (dr.Read())
						{
							var idDocumento = dr["sm_id"];

							if (idDocumento != null && idDocumento != DBNull.Value)
							{
								return Convert.ToInt32(idDocumento);
							}
						}

						return null;
					}
				}
			}
			finally
			{
				if (closeCnn)
				{
					db.Connection.Close();
				}
			}
		}

		public int? GetIdCertificatoDiInvioDomandaDaIdIntervento(string idComune, int idIntervento)
		{
			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				var ramoAlbero = this.GetById(idIntervento, idComune);

				var listaScId = ramoAlbero.GetListaScCodice();

				var condWhere = listaScId.ToString();

				var sql = @"select 
								codiceoggetto_certinvio 
							from 
								alberoproc,
								tipiprocedure 
							where 
								alberoproc.idcomune       = tipiprocedure.idcomune AND
								alberoproc.FKIDPROCEDURA  = tipiprocedure.codiceprocedura AND
								alberoproc.idcomune = {0} and 
								alberoproc.software = {1} and 
								alberoproc.SC_CODICE in (" + condWhere + @")								
							order by 
								alberoproc.SC_CODICE DESC";

				sql = PreparaQueryParametrica(sql, "idComune", "software");

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idComune", ramoAlbero.Idcomune));
					cmd.Parameters.Add(db.CreateParameter("software", ramoAlbero.SOFTWARE));

					using (var dr = cmd.ExecuteReader())
					{
						if (dr.Read())
						{
							var objCodProcedura = dr["codiceoggetto_certinvio"];

							if (objCodProcedura != null && objCodProcedura != DBNull.Value)
							{
								return Convert.ToInt32(objCodProcedura);
							}
						}

						return null;
					}
				}
			}
			finally
			{
				if (closeCnn)
				{
					db.Connection.Close();
				}
			}
		}

		public List<AlberoProcRuoli> GetRuoliDaIdIntervento(string idComune, int idIntervento)
		{
			AlberoProc albero = this.GetById(idIntervento, idComune);

			AlberoProcRuoli ruoli = new AlberoProcRuoli();
			ruoli.OthersWhereClause.Add("ALBEROPROC_RUOLI.FK_IDRUOLO IS NOT NULL");
			ruoli.OthersTables.Add("ALBEROPROC");
			ruoli.OthersWhereClause.Add("ALBEROPROC.IDCOMUNE = ALBEROPROC_RUOLI.IDCOMUNE");
			ruoli.OthersWhereClause.Add("ALBEROPROC.SC_ID = ALBEROPROC_RUOLI.FK_SC_ID");
			ruoli.OthersWhereClause.Add("ALBEROPROC.IDCOMUNE = '" + albero.Idcomune + "'");
			ruoli.OthersWhereClause.Add("ALBEROPROC.SOFTWARE = '" + albero.SOFTWARE + "'");

			string sc_codice = string.Empty;

			for (int i = 2; i <= albero.SC_CODICE.Length; i += 2)
			{
				sc_codice += "'" + albero.SC_CODICE.Substring(0, i) + "',";
			}

			sc_codice = sc_codice.Substring(0, sc_codice.Length - 1);

			ruoli.OthersWhereClause.Add("ALBEROPROC.SC_CODICE IN (" + sc_codice + ")");

			return new AlberoProcRuoliMgr(db).GetList(ruoli);
		}

		/// <summary>
		/// Legge l'albero dei procedimenti risalendo dall'endo passato.
		/// Restituisce il nodo corrispondente all'id passato e tutti  suoi nodi padre
		/// </summary>
		/// <param name="idComune">id comune</param>
		/// <param name="idIntervento">id del nodo dell'albero</param>
		/// <returns>nodo corrispondente all'id passato e tutti  suoi nodi padre</returns>
		public ClassTree<AlberoProc> RisaliStrutturaAlbero(string idComune, int idIntervento, bool verticalizzazioneCartAttiva)
		{
			var ramoAlbero = this.GetById(idIntervento, idComune);

			var listaScId = ramoAlbero.GetListaScCodice();

			var condWhere = listaScId.ToString();

			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				string sql = PreparaQueryParametrica("select * from alberoproc where idComune = {0} and software = {1}", "idComune", "software");

				sql += String.Format(" and sc_codice in ({0}) order by sc_codice asc", condWhere);

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
					cmd.Parameters.Add(db.CreateParameter("software", ramoAlbero.SOFTWARE));

					var lista = db.GetClassList<AlberoProc>(cmd);

					// Ho sempre almeno un elemento
					var albero = new ClassTree<AlberoProc>(lista[0]);
					var tmpNodo = albero.NodiFiglio;

					for (int i = 1; i < lista.Count; i++)
					{
						// Se è attiva la verticalizzazione CART devo mostrare solamente i nodi dell'albero che hanno un collegamento su 
						// STP_ENDO_TIPO2 con STP_ENDO_TIPO2.codiceoggetto != null
						if (verticalizzazioneCartAttiva)
						{
							var contieneOggettoCart = new StpEndoTipo2Mgr(db).GetList(new StpEndoTipo2
							{
								Idcomune = idComune,
								FkScId = Convert.ToInt32(lista[i].Sc_id),
								OthersWhereClause = new ArrayList { "CODICEOGGETTO is not null" }
							}).Count != 0;

							if (!contieneOggettoCart)
							{
								lista[i].SC_NOTE = String.Empty;
							}
						}
						
						tmpNodo.Add(new ClassTree<AlberoProc>(lista[i]));
						tmpNodo = tmpNodo[0].NodiFiglio;
					}

					return albero;
				}
			}
			finally
			{
				if (closeCnn)
				{
					db.Connection.Close();
				}
			}
		}

		internal IEnumerable<AlberoProc> GetAlberaturaIntervento(string idComune, int idIntervento)
		{
			var intervento = GetById(idIntervento, idComune);
			var software = intervento.SOFTWARE;

			var whereIn = intervento.GetListaScCodice().ToString();

			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				string sql = @"SELECT DISTINCT alberoproc.*
								FROM alberoproc
								WHERE idcomune   = {0}
								AND software     = {1}
								AND sc_codice IN (" + whereIn + @")
								ORDER BY sc_codice ASC";

				sql = PreparaQueryParametrica(sql, "idComune", "software", "scCodice");

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
					cmd.Parameters.Add(db.CreateParameter("software", software));

					return db.GetClassList<AlberoProc>(cmd);
				}
			}
			finally
			{
				if (closeCnn)
				{
					db.Connection.Close();
				}
			}
		}

        public string GetIdDrupalDaCodiceIntervento(string idComune, int idIntervento)
        {
            var rami = GetAlberaturaIntervento(idComune, idIntervento);

            var ramo = rami.Reverse().FirstOrDefault(x => !String.IsNullOrEmpty(x.DrupalNid));

            return ramo == null ? null : ramo.DrupalNid;
        }
		
		/// <summary>
		/// TODO: Il flag SC_ATTIVA deve essere controllato a seconda che il metodo venga chiamato dall'area riservata 
		/// oppure dal frontend.
		/// Se chiamato dall'area riservata il flag deve essere == 1 || == 2
		/// Se chiamato dal frontend deve essere == 1 || == 3
		/// </summary>
		internal List<AlberoProc> GetAlberaturaCompletaDaScCodice(string idComune, string software, string scCodice, AmbitoRicerca ambitoricerca)
		{
			var partiCodice = new List<string>();

			for (int i = 0; i < scCodice.Length; i = i + 2)
			{
				partiCodice.Add(scCodice.Substring(0, i + 2));
			}

			var whereIn = "'" + String.Join("','", partiCodice.ToArray()) + "'";
			
			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				string sql = @"SELECT DISTINCT alberoproc.*
								FROM alberoproc
								WHERE idcomune   = {0}
								AND software     = {1}
								AND ( sc_codice IN (" + whereIn + @")
								OR sc_codice LIKE {2} )
								AND sc_attivo  = 0
								AND (sc_pubblica in (" + FiltroRicercaFlagPubblica.Get(ambitoricerca) + @") or sc_pubblica is null )" +
								" ORDER BY sc_codice ASC";

				sql = PreparaQueryParametrica(sql, "idComune", "software", "scCodice");

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
					cmd.Parameters.Add(db.CreateParameter("software", software));
					cmd.Parameters.Add(db.CreateParameter("scCodice", scCodice + "%"));

					var interventi = db.GetClassList<AlberoProc>(cmd);
					var listaFoglieDaRimuovere = new List<AlberoProc>();

					// Prendo tutti i nodi di primo livello che hanno pubblica nullo
					var q = interventi.Where(nodoAlbero => nodoAlbero.SC_CODICE.Length == 2 && String.IsNullOrEmpty(nodoAlbero.SC_PUBBLICA));

					foreach (var nodoAlbero in q)
					{
						listaFoglieDaRimuovere.Add(nodoAlbero);
					}

					listaFoglieDaRimuovere.ForEach(nodoAlbero => interventi.Remove(nodoAlbero));

					return interventi;
				}
			}
			finally
			{
				if (closeCnn)
				{
					db.Connection.Close();
				}
			}
		}

		public List<OnereDto> GetListaOneriDaIdIntervento(string idComune, int idIntervento)
		{
			var ramoAlbero = this.GetById(idIntervento, idComune);

			var listaScId = ramoAlbero.GetListaScCodice();

			var condWhere = listaScId.ToString();

			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				string sql = @"SELECT 
							  DISTINCT 
							  tipicausalioneri.co_id AS codice,
							  tipicausalioneri.co_descrizione AS descrizione,
							  alberoproc_oneri.ao_importocausale AS importo,
							  alberoproc_oneri.note as note
							FROM 
							  alberoproc,
							  alberoproc_oneri,                                                             
							  tipicausalioneri
							WHERE
							  tipicausalioneri.idComune = alberoproc_oneri.idComune AND
							  tipicausalioneri.co_id = alberoproc_oneri.ao_fk_coid AND 
							  alberoproc_oneri.idComune = alberoproc.idComune AND
							  alberoproc_oneri.ao_fk_scid = alberoproc.sc_id  AND
							  alberoproc.idComune = {0} AND
							  alberoproc.software = {1} AND
							  alberoproc.sc_codice IN (" + condWhere + ") order by tipicausalioneri.co_descrizione";

				sql = PreparaQueryParametrica(sql, "idComune", "software");

				using (var cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
					cmd.Parameters.Add(db.CreateParameter("software", ramoAlbero.SOFTWARE));

					using (var dr = cmd.ExecuteReader())
					{
						var oneri = new List<OnereDto>();

						while (dr.Read())
						{
							var el = new OnereDto
							{
								Codice = Convert.ToInt32(dr["codice"]),
								Descrizione = dr["descrizione"].ToString(),
								Importo = Convert.ToSingle(dr["importo"] == DBNull.Value ? (object)0 : dr["importo"]),
								OrigineOnere = "I",
								CodiceInterventoOEndoOrigine = ramoAlbero.Sc_id.Value,
								InterventoOEndoOrigine = ramoAlbero.SC_DESCRIZIONE,
								Note = dr["note"].ToString()
							};

							oneri.Add(el);
						}

						return oneri;
					}
				}
			}
			finally
			{
				if (closeCnn)
				{
					db.Connection.Close();
				}
			}
		}

		public List<DocumentoInterventoDto> GetDocumentiDaIdIntervento(string idComune, int codiceIntervento, AmbitoRicerca ambitoRicercaDocumento)
		{
			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				var ramoAlbero = this.GetById(codiceIntervento, idComune);

				var listaScId = ramoAlbero.GetListaScCodice();

				var condWhere = listaScId.ToString();

				var sql = @"SELECT 
								alberoproc_documenti.sm_id AS codice,
								alberoproc_documenti.descrizione AS descrizione,
								alberoproc_documenti.note AS note,
								alberoproc_documenti.codiceoggetto AS codiceoggetto,
								alberoproc_documenti.Richiesto AS obbligatorio,
								alberoproc_documenti.flg_domandafo AS domandafo,
								alberoproc_documenti.fo_richiedefirma AS richiedefirma,
								alberoproc_documenti.fo_tipodownload AS tipodownload,
								oggetti.nomefile AS nomeFile
							FROM                                                                                 
								alberoproc join alberoproc_documenti ON 
									alberoproc_documenti.idComune = alberoproc.idcomune AND
									alberoproc_documenti.sm_fkscid = alberoproc.sc_id
							left join oggetti ON 
								alberoproc_documenti.idComune = oggetti.idcomune AND
								alberoproc_documenti.codiceoggetto = oggetti.codiceoggetto
							WHERE
								alberoproc.idcomune = {0} AND
								alberoproc.software = {1} AND
								alberoproc_documenti.pubblica in (" + FiltroRicercaFlagPubblica.Get(ambitoRicercaDocumento) + @") and
								alberoproc.sc_codice in (" + condWhere + @")
							ORDER BY
								alberoproc_documenti.ordine";

				sql = PreparaQueryParametrica(sql, "idComune", "software");

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
					cmd.Parameters.Add(db.CreateParameter("software", ramoAlbero.SOFTWARE));

					using (var dr = cmd.ExecuteReader())
					{
						var documentiIntervento = new List<DocumentoInterventoDto>();

						while (dr.Read())
						{
							var codice = Convert.ToInt32(dr["codice"]);
							var descrizione = dr["descrizione"].ToString();
							var note = dr["note"].ToString();
							var codiceoggetto = (int?)null;
							var obbligatorio = dr["obbligatorio"].ToString() == "1";
							var domandafo = dr["domandafo"].ToString() == "1";
							var richiedefirma = dr["richiedefirma"].ToString() == "1";
							var tipodownload = dr["tipodownload"].ToString();
							var nomeFile = dr["nomeFile"].ToString();

							var objCodiceOggetto = dr["codiceoggetto"];

							if (objCodiceOggetto != DBNull.Value)
							{
								codiceoggetto = Convert.ToInt32(objCodiceOggetto);
							}

							var el = new DocumentoInterventoDto
							{
								Codice = codice,
								Descrizione = descrizione,
								CodiceOggetto = codiceoggetto,
								DomandaFo = domandafo,
								Obbligatorio = obbligatorio,
								RichiedeFirma = richiedefirma,
								TipoDownload = tipodownload,
								NomeFile = nomeFile
							};

							documentiIntervento.Add(el);
						}

						return documentiIntervento;
					}
				}
			}
			finally
			{
				if (closeCnn)
				{
					db.Connection.Close();
				}
			}
		}

		public List<SchedaDinamicaInterventoDto> GetSchedeDinamicheFoDaIdIntervento(string idComune, int idIntervento)
		{
			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				var ramoAlbero = this.GetById(idIntervento, idComune);

				var listaScId = ramoAlbero.GetListaScCodice();

				var condWhere = listaScId.ToString();

				string sql = PreparaQueryParametrica(
								@"SELECT 
									distinct
									dyn2_modellit.id,
									dyn2_modellit.descrizione,
									alberoproc_dyn2modellit.flag_tipofirma,
									alberoproc_dyn2modellit.flag_facoltativa,
									alberoproc_dyn2modellit.ordine
								FROM 
									alberoproc,
									alberoproc_dyn2modellit,
									dyn2_modellit
								WHERE
									alberoproc_dyn2modellit.idComune = alberoproc.idComune AND
									alberoproc_dyn2modellit.fk_sc_id = alberoproc.sc_id AND
									dyn2_modellit.idComune = alberoproc_dyn2modellit.idComune AND
									dyn2_modellit.id = alberoproc_dyn2modellit.fk_d2mt_id AND
									alberoproc.idComune = {0} and alberoproc.software = {1} and 
									alberoproc_dyn2modellit.flag_pubblica = 1", 
								"IdComune", 
								"Software");

				sql += String.Format(" AND alberoproc.sc_codice IN ({0})", condWhere);
				sql += " order by alberoproc_dyn2modellit.ordine, dyn2_modellit.descrizione";

				var schedeIntervento = new List<SchedaDinamicaInterventoDto>();

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
					cmd.Parameters.Add(db.CreateParameter("software", ramoAlbero.SOFTWARE));

					using (var dr = cmd.ExecuteReader())
					{
						while (dr.Read())
						{
							var idModello = Convert.ToInt32(dr["id"]);
							var descrizioneModello = dr["descrizione"].ToString();
							var objTipoFirma = dr["flag_tipofirma"];
							var facoltativa = dr["flag_facoltativa"].ToString() == String.Empty ? false : dr["flag_facoltativa"].ToString() == "1";
							var ordine = dr["ordine"] == DBNull.Value ? (int?)null : (int?)Convert.ToInt32(dr["ordine"]);

							var tipoFirma = TipoFirmaEnum.NessunaFirma;

							if (objTipoFirma != DBNull.Value)
							{
								tipoFirma = (TipoFirmaEnum)Convert.ToInt32(objTipoFirma);
							}

							var schedaPresente = schedeIntervento.FirstOrDefault(x => x.Id == idModello);

							if (schedaPresente != null)
							{
								var errMsg = "Nell'intervento {0} (o in uno dei nodi padre ) la scheda dinamica {1} è presente più di una volta. " + Environment.NewLine +
												"Parametri della scheda presente: " + Environment.NewLine +
												"- tipo firma={2}, " + Environment.NewLine +
												"- facoltativa={3} " + Environment.NewLine +
												"Parametri della scheda che si sta cercando di aggiungere:" + Environment.NewLine +
												"- tipo firma={4}, " + Environment.NewLine +
												"- facoltativa={5}";

								var msg = String.Format(errMsg, ramoAlbero.Sc_id, idModello, schedaPresente.TipoFirma, schedaPresente.Facoltativa, tipoFirma, facoltativa);

								throw new ConfigurationErrorsException(msg);
							}

							var scheda = new SchedaDinamicaInterventoDto
							{
								CodiceIntervento = ramoAlbero.Sc_id.Value,
								Id = idModello,
								Descrizione = descrizioneModello,
								TipoFirma = tipoFirma,
								Facoltativa = facoltativa,
								Ordine = ordine
							};

							schedeIntervento.Add(scheda);
						}
					}
				}

				// Aggiungo le schede della procedura dell'intervento
				//var schedeProcedura = this.GetSchedeDinamicheDaProceduraIntervento(ramoAlbero);

				//foreach (var schedaProcedura in schedeProcedura)
				//{
				//	if (schedeIntervento.FirstOrDefault(x => x.Codice == schedaProcedura.Codice) == null)
				//	{
				//		schedeIntervento.Add(schedaProcedura);
				//	}
				//}

				// Le schede devono essere visualizzate in base all'ordine e in base alla descrizione.
				// Le schede senza ordine vanno mostrate prima di quelle con ordine
				schedeIntervento.Sort((x, y) =>
				{
					if (x == y)
					{
						return 0;
					}

					if (x == null)
					{
						return 1;
					}

					if (y == null)
					{
						return -1;
					}

					var cmpRes = x.Ordine.GetValueOrDefault(-1).CompareTo(y.Ordine.GetValueOrDefault(-1));

					if (cmpRes != 0)
					{
						return cmpRes;
					}

					return x.Descrizione.CompareTo(y.Descrizione);
				});

				return schedeIntervento;
			}
			finally
			{
				if (closeCnn)
				{
					db.Connection.Close();
				}
			}
		}

		private List<SchedaDinamicaInterventoDto> GetSchedeDinamicheDaProceduraIntervento(AlberoProc ramoAlbero)
		{
			var codiceProcedura = this.CodiceProceduraDaIntervento(ramoAlbero);

			if (!codiceProcedura.HasValue)
			{
				return new List<SchedaDinamicaInterventoDto>();
			}

			var schedeIntervento = new List<SchedaDinamicaInterventoDto>();

			// Leggo la sita dei modelli dinamici collegati alla procedura dell'intervento selezionato
			var sql = PreparaQueryParametrica(
						@"SELECT
							dyn2_modellit.id,
							dyn2_modellit.descrizione, 
							tipiprocedure_dyn2modellit.flag_tipofirma,
							tipiprocedure_dyn2modellit.flag_facoltativa 
						FROM
							tipiprocedure_dyn2modellit,
							dyn2_modellit
						WHERE
							dyn2_modellit.idcomune = tipiprocedure_dyn2modellit.idcomune AND
							dyn2_modellit.id = tipiprocedure_dyn2modellit.fk_d2mt_id AND
							tipiprocedure_dyn2modellit.idcomune = {0} AND 
							tipiprocedure_dyn2modellit.fk_codiceprocedura = {1} and tipiprocedure_dyn2modellit.flag_pubblica = 1", 
						"idComune", 
						"codiceprocedura");

			using (IDbCommand cmd = db.CreateCommand(sql))
			{
				cmd.Parameters.Add(db.CreateParameter("idComune", ramoAlbero.Idcomune));
				cmd.Parameters.Add(db.CreateParameter("codiceprocedura", codiceProcedura));

				using (var dr = cmd.ExecuteReader())
				{
					while (dr.Read())
					{
						var idModello = Convert.ToInt32(dr["id"]);
						var descrizioneModello = dr["descrizione"].ToString();
						var objTipoFirma = dr["flag_tipofirma"];
						var facoltativa = dr["flag_facoltativa"].ToString() == String.Empty ? false : dr["flag_facoltativa"].ToString() == "1";

						var tipoFirma = TipoFirmaEnum.NessunaFirma;

						if (objTipoFirma != DBNull.Value)
						{
							tipoFirma = (TipoFirmaEnum)Convert.ToInt32(objTipoFirma);
						}

						var schedaPresente = schedeIntervento.FirstOrDefault(x => x.Id == idModello);

						// Se la scheda è già presente la ignoro e utilizzo quella dell'intervento
						if (schedaPresente != null)
						{
							continue;
						}

						var scheda = new SchedaDinamicaInterventoDto
						{
							CodiceIntervento = ramoAlbero.Sc_id.Value,
							Id = idModello,
							Descrizione = descrizioneModello,
							TipoFirma = tipoFirma,
							Facoltativa = facoltativa
						};

						schedeIntervento.Add(scheda);
					}
				}
			}

			return schedeIntervento;
		}

		public List<FamigliaEndoprocedimentoDto> GetEndoFacoltativiDaIdIntervento(string idComune, int codiceIntervento)
		{
			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				var famiglieEndoTrovate = new List<FamigliaEndoprocedimentoDto>();
				var naturaMgr = new NaturaEndoMgr(new DataBase(db.ConnectionDetails.ConnectionString, db.ConnectionDetails.ProviderType));

				// Genero l'alberatura del'intervento
				var ramoAlbero = this.GetById(codiceIntervento, idComune);
				var listaScCodice = ramoAlbero.GetListaScCodice();
				var condWhere = listaScCodice.ToString();

				// Estraggo la lista dei codici intervento risalendo l'abero
				var sql = "select sc_id from alberoproc where idcomune={0} and sc_codice in (" + condWhere + ") and software={1} order by " + db.Specifics.LengthFunction("sc_codice") + " desc";

				var listaScId = new List<int>();

				sql = PreparaQueryParametrica(sql, "idComune", "software");

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
					cmd.Parameters.Add(db.CreateParameter("software", ramoAlbero.SOFTWARE));

					using (var dr = cmd.ExecuteReader())
					{
						while (dr.Read())
						{
							listaScId.Add(Convert.ToInt32(dr[0]));
						}
					}
				}

				// Per ogni sc_id verifico se ci sono records nella tabella ALBEROPROC_ARENDO
				var righearEndo = this.EstraiScIdConRiferimentiSuAlberoprocArendo(idComune, listaScId);

				if (righearEndo.Count == 0)
				{
					return famiglieEndoTrovate;
				}

				var listaFamiglie = new Dictionary<int, FamigliaEndoprocedimentoDto>();
				var listaTipi = new Dictionary<int, TipoEndoprocedimentoDto>();

				var listaQuery = new List<string>();

				// La query di selezione degli endo è composta da una serie di union per ciascuna 
				// condizione estratta dalla tabella alberoproc_arnedo
				var sqlBase = @"SELECT 
							tipifamiglieendo.codice as codFamiglia,
							tipifamiglieendo.tipo as desFamiglia,
							tipiendo.codice as codTipo,
							tipiendo.tipo as desTipo,
							inventarioprocedimenti.codiceinventario as codEndo,
							inventarioprocedimenti.procedimento as desEndo,
							'0' as obbEndo,
							'0' as flgPrincipale,
							tipifamiglieendo.ordine as famigliaOrdine,
							tipiendo.ordine as tipoEndoOrdine,
							inventarioprocedimenti.ordine as endoOrdine,
							inventarioprocedimenti.codicenatura as codicenatura,
                            inventarioprocedimenti.FLAGTIPITITOLO 
						FROM
							inventarioprocedimenti inner join tipiendo ON 
								tipiendo.idcomune = inventarioprocedimenti.idcomune AND
								tipiendo.codice = inventarioprocedimenti.codicetipo
							inner join tipifamiglieendo ON 
								tipifamiglieendo.idcomune = tipiendo.idcomune AND
								tipifamiglieendo.codice = tipiendo.codicefamigliaendo
						WHERE
							inventarioprocedimenti.idcomune = {0} and
							(inventarioprocedimenti.software = {1} or inventarioprocedimenti.software='TT') and
							inventarioprocedimenti.disabilitato = 0 and ";

				var queryFmtString = "select a.* from ({0}) a order by famigliaOrdine, desFamiglia, tipoEndoOrdine, desTipo, endoOrdine, desEndo";

				foreach (var rigaArEndo in righearEndo)
				{
					var sb = new StringBuilder(PreparaQueryParametrica(sqlBase, "idComune", "software"));

					sb.Append("tipiendo.codicefamigliaendo = " + rigaArEndo.FkFamigliaendo.Value);

					if (rigaArEndo.FkCategoriaendo.HasValue)
					{
						sb.Append(" and tipiendo.codice =" + rigaArEndo.FkCategoriaendo.Value);
					}

					listaQuery.Add(sb.ToString());
				}

				var sqlSelezioneEndo = String.Format(queryFmtString, String.Join(" union ", listaQuery.ToArray()));

				this._log.DebugFormat("Query selezione endo facoltativi: {0}", sqlSelezioneEndo);

				using (IDbCommand cmd = db.CreateCommand(sqlSelezioneEndo))
				{
					cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
					cmd.Parameters.Add(db.CreateParameter("software", ramoAlbero.SOFTWARE));

					using (var dr = cmd.ExecuteReader())
					{
						while (dr.Read())
						{
							var codFamiglia = -1;
							var desFamiglia = "Endoprocedimenti";

							var codTipo = -1;
							var desTipo = "Endoprocedimenti";

							var objCodFamiglia = dr["codFamiglia"];

							if (objCodFamiglia != DBNull.Value)
							{
								codFamiglia = Convert.ToInt32(objCodFamiglia);
								desFamiglia = dr["desFamiglia"].ToString();
							}

							var objCodTipologia = dr["codTipo"];

							if (objCodTipologia != DBNull.Value)
							{
								codTipo = Convert.ToInt32(objCodTipologia);
								desTipo = dr["desTipo"].ToString();
							}

							var codEndo = Convert.ToInt32(dr["codEndo"]);
							var desEndo = dr["desEndo"].ToString();
							var obbEndo = dr["obbEndo"].ToString() == "1";
							var flgPrincipale = dr["flgPrincipale"].ToString() == "1";
							var ordine = dr["endoOrdine"] == DBNull.Value ? 9999 : Convert.ToInt32(dr["endoOrdine"]);
							var codiceNatura = dr["codicenatura"] == DBNull.Value ? -1 : Convert.ToInt32(dr["codicenatura"]);
                            var tipoTitoloObbligatorio = dr["FLAGTIPITITOLO"] == DBNull.Value ? false : (dr["FLAGTIPITITOLO"].ToString() == "1");

							if (!listaFamiglie.ContainsKey(codFamiglia))
							{
								var famiglia = new FamigliaEndoprocedimentoDto { Codice = codFamiglia, Descrizione = desFamiglia };
								listaFamiglie.Add(codFamiglia, famiglia);
								famiglieEndoTrovate.Add(famiglia);
							}

							if (!listaTipi.ContainsKey(codTipo))
							{
								var tipo = new TipoEndoprocedimentoDto { Codice = codTipo, Descrizione = desTipo };
								listaTipi.Add(codTipo, tipo);
								listaFamiglie[codFamiglia].TipiEndoprocedimenti.Add(tipo);
							}

							var natura = naturaMgr.GetById(idComune, codiceNatura);

							var endo = new EndoprocedimentoDto
							{
								Codice = codEndo,
								Descrizione = desEndo,
								Richiesto = obbEndo,
								Principale = flgPrincipale,
								Ordine = ordine,
								BinarioDipendenze = natura == null || String.IsNullOrEmpty(natura.BINARIODIPENDENZE) ? 0 : Convert.ToInt32(natura.BINARIODIPENDENZE),
								CodiceNatura = natura == null ? (int?)null : Convert.ToInt32(natura.CODICENATURA),
								Natura = natura == null ? String.Empty : natura.NATURA
							};

							listaTipi[codTipo].Endoprocedimenti.Add(endo);
						}
					}
				}

				return famiglieEndoTrovate;
			}
			finally
			{
				if (closeCnn)
				{
					db.Connection.Close();
				}
			}
		}

		private List<AlberoprocAREndo> EstraiScIdConRiferimentiSuAlberoprocArendo(string idComune, List<int> listaScId)
		{
			var mgr = new AlberoprocAREndoMgr(db);

			for (int i = 0; i < listaScId.Count; i++)
			{
				var filtro = new AlberoprocAREndo
				{
					Idcomune = idComune,
					FkScid = listaScId[i]
				};

				var res = mgr.GetList(filtro);

				if (res.Count > 0)
				{
					return res;
				}
			}

			return new List<AlberoprocAREndo>();
		}

		public List<FamigliaEndoprocedimentoDto> GetEndoDaIdIntervento(string idComune, int codIntervento, AmbitoRicerca ambitoRicerca)
		{
			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				var naturaEndoMgr = new NaturaEndoMgr(new DataBase(db.ConnectionDetails.ConnectionString, db.ConnectionDetails.ProviderType));

				var ramoAlbero = this.GetById(codIntervento, idComune);

				var listaScId = ramoAlbero.GetListaScCodice();

				var condWhere = listaScId.ToString();

				string sql = @"SELECT 
									tipifamiglieendo.codice as codFamiglia,
									tipifamiglieendo.tipo as desFamiglia,
									tipiendo.codice as codTipo,
									tipiendo.tipo as desTipo,
									inventarioprocedimenti.codiceinventario as codEndo,
									inventarioprocedimenti.procedimento as desEndo,
									alberoproc_endo.flag_richiesto as obbEndo,
									alberoproc_endo.flag_principale as flgPrincipale,
									tipifamiglieendo.ordine as famigliaOrdine,
									tipiendo.ordine as tipoEndoOrdine,
									inventarioprocedimenti.ordine as endoOrdine,
									codicenatura as codicenatura,
                                    inventarioprocedimenti.FLAGTIPITITOLO 
								FROM
									alberoproc inner join alberoproc_endo ON
										alberoproc.idComune = alberoproc_endo.idComune AND 
										alberoproc.sc_id = alberoproc_endo.fkscid
									inner join inventarioprocedimenti ON
										inventarioprocedimenti.idcomune =alberoproc_endo.idComune AND
										inventarioprocedimenti.codiceinventario =alberoproc_endo.codiceinventario
									left join tipiendo ON 
										tipiendo.idcomune = inventarioprocedimenti.idcomune AND
										tipiendo.codice = inventarioprocedimenti.codicetipo
									left join tipifamiglieendo ON 
										tipifamiglieendo.idcomune = tipiendo.idcomune AND
										tipifamiglieendo.codice = tipiendo.codicefamigliaendo
								WHERE
									alberoproc.idComune = {0} AND
									alberoproc.software = {1} and 
									alberoproc.sc_codice IN (" + condWhere + @") ";

				if (ambitoRicerca == AmbitoRicerca.AreaRiservata || ambitoRicerca == AmbitoRicerca.FrontofficePubblico)
				{
					sql += " and alberoproc_endo.FLAG_PUBBLICA = 1 ";
				}

                sql += " order by alberoproc_endo.flag_principale desc,alberoproc_endo.flag_richiesto desc,tipiendo.ordine,tipifamiglieendo.ordine,inventarioprocedimenti.ordine, inventarioprocedimenti.procedimento";

				sql = PreparaQueryParametrica(sql, "idComune", "software");

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
					cmd.Parameters.Add(db.CreateParameter("software", ramoAlbero.SOFTWARE));

					using (var dr = cmd.ExecuteReader())
					{
						var famiglieEndoTrovate = new List<FamigliaEndoprocedimentoDto>();
						var listaFamiglie = new Dictionary<int, FamigliaEndoprocedimentoDto>();
						var listaTipi = new Dictionary<int, TipoEndoprocedimentoDto>();

						while (dr.Read())
						{
							var codFamiglia = -1;
							var desFamiglia = "Endoprocedimenti";

							var codTipo = -1;
							var desTipo = "Endoprocedimenti";

							var objCodFamiglia = dr["codFamiglia"];

							if (objCodFamiglia != DBNull.Value)
							{
								codFamiglia = Convert.ToInt32(objCodFamiglia);
								desFamiglia = dr["desFamiglia"].ToString();
							}

							var objCodTipologia = dr["codTipo"];

							if (objCodTipologia != DBNull.Value)
							{
								codTipo = Convert.ToInt32(objCodTipologia);
								desTipo = dr["desTipo"].ToString();
							}

							var codEndo = Convert.ToInt32(dr["codEndo"]);
							var desEndo = dr["desEndo"].ToString();
							var obbEndo = dr["obbEndo"].ToString() == "1";
							var flgPrincipale = dr["flgPrincipale"].ToString() == "1";
							var ordine = dr["endoOrdine"] == DBNull.Value ? 9999 : Convert.ToInt32(dr["endoOrdine"]);
							var codiceNatura = dr["codicenatura"] == DBNull.Value ? -1 : Convert.ToInt32(dr["codicenatura"]);
                            var tipoTitoloObbligatorio = dr["FLAGTIPITITOLO"] == DBNull.Value ? false : (dr["FLAGTIPITITOLO"].ToString() == "1");

							if (!listaFamiglie.ContainsKey(codFamiglia))
							{
								var famiglia = new FamigliaEndoprocedimentoDto { Codice = codFamiglia, Descrizione = desFamiglia };
								listaFamiglie.Add(codFamiglia, famiglia);
								famiglieEndoTrovate.Add(famiglia);
							}

							if (!listaTipi.ContainsKey(codTipo))
							{
								var tipo = new TipoEndoprocedimentoDto { Codice = codTipo, Descrizione = desTipo };
								listaTipi.Add(codTipo, tipo);
								listaFamiglie[codFamiglia].TipiEndoprocedimenti.Add(tipo);
							}

							var natura = naturaEndoMgr.GetById(idComune, codiceNatura);

							var endo = new EndoprocedimentoDto
							{
								Codice = codEndo,
								Descrizione = desEndo,
								Richiesto = obbEndo,
								Principale = flgPrincipale,
								Ordine = ordine,
								BinarioDipendenze = natura == null || String.IsNullOrEmpty(natura.BINARIODIPENDENZE) ? 0 : Convert.ToInt32(natura.BINARIODIPENDENZE),
								CodiceNatura = natura == null ? (int?)null : Convert.ToInt32(natura.CODICENATURA),
								Natura = natura == null ? String.Empty : natura.NATURA,
                                TipoTitoloObbligatorio = tipoTitoloObbligatorio
							};

							listaTipi[codTipo].Endoprocedimenti.Add(endo);
						}

						return famiglieEndoTrovate;
					}
				}
			}
			finally
			{
				if (closeCnn)
				{
					db.Connection.Close();
				}
			}
		}

		public List<NormativaDto> GetListaNormativeDaIdIntervento(string idComune, int codiceIntervento)
		{
			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				var ramoAlbero = this.GetById(codiceIntervento, idComune);

				var listaScId = ramoAlbero.GetListaScCodice();

				var condWhere = listaScId.ToString();

				string sql = @"SELECT 
								leggi.le_id AS codice,
								leggi.le_descrizione AS descrizione,
								leggi.codiceoggetto AS codiceoggetto,
								leggi.le_link as link,
								leggitipi.lt_descrizione AS categoria
							FROM 
								alberoproc join alberoproc_leggi ON
									alberoproc_leggi.idcomune = alberoproc.idcomune AND
									alberoproc_leggi.sl_fkscid = alberoproc.sc_id 
								join leggi ON
									leggi.idcomune = alberoproc_leggi.idcomune AND
									leggi.le_id = alberoproc_leggi.sl_fkleid
								left join leggitipi ON
									leggitipi.idcomune = leggi.idcomune AND
									leggitipi.lt_id = leggi.le_fkltid
							WHERE
								alberoproc.idcomune = {0} AND
								alberoproc.software={1} and
								alberoproc.sc_codice IN (" + condWhere + @")";

				sql = PreparaQueryParametrica(sql, "idComune", "software");

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
					cmd.Parameters.Add(db.CreateParameter("software", ramoAlbero.SOFTWARE));

					using (var dr = cmd.ExecuteReader())
					{
						var normativeTrovate = new List<NormativaDto>();
						var listaFamiglie = new Dictionary<int, FamigliaEndoprocedimentoDto>();
						var listaTipi = new Dictionary<int, TipoEndoprocedimentoDto>();

						while (dr.Read())
						{
							var objCodiceOggetto = dr["codiceoggetto"];

							var el = new NormativaDto
							{
								Codice = Convert.ToInt32(dr["codice"]),
								Descrizione = dr["descrizione"].ToString(),
								CodiceOggetto = objCodiceOggetto == DBNull.Value ? (int?)null : Convert.ToInt32(objCodiceOggetto),
								Categoria = dr["categoria"].ToString(),
								Link = dr["link"].ToString()
							};

							normativeTrovate.Add(el);
						}

						return normativeTrovate;
					}
				}
			}
			finally
			{
				if (closeCnn)
				{
					db.Connection.Close();
				}
			}
		}

		public List<FaseAttuativaDto> GetListaFasiAttuativeDaIdIntervento(string idComune, int codiceIntervento)
		{
			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				var codiceProcedura = this.CodiceProceduraDaIdIntervento(idComune, codiceIntervento);

				if (!codiceProcedura.HasValue)
				{
					return new List<FaseAttuativaDto>();
				}

				string sql = @"SELECT 
								id,
								titolosubprocedura,
								subprocedura,
								note
							FROM 
								subprocedure
							where 
								idcomune = {0} and 
								codiceprocedura = {1}
							order by numerosubprocedura asc";

				sql = PreparaQueryParametrica(sql, "idComune", "codiceProcedura");

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
					cmd.Parameters.Add(db.CreateParameter("codiceProcedura", codiceProcedura));

					using (var dr = cmd.ExecuteReader())
					{
						var fasiAttuativeTrovate = new List<FaseAttuativaDto>();

						while (dr.Read())
						{
							var el = new FaseAttuativaDto
							{
								Codice = Convert.ToInt32(dr["id"]),
								Descrizione = dr["titolosubprocedura"].ToString(),
								DescrizioneEstesa = dr["subprocedura"].ToString(),
								Note = dr["note"].ToString()
							};

							fasiAttuativeTrovate.Add(el);
						}

						return fasiAttuativeTrovate;
					}
				}
			}
			finally
			{
				if (closeCnn)
				{
					db.Connection.Close();
				}
			}
		}
		
		/// <summary>
		/// Utilizzato dal frontoffice. Restituisce una lista di InterventoDto con popolati solo i campi
		/// Codice, Descrizione, ScCodice,HaNodiFiglio, HaNote
		/// </summary>
		public List<InterventoDto> GetNodiFiglio(string idComune, string software, int idNodo, AmbitoRicerca ambito, bool verticalizzazioneCartAttiva)
		{
			AlberoProc nodo = this.GetById(idNodo, idComune);

			string scCodice = nodo == null ? String.Empty : nodo.SC_CODICE;

			var sql = @"SELECT 
							sc_id,
							SC_CODICE,
							SC_DESCRIZIONE,
							sc_note,
							sc_pubblica
						FROM alberoproc
						WHERE sc_attivo  = 0
						AND (" + db.Specifics.NvlFunction("sc_pubblica", 0) +
							  @" in (" + FiltroRicercaFlagPubblica.Get(ambito) + ")" +
							  (idNodo > 0 ? " or sc_pubblica is null " : String.Empty) + @")
						AND idcomune     = {0}
						AND software     = {1}
						AND sc_codice like {2}
						and (FINE_VALIDITA is null or {3} between INIZIO_VALIDITA and FINE_VALIDITA)
						and " + db.Specifics.LengthFunction("sc_codice") + "=" + (scCodice.Length + 2) + @"
						order by SC_ORDINE ASC,SC_DESCRIZIONE asc";

			sql = PreparaQueryParametrica(sql, "idComune", "software", "scCodiceLike", "data");

			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
					cmd.Parameters.Add(db.CreateParameter("software", software));
					cmd.Parameters.Add(db.CreateParameter("scCodiceLike", scCodice + "%"));
					cmd.Parameters.Add(db.CreateParameter("data", DateTime.Now));

					var interventiTrovati = new List<InterventoDto>();

					using (var dr = cmd.ExecuteReader())
					{
						while (dr.Read())
						{
							bool pubblicaAreaRiservata = dr["sc_pubblica"].ToString() == "1" ||
														 dr["sc_pubblica"].ToString() == "2";

							var el = new InterventoDto
							{
								Codice = Convert.ToInt32(dr["sc_id"]),
								Descrizione = dr["SC_DESCRIZIONE"].ToString(),
								ScCodice = dr["SC_CODICE"].ToString(),
								HaNote = dr["SC_NOTE"].ToString().Length > 0,
								PubblicaAreaRiservata = pubblicaAreaRiservata
							};

							interventiTrovati.Add(el);
						}
					}

					interventiTrovati.ForEach(x =>
					{
						x.HaNodiFiglio = VerificaEsistenzaNodifiglio(idComune, software, x.ScCodice, ambito);

						if ((!x.HaNodiFiglio || idNodo <= 0) && ambito == AmbitoRicerca.FrontofficePubblico)
						{
							x.PubblicaAreaRiservata = VerificaPubblicazioneNodo(idComune, x.ScCodice, software, AmbitoRicerca.AreaRiservata);
						}

						if (!x.HaNote)
						{
							x.HaNote = VerificaPresenzaEndoCollegati(idComune, x.Codice);
						}

						// Se è attiva la verticalizzazione CART devo mostrare solamente i nodi dell'albero che hanno un collegamento su 
						// STP_ENDO_TIPO2 con STP_ENDO_TIPO2.codiceoggetto != null
						if (verticalizzazioneCartAttiva)
						{
							var riferimentiCart = new StpEndoTipo2Mgr(db).GetByCodiceIntervento(idComune, x.Codice);

							x.HaNote = riferimentiCart.Where(c => c.Codiceoggetto.HasValue).Count() > 0;

							if (riferimentiCart.Count() > 0 && riferimentiCart.First().Tipo == TipoProcedimentoCart.CATEGORIA.ToString())
							{
								x.HaNodiFiglio = true;
							}
						}
					});

					return interventiTrovati;
				}
			}
			finally
			{
				if (closeCnn)
				{
					db.Connection.Close();
				}
			}
		}

		public bool VerificaPubblicazioneNodo(string idComune, string scCodice, string software, AmbitoRicerca ambitoRicerca)
		{
			using (var db2 = new DataBase(db.ConnectionDetails.ConnectionString, db.ConnectionDetails.ProviderType))
			{
				try
				{
					db2.Connection.Open();

					var condizioneScCodice = new StringBuilder();
					var condizionePubblica = FiltroRicercaFlagPubblica.Get(ambitoRicerca);

					condizioneScCodice.Append("'");

					for (int i = 2; i <= scCodice.Length; i = i + 2)
					{
						if (i > 2)
						{
							condizioneScCodice.Append("','");
						}

						condizioneScCodice.Append(scCodice.Substring(0, i));
					}

					condizioneScCodice.Append("'");

					var sql = @"SELECT 
							  Count(*) 
							FROM 
							  alberoproc
							WHERE
							  idcomune = {0} AND 
							  software = {1} AND
							  sc_codice IN (" + condizioneScCodice + @") AND
							  (sc_pubblica IS NULL OR sc_pubblica IN (" + condizionePubblica + "))";

					sql = PreparaQueryParametrica(sql, "idComune", "software");

					using (IDbCommand cmd = db2.CreateCommand(sql))
					{
						cmd.Parameters.Add(db2.CreateParameter("idComune", idComune));
						cmd.Parameters.Add(db2.CreateParameter("software", software));

						var count = cmd.ExecuteScalar();

						if (count == null || count == DBNull.Value)
						{
							return false;
						}

						return Convert.ToInt32(count) == scCodice.Length / 2;
					}
				}
				finally
				{
					db2.Connection.Close();
				}
			}
		}

		private bool VerificaPresenzaEndoCollegati(string idComune, int idIntervento)
		{
			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				var sql = @"select 
							  count(*)
							FROM
							  inventarioprocedimenti,
							  alberoproc_endo
							where
							  inventarioprocedimenti.idcomune = alberoproc_endo.idcomune 
							  AND inventarioprocedimenti.codiceinventario = alberoproc_endo.codiceinventario
							  AND alberoproc_endo.idcomune = {0}
							  AND alberoproc_endo.fkscid = {1}
							  AND inventarioprocedimenti.disabilitato = 0";

				sql = PreparaQueryParametrica(sql, "idComune", "scId");

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
					cmd.Parameters.Add(db.CreateParameter("scId", idIntervento));

					var obj = cmd.ExecuteScalar();

					if (obj == null || obj == DBNull.Value)
					{
						return false;
					}

					return Convert.ToInt32(obj) > 0;
				}
			}
			finally
			{
				if (closeCnn)
				{
					db.Connection.Close();
				}
			}
		}

		private bool VerificaEsistenzaNodifiglio(string idComune, string software, string scCodice, AmbitoRicerca ambito)
		{
			var sql = @"SELECT 
							count(*)
						FROM alberoproc
						WHERE sc_attivo  = 0
						AND " + db.Specifics.NvlFunction("sc_pubblica", -1) +
							  @" in (" + FiltroRicercaFlagPubblica.Get(ambito) + @",-1)
						AND idcomune     = {0}
						AND software     = {1}
						AND sc_codice like {2}
						and " + db.Specifics.LengthFunction("sc_codice") + "=" + (scCodice.Length + 2) + @"
						order by SC_CODICE ASC";

			sql = PreparaQueryParametrica(sql, "idComune", "software", "scCodiceLike");

			using (IDbCommand cmd = db.CreateCommand(sql))
			{
				cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
				cmd.Parameters.Add(db.CreateParameter("software", software));
				cmd.Parameters.Add(db.CreateParameter("scCodiceLike", scCodice + "%"));

				var objCount = cmd.ExecuteScalar();

				if (objCount == null || objCount == DBNull.Value)
				{
					return false;
				}

				return Convert.ToInt32(objCount) > 0;
			}
		}

		public string GetTestoCompletoNote(string idComune, int codiceIntervento)
		{
			const string FORMAT_STRING_PADRE = "<div class='notePadre'>{0}</div>";
			const string FORMAT_STRING_GENERALE = "<div class='nomeNodo'>{0}</div><div class='descrizioneNodo'>{1}</div>";

			var intervento = this.GetById(codiceIntervento, idComune);

			if (intervento == null)
			{
				return String.Empty;
			}

			var partiCodice = new List<string>();

			for (int i = 0; i < intervento.SC_CODICE.Length; i = i + 2)
			{
				partiCodice.Add(intervento.SC_CODICE.Substring(0, i + 2));
			}

			var whereIn = "'" + String.Join("','", partiCodice.ToArray()) + "'";
			
			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				var sql = "select SC_NOTE,SC_ID,SC_DESCRIZIONE from alberoproc where idcomune = {0} and software={1} and SC_CODICE in (" + whereIn + ") order by SC_CODICE asc";

				sql = PreparaQueryParametrica(sql, "idComune", "software");

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
					cmd.Parameters.Add(db.CreateParameter("software", intervento.SOFTWARE));

					using (var dr = cmd.ExecuteReader())
					{
						var sb = new StringBuilder();

						while (dr.Read())
						{
							var note = dr[0].ToString();
							var id = Convert.ToInt32(dr[1]);
							var descrizione = dr[2].ToString();

							if (String.IsNullOrEmpty(note))
							{
								continue;
							}

							var str = String.Format(FORMAT_STRING_GENERALE, descrizione, note);

							sb.AppendFormat(FORMAT_STRING_PADRE, str);
						}

						return sb.ToString();
					}
				}
			}
			finally
			{
				if (closeCnn)
				{
					db.Connection.Close();
				}
			}
		}

		#region ricerca testuale
		public List<BaseDto<int, string>> RicercaTestualeInterventi(string idComune, string software, string matchParziale, int matchCount, string modoRicerca, string tipoRicerca, AmbitoRicerca ambitoRicerca)
		{
			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				using (var cmd = this.GeneraCommandRicercaInterventi(idComune, software, matchParziale, modoRicerca, tipoRicerca, ambitoRicerca))
				{
					using (var dr = cmd.ExecuteReader())
					{
						var listaElementi = dr.Select(x => new
						{
							Id = Convert.ToInt32(dr["sc_id"]),
							ScCodice = dr["sc_codice"].ToString(),
							Descrizione = dr["sc_descrizione"].ToString()
						});

						return listaElementi.Where(x => this.VerificaPubblicazioneNodo(idComune, x.ScCodice, software, ambitoRicerca))
											.Take(matchCount)
											.Select(x => new BaseDto<int, string>(x.Id, x.Descrizione))
											.ToList();
					}
				}
			}
			catch (Exception ex)
			{
				this._log.ErrorFormat("Errore durante la ricerca testuale dell'intervento con matchParziale {0}, modoRicerca {1}, tipoRicerca {2}: {3}", matchParziale, modoRicerca, tipoRicerca, ex.ToString());

				throw;
			}
			finally
			{
				if (closeCnn)
				{
					db.Connection.Close();
				}
			}
		}

		private IDbCommand GeneraCommandRicercaInterventi(string idComune, string software, string matchParziale, string modoRicerca, string tipoRicerca, AmbitoRicerca ambitoRicerca)
		{
			const string MR_TITOLI_E_DESCRIZIONI = "mr2";
			const string TR_FRASE_COMPLETA = "tr1";
			const string TR_TUTTE_LE_PAROLE = "tr2";

			var sb = new StringBuilder(PreparaQueryParametrica("select distinct sc_id,sc_descrizione, sc_codice from alberoproc where idComune={0} and software={1} and ", "idComune", "software"));

			var listaParole = new List<string>();
			var listaParametri = new List<IDbDataParameter>();

			listaParametri.Add(db.CreateParameter("idComune", idComune));
			listaParametri.Add(db.CreateParameter("software", software));

			if (tipoRicerca == TR_FRASE_COMPLETA)
			{
				listaParole.Add(matchParziale);
			}
			else
			{
				listaParole = this.SplitListaParole(matchParziale);
			}

			sb.Append("( sc_pubblica is null or sc_pubblica in (").Append(FiltroRicercaFlagPubblica.Get(ambitoRicerca)).Append(")) and ");

			sb.Append("(");

			for (int i = 0; i < listaParole.Count; i++)
			{
				var parIdx = i * (modoRicerca == MR_TITOLI_E_DESCRIZIONI ? 2 : 1);
				var nomeParametro = "parametro" + parIdx;
				var str = PreparaQueryParametrica(db.Specifics.UCaseFunction("sc_descrizione") + " like {0} ", nomeParametro);

				listaParametri.Add(db.CreateParameter(nomeParametro, "%" + listaParole[i].ToUpper() + "%"));

				if (modoRicerca == MR_TITOLI_E_DESCRIZIONI)
				{
					nomeParametro = "parametro" + (parIdx + 1);
					str += " or ";
					str += PreparaQueryParametrica(db.Specifics.UCaseFunction("sc_note") + " like {0} ", nomeParametro);

					listaParametri.Add(db.CreateParameter(nomeParametro, listaParole[i].ToUpper()));
				}

				sb.Append("(").Append(str).Append(")");

				if (i < (listaParole.Count - 1))
				{
					if (tipoRicerca == TR_TUTTE_LE_PAROLE)
					{
						sb.Append(" and ");
					}
					else
					{
						sb.Append(" or ");
					}
				}
			}

			sb.Append(")").Append(" order by sc_descrizione asc");

			var cmd = db.CreateCommand(sb.ToString());

			listaParametri.ForEach(x => cmd.Parameters.Add(x));

			return cmd;
		}

		private List<string> SplitListaParole(string matchParziale)
		{
			string[] parts = Regex.Split(matchParziale, @"[^a-zA-Z0-9]+");

			var parole = new List<string>();

			for (int i = 0; i < parts.Length; i++)
			{
				if (parts[i].Length > 0)
				{
					parole.Add(parts[i]);
				}
			}

			return parole;
		}

		#endregion

		public List<int> GetListaIdNodiPadre(string idComune, int codiceIntervento)
		{
			var ramoAlbero = this.GetById(codiceIntervento, idComune);

			var listaScId = ramoAlbero.GetListaScCodice();

			var condWhere = listaScId.ToString();
			
			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				var sql = PreparaQueryParametrica("select sc_id from alberoproc where idcomune={0} and software={1} and sc_codice in (" + condWhere + ") order by sc_codice desc", "idComune", "software");

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
					cmd.Parameters.Add(db.CreateParameter("software", ramoAlbero.SOFTWARE));

					using (var dr = cmd.ExecuteReader())
					{
						var codiciIntervento = new List<int>();

						while (dr.Read())
						{
							codiciIntervento.Add(Convert.ToInt32(dr[0]));
						}

						return codiciIntervento;
					}
				}
			}
			catch (Exception ex)
			{
				this._log.ErrorFormat("Errore in GetListaIdNodiPadre con idComune={0} e codiceIntervento={1}: {2}", idComune, codiceIntervento, ex.ToString());

				throw;
			}
			finally
			{
				if (closeCnn)
				{
					db.Connection.Close();
				}
			}
		}


        public bool DataInterventoValida(string idComune, int idIntervento)
        {
            var intervento = this.GetById(idIntervento, idComune);
            var closeCnn = false;

            try
            {
                if (db.Connection.State == ConnectionState.Closed)
                {
                    db.Connection.Open();
                    closeCnn = true;
                }

                var sql = PreparaQueryParametrica( @"select 
                              INIZIO_VALIDITA, 
                              FINE_VALIDITA 
                            from 
                              alberoproc 
                            where 
                              idcomune={0} and 
                              software={1} and 
                              sc_codice in (" + intervento.GetListaScCodice().ToString() + @") and
                              INIZIO_VALIDITA is not null 
                            order by sc_codice desc", "idComune", "software");

                using (IDbCommand cmd = db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
                    cmd.Parameters.Add(db.CreateParameter("software", intervento.SOFTWARE));

                    using(var dr = cmd.ExecuteReader())
                    {
                        if (!dr.Read())
                        {
                            return true;
                        }

                        var di = Convert.ToDateTime(dr["INIZIO_VALIDITA"]);
                        var df = Convert.ToDateTime(dr["FINE_VALIDITA"]);

                        return di <= DateTime.Now && DateTime.Now <= df;
                    }
                }
            }
            finally
            {
                if (closeCnn)
                    db.Connection.Close();
            }

        }


        public bool InterventoSupportaRedirect(string idComune, int idIntervento)
        {

            bool closeCnn = false;

            try
            {
                if (db.Connection.State == ConnectionState.Closed)
                {
                    db.Connection.Open();
                    closeCnn = true;
                }

                var sql = PreparaQueryParametrica("select flag_ar_redirect from alberoproc where idcomune={0} and sc_id={1}", "idComune", "idIntervento");

                using (IDbCommand cmd = db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
                    cmd.Parameters.Add(db.CreateParameter("idIntervento", idIntervento));

                    var obj = cmd.ExecuteScalar();

                    if (obj == null || obj == DBNull.Value)
                    {
                        return false;
                    }

                    return Convert.ToInt32(obj) == 1;
                }
            }
            finally
            {
                if (closeCnn)
                    db.Connection.Close();
            }

        }

    }
}