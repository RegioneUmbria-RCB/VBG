using System;
using System.Linq;
using System.Collections;
using Init.SIGePro.Data;
using PersonalLib2.Data;
using System.Collections.Generic;
using System.Data;
using System.ComponentModel;
using Init.SIGePro.Authentication;
using System.Text;
using Init.SIGePro.Manager.DTO;
using Init.SIGePro.Manager.DTO.Oneri;
using Init.SIGePro.Manager.DTO.DatiDinamici;
using Init.SIGePro.Manager.Utils.Extensions;
using log4net;

namespace Init.SIGePro.Manager
{
	///<summary>
	/// Descrizione di riepilogo per InventarioProcedimentiMgr.\n	
	/// </summary>
	[DataObject]
	public partial class InventarioProcedimentiMgr
	{
		ILog _log = LogManager.GetLogger(typeof(InventarioProcedimentiMgr));

		public List<InventarioProcedimenti> GetListByTipo(string idComune, string software, string tipoFamiglia, string tipoEndo)
		{
			string sql = @"
SELECT  inventarioProcedimenti.*
FROM    inventarioProcedimenti,
        tipiendo
WHERE   tipiendo.idcomune                = inventarioProcedimenti.idcomune
    AND tipiendo.codice                  = inventarioProcedimenti.codiceTipo
    AND inventarioProcedimenti.idcomune  = {0}
    AND (inventarioProcedimenti.software = {1}
     OR inventarioProcedimenti.software  = 'TT')
    AND tipiendo.codice LIKE {2}";

			if (!String.IsNullOrEmpty(tipoFamiglia))
				sql +=" AND tipiendo.codicefamigliaendo = " + db.Specifics.QueryParameterName("codiceFamiglia");

			sql = String.Format(sql, db.Specifics.QueryParameterName("idComune"), 
									 db.Specifics.QueryParameterName("software"), 
									 db.Specifics.QueryParameterName("tipoEndo") );


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
					cmd.Parameters.Add(db.CreateParameter("tipoEndo", String.IsNullOrEmpty(tipoEndo ) ? "%" : tipoEndo));

					if (!String.IsNullOrEmpty(tipoFamiglia))
						cmd.Parameters.Add(db.CreateParameter("codiceFamiglia", tipoFamiglia));

					return db.GetClassList(cmd, new InventarioProcedimenti(), false, true).ToList<InventarioProcedimenti>();
				}
			}
			finally
			{
				if (closeCnn)
					db.Connection.Close();
			}


		}

		[DataObjectMethod(DataObjectMethodType.Select)]
		public static List<InventarioProcedimenti> FindProcedimentiDomandaFront(string token, int codiceDomandaFront)
		{
			AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

			DataBase db = authInfo.CreateDatabase();

			string sql = @"SELECT 
							  inventarioprocedimenti.*
							FROM 
							  inventarioprocedimenti,
							  domandefront_endo
							WHERE
							  inventarioprocedimenti.idcomune = domandefront_endo.idcomune AND
							  inventarioprocedimenti.codiceinventario = domandefront_endo.codiceinventario AND
							  domandefront_endo.idcomune = {0} AND
							  domandefront_endo.codiceDomanda = {1}
							ORDER BY 
								ORDINE ASC, PROCEDIMENTO asc";

			sql = string.Format( sql , db.Specifics.QueryParameterName("IdComune"),
										db.Specifics.QueryParameterName("codiceDomanda") );
			try
			{
				db.Connection.Open();

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add( db.CreateParameter( "IdComune" , authInfo.IdComune ) );
					cmd.Parameters.Add( db.CreateParameter( "codiceDomanda" , codiceDomandaFront ) );

					return db.GetClassList( cmd , new InventarioProcedimenti() , false , true ).ToList<InventarioProcedimenti>();
				}
			}
			finally
			{
				db.Connection.Close();
			}
		}

		public List<InventarioProcedimenti> GetEndoprocedimentiList(string idComune, List<int> listaCodici)
		{
			if (listaCodici.Count == 0)
				return new List<InventarioProcedimenti>();

			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				var sb = new StringBuilder();

				listaCodici.ForEach( x => 
				{ 
					if(sb.Length > 0) 
						sb.Append(",");
					sb.Append( x );
				});

				string sql = PreparaQueryParametrica(@"SELECT * FROM inventarioprocedimenti where idcomune={0} ", "idComune");

				sql += " and codiceinventario in (" + sb.ToString() + ") order by procedimento asc";

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idComune", idComune));

					return db.GetClassList<InventarioProcedimenti>(cmd);
				}
			}
			finally
			{
				if (closeCnn)
					db.Connection.Close();
			}

		
		}

		public List<SchedaDinamicaEndoprocedimentoDto> GetSchedeDinamicheDaEndoprocedimentiList(string idComune, List<int> listaCodiciEndo, IEnumerable<string> listaTipiLocalizzazioni, bool ignoraTipiLocalizzazioni)
		{
			if (listaCodiciEndo.Count == 0)
				return new List<SchedaDinamicaEndoprocedimentoDto>();


			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				var sb = new StringBuilder();

				listaCodiciEndo.ForEach(x =>
				{
					if (sb.Length > 0)
						sb.Append(",");
					sb.Append(x);
				});

				string sql = PreparaQueryParametrica(@"SELECT 
															inventarioprocdyn2modellit.codiceinventario,
															dyn2_modellit.id,
															dyn2_modellit.descrizione,
															flag_tipofirma,
															flag_facoltativa
														FROM
															inventarioprocdyn2modellit
																JOIN dyn2_modellit ON
																	dyn2_modellit.idcomune = inventarioprocdyn2modellit.idcomune AND
																	dyn2_modellit.id = inventarioprocdyn2modellit.fk_d2mt_id
																left JOIN tipi_localizzazioni ON
																	tipi_localizzazioni.idcomune = inventarioprocdyn2modellit.idcomune AND
																	tipi_localizzazioni.id = inventarioprocdyn2modellit.fk_tipilocalizzazione_id 
														WHERE
														  inventarioprocdyn2modellit.idcomune = {0}  and 
														  inventarioprocdyn2modellit.FLAG_PUBBLICA = 1", "idComune");

				var filtroLocalizzazioni = String.Empty;

				if (!ignoraTipiLocalizzazioni)
				{
					var sbTipiLocalizzazioni = new StringBuilder(" and (tipi_localizzazioni.descrizione is null ");

					if (listaTipiLocalizzazioni.Count() > 0)
					{

						sbTipiLocalizzazioni.
							Append(" or ").
							Append(db.Specifics.UCaseFunction("tipi_localizzazioni.descrizione")).
							Append(" in ('").
							Append(String.Join("','", listaTipiLocalizzazioni.Select(x => x.ToUpper().Replace("'", "\\'")))).
							Append("')");

					}

					sbTipiLocalizzazioni.Append(")");

					filtroLocalizzazioni = sbTipiLocalizzazioni.ToString();
				}
				sql += filtroLocalizzazioni;
				sql += " and inventarioprocdyn2modellit.codiceinventario IN (" + sb.ToString() + ") order by inventarioprocdyn2modellit.ordine, dyn2_modellit.descrizione asc";


				var rVal = new List<SchedaDinamicaEndoprocedimentoDto>();

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idComune", idComune));

					using (var dr = cmd.ExecuteReader())
					{
						while (dr.Read())
						{
							var codInventario = Convert.ToInt32(dr["codiceinventario"]);
							var codModello = Convert.ToInt32(dr["id"]);
							var desModello = dr["descrizione"].ToString();
							var tipoFirma = TipoFirmaEnum.NessunaFirma;
							var objTipoFirma = dr["flag_tipofirma"];
							var facoltativa = dr["flag_facoltativa"].ToString() == String.Empty ? false : dr["flag_facoltativa"].ToString() == "1";

							if (objTipoFirma != DBNull.Value)
								tipoFirma = (TipoFirmaEnum)Convert.ToInt32(objTipoFirma);

							var scheda = new SchedaDinamicaEndoprocedimentoDto
							{
								CodiceEndo = codInventario,
								Codice = codModello,
								Descrizione = desModello,
								TipoFirma = tipoFirma,
								Facoltativa = facoltativa
							};

							rVal.Add(scheda);
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

		public List<BaseDto<int,string>> GetEndoprocedimentiPropostiDaCodiceIntervento(string idComune, int codiceIntervento)
		{

			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				string sql = @"SELECT 
								inventarioprocedimenti.codiceinventario,
								inventarioProcedimenti.procedimento
							FROM 
								alberoproc_endo,
								inventarioprocedimenti 
							WHERE 
								inventarioprocedimenti.idcomune = alberoproc_endo.idcomune AND 
								inventarioprocedimenti.codiceinventario = alberoproc_endo.codiceinventario AND  
								alberoproc_endo.idComune={0} AND
								alberoproc_endo.fkscid={1} AND 
								flag_richiesto = 1 and
								inventarioprocedimenti.disabilitato = 0";

				sql = PreparaQueryParametrica(sql, "IdComune", "CodiceIntervento");

				using (IDbCommand cmd = db.CreateCommand(sql))
				{

					cmd.Parameters.Add(db.CreateParameter("IdComune", idComune));
					cmd.Parameters.Add(db.CreateParameter("CodiceIntervento", codiceIntervento));

					using (var dr = cmd.ExecuteReader())
					{
						var rVal = new List<BaseDto<int, string>>();

						while (dr.Read())
						{
							var el = new BaseDto<int, string>
							{
								Codice = Convert.ToInt32(dr["codiceinventario"]),
								Descrizione = dr["procedimento"].ToString()
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

		public List<OnereDto> GetOneriDaCodiceEndo(string idComune, int codiceEndo)
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
								tipicausalioneri.co_id as codice,
								tipicausalioneri.co_descrizione as descrizione,
								inventarioprocedimentioneri.importo as importo,
								inventarioprocedimenti.codiceinventario as codiceprocedimento,
								inventarioprocedimenti.procedimento as procedimento,
								inventarioprocedimentioneri.note as note
							from 
								inventarioprocedimenti,
								inventarioprocedimentioneri,
								tipicausalioneri
							where
								inventarioprocedimenti.idcomune = inventarioprocedimentioneri.idcomune and
								inventarioprocedimenti.codiceinventario = inventarioprocedimentioneri.codiceinventario and
								tipicausalioneri.idcomune = inventarioprocedimentioneri.idcomune and
								tipicausalioneri.co_id = inventarioprocedimentioneri.fk_coid and
								inventarioprocedimentioneri.idcomune = {0} and
								inventarioprocedimentioneri.codiceinventario = {1}";

				sql = PreparaQueryParametrica(sql, "idComune", "codiceinventario");

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add( db.CreateParameter( "idComune", idComune ));
					cmd.Parameters.Add( db.CreateParameter( "codiceinventario", codiceEndo ));

					using (var dr = cmd.ExecuteReader())
					{
						var rVal = new List<OnereDto>();

						while (dr.Read())
						{
							var el = new OnereDto
							{
								Codice = Convert.ToInt32( dr["Codice"] ),
								Descrizione = dr["descrizione"].ToString(),
								Importo = dr["importo"] == DBNull.Value ? 0.0f : Convert.ToSingle(dr["importo"]),
								OrigineOnere = "E",
								CodiceInterventoOEndoOrigine = Convert.ToInt32(dr["codiceprocedimento"] ),
								InterventoOEndoOrigine = dr["procedimento"].ToString(),
								Note = dr["note"].ToString()
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

		public List<BaseDto<int, string>> GetFamiglieEndoFrontoffice(string idComune, string software)
		{
			return GetInfoEndoPerFrontoffice(idComune, software, "tipifamiglieendo.codice,tipifamiglieendo.tipo", "tipifamiglieendo.ordine");
		}

		public List<BaseDto<int, string>> GetCategorieEndoFrontoffice(string idComune, string software, int codiceFamiglia)
		{
			var where = codiceFamiglia == -1 ? "tipiendo.codicefamigliaendo is null" : "tipiendo.codicefamigliaendo=" + codiceFamiglia;

			return GetInfoEndoPerFrontoffice(idComune, software, "tipiendo.codice,tipiendo.tipo", "tipiendo.ordine", where);
		}

		public List<BaseDto<int, string>> GetEndoFrontoffice(string idComune, string software, int codiceCategoria)
		{
			var where = codiceCategoria == -1 ? "inventarioprocedimenti.codicetipo is null" : "inventarioprocedimenti.codicetipo=" + codiceCategoria;

			return GetInfoEndoPerFrontoffice(idComune, software, "inventarioprocedimenti.codiceinventario,inventarioprocedimenti.procedimento", "inventarioprocedimenti.ordine", where);
		}

		private List<BaseDto<int, string>> GetInfoEndoPerFrontoffice(string idComune, string software, string campiSelect,string campoOrderBy, string condizioneWhere  = "")
		{
			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				var sql = "select distinct " + campiSelect + ", " + campoOrderBy + " " +
@"FROM 
    inventarioprocedimenti 
        left JOIN tipiendo ON
            inventarioprocedimenti.idcomune = tipiendo.idcomune AND
            inventarioprocedimenti.codicetipo = tipiendo.codice 
        left JOIN tipifamiglieendo ON
            tipiendo.idcomune = tipifamiglieendo.idcomune AND
            tipiendo.codicefamigliaendo = tipifamiglieendo.codice
WHERE
	inventarioprocedimenti.idcomune = {0} and
	inventarioprocedimenti.disabilitato = 0 and
	(inventarioprocedimenti.software='TT' or inventarioprocedimenti.software={1}) " + (String.IsNullOrEmpty(condizioneWhere) ? "" : "and " + condizioneWhere) + @"
order by " + campoOrderBy;

				sql = PreparaQueryParametrica(sql, "idcomune", "software");

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idcomune", idComune));
					cmd.Parameters.Add(db.CreateParameter("software", software));

					using (var dr = cmd.ExecuteReader())
					{
						var rVal = new List<BaseDto<int, string>>();

						while (dr.Read())
						{
							var codice = dr[0].ToString();
							var descrizione = dr[1].ToString();

							if (String.IsNullOrEmpty(codice))
								codice = "-1";

							if (String.IsNullOrEmpty(descrizione))
								descrizione = "Endoprocedimento";

							rVal.Add(new BaseDto<int, string> { 
								Codice = Convert.ToInt32(codice),
								Descrizione = descrizione
							});
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

		public enum TipoRicercaEnum
		{
			FraseCompleta,
			AlmenoUnaParola,
			TutteLeParole
		}

		internal class TerminiRicerca
		{
			public readonly string[] Parti;
			private readonly string Separatore;
			public readonly string PrefissoParametro;

			public TerminiRicerca(TipoRicercaEnum tipoRicerca, string partial, string prefissoParametro)
			{
				var dict = new Dictionary<TipoRicercaEnum, string>{
				{ TipoRicercaEnum.AlmenoUnaParola, " OR " },
				{ TipoRicercaEnum.TutteLeParole, " AND " },
				{ TipoRicercaEnum.FraseCompleta, "" }
			};

				this.Parti = partial.Split(' ').Where(x => x.Length > 0).ToArray();
				this.Separatore = dict[tipoRicerca];
				this.PrefissoParametro = prefissoParametro;

				if (tipoRicerca == TipoRicercaEnum.FraseCompleta)
					this.Parti = new string[] { partial };
			}

			internal string ToSql(string campoRicerca, Func<string,string> paramTransform)
			{
				var sb = new StringBuilder();

				sb.Append(" AND (");

				sb.Append( String.Join( this.Separatore , this.Parti
																.Select( (valoreParametro,indice) => String.Format( "{0} like {1}" , campoRicerca , paramTransform( this.PrefissoParametro + indice )))
																.ToArray()));
				sb.Append(")");

				return sb.ToString();
			}
		}

		internal IEnumerable<BaseDto<string, string>> RicercaTestualeFamiglie(string idComune, string software, TerminiRicerca termini)
		{
			var sql = PreparaQueryParametrica("SELECT codice,tipo as descrizione FROM tipifamiglieendo WHERE idcomune={0} AND software={1}", "idComune", "software");

			return RicercaTestuale(sql, idComune, software,db.Specifics.UCaseFunction("tipo"), termini); 
		}

		internal IEnumerable<BaseDto<string, string>> RicercaTestualeCategorie(string idComune, string software, TerminiRicerca termini)
		{
			var sql = PreparaQueryParametrica("SELECT codice,tipo as descrizione FROM tipiendo WHERE idcomune={0} AND software={1}", "idComune", "software");

			return RicercaTestuale(sql, idComune, software, db.Specifics.UCaseFunction("tipo"), termini); 
		}

		internal IEnumerable<BaseDto<string, string>> RicercaTestualeEndo(string idComune, string software, TerminiRicerca termini)
		{
			var sql = PreparaQueryParametrica("SELECT codiceinventario as codice,procedimento as descrizione FROM inventarioprocedimenti WHERE idcomune={0} AND software={1} and disabilitato=0 ", "idComune", "software");

			return RicercaTestuale(sql, idComune, software, db.Specifics.UCaseFunction("procedimento"), termini); 
		}

		private IEnumerable<BaseDto<string, string>> RicercaTestuale(string sqlParziale, string idComune, string software, string campoRicerca, TerminiRicerca terminiRicerca)
		{
			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				var sql = sqlParziale + terminiRicerca.ToSql(campoRicerca, x => db.Specifics.QueryParameterName( x ) );


				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
					cmd.Parameters.Add(db.CreateParameter("software", software));

					for (int i = 0; i < terminiRicerca.Parti.Length; i++)
						cmd.Parameters.Add(db.CreateParameter(terminiRicerca.PrefissoParametro + i, "%" + terminiRicerca.Parti[i].ToUpperInvariant() + "%"));

					return cmd.SelectAll(dr => new BaseDto<string, string>(dr["codice"].ToString(), dr["descrizione"].ToString()));
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



