using System;
using System.Data;
using System.Data.OleDb;

namespace Rgls.Cig.Utility
{
	public class DBConnection
	{
		private const string LOGGERNAME = "CIGDBCON";

		private OleDbConnection m_oConnection = null;

		private OleDbTransaction m_oTransaction = null;

		private bool m_bTransactionRollback = false;

		private int m_iTransactionCounter = 0;

		private IsolationLevel m_iIsolationLevel = IsolationLevel.ReadCommitted;

		private Tracer m_oTracer = null;

		public OleDbConnection Connection
		{
			get
			{
				return this.m_oConnection;
			}
		}

		public OleDbTransaction Transaction
		{
			get
			{
				return this.m_oTransaction;
			}
		}

		public bool IsClosed
		{
			get
			{
				bool result;
				try
				{
					if (this.m_oConnection != null)
					{
						result = (this.m_oConnection.State == ConnectionState.Closed);
					}
					else
					{
						result = true;
					}
				}
				catch (Exception)
				{
					result = true;
				}
				return result;
			}
		}

		public IsolationLevel TransactionIsolation
		{
			get
			{
				return this.m_iIsolationLevel;
			}
			set
			{
				this.m_iIsolationLevel = value;
			}
		}

		public DBConnection(OleDbConnection oConnection, string sLoggerName)
		{
			this.m_oTracer = new Tracer(sLoggerName);
			try
			{
				if (oConnection != null)
				{
					oConnection.Open();
					this.m_oTracer.traceDebug("OpenConnection: " + oConnection.ConnectionString);
				}
			}
			catch (Exception e)
			{
				this.m_oTracer.traceException(e, "DBConnection(): Error while setting DB connection (" + oConnection.ConnectionString + ")");
			}
			finally
			{
				this.m_oConnection = oConnection;
			}
		}

		~DBConnection()
		{
			try
			{
				if (this.m_oConnection != null)
				{
					if (!this.IsClosed)
					{
						this.m_oTracer.traceDebug("CloseConnection: " + this.m_oConnection.ConnectionString);
						this.m_oConnection.Close();
					}
					this.m_oConnection = null;
				}
			}
			catch (Exception)
			{
			}
		}

		public bool setDBConnection(OleDbConnection oConnection)
		{
			bool bFlag = false;
			bool result;
			try
			{
				if (oConnection != null)
				{
					if (oConnection.State == ConnectionState.Closed)
					{
						this.m_oTracer.traceInfo("setDBConnection(): Connessione chiusa");
						result = bFlag;
						return result;
					}
					if (this.m_oConnection != null && !this.IsClosed)
					{
						this.m_oTracer.traceDebug("CloseConnection: " + this.m_oConnection.ConnectionString);
						this.m_oConnection.Close();
					}
				}
				this.m_oConnection = oConnection;
				bFlag = true;
			}
			catch (OleDbException e)
			{
				this.m_oTracer.traceException(e, "setDBConnection(): Error while setting DB connection (" + oConnection.ConnectionString + ")");
			}
			result = bFlag;
			return result;
		}

		public void close()
		{
			try
			{
				if (this.m_oConnection != null)
				{
					this.m_oTracer.traceDebug("CloseConnection: " + this.m_oConnection.ConnectionString);
					this.m_oConnection.Close();
				}
			}
			catch (Exception)
			{
			}
		}

		public int BeginTransaction()
		{
			int result;
			try
			{
				if (this.m_iTransactionCounter == 0)
				{
					this.m_oTransaction = this.m_oConnection.BeginTransaction(this.m_iIsolationLevel);
					this.m_bTransactionRollback = false;
				}
				this.m_iTransactionCounter++;
				result = this.m_iTransactionCounter;
			}
			catch (OleDbException e)
			{
				this.m_oTracer.traceException(e, "BeginTransaction(): Exception in BeginTransaction() ");
				result = -1;
			}
			return result;
		}

		public int CommitTransaction()
		{
			int result;
			try
			{
				if (this.m_iTransactionCounter > 0)
				{
					this.m_iTransactionCounter--;
					if (this.m_iTransactionCounter == 0 && !this.m_bTransactionRollback)
					{
						this.m_oTransaction.Commit();
					}
					result = this.m_iTransactionCounter;
				}
				else
				{
					result = -1;
				}
			}
			catch (OleDbException e)
			{
				this.m_oTracer.traceException(e, "CommitTransaction(): Exception in CommitTransaction()");
				result = -1;
			}
			return result;
		}

		public int RollbackTransaction()
		{
			int result;
			try
			{
				if (this.m_iTransactionCounter > 0)
				{
					this.m_iTransactionCounter = 0;
					this.m_oTransaction.Rollback();
					this.m_bTransactionRollback = true;
					result = this.m_iTransactionCounter;
				}
				else
				{
					result = -1;
				}
			}
			catch (OleDbException e)
			{
				this.m_oTracer.traceException(e, "RollbackTransaction(): Exception in RollbackTransaction()");
				result = -1;
			}
			return result;
		}
	}
}
