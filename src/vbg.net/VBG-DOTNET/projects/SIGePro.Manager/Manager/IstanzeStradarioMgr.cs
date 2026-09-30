using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using PersonalLib2.Data.V2.Legacy;
using PersonalLib2.Sql;
using System;
using System.Collections.Generic;
using System.Data;

namespace Init.SIGePro.Manager
{   ///<summary>
    /// Descrizione di riepilogo per IstanzeStradarioMgr.\n	/// </summary>
    public class IstanzeStradarioMgr : BaseManager
    {

        public IstanzeStradarioMgr(DataBase dataBase) : base(dataBase) { }

        #region Metodi per l'accesso di base al DB

        public IstanzeStradario GetById(String pID, String pIDCOMUNE)
        {
            IstanzeStradario retVal = new IstanzeStradario();
            retVal.ID = pID;
            retVal.IDCOMUNE = pIDCOMUNE;

            var mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return mydc[0];

            return null;
        }

        public IstanzeStradario GetById(string idComune, int idStradario)
        {
            return this.db.GetClass(new IstanzeStradario { IDCOMUNE = idComune, ID = idStradario.ToString(), UseForeign = useForeignEnum.Yes });
        }



        /// <summary>
        /// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
        /// </summary>
        /// <param name="p_class">Criteri di ricerca</param>
        /// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
        public List<IstanzeStradario> GetList(IstanzeStradario p_class)
        {
            return this.db.GetClassList(p_class, false).ToList<IstanzeStradario>();
        }

        internal List<IstanzeStradario> GetByCodiceIstanza(string idComune, int codiceIstanza)
        {
            var sql = $@"SELECT * FROM istanzestradario WHERE idcomune={this.db.Specifics.QueryParameterName("idcomune")} AND codiceistanza={this.db.Specifics.QueryParameterName("codiceIstanza")}";

            using (var cmd = this.db.CreateCommand(sql))
            {
                cmd.Parameters.Add(this.db.CreateParameter("idcomune", idComune));
                cmd.Parameters.Add(this.db.CreateParameter("codiceIstanza", codiceIstanza));

                return this.db.GetClassList<IstanzeStradario>(cmd, new GetClassListFlags(useForeignEnum.Yes));
            }

        }

        public void Delete(IstanzeStradario p_class)
        {
            this.db.Delete(p_class);
        }

        public IstanzeStradario Insert(IstanzeStradario p_class)
        {

            p_class = this.DataIntegrations(p_class);

            this.Validate(p_class, AmbitoValidazione.Insert);

            this.db.Insert(p_class);

            this.ParentDataIntegrations(p_class);

            return p_class;
        }

        public IstanzeStradario Update(IstanzeStradario p_class)
        {
            this.db.Update(p_class);
            return p_class;
        }


        private IstanzeStradario DataIntegrations(IstanzeStradario p_class)
        {
            IstanzeStradario retVal = (IstanzeStradario)p_class.Clone();

            if (String.IsNullOrEmpty(retVal.CODICESTRADARIO) && retVal.Stradario != null)
            {
                if (String.IsNullOrEmpty(retVal.Stradario.IDCOMUNE))
                    retVal.Stradario.IDCOMUNE = retVal.IDCOMUNE;
                else if (!string.Equals(retVal.Stradario.IDCOMUNE, retVal.IDCOMUNE, StringComparison.OrdinalIgnoreCase))
                    throw new IncongruentDataException("STRADARIO.IDCOMUNE diverso da ISTANZESTRADARIO.IDCOMUNE");


                StradarioMgr pStradarioMgr = new StradarioMgr(this.db);
                Stradario pStradario = pStradarioMgr.Extract(retVal.Stradario);

                if (pStradario != null)
                    retVal.CODICESTRADARIO = pStradario.CODICESTRADARIO;
            }

            if (String.IsNullOrEmpty(retVal.PRIMARIO))
                retVal.PRIMARIO = "0";


            return retVal;
        }

        private void Validate(IstanzeStradario p_class, AmbitoValidazione ambitoValidazione)
        {
            if (p_class.PRIMARIO != "0" && p_class.PRIMARIO != "1")
                throw new IncongruentDataException("Impossibile assegnare il valore " + p_class.PRIMARIO + " al campo ISTANZESTRADARIO.PRIMARIO. Valori ammessi: 0,1");

            this.RequiredFieldValidate(p_class, ambitoValidazione);

            this.ForeignValidate(p_class);
        }

