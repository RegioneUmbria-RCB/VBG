using Init.SIGePro.Data;
using Init.SIGePro.Exceptions.IstanzeAllegati;
using Init.SIGePro.Manager;
using Init.SIGePro.Manager.Logic.Ricerche;
using Init.Utils;
using Init.Utils.Web.UI;
using PersonalLib2.Data;
using Sigepro.net.Archivi.DatiDinamici;
using SIGePro.Net;
using SIGePro.WebControls.Ajax;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web;
using System.Web.UI.WebControls;
using System.Xml;
using static Init.SIGePro.Manager.Dyn2ModelliDMgr;

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
            var modello = (Dyn2ModelliT)HttpContext.Current.Items["Dyn2ModelliT"];

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
            return this.Modello.Software;
        }
    }

    private int IdModello => Convert.ToInt32(this.Request.QueryString[Constants.QuerystringIdModello]);


    public Dyn2ModelliT Modello
    {
        get
        {

            return new CacheModello().GetModello(this.Database, this.IdComune, this.IdModello);
        }
    }




    protected void Page_Load(object sender, EventArgs e)
    {
        this.cmdPreview.OnClientClick = "javascript:MostraAnteprima(" + this.Modello.Id + ");return false;";

        this.ImpostaScriptEliminazione(this.cmdElimina);

        if (!this.IsPostBack)
        {
            this.ddlTipoCampo.Item.Items.Add(new ListItem("Testo", Constants.ID_CAMPO_TESTO));
            this.ddlTipoCampo.Item.Items.Add(new ListItem("Campo dinamico", Constants.ID_CAMPO_DATO_DINAMICO));

            this.ddlBaseTipoTesto.Item.DataSource = new Dyn2BaseTipiTestoMgr(this.Database).GetList(new Dyn2BaseTipiTesto());
            this.ddlBaseTipoTesto.Item.DataBind();

            this.rplCampoDinamico.InitParams["idContesto"] = this.Modello.FkD2bcId;

            this.RebindListaCampi();
        }
    }

    private void BindDettaglio(Dyn2ModelliD cls)
    {
        this.multiView.ActiveViewIndex = Constants.ViewIdDettaglio;

        this.IsInserting = cls.Id.GetValueOrDefault(int.MinValue) == int.MinValue;

        this.lblId.Item.Text = this.IsInserting ? "Nuovo" : cls.Id.ToString();

        this.txtPosOrizzontale.Item.ValoreInt = cls.Posorizzontale;
        this.txtPosVerticale.Item.ValoreInt = cls.Posverticale;

        this.ddlTipoCampo.Value = (cls.FkD2cId.GetValueOrDefault(int.MinValue) == int.MinValue) ? Constants.ID_CAMPO_TESTO : Constants.ID_CAMPO_DATO_DINAMICO;

        // Forzo il cambiamento del valore del tipo di controllo per nascondere e visualizzare il panel giusto
        this.ddlTipoCampo_ValueChanged(this, EventArgs.Empty);

        // Inizializzazione dei controlli relativi al tipo di controllo legato al modello
        this.rplCampoDinamico.Class = null;

        this.ddlBaseTipoTesto.Item.SelectedIndex = 0;

        this.txtTestoEsteso.Value = String.Empty;

        // Popolamento dei controlli relativi al tipo di controllo legato al modello
        if (this.ddlTipoCampo.Value == Constants.ID_CAMPO_TESTO)
        {
            if (cls.FkD2mdtId.GetValueOrDefault(int.MinValue) != int.MinValue)
            {
                var testo = new Dyn2ModelliDTestiMgr(this.Database).GetById(this.IdComune, cls.FkD2mdtId.GetValueOrDefault(int.MinValue));
                this.ddlBaseTipoTesto.Value = testo.FkD2bttId;
                this.txtTestoEsteso.Value = testo.Testo;
            }
        }
        else
        {
            if (cls.FkD2cId.GetValueOrDefault(int.MinValue) != int.MinValue)
                this.rplCampoDinamico.Class = new Dyn2CampiMgr(this.Database).GetById(this.IdComune, cls.FkD2cId.GetValueOrDefault(int.MinValue));
        }

        this.rplCampoDinamico.Visible = true;
        this.ddlTipoCampo.Visible = true;

        this.cmdElimina.Visible = !this.IsInserting;
        this.cmdCreaCampo.Visible = this.IsInserting;

        // Se è parte di nua riga multipla rendo impossibile la modifica dell'indice di riga,
        // del tipo campo e del campo dinamico
        var multiplo = cls.FlgMultiplo.GetValueOrDefault(0) == 1;

        this.txtPosVerticale.Enabled =
        this.ddlTipoCampo.Enabled =
        this.rplCampoDinamico.Enabled = true;

        if (multiplo)
        {
            this.txtPosVerticale.Enabled = false;
            this.ddlTipoCampo.Enabled = false;
            this.rplCampoDinamico.Enabled = false;
        }

        // Se il modello è stato storicizzato ed è presente in una o più istanze/anagrafiche/attività
        // mostro l'avvertimento che lo storico verrà eliminato
        if (!this.IsInserting && this.ddlTipoCampo.Value != Constants.ID_CAMPO_TESTO)
        {
            if (new Dyn2ModelliDMgr(this.Database).VerificaPresenzaInStorico(this.IdComune, cls.Id.Value))
            {
                // Registro lo script di richiesta conferma
                var idMessaggio = "dyn2modellicampi.confirm.eliminazione_dastorico";
                var msg = new LayoutTestiBaseMgr(this.Database).GetValoreTesto(idMessaggio, this.IdComune, this.Software);

                var js = "function RichiediConfermaModifica(){return confirm(\"" + msg + "\");}";

                this.Page.ClientScript.RegisterClientScriptBlock(this.GetType(), "confermaStorico", js, true);

                this.cmdElimina.OnClientClick = "return RichiediConfermaModifica() && ConfermaEliminazione();";
                this.cmdSalva.OnClientClick = "return RichiediConfermaModifica();";
            }
        }

        this.cmdModificaDettagli.Visible = false;

        if (!this.IsInserting && cls.FkD2cId.HasValue)
        {
            this.cmdModificaDettagli.Visible = true;
            this.cmdModificaDettagli.OnClientClick = String.Format("MostraDettaglioCampo({0});return false;", cls.FkD2cId.Value);
        }
    }

    protected void multiView_ActiveViewChanged(object sender, EventArgs e)
    {
        switch (this.multiView.ActiveViewIndex)
        {
            case (Constants.ViewIdRisultato):
                this.Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Risultato;
                return;
            case (Constants.ViewIdDettaglio):
                this.Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;
                return;
        }
    }




    #region Scheda lista
    public void cmdChiudiLista_Click(object sender, EventArgs e)
    {
        var fmtUrl = "~/Archivi/DatiDinamici/Dyn2Modelli.aspx?Token={0}&Software={1}&IdModello={2}";

        this.Response.Redirect(string.Format(fmtUrl, this.Token, this.Modello.Software, this.Modello.Id));
    }

    protected void gvLista_SelectedIndexChanged(object sender, EventArgs e)
    {
        var id = Convert.ToInt32(this.gvLista.DataKeys[this.gvLista.SelectedIndex].Value);

        var cls = new Dyn2ModelliDMgr(this.Database).GetById(this.IdComune, id);

        this.BindDettaglio(cls);
    }
    #endregion


    #region Scheda dettaglio

    protected void cmdSalva_Click(object sender, EventArgs e)
    {
        var mgr = new Dyn2ModelliDMgr(this.Database);
        var mgrTesti = new Dyn2ModelliDTestiMgr(this.Database);

        Dyn2ModelliD cls = null;

        if (this.IsInserting)
        {
            cls = new Dyn2ModelliD
            {
                Idcomune = this.IdComune,
                FkD2mtId = this.Modello.Id
            };
        }
        else
        {
            var id = Convert.ToInt32(this.lblId.Item.Text);
            cls = mgr.GetById(this.IdComune, id);
        }

        try
        {
            // Verifico i valori di posizione orizzontale e verticale
            var posVerticale = this.txtPosVerticale.Item.ValoreInt.GetValueOrDefault(int.MinValue);
            var posOrizzontale = this.txtPosOrizzontale.Item.ValoreInt.GetValueOrDefault(int.MinValue);

            if (posVerticale == int.MinValue)
                posVerticale = mgr.GetProssimaPosizioneVerticale(this.IdComune, this.Modello.Id.GetValueOrDefault(int.MinValue));

            if (posOrizzontale == int.MinValue)
                posOrizzontale = mgr.GetProssimaPosizioneOrizzontale(this.IdComune, this.Modello.Id.GetValueOrDefault(int.MinValue), posVerticale);

            cls.Posverticale = posVerticale;
            cls.Posorizzontale = posOrizzontale;

            // Verifico il tipo di campo
            if (this.ddlTipoCampo.Value == Constants.ID_CAMPO_TESTO)
            {
                // Se il campo è di tipo testo verifico che all'interno sia contenuto solo testo o un xml valido
                try
                {
                    this.ValidaXmlInInput(this.txtTestoEsteso.Value);
                }
                catch (Exception ex)
                {
                    this.Errori.Add(ex.Message);
                    return;
                }

                var testo = new Dyn2ModelliDTesti
                {
                    Idcomune = this.IdComune
                };

                if (cls.FkD2mdtId.GetValueOrDefault(int.MinValue) != int.MinValue)
                {
                    testo = mgrTesti.GetById(this.IdComune, cls.FkD2mdtId.GetValueOrDefault(int.MinValue));
                }

                testo.FkD2bttId = this.ddlBaseTipoTesto.Value;
                testo.Testo = this.txtTestoEsteso.Value;

                testo = (cls.FkD2mdtId.GetValueOrDefault(int.MinValue) == int.MinValue) ? mgrTesti.Insert(testo) : mgrTesti.Update(testo);

                cls.FkD2mdtId = testo.Id;
                cls.FkD2cId = null;
            }
            else
            {
                // il campo è un campo dinamico
                if (cls.FkD2mdtId.GetValueOrDefault(int.MinValue) != int.MinValue)
                {
                    mgrTesti.Delete(mgrTesti.GetById(this.IdComune, cls.FkD2mdtId.GetValueOrDefault(int.MinValue)));
                }

                cls.FkD2mdtId = null;
                cls.FkD2cId = Convert.ToInt32(this.rplCampoDinamico.Value);
            }

            this.Database.BeginTransaction();

            if (this.IsInserting)
                cls = mgr.Insert(cls);
            else
                cls = mgr.Update(cls);

            this.Database.CommitTransaction();

            //cmdChiudiDettaglio_Click(this, EventArgs.Empty);
            this.BindDettaglio(cls);
        }
        catch (RequiredFieldException rfe)
        {
            this.Database.RollbackTransaction();

            this.MostraErrore("Attenzione, i campi contrassegnati con un asterisco sono obbligatori.", rfe);
        }
        catch (Exception ex)
        {
            this.MostraErrore(this.IsInserting ? AmbitoErroreEnum.Inserimento : AmbitoErroreEnum.Aggiornamento, ex);
        }
    }


    protected void cmdVerificaCampi_Click(object sender, EventArgs e)
    {
        var modelliMgr = new Dyn2ModelliDMgr(this.Database);
        var campiMgr = new Dyn2CampiMgr(this.Database);
        var testiMgr = new Dyn2ModelliDTestiMgr(this.Database);

        var listaCampi = modelliMgr.GetListByIdModello(this.IdComune, this.Modello.Id.Value);

        var erroriTrovati = false;

        foreach (var rigaCampi in listaCampi)
        {
            var testo = String.Empty;

            if (rigaCampi.FkD2cId.HasValue)
            {
                testo = campiMgr.GetById(this.IdComune, rigaCampi.FkD2cId.Value).Descrizione;
            }
            else
            {
                testo = testiMgr.GetById(this.IdComune, rigaCampi.FkD2mdtId.Value).Testo;
            }

            try
            {
                this.ValidaXmlInInput(testo);
            }
            catch (Exception ex)
            {
                this.Errori.Add(String.Format("Errore nel campo alla riga {0} e colonna {1}: {2}", rigaCampi.Posverticale, rigaCampi.Posorizzontale, ex.Message));
                erroriTrovati = true;
            }
        }

        if (!erroriTrovati)
        {
            this.Page.ClientScript.RegisterStartupScript(this.GetType(), "nessunErroreTrovato", "alert('Il modello non contiene errori.');", true);
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
        var mgr = new Dyn2ModelliDMgr(this.Database);

        var id = Convert.ToInt32(this.lblId.Item.Text);

        var cls = mgr.GetById(this.IdComune, id);

        try
        {
            this.Database.BeginTransaction();

            mgr.Delete(cls);

            this.Database.CommitTransaction();

            this.cmdChiudiDettaglio_Click(sender, e);


        }
        catch (Exception ex)
        {
            this.Database.RollbackTransaction();

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

        this.ScrollTo = this.lblId.Item.Text;

        this.RebindListaCampi();

        this.multiView.ActiveViewIndex = 0;
    }
    #endregion

    protected void cmdNuovo_Click(object sender, EventArgs e)
    {
        this.BindDettaglio(new Dyn2ModelliD());
    }
    protected void ddlTipoCampo_ValueChanged(object sender, EventArgs e)
    {
        this.pnlCampoTesto.Visible = this.ddlTipoCampo.Value == Constants.ID_CAMPO_TESTO;

        this.pnlCampoDinamico.Visible = !this.pnlCampoTesto.Visible;

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
                                              Dictionary<string, string> initParams, string idContesto)
    {
        try
        {
            var sc = new RicerchePlusSearchComponent(token, dataClassType, targetPropertyName, descriptionPropertyNames, prefixText, count, software, ricercaSoftwareTT, initParams);

            // Gestione di una ricerca custom
            sc.Searching += delegate (object sender, RicerchePlusEventArgs e)
            {
                var d2c = (Dyn2Campi)e.SearchedClass;

                d2c.OthersWhereClause.Add(" (fk_d2bc_id='" + idContesto + "' or fk_d2bc_id is null or fk_d2bc_id='')");

            };

            return RicerchePlusCtrl.CreateResultList(sc.Find(true));
        }
        catch (Exception ex)
        {
            return RicerchePlusCtrl.CreateErrorResult(ex);
        }
    }

    #endregion

    private void RebindListaCampi()
    {
        var mgr = new Dyn2ModelliDMgr(this.Database);

        var listaCampi = mgr.GetCampiByIdModello(this.IdComune, this.IdModello);

        this.gvLista.DataSource = listaCampi;
        this.gvLista.DataBind();

        var tags = listaCampi.SelectMany(x => x.GetTags()).Distinct().ToArray();

        this.rptTagsDataList.DataSource = tags;
        this.rptTagsDataList.DataBind();

        this.rptDataList.DataSource = mgr.GetListaAutocompleteFontiInterne(this.IdComune);
        this.DataBind();
    }

    protected void cmdRicalcolaNumerazione_Click(object sender, EventArgs e)
    {
        var mgr = new Dyn2ModelliDMgr(this.Database);
        mgr.RicalcolaNumerazioneRighe(this.IdComune, this.IdModello);

        this.RebindListaCampi();
    }

    protected void RigaMultiplaCheckedChanged(object sender, EventArgs e)
    {
        var cb = (CheckBox)sender;
        var hf = (HiddenField)cb.NamingContainer.FindControl("hidRiga");

        try
        {
            new Dyn2ModelliDMgr(this.Database).ImpostaRigaMultipla(this.IdComune, this.IdModello, Convert.ToInt32(hf.Value), cb.Checked ? 1 : 0);

            this.RebindListaCampi();
        }
        catch (Exception ex)
        {
            cb.Checked = !cb.Checked;
            this.MostraErrore(ex);
            this.Errori.Add(ex.Message);
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

    private bool gruppoMultiplo = false;

    protected void gvLista_RowDataBound(object sender, GridViewRowEventArgs e)
    {
        if (e.Row.RowType == DataControlRowType.Header)
        {
            this.gruppoMultiplo = false;
        }

        if (e.Row.RowType == DataControlRowType.DataRow)
        {
            var dataItem = (ListaCampiModelloBindingItem)e.Row.DataItem;

            var tagsEditor = (TagsEditor)e.Row.FindControl("tagsEditor");
            tagsEditor.IdRiga = dataItem.Id;
            tagsEditor.Tags = dataItem.Tags;
            tagsEditor.DataBind();



            var ddlMultiplo = (DropDownList)e.Row.FindControl("ddlMultiplo");

            ddlMultiplo.SelectedValue = dataItem.FlgMultiplo.ToString();

            // e.Row.Cells[3].CssClass = dataItem.IsCampoDinamico ? "cella-campo" : "cella-testo";
            var sb = new StringBuilder();
            sb.Append(dataItem.IsCampoDinamico ? "cella-campo" : "cella-testo");
            sb.Append(" ");
            sb.Append(dataItem.FlgMultiplo == 0 ? "riga-singola" : "riga-multipla");
            sb.Append(" ");
            sb.Append(dataItem.FlgSpezzaTabella ? "spezza-tabella" : "");

            if (!this.gruppoMultiplo && dataItem.FlgMultiplo != 0)
            {
                sb.Append(" ");
                sb.Append("inizio-gruppo-multiplo");

                this.gruppoMultiplo = true;
            }

            if (this.gruppoMultiplo && dataItem.FlgMultiplo == 0)
            {
                var grid = (GridViewEx)e.Row.NamingContainer;

                grid.Rows[e.Row.RowIndex - 1].CssClass += " fine-gruppo-multiplo";
                this.gruppoMultiplo = false;
            }

            e.Row.CssClass = sb.ToString();
        }
    }

    protected void ddlMultiplo_SelectedIndexChanged(object sender, EventArgs e)
    {
        var ddl = (DropDownList)sender;
        var hf = (HiddenField)ddl.NamingContainer.FindControl("hidRiga");

        try
        {
            new Dyn2ModelliDMgr(this.Database).ImpostaRigaMultipla(this.IdComune, this.Modello.Id.Value, Convert.ToInt32(hf.Value), Convert.ToInt32(ddl.SelectedValue));

            this.RebindListaCampi();
        }
        catch (Exception ex)
        {
            ddl.SelectedIndex = 0;
            this.MostraErrore(ex);
            this.Errori.Add(ex.Message);
        }
    }

    protected void chkSpezzaTabella_CheckedChanged(object sender, EventArgs e)
    {
        var chk = (CheckBox)sender;
        var id = Convert.ToInt32(this.gvLista.DataKeys[((GridViewRow)chk.NamingContainer).RowIndex].Value);
        try
        {
            new Dyn2ModelliDMgr(this.Database).ImpostaSpezzaTabella(this.IdComune, this.Modello.Id.Value, id, chk.Checked);

            this.RebindListaCampi();
        }
        catch (Exception ex)
        {

            this.MostraErrore(ex);
            this.Errori.Add(ex.Message);
        }
    }

    protected void bmFonteDati_OkClicked(object sender, EventArgs e)
    {
        try
        {
            var id = Convert.ToInt32(this.hidIdRigaModello.Value);
            var fonteInterna = this.txtFonteInterna.Value;

            new Dyn2ModelliDMgr(this.Database).AggiornaFontiDati(this.IdComune, id, fonteInterna, "");

            this.RebindListaCampi();
        }
        catch (Exception ex)
        {
            this.Errori.Add(ex.Message);
        }
    }

    protected void cmdFormule_Click(object sender, EventArgs e)
    {
        var fmtUrl = "~/Archivi/DatiDinamici/Dyn2ModelliFormule.aspx?Token={0}&IdModello={1}";

        this.Response.Redirect(string.Format(fmtUrl, this.Token, this.IdModello));
    }

    protected void TagsEditor1_TagsSaved(object sender, TagsSavedEventArgs e)
    {
        try
        {
            var mgr = new Dyn2ModelliDMgr(this.Database);
            mgr.AggiornaTags(this.IdComune, e.IdRiga, e.Tags);

            this.RebindListaCampi();
        }
        catch (Exception ex)
        {
            this.MostraErrore("Si è verificato un errore durante il salvataggio dei tag", ex);
        }
    }
}
