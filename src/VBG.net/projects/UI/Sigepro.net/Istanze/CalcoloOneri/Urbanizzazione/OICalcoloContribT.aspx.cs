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
using System.Collections.Generic;
using Init.Utils.Web.UI;
using Init.Utils;
using System.Text;

public partial class Istanze_CalcoloOneri_Urbanizzazione_OICalcoloContribT : BasePage
{
	public override string Software
	{
		get
		{
            Istanze ist = new IstanzeMgr(Database).GetById(IdComune, ContribT.Codiceistanza.Value);
			return ist.SOFTWARE;
		}
	}

	public Istanze_CalcoloOneri_Urbanizzazione_OICalcoloContribT()
	{
		//VerificaSoftware = false;
	}

	string errMsgCampoNonTrovato = "Nell'attuale configurazione del listino coefficienti non è stato trovato il valore ({0}-{1}).<br>Il tasto \"Procedi\" riporta un coefficiente uguale a zero.<br>Il tasto \"Chiudi\" non modifica il precedente calcolo.";
	
	OICalcoloContribT m_contribT = null;
	private OICalcoloContribT ContribT
	{
		get 
		{ 
			if (m_contribT == null)
			{
				int id = Convert.ToInt32( Request.QueryString["IdContribT"] );
				m_contribT = new OICalcoloContribTMgr(Database).GetById(IdComune, id);
			}

			return m_contribT;
		}
	}


	protected void Page_Load(object sender, EventArgs e)
	{
		Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;

		if (!IsPostBack)
		{
			if ( ContribT.PrimoInserimento )
				DataBindPannelloModifica();
			else
				DataBindPannelloDettaglio();
		}
		
	}

	#region Binding dei dati

