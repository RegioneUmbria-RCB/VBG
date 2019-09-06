using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using log4net;
using Init.Utils;
using System.Configuration;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.WebServices;
using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza;
using Init.Sigepro.FrontEnd.QsParameters;

namespace Init.Sigepro.FrontEnd.Reserved
{
	public partial class ArchivioPratiche : ReservedBasePage
	{
		[Inject]
		public IVisuraService _visuraRepository { get; set; }


		ILog m_logger = LogManager.GetLogger(typeof(IstanzePresentate));

        protected bool Popup
        {
            get {
                var qs = Request.QueryString["popup"];

                return !String.IsNullOrEmpty(qs);
            }
        }

		protected void Page_Load(object sender, EventArgs e)
		{
            this.Master.MostraIntestazione = !Popup;
            this.Master.MostraFooter = !Popup;

			if (!IsPostBack)
			{
				FiltriVisura.IdComune = IdComune;
				FiltriVisura.Software = Software;
				dglistaPratiche.IdComune = IdComune;
				dglistaPratiche.Software = Software;
			}
		}

		protected void cmdSearch_Click(object sender, EventArgs e)
		{
			var richiesta = FiltriVisura.GetRichiestaListaPratiche(base.UserAuthenticationResult.DatiUtente);

			try
			{
				richiesta.LimiteRecords = GetLimiteRecords();

				var listaPratiche = _visuraRepository.GetListaPratiche(richiesta);

				multiView.ActiveViewIndex = 1;

				dglistaPratiche.DataSource = listaPratiche;
				dglistaPratiche.DataBind();
			}
			catch (RecordCountException ex)
			{
				m_logger.ErrorFormat("La ricerca nell'archivio istanze ha restituito un numero troppo elevato di records: {0} \r\n Richiesta: {1}", ex.ToString(), StreamUtils.SerializeClass(richiesta));
				Errori.Add("La ricerca ha restituito un numero troppo elevato di risultati. Aggiungere uno o più filtri per limitare il numero di risultati");
			}
			catch (Exception ex)
			{
				m_logger.ErrorFormat("Errore durante la ricerca delle istanze presentate: {0} \r\n\r\n Richiesta: {1}", ex.ToString(), StreamUtils.SerializeClass(richiesta));
				Errori.Add(ex.Message);
			}
		}

		private int GetLimiteRecords()
		{
			const string APP_SETTINGS_KEY = "limiteRecordsArchivioPratiche";
			var str = ConfigurationManager.AppSettings[APP_SETTINGS_KEY];

			if (string.IsNullOrEmpty(str))
				return 200;

			int limite = 0;

			if (!Int32.TryParse(str, out limite))
				return 200;

			return limite;
		}

		protected void dglistaPratiche_IstanzaSelezionata(object sender, string idComune, string software, string idIstanza)
		{
            Redirect("~/Reserved/DettaglioIstanzaExArchivio.aspx", (qs) =>
            {
                qs.Add(new QsUuidIstanza(idIstanza));

                if (Popup)
                {
                    qs.Add("popup", 1);
                }
            });
		}

		protected void cmdNewSearch_Click(object sender, EventArgs e)
		{
			multiView.ActiveViewIndex = 0;
		}
	}
}
