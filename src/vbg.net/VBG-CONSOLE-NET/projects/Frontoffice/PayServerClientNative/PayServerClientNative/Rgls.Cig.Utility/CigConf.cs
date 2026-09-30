using System;
using System.Configuration;
using System.Xml;

namespace Rgls.Cig.Utility
{
	public class CigConf
	{
		public int WindowMinutes = 10;

		public int SessionToMinutes = 20;

		public bool AllowWrongCigCF = false;

		public bool AllowWrongConsoleCF = true;

		public int SessionSsoMinutes = 10;

		public int MinAccessAge = 0;

		public bool SsoEnabled = false;

		public bool SsoChdataEnabled = false;

		public bool RilascioDisable = false;

		public string SetCharUser = "SCACAC####";

		public string SetCharPassword = "SCACAC####";

		public bool CheckOperator = false;

		public bool AllowCompany = true;

		public bool AllowEmptyAddress = true;

		public bool AllowEmptyMail = false;

		public bool CheckAnagrafe = false;

		public bool CheckAnagrafeLogon = false;

		public bool HideComuneResidenza = false;

		public string ClasseAnagrafe = "";

		public string UrlAnagrafe = "";

		public string PwdRules = "1111111111111111";

		public string MsgPersonalizzato = "\\Messaggio.txt";

		public string UserPwdToOperPerc = "50";

		public bool InsertDocEnabled = false;

		public string ATCIE = "0";

		public string ATCartaDiFirma = "0";

		public string ATInfocamere = "0";

		public string ATPostecert = "0";

		public string ATActalis = "0";

		public CigConf()
		{
			Tracer m_oTracer = new Tracer();
			string sServerCigConfigFile = ConfigurationManager.AppSettings["ServerCigConfigFile"];
			if (sServerCigConfigFile == null || sServerCigConfigFile.Length == 0)
			{
				m_oTracer.traceError("CigConf.CigConf(), file di configurazione (ServerCigConfigFile) non trovato in .config");
			}
			else
			{
				try
				{
					XmlDocument doc = new XmlDocument();
					doc.Load(sServerCigConfigFile);
					foreach (XmlNode nd in doc.FirstChild.ChildNodes)
					{
						string name = nd.Name;
						switch (name)
						{
						case "WindowMinutes":
							this.WindowMinutes = Convert.ToInt32(nd.InnerText);
							break;
						case "SessionToMinutes":
							this.SessionToMinutes = Convert.ToInt32(nd.InnerText);
							break;
						case "AllowWrongCigCF":
							this.AllowWrongCigCF = (Convert.ToInt32(nd.InnerText) == 1);
							break;
						case "AllowWrongConsoleCF":
							this.AllowWrongConsoleCF = (Convert.ToInt32(nd.InnerText) == 1);
							break;
						case "SessionSsoMinutes":
							this.SessionSsoMinutes = Convert.ToInt32(nd.InnerText);
							break;
						case "MinAccessAge":
							this.MinAccessAge = Convert.ToInt32(nd.InnerText);
							break;
						case "SsoEnabled":
							this.SsoEnabled = (Convert.ToInt32(nd.InnerText) == 1);
							break;
						case "SsoChdataEnabled":
							this.SsoChdataEnabled = (Convert.ToInt32(nd.InnerText) == 1);
							break;
						case "RilascioDisable":
							this.RilascioDisable = (Convert.ToInt32(nd.InnerText) == 1);
							break;
						case "SetCharUser":
							this.SetCharUser = nd.InnerText;
							break;
						case "SetCharPassword":
							this.SetCharPassword = nd.InnerText;
							break;
						case "CheckOperator":
							this.CheckOperator = (Convert.ToInt32(nd.InnerText) == 1);
							break;
						case "AllowCompany":
							this.AllowCompany = (Convert.ToInt32(nd.InnerText) == 1);
							break;
						case "AllowEmptyAddress":
							this.AllowEmptyAddress = (Convert.ToInt32(nd.InnerText) == 1);
							break;
						case "AllowEmptyMail":
							this.AllowEmptyMail = (Convert.ToInt32(nd.InnerText) == 1);
							break;
						case "CheckAnagrafe":
							this.CheckAnagrafe = (Convert.ToInt32(nd.InnerText) == 1);
							break;
						case "CheckAnagrafeLogon":
							this.CheckAnagrafeLogon = (Convert.ToInt32(nd.InnerText) == 1);
							break;
						case "HideComuneResidenza":
							this.HideComuneResidenza = (Convert.ToInt32(nd.InnerText) == 1);
							break;
						case "ClasseAnagrafe":
							this.ClasseAnagrafe = nd.InnerText;
							break;
						case "UrlAnagrafe":
							this.UrlAnagrafe = nd.InnerText;
							break;
						case "PwdRules":
							this.PwdRules = nd.InnerText;
							break;
						case "MsgPersonalizzato":
							this.MsgPersonalizzato = nd.InnerText;
							break;
						case "UserPwdToOperPerc":
							this.UserPwdToOperPerc = nd.InnerText;
							break;
						case "InsertDocEnabled":
							this.InsertDocEnabled = (Convert.ToInt32(nd.InnerText) == 1);
							break;
						case "AuthTypeCIE":
							this.ATCIE = nd.InnerText;
							break;
						case "AuthTypeCARTADIFIRMA":
							this.ATCartaDiFirma = nd.InnerText;
							break;
						case "AuthTypeINFOCAMERE":
							this.ATInfocamere = nd.InnerText;
							break;
						case "AuthTypePOSTECERT":
							this.ATPostecert = nd.InnerText;
							break;
						case "AuthTypeACTALIS":
							this.ATActalis = nd.InnerText;
							break;
						}
					}
				}
				catch (Exception e)
				{
					m_oTracer.traceException(e, "CigConf.CigConf()");
				}
			}
		}
	}
}
