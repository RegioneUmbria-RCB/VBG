using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using SIGePro.Net;
using System;
using System.Linq;
using static Init.SIGePro.Manager.IstanzeMgr;

namespace Sigepro.net.Istanze.CalcoloOneri.CostoCostruzione
{
    public partial class CCICalcoliTotInsert : BasePage
    {
        public class DropDownItem
        {
            public int? Id { get; set; }
            public string Descrizione { get; set; }
        }

        private int CodiceIstanza
        {
            get
            {
                var codiceIstanza = this.Request.QueryString["CodiceIstanza"];

                if (String.IsNullOrEmpty(codiceIstanza))
                    throw new ArgumentException("Codice istanza non passato");

                return int.Parse(codiceIstanza);
            }
        }

        public override string Software => this.Istanza.Software;

        private DatiMinimiIstanza _istanza = null;
        private DatiMinimiIstanza Istanza
        {
            get
            {
                if (this._istanza == null)
                    this._istanza = new IstanzeMgr(this.AuthenticationInfo.CreateDatabase()).GetDatiMinimi(this.IdComune, this.CodiceIstanza);

                return this._istanza;
            }
        }



        protected void Page_Load(object sender, EventArgs e)
        {
            this.Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;

            if (!this.IsPostBack)
            {
                this.BindInserimento();
            }
        }

        private void BindInserimento()
        {
            var cls = new CCICalcoloTot();

            this.txtData.DateValue = DateTime.Now.Date;
            this.txtDescrizione.Text = "";

            this.ddlFkCCVCID.DataSource = new CCValiditaCoefficientiMgr(this.Database).GetList(this.IdComune, this.Istanza.Software);
            this.ddlFkCCVCID.DataBind();

            this.SetCoefficienti();

            this.ddlFkCCVCID.SelectedIndex = 0;

            this.ddlFfOCCBTIID.DataSource = new OCCBaseTipoInterventoMgr(this.Database).GetList(null, null);
            this.ddlFfOCCBTIID.DataBind();

            this.ddlFfOCCBTIID.SelectedIndex = 0;

            this.ddlFkOCCBDEID.DataSource = new CCDestinazioniMgr(this.Database).GetBaseDestinazioniList(this.IdComune);
            this.ddlFkOCCBDEID.DataBind();

            this.ddlFkOCCBDEID.SelectedIndex = 0;

            this.BindListaTipiCalcoloBase(this, EventArgs.Empty);
            this.FillDropDownInterventoDettaglio(String.Empty);
            this.FillDropDownDestinazioniDettaglio(String.Empty);
        }

        protected void BindListaTipiCalcoloBase(object sender, EventArgs e)
        {
            this.ddlFkBCCTCID.DataSource = new CCICalcoloTotMgr(this.Database).GetTipiCalcoloBase(this.IdComune, this.Istanza.Software, this.ddlFfOCCBTIID.SelectedValue, this.ddlFkOCCBDEID.SelectedValue);
            this.ddlFkBCCTCID.DataBind();
        }

        protected void txtData_TextChanged(object sender, EventArgs e)
        {
            if (this.txtData.DateValue.HasValue && this.txtData.DateValue.GetValueOrDefault(DateTime.MinValue) != DateTime.MinValue)
            {
                this.SetCoefficienti();
            }
        }

        protected void SetCoefficienti()
        {
            var ccvc = new CCValiditaCoefficientiMgr(this.Database).GetCoefficienteAllaData(this.IdComune, this.Istanza.Software, this.txtData.DateValue.Value);

            if (ccvc == null) return;

            this.ddlFkCCVCID.SelectedValue = ccvc.Id.ToString();
        }

