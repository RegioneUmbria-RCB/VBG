using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Manager.DTO.Comuni;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;

namespace Init.SIGePro.Manager
{   ///<summary>
    /// Descrizione di riepilogo per StradarioMgr.\n	/// </summary>
    public class StradarioMgr : BaseManager
    {

        public StradarioMgr(DataBase dataBase) : base(dataBase) { }

        #region Metodi per l'accesso di base al DB
        public Stradario GetById(string idComune, int codiceStradario)
        {
            var filtro = new Stradario
            {
                IDCOMUNE = idComune,
                CODICESTRADARIO = codiceStradario.ToString(),
                UseForeign = PersonalLib2.Sql.useForeignEnum.Yes
            };

            return this.db.GetClass(filtro);
        }


        [Obsolete("Metodo obsoleto, utilizzatre ")]
        public Stradario GetById(String pCODICESTRADARIO, String pIDCOMUNE)
        {
            Stradario retVal = new Stradario();

            retVal.CODICESTRADARIO = pCODICESTRADARIO;
            retVal.IDCOMUNE = pIDCOMUNE;

            var mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return mydc[0];

            return null;
        }

        public Stradario GetByClass(Stradario p_class)
        {
            Stradario retVal = (Stradario)p_class.Clone();

            var mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return mydc[0];

            return null;

        }


        public List<Stradario> GetList(Stradario p_class, Stradario p_cmpClass)
        {
            return this.db.GetClassList(p_class, p_cmpClass).ToList<Stradario>();
        }


        public Stradario Extract(Stradario p_class)
        {
            if (String.IsNullOrEmpty(p_class.IDCOMUNE))
                throw new RequiredFieldException("Il campo STRADARIO.IDCOMUNE è obbligatorio");

            Stradario retVal = new Stradario();

            if (!String.IsNullOrEmpty(p_class.CODICESTRADARIO))
            {
                throw new IncongruentDataException("Non è possibile utilizzare il metodo StradarioMgr.Extract se si conosce già il valore della colonna CODICESTRADARIO(" + p_class.CODICESTRADARIO + ". Descrizione=" + p_class.DESCRIZIONE);
                //retVal = GetById( p_class.CODICESTRADARIO, p_class.IDCOMUNE );
            }

            retVal = new Stradario();

            retVal = this.RicercaStradarioPerCodiceViario(p_class);

            if (retVal == null)
                retVal = this.RicercaStradarioPerDescrizionePrefisso(p_class);

            if (retVal == null)
                retVal = this.RicercaStradarioPerDescrizione(p_class);

            //			if ( retVal == null )
            //				retVal = new Stradario();

            return retVal;
        }


        private Stradario RicercaStradarioPerCodiceViario(Stradario p_class)
        {
            Stradario retVal = new Stradario();

            if (!String.IsNullOrEmpty(p_class.CODVIARIO))
            {
                retVal.IDCOMUNE = p_class.IDCOMUNE;
                retVal.CODVIARIO = p_class.CODVIARIO;

                StradarioMgr pStradarioMgr = new StradarioMgr(this.db);

                retVal = pStradarioMgr.GetByClass(retVal);
            }

            if (retVal == null)
                return null;

            if (String.IsNullOrEmpty(retVal.CODICESTRADARIO))
                retVal = null;

            return retVal;
        }

        private Stradario RicercaStradarioPerDescrizione(Stradario p_class)
        {
            Stradario retVal = new Stradario();

            if (String.IsNullOrEmpty(p_class.DESCRIZIONE))
                return null;

            retVal.IDCOMUNE = p_class.IDCOMUNE;
            retVal.DESCRIZIONE = p_class.DESCRIZIONE;

            Stradario classCompare = new Stradario();
            classCompare.DESCRIZIONE = "=";

            FormattableString sql = $"select * from stradario where idcomune = {p_class.IDCOMUNE} and descrizione = {p_class.DESCRIZIONE}";
            var ar = this.db.ExecuteReader(sql, dr => new Stradario
            {
                CAP = dr["CAP"].ToString(),
                CODICESTRADARIO = dr["CODICESTRADARIO"].ToString(),
                CODICECOMUNE = dr["CODICECOMUNE"].ToString(),
                CODVIARIO = dr["CODVIARIO"].ToString(),
                CodiceComuneLocalizzazione = dr["COMUNE_LOCALIZZAZIONE"].ToString(),
                CS_DATE = dr["CS_DATE"].ToString(),
                DATAVALIDITA = dr.GetDateTime("DATAVALIDITA"),
                DESCRIZIONE = dr.GetString("DESCRIZIONE"),
                FKIDZONA = dr.GetString("FKIDZONA"),
                IDCOMUNE = dr.GetString("IDCOMUNE"),
                LOCFRAZ = dr.GetString("LOCFRAZ"),
                PREFISSO = dr.GetString("PREFISSO"),
            }).ToList();

            switch (ar.Count)
            {
                case 0: break;
                case 1: retVal = ar[0]; break;
                default: throw new MoreThanOneRecordException("Sono stati trovati " + ar.Count + " records di STRADARIO. Criteri: STRADARIO.IDCOMUNE=" + p_class.IDCOMUNE + " STRADARIO.DESCRIZIONE=" + p_class.DESCRIZIONE);
            }

            if (String.IsNullOrEmpty(retVal.CODICESTRADARIO))
                retVal = null;

            //			if (retVal==null)
            //			{
            //				//Ricerca con la descrizione dello stradario con la LIKE
            //				retVal.IDCOMUNE = p_class.IDCOMUNE;
            //				retVal.DESCRIZIONE = p_class.DESCRIZIONE;
            //
            //				classCompare.DESCRIZIONE = "like";
            //
            //				ar = GetList( retVal, classCompare );
            //
            //				switch ( ar.Count )
            //				{
            //					case 0 : break;
            //					case 1 : retVal = ( Stradario ) ar[0]; break;
            //					default: throw new MoreThanOneRecordException("Trovati " +  ar.Count + " record nello stradario ricercando per STRADARIO.DESCRIZIONE e STRADARIO.IDCOMUNE");
            //				}
            //			}

            return retVal;
        }

