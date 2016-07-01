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
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using Init.SIGePro.Authentication;
using SIGePro.Net;
using SIGePro.Net.Navigation;
using Init.SIGePro.Exceptions;
using System.Collections.Generic;
using PersonalLib2.Sql;

public partial class Archivi_CalcoloOneri_CostoCostruzione_CCConfigurazione : BasePage
{
	protected void Page_Load(object sender, EventArgs e)
	{
		if (!Page.IsPostBack)
		{
			BindCombo();
			CCConfigurazione ccc = new CCConfigurazioneMgr(AuthenticationInfo.CreateDatabase()).GetById(AuthenticationInfo.IdComune, Software);
			BindDettaglio(ccc);
		}
	}

	private void BindCombo()
	{
		

		CCTipiSuperficie ccts = new CCTipiSuperficie();
		ccts.Idcomune = AuthenticationInfo.IdComune;
		ccts.Software = Software;

		TipiCausaliOneri tco = new TipiCausaliOneri();
		tco.Idcomune = AuthenticationInfo.IdComune;
		tco.Software = Software;
		tco.CoDisabilitato = 0;
		tco.CoSerichiedeendo = "0";
		tco.OrderBy = "CO_ORDINAMENTO";

		TipiAree ta = new TipiAree();
		ta.Idcomune = AuthenticationInfo.IdComune;
		ta.Software = Software;

		ddlTab1FkTsId.Item.DataSource = new CCTipiSuperficieMgr( Database ).GetList(ccts);
		ddlTab1FkTsId.Item.DataBind();
		ddlTab1FkTsId.Item.Items.Insert(0, "");

		ddlTab2FkTsId.Item.DataSource = new CCTipiSuperficieMgr( Database ).GetList(ccts);
		ddlTab2FkTsId.Item.DataBind();
		ddlTab2FkTsId.Item.Items.Insert(0, "");

		ddlArt9SuFkTsId.Item.DataSource = new CCTipiSuperficieMgr( Database ).GetList(ccts);
		ddlArt9SuFkTsId.Item.DataBind();
		ddlArt9SuFkTsId.Item.Items.Insert(0, "");

		ddlArt9SaFkTsId.Item.DataSource = new CCTipiSuperficieMgr( Database ).GetList(ccts);
		ddlArt9SaFkTsId.Item.DataBind();
		ddlArt9SaFkTsId.Item.Items.Insert(0, "");

		ddlFkCoId.Item.DataSource = new TipiCausaliOneriMgr( Database ).GetList(tco);
		ddlFkCoId.Item.DataBind();
		ddlFkCoId.Item.Items.Insert(0, "");

		ddlFkTipiAreeCodice.Item.DataSource = new TipiAreeMgr( Database ).GetList(ta);
		ddlFkTipiAreeCodice.Item.DataBind();
		ddlFkTipiAreeCodice.Item.Items.Insert(0, "");

		// Leggo la lista di settori registrata nella sezione "Altre configurazioni"
		CCConfigurazioneSettori filtroSettori = new CCConfigurazioneSettori();
		filtroSettori.Idcomune = IdComune;
		filtroSettori.Software = Software;
		filtroSettori.UseForeign = useForeignEnum.Yes;

		ddlSettori.Item.DataSource = new CCConfigurazioneSettoriMgr(Database).GetList(filtroSettori);
		ddlSettori.Item.DataBind();
		ddlSettori.Item.Items.Insert(0, "");


		// Tipi di imputazione
		List<KeyValuePair<int, string>> listaTipiImputazioni = new List<KeyValuePair<int, string>>();
		listaTipiImputazioni.Add(new KeyValuePair<int, string>(CCConfigurazione.CALCSUP_DETTAGLIUI, "Imputazione dettagliata UI"));
		listaTipiImputazioni.Add(new KeyValuePair<int, string>(CCConfigurazione.CALCSUP_MODELLO, "Imputazione dati sul modello"));

		ddlUsaDettaglioSup.Item.DataSource = listaTipiImputazioni;
		ddlUsaDettaglioSup.Item.DataBind();
	}

