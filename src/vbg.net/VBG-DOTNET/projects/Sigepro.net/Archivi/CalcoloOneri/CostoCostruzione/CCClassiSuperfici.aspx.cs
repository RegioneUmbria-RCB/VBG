using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Manager;
using SIGePro.Net;
using System;

public partial class Archivi_CalcoloOneri_CostoCostruzione_CCClassiSuperfici : BasePage
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

    private void BindDettaglio(CCClassiSuperfici cccs)
    {
        this.IsInserting = cccs.Id.GetValueOrDefault(int.MinValue) == int.MinValue;

        this.lblCodice.Value = this.IsInserting ? "Nuovo" : cccs.Id.ToString();

        this.txtDescrizione.Value = this.IsInserting ? String.Empty : cccs.Classe;

        this.txtDa.Value = this.IsInserting ? String.Empty : cccs.Da.ToString();
        this.txtA.Value = this.IsInserting ? String.Empty : cccs.A.ToString();
        this.txtIncremento.Value = this.IsInserting ? String.Empty : cccs.Incremento.ToString();
        this.txtAliquotaCC.Value = this.IsInserting ? String.Empty : cccs.AliquotaCalcoloCostoCostruzione.ToString();

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
        this.BindDettaglio(new CCClassiSuperfici());
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

        CCClassiSuperfici cccs = new CCClassiSuperficiMgr(this.AuthenticationInfo.CreateDatabase()).GetById(this.AuthenticationInfo.IdComune, id);

        this.BindDettaglio(cccs);
    }

    protected void cmdChiudiLista_Click(object sender, EventArgs e)
    {
        this.multiView.ActiveViewIndex = 0;
    }

    #endregion

    #region Scheda

    protected void cmdSalva_Click(object sender, EventArgs e)
    {
        CCClassiSuperfici cccs = new CCClassiSuperfici();

        try
        {
            cccs.Idcomune = this.AuthenticationInfo.IdComune;
            cccs.Software = this.Software;
            cccs.Id = this.IsInserting ? (int?)null : Convert.ToInt32(this.lblCodice.Value);
            cccs.Classe = this.txtDescrizione.Value;
            cccs.Da = this.txtDa.Item.ValoreInt;
            cccs.A = this.txtA.Item.ValoreInt;
            cccs.Incremento = this.txtIncremento.Item.ValoreDecimal;
            cccs.AliquotaCalcoloCostoCostruzione = this.txtAliquotaCC.Item.ValoreDecimal;

            CCClassiSuperficiMgr mgr = new CCClassiSuperficiMgr(this.AuthenticationInfo.CreateDatabase());

            if (this.IsInserting)
                cccs = mgr.Insert(cccs);
            else
                cccs = mgr.Update(cccs);

            this.BindDettaglio(cccs);
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
            CCClassiSuperfici cccs = new CCClassiSuperfici();

            cccs.Idcomune = this.AuthenticationInfo.IdComune;
            cccs.Software = this.Software;
            cccs.Id = this.IsInserting ? (int?)null : Convert.ToInt32(this.lblCodice.Value);

            CCClassiSuperficiMgr mgr = new CCClassiSuperficiMgr(this.AuthenticationInfo.CreateDatabase());

            mgr.Delete(cccs);

            this.multiView.ActiveViewIndex = 0;
        }
        catch (Exception ex)
        {
            this.MostraErrore("Errore durante l'eliminazione: " + ex.Message, ex);
        }
    }

    #endregion

}
