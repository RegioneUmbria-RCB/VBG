using Init.SIGePro.Data;
using Init.SIGePro.DatiDinamici.Framework.Scripts;
using Init.SIGePro.Manager;
using SIGePro.Net;
using System;
using System.Text;
using System.Web.UI.WebControls;
using VBG.DatiDinamici;
using VBG.DatiDinamici.Contesti;
using VBG.DatiDinamici.Scripts;
using VBG.DatiDinamici.Standard.Utils.CreazioneModelli.Serializables;

public partial class Archivi_DatiDinamici_Dyn2CampiFormule : BasePage
{
    public string IdCampo
    {
        get { return this.Request.QueryString["IdCampo"].ToString(); }
    }

    private Dyn2Campi m_campo;
    private Dyn2Campi Campo
    {
        get
        {
            if (this.m_campo == null)
            {
                if (string.IsNullOrEmpty(this.IdCampo)) return null;

                var mgr = new Dyn2CampiMgr(this.Database);
                this.m_campo = mgr.GetById(this.IdComune, Convert.ToInt32(this.IdCampo));
            }
            return this.m_campo;
        }
    }

    public override string Software
    {
        get
        {
            return this.Campo.Software;
        }
    }

    public bool VisualizzaClasse
    {
        get
        {
            var qs = this.Request.QueryString["VisualizzaClasse"];

            if (String.IsNullOrEmpty(qs))
                return false;

            return Convert.ToBoolean(qs);
        }
    }

    protected void Page_Load(object sender, EventArgs e)
    {
        if (!this.IsPostBack)
        {
            //				if (!AdminSecurityManager.IsCurrentUserAdmin)
            //					ShowLogonScreen();

            if (this.VisualizzaClasse)
            {
                this.MostraClasseGenerata();
                return;
            }

            this.ddlEvento.Item.Items.Add(new ListItem(NomeTipoContestoScript.Get(TipoScriptEnum.Caricamento), TipoScriptEnum.Caricamento.ToString()));
            this.ddlEvento.Item.Items.Add(new ListItem(NomeTipoContestoScript.Get(TipoScriptEnum.Modifica), TipoScriptEnum.Modifica.ToString()));
            this.ddlEvento.Item.Items.Add(new ListItem(NomeTipoContestoScript.Get(TipoScriptEnum.Salvataggio), TipoScriptEnum.Salvataggio.ToString()));

            this.ddlEvento.Value = TipoScriptEnum.Caricamento.ToString();

            this.DataBind();

            this.Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;
        }
    }

    private void ShowLogonScreen()
    {
        this.multiView.ActiveViewIndex = 0;
    }

    public override void DataBind()
    {
        if (this.Campo == null)
            throw new ArgumentException("Il codice campo " + this.IdCampo + " non è valido");

        this.lblCampoCorrente.Text = this.Campo.Nomecampo;

        var mgr = new Dyn2CampiScriptMgr(this.Database);

        Dyn2CampiScript script = mgr.GetById(this.IdComune, this.Campo.Id.GetValueOrDefault(int.MinValue), this.ddlEvento.Value);

        this.txtScript.Text = script == null ? String.Empty : script.GetTestoScript();
    }


    private void MostraClasseGenerata()
    {
        var script = this.Session["CODICE_SCRIPT"] == null ? String.Empty : this.Session["CODICE_SCRIPT"].ToString();

        this.Response.Clear();
        this.Response.AddHeader("content-disposition", "attachment;filename=CodiceScript.txt;");
        this.Response.ContentType = "text/plain";
        this.Response.Write(script);
        this.Response.End();
    }

    protected void cmdVisualizzaClasse_Click(object sender, EventArgs e)
    {
        var contesto = new ContestoModelloDinamico(this.Token, ContestoTranslator.ContestoBaseToContestoEnum(this.Campo.FkD2bcId), null);
        var scriptCompiler = new FrameworkScriptCompiler();

        var script = ScriptCampoDinamico.PerTests(contesto,
            new ModelloDinamicoScriptDto { Script = Encoding.UTF8.GetBytes(this.txtScript.Text) },
            new ModelloDinamicoScriptDto(), new ModelloDinamicoScriptDto(), new ModelloDinamicoScriptDto(), scriptCompiler);

        this.Session["CODICE_SCRIPT"] = script.GetCodiceScript();

        var jsScript = "window.open('" + this.GetUrlAssolutoPagina() + "&VisualizzaClasse=true')";

        this.Page.ClientScript.RegisterStartupScript(this.GetType(), "mostraScript", jsScript, true);
    }

