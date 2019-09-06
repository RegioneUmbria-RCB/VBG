using System;
using System.Collections.Generic;
using System.Web.UI;
using System.Web.UI.WebControls;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions.IstanzeAllegati;
using Init.SIGePro.Manager;
using Init.SIGePro.Manager.Logic.Ricerche;
using SIGePro.Net;
using SIGePro.WebControls.Ajax;
using System.Xml;
using Init.Utils;
using PersonalLib2.Data;
using System.Web;

public partial class Archivi_DatiDinamici_Dyn2ModelliCampi : BasePage
{
	private static class Constants
	{
		public const string ID_CAMPO_TESTO = "T";
		public const string ID_CAMPO_DATO_DINAMICO = "D";
		public const string QuerystringIdModello = "IdModello";
		public const int ViewIdDettaglio = 1;
		public const int ViewIdRisultato = 0;
	}

	private class CacheModello
	{
		public Dyn2ModelliT GetModello(DataBase db, string idComune, int idModello)
		{
			Dyn2ModelliT modello = (Dyn2ModelliT)HttpContext.Current.Items["Dyn2ModelliT"];

			if (modello == null)
			{
				modello = new Dyn2ModelliTMgr(db).GetById(idComune, idModello);
				HttpContext.Current.Items["Dyn2ModelliT"] = modello;
			}

			return modello;
		}
	}

	protected string ScrollTo { get; set; }

	public override string Software
	{
		get
		{
			return Modello.Software;
		}
	}



	public Dyn2ModelliT Modello
	{
		get 
		{
			var strIdModello = Request.QueryString[Constants.QuerystringIdModello];

			if (String.IsNullOrEmpty(strIdModello))
				throw new ArgumentException("IdModello non valido");

			return new CacheModello().GetModello(Database, IdComune, Convert.ToInt32(strIdModello));
		}
	}

	
    

	protected void Page_Load(object sender, EventArgs e)
	{
		cmdPreview.OnClientClick = "javascript:MostraAnteprima(" + Modello.Id + ");return false;";

		ImpostaScriptEliminazione(cmdElimina);

		if (!IsPostBack)
		{
			ddlTipoCampo.Item.Items.Add( new ListItem( "Testo" , Constants.ID_CAMPO_TESTO ) );
			ddlTipoCampo.Item.Items.Add( new ListItem( "Campo dinamico", Constants.ID_CAMPO_DATO_DINAMICO));

			ddlBaseTipoTesto.Item.DataSource = new Dyn2BaseTipiTestoMgr(Database).GetList(new Dyn2BaseTipiTesto());
			ddlBaseTipoTesto.Item.DataBind();

			rplCampoDinamico.InitParams["idContesto"] = Modello.FkD2bcId;
		}
	}

