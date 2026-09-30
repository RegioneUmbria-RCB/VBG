using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.ConversionePDF;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.Common;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.LetturaDatiDinamici.LetturaDaVisuraSigepro;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiDomanda.RiepilogoDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using System;
using System.Collections.Generic;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda
{
    public class RiepilogoDomandaDaVisuraService
    {
        private readonly IVisuraService _visuraService;
        private readonly IOggettiService _oggettiService;
        private readonly RiepilogoDomandaAllegatoService _riepilogoDomandaService;
        private readonly IHtmlToPdfFileConverter _fileConverter;
        private readonly SostituzioneSegnapostoRiepilogoService _sostituzioneSegnapostoRiepilogoService;
        private readonly IDatiDinamiciRepository _datiDinamiciRepository;
        private readonly IConfigurazione<ParametriVisura> _configurazione;
        private readonly IStrutturaModelloDinamicoRepository _strutturaModelloDinamicoRepository;

        public RiepilogoDomandaDaVisuraService(IVisuraService visuraService, IOggettiService oggettiService,
            RiepilogoDomandaAllegatoService allegatiInterventoService, IHtmlToPdfFileConverter fileConverter,
            SostituzioneSegnapostoRiepilogoService sostituzioneSegnapostoRiepilogoService, IDatiDinamiciRepository datiDinamiciRepository,
            IConfigurazione<ParametriVisura> configurazione, IStrutturaModelloDinamicoRepository strutturaModelloDinamicoRepository)
        {
            this._visuraService = visuraService;
            this._oggettiService = oggettiService;
            this._riepilogoDomandaService = allegatiInterventoService;
            this._fileConverter = fileConverter;
            this._sostituzioneSegnapostoRiepilogoService = sostituzioneSegnapostoRiepilogoService;
            this._datiDinamiciRepository = datiDinamiciRepository;
            this._configurazione = configurazione;
            this._strutturaModelloDinamicoRepository = strutturaModelloDinamicoRepository;
        }

        public bool PermetteRigenerazioneRiepilogo(int codiceIntervento)
        {
            var codiceriepilogo = this._riepilogoDomandaService.GetCodiceOggettoDelModelloDiRiepilogo(codiceIntervento);

            if (this._configurazione.Parametri.NascondiRigeneraRiepilogo)
            {
                return false;
            }

            return codiceriepilogo.HasValue;
        }



        public BinaryFile GeneraRiepilogoDomanda(string uuidIstanza)
        {
            var istanza = this._visuraService.GetByUuid(uuidIstanza, false);

            if (istanza == null)
            {
                throw new ArgumentException($"Uuid istanza {uuidIstanza} no valido");
            }
            var codiceriepilogo = this._riepilogoDomandaService.GetCodiceOggettoDelModelloDiRiepilogo(Convert.ToInt32(istanza.CODICEINTERVENTOPROC));

            if (!codiceriepilogo.HasValue)
            {
                throw new Exception($"Impossibile rigenerare il riepilogo di domanda perchè l'intervento {istanza.CODICEINTERVENTOPROC} non ha un modello di riepilogo definito");
            }
            var oggettoRiepilogo = this._oggettiService.GetById(codiceriepilogo.Value);

            this.RipristinaSoggettiCollegati(istanza);


            var istanzaXml = istanza.ToXmlModelloRiepilogo();

            var risultatoTrasformazione = new XslFile(oggettoRiepilogo.FileContent).Trasforma(istanzaXml);

            // Nel caso in cui il modello contenga il segnaposto delle schede dinamiche utilizzo il servizio
            // per leggerle in formato html
            var reader = new VisuraSigeproDatiDinamiciReader(istanza, this._datiDinamiciRepository, this._strutturaModelloDinamicoRepository);
            var risultatoTrasformazioneConSchede = this._sostituzioneSegnapostoRiepilogoService.ProcessaRiepilogo(reader, risultatoTrasformazione);

            var nomeFile = $"modello-domanda.{istanza.CODICEISTANZA}.pdf";
            var pdf = this._fileConverter.Converti(nomeFile, risultatoTrasformazioneConSchede);

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
