using System;
using System.Linq;
using System.Collections.Generic;
using System.Web.UI;
using System.Web.UI.WebControls;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions.IstanzeAllegati;
using Init.SIGePro.Manager;
using Init.SIGePro.Manager.Logic.DatiDinamici;
using Init.Utils.Web.UI;
using SIGePro.Net;

using Init.SIGePro.DatiDinamici.WebControls;
using Init.SIGePro.DatiDinamici;
using Init.SIGePro.DatiDinamici.Contesti;
using Init.SIGePro.DatiDinamici.Scripts;
using Init.SIGePro.DatiDinamici.Interfaces;
using System.Text;

public partial class Archivi_DatiDinamici_Dyn2Campi : BasePage
{
	public string IdCampo
	{
		get { return Request.QueryString["IdCampo"]; }
	}

	public bool PopupCreaNuovo
	{
		get
		{
			if (string.IsNullOrEmpty(Request.QueryString["PopupCreaNuovo"]))
				return false;

			return Convert.ToBoolean(Request.QueryString["PopupCreaNuovo"]);
		}
	}

	protected void Page_Load(object sender, EventArgs e)
	{
		ImpostaScriptEliminazione(cmdElimina);

		if (!IsPostBack)
		{
			var tipiCampi = ControlliDatiDinamiciDictionary
												.DesignTimeItems
												.Keys
												.Select( key => new ListItem(ControlliDatiDinamiciDictionary.Items[key].Descrizione, key.ToString()))
												.OrderBy(x => x.Text)
												.ToArray();
			
			ddlTipoDato.Item.Items.AddRange(tipiCampi);


			foreach (var key in DatiDinamiciInfoDictionary.Items.Keys)
				ddlContesto.Item.Items.Add(new ListItem(DatiDinamiciInfoDictionary.Items[key].Descrizione, ContestoTranslator.ContestoEnumToContestoBase( key )));

			ddlContesto.Item.Items.Insert(0, new ListItem("Nessuno (funzioni non disponibili)",""));

			if (!String.IsNullOrEmpty(IdCampo) )
			{
				BindDettaglio( new Dyn2CampiMgr( Database ).GetById( IdComune , Convert.ToInt32( IdCampo )  ) );
				return;
			}

			if (PopupCreaNuovo)
			{
				BindDettaglio(new Dyn2Campi());
				return;
			}
            
		}
	}

	private void BindDettaglio(Dyn2Campi cls)
	{
		multiView.ActiveViewIndex = 2;

        this.IsInserting = cls.Id.GetValueOrDefault(int.MinValue) == int.MinValue;

		lblId.Item.Text = IsInserting ? "Nuovo" : cls.Id.ToString();

		txtNomeCampo.Item.Text = cls.Nomecampo;
		txtEtichetta.Item.Text = cls.Etichetta;
		txtDescrizione.Item.Text = cls.Descrizione;

		ddlTipoDato.Item.SelectedValue = cls.Tipodato;
		ddlContesto.Item.SelectedValue = cls.FkD2bcId;

		if (!IsInserting)
		{
			BindGrigliaProprieta();
			BindGrigliaSchede(cls);
		}

        AggiornaVisibilitaEtichettaDescrizione();


        cmdEditFormule.Visible = cmdElimina.Visible = !IsInserting;

		pnlProprietaControllo.Visible = !IsInserting;

		if (String.IsNullOrEmpty(cls.FkD2bcId))
			cmdEditFormule.Visible = false;

		var scripts = new Dyn2CampiScriptMgr(this.Database).GetScriptsCampo(this.IdComune, Convert.ToInt32(this.IdCampo));

		if (scripts.Count > 0)
			cmdEditFormule.Visible = true;

		if (cmdEditFormule.Visible)
			cmdEditFormule.Visible = !new Dyn2CampiMgr(Database).VerificaPresenzaInRigheMultiple(IdComune, Convert.ToInt32(IdCampo));

		if (ContieneScriptPerEvento(scripts,TipoScriptEnum.Modifica))
		{
            cmdEditFormule.Visible = true;
			this.Errori.Add("Attenzione, il campo contiene formule nell'evento di modifica. Per migliorare le prestazioni delle schede dinamiche spostare la formula nel modello");
		}

		if (ContieneScriptPerEvento(scripts, TipoScriptEnum.Caricamento))
		{
            cmdEditFormule.Visible = true;
			this.Errori.Add("Attenzione, il campo contiene formule nell'evento di caricamento. Per migliorare le prestazioni delle schede dinamiche spostare la formula nel modello");
		}

		if (ContieneScriptPerEvento(scripts, TipoScriptEnum.Salvataggio))
		{
            cmdEditFormule.Visible = true;
			this.Errori.Add("Attenzione, il campo contiene formule nell'evento di salvataggio. Per migliorare le prestazioni delle schede dinamiche spostare la formula nel modello");
		}
	}

