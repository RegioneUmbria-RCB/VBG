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

public partial class Archivi_CalcoloOneri_CostoCostruzione_CCDestinazioni : BasePage
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

    private void BindDettaglio(CCDestinazioni ccd)
    {
        this.IsInserting = ccd.Id.GetValueOrDefault(int.MinValue) == int.MinValue;

        lblCodice.Text = IsInserting ? "Nuovo" : ccd.Id.ToString();

        if (!IsInserting)
            ddlDestinazioneBase.SelectedValue = ccd.FkOccbdeId;
        else
            ddlDestinazioneBase.SelectedIndex = 0;

        txtDestinazioneBase.Text = ccd.Destinazione;

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
        BindDettaglio(new CCDestinazioni());
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

        CCDestinazioni ccd = new CCDestinazioniMgr(AuthenticationInfo.CreateDatabase()).GetById(AuthenticationInfo.IdComune, id);

        BindDettaglio(ccd);
    }

    protected void cmdChiudiLista_Click(object sender, EventArgs e)
    {
        multiView.ActiveViewIndex = 0;
    }

    #endregion

    #region Scheda

    protected void cmdSalva_Click(object sender, EventArgs e)
    {
        CCDestinazioni ccd = new CCDestinazioni();

        try
        {
            ccd.Idcomune = AuthenticationInfo.IdComune;
            ccd.Software = Software;
            ccd.Id = IsInserting ? (int?)null : Convert.ToInt32(lblCodice.Text);

            ccd.FkOccbdeId = ddlDestinazioneBase.Text;
            ccd.Destinazione = txtDestinazioneBase.Text;

            CCDestinazioniMgr mgr = new CCDestinazioniMgr(AuthenticationInfo.CreateDatabase());

            if (IsInserting)
                ccd = mgr.Insert(ccd);
            else
                ccd = mgr.Update(ccd);

            BindDettaglio(ccd);
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
            CCDestinazioni ccd = new CCDestinazioni();

            ccd.Idcomune = AuthenticationInfo.IdComune;
            ccd.Software = Software;
            ccd.Id = IsInserting ? (int?)null : Convert.ToInt32(lblCodice.Text);

            CCDestinazioniMgr mgr = new CCDestinazioniMgr(AuthenticationInfo.CreateDatabase());

            mgr.Delete(ccd);

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
    }

    #endregion

}