        protected void cmdInserisci_Click(object sender, EventArgs e)
        {
            var cls = new CCICalcoloTot();
            cls.Idcomune = this.AuthenticationInfo.IdComune;
            cls.Codiceistanza = this.CodiceIstanza;
            cls.Data = this.txtData.DateValue;
            cls.Descrizione = string.IsNullOrEmpty(this.txtDescrizione.Text) ? this.ddlFkOCCBDEID.SelectedItem.Text + " - " + this.ddlFfOCCBTIID.SelectedItem.Text : this.txtDescrizione.Text;
            cls.FkBcctcId = this.ddlFkBCCTCID.SelectedValue;
            cls.FkCcvcId = int.Parse(this.ddlFkCCVCID.SelectedValue);
            cls.FkOccbdeId = this.ddlFkOCCBDEID.SelectedValue;
            cls.FkOccbtiId = this.ddlFfOCCBTIID.SelectedValue;
            cls.FkInterventoDettaglioId = String.IsNullOrEmpty(this.ddlTipoInterventoDettaglio.SelectedValue) ?
                                            (int?)null :
                                            int.Parse(this.ddlTipoInterventoDettaglio.SelectedValue);

            cls.FkDestinazioneDettaglioId = String.IsNullOrEmpty(this.ddlTipoDestinazioneDettaglio.SelectedValue) ?
                                            (int?)null :
                                            int.Parse(this.ddlTipoDestinazioneDettaglio.SelectedValue);

            var mgr = new CCICalcoloTotMgr(this.Database);

            try
            {
                cls = mgr.Insert(cls);

                var url = $"~/Istanze/CalcoloOneri/CostoCostruzione/CCICalcoliTot.aspx?Token={this.AuthenticationInfo.Token}&CodiceIstanza={this.CodiceIstanza}&IdCalcoloTot={cls.Id.Value}";

                this.Response.Redirect(url);
            }
            catch (Exception ex)
            {
                this.MostraErrore("Errore durante l'inserimento: " + ex.Message, ex);
            }
        }

        protected void cmdChiudiInserimento_Click(object sender, EventArgs e)
        {
            var url = $"~/Istanze/CalcoloOneri/CostoCostruzione/CCICalcoliTot.aspx?Token={this.AuthenticationInfo.Token}&CodiceIstanza={this.CodiceIstanza}";

            this.Response.Redirect(url);
        }

        protected void OnTipoInterventoBaseModificato(object sender, EventArgs e)
        {
            this.BindListaTipiCalcoloBase(this, EventArgs.Empty);

            this.FillDropDownInterventoDettaglio(this.ddlFfOCCBTIID.SelectedValue);
        }

        private void FillDropDownInterventoDettaglio(string idInterventoBase)
        {
            var dataSource = new CCTipoInterventoMgr(this.Database).GetListByTipoInterventoBase(this.IdComune, idInterventoBase)
                                                                    .Select(x => new DropDownItem
                                                                    {
                                                                        Id = x.Id.Value,
                                                                        Descrizione = x.Intervento
                                                                    }).ToList();

            dataSource.Insert(0, new DropDownItem { Id = null, Descrizione = "Non definito" });

            this.ddlTipoInterventoDettaglio.SelectedValue = "";
            this.ddlTipoInterventoDettaglio.DataSource = dataSource;
            this.ddlTipoInterventoDettaglio.DataBind();
        }

        private void FillDropDownDestinazioniDettaglio(string idDestinazioneBase)
        {
            var dataSource = new CCDestinazioniMgr(this.Database).GetListByDestinazioneBase(this.IdComune, idDestinazioneBase)
                                                                    .Select(x => new DropDownItem
                                                                    {
                                                                        Id = x.Id.Value,
                                                                        Descrizione = x.Destinazione
                                                                    }).ToList();
            dataSource.Insert(0, new DropDownItem { Id = null, Descrizione = "Non definita" });

            this.ddlTipoDestinazioneDettaglio.SelectedValue = "";
            this.ddlTipoDestinazioneDettaglio.DataSource = dataSource;
            this.ddlTipoDestinazioneDettaglio.DataBind();
        }

        protected void OnTipoDestinazioneBaseModificato(object sender, EventArgs e)
        {
            this.BindListaTipiCalcoloBase(this, EventArgs.Empty);

            this.FillDropDownDestinazioniDettaglio(this.ddlFkOCCBDEID.SelectedValue);
        }
    }
}