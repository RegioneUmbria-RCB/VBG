using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.DataAccess.Visura;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using VBG.DatiDinamici;
using VBG.DatiDinamici.Interfaces;
using VBG.DatiDinamici.Standard.Scripts;
using VBG.DatiDinamici.Standard.Utils.CreazioneModelli;

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.Visura
{
    public class VisuraDatiDinamiciService : IVisuraDatiDinamiciService
    {
        private readonly IAliasResolver _aliasResolver;
        private readonly IDatiDinamiciRepository _repository;
        private readonly ITokenApplicazioneService _tokenService;
        private readonly IModelliDinamiciFactory _modelliDinamiciFactory;
        private readonly IStrutturaModelloDinamicoRepository _strutturaModelloDinamicoRepository;

        public VisuraDatiDinamiciService(IAliasResolver aliasResolver, IDatiDinamiciRepository repository, ITokenApplicazioneService tokenService, IModelliDinamiciFactory modelliDinamiciFactory, IStrutturaModelloDinamicoRepository strutturaModelloDinamicoRepository)
        {
            this._aliasResolver = aliasResolver;
            this._repository = repository;
            this._tokenService = tokenService;
            this._modelliDinamiciFactory = modelliDinamiciFactory;
            this._strutturaModelloDinamicoRepository = strutturaModelloDinamicoRepository;
        }

        private class DummyClassLoader : IClasseContestoLoader
        {
            private readonly Istanze _istanza;

            public DummyClassLoader(Istanze istanza)
            {
                this._istanza = istanza;
            }

            public IClasseContestoModelloDinamico LoadClass()
            {
                return JsonConvert.DeserializeObject<Istanze>(JsonConvert.SerializeObject(this._istanza));
            }
        }

        public ModelloDinamicoIstanza GetModello(Istanze istanza, int idModello)
        {
            var cache = this._strutturaModelloDinamicoRepository.GetStrutturaModelloDinamico(idModello);
            var classeContestoLoader = new DummyClassLoader(istanza);
            var repository = new VisuraDatiRepository(istanza);

            var builder = new ModelloDinamicoLoaderBuilder();

            var loader = builder.UsaStruttura(cache)
                           .UsaLoaderClasseContesto(classeContestoLoader)
                           // .UsaQueryLocalizzazioni(classeContestoLoader) // Usa la classe di default che non restituisce localizzazioni
                           .UsaRepository(repository)
                           .UsaToken(this._tokenService.GetToken())
                           .Build(this._aliasResolver.AliasComune, ContestoScriptEnum.Frontoffice);

            return this._modelliDinamiciFactory.CreaModelloIstanza(loader, idModello, 0, false);

            //var cache = this._repository.GetCacheModelloDinamico(idModello);
            //var factory = new VisuraFoDataAccessFactory(cache, istanza, this._tokenService);
            //var loader = new ModelloDinamicoLoader(factory, this._aliasResolver.AliasComune, ContestoScriptEnum.Frontoffice);
            //return this._modelliDinamiciFactory.CreaModelloIstanza(loader, idModello, 0, false);
        }


        public IEnumerable<VisuraTitoloModelloDinamicoIstanza> GetTitoliModelli(Istanze istanza)
        {
            var intervento = Convert.ToInt32(istanza.CODICEINTERVENTOPROC);
            var endo = istanza.EndoProcedimenti.Select(x => Convert.ToInt32(x.CODICEINVENTARIO));

            var schede = this._repository.GetSchedeDaInterventoEEndo(intervento, endo, Enumerable.Empty<string>(), UsaTipiLocalizzazioniPerSelezionareSchedeDinamiche.No);

            var schedeIntervento = schede.SchedeIntervento.Select(x => new VisuraTitoloModelloDinamicoIstanza
            {
                Id = x.Id,
                Descrizione = x.Descrizione
            });

            var schedeEndo = schede.SchedeEndoprocedimenti.Select(x => new VisuraTitoloModelloDinamicoIstanza
            {
                Id = x.Id,
                Descrizione = x.Descrizione
            });

            return schedeIntervento.Union(schedeEndo);
        }

    }
}