	/// <summary>
	/// Effettua il binding dei dati del pannello di modifica
	/// </summary>
	public void DataBindPannelloModifica()
	{
		multiView.ActiveViewIndex = 0;

		OICalcoloContribTMgr tmgr = new OICalcoloContribTMgr(Database);

        List<KeyValuePair<string, string>> listaZo = tmgr.GetValoriComboZonaOmogenea(IdComune, ContribT.Id.GetValueOrDefault(int.MinValue));

		lbltipoDestinazione.Text = new OCCBaseDestinazioniMgr(Database).GetById(ContribT.FkOccbdeId).Destinazione;

		pnlZonaOmogenea.Visible = listaZo.Count > 0;

		if (pnlZonaOmogenea.Visible)
		{

            labelZonaOmogenea1.Text = tmgr.GetDescrizioneComboZonaOmogenea(IdComune, ContribT.Id.GetValueOrDefault(int.MinValue));

			ddlZonaOmogenea.DataSource = listaZo;
			ddlZonaOmogenea.DataBind();

            ImpostaValoreCombo(ddlZonaOmogenea, ContribT.FkAreeCodiceareaZto.GetValueOrDefault(int.MinValue), pnlErroreZonaOmogenea, errMsgCampoNonTrovato, delegate(DropDownList ddl, int val)
			{
				Aree area = new AreeMgr( Database ).GetById( val.ToString() , IdComune );
				ddl.Items.Add(new ListItem(area.DENOMINAZIONE, area.CODICEAREA));
			});
		}

		ddlZonaOmogenea_SelectedIndexChanged(ddlZonaOmogenea, EventArgs.Empty);

		if (pnlZonaPrg.Visible)
		{
            labelZonaPrg1.Text = tmgr.GetDescrizioneComboZonaPrg(IdComune, ContribT.Id.GetValueOrDefault(int.MinValue));

            ImpostaValoreCombo(ddlZonaPrg, ContribT.FkAreeCodiceareaPrg.GetValueOrDefault(int.MinValue), pnlErroreZonaPrg, errMsgCampoNonTrovato, delegate(DropDownList ddl, int val)
			{
				Aree area = new AreeMgr(Database).GetById(val.ToString(), IdComune);
				ddl.Items.Add(new ListItem(area.DENOMINAZIONE, area.CODICEAREA));
			});
		}

		ddlZonaPrg_SelectedIndexChanged(this, EventArgs.Empty);

		if (pnlTipiIntervento.Visible)
		{
            ImpostaValoreCombo(ddlTipoIntervento, ContribT.FkOinId.GetValueOrDefault(int.MinValue), pnlErroreTipoIntervento, errMsgCampoNonTrovato, delegate(DropDownList ddl, int val)
			{
				OInterventi interv = new OInterventiMgr(Database).GetById( IdComune , val );
				ddl.Items.Add(new ListItem(interv.Intervento, interv.Id.ToString()));
			});
		}

		ddlTipoIntervento_SelectedIndexChanged(this, EventArgs.Empty);

		if (pnlIndiciTerritoriali.Visible)
		{
            ImpostaValoreCombo(ddlIndiciTerritoriali, ContribT.FkOitId.GetValueOrDefault(int.MinValue), pnlErroreIndiciTerritoriali, errMsgCampoNonTrovato, delegate(DropDownList ddl, int val)
			{
				OIndiciTerritoriali it = new OIndiciTerritorialiMgr(Database).GetById(IdComune, val);
				ddl.Items.Add(new ListItem(it.ToString(), it.Id.ToString()));
			});
		}


        List<KeyValuePair<string, string>> listaInterventiTabd = tmgr.GetValoriComboInterventiTabd(IdComune, ContribT.Id.GetValueOrDefault(int.MinValue));

		pnlInterventiTabD.Visible = listaInterventiTabd.Count > 0;

		if (pnlInterventiTabD.Visible)
		{
			ddlInterventiTabD.DataSource = listaInterventiTabd;
			ddlInterventiTabD.DataBind();

            ImpostaValoreCombo(ddlInterventiTabD, ContribT.FkOinIdTabd.GetValueOrDefault(int.MinValue), pnlErroreInterventiTabD, errMsgCampoNonTrovato, delegate(DropDownList ddl, int val)
			{
				OInterventi it = new OInterventiMgr(Database).GetById(IdComune, val);
				ddl.Items.Add(new ListItem(it.Intervento, it.Id.ToString()));
			});
		}

		ddlInterventiTabD_SelectedIndexChanged(this, EventArgs.Empty);


		if (pnlClassiAdddetti.Visible)
		{
            ImpostaValoreCombo(ddlAddetti, ContribT.FkOclaId.GetValueOrDefault(int.MinValue), pnlErroreClassiAddetti, errMsgCampoNonTrovato, delegate(DropDownList ddl, int val)
			{
				OClassiAddetti it = new OClassiAddettiMgr(Database).GetById(IdComune, val);
				ddl.Items.Add(new ListItem(it.Classe, it.Id.ToString()));
			});
		}
	}


	#region Gestione dell'impostazione dei valori delle combo
	protected delegate void ComboBindingErrordelegate(DropDownList ddl, int selectedValue);

	protected void ImpostaValoreCombo(DropDownList combo, int valoreSelezionato, Panel panelErrore, string errMsg, ComboBindingErrordelegate callbackErrore)
	{
		panelErrore.Visible = false;

		try
		{
			if (valoreSelezionato == int.MinValue)
				combo.SelectedIndex = 0;
			else
				combo.SelectedValue = valoreSelezionato.ToString();
		}
		catch (ArgumentOutOfRangeException ex)
		{
			if (callbackErrore != null)
			{
				callbackErrore(combo, valoreSelezionato);
				combo.SelectedValue = valoreSelezionato.ToString();
			}

			if (panelErrore != null)
			{
				panelErrore.Visible = true;

				Literal l = new Literal();
				l.Text = String.Format(errMsg, combo.SelectedItem.Value, combo.SelectedItem.Text);

				panelErrore.Controls.Add(l);
			}
		}

	}

