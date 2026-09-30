using Init.Sigepro.FrontEnd.AppLogic.GestioneEntiTerzi;
using Init.Sigepro.FrontEnd.AppLogic.Services.Navigation;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using Ninject;
using System;
using System.Linq;
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
        public IRedirectService _redirectService { get; set; }
        [Inject]
        public IScrivaniaEntiTerziService _service { get; set; }

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!this.IsPostBack)
            {
                this.DataBindCombo();

                if (!String.IsNullOrEmpty(this.Request.QueryString[Constants.QuerystringRestore]))
                {
                    this.RipristinaUltimaRicerca();
                }
                else
                {
                    this.NuovaRicerca();
                }
            }
        }

        private void RipristinaUltimaRicerca()
        {
            var sessionVal = this.Session[Constants.SessionKeyUltimaRicerca];

            if (sessionVal == null)
            {
                this.NuovaRicerca();
                return;
            }

            var filtri = (ETFiltriRicerca)sessionVal;

            this.dtbDallaData.Text = filtri.DallaData == DateTime.MinValue ? "" : filtri.DallaData.ToString("dd/MM/yyyy");
            this.dtbAllaData.Text = filtri.AllaData == DateTime.MaxValue ? "" : filtri.AllaData.ToString("dd/MM/yyyy");

            this.txtNumeroProtocollo.Text = filtri.NumeroProtocollo;
            this.txtNumeroPratica.Text = filtri.NumeroIstanza;

            this.ddlElaborata.SelectedValue = filtri.Elaborata.HasValue ? (filtri.Elaborata.Value ? "1" : "0") : "";
            this.ddlSoftware.SelectedValue = filtri.Software;

            this.cmdCerca_Click(this, EventArgs.Empty);
        }

        private void DataBindCombo()
        {
            this.ddlSoftware.Items.Add(new ListItem("Tutti", ""));

            var software = this._service.GetListaSoftwareConPratiche(new ETCodiceAnagrafe(this.UserAuthenticationResult.DatiUtente.Codiceanagrafe.Value));

            this.ddlSoftware.Items.AddRange(software.Select(x => new ListItem(x.Descrizione, x.Codice)).ToArray());

            this.ddlElaborata.Items.Add(new ListItem("Tutti", ""));
            this.ddlElaborata.Items.Add(new ListItem("Elaborata", "1"));
            this.ddlElaborata.Items.Add(new ListItem("Non elaborata", "0"));
        }

        private void NuovaRicerca()
        {
            this.multiView.ActiveViewIndex = Constants.ViewIdRicerca;

            this.SvuotaForm();

        }

        private void SvuotaForm()
        {
            this.dtbDallaData.Text = String.Empty;
            this.dtbAllaData.Text = String.Empty;

            this.txtNumeroProtocollo.Text = String.Empty;
            this.txtNumeroPratica.Text = String.Empty;

            this.ddlElaborata.SelectedIndex = 0;
            this.ddlSoftware.SelectedIndex = 0;
        }

        private void EffettuaRicerca()
        {
            this.multiView.ActiveViewIndex = Constants.ViewIdLista;

            var filtri = new ETFiltriRicerca
            {
                DallaData = this.dtbDallaData.Inner.DateValue.GetValueOrDefault(DateTime.MinValue),
                AllaData = this.dtbAllaData.Inner.DateValue.GetValueOrDefault(DateTime.MaxValue),
                Elaborata = String.IsNullOrEmpty(this.ddlElaborata.Value) ? (bool?)null : this.ddlElaborata.Value == "1",
                NumeroIstanza = this.txtNumeroPratica.Value,
                NumeroProtocollo = this.txtNumeroProtocollo.Value,
                Software = this.ddlSoftware.Value
            };

            this.SalvaUltimaRicerca(filtri);

            this.gvRisultati.DataSource = this._service.GetPraticheDiCompetenza(new ETCodiceAnagrafe(this._authenticationDataResolver.DatiAutenticazione.DatiUtente.Codiceanagrafe.Value), filtri);
            this.gvRisultati.DataBind();
        }

        private void SalvaUltimaRicerca(ETFiltriRicerca filtri)
        {
            this.Session[Constants.SessionKeyUltimaRicerca] = filtri;
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

            this.Response.Redirect(url);
        }

        protected void Unnamed_Click(object sender, EventArgs e)
        {

        }

        protected void cmdCerca_Click(object sender, EventArgs e)
        {
            this.EffettuaRicerca();
        }

        protected void cmdNuovaRicerca_Click(object sender, EventArgs e)
        {
            this.multiView.ActiveViewIndex = Constants.ViewIdRicerca;
        }

        protected void cmdChiudi_Click(object sender, EventArgs e)
        {
            this._redirectService.RedirectToHomeAreaRiservata();
        }

        protected void gvRisultati_RowDataBound(object sender, GridViewRowEventArgs e)
        {
            if (e.Row.RowType == DataControlRowType.DataRow)
            {
                var item = (ETPratica)e.Row.DataItem;
                e.Row.Cells[0].Attributes.Add("data-order", this.ParseDateTimeFromString(item.DataPresentazione).ToUnixTimeSeconds().ToString());
                e.Row.Cells[1].Attributes.Add("data-order", this.ParseDateTimeFromString(item.DataProtocollo).ToUnixTimeSeconds().ToString());
            }
        }

        private DateTimeOffset ParseDateTimeFromString(string value)
        {
            if (string.IsNullOrEmpty(value))
            {
                return new DateTimeOffset(new DateTime(1900, 1, 1));
            }

            DateTime dt;
            if (DateTime.TryParse(value, out dt))
            {
                return new DateTimeOffset(dt);
            }
            else
            {
                return new DateTimeOffset(new DateTime(1900, 1, 1));
            }
        }
    }
}