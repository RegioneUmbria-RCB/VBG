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
using Init.Utils;

public partial class Archivi_CalcoloOneri_Urbanizzazione_OIndiciTerritoriali : BasePage
{
    protected void Page_Load(object sender, EventArgs e)
    {
        ImpostaScriptEliminazione(cmdElimina);

        if(!Page.IsPostBack)
            gvLista.DataBind();
    }

    private void BindDettaglio(OIndiciTerritoriali cls)
    {
        IsInserting = cls.Id.GetValueOrDefault(int.MinValue) == int.MinValue;

        lblCodice.Text = IsInserting ? "Nuovo" : cls.Id.ToString();
        txtDTZ.ValoreDouble = cls.Dtz.GetValueOrDefault(double.MinValue);
        txtIFF.ValoreDouble = cls.Iff.GetValueOrDefault(double.MinValue);
        txtIFT.ValoreDouble = cls.Ift.GetValueOrDefault(double.MinValue);
        txtDescrizione.Text = cls.Descrizione;

        // gestione bottoni
        cmdElimina.Visible = !IsInserting;

        multiView.ActiveViewIndex = 1;
    }

    #region Lista
    protected void gvLista_SelectedIndexChanged(object sender, EventArgs e)
    {
        OIndiciTerritorialiMgr mgr = new OIndiciTerritorialiMgr(AuthenticationInfo.CreateDatabase());

        int id = Convert.ToInt32(gvLista.DataKeys[gvLista.SelectedIndex][0]);

        OIndiciTerritoriali cls = mgr.GetById(AuthenticationInfo.IdComune, id);

        BindDettaglio(cls);
    }
    protected void cmdCloseList_Click(object sender, EventArgs e)
    {
		base.CloseCurrentPage(); 
    }
    protected void cmdNuovo_Click(object sender, EventArgs e)
    {
        BindDettaglio(new OIndiciTerritoriali());
    }
    protected void gvLista_RowDataBound(object sender, GridViewRowEventArgs e)
    {
        if (e.Row.RowType == DataControlRowType.DataRow)
        {
            Label lblDTZ = e.Row.FindControl("lblDTZ") as Label;
            lblDTZ.Text = (e.Row.DataItem as OIndiciTerritoriali).Dtz.GetValueOrDefault(double.MinValue) != double.MinValue ? (e.Row.DataItem as OIndiciTerritoriali).Dtz.GetValueOrDefault(double.MinValue).ToString("N2") : String.Empty;

            Label lblIFT = e.Row.FindControl("lblIFT") as Label;
            lblIFT.Text = (e.Row.DataItem as OIndiciTerritoriali).Ift.GetValueOrDefault(double.MinValue) != double.MinValue ? (e.Row.DataItem as OIndiciTerritoriali).Ift.GetValueOrDefault(double.MinValue).ToString("N2") : String.Empty;

            Label lblIFF = e.Row.FindControl("lblIFF") as Label;
            lblIFF.Text = (e.Row.DataItem as OIndiciTerritoriali).Iff.GetValueOrDefault(double.MinValue) != double.MinValue ? (e.Row.DataItem as OIndiciTerritoriali).Iff.GetValueOrDefault(double.MinValue).ToString("N2") : String.Empty;
        }
    }
    #endregion

    #region Scheda
    protected void cmdSalva_Click(object sender, EventArgs e)
    {
        OIndiciTerritoriali cls = new OIndiciTerritoriali();

        cls.Software = Software;
        cls.Idcomune = AuthenticationInfo.IdComune;

        if (!IsInserting)
            cls.Id = int.Parse(lblCodice.Text);

        cls.Dtz = txtDTZ.ValoreDouble;
        cls.Iff = txtIFF.ValoreDouble;
        cls.Ift = txtIFT.ValoreDouble;

		cls.Descrizione = txtDescrizione.Text;

		if (String.IsNullOrEmpty( cls.Descrizione ))
            cls.Descrizione = "DTZ: " + (DoubleChecker.IsDoubleEmpty(cls.Dtz) ? "" : cls.Dtz.GetValueOrDefault(double.MinValue).ToString("N2")) + ", IFT: " + (DoubleChecker.IsDoubleEmpty(cls.Ift) ? "" : cls.Ift.GetValueOrDefault(double.MinValue).ToString("N2")) + ", IFF: " + (DoubleChecker.IsDoubleEmpty(cls.Iff) ? "" : cls.Iff.GetValueOrDefault(double.MinValue).ToString("N2"));

        OIndiciTerritorialiMgr mgr = new OIndiciTerritorialiMgr(AuthenticationInfo.CreateDatabase());

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
            MostraErrore("Errore durante il salvataggio: " + ex.Message, ex);
        }
    }
    protected void cmdElimina_Click(object sender, EventArgs e)
    {
        OIndiciTerritorialiMgr mgr = new OIndiciTerritorialiMgr(AuthenticationInfo.CreateDatabase());
        OIndiciTerritoriali cls = mgr.GetById(AuthenticationInfo.IdComune, int.Parse(lblCodice.Text));

        try
        {
            mgr.Delete(cls);
        }
        catch (Exception ex)
        {
            MostraErrore("Errore durante il salvataggio: " + ex.Message, ex);
        }
        gvLista.DataBind();
        multiView.ActiveViewIndex = 0;
    }
    protected void cmdChiudiDettaglio_Click(object sender, EventArgs e)
    {
        gvLista.DataBind();
        multiView.ActiveViewIndex = 0;
    }
    #endregion

    protected void multiView_ActiveViewChanged(object sender, EventArgs e)
    {
        switch (multiView.ActiveViewIndex)
        {
            case (1):
                Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;
                return;
            default:
                Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Risultato;
                return;
        }
    }
}
