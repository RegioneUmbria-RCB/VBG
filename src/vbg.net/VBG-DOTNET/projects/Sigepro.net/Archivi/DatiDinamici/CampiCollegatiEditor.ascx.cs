using Init.SIGePro.Manager.Logic.DatiDinamici.ConfigurazioneSchede.SchedeCollegateACampi;
using Ninject;
using Ninject.Web;
using SIGePro.Net;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.UI.WebControls;

namespace Sigepro.net.Archivi.DatiDinamici
{
    public partial class CampiCollegatiEditor : UserControlBase
    {
        [Inject]
        public SchedeCollegateACampiService _schedeCollegateACampiService { get; set; }

        public int? IdModello
        {
            get
            {
                var obj = this.ViewState["IdModello"];
                if (obj == null)
                    return null;
                return (int)obj;
            }

            set
            {
                this.ViewState["IdModello"] = value;
            }
        }

        protected bool MostraPopup
        {
            set => this.ViewState["MostraPopup"] = value;
            get => this.ViewState["MostraPopup"] != null && (bool)this.ViewState["MostraPopup"];
        }

        public string Software => ((BasePage)this.Page).Software;

        protected List<string> Errori
        {
            get { return (this.Page.Master as SigeproNetMaster).Errori; }
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!this.IdModello.HasValue)
            {
                throw new InvalidOperationException("IdModello non valorizzato");
            }

            if (!this.IsPostBack)
            {
                this.ddlFiltroSoftware.Item.DataSource = this._schedeCollegateACampiService.GetSoftwareCampiDinamici();
                this.ddlFiltroSoftware.Item.DataBind();
                this.ddlFiltroSoftware.Value = this.Software;
            }
        }

        public override void DataBind()
        {
            var listaCampi = this._schedeCollegateACampiService.GetCampiCollegatiDaIdScheda(this.IdModello.Value);
            this.rptDipendenzeCampi.DataSource = listaCampi;
            this.rptDipendenzeCampi.DataBind();

            this.ltrNumeroCampi.Text = listaCampi.Count().ToString();

            base.DataBind();
        }

        protected void bmAggiungiCampoCollegato_KoClicked(object sender, EventArgs e)
        {
            this.txtFiltroNomeCampo.Value = "";
            this.MostraPopup = false;
        }

        protected void cmdCercaCampiCollegati_Click(object sender, EventArgs e)
        {

            this.rptCampiCollegati.DataSource = this._schedeCollegateACampiService.RicercaCampiDinamici(this.ddlFiltroSoftware.Value, this.txtFiltroNomeCampo.Value);
            this.rptCampiCollegati.DataBind();

        }

        protected void cmdAggiungiCampoCollegato_Click(object sender, EventArgs e)
        {
            var lb = (LinkButton)sender;
            var idCampo = Convert.ToInt32(lb.CommandArgument);

            try
            {
                this._schedeCollegateACampiService.AggiungiCampoCollegato(this.IdModello.Value, idCampo);

                this.DataBind();

                this.bmAggiungiCampoCollegato_KoClicked(this, EventArgs.Empty);
            }
            catch (Exception ex)
            {
                this.Errori.Add("Si è verificato un errore durante l'aggiunta del campo: " + ex.Message);
            }
        }

        protected void aggiungiCampiCollegati_Click(object sender, EventArgs e)
        {
            this.MostraPopup = true;
        }


        protected void bmRimuoviCampo_OkClicked(object sender, EventArgs e)
        {
            try
            {
                this._schedeCollegateACampiService.RimuoviCampoCollegato(this.IdModello.Value, Convert.ToInt32(this.hfIdCampoCollegatoDaRimuovere.Value));

                this.DataBind();

                this.bmAggiungiCampoCollegato_KoClicked(this, EventArgs.Empty);
            }
            catch (Exception ex)
            {
                this.Errori.Add("Si è verificato un errore durante l'aggiunta del campo: " + ex.Message);
            }
        }
    }
}