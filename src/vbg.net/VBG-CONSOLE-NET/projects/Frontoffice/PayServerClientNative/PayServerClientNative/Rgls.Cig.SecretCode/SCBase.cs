using Rgls.Cig.StringGen;
using Rgls.Cig.Utility;
using System;
using System.Data.OleDb;
using System.Security.Cryptography;
using System.Text;
using System.Web;
using System.Xml;

namespace Rgls.Cig.SecretCode
{
	public class SCBase : BaseObject
	{
		public const string DEFAULT_COMPONENTE = "DEF";

		public const int LEN_COMPONENTE = 3;

		public const int CK_TIMEABS = 1;

		public const int CK_TIMEREL = 2;

		public const int pCK_EXPIRED = -100;

		public const int pCK_BADIPADDR = -101;

		public const int pPS2S_HASHERROR = -1;

		public const int pPS2S_HASHNOTFOUND = -2;

		public const int pPS2S_COMPERROR = -3;

		public const int pPS2S_XMLERROR = -6;

		public const int pPS2S_DATEERROR = -7;

		public const int pPS2S_CREATEHASHERROR = -8;

		public const int pPS2S_HTTPCONNECTION = -9;

		public const int pPS2S_RIDNOTFOUND = -10;

		public const int pPS2S_RIDUSED = -11;

		public const int pPS2S_TIDNOTFOUND = -12;

		public const int pPS2S_TIDUSED = -13;

		public const int pPS2S_ERRORCONN = -14;

		public const int PS2S_CS_STATOOK = 0;

		public const int PS2S_CS_STATOUSED = 1;

		public const int PS2S_CS_STATOKO = 2;

		public const int PS2S_KT_CLEAR = 1;

		public const int PS2S_PROTOCOLVERSION_1 = 1;

		public const int PS2S_PROTOCOLVERSION_2 = 2;

		private const string LOGGERNAME = "CIGSECR";

		private const string SECRET_INVALID = "X";

		protected const char CARATTERE_COOKIE = 'C';

		protected const char CARATTERE_TICKET = 'T';

		protected const char CARATTERE_RICHIESTAUT = 'R';

		protected const char CARATTERE_RICHIESTPAG = 'P';

		protected const char CARATTERE_DATIPAGAMENTO = 'D';

		protected const char CARATTERE_DATIATTIVAZIONESERVIZIO = 'A';

		protected const string TIME_FORMAT = "yyyyMMddHHmm";

		private string sCK_Identificatore = null;

		private string sCK_Username = null;

		private string sCK_Chiave = null;

		private int iCK_Cardtype = -1;

		private string sCK_Creazione = null;

		private DateTime dDataVariazione = new DateTime(1753, 1, 1);

		private string sSecret = null;

		private string sComponente = null;

		private string sDescrizione = null;

		private string sPS2S_Componente = null;

		private string sPS2S_DataBuffer = null;

		private string sPS2S_NetBuffer = null;

		private string sPS2S_HASHR = null;

		private string sPS2S_HASHC = null;

		private string sServerURL = null;

		private string sProxyServer = null;

		private string sProxyPort = null;

		private bool bUseDatabase = false;

		private OleDbDataReader QueryRslt = null;

		private TrasfObj Trasf = null;

		private int iNumeroRighe = 0;

		private int iPS2S_ProtocolVersion = 1;

		protected string ServerURL
		{
			set
			{
				this.sServerURL = value;
			}
		}

		protected string ProxyServer
		{
			set
			{
				this.sProxyServer = value;
			}
		}

		protected string ProxyPort
		{
			set
			{
				this.sProxyPort = value;
			}
		}

		protected int LenComponente
		{
			get
			{
				return 3;
			}
		}

		protected string ComponenteDefault
		{
			get
			{
				return "DEF";
			}
		}

		protected DateTime DataVariazione
		{
			get
			{
				return this.dDataVariazione;
			}
		}

		protected string Descrizione
		{
			get
			{
				return this.sDescrizione;
			}
		}

		protected string CK_Identificatore
		{
			get
			{
				return this.sCK_Identificatore;
			}
		}

		protected string CK_Username
		{
			get
			{
				return this.sCK_Username;
			}
		}

		protected string CK_Chiave
		{
			get
			{
				return this.sCK_Chiave;
			}
		}

		protected int CK_Cardtype
		{
			get
			{
				return this.iCK_Cardtype;
			}
		}

		protected string CK_Creazione
		{
			get
			{
				return this.sCK_Creazione;
			}
		}

		protected string PS2S_NetBuffer
		{
			get
			{
				return this.sPS2S_NetBuffer;
			}
			set
			{
				this.sPS2S_NetBuffer = value;
			}
		}

		protected string PS2S_DataBuffer
		{
			get
			{
				return this.sPS2S_DataBuffer;
			}
			set
			{
				this.sPS2S_DataBuffer = value;
			}
		}

		protected string PS2S_HASHR
		{
			get
			{
				return this.sPS2S_HASHR;
			}
		}

		protected string PS2S_HASHC
		{
			get
			{
				return this.sPS2S_HASHC;
			}
		}

		protected string PS2S_Componente
		{
			get
			{
				return this.sPS2S_Componente;
			}
			set
			{
				this.sPS2S_Componente = value;
			}
		}

		protected string Componente
		{
			get
			{
				return this.sComponente;
			}
			set
			{
				this.sComponente = value;
			}
		}

		protected int NumeroRighe
		{
			get
			{
				return this.iNumeroRighe;
			}
		}

		protected int ProtocolVersion
		{
			get
			{
				return this.iPS2S_ProtocolVersion;
			}
		}

		protected SCBase(string sChiave, int iType) : base("CIGSECR")
		{
			this.InitializaObj(false);
			if (iType == 1)
			{
				this.sSecret = this.Trasf.Cripta(sChiave);
			}
		}

		protected SCBase(bool mode) : base("Sicurezza", "CIGSECR")
		{
			this.InitializaObj(true);
		}

		protected SCBase(string configFile) : base(configFile, "Sicurezza", "CIGSECR")
		{
			this.InitializaObj(true);
		}

		private void InitializaObj(bool bUseDb)
		{
			this.Trasf = new TrasfObj();
			this.bUseDatabase = bUseDb;
			this.sSecret = "X";
			this.sComponente = "DEF";
		}

		protected string GetErrorDescr(int err)
		{
			string result;
			switch (err)
			{
			case -101:
				result = "Indirizzo IP non valido";
				break;
			case -100:
				result = "Coockie spirato";
				break;
			default:
				switch (err)
				{
				case -14:
					result = "Errore di connessione al DB";
					return result;
				case -13:
					result = "TID (response ID) gia' usato";
					return result;
				case -12:
					result = "TID (response ID) non trovato";
					return result;
				case -11:
					result = "RID (request ID) gia' usato";
					return result;
				case -10:
					result = "RID (request ID) non trovato";
					return result;
				case -9:
					result = "Connessione HTTP fallita";
					return result;
				case -8:
					result = "Creazione hash fallito";
					return result;
				case -7:
					result = "Finestra temporale scaduta";
					return result;
				case -6:
					result = "XML non valido";
					return result;
				case -3:
					result = "Componente non valido";
					return result;
				case -2:
					result = "Hash non trovato";
					return result;
				case -1:
					result = "Hash non valido";
					return result;
				}
				result = "Errore sconoscito";
				break;
			}
			return result;
		}

