using System;
using System.Data;
using Init.Utils;
using PersonalLib2.Data;

namespace Init.SIGeProExport.Utils
{
	/// <summary>
	/// Descrizione di riepilogo per Sequence.
	/// </summary>
	public class Sequence
	{
		DataBase _db = null;
		string _tableName = String.Empty;
		string _fieldName = String.Empty;
		string _whereCondition = String.Empty;
		/// <summary>
		/// Oggetto PersonalLib2.Data.Database con la connessione attiva ma senza transazione.
		/// </summary>
		public DataBase Db
		{
			get { return _db; }
			set { _db = value; }
		}

		/// <summary>
		/// Tabella dalla quale leggere la sequenza
		/// </summary>
		public string TableName
		{
			get { return _tableName; }
			set { _tableName = value; }
		}

		/// <summary>
		/// Nome del campo con la sequenza
		/// </summary>
		public string FieldName
		{
			get { return _fieldName; }
			set { _fieldName = value; }
		}
		/// <summary>
		/// Eventuale connessione where
		/// </summary>
		public string WhereCondition
		{
			get { return _whereCondition; }
			set { _whereCondition = value; }
		}

		/// <summary>
		/// La funzione ritorna il prossimo valore di una sequenza
		/// </summary>
		/// <returns></returns>
		public int NextVal( )
		{
			bool commitTransaction = false;
			bool closeConnection = false;
			int mNextVal = 0;
			string cmdText = String.Empty;

			string pWhereCondition = String.Empty;

			if ( ! StringChecker.IsStringEmpty( _whereCondition ) )
				if ( _whereCondition.ToUpper().IndexOf("WHERE") == -1 )
					pWhereCondition = " WHERE ";

			pWhereCondition += _whereCondition;

			if ( Db.Connection.State != ConnectionState.Open )
			{
				Db.Connection.Open();
				closeConnection = true;
			}

			if ( Db.Transaction == null )
			{
				Db.BeginTransaction();
				commitTransaction = true;
			}

			try
			{
				cmdText = "Update " + _tableName + " Set " + _fieldName + "=" + _fieldName + pWhereCondition;
				Db.CreateCommand( cmdText ).ExecuteNonQuery();

				cmdText = "Select " + _fieldName + " From " + _tableName + pWhereCondition;
				object oNextVal = Db.CreateCommand( cmdText ).ExecuteScalar();
				mNextVal = ( oNextVal == null ) ? 1 : Convert.ToInt32( oNextVal ) + 1;
			
				cmdText = "Update " + _tableName + " Set " + _fieldName + "=" + mNextVal + pWhereCondition;
				Db.CreateCommand( cmdText ).ExecuteNonQuery();

				if ( commitTransaction )
					Db.CommitTransaction();
			}
			catch( Exception ex )
			{
				if ( commitTransaction )
					Db.RollbackTransaction();

				throw ex;
			}
			finally
			{
				if ( closeConnection )
					Db.Connection.Close();
			}

			return mNextVal;
		}
	}
}