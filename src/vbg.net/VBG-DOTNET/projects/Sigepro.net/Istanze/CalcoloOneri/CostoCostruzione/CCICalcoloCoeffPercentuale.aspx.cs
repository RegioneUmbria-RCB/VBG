using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using Init.Utils;
using Init.Utils.Web.UI;
using log4net;
using SIGePro.Net;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Web.UI.WebControls;

public partial class Istanze_CalcoloOneri_CostoCostruzione_CCICalcoloCoeffPercentuale : BasePage
{


    private class ViewManager
    {
        private static class Constants
        {
            public const int IdViewCoefficientiStandard = 0;
            public const int IdViewCoefficientiClassiSiperficie = 1;
        }

        private readonly MultiView _multiView;

        public ViewManager(MultiView multiView)
        {
            this._multiView = multiView;
        }

        public void MostraViewCoefficientiStandard()
        {
            this._multiView.ActiveViewIndex = Constants.IdViewCoefficientiStandard;
        }

        public void MostraViewCoefficientiClassiSiperficie()
        {
            this._multiView.ActiveViewIndex = Constants.IdViewCoefficientiClassiSiperficie;
        }
    }

    private readonly ILog _log = LogManager.GetLogger(typeof(Istanze_CalcoloOneri_CostoCostruzione_CCICalcoloCoeffPercentuale));
    private ViewManager _viewManager;
    private CCICalcoloTContributo m_tcontributo;
    private CCICalcoloTot m_calcoloTot;
    private Istanze m_istanza;

    private readonly string errMsgCampoNonTrovato = "Nell'attuale configurazione del listino coefficienti non è stato trovato il valore ({0}-{1}).<br>Il tasto \"Salva\" riporta un coefficiente uguale a zero.<br>Il tasto \"Chiudi\" non modifica il precedente calcolo.";

    private readonly List<string> m_erroriCondizioni = new List<string>();

    private CCICalcoloTContributo TContributo
    {
        get
        {
            if (this.m_tcontributo == null)
            {
                if (this.Request.QueryString["TipoDettaglio"] == "P")
                {
                    this.m_tcontributo = new CCICalcoloTotMgr(this.Database).GetStatoDiProgetto(this.CalcoloTot);
                }
                else
                {
                    this.m_tcontributo = new CCICalcoloTotMgr(this.Database).GetStatoAttuale(this.CalcoloTot);
                }
            }

            return this.m_tcontributo;
        }
    }

    private CCICalcoloTot CalcoloTot
    {
        get
        {
            if (this.m_calcoloTot == null)
            {
                var id = Convert.ToInt32(this.Request.QueryString["IdCalcoloTot"]);
                this.m_calcoloTot = new CCICalcoloTotMgr(this.Database).GetById(this.AuthenticationInfo.IdComune, id);
            }

            return this.m_calcoloTot;
        }
    }

    private Istanze Istanza
    {
        get
        {
            if (this.m_istanza == null)
                this.m_istanza = new IstanzeMgr(this.Database).GetById(this.CalcoloTot.Idcomune, this.CalcoloTot.Codiceistanza.Value);

            return this.m_istanza;
        }
    }

    public override string Software
    {
        get
        {
            return this.Istanza.SOFTWARE;
        }
    }

    public bool BloccoCoefficienteVisibile
    {
        get { return this.pnlTipoIntervento.Visible || this.pnlUbicazione.Visible; }
    }

    public string TitoloBloccoCoefficiente
    {
        get
        {
            var ttl = String.Empty;

            if (this.pnlTipoIntervento.Visible)
                ttl += " tipo intervento";

            if (this.pnlUbicazione.Visible)
            {
                if (ttl.Length > 0)
                    ttl += " e";

                ttl += " ubicazione";
            }


            return "Coefficiente calcolato in base a:" + ttl;

        }
    }


    public Istanze_CalcoloOneri_CostoCostruzione_CCICalcoloCoeffPercentuale()
    {
    }

