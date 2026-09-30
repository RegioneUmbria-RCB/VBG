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
using Init.SIGePro.Manager;
using Init.SIGePro.Data;
using SIGePro.Net.Navigation;
using SIGePro;
using Init.SIGePro.Exceptions;

public partial class Archivi_CalcoloOneri_CostoCostruzione_CCDettagliSuperficie : BasePage
{
    #region Proprietà

    private int? FkCcTsId
    {
        get { return string.IsNullOrEmpty(Request.QueryString["fkcctsid"]) ? (int?)null : Convert.ToInt32(Request.QueryString["fkcctsid"]); }
    }

    #endregion

    protected void Page_Load(object sender, EventArgs e)
    {
        ImpostaScriptEliminazione(cmdElimina);

        if (!Page.IsPostBack)
        {
            gvLista.DataBind();
            BindCombo();
            ImpostaDescrizionePadre();
        }
    }

    private void ImpostaDescrizionePadre()
    {
        if (FkCcTsId.GetValueOrDefault(int.MinValue) > int.MinValue)
        {
            lblDescrizione1.Text = new CCTipiSuperficieMgr(AuthenticationInfo.CreateDatabase()).GetById(AuthenticationInfo.IdComune, FkCcTsId.GetValueOrDefault(int.MinValue)).Descrizione;
        }
    }

    protected void multiView_ActiveViewChanged(object sender, EventArgs e)
    {
        switch (multiView.ActiveViewIndex)
        {
            case (1):
                Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;
                return;
            default:
                Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Risultato;
                return;
        }
    }

    private void BindDettaglio(CCDettagliSuperficie ccds)
    {
        this.IsInserting = ccds.Id.GetValueOrDefault(int.MinValue) == int.MinValue;

        lblCodice.Text = IsInserting ? "Nuovo" : ccds.Id.ToString();
        txtDescrizione.Text = ccds.Descrizione;

        cmdElimina.Visible = !IsInserting;

        multiView.ActiveViewIndex = 1;
    }

    #region Lista

    protected void gvLista_SelectedIndexChanged(object sender, EventArgs e)
    {
        int id = Convert.ToInt32(gvLista.DataKeys[gvLista.SelectedIndex].Value);

        CCDettagliSuperficie ccds = new CCDettagliSuperficieMgr(AuthenticationInfo.CreateDatabase()).GetById(AuthenticationInfo.IdComune, id);

        BindDettaglio(ccds);
    }

    protected void cmdChiudiLista_Click(object sender, EventArgs e)
    {
        Response.Redirect("CCTipiSuperficie.aspx?software=" + Software + "&token=" + Token + "&id=" + FkCcTsId);
    }

    protected void gvLista_RowDataBound(object sender, GridViewRowEventArgs e)
    {
        if (e.Row.RowType == DataControlRowType.DataRow)
        {
            Label lblTipoSuperficie = e.Row.FindControl("lblTipoSuperficie") as Label;
            CCTipiSuperficie ccts = new CCTipiSuperficieMgr(Database).GetById(AuthenticationInfo.IdComune, (e.Row.DataItem as CCDettagliSuperficie).FkCcTsId.GetValueOrDefault(int.MinValue));
            lblTipoSuperficie.Text = ccts.Descrizione;
        }
    }

    #endregion

    #region Scheda

    protected void cmdSalva_Click(object sender, EventArgs e)
    {
        CCDettagliSuperficie ccds = new CCDettagliSuperficie();

        try
        {
            ccds.Idcomune = AuthenticationInfo.IdComune;
            ccds.Software = Software;
            ccds.Id = IsInserting ? (int?)null : Convert.ToInt32(lblCodice.Text);
            ccds.Descrizione = txtDescrizione.Text;
            ccds.Note = txtNote.Text;

            ccds.FkCcTsId = (String.IsNullOrEmpty(ddlTipiSuperficie.Text) ? (int?)null : Convert.ToInt32(ddlTipiSuperficie.Text));
            CCDettagliSuperficieMgr mgr = new CCDettagliSuperficieMgr(AuthenticationInfo.CreateDatabase());

            if (IsInserting)
                ccds = mgr.Insert(ccds);
            else
                ccds = mgr.Update(ccds);

            BindDettaglio(ccds);
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
        gvLista.DataBind();
        multiView.ActiveViewIndex = 0;
    }

    protected void cmdElimina_Click(object sender, EventArgs e)
    {
        try
        {
            CCDettagliSuperficie ccds = new CCDettagliSuperficie();

            ccds.Idcomune = AuthenticationInfo.IdComune;
            ccds.Software = Software;
            ccds.Id = IsInserting ? (int?)null : Convert.ToInt32(lblCodice.Text);

            CCDettagliSuperficieMgr mgr = new CCDettagliSuperficieMgr(AuthenticationInfo.CreateDatabase());

            mgr.Delete(ccds);

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
        CCTipiSuperficie ccts = new CCTipiSuperficie();
        ccts.Idcomune = AuthenticationInfo.IdComune;
        ccts.Software = Software;
        ccts.Id = FkCcTsId;
        
        ddlTipiSuperficie.DataSource = new CCTipiSuperficieMgr(AuthenticationInfo.CreateDatabase()).GetList(ccts);
        ddlTipiSuperficie.DataBind();
    }

    #endregion


    protected void cmdNuovo_Click(object sender, EventArgs e)
    {
        CCDettagliSuperficie ccds = new CCDettagliSuperficie();
        ccds.FkCcTsId = FkCcTsId;

        BindDettaglio(ccds);
    }
}
