using System;
using System.Collections;
using Init.SIGePro.Data;
using PersonalLib2.Data;
using System.Collections.Generic;
using System.Data;

namespace Init.SIGePro.Manager 
{ 	///<summary>
	/// Descrizione di riepilogo per CittadinanzaMgr.\n	/// </summary>
	public class ComuniMgr: BaseManager
	{
		public class DatiComuneCompatto
		{
			public string CodiceComune { get; set; }
			public string Comune { get; set; }
			public string SiglaProvincia { get; set; }
			public string Cf { get; set; }
			public string Provincia { get; set; }
		}

		public class DatiProvinciaCompatto
		{
			public string SiglaProvincia { get; set; }
			public string Provincia { get; set; }
		}

		public ComuniMgr( DataBase dataBase ) : base( dataBase ) {}

		#region Metodi per l'accesso di base al DB

		public Comuni GetById( String pCODICECOMUNE )
		{
			Comuni retVal = new Comuni();
			retVal.CODICECOMUNE = pCODICECOMUNE;

			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(retVal,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as Comuni;
			
			return null;
		}

		public Comuni GetByCodiceIstat(string codiceIstat)
		{
			var filtro = new Comuni
			{
				CODICEISTAT = codiceIstat
			};

			return (Comuni)db.GetClass(filtro);
		}

        public Comuni GetByClass(Comuni cls)
        {
            PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(cls, true, false);
            if (mydc.Count != 0)
                return (mydc[0]) as Comuni;

            return null;
        }

		public Comuni GetByComune( String pCOMUNE )
		{
			Comuni retVal = new Comuni();
			retVal.COMUNE = pCOMUNE;

			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(retVal,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as Comuni;
			
			return null;
		}

 

		/// <summary>
		/// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
		/// </summary>
		/// <param name="p_class">Criteri di ricerca</param>
		/// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
		public ArrayList GetList(Comuni p_class)
		{
			return this.GetList(p_class,null);
		}
	
		public ArrayList GetList(Comuni p_class, Comuni p_cmpClass )
		{
			return db.GetClassList(p_class,p_cmpClass,false,false);
		}
		
		

		
		#endregion

		public List<DatiComuneCompatto> FindComuniDaMatchParziale(string matchParziale)
		{
			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				string sql = "select CODICECOMUNE, COMUNE , SIGLAPROVINCIA  from COMUNI where upper( COMUNE ) like {0} order by comune asc";

				sql = PreparaQueryParametrica(sql, "matchComune");

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("matchComune", matchParziale.ToUpper() + "%" ) );

					using (var dr = cmd.ExecuteReader())
					{
						var rVal = new List<DatiComuneCompatto>();

						while (dr.Read())
						{
							var datiComune = new DatiComuneCompatto
							{
								CodiceComune = dr["CODICECOMUNE"].ToString(),
								Comune = dr["COMUNE"].ToString(),
								SiglaProvincia = dr["SIGLAPROVINCIA"].ToString()
							};

							rVal.Add(datiComune);
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

		public List<DatiComuneCompatto> GetListaComuni(string siglaProvincia)
		{
			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				string sql = "select CODICECOMUNE, COMUNE , SIGLAPROVINCIA , CF , PROVINCIA from COMUNI";
				
				if(!String.IsNullOrEmpty(siglaProvincia))
					sql += " where SIGLAPROVINCIA = {0}";

				sql += " order by comune asc";

				if (!String.IsNullOrEmpty(siglaProvincia))
					sql = PreparaQueryParametrica(sql, "provincia");

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					if(!String.IsNullOrEmpty(siglaProvincia))
						cmd.Parameters.Add( db.CreateParameter( "provincia" , siglaProvincia.ToUpper() ));

					using (var dr = cmd.ExecuteReader())
					{
						var rVal = new List<DatiComuneCompatto>();

						while (dr.Read())
						{
							var datiComune = new DatiComuneCompatto
							{
								Cf = dr["CF"].ToString(),
								CodiceComune = dr["CODICECOMUNE"].ToString(),
								Comune =dr["COMUNE"].ToString(),
								Provincia = dr["PROVINCIA"].ToString(),
								SiglaProvincia = dr["SIGLAPROVINCIA"].ToString()
							};

							rVal.Add(datiComune);
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

		public DatiComuneCompatto GetDaticomune(string codiceComune)
		{

			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				string sql = @"select 
								CODICECOMUNE, 
								COMUNE , 
								SIGLAPROVINCIA , 
								CF , 
								PROVINCIA 
							from 
								COMUNI 
							where
								CODICECOMUNE = {0}";

				sql = PreparaQueryParametrica(sql, "codiceComune");

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("codiceComune", codiceComune.ToUpper()));

					using (var dr = cmd.ExecuteReader())
					{
						if (dr.Read())
						{
							return new DatiComuneCompatto
							{
								Cf = dr["CF"].ToString(),
								CodiceComune = dr["CODICECOMUNE"].ToString(),
								Comune = dr["COMUNE"].ToString(),
								Provincia = dr["PROVINCIA"].ToString(),
								SiglaProvincia = dr["SIGLAPROVINCIA"].ToString()
							};
						}

						return null;
					}
				}
			}
			finally
			{
				if (closeCnn)
					db.Connection.Close();
			}

		}

		public DatiProvinciaCompatto GetDatiProvincia(string siglaProvincia)
		{
			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				string sql = @"select 
									SIGLAPROVINCIA , 
									PROVINCIA 
								from 
									COMUNI 
								where
									SIGLAPROVINCIA = {0}";

				sql = PreparaQueryParametrica(sql, "siglaProvincia");

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("siglaProvincia", siglaProvincia.ToUpper()));

					using (var dr = cmd.ExecuteReader())
					{
						if (dr.Read())
						{
							return new DatiProvinciaCompatto
							{
								Provincia = dr["PROVINCIA"].ToString(),
								SiglaProvincia = dr["SIGLAPROVINCIA"].ToString()
							};
						}

						return null;
					}
				}
			}
			finally
			{
				if (closeCnn)
					db.Connection.Close();
			}
		}

		public DatiProvinciaCompatto GetProvinciaDaCodiceComune(string codiceComune)
		{
			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				string sql = @"select 
									SIGLAPROVINCIA , 
									PROVINCIA 
								from 
									COMUNI 
								where
									CODICECOMUNE= {0}";

				sql = PreparaQueryParametrica(sql, "codiceComune");

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("codiceComune", codiceComune.ToUpper()));

					using (var dr = cmd.ExecuteReader())
					{
						if (dr.Read())
						{
							return new DatiProvinciaCompatto
							{
								Provincia = dr["PROVINCIA"].ToString(),
								SiglaProvincia = dr["SIGLAPROVINCIA"].ToString()
							};
						}

						return null;
					}
				}
			}
			finally
			{
				if (closeCnn)
					db.Connection.Close();
			}
		}

		public List<DatiProvinciaCompatto> GetListaProvincie()
		{

			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				string sql = "select distinct SIGLAPROVINCIA , PROVINCIA from COMUNI order by PROVINCIA";

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					using (var dr = cmd.ExecuteReader())
					{
						var rVal = new List<DatiProvinciaCompatto>();

						while (dr.Read())
						{
							var datiProvincia = new DatiProvinciaCompatto
							{
								Provincia = dr["PROVINCIA"].ToString(),
								SiglaProvincia = dr["SIGLAPROVINCIA"].ToString()
							};

							rVal.Add(datiProvincia);
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

		public List<DatiComuneCompatto> GetComuniAssociati(string idComune)
		{

			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				string sql = @"select 
									COMUNI.CODICECOMUNE, 
									COMUNI.COMUNE , 
									COMUNI.SIGLAPROVINCIA , 
									COMUNI.CF , 
									COMUNI.PROVINCIA 
								from 
									COMUNIASSOCIATI,
									COMUNI
								where
									COMUNI.CODICECOMUNE = COMUNIASSOCIATI.CODICECOMUNE and
									COMUNIASSOCIATI.IDCOMUNE={0}
								order by COMUNE ASC";

				sql = PreparaQueryParametrica(sql, "idComune");

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idComune", idComune));

					using (var dr = cmd.ExecuteReader())
					{
						var rVal = new List<DatiComuneCompatto>();

						while (dr.Read())
						{
							var c = new DatiComuneCompatto
							{
								Cf = dr["CF"].ToString(),
								CodiceComune = dr["CODICECOMUNE"].ToString(),
								Comune = dr["COMUNE"].ToString(),
								Provincia = dr["PROVINCIA"].ToString(),
								SiglaProvincia = dr["SIGLAPROVINCIA"].ToString()
							};

							rVal.Add(c);
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

		internal DatiComuneCompatto GetDatiComuneDaSiglaProvincia(string siglaProvincia)
		{
			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				string sql = @"select 
									*
								from 
									COMUNI 
								where
									siglaprovincia={0} and
								comune = provincia";

				sql = PreparaQueryParametrica(sql, "siglaProvincia");

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("siglaProvincia", siglaProvincia));

					using (var dr = cmd.ExecuteReader())
					{
						if(dr.Read())
						{
							return new DatiComuneCompatto
							{
								Cf = dr["CF"].ToString(),
								CodiceComune = dr["CODICECOMUNE"].ToString(),
								Comune = dr["COMUNE"].ToString(),
								Provincia = dr["PROVINCIA"].ToString(),
								SiglaProvincia = dr["SIGLAPROVINCIA"].ToString()
							};
						}

						return null;
					}
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

