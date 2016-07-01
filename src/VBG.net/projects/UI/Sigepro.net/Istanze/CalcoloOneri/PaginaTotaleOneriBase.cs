using System;
using System.Data;
using System.Configuration;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;
using SIGePro.Net;
using System.Collections.Generic;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;

namespace Sigepro.net.Istanze.CalcoloOneri
{
	public class PaginaTotaleOneriBase : BasePage
	{

		protected void ImpostaScriptCopia(WebControl ctrl, int codiceIstanza, int tipoCausale)
		{
			string msg;
			if (VerificaConfermaBottoneRiporta(codiceIstanza, out msg, tipoCausale))
			{
				string key = "confermaCopia";

				if (!Page.ClientScript.IsClientScriptBlockRegistered(key))
				{
					Page.ClientScript.RegisterClientScriptBlock(typeof(string), key, "function confermaCopia(){ return confirm(" + msg + ");}", true);
				}

				ctrl.Attributes.Add("onclick", "return confermaCopia()");
			}
		}

		private bool VerificaConfermaBottoneRiporta(int codiceIstanza, out string msg, int tipoCausale)
		{
			msg = string.Empty;
			bool retVal = false;

			List<IstanzeOneri> al = GetOneriFromIstanzaCausale(codiceIstanza, tipoCausale);

			if (al.Count > 0)
			{
				if (al.Count == 1)
				{
					msg = "\"Esiste un onere nell\'istanza con la stessa causale: proseguire con la somma?\"";
				}
				else
				{
					msg = "\"Esiste più di un onere nell\'istanza con la stessa causale: inserire un nuovo onere?\"";
				}
				retVal = true;
			}

			return retVal;
		}

		protected void MostraConfermaCopiaOneri()
		{
			string key = "copiaEffettuata";

			if (!Page.ClientScript.IsClientScriptBlockRegistered(key))
			{
				Page.ClientScript.RegisterClientScriptBlock(typeof(string), key, "alert(\'Onere copiato con successo\');", true);
			}
		}

		protected List<IstanzeOneri> GetOneriFromIstanzaCausale(int codiceIstanza, int tipoCausale)
		{
			var mgr = new IstanzeOneriMgr(Database);

			var filtro = new IstanzeOneri
			{
				IDCOMUNE = IdComune,
				CODICEISTANZA = codiceIstanza.ToString(),
				FKIDTIPOCAUSALE = tipoCausale.ToString()
			};

			return mgr.GetList(filtro);
		}
	
	}

		
}