        private Stradario RicercaStradarioPerDescrizionePrefisso(Stradario p_class)
        {
            Stradario retVal = new Stradario();

            if (!String.IsNullOrEmpty(p_class.DESCRIZIONE))
            {
                retVal.IDCOMUNE = p_class.IDCOMUNE;
                retVal.DESCRIZIONE = p_class.DESCRIZIONE;
                retVal.PREFISSO = p_class.PREFISSO;

                Stradario classCompare = new Stradario();
                classCompare.DESCRIZIONE = "=";
                classCompare.PREFISSO = "=";

                var ar = this.GetList(retVal, classCompare);

                switch (ar.Count)
                {
                    case 0: break;
                    case 1: retVal = ar[0]; break;
                    default: throw new MoreThanOneRecordException("Sono stati trovati " + ar.Count + " records di STRADARIO. Criteri: STRADARIO.IDCOMUNE=" + p_class.IDCOMUNE + " STRADARIO.PREFISSO=" + p_class.PREFISSO + " STRADARIO.DESCRIZIONE=" + p_class.DESCRIZIONE);
                }
            }


            if (String.IsNullOrEmpty(retVal.CODICESTRADARIO))
                retVal = null;

            return retVal;
        }


        public Stradario Insert(Stradario p_class)
        {
            Stradario retVal = (Stradario)p_class.Clone();

            this.Validate(retVal, AmbitoValidazione.Insert);

            this.db.Insert(retVal);

            return retVal;
        }

        public Stradario Update(Stradario p_class)
        {
            Stradario retVal = (Stradario)p_class.Clone();

            this.db.Update(retVal);

            return retVal;
        }


        public void Validate(Stradario p_class, Init.SIGePro.Manager.Validator.AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(p_class, ambitoValidazione);

            this.ForeignValidate(p_class);
        }

        public void ForeignValidate(Stradario p_class)
        {
            #region STRADARIO.FKIDZONA
            if (!String.IsNullOrEmpty(p_class.FKIDZONA))
            {
                var conditions = new List<KeyValuePair<string, string>>
                {
                    new KeyValuePair<string, string>("CODICEZONA", p_class.FKIDZONA),
                    new KeyValuePair<string, string>("IDCOMUNE", p_class.IDCOMUNE)
                };

                if (this.recordCount("STRADARIOZONE", "CODICEZONA", conditions) == 0)
                {
                    throw (new RecordNotfoundException("STRADARIO.FKIDZONA non trovato nella tabella STRADARIOZONE"));
                }
            }
            #endregion
        }

        #endregion


        public List<Stradario> GetByIndirizzo(string idComune, string codiceComune, string indirizzo)
        {

            bool closeCnn = false;

            try
            {
                if (this.db.Connection.State == ConnectionState.Closed)
                {
                    this.db.Connection.Open();
                    closeCnn = true;
                }

                var sql = @"select 
								*
							from 
								stradario 
							where 
								idcomune={0} and 
								( codicecomune = {2} or codicecomune is null) and "
                                + this.db.Specifics.UCaseFunction("DESCRIZIONE") + @" = {1}
							order by descrizione asc";

                sql = this.PreparaQueryParametrica(sql, "idcomune", "codicecomune", "descrizione");

                using (IDbCommand cmd = this.db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this.db.CreateParameter("idcomune", idComune));
                    cmd.Parameters.Add(this.db.CreateParameter("codicecomune", String.IsNullOrEmpty(codiceComune) ? idComune : codiceComune));
                    cmd.Parameters.Add(this.db.CreateParameter("descrizione", indirizzo.ToUpper()));

                    return this.db.GetClassList<Stradario>(cmd);
                }
            }
            finally
            {
                if (closeCnn)
                    this.db.Connection.Close();
            }

        }