        private void ForeignValidate(IstanzeStradario p_class)
        {
            #region ISTANZESTRADARIO.CODICEISTANZA
            if (!String.IsNullOrEmpty(p_class.CODICEISTANZA))
            {
                var condIstanze = new List<KeyValuePair<string, string>>
                {
                    new KeyValuePair<string, string>("CODICEISTANZA", p_class.CODICEISTANZA),
                    new KeyValuePair<string, string>("IDCOMUNE", p_class.IDCOMUNE)
                };
                if (this.recordCount("ISTANZE", "CODICEISTANZA", condIstanze) == 0)
                {
                    throw (new RecordNotfoundException("ISTANZESTRADARIO.CODICEISTANZA non trovato nella tabella ISTANZE"));
                }
            }
            #endregion

            #region ISTANZESTRADARIO.CODICESTRADARIO
            if (!String.IsNullOrEmpty(p_class.CODICESTRADARIO))
            {
                var condStradario = new List<KeyValuePair<string, string>>
                {
                    new KeyValuePair<string, string>("CODICESTRADARIO", p_class.CODICESTRADARIO),
                    new KeyValuePair<string, string>("IDCOMUNE", p_class.IDCOMUNE)
                };
                if (this.recordCount("STRADARIO", "CODICESTRADARIO", condStradario) == 0)
                {
                    throw (new RecordNotfoundException("ISTANZESTRADARIO.CODICESTRADARIO non trovato nella tabella STRADARIO"));
                }
            }
            #endregion

            #region ISTANZESTRADARIO.COLORE
            if (!String.IsNullOrEmpty(p_class.COLORE))
            {
                var condColore = new List<KeyValuePair<string, string>>
                {
                    new KeyValuePair<string, string>("CODICECOLORE", p_class.COLORE),
                    new KeyValuePair<string, string>("IDCOMUNE", p_class.IDCOMUNE)
                };
                if (this.recordCount("STRADARIOCOLORE", "CODICECOLORE", condColore) == 0)
                {
                    throw (new RecordNotfoundException("ISTANZESTRADARIO.COLORE non trovato nella tabella STRADARIOCOLORE"));
                }
            }
            #endregion
        }

        private void ParentDataIntegrations(IstanzeStradario p_class)
        {
            if (p_class.PRIMARIO == "1")
            {
                bool internalOpen = false;

                IDbCommand command = this.db.CreateCommand();

                if (this.db.Connection.State == ConnectionState.Closed)
                {
                    internalOpen = true;
                    this.db.Connection.Open();
                }

                var dpf = new DataProviderFactory(this.db.Connection);

                IDbDataParameter pcodicestradario = dpf.CreateDataParameter();
                pcodicestradario.ParameterName = dpf.Specifics.ParameterName("CODICESTRADARIO");
                pcodicestradario.Value = p_class.CODICESTRADARIO;
                string s_codicestradario = dpf.Specifics.QueryParameterName("CODICESTRADARIO");
                command.Parameters.Add(pcodicestradario);


                IDbDataParameter pcivico = dpf.CreateDataParameter();
                pcivico.ParameterName = dpf.Specifics.ParameterName("CIVICO");
                pcivico.Value = (!String.IsNullOrEmpty(p_class.CIVICO)) ? (object)p_class.CIVICO : DBNull.Value;
                string s_civico = dpf.Specifics.QueryParameterName("CIVICO");
                command.Parameters.Add(pcivico);


                IDbDataParameter pidcomune = dpf.CreateDataParameter();
                pidcomune.ParameterName = dpf.Specifics.ParameterName("IDCOMUNE");
                pidcomune.Value = p_class.IDCOMUNE;
                string s_idcomune = dpf.Specifics.QueryParameterName("IDCOMUNE");
                command.Parameters.Add(pidcomune);

                IDbDataParameter pcodiceistanza = dpf.CreateDataParameter();
                pcodiceistanza.ParameterName = dpf.Specifics.ParameterName("CODICEISTANZA");
                pcodiceistanza.Value = p_class.CODICEISTANZA;
                string s_codiceistanza = dpf.Specifics.QueryParameterName("CODICEISTANZA");
                command.Parameters.Add(pcodiceistanza);

                command.CommandText = "UPDATE ISTANZE SET CODICESTRADARIO = " + s_codicestradario + ", CIVICO = " + s_civico + " WHERE IDCOMUNE = " + s_idcomune + " AND CODICEISTANZA = " + s_codiceistanza;


                command.ExecuteNonQuery();

                if (internalOpen)
                    this.db.Connection.Close();
            }
        }

        #endregion
    }
}