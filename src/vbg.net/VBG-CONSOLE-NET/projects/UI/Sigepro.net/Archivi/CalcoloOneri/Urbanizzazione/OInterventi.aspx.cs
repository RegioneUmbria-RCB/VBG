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
using SIGePro.Net;
using SIGePro.Net.Navigation;
using Init.SIGePro.Exceptions;

public partial class Archivi_CalcoloOneri_Urbanizzazione_OInterventi : BasePage
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

    private void BindDettaglio(OInterventi oi)
    {
        this.IsInserting = oi.Id.GetValueOrDefault(int.MinValue) == int.MinValue;

        lblCodice.Text = IsInserting ? "Nuovo" : oi.Id.ToString();

        if (!IsInserting && !String.IsNullOrEmpty(oi.FkOccbtiId))
            ddlInterventoBase.SelectedValue = oi.FkOccbtiId;
        else
            ddlInterventoBase.SelectedIndex = 0;

        txtIntervento.Text = oi.Intervento;
        txtOrdinamento.ValoreInt = oi.Ordinamento;

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
        BindDettaglio(new OInterventi());
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

        OInterventi oi = new OInterventiMgr(AuthenticationInfo.CreateDatabase()).GetById(AuthenticationInfo.IdComune, id);

        BindDettaglio(oi);
    }

    protected void cmdChiudiLista_Click(object sender, EventArgs e)
    {
        multiView.ActiveViewIndex = 0;
    }

    protected void gvLista_RowDataBound(object sender, GridViewRowEventArgs e)
    {
        if (e.Row.RowType == DataControlRowType.DataRow)
        {
            Label lblInterventoBase = e.Row.FindControl("lblInterventoBase") as Label;
            OCCBaseTipoIntervento occbti = new OCCBaseTipoInterventoMgr(Database).GetById((e.Row.DataItem as OInterventi).FkOccbtiId);
            lblInterventoBase.Text = occbti.Intervento;
        }
    }

    #endregion

    #region Scheda

    protected void cmdSalva_Click(object sender, EventArgs e)
    {
        OInterventi oi = new OInterventi();

        try
        {
            oi.Idcomune = AuthenticationInfo.IdComune;
            oi.Software = Software;
            oi.Id = null;
            if (!IsInserting)
                oi.Id =Convert.ToInt32(lblCodice.Text);

            oi.FkOccbtiId = ddlInterventoBase.Text;
            oi.Intervento = txtIntervento.Text;
			oi.Ordinamento = txtOrdinamento.ValoreInt;

            OInterventiMgr mgr = new OInterventiMgr(AuthenticationInfo.CreateDatabase());

            if (IsInserting)
                oi = mgr.Insert(oi);
            else
                oi = mgr.Update(oi);

            BindDettaglio(oi);
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
            OInterventi oi = new OInterventi();

            oi.Idcomune = AuthenticationInfo.IdComune;
            oi.Software = Software;
            oi.Id = null;
            if (!IsInserting)
                oi.Id =Convert.ToInt32(lblCodice.Text);

            OInterventiMgr mgr = new OInterventiMgr(AuthenticationInfo.CreateDatabase());

            mgr.Delete(oi);

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
        #region Interventi base

        OCCBaseTipoIntervento occbti = new OCCBaseTipoIntervento();

        ddlSrcInterventoBase.DataSource = new OCCBaseTipoInterventoMgr(AuthenticationInfo.CreateDatabase()).GetList(occbti);
        ddlSrcInterventoBase.DataBind();

        ddlSrcInterventoBase.Items.Insert(0, String.Empty);

        ddlInterventoBase.DataSource = new OCCBaseTipoInterventoMgr(AuthenticationInfo.CreateDatabase()).GetList(occbti);
        ddlInterventoBase.DataBind();

        ddlInterventoBase.Items.Insert(0, String.Empty);

        #endregion
    }

    #endregion

}
