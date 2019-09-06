using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.Services.Navigation;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;

namespace Init.Sigepro.FrontEnd.Reserved
{
	public partial class DettaglioIstanzaV2 : ReservedBasePage
	{
		[Inject]
		public IIstanzePresentateRepository _istanzePresentateRepository { get; set; }
        [Inject]
        public IConfigurazione<ParametriVisura> _configurazione { get; set; }
        [Inject]
        public NavigationService _navigationService { get; set; }

        protected string CodiceIstanza
		{
			get
			{
				var obj = Request.QueryString["Id"];

				if (String.IsNullOrEmpty(obj))
					throw new Exception("Codice istanza non valido o non impostato");

				return obj.ToString();			
			}
		}

		public DettaglioIstanzaV2()
		{
		}

		protected void Page_Load(object sender, EventArgs e)
		{
			if (!IsPostBack)
			{
				DataBind();			
			}
		}

		public override void DataBind()
		{
			var domanda = _istanzePresentateRepository.GetDettaglioPratica(IdComune, Software, CodiceIstanza);

			if (domanda.dettaglioErrore != null && domanda.dettaglioErrore.Length > 0)
			{
				foreach (var errore in domanda.dettaglioErrore)
				{
					this.Errori.Add(errore.descrizione);
				}

				visuraCtrl.Visible = false;

				return;
			}

			visuraCtrl.DataSource = domanda.dettaglioPratica;
			visuraCtrl.DataBind();

            ltrIntestazioneDettaglio.Text = _configurazione.Parametri.MessaggioIntestazioneVisura;
        }

		protected void VisuraCtrlV2_ErroreRendering(object sender, string errore)
		{
			this.Errori.Add(errore);
		}

		public void cmdClose_Click(object sender, EventArgs e)
		{
			Redirect("~/Reserved/IstanzePresentateV2.aspx", (x) => {
				x.Add("fromLastResult", "true");
			});
		}
	}
}