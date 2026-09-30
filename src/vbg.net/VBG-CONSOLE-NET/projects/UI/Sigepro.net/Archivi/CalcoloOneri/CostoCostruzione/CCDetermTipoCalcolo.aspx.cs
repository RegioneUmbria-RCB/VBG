using System;
using System.Data;
using System.Configuration;
using System.Collections;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;
using SIGePro.Net;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using SIGePro.Net.Navigation;
using Init.SIGePro.Exceptions;

public partial class Archivi_CalcoloOneri_CostoCostruzione_CCDetermTipoCalcolo : BasePage
{
    protected void Page_Load(object sender, EventArgs e)
    {
        ImpostaScriptEliminazione(cmdElimina);

        if (!Page.IsPostBack)
            BindCombo();
    }

    protected void multiView_ActiveViewChanged(object sender, EventArgs e)
    {
        switch (multiView.ActiveViewIndex)
        {
            case (1):
                Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Risultato;
                return;
            case (2):
                Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;
                return;
            default:
                Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Ricerca;
                return;
        }
    }

    private void BindDettaglio(CCDetermTipoCalcolo ccdtc)
    {
        this.IsInserting = ccdtc.Id.GetValueOrDefault(int.MinValue) == int.MinValue;

        lblCodice.Text = IsInserting ? "Nuovo" : ccdtc.Id.ToString();

        if (!IsInserting)
            ddlCalcoloBase.SelectedValue = ccdtc.FkCcbtcId;
        else
            ddlCalcoloBase.SelectedIndex = 0;

        if (!IsInserting)
            ddlDestinazioneBase.SelectedValue = ccdtc.FkOccbdeId;
        else
            ddlDestinazioneBase.SelectedIndex = 0;

        if (!IsInserting)
            ddlInterventoBase.SelectedValue = ccdtc.FkOccbtiId;
        else
            ddlInterventoBase.SelectedIndex = 0;

        txtPriorita.ValoreInt = ccdtc.Priorita;

        cmdElimina.Visible = !IsInserting;
        multiView.ActiveViewIndex = 2;
    }

    #region Cerca

    protected void cmdCerca_Click(object sender, EventArgs e)
    {
        gvLista.DataBind();

        multiView.ActiveViewIndex = 1;
    }

    protected void cmdNuovo_Click(object sender, EventArgs e)
    {
        BindDettaglio(new CCDetermTipoCalcolo());
    }

    protected void cmdChiudi_Click(object sender, EventArgs e)
    {
		base.CloseCurrentPage(); 
    }

    #endregion

    #region Lista

    protected void gvLista_SelectedIndexChanged(object sender, EventArgs e)
    {
        int id = Convert.ToInt32(gvLista.DataKeys[gvLista.SelectedIndex].Value);

        CCDetermTipoCalcolo ccdtc = new CCDetermTipoCalcoloMgr(AuthenticationInfo.CreateDatabase()).GetById(AuthenticationInfo.IdComune, id);

        BindDettaglio(ccdtc);
    }

    protected void cmdChiudiLista_Click(object sender, EventArgs e)
    {
        multiView.ActiveViewIndex = 0;
    }


    #endregion

    #region Scheda

    protected void cmdSalva_Click(object sender, EventArgs e)
    {
        CCDetermTipoCalcolo ccdtc = new CCDetermTipoCalcolo();

        try
        {
            ccdtc.Idcomune = AuthenticationInfo.IdComune;
            ccdtc.Software = Software;
            ccdtc.Id = null;
            if (!IsInserting)
                ccdtc.Id =Convert.ToInt32(lblCodice.Text);

            ccdtc.FkCcbtcId = ddlCalcoloBase.Text;
            ccdtc.FkOccbdeId = ddlDestinazioneBase.Text;
            ccdtc.FkOccbtiId = ddlInterventoBase.Text;

			ccdtc.Priorita = txtPriorita.ValoreInt;

            CCDetermTipoCalcoloMgr mgr = new CCDetermTipoCalcoloMgr(AuthenticationInfo.CreateDatabase());

            if (IsInserting)
                ccdtc = mgr.Insert(ccdtc);
            else
                ccdtc = mgr.Update(ccdtc);

            BindDettaglio(ccdtc);

            gvLista.DataBind();
        }
        catch (RequiredFieldException rfe)
        {
            MostraErrore("Attenzione, i campi contrassegnati con un asterisco sono obbligatori.", rfe);
        }
        catch (Exception ex)
        {
            MostraErrore("Errore durante il salvataggio: " + ex.Message, ex);
        }
    }

    protected void cmdChiudiDettaglio_Click(object sender, EventArgs e)
    {
        multiView.ActiveViewIndex = 0;
    }

    protected void cmdElimina_Click(object sender, EventArgs e)
    {
        try
        {
            CCDetermTipoCalcolo ccdtc = new CCDetermTipoCalcolo();

            ccdtc.Idcomune = AuthenticationInfo.IdComune;
            ccdtc.Software = Software;
            ccdtc.Id = null;
            if (!IsInserting)
                ccdtc.Id = Convert.ToInt32(lblCodice.Text);

            CCDetermTipoCalcoloMgr mgr = new CCDetermTipoCalcoloMgr(AuthenticationInfo.CreateDatabase());

            mgr.Delete(ccdtc);

            gvLista.DataBind();

            multiView.ActiveViewIndex = 0;
        }
        catch (Exception ex)
        {
            MostraErrore("Errore durante l'eliminazione: " + ex.Message, ex);
        }
    }
    #endregion

    #region DropDownlist

    private void BindCombo()
    { 
        #region Intervento base

        OCCBaseTipoIntervento occbti = new OCCBaseTipoIntervento();
        
        ddlSrcInterventoBase.DataSource = new OCCBaseTipoInterventoMgr(AuthenticationInfo.CreateDatabase()).GetList(occbti);
        ddlSrcInterventoBase.DataBind();

        ddlSrcInterventoBase.Items.Insert(0, String.Empty);

        ddlInterventoBase.DataSource = new OCCBaseTipoInterventoMgr(AuthenticationInfo.CreateDatabase()).GetList(occbti);
        ddlInterventoBase.DataBind();

        ddlInterventoBase.Items.Insert(0, String.Empty);

        #endregion

        #region Destinazione base

        OCCBaseDestinazioni occbd = new OCCBaseDestinazioni();

        ddlSrcDestinazioneBase.DataSource = new OCCBaseDestinazioniMgr(AuthenticationInfo.CreateDatabase()).GetList(occbd);
        ddlSrcDestinazioneBase.DataBind();

        ddlSrcDestinazioneBase.Items.Insert(0, String.Empty);

        ddlDestinazioneBase.DataSource = new OCCBaseDestinazioniMgr(AuthenticationInfo.CreateDatabase()).GetList(occbd);
        ddlDestinazioneBase.DataBind();

        ddlDestinazioneBase.Items.Insert(0, String.Empty);

        #endregion

        #region Calcolo base

        CCBaseTipoCalcolo ccbtc = new CCBaseTipoCalcolo();

        ddlSrcCalcoloBase.DataSource = new CCBaseTipoCalcoloMgr(AuthenticationInfo.CreateDatabase()).GetList(ccbtc);
        ddlSrcCalcoloBase.DataBind();

        ddlSrcCalcoloBase.Items.Insert(0, String.Empty);

        ddlCalcoloBase.DataSource = new CCBaseTipoCalcoloMgr(AuthenticationInfo.CreateDatabase()).GetList(ccbtc);
        ddlCalcoloBase.DataBind();

        ddlCalcoloBase.Items.Insert(0, String.Empty);

        #endregion
    }

    #endregion

}
