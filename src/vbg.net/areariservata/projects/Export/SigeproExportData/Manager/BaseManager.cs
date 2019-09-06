using System;
using System.Data;
using System.Reflection;
using Init.SIGeProExport.Exceptions;
using Init.SIGeProExport.Validator;
using PersonalLib2.Data;
using PersonalLib2.Sql;

namespace Init.SIGeProExport.Manager
{
	/// <summary>
	/// Descrizione di riepilogo per BaseManager.
	/// </summary>
	public class BaseManager : IManager
	{
		public BaseManager(DataBase dataBase)
		{
			_db = dataBase;
		}
		
		protected IDbTransaction Transaction = null;

		#region proprietà per l'accesso al db

		DataBase _db = null;

		virtual protected DataBase db
		{
			get
			{
				return _db;
			}
		}

		#endregion

		public DataClass Insert(DataClass pClass)
		{
			return null;
		}

		public DataClass Update(DataClass pClass)
		{
			return null;
		}

		public DataClass ChildDataIntegrations(DataClass pClass)
		{
			return null;
		}

		public bool RequiredFieldValidate(DataClass pClass )
		{
			bool retVal = false;

			ClassValidator pValidator = new ClassValidator( pClass );
			
			retVal = pValidator.RequiredFieldValidator( _db );

			return retVal;
		}

		public int recordCount(string p_tabella, string p_campo, string p_condwhere)
		{
			
			string SQLfmt = String.Empty; 
			
			try
			{
				SQLfmt = "select count( " + p_campo + " ) as conta from " + p_tabella + " " + p_condwhere;
				using ( IDbCommand command = db.CreateCommand(SQLfmt) )
				{
					object obj = command.ExecuteScalar();
					return ( obj == DBNull.Value ) ? 0 : Convert.ToInt32( obj.ToString() );
				}
				
			}
			catch( System.Exception ex )
			{
				throw new BaseException( "Errore in " + MethodBase.GetCurrentMethod() + ": sql=\"" + SQLfmt + "\" - Exception: " + ex.ToString() , ex );
			}
		}


        public int findMax(string p_tabella, string p_campo, string p_condwhere)
        {

            string SQLfmt = String.Empty;
            bool internalOpen = false;

            try
            {
                SQLfmt = "select max( " + p_campo + " ) as massimo from " + p_tabella;
                if (!String.IsNullOrEmpty(p_condwhere))
                    SQLfmt = SQLfmt + " where " + p_condwhere;
                using (IDbCommand command = db.CreateCommand(SQLfmt))
                {
                    if (db.Connection.State == ConnectionState.Closed)
                    {
                        internalOpen = true;
                        db.Connection.Open();
                    }
                    object obj = command.ExecuteScalar();
                    
                    if (internalOpen)
                        db.Connection.Close();

                    return (obj == DBNull.Value) ? 0 : Convert.ToInt32(obj.ToString());
                }

            }
            catch (System.Exception ex)
            {
                throw new BaseException("Errore in " + MethodBase.GetCurrentMethod() + ": sql=\"" + SQLfmt + "\" - Exception: " + ex.ToString(), ex);
            }

        }
	}
}