	private void BindDettaglio(Dyn2ModelliD cls)
	{
		multiView.ActiveViewIndex = Constants.ViewIdDettaglio;

        this.IsInserting = cls.Id.GetValueOrDefault(int.MinValue) == int.MinValue;

		lblId.Item.Text = IsInserting ? "Nuovo" : cls.Id.ToString();

		txtPosOrizzontale.Item.ValoreInt = cls.Posorizzontale;
		txtPosVerticale.Item.ValoreInt = cls.Posverticale;

        ddlTipoCampo.Value =  (cls.FkD2cId.GetValueOrDefault(int.MinValue) == int.MinValue) ? Constants.ID_CAMPO_TESTO : Constants.ID_CAMPO_DATO_DINAMICO;

		// Forzo il cambiamento del valore del tipo di controllo per nascondere e visualizzare il panel giusto
		ddlTipoCampo_ValueChanged(this, EventArgs.Empty);

		// Inizializzazione dei controlli relativi al tipo di controllo legato al modello
		rplCampoDinamico.Class = null;

		ddlBaseTipoTesto.Item.SelectedIndex = 0;

		txtTestoEsteso.Value = String.Empty;

		// Popolamento dei controlli relativi al tipo di controllo legato al modello
		if (ddlTipoCampo.Value == Constants.ID_CAMPO_TESTO)
		{
            if (cls.FkD2mdtId.GetValueOrDefault(int.MinValue) != int.MinValue)
			{
				Dyn2ModelliDTesti testo = new Dyn2ModelliDTestiMgr(Database).GetById(IdComune, cls.FkD2mdtId.GetValueOrDefault(int.MinValue));
				ddlBaseTipoTesto.Value = testo.FkD2bttId;
				txtTestoEsteso.Value = testo.Testo;
			}
		}
		else
		{
            if (cls.FkD2cId.GetValueOrDefault(int.MinValue) != int.MinValue)
                rplCampoDinamico.Class = new Dyn2CampiMgr(Database).GetById(IdComune, cls.FkD2cId.GetValueOrDefault(int.MinValue));
		}

		rplCampoDinamico.Visible	= true;
		ddlTipoCampo.Visible		= true;

		cmdElimina.Visible		= !IsInserting;
		cmdCreaCampo.Visible	= IsInserting;
		
		// Se è parte di nua riga multipla rendo impossibile la modifica dell'indice di riga,
		// del tipo campo e del campo dinamico
		bool multiplo = cls.FlgMultiplo.GetValueOrDefault(0) == 1;

		txtPosVerticale.Enabled =
		ddlTipoCampo.Enabled =
		rplCampoDinamico.Enabled = true;

		if (multiplo)
		{
			txtPosVerticale.Enabled = false;
			ddlTipoCampo.Enabled = false;
			rplCampoDinamico.Enabled = false;
		}

		// Se il modello è stato storicizzato ed è presente in una o più istanze/anagrafiche/attività
		// mostro l'avvertimento che lo storico verrà eliminato
		if (!IsInserting && ddlTipoCampo.Value != Constants.ID_CAMPO_TESTO)
		{
			if (new Dyn2ModelliDMgr(Database).VerificaPresenzaInStorico(IdComune, cls.Id.Value))
			{
				// Registro lo script di richiesta conferma
				string idMessaggio = "dyn2modellicampi.confirm.eliminazione_dastorico";
				string msg = new LayoutTestiBaseMgr(Database).GetValoreTesto(idMessaggio, IdComune, Software);

				string js = "function RichiediConfermaModifica(){return confirm(\"" + msg + "\");}";

				Page.ClientScript.RegisterClientScriptBlock(this.GetType(), "confermaStorico", js, true);

				cmdElimina.OnClientClick = "return RichiediConfermaModifica() && ConfermaEliminazione();";
				cmdSalva.OnClientClick = "return RichiediConfermaModifica();";
			}
		}

		cmdModificaDettagli.Visible = false;

		if (!IsInserting && cls.FkD2cId.HasValue)
		{
			cmdModificaDettagli.Visible = true;
			cmdModificaDettagli.OnClientClick = String.Format("MostraDettaglioCampo({0});return false;", cls.FkD2cId.Value);
		}
	}

    protected void multiView_ActiveViewChanged(object sender, EventArgs e)
	{
		switch (multiView.ActiveViewIndex)
		{
			case (Constants.ViewIdRisultato):
				Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Risultato;
				gvLista.DataBind();
				return;
			case (Constants.ViewIdDettaglio):
				Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;
				return;
			default:
				Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Ricerca;
				return;
		}
	}




	#region Scheda lista
	public void cmdChiudiLista_Click(object sender, EventArgs e)
	{
		string fmtUrl = "~/Archivi/DatiDinamici/Dyn2Modelli.aspx?Token={0}&Software={1}&IdModello={2}";

		Response.Redirect(string.Format(fmtUrl, Token, Modello.Software, Modello.Id));
	}