    protected string GetUrlAssolutoPagina()
    {
        var url = this.ResolveClientUrl("~/Archivi/DatiDinamici/Dyn2CampiFormule.aspx") + "?" + this.Request.QueryString;

        return url;
    }

    protected void cmdCompila_Click(object sender, EventArgs e)
    {
        this.VerificaCompilazioneFormule();
    }

    private bool VerificaCompilazioneFormule()
    {
        var contesto = new ContestoModelloDinamico(this.Token, ContestoTranslator.ContestoBaseToContestoEnum(this.Campo.FkD2bcId), null);

        ScriptCampoDinamico script = null;

        if (!String.IsNullOrEmpty(this.txtScript.Text.Trim()))
        {
            var scriptCompiler = new FrameworkScriptCompiler();
            script = ScriptCampoDinamico.PerTests(contesto, new ModelloDinamicoScriptDto { Script = Encoding.UTF8.GetBytes(this.txtScript.Text) },
                                                            new ModelloDinamicoScriptDto(), new ModelloDinamicoScriptDto(), new ModelloDinamicoScriptDto(), scriptCompiler);

            try
            {
                script.Compila();
            }
            catch (Exception ex)
            {
                this.MostraErrore("Errore durante la compilazione della formula del modello: " + ex.ToString(), ex);
                this.txtScript.Focus();
                return false;
            }
        }

        return true;
    }

    protected void cmdSalva_Click(object sender, EventArgs e)
    {
        if (!this.VerificaCompilazioneFormule()) return;

        bool bInsert = false;

        var mgr = new Dyn2CampiScriptMgr(this.Database);

        Dyn2CampiScript script = mgr.GetById(this.IdComune, this.Campo.Id.GetValueOrDefault(int.MinValue), this.ddlEvento.Value);

        if (script == null)
        {
            script = new Dyn2CampiScript();
            script.Idcomune = this.IdComune;
            script.FkD2cId = this.Campo.Id;
            script.Evento = this.ddlEvento.Value;

            bInsert = true;
        }

        script.SetTestoScript(this.txtScript.Text);

        try
        {
            if (bInsert)
                mgr.Insert(script);
            else
                mgr.Update(script);

            string js = "alert('Formula salvata correttamente.');";
            this.Page.ClientScript.RegisterStartupScript(this.GetType(), "loadScript", js, true);

            this.DataBind();
        }
        catch (Exception ex)
        {
            this.MostraErrore(AmbitoErroreEnum.Aggiornamento, ex);
        }

    }

    protected void cmdChiudi_Click(object sender, EventArgs e)
    {
        string fmtUrl = "~/Archivi/DatiDinamici/Dyn2Campi.aspx?Token={0}&Software={1}&IdCampo={2}&Popup={3}";
        this.Response.Redirect(String.Format(fmtUrl, this.AuthenticationInfo.Token, this.Campo.Software, this.IdCampo, this.IsInPopup));
    }

    protected void AdminAuthenticationOk(object sender, EventArgs e)
    {
        string fmtUrl = "~/Archivi/DatiDinamici/Dyn2CampiFormule.aspx?Token={0}&IdCampo={1}&Popup={2}";
        this.Response.Redirect(String.Format(fmtUrl, this.AuthenticationInfo.Token, this.IdCampo, this.IsInPopup));
    }

    protected void AdminAuthenticationKo(object sender, EventArgs e)
    {
        this.cmdChiudi_Click(sender, EventArgs.Empty);
    }

    protected void ddlEvento_ValueChanged(object sender, EventArgs e)
    {
        this.DataBind();
    }

    protected override void MostraErrore(string messaggio, Exception ex)
    {
        var errMsg = messaggio;

        if (String.IsNullOrEmpty(errMsg))
            errMsg = ex.ToString();

        errMsg = errMsg.Replace("\r", "");
        errMsg = errMsg.Replace("\n", "\\n");
        errMsg = errMsg.Replace("'", "\\'");

        this.Page.ClientScript.RegisterStartupScript(this.GetType(), "errorMessage", "alert(\"" + errMsg + "\");", true);

    }
}