    protected void Page_Load(object sender, EventArgs e)
    {
        this._viewManager = new ViewManager(this.multiView);

        if (!this.IsPostBack)
        {
            this._viewManager.MostraViewCoefficientiStandard();
            this.DataBindCoefficientiStandard();
        }

        this.Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;
    }

    private void BindComboAttivitaIntervento()
    {
        // ddl Attività è visibile e bindato solamente se CC_FK_SE_CODICESETTORE è valorizzato
        this.pnlAttivita.Visible = false;

        var tipoIntervento = String.IsNullOrEmpty(this.ddlTipoIntervento.SelectedValue) ? int.MinValue : Convert.ToInt32(this.ddlTipoIntervento.SelectedValue);
        var areeCodiceArea = int.MinValue;

        if (this.pnlUbicazione.Visible && !String.IsNullOrEmpty(this.ddlUbicazione.SelectedValue))
            areeCodiceArea = Convert.ToInt32(this.ddlUbicazione.SelectedValue);

        var att = new CCCoeffContributoMgr(this.Database).GetAttivitaSelezionabili(this.IdComune, this.Software, tipoIntervento, areeCodiceArea);

        if (att.Count > 0)
        {
            this.pnlAttivita.Visible = true;

            var cfg = new CCConfigurazioneMgr(this.Database).GetById(this.IdComune, this.Software);

            this.lblAttivita.Text = new SettoriMgr(this.Database).GetById(cfg.FkSeCodicesettore, this.IdComune).ToString();

            this.ddlAttivita.DataSource = att;
            this.ddlAttivita.DataBind();

            this.ddlAttivita.Items.Insert(0, "");

            var condAttMgr = new CCCondizioniAttivitaMgr(this.Database);

            for (var i = 0; i < att.Count; i++)
            {
                if (condAttMgr.VerificaCondizioneAttivita(this.IdComune, this.TContributo.FkCcicId.GetValueOrDefault(int.MinValue), att[i].FkAtCodiceistat))
                {
                    this.ddlAttivita.SelectedValue = att[i].Id.ToString();
                    break;
                }
            }
        }
    }


    public void DataBindCoefficientiStandard()
    {
        var aliquotePerClassi = new CCICalcoloTotMgr(this.Database).GetAliquotePerClassiSuperficie(this.IdComune, this.CalcoloTot.Id.Value, this.TContributo.Stato == "P" ? CCICalcoloTotMgr.StatoCalcoloEnum.Progetto : CCICalcoloTotMgr.StatoCalcoloEnum.Attuale);

        if (aliquotePerClassi.Aliquote.Count() == 0)
            this.cmdAliquotaClassiSuperfici.Visible = false;

        this.ddlDestinazione.DataBind();
        this.ddlTipoIntervento.DataBind();
        this.ddlUbicazione.DataBind();

        this.BindComboAttivitaIntervento();

        this.rptCoefficienti.DataBind();

        this.RicalcolaCoefficienteUbicazione();
        this.RicalcolaCoefficienteTotale(null, EventArgs.Empty);

        var mgrDContributo = new CCICalcoloDContributoMgr(this.Database);
        var dContributo = mgrDContributo.GetByIdTContributo(this.TContributo.Idcomune, this.TContributo.Id.GetValueOrDefault(int.MinValue));

        if (this.TContributo.FkCcdeId.GetValueOrDefault(int.MinValue) != int.MinValue)
        {
            this.ddlDestinazione.SelectedValue = this.TContributo.FkCcdeId.ToString();
            this.ddlDestinazione_SelectedIndexChanged(this.ddlDestinazione, EventArgs.Empty);
        }

        if (!DoubleChecker.IsEmpty(this.TContributo.Coefficiente))
        {
            this.txtTotaleCoefficiente.ValoreDecimal = this.TContributo.Coefficiente.GetValueOrDefault(0.0m);
        }

        if (dContributo != null)
        {
            if (dContributo.FkCctiId.GetValueOrDefault(int.MinValue) != int.MinValue)
            {
                this.ddlTipoIntervento.SelectedValue = dContributo.FkCctiId.ToString();
                this.ddlTipoIntervento_SelectedIndexChanged(this.ddlTipoIntervento, EventArgs.Empty);
            }

            // Bindare la combo delle attivita

            if (dContributo.FkAreeCodicearea.GetValueOrDefault(int.MinValue) != int.MinValue)
            {
                this.ddlUbicazione.SelectedValue = dContributo.FkAreeCodicearea.ToString();
                this.ddlUbicazione_SelectedIndexChanged(this.ddlUbicazione, EventArgs.Empty);
            }

            if (dContributo.FkCccaId.GetValueOrDefault(int.MinValue) != int.MinValue && this.pnlAttivita.Visible)
            {
                if (dContributo.FkCccaId == -1)
                    this.ddlAttivita.SelectedValue = "";
                else
                    this.ddlAttivita.SelectedValue = dContributo.FkCccaId.ToString();

                this.ddlAttivita_SelectedIndexChanged(this.ddlAttivita, EventArgs.Empty);
            }

            this.txtCoefficiente.ValoreDecimal = dContributo.Coefficiente.GetValueOrDefault(0.0m);
        }


    }