	protected void gvLista_SelectedIndexChanged(object sender, EventArgs e)
	{
		int id = Convert.ToInt32(gvLista.DataKeys[gvLista.SelectedIndex].Value);

		Dyn2ModelliD cls = new Dyn2ModelliDMgr(Database).GetById(IdComune, id);

		BindDettaglio(cls);
	}
	#endregion


	#region Scheda dettaglio
	
	protected void cmdSalva_Click(object sender, EventArgs e)
	{
		Dyn2ModelliDMgr mgr = new Dyn2ModelliDMgr(Database);
		Dyn2ModelliDTestiMgr mgrTesti = new Dyn2ModelliDTestiMgr(Database);

		Dyn2ModelliD cls = null;

		if (IsInserting)
		{
			cls = new Dyn2ModelliD
			{
				Idcomune = IdComune,
				FkD2mtId = Modello.Id
			};
		}
		else
		{
			int id = Convert.ToInt32(lblId.Item.Text);
			cls = mgr.GetById(IdComune, id);
		}

		try
		{
			// Verifico i valori di posizione orizzontale e verticale
            int posVerticale	= txtPosVerticale.Item.ValoreInt.GetValueOrDefault(int.MinValue);
            int posOrizzontale	= txtPosOrizzontale.Item.ValoreInt.GetValueOrDefault(int.MinValue);

            if (posVerticale == int.MinValue)
                posVerticale = mgr.GetProssimaPosizioneVerticale(IdComune, Modello.Id.GetValueOrDefault(int.MinValue));

            if (posOrizzontale == int.MinValue)
                posOrizzontale = mgr.GetProssimaPosizioneOrizzontale(IdComune, Modello.Id.GetValueOrDefault(int.MinValue), posVerticale);

			cls.Posverticale	= posVerticale;
			cls.Posorizzontale	= posOrizzontale;
			
			// Verifico il tipo di campo
			if (ddlTipoCampo.Value == Constants.ID_CAMPO_TESTO)
			{
				// Se il campo è di tipo testo verifico che all'interno sia contenuto solo testo o un xml valido
				try
				{
					ValidaXmlInInput(txtTestoEsteso.Value);
				}
				catch (Exception ex)
				{
					this.Errori.Add(ex.Message);
					return;
				}

				var testo = new Dyn2ModelliDTesti
				{
					Idcomune = IdComune
				};

				if (cls.FkD2mdtId.GetValueOrDefault(int.MinValue) != int.MinValue)
				{
                    testo = mgrTesti.GetById(IdComune, cls.FkD2mdtId.GetValueOrDefault(int.MinValue));
				}

				testo.FkD2bttId = ddlBaseTipoTesto.Value;
				testo.Testo = txtTestoEsteso.Value;

                testo = (cls.FkD2mdtId.GetValueOrDefault(int.MinValue) == int.MinValue) ? mgrTesti.Insert(testo) : mgrTesti.Update(testo);

				cls.FkD2mdtId = testo.Id;
                cls.FkD2cId = null;
			}
			else
			{
				// il campo è un campo dinamico
                if (cls.FkD2mdtId.GetValueOrDefault(int.MinValue) != int.MinValue)
				{
                    mgrTesti.Delete(mgrTesti.GetById(IdComune, cls.FkD2mdtId.GetValueOrDefault(int.MinValue)));
				}

                cls.FkD2mdtId = null;
				cls.FkD2cId = Convert.ToInt32( rplCampoDinamico.Value );
			}

			Database.BeginTransaction();

			if (IsInserting)
				cls = mgr.Insert(cls);
			else
				cls = mgr.Update(cls);

			Database.CommitTransaction();

			//cmdChiudiDettaglio_Click(this, EventArgs.Empty);
			BindDettaglio(cls);
		}
		catch (RequiredFieldException rfe)
		{
			Database.RollbackTransaction();

			MostraErrore("Attenzione, i campi contrassegnati con un asterisco sono obbligatori.", rfe);
		}
		catch (Exception ex)
		{
			MostraErrore(IsInserting ? AmbitoErroreEnum.Inserimento : AmbitoErroreEnum.Aggiornamento, ex);
		}
	}