	private bool ContieneScriptPerEvento(Dictionary<TipoScriptEnum, IDyn2ScriptCampo> scripts, TipoScriptEnum evento)
	{
		if (!scripts.ContainsKey(evento))
			return false;

		var s = scripts[evento] as Dyn2CampiScript;

		return s.GetTestoScript().Trim().Length > 0;
	}

	/// <summary>
	/// Se il campo è utilizzato in altre schede mostra e popola la griglia che le enumera
	/// </summary>
	/// <param name="cls"></param>
	private void BindGrigliaSchede(Dyn2Campi cls)
	{
		var d2ModelliMgr = new Dyn2ModelliTMgr(Database);
		var listaSchede = d2ModelliMgr.GetSchedeContenentiIlCampo(this.IdComune, cls.Id);

		gvSchedeDelCampo.DataSource = listaSchede;
		gvSchedeDelCampo.DataBind();

		pnlCampoCompareInSchede.Visible = listaSchede.Count() > 0;
	}


    private void AggiornaVisibilitaEtichettaDescrizione()
    {
        var tipoDato = (TipoControlloEnum)Enum.Parse(typeof(TipoControlloEnum), ddlTipoDato.Value);
        var controlloItem = ControlliDatiDinamiciDictionary.Items[tipoDato];

        // Etichetta visibile
        this.txtEtichetta.Visible = controlloItem.HaEtichetta;

        if (!this.txtEtichetta.Visible)
        {
            this.txtEtichetta.Value = String.Empty;
        }

        // Descrizione visibile
        this.txtDescrizione.Visible = controlloItem.HaDescrizione;

        if (!this.txtDescrizione.Visible)
        {
            this.txtDescrizione.Value = String.Empty;
        }
    }


	private void BindGrigliaProprieta()
	{
		var id = Convert.ToInt32(lblId.Value);
		var tipoDato = (TipoControlloEnum)Enum.Parse(typeof(TipoControlloEnum), ddlTipoDato.Value);
        var controlloItem = ControlliDatiDinamiciDictionary.Items[tipoDato];
        
        // Proprietà designer
        var proprietaControllo = controlloItem.ProprietaEditabili;
		var proprietaValorizzate = new Dyn2CampiMgr(Database).GetProprietaControllo(IdComune, id );

		proprietaValorizzate.ForEach(prop  => 
		{
			if (proprietaControllo.ContainsKey(prop.Proprieta))
				proprietaControllo[prop.Proprieta].Value = prop.Valore;
		});

		gvProprietaControllo.DataSource = proprietaControllo.Values;
		gvProprietaControllo.DataBind();

        pnlProprietaControllo.Visible = proprietaControllo.Values.Any();


        // Help del controllo
        var helpControllo = controlloItem.HelpControllo;

		ddlTipoDato.HelpControl = String.IsNullOrEmpty(helpControllo) ? String.Empty : "helpTipoControllo";
		helpTipoControllo.Text = helpControllo;

		if (helpControllo != null && helpControllo.Length > 600)
			helpTipoControllo.CssClass = "HelpControllo large";
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
		BindDettaglio(new Dyn2Campi());
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
		int id = Convert.ToInt32(gvLista.DataKeys[gvLista.SelectedIndex].Value);

		Dyn2Campi cls = new Dyn2CampiMgr(Database).GetById(IdComune, id);

		BindDettaglio(cls);
	}
	#endregion