        //internal List<Stradario> GetByMatchParziale(string idComune, string codiceComune, string comuneLocalizzazione, CondizioniRicercaStradarioPerDescrizione condizioniRicerca)
        //{
        //	const string prefissoParametro = "parametro";

        //	bool closeCnn = false;

        //	try
        //	{
        //		var idx = 0;

        //		var partiWhere = condizioniRicerca.MatchParziali
        //										  .Select(x => db.Specifics.UCaseFunction("DESCRIZIONE") + " like " + db.Specifics.QueryParameterName(prefissoParametro + idx++))
        //										  .ToArray();

        //		var where = String.Join(condizioniRicerca.Separator, partiWhere);

        //		if (String.IsNullOrEmpty(where))
        //			return new List<Stradario>();


        //		var sql = new StringBuilder();

        //		sql.Append(@"select 
        //						PREFISSO, 
        //						CODICESTRADARIO,
        //						DESCRIZIONE,
        //						LOCFRAZ,
        //						CODVIARIO
        //					from 
        //						stradario 
        //					where 
        //						datavalidita is null and
        //						idcomune={0} ");

        //              if (!String.IsNullOrEmpty(codiceComune))
        //              {
        //                  sql.Append("and " + db.Specifics.NvlFunction("codicecomune", codiceComune) + @"= {1}");
        //              }

        //              sql.Append(" and (");
        //		sql.Append(where);
        //		sql.Append(")  and (comune_localizzazione ");

        //              sql.Append(" = ")
        //                  .Append(db.Specifics.QueryParameterName("comuneLocalizzazione"))
        //                  .Append(" or comune_localizzazione is null)");

        //		sql.Append(" order by descrizione asc");

        //		var query = PreparaQueryParametrica(sql.ToString(), "idcomune", "codicecomune", "comuneLocalizzazione");

        //		if (db.Connection.State == ConnectionState.Closed)
        //		{
        //			db.Connection.Open();
        //			closeCnn = true;
        //		}

        //		using (IDbCommand cmd = db.CreateCommand(query))
        //		{
        //			cmd.Parameters.Add(db.CreateParameter("idcomune", idComune));
        //                  if (!String.IsNullOrEmpty(codiceComune))
        //                  {
        //                      cmd.Parameters.Add(db.CreateParameter("codicecomune", codiceComune));
        //                  }

        //                  idx = 0;

        //                  foreach (var parziale in condizioniRicerca.MatchParziali)
        //                  {
        //                      cmd.Parameters.Add(db.CreateParameter(prefissoParametro + idx++, "%" + parziale.ToUpperInvariant() + "%"));
        //                  }

        //                  if (String.IsNullOrEmpty(comuneLocalizzazione))
        //                  {
        //                      comuneLocalizzazione = codiceComune;
        //                  }


        //			cmd.Parameters.Add(db.CreateParameter("comuneLocalizzazione", comuneLocalizzazione));


        //			using (var dr = cmd.ExecuteReader())
        //			{
        //				var rVal = new List<Stradario>();

        //				while (dr.Read())
        //				{
        //					var el = new Stradario
        //					{
        //						PREFISSO = dr["PREFISSO"].ToString(),
        //						CODICESTRADARIO = dr["CODICESTRADARIO"].ToString(),
        //						DESCRIZIONE = dr["DESCRIZIONE"].ToString(),
        //						LOCFRAZ = dr["LOCFRAZ"].ToString(),
        //						CODVIARIO = dr["CODVIARIO"].ToString()
        //					};

        //					rVal.Add(el);
        //				}

        //				return rVal;
        //			}
        //		}
        //	}
        //	catch (Exception ex)
        //	{
        //		throw;
        //	}
        //	finally
        //	{
        //		if (closeCnn)
        //			db.Connection.Close();
        //	}

        //}

        //        internal List<Stradario> GetByMatchParziale(string idComune, string codiceComune, string indirizzo)
        //        {
        //            codiceComune = String.IsNullOrEmpty(codiceComune) ? idComune : codiceComune;
        //            indirizzo = indirizzo.Trim();

        //            List<string> sc = new List<string>();
        //            string[] addressChunks = indirizzo.Split(' ');

        //            var tmpChunks = new List<string>();

        //            for (int i = 0; i < addressChunks.Length; i++)
        //            {
        //                tmpChunks.AddRange(addressChunks[i].Split('.'));
        //            }

        //            addressChunks = tmpChunks.ToArray();


        //            for (int i = 0; i < addressChunks.Length; i++)
        //            {
        //                if (addressChunks[i].Length < 2)
        //                    continue;

