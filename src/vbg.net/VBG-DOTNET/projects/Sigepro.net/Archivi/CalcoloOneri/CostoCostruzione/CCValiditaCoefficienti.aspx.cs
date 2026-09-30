using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Manager;
using SIGePro.Net;
using System;

public partial class Archivi_CalcoloOneri_CCValiditaCoefficienti : BasePage
{
    #region Proprietà

    private int? Id
    {
        get { return string.IsNullOrEmpty(this.Request.QueryString["id"]) ? (int?)null : Convert.ToInt32(this.Request.QueryString["id"]); }
    }

    #endregion

    protected void Page_Load(object sender, EventArgs e)
    {
        this.ImpostaScriptEliminazione(this.cmdElimina);

        if (!this.Page.IsPostBack)
        {
            if (this.Id.HasValue)
            {
                var ccvc = new CCValiditaCoefficientiMgr(this.AuthenticationInfo.CreateDatabase()).GetById(this.AuthenticationInfo.IdComune, this.Id.GetValueOrDefault(int.MinValue));
                this.BindDettaglio(ccvc);
            }
            else
            {
                this.DataBind();
            }
        }
    }

    public override void DataBind()
    {
        this.gvLista.DataSource = new CCValiditaCoefficientiMgr(this.AuthenticationInfo.CreateDatabase()).GetList(this.AuthenticationInfo.IdComune, this.Software);
        this.gvLista.DataBind();

        this.multiView.SetActiveView(this.listaView);
    }

    protected void multiView_ActiveViewChanged(object sender, EventArgs e)
    {
        switch (this.multiView.ActiveViewIndex)
        {
            case (1):
                this.Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Risultato;
                return;
            default:
                this.Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Ricerca;
                return;
        }
    }

    private void BindDettaglio(CCValiditaCoefficienti ccvc)
    {
        this.IsInserting = ccvc.Id.GetValueOrDefault(int.MinValue) == int.MinValue;

        this.lblCodice.Text = this.IsInserting ? "Nuovo" : ccvc.Id.ToString();
        this.txtDescrizione.Text = ccvc.Descrizione;
        this.txtDataInizioValidita.DateValue = ccvc.Datainiziovalidita;
        this.txtCostoMq.ValoreDouble = ccvc.Costomq.GetValueOrDefault(double.MinValue);

        this.cmdElimina.Visible = this.cmdCoefficientiContributi.Visible = !this.IsInserting;

        this.multiView.ActiveViewIndex = 1;
    }

    #region Cerca

    protected void cmdNuovo_Click(object sender, EventArgs e)
    {
        this.BindDettaglio(new CCValiditaCoefficienti());
    }

    protected void cmdChiudi_Click(object sender, EventArgs e)
    {
        base.CloseCurrentPage();
    }

    #endregion

    #region Lista

    protected void gvLista_SelectedIndexChanged(object sender, EventArgs e)
    {
        var id = Convert.ToInt32(this.gvLista.DataKeys[this.gvLista.SelectedIndex].Value);

        var ccvc = new CCValiditaCoefficientiMgr(this.AuthenticationInfo.CreateDatabase()).GetById(this.AuthenticationInfo.IdComune, id);

        this.BindDettaglio(ccvc);
    }

    #endregion

    #region Scheda

    protected void cmdSalva_Click(object sender, EventArgs e)
    {
        var ccvc = new CCValiditaCoefficienti();

        try
        {
            ccvc.Idcomune = this.AuthenticationInfo.IdComune;
            ccvc.Software = this.Software;
            ccvc.Id = this.IsInserting ? (int?)null : Convert.ToInt32(this.lblCodice.Text);
            ccvc.Descrizione = this.txtDescrizione.Text;
            ccvc.Datainiziovalidita = this.txtDataInizioValidita.DateValue;
            ccvc.Costomq = this.txtCostoMq.ValoreDouble;

            var mgr = new CCValiditaCoefficientiMgr(this.AuthenticationInfo.CreateDatabase());

            if (this.IsInserting)
                ccvc = mgr.Insert(ccvc);
            else
                ccvc = mgr.Update(ccvc);

            this.BindDettaglio(ccvc);
        }
        catch (RequiredFieldException rfe)
        {
            this.MostraErrore("Attenzione, i campi contrassegnati con un asterisco sono obbligatori.", rfe);
        }
        catch (Exception ex)
        {
            this.MostraErrore("Errore durante il salvataggio: " + ex.Message, ex);
        }
    }

    protected void cmdChiudiDettaglio_Click(object sender, EventArgs e)
    {
        this.DataBind();
    }

    protected void cmdElimina_Click(object sender, EventArgs e)
    {
        try
        {
            var ccvc = new CCValiditaCoefficienti();

            ccvc.Idcomune = this.AuthenticationInfo.IdComune;
            ccvc.Software = this.Software;
            ccvc.Id = this.IsInserting ? (int?)null : Convert.ToInt32(this.lblCodice.Text);

            var mgr = new CCValiditaCoefficientiMgr(this.AuthenticationInfo.CreateDatabase());

            mgr.Delete(ccvc);

            this.DataBind();
        }
        catch (Exception ex)
        {
            this.MostraErrore("Errore durante l'eliminazione: " + ex.Message, ex);
        }
    }

    #endregion

    #region Links

    protected void cmdCoefficientiContributi_Click(object sender, EventArgs e)
    {
        this.Response.Redirect("CCCoeffContributo.aspx?software=" + this.Software + "&token=" + this.Token + "&FkCcvcId=" + this.lblCodice.Text);
    }

    protected void cmdCoeffContribAttivita_Click(object sender, EventArgs e)
    {
        this.Response.Redirect("CCCoeffContribAttivita.aspx?software=" + this.Software + "&token=" + this.Token + "&FkCcvcId=" + this.lblCodice.Text);
    }

    protected void cmdCoeffDettaglio_Click(object sender, EventArgs e)
    {
        this.Response.Redirect("CCValiditaCoefficientiDettaglio.aspx?software=" + this.Software + "&token=" + this.Token + "&ListinoId=" + this.lblCodice.Text);
    }

    #endregion


}
