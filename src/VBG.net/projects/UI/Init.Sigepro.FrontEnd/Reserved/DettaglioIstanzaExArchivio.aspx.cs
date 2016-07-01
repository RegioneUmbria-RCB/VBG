using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved
{
	public partial class DettaglioIstanzaExArchivio : ReservedBasePage
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
					return "~/Reserved/ArchivioPratiche.aspx";

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
			Redirect("~/Reserved/GestioneMovimenti/EffettuaMovimento.aspx", qs => qs.Add("IdMovimento", idScadenza));
		}


		protected void cmdClose_Click(object sender, EventArgs e)
		{
			Redirect(ReturnTo, ReturnToArgs);
		}

	}
}
