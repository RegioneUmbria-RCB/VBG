using Init.SIGePro.Data;
using Init.SIGePro.DatiDinamici.WebControls;
using Init.SIGePro.Exceptions.IstanzeAllegati;
using Init.SIGePro.Manager;
using Init.Utils.Web.UI;
using Ninject;
using SIGePro.Manager.Verticalizzazioni;
using SIGePro.Manager.VerticalizzazioniBase;
using SIGePro.Net;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.UI.WebControls;
using VBG.DatiDinamici.Contesti;
using VBG.DatiDinamici.Interfaces;
using VBG.DatiDinamici.Scripts;
using VBG.DatiDinamici.Web;
using VBG.DatiDinamici.WebControls;

public partial class Archivi_DatiDinamici_Dyn2Campi : BasePage
{
    [Inject]
    public IVerticalizzazioniFactory _verticalizzazioniFactory { get; set; }


    public string IdCampo
    {
        get { return this.Request.QueryString["IdCampo"]; }
    }

    public bool PopupCreaNuovo
    {
        get
        {
            if (string.IsNullOrEmpty(this.Request.QueryString["PopupCreaNuovo"]))
                return false;

            return Convert.ToBoolean(this.Request.QueryString["PopupCreaNuovo"]);
        }
    }

    private readonly Lazy<VerticalizzazioneFvgSol> _verticalizzazioneFVG;


    public Archivi_DatiDinamici_Dyn2Campi()
    {
        this._verticalizzazioneFVG = new Lazy<VerticalizzazioneFvgSol>(() => this._verticalizzazioniFactory.Create<VerticalizzazioneFvgSol>(this.AuthenticationInfo.Alias, this.Software));
    }

    protected void Page_Load(object sender, EventArgs e)
    {
        this.ImpostaScriptEliminazione(this.cmdElimina);

        if (!this.IsPostBack)
        {
            var dictionary = ControlliDatiDinamiciDictionary.GetCampiDesignSupportati(this._verticalizzazioneFVG.Value.Attiva);
            var tipiCampi = dictionary
                            .Keys
                            .Select(key => new ListItem(dictionary[key].Descrizione, key.ToString()))
                            .OrderBy(x => x.Text)
                            .ToArray();

            this.ddlTipoDato.Item.Items.AddRange(tipiCampi);


            foreach (var key in DatiDinamiciInfoDictionary.Items.Keys)
                this.ddlContesto.Item.Items.Add(new ListItem(DatiDinamiciInfoDictionary.Items[key].Descrizione, ContestoTranslator.ContestoEnumToContestoBase(key)));

            this.ddlContesto.Item.Items.Insert(0, new ListItem("Nessuno (funzioni non disponibili)", ""));

            if (!String.IsNullOrEmpty(this.IdCampo))
            {
                this.BindDettaglio(new Dyn2CampiMgr(this.Database).GetById(this.IdComune, Convert.ToInt32(this.IdCampo)));
                return;
            }

            if (this.PopupCreaNuovo)
            {
                this.BindDettaglio(new Dyn2Campi());
                return;
            }

        }
    }

