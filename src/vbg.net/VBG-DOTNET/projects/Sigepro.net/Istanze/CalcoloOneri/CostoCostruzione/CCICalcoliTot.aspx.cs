using Init.SIGePro.Manager;
using Sigepro.net.Istanze.CalcoloOneri;
using System;
using System.Web.UI.WebControls;
using static Init.SIGePro.Manager.IstanzeMgr;

public partial class Istanze_CalcoloOneri_CostoCostruzione_CCICalcoliTot : PaginaTotaleOneriBase
{

    public override string Software => this.Istanza.Software;

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

    private DatiMinimiIstanza m_istanza = null;
    private DatiMinimiIstanza Istanza
    {
        get
        {
            if (this.m_istanza == null)
                this.m_istanza = new IstanzeMgr(this.AuthenticationInfo.CreateDatabase()).GetDatiMinimi(this.IdComune, this.CodiceIstanza);

            return this.m_istanza;
        }
    }

    private int? IdCalcoloTot
    {
        get
        {
            if (!String.IsNullOrEmpty(this.Request.QueryString["IdCalcoloTot"]))
                return Convert.ToInt32(this.Request.QueryString["IdCalcoloTot"]);
            return null;
        }
    }

    //#region visibilità delle righe della tabella del contributo
    //public bool MostraRigaContributoProgetto
    //{
    //    get { var o = this.ViewState["MostraRigaContributoProgetto"]; return o == null ? true : (bool)o; }
    //    set { this.ViewState["MostraRigaContributoProgetto"] = value; }
    //}

    //public bool MostraRigaContributoAttuale
    //{
    //    get { var o = this.ViewState["MostraRigaContributoAttuale"]; return o == null ? true : (bool)o; }
    //    set { this.ViewState["MostraRigaContributoAttuale"] = value; }
    //}
    //#endregion

    //public bool MostraTabellaContributo
    //{
    //    get { return this.MostraRigaContributoProgetto || this.MostraRigaContributoAttuale; }
    //}

    protected void Page_Load(object sender, EventArgs e)
    {
        this.Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Risultato;

        if (!this.IsPostBack)
        {
            if (this.IdCalcoloTot.HasValue)
            {
                var url = $"~/Istanze/CalcoloOneri/CostoCostruzione/CCICalcoliTotDettaglio.aspx?Token={this.AuthenticationInfo.Token}&CodiceIstanza={this.CodiceIstanza}&IdCalcoloTot={this.IdCalcoloTot.Value}";

                this.Response.Redirect(url);

                return;
            }

            this.BindListaCalcoli();
        }
    }

    private void BindListaCalcoli()
    {
        this.gvLista.DataSource = new CCICalcoloTotMgr(this.AuthenticationInfo.CreateDatabase()).GetList(this.AuthenticationInfo.IdComune, this.CodiceIstanza);
        this.gvLista.DataBind();
    }


    protected void cmdNuovo_Click(object sender, EventArgs e)
    {
        var url = $"~/Istanze/CalcoloOneri/CostoCostruzione/CCICalcoliTotInsert.aspx?Token={this.AuthenticationInfo.Token}&CodiceIstanza={this.CodiceIstanza}";

        this.Response.Redirect(url);
    }

    //private void BindDettaglio(CCICalcoloTot cls)
    //{
    //    this.IsInserting = !cls.Id.HasValue;
    //    this.cmdEliminaCalcolo.Visible = !this.IsInserting;
    //    if (this.IsInserting)
    //    {
    //        throw new Exception(this.IdComune + " - Inserimento non consentito in questa pagina");
    //    }
    //    else
    //    {
    //        this.BindAggiornamento(cls);
    //    }
    //}

    //private void BindAggiornamento(CCICalcoloTot cls)
    //{
    //    new CCICalcoloTotMgr(this.Database).IntegraForeignKey(cls);

    //    this.lblId.Text = cls.Id.ToString();
    //    this.lblData.Text = cls.Data.GetValueOrDefault(DateTime.MinValue).ToString("dd/MM/yyyy");
    //    this.lblListino.Text = new CCValiditaCoefficientiMgr(this.Database).GetById(this.AuthenticationInfo.IdComune, cls.FkCcvcId.Value).Descrizione;
    //    this.lblTipoCalcolo.Text = new CCBaseTipoCalcoloMgr(this.Database).GetById(cls.FkBcctcId).Tipocalcolo;
    //    this.lblTipoIntervento.Text = new OCCBaseTipoInterventoMgr(this.Database).GetById(cls.FkOccbtiId).Intervento;
    //    this.lblDestinazione.Text = new OCCBaseDestinazioniMgr(this.Database).GetById(cls.FkOccbdeId).Destinazione;

