using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Utils;
using Init.SIGePro.Validator;
using Init.Utils;
using PersonalLib2.Data;
using PersonalLib2.Data.Providers;
using PersonalLib2.Sql;
using PersonalLib2.Utils;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Reflection;

namespace Init.SIGePro.Manager
{
    /// <summary>
    /// Descrizione di riepilogo per BaseManager.
    /// </summary>
    public class BaseManager : IManager
    {
        public BaseManager(DataBase dataBase)
        {
            this.m_db = dataBase;

            this.RegisterHandlers();

            /*m_db.OnInsert += new DataBase.DbEventDelegate(m_db_OnInsert);
			m_db.OnUpdate += new DataBase.DbEventDelegate(m_db_OnUpdate);
			m_db.OnDelete += new DataBase.DbEventDelegate(m_db_OnDelete);*/
        }

        public virtual void RegisterHandlers() { }



        protected IDbTransaction Transaction = null;

        #region proprietà per l'accesso al db

        private readonly DataBase m_db = null;

        virtual public DataBase db
        {
            get
            {
                return this.m_db;
            }
        }

        #endregion

        #region IManager
        /*
		public DataClass Insert(DataClass pClass)
		{
			// TODO: aggiungere l'implementazione di BaseManager.Insert
			return null;
		}
		*/
        public bool RequiredFieldValidate(DataClass pClass, AmbitoValidazione ambitoValidazione)
        {
            bool retVal = false;

            ClassValidator pValidator = new ClassValidator(pClass);

            retVal = pValidator.RequiredFieldValidator(this.m_db, ambitoValidazione);

            return retVal;
        }

        public DataClass Update(DataClass pClass)
        {
            // TODO: aggiungere l'implementazione di BaseManager.Update
            return null;
        }

        public virtual DataClass ChildDataIntegrations(DataClass pClass)
        {
            // TODO: aggiungere l'implementazione di BaseManager.ChildDataIntegrations
            return null;
        }

        #endregion

        #region funzioni pubbliche

        public int recordCount(string tabella, string campo, IEnumerable<KeyValuePair<string, string>> conditionList)
        {
            bool internalOpen = false;
            string sql = String.Format("select count({0}) as conta from {1} ", campo, tabella);
            try
            {
                if (this.db.Connection.State == ConnectionState.Closed)
                {
                    this.db.Connection.Open();
                    internalOpen = true;
                }

                if (conditionList != null)
                {
                    sql = String.Concat(sql, "where ");

                    IEnumerable<string> condizioniWhere = conditionList.Select(x => x.Key + " = " + this.db.Specifics.QueryParameterName(x.Key));

                    sql += condizioniWhere.Count() == 0 ? "1=1" : String.Join(" and ", condizioniWhere.ToArray());
                }

                using (IDbCommand cmd = this.db.CreateCommand(sql))
                {
                    conditionList.ToList().ForEach(x => cmd.Parameters.Add(this.db.CreateParameter(x.Key, x.Value)));

                    object obj = cmd.ExecuteScalar();
                    return (obj == DBNull.Value) ? 0 : Convert.ToInt32(obj.ToString());
                }

            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO NEL METODO MethodBase.GetCurrentMethod(), SQL = {0}", sql), ex);
            }
            finally
            {
                if (internalOpen) this.db.Connection.Close();
            }
        }

        [Obsolete("Utilizzare il metodo recordCount che prende come parametri string, string, List<KeyValuePair<string, string>>")]
        public int recordCount(string p_tabella, string p_campo, string p_condwhere)
        {

            string SQLfmt = String.Empty;
            bool internalOpen = false;

            if (this.db.Connection.State == ConnectionState.Closed)
            {
                this.db.Connection.Open();
                internalOpen = true;
            }

            try
            {

                SQLfmt = "select count( " + p_campo + " ) as conta from " + p_tabella;
                if (!string.IsNullOrEmpty(p_condwhere))
                {
                    if (p_condwhere.Trim().ToUpper().Substring(0, 5) != "WHERE")
                    {
                        SQLfmt += " WHERE ";
                    }

                    SQLfmt += " " + p_condwhere;
                }
                using (IDbCommand command = this.db.CreateCommand(SQLfmt))
                {
                    object obj = command.ExecuteScalar();
                    return (obj == DBNull.Value) ? 0 : Convert.ToInt32(obj.ToString());
                }

            }
            catch (System.Exception ex)
            {
                throw new BaseException("Errore in " + MethodBase.GetCurrentMethod() + ": sql=\"" + SQLfmt + "\" - Exception: " + ex.ToString(), ex);
            }
            finally
            {
                if (internalOpen) this.db.Connection.Close();
            }
        }

        [Obsolete("Utilizzare al suo posto StringChecker.IsStringEmpty")]
        public bool IsStringEmpty(string str)
        {
            return StringChecker.IsStringEmpty(str);
        }

        [Obsolete("Utilizzare al suo posto StringChecker.IsObjectEmpty")]
        public bool IsObjectEmpty(object obj)
        {
            return StringChecker.IsObjectEmpty(obj);
        }

        [Obsolete("Utilizzare al suo posto StringChecker.IsNumeric")]
        public bool IsNumeric(string str)
        {
            return StringChecker.IsNumeric(str);
        }
        #endregion

        #region utilità

        protected int FindMax(string nomeCampo, string nomeTabella, string idComune, List<KeyValuePair<string, object>> condizioneWhere)
        {
            return this.FindMax(nomeCampo, nomeTabella, idComune, 1, condizioneWhere);
        }