	#endregion

	/// <summary>
	/// Effettua il binding dei dati del pannello di visualizzazione
	/// </summary>
	public void DataBindPannelloDettaglio()
	{
		multiView.ActiveViewIndex = 1;

		OICalcoloContribTMgr tmgr = new OICalcoloContribTMgr(Database);

		lblEdtTipoDestinazione.Text = new OCCBaseDestinazioniMgr(Database).GetById(ContribT.FkOccbdeId).Destinazione;

        pnlEdtInterventiTabd.Visible = ContribT.FkOinIdTabd.GetValueOrDefault(int.MinValue) != int.MinValue;

		if (pnlEdtInterventiTabd.Visible)
            lblInterventiTabd.Text = new OInterventiMgr(Database).GetById(IdComune, ContribT.FkOinIdTabd.GetValueOrDefault(int.MinValue)).Intervento;


        pnlEdtClassiAddetti.Visible = ContribT.FkOclaId.GetValueOrDefault(int.MinValue) != int.MinValue;

		if (pnlEdtClassiAddetti.Visible)
            lblClassiAddetti.Text = new OClassiAddettiMgr(Database).GetById(IdComune, ContribT.FkOclaId.GetValueOrDefault(int.MinValue)).Classe;



        pnlEdtIndiciTerritoriali.Visible = ContribT.FkOitId.GetValueOrDefault(int.MinValue) != int.MinValue;

		if ( pnlEdtIndiciTerritoriali.Visible )
		{
            OIndiciTerritoriali it = new OIndiciTerritorialiMgr(Database).GetById(IdComune, ContribT.FkOitId.GetValueOrDefault(int.MinValue));

			lblIndiciTerritoriali.Text = it.ToString();
		}



        pnlEdtTipoIntervento.Visible = ContribT.FkOinId.GetValueOrDefault(int.MinValue) != int.MinValue;

		if( pnlEdtTipoIntervento.Visible )
            lblTipoIntervento.Text = new OInterventiMgr(Database).GetById(IdComune, ContribT.FkOinId.GetValueOrDefault(int.MinValue)).Intervento;


        pnlEdtZonaOmogenea.Visible = ContribT.FkAreeCodiceareaZto.GetValueOrDefault(int.MinValue) != int.MinValue;

		if (pnlEdtZonaOmogenea.Visible)
		{
            labelZonaOmogenea2.Text = tmgr.GetDescrizioneComboZonaOmogenea(IdComune, ContribT.Id.GetValueOrDefault(int.MinValue));
			lblZonaOmogenea.Text = new AreeMgr(Database).GetById(ContribT.FkAreeCodiceareaZto.ToString(), IdComune).DENOMINAZIONE;
		}

        pnlEdtZonaPrg.Visible = ContribT.FkAreeCodiceareaPrg.GetValueOrDefault(int.MinValue) != int.MinValue;

		if (pnlEdtZonaPrg.Visible)
		{
            labelZonaPrg2.Text = tmgr.GetDescrizioneComboZonaPrg(IdComune, ContribT.Id.GetValueOrDefault(int.MinValue));
			lblZonaPrg.Text = new AreeMgr(Database).GetById(ContribT.FkAreeCodiceareaPrg.ToString(), IdComune).DENOMINAZIONE;
		}

        DataGridSource = new OICalcoloContribTMgr(Database).GeneraDatasetContributoUrbanizzazione(IdComune, ContribT.Id.GetValueOrDefault(int.MinValue));

		BindDgCoefficienti();

	}

