using Init.Sigepro.FrontEnd.AppLogic.GestioneQuestionario;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved.Questionario
{
    public partial class Compila : ReservedBasePage
    {
        [Inject]
        protected IQuestionarioSoddisfazioneService _questionarioService { get; set; }

        private QsUuidIstanza Uuid => new QsUuidIstanza(Request.QueryString);

        protected void Page_Load(object sender, EventArgs e)
        {
            VerificaCompilazioneQuestionario();
        }

        private void VerificaCompilazioneQuestionario()
        {
            if(!this._questionarioService.CompilazioneQuestionarioAttiva)
            {
                RedirectADettaglioPratica();
                return;
            }

            if (this._questionarioService.QuestionarioCompilato(this.Uuid.Value))
            {
                RedirectACompilazioneCompletata();
                return;
            }
        }

        private void RedirectADettaglioPratica()
        {
            var url = UrlBuilder.Url("~/reserved/dettaglioistanzaex.aspx", qs =>
            {
                qs.Add(new QsAliasComune(IdComune));
                qs.Add(new QsSoftware(Software));
                qs.Add(Uuid);
            });

            Response.Redirect(url);
        }

        protected void cmdInviaValutazione_Click(object sender, EventArgs e)
        {
            try
            {
                if (String.IsNullOrEmpty(hidValutazione.Value))
                {
                    throw new Exception("Si prega di compilare il campo \"Valutazione\"");
                }

                var valutazione = Convert.ToInt32(hidValutazione.Value);
                var suggerimenti = txtNote.Value;

                this._questionarioService.SalvaQuestionario(this.Uuid.Value, valutazione, suggerimenti);
            }
            catch (Exception ex)
            {
                this.Errori.Add(ex.Message);
                return;
            }

            RedirectACompilazioneCompletata();
        }

        private void RedirectACompilazioneCompletata()
        {
            var url = UrlBuilder.Url("~/reserved/questionario/compilazione-completata.aspx", qs =>
            {
                qs.Add(new QsAliasComune(IdComune));
                qs.Add(new QsSoftware(Software));
                qs.Add(this.Uuid);
            });

            Response.Redirect(url);
        }

        protected void cmdChiudi_Click(object sender, EventArgs e)
        {
            var url = UrlBuilder.Url("~/reserved/DettaglioIstanzaEx.aspx", qs =>
            {
                qs.Add(new QsAliasComune(IdComune));
                qs.Add(new QsSoftware(Software));
                qs.Add(this.Uuid);
            });

            Response.Redirect(url);
        }
    }
}