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

public partial class Archivi_CalcoloOneri_CostoCostruzione_CCTabellaClassiEdificio : BasePage
{
    protected void Page_Load(object sender, EventArgs e)
    {
        ImpostaScriptEliminazione(cmdElimina);
    }

    protected void cmdCerca_Click(object sender, EventArgs e)
    {
        gvLista.DataBind();

        multiView.ActiveViewIndex = 1;
    }
    protected void gvLista_SelectedIndexChanged(object sender, EventArgs e)
    {
        int id = Convert.ToInt32(gvLista.DataKeys[gvLista.SelectedIndex].Value);

        CCTabellaClassiEdificio cctce = new CCTabellaClassiEdificioMgr(AuthenticationInfo.CreateDatabase()).GetById(AuthenticationInfo.IdComune, id);

        BindDettaglio(cctce);
    }

    private void BindDettaglio(CCTabellaClassiEdificio cctce)
    {
        this.IsInserting = cctce.Id.GetValueOrDefault(int.MinValue) == int.MinValue;

        lblId.Text = IsInserting ? "Nuovo" : cctce.Id.ToString();
        txtDescrizione.Text = cctce.Descrizione;
        txtDa.ValoreInt = cctce.Da;
        txtA.ValoreInt = cctce.A;
        txtMaggiorazione.ValoreDouble = cctce.Maggiorazione.GetValueOrDefault(double.MinValue);

        cmdElimina.Visible = !IsInserting;

        multiView.ActiveViewIndex = 2;
    }
    protected void cmdCloseList_Click(object sender, EventArgs e)
    {
        multiView.ActiveViewIndex = 0;
    }
    protected void cmdNuovo_Click(object sender, EventArgs e)
    {
        BindDettaglio(new CCTabellaClassiEdificio());
    }
    protected void cmdChiudiDettaglio_Click(object sender, EventArgs e)
    {
        multiView.ActiveViewIndex = 0;
    }
    protected void cmdSalva_Click(object sender, EventArgs e)
    {
        CCTabellaClassiEdificio cctce = new CCTabellaClassiEdificio();

        try
        {
            cctce.Idcomune = AuthenticationInfo.IdComune;
            cctce.Software = Software;
            cctce.Id = IsInserting ? (int?)null : Convert.ToInt32(lblId.Text);
            cctce.Descrizione = txtDescrizione.Text;
			cctce.Da = txtDa.ValoreInt;
			cctce.A = txtA.ValoreInt;
            cctce.Maggiorazione = txtMaggiorazione.ValoreDouble;


            CCTabellaClassiEdificioMgr mgr = new CCTabellaClassiEdificioMgr(AuthenticationInfo.CreateDatabase());

            if (IsInserting)
                cctce = mgr.Insert(cctce);
            else
                cctce = mgr.Update(cctce);

            BindDettaglio(cctce);
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
    protected void cmdChiudi_Click(object sender, EventArgs e)
    {
		base.CloseCurrentPage(); 
    }
    protected void cmdElimina_Click(object sender, EventArgs e)
    {
        try
        {
            CCTabellaClassiEdificio cctce = new CCTabellaClassiEdificio();

            cctce.Idcomune = AuthenticationInfo.IdComune;
            cctce.Id = IsInserting ? (int?)null : Convert.ToInt32(lblId.Text);

            CCTabellaClassiEdificioMgr mgr = new CCTabellaClassiEdificioMgr(AuthenticationInfo.CreateDatabase());

            mgr.Delete(cctce);

            multiView.ActiveViewIndex = 0;
        }
        catch (Exception ex)
        {
            MostraErrore("Errore durante l'eliminazione: " + ex.Message, ex);
        }
    }
}
