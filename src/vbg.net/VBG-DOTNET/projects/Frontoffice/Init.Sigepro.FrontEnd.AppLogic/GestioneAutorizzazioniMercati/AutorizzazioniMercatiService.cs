using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche.Backend;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAutorizzazioniMercati
{
    public class AutorizzazioniMercatiService
    {
        private readonly ISalvataggioDomandaStrategy _salvataggioStrategy;
        private readonly AutorizzazioniMercatiRepository _repository;
        private readonly IAnagraficheBackendService _anagraficheService;

        internal AutorizzazioniMercatiService(ISalvataggioDomandaStrategy salvataggioStrategy, AutorizzazioniMercatiRepository repository, IAnagraficheBackendService anagraficheService)
        {
            this._salvataggioStrategy = salvataggioStrategy;
            this._repository = repository;
            this._anagraficheService = anagraficheService;
        }

        public IEnumerable<DatiCompattiAutorizzazione> GetAutorizzazioni(int idDomanda, string[] codiceRegistri, string stringaFormattazione)
        {
            var domanda = this._salvataggioStrategy.GetById(idDomanda);
            var codiceAnagrafeRichiesente = this.EstraiCodiceAnagrafeRichiedente(domanda);
            var codiceAnagrafeAzienda = this.EstraiCodiceAnagrafeAzienda(domanda);
            var codiceIntervento = domanda.ReadInterface.AltriDati.Intervento.Codice;

            if (codiceAnagrafeRichiesente != -1)
            {
                var autorizzazioniRichiedente = this
                                                    ._repository
                                                    .GetListaAutorizzazioni(codiceAnagrafeRichiesente, codiceRegistri, stringaFormattazione, codiceIntervento)
                                                    .Select(x => new DatiCompattiAutorizzazione { Codice = x.Codice, Descrizione = x.Descrizione });

                foreach (var aut in autorizzazioniRichiedente)
                    yield return aut;
            }

            if (codiceAnagrafeAzienda != -1)
            {
                var autorizzazioniAzienda = this
                                        ._repository
                                        .GetListaAutorizzazioni(codiceAnagrafeAzienda, codiceRegistri, stringaFormattazione, codiceIntervento)
                                        .Select(x => new DatiCompattiAutorizzazione { Codice = x.Codice, Descrizione = x.Descrizione });

                foreach (var aut in autorizzazioniAzienda)
                    yield return aut;
            }
        }



        public DatiEstesiAutorizzazione GetAutorizzazione(int idDomanda, int idAutorizzazione)
        {
            var domanda = this._salvataggioStrategy.GetById(idDomanda);
            var codiceIntervento = domanda.ReadInterface.AltriDati.Intervento.Codice;

            var autorizzazione = this._repository.GetDettagliAutorizzazione(idAutorizzazione, codiceIntervento);

            if (autorizzazione == null)
                return null;

            return new DatiEstesiAutorizzazione
            {
                Codice = autorizzazione.Codice,
                Data = autorizzazione.Data,
                Numero = autorizzazione.Numero,
                NumeroPresenze = autorizzazione.NumeroPresenze,
                Ente = new DatiEnteAutorizzazione
                {
                    Codice = autorizzazione.Ente.Codice,
                    Descrizione = autorizzazione.Ente.Descrizione
                }
            };
        }

        public IEnumerable<DatiEnteAutorizzazione> GetEnti()
        {
            return this._repository.GetListaEnti().Select(x => new DatiEnteAutorizzazione { Codice = x.Codice, Descrizione = x.Descrizione });
        }

        private int EstraiCodiceAnagrafeAzienda(GestionePresentazioneDomanda.DomandaOnline domanda)
        {
            if (domanda.ReadInterface.Anagrafiche.GetAzienda() == null)
                return -1;

            var cfAzienda = domanda.ReadInterface.Anagrafiche.GetAzienda().Codicefiscale;
            var pivaAzienda = domanda.ReadInterface.Anagrafiche.GetAzienda().PartitaIva;

            if (String.IsNullOrEmpty(cfAzienda))
                cfAzienda = pivaAzienda;

            var anagrafe = this._anagraficheService.RicercaAnagraficaBackoffice(TipoPersonaEnum.Giuridica, cfAzienda);

            if (anagrafe == null || String.IsNullOrEmpty(anagrafe.CODICEANAGRAFE))
                return -1;

            return Convert.ToInt32(anagrafe.CODICEANAGRAFE);
        }

        private int EstraiCodiceAnagrafeRichiedente(GestionePresentazioneDomanda.DomandaOnline domanda)
        {
            var cfRichiedente = domanda.ReadInterface.Anagrafiche.GetRichiedente().Codicefiscale;

            var anagrafe = this._anagraficheService.RicercaAnagraficaBackoffice(TipoPersonaEnum.Fisica, cfRichiedente);

            if (anagrafe == null || String.IsNullOrEmpty(anagrafe.CODICEANAGRAFE))
                return -1;

            return Convert.ToInt32(anagrafe.CODICEANAGRAFE);
        }

        public void ImpostaEstremiAutorizzazione(int idDomanda, string numero, string data, string idEnte, string ente, string enteNonTrovato, string numeroPresenze)
        {
            var domanda = this._salvataggioStrategy.GetById(idDomanda);

            if (String.IsNullOrEmpty(idEnte))
                ente = enteNonTrovato;

            domanda.WriteInterface.AutorizzazioniMercati.SalvaEstremiAutorizzazione(-1, numero, data, idEnte, ente, "0", numeroPresenze);

            this._salvataggioStrategy.Salva(domanda);
        }

        public void ImpostaEstremiAutorizzazione(int idDomanda, int idAutorizzazione, string numeroPresenzeDichiarate)
        {
            var domanda = this._salvataggioStrategy.GetById(idDomanda);
            var datiAutorizzazione = this._repository.GetDettagliAutorizzazione(idAutorizzazione, domanda.ReadInterface.AltriDati.Intervento.Codice);

            domanda
                .WriteInterface
                .AutorizzazioniMercati
                .SalvaEstremiAutorizzazione(
                    datiAutorizzazione.Codice,
                    datiAutorizzazione.Numero,
                    datiAutorizzazione.Data.ToString("dd/MM/yyyy"),
                    datiAutorizzazione.Ente.Codice,
                    datiAutorizzazione.Ente.Descrizione,
                    datiAutorizzazione.NumeroPresenze.ToString(),
                    numeroPresenzeDichiarate
                );


            this._salvataggioStrategy.Salva(domanda);
        }


        public void MappaDatiAutorizzazioneSuDatiDinamici(int idDomanda, MappaturaDatiAutorizzazioni mappatura)
        {
            var domanda = this._salvataggioStrategy.GetById(idDomanda);

            var valoriCampiDinamici = mappatura.Mappa(domanda.ReadInterface.AutorizzazioniMercati.Autorizzazione);

            foreach (var valore in valoriCampiDinamici)
                domanda.WriteInterface.DatiDinamici.AggiornaOCrea(valore.IdCampo, 0, 0, valore.Valore, valore.ValoreDecodificato, String.Empty);

            this._salvataggioStrategy.Salva(domanda);
        }
    }
}
