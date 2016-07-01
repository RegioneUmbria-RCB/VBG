using System;
using System.Collections;
using System.Configuration;
using System.Data;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Ninject;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
	public partial class PraticaInviataConSuccesso : ReservedBasePage
	{
		[Inject]
		public IConfigurazioneVbgRepository _configurazioneVbgRepository { get; set; }

		public int IdPratica
		{
			get { return Convert.ToInt32(Request.QueryString["Id"]); }
		}

		protected void Page_Load(object sender, EventArgs e)
		{
			visuraCtrl.ScadenzaSelezionata += new VisuraCtrl.ScadenzaSelezionataDelegate(visuraCtrl_ScadenzaSelezionata);

			if (!IsPostBack)
			{
				DataBind();
			}
		}

		public override void DataBind()
		{
			lblNomeUtente.Text = UserAuthenticationResult.DatiUtente.Nominativo + " " + UserAuthenticationResult.DatiUtente.Nome;

			var boConfig = _configurazioneVbgRepository.LeggiConfigurazioneComune(IdComune, Software);

			lblNomeComune.Text = boConfig.DENOMINAZIONE;
			lblTelefonoSportello.Text = boConfig.TELEFONO;
			lblOrariSportello.Text = boConfig.ORARIO;

			visuraCtrl.EffettuaVisuraIstanza(IdComune, Software, IdPratica);
		}

		void visuraCtrl_ScadenzaSelezionata(object sender, string idScadenza)
		{
			Redirect("~/Reserved/GestioneMovimenti/EffettuaMovimento.aspx", qs => qs.Add("IdMovimento", idScadenza));
		}
	}
}
