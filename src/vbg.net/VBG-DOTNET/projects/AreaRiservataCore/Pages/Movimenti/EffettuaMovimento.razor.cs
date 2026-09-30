using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDiOrigine;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneWorkflowMovimento;
using Init.Sigepro.FrontEnd.GestioneMovimenti.ViewModels;
using Microsoft.AspNetCore.Components;

namespace AreaRiservataCore.Pages.Movimenti
{
    public partial class EffettuaMovimento
    {
        private static class Constants
        {
            public const string TestoIntestazione = "In riferimento alla pratica numero {numeroPratica} del {dataPratica}" +
                                                    "{datiProtocollo} relativamente all'attività istruttoria in seguito riportata";
            public const string SegnapostoNumeroPratica = "{numeroPratica}";
            public const string SegnapostoDataPratica = "{dataPratica}";
            public const string SegnapostoDatiProtocollo = "{datiProtocollo}";
            public const string ReturnToSessionKey = "EffettuaMovimento.ReturnToSessionKey";
        }

        [Inject]
        protected RiepilogoMovimentoDiOrigineViewModel _viewModel { get; set; } = default!;

        [Inject]
        protected IConfigurazione<ParametriIntegrazioniDocumentali> _parametriIntegrazione { get; set; } = default!;

        private string _stepDescription = "";

        public bool PubblicaNote
        {
            get { return !this._parametriIntegrazione.Parametri.NascondiNoteMovimento; }
        }

        // protected MovimentoDaEffettuare MovimentoDaEffettuare { get; set; }
        protected MovimentoDiOrigine? _dataSource { get; set; }

        protected override void OnInitializedMovimenti()
        {
            this._dataSource = this._viewModel.GetMovimentoDiOrigine(this.IdMovimento);
            this._stepDescription = GeneraDescrizioneStep(this._dataSource);

            base.OnInitializedMovimenti();
        }

        public string GetAttivitaRichiesta()
        {
            return this._viewModel.GetAttivitaRichiesta(this.IdMovimento);
        }

        private static string GeneraDescrizioneStep(MovimentoDiOrigine movimentoDiOrigine)
        {
            var numeroPratica = movimentoDiOrigine.DatiIstanza.NumeroIstanza;
            var dataPratica = movimentoDiOrigine.DatiIstanza.DataIstanza.ToString("dd/MM/yyyy");
            var datiprotocollo = String.Empty;

            if (movimentoDiOrigine.DatiIstanza.Protocollo.DatiPresenti)
                datiprotocollo = String.Format(" (prot n.{0} del {1}) ",
                                                movimentoDiOrigine.DatiIstanza.Protocollo.Numero,
                                                movimentoDiOrigine.DatiIstanza.Protocollo.Data!.Value.ToString("dd/MM/yyyy"));

            var str = Constants.TestoIntestazione.Replace(Constants.SegnapostoNumeroPratica, numeroPratica);
            str = str.Replace(Constants.SegnapostoDataPratica, dataPratica);
            str = str.Replace(Constants.SegnapostoDatiProtocollo, datiprotocollo);

            return str;
        }

        private async Task OnBtnNextAsync()
        {
            this._viewModel.CreaMovimento(this.IdMovimento);

            await this.GoToNextStepAsync();
        }

        private async Task OnBtnCloseAsync()
        {
            await this.GoTo_GoBackURLAsync();
        }

        protected override IStepViewModel GetViewmodel()
        {
            return this._viewModel;
        }
    }
}
