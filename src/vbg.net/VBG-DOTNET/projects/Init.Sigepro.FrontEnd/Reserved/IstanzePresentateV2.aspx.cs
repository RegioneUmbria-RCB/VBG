using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza;
using Init.Sigepro.FrontEnd.AppLogic.StcService;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using Ninject;
using System;

namespace Init.Sigepro.FrontEnd.Reserved
{
    public partial class IstanzePresentateV2 : ReservedBasePage
    {
        private static class Constants
        {
            public const int VistaRicerca = 0;
            public const int VistaLista = 1;
            public const string SessionKey = "IstanzePresentateV2:SessionKey";
        }

        [Inject]
        public IIstanzePresentateRepository _istanzePresentateRepository { get; set; }

        private bool BindFromLastResult
        {
            get
            {
                var val = this.Request.QueryString["fromLastResult"];

                if (String.IsNullOrEmpty(val))
                    return false;

                return val.ToUpper() == "TRUE";
            }
        }

        private DettaglioPraticaBreveType[] UltimoRisultato
        {
            get { return (DettaglioPraticaBreveType[])this.Session[Constants.SessionKey]; }
            set { this.Session[Constants.SessionKey] = value; }
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!this.IsPostBack)
            {
                if (this.BindFromLastResult)
                {
                    this.DataBindFromLastResult();
                }
                else
                {
                    this.DataBind();
                }
            }
        }

        private void DataBindFromLastResult()
        {
            this.BindResultsGrid(this.UltimoRisultato);
        }

        public override void DataBind()
        {
            this.filtriVisuraControl.IdComune = this.IdComune;
            this.filtriVisuraControl.Software = this.Software;
            this.filtriVisuraControl.DataBind();
        }



        protected void cmdCerca_Click(object sender, EventArgs e)
        {
            try
            {
                var filtri = this.filtriVisuraControl.GetRichiestaLista(this.UserAuthenticationResult.DatiUtente);

                var risultato = this._istanzePresentateRepository.GetListaPratiche(this.IdComune, this.Software, filtri);

                if (risultato.dettaglioErrore != null && risultato.dettaglioErrore.Length > 0)
                {
                    foreach (var errore in risultato.dettaglioErrore)
                    {
                        this.Errori.Add(errore.numeroErrore + " - " + errore.descrizione);
                    }
                }

                this.BindResultsGrid(risultato.dettaglioPratica);
            }
            catch (Exception ex)
            {
                this.Errori.Add(ex.Message);
            }
        }

        private void BindResultsGrid(DettaglioPraticaBreveType[] risultato)
        {
            this.multiView.ActiveViewIndex = Constants.VistaLista;

            this.UltimoRisultato = risultato;

            this.listaPraticheVisuraV2.PageIndex = 0;
            this.listaPraticheVisuraV2.DataSource = risultato;
            this.listaPraticheVisuraV2.DataBind();
        }


        protected void cmdChiudi_Click(object sender, EventArgs e)
        {
            this.multiView.ActiveViewIndex = Constants.VistaRicerca;
        }

        protected void listaPraticheVisuraV2_IstanzaSelezionata(string codiceIstanza)
        {
            var url = UrlBuilder.Url("~/Reserved/DettaglioIstanzaV2.aspx", x =>
            {
                x.Add(new QsAliasComune(this.IdComune));
                x.Add("id", codiceIstanza);
                x.Add(new QsSoftware(this.Software));

            });

            this.Response.Redirect(url);
        }
    }
}