    private void BindDettaglio(Dyn2Campi cls)
    {
        this.multiView.ActiveViewIndex = 2;

        this.IsInserting = cls.Id.GetValueOrDefault(int.MinValue) == int.MinValue;

        this.lblId.Item.Text = this.IsInserting ? "Nuovo" : cls.Id.ToString();

        this.txtNomeCampo.Item.Text = cls.Nomecampo;
        this.txtEtichetta.Item.Text = cls.Etichetta;
        this.txtDescrizione.Item.Text = cls.Descrizione;

        this.ddlTipoDato.Item.SelectedValue = cls.Tipodato;
        this.ddlContesto.Item.SelectedValue = cls.FkD2bcId;

        if (!this.IsInserting)
        {
            this.BindGrigliaProprieta();
            this.BindGrigliaSchede(cls);
        }

        this.AggiornaVisibilitaEtichettaDescrizione();


        this.cmdEditFormule.Visible = this.cmdElimina.Visible = !this.IsInserting;

        this.pnlProprietaControllo.Visible = !this.IsInserting;

        if (String.IsNullOrEmpty(cls.FkD2bcId))
            this.cmdEditFormule.Visible = false;

        if (cls.FkD2bcId == ContestoTranslator.ContestoEnumToContestoBase(ContestoModelloEnum.Mercati))
            this.cmdEditFormule.Visible = false;


        var scripts = new Dyn2CampiScriptMgr(this.Database).GetScriptsCampo(this.IdComune, Convert.ToInt32(this.IdCampo));

        if (scripts.Count > 0)
            this.cmdEditFormule.Visible = true;

        if (this.cmdEditFormule.Visible)
            this.cmdEditFormule.Visible = !new Dyn2CampiMgr(this.Database).VerificaPresenzaInRigheMultiple(this.IdComune, Convert.ToInt32(this.IdCampo));

        if (this.ContieneScriptPerEvento(scripts, TipoScriptEnum.Modifica))
        {
            this.cmdEditFormule.Visible = true;
            this.Errori.Add("Attenzione, il campo contiene formule nell'evento di modifica. Per migliorare le prestazioni delle schede dinamiche spostare la formula nel modello");
        }

        if (this.ContieneScriptPerEvento(scripts, TipoScriptEnum.Caricamento))
        {
            this.cmdEditFormule.Visible = true;
            this.Errori.Add("Attenzione, il campo contiene formule nell'evento di caricamento. Per migliorare le prestazioni delle schede dinamiche spostare la formula nel modello");
        }

        if (this.ContieneScriptPerEvento(scripts, TipoScriptEnum.Salvataggio))
        {
            this.cmdEditFormule.Visible = true;
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
        var d2ModelliMgr = new Dyn2ModelliTMgr(this.Database);
        var listaSchede = d2ModelliMgr.GetSchedeContenentiIlCampo(this.IdComune, cls.Id);

        this.gvSchedeDelCampo.DataSource = listaSchede;
        this.gvSchedeDelCampo.DataBind();

        this.pnlCampoCompareInSchede.Visible = listaSchede.Count() > 0;
    }

    private ControlliDatiDinamiciDictionaryItem GetTipoControlloDaTipoDato(TipoControlloEnum tipoControllo)
    {
        var dictionary = ControlliDatiDinamiciDictionary.GetCampiDesignSupportati(true);

        return dictionary[tipoControllo];
    }


    private void AggiornaVisibilitaEtichettaDescrizione()
    {
        var tipoDato = (TipoControlloEnum)Enum.Parse(typeof(TipoControlloEnum), this.ddlTipoDato.Value);
        var controlloItem = this.GetTipoControlloDaTipoDato(tipoDato);

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
        var id = Convert.ToInt32(this.lblId.Value);
        var tipoDato = (TipoControlloEnum)Enum.Parse(typeof(TipoControlloEnum), this.ddlTipoDato.Value);
        var controlloItem = this.GetTipoControlloDaTipoDato(tipoDato);

        // Proprietà designer
        var proprietaControllo = controlloItem.ProprietaEditabili;
        var proprietaValorizzate = new Dyn2CampiMgr(this.Database).GetProprietaControllo(this.IdComune, id);

        proprietaValorizzate.ForEach(prop =>
        {
            if (proprietaControllo.ContainsKey(prop.Proprieta))
                proprietaControllo[prop.Proprieta].Value = prop.Valore;
        });

        // Se è attivo il parametro "etichetta a destra" della verticalizzazione FVG_SOL 
        // e sto creando un controllo di tipo checkbox allora imposto di default il valore della proprietà
        // "Etichetta a destra" a 1)
        var verticalizzazioneFvgSol = this._verticalizzazioneFVG.Value;

        if (verticalizzazioneFvgSol.Attiva && verticalizzazioneFvgSol.CheckboxEtichettaADestra && tipoDato == TipoControlloEnum.Checkbox)
        {
            if (String.IsNullOrEmpty(proprietaControllo["EtichettaADestra"].Value))
            {
                proprietaControllo["EtichettaADestra"].Value = "true";
            }
        }

        this.gvProprietaControllo.DataSource = proprietaControllo.Values;
        this.gvProprietaControllo.DataBind();

        this.pnlProprietaControllo.Visible = proprietaControllo.Values.Any();

        // Help del controllo
        var helpControllo = controlloItem.HelpControllo;

        this.ddlTipoDato.HelpControl = String.IsNullOrEmpty(helpControllo) ? String.Empty : "helpTipoControllo";
        this.helpTipoControllo.Text = helpControllo;

        if (helpControllo != null && helpControllo.Length > 600)
            this.helpTipoControllo.CssClass = "HelpControllo large";
    }

    protected void multiView_ActiveViewChanged(object sender, EventArgs e)
    {
        switch (this.multiView.ActiveViewIndex)
        {
            case (1):
                this.Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Risultato;
                return;
            case (2):
                this.Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;
                return;
            default:
                this.Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Ricerca;
                return;
        }
    }

    #region Scheda ricerca
    public void cmdCerca_Click(object sender, EventArgs e)
    {
        this.gvLista.DataBind();

        this.multiView.ActiveViewIndex = 1;
    }

    public void cmdNuovo_Click(object sender, EventArgs e)
    {
        this.BindDettaglio(new Dyn2Campi());
    }

    public void cmdChiudi_Click(object sender, EventArgs e)
    {
        base.CloseCurrentPage();
    }
    #endregion


    #region Scheda lista
    public void cmdChiudiLista_Click(object sender, EventArgs e)
    {
        this.multiView.ActiveViewIndex = 0;
    }

    protected void gvLista_SelectedIndexChanged(object sender, EventArgs e)
    {
        var id = Convert.ToInt32(this.gvLista.DataKeys[this.gvLista.SelectedIndex].Value);

        var cls = new Dyn2CampiMgr(this.Database).GetById(this.IdComune, id);

        this.BindDettaglio(cls);
    }
    #endregion


    #region Scheda dettaglio
    protected void cmdSalva_Click(object sender, EventArgs e)
    {
        var mgr = new Dyn2CampiMgr(this.Database);
        var mgrProp = new Dyn2CampiProprietaMgr(this.Database);

        Dyn2Campi cls = null;

        if (this.IsInserting)
        {
            cls = new Dyn2Campi();
            cls.Idcomune = this.IdComune;
            cls.Software = this.Software;
        }
        else
        {
            var id = Convert.ToInt32(this.lblId.Item.Text);

            cls = mgr.GetById(this.IdComune, id);
        }

        try
        {
            if (this.txtEtichetta.Value.Length > 2000)
            {
                throw new Exception("Il campo " + this.txtEtichetta.Descrizione + " non può contenere più di 2000 caratteri");
            }

            if (this.txtDescrizione.Value.Length > 4000)
            {
                throw new Exception("Il campo " + this.txtDescrizione.Descrizione + " non può contenere più di 2000 caratteri");
            }


            cls.Nomecampo = this.txtNomeCampo.Value.ToUpper();
            cls.Etichetta = this.txtEtichetta.Value;
            cls.Descrizione = this.txtDescrizione.Value;
            cls.Tipodato = this.ddlTipoDato.Value;
            cls.FkD2bcId = this.ddlContesto.Value == String.Empty ? (string)null : this.ddlContesto.Value;
            //cls.Obbligatorio = chkObbligatorio.Item.Checked ? 1 : 0;

            if (this.IsInserting)
            {
                cls = mgr.Insert(cls);

                var verticalizzazioneFvgSol = this._verticalizzazioniFactory.Create<VerticalizzazioneFvgSol>(this.AuthenticationInfo.Alias, this.Software);

                if (verticalizzazioneFvgSol.Attiva && verticalizzazioneFvgSol.CheckboxEtichettaADestra && cls.Tipodato == TipoControlloEnum.Checkbox.ToString())
                {
                    var prop = new Dyn2CampiProprieta();
                    prop.Idcomune = this.IdComune;
                    prop.FkD2cId = cls.Id;
                    prop.Proprieta = "EtichettaADestra";
                    prop.Valore = "true";

                    mgrProp.Insert(prop);
                }

            }
            else
            {
                cls = mgr.Update(cls);

                mgrProp.DeleteByIdCampo(this.IdComune, cls.Id.GetValueOrDefault(int.MinValue));

                foreach (RepeaterItem row in this.gvProprietaControllo.Items)
                {
                    var txtProprieta = (LabeledTextBox)row.FindControl("txtProprieta");
                    var ddlProprieta = (LabeledDropDownList)row.FindControl("ddlProprieta");
                    var hfPropId = (HiddenField)row.FindControl("hfPropId");

                    var valore = txtProprieta.Visible ? txtProprieta.Value : ddlProprieta.Value;

                    if (String.IsNullOrEmpty(valore)) continue;

                    var prop = new Dyn2CampiProprieta();
                    prop.Idcomune = this.IdComune;
                    prop.FkD2cId = cls.Id;
                    prop.Proprieta = hfPropId.Value;
                    prop.Valore = valore;

                    mgrProp.Insert(prop);
                }
            }


            var eventSrc = $@"
                const event = new CustomEvent('{(this.IsInserting ? "campo-creato" : "campo-modificato")}', {{ 
                    detail: {{
                        codice: {cls.Id.Value.ToString()}, 
                        descrizione:'{cls.Nomecampo}'
                    }}
                }});

                window.opener?.dispatchEvent(event);
            ";

            this.ClientScript.RegisterClientScriptBlock(this.GetType(), "evento-campo-creato", eventSrc, true);


            this.BindDettaglio(cls);
        }
        catch (RequiredFieldException rfe)
        {
            this.MostraErrore("Attenzione, i campi contrassegnati con un asterisco sono obbligatori.", rfe);
        }
        catch (Exception ex)
        {
            this.MostraErrore(this.IsInserting ? AmbitoErroreEnum.Inserimento : AmbitoErroreEnum.Aggiornamento, ex);
        }
    }

    protected void cmdEditFormule_Click(object sender, EventArgs e)
    {
        var fmtUrl = "~/Archivi/DatiDinamici/Dyn2CampiFormule.aspx?Token={0}&IdCampo={1}&Popup={2}";
        this.Response.Redirect(String.Format(fmtUrl, this.AuthenticationInfo.Token, this.lblId.Value, this.IsInPopup));
    }

    protected void cmdElimina_Click(object sender, EventArgs e)
    {
        var mgr = new Dyn2CampiMgr(this.Database);

        var id = Convert.ToInt32(this.lblId.Item.Text);

        var cls = mgr.GetById(this.IdComune, id);

        try
        {
            mgr.Delete(cls);

            this.cmdChiudiDettaglio_Click(sender, e);
        }
        catch (Exception ex)
        {
            this.MostraErrore(AmbitoErroreEnum.Cancellazione, ex);
        }
    }

    protected void cmdChiudiDettaglio_Click(object sender, EventArgs e)
    {
        if (this.IsInPopup)
        {
            this.Page.ClientScript.RegisterStartupScript(this.GetType(), "closeScript", "self.close();", true);

            return;
        }

        var fmtUrl = "~/Archivi/DatiDinamici/Dyn2Campi.aspx?Token={0}&Software={1}";
        this.Response.Redirect(String.Format(fmtUrl, this.AuthenticationInfo.Token, this.Software));
    }
    #endregion

    #region decodifica dei valori dei dati
    public string TraduciTipoDato(object tipoDato)
    {
        var key = (TipoControlloEnum)Enum.Parse(typeof(TipoControlloEnum), tipoDato.ToString());

        /*		try
                {*/
        return this.GetTipoControlloDaTipoDato(key)?.Descrizione;
        /*	}
            catch (Exception)
            {
                return key.ToString();
            }*/
    }

    public string TraduciContesto(object contesto)
    {
        if (contesto == null || string.IsNullOrEmpty(contesto.ToString())) return "Tutti";

        var contestoEnum = ContestoTranslator.ContestoBaseToContestoEnum(contesto.ToString());

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
        if (!this.IsInserting)
        {
            this.BindGrigliaProprieta();
        }

        this.AggiornaVisibilitaEtichettaDescrizione();
    }

    protected void ddlContesto_ValueChanged(object sender, EventArgs e)
    {
        if (!this.IsInserting)
        {
            this.cmdEditFormule.Visible = !String.IsNullOrEmpty(this.ddlContesto.Value);
        }
    }

    protected void gvProprietaControllo_ItemDataBound(object sender, RepeaterItemEventArgs e)
    {
        if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
        {
            var dataIt = e.Item.DataItem as EditablePropertyDetails;

            var txtProprieta = (LabeledTextBox)e.Item.FindControl("txtProprieta");
            var ddlProprieta = (LabeledDropDownList)e.Item.FindControl("ddlProprieta");

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
                catch (Exception)
                {
                    ddlProprieta.Item.SelectedIndex = 0;
                }
            }

        }
    }

}
