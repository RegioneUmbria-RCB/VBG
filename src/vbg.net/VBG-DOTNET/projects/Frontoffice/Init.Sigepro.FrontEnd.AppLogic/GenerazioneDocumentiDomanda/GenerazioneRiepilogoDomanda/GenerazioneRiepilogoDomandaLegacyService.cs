using Init.Sigepro.FrontEnd.AppLogic.Adapters;
using Init.Sigepro.FrontEnd.AppLogic.ConversionePDF;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.Common;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.LetturaDatiDinamici.LetturaDaDomandaOnline;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using Init.Sigepro.FrontEnd.Infrastructure.Server;
using System;
using System.IO;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda
{
    public class GenerazioneRiepilogoDomandaLegacyService
    {
        private readonly ISalvataggioDomandaStrategy _caricamentoDomandaStrategy;
        private readonly IOggettiService _oggettiService;
        private readonly IIstanzaSigeproAdapterService _istanzaSigeproAdapterService;
        private readonly SostituzioneSegnapostoRiepilogoService _sostituzioneSegnapostoRiepilogoService;
        private readonly IDatiDinamiciRepository _datiDinamiciRepository;
        private readonly IPathMapper _pathMapper;
        private readonly IHtmlToPdfFileConverter _fileConverter;
        private readonly IStrutturaModelloDinamicoRepository _strutturaModelloDinamicoRepository;

        public GenerazioneRiepilogoDomandaLegacyService(ISalvataggioDomandaStrategy caricamentoDomandaStrategy, IOggettiService oggettiService,
            IIstanzaSigeproAdapterService istanzaSigeproAdapterService, SostituzioneSegnapostoRiepilogoService sostituzioneSegnapostoRiepilogoService,
            IDatiDinamiciRepository datiDinamiciRepository, IPathMapper pathMapper, IHtmlToPdfFileConverter fileConverter, IStrutturaModelloDinamicoRepository strutturaModelloDinamicoRepository)
        {
            this._caricamentoDomandaStrategy = caricamentoDomandaStrategy;
            this._oggettiService = oggettiService;
            this._istanzaSigeproAdapterService = istanzaSigeproAdapterService;
            this._sostituzioneSegnapostoRiepilogoService = sostituzioneSegnapostoRiepilogoService;
            this._datiDinamiciRepository = datiDinamiciRepository;
            this._pathMapper = pathMapper;
            this._fileConverter = fileConverter;
            this._strutturaModelloDinamicoRepository = strutturaModelloDinamicoRepository;
        }

        public BinaryFile GeneraRiepilogoDomanda(int idDomanda, bool aggiungiPdfSchedeAListaAllegati, bool dumpXml)
        {
            var domanda = this._caricamentoDomandaStrategy.GetById(idDomanda);
            var oggettoRiepilogo = domanda.ReadInterface.Documenti.Intervento.GetRiepilogoDomanda();

            if (oggettoRiepilogo == null || !oggettoRiepilogo.CodiceOggettoModello.HasValue)
            {
                throw new Exception("Non è stato definito un riepilogo di domanda per la domanda con id " + idDomanda);
            }

            return this.GeneraRiepilogoDomanda(domanda, oggettoRiepilogo.CodiceOggettoModello.Value, aggiungiPdfSchedeAListaAllegati, dumpXml);
        }

        public BinaryFile GeneraRiepilogoDomanda(DomandaOnline domanda, int idFileModello, bool aggiungiPdfSchedeAListaAllegati, bool dumpXml = false)
        {
            var oggetto = this._oggettiService.GetById(idFileModello);
            var idDomanda = domanda.DataKey.ToString();

            if (oggetto == null)
                throw new ArgumentException("L'oggetto " + idFileModello + " non è stato trovato");

            var istanzaXml = this._istanzaSigeproAdapterService.ToIstanzaBackoffice(
                    domanda.ReadInterface,
                    new IstanzaSigeproAdapterFlags
                    {
                        AggiungiPdfSchedeAListaAllegati = aggiungiPdfSchedeAListaAllegati,
                        RecuperaMetadatiToken = true
                    }).ToXmlModelloRiepilogo(idDomanda);

            if (dumpXml)
            {
                this.DumpDatiDomanda(idDomanda, istanzaXml);
            }

            var risultatoTrasformazione = new XslFile(oggetto.FileContent).Trasforma(istanzaXml);

            // Nel caso in cui il modello contenga il segnaposto delle schede dinamiche utilizzo il servizio
            // per leggerle in formato html
            var reader = new DomandaOnlineDatiDinamiciReader(domanda, this._datiDinamiciRepository, this._strutturaModelloDinamicoRepository, this._istanzaSigeproAdapterService);
            var risultatoTrasformazioneConSchede = this._sostituzioneSegnapostoRiepilogoService.ProcessaRiepilogo(reader, risultatoTrasformazione);

            var nomeFile = String.Format("modello-domanda.{0}.pdf", idDomanda);
            var pdf = this._fileConverter.Converti(nomeFile, risultatoTrasformazioneConSchede);

            return pdf;
        }

        private void DumpDatiDomanda(string idDomanda, string istanzaXml)
        {
            if (this._pathMapper.IsPathMappingSupported)
            {
                var path = this._pathMapper.MapPath("~/logs/");
                path = Path.Combine(path, $"riepilogo_{idDomanda}.xml");
                using (var fs = File.Open(path, FileMode.CreateNew))
                {
                    fs.Write(Encoding.UTF8.GetBytes(istanzaXml), 0, Encoding.UTF8.GetByteCount(istanzaXml));
                }
            }
        }
    }
}
