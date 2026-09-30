using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Manager;
using SIGePro.Net;
using System;

public partial class Archivi_CalcoloOneri_CostoCostruzione_CCTabellaCaratterist : BasePage
{
    protected void Page_Load(object sender, EventArgs e)
    {
        this.ImpostaScriptEliminazione(this.cmdElimina);
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

    private void BindDettaglio(CCTabellaCaratterist cctc)
    {
        this.IsInserting = cctc.Id.GetValueOrDefault(int.MinValue) == int.MinValue;

        this.lblCodice.Text = this.IsInserting ? "Nuovo" : cctc.Id.ToString();

        this.txtDescrizione.Text = this.IsInserting ? String.Empty : cctc.Descrizione;

        this.txtPerc.Text = this.IsInserting ? String.Empty : cctc.Perc.ToString();

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
        this.BindDettaglio(new CCTabellaCaratterist());
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

        CCTabellaCaratterist cctc = new CCTabellaCaratteristMgr(this.AuthenticationInfo.CreateDatabase()).GetById(this.AuthenticationInfo.IdComune, id);

        this.BindDettaglio(cctc);
    }

    protected void cmdChiudiLista_Click(object sender, EventArgs e)
    {
        this.multiView.ActiveViewIndex = 0;
    }

    #endregion

    #region Scheda

    protected void cmdSalva_Click(object sender, EventArgs e)
    {
        CCTabellaCaratterist cctc = new CCTabellaCaratterist();

        try
        {
            cctc.Idcomune = this.AuthenticationInfo.IdComune;
            cctc.Software = this.Software;
            cctc.Id = this.IsInserting ? (int?)null : Convert.ToInt32(this.lblCodice.Text);
            cctc.Descrizione = this.txtDescrizione.Text;
            cctc.Perc = this.txtPerc.ValoreDecimal;

            CCTabellaCaratteristMgr mgr = new CCTabellaCaratteristMgr(this.AuthenticationInfo.CreateDatabase());

            if (this.IsInserting)
                cctc = mgr.Insert(cctc);
            else
                cctc = mgr.Update(cctc);

            this.BindDettaglio(cctc);
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
            CCTabellaCaratterist cctc = new CCTabellaCaratterist();

            cctc.Idcomune = this.AuthenticationInfo.IdComune;
            cctc.Software = this.Software;
            cctc.Id = this.IsInserting ? (int?)null : Convert.ToInt32(this.lblCodice.Text);

            CCTabellaCaratteristMgr mgr = new CCTabellaCaratteristMgr(this.AuthenticationInfo.CreateDatabase());

            mgr.Delete(cctc);

            this.multiView.ActiveViewIndex = 0;
        }
        catch (Exception ex)
        {
            this.MostraErrore("Errore durante l'eliminazione: " + ex.Message, ex);
        }
    }

    #endregion

}
