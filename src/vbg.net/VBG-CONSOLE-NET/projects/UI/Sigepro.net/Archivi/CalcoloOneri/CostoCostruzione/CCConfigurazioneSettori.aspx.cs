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
using Init.SIGePro.Authentication;
using SIGePro.Net;
using SIGePro.Net.Navigation;
using PersonalLib2.Exceptions;
using System.Collections.Generic;

public partial class Archivi_CalcoloOneri_CostoCostruzione_CCConfigurazioneSettori : BasePage
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
        Settori sett = new Settori();
        sett.IDCOMUNE = AuthenticationInfo.IdComune;
        sett.SOFTWARE = Software;

        List<Settori> arSettori = new SettoriMgr(AuthenticationInfo.CreateDatabase()).GetList(sett);

        ddlFkSeCodiceSettore.DataSource = arSettori;
        ddlFkSeCodiceSettore.DataBind();
        ddlFkSeCodiceSettore.Items.Insert(0, "");
    }
    private void BindDettaglio(CCConfigurazioneSettori cccs)
    {
        if (cccs != null)
        {
            if (ddlFkSeCodiceSettore.Items.Count > 0)
                ddlFkSeCodiceSettore.SelectedValue = ( string.IsNullOrEmpty( cccs.FkSeCodicesettore )) ? "" : cccs.FkSeCodicesettore;
        }

        multiView.ActiveViewIndex = 1;
    }
    private void BindGrid()
    {
        gvLista.DataSource = new CCConfigurazioneSettoriMgr(AuthenticationInfo.CreateDatabase()).GetList(AuthenticationInfo.IdComune, Software, null);
        gvLista.DataBind();
    }

    #region Lista
    protected void ImageButton1_Click(object sender, EventArgs e)
    {
        Response.Redirect("CCConfigurazione.aspx?software=" + Software + "&token=" + Token);
    }
    protected void gvLista_RowDataBound(object sender, GridViewRowEventArgs e)
    {
        if (e.Row.RowType == DataControlRowType.DataRow)
        {
            Label lblFkSeCodicesettore = e.Row.FindControl("lblFkSeCodicesettore") as Label;
            lblFkSeCodicesettore.Text = (e.Row.DataItem as CCConfigurazioneSettori).FkSeCodicesettore;

            Label lblTipoInformazione = e.Row.FindControl("lblTipoInformazione") as Label;
            Settori sett = new SettoriMgr(Database).GetById((e.Row.DataItem as CCConfigurazioneSettori).FkSeCodicesettore, AuthenticationInfo.IdComune);
            lblTipoInformazione.Text = sett.SETTORE;

            ImpostaScriptEliminazione((e.Row.FindControl("cmdElimina") as ImageButton ));
        }
    }
    protected void gvLista_RowDeleting(object sender, GridViewDeleteEventArgs e)
    {
        e.Cancel = true;
        string fk_se_codicesettore = gvLista.DataKeys[e.RowIndex].Value.ToString();

        try
        {
            CCConfigurazioneSettori cccs = new CCConfigurazioneSettori();
            CCConfigurazioneSettoriMgr mgr = new CCConfigurazioneSettoriMgr(AuthenticationInfo.CreateDatabase());

            cccs.FkSeCodicesettore = fk_se_codicesettore;
            cccs.Idcomune = AuthenticationInfo.IdComune;
            cccs.Software = Software;

            mgr.Delete(cccs);
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
        BindDettaglio(new CCConfigurazioneSettori());
    }
    #endregion

    #region Scheda
    protected void cmdSalva_Click(object sender, EventArgs e)
    {
        CCConfigurazioneSettori cccs = new CCConfigurazioneSettori();

        try
        {
            cccs.Idcomune = AuthenticationInfo.IdComune;
            cccs.Software = Software;
            cccs.FkSeCodicesettore = ddlFkSeCodiceSettore.SelectedValue;

            CCConfigurazioneSettoriMgr mgr = new CCConfigurazioneSettoriMgr(AuthenticationInfo.CreateDatabase());

            cccs = mgr.Insert(cccs);

            multiView.ActiveViewIndex = 0;

            BindGrid();
        }
        catch (DatabaseException de)
        {
            MostraErrore("Non è possibile inserire più volte lo stesso tipo informazione!",de);
        }
        catch (Exception ex)
        {
            MostraErrore("Errore durante il salvataggio: " + ex.Message, ex);
        }
    }
    protected void cmdChiudiDettaglio_Click(object sender, EventArgs e)
    {
        multiView.ActiveViewIndex = 0;
        BindGrid();
    }
    #endregion
}
