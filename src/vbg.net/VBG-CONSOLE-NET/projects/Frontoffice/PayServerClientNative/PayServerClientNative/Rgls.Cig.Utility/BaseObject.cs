using System;
using System.Configuration;
using System.Data.OleDb;
using System.Xml;

namespace Rgls.Cig.Utility
{
	public class BaseObject
	{
		private const string CONFIGFILE_DB = "RglsCIGDB.xml";

		public Tracer m_oTracer = null;

		protected DBConnection m_oDBConnection = null;

		private bool m_ConnFlag = false;

		private string m_sConnString = "";

		protected string m_sDbType = "";

		protected string m_sDbOwner = "";

		protected string DBOWNERST = "";

		protected bool ISMSSQL = false;

		protected bool ISORACLE = false;

		protected bool ISMYSQL = false;

		protected string FIELDDEL_1 = "";

		protected string FIELDDEL_2 = "";

		protected string STRINGCONCAT = "";

		protected string FORMATODATA = "";

		protected string INVOCASTOREDPROCEDURE = "";

		protected string INVOCASTOREDPROCEDURE_1 = "";

		protected string INVOCASTOREDPROCEDURE_2 = "";

		protected string SQLTOP_PRE = "";

		protected string SQLTOP_IN = "";

		protected string SQLTOP_IN_1 = "";

		protected string SQLTOP_IN_2 = "";

		protected string SQLTOP_POST = "";

		protected string SQLTOP_POST_1 = "";

		protected string SQLMAXRECCOUNT = "";

		public BaseObject(string sLoggerName)
		{
			this.InitializeObject(null, null, sLoggerName);
		}

		public BaseObject(string sSezione, string sLoggerName)
		{
			this.InitializeObject(null, sSezione, sLoggerName);
		}

		public BaseObject(string sDBConfigFile, string sSezione, string sLoggerName)
		{
			if (sDBConfigFile == null)
			{
				sDBConfigFile = "RglsCIGDB.xml";
			}
			this.InitializeObject(sDBConfigFile, sSezione, sLoggerName);
		}

		private bool SetDbCostant()
		{
			bool result;
			if (this.m_sDbType == "MS")
			{
				this.DBOWNERST = "";
				this.ISMSSQL = true;
				this.ISORACLE = false;
				this.ISMYSQL = false;
				this.FIELDDEL_1 = "[";
				this.FIELDDEL_2 = "]";
				this.STRINGCONCAT = "+";
				this.FORMATODATA = "'{ts '''yyyy-MM-dd HH:mm:ss'''}'";
				this.INVOCASTOREDPROCEDURE = "EXEC";
				this.INVOCASTOREDPROCEDURE_1 = "";
				this.INVOCASTOREDPROCEDURE_2 = "";
				this.SQLTOP_PRE = "";
				this.SQLTOP_IN = "TOP 100";
				this.SQLTOP_IN_1 = "TOP";
				this.SQLTOP_IN_2 = "";
				this.SQLTOP_POST = "";
				this.SQLTOP_POST_1 = "";
				this.SQLMAXRECCOUNT = "100";
			}
			else if (this.m_sDbType == "ORA")
			{
				this.DBOWNERST = "STORICO.";
				this.ISMSSQL = false;
				this.ISORACLE = true;
				this.ISMYSQL = false;
				this.FIELDDEL_1 = "\"";
				this.FIELDDEL_2 = "\"";
				this.STRINGCONCAT = "||";
				this.FORMATODATA = "'TO_DATE('''yyyy-MM-dd HH:mm:ss''', ''YYYY-MM-DD HH24:MI:SS'')'";
				this.INVOCASTOREDPROCEDURE = "CALL";
				this.INVOCASTOREDPROCEDURE_1 = "(";
				this.INVOCASTOREDPROCEDURE_2 = ")";
				this.SQLTOP_PRE = "SELECT * FROM (";
				this.SQLTOP_IN = "";
				this.SQLTOP_IN_1 = "";
				this.SQLTOP_IN_2 = "";
				this.SQLTOP_POST = ") WHERE ROWNUM <= 100";
				this.SQLTOP_POST_1 = ") WHERE ROWNUM <=";
				this.SQLMAXRECCOUNT = "100";
			}
			else
			{
				if (!(this.m_sDbType == "MYSQL"))
				{
					result = false;
					return result;
				}
				this.DBOWNERST = "";
				this.ISMSSQL = false;
				this.ISORACLE = false;
				this.ISMYSQL = true;
				this.FIELDDEL_1 = "[";
				this.FIELDDEL_2 = "]";
				this.STRINGCONCAT = "+";
				this.FORMATODATA = "'{ts '''yyyy-MM-dd HH:mm:ss'''}'";
				this.INVOCASTOREDPROCEDURE = "EXEC";
				this.INVOCASTOREDPROCEDURE_1 = "";
				this.INVOCASTOREDPROCEDURE_2 = "";
				this.SQLTOP_PRE = "";
				this.SQLTOP_IN = "TOP 100";
				this.SQLTOP_IN_1 = "TOP";
				this.SQLTOP_IN_2 = "";
				this.SQLTOP_POST = "";
				this.SQLTOP_POST_1 = "";
				this.SQLMAXRECCOUNT = "100";
			}
			result = true;
			return result;
		}