        protected int FindMax(string nomeCampo, string nomeTabella, string idComune, int incremento, List<KeyValuePair<string, object>> condizioneWhere)
        {
            IProvider provider = this.db.Specifics;
            string sql = "select " + provider.MaxFunction(nomeCampo) + " from " + nomeTabella;
            string where = String.Empty;

            if (!string.IsNullOrEmpty(idComune))
                where += " where IDCOMUNE='" + idComune + "'";

            if (condizioneWhere != null && condizioneWhere.Count > 0)
            {
                foreach (KeyValuePair<string, object> filtro in condizioneWhere)
                {
                    if (!string.IsNullOrEmpty(where))
                    {
                        where += " and ";
                    }
                    else
                    {
                        where += " where ";
                    }

                    where += filtro.Key + " = " + provider.QueryParameterName(filtro.Key);
                }

                sql += where;
            }

            bool closeCnn = false;

            if (this.db.Connection.State == ConnectionState.Closed)
            {
                closeCnn = true;
                this.db.Connection.Open();
            }
            try
            {
                using (IDbCommand cmd = this.db.CreateCommand())
                {
                    cmd.CommandText = sql;

                    foreach (KeyValuePair<string, object> filtro in condizioneWhere)
                    {
                        IDbDataParameter par = cmd.CreateParameter();
                        par.ParameterName = filtro.Key;
                        par.Value = filtro.Value;

                        cmd.Parameters.Add(par);
                    }

                    object val = cmd.ExecuteScalar();

                    if (val == null || val == DBNull.Value)
                        return incremento;

                    return Convert.ToInt32(val) + incremento;
                }
            }
            finally
            {
                if (closeCnn)
                    this.db.Connection.Close();
            }
        }

        #endregion

        #region Gestori degli eventi generati dalla classe database e relative funzioni di utilità
        private void m_db_OnDelete(object sender, DbEventArgs e)
        {
            this.LoggaOperazione("DELETE", e.DataClass, null, e.Command);
        }
        /*
        private void m_db_OnUpdate(object sender, DbEventArgs e)
        {
            DataClass classeConfronto = (DataClass)Activator.CreateInstance(e.DataClass.GetType());

            foreach (PropertyInfo pi in e.DataClass.GetKeyFields())
                pi.SetValue(classeConfronto, pi.GetValue(e.DataClass, null), null);

            classeConfronto = this.m_db.GetClass(classeConfronto);

            if (classeConfronto == null)
                return;

            foreach (KeyValuePair<DataFieldAttribute, PropertyInfo> dataField in e.DataClass.GetDataFields())
            {
                object val = dataField.Value.GetValue(e.DataClass, null);
                object cmpVal = dataField.Value.GetValue(classeConfronto, null);

                if (val != cmpVal)
                    this.LoggaOperazione("UPDATE", e.DataClass, dataField.Key.ColumnName, e.Command);
            }
        }
        */
        private void m_db_OnInsert(object sender, DbEventArgs e)
        {
            this.LoggaOperazione("INSERT", e.DataClass, null, e.Command);
        }



        private string GetCodiceRichiesta(string idComune, string operazione, string tabella, string colonna)
        {
            LogInfo li = new LogInfo();
            li.IDCOMUNE = idComune;
            li.LI_OP = operazione;
            li.LI_TAB = tabella;
            li.LI_COL = colonna;

            li = (LogInfo)this.m_db.GetClass(li);

            if (li == null) return String.Empty;

            return li.LI_DESC;
        }

        private string GetIdComune(DataClass dc)
        {
            PropertyInfo[] props = dc.GetType().GetProperties();

            for (int i = 0; i < props.Length; i++)
            {
                if (props[i].Name.ToUpper() == "IDCOMUNE")
                {
                    object val = props[i].GetValue(dc, null);

                    if (val != null)
                        return val.ToString();
                    else return null;
                }
            }

            return String.Empty;
        }

        private void LoggaOperazione(string operazione, DataClass dc, string colonna, IDbCommand cmd)
        {
            string idComune = this.GetIdComune(dc);

            // La property IDCOMUNE non esiste nella classe
            if (String.IsNullOrEmpty(idComune)) return;


            string codiceRichiesta = this.GetCodiceRichiesta(idComune, operazione, dc.DataTableName, colonna);

            if (!String.IsNullOrEmpty(codiceRichiesta))
                Logger.LogRichiesta(this.m_db, idComune, codiceRichiesta, CommandTracer.Trace(cmd));
        }

        #endregion


        /// <summary>
        /// Prepara una query parametrica utilizzando i nomi dei parametri passati
        /// </summary>
        /// <example>
        /// string s = PreparaParametriQuery("Select * from t where a={0} and b={1}","primo","secondo");
        /// </example>
        /// <param name="sql">Query con segnaposto di sostituzione in cui inserire i parametri</param>
        /// <param name="nomiParametri">Lista di nomi di parametri da riportare nella query in base alle specifiche del db</param>
        /// <returns>Espressione sql con i nomi dei paramtri al posto dei segnaposto</returns>
        protected string PreparaQueryParametrica(string sql, params string[] nomiParametri)
        {
            for (int i = 0; i < nomiParametri.Length; i++)
                nomiParametri[i] = this.db.Specifics.QueryParameterName(nomiParametri[i]);

            return String.Format(sql, nomiParametri);
        }

        public bool CheckConnectionState()
        {
            if (this.m_db.Connection.State == ConnectionState.Closed)
            {
                this.m_db.Connection.Open();
                return true;
            }

            return false;
        }

        protected T ExecuteInConnection<T>(Func<T> function)
        {
            bool shouldClose = this.CheckConnectionState();

            try
            {
                return function();
            }
            finally
            {
                if (shouldClose)
                {
                    this.m_db.Connection.Close();
                }
            }
        }
    }
}