        //                var parola = addressChunks[i].ToUpper().Replace("'", "''");

        //                if (PAROLE_RISERVATE.Contains(parola)) continue;

        //                sc.Add( db.Specifics.UCaseFunction("DESCRIZIONE") + " like '%" + parola + "%'");
        //            }

        //            string where = string.Join(" or ", sc.ToArray());

        //            if(where == String.Empty)
        //                return new List<Stradario>();

        //            bool closeCnn = false;

        //            try
        //            {
        //                if (db.Connection.State == ConnectionState.Closed)
        //                {
        //                    db.Connection.Open();
        //                    closeCnn = true;
        //                }

        //                var sql = new StringBuilder();

        //                sql.Append(@"select 
        //								PREFISSO, CODICESTRADARIO,DESCRIZIONE 
        //							from 
        //								stradario 
        //							where 
        //								idcomune={0} and " + db.Specifics.NvlFunction("codicecomune", codiceComune ) + @"={1} and (");
        //                sql.Append(where);
        //                sql.Append(") order by descrizione asc");

        //                var query = PreparaQueryParametrica(sql.ToString(), "idcomune", "codicecomune");

        //                using (IDbCommand cmd = db.CreateCommand(query))
        //                {
        //                    cmd.Parameters.Add(db.CreateParameter("idcomune", idComune));
        //                    cmd.Parameters.Add(db.CreateParameter("codicecomune", codiceComune ));

        //                    using (var dr = cmd.ExecuteReader())
        //                    {
        //                        var rVal = new List<Stradario>();

        //                        while (dr.Read())
        //                        {
        //                            var el = new Stradario
        //                            {
        //                                PREFISSO = dr["PREFISSO"].ToString(),
        //                                CODICESTRADARIO = dr["CODICESTRADARIO"].ToString(),
        //                                DESCRIZIONE = dr["DESCRIZIONE"].ToString()
        //                            };

        //                            rVal.Add(el);
        //                        }

        //                        return rVal;
        //                    }
        //                }
        //            }
        //            catch (Exception ex)
        //            {
        //                throw;
        //            }
        //            finally
        //            {
        //                if (closeCnn)
        //                    db.Connection.Close();
        //            }
        //        }


        public Stradario GetByCodViario(string idComune, string codViario)
        {
            bool closeCnn = false;

            try
            {
                if (this.db.Connection.State == ConnectionState.Closed)
                {
                    this.db.Connection.Open();
                    closeCnn = true;
                }

                var sql = @"select 
								*
							from 
								stradario 
							where 
								idcomune={0} and 
								codviario = {1} and 
								datavalidita is null
							order by descrizione asc";

                sql = this.PreparaQueryParametrica(sql, "idcomune", "codviario");

                using (IDbCommand cmd = this.db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this.db.CreateParameter("idcomune", idComune));
                    cmd.Parameters.Add(this.db.CreateParameter("codviario", codViario));

                    var list = this.db.GetClassList<Stradario>(cmd);

                    return list.FirstOrDefault();
                }
            }
            finally
            {
                if (closeCnn)
                    this.db.Connection.Close();
            }
        }

        public DatiComuneCompatto[] GetComuniLocalizzazioni(string idComune, string codiceComune)
        {
            bool closeCnn = false;

            try
            {
                if (this.db.Connection.State == ConnectionState.Closed)
                {
                    this.db.Connection.Open();
                    closeCnn = true;
                }

                var sql = @"select distinct 
								comuni.cf,
								comuni.codicecomune,
								comuni.comune,
								comuni.provincia,
								comuni.SiglaProvincia
							from 
							  stradario, 
							  comuni
							where 
								comuni.codicecomune = stradario.comune_localizzazione and
                                stradario.datavalidita is null and
								stradario.idcomune = {0} and " + this.db.Specifics.NvlFunction("stradario.codicecomune", codiceComune) + @"= {1}
							order by comuni.comune";

                sql = this.PreparaQueryParametrica(sql, "idcomune", "codicecomune");

                using (IDbCommand cmd = this.db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this.db.CreateParameter("idcomune", idComune));
                    cmd.Parameters.Add(this.db.CreateParameter("codicecomune", codiceComune));

                    using (var dr = cmd.ExecuteReader())
                    {
                        var rVal = new List<DatiComuneCompatto>();

                        while (dr.Read())
                        {
                            rVal.Add(new DatiComuneCompatto
                            {
                                Cf = dr["cf"].ToString(),
                                CodiceComune = dr["codicecomune"].ToString(),
                                Comune = dr["comune"].ToString(),
                                Provincia = dr["provincia"].ToString(),
                                SiglaProvincia = dr["SiglaProvincia"].ToString(),
                            });
                        }

                        return rVal.ToArray();
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