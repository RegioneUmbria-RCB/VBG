using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.GestioneBari.Tares;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
	public partial class GestioneRecuperDatiCaf : IstanzeStepPage
	{
		#region Dati letti dal workflow xml
		public int CodiceTipoSoggettoAnagrafica
		{
			get { object o = this.ViewState["CodiceTipoSoggettoanagrafica"]; return o == null ? -1 : (int)o; }
			set { this.ViewState["CodiceTipoSoggettoanagrafica"] = value; }
		}

		public int CodiceTipoSoggettoAnagraficaCollegata
		{
			get { object o = this.ViewState["CodiceTipoSoggettoAnagraficaCollegata"]; return o == null ? -1 : (int)o; }
			set { this.ViewState["CodiceTipoSoggettoAnagraficaCollegata"] = value; }
		}


		#endregion

		[Inject]
		protected TaresBariService _taresService { get; set; }

		protected void Page_Load(object sender, EventArgs e)
		{
		}

		public override bool CanEnterStep()
		{
			_taresService.InserisciOperatoreeCafNeiSoggettiDellaDomanda(IdDomanda, UserAuthenticationResult.DatiUtente.Codicefiscale, CodiceTipoSoggettoAnagrafica, CodiceTipoSoggettoAnagraficaCollegata);

			return false;
		}
	}
}