	#region gestione delle combo a cascata
	protected void ddlZonaOmogenea_SelectedIndexChanged(object sender, EventArgs e)
	{
		OICalcoloContribTMgr tmgr = new OICalcoloContribTMgr(Database);

		int idZto = pnlZonaOmogenea.Visible ? Convert.ToInt32(ddlZonaOmogenea.SelectedValue) : int.MinValue;

        List<KeyValuePair<string, string>> listaPrg = tmgr.GetValoriComboZonaPrg(IdComune, ContribT.Id.GetValueOrDefault(int.MinValue), idZto);

		pnlZonaPrg.Visible = listaPrg.Count > 0;

		if (listaPrg.Count > 0)
		{
			ddlZonaPrg.DataSource = listaPrg;
			ddlZonaPrg.DataBind();
		}

		ddlZonaPrg_SelectedIndexChanged(this, EventArgs.Empty);
	}

	protected void ddlZonaPrg_SelectedIndexChanged(object sender, EventArgs e)
	{
		OICalcoloContribTMgr tmgr = new OICalcoloContribTMgr(Database);

		int idZto = pnlZonaOmogenea.Visible ? Convert.ToInt32(ddlZonaOmogenea.SelectedValue) : int.MinValue;
		int idPrg = pnlZonaPrg.Visible ? Convert.ToInt32(ddlZonaPrg.SelectedValue) : int.MinValue;

        List<KeyValuePair<string, string>> listaTipiInt = tmgr.GetValoriComboTipoIntervento(IdComune, ContribT.Id.GetValueOrDefault(int.MinValue), idZto, idPrg);

		pnlTipiIntervento.Visible = listaTipiInt.Count > 0;

		if (listaTipiInt.Count > 0)
		{
			ddlTipoIntervento.DataSource = listaTipiInt;
			ddlTipoIntervento.DataBind();
		}

		ddlTipoIntervento_SelectedIndexChanged(this, EventArgs.Empty);
	}

	protected void ddlTipoIntervento_SelectedIndexChanged(object sender, EventArgs e)
	{
		OICalcoloContribTMgr tmgr = new OICalcoloContribTMgr(Database);

		int idZto = pnlZonaOmogenea.Visible ? Convert.ToInt32(ddlZonaOmogenea.SelectedValue) : int.MinValue;
		int idPrg = pnlZonaPrg.Visible ? Convert.ToInt32(ddlZonaPrg.SelectedValue) : int.MinValue;
		int idInterv = pnlTipiIntervento.Visible ? Convert.ToInt32(ddlTipoIntervento.SelectedValue) : int.MinValue;

        List<KeyValuePair<string, string>> listaIndiciTerr = tmgr.GetValoriComboIndiciTerritoriali(IdComune, ContribT.Id.GetValueOrDefault(int.MinValue), idZto, idPrg, idInterv);

		pnlIndiciTerritoriali.Visible = listaIndiciTerr.Count > 0;

		if (listaIndiciTerr.Count > 0)
		{
			ddlIndiciTerritoriali.DataSource = listaIndiciTerr;
			ddlIndiciTerritoriali.DataBind();
		}
	}

	protected void ddlInterventiTabD_SelectedIndexChanged(object sender, EventArgs e)
	{
		int idIntervento = pnlInterventiTabD.Visible ? Convert.ToInt32(ddlInterventiTabD.SelectedValue) : int.MinValue;
		
		OICalcoloContribTMgr tmgr = new OICalcoloContribTMgr(Database);

        List<KeyValuePair<string, string>> listaClassiAddetti = tmgr.GetValoriComboClassiAddetti(IdComune, ContribT.Id.GetValueOrDefault(int.MinValue), idIntervento);

		pnlClassiAdddetti.Visible = listaClassiAddetti.Count > 0;

		if (listaClassiAddetti.Count > 0)
		{
			ddlAddetti.DataSource = listaClassiAddetti;
			ddlAddetti.DataBind();
		}
	}


	#endregion

	#region Binding della griglia dati
	private DataSet DataGridSource
	{
		get { return (DataSet)Session["OICalcoloContribTDataGridSource"]; }
		set { Session["OICalcoloContribTDataGridSource"] = value; }
	}

	private void BindDgCoefficienti()
	{
		dgDettagliCalcolo.DataSource = DataGridSource;
		dgDettagliCalcolo.DataBind();
	}


