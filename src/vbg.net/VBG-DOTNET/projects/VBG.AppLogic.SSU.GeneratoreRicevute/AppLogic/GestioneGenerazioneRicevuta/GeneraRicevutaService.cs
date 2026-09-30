using Init.Sigepro.FrontEnd.AppLogic.Adapters;
using Init.Sigepro.FrontEnd.AppLogic.ConversionePDF;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using VBG.AppLogic.SSU.APICatalogoServizi.Client;
using VBG.AppLogic.SSU.GestioneRiepilogoDomanda;

namespace VBG.AppLogic.SSU.GeneratoreRicevute.AppLogic.GestioneGenerazioneRicevuta
{
    public class GeneraRicevutaService
    {
        private readonly ILogger<GeneraRicevutaService> _logger;
        private readonly SsuCatalogoServiziClient _apiClient;
        private readonly IIstanzaSigeproAdapterService _istanzaSigeproAdapterService;
        private readonly IHtmlToPdfAsyncFileConverter _fileConverter;
        private readonly IOggettiService _oggettiService;

        public GeneraRicevutaService(ILogger<GeneraRicevutaService> logger, SsuCatalogoServiziClient apiClient, IIstanzaSigeproAdapterService istanzaSigeproAdapterService, IHtmlToPdfAsyncFileConverter fileConverter, IOggettiService oggettiService)
        {
            this._logger = logger;
            this._apiClient = apiClient;
            this._istanzaSigeproAdapterService = istanzaSigeproAdapterService;
            this._fileConverter = fileConverter;
            this._oggettiService = oggettiService;
        }

        public async Task<int> GeneraPdfRicevutaAsync(DomandaOnline domanda, string idDomandaSsu)
        {
            try
            {
                this._logger.LogDebug("Inizio della generazione della ricevuta per la domanda ssu con idDomanda {0}", idDomandaSsu);

                var codiceEnte = domanda?.ReadInterface.Ssu.CodiceEnte;

                var templateXslObject = await this._apiClient.GetRicevutaDomandaXslAsync(codiceEnte);

                var modelloDomandaReader = new SsuModelloDomandaReader(templateXslObject.TemplateXsl ?? "");
                var modello = modelloDomandaReader.Read();

                var istanzaXml = this._istanzaSigeproAdapterService.ToIstanzaBackoffice(domanda.ReadInterface).ToXmlModelloRiepilogo(idDomandaSsu);

                var risultatoTrasformazione = modello.Trasforma(istanzaXml);

                var nomeFile = String.Format("ricevuta.{0}.pdf", idDomandaSsu);
                var pdfRicevuta = await this._fileConverter.ConvertiAsync(nomeFile, risultatoTrasformazione);

                var codiceOggetto = this._oggettiService.InserisciOggetto(pdfRicevuta);

                this._logger.LogDebug("Generazione della ricevuta completata con codiceOggetto {0}", codiceOggetto);
                return codiceOggetto;
            }
            catch (Exception ex)
            {
                this._logger.LogError("Errore durante la generazione della ricevuta: {0}", ex);
                throw;
            }

        }
    }
}
