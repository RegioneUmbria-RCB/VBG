using System;
using System.Data;
using System.Configuration;
using System.Collections;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;

namespace Init.Sigepro.FrontEnd.Reserved
{
	public partial class DettaglioIstanzaEx : ReservedBasePage
	{
		protected int IdIstanza
		{
			get { return Convert.ToInt32(Request.QueryString["Id"]); }
		}

		protected string ReturnTo
		{
			get
			{
				string str = Request.QueryString["ReturnTo"];

				if (String.IsNullOrEmpty(str))
					return "~/Reserved/IstanzePresentate.aspx";

				return str;
			}
		}


		

		protected string ReturnToArgs
		{
			get
			{
				return Request.QueryString["ReturnToArgs"];
			}
		}


		protected void Page_Load(object sender, EventArgs e)
		{
			VisuraExCtrl1.ScadenzaSelezionata += new VisuraExCtrl.ScadenzaSelezionataDelegate(visuraCtrl_ScadenzaSelezionata);

			if (!IsPostBack)
			{
				VisuraExCtrl1.EffettuaVisuraIstanza(IdComune, Software, IdIstanza);
			}

		}

		void visuraCtrl_ScadenzaSelezionata(object sender, string idScadenza)
		{
			Redirect("~/Reserved/Gestionemovimenti/EffettuaMovimento.aspx", qs => qs.Add("IdMovimento", idScadenza));
		}


		protected void cmdClose_Click(object sender, EventArgs e)
		{
			Redirect(ReturnTo, ReturnToArgs);
		}
	}
}
