using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Manager;
using Init.SIGePro.Manager.Logic.CalcoloOneri.Rateizzazioni;
using Ninject;
using SIGePro.Net;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Sigepro.net.Archivi.CalcoloOneri
{
    public partial class Archivi_CalcoloOneri_TipiRateizzazione : BasePage
    {
        [Inject]
        public RateizzazioniService _rateizzazioniService { get; set; }

        private class Constants
        {
            public const string AmmortamentoDefault = "DEFAULT";
            public const string AmmortamentoFrancese = "AMMORTAMENTO_FRANCESE";
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            this.ImpostaScriptEliminazione(this.cmdEliminaTipoRateizzazione);
            this.cmdPreview.OnClientClick = "return visualizzaPreview('" + this.TxImportoTest.Item.ClientID + "');";

            if (!this.IsPostBack)
            {
                this.BindComboScadenza();
                this.BindComboDataInizioRata();
                this.BindComboMovimenti();
                this.BindGrid();
                this.BindComboFrequenzaRate();
                this.BindComboTipoRateizzazione();
            }
        }

        private void BindDettaglio(OneriTipiRateizzazione cls)
        {
            this.IsInserting = cls.Tiporateizzazione.GetValueOrDefault(int.MinValue) == int.MinValue;
            this.cmdEliminaTipoRateizzazione.Visible = !this.IsInserting;
            this.lblTipoRateizzazioneEt.Visible = !this.IsInserting;
            this.lblTipoRateizzazione.Visible = !this.IsInserting;
            this.PanelPreview.Visible = !this.IsInserting;
            this.gvListaInteressiLegali.DataSource = null;
            this.gvListaInteressiLegali.DataBind();
            if (this.IsInserting)
            {
                this.BindInserimento();
            }
            else
            {
                this.BindAggiornamento(cls);
            }

            this.multiView.ActiveViewIndex = 1;
        }


        private void BindComboDataInizioRata()
        {
            this.ddlDataInizioRateizzazione.Item.DataSource = new OneriTipiRateizzazioneMgr(this.Database).GetTipiCalcoloInizioRata();
            this.ddlDataInizioRateizzazione.Item.DataBind();
        }

        private void BindComboTipoRateizzazione()
        {
            this.ddlTipoRateizzazioni.Item.DataSource = new OneriTipiRateizzazioneMgr(this.Database).GetTipoRateizzazione();
            this.ddlTipoRateizzazioni.Item.DataBind();
        }

        private void BindComboFrequenzaRate()
        {
            this.ddlFrequenzaRateAmmortamentoFrancese.Item.DataSource = new OneriTipiRateizzazioneMgr(this.Database).GetFrequenzaRate();
            this.ddlFrequenzaRateAmmortamentoFrancese.Item.DataBind();
        }

        private void BindComboScadenza()
        {
            this.ddlComportamentoScadenza.Item.DataSource = (new OneriTipiRateizzazioneMgr(this.Database)).GetTipiScadenze();
            this.ddlComportamentoScadenza.Item.DataBind();
        }

        private void BindInserimento()
        {
            this.ddlComportamentoScadenza.Value = null;
            this.ddlDataInizioRateizzazione.Value = null;
            this.ddlInteressiLegali.SelectedValue = null;
            this.ddlMovimento.Value = null;
            this.chkInteressiLegali.Item.Checked = false;
            this.TxDescrizione.Value = "";
            this.TxNumerorate.Value = "";
            this.TxRipartizioneRate.Value = "0";
            this.TxFrequenzaRate.Value = "0";
            this.TxInteressiRate.Value = "";
            this.TxDataInizioTest.Value = DateTime.Now.ToString("dd/MM/yyyy");
            this.TxDataInizioIntLegaliTest.Value = DateTime.Now.ToString("dd/MM/yyyy");
            this.TxImportoTest.Value = "";

            this.OnDdlDataInizioRateizzazioneChanged(null);
            this.CheckInteressiLegali();

            this.gvListaPreview.DataSource = null;
            this.gvListaPreview.DataBind();

            this.gvListaInteressiLegali.DataSource = null;
            this.gvListaInteressiLegali.DataBind();
        }

        private void BindAggiornamento(OneriTipiRateizzazione cls)
        {
            this.lblTipoRateizzazione.Text = cls.Tiporateizzazione.ToString();
            this.TxDescrizione.Value = cls.Descrizione;
            this.TxNumerorate.Value = cls.Nrorate;
            this.TxRipartizioneRate.Value = cls.Ripartizionerate;
            this.ddlDataInizioRateizzazione.Value = cls.Determdatainiziorate.ToString();
            this.TxFrequenzaRate.Value = cls.Frequenzarate;

            if (this.ddlTipoRateizzazioni.Value == OneriTipiRateizzazioneMgr.TipoRateizzazione.AMMORTAMENTO_FRANCESE.ToString())
            {
                this.TxFrequenzaRate.Value = "0";
                this.TxRipartizioneRate.Value = "0";
            }

            this.ddlComportamentoScadenza.Value = cls.Scadenzarate.ToString();
            this.TxInteressiRate.Value = cls.Interessirate;
            this.chkInteressiLegali.Item.Checked = cls.FlagInteressiLegali == 1 ? true : false;
            this.ddlInteressiLegali.SelectedValue = (cls.TipoAnatocismo.GetValueOrDefault(int.MinValue) == int.MinValue || cls.TipoAnatocismo == 0) ? "0" : cls.TipoAnatocismo.ToString();
            this.txtSpeseRateizzazione.Value = cls.SpeseRateizzazione.GetValueOrDefault(0).ToString();

            this.ddlTipoRateizzazioni.Value = cls.TipologiaRateizzazione ?? OneriTipiRateizzazioneMgr.TipoRateizzazione.DEFAULT.ToString();

            this.OnDdlDataInizioRateizzazioneChanged(cls);
            this.CheckInteressiLegali();

            if (!string.IsNullOrEmpty(this.TxImportoTest.Value))
            {
                this.gvListaPreview.DataSource = this.CalcoloRate(Convert.ToDouble(this.TxImportoTest.Value));
                this.gvListaPreview.DataBind();
            }
        }

        protected void multiView_ActiveViewChanged(object sender, EventArgs e)
        {
            switch (this.multiView.ActiveViewIndex)
            {
                case (0):
                    this.Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Risultato;
                    return;
                case (1):
                    this.Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;
                    this.CheckCampiRateizzazioneFrancese();
                    return;
            }
        }

        #region Scheda risultato
        private void BindGrid()
        {
            OneriTipiRateizzazione otr = new OneriTipiRateizzazione();
            otr.Idcomune = this.IdComune;
            otr.Software = this.Software;
            this.gvLista.DataSource = new OneriTipiRateizzazioneMgr(this.Database).GetList(otr);
            this.gvLista.DataBind();

            this.multiView.ActiveViewIndex = 0;
        }

        protected void gvLista_SelectedIndexChanged(object sender, EventArgs e)
        {
            int tipoRateizzazione = Convert.ToInt32(this.gvLista.DataKeys[this.gvLista.SelectedIndex].Value);
            OneriTipiRateizzazione cls = new OneriTipiRateizzazioneMgr(this.Database).GetById(this.IdComune, tipoRateizzazione);

            this.TxDataInizioTest.Value = DateTime.Now.ToString("dd/MM/yyyy");
            this.TxDataInizioIntLegaliTest.Value = DateTime.Now.ToString("dd/MM/yyyy");
            this.TxImportoTest.Value = "";
            this.gvListaPreview.DataSource = null;
            this.gvListaPreview.DataBind();

            this.BindDettaglio(cls);
        }

        protected void gvLista_RowDataBound(object sender, GridViewRowEventArgs e)
        {
            ImageButton cmdElimina = e.Row.FindControl("cmdElimina") as ImageButton;

            if (cmdElimina != null)
                this.ImpostaScriptEliminazione(cmdElimina);
        }

        protected void gvLista_RowDeleting(object sender, GridViewDeleteEventArgs e)
        {
            int tipoRateizzazione = Convert.ToInt32(this.gvLista.DataKeys[e.RowIndex].Value);

            OneriTipiRateizzazioneMgr mgrotr = new OneriTipiRateizzazioneMgr(this.Database);
            OneriTipiRateizzazione otr = mgrotr.GetById(this.IdComune, tipoRateizzazione);
            mgrotr.Delete(otr);

            this.BindGrid();
        }

        public void cmdNuovo_Click(object sender, EventArgs e)
        {
            this.BindDettaglio(new OneriTipiRateizzazione());
        }

        public void cmdChiudi_Click(object sender, EventArgs e)
        {
            base.CloseCurrentPage();
        }
        #endregion


        #region Scheda inserimento
        public void cmdChiudiLista_Click(object sender, EventArgs e)
        {
            this.multiView.ActiveViewIndex = 0;
        }
        #endregion


        #region Scheda dettaglio
        protected void cmdSalva_Click(object sender, EventArgs e)
        {
            OneriTipiRateizzazioneMgr mgr = new OneriTipiRateizzazioneMgr(this.Database);
            OneriTipiRateizzazione cls = null;

            if (this.IsInserting)
            {
                cls = new OneriTipiRateizzazione();
                cls.Idcomune = this.IdComune;
                cls.Software = this.Software;
            }
            else
            {
                int id = Convert.ToInt32(this.lblTipoRateizzazione.Text);
                cls = mgr.GetById(this.IdComune, id);
            }

            try
            {
                cls.Descrizione = this.TxDescrizione.Value;
                cls.Nrorate = this.TxNumerorate.Value;
                cls.Ripartizionerate = this.TxRipartizioneRate.Value;

                cls.Determdatainiziorate = Convert.ToInt32(this.ddlDataInizioRateizzazione.Value);

                if (this.ddlDataInizioRateizzazione.Value == "3")
                    cls.FkTipomovDetermdatain = this.ddlMovimento.Value;

                cls.Frequenzarate = this.TxFrequenzaRate.Value;

                if (this.ddlTipoRateizzazioni.Value == OneriTipiRateizzazioneMgr.TipoRateizzazione.AMMORTAMENTO_FRANCESE.ToString())
                {
                    cls.Frequenzarate = this.ddlFrequenzaRateAmmortamentoFrancese.Value;
                    cls.Ripartizionerate = "0";
                }

                cls.Scadenzarate = Convert.ToInt32(this.ddlComportamentoScadenza.Value);
                cls.Interessirate = this.TxInteressiRate.Value;

                if (this.chkInteressiLegali.Item.Checked)
                {
                    cls.FlagInteressiLegali = 1;
                    cls.TipoAnatocismo = this.ddlInteressiLegali.SelectedValue == "0" ? (int?)null : Convert.ToInt32(this.ddlInteressiLegali.SelectedValue);
                }
                else
                {
                    cls.FlagInteressiLegali = 0;
                }

                cls.TipologiaRateizzazione = this.ddlTipoRateizzazioni.Value;
                cls.SpeseRateizzazione = String.IsNullOrEmpty(this.txtSpeseRateizzazione.Value) ? 0 : Convert.ToDecimal(this.txtSpeseRateizzazione.Value);

                if (this.IsInserting)
                    cls = mgr.Insert(cls);
                else
                    cls = mgr.Update(cls);

                this.BindDettaglio(cls);
            }
            catch (RequiredFieldException rfe)
            {
                this.MostraErrore("Attenzione, i campi contrassegnati con un asterisco sono obbligatori.", rfe);
            }
            catch (Exception ex)
            {
                this.MostraErrore(this.IsInserting ? AmbitoErroreEnum.Inserimento : AmbitoErroreEnum.Aggiornamento, ex);
            }
        }


        protected void cmdChiudiDettaglio_Click(object sender, EventArgs e)
        {
            this.BindGrid();
            this.multiView.ActiveViewIndex = 0;
        }
        #endregion

        protected void cmdEliminaTipoRateizzazione_Click(object sender, EventArgs e)
        {
            OneriTipiRateizzazioneMgr mgrotr = new OneriTipiRateizzazioneMgr(this.Database);
            OneriTipiRateizzazione otr = mgrotr.GetById(this.IdComune, Convert.ToInt32(this.lblTipoRateizzazione.Text));
            mgrotr.Delete(otr);

            this.BindGrid();

            this.multiView.ActiveViewIndex = 0;
        }

        private void OnDdlDataInizioRateizzazioneChanged(OneriTipiRateizzazione tipoRateizzazione)
        {
            if (this.ddlDataInizioRateizzazione.Value == "3")
            {
                this.ddlMovimento.Visible = true;
                this.ddlMovimento.Value = tipoRateizzazione == null ? null : tipoRateizzazione.FkTipomovDetermdatain;
            }
            else
            {
                this.ddlMovimento.Visible = false;
            }
        }

        protected void ddlDataInizioRateizzazione_ValueChanged(object sender, EventArgs e)
        {
            this.OnDdlDataInizioRateizzazioneChanged(null);
        }

        private void BindComboMovimenti()
        {
            TipiMovimento tipiMov = new TipiMovimento();
            tipiMov.Idcomune = this.IdComune;
            tipiMov.Software = this.Software;
            TipiMovimentoMgr tipiMovMgr = new TipiMovimentoMgr(this.Database);
            List<TipiMovimento> list = tipiMovMgr.GetList(tipiMov);

            this.ddlMovimento.Item.DataSource = list;
            this.ddlMovimento.Item.DataBind();
        }

        protected void TxNumerorate_ValueChanged(object sender, EventArgs e)
        {
            if (!string.IsNullOrEmpty(this.TxNumerorate.Value))
            {
                //Modifica il campo Ripartizione rate
                Rateizzazione calcoloRate = new Rateizzazione();
                calcoloRate.NumeroRate = Convert.ToInt32(this.TxNumerorate.Value);

                string sRipartizioneRate = "";
                for (int iCount = 0; iCount < Convert.ToInt32(this.TxNumerorate.Value); iCount++)
                    sRipartizioneRate += calcoloRate.CalcolaRipartizioneRata(iCount) + ";";

                sRipartizioneRate = sRipartizioneRate.Remove(sRipartizioneRate.Length - 1);

                this.TxRipartizioneRate.Value = sRipartizioneRate;

                if (this.ddlTipoRateizzazioni.Value == OneriTipiRateizzazioneMgr.TipoRateizzazione.DEFAULT.ToString())
                {
                    //Modifica il campo Frequenza rate
                    if (string.IsNullOrEmpty(this.TxFrequenzaRate.Value))
                    {
                        string sFrequenzaRate = "";
                        for (int iCount = 0; iCount < Convert.ToInt32(this.TxNumerorate.Value); iCount++)
                            sFrequenzaRate += "30;";

                        sFrequenzaRate = sFrequenzaRate.Remove(sFrequenzaRate.Length - 1);
                        this.TxFrequenzaRate.Value = sFrequenzaRate;
                    }
                    else
                    {
                        string[] aFrequenzaRate = this.TxFrequenzaRate.Value.Split(new Char[] { ';' });
                        if (Convert.ToInt32(this.TxNumerorate.Value) < aFrequenzaRate.Length)
                        {
                            this.TxFrequenzaRate.Value = "";
                            string sFrequenzaRate = "";
                            for (int iCount = 0; iCount < Convert.ToInt32(this.TxNumerorate.Value); iCount++)
                                sFrequenzaRate += aFrequenzaRate[iCount] + ";";

                            sFrequenzaRate = sFrequenzaRate.Remove(sFrequenzaRate.Length - 1);
                            this.TxFrequenzaRate.Value = sFrequenzaRate;
                        }
                    }
                }
                else
                {
                    this.TxFrequenzaRate.Value = "0";
                    this.TxRipartizioneRate.Value = "0";
                }
                //Modifica il campo Interessi
                this.TxInteressiRate.Value = "";
            }
        }

        protected void cmdPreview_Click(object sender, EventArgs e)
        {
            this.PanelGridPreview.Visible = true;
            this.gvListaPreview.DataSource = this.CalcoloRate(Convert.ToDouble(this.TxImportoTest.Value));
            this.gvListaPreview.DataBind();
        }

        private IEnumerable<IstanzeOneri> CalcoloRate(double dImporto)
        {
            try
            {
                var tipoRateizzazione = Convert.ToInt32(this.lblTipoRateizzazione.Text);
                var data = this.TxDataInizioTest.Item.DateValue.Value;
                var dataInizio = (this.chkInteressiLegali.Item.Checked) ? this.TxDataInizioIntLegaliTest.Item.DateValue.Value : (DateTime?)null;
                var importo = Convert.ToDecimal(dImporto);
                var speseRateizzazione = String.IsNullOrEmpty(this.txtSpeseRateizzazione.Value) ? 0 : Convert.ToDecimal(this.txtSpeseRateizzazione.Value);

                var datiRateizzati = this._rateizzazioniService.CalcolaRate(this.Token, tipoRateizzazione, data, dataInizio, importo, speseRateizzazione).ToList();

                var istanzeOneriList = new List<IstanzeOneri>();

                return datiRateizzati.Select(x => new IstanzeOneri
                {
                    PREZZO = Math.Round(Convert.ToDouble(x.Prezzo.GetValueOrDefault(0)), 2),
                    PREZZOISTRUTTORIA = 0,
                    DATASCADENZA = x.DataScadenza,
                    NUMERORATA = x.NumeroRata.ToString(),
                    ImportoInteressi = Math.Round(x.Interesse.GetValueOrDefault(0), 2),
                    QuotaCapitale = Math.Round(x.QuotaCapitale.GetValueOrDefault(0), 2),
                    CapitaleResiduo = Math.Round(x.CapitaleResiduo.GetValueOrDefault(0), 2)
                });
            }
            catch (Exception ex)
            {
                this.MostraErrore("Attenzione, non è possibile effettuare la rateizzazione per il seguente motivo: " + ex.Message, ex);

                return new List<IstanzeOneri>();
            }
        }

        private void CheckInteressiLegali()
        {
            this.TxInteressiRate.Visible = !this.chkInteressiLegali.Item.Checked;
            this.ddlInteressiLegali.Visible = this.chkInteressiLegali.Item.Checked;
            this.TxDataInizioIntLegaliTest.Visible = this.chkInteressiLegali.Item.Checked;
            this.imgBtnDetail.Visible = this.chkInteressiLegali.Item.Checked;
            this.imgBtnCancel.Visible = this.chkInteressiLegali.Item.Checked;

            if (this.chkInteressiLegali.Item.Checked)
                this.TxDataInizioTest.Descrizione = "Data finale";
            else
                this.TxDataInizioTest.Descrizione = "Data di inizio rateizzazione";
        }

        protected void chkInteressiLegali_ValueChanged(object sender, EventArgs e)
        {
            this.CheckInteressiLegali();
        }

        protected void imgBtnDetail_Click(object sender, ImageClickEventArgs e)
        {
            this.gvListaInteressiLegali.DataSource = new InteressiLegaliMgr(this.Database).GetList(new InteressiLegali());
            this.gvListaInteressiLegali.DataBind();
        }

        protected void ingBtnCancel_Click(object sender, ImageClickEventArgs e)
        {
            this.gvListaInteressiLegali.DataSource = null;
            this.gvListaInteressiLegali.DataBind();
        }

        protected void ddlFrequenzaRateAmmortamentoFrancese_ValueChanged(object sender, EventArgs e)
        {

        }

        protected void ddlTipoRateizzazioni_ValueChanged(object sender, EventArgs e)
        {
            this.CheckCampiRateizzazioneFrancese();
        }

        private void CheckCampiRateizzazioneFrancese()
        {
            this.ddlFrequenzaRateAmmortamentoFrancese.Visible = false;
            this.TxFrequenzaRate.Visible = true;
            this.TxRipartizioneRate.Enabled = true;
            this.chkInteressiLegali.Visible = true;

            if (this.ddlTipoRateizzazioni.Value == OneriTipiRateizzazioneMgr.TipoRateizzazione.AMMORTAMENTO_FRANCESE.ToString())
            {
                this.TxRipartizioneRate.Value = "0";
                this.TxRipartizioneRate.Enabled = false;
                this.TxFrequenzaRate.Visible = false;
                this.ddlFrequenzaRateAmmortamentoFrancese.Visible = true;
                this.chkInteressiLegali.Item.Checked = false;
                this.chkInteressiLegali.Visible = false;
            }
        }

    }
}
