using Init.Sigepro.FrontEnd.AppLogic.Adapters;
using Init.Sigepro.FrontEnd.AppLogic.ConversionePDF;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.Visura;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.Common;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.LetturaDatiDinamici.LetturaDaVisuraSigepro;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda
{
    public class RiepilogoDomandaDaVisuraService
    {

        IVisuraService _visuraService;
        RiepilogoDomanda _riepilogoDomanda;
        IOggettiService _oggettiService;
        AllegatiInterventoService _allegatiInterventoService;
        IHtmlToPdfFileConverter _fileConverter;
        SostituzioneSegnapostoRiepilogoService _sostituzioneSegnapostoRiepilogoService;
        IDatiDinamiciRepository _datiDinamiciRepository;
        VisuraDyn2DataAccessProvider _visuraDAP;

        public RiepilogoDomandaDaVisuraService(IVisuraService visuraService, RiepilogoDomanda riepilogoDomanda, IOggettiService oggettiService, AllegatiInterventoService allegatiInterventoService, IHtmlToPdfFileConverter fileConverter, SostituzioneSegnapostoRiepilogoService sostituzioneSegnapostoRiepilogoService, IDatiDinamiciRepository datiDinamiciRepository, VisuraDyn2DataAccessProvider visuraDAP)
        {
            this._visuraService = visuraService;
            this._riepilogoDomanda = riepilogoDomanda;
            this._oggettiService = oggettiService;
            this._allegatiInterventoService = allegatiInterventoService;
            this._fileConverter = fileConverter;
            this._sostituzioneSegnapostoRiepilogoService = sostituzioneSegnapostoRiepilogoService;
            this._datiDinamiciRepository = datiDinamiciRepository;
            this._visuraDAP = visuraDAP;
        }



        public BinaryFile GeneraRiepilogoDomanda(string uuidIstanza)
        {
            var istanza = this._visuraService.GetByUuid(uuidIstanza);

            if (istanza == null)
            {
                throw new ArgumentException($"Uuid istanza {uuidIstanza} no valido");
            }
            var codiceriepilogo = this._allegatiInterventoService.GetCodiceOggettoDelModelloDiRiepilogo(Convert.ToInt32(istanza.CODICEINTERVENTOPROC));
            
            if (!codiceriepilogo.HasValue)
            {
                throw new Exception($"Impossibile rigenerare il riepilogo di domanda perchè l'intervento {istanza.CODICEINTERVENTOPROC} non ha un modello di riepilogo definito");
            }
            var oggettoRiepilogo = this._oggettiService.GetById(codiceriepilogo.Value);

            RipristinaSoggettiCollegati(istanza);


            var istanzaXml = IstanzaSigeproAdapter.ConvertiIstanzaPerCompilazioneModello(istanza);

            var risultatoTrasformazione = new XslFile(oggettoRiepilogo.FileContent).Trasforma(istanzaXml);

            // Nel caso in cui il modello contenga il segnaposto delle schede dinamiche utilizzo il servizio
            // per leggerle in formato html
            var reader = new VisuraSigeproDatiDinamiciReader(istanza, this._datiDinamiciRepository, this._visuraDAP);
            var risultatoTrasformazioneConSchede = _sostituzioneSegnapostoRiepilogoService.ProcessaRiepilogo(reader, risultatoTrasformazione);

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
                DESCRSOGGETTO = istanza.TipoSoggetto.TIPOSOGGETTO
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
