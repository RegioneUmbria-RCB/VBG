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

public partial class Archivi_CalcoloOneri_CostoCostruzione_CCTipiSuperficie : BasePage
{
    #region Proprietà

    private int? Id
    {
        get { return string.IsNullOrEmpty(Request.QueryString["id"]) ? (int?)null : Convert.ToInt32(Request.QueryString["id"]); }
    }

    #endregion

    protected void Page_Load(object sender, EventArgs e)
    {
        ImpostaScriptEliminazione(cmdElimina);
        if (!Page.IsPostBack)
        {
            if (Id.GetValueOrDefault(int.MinValue) > int.MinValue)
            {
                CCTipiSuperficie ccts = new CCTipiSuperficieMgr(AuthenticationInfo.CreateDatabase()).GetById(AuthenticationInfo.IdComune, Id.GetValueOrDefault(int.MinValue));
                BindDettaglio(ccts);
            }
        }
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

    private void BindDettaglio(CCTipiSuperficie ccts)
    {
        this.IsInserting = ccts.Id.GetValueOrDefault(int.MinValue) == int.MinValue;

        lblCodice.Text = IsInserting ? "Nuovo" : ccts.Id.ToString();
        txtDescrizione.Text = ccts.Descrizione;

        cmdElimina.Visible = !IsInserting;
        cmdDettaglio.Visible = !IsInserting;

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
        BindDettaglio(new CCTipiSuperficie());
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

        CCTipiSuperficie ccts = new CCTipiSuperficieMgr(AuthenticationInfo.CreateDatabase()).GetById(AuthenticationInfo.IdComune, id);

        BindDettaglio(ccts);
    }

    protected void cmdChiudiLista_Click(object sender, EventArgs e)
    {
        multiView.ActiveViewIndex = 0;
    }

    #endregion

    #region Scheda

    protected void cmdSalva_Click(object sender, EventArgs e)
    {
        CCTipiSuperficie ccts = new CCTipiSuperficie();

        try
        {
            ccts.Idcomune = AuthenticationInfo.IdComune;
            ccts.Software = Software;
            ccts.Id = IsInserting ? (int?)null : Convert.ToInt32(lblCodice.Text);
            ccts.Descrizione = txtDescrizione.Text;
            ccts.Note = txtNote.Text;

            CCTipiSuperficieMgr mgr = new CCTipiSuperficieMgr(AuthenticationInfo.CreateDatabase());

            if (IsInserting)
                ccts = mgr.Insert(ccts);
            else
                ccts = mgr.Update(ccts);

            BindDettaglio(ccts);
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
            CCTipiSuperficie ccts = new CCTipiSuperficie();

            ccts.Idcomune = AuthenticationInfo.IdComune;
            ccts.Software = Software;
            ccts.Id = IsInserting ? (int?)null : Convert.ToInt32(lblCodice.Text);

            CCTipiSuperficieMgr mgr = new CCTipiSuperficieMgr(AuthenticationInfo.CreateDatabase());

            mgr.Delete(ccts);

            multiView.ActiveViewIndex = 0;
        }
        catch (Exception ex)
        {
            MostraErrore("Errore durante l'eliminazione: " + ex.Message, ex);
        }
    }

    #endregion

    #region Links

    protected void cmdDettaglio_Click(object sender, EventArgs e)
    {
        Response.Redirect("CCDettagliSuperficie.aspx?software=" + Software + "&token=" + Token + "&fkcctsid=" + lblCodice.Text);
    }

    #endregion
}