    protected void cmdSalva_Click(object sender, EventArgs e)
    {
        // Aggiornamento di CC_ICALCOLO_TCONTRIBUTO
        this.TContributo.FkCcdeId = this.pnlDestinazioni.Visible ? Convert.ToInt32(this.ddlDestinazione.SelectedValue) : (int?)null;
        this.TContributo.Coefficiente = this.txtTotaleCoefficiente.ValoreDecimal;

        new CCICalcoloTContributoMgr(this.Database).Update(this.TContributo);

        // Aggiornamento/Inserimento di CC_ICALCOLO_DCONTRIBUTO
        var mgrDContributo = new CCICalcoloDContributoMgr(this.Database);
        var dContributo = mgrDContributo.GetByIdTContributo(this.TContributo.Idcomune, this.TContributo.Id.GetValueOrDefault(int.MinValue));

        var isInsertingDContributo = dContributo == null;

        if (isInsertingDContributo)
            dContributo = new CCICalcoloDContributo();

        dContributo.Idcomune = this.TContributo.Idcomune;
        dContributo.FkCcictcId = this.TContributo.Id;
        dContributo.Codiceistanza = this.TContributo.Codiceistanza;

        dContributo.FkCctiId = this.pnlTipoIntervento.Visible ? Convert.ToInt32(this.ddlTipoIntervento.SelectedValue) : (int?)null;
        dContributo.FkAreeCodicearea = this.pnlUbicazione.Visible ? Convert.ToInt32(this.ddlUbicazione.SelectedValue) : (int?)null;
        dContributo.Coefficiente = this.txtCoefficiente.ValoreDecimal;

        dContributo.FkCccaId = null;
        if (this.pnlAttivita.Visible)
            dContributo.FkCccaId = String.IsNullOrEmpty(this.ddlAttivita.SelectedValue) ? -1 : Convert.ToInt32(this.ddlAttivita.SelectedValue);

        if (isInsertingDContributo)
            mgrDContributo.Insert(dContributo);
        else
            mgrDContributo.Update(dContributo);

        // Aggiornamento/Inserimento delle righe di CC_ICALCOLO_DCONTRIBATTIV
        var mgrContribAttiv = new CCICalcoloDContribAttivMgr(this.Database);

        mgrContribAttiv.DeleteByIdTContributo(this.TContributo.Idcomune, this.TContributo.Id.GetValueOrDefault(int.MinValue));

        foreach (RepeaterItem ri in this.rptCoefficienti.Items)
        {
            if (!ri.Visible) continue;


            var ddlAttivitaGrid = (DropDownList)ri.FindControl("ddlAttivita");
            var txtCoefficienteAttivita = (DecimalTextBox)ri.FindControl("txtCoefficienteAttivita");

            var mgrCondAtt = new CCCondizioniAttivitaMgr(this.Database);

            var ccca = mgrCondAtt.GetByCodiceIstat(this.AuthenticationInfo.IdComune, ddlAttivitaGrid.SelectedValue);

            var dca = new CCICalcoloDContribAttiv();
            dca.Idcomune = this.TContributo.Idcomune;
            dca.Codiceistanza = this.TContributo.Codiceistanza;
            dca.FkCcictcId = this.TContributo.Id;
            dca.FkCcccaId = ccca.Id;
            dca.Coefficiente = txtCoefficienteAttivita.ValoreDecimal;

            mgrContribAttiv.Insert(dca);
        }

    }

