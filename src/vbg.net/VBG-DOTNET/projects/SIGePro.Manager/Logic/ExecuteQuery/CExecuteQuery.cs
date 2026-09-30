using PersonalLib2.Data;
using System;
using System.Data;

namespace Init.SIGePro.Manager.Logic.ExecuteQuery
{
    /// <summary>
    /// Descrizione di riepilogo per Class1.
    /// </summary>
    public class CExecuteQueryMgr
    {
        public CExecuteQueryMgr()
        {
        }

        public CExecuteQueryMgr(DataBase db)
        {
            this.Database = db;
        }

        #region Proprietà
        public DataBase Database { get; set; }

        private string sQuery;
        public string Query
        {
            get
            {
                return this.sQuery;
            }
            set
            {
                this.sQuery = value;
            }
        }
        #endregion

        #region Metodi privati
        #endregion

        #region Metodi pubblici
        public DataSet ExecuteQuery()
        {
            DataSet ds = new DataSet();

            try
            {
                if (this.Query.ToUpper().StartsWith("SELECT"))
                {
                    using (IDbCommand pCmd = this.Database.CreateCommand(this.Query))
                    {
                        // DataProviderFactory dpf = new DataProviderFactory(Database.Connection);
                        IDbDataAdapter adaptQuery = this.Database.CreateDataAdapter(pCmd);
                        adaptQuery.Fill(ds);
                    }

                }
                else
                {
                    throw new Exception("La query che si tenta di eseguire non inizia con la seguente parola chiave: SELECT");
                }
            }
            catch (Exception ex)
            {
                throw new Exception("Si e' verificato un errore durante l'esecuzione della query: " + this.Query + ". Messaggio: " + ex.Message);
            }

            return ds;
        }

        public int ExecuteNonQuery()
        {
            int iResult = 0;
            bool closeCnn = false;

            try
            {
                if (this.Database.Connection.State == ConnectionState.Closed)
                {
                    this.Database.Connection.Open();
                    closeCnn = true;
                }
                using (IDbCommand pCmd = this.Database.CreateCommand(this.Query))
                {
                    iResult = pCmd.ExecuteNonQuery();
                }
            }
            catch (Exception ex)
            {
                throw new Exception("Si e' verificato un errore durante l'esecuzione della query: " + this.Query + ". Messaggio: " + ex.Message);
            }
            finally
            {
                if (closeCnn == true) this.Database.Connection.Close();
            }

            return iResult;
        }
        #endregion
    }
}
