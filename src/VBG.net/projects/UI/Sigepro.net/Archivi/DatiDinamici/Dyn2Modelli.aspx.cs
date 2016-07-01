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
using SIGePro.Net;
using SIGePro.Net.Navigation;
using Init.SIGePro.Manager;
using Init.SIGePro.Exceptions.IstanzeAllegati;
using Init.SIGePro.Manager.Logic.DatiDinamici;
using Init.SIGePro.DatiDinamici;
using System.Collections.Generic;
using Init.SIGePro.Manager.Logic.Ricerche;
using SIGePro.WebControls.Ajax;
using Init.SIGePro.DatiDinamici.Contesti;


public partial class Archivi_DatiDinamici_Dyn2Modelli : BasePage
{
	public string IdModello
	{
		get { return Request.QueryString["IdModello"]; }
	}

	protected void Page_Load(object sender, EventArgs e)
	{
		ImpostaScriptEliminazione(cmdElimina);

		if (!IsPostBack)
		{
			foreach (var key in DatiDinamiciInfoDictionary.Items.Keys)
			{
				ddlSrcContesto.Item.Items.Add(new ListItem(DatiDinamiciInfoDictionary.Items[key].Descrizione, ContestoTranslator.ContestoEnumToContestoBase(key)));
				ddlContesto.Item.Items.Add(new ListItem(DatiDinamiciInfoDictionary.Items[key].Descrizione, ContestoTranslator.ContestoEnumToContestoBase(key)));
			}

			ddlSrcContesto.Item.Items.Insert(0, new ListItem("", ""));

			if (!String.IsNullOrEmpty(IdModello))
			{
				BindDettaglio(new Dyn2ModelliTMgr(Database).GetById(IdComune, Convert.ToInt32(IdModello)));
			}
		}
	}

