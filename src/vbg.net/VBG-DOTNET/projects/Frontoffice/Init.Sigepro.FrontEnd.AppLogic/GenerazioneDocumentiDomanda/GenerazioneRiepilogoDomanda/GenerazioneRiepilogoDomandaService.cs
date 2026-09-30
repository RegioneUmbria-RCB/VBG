
using Init.Sigepro.FrontEnd.AppLogic.Adapters;
using Init.Sigepro.FrontEnd.AppLogic.ConversionePDF;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.LetturaDatiDinamici.LetturaDaDomandaOnline;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using log4net;
using System;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda
{

    public class GenerazioneRiepilogoDomandaService
    {
        public enum FormatoConversioneModello
        {
            HTML,
            PDF
        }

        private readonly ILog _log = LogManager.GetLogger(typeof(GenerazioneRiepilogoDomandaService));
        private readonly ISalvataggioDomandaStrategy _caricamentoDomandaStrategy;
        private readonly IHtmlToPdfAsyncFileConverter _fileConverter;
        private readonly SostituzioneSegnapostoRiepilogoService _sostituzioneSegnapostoRiepilogoService;
        private readonly IIstanzaSigeproAdapterService _istanzaSigeproAdapterService;
        private readonly DomandaOnlineDatiDinamiciReaderFactory _domandaOnlineDatiDinamiciReaderFactory;
        private readonly ModelloDomandaReaderFactory _modelloDomandaReaderFactory;

        public GenerazioneRiepilogoDomandaService(
                                ISalvataggioDomandaStrategy caricamentoDomandaStrategy,
                                SostituzioneSegnapostoRiepilogoService sostituzioneSegnapostoRiepilogoService,
                                IHtmlToPdfAsyncFileConverter fileConverter, IIstanzaSigeproAdapterService istanzaSigeproAdapterService,
                                DomandaOnlineDatiDinamiciReaderFactory domandaOnlineDatiDinamiciReaderFactory,
                                ModelloDomandaReaderFactory modelloDomandaReaderFactory)
        {
            this._caricamentoDomandaStrategy = caricamentoDomandaStrategy;
            this._fileConverter = fileConverter;
            this._sostituzioneSegnapostoRiepilogoService = sostituzioneSegnapostoRiepilogoService;
            this._istanzaSigeproAdapterService = istanzaSigeproAdapterService;
            this._domandaOnlineDatiDinamiciReaderFactory = domandaOnlineDatiDinamiciReaderFactory;
            this._modelloDomandaReaderFactory = modelloDomandaReaderFactory;
        }

        public async Task<BinaryFile> GeneraRiepilogoDomandaAsync(int idDomanda, bool aggiungiPdfSchedeAListaAllegati)
        {
            this._log.DebugFormat("Generazione del riepilogo con reader factory predefinita per la domanda {0}, aggiungiPdfSchedeAListaAllegati {1}", idDomanda, aggiungiPdfSchedeAListaAllegati);

            var domanda = await this._caricamentoDomandaStrategy.GetByIdAsync(idDomanda);
            var reader = await this._modelloDomandaReaderFactory.CreateAsync(idDomanda);

            return await this.GeneraRiepilogoAsync(domanda, reader, aggiungiPdfSchedeAListaAllegati);
        }

        public async Task<BinaryFile> GeneraRiepilogoDomandaAsync(int idDomanda, IModelloDomandaReader reader, IStrutturaModelloDinamicoRepository? strutturaModelloDinamicoRepository, bool aggiungiPdfSchedeAListaAllegati)
        {
            this._log.DebugFormat("Generazione del riepilogo della domanda {0}, aggiungiPdfSchedeAListaAllegati {1}", idDomanda, aggiungiPdfSchedeAListaAllegati);

            var domanda = await this._caricamentoDomandaStrategy.GetByIdAsync(idDomanda);

            return await this.GeneraRiepilogoAsync(domanda, reader, aggiungiPdfSchedeAListaAllegati, strutturaModelloDinamicoRepository);
        }

        private async Task<BinaryFile> GeneraRiepilogoAsync(DomandaOnline domanda, IModelloDomandaReader modelloDomandaReader, bool aggiungiPdfSchedeAListaAllegati, IStrutturaModelloDinamicoRepository? strutturaModelloDinamicoRepository = null)
        {
            var modello = modelloDomandaReader.Read();

            var idDomanda = domanda.DataKey.ToString();

            var istanzaXml = this._istanzaSigeproAdapterService.ToIstanzaBackoffice(
                    domanda.ReadInterface,
                    new IstanzaSigeproAdapterFlags
                    {
                        AggiungiPdfSchedeAListaAllegati = aggiungiPdfSchedeAListaAllegati,
                        RecuperaMetadatiToken = true
                    }).ToXmlModelloRiepilogo(idDomanda);

            var risultatoTrasformazione = modello.Trasforma(istanzaXml);
            // Processo i segnaposto delle schede dinamiche
            var istanzaSigeproReader = this._domandaOnlineDatiDinamiciReaderFactory.Create(domanda, strutturaModelloDinamicoRepository);
            var risultatoTrasformazioneConSchede = await this._sostituzioneSegnapostoRiepilogoService.ProcessaRiepilogoAsync(istanzaSigeproReader, risultatoTrasformazione);

            var nomeFile = String.Format("modello-domanda.{0}.pdf", idDomanda);
            var pdf = await this._fileConverter.ConvertiAsync(nomeFile, risultatoTrasformazioneConSchede);

            return pdf;
        }


    }
}
