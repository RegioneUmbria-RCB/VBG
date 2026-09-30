using Init.SIGePro.Data;
using Init.SIGePro.DatiDinamici.Framework.Scripts;
using Init.SIGePro.Manager;
using SIGePro.Net;
using System;
using System.Linq;
using System.Text;
using System.Web.UI.WebControls;
using VBG.DatiDinamici;
using VBG.DatiDinamici.Contesti;
using VBG.DatiDinamici.Scripts;
using VBG.DatiDinamici.Standard.Utils.CreazioneModelli.Serializables;

namespace Sigepro.net.Archivi.DatiDinamici
{
    public partial class Dyn2ModelliFormule : BasePage
    {



        public int IdModello
        {
            get { return Convert.ToInt32(this.Request.QueryString["IdModello"]); }
        }



        private readonly Lazy<Dyn2ModelliT> _modello;

        private Dyn2ModelliT Modello => this._modello.Value;

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

        public int TagsCount
        {
            get => Convert.ToInt32(this.ViewState["tags"] ?? "");
            set => this.ViewState["tags"] = value;
        }

        public override string Software
        {
            get
            {
                return this.Modello.Software;
            }
        }

        public Dyn2ModelliFormule()
        {
            this._modello = new Lazy<Dyn2ModelliT>(() =>
            {
                var mgr = new Dyn2ModelliTMgr(this.Database);
                return mgr.GetById(this.IdComune, this.IdModello);
            });
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            this.divFormulaSalvata.Visible = false;
            this.divFormulaCompilata.Visible = false;

            if (!this.IsPostBack)
            {
                this.campiCollegatiEditor.IdModello = this.IdModello;
                this.campiCollegatiEditor.DataBind();

                if (this.VisualizzaClasse)
                {
                    this.MostraClasseGenerata();
                    return;
                }

                this.RicaricaTags();

                this.ddlEvento.Item.Items.Add(new ListItem(NomeTipoContestoScript.Get(TipoScriptEnum.Caricamento), TipoScriptEnum.Caricamento.ToString()));
                this.ddlEvento.Item.Items.Add(new ListItem(NomeTipoContestoScript.Get(TipoScriptEnum.Modifica), TipoScriptEnum.Modifica.ToString()));
                this.ddlEvento.Item.Items.Add(new ListItem(NomeTipoContestoScript.Get(TipoScriptEnum.Salvataggio), TipoScriptEnum.Salvataggio.ToString()));
                this.ddlEvento.Item.Items.Add(new ListItem(NomeTipoContestoScript.Get(TipoScriptEnum.Massiva), TipoScriptEnum.Massiva.ToString()));
                this.ddlEvento.Item.Items.Add(new ListItem(NomeTipoContestoScript.Get(TipoScriptEnum.Funzioni), TipoScriptEnum.Funzioni.ToString()));

                this.ddlEvento.Value = TipoScriptEnum.Caricamento.ToString();

                this.DataBind();

                this.Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;
            }
        }

        private void RicaricaTags()
        {
            var mgr = new Dyn2ModelliDMgr(this.Database);
            var listaCampi = mgr.GetCampiByIdModello(this.IdComune, this.IdModello);
            var tags = listaCampi.SelectMany(x => x.GetTags()).Distinct().OrderBy(x => x).ToArray();

            this.TagsCount = tags.Length;

            this.rptTags.DataSource = tags;
            this.rptTags.DataBind();
        }

        public override void DataBind()
        {
            if (this.Modello == null)
                throw new ArgumentException("Il codice modello " + this.IdModello + " non è valido");

            var mgr = new Dyn2ModelliScriptMgr(this.Database);
            var evento = (TipoScriptEnum)Enum.Parse(typeof(TipoScriptEnum), this.ddlEvento.Value);

            this.txtScript.Text = mgr.GetTestoScript(this.IdComune, this.Modello.Id.Value, evento);
            this.txtUsing.Text = mgr.GetTestoScript(this.IdComune, this.Modello.Id.Value, TipoScriptEnum.Using);
            this.txtServices.Text = mgr.GetTestoScript(this.IdComune, this.Modello.Id.Value, TipoScriptEnum.Inject);

            this.chkNascondiUsing.Checked = !String.IsNullOrEmpty(this.txtUsing.Text);
            this.chkNascondiServizi.Checked = !String.IsNullOrEmpty(this.txtServices.Text);
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
            var contesto = new ContestoModelloDinamico(this.Token, ContestoTranslator.ContestoBaseToContestoEnum(this.Modello.FkD2bcId), null);

            var script = ScriptCampoDinamico.PerTests(contesto,
                new ModelloDinamicoScriptDto { Script = Encoding.UTF8.GetBytes(this.txtScript.Text) },
                new ModelloDinamicoScriptDto(),
                new ModelloDinamicoScriptDto { Script = Encoding.UTF8.GetBytes(this.txtUsing.Text) },
                new ModelloDinamicoScriptDto { Script = Encoding.UTF8.GetBytes(this.txtServices.Text) },
                new FrameworkScriptCompiler());

            this.Session["CODICE_SCRIPT"] = script.GetCodiceScript();

            var jsScript = "window.open('" + this.GetUrlAssolutoPagina() + "&VisualizzaClasse=true')";

            this.Page.ClientScript.RegisterStartupScript(this.GetType(), "mostraScript", jsScript, true);
        }


