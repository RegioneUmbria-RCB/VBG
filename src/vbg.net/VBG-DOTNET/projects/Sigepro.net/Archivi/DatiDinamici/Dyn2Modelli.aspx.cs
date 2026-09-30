using Init.SIGePro.Data;
using Init.SIGePro.Exceptions.IstanzeAllegati;
using Init.SIGePro.Manager;
using Init.SIGePro.Manager.Logic.Ricerche;
using Init.Utils.Web.UI;
using SIGePro.Net;
using SIGePro.WebControls.Ajax;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.UI.WebControls;
using VBG.DatiDinamici.Contesti;

public partial class Archivi_DatiDinamici_Dyn2Modelli : BasePage
{
    private static class Constants
    {
        public const string FiltriRicercaSessionKey = "FiltriRicercaSessionKey";
    }

    public string IdModello
    {
        get { return this.Request.QueryString["IdModello"]; }
    }

    public bool RipristinaFiltri
    {
        get { return !String.IsNullOrEmpty(this.Request.QueryString["ripristinaFiltri"]); }
    }

    public bool MostraInterventiUtilizzo
    {
        get => (bool)(this.ViewState["MostraInterventiUtilizzo"] ?? false);
        set => this.ViewState["MostraInterventiUtilizzo"] = value;
    }

    public bool MostraEndoUtilizzo
    {
        get => (bool)(this.ViewState["MostraEndoUtilizzo"] ?? false);
        set => this.ViewState["MostraEndoUtilizzo"] = value;
    }

    protected void Page_Load(object sender, EventArgs e)
    {
        this.ImpostaScriptEliminazione(this.cmdElimina);

        if (!this.IsPostBack)
        {
            foreach (var key in DatiDinamiciInfoDictionary.Items.Keys)
            {
                this.ddlSrcContesto.Item.Items.Add(new ListItem(DatiDinamiciInfoDictionary.Items[key].Descrizione, ContestoTranslator.ContestoEnumToContestoBase(key)));
                this.ddlContesto.Item.Items.Add(new ListItem(DatiDinamiciInfoDictionary.Items[key].Descrizione, ContestoTranslator.ContestoEnumToContestoBase(key)));
            }

            this.ddlSrcContesto.Item.Items.Insert(0, new ListItem("", ""));

            if (this.RipristinaFiltri)
            {
                this.RipristinaFiltriRicerca();

                //cmdCerca_Click(this, EventArgs.Empty);
            }

            if (!String.IsNullOrEmpty(this.IdModello))
            {
                this.BindDettaglio(new Dyn2ModelliTMgr(this.Database).GetById(this.IdComune, Convert.ToInt32(this.IdModello)));
            }
        }
    }

    private void SalvaFiltriRicerca()
    {
        var filtri = new Dictionary<string, string>();

        foreach (var control in this.ricercaView.Controls)
        {
            if (control is LabeledControlBase)
            {
                var ctrl = control as LabeledControlBase;

                filtri[ctrl.ID] = ctrl.Value;
            }
        }

        this.Session[Constants.FiltriRicercaSessionKey] = filtri;
    }

    private void RipristinaFiltriRicerca()
    {
        if (this.Session[Constants.FiltriRicercaSessionKey] == null)
        {
            return;
        }

        var filtri = (Dictionary<string, string>)this.Session[Constants.FiltriRicercaSessionKey];

        foreach (var idControllo in filtri.Keys)
        {
            var ctrl = (LabeledControlBase)this.ricercaView.FindControl(idControllo);

            ctrl.Value = filtri[idControllo];
        }
    }

    private void SvuotaFiltriRicerca()
    {
        this.Session.Remove(Constants.FiltriRicercaSessionKey);
    }

