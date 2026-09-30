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
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using System.Collections.Generic;
using Init.SIGePro.Authentication;
using SIGePro.Net;
using PersonalLib2.Exceptions;
using Init.SIGePro.Exceptions;

public partial class Archivi_CalcoloOneri_Urbanizzazione_OConfigurazioneTipiOnere : BasePage
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!Page.IsPostBack)
        {
            BindCombo();
            BindGrid();
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
    private void BindCombo()
    {
        ddlOnereBase.Items.Clear();
        List<OBaseTipiOnere> listTipiOnere = new OBaseTipiOnereMgr(AuthenticationInfo.CreateDatabase()).GetList(new OBaseTipiOnere());
        
        foreach (OBaseTipiOnere obto in listTipiOnere)
        {
            OConfigurazioneTipiOnere octo = new OConfigurazioneTipiOnereMgr(AuthenticationInfo.CreateDatabase()).GetById(AuthenticationInfo.IdComune, obto.Id, Software);

            if (octo == null)    
                ddlOnereBase.Items.Add(new ListItem(obto.Descrizione,obto.Id));
        }
        ddlOnereBase.Items.Insert(0, new ListItem());

        TipiCausaliOneri tco = new TipiCausaliOneri();
        tco.Idcomune = AuthenticationInfo.IdComune;
        tco.Software = Software;
        tco.CoDisabilitato = 0;
        tco.CoSerichiedeendo = "0";
        tco.OrderBy = "CO_ORDINAMENTO";

        ddlTipoCausaleOnere.DataSource = new TipiCausaliOneriMgr(AuthenticationInfo.CreateDatabase()).GetList(tco);
        ddlTipoCausaleOnere.DataBind();
        ddlTipoCausaleOnere.Items.Insert(0, new ListItem());
    }
    private void BindDettaglio(OConfigurazioneTipiOnere octo)
    {
        multiView.ActiveViewIndex = 1;
    }
    private void BindGrid()
    {
        OConfigurazioneTipiOnere octo = new OConfigurazioneTipiOnere();
        octo.Idcomune = AuthenticationInfo.IdComune;
        octo.Software = Software;

        gvLista.DataSource = new OConfigurazioneTipiOnereMgr(AuthenticationInfo.CreateDatabase()).GetList(octo);
        gvLista.DataBind();
    }

    #region Lista
    protected void ImageButton1_Click(object sender, EventArgs e)
    {
        Response.Redirect("OConfigurazione.aspx?software=" + Software + "&token=" + Token);
    }
    protected void gvLista_RowDataBound(object sender, GridViewRowEventArgs e)
    {
        if (e.Row.RowType == DataControlRowType.DataRow)
        {
			OConfigurazioneTipiOnere cto = (e.Row.DataItem as OConfigurazioneTipiOnere);

            Label lblDescrizioneOneriBase = e.Row.FindControl("lblDescrizioneOneriBase") as Label;
            OBaseTipiOnere obto = new OBaseTipiOnereMgr(Database).GetById(cto.FkBtoId);
            lblDescrizioneOneriBase.Text = obto.Descrizione;

            Label lblDescrizioneTipoCausaleOneri = e.Row.FindControl("lblDescrizioneTipoCausaleOneri") as Label;
			TipiCausaliOneri tco = new TipiCausaliOneriMgr(Database).GetById(IdComune,cto.FkCoId.GetValueOrDefault(int.MinValue));
            lblDescrizioneTipoCausaleOneri.Text = tco.CoDescrizione;

            HtmlInputHidden lblCodiceOnereBase = e.Row.FindControl("lblCodiceOnereBase") as HtmlInputHidden;
            if (lblCodiceOnereBase != null) lblCodiceOnereBase.Value = cto.FkBtoId;

            ImpostaScriptEliminazione((e.Row.FindControl("cmdElimina") as ImageButton));

        }
    }
    protected void gvLista_RowDeleting(object sender, GridViewDeleteEventArgs e)
    {
        e.Cancel = true;
        
        HtmlInputHidden lblCodiceOnereBase = gvLista.Rows[e.RowIndex].FindControl("lblCodiceOnereBase") as HtmlInputHidden;

        try
        {
            OConfigurazioneTipiOnere octo = new OConfigurazioneTipiOnere();
            OConfigurazioneTipiOnereMgr mgr = new OConfigurazioneTipiOnereMgr(AuthenticationInfo.CreateDatabase());

            octo.FkBtoId = lblCodiceOnereBase.Value;
            octo.Idcomune = AuthenticationInfo.IdComune;
            octo.Software = Software;

            mgr.Delete(octo);
        }
        catch (Exception ex)
        {
            MostraErrore(AmbitoErroreEnum.Cancellazione, ex);
        }
        finally
        {
            BindGrid();
        }
    }
    protected void cmdNuovo_Click(object sender, EventArgs e)
    {
        BindCombo();
        BindDettaglio(new OConfigurazioneTipiOnere());
    }
    #endregion

    #region Scheda
    protected void cmdSalva_Click(object sender, EventArgs e)
    {
        OConfigurazioneTipiOnere octo = new OConfigurazioneTipiOnere();

        try
        {
            octo.Idcomune = AuthenticationInfo.IdComune;
            octo.Software = Software;

            octo.FkBtoId = ddlOnereBase.SelectedValue;
            octo.FkCoId = string.IsNullOrEmpty(ddlTipoCausaleOnere.SelectedValue) ? (int?)null : Convert.ToInt32(ddlTipoCausaleOnere.SelectedValue);

            OConfigurazioneTipiOnereMgr mgr = new OConfigurazioneTipiOnereMgr(AuthenticationInfo.CreateDatabase());

            octo = mgr.Insert(octo);

            BindGrid();
            BindCombo();

            multiView.ActiveViewIndex = 0;
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
        BindGrid();
        multiView.ActiveViewIndex = 0;
    }
    #endregion
}
