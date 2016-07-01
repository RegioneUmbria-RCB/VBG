using System;

namespace Init.Sigepro.FrontEnd.Reserved
{
	public partial class DettaglioIstanza : ReservedBasePage
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
			visuraCtrl.ScadenzaSelezionata += new VisuraCtrl.ScadenzaSelezionataDelegate(visuraCtrl_ScadenzaSelezionata);

			if (!IsPostBack)
				visuraCtrl.EffettuaVisuraIstanza(IdComune, Software, IdIstanza);

		}

		void visuraCtrl_ScadenzaSelezionata(object sender, string idScadenza)
		{
			Redirect("~/Reserved/GestioneMovimenti/EffettuaMovimento.aspx", qs => qs.Add("IdMovimento" ,idScadenza));
		}

		
		protected void cmdClose_Click(object sender, EventArgs e)
		{
			Redirect(ReturnTo, ReturnToArgs);
		}
	}
}
