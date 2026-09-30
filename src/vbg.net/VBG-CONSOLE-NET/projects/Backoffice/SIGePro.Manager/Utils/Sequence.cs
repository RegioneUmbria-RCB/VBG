using System;
using System.Data;
using PersonalLib2.Data;

namespace Init.SIGePro.Utils
{
	/// <summary>
	/// Descrizione di riepilogo per Sequence.
	/// </summary>
	public class Sequence
	{
		DataBase _db = null;
		string _sequenceName = String.Empty;
        string _idComune = String.Empty;

		const int LIMITE_SUPERIORE_RECORDS = 90000000;

        public string IdComune
        {
            get { return _idComune; }
            set { _idComune = value; }
        }
		/// <summary>
		/// Oggetto PersonalLib2.Data.Database con la connessione attiva ma senza transazione.
		/// </summary>
		public DataBase Db
		{
			get { return _db; }
			set { _db = value; }
		}

		/// <summary>
		/// Nome della sequenza da leggere/aggiornare
		/// </summary>
		public string SequenceName
		{
			get { return _sequenceName; }
			set { _sequenceName = value; }
		}

		/// <summary>
		/// La funzione ritorna il prossimo valore di una sequenza
		/// </summary>
		/// <returns></returns>
		public int NextVal( )
		{
			return NextValInternal(Db, IdComune, SequenceName);
		}

		public static int NextValInternal( DataBase oldDb , string IdComune , string SequenceName)
		{
			int mNextVal = 0;
			string cmdText = String.Empty;

			var newDb = new DataBase(oldDb.ConnectionDetails.ConnectionString, oldDb.ConnectionDetails.ProviderType);

			newDb.Connection.Open();
			newDb.BeginTransaction();

			try
			{
                cmdText = "Update SEQUENCETABLE Set CURRVAL=CURRVAL Where IDCOMUNE=" + newDb.Specifics.QueryParameterName("IDCOMUNE") + " and SEQUENCENAME=" + newDb.Specifics.QueryParameterName("SEQUENCENAME");
                IDbCommand mCommand = newDb.CreateCommand(cmdText);

				mCommand.Parameters.Add(newDb.CreateParameter("IDCOMUNE", IdComune));
				mCommand.Parameters.Add(newDb.CreateParameter("SEQUENCENAME", SequenceName));
				mCommand.ExecuteNonQuery();

				cmdText = "Select CURRVAL From SEQUENCETABLE Where IDCOMUNE=" + newDb.Specifics.QueryParameterName("IDCOMUNE") + " and SEQUENCENAME=" + newDb.Specifics.QueryParameterName("SEQUENCENAME");
                mCommand.CommandText = cmdText;
				object oNextVal = mCommand.ExecuteScalar();

                if (oNextVal == null)
                {
                    //La sequenza non è stata trovata, quindi se è del tipo tabella.colonna viene creata
                    if (SequenceName.IndexOf(".") > -1)
                    {
                        string[] tmpVet = SequenceName.Split( Convert.ToChar("."));
                        string myTableName = tmpVet[0];
                        string myColumnName = tmpVet[1];

                        //viene fatta la max sulla tabella di riferimento
						cmdText = "select max(" + myColumnName + ") as massimo from " + myTableName + " where idcomune =" + newDb.Specifics.QueryParameterName("IDCOMUNE") + " and " + myColumnName + " < " + LIMITE_SUPERIORE_RECORDS;
                        mCommand.CommandText = cmdText;

                        mCommand.Parameters.Clear();
						mCommand.Parameters.Add(newDb.CreateParameter("IDCOMUNE", IdComune));
                        oNextVal = mCommand.ExecuteScalar();

                        if (oNextVal == null || String.IsNullOrEmpty( oNextVal.ToString() ))
                            oNextVal = 0;

                        //si inserisce il valore trovato nella sequencetable
						cmdText = "insert into SEQUENCETABLE (IDCOMUNE,SEQUENCENAME,CURRVAL) values (" + newDb.Specifics.QueryParameterName("IDCOMUNE") + "," + newDb.Specifics.QueryParameterName("SEQUENCENAME") + "," + newDb.Specifics.QueryParameterName("CURRVAL") + ")";
                        mCommand.CommandText = cmdText;

                        mCommand.Parameters.Clear();
						mCommand.Parameters.Add(newDb.CreateParameter("IDCOMUNE", IdComune));
						mCommand.Parameters.Add(newDb.CreateParameter("SEQUENCENAME", SequenceName));
						mCommand.Parameters.Add(newDb.CreateParameter("CURRVAL", oNextVal.ToString()));
                        mCommand.ExecuteNonQuery();
                    }
                    else
                    {
                        //throw new Exception("Sequenza non trovata: IDCOMUNE='" + IdComune + "' and SEQUENCENAME='" + SequenceName + "'");

                        //la sequenza non è del tipo "[TABELLA].[ID]"
                        //e non è presente nella SEQUENCETABLE
                        //quindi si inserisce il valore 0 nella sequencetable
						cmdText = "insert into SEQUENCETABLE (IDCOMUNE,SEQUENCENAME,CURRVAL) values (" + newDb.Specifics.QueryParameterName("IDCOMUNE") + "," + newDb.Specifics.QueryParameterName("SEQUENCENAME") + "," + newDb.Specifics.QueryParameterName("CURRVAL") + ")";
                        mCommand.CommandText = cmdText;

                        mCommand.Parameters.Clear();
						mCommand.Parameters.Add(newDb.CreateParameter("IDCOMUNE", IdComune));
						mCommand.Parameters.Add(newDb.CreateParameter("SEQUENCENAME", SequenceName));
                        oNextVal = 0;
						mCommand.Parameters.Add(newDb.CreateParameter("CURRVAL", oNextVal));
                        mCommand.ExecuteNonQuery();

                        
                    }
                }

				mNextVal = ( oNextVal == null ) ? 1 : Convert.ToInt32( oNextVal ) + 1;

				cmdText = "Update SEQUENCETABLE Set CURRVAL=" + mNextVal + " Where IDCOMUNE=" + newDb.Specifics.QueryParameterName("IDCOMUNE") + " and SEQUENCENAME=" + newDb.Specifics.QueryParameterName("SEQUENCENAME"); ;
                mCommand.CommandText = cmdText;

                mCommand.Parameters.Clear();
				mCommand.Parameters.Add(newDb.CreateParameter("IDCOMUNE", IdComune));
				mCommand.Parameters.Add(newDb.CreateParameter("SEQUENCENAME", SequenceName));

				mCommand.ExecuteNonQuery();

				newDb.CommitTransaction();
			}
			catch( Exception ex )
			{
				newDb.RollbackTransaction();

				throw;
			}
			finally
			{
				newDb.Connection.Close();
			}

			return mNextVal;
		}
	}
}