	protected void cmdVerificaCampi_Click(object sender, EventArgs e)
	{
		var modelliMgr = new Dyn2ModelliDMgr(Database);
		var campiMgr = new Dyn2CampiMgr(Database);
		var testiMgr = new Dyn2ModelliDTestiMgr(Database);

		var listaCampi = modelliMgr.GetList(IdComune, Modello.Id.Value);

		var erroriTrovati = false;

		foreach (var rigaCampi in listaCampi)
		{
			var testo = String.Empty;

			if (rigaCampi.FkD2cId.HasValue)
			{
				testo = campiMgr.GetById(IdComune, rigaCampi.FkD2cId.Value).Descrizione;
			}
			else
			{
				testo = testiMgr.GetById(IdComune, rigaCampi.FkD2mdtId.Value).Testo;
			}

			try
			{
				ValidaXmlInInput(testo);
			}
			catch (Exception ex)
			{
				this.Errori.Add(String.Format("Errore nel campo alla riga {0} e colonna {1}: {2}", rigaCampi.Posverticale, rigaCampi.Posorizzontale, ex.Message));
				erroriTrovati = true;
			}
		}

		if (!erroriTrovati)
		{
			Page.ClientScript.RegisterStartupScript(this.GetType() , "nessunErroreTrovato", "alert('Il modello non contiene errori.');",true);
		}
	}

	private void ValidaXmlInInput(string testo)
	{
		const string xmlFmt = "<?xml version=\"1.0\"?><contenuto>{0}</contenuto>";

		var xml = String.Format(xmlFmt, testo);

		try
		{
			var doc = new XmlDocument();
			doc.Load(StreamUtils.StringToStream(xml));
		}
		catch (XmlException ex)
		{
			throw new Exception("Il testo immesso contiene tag non chiusi o entità html non valide. (dettagli tecnici: <i>" + ex.Message + "</i>)");
		}
	}

	protected void cmdElimina_Click(object sender, EventArgs e)
	{
		Dyn2ModelliDMgr mgr = new Dyn2ModelliDMgr(Database);

		int id = Convert.ToInt32(lblId.Item.Text);

		Dyn2ModelliD cls = mgr.GetById(IdComune, id);

		try
		{
			Database.BeginTransaction();

			mgr.Delete(cls);

			Database.CommitTransaction();

			cmdChiudiDettaglio_Click(sender, e);


		}
		catch (Exception ex)
		{
			Database.RollbackTransaction();

			MostraErrore(AmbitoErroreEnum.Cancellazione, ex);
		}
	}

	protected void cmdChiudiDettaglio_Click(object sender, EventArgs e)
	{
		
		if (IsInPopup)
		{
			Page.ClientScript.RegisterStartupScript(this.GetType(), "closeScript", "self.close();", true);

			return;
		}

        this.ScrollTo = lblId.Item.Text;


        multiView.ActiveViewIndex = 0;
	}
	#endregion

	protected void cmdNuovo_Click(object sender, EventArgs e)
	{
		BindDettaglio(new Dyn2ModelliD());
	}
	protected void ddlTipoCampo_ValueChanged(object sender, EventArgs e)
	{
		pnlCampoTesto.Visible = ddlTipoCampo.Value == Constants.ID_CAMPO_TESTO;

		pnlCampoDinamico.Visible = !pnlCampoTesto.Visible;

	}

