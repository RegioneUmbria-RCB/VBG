using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Init.Sigepro.FrontEnd.AppLogic.GestioneBari.Tasi;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.TasiBari
{
	public partial class RecuperoDatiStradario : IstanzeStepPage
	{
		[Inject]
		protected TasiBariService _tasiService { get; set; }

		[Inject]
		protected LocalizzazioniService _localizzazioniService { get; set; }

		public int IdIndirizzoNonDefinito
		{
			get { object o = this.ViewState["IdIndirizzoNonDefinito"]; return o == null ? 0 : (int)o; }
			set { this.ViewState["IdIndirizzoNonDefinito"] = value; }
		}




		protected void Page_Load(object sender, EventArgs e)
		{

		}

		private void ImpostaStradarioDaDatiUtenza()
		{
			if (this.ReadFacade.Domanda.TasiBari.DatiImmobili == null)
				return;

			this._tasiService.ImpostaLocalizzazioneDomanda(this.IdDomanda, this._localizzazioniService, IdIndirizzoNonDefinito);
		}

		public override bool CanEnterStep()
		{
			if (this.ReadFacade.Domanda.Localizzazioni.Indirizzi.Count() == 0)
				ImpostaStradarioDaDatiUtenza();

			return false;
		}
	}
}