	#region Scheda dettaglio
	protected void cmdSalva_Click(object sender, EventArgs e)
	{
		Dyn2CampiMgr mgr = new Dyn2CampiMgr(Database);
		Dyn2CampiProprietaMgr mgrProp = new Dyn2CampiProprietaMgr(Database);

		Dyn2Campi cls = null;

		if (IsInserting)
		{
			cls = new Dyn2Campi();
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
			cls.Nomecampo = txtNomeCampo.Value.ToUpper();
			cls.Etichetta = txtEtichetta.Value;
			cls.Descrizione = txtDescrizione.Value;
			cls.Tipodato = ddlTipoDato.Value;
			cls.FkD2bcId = ddlContesto.Value == String.Empty ? (string)null : ddlContesto.Value;
			//cls.Obbligatorio = chkObbligatorio.Item.Checked ? 1 : 0;

			if (IsInserting)
			{
				cls = mgr.Insert(cls);
			}
			else
			{
				cls = mgr.Update(cls);

                mgrProp.DeleteByIdCampo(IdComune, cls.Id.GetValueOrDefault(int.MinValue));

				foreach (RepeaterItem row in gvProprietaControllo.Items)
				{
					LabeledTextBox txtProprieta = (LabeledTextBox)row.FindControl("txtProprieta");
					LabeledDropDownList ddlProprieta = (LabeledDropDownList)row.FindControl("ddlProprieta");
					HiddenField hfPropId = (HiddenField)row.FindControl("hfPropId");

					string valore = txtProprieta.Visible ? txtProprieta.Value : ddlProprieta.Value;

					if (String.IsNullOrEmpty(valore)) continue;

					Dyn2CampiProprieta prop = new Dyn2CampiProprieta();
					prop.Idcomune = IdComune;
					prop.FkD2cId = cls.Id;
					prop.Proprieta = hfPropId.Value;
					prop.Valore = valore;

					mgrProp.Insert(prop);
				}
			}

			if (PopupCreaNuovo)
			{
				Response.Clear();

				string startScript = "window.opener.impostaCampo({ codice: " + cls.Id.Value.ToString() + ", descrizione:'" + cls.Nomecampo + "' });self.close();";

				Response.Write("<script type='text/javascript'>");
				Response.Write(startScript);
				Response.Write("</script>");

				Response.End();
				return;
			}


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

	protected void cmdEditFormule_Click(object sender, EventArgs e)
	{
		string fmtUrl = "~/Archivi/DatiDinamici/Dyn2CampiFormule.aspx?Token={0}&IdCampo={1}&Popup={2}";
		Response.Redirect(String.Format(fmtUrl, AuthenticationInfo.Token, lblId.Value , IsInPopup));
	}

	protected void cmdElimina_Click(object sender, EventArgs e)
	{
		Dyn2CampiMgr mgr = new Dyn2CampiMgr(Database);

		int id = Convert.ToInt32(lblId.Item.Text);

		Dyn2Campi cls = mgr.GetById(IdComune, id);

		try
		{
			mgr.Delete(cls);

			cmdChiudiDettaglio_Click(sender, e);
		}
		catch (Exception ex)
		{
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

		string fmtUrl = "~/Archivi/DatiDinamici/Dyn2Campi.aspx?Token={0}&Software={1}";
		Response.Redirect( String.Format( fmtUrl , AuthenticationInfo.Token , Software ));
	}
	#endregion

	#region decodifica dei valori dei dati
	public string TraduciTipoDato(object tipoDato)
	{
		TipoControlloEnum key = (TipoControlloEnum)Enum.Parse(typeof(TipoControlloEnum),tipoDato.ToString() );

/*		try
		{*/
			return ControlliDatiDinamiciDictionary.Items[key].Descrizione;
	/*	}
		catch (Exception)
		{
			return key.ToString();
		}*/
	}

	public string TraduciContesto(object contesto)
	{
		if (contesto == null || string.IsNullOrEmpty(contesto.ToString())) return "Tutti";

		var contestoEnum = ContestoTranslator.ContestoBaseToContestoEnum( contesto.ToString());

		return DatiDinamiciInfoDictionary.Items[contestoEnum].Descrizione;
	}

	public string TraduciObbligatorio(object obbligatorio)
	{
		if (obbligatorio == null || obbligatorio.ToString() == "0") return "No";

		return "Si";
	}
	#endregion


	protected void ddlTipoDato_ValueChanged(object sender, EventArgs e)
	{
		if (!IsInserting)
        {
            BindGrigliaProprieta();
        }

        AggiornaVisibilitaEtichettaDescrizione();
    }

	protected void ddlContesto_ValueChanged(object sender, EventArgs e)
	{
		if (!IsInserting)
		{
			cmdEditFormule.Visible = !String.IsNullOrEmpty(ddlContesto.Value);
		}
	}

	protected void gvProprietaControllo_ItemDataBound(object sender, RepeaterItemEventArgs e)
	{
		if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
		{
			EditablePropertyDetails dataIt = e.Item.DataItem as EditablePropertyDetails;

			LabeledTextBox txtProprieta = (LabeledTextBox)e.Item.FindControl("txtProprieta");
			LabeledDropDownList ddlProprieta = (LabeledDropDownList)e.Item.FindControl("ddlProprieta");

			if (dataIt.TipoControllo == TipoControlloEditEnum.TextBox)
			{
				txtProprieta.Visible = true;
				ddlProprieta.Visible = false;

				txtProprieta.Value = dataIt.Value;
			}
			else
			{
				txtProprieta.Visible = false;
				ddlProprieta.Visible = true;

				ddlProprieta.Item.DataValueField = "Key";
				ddlProprieta.Item.DataTextField = "Value";
				ddlProprieta.Item.DataSource = dataIt.ValoriLista;
				ddlProprieta.Item.DataBind();

				try
				{
					ddlProprieta.Value = dataIt.Value;
				}
				catch (Exception ex)
				{
					ddlProprieta.Item.SelectedIndex = 0;
				}
			}

		}
	}

}