	#region metodi di ricerca di ricercheplus
	[System.Web.Services.WebMethodAttribute(), System.Web.Script.Services.ScriptMethodAttribute()]
	public static string[] GetCompletionList(string token, string dataClassType,
											  string targetPropertyName,
											  string descriptionPropertyNames,
											  string prefixText,
											  int count,
											  string software,
											  bool ricercaSoftwareTT,
											  Dictionary<string,string> initParams, string idContesto)
	{
		try
		{
			RicerchePlusSearchComponent sc = new RicerchePlusSearchComponent(token, dataClassType, targetPropertyName, descriptionPropertyNames, prefixText, count, software , ricercaSoftwareTT, initParams);

			// Gestione di una ricerca custom
			sc.Searching += delegate(object sender, RicerchePlusEventArgs e)
			{
				Dyn2Campi d2c = (Dyn2Campi)e.SearchedClass;

				d2c.OthersWhereClause.Add(" (fk_d2bc_id='" + idContesto + "' or fk_d2bc_id is null or fk_d2bc_id='')");

			};

			return RicerchePlusCtrl.CreateResultList(sc.Find(true));
		}
		catch (Exception ex)
		{
			return RicerchePlusCtrl.CreateErrorResult( ex );
		}
	}

	#endregion

	protected void cmdRicalcolaNumerazione_Click(object sender, EventArgs e)
	{
		Dyn2ModelliDMgr mgr = new Dyn2ModelliDMgr(Database);
        mgr.RicalcolaNumerazioneRighe(IdComune, Modello.Id.GetValueOrDefault(int.MinValue));

		gvLista.DataBind();
	}

	protected void RigaMultiplaCheckedChanged(object sender, EventArgs e)
	{
        CheckBox cb = (CheckBox)sender;
        HiddenField hf = (HiddenField)cb.NamingContainer.FindControl("hidRiga");

        try
        {
            new Dyn2ModelliDMgr(Database).ImpostaRigaMultipla(IdComune, Modello.Id.Value, Convert.ToInt32(hf.Value), cb.Checked ? 1 : 0);

            gvLista.DataBind();
        }
        catch (Exception ex)
        {
            cb.Checked = !cb.Checked;
            MostraErrore(ex);
            Errori.Add(ex.Message);
        }
	}

	protected void gvLista_DataBound(object sender, EventArgs e)
	{/*
		Dyn2ModelliT modello = new Dyn2ModelliTMgr(Database).GetById(IdComune, Modello.Id.Value);

		if (modello.Modellomultiplo.GetValueOrDefault(0) == 1)
		{
			gvLista.Columns[5].Visible = false;
		}*/
	}

    protected void gvLista_RowDataBound(object sender, GridViewRowEventArgs e)
    {
        if (e.Row.RowType == DataControlRowType.DataRow)
        {
            var dataItem = (Dyn2ModelliD)e.Row.DataItem;
            var ddlMultiplo = (DropDownList)e.Row.FindControl("ddlMultiplo");

            ddlMultiplo.SelectedValue = dataItem.FlgMultiplo.GetValueOrDefault(0).ToString();
        }
    }

    protected void ddlMultiplo_SelectedIndexChanged(object sender, EventArgs e)
    {
        DropDownList ddl = (DropDownList)sender;
        HiddenField hf = (HiddenField)ddl.NamingContainer.FindControl("hidRiga");

        try
        {
            new Dyn2ModelliDMgr(Database).ImpostaRigaMultipla(IdComune, Modello.Id.Value, Convert.ToInt32(hf.Value), Convert.ToInt32(ddl.SelectedValue));

            gvLista.DataBind();
        }
        catch (Exception ex)
        {
            ddl.SelectedIndex = 0;
            MostraErrore(ex);
            Errori.Add(ex.Message);
        }
    }

    protected void chkSpezzaTabella_CheckedChanged(object sender, EventArgs e)
    {
        var chk = (CheckBox)sender;
        var id = Convert.ToInt32(gvLista.DataKeys[((GridViewRow)chk.NamingContainer).RowIndex].Value);
        try
        {
            new Dyn2ModelliDMgr(Database).ImpostaSpezzaTabella(IdComune, Modello.Id.Value, id, chk.Checked);

            gvLista.DataBind();
        }
        catch (Exception ex)
        {

            MostraErrore(ex);
            Errori.Add(ex.Message);
        }
    }
}
