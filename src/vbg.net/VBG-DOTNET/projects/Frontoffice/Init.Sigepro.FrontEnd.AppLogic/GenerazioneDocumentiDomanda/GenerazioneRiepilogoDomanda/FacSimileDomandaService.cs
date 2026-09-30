using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiDomanda.Endoprocedimenti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiDomanda.Endoprocedimenti.LogicaSincronizzazione;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiDomanda.Intervento.LogicaSincronizzazione;
using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneInterventi;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDatiDinamici.Sincronizzazione;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneEndoprocedimenti.Sincronizzazione;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.SIGePro.Manager.DTO.Common;
using Init.SIGePro.Manager.DTO.DatiDinamici;
using Init.SIGePro.Manager.DTO.Endoprocedimenti;
using System.Collections.Generic;
using System.Linq;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda
{
    public class FacSimileDomandaService
    {
        private readonly IResolveDescrizioneIntervento _resolveDescrizioneIntervento;
        private readonly EndoprocedimentiService _endoprocedimentiService;
        private readonly IDatiDinamiciRepository _datiDinamiciRepository;
        private readonly IAllegatiEndoprocedimentiService _allegatiEndoService;
        private readonly ILogicaSincronizzazioneAllegatiIntervento _logicaSincronizzazioneAllegatiIntervento;
        private readonly IAliasSoftwareResolver _aliasSoftwareResolver;
        private readonly IInterventiRepository _interventiRepository;
        private readonly IInterventiAllegatiRepository _interventiAllegatiRepository;
        private readonly GenerazioneRiepilogoDomandaLegacyService _riepilogoDomanda;

        public FacSimileDomandaService(IResolveDescrizioneIntervento resolveDescrizioneIntervento, EndoprocedimentiService endoprocedimentiService,
            IDatiDinamiciRepository datiDinamiciRepository, IAllegatiEndoprocedimentiService allegatiEndoService,
            ILogicaSincronizzazioneAllegatiIntervento logicaSincronizzazioneAllegatiIntervento,
            IAliasSoftwareResolver aliasSoftwareResolver, IInterventiRepository interventiRepository,
            IInterventiAllegatiRepository interventiAllegatiRepository, GenerazioneRiepilogoDomandaLegacyService riepilogoDomanda)
        {
            this._resolveDescrizioneIntervento = resolveDescrizioneIntervento;
            this._endoprocedimentiService = endoprocedimentiService;
            this._datiDinamiciRepository = datiDinamiciRepository;
            this._allegatiEndoService = allegatiEndoService;
            this._logicaSincronizzazioneAllegatiIntervento = logicaSincronizzazioneAllegatiIntervento;
            this._aliasSoftwareResolver = aliasSoftwareResolver;
            this._interventiRepository = interventiRepository;
            this._interventiAllegatiRepository = interventiAllegatiRepository;
            this._riepilogoDomanda = riepilogoDomanda;
        }

        private DomandaOnline Genera(int idIntervento, IEnumerable<int> endoFacoltativiAttivati)
        {
            var domanda = DomandaOnline.FacSimile(this._aliasSoftwareResolver.AliasComune, this._aliasSoftwareResolver.Software);

            // CodiceComune
            domanda.WriteInterface.AltriDati.ImpostaCodiceComune(this._aliasSoftwareResolver.AliasComune, this._aliasSoftwareResolver.AliasComune);

            // Intervento
            domanda.WriteInterface.AltriDati.ImpostaIntervento(idIntervento, null, this._resolveDescrizioneIntervento);

            // Endo
            var endoprocedimenti = this._endoprocedimentiService.GetListaEndoDaIdIntervento(null, idIntervento);

            var listaIdEndoAttivati = this.EstraiListaEndo(endoprocedimenti.Principali).Select(x => x.Codice)
                                          .Union(this.EstraiListaEndo(endoprocedimenti.Richiesti).Select(x => x.Codice))
                                          .Union(this.EstraiListaEndo(endoprocedimenti.Ricorrenti)
                                                                                .Where(x => endoFacoltativiAttivati.Contains(x.Codice))
                                                                                .Select(x => x.Codice))
                                          .Union(this.EstraiListaEndo(endoprocedimenti.Altri)
                                                                                .Where(x => endoFacoltativiAttivati.Contains(x.Codice))
                                                                                .Select(x => x.Codice))
                                          .Select(x => new SubEndoprocedimentoSelezionato(x));

            domanda.WriteInterface.Endoprocedimenti.AggiungiESincronizza(listaIdEndoAttivati, new LogicaSincronizzazioneSubEndo(domanda, this._endoprocedimentiService, this._endoprocedimentiService));

            // modelli dinamici
            var schedeDinamicheRichieste = this._datiDinamiciRepository.GetSchedeDaInterventoEEndo(idIntervento, listaIdEndoAttivati.Select(x => x.Id), Enumerable.Empty<string>(), UsaTipiLocalizzazioniPerSelezionareSchedeDinamiche.No);

            var schedeintervento = schedeDinamicheRichieste.
                                    SchedeIntervento.
                                    Select(x => new ModelloDinamicoInterventoDaSincronizzare(x.CodiceIntervento, x.Id, x.Descrizione, TipoFirmaEnum.NessunaFirma, x.Facoltativa, x.Ordine.GetValueOrDefault(999)));

            var schedeEndo = schedeDinamicheRichieste.
                                    SchedeEndoprocedimenti.
                                    Select(x => new ModelloDinamicoEndoprocedimentoDaSincronizzare(x.CodiceEndo, x.Id, x.Descrizione, TipoFirmaEnum.NessunaFirma, x.Facoltativa, x.Ordine.GetValueOrDefault(999)));


            domanda.WriteInterface.DatiDinamici.SincronizzaModelliDinamici(new SincronizzaModelliDinamiciCommand(schedeintervento, schedeEndo, null));

            foreach (var modello in domanda.ReadInterface.DatiDinamici.Modelli)
                domanda.WriteInterface.DatiDinamici.ModificaStatoCompilazioneModello(modello.IdModello, 0, true);

            // Allegati intervento
            this._logicaSincronizzazioneAllegatiIntervento.Sincronizza(domanda);

            foreach (var allegato in domanda.ReadInterface.Documenti.Intervento.Documenti)
                domanda.WriteInterface.Documenti.AllegaFileADocumento(allegato.Id, -1, allegato.Richiesto ? "Allegato Obbligatorio" : "Allegato Non Obbligatorio", false);

            // Allegati endo
            new LogicaSincronizzazioneAllegatiEndo(domanda, this._allegatiEndoService).Sincronizza();

            foreach (var allegato in domanda.ReadInterface.Documenti.Endo.Documenti)
                domanda.WriteInterface.Documenti.AllegaFileADocumento(allegato.Id, -1, allegato.Richiesto ? "Allegato Obbligatorio" : "Allegato Non Obbligatorio", false);


            domanda.ReadInterface.Invalidate();

            return domanda;
        }

        public BinaryFile GeneraFacSimileDomanda(int idIntervento, IEnumerable<int> endoFacoltativiAttivati)
        {
            var allegati = this._interventiAllegatiRepository.GetAllegatiDaIdintervento(idIntervento, AmbitoRicerca.AreaRiservata);

            var domanda = this.Genera(idIntervento, endoFacoltativiAttivati);
            var codiceModelloRiepilogo = this._interventiRepository.GetidDocumentoRiepilogoDaIdIntervento(domanda.ReadInterface.AltriDati.Intervento.Codice);

            var allegatoRiepilogo = allegati.Where(x => x.Codice == codiceModelloRiepilogo).FirstOrDefault();

            return this._riepilogoDomanda.GeneraRiepilogoDomanda(domanda, allegatoRiepilogo.CodiceOggettoModello.Value, false);
        }

        private IEnumerable<EndoprocedimentoDto> EstraiListaEndo(IEnumerable<FamigliaEndoprocedimentoDto> famiglie)
        {
            return famiglie.SelectMany(x => x.TipiEndoprocedimenti).SelectMany(x => x.Endoprocedimenti);
        }
    }
}