    protected void cmdChiudi_Click(object sender, EventArgs e)
    {
        var url = "~/Istanze/CalcoloOneri/CostoCostruzione/CCICalcoliTot.aspx?Token={0}&CodiceIstanza={1}&IdCalcoloTot={2}";

        this.Response.Redirect(String.Format(url, this.AuthenticationInfo.Token, this.CalcoloTot.Codiceistanza, this.CalcoloTot.Id));
    }



    protected void ddlDestinazione_SelectedIndexChanged(object sender, EventArgs e)
    {
        this.ddlTipoIntervento.DataBind();
        this.RicalcolaCoefficienteUbicazione();
    }

    protected void ddlUbicazione_SelectedIndexChanged(object sender, EventArgs e)
    {
        this.RicalcolaCoefficienteUbicazione();

        this.BindComboAttivitaIntervento();
    }

    private void RicalcolaCoefficienteUbicazione()
    {
        var idComune = this.AuthenticationInfo.IdComune;

        var idCalcolo = this.CalcoloTot.Id.GetValueOrDefault(int.MinValue);
        var idDest = this.pnlDestinazioni.Visible ? Convert.ToInt32(this.ddlDestinazione.SelectedValue) : int.MinValue;
        var idTipoInt = this.pnlTipoIntervento.Visible ? Convert.ToInt32(this.ddlTipoIntervento.SelectedValue) : int.MinValue;
        var idUbic = this.pnlUbicazione.Visible ? Convert.ToInt32(this.ddlUbicazione.SelectedValue) : int.MinValue;
        var idAttivita = this.pnlAttivita.Visible && !String.IsNullOrEmpty(this.ddlAttivita.SelectedValue) ? Convert.ToInt32(this.ddlAttivita.SelectedValue) : int.MinValue;


        this.txtCoefficiente.ValoreDecimal = new CCICalcoloTotMgr(this.Database).GetCoefficienteDContributo(idComune, idCalcolo, idDest, idTipoInt, idUbic, idAttivita);
    }


    protected void ddlTipoIntervento_SelectedIndexChanged(object sender, EventArgs e)
    {
        this.BindComboAttivitaIntervento();
        this.ddlUbicazione.DataBind();
        this.RicalcolaCoefficienteUbicazione();
    }

    protected void ddlUbicazione_DataBound(object sender, EventArgs e)
    {
        this.pnlUbicazione.Visible = this.ddlUbicazione.Items.Count > 0;
    }

    protected void ddlTipoIntervento_DataBound(object sender, EventArgs e)
    {
        this.pnlTipoIntervento.Visible = this.ddlTipoIntervento.Items.Count > 0;
    }

    protected void ddlDestinazione_DataBound(object sender, EventArgs e)
    {
        this.pnlDestinazioni.Visible = this.ddlDestinazione.Items.Count > 0;
    }

    protected void ConfigSettoriDataSource_Selecting(object sender, ObjectDataSourceSelectingEventArgs e)
    {
        e.InputParameters[1] = this.Istanza.SOFTWARE;
    }

