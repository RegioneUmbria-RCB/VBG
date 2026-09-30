using Init.Sigepro.FrontEnd.AppLogic.GestioneCommissioni;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.UrlDownloadOggetti;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using Init.Sigepro.FrontEnd.QsParameters.Commissioni;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved.Commissioni
{
    public partial class dettaglio_commissione : CommissioniBasePage
    {
        public QsIdCommissione Id => new QsIdCommissione(Request.QueryString);

        [Inject]
        protected ICommissioniService _commissioniService { get; set; }

        [Inject]
        protected IUrlDownloadOggettiService _urlDownloadOggettiService { get; set; }

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                DataBind();
            }
        }

        public override void DataBind()
        {
            var commissione = this._commissioniService.GetDettaglioCommissionePerUtenteCorrente(this.Id.Value);

            if (commissione == null)
            {
                ErroreAccesso();
                return;
            }

            this.lblDataCommissione.Value = commissione.Testata.Data;
            this.lblDescrizione.Value = commissione.Testata.Descrizione;
            this.lblNumeroCommissione.Value = commissione.Testata.Numero;
            this.lblOraInizio.Value = commissione.Testata.OraInizio;
            this.lblOraFine.Value = commissione.Testata.OraFine;

            var i = 1;
            rptConvocazioni.DataSource = commissione.Convocazioni.Select(x => new
            {
                DataOra = $"{x.Data} {x.Ora}",
                Descrizione = $"{i++}A Convocazione",
                Attiva = x.Id == commissione.Testata.Convocazione?.Id
            });
            rptConvocazioni.DataBind();

            gvPratiche.DataSource = commissione.Pratiche;
            gvPratiche.DataBind();

            gvDocumenti.DataSource = commissione.Documenti;
            gvDocumenti.DataBind();

            fsDocumenti.Visible = commissione.Documenti?.Length > 0;
        }

        public string GetUrlDownload(int codiceOggetto)
        {
            return this._urlDownloadOggettiService.GetUrlDownload(codiceOggetto);
        }

        protected void cmdChiudi_Click(object sender, EventArgs e)
        {
            var url = UrlBuilder.Url("~/reserved/commissioni/lista-commissioni.aspx", mp =>
            {
                mp.Add(new QsAliasComune(IdComune));
                mp.Add(new QsSoftware(Software));
            });

            Response.Redirect(url);
        }

        protected void gvPratiche_SelectedIndexChanged(object sender, EventArgs e)
        {
            var uuid = gvPratiche.DataKeys[gvPratiche.SelectedIndex].Value.ToString();
            var url = UrlBuilder.Url("~/reserved/commissioni/dettaglio-pratica.aspx", mp =>
            {
                mp.Add(new QsAliasComune(IdComune));
                mp.Add(new QsSoftware(Software));
                mp.Add(this.Id);
                mp.Add(new QsUuidIstanza(uuid));
            });

            Response.Redirect(url);
        }

        protected void bmApprovaVerbale_OkClicked(object sender, EventArgs e)
        {
            

            try
            {
                var idAllegato = Convert.ToInt32(this.hidIdVerbale.Value);

                if (!this._commissioniService.ApprovaAllegatoPerUtenteCorrente(this.Id.Value, idAllegato))
                {
                    ErroreAccesso();
                    return;
                }

                DataBind();
            }
            catch (Exception ex)
            {
                this.Errori.Add(ex.Message);
            }
        }
    }
}