		protected string CreaBufferHash(string sDataBuffer, string sTag)
		{
			string result;
			if (this.sSecret == "X")
			{
				result = "";
			}
			else
			{
				this.m_oTracer.traceInfo("Entr SCBase.CreaBufferHash(), DataBuffer=" + sDataBuffer + " Tag=" + sTag);
				string sTagOrario = (sTag == null) ? this.Trasf.TagOrario() : sTag;
				string sTagOrarioS = this.Trasf.TagOrarioShort(sTagOrario);
				string sBuffer = sTagOrario + sDataBuffer + this.Trasf.Decripta(this.sSecret) + sTagOrarioS;
				byte[] bdata = CBuf.ToByteArray(sBuffer);
				byte[] hash = new MD5CryptoServiceProvider().ComputeHash(bdata);
				string sRet = CBuf.ToBase16String(hash);
				this.m_oTracer.traceInfo("Exit SCBase.CreaBufferHash() Ret=" + sRet);
				result = sRet;
			}
			return result;
		}

		protected int InizializzaSecret(string sComp)
		{
			this.m_oTracer.traceInfo("Entr SCBase.InizializzaSecret(), Component=" + sComp);
			OleDbDataReader oRslt = null;
			string sc = (sComp == null) ? this.sComponente : CStr.Left(sComp, 3);
			string ssql = "SELECT DataOra, Secretstring FROM " + this.m_sDbOwner + "TabellaChiave TabellaChiave WHERE Componente=" + DBUtil.createParam(sc);
			int result;
			try
			{
				Statement oStmt = new Statement(this.m_oDBConnection);
				oRslt = oStmt.executeQuery(ssql);
				if (oRslt.Read())
				{
					oRslt.Close();
					this.m_oTracer.traceWarning("Exit SCBase.SelezionaSecret(), Componente gia' presente");
					result = 512;
					return result;
				}
				oRslt.Close();
				ssql = string.Concat(new string[]
				{
					"INSERT INTO ",
					this.m_sDbOwner,
					"TabellaChiave (Componente, DataOra, Secretstring) VALUES (",
					DBUtil.createParam(sc),
					", ",
					DBUtil.createParam(DBUtil.now()),
					", ",
					DBUtil.createParam(this.Trasf.Cripta("CHIAVE")),
					")"
				});
				if (oStmt.executeUpdate(ssql) <= 0)
				{
					this.m_oTracer.traceError("Exit AecCode.InizializzaSecret(), no one record inserted");
					result = 83886086;
					return result;
				}
			}
			catch (Exception ex)
			{
				if (oRslt != null)
				{
					oRslt.Close();
				}
				this.m_oTracer.traceException(ex, "SecretCode.InizializzaSecret()");
				result = 15728640;
				return result;
			}
			this.m_oTracer.traceInfo("Exit SCBase.InizializzaSecret()");
			result = 0;
			return result;
		}

		protected int SelezionaSecret(string sComp)
		{
			this.m_oTracer.traceInfo("Entr SCBase.SelezionaSecret(), Component=" + sComp);
			int result;
			if (this.bUseDatabase)
			{
				OleDbDataReader oRslt = null;
				string sc = (sComp == null) ? this.sComponente : CStr.Left(sComp, 3);
				string ssql = "SELECT DataOra, Secretstring, Descrizione FROM " + this.m_sDbOwner + "TabellaChiave TabellaChiave WHERE Componente=" + DBUtil.createParam(sc);
				try
				{
					Statement oStmt = new Statement(this.m_oDBConnection);
					oRslt = oStmt.executeQuery(ssql);
					if (!oRslt.Read())
					{
						oRslt.Close();
						this.m_oTracer.traceWarning("Exit SCBase.SelezionaSecret(), Componente non presente");
						result = 512;
						return result;
					}
					this.sSecret = Statement.ReadString(oRslt, "Secretstring");
					this.sDescrizione = Statement.ReadString(oRslt, "Descrizione");
					this.dDataVariazione = Statement.ReadDateTime(oRslt, "DataOra");
					oRslt.Close();
				}
				catch (Exception ex)
				{
					if (oRslt != null)
					{
						oRslt.Close();
					}
					this.m_oTracer.traceException(ex, "SecretCode.SelezionaSecret()");
					result = 15728640;
					return result;
				}
			}
			else
			{
				this.m_oTracer.traceInfo("Entr SCBase.SelezionaSecret(), Component=" + sComp + " - Secret gia' configurato");
			}
			this.m_oTracer.traceInfo("Exit SCBase.SelezionaSecret()");
			result = 0;
			return result;
		}

		protected int AggiornaSecret(string sOldSecret, string sNewSecret, string sComp)
		{
			this.m_oTracer.traceInfo(string.Concat(new string[]
			{
				"Entr SCBase.AggiornaSecret(), OldSecret=",
				sOldSecret,
				" NewSecret=",
				sNewSecret,
				" Component=",
				sComp
			}));
			OleDbDataReader oRslt = null;
			string sc = (sComp == null) ? this.sComponente : CStr.Left(sComp, 3);
			string ssql = "SELECT DataOra, Secretstring FROM " + this.m_sDbOwner + "TabellaChiave TabellaChiave WHERE Componente=" + DBUtil.createParam(sc);
			int result;
			try
			{
				Statement oStmt = new Statement(this.m_oDBConnection);
				oRslt = oStmt.executeQuery(ssql);
				if (!oRslt.Read())
				{
					oRslt.Close();
					this.m_oTracer.traceWarning("Exit SCBase.AggiornaSecret(), Componente non presente");
					result = 512;
					return result;
				}
				string sOld = Statement.ReadString(oRslt, "Secretstring");
				oRslt.Close();
				if (CStr.CompareToIgnoreCase(this.Trasf.Decripta(sOld), sOldSecret) != 0)
				{
					this.m_oTracer.traceInfo("Exit SCBase.AggiornaSecret(), Cmponente differente, OldSecret=" + this.Trasf.Decripta(sOld));
					result = 512;
					return result;
				}
				ssql = string.Concat(new string[]
				{
					"UPDATE ",
					this.m_sDbOwner,
					"TabellaChiave SET DataOra = ",
					DBUtil.createParam(DBUtil.now()),
					", Secretstring = ",
					DBUtil.createParam(this.Trasf.Cripta(sNewSecret)),
					" WHERE Componente=",
					DBUtil.createParam(sc)
				});
				if (oStmt.executeUpdate(ssql) <= 0)
				{
					this.m_oTracer.traceError("Exit SCBase.AggiornaSecret(), no one record updated");
					result = 15728640;
					return result;
				}
				this.sSecret = this.Trasf.Cripta(sNewSecret);
			}
			catch (Exception ex)
			{
				if (oRslt != null)
				{
					oRslt.Close();
				}
				this.m_oTracer.traceException(ex, "SecretCode.AggiornaSecret()");
				result = 15728640;
				return result;
			}
			this.m_oTracer.traceInfo("Exit SCBase.AggiornaSecret(). NewSecret=" + this.sSecret);
			result = 0;
			return result;
		}

