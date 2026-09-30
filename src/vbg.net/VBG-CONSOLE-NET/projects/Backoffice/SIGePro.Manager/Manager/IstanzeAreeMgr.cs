using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Validator;
using Init.Utils;
using PersonalLib2.Data;
using PersonalLib2.Data.V2.Legacy;
using System;
using System.Collections.Generic;
using System.Data;

namespace Init.SIGePro.Manager
{   ///<summary>
    /// Descrizione di riepilogo per IstanzeAreeMgr.\n	/// </summary>
    public class IstanzeAreeMgr : BaseManager
    {

        public IstanzeAreeMgr(DataBase dataBase) : base(dataBase) { }

        #region Metodi per l'accesso di base al DB

        public IstanzeAree GetById(String pCODICEISTANZA, String pCODICEAREA, String pIDCOMUNE)
        {
            IstanzeAree retVal = new IstanzeAree();
            retVal.CODICEISTANZA = pCODICEISTANZA;
            retVal.CODICEAREA = pCODICEAREA;
            retVal.IDCOMUNE = pIDCOMUNE;

            List<IstanzeAree> mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return (mydc[0]) as IstanzeAree;

            return null;
        }



        /// <summary>
        /// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
        /// </summary>
        /// <param name="p_class">Criteri di ricerca</param>
        /// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
        public List<IstanzeAree> GetList(IstanzeAree p_class)
        {
            return this.GetList(p_class, null);
        }

        public List<IstanzeAree> GetList(IstanzeAree p_class, IstanzeAree p_cmpClass)
        {
            return this.db.GetClassList(p_class, p_cmpClass, false);
        }


        public void Delete(IstanzeAree p_class)
        {
            this.db.Delete(p_class);
        }

        public IstanzeAree Insert(IstanzeAree p_class)
        {

            p_class = this.DataIntegrations(p_class);

            this.Validate(p_class, AmbitoValidazione.Insert);

            this.db.Insert(p_class);

            this.ParentDataIntegrations(p_class);

            return p_class;
        }

        public IstanzeAree Update(IstanzeAree p_class)
        {
            this.db.Update(p_class);
            return p_class;
        }


        private IstanzeAree DataIntegrations(IstanzeAree p_class)
        {
            IstanzeAree retVal = (IstanzeAree)p_class.Clone();

            if (StringChecker.IsStringEmpty(retVal.PRIMARIO))
                retVal.PRIMARIO = "0";

            if (StringChecker.IsStringEmpty(retVal.AUTOINS))
                retVal.AUTOINS = "0";

            return retVal;
        }

        private void Validate(IstanzeAree p_class, AmbitoValidazione ambitoValidazione)
        {
            if (p_class.PRIMARIO != "0" && p_class.PRIMARIO != "1")
                throw new IncongruentDataException("Impossibile assegnare il valore " + p_class.PRIMARIO + " al campo ISTANZEAREE.PRIMARIO. Valori ammessi: 0,1");

            if (p_class.AUTOINS != "0" && p_class.AUTOINS != "1")
                throw new IncongruentDataException("Impossibile assegnare il valore " + p_class.AUTOINS + " al campo ISTANZEAREE.AUTOINS. Valori ammessi: 0,1");

            this.RequiredFieldValidate(p_class, ambitoValidazione);

            this.ForeignValidate(p_class);
        }

        private void ForeignValidate(IstanzeAree p_class)
        {
            #region ISTANZEAREE.CODICEISTANZA
            if (!this.IsStringEmpty(p_class.CODICEISTANZA))
            {
                if (this.recordCount("ISTANZE", "CODICEISTANZA", "WHERE CODICEISTANZA = " + p_class.CODICEISTANZA + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0)
                {
                    throw (new RecordNotfoundException("ISTANZEAREE.CODICEISTANZA non trovato nella tabella ISTANZE"));
                }
            }
            #endregion

            #region ISTANZEAREE.CODICEAREA
            if (!this.IsStringEmpty(p_class.CODICEAREA))
            {
                if (this.recordCount("AREE", "CODICEAREA", "WHERE CODICEAREA = " + p_class.CODICEAREA + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0)
                {
                    throw (new RecordNotfoundException("ISTANZEAREE.CODICEAREA non trovato nella tabella AREE"));
                }
            }
            #endregion
        }

        private void ParentDataIntegrations(IstanzeAree p_class)
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

                IDbDataParameter pcodicearea = dpf.CreateDataParameter();
                pcodicearea.ParameterName = dpf.Specifics.ParameterName("CODICEAREA");
                pcodicearea.Value = p_class.CODICEAREA;
                string s_codicearea = dpf.Specifics.QueryParameterName("CODICEAREA");
                command.Parameters.Add(pcodicearea);

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

                command.CommandText = "UPDATE ISTANZE SET CODICEAREA = " + s_codicearea + " WHERE IDCOMUNE = " + s_idcomune + " AND CODICEISTANZA = " + s_codiceistanza;

                command.ExecuteNonQuery();

                if (internalOpen)
                    this.db.Connection.Close();
            }
        }

        #endregion
    }
}