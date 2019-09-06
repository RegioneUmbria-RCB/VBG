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
using Init.SIGePro.Exceptions;

public partial class Archivi_CalcoloOneri_CCValiditaCoefficienti : BasePage
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
                CCValiditaCoefficienti ccvc = new CCValiditaCoefficientiMgr(AuthenticationInfo.CreateDatabase()).GetById(AuthenticationInfo.IdComune, Id.GetValueOrDefault(int.MinValue));
                BindDettaglio(ccvc);
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

    private void BindDettaglio(CCValiditaCoefficienti ccvc)
    {
        this.IsInserting = ccvc.Id.GetValueOrDefault(int.MinValue) == int.MinValue;

        lblCodice.Text = IsInserting ? "Nuovo" : ccvc.Id.ToString();
        txtDescrizione.Text = ccvc.Descrizione;
        txtDataInizioValidita.DateValue = ccvc.Datainiziovalidita;
        txtCostoMq.ValoreDouble = ccvc.Costomq.GetValueOrDefault(double.MinValue);

        cmdElimina.Visible = cmdCoefficientiContributi.Visible = !IsInserting;

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
        BindDettaglio(new CCValiditaCoefficienti());
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

        CCValiditaCoefficienti ccvc = new CCValiditaCoefficientiMgr(AuthenticationInfo.CreateDatabase()).GetById(AuthenticationInfo.IdComune, id);

        BindDettaglio(ccvc);
    }

    protected void cmdChiudiLista_Click(object sender, EventArgs e)
    {
        multiView.ActiveViewIndex = 0;
    }

    #endregion

    #region Scheda

    protected void cmdSalva_Click(object sender, EventArgs e)
    {
        CCValiditaCoefficienti ccvc = new CCValiditaCoefficienti();

        try
        {
            ccvc.Idcomune = AuthenticationInfo.IdComune;
            ccvc.Software = Software;
            ccvc.Id = IsInserting ? (int?)null : Convert.ToInt32(lblCodice.Text);
            ccvc.Descrizione = txtDescrizione.Text;
            ccvc.Datainiziovalidita = txtDataInizioValidita.DateValue;
            ccvc.Costomq = txtCostoMq.ValoreDouble;

            CCValiditaCoefficientiMgr mgr = new CCValiditaCoefficientiMgr(AuthenticationInfo.CreateDatabase());

            if (IsInserting)
                ccvc = mgr.Insert(ccvc);
            else
                ccvc = mgr.Update(ccvc);

            BindDettaglio(ccvc);
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
            CCValiditaCoefficienti ccvc = new CCValiditaCoefficienti();

            ccvc.Idcomune = AuthenticationInfo.IdComune;
            ccvc.Software = Software;
            ccvc.Id = IsInserting ? (int?)null : Convert.ToInt32(lblCodice.Text);

            CCValiditaCoefficientiMgr mgr = new CCValiditaCoefficientiMgr(AuthenticationInfo.CreateDatabase());

            mgr.Delete(ccvc);

            multiView.ActiveViewIndex = 0;
        }
        catch (Exception ex)
        {
            MostraErrore("Errore durante l'eliminazione: " + ex.Message, ex);
        }
    }

    #endregion

    #region Links

    protected void cmdCoefficientiContributi_Click(object sender, EventArgs e)
    {
        Response.Redirect("CCCoeffContributo.aspx?software=" + Software + "&token=" + Token + "&FkCcvcId=" + lblCodice.Text);
    }

    protected void cmdCoeffContribAttivita_Click(object sender, EventArgs e)
    {
        Response.Redirect("CCCoeffContribAttivita.aspx?software=" + Software + "&token=" + Token + "&FkCcvcId=" + lblCodice.Text);
    }

    #endregion

}
