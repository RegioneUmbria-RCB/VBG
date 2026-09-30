using GeneratoreRiepiloghiHtml.AppLogic.GenerazioneRiepiloghi.GestioneSegnapostoRiepilogo;
using GeneratoreRiepiloghiHtml.AppLogic.GenerazioneRiepiloghi.RiepiloghiSchede;
using GeneratoreRiepiloghiHtml.AppLogic.GenerazioneRiepiloghiSchede.FileConverter;
using GeneratoreRiepiloghiHtml.AppLogic.GestioneInterventi;
using GeneratoreRiepiloghiHtml.AppLogic.GestioneOggetti;
using GeneratoreRiepiloghiHtml.AppLogic.Visura;
using VisuraVbg;

namespace GeneratoreRiepiloghiHtml.AppLogic.GenerazioneRiepiloghi.RiepiloghiIstanze
{
    public class GeneratoreRiepilogoDomanda
    {
        private readonly IVisuraService _visuraService;
        private readonly IInterventiService _allegatiInterventoService;
        private readonly IOggettiService _oggettiService;
        private readonly IHtmlToPdfFileConverter _fileConverter;
        private readonly SostituzioneSegnapostoRiepilogoService _sostituzioneSegnapostoRiepilogoService;
        private readonly ILogger<GeneratoreRiepilogoDomanda> _logger;

        public GeneratoreRiepilogoDomanda(IVisuraService visuraService, IInterventiService allegatiInterventoService, IOggettiService oggettiService,
                                        IHtmlToPdfFileConverter fileConverter, GeneratoreRiepilogoSingolaSchedaService generatoreRiepilogoSchede,
                                        SostituzioneSegnapostoRiepilogoService sostituzioneSegnapostoRiepilogoService, ILogger<GeneratoreRiepilogoDomanda> logger)
        {
            this._visuraService = visuraService;
            this._allegatiInterventoService = allegatiInterventoService;
            this._oggettiService = oggettiService;
            this._fileConverter = fileConverter;
            this._sostituzioneSegnapostoRiepilogoService = sostituzioneSegnapostoRiepilogoService;
            this._logger = logger;
        }

        public async Task<BinaryFile> GeneraRiepilogoDomandaAsync(int idIstanza)
        {
            var istanza = await this._visuraService.GetDettaglioPraticaAsync(idIstanza);

            if (istanza == null)
            {
                throw new ArgumentException($"Id istanza {idIstanza} non valido");
            }

            return await this.GeneraRiepilogoDomandaAsync(istanza);
        }

        public async Task<BinaryFile> GeneraRiepilogoDomandaAsync(Istanze istanza)
        {
            using var scope = this._logger.BeginScope("Generazione del riepilogo della domanda per l'istanza {codiceIstanza}", istanza.CODICEISTANZA);

            var codiceriepilogo = await this._allegatiInterventoService.GetCodiceOggettoDelModelloDiRiepilogoAsync(Convert.ToInt32(istanza.CODICEINTERVENTOPROC));

            if (!codiceriepilogo.HasValue)
            {
                throw new Exception($"Impossibile rigenerare il riepilogo di domanda perchè l'intervento {istanza.CODICEINTERVENTOPROC} non ha un modello di riepilogo definito");
            }
            BinaryFile oggettoRiepilogo = await this._oggettiService.GetByIdAsync(codiceriepilogo.Value);

            this.RipristinaSoggettiCollegati(istanza);

            var istanzaXml = istanza.ToXmlModelloRiepilogo();

            this._logger.LogDebug("Istanza in formato XML: {istanzaXml}", istanzaXml);

            var risultatoTrasformazione = new XslFile(oggettoRiepilogo.FileContent).Trasforma(istanzaXml);

            this._logger.LogDebug("Riepilogo trasformato in HTML: {risultatoTrasformazione}", risultatoTrasformazione);

            // Nel caso in cui il modello contenga il segnaposto delle schede dinamiche utilizzo il servizio
            // per leggerle in formato html
            var risultatoTrasformazioneConSchede = await this._sostituzioneSegnapostoRiepilogoService.ProcessaRiepilogoAsync(risultatoTrasformazione, istanza);

            this._logger.LogDebug("Riepilogo trasformato in HTML con schede dinamiche: {risultatoTrasformazioneConSchede}", risultatoTrasformazioneConSchede);

            var nomeFile = $"modello-domanda.{istanza.CODICEISTANZA}.pdf";
            var pdf = await this._fileConverter.ConvertiAsync(nomeFile, risultatoTrasformazioneConSchede);

            return pdf;
        }

        private void RipristinaSoggettiCollegati(Istanze istanza)
        {
            var nuoviRichiedenti = new List<IstanzeRichiedenti>(istanza.Richiedenti);

            // Richiedente
            var richiedente = new IstanzeRichiedenti
            {
                TipoSoggetto = istanza.TipoSoggetto,
                Richiedente = istanza.Richiedente,
                CODICETIPOSOGGETTO = istanza.TipoSoggetto?.CODICETIPOSOGGETTO,
                DESCRSOGGETTO = istanza.TipoSoggetto?.TIPOSOGGETTO
            };

            nuoviRichiedenti.Add(richiedente);

            if (istanza.Professionista != null)
            {
                richiedente.Procuratore = istanza.Professionista;

                var professionista = new IstanzeRichiedenti
                {
                    TipoSoggetto = new TipiSoggetto
                    {
                        TIPOSOGGETTO = "Tecnico",
                        TIPODATO = "T"
                    },
                    Richiedente = istanza.Professionista,
                    DESCRSOGGETTO = "Tecnico/Delegato",
                    AnagrafeCollegata = istanza.Richiedente
                };

                nuoviRichiedenti.Add(professionista);
            }

            if (istanza.AziendaRichiedente != null)
            {
                var azienda = new IstanzeRichiedenti
                {
                    TipoSoggetto = new TipiSoggetto
                    {
                        TIPOSOGGETTO = "Azienda",
                        TIPODATO = "A"
                    },
                    Richiedente = istanza.AziendaRichiedente,
                    DESCRSOGGETTO = "Azienda",
                    AnagrafeCollegata = istanza.Richiedente
                };

                nuoviRichiedenti.Add(azienda);
            }
            istanza.Richiedenti = nuoviRichiedenti.ToArray();
        }
    }
}
