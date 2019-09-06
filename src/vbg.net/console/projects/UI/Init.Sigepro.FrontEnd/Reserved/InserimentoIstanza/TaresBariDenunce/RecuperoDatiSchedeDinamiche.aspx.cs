using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.GestioneBari.DenunceTares;
using Init.Sigepro.FrontEnd.AppLogic.GestioneBari.Core.MappatureCampiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.Utils.SerializationExtensions;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.TaresBariDenunce
{
	public partial class RecuperoDatiSchedeDinamiche : IstanzeStepPage
	{
		[Inject]
		protected DenunceTaresBariService _taresService { get; set; }

		public string PathFileMappature
		{
			get { object o = this.ViewState["PathFileMappature"]; return o == null ? String.Empty : (string)o; }
			set { this.ViewState["PathFileMappature"] = value; }
		}

        public bool FermaSuStep
        {
            get { object o = this.ViewState["FermaSuStep"]; return o == null ? false : (bool)o; }
            set { this.ViewState["FermaSuStep"] = value; }
        }


		protected void Page_Load(object sender, EventArgs e)
		{
            if (!IsPostBack)
            {
                ltrOutput.Text = this._taresService.GetUtenzaSelezionata(IdDomanda).ToXmlString();
            }
		}

		public override void OnInitializeStep()
		{
			var idDomanda = IdDomanda;
			var pathFileMappature = HttpContext.Current.Server.MapPath(PathFileMappature);

			this._taresService.MappaCampiSuSchedeDinamiche(idDomanda, pathFileMappature);
		}

        public override bool CanEnterStep()
        {
            return FermaSuStep;
        }

	}
}