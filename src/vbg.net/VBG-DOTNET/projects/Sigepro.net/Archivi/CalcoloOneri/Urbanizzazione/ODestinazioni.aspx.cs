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
using Init.SIGePro.Manager;
using Init.SIGePro.Data;
using SIGePro.Net;
using SIGePro.Net.Navigation;
using Init.SIGePro.Exceptions;

public partial class Archivi_CalcoloOneri_Urbanizzazione_ODestinazioni : BasePage
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

    private void BindDettaglio(ODestinazioni od)
    {
        this.IsInserting = od.Id.GetValueOrDefault(int.MinValue) == int.MinValue;

        lblCodice.Text = IsInserting ? "Nuovo" : od.Id.ToString();
        
        if (!IsInserting && !String.IsNullOrEmpty(od.FkOccbdeId))
            ddlDestinazioneBase.SelectedValue = od.FkOccbdeId;

        txtDestinazione.Text = od.Destinazione;
        txtOrdinamento.ValoreInt = od.Ordinamento;

        if (!IsInserting && od.FkTumUmid.GetValueOrDefault(int.MinValue) != int.MinValue) 
            ddlUnitaMisura.SelectedValue = od.FkTumUmid.ToString();

        if (IsInserting)
        {
            ddlDestinazioneBase.SelectedIndex = 0;
            ddlUnitaMisura.SelectedIndex = 0;
        }

        cmdElimina.Visible = (!IsInserting);

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
        BindDettaglio(new ODestinazioni());
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

        ODestinazioni od = new ODestinazioniMgr(AuthenticationInfo.CreateDatabase()).GetById(AuthenticationInfo.IdComune, id);

        BindDettaglio(od);
    }

    protected void cmdChiudiLista_Click(object sender, EventArgs e)
    {
        multiView.ActiveViewIndex = 0;
    }

    protected void gvLista_RowDataBound(object sender, GridViewRowEventArgs e)
    {
        if (e.Row.RowType == DataControlRowType.DataRow)
        {
            Label lblDestinazioneBase = e.Row.FindControl("lblDestinazioneBase") as Label;
            OCCBaseDestinazioni occbd = new OCCBaseDestinazioniMgr(Database).GetById((e.Row.DataItem as ODestinazioni).FkOccbdeId);
            lblDestinazioneBase.Text = occbd.Destinazione;

            Label lblUnitaMisura = e.Row.FindControl("lblUnitaMisura") as Label;
            TipiUnitaMisura tum = new TipiUnitaMisuraMgr(AuthenticationInfo.CreateDatabase()).GetById((e.Row.DataItem as ODestinazioni).FkTumUmid.GetValueOrDefault(int.MinValue), AuthenticationInfo.IdComune);
            lblUnitaMisura.Text = tum.UmDescrbreve;
        }
    }

    #endregion

    #region Scheda

    protected void cmdSalva_Click(object sender, EventArgs e)
    {
        ODestinazioni od = new ODestinazioni();

        try
        {
            od.Idcomune = AuthenticationInfo.IdComune;
            od.Software = Software;
            od.Id = IsInserting ? (int?)null : Convert.ToInt32(lblCodice.Text);

            od.FkOccbdeId = ddlDestinazioneBase.SelectedValue;
            od.FkTumUmid = Convert.ToInt32( ddlUnitaMisura.SelectedValue );

            od.Destinazione = txtDestinazione.Text;
			od.Ordinamento = txtOrdinamento.ValoreInt;

            ODestinazioniMgr mgr = new ODestinazioniMgr(AuthenticationInfo.CreateDatabase());

            if (IsInserting)
                od = mgr.Insert(od);
            else
                od = mgr.Update(od);

            BindDettaglio(od);
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
            ODestinazioni od = new ODestinazioni();

            od.Idcomune = AuthenticationInfo.IdComune;
            od.Software = Software;
            od.Id = IsInserting ? (int?)null : Convert.ToInt32(lblCodice.Text);

            ODestinazioniMgr mgr = new ODestinazioniMgr(AuthenticationInfo.CreateDatabase());

            mgr.Delete(od);

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
        #region Destinazione base

        OCCBaseDestinazioni occbd = new OCCBaseDestinazioni();

        ddlSrcDestinazioneBase.DataSource = new OCCBaseDestinazioniMgr(AuthenticationInfo.CreateDatabase()).GetList(occbd);
        ddlSrcDestinazioneBase.DataBind();

        ddlSrcDestinazioneBase.Items.Insert(0, String.Empty);

        ddlDestinazioneBase.DataSource = new OCCBaseDestinazioniMgr(AuthenticationInfo.CreateDatabase()).GetList(occbd);
        ddlDestinazioneBase.DataBind();

        ddlDestinazioneBase.Items.Insert(0, String.Empty);

        #endregion

        #region Unità di misura

        //non devono comparire tutte le unità di misura ma solamente quelle specificate in configurazione degli
        //oneri di urbanizzazione
        OConfigurazione oc = new OConfigurazioneMgr(AuthenticationInfo.CreateDatabase()).GetById(AuthenticationInfo.IdComune, Software);

		if (oc == null)
			throw new Exception("Configurare le unità di misura nella pagina di configurazione degli oneri di urbanizzazione.");

        TipiUnitaMisura tum = new TipiUnitaMisura();
        tum.Idcomune = AuthenticationInfo.IdComune;
        tum.OthersWhereClause.Add("UM_ID IN ('" + oc.FkTumUmidMq + "','" + oc.FkTumUmidMc + "')");
        tum.OrderBy = "UM_DESCRBREVE";

        ddlUnitaMisura.DataSource = new TipiUnitaMisuraMgr( AuthenticationInfo.CreateDatabase()).GetList(tum);
        ddlUnitaMisura.DataBind();

        ddlUnitaMisura.Items.Insert(0, String.Empty);

        #endregion
    }

    #endregion

}
