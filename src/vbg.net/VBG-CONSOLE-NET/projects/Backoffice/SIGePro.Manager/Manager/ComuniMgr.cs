using Init.SIGePro.Data;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Data;

namespace Init.SIGePro.Manager
{   ///<summary>
    /// Descrizione di riepilogo per CittadinanzaMgr.\n	/// </summary>
    public class ComuniMgr : BaseManager
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

        public ComuniMgr(DataBase dataBase) : base(dataBase) { }

        #region Metodi per l'accesso di base al DB

        public Comuni GetById(String pCODICECOMUNE)
        {
            Comuni retVal = new Comuni();
            retVal.CODICECOMUNE = pCODICECOMUNE;

            List<Comuni> mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return (mydc[0]) as Comuni;

            return null;
        }

        public Comuni GetByCodiceIstat(string codiceIstat)
        {
            Comuni filtro = new Comuni
            {
                CODICEISTAT = codiceIstat
            };

            return (Comuni)this.db.GetClass(filtro);
        }

        public Comuni GetByClass(Comuni cls)
        {
            List<Comuni> mydc = this.db.GetClassList(cls, true);
            if (mydc.Count != 0)
                return (mydc[0]) as Comuni;

            return null;
        }

        public Comuni GetByComune(String pCOMUNE)
        {
            Comuni retVal = new Comuni();
            retVal.COMUNE = pCOMUNE;

            List<Comuni> mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return (mydc[0]) as Comuni;

            return null;
        }



        /// <summary>
        /// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
        /// </summary>
        /// <param name="p_class">Criteri di ricerca</param>
        /// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
        public List<Comuni> GetList(Comuni p_class)
        {
            return this.GetList(p_class, null);
        }

        public List<Comuni> GetList(Comuni p_class, Comuni p_cmpClass)
        {
            return this.db.GetClassList(p_class, p_cmpClass, false);
        }




        #endregion

