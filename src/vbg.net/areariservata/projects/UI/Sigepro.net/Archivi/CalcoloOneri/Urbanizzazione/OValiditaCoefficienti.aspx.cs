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

public partial class Archivi_CalcoloOneri_Urbanizzazione_OValiditaCoefficienti : BasePage
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
                OValiditaCoefficienti ovc = new OValiditaCoefficientiMgr(AuthenticationInfo.CreateDatabase()).GetById(AuthenticationInfo.IdComune, Id.GetValueOrDefault(int.MinValue));
                BindDettaglio(ovc);
            }
        }
	}

	protected void cmdCerca_Click(object sender, EventArgs e)
	{
		gvLista.DataBind();

		multiView.ActiveViewIndex = 1;
	}

	protected void gvLista_SelectedIndexChanged(object sender, EventArgs e)
	{
		int id = Convert.ToInt32(gvLista.DataKeys[gvLista.SelectedIndex].Value);

		OValiditaCoefficienti ovc = new OValiditaCoefficientiMgr(AuthenticationInfo.CreateDatabase()).GetById(AuthenticationInfo.IdComune, id);

		BindDettaglio(ovc);
	}

	private void BindDettaglio(OValiditaCoefficienti ovc)
	{
        this.IsInserting = ovc.Id.GetValueOrDefault(int.MinValue) == int.MinValue;

		lblCodice.Text = IsInserting ? "Nuovo" : ovc.Id.ToString();
		txtDescrizione.Text = ovc.Descrizione;
		txtDataInizioValidita.DateValue = IsInserting ? (DateTime?)null : ovc.Datainiziovalidita;

        cmdElimina.Visible = cmdTabellaABC.Visible = cmdTabellaD.Visible = !IsInserting;

		multiView.ActiveViewIndex = 2;
	}

	protected void cmdCloseList_Click(object sender, EventArgs e)
	{
		multiView.ActiveViewIndex = 0;
	}

	protected void cmdNuovo_Click(object sender, EventArgs e)
	{
		BindDettaglio(new OValiditaCoefficienti());
	}

	protected void cmdChiudiDettaglio_Click(object sender, EventArgs e)
	{
		multiView.ActiveViewIndex = 0;
	}

	protected void cmdSalva_Click(object sender, EventArgs e)
	{
		OValiditaCoefficienti ovc = new OValiditaCoefficienti();
        
		try
		{
			ovc.Idcomune = AuthenticationInfo.IdComune;
			ovc.Software = Software;
			ovc.Id = IsInserting ? (int?)null : Convert.ToInt32(lblCodice.Text);
			ovc.Descrizione = txtDescrizione.Text;
			ovc.Datainiziovalidita = txtDataInizioValidita.DateValue;

			OValiditaCoefficientiMgr mgr = new OValiditaCoefficientiMgr(AuthenticationInfo.CreateDatabase());

			if (IsInserting)
				ovc = mgr.Insert(ovc);
			else
				ovc = mgr.Update(ovc);

			BindDettaglio(ovc);
        }
        catch (RequiredFieldException rfe)
        {
            MostraErrore("Attenzione, i campi contrassegnati con un asterisco sono obbligatori.", rfe);
        }
		catch (Exception ex)
		{
			MostraErrore("Errore durante il salvataggio: " + ex.Message , ex);
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
			OValiditaCoefficienti ovc = new OValiditaCoefficienti();

			ovc.Idcomune = AuthenticationInfo.IdComune;
			ovc.Software = Software;
			ovc.Id = IsInserting ? (int?)null : Convert.ToInt32(lblCodice.Text);

			OValiditaCoefficientiMgr mgr = new OValiditaCoefficientiMgr(AuthenticationInfo.CreateDatabase());

			mgr.Delete(ovc);

			multiView.ActiveViewIndex = 0;
		}
		catch (Exception ex)
		{
			MostraErrore("Errore durante l'eliminazione: " + ex.Message, ex);
		}
	}

    #region Links

    protected void cmdTabellaD_Click(object sender, EventArgs e)
    {
        Response.Redirect("OTabellaD.aspx?token=" + Token + "&software=" + Software + "&FkOvcId=" + lblCodice.Text);
    }

    protected void cmdTabellaABC_Click(object sender, EventArgs e)
    {
        Response.Redirect("OTabellaABC.aspx?token=" + Token + "&software=" + Software + "&FkOvcId=" + lblCodice.Text);
    }

    #endregion

}
