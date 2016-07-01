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

public partial class Archivi_CalcoloOneri_CostoCostruzione_CCClassiSuperfici : BasePage
{
    protected void Page_Load(object sender, EventArgs e)
    {
        ImpostaScriptEliminazione(cmdElimina);
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

    private void BindDettaglio(CCClassiSuperfici cccs)
    {
        this.IsInserting = cccs.Id.GetValueOrDefault(int.MinValue) == int.MinValue;

        lblCodice.Value = IsInserting ? "Nuovo" : cccs.Id.ToString();

        txtDescrizione.Value = IsInserting ? String.Empty : cccs.Classe;

        txtDa.Value = IsInserting ? String.Empty : cccs.Da.ToString();
        txtA.Value = IsInserting ? String.Empty : cccs.A.ToString();
        txtIncremento.Value = IsInserting ? String.Empty : cccs.Incremento.ToString();
		txtAliquotaCC.Value = IsInserting ? String.Empty : cccs.AliquotaCalcoloCostoCostruzione.ToString();
    
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
        BindDettaglio(new CCClassiSuperfici());
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

        CCClassiSuperfici cccs = new CCClassiSuperficiMgr(AuthenticationInfo.CreateDatabase()).GetById(AuthenticationInfo.IdComune, id);

        BindDettaglio(cccs);
    }

    protected void cmdChiudiLista_Click(object sender, EventArgs e)
    {
        multiView.ActiveViewIndex = 0;
    }

    #endregion

    #region Scheda

    protected void cmdSalva_Click(object sender, EventArgs e)
    {
        CCClassiSuperfici cccs = new CCClassiSuperfici();

        try
        {
            cccs.Idcomune = AuthenticationInfo.IdComune;
            cccs.Software = Software;
            cccs.Id = IsInserting ? (int?)null : Convert.ToInt32(lblCodice.Value);
            cccs.Classe = txtDescrizione.Value;
			cccs.Da = txtDa.Item.ValoreInt;
			cccs.A = txtA.Item.ValoreInt;
			cccs.Incremento = String.IsNullOrEmpty(txtIncremento.Value) ? (double?)null : txtIncremento.Item.ValoreDouble;
			cccs.AliquotaCalcoloCostoCostruzione = String.IsNullOrEmpty(txtAliquotaCC.Value) ? (double?)null : txtAliquotaCC.Item.ValoreDouble;

            CCClassiSuperficiMgr mgr = new CCClassiSuperficiMgr(AuthenticationInfo.CreateDatabase());

            if (IsInserting)
                cccs = mgr.Insert(cccs);
            else
                cccs = mgr.Update(cccs);

            BindDettaglio(cccs);
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
            CCClassiSuperfici cccs = new CCClassiSuperfici();

            cccs.Idcomune = AuthenticationInfo.IdComune;
            cccs.Software = Software;
            cccs.Id = IsInserting ? (int?)null : Convert.ToInt32(lblCodice.Value);

            CCClassiSuperficiMgr mgr = new CCClassiSuperficiMgr(AuthenticationInfo.CreateDatabase());

            mgr.Delete(cccs);

            multiView.ActiveViewIndex = 0;
        }
        catch (Exception ex)
        {
            MostraErrore("Errore durante l'eliminazione: " + ex.Message, ex);
        }
    }

    #endregion

}
