using System;
//using Init.Sigepro.FrontEnd.AppLogic.Visura.Collections;
using System.Diagnostics;

using log4net;
using Init.Utils;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza;
using System.Collections.Generic;

namespace Init.Sigepro.FrontEnd.Reserved
{
	public partial class IstanzePresentate : ReservedBasePage
	{
		[Inject]
		public IVisuraService _visuraService { get; set; }

        bool RestoreResults
        {
            get
            {
                return Request.QueryString["restore"] == "1";
            }
        }


		ILog m_logger = LogManager.GetLogger(typeof(IstanzePresentate));


		protected void Page_Load(object sender, EventArgs e)
		{
			if (!IsPostBack)
			{
				FiltriVisura.IdComune = IdComune;
				FiltriVisura.Software = Software;
				dglistaPratiche.IdComune = IdComune;
				dglistaPratiche.Software = Software;

                if (RestoreResults)
                {
                    RebindFromCache();
                }
			}
		}

        private void RebindFromCache()
        {
            multiView.ActiveViewIndex = 1;

            dglistaPratiche.RebindFromCache();
        }

        protected void cmdSearch_Click(object sender, EventArgs e)
		{
			var richiesta = FiltriVisura.GetRichiestaListaPratiche(base.UserAuthenticationResult.DatiUtente);

			try
			{
				multiView.ActiveViewIndex = 1;

				dglistaPratiche.PageIndex = 0;
				dglistaPratiche.DataSource = _visuraService.GetListaPratiche(richiesta);
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
            var returnTo = UrlBuilder.Url("~/Reserved/IstanzePresentate.aspx", qs =>
            {
                qs.Add(new QsAliasComune(this.IdComune));
                qs.Add(new QsSoftware(this.Software));
                qs.Add("restore", "1");
            });

            var url = UrlBuilder.Url("~/Reserved/DettaglioIstanzaEx.aspx", qs => {
                qs.Add(new QsAliasComune(this.IdComune));
                qs.Add(new QsSoftware(this.Software));
                qs.Add(new QsUuidIstanza(idIstanza));
                qs.Add(new QsReturnTo(returnTo));
            });
            Response.Redirect(url);
		}

		protected void cmdNewSearch_Click(object sender, EventArgs e)
		{
			multiView.ActiveViewIndex = 0;
		}
	}
}
