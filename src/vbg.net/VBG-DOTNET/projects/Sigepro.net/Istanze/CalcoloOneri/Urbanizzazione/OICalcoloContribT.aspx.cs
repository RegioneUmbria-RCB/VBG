using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using Init.Utils.Web.UI;
using SIGePro.Net;
using System;
using System.Data;
using System.Text;
using System.Web.UI;
using System.Web.UI.WebControls;

public partial class Istanze_CalcoloOneri_Urbanizzazione_OICalcoloContribT : BasePage
{
    public override string Software
    {
        get
        {
            var ist = new IstanzeMgr(this.Database).GetById(this.IdComune, this.ContribT.Codiceistanza.Value);
            return ist.SOFTWARE;
        }
    }

    public Istanze_CalcoloOneri_Urbanizzazione_OICalcoloContribT()
    {
        //VerificaSoftware = false;
    }

    private readonly string errMsgCampoNonTrovato = "Nell'attuale configurazione del listino coefficienti non è stato trovato il valore ({0}-{1}).<br>Il tasto \"Procedi\" riporta un coefficiente uguale a zero.<br>Il tasto \"Chiudi\" non modifica il precedente calcolo.";

    private OICalcoloContribT m_contribT = null;
    private OICalcoloContribT ContribT
    {
        get
        {
            if (this.m_contribT == null)
            {
                var id = Convert.ToInt32(this.Request.QueryString["IdContribT"]);
                this.m_contribT = new OICalcoloContribTMgr(this.Database).GetById(this.IdComune, id);
            }

            return this.m_contribT;
        }
    }


    protected void Page_Load(object sender, EventArgs e)
    {
        this.Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;

        if (!this.IsPostBack)
        {
            if (this.ContribT.PrimoInserimento)
                this.DataBindPannelloModifica();
            else
                this.DataBindPannelloDettaglio();
        }

    }

    #region Binding dei dati