    private void BindDettaglio(Dyn2ModelliT cls)
    {
        this.multiView.ActiveViewIndex = 2;

        this.IsInserting = cls.Id.GetValueOrDefault(int.MinValue) == int.MinValue;

        this.lblId.Item.Text = this.IsInserting ? "Nuovo" : cls.Id.ToString();
        this.txtDescrizione.Value = cls.Descrizione;
        this.ddlContesto.Value = cls.FkD2bcId;
        this.chkMultiplo.Item.Checked = cls.Modellomultiplo.GetValueOrDefault(0) == 1;
        this.chkStoricizza.Item.Checked = cls.FlgStoricizza.GetValueOrDefault(0) == 1;
        this.chkReadOnly.Item.Checked = cls.FlgReadonlyWeb.GetValueOrDefault(0) == 1;
        this.cmdGestioneCampi.Visible = this.cmdFormule.Visible = this.cmdElimina.Visible = !this.IsInserting;
        this.txtCodiceScheda.Value = cls.CodiceScheda;

        this.chkMultiplo.Visible = true;

        this.ImpostaVisibilitaCampiDaIdContesto(this.ddlContesto.Value);

        // Lettura degli interventi a cui è collegato il modello
        this.MostraInterventiUtilizzo = false;
        this.MostraEndoUtilizzo = false;

        if (cls.Id.HasValue)
        {
            var mgr = new Dyn2ModelliTMgr(this.Database);

            var listaModelliIntervento = mgr.GetListaInterventiDaIdModello(this.IdComune, cls.Id.Value)
                                            .Select(x => new
                                            {
                                                x.Id,
                                                x.Software,
                                                x.Descrizione,
                                                Url = this.GetJavaUrl(cls.Id.Value, $"../alberoproc/view.htm?codice={x.Id}&software={x.Software}")
                                            });
            this.gvUtilizzatoInterventi.DataSource = listaModelliIntervento;
            this.gvUtilizzatoInterventi.DataBind();

            this.MostraInterventiUtilizzo = listaModelliIntervento.Any();

            var listaModelliEndo = mgr.GetListaEndoDaIdModello(this.IdComune, cls.Id.Value)
                                        .Select(x => new
                                        {
                                            x.Id,
                                            x.Software,
                                            x.Descrizione,
                                            Url = this.GetJavaUrl(cls.Id.Value, $"../inventarioprocedimenti/view.htm?codice={x.Id}&software={x.Software}")
                                        });
            this.gvEndoInterventi.DataSource = listaModelliEndo;
            this.gvEndoInterventi.DataBind();

            this.MostraEndoUtilizzo = listaModelliEndo.Any();


        }
        /*
		bool esistonoRigheMultiple = new Dyn2ModelliTMgr(Database).VerificaEsistenzaRigheMultiple(IdComune, Convert.ToInt32(IdModello));

		if (esistonoRigheMultiple)
		{
			chkMultiplo.Visible = false;
		}*/
    }

    private string GetJavaUrl(int idModello, string nuovoPath)
    {
        var gotoUrl = $"/aspnet/Archivi/DatiDinamici/Dyn2Modelli.aspx?Software={this.Software}&IdModello={idModello}";
        var returnTo = $"/externalresource/goTo.htm?url={this.Server.UrlEncode(this.Server.UrlEncode(this.Server.UrlEncode(gotoUrl)))}";

        return $"{this.JavaBaseUrl}/history/set.htm?ReturnTo={returnTo}&GoTo={this.Server.UrlEncode(nuovoPath)}";
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
        this.SalvaFiltriRicerca();

        this.gvLista.DataSource = new Dyn2ModelliTMgr(this.Database).CercaModelli(this.IdComune, this.Software, this.txtSrcId.Value, this.txtSrcCodice.Value, this.txtSrcDescrizione.Value, this.ddlSrcContesto.Value);
        this.gvLista.DataBind();

        this.multiView.ActiveViewIndex = 1;
    }



    public void cmdNuovo_Click(object sender, EventArgs e)
    {
        this.BindDettaglio(new Dyn2ModelliT());
    }

    public void cmdNuovoComeCopia_Click(object sender, EventArgs e)
    {
        this.chkCopiaFormule.Item.Checked = true;
        this.multiView.ActiveViewIndex = 3;
    }

    public void cmdChiudi_Click(object sender, EventArgs e)
    {
        this.SvuotaFiltriRicerca();
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
        var id = this.gvLista.DataKeys[this.gvLista.SelectedIndex].Value.ToString();

        var fmtUrl = "~/Archivi/DatiDinamici/Dyn2Modelli.aspx?Token={0}&Software={1}&IdModello={2}";

        this.Response.Redirect(string.Format(fmtUrl, this.Token, this.Software, id));
    }

    public string TraduciContesto(object contesto)
    {
        if (contesto == null || string.IsNullOrEmpty(contesto.ToString())) return "Tutti";

        var contestoEnum = ContestoTranslator.ContestoBaseToContestoEnum(contesto.ToString());

        return DatiDinamiciInfoDictionary.Items[contestoEnum].Descrizione;
    }
    #endregion