        public List<DatiComuneCompatto> FindComuniDaMatchParziale(string matchParziale)
        {
            bool closeCnn = false;

            try
            {
                if (this.db.Connection.State == ConnectionState.Closed)
                {
                    this.db.Connection.Open();
                    closeCnn = true;
                }

                string sql = "select CODICECOMUNE, COMUNE , SIGLAPROVINCIA  from COMUNI where upper( COMUNE ) like {0} order by comune asc";

                sql = this.PreparaQueryParametrica(sql, "matchComune");

                using (IDbCommand cmd = this.db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this.db.CreateParameter("matchComune", matchParziale.ToUpper() + "%"));

                    using (IDataReader dr = cmd.ExecuteReader())
                    {
                        List<DatiComuneCompatto> rVal = new List<DatiComuneCompatto>();

                        while (dr.Read())
                        {
                            DatiComuneCompatto datiComune = new DatiComuneCompatto
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
                    this.db.Connection.Close();
            }
        }

        public List<DatiComuneCompatto> GetListaComuni(string siglaProvincia)
        {
            bool closeCnn = false;

            try
            {
                if (this.db.Connection.State == ConnectionState.Closed)
                {
                    this.db.Connection.Open();
                    closeCnn = true;
                }

                string sql = "select CODICECOMUNE, COMUNE , SIGLAPROVINCIA , CF , PROVINCIA from COMUNI";

                if (!String.IsNullOrEmpty(siglaProvincia))
                    sql += " where SIGLAPROVINCIA = {0}";

                sql += " order by comune asc";

                if (!String.IsNullOrEmpty(siglaProvincia))
                    sql = this.PreparaQueryParametrica(sql, "provincia");

                using (IDbCommand cmd = this.db.CreateCommand(sql))
                {
                    if (!String.IsNullOrEmpty(siglaProvincia))
                        cmd.Parameters.Add(this.db.CreateParameter("provincia", siglaProvincia.ToUpper()));

                    using (IDataReader dr = cmd.ExecuteReader())
                    {
                        List<DatiComuneCompatto> rVal = new List<DatiComuneCompatto>();

                        while (dr.Read())
                        {
                            DatiComuneCompatto datiComune = new DatiComuneCompatto
                            {
                                Cf = dr["CF"].ToString(),
                                CodiceComune = dr["CODICECOMUNE"].ToString(),
                                Comune = dr["COMUNE"].ToString(),
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
                    this.db.Connection.Close();
            }

        }

        public DatiComuneCompatto GetDaticomune(string codiceComune)
        {

            bool closeCnn = false;

            try
            {
                if (this.db.Connection.State == ConnectionState.Closed)
                {
                    this.db.Connection.Open();
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

                sql = this.PreparaQueryParametrica(sql, "codiceComune");

                using (IDbCommand cmd = this.db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this.db.CreateParameter("codiceComune", codiceComune.ToUpper()));

                    using (IDataReader dr = cmd.ExecuteReader())
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
                    this.db.Connection.Close();
            }

        }

        public DatiProvinciaCompatto GetDatiProvincia(string siglaProvincia)
        {
            bool closeCnn = false;

            try
            {
                if (this.db.Connection.State == ConnectionState.Closed)
                {
                    this.db.Connection.Open();
                    closeCnn = true;
                }

                string sql = @"select 
									SIGLAPROVINCIA , 
									PROVINCIA 
								from 
									COMUNI 
								where
									SIGLAPROVINCIA = {0}";

                sql = this.PreparaQueryParametrica(sql, "siglaProvincia");

                using (IDbCommand cmd = this.db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this.db.CreateParameter("siglaProvincia", siglaProvincia.ToUpper()));

                    using (IDataReader dr = cmd.ExecuteReader())
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
                    this.db.Connection.Close();
            }
        }

        public DatiProvinciaCompatto GetProvinciaDaCodiceComune(string codiceComune)
        {
            bool closeCnn = false;

            try
            {
                if (this.db.Connection.State == ConnectionState.Closed)
                {
                    this.db.Connection.Open();
                    closeCnn = true;
                }

                string sql = @"select 
									SIGLAPROVINCIA , 
									PROVINCIA 
								from 
									COMUNI 
								where
									CODICECOMUNE= {0}";

                sql = this.PreparaQueryParametrica(sql, "codiceComune");

                using (IDbCommand cmd = this.db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this.db.CreateParameter("codiceComune", codiceComune.ToUpper()));

                    using (IDataReader dr = cmd.ExecuteReader())
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
                    this.db.Connection.Close();
            }
        }

        public List<DatiProvinciaCompatto> GetListaProvincie()
        {

            bool closeCnn = false;

            try
            {
                if (this.db.Connection.State == ConnectionState.Closed)
                {
                    this.db.Connection.Open();
                    closeCnn = true;
                }

                string sql = "select distinct SIGLAPROVINCIA , PROVINCIA from COMUNI order by PROVINCIA";

                using (IDbCommand cmd = this.db.CreateCommand(sql))
                {
                    using (IDataReader dr = cmd.ExecuteReader())
                    {
                        List<DatiProvinciaCompatto> rVal = new List<DatiProvinciaCompatto>();

                        while (dr.Read())
                        {
                            DatiProvinciaCompatto datiProvincia = new DatiProvinciaCompatto
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
                    this.db.Connection.Close();
            }

        }

        public List<DatiComuneCompatto> GetComuniAssociati(string idComune)
        {

            bool closeCnn = false;

            try
            {
                if (this.db.Connection.State == ConnectionState.Closed)
                {
                    this.db.Connection.Open();
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

                sql = this.PreparaQueryParametrica(sql, "idComune");

                using (IDbCommand cmd = this.db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this.db.CreateParameter("idComune", idComune));

                    using (IDataReader dr = cmd.ExecuteReader())
                    {
                        List<DatiComuneCompatto> rVal = new List<DatiComuneCompatto>();

                        while (dr.Read())
                        {
                            DatiComuneCompatto c = new DatiComuneCompatto
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
                    this.db.Connection.Close();
            }

        }

        internal DatiComuneCompatto GetDatiComuneDaSiglaProvincia(string siglaProvincia)
        {
            bool closeCnn = false;

            try
            {
                if (this.db.Connection.State == ConnectionState.Closed)
                {
                    this.db.Connection.Open();
                    closeCnn = true;
                }

                string sql = @"select 
									*
								from 
									COMUNI 
								where
									siglaprovincia={0} and
								comune = provincia";

                sql = this.PreparaQueryParametrica(sql, "siglaProvincia");

                using (IDbCommand cmd = this.db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this.db.CreateParameter("siglaProvincia", siglaProvincia));

                    using (IDataReader dr = cmd.ExecuteReader())
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
                    this.db.Connection.Close();
            }




        }
    }
}

