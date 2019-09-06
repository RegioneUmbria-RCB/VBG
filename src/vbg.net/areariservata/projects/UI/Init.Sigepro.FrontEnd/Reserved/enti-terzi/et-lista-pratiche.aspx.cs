using Init.Sigepro.FrontEnd.AppLogic.GestioneEntiTerzi;
using Init.Sigepro.FrontEnd.AppLogic.Services.Navigation;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved.enti_terzi
{
    public partial class et_lista_pratiche : ReservedBasePage
    {
        private static class Constants
        {
            public const int ViewIdRicerca = 0;
            public const int ViewIdLista = 1;
            public const string SessionKeyUltimaRicerca = "et_lista_pratiche.ultimaRicerca";
            public const string QuerystringRestore = "restore";
        }

        [Inject]
        public RedirectService _redirectService { get; set; }
        [Inject]
        public IScrivaniaEntiTerziService _service { get; set; }

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                DataBindCombo();

                if (!String.IsNullOrEmpty(Request.QueryString[Constants.QuerystringRestore]))
                {
                    RipristinaUltimaRicerca();
                }
                else
                {
                    NuovaRicerca();
                }
            }
        }

        private void RipristinaUltimaRicerca()
        {
            var sessionVal = Session[Constants.SessionKeyUltimaRicerca];

            if (sessionVal == null)
            {
                NuovaRicerca();
                return;
            }

            var filtri = (ETFiltriRicerca)sessionVal;

            dtbDallaData.Text = filtri.DallaData == DateTime.MinValue ? "" : filtri.DallaData.ToString("dd/MM/yyyy");
            dtbAllaData.Text = filtri.AllaData == DateTime.MaxValue ? "" : filtri.AllaData.ToString("dd/MM/yyyy");

            txtNumeroProtocollo.Text = filtri.NumeroProtocollo;
            txtNumeroPratica.Text = filtri.NumeroIstanza;

            ddlElaborata.SelectedValue = filtri.Elaborata.HasValue ? (filtri.Elaborata.Value ? "1" : "0") : "";
            ddlSoftware.SelectedValue = filtri.Software;

            cmdCerca_Click(this, EventArgs.Empty);
        }

        private void DataBindCombo()
        {
            ddlSoftware.Items.Add(new ListItem("Tutti", ""));

            var software = this._service.GetListaSoftwareConPratiche(new ETCodiceAnagrafe(this.UserAuthenticationResult.DatiUtente.Codiceanagrafe.Value));

            ddlSoftware.Items.AddRange(software.Select(x => new ListItem(x.Descrizione, x.Codice)).ToArray());

            ddlElaborata.Items.Add(new ListItem("Tutti", ""));
            ddlElaborata.Items.Add(new ListItem("Elaborata", "1"));
            ddlElaborata.Items.Add(new ListItem("Non elaborata", "0"));
        }

        private void NuovaRicerca()
        {
            multiView.ActiveViewIndex = Constants.ViewIdRicerca;

            SvuotaForm();

        }

        private void SvuotaForm()
        {
            dtbDallaData.Text = String.Empty;
            dtbAllaData.Text = String.Empty;

            txtNumeroProtocollo.Text = String.Empty;
            txtNumeroPratica.Text = String.Empty;

            ddlElaborata.SelectedIndex = 0;
            ddlSoftware.SelectedIndex = 0;
        }

        private void EffettuaRicerca()
        {
            this.multiView.ActiveViewIndex = Constants.ViewIdLista;

            var filtri = new ETFiltriRicerca
            {
                DallaData = dtbDallaData.Inner.DateValue.GetValueOrDefault(DateTime.MinValue),
                AllaData = dtbAllaData.Inner.DateValue.GetValueOrDefault(DateTime.MaxValue),
                Elaborata = String.IsNullOrEmpty(ddlElaborata.Value) ? (bool?)null : ddlElaborata.Value == "1",
                NumeroIstanza = txtNumeroPratica.Value,
                NumeroProtocollo = txtNumeroProtocollo.Value,
                Software = ddlSoftware.Value
            };

            SalvaUltimaRicerca(filtri);

            gvRisultati.DataSource = this._service.GetPraticheDiCompetenza(new ETCodiceAnagrafe(this._authenticationDataResolver.DatiAutenticazione.DatiUtente.Codiceanagrafe.Value), filtri);
            gvRisultati.DataBind();
        }

        private void SalvaUltimaRicerca(ETFiltriRicerca filtri)
        {
            Session[Constants.SessionKeyUltimaRicerca] = filtri;
        }

        private void MostraDettaglio(string uuid)
        {

        }

        protected void gvRisultati_SelectedIndexChanged(object sender, EventArgs e)
        {
            var url = UrlBuilder.Url("~/reserved/enti-terzi/et-dettaglio-pratica.aspx", pb =>
            {
                pb.Add(new QsAliasComune(this.IdComune));
                pb.Add(new QsSoftware(this.Software));
                pb.Add(new QsUuidIstanza(this.gvRisultati.DataKeys[this.gvRisultati.SelectedIndex].Value.ToString()));
            });

            Response.Redirect(url);
        }

        protected void Unnamed_Click(object sender, EventArgs e)
        {

        }

        protected void cmdCerca_Click(object sender, EventArgs e)
        {
            EffettuaRicerca();
        }

        protected void cmdNuovaRicerca_Click(object sender, EventArgs e)
        {
            this.multiView.ActiveViewIndex = Constants.ViewIdRicerca;
        }

        protected void cmdChiudi_Click(object sender, EventArgs e)
        {
            this._redirectService.RedirectToHomeAreaRiservata();
        }
    }
}