		protected int UpdateDescrizione(string sDesc, string sComp)
		{
			this.m_oTracer.traceInfo("Entr SCBase.UpdateDescrizione(), Descr=" + sDesc + "Component=" + sComp);
			string sc = (sComp == null) ? this.sComponente : CStr.Left(sComp, 3);
			string ssql = string.Concat(new string[]
			{
				"UPDATE ",
				this.m_sDbOwner,
				"TabellaChiave SET Descrizione = ",
				DBUtil.createParam(sDesc),
				" WHERE Componente=",
				DBUtil.createParam(sc)
			});
			int result;
			try
			{
				Statement oStmt = new Statement(this.m_oDBConnection);
				if (oStmt.executeUpdate(ssql) <= 0)
				{
					this.m_oTracer.traceError("Exit SCBase.UpdateDescrizione(), no one record updated");
					result = 15728640;
					return result;
				}
			}
			catch (Exception ex)
			{
				this.m_oTracer.traceException(ex, "SecretCode.UpdateDescrizione()");
				result = 15728640;
				return result;
			}
			this.m_oTracer.traceInfo("Exit SCBase.UpdateDescrizione()");
			result = 0;
			return result;
		}

		protected int Sql_Delete(string sComp)
		{
			this.m_oTracer.traceInfo("Entr SCBase.Sql_Delete(), Component=" + sComp);
			string sc = (sComp == null) ? this.sComponente : CStr.Left(sComp, 3);
			string ssql = "DELETE FROM " + this.m_sDbOwner + "TabellaChiave WHERE Componente=" + DBUtil.createParam(sc);
			int result;
			try
			{
				Statement oStmt = new Statement(this.m_oDBConnection);
				if (oStmt.executeUpdate(ssql) <= 0)
				{
					this.m_oTracer.traceInfo("Exit SCBase.Sql_Delete(), componente non trovato");
					result = 15728640;
					return result;
				}
			}
			catch (Exception ex)
			{
				this.m_oTracer.traceException(ex, "Exit SCBase.Sql_Delete()");
				result = 15728640;
				return result;
			}
			this.m_oTracer.traceInfo("Exit SCBase.Sql_Delete()");
			result = 0;
			return result;
		}

		protected int Sql_SelectFirst()
		{
			this.m_oTracer.traceInfo("Entr SCBase.Sql_SelectFirst()");
			string ssql = "SELECT Componente, Descrizione, DataOra FROM " + this.m_sDbOwner + "TabellaChiave ORDER BY Componente";
			int result;
			try
			{
				Statement oStmt = new Statement(this.m_oDBConnection);
				this.QueryRslt = oStmt.executeQuery(ssql);
				if (!this.QueryRslt.Read())
				{
					this.QueryRslt.Close();
					this.m_oTracer.traceWarning("Exit SCBase.Sql_SelectFirst(), nessun componente presente");
					result = 512;
					return result;
				}
				this.sComponente = Statement.ReadString(this.QueryRslt, "Componente");
				this.sDescrizione = Statement.ReadString(this.QueryRslt, "Descrizione");
				this.dDataVariazione = Statement.ReadDateTime(this.QueryRslt, "DataOra");
			}
			catch (Exception ex)
			{
				if (this.QueryRslt != null)
				{
					this.QueryRslt.Close();
				}
				this.m_oTracer.traceException(ex, "SecretCode.Sql_SelectFirst()");
				result = 15728640;
				return result;
			}
			this.m_oTracer.traceInfo("Exit SCBase.Sql_SelectFirst(), Componente=" + this.sComponente);
			result = 0;
			return result;
		}

		protected int Sql_SelectNext()
		{
			this.m_oTracer.traceInfo("Entr SCBase.Sql_SelectNext()");
			int result;
			try
			{
				if (!this.QueryRslt.Read())
				{
					this.QueryRslt.Close();
					this.m_oTracer.traceWarning("Exit SCBase.Sql_SelectNext(), nessun componente presente");
					result = 512;
					return result;
				}
				this.sComponente = Statement.ReadString(this.QueryRslt, "Componente");
				this.sDescrizione = Statement.ReadString(this.QueryRslt, "Descrizione");
				this.dDataVariazione = Statement.ReadDateTime(this.QueryRslt, "DataOra");
			}
			catch (Exception ex)
			{
				if (this.QueryRslt != null)
				{
					this.QueryRslt.Close();
				}
				this.m_oTracer.traceException(ex, "SecretCode.Sql_SelectNext()");
				result = 15728640;
				return result;
			}
			this.m_oTracer.traceInfo("Exit SCBase.Sql_SelectNext(), Componente=" + this.sComponente);
			result = 0;
			return result;
		}

		protected int PS2S_Net2DataBuffer(string sNetBuffer, string sCompDefault, int lFinestraTemporale)
		{
			this.m_oTracer.traceInfo(string.Concat(new object[]
			{
				"Entr SCBase.PS2S_Net2DataBuffer(), NetBuff=",
				sNetBuffer,
				", Finestra Temporale=",
				lFinestraTemporale
			}));
			int result;
			if (this.bUseDatabase && !base.IsReady())
			{
				this.m_oTracer.traceError("Exit SCBase.PS2S_Net2DataBuffer(): DB connection error");
				result = -14;
			}
			else
			{
				string sProtocolVersion = this.MySelectSingleNode(sNetBuffer, "ProtocolVersion");
				this.iPS2S_ProtocolVersion = ((sProtocolVersion.Length == 0) ? 1 : Convert.ToInt32(sProtocolVersion));
				this.m_oTracer.traceInfo("Sec: Entr PS2S_Net2DataBuffer, Protocol version =" + this.iPS2S_ProtocolVersion);
				string sBufferRicevuto;
				if (this.iPS2S_ProtocolVersion == 1)
				{
					sBufferRicevuto = CStr.Substitute(sNetBuffer, "%26", "&");
				}
				else
				{
					sBufferRicevuto = sNetBuffer;
				}
				this.sPS2S_DataBuffer = "";
				this.sPS2S_HASHR = "";
				this.sPS2S_HASHC = "";
				if (this.MySelectSingleNode(sBufferRicevuto, "CodicePortale") == "")
				{
					this.sPS2S_Componente = sCompDefault;
				}
				else
				{
					this.sPS2S_Componente = this.MySelectSingleNode(sBufferRicevuto, "CodicePortale");
				}
				string TagOrario = this.MySelectSingleNode(sBufferRicevuto, "TagOrario");
				if (TagOrario == "")
				{
					this.m_oTracer.traceError("Exit SCBase.PS2S_Net2DataBuffer(): Extract TagOrario Error");
					result = -6;
				}
				else
				{
					string BufferData = this.MySelectSingleNode(sBufferRicevuto, "BufferDati");
					if (BufferData == "")
					{
						this.m_oTracer.traceError("Exit SCBase.PS2S_Net2DataBuffer(): Extract BufferDati Error");
						result = -6;
					}
					else
					{
						DateTime DataRic = new DateTime(Convert.ToInt32(TagOrario.Substring(0, 4), 10), Convert.ToInt32(TagOrario.Substring(4, 2), 10), Convert.ToInt32(TagOrario.Substring(6, 2), 10), Convert.ToInt32(TagOrario.Substring(8, 2), 10), Convert.ToInt32(TagOrario.Substring(10, 2), 10), 0);
						DateTime DataOd = new DateTime(DateTime.Now.Year, DateTime.Now.Month, DateTime.Now.Day, DateTime.Now.Hour, DateTime.Now.Minute, 0);
						int MinutiDiff = (int)Math.Abs(DataOd.Subtract(DataRic).TotalMinutes);
						if (MinutiDiff > lFinestraTemporale)
						{
							this.m_oTracer.traceError("Exit SCBase.PS2S_Net2DataBuffer(): Finestra temporale scaduta");
							result = -7;
						}
						else if (this.SelezionaSecret(this.sPS2S_Componente) != 0)
						{
							this.m_oTracer.traceError("Exit SCBase.PS2S_Net2DataBuffer(): Application Initializzation ErrorHash Verify Error");
							result = -3;
						}
						else
						{
							this.sPS2S_HASHC = this.CreaBufferHash(BufferData, TagOrario);
							if (this.sPS2S_HASHC == "")
							{
								this.m_oTracer.traceError("Exit SCBase.PS2S_Net2DataBuffer(): Error Hash creation");
								result = -8;
							}
							else
							{
								this.sPS2S_HASHR = this.MySelectSingleNode(sBufferRicevuto, "Hash");
								if (this.sPS2S_HASHR == "")
								{
									this.m_oTracer.traceError("Exit SCBase.PS2S_Net2DataBuffer(): Extract Hash Error");
									result = -2;
								}
								else if (CStr.CompareToIgnoreCase(this.sPS2S_HASHC, this.sPS2S_HASHR) != 0)
								{
									this.m_oTracer.traceError(string.Concat(new string[]
									{
										"Exit SCBase.PS2S_Net2DataBuffer(): Hash Verify Error, HashRicevuto=",
										this.sPS2S_HASHR,
										" HashCalcolato=",
										this.sPS2S_HASHC,
										" Buffer dati=",
										BufferData
									}));
									result = -1;
								}
								else
								{
									if (this.iPS2S_ProtocolVersion == 2)
									{
										this.sPS2S_DataBuffer = CBuf.DecodeBase64(BufferData);
									}
									else
									{
										this.sPS2S_DataBuffer = BufferData;
									}
									this.m_oTracer.traceInfo("Exit SCBase.PS2S_Net2DataBuffer(), sPS2S_DataBuffer=" + this.sPS2S_DataBuffer);
									result = 0;
								}
							}
						}
					}
				}
			}
			return result;
		}