	protected void dgDettagliCalcolo_ItemDataBound(object sender, DataGridItemEventArgs e)
	{
		DataRowView rowView = (DataRowView)e.Item.DataItem;

		//Visualizazione delle colonne (bisogna fare così perchè la proprietà autogenerateColumns è true)
		if (DataGridSource == null) return;

		DataTable dt = DataGridSource.Tables[0];

		// Di default la prima colonna non è visibile
		e.Item.Cells[0].Visible = false;

		#region visualizzazione delle intestazioni
		if (e.Item.ItemType == ListItemType.Header)
		{
			for (int i = 0; i < dt.Columns.Count; i++)
			{
				string nomeColonna = dt.Columns[i].ColumnName;

				if (nomeColonna == "Destinazione" || nomeColonna == "Cubatura" )
				{
					e.Item.Cells[i].RowSpan = 2;
					e.Item.Cells[i].VerticalAlign = VerticalAlign.Middle;
				}
				else if (nomeColonna.IndexOf("_costom") > 0 || 
						 nomeColonna.IndexOf("_riduzione") > 0 ||
						 nomeColonna.IndexOf("_percriduzione") > 0)
				{
					e.Item.Cells[i].Visible = false;
				}
				else if (nomeColonna.IndexOf("_costoTot") > 0)
				{
					e.Item.Cells[i].ColumnSpan = 3;

					int idTipoOnere = Convert.ToInt32(nomeColonna.Replace("_costoTot", ""));
					e.Item.Cells[i].Text = new OTipiOneriMgr(Database).GetById(IdComune, idTipoOnere).Descrizione;
					e.Item.Cells[i].HorizontalAlign = HorizontalAlign.Center;
				}

			}
		}
		#endregion

		#region Visualizzazione degli elementi
		if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
		{
			if (e.Item.ItemIndex == 0)
			{
				for (int i = 0; i < dt.Columns.Count; i++)
				{
					string nomeColonna = dt.Columns[i].ColumnName;
					if (nomeColonna == "Destinazione" || 
						nomeColonna == "Cubatura" ||
						nomeColonna.IndexOf("_percriduzione") > 0)
					{
						e.Item.Cells[i].Visible = false;
					}
					else
					{
						e.Item.Cells[i].CssClass = "IntestazioneTabella";
						e.Item.Cells[i].HorizontalAlign = HorizontalAlign.Center;
					}
				}


				return;
			}

			int idxControllo = 0;

			for (int i = 0; i < dt.Columns.Count; i++)
			{
				string nomeColonna = dt.Columns[i].ColumnName;
				if (nomeColonna.IndexOf("_costom") > 0)
				{
					string idTipoOnere = nomeColonna.Replace("_costom", "");

					HiddenField hf = new HiddenField();
					hf.ID = "hiddenField" + idxControllo.ToString();
					hf.Value = idTipoOnere;

					Literal l = new Literal();
					l.Text = Convert.ToDouble(rowView[nomeColonna]).ToString("N2");

					e.Item.Cells[i].Controls.Clear();
					e.Item.Cells[i].Controls.Add(l);
					//e.Item.Cells[i].Controls.Add(dtb);
					e.Item.Cells[i].Controls.Add(hf);


					
				}
				else if (nomeColonna.IndexOf("_riduzione") > 0 )
				{
					string idTipoOnere = nomeColonna.Replace("_riduzione", "");

					Literal l = new Literal();
					l.Text = "€&nbsp;";

					double valorePercRiduzione = Convert.ToDouble(rowView[idTipoOnere + "_percriduzione"]);
					//int valorePercRiduzione = Convert.ToInt32(rowView[idTipoOnere + "_percriduzione"]);


					DoubleTextBox dtb = new DoubleTextBox();
					dtb.ID = "doubleTextBoxRiduzione" + idxControllo.ToString();
					dtb.ValoreDouble = Convert.ToDouble(rowView[idTipoOnere + "_riduzione"]);
					dtb.Columns = 5;
					dtb.ReadOnly = valorePercRiduzione != 0;
					
					HelpDiv helpDiv = new HelpDiv();
					helpDiv.ID = "helpDiv" +  idxControllo.ToString();
					helpDiv.Text = GetListaRiduzioni(Convert.ToInt32(rowView["IdDestinazione"]), Convert.ToInt32(idTipoOnere) );

					HelpIcon helpIcon = new HelpIcon();
					helpIcon.ID = "helpIcon" + idxControllo.ToString();
					helpIcon.HelpControl = helpDiv.ID;
					helpIcon.Style.Add("clear", "both");

					ImageButton imgBtn = new ImageButton();
					imgBtn.ID = "linkButton" + idxControllo.ToString();
					imgBtn.Click += new ImageClickEventHandler(OnEditRiduzioni);
					imgBtn.ImageUrl = "~/Images/edit.gif";

					if (dtb.ValoreDouble != 0.0d && valorePercRiduzione == 0)
					{
						imgBtn.OnClientClick = "return ModificaNote('" + Token + "','" + ContribT.Id.ToString() + "','" + rowView["IdDestinazione"].ToString() + "','" + idTipoOnere + "')";
					}
					else
					{
						imgBtn.OnClientClick = "return ModificaRiduzioni('" + Token + "','" + ContribT.Id.ToString() + "','" + rowView["IdDestinazione"].ToString() + "')";
					}

					e.Item.Cells[i].Controls.Clear();
					e.Item.Cells[i].Controls.Add(l);
					e.Item.Cells[i].Controls.Add(dtb);
					e.Item.Cells[i].Controls.Add(helpDiv);
					e.Item.Cells[i].Controls.Add(imgBtn);
					e.Item.Cells[i].Controls.Add(helpIcon);

					idxControllo++;
				}
				else if (nomeColonna.IndexOf("_costoTot") > 0)
				{
					string idTipoOnere = nomeColonna.Replace("_costoTot", "");

					Literal lt = new Literal();
					lt.ID = "literal_" + idTipoOnere;
					lt.Text = "<b>" + Convert.ToDouble(e.Item.Cells[i].Text).ToString("N2") + "</b>";

					e.Item.Cells[i].Controls.Clear();

					e.Item.Cells[i].Controls.Add(lt);
				}
				else if (nomeColonna == "Cubatura")
				{
					Literal lt = new Literal();
					lt.ID = "literalCubatura";
					lt.Text = "<b>" + Convert.ToDouble(e.Item.Cells[i].Text).ToString("N2") + "</b>";

					e.Item.Cells[i].Controls.Clear();

					e.Item.Cells[i].Controls.Add(lt);
				}
				else if (nomeColonna.IndexOf("_percriduzione") > 0)
				{
					e.Item.Cells[i].Visible = false;
				}


				if (nomeColonna == "Cubatura" || 
					nomeColonna.IndexOf("_costoTot") > 0 || 
					nomeColonna.IndexOf("_costom") > 0 ||
					nomeColonna.IndexOf("_riduzione") > 0)
				{
					e.Item.Cells[i].Style.Add("text-align", "right");
				}
			}
		}
		#endregion


		#region Visualizzazione del footer
		if (e.Item.ItemType == ListItemType.Footer)
		{
			for (int i = 0; i < dt.Columns.Count; i++)
			{
				string nomeColonna = dt.Columns[i].ColumnName;

				if (nomeColonna.IndexOf("_percriduzione") > 0)
				{
					e.Item.Cells[i].Visible = false;
				}else if (nomeColonna == "Cubatura" || nomeColonna.IndexOf("_costoTot") > 0)
				{
					double totale = TotaleColonna(dt, nomeColonna);

					Literal lt = new Literal();
					lt.Text = "<b>" + totale.ToString("N2") + "</b>";

					e.Item.Cells[i].Controls.Clear();
					e.Item.Cells[i].Controls.Add(lt);

					e.Item.Cells[i].Style.Add("text-align", "right");
				}
				else
				{
					e.Item.Cells[i].Style.Add("border", "0px");
				}
			}
		}
		#endregion
	}

