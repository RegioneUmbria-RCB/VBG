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
using Init.Utils;

public partial class Archivi_CalcoloOneri_CostoCostruzione_CCTabella3 : BasePage
{
    protected void Page_Load(object sender, EventArgs e)
    {
        ImpostaScriptEliminazione(cmdElimina);

		if (!IsPostBack)
		{
			CCDettagliSuperficie filtro = new CCDettagliSuperficie();
			filtro.Idcomune = IdComune;
			filtro.Software = Software;
			filtro.OrderBy = "Descrizione asc";

			ddlDettaglioSuperficie.Item.DataSource = new CCDettagliSuperficieMgr(Database).GetList(filtro);
			ddlDettaglioSuperficie.Item.DataBind();

			ddlDettaglioSuperficie.Item.Items.Insert(0, new ListItem(String.Empty, String.Empty));
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

    private void BindDettaglio(CCTabella3 cct3)
    {
        this.IsInserting = cct3.Id == null;

        lblCodice.Text = IsInserting ? "Nuovo" : cct3.Id.ToString();
        
        txtDescrizione.Text = IsInserting ? String.Empty : cct3.Descrizione;

        txtDa.Text = (IsInserting || cct3.RapportoSuSnrDa.GetValueOrDefault(int.MinValue) == int.MinValue) ? String.Empty : cct3.RapportoSuSnrDa.ToString();
        txtA.Text = (IsInserting || cct3.RapportoSuSnrA.GetValueOrDefault(int.MinValue) == int.MinValue) ? String.Empty : cct3.RapportoSuSnrA.ToString();
        txtPerc.Text = (IsInserting || DoubleChecker.IsDoubleEmpty( cct3.Perc ) ) ? String.Empty : cct3.Perc.ToString();

		ddlDettaglioSuperficie.Value = cct3.FkCcDsId.GetValueOrDefault(int.MinValue) == int.MinValue ? String.Empty : cct3.FkCcDsId.ToString();

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
        BindDettaglio(new CCTabella3());
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

        CCTabella3 cct3 = new CCTabella3Mgr(AuthenticationInfo.CreateDatabase()).GetById(AuthenticationInfo.IdComune, id);

        BindDettaglio(cct3);
    }

    protected void cmdChiudiLista_Click(object sender, EventArgs e)
    {
        multiView.ActiveViewIndex = 0;
    }

    #endregion

    #region Scheda

    protected void cmdSalva_Click(object sender, EventArgs e)
    {
        CCTabella3 cct3 = new CCTabella3();

        try
        {
            cct3.Idcomune = AuthenticationInfo.IdComune;
            cct3.Software = Software;
            cct3.Id = IsInserting ? (int?)null : Convert.ToInt32(lblCodice.Text);
            cct3.Descrizione = txtDescrizione.Text;
			cct3.RapportoSuSnrDa = txtDa.ValoreInt;
			cct3.RapportoSuSnrA = txtA.ValoreInt;
            cct3.Perc = txtPerc.ValoreDouble;
			cct3.FkCcDsId = String.IsNullOrEmpty(ddlDettaglioSuperficie.Value) ? (int?)null : Convert.ToInt32(ddlDettaglioSuperficie.Value);

            CCTabella3Mgr mgr = new CCTabella3Mgr(AuthenticationInfo.CreateDatabase());

            if (IsInserting)
                cct3 = mgr.Insert(cct3);
            else
                cct3 = mgr.Update(cct3);

            BindDettaglio(cct3);
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
            CCTabella3 cct3 = new CCTabella3();

            cct3.Idcomune = AuthenticationInfo.IdComune;
            cct3.Software = Software;
            cct3.Id = IsInserting ? (int?)null : Convert.ToInt32(lblCodice.Text);

            CCTabella3Mgr mgr = new CCTabella3Mgr(AuthenticationInfo.CreateDatabase());

            mgr.Delete(cct3);

            multiView.ActiveViewIndex = 0;
        }
        catch (Exception ex)
        {
            MostraErrore("Errore durante l'eliminazione: " + ex.Message, ex);
        }
    }

    #endregion

}