    protected void rptCoefficienti_ItemDataBound(object sender, RepeaterItemEventArgs e)
    {
        if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
        {
            var ddlAttivita = (DropDownList)e.Item.FindControl("ddlAttivita");
            var txtCoefficienteAttivita = (DecimalTextBox)e.Item.FindControl("txtCoefficienteAttivita");
            var pnlErroreAttivita = (Panel)e.Item.FindControl("pnlErroreAttivita");

            var cs = (CCConfigurazioneSettori)e.Item.DataItem;

            var attivitaPerSettore = new CCConfigurazioneSettoriMgr(this.Database).GetAttivitaPerSettore(this.CalcoloTot.Idcomune, this.CalcoloTot.Id.GetValueOrDefault(int.MinValue), cs.FkSeCodicesettore);

            if (attivitaPerSettore.Rows.Count == 0)
            {
                e.Item.Visible = false;
                return;
            }

            ddlAttivita.DataSource = attivitaPerSettore;
            ddlAttivita.DataBind();

            var attivitaSelezionata = this.TrovaAttivitaSelezionata(cs.FkSeCodicesettore);

            if (attivitaSelezionata == null)
            {
                var idAttivitaPreselezionata = this.TrovaAttivitaPreselezionabile(attivitaPerSettore, this.m_erroriCondizioni);

                if (String.IsNullOrEmpty(idAttivitaPreselezionata))
                    ddlAttivita.SelectedIndex = 0;
                else
                    ddlAttivita.SelectedValue = idAttivitaPreselezionata;

                // Ricalcolo il coefficiente
                this.AttivitaSelectedIndexChanged(ddlAttivita, EventArgs.Empty);
            }
            else
            {
                var codiceIstatAttivita = new CCCondizioniAttivitaMgr(this.Database).GetById(this.IdComune, attivitaSelezionata.FkCcccaId.GetValueOrDefault(int.MinValue)).FkAtCodiceistat;

                var els = attivitaPerSettore.Select("id='" + codiceIstatAttivita + "'");

                pnlErroreAttivita.Visible = false;

                if (els == null || els.Length == 0)
                {
                    var att = new AttivitaMgr(this.Database).GetById(codiceIstatAttivita, this.IdComune);
                    ddlAttivita.Items.Add(new ListItem(att.ISTAT, att.CodiceIstat));

                    pnlErroreAttivita.Visible = true;

                    var l = new Literal();
                    l.Text = String.Format(this.errMsgCampoNonTrovato, att.CodiceIstat, att.ISTAT);

                    pnlErroreAttivita.Controls.Add(l);

                    txtCoefficienteAttivita.ValoreDecimal = 0.0m;
                }
                else
                {
                    txtCoefficienteAttivita.ValoreDecimal = attivitaSelezionata.Coefficiente;
                }

                ddlAttivita.SelectedValue = codiceIstatAttivita;
            }

        }
    }

    private string TrovaAttivitaPreselezionabile(DataTable attivitaPerSettore, List<string> errori)
    {
        var mgrCondAtt = new CCCondizioniAttivitaMgr(this.Database);

        foreach (DataRow dr in attivitaPerSettore.Rows)
        {
            try
            {
                // Verifico nella tabella CC_CondizioniAttivita se esiste una condizione che mi permette di preselezionare un valore
                if (mgrCondAtt.VerificaCondizioneAttivita(this.AuthenticationInfo.IdComune, this.TContributo.FkCcicId.GetValueOrDefault(int.MinValue), dr["Id"].ToString()))
                    return dr["id"].ToString();
            }
            catch (Exception ex)
            {
                errori.Add("Errore nella condizione per il codice " + dr["id"].ToString() + ": " + ex.Message);
            }
        }

        return String.Empty;
    }

    private CCICalcoloDContribAttiv TrovaAttivitaSelezionata(string codiceSettore)
    {
        var mgrContrAttiv = new CCICalcoloDContribAttivMgr(this.Database);
        return mgrContrAttiv.GetByIdSettore(this.IdComune, this.TContributo.Id.GetValueOrDefault(int.MinValue), codiceSettore);
    }

    protected void AttivitaSelectedIndexChanged(object sender, EventArgs e)
    {
        var ddl = (DropDownList)sender;
        var txtCoefficienteAttivita = (DecimalTextBox)ddl.NamingContainer.FindControl("txtCoefficienteAttivita");

        var idComune = this.AuthenticationInfo.IdComune;

        var idCalcolo = this.CalcoloTot.Id.GetValueOrDefault(int.MinValue);
        var idAttivita = ddl.SelectedValue;

        txtCoefficienteAttivita.ValoreDecimal = new CCConfigurazioneSettoriMgr(this.Database).GetCoefficienteDContributoAttiv(idComune, idCalcolo, idAttivita);
    }


