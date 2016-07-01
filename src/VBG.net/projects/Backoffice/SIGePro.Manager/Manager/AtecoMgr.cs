
using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using System.Data;
using System.ComponentModel;
using Init.SIGePro.Authentication;

using PersonalLib2.Sql;
using Init.Utils.Sorting;
using PersonalLib2.Data;
using System.Text.RegularExpressions;
using Init.SIGePro.Manager.DTO.Interventi;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class AtecoMgr
    {
		public Ateco GetById(int id)
		{
			Ateco c = new Ateco();

			c.Id = id;

			return (Ateco)db.GetClass(c);
		}

		/// <summary>
		/// Restituisce i nodi figlio del nodo con l'id padre passato
		/// Attenzione! Vengono restituite solamente le colonne id e titolo.
		/// Se si vuole ottenere una lista con tutti i dati popolati utilizzare
		/// il metodo GetList
		/// </summary>
		/// <param name="idPadre"></param>
		/// <returns></returns>
		public List<Ateco> GetNodiFiglioByIdPadre(int? idPadre)
		{

			bool closeCnn = false;
			var auxDb = new DataBase(db.ConnectionDetails.ConnectionString, db.ConnectionDetails.ProviderType);
			var auxMgr = new AtecoMgr(auxDb);

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				var sql = "select id,titolo,descrizione,codice,codicebreve from ateco where ";

				if (!idPadre.HasValue)
					sql += "fkidpadre is null";
				else
					sql += PreparaQueryParametrica("fkidpadre = {0}","idPadre");

				sql += " order by codice asc"; 

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					if (idPadre.HasValue)
						cmd.Parameters.Add(db.CreateParameter("idPadre", idPadre));

					using (var dr = cmd.ExecuteReader())
					{
						var rVal = new List<Ateco>();

						while (dr.Read())
						{
							var el = new Ateco
							{
								Id = Convert.ToInt32( dr["id"] ),
								Titolo = dr["titolo"].ToString(),
								Codice = dr["codice"].ToString(),
								Codicebreve = dr["codicebreve"].ToString(),
								HasDescription = dr["descrizione"] != DBNull.Value
							};

							el.HasChilds = auxMgr.CountChildNodes(el.Id.Value) > 0;

							rVal.Add(el);
						}

						return rVal;
					}
				}
			}
			finally
			{
				if (closeCnn)
					db.Connection.Close();
			}

		}

		public List<Ateco> RicercaAteco(string matchParziale, int matchCount, string modoRicerca, string tipoRicerca)
		{

			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				using (IDbCommand cmd = GeneraCommandRicercaAteco( matchParziale , modoRicerca , tipoRicerca) )
				{
					using (var dr = cmd.ExecuteReader())
					{
						var rVal = new List<Ateco>();

						while (dr.Read() && rVal.Count < matchCount)
						{
							var el = new Ateco
							{
								Id = Convert.ToInt32( dr["Id"] ),
								Titolo = dr["Titolo"].ToString(),
								Codicebreve = dr["Codicebreve"].ToString()
							};

							rVal.Add(el);
						}

						return rVal;
					}

				}
			}
			finally
			{
				if (closeCnn)
					db.Connection.Close();
			}

		}

		private IDbCommand GeneraCommandRicercaAteco(string matchParziale, string modoRicerca, string tipoRicerca)
		{
			const string MR_TITOLI = "mr1";
			const string MR_TITOLI_E_DESCRIZIONI = "mr2";
			const string MR_CODICE_ATECO = "mr3";

			const string TR_FRASE_COMPLETA = "tr1";
			const string TR_TUTTE_LE_PAROLE = "tr2";
			const string TR_ALMENO_UNA_PAROLA = "tr3";


			if (modoRicerca == MR_CODICE_ATECO)
				return GeneraCommandRicercaCodiceAteco(matchParziale);

			var sb = new StringBuilder("select id,Titolo,Codicebreve from ateco where ");

			var listaParole = new List<string>();
			var listaParametri = new List<IDbDataParameter>();

			if (tipoRicerca == TR_FRASE_COMPLETA)
				listaParole.Add(matchParziale);
			else
				listaParole = SplitListaParole(matchParziale);

			sb.Append("(");

			for (int i = 0; i < listaParole.Count; i++)
			{
				var parIdx = i * ( modoRicerca == MR_TITOLI_E_DESCRIZIONI ? 2 : 1 );
				var nomeParametro = "parametro" + parIdx;
				var str = PreparaQueryParametrica(db.Specifics.UCaseFunction("Titolo") + " like {0} ", nomeParametro);

				listaParametri.Add(db.CreateParameter(nomeParametro, "%" + listaParole[i].ToUpper() + "%"));

				if (modoRicerca == MR_TITOLI_E_DESCRIZIONI)
				{
					nomeParametro = "parametro" + (parIdx+1);
					str += " or ";
					str += db.Specifics.ClobLike("descrizione", nomeParametro, true);

					listaParametri.Add(db.CreateParameter(nomeParametro, listaParole[i].ToUpper()));
				}

				sb.Append("(").Append(str).Append(")");

				if (i < (listaParole.Count - 1))
				{
					if (tipoRicerca == TR_TUTTE_LE_PAROLE)
						sb.Append(" and ");
					else
						sb.Append(" or ");
				}
			}

			sb.Append(")").Append(" order by codicebreve,titolo asc");

			var cmd = db.CreateCommand( sb.ToString());

			listaParametri.ForEach( x => cmd.Parameters.Add( x ));

			return cmd;
		}

		private List<string> SplitListaParole(string matchParziale)
		{
			string[] parts = Regex.Split(matchParziale, @"[^a-zA-Z0-9]+");

			var rVal = new List<string>();

			for (int i = 0; i < parts.Length; i++)
			{
				if (parts[i].Length > 0)
					rVal.Add(parts[i]);
			}

			return rVal;
		}

		private IDbCommand GeneraCommandRicercaCodiceAteco(string matchParziale)
		{
			var sql = "select id,Titolo,Codicebreve from ateco where codicebreve like {0} order by codicebreve,titolo asc";
			sql = PreparaQueryParametrica(sql, "codiceBreve");


			var cmd = db.CreateCommand(sql);

			cmd.Parameters.Add(db.CreateParameter("codiceBreve", matchParziale + "%"));

			return cmd;

		}




		public List<int> CaricaListaIdGerarchia(int id)
		{

			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				var sql = PreparaQueryParametrica("select fkidpadre from ateco where id={0}", "id");

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("id", id));

					var rVal = new List<int>();
					rVal.Add(id);

					object obj = cmd.ExecuteScalar();

					while (obj != null && obj != DBNull.Value)
					{
						int tmpId = Convert.ToInt32(obj);
						rVal.Add(tmpId);

						((IDbDataParameter)cmd.Parameters[0]).Value = tmpId;

						obj = cmd.ExecuteScalar();
					}

					return rVal;
				}
			}
			finally
			{
				if (closeCnn)
					db.Connection.Close();
			}

		}

		public int CercaNodiConLink(string idComune, string software, int id, AmbitoRicerca ambitoRicerca)
		{

			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				var count = ContaNodiAlberoproc(idComune, software, id, ambitoRicerca);

				if (count > 0)
					return id;

				var sql = PreparaQueryParametrica("select fkidpadre from ateco where id={0}", "id");

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("id", id));

					object obj = cmd.ExecuteScalar();

					while (obj != null && obj != DBNull.Value)
					{
						int tmpId = Convert.ToInt32(obj);

						count = ContaNodiAlberoproc(idComune, software, tmpId,ambitoRicerca);

						if (count > 0)
							return tmpId;

						((IDbDataParameter)cmd.Parameters[0]).Value = tmpId;

						obj = cmd.ExecuteScalar();
					}

					return -1;
				}
			}
			finally
			{
				if (closeCnn)
					db.Connection.Close();
			}

		}

		private int ContaNodiAlberoproc(string idComune, string software, int idAteco, AmbitoRicerca ambitoRicerca)
		{

			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				var sql = @"SELECT count(alberoproc.sc_id)
							FROM alberoproc,
							  alberoproc_ateco
							WHERE alberoproc.idcomune = alberoproc_ateco.idcomune
							AND alberoproc.sc_id      = alberoproc_ateco.fk_scid
							and " + db.Specifics.NvlFunction("sc_pubblica", 0) +
								  @" in (" + FiltroRicercaFlagPubblica.Get(ambitoRicerca) + @")
							and alberoproc_ateco.idcomune = {0}
							and alberoproc_ateco.fk_idateco = {1} and alberoproc.software = {2}";

				sql = PreparaQueryParametrica(sql, "idComune","idAteco" , "software");

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
					cmd.Parameters.Add(db.CreateParameter("idAteco", idAteco));
					cmd.Parameters.Add(db.CreateParameter("software", software));

					var objCount = cmd.ExecuteScalar();

					return objCount == null || objCount == DBNull.Value ? 0 : Convert.ToInt32(objCount);
				}
			}
			finally
			{
				if (closeCnn)
					db.Connection.Close();
			}

		}

		public List<AlberoProc> GetListaAlberoProcDaIdAteco(string idComune, int id, AmbitoRicerca ambitoRicerca)
		{
			return GetListaAlberoProcDaIdAteco(idComune, id, String.Empty,ambitoRicerca);
		}

		public List<AlberoProc> GetListaAlberoProcDaIdAteco(string idComune, int idAteco, string software, AmbitoRicerca ambitoRicerca)
		{
			// Carico la gerarchia dei nodi ATECO
			var nodoConLink = CercaNodiConLink(idComune, software, idAteco,ambitoRicerca);

			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				// Leggo gli interventi collegati tramite alberoproc_ateco
				var sql = @"SELECT alberoproc.*
							FROM alberoproc,
							  alberoproc_ateco
							WHERE alberoproc.idcomune = alberoproc_ateco.idcomune
							AND alberoproc.sc_id      = alberoproc_ateco.fk_scid
							and " + db.Specifics.NvlFunction("sc_pubblica", 0) + 
								@" in (" + FiltroRicercaFlagPubblica.Get(ambitoRicerca ) + @")
							and alberoproc_ateco.idcomune = {0}
							and alberoproc_ateco.fk_idateco = {1}";

				if (!String.IsNullOrEmpty(software))
					sql += " and alberoproc.software = '" + software + "'";

				sql = PreparaQueryParametrica(sql, "idComune","idAteco");

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add( db.CreateParameter( "idComune", idComune));
					cmd.Parameters.Add(db.CreateParameter("idAteco", nodoConLink));

					var listaInterventi = db.GetClassList<AlberoProc>(cmd);

					var rVal = new List<AlberoProc>();
					var alberoProcMgr = new AlberoProcMgr(db);

					listaInterventi.ForEach(x =>
					{
						var tmpLst = alberoProcMgr.GetAlberaturaCompletaDaScCodice( x.Idcomune , x.SOFTWARE , x.SC_CODICE, ambitoRicerca);

						tmpLst.ForEach( y =>{ 
							if( rVal.Find( intervento => intervento.Sc_id == y.Sc_id ) == null )
								rVal.Add( y );
						});
					});

					return rVal;
				}
			}
			finally
			{
				if (closeCnn)
					db.Connection.Close();
			}

		}

		public List<InterventoDto> GetListaInterventiRootDaIdAteco(string idComune, int idInterventoPadre, int idAteco, string software, AmbitoRicerca ambitoRicerca)
		{
			var alberoProcMgr = new AlberoProcMgr(db);

			var nodoPadre = alberoProcMgr.GetById(idInterventoPadre, idComune);

			var scIdPadre = nodoPadre == null ? String.Empty : nodoPadre.SC_CODICE;

			var lst = GetListaAlberoProcDaIdAteco(idComune, idAteco, software,ambitoRicerca);
			
			var rVal = new List<InterventoDto>();

			if (lst.Count == 0)
				return rVal;

			



			var queryResult = from AlberoProc ap in lst
							   where ap.SC_CODICE.StartsWith(scIdPadre ) && ap.SC_CODICE.Length == (scIdPadre.Length + 2)
							   select ap;

			foreach (var item in queryResult)
			{
				var el = new InterventoDto
				{
					Codice = item.Sc_id.Value,
					Descrizione = item.SC_DESCRIZIONE,
					ScCodice = item.SC_CODICE,
					HaNote = !String.IsNullOrEmpty( item.SC_NOTE ),
					PubblicaAreaRiservata = item.SC_PUBBLICA == "1" || item.SC_PUBBLICA == "2"
				};

				var childCount = (from AlberoProc ap in lst
								  where ap.SC_CODICE.StartsWith(el.ScCodice) && ap.SC_CODICE.Length == (el.ScCodice.Length + 2)
								 select ap).Count();

				el.HaNodiFiglio = childCount > 0;

				if ((!el.HaNodiFiglio || el.Codice <= 0) && ambitoRicerca == AmbitoRicerca.FrontofficePubblico)
					el.PubblicaAreaRiservata = alberoProcMgr.VerificaPubblicazioneNodo(idComune, el.ScCodice, software , ambitoRicerca);

				rVal.Add(el);
			}

			return rVal;
		}

		private int CountChildNodes( int idNodo)
		{

			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				var sql = PreparaQueryParametrica("select count(*) from ateco where fkidpadre = {0}","idPadre");

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idPadre", idNodo));

					var objCount = cmd.ExecuteScalar();

					if (objCount == DBNull.Value)
						return 0;

					return Convert.ToInt32( objCount);
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
				