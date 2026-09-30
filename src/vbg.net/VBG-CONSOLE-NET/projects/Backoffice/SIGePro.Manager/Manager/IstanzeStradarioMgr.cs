using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Validator;
using Init.Utils;
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

            List<IstanzeStradario> mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return (mydc[0]) as IstanzeStradario;

            return null;
        }

        public IstanzeStradario GetById(string idComune, int idStradario)
        {
            return (IstanzeStradario)this.db.GetClass(new IstanzeStradario { IDCOMUNE = idComune, ID = idStradario.ToString(), UseForeign = useForeignEnum.Yes });
        }



        /// <summary>
        /// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
        /// </summary>
        /// <param name="p_class">Criteri di ricerca</param>
        /// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
        public List<IstanzeStradario> GetList(IstanzeStradario p_class)
        {
            return this.GetList(p_class, null);
        }

        public List<IstanzeStradario> GetList(IstanzeStradario p_class, IstanzeStradario p_cmpClass)
        {
            return this.db.GetClassList(p_class, p_cmpClass, false);
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

            if (this.IsStringEmpty(retVal.CODICESTRADARIO) && retVal.Stradario != null)
            {
                if (this.IsStringEmpty(retVal.Stradario.IDCOMUNE))
                    retVal.Stradario.IDCOMUNE = retVal.IDCOMUNE;
                else if (retVal.Stradario.IDCOMUNE.ToUpper() != retVal.IDCOMUNE.ToUpper())
                    throw new IncongruentDataException("STRADARIO.IDCOMUNE diverso da ISTANZESTRADARIO.IDCOMUNE");


                StradarioMgr pStradarioMgr = new StradarioMgr(this.db);
                Stradario pStradario = pStradarioMgr.Extract(retVal.Stradario);

                if (pStradario != null)
                    retVal.CODICESTRADARIO = pStradario.CODICESTRADARIO;
            }

            if (StringChecker.IsStringEmpty(retVal.PRIMARIO))
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
            if (!this.IsStringEmpty(p_class.CODICEISTANZA))
            {
                if (this.recordCount("ISTANZE", "CODICEISTANZA", "WHERE CODICEISTANZA = " + p_class.CODICEISTANZA + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0)
                {
                    throw (new RecordNotfoundException("ISTANZESTRADARIO.CODICEISTANZA non trovato nella tabella ISTANZE"));
                }
            }
            #endregion

            #region ISTANZESTRADARIO.CODICESTRADARIO
            if (!this.IsStringEmpty(p_class.CODICESTRADARIO))
            {
                if (this.recordCount("STRADARIO", "CODICESTRADARIO", "WHERE CODICESTRADARIO = " + p_class.CODICESTRADARIO + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0)
                {
                    throw (new RecordNotfoundException("ISTANZESTRADARIO.CODICESTRADARIO non trovato nella tabella STRADARIO"));
                }
            }
            #endregion

            #region ISTANZESTRADARIO.COLORE
            if (!this.IsStringEmpty(p_class.COLORE))
            {
                if (this.recordCount("STRADARIOCOLORE", "CODICECOLORE", "WHERE CODICECOLORE = '" + p_class.COLORE + "' AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0)
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

                DataProviderFactory dpf = new DataProviderFactory(this.db.Connection);

                IDbDataParameter pcodicestradario = dpf.CreateDataParameter();
                pcodicestradario.ParameterName = dpf.Specifics.ParameterName("CODICESTRADARIO");
                pcodicestradario.Value = p_class.CODICESTRADARIO;
                string s_codicestradario = dpf.Specifics.QueryParameterName("CODICESTRADARIO");
                command.Parameters.Add(pcodicestradario);


                IDbDataParameter pcivico = dpf.CreateDataParameter();
                pcivico.ParameterName = dpf.Specifics.ParameterName("CIVICO");
                pcivico.Value = (!this.IsStringEmpty(p_class.CIVICO)) ? (object)p_class.CIVICO : DBNull.Value;
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