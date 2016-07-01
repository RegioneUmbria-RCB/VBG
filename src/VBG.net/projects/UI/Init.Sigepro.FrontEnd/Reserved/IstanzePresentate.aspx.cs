using System;
//using Init.Sigepro.FrontEnd.AppLogic.Visura.Collections;
using System.Diagnostics;

using log4net;
using Init.Utils;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;

namespace Init.Sigepro.FrontEnd.Reserved
{
	public partial class IstanzePresentate : ReservedBasePage
	{
		[Inject]
		public IVisuraRepository _visuraRepository { get; set; }


		ILog m_logger = LogManager.GetLogger(typeof(IstanzePresentate));


		protected void Page_Load(object sender, EventArgs e)
		{
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
				var listaPratiche = _visuraRepository.GetListaPratiche( IdComune, richiesta);

				multiView.ActiveViewIndex = 1;

				dglistaPratiche.PageIndex = 0;
				dglistaPratiche.DataSource = listaPratiche;
				dglistaPratiche.DataBind();
			}
			catch (Exception ex)
			{
				m_logger.ErrorFormat("Errore durante la ricerca delle istanze presentate: {0} \r\n\r\n Richiesta: {1}", ex.ToString(), StreamUtils.SerializeClass(richiesta));
				Errori.Add(ex.Message);
			}
		}

		protected void dglistaPratiche_IstanzaSelezionata(object sender, string idComune, string software, string idIstanza)
		{
			Redirect("~/Reserved/DettaglioIstanzaEx.aspx", qs => qs.Add("Id", idIstanza));
		}

		protected void cmdNewSearch_Click(object sender, EventArgs e)
		{
			multiView.ActiveViewIndex = 0;
		}
	}
}