        protected void cmdCompila_Click(object sender, EventArgs e)
        {
            this.VerificaCompilazioneFormule();
        }

        private bool VerificaCompilazioneFormule()
        {
            var contesto = new ContestoModelloDinamico(this.Token, ContestoTranslator.ContestoBaseToContestoEnum(this.Modello.FkD2bcId), null);

            ScriptCampoDinamico script = null;

            if (!String.IsNullOrEmpty(this.txtScript.Text.Trim()))
            {
                var funzioniCondivise = this.ddlEvento.Item.SelectedValue == TipoScriptEnum.Funzioni.ToString();

                if (funzioniCondivise)
                {
                    script = ScriptCampoDinamico.PerTests(contesto,
                                        new ModelloDinamicoScriptDto(),
                                        new ModelloDinamicoScriptDto { Script = Encoding.UTF8.GetBytes(this.txtScript.Text) },
                                        new ModelloDinamicoScriptDto { Script = Encoding.UTF8.GetBytes(this.txtUsing.Text) },
                                        new ModelloDinamicoScriptDto { Script = Encoding.UTF8.GetBytes(this.txtServices.Text) },
                                        new FrameworkScriptCompiler());
                }
                else
                {
                    var mgr = new Dyn2ModelliScriptMgr(this.Database);
                    var fc = mgr.GetById(this.IdComune, this.Modello.Id.GetValueOrDefault(int.MinValue), TipoScriptEnum.Funzioni.ToString());

                    var scriptCondivisi = (fc != null) ? fc.GetTestoScript() : String.Empty;

                    script = ScriptCampoDinamico.PerTests(contesto,
                                                        new ModelloDinamicoScriptDto { Script = Encoding.UTF8.GetBytes(this.txtScript.Text) },
                                                        new ModelloDinamicoScriptDto { Script = Encoding.UTF8.GetBytes(scriptCondivisi) },
                                                        new ModelloDinamicoScriptDto { Script = Encoding.UTF8.GetBytes(this.txtUsing.Text) },
                                                        new ModelloDinamicoScriptDto { Script = Encoding.UTF8.GetBytes(this.txtServices.Text) },
                                                        new FrameworkScriptCompiler());
                }

                try
                {
                    script.Compila();

                    this.divFormulaCompilata.Visible = true;
                }
                catch (Exception ex)
                {
                    this.MostraErrore(ex.Message, ex);
                    this.txtScript.Focus();
                    return false;
                }
            }


            return true;
        }

        protected void cmdSalva_Click(object sender, EventArgs e)
        {
            if (!this.VerificaCompilazioneFormule()) return;


            try
            {
                var mgr = new Dyn2ModelliScriptMgr(this.Database);

                mgr.SalvaScript(this.IdComune, this.Modello.Id.Value, (TipoScriptEnum)Enum.Parse(typeof(TipoScriptEnum), this.ddlEvento.Value), this.txtScript.Text, this.txtUsing.Text, this.txtServices.Text);

                this.divFormulaSalvata.Visible = true;

                this.DataBind();
            }
            catch (Exception ex)
            {
                this.MostraErrore(AmbitoErroreEnum.Aggiornamento, ex);
            }
        }

        protected void cmdChiudi_Click(object sender, EventArgs e)
        {
            var fmtUrl = "~/Archivi/DatiDinamici/Dyn2Modelli.aspx?Token={0}&Software={1}&IdModello={2}";
            this.Response.Redirect(String.Format(fmtUrl, this.AuthenticationInfo.Token, this.Modello.Software, this.IdModello));
        }

        protected void AdminAuthenticationOk(object sender, EventArgs e)
        {
            var fmtUrl = "~/Archivi/DatiDinamici/Dyn2ModelliFormule.aspx?Token={0}&IdModello={1}";
            this.Response.Redirect(String.Format(fmtUrl, this.AuthenticationInfo.Token, this.IdModello));
        }

        protected void AdminAuthenticationKo(object sender, EventArgs e)
        {
            var fmtUrl = "~/Archivi/DatiDinamici/Dyn2Modelli.aspx?Token={0}&Software={1}&IdModello={2}";
            this.Response.Redirect(String.Format(fmtUrl, this.AuthenticationInfo.Token, this.Modello.Software, this.IdModello));
        }

        protected void ddlEvento_ValueChanged(object sender, EventArgs e)
        {
            this.DataBind();
        }

        protected string GetUrlAssolutoPagina()
        {
            var url = this.ResolveClientUrl("~/Archivi/DatiDinamici/Dyn2ModelliFormule.aspx") + "?" + this.Request.QueryString;

            return url;
        }

        protected override void MostraErrore(Exception ex)
        {

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
}
