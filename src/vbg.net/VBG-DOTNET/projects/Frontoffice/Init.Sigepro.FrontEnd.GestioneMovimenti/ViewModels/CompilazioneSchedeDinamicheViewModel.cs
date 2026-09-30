// -----------------------------------------------------------------------
// <copyright file="CompilazioneSchedeDinamicheViewModel.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.ViewModels
{
    using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
    using Init.Sigepro.FrontEnd.AppLogic.Common;
    using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici;
    using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.DatiDinamici;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDaEffettuare;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDiOrigine;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneWorkflowMovimento;
    using Init.Sigepro.FrontEnd.Infrastructure.Dispatching;
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using VBG.DatiDinamici;
    using VBG.DatiDinamici.GestioneLocalizzazioni;
    using VBG.DatiDinamici.Interfaces;
    using VBG.DatiDinamici.Standard.Scripts;
    using VBG.DatiDinamici.Standard.Utils.CreazioneModelli;

    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    public class CompilazioneSchedeDinamicheViewModel : IStepViewModel
    {
        public class SchedaDinamica
        {
            public int IdScheda { get; set; }
            public string Descrizione { get; set; }
            public bool Compilata { get; set; }
        }

        private readonly IMovimentiDaEffettuareRepository _movimentiDaEffettuareRepository;
        private readonly IMovimentiDiOrigineRepository _movimentiDiOrigineRepository;
        private readonly IModelliDinamiciFactory _modelliDinamiciFactory;
        private readonly IStrutturaModelloDinamicoRepository _strutturaModelloDinamicoRepository;
        private readonly IDatiDinamiciRepository _datiDinamiciRepository;
        private readonly IAliasResolver _aliasResolver;
        private readonly ITokenApplicazioneService _tokenService;
        private readonly ICommandSender _bus;
        private readonly MovimentiIstanzeManager _istanzeManager;
        private MovimentoDaEffettuare _movimentoDaEffettuare;

        public CompilazioneSchedeDinamicheViewModel(ICommandSender bus, IMovimentiDaEffettuareRepository readRepository, IDatiDinamiciRepository datiDinamiciRepository,
            IAliasResolver aliasResolver, ITokenApplicazioneService tokenService, MovimentiIstanzeManager istanzeManager, IMovimentiDiOrigineRepository movimentiDiOrigineRepository,
            IModelliDinamiciFactory modelliDinamiciFactory, IStrutturaModelloDinamicoRepository strutturaModelloDinamicoRepository)
        {
            this._movimentiDaEffettuareRepository = readRepository;
            this._movimentiDiOrigineRepository = movimentiDiOrigineRepository;
            this._modelliDinamiciFactory = modelliDinamiciFactory;
            this._strutturaModelloDinamicoRepository = strutturaModelloDinamicoRepository;
            this._datiDinamiciRepository = datiDinamiciRepository;
            this._aliasResolver = aliasResolver;
            this._tokenService = tokenService;
            this._bus = bus;
            this._istanzeManager = istanzeManager;
        }

        public IEnumerable<SchedaDinamica> GetListaSchedeDinamiche()
        {
            var movimentoDiOrigine = this._movimentiDiOrigineRepository.GetById(this._movimentoDaEffettuare);

            return movimentoDiOrigine.SchedeDinamiche.Select(x =>
                new SchedaDinamica
                {
                    IdScheda = x.IdScheda,
                    Descrizione = x.NomeScheda,
                    Compilata = this._movimentoDaEffettuare.ListaIdSchedeCompilate.Contains(x.IdScheda)
                });
        }

        public string GetTitoloMovimentoDaEffettuare()
        {
            return this._movimentoDaEffettuare.NomeAttivita;
        }

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

        private class QueryLocalizzazioniFactory : IQueryLocalizzazioniFactory
        {
            private readonly Istanze _istanza;

            public QueryLocalizzazioniFactory(Istanze istanza)
            {
                this._istanza = istanza;
            }

            public IQueryLocalizzazioni GetQueryLocalizzazioni() => new QueryLocalizzazioni(this._istanza);
        }

        private ModelloDinamicoLoader CreateLoader(int idModello, MovimentoDaEffettuare movimentoDaEffettuare)
        {
            var istanza = (Istanze)this._istanzeManager.LeggiIstanza(movimentoDaEffettuare.CodiceIstanza);
            var struttura = this._strutturaModelloDinamicoRepository.GetStrutturaModelloDinamico(idModello);
            var classLoader = new ClassLoader(istanza);
            var queryLocalizzazioni = new QueryLocalizzazioniFactory(istanza);
            var movimentoDiOrigine = this._movimentiDiOrigineRepository.GetById(movimentoDaEffettuare);
            var repository = new IstanzeDyn2DatiRepository(movimentoDaEffettuare, movimentoDiOrigine.SchedeDinamiche, this._bus);

            var loader = new ModelloDinamicoLoaderBuilder();

            return loader.UsaStruttura(struttura)
                          .UsaLoaderClasseContesto(classLoader)
                          .UsaQueryLocalizzazioni(queryLocalizzazioni)
                          .UsaRepository(repository)
                          .UsaToken(this._tokenService.GetToken())
                          .Build(this._aliasResolver.AliasComune, ContestoScriptEnum.Frontoffice);
        }

        public ModelloDinamicoIstanza CaricaSchedadinamica(int idSchedaDinamica)
        {
            //var movimentoDiOrigine = this._movimentiDiOrigineRepository.GetById(this._movimentoDaEffettuare);
            // var codiceIstanza = movimentoDiOrigine.DatiIstanza.CodiceIstanza;
            //var cacheModello = this._datiDinamiciRepository.GetCacheModelloDinamico(idSchedaDinamica);
            //
            //var dap = new MovimentiDyn2DataAccessFactory(cacheModello, movimentoDiOrigine, this._movimentoDaEffettuare, this._bus, this._tokenService, this._istanzeManager, this._mapper);
            //var loader = new ModelloDinamicoLoader(dap, this._aliasResolver.AliasComune, ContestoScriptEnum.Frontoffice);
            var loader = this.CreateLoader(idSchedaDinamica, this._movimentoDaEffettuare);
            var modello = this._modelliDinamiciFactory.CreaModelloIstanza(loader, idSchedaDinamica, 0, false);

            //modello.ModelloFrontoffice = true;

            return modello;
        }

        public void SalvaSchedaDinamica(int idMovimento, ModelloDinamicoBase modelloDinamicoBase)
        {



            if (modelloDinamicoBase == null)
                return;

            modelloDinamicoBase.ValidaModello();

            // var movimentoDiOrigine = this._movimentiDiOrigineRepository.GetById(this._movimentoDaEffettuare);
            // var cacheModello = this._datiDinamiciRepository.GetCacheModelloDinamico(modelloDinamicoBase.IdModello);
            // var dap = new MovimentiDyn2DataAccessFactory(cacheModello, movimentoDiOrigine, this._movimentoDaEffettuare, this._bus, this._tokenService, this._istanzeManager, this._mapper);
            // var loader = new ModelloDinamicoLoader(dap, this._aliasResolver.AliasComune, ContestoScriptEnum.Frontoffice);
            var loader = this.CreateLoader(modelloDinamicoBase.IdModello, this._movimentoDaEffettuare);

            modelloDinamicoBase.LegacySetLoader(loader);
            modelloDinamicoBase.Salva();

            if (modelloDinamicoBase.ErroriScript.Count() > 0)
                throw new Exception();
        }

        public void SetIdMovimento(int idMovimento)
        {
            this._movimentoDaEffettuare = this._movimentiDaEffettuareRepository.GetById(idMovimento);
        }

        public bool CanEnterStep()
        {
            var schede = this.GetListaSchedeDinamiche();

            return schede.Count() > 0;
        }

        public bool CanExitStep()
        {
            var schede = this.GetListaSchedeDinamiche();

            return schede.Count(x => !x.Compilata) == 0;
        }
    }
}
