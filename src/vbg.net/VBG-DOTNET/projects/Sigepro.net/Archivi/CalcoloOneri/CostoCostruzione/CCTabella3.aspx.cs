using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Manager;
using Init.Utils;
using SIGePro.Net;
using System;
using System.Web.UI.WebControls;

public partial class Archivi_CalcoloOneri_CostoCostruzione_CCTabella3 : BasePage
{
    protected void Page_Load(object sender, EventArgs e)
    {
        this.ImpostaScriptEliminazione(this.cmdElimina);

        if (!this.IsPostBack)
        {
            var filtro = new CCDettagliSuperficie();
            filtro.Idcomune = this.IdComune;
            filtro.Software = this.Software;
            filtro.OrderBy = "Descrizione asc";

            this.ddlDettaglioSuperficie.Item.DataSource = new CCDettagliSuperficieMgr(this.Database).GetList(filtro);
            this.ddlDettaglioSuperficie.Item.DataBind();

            this.ddlDettaglioSuperficie.Item.Items.Insert(0, new ListItem(String.Empty, String.Empty));
        }
    }

    protected void multiView_ActiveViewChanged(object sender, EventArgs e)
    {
        switch (this.multiView.ActiveViewIndex)
        {
            case (1):
                this.Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Risultato;
                return;
            case (2):
                this.Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;
                return;
            default:
                this.Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Ricerca;
                return;
        }
    }

    private void BindDettaglio(CCTabella3 cct3)
    {
        this.IsInserting = cct3.Id == null;

        this.lblCodice.Text = this.IsInserting ? "Nuovo" : cct3.Id.ToString();

        this.txtDescrizione.Text = this.IsInserting ? String.Empty : cct3.Descrizione;

        this.txtDa.Text = (this.IsInserting || cct3.RapportoSuSnrDa.GetValueOrDefault(int.MinValue) == int.MinValue) ? String.Empty : cct3.RapportoSuSnrDa.ToString();
        this.txtA.Text = (this.IsInserting || cct3.RapportoSuSnrA.GetValueOrDefault(int.MinValue) == int.MinValue) ? String.Empty : cct3.RapportoSuSnrA.ToString();
        this.txtPerc.Text = (this.IsInserting || DoubleChecker.IsEmpty(cct3.Perc)) ? String.Empty : cct3.Perc.ToString();

        this.ddlDettaglioSuperficie.Value = cct3.FkCcDsId.GetValueOrDefault(int.MinValue) == int.MinValue ? String.Empty : cct3.FkCcDsId.ToString();

        this.cmdElimina.Visible = !this.IsInserting;
        this.multiView.ActiveViewIndex = 2;
    }

    #region Cerca

    protected void cmdCerca_Click(object sender, EventArgs e)
    {
        this.gvLista.DataBind();

        this.multiView.ActiveViewIndex = 1;

    }

    protected void cmdNuovo_Click(object sender, EventArgs e)
    {
        this.BindDettaglio(new CCTabella3());
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

        CCTabella3 cct3 = new CCTabella3Mgr(this.AuthenticationInfo.CreateDatabase()).GetById(this.AuthenticationInfo.IdComune, id);

        this.BindDettaglio(cct3);
    }

    protected void cmdChiudiLista_Click(object sender, EventArgs e)
    {
        this.multiView.ActiveViewIndex = 0;
    }

    #endregion

    #region Scheda

    protected void cmdSalva_Click(object sender, EventArgs e)
    {
        var cct3 = new CCTabella3();

        try
        {
            cct3.Idcomune = this.AuthenticationInfo.IdComune;
            cct3.Software = this.Software;
            cct3.Id = this.IsInserting ? (int?)null : Convert.ToInt32(this.lblCodice.Text);
            cct3.Descrizione = this.txtDescrizione.Text;
            cct3.RapportoSuSnrDa = this.txtDa.ValoreInt;
            cct3.RapportoSuSnrA = this.txtA.ValoreInt;
            cct3.Perc = this.txtPerc.ValoreDecimal;
            cct3.FkCcDsId = String.IsNullOrEmpty(this.ddlDettaglioSuperficie.Value) ? (int?)null : Convert.ToInt32(this.ddlDettaglioSuperficie.Value);

            var mgr = new CCTabella3Mgr(this.AuthenticationInfo.CreateDatabase());

            if (this.IsInserting)
                cct3 = mgr.Insert(cct3);
            else
                cct3 = mgr.Update(cct3);

            this.BindDettaglio(cct3);
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
        this.multiView.ActiveViewIndex = 0;
    }

    protected void cmdElimina_Click(object sender, EventArgs e)
    {
        try
        {
            var cct3 = new CCTabella3();

            cct3.Idcomune = this.AuthenticationInfo.IdComune;
            cct3.Software = this.Software;
            cct3.Id = this.IsInserting ? (int?)null : Convert.ToInt32(this.lblCodice.Text);

            var mgr = new CCTabella3Mgr(this.AuthenticationInfo.CreateDatabase());

            mgr.Delete(cct3);

            this.multiView.ActiveViewIndex = 0;
        }
        catch (Exception ex)
        {
            this.MostraErrore("Errore durante l'eliminazione: " + ex.Message, ex);
        }
    }

    #endregion

}