		protected int PS2S_Data2NetBuffer(string sDataBuffer, string sComp, DateTime dt, int iProtocolVersion)
		{
			this.m_oTracer.traceInfo("Entr SCBase.PS2S_Data2NetBuffer(), DataBuff=" + sDataBuffer);
			int result;
			if (this.bUseDatabase && !base.IsReady())
			{
				this.m_oTracer.traceError("Exit SCBase.PS2S_Data2NetBuffer(): DB connection error");
				result = -14;
			}
			else
			{
				int ret = this.SelezionaSecret(sComp);
				if (ret != 0)
				{
					this.sPS2S_NetBuffer = "";
					this.m_oTracer.traceError("Exit SCBase.PS2S_Data2NetBuffer(): Error secret key Component=" + sComp);
					result = -3;
				}
				else
				{
					string sBufferDati = (iProtocolVersion == 2) ? CBuf.EncodeBase64(sDataBuffer) : sDataBuffer;
					string TagOrario = string.Format("{0:D4}{1:D2}{2:D2}{3:D2}{4:D2}", new object[]
					{
						dt.Year,
						dt.Month,
						dt.Day,
						dt.Hour,
						dt.Minute
					});
					string HashCalc = this.CreaBufferHash(sBufferDati, TagOrario);
					if (HashCalc == "")
					{
						this.m_oTracer.traceError("Exit SCBase.PS2S_Data2NetBuffer(): Error Hash creation");
						result = -8;
					}
					else
					{
						string c = "<Buffer>";
						if (iProtocolVersion == 2)
						{
							c += "<ProtocolVersion>2</ProtocolVersion>";
						}
						string text = c;
						c = string.Concat(new string[]
						{
							text,
							"<TagOrario>",
							TagOrario,
							"</TagOrario><CodicePortale>",
							sComp,
							"</CodicePortale><BufferDati>",
							sBufferDati,
							"</BufferDati><Hash>",
							HashCalc,
							"</Hash></Buffer>"
						});
						if (iProtocolVersion == 2)
						{
							this.sPS2S_NetBuffer = c;
						}
						else
						{
							this.sPS2S_NetBuffer = CStr.Substitute(c, "&", "%26");
						}
						this.m_oTracer.traceInfo("Exit SCBase.PS2S_Data2NetBuffer(), sPS2S_NetBuffer=" + this.sPS2S_NetBuffer);
						result = 0;
					}
				}
			}
			return result;
		}

		protected int CK_CS_CreateCK(string sUsername, string sChiave, string sIPClient, int iCardType)
		{
			this.m_oTracer.traceInfo("Entr SCBase.CK_CS_CreateCK(), Username=" + sUsername);
			StrGen oSG = new StrGen();
			string Identificatore = oSG.Identificativo('C');
			DateTime oCreazione = DateTime.Now;
			string Buffer = string.Concat(new object[]
			{
				"<SSO><USER>",
				sUsername,
				"</USER><CHIAVE>",
				sChiave,
				"</CHIAVE><CARDTYPE>",
				iCardType,
				"</CARDTYPE><DATACREAZIONE>",
				oCreazione.ToString("yyyyMMddHHmm"),
				"</DATACREAZIONE></SSO>"
			});
			string ssql = string.Concat(new string[]
			{
				"INSERT INTO ",
				this.m_sDbOwner,
				"TabellaIdentificativo (Identificativo, DataOra, Stato, Buffer, IPClient) VALUES (",
				DBUtil.createParam(Identificatore),
				", ",
				DBUtil.createParam(oCreazione),
				", ",
				DBUtil.createParam(1),
				", ",
				DBUtil.createParam(Buffer),
				", ",
				DBUtil.createParam(sIPClient),
				")"
			});
			int result;
			try
			{
				Statement oStmt = new Statement(this.m_oDBConnection);
				if (oStmt.executeUpdate(ssql) != 1)
				{
					this.m_oTracer.traceError("Exit SCBase.CK_CS_CreateCK(), no record inserted");
					result = 83886086;
					return result;
				}
			}
			catch (Exception ex)
			{
				this.m_oTracer.traceException(ex, "Exit SCBase.CK_CS_CreateCK()");
				result = 15728640;
				return result;
			}
			this.sCK_Chiave = sChiave;
			this.sCK_Username = sUsername;
			this.sCK_Identificatore = this.Trasf.Cripta(Identificatore);
			this.sCK_Creazione = oCreazione.ToString("yyyyMMddHHmm");
			this.iCK_Cardtype = iCardType;
			this.m_oTracer.traceInfo("Exit SCBase.CK_CS_CreateCK(), CK_Identificatore=" + this.sCK_Identificatore);
			result = 0;
			return result;
		}

