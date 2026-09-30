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
using SIGePro.Net.Navigation;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using System.Collections.Generic;
using Init.SIGePro.Authentication;
using SIGePro.Net;

public partial class Archivi_CalcoloOneri_CostoCostruzione_CCCondizioniAttivita : BasePage
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!Page.IsPostBack)
            BindCombo();
    }

    public override void DataBind()
    {
        gvLista.DataBind();
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

    #region Cerca

    protected void cmdCerca_Click(object sender, EventArgs e)
    {
        if (String.IsNullOrEmpty(ddlSrcSettori.SelectedValue))
        {
            MostraErrore("Attenzione, selezionare un settore altrimenti non sarà possibile eseguire la ricerca\n", null);
        }
        else
        {
            lblSettore.Text = ddlSrcSettori.Items[ddlSrcSettori.SelectedIndex].Text;
            multiView.ActiveViewIndex = 1;
            DataBind();
        }
    }

    protected void cmdChiudi_Click(object sender, EventArgs e)
    {
		base.CloseCurrentPage(); 
    }

    #endregion

    #region Lista

    protected void cmdChiudiLista_Click(object sender, EventArgs e)
    {
        multiView.ActiveViewIndex = 0;
    }

    protected void gvLista_RowDataBound(object sender, GridViewRowEventArgs e)
    {
        if (e.Row.RowType == DataControlRowType.DataRow)
        {
            Label lblCondizione = e.Row.FindControl("lblCondizione") as Label;
            CCCondizioniAttivita ccca = new CCCondizioniAttivitaMgr(Database).GetByCodiceIstat(AuthenticationInfo.IdComune,(e.Row.DataItem as Attivita).CodiceIstat);
            
            ImageButton imgModifica = e.Row.FindControl("imgModifica") as ImageButton;
            ImageButton imgElimina = e.Row.FindControl("imgElimina") as ImageButton;
            ImageButton imgUtilizza = e.Row.FindControl("imgUtilizza") as ImageButton;

            if (imgElimina!=null)
                ImpostaScriptEliminazione(imgElimina);

            if (lblCondizione != null) lblCondizione.Text = ccca == null ? String.Empty : ccca.Condizionewhere;
            if (imgUtilizza != null) imgUtilizza.Visible = (ccca == null);
            if( imgModifica != null ) imgModifica.Visible = imgElimina.Visible = (ccca != null);

            TextBox txtCondizione = e.Row.FindControl("txtCondizione") as TextBox;
            if (txtCondizione != null) txtCondizione.Text = ccca == null ? String.Empty : ccca.Condizionewhere;
            
            HtmlInputHidden lblCodiceIstat = e.Row.FindControl("lblCodiceIstat") as HtmlInputHidden;
            if (lblCodiceIstat != null) lblCodiceIstat.Value = (e.Row.DataItem as Attivita).CodiceIstat;

            HtmlInputHidden lblId = e.Row.FindControl("lblId") as HtmlInputHidden;
            if (lblId != null) lblId.Value = ccca == null ? String.Empty : ccca.Id.ToString();
        }
    }

    #endregion

    #region DropDownlist

    private void BindCombo()
    {
        #region Settori
        Settori settore = new Settori();
        settore.IDCOMUNE = AuthenticationInfo.IdComune;
        settore.SOFTWARE = Software;
        settore.OthersTables.Add("CC_CONFIGURAZIONE_SETTORI");
        settore.OthersWhereClause.Add("CC_CONFIGURAZIONE_SETTORI.IDCOMUNE = SETTORI.IDCOMUNE");
        settore.OthersWhereClause.Add("CC_CONFIGURAZIONE_SETTORI.FK_SE_CODICESETTORE = SETTORI.CODICESETTORE");

        ddlSrcSettori.DataSource = new SettoriMgr(AuthenticationInfo.CreateDatabase()).GetList(settore);
        ddlSrcSettori.DataBind();
        #endregion
    }

    #endregion

    #region Gestione eventi lista

    protected void gvLista_RowUpdating(object sender, GridViewUpdateEventArgs e)
    {
        e.Cancel = true;
        HtmlInputHidden lblCodiceIstat = gvLista.Rows[e.RowIndex].FindControl("lblCodiceIstat") as HtmlInputHidden;
        TextBox txtCondizione = gvLista.Rows[e.RowIndex].FindControl("txtCondizione") as TextBox;

        CCCondizioniAttivitaMgr mgr = new CCCondizioniAttivitaMgr(AuthenticationInfo.CreateDatabase());

        CCCondizioniAttivita ca = mgr.GetByCodiceIstat(AuthenticationInfo.IdComune, lblCodiceIstat.Value);

        IsInserting = (ca == null);

        if (IsInserting)
            ca = new CCCondizioniAttivita();

        ca.Idcomune = AuthenticationInfo.IdComune;
        ca.FkAtCodiceistat = lblCodiceIstat.Value;
        ca.Condizionewhere = txtCondizione.Text;
        ca.Software = Software;

        if(IsInserting )
            mgr.Insert( ca );
        else
            mgr.Update(ca);

        gvLista.EditIndex = -1;
        DataBind();

    }

    protected void gvLista_RowDeleting(object sender, GridViewDeleteEventArgs e)
    {
        e.Cancel = true;
        CCCondizioniAttivita ccca = new CCCondizioniAttivita();
        CCCondizioniAttivitaMgr mgr = new CCCondizioniAttivitaMgr( AuthenticationInfo.CreateDatabase( ) );

        HtmlInputHidden lblId = gvLista.Rows[e.RowIndex].FindControl("lblId") as HtmlInputHidden;

        try
        {
            ccca.Id = Convert.ToInt32( lblId.Value );
            ccca.Idcomune = AuthenticationInfo.IdComune;

            mgr.Delete(ccca);
        }
        catch (Exception ex)
        {
            MostraErrore(AmbitoErroreEnum.Cancellazione, ex);
        }
        finally
        {
            DataBind();
        }
    }

    #endregion

}