	private void BindDettaglio(Dyn2ModelliT cls)
	{
		multiView.ActiveViewIndex = 2;

		this.IsInserting = cls.Id.GetValueOrDefault(int.MinValue) == int.MinValue;

		lblId.Item.Text = IsInserting ? "Nuovo" : cls.Id.ToString();
		txtDescrizione.Value = cls.Descrizione;
		ddlContesto.Value = cls.FkD2bcId;
		chkMultiplo.Item.Checked = cls.Modellomultiplo.GetValueOrDefault(0) == 1;
		chkStoricizza.Item.Checked = cls.FlgStoricizza.GetValueOrDefault(0) == 1;
		chkReadOnly.Item.Checked = cls.FlgReadonlyWeb.GetValueOrDefault(0) == 1;
		cmdGestioneCampi.Visible = cmdFormule.Visible = cmdElimina.Visible = !IsInserting;
		txtCodiceScheda.Value = cls.CodiceScheda;

		chkMultiplo.Visible = true;
		/*
		bool esistonoRigheMultiple = new Dyn2ModelliTMgr(Database).VerificaEsistenzaRigheMultiple(IdComune, Convert.ToInt32(IdModello));

		if (esistonoRigheMultiple)
		{
			chkMultiplo.Visible = false;
		}*/
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

	#region Scheda ricerca
	public void cmdCerca_Click(object sender, EventArgs e)
	{
		gvLista.DataBind();

		multiView.ActiveViewIndex = 1;
	}

	public void cmdNuovo_Click(object sender, EventArgs e)
	{
		BindDettaglio(new Dyn2ModelliT());
	}

	public void cmdNuovoComeCopia_Click(object sender, EventArgs e)
	{
		chkCopiaFormule.Item.Checked = true;
		multiView.ActiveViewIndex = 3;
	}

	public void cmdChiudi_Click(object sender, EventArgs e)
	{
		base.CloseCurrentPage();
	}
	#endregion


	#region Scheda lista
	public void cmdChiudiLista_Click(object sender, EventArgs e)
	{
		multiView.ActiveViewIndex = 0;
	}

	protected void gvLista_SelectedIndexChanged(object sender, EventArgs e)
	{
		string id = gvLista.DataKeys[gvLista.SelectedIndex].Value.ToString();

		string fmtUrl = "~/Archivi/DatiDinamici/Dyn2Modelli.aspx?Token={0}&Software={1}&IdModello={2}";

		Response.Redirect(string.Format(fmtUrl, Token, Software, id));
	}

	public string TraduciContesto(object contesto)
	{
		if (contesto == null || string.IsNullOrEmpty(contesto.ToString())) return "Tutti";

		var contestoEnum = ContestoTranslator.ContestoBaseToContestoEnum( contesto.ToString());

		return DatiDinamiciInfoDictionary.Items[contestoEnum].Descrizione;
	}
	#endregion


	#region Scheda dettaglio
	protected void cmdSalva_Click(object sender, EventArgs e)
	{
		Dyn2ModelliTMgr mgr = new Dyn2ModelliTMgr(Database);

		Dyn2ModelliT cls = null;

		if (IsInserting)
		{
			cls = new Dyn2ModelliT();
			cls.Idcomune = IdComune;
			cls.Software = Software;
		}
		else
		{
			int id = Convert.ToInt32(lblId.Item.Text);

			cls = mgr.GetById(IdComune, id);
		}

		try
		{
			cls.Descrizione = txtDescrizione.Value;
			cls.FkD2bcId = String.IsNullOrEmpty(ddlContesto.Value) ? (string)null : ddlContesto.Value;
			cls.Modellomultiplo = chkMultiplo.Item.Checked ? 1 : 0;
			cls.FlgStoricizza = chkStoricizza.Item.Checked ? 1 : 0;
			cls.FlgReadonlyWeb = chkReadOnly.Item.Checked ? 1 : 0;
			cls.CodiceScheda = txtCodiceScheda.Value.ToUpper();

			if (IsInserting)
				cls = mgr.Insert(cls);
			else
				cls = mgr.Update(cls);

			BindDettaglio(cls);
		}
		catch (RequiredFieldException rfe)
		{
			MostraErrore("Attenzione, i campi contrassegnati con un asterisco sono obbligatori.", rfe);
		}
		catch (Exception ex)
		{
			MostraErrore(IsInserting ? AmbitoErroreEnum.Inserimento : AmbitoErroreEnum.Aggiornamento, ex);
		}
	}

	protected void cmdElimina_Click(object sender, EventArgs e)
	{
		Dyn2ModelliTMgr mgr = new Dyn2ModelliTMgr(Database);

		int id = Convert.ToInt32(lblId.Item.Text);

		Dyn2ModelliT cls = mgr.GetById(IdComune, id);

		try
		{
			mgr.Delete(cls);

			multiView.ActiveViewIndex = 0;
		}
		catch (Exception ex)
		{
			MostraErrore(AmbitoErroreEnum.Cancellazione, ex);
		}
	}

	protected void cmdChiudiDettaglio_Click(object sender, EventArgs e)
	{
		string fmtUrl = "~/Archivi/DatiDinamici/Dyn2Modelli.aspx?Token={0}&Software={1}";

		Response.Redirect(string.Format(fmtUrl, Token, Software));
	}

	protected void cmdFormule_Click(object sender, EventArgs e)
	{
		string id = lblId.Value;

		string fmtUrl = "~/Archivi/DatiDinamici/Dyn2ModelliFormule.aspx?Token={0}&IdModello={1}";

		Response.Redirect(string.Format(fmtUrl, Token, id));
	}

	protected void cmdGestioneCampi_Click(object sender, EventArgs e)
	{
		string id = lblId.Value;

		string fmtUrl = "~/Archivi/DatiDinamici/Dyn2ModelliCampi.aspx?Token={0}&IdModello={1}";

		Response.Redirect(string.Format(fmtUrl, Token, id));
	}
	#endregion

	#region Scheda copia da

	#region metodi di ricerca di ricercheplus
	[System.Web.Services.WebMethodAttribute(), System.Web.Script.Services.ScriptMethodAttribute()]
	public static string[] GetCompletionList(string token, string dataClassType,
											  string targetPropertyName,
											  string descriptionPropertyNames,
											  string prefixText,
											  int count,
											  string software,
											  bool ricercaSoftwareTT,
											  Dictionary<string, string> initParams)
	{
		try
		{
			RicerchePlusSearchComponent sc = new RicerchePlusSearchComponent(token, dataClassType, targetPropertyName, descriptionPropertyNames, prefixText, count, software, ricercaSoftwareTT, initParams);

			return RicerchePlusCtrl.CreateResultList(sc.Find(true));
		}
		catch (Exception ex)
		{
			return RicerchePlusCtrl.CreateErrorResult(ex);
		}
	}

	#endregion


	public void cmdConfermaCopia_Click(object sender, EventArgs e)
	{
		try
		{
			var mgr = new Dyn2ModelliTMgr(Database);

			var idSchedaDaCopiare= Convert.ToInt32( rplModelloDinamico.Value );
			var copiaFormule = chkCopiaFormule.Item.Checked;


			int nuovoId = mgr.CopiaScheda(IdComune, idSchedaDaCopiare, copiaFormule);

			BindDettaglio(new Dyn2ModelliTMgr(Database).GetById(IdComune, Convert.ToInt32(nuovoId)));
		}
		catch (Exception ex)
		{
			MostraErrore(AmbitoErroreEnum.Inserimento, ex);
		}
	}

	#endregion




}