    /// <summary>
    /// Effettua il binding dei dati del pannello di modifica
    /// </summary>
    public void DataBindPannelloModifica()
    {
        this.multiView.ActiveViewIndex = 0;

        var tmgr = new OICalcoloContribTMgr(this.Database);

        var listaZo = tmgr.GetValoriComboZonaOmogenea(this.IdComune, this.ContribT.Id.GetValueOrDefault(int.MinValue));

        this.lbltipoDestinazione.Text = new OCCBaseDestinazioniMgr(this.Database).GetById(this.ContribT.FkOccbdeId).Destinazione;

        this.pnlZonaOmogenea.Visible = listaZo.Count > 0;

        if (this.pnlZonaOmogenea.Visible)
        {

            this.labelZonaOmogenea1.Text = tmgr.GetDescrizioneComboZonaOmogenea(this.IdComune, this.ContribT.Id.GetValueOrDefault(int.MinValue));

            this.ddlZonaOmogenea.DataSource = listaZo;
            this.ddlZonaOmogenea.DataBind();

            this.ImpostaValoreCombo(this.ddlZonaOmogenea, this.ContribT.FkAreeCodiceareaZto.GetValueOrDefault(int.MinValue), this.pnlErroreZonaOmogenea, this.errMsgCampoNonTrovato, delegate (DropDownList ddl, int val)
            {
                var area = new AreeMgr(this.Database).GetById(val.ToString(), this.IdComune);
                ddl.Items.Add(new ListItem(area.DENOMINAZIONE, area.CODICEAREA));
            });
        }

        this.ddlZonaOmogenea_SelectedIndexChanged(this.ddlZonaOmogenea, EventArgs.Empty);

        if (this.pnlZonaPrg.Visible)
        {
            this.labelZonaPrg1.Text = tmgr.GetDescrizioneComboZonaPrg(this.IdComune, this.ContribT.Id.GetValueOrDefault(int.MinValue));

            this.ImpostaValoreCombo(this.ddlZonaPrg, this.ContribT.FkAreeCodiceareaPrg.GetValueOrDefault(int.MinValue), this.pnlErroreZonaPrg, this.errMsgCampoNonTrovato, delegate (DropDownList ddl, int val)
            {
                var area = new AreeMgr(this.Database).GetById(val.ToString(), this.IdComune);
                ddl.Items.Add(new ListItem(area.DENOMINAZIONE, area.CODICEAREA));
            });
        }

        this.ddlZonaPrg_SelectedIndexChanged(this, EventArgs.Empty);

        if (this.pnlTipiIntervento.Visible)
        {
            this.ImpostaValoreCombo(this.ddlTipoIntervento, this.ContribT.FkOinId.GetValueOrDefault(int.MinValue), this.pnlErroreTipoIntervento, this.errMsgCampoNonTrovato, delegate (DropDownList ddl, int val)
            {
                var interv = new OInterventiMgr(this.Database).GetById(this.IdComune, val);
                ddl.Items.Add(new ListItem(interv.Intervento, interv.Id.ToString()));
            });
        }

        this.ddlTipoIntervento_SelectedIndexChanged(this, EventArgs.Empty);

        if (this.pnlIndiciTerritoriali.Visible)
        {
            this.ImpostaValoreCombo(this.ddlIndiciTerritoriali, this.ContribT.FkOitId.GetValueOrDefault(int.MinValue), this.pnlErroreIndiciTerritoriali, this.errMsgCampoNonTrovato, delegate (DropDownList ddl, int val)
            {
                var it = new OIndiciTerritorialiMgr(this.Database).GetById(this.IdComune, val);
                ddl.Items.Add(new ListItem(it.ToString(), it.Id.ToString()));
            });
        }


        var listaInterventiTabd = tmgr.GetValoriComboInterventiTabd(this.IdComune, this.ContribT.Id.GetValueOrDefault(int.MinValue));

        this.pnlInterventiTabD.Visible = listaInterventiTabd.Count > 0;

        if (this.pnlInterventiTabD.Visible)
        {
            this.ddlInterventiTabD.DataSource = listaInterventiTabd;
            this.ddlInterventiTabD.DataBind();

            this.ImpostaValoreCombo(this.ddlInterventiTabD, this.ContribT.FkOinIdTabd.GetValueOrDefault(int.MinValue), this.pnlErroreInterventiTabD, this.errMsgCampoNonTrovato, delegate (DropDownList ddl, int val)
            {
                var it = new OInterventiMgr(this.Database).GetById(this.IdComune, val);
                ddl.Items.Add(new ListItem(it.Intervento, it.Id.ToString()));
            });
        }

        this.ddlInterventiTabD_SelectedIndexChanged(this, EventArgs.Empty);


        if (this.pnlClassiAdddetti.Visible)
        {
            this.ImpostaValoreCombo(this.ddlAddetti, this.ContribT.FkOclaId.GetValueOrDefault(int.MinValue), this.pnlErroreClassiAddetti, this.errMsgCampoNonTrovato, delegate (DropDownList ddl, int val)
            {
                var it = new OClassiAddettiMgr(this.Database).GetById(this.IdComune, val);
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
        catch (ArgumentOutOfRangeException)
        {
            if (callbackErrore != null)
            {
                callbackErrore(combo, valoreSelezionato);
                combo.SelectedValue = valoreSelezionato.ToString();
            }

            if (panelErrore != null)
            {
                panelErrore.Visible = true;

                var l = new Literal();
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
        this.multiView.ActiveViewIndex = 1;

        var tmgr = new OICalcoloContribTMgr(this.Database);

        this.lblEdtTipoDestinazione.Text = new OCCBaseDestinazioniMgr(this.Database).GetById(this.ContribT.FkOccbdeId).Destinazione;

        this.pnlEdtInterventiTabd.Visible = this.ContribT.FkOinIdTabd.GetValueOrDefault(int.MinValue) != int.MinValue;

        if (this.pnlEdtInterventiTabd.Visible)
            this.lblInterventiTabd.Text = new OInterventiMgr(this.Database).GetById(this.IdComune, this.ContribT.FkOinIdTabd.GetValueOrDefault(int.MinValue)).Intervento;


        this.pnlEdtClassiAddetti.Visible = this.ContribT.FkOclaId.GetValueOrDefault(int.MinValue) != int.MinValue;

        if (this.pnlEdtClassiAddetti.Visible)
            this.lblClassiAddetti.Text = new OClassiAddettiMgr(this.Database).GetById(this.IdComune, this.ContribT.FkOclaId.GetValueOrDefault(int.MinValue)).Classe;



        this.pnlEdtIndiciTerritoriali.Visible = this.ContribT.FkOitId.GetValueOrDefault(int.MinValue) != int.MinValue;

        if (this.pnlEdtIndiciTerritoriali.Visible)
        {
            var it = new OIndiciTerritorialiMgr(this.Database).GetById(this.IdComune, this.ContribT.FkOitId.GetValueOrDefault(int.MinValue));

            this.lblIndiciTerritoriali.Text = it.ToString();
        }



        this.pnlEdtTipoIntervento.Visible = this.ContribT.FkOinId.GetValueOrDefault(int.MinValue) != int.MinValue;

        if (this.pnlEdtTipoIntervento.Visible)
            this.lblTipoIntervento.Text = new OInterventiMgr(this.Database).GetById(this.IdComune, this.ContribT.FkOinId.GetValueOrDefault(int.MinValue)).Intervento;


        this.pnlEdtZonaOmogenea.Visible = this.ContribT.FkAreeCodiceareaZto.GetValueOrDefault(int.MinValue) != int.MinValue;

        if (this.pnlEdtZonaOmogenea.Visible)
        {
            this.labelZonaOmogenea2.Text = tmgr.GetDescrizioneComboZonaOmogenea(this.IdComune, this.ContribT.Id.GetValueOrDefault(int.MinValue));
            this.lblZonaOmogenea.Text = new AreeMgr(this.Database).GetById(this.ContribT.FkAreeCodiceareaZto.ToString(), this.IdComune).DENOMINAZIONE;
        }

        this.pnlEdtZonaPrg.Visible = this.ContribT.FkAreeCodiceareaPrg.GetValueOrDefault(int.MinValue) != int.MinValue;

        if (this.pnlEdtZonaPrg.Visible)
        {
            this.labelZonaPrg2.Text = tmgr.GetDescrizioneComboZonaPrg(this.IdComune, this.ContribT.Id.GetValueOrDefault(int.MinValue));
            this.lblZonaPrg.Text = new AreeMgr(this.Database).GetById(this.ContribT.FkAreeCodiceareaPrg.ToString(), this.IdComune).DENOMINAZIONE;
        }

        this.DataGridSource = new OICalcoloContribTMgr(this.Database).GeneraDatasetContributoUrbanizzazione(this.IdComune, this.ContribT.Id.GetValueOrDefault(int.MinValue));

        this.BindDgCoefficienti();

    }

    #region gestione delle combo a cascata
    protected void ddlZonaOmogenea_SelectedIndexChanged(object sender, EventArgs e)
    {
        var tmgr = new OICalcoloContribTMgr(this.Database);

        var idZto = this.pnlZonaOmogenea.Visible ? Convert.ToInt32(this.ddlZonaOmogenea.SelectedValue) : int.MinValue;

        var listaPrg = tmgr.GetValoriComboZonaPrg(this.IdComune, this.ContribT.Id.GetValueOrDefault(int.MinValue), idZto);

        this.pnlZonaPrg.Visible = listaPrg.Count > 0;

        if (listaPrg.Count > 0)
        {
            this.ddlZonaPrg.DataSource = listaPrg;
            this.ddlZonaPrg.DataBind();
        }

        this.ddlZonaPrg_SelectedIndexChanged(this, EventArgs.Empty);
    }

    protected void ddlZonaPrg_SelectedIndexChanged(object sender, EventArgs e)
    {
        var tmgr = new OICalcoloContribTMgr(this.Database);

        var idZto = this.pnlZonaOmogenea.Visible ? Convert.ToInt32(this.ddlZonaOmogenea.SelectedValue) : int.MinValue;
        var idPrg = this.pnlZonaPrg.Visible ? Convert.ToInt32(this.ddlZonaPrg.SelectedValue) : int.MinValue;

        var listaTipiInt = tmgr.GetValoriComboTipoIntervento(this.IdComune, this.ContribT.Id.GetValueOrDefault(int.MinValue), idZto, idPrg);

        this.pnlTipiIntervento.Visible = listaTipiInt.Count > 0;

        if (listaTipiInt.Count > 0)
        {
            this.ddlTipoIntervento.DataSource = listaTipiInt;
            this.ddlTipoIntervento.DataBind();
        }

        this.ddlTipoIntervento_SelectedIndexChanged(this, EventArgs.Empty);
    }

    protected void ddlTipoIntervento_SelectedIndexChanged(object sender, EventArgs e)
    {
        var tmgr = new OICalcoloContribTMgr(this.Database);

        var idZto = this.pnlZonaOmogenea.Visible ? Convert.ToInt32(this.ddlZonaOmogenea.SelectedValue) : int.MinValue;
        var idPrg = this.pnlZonaPrg.Visible ? Convert.ToInt32(this.ddlZonaPrg.SelectedValue) : int.MinValue;
        var idInterv = this.pnlTipiIntervento.Visible ? Convert.ToInt32(this.ddlTipoIntervento.SelectedValue) : int.MinValue;

        var listaIndiciTerr = tmgr.GetValoriComboIndiciTerritoriali(this.IdComune, this.ContribT.Id.GetValueOrDefault(int.MinValue), idZto, idPrg, idInterv);

        this.pnlIndiciTerritoriali.Visible = listaIndiciTerr.Count > 0;

        if (listaIndiciTerr.Count > 0)
        {
            this.ddlIndiciTerritoriali.DataSource = listaIndiciTerr;
            this.ddlIndiciTerritoriali.DataBind();
        }
    }

    protected void ddlInterventiTabD_SelectedIndexChanged(object sender, EventArgs e)
    {
        var idIntervento = this.pnlInterventiTabD.Visible ? Convert.ToInt32(this.ddlInterventiTabD.SelectedValue) : int.MinValue;

        var tmgr = new OICalcoloContribTMgr(this.Database);

        var listaClassiAddetti = tmgr.GetValoriComboClassiAddetti(this.IdComune, this.ContribT.Id.GetValueOrDefault(int.MinValue), idIntervento);

        this.pnlClassiAdddetti.Visible = listaClassiAddetti.Count > 0;

        if (listaClassiAddetti.Count > 0)
        {
            this.ddlAddetti.DataSource = listaClassiAddetti;
            this.ddlAddetti.DataBind();
        }
    }


    #endregion

    #region Binding della griglia dati
    private DataSet DataGridSource
    {
        get { return (DataSet)this.Session["OICalcoloContribTDataGridSource"]; }
        set { this.Session["OICalcoloContribTDataGridSource"] = value; }
    }

    private void BindDgCoefficienti()
    {
        this.dgDettagliCalcolo.DataSource = this.DataGridSource;
        this.dgDettagliCalcolo.DataBind();
    }


    protected void dgDettagliCalcolo_ItemDataBound(object sender, DataGridItemEventArgs e)
    {
        var rowView = (DataRowView)e.Item.DataItem;

        //Visualizazione delle colonne (bisogna fare così perchè la proprietà autogenerateColumns è true)
        if (this.DataGridSource == null) return;

        var dt = this.DataGridSource.Tables[0];

        // Di default la prima colonna non è visibile
        e.Item.Cells[0].Visible = false;

        #region visualizzazione delle intestazioni
        if (e.Item.ItemType == ListItemType.Header)
        {
            for (var i = 0; i < dt.Columns.Count; i++)
            {
                var nomeColonna = dt.Columns[i].ColumnName;

                if (nomeColonna == "Destinazione" || nomeColonna == "Cubatura")
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

                    var idTipoOnere = Convert.ToInt32(nomeColonna.Replace("_costoTot", ""));
                    e.Item.Cells[i].Text = new OTipiOneriMgr(this.Database).GetById(this.IdComune, idTipoOnere).Descrizione;
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
                for (var i = 0; i < dt.Columns.Count; i++)
                {
                    var nomeColonna = dt.Columns[i].ColumnName;
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

            var idxControllo = 0;

            for (var i = 0; i < dt.Columns.Count; i++)
            {
                var nomeColonna = dt.Columns[i].ColumnName;
                if (nomeColonna.IndexOf("_costom") > 0)
                {
                    var idTipoOnere = nomeColonna.Replace("_costom", "");

                    var hf = new HiddenField();
                    hf.ID = "hiddenField" + idxControllo.ToString();
                    hf.Value = idTipoOnere;

                    var l = new Literal();
                    l.Text = Convert.ToDouble(rowView[nomeColonna]).ToString("N2");

                    e.Item.Cells[i].Controls.Clear();
                    e.Item.Cells[i].Controls.Add(l);
                    //e.Item.Cells[i].Controls.Add(dtb);
                    e.Item.Cells[i].Controls.Add(hf);



                }
                else if (nomeColonna.IndexOf("_riduzione") > 0)
                {
                    var idTipoOnere = nomeColonna.Replace("_riduzione", "");

                    var l = new Literal();
                    l.Text = "€&nbsp;";

                    var valorePercRiduzione = Convert.ToDecimal(rowView[idTipoOnere + "_percriduzione"]);
                    //int valorePercRiduzione = Convert.ToInt32(rowView[idTipoOnere + "_percriduzione"]);


                    var dtb = new DecimalTextBox();
                    dtb.ID = "doubleTextBoxRiduzione" + idxControllo.ToString();
                    dtb.ValoreDecimal = Convert.ToDecimal(rowView[idTipoOnere + "_riduzione"]);
                    dtb.Columns = 5;
                    dtb.ReadOnly = valorePercRiduzione > 0.0m;

                    var helpDiv = new HelpDiv();
                    helpDiv.ID = "helpDiv" + idxControllo.ToString();
                    helpDiv.Text = this.GetListaRiduzioni(Convert.ToInt32(rowView["IdDestinazione"]), Convert.ToInt32(idTipoOnere));

                    var helpIcon = new HelpIcon();
                    helpIcon.ID = "helpIcon" + idxControllo.ToString();
                    helpIcon.HelpControl = helpDiv.ID;
                    helpIcon.Style.Add("clear", "both");

                    var imgBtn = new ImageButton();
                    imgBtn.ID = "linkButton" + idxControllo.ToString();
                    imgBtn.Click += new ImageClickEventHandler(this.OnEditRiduzioni);
                    imgBtn.ImageUrl = "~/Images/edit.gif";

                    if (dtb.ValoreDecimal.GetValueOrDefault(0.0m) != 0.0m && valorePercRiduzione == 0.0m)
                    {
                        imgBtn.OnClientClick = "return ModificaNote('" + this.Token + "','" + this.ContribT.Id.ToString() + "','" + rowView["IdDestinazione"].ToString() + "','" + idTipoOnere + "')";
                    }
                    else
                    {
                        imgBtn.OnClientClick = "return ModificaRiduzioni('" + this.Token + "','" + this.ContribT.Id.ToString() + "','" + rowView["IdDestinazione"].ToString() + "')";
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
                    var idTipoOnere = nomeColonna.Replace("_costoTot", "");

                    var lt = new Literal();
                    lt.ID = "literal_" + idTipoOnere;
                    lt.Text = "<b>" + Convert.ToDouble(e.Item.Cells[i].Text).ToString("N2") + "</b>";

                    e.Item.Cells[i].Controls.Clear();

                    e.Item.Cells[i].Controls.Add(lt);
                }
                else if (nomeColonna == "Cubatura")
                {
                    var lt = new Literal();
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
            for (var i = 0; i < dt.Columns.Count; i++)
            {
                var nomeColonna = dt.Columns[i].ColumnName;

                if (nomeColonna.IndexOf("_percriduzione") > 0)
                {
                    e.Item.Cells[i].Visible = false;
                }
                else if (nomeColonna == "Cubatura" || nomeColonna.IndexOf("_costoTot") > 0)
                {
                    var totale = this.TotaleColonna(dt, nomeColonna);

                    var lt = new Literal();
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

    private void OnEditRiduzioni(object sender, ImageClickEventArgs e)
    {
        // this.RebindDgCoefficienti();
        // Non fa niente, la logica è stata spostata su cmdAggiorna_Click, in questo modo quando si chiude la finestra di modifica delle riduzioni viene ricaricata la griglia con i nuovi dati
    }



    private string GetListaRiduzioni(int idDestinazione, int idTipoOnere)
    {
        var idContribT = this.ContribT.Id.GetValueOrDefault(int.MinValue);
        var mgr = new OICalcoloContribRRiduzMgr(this.Database);

        var lst = mgr.GetRiduzioniDaContribtDestinazioneTipoOnere(this.IdComune, idContribT, idDestinazione, idTipoOnere);

        if (lst.Count == 0)
        {
            // Nessuna riduzione, mostro le eventuali note
            var crMgr = new OICalcoloContribRMgr(this.Database);
            var r = crMgr.GetByContribTTipoOnereDestinazione(this.IdComune, this.ContribT.Id.GetValueOrDefault(int.MinValue), idTipoOnere, idDestinazione);

            if (r != null && !String.IsNullOrEmpty(r.Note))
                return r.Note;

            return "Nessuna riduzione o incremento";
        }

        var totaleRiduzioni = 0.0m;

        var sb = new StringBuilder();
        sb.Append("<table width='600px'>");
        sb.Append("<colgroup width='80%'>");
        sb.Append("<colgroup width='20%'>");

        for (var i = 0; i < lst.Count; i++)
        {
            sb.Append("<tr><td style='color:#000'>").Append(lst[i].Riduzione.ToString());
            sb.Append("</td><td style='text-align:right;color:#000'>").Append(lst[i].Riduzioneperc.GetValueOrDefault(0.0m).ToString("N2")).Append("%</td></tr>");

            if (!String.IsNullOrEmpty(lst[i].Note))
            {
                sb.Append("<tr><td style='padding-left:10px'><i>").Append(lst[i].Note).Append("</i></td><td>&nbsp;</td></tr>");
            }

            totaleRiduzioni += lst[i].Riduzioneperc.GetValueOrDefault(0.0m);
        }

        sb.Append("<tr><td colspan='2' style='text-align:right;color:#000'>---------------</td></tr>");
        sb.Append("<tr><td style='color:#000'>Totale</td><td style='text-align:right;color:#000'>").Append(totaleRiduzioni.ToString("N2")).Append("%</td></tr>");

        sb.Append("</table>");

        return sb.ToString();
    }

    private double TotaleColonna(DataTable dt, string columnName)
    {
        var totale = 0.0d;

        for (var i = 1; i < dt.Rows.Count; i++)
        {
            totale += Convert.ToDouble(dt.Rows[i][columnName]);
        }


        return totale;
    }

    protected override void OnInit(EventArgs e)
    {
        base.OnInit(e);

        this.BindDgCoefficienti();
    }
    #endregion

    #endregion

    private void Chiudi()
    {
        var url = string.Empty;

        if (this.ContribT.PrimoInserimento)
        {
            url = "~/Istanze/CalcoloOneri/Urbanizzazione/OICalcoloTot.aspx?Token={0}&IdCalcoloTot={1}";
        }
        else
        {
            url = "~/Istanze/CalcoloOneri/Urbanizzazione/OICalcoloContribT.aspx?Token={0}&IdContribT={2}";
        }

        this.Response.Redirect(string.Format(url, this.AuthenticationInfo.Token, this.ContribT.FkOictId, this.ContribT.Id));
    }



    protected void cmdChiudiDettaglio_Click(object sender, EventArgs e)
    {
        this.Chiudi();
    }



    protected void cmdProcedi_Click(object sender, EventArgs e)
    {
        var tmgr = new OICalcoloContribTMgr(this.Database);

        this.ContribT.FkAreeCodiceareaZto = null;
        if (this.pnlZonaOmogenea.Visible)
            this.ContribT.FkAreeCodiceareaZto = Convert.ToInt32(this.ddlZonaOmogenea.SelectedValue);

        this.ContribT.FkAreeCodiceareaPrg = null;
        if (this.pnlZonaPrg.Visible)
            this.ContribT.FkAreeCodiceareaPrg = Convert.ToInt32(this.ddlZonaPrg.SelectedValue);

        this.ContribT.FkOinId = null;
        if (this.pnlTipiIntervento.Visible)
            this.ContribT.FkOinId = Convert.ToInt32(this.ddlTipoIntervento.SelectedValue);

        this.ContribT.FkOitId = null;
        if (this.pnlIndiciTerritoriali.Visible)
            this.ContribT.FkOitId = Convert.ToInt32(this.ddlIndiciTerritoriali.SelectedValue);

        this.ContribT.FkOclaId = null;
        if (this.pnlClassiAdddetti.Visible)
            this.ContribT.FkOclaId = Convert.ToInt32(this.ddlAddetti.SelectedValue);

        this.ContribT.FkOinIdTabd = null;
        if (this.pnlInterventiTabD.Visible)
            this.ContribT.FkOinIdTabd = Convert.ToInt32(this.ddlInterventiTabD.SelectedValue);

        tmgr.Update(this.ContribT);

        new OICalcoloContribTMgr(this.Database).Elabora(this.ContribT);

        this.DataBindPannelloDettaglio();
    }



    protected void cmdChiudiCalcolo_Click(object sender, EventArgs e)
    {
        var url = "~/Istanze/CalcoloOneri/Urbanizzazione/OICalcoloTot.aspx?Token={0}&CodiceIstanza={1}&IdCalcoloTot={2}";
        this.Response.Redirect(string.Format(url, this.AuthenticationInfo.Token, this.ContribT.Codiceistanza, this.ContribT.FkOictId));
    }
    protected void cmdMOdificaTestata_Click(object sender, EventArgs e)
    {
        this.DataBindPannelloModifica();
    }



    protected void cmdSalva_Click(object sender, EventArgs e)
    {
        var mgr = new OICalcoloContribRMgr(this.Database);

        foreach (DataGridItem it in this.dgDettagliCalcolo.Items)
        {
            var idxControllo = 0;

            while (true)
            {
                var hf = (HiddenField)it.FindControl("hiddenField" + idxControllo.ToString());
                var dtbRiduzione = (DecimalTextBox)it.FindControl("doubleTextBoxRiduzione" + idxControllo.ToString());

                if (hf == null)
                    break;

                var idDestinazione = Convert.ToInt32(this.dgDettagliCalcolo.DataKeys[it.ItemIndex]);
                var idTipoOnere = Convert.ToInt32(hf.Value);

                //double costom	= String.IsNullOrEmpty(dtbCostom.Text) ? 0.0d : dtbCostom.ValoreDouble;
                var riduzione = dtbRiduzione.ValoreDecimal.GetValueOrDefault(0.0m);

                mgr.UpdateRiduzioneDaIdDestinazioneTipoOnere(this.IdComune, this.ContribT.Id.GetValueOrDefault(int.MinValue), idDestinazione, idTipoOnere, riduzione);


                idxControllo++;
            }
        }

        this.RebindDgCoefficienti();
    }

    private void RebindDgCoefficienti()
    {
        this.DataGridSource = new OICalcoloContribTMgr(this.Database).GeneraDatasetContributoUrbanizzazione(this.IdComune, this.ContribT.Id.GetValueOrDefault(int.MinValue));

        this.BindDgCoefficienti();
    }

    protected void cmdAggiorna_Click(object sender, EventArgs e)
    {
        this.RebindDgCoefficienti();
    }
}