    //    this.txtEditDescrizione.Text = cls.Descrizione;

    //    this.MostraRigaContributoAttuale =
    //    this.MostraRigaContributoProgetto = false;

    //    var totaleContributo = 0.0d;
    //    var contrAttuale = 0.0d;
    //    var contrProgetto = 0.0d;

    //    if (cls.StatoAttuale != null)
    //    {
    //        this.MostraRigaContributoAttuale = true;
    //        this.txtCostoEdificioAttuale.ValoreDouble = cls.StatoAttuale.CostocEdificio.GetValueOrDefault(double.MinValue);
    //        this.txtCoefficenteAttuale.ValoreDouble = cls.StatoAttuale.Coefficiente.GetValueOrDefault(double.MinValue);

    //        if (!cls.StatoAttuale.Riduzioneperc.HasValue)
    //        {
    //            cls.StatoAttuale.Riduzioneperc = 0.0d;
    //            new CCICalcoloTContributoMgr(this.Database).Update(cls.StatoAttuale);
    //        }

    //        var quotaNoRiduz = cls.StatoAttuale.GetQuotaSenzaRiduzioni();
    //        var quotaConRiduz = cls.StatoAttuale.GetQuotaConRiduzioni();

    //        this.lblQuotaAttuale.Text = quotaNoRiduz.ToString("N2");
    //        this.txtQuotaContributoAttuale.ValoreDouble = quotaConRiduz;

    //        contrAttuale = quotaConRiduz;

    //        this.cmdDettagliCostoEdificioAttuale.Visible = cls.StatoAttuale.FkCcicId.HasValue;

    //        if (cls.StatoAttuale.FkCcicId.HasValue)
    //            this.cmdDettagliCostoEdificioAttuale.CommandArgument = cls.StatoAttuale.FkCcicId.ToString();

    //        this.cmdDettagliContributoAttuale.CommandArgument = cls.Id.ToString() + "$A";

    //        var haRiduzioni = new CcICalcoloTContributoRiduzMgr(this.Database).GetListByIdTContributo(this.IdComune, cls.StatoAttuale.Id.Value).Count > 0;

    //        this.txtVariazioneAttuale.ReadOnly = haRiduzioni;
    //        this.txtVariazioneAttuale.ValoreDouble = cls.StatoAttuale.Riduzioneperc.GetValueOrDefault(double.MinValue);

    //        this.hlpVariazioneAttuale.Text = this.GeneraTestoRiduzioni(cls.StatoAttuale);
    //    }

    //    if (cls.StatoDiProgetto != null)
    //    {
    //        this.MostraRigaContributoProgetto = true;
    //        this.txtCostoEdificioProgetto.ValoreDouble = cls.StatoDiProgetto.CostocEdificio.GetValueOrDefault(double.MinValue);
    //        this.txtCoefficenteProgetto.ValoreDouble = cls.StatoDiProgetto.Coefficiente.GetValueOrDefault(double.MinValue);

    //        if (!cls.StatoDiProgetto.Riduzioneperc.HasValue)
    //        {
    //            cls.StatoDiProgetto.Riduzioneperc = 0.0d;
    //            new CCICalcoloTContributoMgr(this.Database).Update(cls.StatoDiProgetto);
    //        }

    //        var quotaNoRiduz = cls.StatoDiProgetto.GetQuotaSenzaRiduzioni();
    //        var quotaConRiduz = cls.StatoDiProgetto.GetQuotaConRiduzioni();

    //        this.lblQuotaProgetto.Text = quotaNoRiduz.ToString("N2");
    //        this.txtQuotaContributoProgetto.ValoreDouble = quotaConRiduz;

    //        contrProgetto = quotaConRiduz;

    //        this.cmdDettagliCostoEdificioProgetto.Visible = cls.StatoDiProgetto.FkCcicId.HasValue;

    //        if (cls.StatoDiProgetto.FkCcicId.HasValue)
    //            this.cmdDettagliCostoEdificioProgetto.CommandArgument = cls.StatoDiProgetto.FkCcicId.ToString();

    //        this.cmdDettagliContributoProgetto.CommandArgument = cls.Id.ToString() + "$P";

    //        this.txtVariazioneProgetto.ValoreDouble = cls.StatoDiProgetto.Riduzioneperc.GetValueOrDefault(double.MinValue);

