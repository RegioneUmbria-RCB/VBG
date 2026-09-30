using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche.LogicaRisoluzioneSoggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using System;
using System.Collections.Generic;
using System.Linq;
#if NET48
using Init.Sigepro.FrontEnd.Infrastructure.IOC;
#endif
namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche
{
    public class AnagraficheReadInterface : IAnagraficheReadInterface
    {
        private readonly PresentazioneIstanzaDataKey _dataKey;
        private readonly PresentazioneIstanzaDbV2 _database;
        private readonly List<AnagraficaDomanda> _anagrafiche;
        private readonly IEnumerable<AnagraficaDomanda> _richiedenti;
        private readonly IEnumerable<AnagraficaDomanda> _aziende;

        public AnagraficheReadInterface(PresentazioneIstanzaDataKey dataKey, PresentazioneIstanzaDbV2 database)
        {
            this._dataKey = dataKey;
            this._database = database;

            this._anagrafiche = this._database.ANAGRAFE
                                  .Cast<PresentazioneIstanzaDbV2.ANAGRAFERow>()
                                  .Select(x => AnagraficaDomanda.FromAnagrafeRow(x)).ToList();

            this.CollegaAnagrafiche();

            this._richiedenti = this._anagrafiche
                        .Where(x => x.TipoSoggetto.Ruolo == RuoloTipoSoggettoDomandaEnum.Richiedente);


            this._aziende = this._anagrafiche
                                .Where(x => x.TipoSoggetto.Ruolo == RuoloTipoSoggettoDomandaEnum.Azienda);
        }


        private void CollegaAnagrafiche()
        {
            this._anagrafiche.ForEach(x =>
            {
                if (!x.IdAnagraficaCollegata.HasValue)
                    return;

                x.CollegaAnagrafica(this.GetById(x.IdAnagraficaCollegata.Value)!);
            });
        }

        public AnagraficaDomanda? GetById(int idAnagrafica)
        {
            return this._anagrafiche.FirstOrDefault(x => x.Id!.Value == idAnagrafica);
        }


        public IEnumerable<AnagraficaDomanda> Anagrafiche
        {
            get { return this._anagrafiche; }
        }

        public IEnumerable<AnagraficaDomanda> GetRichiedenti() => this._richiedenti;

        public IEnumerable<AnagraficaDomanda> GetAltriSoggetti(ILogicaRisoluzioneTecnico logicaRisoluzioneTecnico)
        {
            var richiedente = this.GetRichiedente();
            var tecnico = this.GetTecnico(logicaRisoluzioneTecnico);
            var azienda = this.GetAzienda();

            return this._anagrafiche.Where(x => x != richiedente && x != tecnico && x != azienda);
        }

        public AnagraficaDomanda? GetRichiedente()
        {
            var listaRichiedenti = this.GetRichiedenti();

            if (!listaRichiedenti.Any())
                return null;

            // L'utente loggato è uno dei richiedenti?
            var utenteLoggato = listaRichiedenti.FirstOrDefault(x => x.Codicefiscale == this._dataKey.CodiceUtente);

            if (utenteLoggato is not null)
            {
                return utenteLoggato;
            }

            // Leggo la lista dei richiedenti che hanno una PEC valida
            var listaRichiedentiConPec = listaRichiedenti.Where(x => !String.IsNullOrEmpty(x.Contatti.Pec));

            // Se esistono richiedenti con pec hanno la priorità sui richiedenti senza PEC
            if (listaRichiedentiConPec.Any())
            {
                // Nella lista esistono soggetti senza procura?
                var soggettoConPecSenzaProcura = this.EstraiPrimoRichiedenteSenzaProcura(listaRichiedentiConPec);

                if (soggettoConPecSenzaProcura is not null)
                    return soggettoConPecSenzaProcura;

                return listaRichiedentiConPec.ElementAt(0);
            }

            // Non esistono soggetti con PEC, cerco nella lista di tutti i richiedenti se ne esiste uno senza procura
            var soggettoSenzaPecSenzaProcura = this.EstraiPrimoRichiedenteSenzaProcura(listaRichiedenti);

            if (soggettoSenzaPecSenzaProcura is not null)
                return soggettoSenzaPecSenzaProcura;

            // Nessun soggetto ha la pec e tutti i soggetti hanno la procura. A questo punto un soggetto vale l'altro 
            // e restituisco il primo soggetto della lista
            return listaRichiedenti.First();
        }

        private AnagraficaDomanda? EstraiPrimoRichiedenteSenzaProcura(IEnumerable<AnagraficaDomanda> listaRichiedenti)
        {
            foreach (var richiedente in listaRichiedenti)
            {
                var codiceProcuratore = this._database.Procure
                                                      .Where(x => x.CodiceAnagrafe.ToUpperInvariant() == richiedente.Codicefiscale.ToUpperInvariant())
                                                      .Select(x => x.CodiceProcuratore);

                if (codiceProcuratore.Any() && String.IsNullOrEmpty(codiceProcuratore.ElementAt(0)))
                    return richiedente;
            }

            return null;
        }

        public AnagraficaDomanda? GetTecnico(ILogicaRisoluzioneTecnico logicaRisoluzioneTecnico)
        {
            return logicaRisoluzioneTecnico.Risolvi(this._anagrafiche);
        }

        public AnagraficaDomanda? GetAzienda()
        {
            // il richiedente della pratica è collegato ad un'anagrafica con ruolo azienda? In tal caso restituisco l'anagrafica collegata al richiedente
            var richiedente = this.GetRichiedente();

            if (richiedente?.AnagraficaCollegata?.TipoSoggetto?.Ruolo == RuoloTipoSoggettoDomandaEnum.Azienda)
            {
                return richiedente.AnagraficaCollegata;
            }


            return this._aziende?.FirstOrDefault();
        }

        public AnagraficaDomanda FindByRiferimentiSoggetto(TipoPersonaEnum tipoPersona, string codiceFiscalePartitaIva)
        {
            return this._anagrafiche.Where(x => x.TipoPersona == tipoPersona &&
                                                (x.Codicefiscale.ToUpperInvariant() == codiceFiscalePartitaIva.ToUpperInvariant() ||
                                                  x.PartitaIva.ToUpperInvariant() == codiceFiscalePartitaIva.ToUpperInvariant()))
                                    .FirstOrDefault();
        }


        public IEnumerable<AnagraficaDomanda> GetPossibiliProcuratoriDi(string codiceFiscaleUtente)
        {
            return this._anagrafiche.Where(x => x.TipoPersona == TipoPersonaEnum.Fisica &&
                                                 !x.Codicefiscale.Equals(codiceFiscaleUtente, StringComparison.InvariantCultureIgnoreCase));
        }

        public IEnumerable<AnagraficaDomanda> GetSoggettiSottoscrittori()
        {
            var rVal = new List<AnagraficaDomanda>();

            foreach (var procurato in this._database.Procure)
            {
                var soggettoProcurato = !String.IsNullOrEmpty(procurato.CodiceProcuratore);
                var codiceFiscale = soggettoProcurato ? procurato.CodiceProcuratore : procurato.CodiceAnagrafe;

                if (rVal.FirstOrDefault(r => r.Codicefiscale == codiceFiscale) is not null)
                    continue;

                var q = this._anagrafiche.Where(x => x.Codicefiscale.ToUpperInvariant() == codiceFiscale.ToUpperInvariant());

                // Se ho più di una riga il soggetto potrebbe essere in qualità di altro tipo soggetto che non può firmare
                // come ad esempio tecnico. Verifico prima che ci sia una qualifica che permette la firma altrimenti
                // uso il tipo soggetto impostato
                if (q.Count() == 1)
                {
                    rVal.Add(q.First());
                }
                else
                {
                    var tipoSoggettoTrovato = false;

                    foreach (var soggetto in q)
                    {
                        if (soggetto.TipoSoggetto.Ruolo == RuoloTipoSoggettoDomandaEnum.Richiedente)
                        {
                            rVal.Add(soggetto);
                            tipoSoggettoTrovato = true;
                            break;
                        }
                    }

                    if (!tipoSoggettoTrovato)
                        rVal.Add(q.First());
                }
            }

            return rVal;
        }

        public IEnumerable<AnagraficaDomanda> GetSoggettiNonSottoscrittori()
        {
            var rVal = new List<AnagraficaDomanda>();

            var listaProcurati = this._database.Procure.Where(x => !String.IsNullOrEmpty(x.CodiceProcuratore));

            foreach (var procurato in listaProcurati)
            {
                if (rVal.FirstOrDefault(x => x.Codicefiscale.Equals(procurato.CodiceAnagrafe, StringComparison.InvariantCultureIgnoreCase)) is not null)
                {
                    continue;
                }

                var soggetto = this.FindByRiferimentiSoggetto(TipoPersonaEnum.Fisica, procurato.CodiceAnagrafe);

                if (soggetto is not null)
                {
                    rVal.Add(soggetto);
                }
            }

            return rVal;
        }

        public IEnumerable<AnagraficaDomanda> GetAnagraficheCollegabili()
        {
            return this._anagrafiche.Where(x => x.TipoPersona == TipoPersonaEnum.Giuridica);
        }


        public AnagraficaDomanda? GetLegaleRappresentanteDi(AnagraficaDomanda azienda, ITipiSoggettoService tipiSoggettoService)
        {
            // Provo ad estrarre il legale rappresentante (è la prima persona fisica collegata a questa anagrafica con flag_legalerapp = 1)
            var res = this.Anagrafiche.Where(lr =>
            {
                return lr.TipoPersona == TipoPersonaEnum.Fisica &&
                            lr.AnagraficaCollegata != null &&
                            lr.IdAnagraficaCollegata == azienda.Id &&
                            this.IsLegaleRappresentante(tipiSoggettoService, lr.TipoSoggetto.Id.Value);
            });

            return res.FirstOrDefault();
        }

        private bool IsLegaleRappresentante(ITipiSoggettoService tipiSoggettoService, int codiceTipoSoggetto)
        {
            return tipiSoggettoService.GetById(codiceTipoSoggetto).FlagLegaleRappresentante;
        }
    }
}