	void OnEditRiduzioni(object sender, ImageClickEventArgs e)
	{
		RebindDgCoefficienti();
	}



	private string GetListaRiduzioni(int idDestinazione , int idTipoOnere)
	{
        int idContribT = ContribT.Id.GetValueOrDefault(int.MinValue);
		OICalcoloContribRRiduzMgr mgr = new OICalcoloContribRRiduzMgr( Database );

		List < OICalcoloContribRRiduz> lst = mgr.GetRiduzioniDaContribtDestinazioneTipoOnere(IdComune, idContribT, idDestinazione, idTipoOnere);

		if (lst.Count == 0)
		{
			// Nessuna riduzione, mostro le eventuali note
			OICalcoloContribRMgr crMgr = new OICalcoloContribRMgr(Database);
            OICalcoloContribR r = crMgr.GetByContribTTipoOnereDestinazione(IdComune, ContribT.Id.GetValueOrDefault(int.MinValue), idTipoOnere, idDestinazione);

			if ( r!= null && !String.IsNullOrEmpty(r.Note))
				return r.Note;

			return "Nessuna riduzione o incremento";
		}

		double totaleRiduzioni = 0.0d;

		StringBuilder sb = new StringBuilder();
		sb.Append("<table width='600px'>");
		sb.Append("<colgroup width='80%'>");
		sb.Append("<colgroup width='20%'>");

		for (int i = 0; i < lst.Count; i++)
		{
			sb.Append("<tr><td style='color:#000'>").Append(lst[i].Riduzione.ToString());
            sb.Append("</td><td style='text-align:right;color:#000'>").Append(lst[i].Riduzioneperc.GetValueOrDefault(double.MinValue).ToString("N2")).Append("%</td></tr>");

			if (!String.IsNullOrEmpty(lst[i].Note))
			{
				sb.Append("<tr><td style='padding-left:10px'><i>").Append(lst[i].Note).Append("</i></td><td>&nbsp;</td></tr>");
			}

            totaleRiduzioni += lst[i].Riduzioneperc.GetValueOrDefault(0);
		}

		sb.Append("<tr><td colspan='2' style='text-align:right;color:#000'>---------------</td></tr>");
		sb.Append("<tr><td style='color:#000'>Totale</td><td style='text-align:right;color:#000'>").Append(totaleRiduzioni.ToString("N2")).Append("%</td></tr>");

		sb.Append("</table>");

		return sb.ToString();
	}