    //        var haRiduzioni = new CcICalcoloTContributoRiduzMgr(this.Database).GetListByIdTContributo(this.IdComune, cls.StatoDiProgetto.Id.Value).Count > 0;
    //        this.txtVariazioneProgetto.ReadOnly = haRiduzioni;

    //        // this.lnkEditVariazioneProgetto.OnClientClick = "return " + (haRiduzioni || cls.StatoDiProgetto.Riduzioneperc == 0.0d ? "ModificaRiduzioni" : "ModificaNote") + "('" + this.Token + "'," + cls.StatoDiProgetto.Id + ");";

    //        this.hlpVariazioneProgetto.Text = this.GeneraTestoRiduzioni(cls.StatoDiProgetto);
    //    }

    //    totaleContributo = contrProgetto - contrAttuale;

    //    this.txtQuotaContributoTotale.ValoreDouble = totaleContributo;

    //    this.multiView.ActiveViewIndex = 2;


    //}

    //private string GeneraTestoRiduzioni(CCICalcoloTContributo cCICalcoloTContributo)
    //{
    //    var riduzioni = new CcICalcoloTContributoRiduzMgr(this.Database).GetListByIdTContributo(this.IdComune, cCICalcoloTContributo.Id.Value);

    //    if (riduzioni.Count == 0) return cCICalcoloTContributo.Noteriduzione;   // TODO: Verificare che non sia stato immesso un valore manualmente in tal caso ritornare le note del calcolo

    //    var sb = new StringBuilder();

    //    sb.Append("<table width='100%'>");

    //    var totale = 0.0d;

    //    foreach (var r in riduzioni)
    //    {
    //        sb.Append("<tr style='color:#000'><td>");
    //        sb.Append(r.CausaleRiduzione.Descrizione);
    //        sb.Append("</td><td style='text-align:right;'>");
    //        sb.Append(r.Riduzioneperc.GetValueOrDefault(double.MinValue).ToString("N2") + "%");
    //        sb.Append("</td></tr>");

    //        if (!String.IsNullOrEmpty(r.Note))
    //        {
    //            sb.Append("<tr><td>&nbsp;&nbsp;<i>");
    //            sb.Append(r.Note);
    //            sb.Append("</i></td><td>&nbsp;</td></tr>");
    //        }

    //        totale += r.Riduzioneperc.GetValueOrDefault(0);

    //    }

    //    sb.Append("<tr style='color:#000'><td>&nbsp;</td><td style='text-align:right;'>-------------</td></tr>");

    //    sb.Append("<tr style='color:#000'><td>Totale</td><td style='text-align:right;'>");
    //    sb.Append(totale.ToString("N2"));
    //    sb.Append("%");
    //    sb.Append("</td></tr>");

    //    sb.Append("</table>");

    //    return sb.ToString();

    //}


    //protected void multiView_ActiveViewChanged(object sender, EventArgs e)
    //{
    //    switch (this.multiView.ActiveViewIndex)
    //    {
    //        case (0):
    //            this.Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Risultato;
    //            return;
    //        case (1):

    //            return;
    //        case (2):
    //            this.Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;
    //            return;
    //    }
    //}



    //protected void cmdAggiornaDescrizione_Click(object sender, EventArgs e)
    //{
    //    var mgr = new CCICalcoloTotMgr(this.Database);
    //    var cls = mgr.GetById(this.AuthenticationInfo.IdComune, Convert.ToInt32(this.lblId.Text));

    //    cls.Descrizione = this.txtEditDescrizione.Text;

    //    try
    //    {
    //        mgr.Update(cls);
    //    }
    //    catch (Exception ex)
    //    {
    //        this.MostraErrore("Errore durante l'aggiornamento: " + ex.Message, ex);
    //    }
    //}

    protected void gvLista_SelectedIndexChanged(object sender, EventArgs e)
    {
        var selId = Convert.ToInt32(this.gvLista.DataKeys[this.gvLista.SelectedIndex][0]);

        var url = $"~/Istanze/CalcoloOneri/CostoCostruzione/CCICalcoliTotDettaglio.aspx?Token={this.Token}&CodiceIstanza={this.CodiceIstanza}&IdCalcoloTot={selId}";

        this.Response.Redirect(url);
    }

    //protected void cmdSalvaContributo_Click(object sender, EventArgs e)
    //{
    //    var id = int.Parse(this.lblId.Text);
    //    var mgrTot = new CCICalcoloTotMgr(this.Database);
    //    var mgrICalc = new CCICalcoloTContributoMgr(this.Database);

    //    var cTot = mgrTot.GetById(this.AuthenticationInfo.IdComune, id);
    //    mgrTot.IntegraForeignKey(cTot);