		protected int CK_CS_VerifyCK(string sIdentificatore, string sIPClient, int iFinestraTemporale, int iModalita)
		{
			string sBUser = null;
			string sBChiave = null;
			string sBCardtype = null;
			string sBDataCreazione = null;
			this.m_oTracer.traceInfo("Entr SCBase.CK_CS_VerifyCK(), Identificatore=" + sIdentificatore);
			string Identificatore = this.Trasf.Decripta(sIdentificatore);
			string ssql = "SELECT Identificativo, DataOra, Stato, Buffer, IPClient FROM " + this.m_sDbOwner + "TabellaIdentificativo TabellaIdentificativo WHERE Identificativo=" + DBUtil.createParam(Identificatore);
			int result;
			try
			{
				Statement oStmt = new Statement(this.m_oDBConnection);
				OleDbDataReader oRslt = oStmt.executeQuery(ssql);
				if (!oRslt.Read())
				{
					oRslt.Close();
					this.m_oTracer.traceWarning("Exit SCBase.CK_CS_VerifyCK(), identificativo non trovato.");
					result = -12;
					return result;
				}
				int iStato = Statement.ReadInt(oRslt, "Stato");
				string sBuffer = Statement.ReadString(oRslt, "Buffer");
				string sBIPClient = Statement.ReadString(oRslt, "IPClient");
				DateTime oInizio = (iModalita == 2) ? Statement.ReadDateTime(oRslt, "DataOra") : new DateTime(1753, 1, 1);
				oRslt.Close();
				if (iStato < 1)
				{
					this.m_oTracer.traceInfo(string.Concat(new object[]
					{
						"Exit SCBase.CK_CS_VerifyCK(), Cookie ",
						Identificatore,
						" in stato ",
						iStato
					}));
					result = -100;
					return result;
				}
				sBUser = this.SelectSingleNode(sBuffer, "USER");
				sBChiave = this.SelectSingleNode(sBuffer, "CHIAVE");
				sBCardtype = this.SelectSingleNode(sBuffer, "CARDTYPE");
				sBDataCreazione = this.SelectSingleNode(sBuffer, "DATACREAZIONE");
				try
				{
					if (iModalita == 1)
					{
						oInizio = DateTime.ParseExact(sBDataCreazione, "yyyyMMddHHmm", null);
					}
				}
				catch (Exception ex)
				{
					this.m_oTracer.traceException(ex, "Exit SCBase.CK_CS_VerifyCK()");
					result = 256;
					return result;
				}
				if (oInizio == new DateTime(1753, 1, 1))
				{
					this.m_oTracer.traceError("Exit SCBase.CK_CS_VerifyCK(), Modalita' errata = " + iModalita);
					result = 256;
					return result;
				}
				if (DBUtil.dateDiffMin(DateTime.Now, oInizio) > (long)iFinestraTemporale)
				{
					this.m_oTracer.traceError("Exit SCBase.CK_CS_VerifyCK(), Finestra temporale scaduta");
					result = -100;
					return result;
				}
				if (sIPClient != null)
				{
					if (CStr.CompareToIgnoreCase(sIPClient, sIPClient) != 0)
					{
						this.m_oTracer.traceError("Exit SCBase.CK_CS_VerifyCK(), IP errato: " + sIPClient + "/" + sIPClient);
						result = -101;
						return result;
					}
				}
			}
			catch (Exception ex)
			{
				this.m_oTracer.traceException(ex, "SecretCode.CK_CS_VerifyCK()");
				result = 15728640;
				return result;
			}
			this.sCK_Identificatore = sIdentificatore;
			this.sCK_Chiave = sBChiave;
			this.sCK_Creazione = sBDataCreazione;
			this.sCK_Username = sBUser;
			this.iCK_Cardtype = Convert.ToInt32(sBCardtype);
			this.m_oTracer.traceInfo("Exit SCBase.CK_CS_VerifyCK(), Identificatore=" + sIdentificatore);
			result = 0;
			return result;
		}

		protected int CK_CS_RefreshCK(string sIdentificatore)
		{
			this.m_oTracer.traceInfo("Entr SCBase.CK_CS_RefreshCK(), Identificatore=" + sIdentificatore);
			string Identificatore = this.Trasf.Decripta(sIdentificatore);
			string ssql = string.Concat(new string[]
			{
				"UPDATE ",
				this.m_sDbOwner,
				"TabellaIdentificativo SET Stato=Stato+1, DataOra=",
				DBUtil.createParam(DBUtil.now()),
				" WHERE Identificativo=",
				DBUtil.createParam(Identificatore)
			});
			int result;
			try
			{
				Statement oStmt = new Statement(this.m_oDBConnection);
				if (oStmt.executeUpdate(ssql) != 1)
				{
					this.m_oTracer.traceError("Exit SCBase.CK_CS_RefreshCK(), no record updated");
					result = 83886086;
					return result;
				}
			}
			catch (Exception ex)
			{
				this.m_oTracer.traceException(ex, "Exit SCBase.CK_CS_RefreshCK()");
				result = 15728640;
				return result;
			}
			this.m_oTracer.traceInfo("Exit SCBase.CK_CS_RefreshCK(), Identificatore=" + sIdentificatore);
			result = 0;
			return result;
		}

		protected int CK_CS_DeleteCK(string sIdentificatore)
		{
			this.m_oTracer.traceInfo("Entr SCBase.CK_CS_DeleteCK(), Identificatore=" + sIdentificatore);
			string Identificatore = this.Trasf.Decripta(sIdentificatore);
			string ssql = string.Concat(new string[]
			{
				"UPDATE ",
				this.m_sDbOwner,
				"TabellaIdentificativo SET Stato=0, DataOra=",
				DBUtil.createParam(DBUtil.now()),
				" WHERE Identificativo=",
				DBUtil.createParam(Identificatore)
			});
			int result;
			try
			{
				Statement oStmt = new Statement(this.m_oDBConnection);
				if (oStmt.executeUpdate(ssql) != 1)
				{
					this.m_oTracer.traceError("Exit SCBase.CK_CS_DeleteCK(), no record updated");
					result = 83886086;
					return result;
				}
			}
			catch (Exception ex)
			{
				this.m_oTracer.traceException(ex, "Exit SCBase.CK_CS_DeleteCK()");
				result = 15728640;
				return result;
			}
			this.m_oTracer.traceInfo("Exit SCBase.CK_CS_DeleteCK(), Identificatore=" + sIdentificatore);
			result = 0;
			return result;
		}

		private int pPS2S_Client_Request2RID(string sRequestBuffer, string sComp, DateTime dt, int iProtocolVersion)
		{
			this.m_oTracer.traceInfo("Entr SCBase.pPS2S_Client_Request2RID(), RequestBuffer=" + sRequestBuffer);
			int ret = this.PS2S_Data2NetBuffer(sRequestBuffer, sComp, dt, iProtocolVersion);
			int result;
			if (ret != 0)
			{
				this.m_oTracer.traceError("Exit SCBase.pPS2S_Client_Request2RID(), ret=" + ret);
				result = ret;
			}
			else
			{
				URLWorker oURLWorker = new URLWorker();
				oURLWorker.URL = this.sServerURL;
				oURLWorker.ProxyServer = this.sProxyServer;
				oURLWorker.ProxyPort = this.sProxyPort;
				oURLWorker.BufferIn = "buffer=" + HttpUtility.UrlEncode(this.sPS2S_NetBuffer, Encoding.Default);
				string sret = oURLWorker.DoRequest("POST");
				if (sret != null)
				{
					this.m_oTracer.traceError("Exit SCBase.pPS2S_Client_Request2RID(), impossibile raggiungere il webserver di cig: " + this.sServerURL + " " + sret);
					result = -9;
				}
				else
				{
					ret = this.PS2S_Data2NetBuffer(oURLWorker.BufferOut, sComp, dt, iProtocolVersion);
					this.m_oTracer.traceInfo("Exit SCBase.pPS2S_Client_Request2RID(), ret=" + ret);
					result = ret;
				}
			}
			return result;
		}

