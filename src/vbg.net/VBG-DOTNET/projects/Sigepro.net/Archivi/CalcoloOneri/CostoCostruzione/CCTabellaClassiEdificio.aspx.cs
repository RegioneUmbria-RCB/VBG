using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Manager;
using SIGePro.Net;
using System;

public partial class Archivi_CalcoloOneri_CostoCostruzione_CCTabellaClassiEdificio : BasePage
{
    protected void Page_Load(object sender, EventArgs e)
    {
        this.ImpostaScriptEliminazione(this.cmdElimina);
    }

    protected void cmdCerca_Click(object sender, EventArgs e)
    {
        this.gvLista.DataBind();

        this.multiView.ActiveViewIndex = 1;
    }
    protected void gvLista_SelectedIndexChanged(object sender, EventArgs e)
    {
        var id = Convert.ToInt32(this.gvLista.DataKeys[this.gvLista.SelectedIndex].Value);

        var cctce = new CCTabellaClassiEdificioMgr(this.AuthenticationInfo.CreateDatabase()).GetById(this.AuthenticationInfo.IdComune, id);

        this.BindDettaglio(cctce);
    }

    private void BindDettaglio(CCTabellaClassiEdificio cctce)
    {
        this.IsInserting = cctce.Id.GetValueOrDefault(int.MinValue) == int.MinValue;

        this.lblId.Text = this.IsInserting ? "Nuovo" : cctce.Id.ToString();
        this.txtDescrizione.Text = cctce.Descrizione;
        this.txtDa.ValoreInt = cctce.Da;
        this.txtA.ValoreInt = cctce.A;
        this.txtMaggiorazione.ValoreDecimal = cctce.Maggiorazione;

        this.cmdElimina.Visible = !this.IsInserting;

        this.multiView.ActiveViewIndex = 2;
    }
    protected void cmdCloseList_Click(object sender, EventArgs e)
    {
        this.multiView.ActiveViewIndex = 0;
    }
    protected void cmdNuovo_Click(object sender, EventArgs e)
    {
        this.BindDettaglio(new CCTabellaClassiEdificio());
    }
    protected void cmdChiudiDettaglio_Click(object sender, EventArgs e)
    {
        this.multiView.ActiveViewIndex = 0;
    }
    protected void cmdSalva_Click(object sender, EventArgs e)
    {
        var cctce = new CCTabellaClassiEdificio();

        try
        {
            cctce.Idcomune = this.AuthenticationInfo.IdComune;
            cctce.Software = this.Software;
            cctce.Id = this.IsInserting ? (int?)null : Convert.ToInt32(this.lblId.Text);
            cctce.Descrizione = this.txtDescrizione.Text;
            cctce.Da = this.txtDa.ValoreInt;
            cctce.A = this.txtA.ValoreInt;
            cctce.Maggiorazione = this.txtMaggiorazione.ValoreDecimal;


            var mgr = new CCTabellaClassiEdificioMgr(this.AuthenticationInfo.CreateDatabase());

            if (this.IsInserting)
                cctce = mgr.Insert(cctce);
            else
                cctce = mgr.Update(cctce);

            this.BindDettaglio(cctce);
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
    protected void cmdChiudi_Click(object sender, EventArgs e)
    {
        base.CloseCurrentPage();
    }
    protected void cmdElimina_Click(object sender, EventArgs e)
    {
        try
        {
            var cctce = new CCTabellaClassiEdificio();

            cctce.Idcomune = this.AuthenticationInfo.IdComune;
            cctce.Id = this.IsInserting ? (int?)null : Convert.ToInt32(this.lblId.Text);

            var mgr = new CCTabellaClassiEdificioMgr(this.AuthenticationInfo.CreateDatabase());

            mgr.Delete(cctce);

            this.multiView.ActiveViewIndex = 0;
        }
        catch (Exception ex)
        {
            this.MostraErrore("Errore durante l'eliminazione: " + ex.Message, ex);
        }
    }
}