		private void InitializeObject(string sDBConfigFile, string sSezione, string sLoggerName)
		{
			this.m_ConnFlag = false;
			if (sLoggerName != null)
			{
				this.m_oTracer = new Tracer(sLoggerName);
			}
			if (sSezione != null)
			{
				if (sDBConfigFile == null)
				{
					sDBConfigFile = ConfigurationManager.AppSettings["DBConfigFile"];
					if (sDBConfigFile == null || sDBConfigFile.Length == 0)
					{
						if (this.m_oTracer != null)
						{
							this.m_oTracer.traceError("Costruttore BaseObject: chiave in App.config DBConfigFile non trovata o nulla");
						}
						return;
					}
				}
				try
				{
					XmlDocument doc = new XmlDocument();
					doc.Load(sDBConfigFile);
					XmlNode node = doc.DocumentElement.SelectSingleNode(sSezione);
					if (node == null)
					{
						if (this.m_oTracer != null)
						{
							this.m_oTracer.traceError(string.Concat(new string[]
							{
								"Costruttore BaseObject: sezione '",
								sSezione,
								"' in '",
								sDBConfigFile,
								"' non trovata"
							}));
						}
						return;
					}
					foreach (XmlNode nd in node.ChildNodes)
					{
						string name = nd.Name;
						if (name != null)
						{
							if (!(name == "DbType"))
							{
								if (!(name == "DbOwner"))
								{
									if (name == "ConnString")
									{
										this.m_sConnString = nd.InnerText;
									}
								}
								else
								{
									this.m_sDbOwner = nd.InnerText;
								}
							}
							else
							{
								this.m_sDbType = nd.InnerText;
							}
						}
					}
				}
				catch (Exception e)
				{
					if (this.m_oTracer != null)
					{
						this.m_oTracer.traceException(e, "Costruttore BaseObject");
					}
					return;
				}
				if (this.m_sConnString == null || this.m_sConnString.Length == 0)
				{
					if (this.m_oTracer != null)
					{
						this.m_oTracer.traceError("Costruttore BaseObject: nodo 'ConnString' in sezione '" + sSezione + "' non trovato o nullo");
					}
				}
				else if (!this.SetDbCostant())
				{
					if (this.m_oTracer != null)
					{
						this.m_oTracer.traceError("Costruttore BaseObject: nodo 'DbType' in sezione '" + sSezione + "' mancante");
					}
				}
				else
				{
					try
					{
						this.m_oDBConnection = new DBConnection(new OleDbConnection(this.m_sConnString), sLoggerName);
					}
					catch (Exception e)
					{
						if (this.m_oTracer != null)
						{
							this.m_oTracer.traceException(e, "Costruttore BaseObject");
						}
						return;
					}
					this.m_ConnFlag = !this.m_oDBConnection.IsClosed;
				}
			}
		}

		~BaseObject()
		{
			this.CloseDBConnection();
		}

		public void CloseDBConnection()
		{
			try
			{
				if (this.m_oDBConnection != null && !this.m_oDBConnection.IsClosed)
				{
					this.m_oDBConnection.close();
				}
			}
			catch
			{
				if (this.m_oTracer != null)
				{
					this.m_oTracer.traceInfo("CloseDBConnection(), DB gia' chiuso");
				}
			}
			this.m_oDBConnection = null;
		}

		public bool IsReady()
		{
			return this.m_ConnFlag;
		}

		public bool SetDBConnection(BaseObject oExtern)
		{
			this.m_sDbType = oExtern.m_sDbType;
			this.m_sDbOwner = oExtern.m_sDbOwner;
			bool result;
			if (!this.SetDbCostant())
			{
				if (this.m_oTracer != null)
				{
					this.m_oTracer.traceError("BaseObject.SetDBConnection: DbType non definito");
				}
				result = false;
			}
			else
			{
				this.m_ConnFlag = false;
				try
				{
					if (oExtern.m_oDBConnection != null)
					{
						if (oExtern.m_oDBConnection.IsClosed)
						{
							result = this.m_ConnFlag;
							return result;
						}
					}
					this.m_oDBConnection = oExtern.m_oDBConnection;
					this.m_ConnFlag = true;
				}
				catch (Exception e)
				{
					if (this.m_oTracer != null)
					{
						this.m_oTracer.traceException(e, "SetDBConnection()");
					}
					result = this.m_ConnFlag;
					return result;
				}
				result = this.m_ConnFlag;
			}
			return result;
		}
	}
}
