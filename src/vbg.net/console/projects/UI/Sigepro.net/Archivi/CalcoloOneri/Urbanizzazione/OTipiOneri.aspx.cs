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
using SIGePro.Net;
using SIGePro.Net.Navigation;
using Init.SIGePro.Manager;
using Init.SIGePro.Exceptions;

public partial class Archivi_CalcoloOneri_Urbanizzazione_OTipiOneri : BasePage
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

    private void BindDettaglio(OTipiOneri oto)
    {
        this.IsInserting = oto.Id.GetValueOrDefault(int.MinValue) == int.MinValue;

        lblCodice.Text = IsInserting ? "Nuovo" : oto.Id.ToString();

        if (!IsInserting && !String.IsNullOrEmpty(oto.FkBtoId))
            ddlOnereBase.SelectedValue = oto.FkBtoId;

        txtDescrizione.Text = oto.Descrizione;
        txtDescrizioneEstesa.Text = oto.Descrizionelunga;

        if (IsInserting) ddlOnereBase.SelectedIndex = 0;

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
        BindDettaglio(new OTipiOneri());
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

        OTipiOneri oto = new OTipiOneriMgr(AuthenticationInfo.CreateDatabase()).GetById(AuthenticationInfo.IdComune, id);

        BindDettaglio(oto);
    }

    protected void cmdChiudiLista_Click(object sender, EventArgs e)
    {
        multiView.ActiveViewIndex = 0;
    }

    protected void gvLista_RowDataBound(object sender, GridViewRowEventArgs e)
    {
        if (e.Row.RowType == DataControlRowType.DataRow)
        {
            Label lblOnereBase = e.Row.FindControl("lblOnereBase") as Label;
            OBaseTipiOnere obto = new OBaseTipiOnereMgr(Database).GetById((e.Row.DataItem as OTipiOneri).FkBtoId);
            lblOnereBase.Text = obto.Descrizione;
        }
    }

    #endregion

    #region Scheda

    protected void cmdSalva_Click(object sender, EventArgs e)
    {
        OTipiOneri oto = new OTipiOneri();

        try
        {
            oto.Idcomune = AuthenticationInfo.IdComune;
            oto.Software = Software;
            oto.Id = null;
            if (!IsInserting)
                oto.Id = Convert.ToInt32(lblCodice.Text);

            oto.FkBtoId = ddlOnereBase.SelectedValue;

            oto.Descrizione = txtDescrizione.Text;
            oto.Descrizionelunga = txtDescrizioneEstesa.Text;

            OTipiOneriMgr mgr = new OTipiOneriMgr(AuthenticationInfo.CreateDatabase());

            if (IsInserting)
                oto = mgr.Insert(oto);
            else
                oto = mgr.Update(oto);

            BindDettaglio(oto);
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
            OTipiOneri oto = new OTipiOneri();

            oto.Idcomune = AuthenticationInfo.IdComune;
            oto.Software = Software;
            oto.Id = null;
            if (!IsInserting)
                oto.Id =Convert.ToInt32(lblCodice.Text);

            OTipiOneriMgr mgr = new OTipiOneriMgr(AuthenticationInfo.CreateDatabase());

            mgr.Delete(oto);

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
        #region Base tipi oneri

        OBaseTipiOnere obto = new OBaseTipiOnere();

        ddlSrcOnereBase.DataSource = new OBaseTipiOnereMgr(AuthenticationInfo.CreateDatabase()).GetList(obto);
        ddlSrcOnereBase.DataBind();
        
        ddlSrcOnereBase.Items.Insert(0, String.Empty);
        ddlSrcOnereBase.SelectedIndex = 0;

        ddlOnereBase.DataSource = new OBaseTipiOnereMgr(AuthenticationInfo.CreateDatabase()).GetList(obto);
        ddlOnereBase.DataBind();

        ddlOnereBase.Items.Insert(0, String.Empty);

        #endregion
    }

    #endregion

}