	private double TotaleColonna(DataTable dt, string columnName)
	{
		double totale = 0.0d;

		for (int i = 1; i < dt.Rows.Count; i++)
		{
			totale += Convert.ToDouble(dt.Rows[i][columnName]);
		}
	

		return totale;
	}

	protected override void OnInit(EventArgs e)
	{
		base.OnInit(e);

		BindDgCoefficienti();
	}
	#endregion

	#endregion

	private void Chiudi()
    {
        string url = string.Empty;

        if (ContribT.PrimoInserimento)
        {
    		url = "~/Istanze/CalcoloOneri/Urbanizzazione/OICalcoloTot.aspx?Token={0}&IdCalcoloTot={1}";
        }
        else
        {
            url = "~/Istanze/CalcoloOneri/Urbanizzazione/OICalcoloContribT.aspx?Token={0}&IdContribT={2}";
        }
		
        Response.Redirect(string.Format(url, AuthenticationInfo.Token,  ContribT.FkOictId, ContribT.Id ) );
	}



	protected void cmdChiudiDettaglio_Click(object sender, EventArgs e)
	{
		Chiudi();
	}



	protected void cmdProcedi_Click(object sender, EventArgs e)
	{
		OICalcoloContribTMgr tmgr = new OICalcoloContribTMgr(Database);

        ContribT.FkAreeCodiceareaZto = null;
        if(pnlZonaOmogenea.Visible)
            ContribT.FkAreeCodiceareaZto = Convert.ToInt32(ddlZonaOmogenea.SelectedValue);

        ContribT.FkAreeCodiceareaPrg = null;
        if(pnlZonaPrg.Visible)
		    ContribT.FkAreeCodiceareaPrg = Convert.ToInt32(ddlZonaPrg.SelectedValue);

        ContribT.FkOinId = null;
        if (pnlTipiIntervento.Visible)
		    ContribT.FkOinId = Convert.ToInt32(ddlTipoIntervento.SelectedValue);

        ContribT.FkOitId = null;
        if (pnlIndiciTerritoriali.Visible)
		    ContribT.FkOitId = Convert.ToInt32(ddlIndiciTerritoriali.SelectedValue);

        ContribT.FkOclaId = null;
        if (pnlClassiAdddetti.Visible)
		    ContribT.FkOclaId = Convert.ToInt32(ddlAddetti.SelectedValue);

        ContribT.FkOinIdTabd = null;
        if (pnlInterventiTabD.Visible)
		    ContribT.FkOinIdTabd = Convert.ToInt32(ddlInterventiTabD.SelectedValue);

		tmgr.Update(ContribT);

		new OICalcoloContribTMgr(Database).Elabora(ContribT);

		DataBindPannelloDettaglio();
	}



