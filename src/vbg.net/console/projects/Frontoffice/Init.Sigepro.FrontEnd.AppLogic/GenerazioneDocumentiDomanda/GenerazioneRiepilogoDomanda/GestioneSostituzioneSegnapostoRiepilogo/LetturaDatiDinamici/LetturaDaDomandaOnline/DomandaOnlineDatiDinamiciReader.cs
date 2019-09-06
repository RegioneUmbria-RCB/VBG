using Init.Sigepro.FrontEnd.AppLogic.Adapters;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.DataAccess;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.StrutturaModelli;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.SIGePro.DatiDinamici.Interfaces;
using Init.SIGePro.DatiDinamici.Interfaces.Istanze;
using Init.SIGePro.DatiDinamici.VisibilitaCampi;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.LetturaDatiDinamici.LetturaDaDomandaOnline
{
    public class DomandaOnlineDatiDinamiciReader : IDatiDinamiciRiepilogoReader
    {
        DomandaOnline _domanda;
        IDatiDinamiciRepository _datiDinamiciRepository;

        public DomandaOnlineDatiDinamiciReader(DomandaOnline domanda, IDatiDinamiciRepository datiDinamiciRepository)
        {
            this._domanda = domanda;
            this._datiDinamiciRepository = datiDinamiciRepository;
        }

        public IDyn2DataAccessProvider CreateDataAccessProvider(int idScheda, ITokenApplicazioneService tokenApplicazioneService)
        {
            return new Dyn2DataAccessProvider(this._domanda, idScheda, tokenApplicazioneService);
        }

        public IDyn2DataAccessProvider CreateDataAccessProviderStampaMolteplicita(int idScheda, int indiceMolteplicita, ITokenApplicazioneService tokenApplicazioneService)
        {
            return new Dyn2DataAccessProviderStampaMolteplicita(this._domanda, idScheda, indiceMolteplicita, tokenApplicazioneService);
        }

        public IEnumerable<IdValoreCampo> GetCampiNonVisibili(int idModello)
        {
            return this._domanda.ReadInterface.DatiDinamici.GetCampiNonVisibili(idModello);
        }

        public IValoreDatoDinamicoRiepilogo GetCampoDinamico(int idCampoDinamico, int indiceMolteplicita = 0)
        {
            return this._domanda.ReadInterface
                                .DatiDinamici
                                .DatiDinamici
                                .Where(x => x.IdCampo == idCampoDinamico && x.IndiceMolteplicita == indiceMolteplicita)
                                .FirstOrDefault();
        }

        public int GetCodiceIstanza()
        {
            return -1;
        }

        public string GetIdComune()
        {
            return this._domanda.DataKey.IdComune;
        }

        public IEnumerable<int> GetIndiciSchede(int idModello, IStrutturaModelloReader strutturaReader)
        {
            return this._domanda.ReadInterface.DatiDinamici.GetIndiciSchede(strutturaReader.Read(idModello));
        }

        IEnumerable<IModelloDinamicoRiepilogo> __tmpListaModelli = Enumerable.Empty<IModelloDinamicoRiepilogo>();

        private IEnumerable<IModelloDinamicoRiepilogo> ListaTotaleModelli()
        {
            if (this.__tmpListaModelli.Count() == 0)
            {
                this.__tmpListaModelli = this._domanda
                        .ReadInterface
                        .DatiDinamici
                        .Modelli
                        //.Where(m => m.Compilato)
                        .Select(x => x.EstraiOrdine(this._domanda.ReadInterface.DatiDinamici))
                        .OrderBy(m => m.Ordine)
                        .Select(m => m.Modello);
            }

            return this.__tmpListaModelli;
        }

        public IEnumerable<IModelloDinamicoRiepilogo> GetListaModelli()
        {
            var listaEndo = new List<int>();
            
            listaEndo.Add(this._domanda.ReadInterface.Endoprocedimenti.Principale.Codice);
            listaEndo.AddRange(this._domanda.ReadInterface.Endoprocedimenti.Secondari.Select(x => x.Codice));

            return this.GetListaModelliIntervento().
                    Union(listaEndo.SelectMany(x => GetListaModelliEndo(x)));
        }

        public IEnumerable<IModelloDinamicoRiepilogo> GetListaModelliEndo(int idEndo)
        {
            var modelli = this._datiDinamiciRepository.GetSchedeDaInterventoEEndo(-1, new[] { idEndo }, Enumerable.Empty<string>(), UsaTipiLocalizzazioniPerSelezionareSchedeDinamiche.No);

            var idSchede = modelli.SchedeEndoprocedimenti.Select(x => x.Id);

            return ListaTotaleModelli().Where(x => idSchede.Contains(x.IdModello)).Select(x => x);
        }

        public IEnumerable<IModelloDinamicoRiepilogo> GetListaModelliIntervento()
        {
            var idIntervento = this._domanda.ReadInterface.AltriDati.Intervento.Codice;

            var modelli = this._datiDinamiciRepository.GetSchedeDaInterventoEEndo(idIntervento, Enumerable.Empty<int>(), Enumerable.Empty<string>(), UsaTipiLocalizzazioniPerSelezionareSchedeDinamiche.No);

            var idSchede = modelli.SchedeIntervento.Select(x => x.Id);

            return ListaTotaleModelli().Where(x => idSchede.Contains(x.IdModello)).Select(x => x);
        }
    }
}
