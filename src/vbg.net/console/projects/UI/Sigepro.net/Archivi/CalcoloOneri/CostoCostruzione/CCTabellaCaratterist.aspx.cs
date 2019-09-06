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

public partial class Archivi_CalcoloOneri_CostoCostruzione_CCTabellaCaratterist : BasePage
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

    private void BindDettaglio(CCTabellaCaratterist cctc)
    {
        this.IsInserting = cctc.Id.GetValueOrDefault(int.MinValue) == int.MinValue;

        lblCodice.Text = IsInserting ? "Nuovo" : cctc.Id.ToString();

        txtDescrizione.Text = IsInserting ? String.Empty : cctc.Descrizione;

        txtPerc.Text = IsInserting ? String.Empty : cctc.Perc.ToString();

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
        BindDettaglio(new CCTabellaCaratterist());
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

        CCTabellaCaratterist cctc = new CCTabellaCaratteristMgr(AuthenticationInfo.CreateDatabase()).GetById(AuthenticationInfo.IdComune, id);

        BindDettaglio(cctc);
    }

    protected void cmdChiudiLista_Click(object sender, EventArgs e)
    {
        multiView.ActiveViewIndex = 0;
    }

    #endregion

    #region Scheda

    protected void cmdSalva_Click(object sender, EventArgs e)
    {
        CCTabellaCaratterist cctc = new CCTabellaCaratterist();

        try
        {
            cctc.Idcomune = AuthenticationInfo.IdComune;
            cctc.Software = Software;
            cctc.Id = IsInserting ? (int?)null : Convert.ToInt32(lblCodice.Text);
            cctc.Descrizione = txtDescrizione.Text;
            cctc.Perc = String.IsNullOrEmpty(txtPerc.Text) ? (double?)null : txtPerc.ValoreDouble;

            CCTabellaCaratteristMgr mgr = new CCTabellaCaratteristMgr(AuthenticationInfo.CreateDatabase());

            if(IsInserting)
                cctc = mgr.Insert(cctc);
            else
                cctc = mgr.Update(cctc);

            BindDettaglio(cctc);
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
            CCTabellaCaratterist cctc = new CCTabellaCaratterist();

            cctc.Idcomune = AuthenticationInfo.IdComune;
            cctc.Software = Software;
            cctc.Id = IsInserting ? (int?)null : Convert.ToInt32(lblCodice.Text);

            CCTabellaCaratteristMgr mgr = new CCTabellaCaratteristMgr(AuthenticationInfo.CreateDatabase());

            mgr.Delete(cctc);

            multiView.ActiveViewIndex = 0;
        }
        catch (Exception ex)
        {
            MostraErrore("Errore durante l'eliminazione: " + ex.Message, ex);
        }
    }

    #endregion

}