    //    if (cTot.StatoDiProgetto != null)
    //    {
    //        cTot.StatoDiProgetto.Coefficiente = this.txtCoefficenteProgetto.ValoreDouble;
    //        cTot.StatoDiProgetto.CostocEdificio = this.txtCostoEdificioProgetto.ValoreDouble;
    //        cTot.StatoDiProgetto.Riduzioneperc = String.IsNullOrEmpty(this.txtVariazioneProgetto.Text) ? 0.0d : this.txtVariazioneProgetto.ValoreDouble;
    //        cTot.StatoDiProgetto = mgrICalc.Update(cTot.StatoDiProgetto);
    //    }

    //    if (cTot.StatoAttuale != null)
    //    {
    //        cTot.StatoAttuale.Coefficiente = this.txtCoefficenteAttuale.ValoreDouble;
    //        cTot.StatoAttuale.CostocEdificio = this.txtCostoEdificioAttuale.ValoreDouble;
    //        cTot.StatoAttuale.Riduzioneperc = String.IsNullOrEmpty(this.txtVariazioneAttuale.Text) ? 0.0d : this.txtVariazioneAttuale.ValoreDouble;
    //        cTot.StatoAttuale = mgrICalc.Update(cTot.StatoAttuale);
    //    }

    //    cTot = mgrTot.GetById(this.AuthenticationInfo.IdComune, id);

    //    this.BindDettaglio(cTot);

    //}

    //protected void cmdChiudiDettaglio_Click(object sender, EventArgs e)
    //{
    //    if (this.IdCalcoloTot.HasValue)
    //    {
    //        this.BindListaCalcoli();
    //    }

    //    this.multiView.ActiveViewIndex = 0;

    //}

    //protected void ApriDettagli(object sender, EventArgs e)
    //{
    //    var lbSender = (ImageButton)sender;

    //    var idCalcolo = int.Parse(lbSender.CommandArgument);

    //    var cfg = new CCConfigurazioneMgr(this.Database).GetById(this.IdComune, this.Istanza.Software);

    //    var fmtUrl = "";

    //    // Utilizza l'immissione dei dati nel modello?
    //    if (cfg.Usadettagliosup == CCConfigurazione.CALCSUP_MODELLO)
    //        fmtUrl = "~/Istanze/CalcoloOneri/CostoCostruzione/CCITabella1.aspx?Token={0}&IdCalcolo={1}";
    //    else
    //        fmtUrl = "~/Istanze/CalcoloOneri/CostoCostruzione/CCICalcoliDettaglio.aspx?Token={0}&IdCalcolo={1}";

    //    this.Response.Redirect(String.Format(fmtUrl, this.AuthenticationInfo.Token, idCalcolo), true);
    //}

    //protected void ApriDettagliContributo(object sender, EventArgs e)
    //{
    //    var lbSender = (ImageButton)sender;

    //    var parts = lbSender.CommandArgument.Split('$');

    //    var idCalcoloTot = int.Parse(parts[0]);
    //    var tipoDettaglio = parts[1];

    //    var fmtUrl = "~/Istanze/CalcoloOneri/CostoCostruzione/CCICalcoloCoeffPercentuale.aspx?Token={0}&IdCalcoloTot={1}&TipoDettaglio={2}";

    //    this.Response.Redirect(String.Format(fmtUrl, this.AuthenticationInfo.Token, idCalcoloTot, tipoDettaglio), true);


    //}

    //protected void cmdRiportaValore_Click(object sender, EventArgs e)
    //{
    //    var istanzeOneriMgr = new IstanzeOneriMgr(this.Database, this.AuthenticationInfo);
    //    var codiceCausale = new CCConfigurazioneMgr(this.Database).GetById(this.IdComune, this.Software).FkCoId;
    //    var oneriEsistenti = this.GetOneriFromIstanzaCausale(this.CodiceIstanza, codiceCausale.Value);
    //    var importoOnere = this.txtQuotaContributoTotale.ValoreDouble;

    //    //Verifico se è stato trovato un onere nell'istanza con la stessa causale
    //    if (oneriEsistenti.Count == 1)
    //    {
    //        //E' stato trovato un onere 
    //        var onere = oneriEsistenti[0];

    //        var importo = onere.PREZZO.GetValueOrDefault(0.0d) + importoOnere;
    //        var idComune = onere.IDCOMUNE;
    //        var idOnere = Convert.ToInt32(onere.ID);

