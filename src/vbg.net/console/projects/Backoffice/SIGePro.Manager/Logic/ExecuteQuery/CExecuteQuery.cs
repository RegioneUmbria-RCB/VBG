using System;
using System.Data;
using PersonalLib2.Data;

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
            Database = db;
        }

		#region Proprietà
		private DataBase pDatabase;
		public DataBase Database
		{
			get
			{
				return pDatabase;
			}
			set
			{
				pDatabase = value;
			}
		}

		private string sQuery;
		public string Query
		{
			get
			{
				return sQuery;
			}
			set
			{
				sQuery = value;
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
				if (Query.ToUpper().StartsWith("SELECT"))
				{
                    using (IDbCommand pCmd = Database.CreateCommand(Query))
                    {
                        DataProviderFactory dpf = new DataProviderFactory(Database.Connection);
                        IDbDataAdapter adaptQuery = dpf.CreateDataAdapter(pCmd);
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
				throw new Exception("Si e' verificato un errore durante l'esecuzione della query: " + Query + ". Messaggio: " + ex.Message);
			}

			return ds;
		}

		public int ExecuteNonQuery()
		{
			int iResult = 0;
            bool closeCnn = false;

            try
            {
                if (Database.Connection.State == ConnectionState.Closed)
                {
                    Database.Connection.Open();
                    closeCnn = true;
                }
                using (IDbCommand pCmd = Database.CreateCommand(Query))
                {
                    iResult = pCmd.ExecuteNonQuery();
                }
            }
            catch (Exception ex)
            {
                throw new Exception("Si e' verificato un errore durante l'esecuzione della query: " + Query + ". Messaggio: " + ex.Message);
            }
            finally
            {
                if (closeCnn == true) Database.Connection.Close();
            }

			return iResult;
		}
		#endregion
	}
}
