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
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
	public partial class DatiIstanzaCE : IstanzeStepPage
	{
		[Inject]
		public DatiDomandaService DatiDomandaService { get; set; }

		protected void Page_Load(object sender, EventArgs e)
		{
			Master.IgnoraSalvataggioDati = true;

			if (!IsPostBack)
				DataBind();
		}

		public override void OnBeforeExitStep()
		{
			DatiDomandaService.ImpostaDatiIstanza(IdDomanda, Note.Text, Oggetto.Text, String.Empty);
		}

		public override bool CanExitStep()
		{
			if (String.IsNullOrEmpty(ReadFacade.Domanda.AltriDati.DescrizioneLavori))
			{
				Errori.Add("Compilare tutti i campi obbligatori");
				return false;
			}

			return true;
		}


		public override void DataBind()
		{
			Note.Text		= ReadFacade.Domanda.AltriDati.Note;
			Oggetto.Text	= ReadFacade.Domanda.AltriDati.DescrizioneLavori;
		}
	}
}