		protected int PS2S_PS_Request2RID(string sRequestBuffer, string sCompDefault, int lFinestraTemporale)
		{
			return this.pPS2S_Server_Request2RID(sRequestBuffer, sCompDefault, lFinestraTemporale, 'P');
		}

		protected int PS2S_CS_Request2RID(string sRequestBuffer, string sCompDefault, int lFinestraTemporale)
		{
			return this.pPS2S_Server_Request2RID(sRequestBuffer, sCompDefault, lFinestraTemporale, 'R');
		}

		private int pPS2S_Server_Request2RID(string sRequestBuffer, string sCompDefault, int lFinestraTemporale, char cId)
		{
			this.m_oTracer.traceInfo("Entr SCBase.pPS2S_Server_Request2RID(), RequestBuffer=" + sRequestBuffer);
			int ret = this.PS2S_Net2DataBuffer(sRequestBuffer, sCompDefault, lFinestraTemporale);
			int result;
			if (ret != 0)
			{
				this.m_oTracer.traceError("Exit SCBase.pPS2S_Server_Request2RID() - NET2DATA, ret=" + ret);
				result = ret;
			}
			else
			{
				StrGen oSG = new StrGen();
				string Identificatore = oSG.Identificativo(cId);
				string ssql = string.Concat(new string[]
				{
					"INSERT INTO ",
					this.m_sDbOwner,
					"TabellaIdentificativo (Identificativo, DataOra, Stato, Buffer) VALUES (",
					DBUtil.createParam(Identificatore),
					", ",
					DBUtil.createParam(DBUtil.now()),
					", ",
					DBUtil.createParam(0),
					", ",
					DBUtil.createParam(sRequestBuffer),
					")"
				});
				try
				{
					Statement oStmt = new Statement(this.m_oDBConnection);
					if (oStmt.executeUpdate(ssql) != 1)
					{
						this.m_oTracer.traceError("Exit SCBase.pPS2S_Server_Request2RID(), no record inserted");
						result = 83886086;
						return result;
					}
				}
				catch (Exception ex)
				{
					this.m_oTracer.traceException(ex, "Exit SCBase.pPS2S_Server_Request2RID()");
					result = 15728640;
					return result;
				}
				this.sPS2S_DataBuffer = Identificatore;
				this.m_oTracer.traceInfo("Exit SCBase.pPS2S_Server_Request2RID(), sPS2S_DataBuffer=" + this.sPS2S_DataBuffer);
				result = 0;
			}
			return result;
		}

		protected int PS2S_PS_RID2Request(string sRIDBuffer, string sCompDefault, int lFinestraTemporale)
		{
			return this.pPS2S_Server_RID2Request(sRIDBuffer, sCompDefault, lFinestraTemporale);
		}

		protected int PS2S_CS_RID2Request(string sRIDBuffer, string sCompDefault, int lFinestraTemporale)
		{
			return this.pPS2S_Server_RID2Request(sRIDBuffer, sCompDefault, lFinestraTemporale);
		}

		protected int PS2S_CS_AID2Activation(string sRIDBuffer, string sCompDefault, int lFinestraTemporale)
		{
			return this.pPS2S_Server_AID2Activation(sRIDBuffer, sCompDefault, lFinestraTemporale);
		}

		private int pPS2S_Server_RID2Request(string sRIDBuffer, string sCompDefault, int lFinestraTemporale)
		{
			this.m_oTracer.traceInfo("Entr SCBase.pPS2S_Server_RID2Request(), RIDBuffer=" + sRIDBuffer);
			int ret = this.PS2S_Net2DataBuffer(sRIDBuffer, sCompDefault, lFinestraTemporale);
			int result;
			if (ret != 0)
			{
				result = ret;
			}
			else
			{
				string Identificatore = this.sPS2S_DataBuffer;
				string ssql = "SELECT Identificativo, DataOra, Stato, Buffer FROM " + this.m_sDbOwner + "TabellaIdentificativo TabellaIdentificativo WHERE Identificativo=" + DBUtil.createParam(Identificatore);
				string sBuffer;
				try
				{
					Statement oStmt = new Statement(this.m_oDBConnection);
					this.QueryRslt = oStmt.executeQuery(ssql);
					if (!this.QueryRslt.Read())
					{
						this.QueryRslt.Close();
						this.m_oTracer.traceError("Exit SCBase.PS2S_CS_RID2Request(), Error on ExecuteQuery");
						result = -10;
						return result;
					}
					int iStato = Statement.ReadInt(this.QueryRslt, "Stato");
					sBuffer = Statement.ReadString(this.QueryRslt, "Buffer");
					this.QueryRslt.Close();
					if (iStato != 0)
					{
						this.m_oTracer.traceError(string.Concat(new object[]
						{
							"Exit SCBase.pPS2S_Server_RID2Request(), Request ",
							Identificatore,
							" in stato ",
							iStato
						}));
						result = -100;
						return result;
					}
					ssql = string.Concat(new string[]
					{
						"UPDATE ",
						this.m_sDbOwner,
						"TabellaIdentificativo SET Stato=",
						DBUtil.createParam(1),
						" WHERE Identificativo=",
						DBUtil.createParam(Identificatore)
					});
					if (oStmt.executeUpdate(ssql) != 1)
					{
						this.m_oTracer.traceError("Exit SCBase.pPS2S_Server_RID2Request() - SQL, ret=" + ret);
						result = 83886084;
						return result;
					}
				}
				catch (Exception ex)
				{
					if (this.QueryRslt != null)
					{
						this.QueryRslt.Close();
					}
					this.m_oTracer.traceException(ex, "SecretCode.pPS2S_Server_RID2Request()");
					result = 15728640;
					return result;
				}
				ret = this.PS2S_Net2DataBuffer(sBuffer, sCompDefault, lFinestraTemporale);
				if (ret != 0)
				{
					this.m_oTracer.traceError("Exit SCBase.pPS2S_Server_RID2Request() - NET2DATA, ret=" + ret);
					result = ret;
				}
				else
				{
					this.m_oTracer.traceInfo("Exit SCBase.pPS2S_Server_RID2Request()");
					result = 0;
				}
			}
			return result;
		}

