using Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using Init.Sigepro.FrontEnd.QsParameters.Fvg;
using Ninject;
using System;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.moduli_fvg.compilazione
{
    public partial class lista : BasePage
    {
        // esempio di utilizzo:
        // http://localhost:1137/AreaRiservata/moduli-fvg/compilazione/lista.aspx?idcomune=FVGSOL&software=SS&istanza=633424332223082&modulo=QIG
        public static class Constants
        {
            public const string UrlPaginaCompilazioneScheda = "~/moduli-fvg/compilazione/compila.aspx";
            public const string UrlPaginaInvioCompletato = "~/moduli-fvg/compilazione/invio-completato.aspx";
        }


        [Inject]
        protected ServiziFVGService _service { get; set; }


        protected QsFvgCodiceIstanza CodiceIstanza => new QsFvgCodiceIstanza(this.Request.QueryString);
        protected QsFvgIdModulo IdModulo => new QsFvgIdModulo(this.Request.QueryString);
        protected QsFvgPassaASuccessiva PassaASuccessiva => new QsFvgPassaASuccessiva(this.Request.QueryString);

        private long? CopiaDa
        {
            get
            {
                var copiaDa = this.Request.QueryString["copia-da"];

                if (String.IsNullOrEmpty(copiaDa))
                {
                    return null;
                }

                return long.Parse(copiaDa);
            }
        }

        protected string ReturnUrl => this.Request.QueryString["returnurl"] ?? string.Empty;

        public bool TutteLeSchedeSonoCompilate
        {
            get { object o = this.ViewState["TutteLeSchedeSonoCompilate"]; return o == null ? false : (bool)o; }
            set { this.ViewState["TutteLeSchedeSonoCompilate"] = value; }
        }


        protected void Page_Load(object sender, EventArgs e)
        {
            if (!this.IsPostBack)
            {

                if (this.PassaASuccessiva.HasValue)
                {
                    this.PassaASchedaSuccessiva();
                }
                else
                {
                    this.DataBind(this.CopiaDa);
                }
            }
        }

        private void PassaASchedaSuccessiva()
        {
            var endoprocedimento = this._service.GetDatiModulo(this.CodiceIstanza.Value, this.IdModulo.Value);
            var idScheda = endoprocedimento.GetIdSchedaSuccessiva(this.PassaASuccessiva.Value);

            if (idScheda.HasValue)
            {
                this.CompilaScheda(idScheda.Value);
                return;
            }

            this.DataBind();
        }

        public override void DataBind()
        {
            this.DataBind(null);
        }

        public void DataBind(long? copiaDa)
        {
            var endoprocedimento = this._service.GetDatiModulo(this.CodiceIstanza.Value, this.IdModulo.Value, copiaDa);
            this.lblNomeEndoprocedimento.Text = this.Page.Title = endoprocedimento.Descrizione;

            this.rptListaSchedeDinamiche.DataSource = endoprocedimento.ListaSchedePubblicate;
            this.rptListaSchedeDinamiche.DataBind();

            this.TutteLeSchedeSonoCompilate = endoprocedimento.TutteLeSchedeSonoCompilate;

            this.divTutteLeSchedeSonoCompilate.Visible = this.TutteLeSchedeSonoCompilate;
            //this.cmdInviaDatiAlComune.Visible = TutteLeSchedeSonoCompilate;            

            this.divDatiPrecompilatiConSuccesso.Visible = copiaDa.HasValue;
        }

        protected void OnSchedaSelezionata(object sender, EventArgs e)
        {
            var button = (LinkButton)sender;
            var idScheda = Convert.ToInt32(button.CommandArgument);

            this.CompilaScheda(idScheda);
        }

        private void CompilaScheda(int idScheda)
        {
            var url = UrlBuilder.Url(Constants.UrlPaginaCompilazioneScheda, x =>
            {
                x.Add(new QsAliasComune(this.IdComune));
                x.Add(new QsSoftware(this.Software));
                x.Add(this.CodiceIstanza);
                x.Add(this.IdModulo);
                x.Add(new QsFvgIdScheda(idScheda));
            });

            this.Response.Redirect(url);
        }

        protected void cmdInviaDatiAlComune_Click(object sender, EventArgs e)
        {
            try
            {
                this._service.AllegaPdfADomanda(this.CodiceIstanza.Value, this.IdModulo.Value);

                var url = UrlBuilder.Url(Constants.UrlPaginaInvioCompletato, x =>
                {
                    x.Add(new QsAliasComune(this.IdComune));
                    x.Add(new QsSoftware(this.Software));
                    x.Add(this.CodiceIstanza);
                    x.Add(this.IdModulo);
                });

                this.Response.Redirect(url);
            }
            catch (Exception ex)
            {
                this.Errori.Add(ex.Message);
            }
        }

        protected void cmdGeneraPdf_Click(object sender, EventArgs e)
        {
            var pdf = this._service.GeneraPdfModulo(this.CodiceIstanza.Value, this.IdModulo.Value);

            this.Response.ContentType = pdf.MimeType;
            this.Response.AddHeader("content-disposition", "attachment; filename=\"" + pdf.FileName + "\"");
            this.Response.BinaryWrite(pdf.FileContent);
        }
    }
}