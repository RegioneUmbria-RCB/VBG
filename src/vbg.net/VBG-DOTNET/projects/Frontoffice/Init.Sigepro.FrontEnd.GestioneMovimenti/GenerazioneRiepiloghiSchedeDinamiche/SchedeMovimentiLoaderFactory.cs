using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using Init.Sigepro.FrontEnd.GestioneMovimenti.DatiDinamici;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDaEffettuare;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDiOrigine;
using Init.Sigepro.FrontEnd.Infrastructure.Dispatching;
using VBG.DatiDinamici;
using VBG.DatiDinamici.GestioneLocalizzazioni;
using VBG.DatiDinamici.Interfaces;
using VBG.DatiDinamici.Standard.Scripts;
using VBG.DatiDinamici.Standard.Utils.CreazioneModelli;

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.GenerazioneRiepiloghiSchedeDinamiche
{
    public class SchedeMovimentiLoaderFactory
    {
        public class ClassLoader : IClasseContestoLoader
        {
            private readonly Istanze _istanza;

            public ClassLoader(Istanze istanza)
            {
                this._istanza = istanza;
            }

            public IClasseContestoModelloDinamico LoadClass()
            {
                return this._istanza;
            }
        }

        private sealed class QueryLocalizzazioniFactory : IQueryLocalizzazioniFactory
        {
            private readonly Istanze _istanza;

            public QueryLocalizzazioniFactory(Istanze istanza)
            {
                this._istanza = istanza;
            }

            public IQueryLocalizzazioni GetQueryLocalizzazioni() => new QueryLocalizzazioni(this._istanza);
        }

        private readonly IAliasResolver _aliasResolver;
        private readonly ICommandSender _commandSender;
        private readonly IDatiDinamiciRepository _datiDinamiciRepository;
        private readonly ITokenApplicazioneService _tokenService;
        private readonly MovimentiIstanzeManager _istanzeManager;
        private readonly IMovimentiDiOrigineRepository _movimentiDiOrigineRepository;
        private readonly IStrutturaModelloDinamicoRepository _strutturaModelloDinamicoRepository;

        public SchedeMovimentiLoaderFactory(IAliasResolver aliasResolver, ICommandSender commandSender, IDatiDinamiciRepository datiDinamiciRepository, ITokenApplicazioneService tokenService, MovimentiIstanzeManager istanzeManager, IMovimentiDiOrigineRepository movimentiDiOrigineRepository, IStrutturaModelloDinamicoRepository strutturaModelloDinamicoRepository)
        {
            this._aliasResolver = aliasResolver;
            this._commandSender = commandSender;
            this._datiDinamiciRepository = datiDinamiciRepository;
            this._tokenService = tokenService;
            this._istanzeManager = istanzeManager;
            this._movimentiDiOrigineRepository = movimentiDiOrigineRepository;
            this._strutturaModelloDinamicoRepository = strutturaModelloDinamicoRepository;
        }

        public ModelloDinamicoLoader CreateLoader(int idModello, MovimentoDaEffettuare movimentoDaEffettuare)
        {
            var istanza = (Istanze)this._istanzeManager.LeggiIstanza(movimentoDaEffettuare.CodiceIstanza);
            var struttura = this._strutturaModelloDinamicoRepository.GetStrutturaModelloDinamico(idModello);
            var classLoader = new ClassLoader(istanza);
            var queryLocalizzazioni = new QueryLocalizzazioniFactory(istanza);
            var movimentoDiOrigine = this._movimentiDiOrigineRepository.GetById(movimentoDaEffettuare);
            var repository = new IstanzeDyn2DatiRepository(movimentoDaEffettuare, movimentoDiOrigine.SchedeDinamiche, this._commandSender);

            var loader = new ModelloDinamicoLoaderBuilder();

            return loader.UsaStruttura(struttura)
                          .UsaLoaderClasseContesto(classLoader)
                          .UsaQueryLocalizzazioni(queryLocalizzazioni)
                          .UsaRepository(repository)
                          .UsaToken(this._tokenService.GetToken())
                          .Build(this._aliasResolver.AliasComune, ContestoScriptEnum.Frontoffice);
        }
    }
}
