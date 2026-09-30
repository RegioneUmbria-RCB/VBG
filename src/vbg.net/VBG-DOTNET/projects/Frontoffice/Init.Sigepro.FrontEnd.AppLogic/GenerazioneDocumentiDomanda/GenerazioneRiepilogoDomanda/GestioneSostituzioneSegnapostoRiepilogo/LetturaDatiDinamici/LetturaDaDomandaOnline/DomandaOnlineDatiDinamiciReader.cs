using Init.Sigepro.FrontEnd.AppLogic.Adapters;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.DataAccess;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using System;
using System.Collections.Generic;
using System.Linq;
using VBG.DatiDinamici;
using VBG.DatiDinamici.Interfaces;
using VBG.DatiDinamici.Standard.Scripts;
using VBG.DatiDinamici.Standard.Utils.CreazioneModelli;
using VBG.DatiDinamici.WebControls.MaschereCampiNonVisibili;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.LetturaDatiDinamici.LetturaDaDomandaOnline
{
    public class DomandaOnlineDatiDinamiciReaderFactory
    {
        private readonly IDatiDinamiciRepository _datiDinamiciRepository;
        private readonly IIstanzaSigeproAdapterService _istanzaSigeproAdapterService;
        private readonly IStrutturaModelloDinamicoRepository _strutturaModelloDinamicoRepository;

        public DomandaOnlineDatiDinamiciReaderFactory(IDatiDinamiciRepository datiDinamiciRepository, IIstanzaSigeproAdapterService istanzaSigeproAdapterService, IStrutturaModelloDinamicoRepository strutturaModelloDinamicoRepository)
        {
            this._datiDinamiciRepository = datiDinamiciRepository;
            this._istanzaSigeproAdapterService = istanzaSigeproAdapterService;
            this._strutturaModelloDinamicoRepository = strutturaModelloDinamicoRepository;
        }

        public DomandaOnlineDatiDinamiciReader Create(DomandaOnline domanda, IStrutturaModelloDinamicoRepository? strutturaModelloDinamicoRepository)
        {
            var strutturaRepository = strutturaModelloDinamicoRepository ?? this._strutturaModelloDinamicoRepository;

            return new DomandaOnlineDatiDinamiciReader(domanda, this._datiDinamiciRepository, strutturaRepository, this._istanzaSigeproAdapterService);
        }
    }


    public class DomandaOnlineDatiDinamiciReader : ISchedeDinamicheDomandaAlRiepilogoService
    {
        private readonly DomandaOnline _domanda;
        private readonly Istanze _istanzaSigepro;
        private readonly IDatiDinamiciRepository _datiDinamiciRepository;
        private readonly IStrutturaModelloDinamicoRepository _strutturaModelloDinamicoRepository;

        public bool PuoCaricareSchedeNonPresenti => false;
        public bool SupportaCachingCampiNonVisibili => true;

        public DomandaOnlineDatiDinamiciReader(DomandaOnline domanda, IDatiDinamiciRepository datiDinamiciRepository, IStrutturaModelloDinamicoRepository strutturaModelloDinamicoRepository, IIstanzaSigeproAdapterService istanzaSigeproAdapterService)
        {
            this._domanda = domanda;
            this._datiDinamiciRepository = datiDinamiciRepository;
            this._strutturaModelloDinamicoRepository = strutturaModelloDinamicoRepository;
            this._istanzaSigepro = istanzaSigeproAdapterService.ToIstanzaBackoffice(domanda.ReadInterface);
        }

        public CampiNonVisibili GetCampiNonVisibili(int idModello)
        {
            return new CampiNonVisibili(this._domanda.ReadInterface.DatiDinamici.GetCampiNonVisibili(idModello));
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

        public IEnumerable<int> GetIndiciSchede(int idModello)
        {
            var struttura = this._strutturaModelloDinamicoRepository.GetStrutturaModelloDinamico(idModello);
            return this._domanda.ReadInterface.DatiDinamici.GetIndiciSchede(struttura);
        }

        public IEnumerable<IModelloDinamicoRiepilogo> GetListaModelli()
        {
            if (!this._domanda.ReadInterface.DatiDinamici.Modelli.Any())
            {
                return Enumerable.Empty<IModelloDinamicoRiepilogo>();
            }

            return this._domanda
                        .ReadInterface
                        .DatiDinamici
                        .Modelli
                        //.Where(m => m.Compilato)
                        .Select(x => x.EstraiOrdine(this._domanda.ReadInterface.DatiDinamici))
                        .OrderBy(m => m.Ordine)
                        .Select(m => m.Modello).ToList();
        }

        public IEnumerable<IModelloDinamicoRiepilogo> GetListaModelliEndo(int idEndo)
        {
            var modelli = this._datiDinamiciRepository.GetSchedeDaInterventoEEndo(-1, new[] { idEndo }, Enumerable.Empty<string>(), UsaTipiLocalizzazioniPerSelezionareSchedeDinamiche.No);

            var idSchedeOrdinate = modelli.SchedeEndoprocedimenti.OrderBy(x => x.Ordine).Select(x => x.Id);

            return this.GetListaModelli().Where(x => idSchedeOrdinate.Contains(x.IdModello));

            /*
            foreach (var idScheda in idSchedeOrdinate)
            {
                yield return GetListaModelli().Where(x => x.IdModello == idScheda).First();
            }
            */
        }

        public IEnumerable<IModelloDinamicoRiepilogo> GetListaModelliIntervento()
        {
            var idIntervento = this._domanda.ReadInterface.AltriDati.Intervento.Codice;

            var modelli = this._datiDinamiciRepository.GetSchedeDaInterventoEEndo(idIntervento, Enumerable.Empty<int>(), Enumerable.Empty<string>(), UsaTipiLocalizzazioniPerSelezionareSchedeDinamiche.No);

            var idSchedeOrdinate = modelli.SchedeIntervento.OrderBy(x => x.Ordine).Select(x => x.Id);
            var result = new List<IModelloDinamicoRiepilogo>();

            foreach (var idScheda in idSchedeOrdinate)
            {
                var el = this.GetListaModelli().Where(x => x.IdModello == idScheda).FirstOrDefault();

                if (el == null)
                {
                    continue;
                }

                result.Add(el);
            }

            return result;
        }

        public ModelloDinamicoLoader CreateLoader(int idScheda, int indiceMolteplicita, ITokenApplicazioneService tokenApplicazioneService)
        {
            var cache = this._strutturaModelloDinamicoRepository.GetStrutturaModelloDinamico(idScheda);
            var classeContestoLoader = new DomandaOnLineClasseContestoLoader(this._istanzaSigepro);
            var repository = (IDyn2DatiRepository)new DatiRepository(this._domanda);

            if (indiceMolteplicita != -1)
            {
                repository = new StampaMolteplicitaRepository(repository, indiceMolteplicita, cache);
            }

            var builder = new ModelloDinamicoLoaderBuilder();

            var loader = builder.UsaStruttura(cache)
                           .UsaLoaderClasseContesto(classeContestoLoader)
                           .UsaQueryLocalizzazioni(classeContestoLoader)
                           .UsaRepository(repository)
                           .UsaToken(tokenApplicazioneService.GetToken())
                           .Build(this.GetIdComune(), ContestoScriptEnum.Frontoffice);

            return loader;
        }
    }
}
