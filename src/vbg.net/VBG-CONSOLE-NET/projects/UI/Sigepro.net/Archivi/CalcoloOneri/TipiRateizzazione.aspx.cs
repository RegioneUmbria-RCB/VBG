using System;
using System.Linq;
using System.Data;
using System.Configuration;
using System.Collections;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;
using Init.SIGePro.Data;
using SIGePro.Net;
using SIGePro.Net.Navigation;
using Init.SIGePro.Manager;
using System.Collections.Generic;
using Init.SIGePro.Manager.Logic.CalcoloOneri.Rateizzazioni;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Manager.Configuration;

namespace Sigepro.net.Archivi.CalcoloOneri
{
    public partial class Archivi_CalcoloOneri_TipiRateizzazione : BasePage
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            ImpostaScriptEliminazione(cmdEliminaTipoRateizzazione);
            cmdPreview.OnClientClick = "return visualizzaPreview('" + TxImportoTest.Item.ClientID + "');";

            if (!IsPostBack)
            {
                BindComboScadenza();
                BindComboDataInizioRata();
                BindComboMovimenti();
                BindGrid();
            }
        }

        private void BindDettaglio(OneriTipiRateizzazione cls)
        {
            this.IsInserting = cls.Tiporateizzazione.GetValueOrDefault(int.MinValue) == int.MinValue;
            cmdEliminaTipoRateizzazione.Visible = !IsInserting;
            this.lblTipoRateizzazioneEt.Visible = !IsInserting;
            this.lblTipoRateizzazione.Visible = !IsInserting;
            this.PanelPreview.Visible = !IsInserting;
            this.gvListaInteressiLegali.DataSource = null;
            this.gvListaInteressiLegali.DataBind();
            if (IsInserting)
            {
                BindInserimento();  
            }
            else
            {
                BindAggiornamento(cls);
            }

            this.multiView.ActiveViewIndex = 1;
        }


        private void BindComboDataInizioRata()
        {
            this.ddlDataInizioRateizzazione.Item.DataSource = new OneriTipiRateizzazioneMgr(Database).GetTipiCalcoloInizioRata();
            this.ddlDataInizioRateizzazione.Item.DataBind();
        }

        private void BindComboScadenza()
        {
            this.ddlComportamentoScadenza.Item.DataSource = (new OneriTipiRateizzazioneMgr(Database)).GetTipiScadenze();
            this.ddlComportamentoScadenza.Item.DataBind();
        }

        private void BindInserimento()
        {
            ddlComportamentoScadenza.Value = null;
            ddlDataInizioRateizzazione.Value = null;
            ddlInteressiLegali.SelectedValue = null;
            ddlMovimento.Value = null;
            chkInteressiLegali.Item.Checked = false;
            this.TxDescrizione.Value = "";
            this.TxNumerorate.Value = "";
            this.TxRipartizioneRate.Value = "";
            this.TxFrequenzaRate.Value = "";
            this.TxInteressiRate.Value = "";
            this.TxDataInizioTest.Value = DateTime.Now.ToString("dd/MM/yyyy");
            this.TxDataInizioIntLegaliTest.Value = DateTime.Now.ToString("dd/MM/yyyy");
            this.TxImportoTest.Value = "";

            OnDdlDataInizioRateizzazioneChanged(null);
            CheckInteressiLegali();

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
            this.ddlComportamentoScadenza.Value = cls.Scadenzarate.ToString();
            this.TxInteressiRate.Value = cls.Interessirate;
            this.chkInteressiLegali.Item.Checked = cls.FlagInteressiLegali == 1 ? true : false;
            this.ddlInteressiLegali.SelectedValue = (cls.TipoAnatocismo.GetValueOrDefault(int.MinValue) == int.MinValue || cls.TipoAnatocismo == 0) ? "0" : cls.TipoAnatocismo.ToString();

            OnDdlDataInizioRateizzazioneChanged(cls);
            CheckInteressiLegali();

            if (!string.IsNullOrEmpty(TxImportoTest.Value))
            {
                gvListaPreview.DataSource = CalcoloRate(Convert.ToDouble(TxImportoTest.Value));
                gvListaPreview.DataBind();
            }
        }

        protected void multiView_ActiveViewChanged(object sender, EventArgs e)
        {
            switch (multiView.ActiveViewIndex)
            {
                case (0):
                    Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Risultato;
                    return;
                case (1):
                    Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;
                    return;
            }
        }

        #region Scheda risultato
        private void BindGrid()
        {
            OneriTipiRateizzazione otr = new OneriTipiRateizzazione();
            otr.Idcomune = IdComune;
            otr.Software = Software;
            this.gvLista.DataSource = new OneriTipiRateizzazioneMgr(Database).GetList(otr);
            this.gvLista.DataBind();

            multiView.ActiveViewIndex = 0;
        }

        protected void gvLista_SelectedIndexChanged(object sender, EventArgs e)
        {
            int tipoRateizzazione = Convert.ToInt32(gvLista.DataKeys[gvLista.SelectedIndex].Value);
            OneriTipiRateizzazione cls = new OneriTipiRateizzazioneMgr(Database).GetById(IdComune, tipoRateizzazione);

            this.TxDataInizioTest.Value = DateTime.Now.ToString("dd/MM/yyyy");
            this.TxDataInizioIntLegaliTest.Value = DateTime.Now.ToString("dd/MM/yyyy");
            this.TxImportoTest.Value = "";
            this.gvListaPreview.DataSource = null;
            this.gvListaPreview.DataBind();

            BindDettaglio(cls);
        }

        protected void gvLista_RowDataBound(object sender, GridViewRowEventArgs e)
        {
            ImageButton cmdElimina = e.Row.FindControl("cmdElimina") as ImageButton;

            if (cmdElimina != null)
                ImpostaScriptEliminazione(cmdElimina);
        }

        protected void gvLista_RowDeleting(object sender, GridViewDeleteEventArgs e)
        {
            int tipoRateizzazione = Convert.ToInt32(gvLista.DataKeys[e.RowIndex].Value);

            OneriTipiRateizzazioneMgr mgrotr = new OneriTipiRateizzazioneMgr(Database);
            OneriTipiRateizzazione otr = mgrotr.GetById(IdComune, tipoRateizzazione);
            mgrotr.Delete(otr);

            BindGrid();
        }

        public void cmdNuovo_Click(object sender, EventArgs e)
        {
            BindDettaglio(new OneriTipiRateizzazione());
        }

        public void cmdChiudi_Click(object sender, EventArgs e)
        {
			base.CloseCurrentPage(); 
        }
        #endregion


        #region Scheda inserimento
        public void cmdChiudiLista_Click(object sender, EventArgs e)
        {
            multiView.ActiveViewIndex = 0;
        }
        #endregion


        #region Scheda dettaglio
        protected void cmdSalva_Click(object sender, EventArgs e)
        {
            OneriTipiRateizzazioneMgr mgr = new OneriTipiRateizzazioneMgr(Database);
            OneriTipiRateizzazione cls = null;

            if (IsInserting)
            {
                cls = new OneriTipiRateizzazione();
                cls.Idcomune = IdComune;
                cls.Software = Software;
            }
            else
            {
                int id = Convert.ToInt32(this.lblTipoRateizzazione.Text);
                cls = mgr.GetById(IdComune, id);
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
                cls.Scadenzarate = Convert.ToInt32(this.ddlComportamentoScadenza.Value);
                cls.Interessirate = this.TxInteressiRate.Value;

                if (chkInteressiLegali.Item.Checked)
                {
                    cls.FlagInteressiLegali = 1;
                    cls.TipoAnatocismo = ddlInteressiLegali.SelectedValue == "0" ? (int?)null : Convert.ToInt32(ddlInteressiLegali.SelectedValue);
                }
                else
                {
                    cls.FlagInteressiLegali = 0;
                }


                if (IsInserting)
                    cls = mgr.Insert(cls);
                else
                    cls = mgr.Update(cls);

                BindDettaglio(cls);
            }
            catch (RequiredFieldException rfe)
            {
                MostraErrore("Attenzione, i campi contrassegnati con un asterisco sono obbligatori.", rfe);
            }
            catch (Exception ex)
            {
                MostraErrore(IsInserting ? AmbitoErroreEnum.Inserimento : AmbitoErroreEnum.Aggiornamento, ex);
            }
        }


        protected void cmdChiudiDettaglio_Click(object sender, EventArgs e)
        {
            BindGrid();
            multiView.ActiveViewIndex = 0;
        }
        #endregion

        protected void cmdEliminaTipoRateizzazione_Click(object sender, EventArgs e)
        {
            OneriTipiRateizzazioneMgr mgrotr = new OneriTipiRateizzazioneMgr(Database);
            OneriTipiRateizzazione otr = mgrotr.GetById(IdComune, Convert.ToInt32(this.lblTipoRateizzazione.Text));
            mgrotr.Delete(otr);

            BindGrid();

            multiView.ActiveViewIndex = 0;
        }

        private void OnDdlDataInizioRateizzazioneChanged(OneriTipiRateizzazione tipoRateizzazione)
        {
            if (this.ddlDataInizioRateizzazione.Value == "3")
            {
                ddlMovimento.Visible = true;
                ddlMovimento.Value = tipoRateizzazione == null ? null : tipoRateizzazione.FkTipomovDetermdatain;
            }
            else
            {
                ddlMovimento.Visible = false;
            }
        }

        protected void ddlDataInizioRateizzazione_ValueChanged(object sender, EventArgs e)
        {
            OnDdlDataInizioRateizzazioneChanged(null);
        }

        private void BindComboMovimenti()
        {
            TipiMovimento tipiMov = new TipiMovimento();
            tipiMov.Idcomune = IdComune;
            tipiMov.Software = Software;
            TipiMovimentoMgr tipiMovMgr = new TipiMovimentoMgr(Database);
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
                for (int iCount = 0; iCount < Convert.ToInt32(TxNumerorate.Value); iCount++)
                    sRipartizioneRate += calcoloRate.CalcolaRipartizioneRata(iCount) + ";";

                sRipartizioneRate = sRipartizioneRate.Remove(sRipartizioneRate.Length - 1);

                this.TxRipartizioneRate.Value = sRipartizioneRate;

                //Modifica il campo Frequenza rate
                if (string.IsNullOrEmpty(this.TxFrequenzaRate.Value))
                {
                    string sFrequenzaRate = "";
                    for (int iCount = 0; iCount < Convert.ToInt32(TxNumerorate.Value); iCount++)
                        sFrequenzaRate += "30;";

                    sFrequenzaRate = sFrequenzaRate.Remove(sFrequenzaRate.Length - 1);
                    this.TxFrequenzaRate.Value = sFrequenzaRate;
                }
                else
                {
                    string[] aFrequenzaRate = TxFrequenzaRate.Value.Split(new Char[] { ';' });
                    if (Convert.ToInt32(TxNumerorate.Value) < aFrequenzaRate.Length)
                    {
                        TxFrequenzaRate.Value = "";
                        string sFrequenzaRate = "";
                        for (int iCount = 0; iCount < Convert.ToInt32(TxNumerorate.Value); iCount++)
                            sFrequenzaRate += aFrequenzaRate[iCount] + ";";

                        sFrequenzaRate = sFrequenzaRate.Remove(sFrequenzaRate.Length - 1);
                        this.TxFrequenzaRate.Value = sFrequenzaRate;
                    }
                }

                //Modifica il campo Interessi
                this.TxInteressiRate.Value = "";
            }
        }

        protected void cmdPreview_Click(object sender, EventArgs e)
        {
            gvListaPreview.DataSource = CalcoloRate(Convert.ToDouble(TxImportoTest.Value));
            gvListaPreview.DataBind();
        }

        private IEnumerable<IstanzeOneri> CalcoloRate(double dImporto)
        {
			try
			{
				var tipoRateizzazione = Convert.ToInt32(lblTipoRateizzazione.Text);
				var data = this.TxDataInizioTest.Item.DateValue.Value;
				var dataInizio = (chkInteressiLegali.Item.Checked) ? this.TxDataInizioIntLegaliTest.Item.DateValue.Value : (DateTime?)null;
				var importo = Convert.ToDecimal(dImporto);

				return new RateizzazioniService(Token).Rateizza(tipoRateizzazione, data, dataInizio, importo)
													  .Select(x =>
															new IstanzeOneri
															{
																PREZZO = x.Prezzo,
																PREZZOISTRUTTORIA = 0,
																DATASCADENZA = x.DataScadenza,
																NUMERORATA = x.NumeroRata.ToString()
															}
													  );
			}
			catch (Exception ex)
			{
				MostraErrore("Attenzione, non è possibile effettuare la rateizzazione per il seguente motivo: " + ex.Message, ex);

				return new List<IstanzeOneri>();
			}
        }

        private void CheckInteressiLegali()
        {
            TxInteressiRate.Visible = !chkInteressiLegali.Item.Checked;
            ddlInteressiLegali.Visible = chkInteressiLegali.Item.Checked;
            TxDataInizioIntLegaliTest.Visible = chkInteressiLegali.Item.Checked;
            imgBtnDetail.Visible = chkInteressiLegali.Item.Checked;
            imgBtnCancel.Visible = chkInteressiLegali.Item.Checked;

            if (chkInteressiLegali.Item.Checked)
                TxDataInizioTest.Descrizione = "Data finale";
            else
                TxDataInizioTest.Descrizione = "Data di inizio rateizzazione";
        }

        protected void chkInteressiLegali_ValueChanged(object sender, EventArgs e)
        {
            CheckInteressiLegali();
        }

        protected void imgBtnDetail_Click(object sender, ImageClickEventArgs e)
        {
            this.gvListaInteressiLegali.DataSource = new InteressiLegaliMgr(Database).GetList(new InteressiLegali()) ;
            this.gvListaInteressiLegali.DataBind();
        }

        protected void ingBtnCancel_Click(object sender, ImageClickEventArgs e)
        {
            this.gvListaInteressiLegali.DataSource = null;
            this.gvListaInteressiLegali.DataBind();
        }
    }
}