	private void BindDettaglio(CCConfigurazione ccc)
	{
		if (ccc != null)
		{
			if (ddlTab1FkTsId.Item.Items.Count > 0)
                ddlTab1FkTsId.Item.SelectedValue = (ccc.Tab1FkTsId.GetValueOrDefault(int.MinValue) == int.MinValue) ? "" : ccc.Tab1FkTsId.ToString();
			
			if (ddlTab2FkTsId.Item.Items.Count > 0)
                ddlTab2FkTsId.Item.SelectedValue = (ccc.Tab2FkTsId.GetValueOrDefault(int.MinValue) == int.MinValue) ? "" : ccc.Tab2FkTsId.ToString();
			
			if (ddlArt9SuFkTsId.Item.Items.Count > 0)
                ddlArt9SuFkTsId.Item.SelectedValue = (ccc.Art9suFkTsId.GetValueOrDefault(int.MinValue) == int.MinValue) ? "" : ccc.Art9suFkTsId.ToString();
			
			if (ddlArt9SaFkTsId.Item.Items.Count > 0)
                ddlArt9SaFkTsId.Item.SelectedValue = (ccc.Art9saFkTsId.GetValueOrDefault(int.MinValue) == int.MinValue) ? "" : ccc.Art9saFkTsId.ToString();
			
			if (ddlFkCoId.Item.Items.Count > 0)
                ddlFkCoId.Item.SelectedValue = (ccc.FkCoId.GetValueOrDefault(int.MinValue) == int.MinValue) ? ddlFkCoId.Item.Items[0].Value : ccc.FkCoId.ToString();
			
			if (ddlFkTipiAreeCodice.Item.Items.Count > 0)
                ddlFkTipiAreeCodice.Item.SelectedValue = (ccc.FkTipiareeCodice.GetValueOrDefault(int.MinValue) == int.MinValue) ? "" : ccc.FkTipiareeCodice.ToString();

			if (ddlSettori.Item.Items.Count > 0)
				ddlSettori.Value = String.IsNullOrEmpty(ccc.FkSeCodicesettore) ? "" : ccc.FkSeCodicesettore;

			try
			{
                ddlUsaDettaglioSup.Value = (ccc.Usadettagliosup.GetValueOrDefault(int.MinValue) == int.MinValue || ccc.Usadettagliosup == 1) ? "0" : ccc.Usadettagliosup.ToString();
			}
			catch (Exception ex)
			{
				ddlUsaDettaglioSup.Value = "0";
			}
		}

	}
	protected void cmdSalva_Click(object sender, EventArgs e)
	{
		CCConfigurazione ccc = new CCConfigurazione();

		try
		{
			ccc.Idcomune = AuthenticationInfo.IdComune;
			ccc.Software = Software;
            ccc.Tab1FkTsId = (string.IsNullOrEmpty(ddlTab1FkTsId.Item.SelectedValue)) ? (int?)null : Convert.ToInt32(ddlTab1FkTsId.Item.SelectedValue);
            ccc.Tab2FkTsId = (string.IsNullOrEmpty(ddlTab2FkTsId.Item.SelectedValue)) ? (int?)null : Convert.ToInt32(ddlTab2FkTsId.Item.SelectedValue);
            ccc.Art9suFkTsId = (string.IsNullOrEmpty(ddlArt9SuFkTsId.Item.SelectedValue)) ? (int?)null : Convert.ToInt32(ddlArt9SuFkTsId.Item.SelectedValue);
            ccc.Art9saFkTsId = (string.IsNullOrEmpty(ddlArt9SaFkTsId.Item.SelectedValue)) ? (int?)null : Convert.ToInt32(ddlArt9SaFkTsId.Item.SelectedValue);
            ccc.FkCoId = (string.IsNullOrEmpty(ddlFkCoId.Item.SelectedValue)) ? (int?)null : Convert.ToInt32(ddlFkCoId.Item.SelectedValue);
            ccc.FkTipiareeCodice = string.IsNullOrEmpty(ddlFkTipiAreeCodice.Item.SelectedValue) ? (int?)null : Convert.ToInt32(ddlFkTipiAreeCodice.Item.SelectedValue);
			ccc.Usadettagliosup = Convert.ToInt32(ddlUsaDettaglioSup.Value);
			ccc.FkSeCodicesettore = ddlSettori.Value;

			CCConfigurazioneMgr mgr = new CCConfigurazioneMgr(AuthenticationInfo.CreateDatabase());

			mgr.Save(ccc);

			BindDettaglio(ccc);
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
		base.CloseCurrentPage();
	}
	protected void multiView_ActiveViewChanged(object sender, EventArgs e)
	{
		Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;
	}
	protected void cmdAltriDati_Click(object sender, EventArgs e)
	{
		Response.Redirect("CCConfigurazioneSettori.aspx?software=" + Software + "&token=" + Token);
	}
}
