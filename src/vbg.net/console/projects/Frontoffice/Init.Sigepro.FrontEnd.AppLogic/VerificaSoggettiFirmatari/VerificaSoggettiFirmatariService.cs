using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDocumenti;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.VerificaSoggettiFirmatari
{
    public class VerificaSoggettiFirmatariService
    {
        public class ConfigurazioneSoggettiFirmatari
        {
            public Dictionary<int, IEnumerable<TipoSoggettoFirmatario>> SoggettiIntervento { get; private set; }
            public Dictionary<int, IEnumerable<TipoSoggettoFirmatario>> SoggettiEndo { get; private set; }

            public ConfigurazioneSoggettiFirmatari()
            {
                this.SoggettiIntervento = new Dictionary<int, IEnumerable<TipoSoggettoFirmatario>>();
                this.SoggettiEndo = new Dictionary<int, IEnumerable<TipoSoggettoFirmatario>>();
            }
        }


        IVerificaFirmaDigitaleService _verificaFirmaService;
        AreaRiservataServiceCreatorV2 _serviceCreator;
        ISalvataggioDomandaStrategy _salvataggioStrategy;
        IOggettiService _oggettiService;

        internal VerificaSoggettiFirmatariService(IVerificaFirmaDigitaleService verificaFirmaService, AreaRiservataServiceCreatorV2 serviceCreator, ISalvataggioDomandaStrategy salvataggioStrategy, IOggettiService oggettiService)
        {
            this._verificaFirmaService = verificaFirmaService;
            this._serviceCreator = serviceCreator;
            this._salvataggioStrategy = salvataggioStrategy;
            this._oggettiService = oggettiService;
        }

        public bool VerificaSoggettoFirmatario(BinaryFile file, IEnumerable<SoggettoFirmatario> soggettiPossibili)
        {
            var esitoVerificaFirma = this._verificaFirmaService.VerificaFirmaDigitale(file);

            if (esitoVerificaFirma.Stato == StatoVerificaFirma.FirmaValida)
            {
                foreach (var soggetto in soggettiPossibili)
                {
                    if (soggetto.VerificaPresenza(esitoVerificaFirma))
                    {
                        return true;
                    }
                }
            }

            return false;
        }

        public ConfigurazioneSoggettiFirmatari GetTipiSoggettoDaDocumenti(IEnumerable<int> idDocumentiIntervento, IEnumerable<int> idDocumentiEndo)
        {
            using (var ws = this._serviceCreator.CreateClient())
            {
                var tipoSoggettoDaIdDocumento = ws.Service.GetSoggettiFirmatariDaIdDocumenti(ws.Token, new AreaRiservataService.RichiestaSoggettiFirmatariDaIdDocumenti
                {
                    IdDocumentiIntervento = idDocumentiIntervento.ToArray(),
                    IdDocumentiEndo = idDocumentiEndo.ToArray()
                });

                var configurazione = new ConfigurazioneSoggettiFirmatari();

                if (tipoSoggettoDaIdDocumento.SoggettiAllegatiIntervento != null)
                {
                    foreach (var element in tipoSoggettoDaIdDocumento.SoggettiAllegatiIntervento)
                    {
                        configurazione.SoggettiIntervento.Add(element.CodiceDocumento, element.TipiSoggetto.Select(ts => new TipoSoggettoFirmatario(ts.Id, ts.Descrizione)));
                    }
                }

                if (tipoSoggettoDaIdDocumento.SoggettiAllegatiEndo != null)
                {
                    foreach (var element in tipoSoggettoDaIdDocumento.SoggettiAllegatiEndo)
                    {
                        configurazione.SoggettiEndo.Add(element.CodiceDocumento, element.TipiSoggetto.Select(ts => new TipoSoggettoFirmatario(ts.Id, ts.Descrizione)));
                    }
                }

                return configurazione;
            }
            
        }

        public EsitoVerificaSoggetti Verifica(int idDomanda)
        {
            var domanda = this._salvataggioStrategy.GetById(idDomanda);

            var idDocumentiIntervento = domanda.ReadInterface.Documenti.Intervento.Documenti
                                                .Where(x => x.IdRiferimentoBackoffice.HasValue)
                                                .Select(x => x.IdRiferimentoBackoffice.Value);

            var idDocumentiEndo = domanda.ReadInterface.Documenti.Endo.Documenti
                                                .Where(x => x.IdRiferimentoBackoffice.HasValue)
                                                .Select(x => x.IdRiferimentoBackoffice.Value);

            var firmatariRichiestiDictionary = GetTipiSoggettoDaDocumenti(idDocumentiIntervento, idDocumentiEndo);
            var anagraficheDomanda = domanda.ReadInterface.Anagrafiche.Anagrafiche;
            var esitoValidazione = new EsitoVerificaSoggetti();

            Verifica(esitoValidazione, firmatariRichiestiDictionary.SoggettiIntervento, domanda.ReadInterface.Documenti.Intervento, anagraficheDomanda);
            Verifica(esitoValidazione, firmatariRichiestiDictionary.SoggettiEndo, domanda.ReadInterface.Documenti.Endo, anagraficheDomanda);

            return esitoValidazione;
        }

        private void Verifica(EsitoVerificaSoggetti esitoValidazione, Dictionary<int, IEnumerable<TipoSoggettoFirmatario>> configurazioneSoggetti, IGetByRiferimentoBackoffice repositoryDocumenti, IEnumerable<AnagraficaDomanda> anagraficheDomanda)
        {
            foreach (var idDocumento in configurazioneSoggetti.Keys)
            {
                var firmatariRichiesti = configurazioneSoggetti[idDocumento];
                var codiciFirmatariRichiesti = firmatariRichiesti.Select(f => f.Codice);
                var documentoDaVerificare = repositoryDocumenti.GetByRiferimentoBackoffice(idDocumento);
                var firmatariRichiestiPresenti = anagraficheDomanda.Where(x => codiciFirmatariRichiesti.Contains(x.TipoSoggetto.Id.Value))
                                                                    .Select(x => new SoggettoFirmatario(x.Codicefiscale, x.Nome, x.Nominativo, x.TipoSoggetto.Descrizione));

                if (documentoDaVerificare.AllegatoDellUtente == null)
                {
                    // Per ora non restituisco errori nel caso in cui l'utente non abbia allegato il file
                    //esitoValidazione.ErroreDocumentoNonPresente(documentoDaVerificare.Descrizione);

                    continue;
                }

                if (firmatariRichiestiPresenti.Count() == 0)
                {
                    esitoValidazione.AggiungiErroreSoggettiNonTrovati(documentoDaVerificare.Descrizione, firmatariRichiesti);

                    continue;
                }

                var file = this._oggettiService.GetById(documentoDaVerificare.AllegatoDellUtente.CodiceOggetto);

                var esito = VerificaSoggettoFirmatario(file, firmatariRichiestiPresenti);

                if (!esito)
                {
                    esitoValidazione.AggiungiErroreVerificaFallita(documentoDaVerificare.Descrizione, file.FileName, firmatariRichiestiPresenti);
                }
            }
        }
    }
}