		private int pPS2S_Server_AID2Activation(string sAIDBuffer, string sCompDefault, int lFinestraTemporale)
		{
			this.m_oTracer.traceInfo("Entr SCBase.PS2S_CS_AID2Activation(), AIDBuffer=" + sAIDBuffer);
			int res = this.PS2S_Net2DataBuffer(sAIDBuffer, sCompDefault, lFinestraTemporale);
			int result;
			if (res != 0)
			{
				result = res;
			}
			else
			{
				string Identificatore = this.sPS2S_DataBuffer;
				string ComponenteRichiesta = this.sPS2S_Componente;
				try
				{
					Statement oStmt = new Statement(this.m_oDBConnection);
					string ssql = "SELECT Identificativo, DataOra, Stato, Buffer FROM " + this.m_sDbOwner + "TabellaIdentificativo TabellaIdentificativo WHERE Identificativo=" + DBUtil.createParam(Identificatore);
					this.QueryRslt = oStmt.executeQuery(ssql);
					if (!this.QueryRslt.Read())
					{
						this.m_oTracer.traceError("Exit SCBase.PS2S_CS_AID2Activation(), Error on ExecuteQuery");
						result = -10;
						return result;
					}
					if (Statement.ReadInt(this.QueryRslt, "Stato") != 0)
					{
						this.QueryRslt.Close();
						this.m_oTracer.traceError(string.Concat(new object[]
						{
							"Exit SCBase.PS2S_CS_AID2Activation(), AID ",
							Identificatore,
							" in stato ",
							Statement.ReadInt(this.QueryRslt, "Stato")
						}));
						result = -11;
						return result;
					}
					string Activation = Statement.ReadString(this.QueryRslt, "Buffer");
					this.QueryRslt.Close();
					string sSqlStmt = string.Concat(new string[]
					{
						"UPDATE ",
						this.m_sDbOwner,
						"TabellaIdentificativo SET Stato=",
						DBUtil.createParam(1),
						" WHERE Identificativo=",
						DBUtil.createParam(Identificatore)
					});
					int ret = oStmt.executeUpdate(sSqlStmt);
					if (ret != 1)
					{
						this.m_oTracer.traceError("Exit SCBase.PS2S_CS_AID2Activation() - SQL, ret=" + ret);
						result = 83886084;
						return result;
					}
					res = this.PS2S_Data2NetBuffer(Activation, ComponenteRichiesta, DateTime.Now, this.iPS2S_ProtocolVersion);
					if (res != 0)
					{
						this.m_oTracer.traceError("Exit SCBase.PS2S_CS_AID2Activation() - NET2DATA, ret=" + res);
						result = res;
						return result;
					}
				}
				catch (Exception ex)
				{
					this.m_oTracer.traceException(ex, "Exit SCBase.PS2S_CS_AID2Activation()");
					result = 15728640;
					return result;
				}
				this.m_oTracer.traceInfo("Exit SCBase.PS2S_CS_AID2Activation()");
				result = 0;
			}
			return result;
		}

		protected int PS2S_PS_Ticket2TID(string sTicketBuffer)
		{
			return this.pPS2S_Server_Ticket2TID(sTicketBuffer, 'D');
		}

		protected int PS2S_CS_Ticket2TID(string sTicketBuffer)
		{
			return this.pPS2S_Server_Ticket2TID(sTicketBuffer, 'T');
		}

		protected int PS2S_CS_Activation2AID(string sActivationBuffer)
		{
			return this.pPS2S_Server_Ticket2TID(sActivationBuffer, 'A');
		}

		private int pPS2S_Server_Ticket2TID(string sTicketBuffer, char cId)
		{
			this.m_oTracer.traceInfo("Entr  SecretCode.pPS2S_Server_Ticket2TID(), TicketBuffer=" + sTicketBuffer);
			StrGen oSG = new StrGen();
			string Identificatore = oSG.Identificativo(cId);
			string ssql = string.Concat(new string[]
			{
				"INSERT INTO ",
				this.m_sDbOwner,
				"TabellaIdentificativo (Identificativo, DataOra, Stato, Buffer) VALUES (",
				DBUtil.createParam(Identificatore),
				", ",
				DBUtil.createParam(DBUtil.now()),
				", ",
				DBUtil.createParam(0),
				", ",
				DBUtil.createParam(sTicketBuffer),
				")"
			});
			int result;
			try
			{
				Statement oStmt = new Statement(this.m_oDBConnection);
				if (oStmt.executeUpdate(ssql) != 1)
				{
					this.m_oTracer.traceError("Exit SCBase.pPS2S_Server_Ticket2TID(), no record inserted");
					result = 83886086;
					return result;
				}
			}
			catch (Exception ex)
			{
				this.m_oTracer.traceException(ex, "Exit SCBase.pPS2S_Server_Ticket2TID()");
				result = 15728640;
				return result;
			}
			this.sPS2S_NetBuffer = Identificatore;
			this.m_oTracer.traceInfo("Exit SCBase.pPS2S_Server_Ticket2TID(), PS2S_NetBuffer=" + this.sPS2S_NetBuffer);
			result = 0;
			return result;
		}

		protected int PS2S_PC_Request2RID(string sRequestBuffer, string sComp, DateTime dt, int iProtocolVersion)
		{
			return this.pPS2S_Client_Request2RID(sRequestBuffer, sComp, dt, iProtocolVersion);
		}

		protected int PS2S_PC_Request2RID(string sRequestBuffer, string sComp, DateTime dt)
		{
			return this.pPS2S_Client_Request2RID(sRequestBuffer, sComp, dt, 2);
		}

		protected int PS2S_CC_Request2RID(string sRequestBuffer, string sComp, DateTime dt, int iProtocolVersion)
		{
			return this.pPS2S_Client_Request2RID(sRequestBuffer, sComp, dt, iProtocolVersion);
		}

		protected int PS2S_CC_Request2RID(string sRequestBuffer, string sComp, DateTime dt)
		{
			return this.pPS2S_Client_Request2RID(sRequestBuffer, sComp, dt, 2);
		}

		protected int PS2S_PC_PID2Data(string sIdBuffer, string sComp, DateTime dt, int lFinestraTemporale, int iProtocolVersion)
		{
			return this.pPS2S_Client_TID2Ticket(sIdBuffer, sComp, dt, lFinestraTemporale, iProtocolVersion);
		}

		protected int PS2S_PC_PID2Data(string sIdBuffer, string sComp, DateTime dt, int lFinestraTemporale)
		{
			return this.pPS2S_Client_TID2Ticket(sIdBuffer, sComp, dt, lFinestraTemporale, 2);
		}

		protected int PS2S_CC_TID2Ticket(string sIdBuffer, string sComp, DateTime dt, int lFinestraTemporale, int iProtocolVersion)
		{
			return this.pPS2S_Client_TID2Ticket(sIdBuffer, sComp, dt, lFinestraTemporale, iProtocolVersion);
		}

		protected int PS2S_CC_TID2Ticket(string sIdBuffer, string sComp, DateTime dt, int lFinestraTemporale)
		{
			return this.pPS2S_Client_TID2Ticket(sIdBuffer, sComp, dt, lFinestraTemporale, 2);
		}

		protected int PS2S_CC_AID2Activation(string sIdBuffer, string sComp, DateTime dt, int lFinestraTemporale, int iProtocolVersion)
		{
			return this.pPS2S_Client_TID2Ticket(sIdBuffer, sComp, dt, lFinestraTemporale, iProtocolVersion);
		}

		protected int PS2S_CC_AID2Activation(string sIdBuffer, string sComp, DateTime dt, int lFinestraTemporale)
		{
			return this.pPS2S_Client_TID2Ticket(sIdBuffer, sComp, dt, lFinestraTemporale, 2);
		}

