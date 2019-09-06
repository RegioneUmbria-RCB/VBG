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

public partial class Archivi_CalcoloOneri_Urbanizzazione_OClassiAddetti : BasePage
{
	protected void Page_Load(object sender, EventArgs e)
	{
		ImpostaScriptEliminazione(cmdElimina);
    }

    private void BindDettaglio(OClassiAddetti cls)
    {
        IsInserting = cls.Id.GetValueOrDefault(int.MinValue) == int.MinValue;

        lblCodice.Text = IsInserting ? "Nuovo" : cls.Id.ToString();
        txtClasse.Text = cls.Classe;

        // gestione bottoni
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
        BindDettaglio(new OClassiAddetti());
    }

    protected void cmdChiudi_Click(object sender, EventArgs e)
    {
		base.CloseCurrentPage(); 
    }


    #endregion

    #region Lista

    protected void gvLista_SelectedIndexChanged(object sender, EventArgs e)
	{
		OClassiAddettiMgr mgr = new OClassiAddettiMgr(AuthenticationInfo.CreateDatabase());

		int id = Convert.ToInt32( gvLista.DataKeys[gvLista.SelectedIndex][0] );

		OClassiAddetti cls = mgr.GetById(AuthenticationInfo.IdComune, id);

		BindDettaglio(cls);
    }

    protected void cmdCloseList_Click(object sender, EventArgs e)
    {
        multiView.ActiveViewIndex = 0;
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

    #endregion

    #region Scheda

    protected void cmdSalva_Click(object sender, EventArgs e)
	{
		OClassiAddetti cls = new OClassiAddetti();

		cls.Software = Software;
		cls.Idcomune = AuthenticationInfo.IdComune;

		if (!IsInserting)
			cls.Id = int.Parse(lblCodice.Text);

		cls.Classe = txtClasse.Text;
        cls.Ordinamento = txtOrdinamento.ValoreInt;

		OClassiAddettiMgr mgr = new OClassiAddettiMgr(AuthenticationInfo.CreateDatabase());

		try
		{
			if (IsInserting)
				mgr.Insert(cls);
			else
				mgr.Update(cls);

            BindDettaglio(cls);
		}
        catch (RequiredFieldException rfe)
        {
            MostraErrore("Attenzione, i campi contrassegnati con un asterisco sono obbligatori.", rfe);
        }
		catch (Exception ex)
		{
			MostraErrore("Errore durante il salvataggio: " + ex.Message , ex );
		}
	}

	protected void cmdElimina_Click(object sender, EventArgs e)
	{
		OClassiAddettiMgr mgr = new OClassiAddettiMgr( AuthenticationInfo.CreateDatabase() );

		OClassiAddetti cls = mgr.GetById( AuthenticationInfo.IdComune , int.Parse(lblCodice.Text) );

		try
		{
			mgr.Delete(cls);
		}
		catch (Exception ex)
		{
			MostraErrore("Errore durante il salvataggio: " + ex.Message , ex );
		}

		multiView.ActiveViewIndex = 0;
	}
	protected void cmdChiudiDettaglio_Click(object sender, EventArgs e)
	{
		multiView.ActiveViewIndex = 0;
    }

    #endregion

}