    public string GetNomeSettore(object idSettore)
    {
        var mgr = new SettoriMgr(this.Database);
        return mgr.GetById(idSettore.ToString(), this.AuthenticationInfo.IdComune).SETTORE;
    }

    protected override void OnPreRender(EventArgs e)
    {
        if (this.m_erroriCondizioni.Count > 0)
        {
            this.rptErrori.DataSource = this.m_erroriCondizioni;
            this.rptErrori.DataBind();
        }

        base.OnPreRender(e);
    }


    protected void RicalcolaCoefficienteTotale(object sender, EventArgs e)
    {
        var val = 0.0m;

        if (this.txtCoefficiente.ValoreDecimal.GetValueOrDefault(0.0m) > 0.0m)
            val += this.txtCoefficiente.ValoreDecimal.GetValueOrDefault(0.0m);

        foreach (RepeaterItem ri in this.rptCoefficienti.Items)
        {
            if (!ri.Visible) continue;

            var txtCoefficienteAttivita = (DecimalTextBox)ri.FindControl("txtCoefficienteAttivita");

            val += txtCoefficienteAttivita.ValoreDecimal.GetValueOrDefault(0.0m);
        }

        this.txtTotaleCoefficiente.ValoreDecimal = val;
    }

    protected void ddlAttivita_SelectedIndexChanged(object sender, EventArgs e)
    {
        //ddlTipoIntervento.DataBind();
        this.RicalcolaCoefficienteUbicazione();
    }


    #region gestione delle aliquote per classi di superfici

    protected void cmdAliquotaClassiSuperfici_Click(object sender, EventArgs e)
    {
        this._viewManager.MostraViewCoefficientiClassiSiperficie();

        this.BindAliquotePerClassiSuperfici();
    }

    protected void BindAliquotePerClassiSuperfici()
    {
        var aliquotePerClassi = new CCICalcoloTotMgr(this.Database).GetAliquotePerClassiSuperficie(this.IdComune, this.CalcoloTot.Id.Value, this.TContributo.Stato == "P" ? CCICalcoloTotMgr.StatoCalcoloEnum.Progetto : CCICalcoloTotMgr.StatoCalcoloEnum.Attuale);

        this.rptAliquotePerClassiSuperfici.DataSource = aliquotePerClassi.Aliquote;
        this.rptAliquotePerClassiSuperfici.DataBind();

        this.lblTotaleAliquota.Text = aliquotePerClassi.AliquotaTotale + " %";
        this.lblTotaleCostoCostruzione.Text = aliquotePerClassi.CostoEdificioTotale + " €";
        this.lblTotaleContributo.Text = aliquotePerClassi.ContributoTotale + " €";

    }

    protected void cmdSalvaAliquotePerClassi_Click(object sender, EventArgs e)
    {
        var aliquotePerClassi = new CCICalcoloTotMgr(this.Database).GetAliquotePerClassiSuperficie(this.IdComune, this.CalcoloTot.Id.Value, this.TContributo.Stato == "P" ? CCICalcoloTotMgr.StatoCalcoloEnum.Progetto : CCICalcoloTotMgr.StatoCalcoloEnum.Attuale);

        try
        {
            this.TContributo.FkCcdeId = (int?)null;
            this.TContributo.Coefficiente = Decimal.Parse(aliquotePerClassi.AliquotaTotale);

            new CCICalcoloTContributoMgr(this.Database).Update(this.TContributo);

            this.cmdChiudi_Click(sender, e);
        }
        catch (Exception ex)
        {
            this.Errori.Add("Errore durante il salvataggio dei dati delle aliquote per classi: " + ex.Message);
            this._log.ErrorFormat("Chiamata: {0}, querystring: {1} \r\n Errore: {1}", this.Request.Url, this.Request.QueryString, ex.ToString());
        }
    }

    #endregion
}
