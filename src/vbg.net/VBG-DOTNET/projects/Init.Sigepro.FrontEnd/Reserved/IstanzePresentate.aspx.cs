using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni.SIC;
using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using Init.Utils;
using log4net;
using Newtonsoft.Json;
using Ninject;
using System;
using System.Linq;

namespace Init.Sigepro.FrontEnd.Reserved
{
    public partial class IstanzePresentate : ReservedBasePage
    {
        [Inject]
        public IVisuraService _visuraService { get; set; }
        [Inject]
        public ILocalizzazioniSICSyncService _localizzazioniSICService { get; set; }

        private bool RestoreResults
        {
            get
            {
                return this.Request.QueryString["restore"] == "1";
            }
        }

        private readonly ILog m_logger = LogManager.GetLogger(typeof(IstanzePresentate));


        protected void Page_Load(object sender, EventArgs e)
        {
            if (!this.IsPostBack)
            {
                this.FiltriVisura.IdComune = this.IdComune;
                this.FiltriVisura.Software = this.Software;
                this.dglistaPratiche.IdComune = this.IdComune;
                this.dglistaPratiche.Software = this.Software;

                if (this.RestoreResults)
                {
                    this.RebindFromCache();
                }
            }
        }

        protected override void OnPreRender(EventArgs e)
        {
            base.OnPreRender(e);
            var feats = this._localizzazioniSICService.GetFeatures();
            this.cmdMostraInMappa.Visible = feats.MostraMappaListaIstanze;
        }

        private void RebindFromCache()
        {
            this.multiView.ActiveViewIndex = 1;

            this.dglistaPratiche.RebindFromCache();
        }

        protected void cmdSearch_Click(object sender, EventArgs e)
        {
            var richiesta = this.FiltriVisura.GetRichiestaListaPratiche(base.UserAuthenticationResult.DatiUtente);

            try
            {
                this.multiView.ActiveViewIndex = 1;

                this.dglistaPratiche.PageIndex = 0;
                this.dglistaPratiche.DataSource = this._visuraService.GetListaPratiche(richiesta);
                this.dglistaPratiche.DataBind();
            }
            catch (Exception ex)
            {
                this.m_logger.ErrorFormat("Errore durante la ricerca delle istanze presentate: {0} \r\n\r\n Richiesta: {1}", ex.ToString(), StreamUtils.SerializeClass(richiesta));

                this.multiView.ActiveViewIndex = 0;

                this.Errori.Add("La ricerca ha restituito un numero troppo elevato di risultati. Utilizzare uno o più filtri per ridurre il numero di risultati restituiti.");
            }
        }

        public string GetNavigateUrlFormatString()
        {
            var returnTo = UrlBuilder.Url("~/Reserved/IstanzePresentate.aspx", qs =>
            {
                qs.Add(new QsAliasComune(this.IdComune));
                qs.Add(new QsSoftware(this.Software));
                qs.Add("restore", "1");
            });

            var url = UrlBuilder.Url("~/Reserved/DettaglioIstanzaEx.aspx", qs =>
            {
                qs.Add(new QsAliasComune(this.IdComune));
                qs.Add(new QsSoftware(this.Software));
                qs.Add(new QsReturnTo(returnTo));
            });
            return url + "&uuid-pratica={0}";
        }

        protected void cmdNewSearch_Click(object sender, EventArgs e)
        {
            this.multiView.ActiveViewIndex = 0;
        }

        protected void cmdMostraInMappa_Click(object sender, EventArgs e)
        {
            this.RedirectAllaMappa();
        }

        private void RedirectAllaMappa()
        {
            var host = this.Request.Url.Host;
            var port = this.Request.Url.Port;
            var scheme = this.Request.Url.Scheme;
            var appName = this.Request.ApplicationPath;
            var token = this.UserAuthenticationResult.Token;
            var idComune = this._aliasSoftwareResolver.AliasComune;
            var software = this._aliasSoftwareResolver.Software;

            if (!String.IsNullOrEmpty(this.hidUuidPratiche.Value))
            {
                var response = this._localizzazioniSICService.GeneraURLMappaListaPratiche(new GeneraURLMappaListaPraticheRequest
                {
                    CallbackUrl = $"{scheme}://{host}:{port}{appName}/sit-return-lista-pratiche/{token}/{idComune}/{software}",
                    CancelUrl = "",
                    UuidIstanze = JsonConvert.DeserializeObject<String[]>(this.hidUuidPratiche.Value).ToList()
                });

                if (!String.IsNullOrEmpty(response.Errore))
                {
                    this.Errori.Add(response.Errore);
                    return;
                }

                this.Response.Redirect(response.Url);
            }
        }
    }
}