    //        try
    //        {
    //            istanzeOneriMgr.UpdateImporto(idComune, idOnere, importo);
    //        }
    //        catch (Exception ex)
    //        {
    //            this.MostraErrore(AmbitoErroreEnum.Aggiornamento, ex);
    //        }
    //    }
    //    else
    //    {
    //        //Non è stato trovato nessun onere o più di uno
    //        try
    //        {
    //            var oneriService = new OneriService(this.AuthenticationInfo);

    //            oneriService.Inserisci(this.CodiceIstanza, codiceCausale.Value, importoOnere);
    //        }
    //        catch (Exception ex)
    //        {
    //            this.MostraErrore(AmbitoErroreEnum.Inserimento, ex);
    //        }
    //    }

    //    this.MostraConfermaCopiaOneri();
    //    //ImpostaScriptCopia(cmdCopiaOneri, CodiceIstanza, codiceCausale.Value);
    //}




    protected void cmdChiudi_Click(object sender, EventArgs e)
    {
        base.CloseCurrentPage();
    }
    protected void gvLista_RowDataBound(object sender, GridViewRowEventArgs e)
    {
        var cmdElimina = e.Row.FindControl("cmdElimina") as ImageButton;

        if (cmdElimina != null)
            this.ImpostaScriptEliminazione(cmdElimina);
    }
    protected void gvLista_RowDeleting(object sender, GridViewDeleteEventArgs e)
    {
        var selId = Convert.ToInt32(this.gvLista.DataKeys[e.RowIndex][0]);

        var mgr = new CCICalcoloTotMgr(this.Database);
        var cls = mgr.GetById(this.AuthenticationInfo.IdComune, selId);

        try
        {
            mgr.Delete(cls);

            this.BindListaCalcoli();
        }
        catch (Exception ex)
        {
            this.MostraErrore(ex);
        }
    }
    //protected void cmdEliminaCalcolo_Click(object sender, EventArgs e)
    //{
    //    var selId = Convert.ToInt32(this.lblId.Text);

    //    var mgr = new CCICalcoloTotMgr(this.Database);
    //    var cls = mgr.GetById(this.AuthenticationInfo.IdComune, selId);

    //    try
    //    {
    //        mgr.Delete(cls);
    //    }
    //    catch (Exception ex)
    //    {
    //        this.MostraErrore(ex);

    //        return;
    //    }

    //    this.BindListaCalcoli();

    //    this.multiView.ActiveViewIndex = 0;
    //}


    //protected void EditContributoProgetto(object sender, ImageClickEventArgs e)
    //{
    //    var calcoloTot = new CCICalcoloTotMgr(this.Database).GetById(this.AuthenticationInfo.IdComune, this.IdCalcoloTot.Value);
    //    new CCICalcoloTotMgr(this.Database).IntegraForeignKey(calcoloTot);

    //    this.ModificaRiduzioniCtrl.TContributoId = calcoloTot.StatoDiProgetto.Id.Value;
    //    this.ModificaRiduzioniCtrl.DataBind();
    //}

    //protected void EditContributoAttuale(object sender, ImageClickEventArgs e)
    //{
    //    var calcoloTot = new CCICalcoloTotMgr(this.Database).GetById(this.AuthenticationInfo.IdComune, this.IdCalcoloTot.Value);
    //    new CCICalcoloTotMgr(this.Database).IntegraForeignKey(calcoloTot);

    //    this.ModificaRiduzioniCtrl.TContributoId = calcoloTot.StatoAttuale.Id.Value;
    //    this.ModificaRiduzioniCtrl.DataBind();
    //}

    //protected void OnModificaRiduzioniRiuscita(object sender, EventArgs e)
    //{
    //    var calcoloTot = new CCICalcoloTotMgr(this.Database).GetById(this.AuthenticationInfo.IdComune, this.IdCalcoloTot.Value);
    //    this.BindDettaglio(calcoloTot);
    //}

    //protected void OnErroreSalvataggioRiduzioni(object sender, Exception e)
    //{
    //    this.MostraErrore(e);
    //}

    //protected override void OnPreRender(EventArgs e)
    //{
    //    var mgrConfigurazione = new CCConfigurazioneMgr(this.Database);
    //    var configurazione = mgrConfigurazione.GetById(this.AuthenticationInfo.IdComune, this.Istanza.Software);
    //    var idCausaleOnere = configurazione?.FkCoId;

    //    this.cmdCopiaOneri.Visible = idCausaleOnere.HasValue;

    //    if (idCausaleOnere.HasValue)
    //    {
    //        this.ImpostaScriptCopia(this.cmdCopiaOneri, this.CodiceIstanza, idCausaleOnere.Value);
    //    }

    //    base.OnPreRender(e);
    //}
}
