using System.Collections.Generic;
using System.Linq;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiEndoprocedimenti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDatiDinamici.Sincronizzazione;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDocumenti.LogicaSincronizzazione;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneEndoprocedimenti.Sincronizzazione;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.GestioneInterventi;
namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda
{
    public class FacSimileDomanda
    {
        IResolveDescrizioneIntervento _resolveDescrizioneIntervento;
        EndoprocedimentiService _endoprocedimentiService;
        IDatiDinamiciRepository _datiDinamiciRepository;
        IAllegatiEndoprocedimentiService _allegatiEndoService;
        ILogicaSincronizzazioneAllegatiIntervento _logicaSincronizzazioneAllegatiIntervento;


        public FacSimileDomanda(IResolveDescrizioneIntervento resolveDescrizioneIntervento, EndoprocedimentiService endoprocedimentiService, IDatiDinamiciRepository datiDinamiciRepository, IAllegatiEndoprocedimentiService allegatiEndoService, ILogicaSincronizzazioneAllegatiIntervento logicaSincronizzazioneAllegatiIntervento)
        {
            this._resolveDescrizioneIntervento = resolveDescrizioneIntervento;
            this._endoprocedimentiService = endoprocedimentiService;
            this._datiDinamiciRepository = datiDinamiciRepository;
            this._allegatiEndoService = allegatiEndoService;
            this._logicaSincronizzazioneAllegatiIntervento = logicaSincronizzazioneAllegatiIntervento;
        }

        public DomandaOnline Genera(string aliasComune, string software, int idIntervento, IEnumerable<int> endoFacoltativiAttivati)
        {
            var domanda = DomandaOnline.FacSimile(aliasComune, software);

            // CodiceComune
            domanda.WriteInterface.AltriDati.ImpostaCodiceComune(aliasComune);

            // Intervento
            domanda.WriteInterface.AltriDati.ImpostaIntervento(idIntervento, (int?)null, new NullWorkflowService(), this._resolveDescrizioneIntervento);

            // Endo
            var endoprocedimenti = this._endoprocedimentiService.LeggiEndoprocedimentiDaCodiceIntervento(aliasComune, idIntervento);

            var listaIdEndoAttivati = EstraiListaEndo(endoprocedimenti.FamiglieEndoprocedimentiPrincipali).Select(x => x.Codice)
                                          .Union(EstraiListaEndo(endoprocedimenti.FamiglieEndoprocedimentiAttivati).Select(x => x.Codice))
                                          .Union(EstraiListaEndo(endoprocedimenti.FamiglieEndoprocedimentiAttivabili)
                                                                                .Where(x => endoFacoltativiAttivati.Contains(x.Codice))
                                                                                .Select(x => x.Codice))
                                          .Union(EstraiListaEndo(endoprocedimenti.FamiglieEndoprocedimentiFacoltativi)
                                                                                .Where(x => endoFacoltativiAttivati.Contains(x.Codice))
                                                                                .Select(x => x.Codice));

            domanda.WriteInterface.Endoprocedimenti.AggiungiESincronizza(listaIdEndoAttivati, new LogicaSincronizzazioneEndo(domanda, this._endoprocedimentiService));

            // modelli dinamici
            var schedeDinamicheRichieste = _datiDinamiciRepository.GetSchedeDaInterventoEEndo(idIntervento, listaIdEndoAttivati, Enumerable.Empty<string>(), UsaTipiLocalizzazioniPerSelezionareSchedeDinamiche.No);

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
            new LogicaSincronizzazioneAllegatiEndo(domanda, _allegatiEndoService).Sincronizza();

            foreach (var allegato in domanda.ReadInterface.Documenti.Endo.Documenti)
                domanda.WriteInterface.Documenti.AllegaFileADocumento(allegato.Id, -1, allegato.Richiesto ? "Allegato Obbligatorio" : "Allegato Non Obbligatorio", false);


            domanda.ReadInterface.Invalidate();

            return domanda;
        }


        private IEnumerable<EndoprocedimentoDto> EstraiListaEndo(IEnumerable<FamigliaEndoprocedimentoDto> famiglie)
        {
            return famiglie.SelectMany(x => x.TipiEndoprocedimenti).SelectMany(x => x.Endoprocedimenti);
        }
    }
}