		private int pPS2S_Client_TID2Ticket(string sIdBuffer, string sComp, DateTime dt, int lFinestraTemporale, int iProtocolVersion)
		{
			this.m_oTracer.traceInfo("Entr SCBase.pPS2S_Client_TID2Ticket(), IdBuffer=" + sIdBuffer);
			int ret = this.PS2S_Data2NetBuffer(sIdBuffer, sComp, dt, iProtocolVersion);
			int result;
			if (ret != 0)
			{
				this.m_oTracer.traceError("Exit SCBase.pPS2S_Client_TID2Ticket() - DATA2NET, ret=" + ret);
				result = ret;
			}
			else
			{
				URLWorker oURLWorker = new URLWorker();
				oURLWorker.URL = this.sServerURL;
				oURLWorker.ProxyServer = this.sProxyServer;
				oURLWorker.ProxyPort = this.sProxyPort;
				oURLWorker.BufferIn = "buffer=" + this.sPS2S_NetBuffer;
				string sret = oURLWorker.DoRequest("POST");
				if (sret != null)
				{
					this.m_oTracer.traceError("Exit SCBase.pPS2S_Client_TID2Ticket(), impossibile raggiungere il webserver di cig: " + this.sServerURL + " " + sret);
					result = -9;
				}
				else
				{
					ret = this.PS2S_Net2DataBuffer(oURLWorker.BufferOut, sComp, lFinestraTemporale);
					this.m_oTracer.traceInfo("Exit SCBase.pPS2S_Client_TID2Ticket(), ret=" + ret);
					result = ret;
				}
			}
			return result;
		}

		protected int PS2S_PS_TID2Ticket(string sTIDBuffer, string sCompDefault, int lFinestraTemporale)
		{
			return this.pPS2S_Server_TID2Ticket(sTIDBuffer, sCompDefault, lFinestraTemporale);
		}

		protected int PS2S_CS_TID2Ticket(string sTIDBuffer, string sCompDefault, int lFinestraTemporale)
		{
			return this.pPS2S_Server_TID2Ticket(sTIDBuffer, sCompDefault, lFinestraTemporale);
		}

		private int pPS2S_Server_TID2Ticket(string sTIDBuffer, string sCompDefault, int lFinestraTemporale)
		{
			this.m_oTracer.traceInfo("Entr SCBase.pPS2S_Server_TID2Ticket(), TIDBuffer=" + sTIDBuffer);
			int ret = this.PS2S_Net2DataBuffer(sTIDBuffer, sCompDefault, lFinestraTemporale);
			int result;
			if (ret != 0)
			{
				result = ret;
			}
			else
			{
				string Identificatore = this.sPS2S_DataBuffer;
				string ComponenteRichiesta = this.sPS2S_Componente;
				string ssql = "SELECT Identificativo, DataOra, Stato, Buffer FROM " + this.m_sDbOwner + "TabellaIdentificativo TabellaIdentificativo WHERE Identificativo=" + DBUtil.createParam(Identificatore);
				string sBuffer;
				try
				{
					Statement oStmt = new Statement(this.m_oDBConnection);
					this.QueryRslt = oStmt.executeQuery(ssql);
					if (!this.QueryRslt.Read())
					{
						this.QueryRslt.Close();
						this.m_oTracer.traceError("Exit SCBase.pPS2S_Server_TID2Ticket(), Error on ExecuteQuery");
						result = -10;
						return result;
					}
					int iStato = Statement.ReadInt(this.QueryRslt, "Stato");
					sBuffer = Statement.ReadString(this.QueryRslt, "Buffer");
					this.QueryRslt.Close();
					if (iStato != 0)
					{
						this.m_oTracer.traceError(string.Concat(new object[]
						{
							"Exit SCBase.pPS2S_Server_TID2Ticket(), Request ",
							Identificatore,
							" in stato ",
							iStato
						}));
						result = -100;
						return result;
					}
					ssql = string.Concat(new string[]
					{
						"UPDATE ",
						this.m_sDbOwner,
						"TabellaIdentificativo SET Stato=",
						DBUtil.createParam(1),
						" WHERE Identificativo=",
						DBUtil.createParam(Identificatore)
					});
					if (oStmt.executeUpdate(ssql) != 1)
					{
						this.m_oTracer.traceError("Exit SCBase.pPS2S_Server_TID2Ticket() - SQL, ret=" + ret);
						result = 83886084;
						return result;
					}
				}
				catch (Exception ex)
				{
					if (this.QueryRslt != null)
					{
						this.QueryRslt.Close();
					}
					this.m_oTracer.traceException(ex, "SecretCode.pPS2S_Server_TID2Ticket()");
					result = 15728640;
					return result;
				}
				ret = this.PS2S_Data2NetBuffer(sBuffer, ComponenteRichiesta, DateTime.Now, this.iPS2S_ProtocolVersion);
				if (ret != 0)
				{
					this.m_oTracer.traceError("Exit SCBase.pPS2S_Server_TID2Ticket() - DATA2NET, ret=" + ret);
					result = ret;
				}
				else
				{
					this.m_oTracer.traceInfo("Exit SCBase.pPS2S_Server_TID2Ticket()");
					result = 0;
				}
			}
			return result;
		}

		protected bool Sql_Delete(DateTime dInizio, DateTime dFine)
		{
			string sSql = string.Concat(new string[]
			{
				"DELETE FROM ",
				this.m_sDbOwner,
				"TabellaIdentificativo WHERE DataOra BETWEEN ",
				DBUtil.createParam(dInizio),
				" AND ",
				DBUtil.createParam(dFine)
			});
			bool result;
			try
			{
				Statement oStmt = new Statement(this.m_oDBConnection);
				this.iNumeroRighe = oStmt.executeUpdate(sSql);
			}
			catch (Exception e)
			{
				this.m_oTracer.traceException(e, "Exit SCBase.Sql_Delete()");
				result = false;
				return result;
			}
			result = true;
			return result;
		}

		private string MySelectSingleNode(string xmlBuffer, string tag)
		{
			string s = "";
			string iTag = "<" + tag + ">";
			string fTag = "</" + tag + ">";
			int pi = xmlBuffer.IndexOf(iTag);
			int pf = xmlBuffer.IndexOf(fTag);
			if (pi > 0 && pi < pf)
			{
				try
				{
					s = xmlBuffer.Substring(0, pf);
					s = s.Substring(pi + iTag.Length);
				}
				catch
				{
					s = "";
				}
			}
			return s;
		}

		private string SelectSingleNode(string sBufferRicevuto, string sNode)
		{
			XmlNode objNodeList;
			string result;
			try
			{
				XmlDocument xmlDoc = new XmlDocument();
				xmlDoc.PreserveWhitespace = true;
				xmlDoc.LoadXml(sBufferRicevuto);
				objNodeList = xmlDoc.DocumentElement.SelectSingleNode(sNode);
				if (objNodeList == null)
				{
					result = "";
					return result;
				}
			}
			catch (Exception ex)
			{
				this.m_oTracer.traceException(ex, "SecretCode.SelectSingleNode()");
				result = "";
				return result;
			}
			result = objNodeList.InnerXml;
			return result;
		}
	}
}
