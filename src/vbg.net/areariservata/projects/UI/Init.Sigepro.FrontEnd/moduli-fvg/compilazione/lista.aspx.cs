using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using Init.Sigepro.FrontEnd.QsParameters.Fvg;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
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
        

        protected QsFvgCodiceIstanza CodiceIstanza => new QsFvgCodiceIstanza(Request.QueryString);
        protected QsFvgIdModulo IdModulo => new QsFvgIdModulo(Request.QueryString);
        protected QsFvgPassaASuccessiva PassaASuccessiva => new QsFvgPassaASuccessiva(Request.QueryString);

        public bool TutteLeSchedeSonoCompilate
        {
            get { object o = this.ViewState["TutteLeSchedeSonoCompilate"]; return o == null ? false : (bool)o; }
            set { this.ViewState["TutteLeSchedeSonoCompilate"] = value; }
        }


        protected void Page_Load(object sender, EventArgs e)
        {
            if(!IsPostBack)
            {

                if (this.PassaASuccessiva.HasValue)
                {
                    PassaASchedaSuccessiva();
                }
                else
                {
                    DataBind();
                }
            }
        }

        private void PassaASchedaSuccessiva()
        {
            var endoprocedimento = this._service.GetDatiModulo(this.CodiceIstanza.Value, this.IdModulo.Value);
            var numeroSchede = endoprocedimento.ListaSchede.Count();

            for (int i = 0; i < numeroSchede; i++)
            {
                var scheda = endoprocedimento.ListaSchede.ElementAt(i);

                if (scheda.Id == this.PassaASuccessiva.Value)
                {
                    // La scheda compilata è l'ultima
                    if (i < numeroSchede - 1)
                    {
                        CompilaScheda(endoprocedimento.ListaSchede.ElementAt(i + 1).Id);
                        return;
                    }
                }
            }

            DataBind();
        }

        public override void DataBind()
        {
            var endoprocedimento = this._service.GetDatiModulo(this.CodiceIstanza.Value, this.IdModulo.Value);
            this.lblNomeEndoprocedimento.Text = this.Page.Title = endoprocedimento.Descrizione;


            this.rptListaSchedeDinamiche.DataSource = endoprocedimento.ListaSchede;
            this.rptListaSchedeDinamiche.DataBind();

            this.TutteLeSchedeSonoCompilate = endoprocedimento.ListaSchede.Where(x => x.Compilata).Count() == endoprocedimento.ListaSchede.Count();

            this.divTutteLeSchedeSonoCompilate.Visible = TutteLeSchedeSonoCompilate;
            //this.cmdInviaDatiAlComune.Visible = TutteLeSchedeSonoCompilate;            
        }

        protected void OnSchedaSelezionata(object sender, EventArgs e)
        {
            var button = (LinkButton)sender;
            var idScheda = Convert.ToInt32(button.CommandArgument);

            CompilaScheda(idScheda);
        }

        private void CompilaScheda(int idScheda)
        {
            var url = UrlBuilder.Url(Constants.UrlPaginaCompilazioneScheda, x =>
            {
                x.Add(new QsAliasComune(this.IdComune));
                x.Add(new QsSoftware(this.Software));
                x.Add(CodiceIstanza);
                x.Add(IdModulo);
                x.Add(new QsFvgIdScheda(idScheda));
            });

            Response.Redirect(url);
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
                    x.Add(CodiceIstanza);
                    x.Add(IdModulo);
                });

                Response.Redirect(url);
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