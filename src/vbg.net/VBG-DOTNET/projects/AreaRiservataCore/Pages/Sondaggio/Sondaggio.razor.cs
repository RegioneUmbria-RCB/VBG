using Init.Sigepro.FrontEnd.AppLogic.GestioneQuestionario;
using Microsoft.AspNetCore.Components;
using VBG.BlazorComponentsLibrary.EditFormComponents;

namespace AreaRiservataCore.Pages.Sondaggio
{
    public partial class Sondaggio
    {

        [Parameter]
        public string UuidPratica { get; set; }

        [Inject]
        public IQuestionarioSoddisfazioneService _questionarioService { get; set; } = default!;


        protected int Stars { get; set; } = 5;

        protected TextInputForm txtNote;

        protected int _value = 0;
        private bool _questionarioCompilato = false;

        private void SetValue(int value)
        {
            this._value = value;
        }

        //private string Uuid
        //{
        //    get
        //    {
        //        string id = string.Empty;
        //        var uri = Navigation.ToAbsoluteUri(Navigation.Uri);
        //        if (QueryHelpers.ParseQuery(uri.Query).TryGetValue("uuid-pratica", out var _id))
        //        {
        //            id = _id;
        //        }

        //        return id;
        //    }
        //}

        protected override async Task OnInitializedAsync()
        {
            await base.OnInitializedAsync();

            await this.VerificaCompilazioneQuestionarioAsync();
        }

        private async Task VerificaCompilazioneQuestionarioAsync()
        {
            if (!this._questionarioService.CompilazioneQuestionarioAttiva)
            {
                await this.RedirectADettaglioPraticaAsync();
                return;
            }

            this._questionarioCompilato = this._questionarioService.QuestionarioCompilato(this.UuidPratica);
        }

        public async Task OnInviaValutazioneAsync()
        {

            await _spinnerService.ShowSpinnerAsync(async () =>
            {
                await base.OnParametersSetAsync();

                try
                {
                    if (this._value <= 0)
                    {
                        throw new Exception("Si prega di compilare il campo \"Valutazione\"");
                    }

                    var suggerimenti = this.txtNote.Value;

                    this._questionarioService.SalvaQuestionario(this.UuidPratica, this._value, suggerimenti);

                    this._questionarioCompilato = true;
                }
                catch (Exception ex)
                {
                    this.MessageContainer.ClearErrors();
                    this.MessageContainer.AddError(ex.Message);
                    return;
                }
            });


        }

        public async Task OnChiudiAsync()
        {
            await this.RedirectADettaglioPraticaAsync();
        }

        //private void RedirectACompilazioneCompletata()
        //{
        //    var url = UrlBuilder.Url("~/reserved/questionario/compilazione-completata.aspx", qs =>
        //    {
        //        qs.Add(new QsAliasComune(IdComune));
        //        qs.Add(new QsSoftware(Software));
        //        qs.Add(this.Uuid);
        //    });

        //    Response.Redirect(url);
        //}

        private async Task RedirectADettaglioPraticaAsync()
        {
            await this.GotoAsync($"dettaglioistanzaex/{this.UuidPratica}/istanzepresentate/");
        }
    }
}