	protected void cmdChiudiCalcolo_Click(object sender, EventArgs e)
	{
        string url = "~/Istanze/CalcoloOneri/Urbanizzazione/OICalcoloTot.aspx?Token={0}&CodiceIstanza={1}&IdCalcoloTot={2}";
        Response.Redirect(string.Format(url, AuthenticationInfo.Token, ContribT.Codiceistanza, ContribT.FkOictId));
	}
	protected void cmdMOdificaTestata_Click(object sender, EventArgs e)
	{
		DataBindPannelloModifica();
	}
	
    

	protected void cmdSalva_Click(object sender, EventArgs e)
	{
		OICalcoloContribRMgr mgr = new OICalcoloContribRMgr(Database);

		foreach (DataGridItem it in dgDettagliCalcolo.Items)
		{
			int idxControllo = 0;

			while (true)
			{
				HiddenField hf	  = (HiddenField)it.FindControl("hiddenField" + idxControllo.ToString());
				//DoubleTextBox dtbCostom = (DoubleTextBox)it.FindControl("doubleTextBox" + idxControllo.ToString());
				DoubleTextBox dtbRiduzione = (DoubleTextBox)it.FindControl("doubleTextBoxRiduzione" + idxControllo.ToString());

				if (hf == null)
					break;

				int idDestinazione = Convert.ToInt32( dgDettagliCalcolo.DataKeys[ it.ItemIndex ] );
				int idTipoOnere = Convert.ToInt32(hf.Value);

				//double costom	= String.IsNullOrEmpty(dtbCostom.Text) ? 0.0d : dtbCostom.ValoreDouble;
				double riduzione= String.IsNullOrEmpty(dtbRiduzione.Text) ? 0.0d : dtbRiduzione.ValoreDouble;

                mgr.UpdateRiduzioneDaIdDestinazioneTipoOnere(IdComune, ContribT.Id.GetValueOrDefault(int.MinValue), idDestinazione, idTipoOnere, riduzione);


				idxControllo++;
			}
		}

		RebindDgCoefficienti();
	}

	private void RebindDgCoefficienti()
	{
        DataGridSource = new OICalcoloContribTMgr(Database).GeneraDatasetContributoUrbanizzazione(IdComune, ContribT.Id.GetValueOrDefault(int.MinValue));

		BindDgCoefficienti();
	}



}