    #region Scheda dettaglio
    protected void cmdSalva_Click(object sender, EventArgs e)
    {
        Dyn2ModelliTMgr mgr = new Dyn2ModelliTMgr(this.Database);

        Dyn2ModelliT cls = null;

        if (this.IsInserting)
        {
            cls = new Dyn2ModelliT();
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
            cls.Descrizione = this.txtDescrizione.Value;
            cls.FkD2bcId = String.IsNullOrEmpty(this.ddlContesto.Value) ? null : this.ddlContesto.Value;
            cls.Modellomultiplo = this.chkMultiplo.Item.Checked ? 1 : 0;
            cls.FlgStoricizza = this.chkStoricizza.Item.Checked ? 1 : 0;
            cls.FlgReadonlyWeb = this.chkReadOnly.Item.Checked ? 1 : 0;
            cls.CodiceScheda = this.txtCodiceScheda.Value.ToUpper();

            if (this.IsInserting)
                cls = mgr.Insert(cls);
            else
                cls = mgr.Update(cls);

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

    protected void cmdElimina_Click(object sender, EventArgs e)
    {
        Dyn2ModelliTMgr mgr = new Dyn2ModelliTMgr(this.Database);

        var id = Convert.ToInt32(this.lblId.Item.Text);

        Dyn2ModelliT cls = mgr.GetById(this.IdComune, id);

        try
        {
            mgr.Delete(cls);

            this.multiView.ActiveViewIndex = 0;
        }
        catch (Exception ex)
        {
            this.MostraErrore(AmbitoErroreEnum.Cancellazione, ex);
        }
    }

    protected void cmdChiudiDettaglio_Click(object sender, EventArgs e)
    {
        var fmtUrl = "~/Archivi/DatiDinamici/Dyn2Modelli.aspx?Token={0}&Software={1}&ripristinafiltri=1";

        this.Response.Redirect(string.Format(fmtUrl, this.Token, this.Software));
    }

    protected void cmdFormule_Click(object sender, EventArgs e)
    {
        var id = this.lblId.Value;

        var fmtUrl = "~/Archivi/DatiDinamici/Dyn2ModelliFormule.aspx?Token={0}&IdModello={1}";

        this.Response.Redirect(string.Format(fmtUrl, this.Token, id));
    }

    protected void cmdGestioneCampi_Click(object sender, EventArgs e)
    {
        var id = this.lblId.Value;

        var fmtUrl = "~/Archivi/DatiDinamici/Dyn2ModelliCampi.aspx?Token={0}&IdModello={1}";

        this.Response.Redirect(string.Format(fmtUrl, this.Token, id));
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
            var mgr = new Dyn2ModelliTMgr(this.Database);

            var idSchedaDaCopiare = Convert.ToInt32(this.rplModelloDinamico.Value);
            var copiaFormule = this.chkCopiaFormule.Item.Checked;


            var nuovoId = mgr.CopiaScheda(this.IdComune, idSchedaDaCopiare, copiaFormule);

            this.BindDettaglio(new Dyn2ModelliTMgr(this.Database).GetById(this.IdComune, Convert.ToInt32(nuovoId)));
        }
        catch (Exception ex)
        {
            this.MostraErrore(AmbitoErroreEnum.Inserimento, ex);
        }
    }




    #endregion

    protected void ddlContesto_ValueChanged(object sender, EventArgs e)
    {
        this.ImpostaVisibilitaCampiDaIdContesto(this.ddlContesto.Value);
    }

    private void ImpostaVisibilitaCampiDaIdContesto(string idContestoBase)
    {
        this.chkStoricizza.Visible = true;
        this.chkReadOnly.Visible = true;
        this.chkMultiplo.Visible = true;
        this.cmdFormule.Visible = true;

        if (idContestoBase == ContestoTranslator.ContestoEnumToContestoBase(ContestoModelloEnum.Mercati) || idContestoBase == ContestoTranslator.ContestoEnumToContestoBase(ContestoModelloEnum.Posteggi))
        {
            this.chkStoricizza.Visible = false;
            this.chkReadOnly.Visible = false;
            this.chkMultiplo.Visible = false;
            this.cmdFormule.Visible = false;
        }
    }
}
