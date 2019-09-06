// -----------------------------------------------------------------------
// <copyright file="CompilazioneSchedeDinamicheViewModel.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.ViewModels
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
	using Init.Sigepro.FrontEnd.AppLogic.Common;
	using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici;
	using Init.Sigepro.FrontEnd.GestioneMovimenti.DatiDinamici;
	using Init.Sigepro.FrontEnd.GestioneMovimenti.Persistence;
	using Init.Sigepro.FrontEnd.Infrastructure.Dispatching;
	using Init.SIGePro.DatiDinamici;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDaEffettuare;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDiOrigine;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneWorkflowMovimento;

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

        IMovimentiDaEffettuareRepository _movimentiDaEffettuareRepository;
        IMovimentiDiOrigineRepository _movimentiDiOrigineRepository;
		IDatiDinamiciRepository _datiDinamiciRepository;
		IAliasResolver _aliasResolver;
		ITokenApplicazioneService _tokenService;
		ICommandSender _bus;
		MovimentiIstanzeManager _istanzeManager;

        MovimentoDaEffettuare _movimentoDaEffettuare;

        public CompilazioneSchedeDinamicheViewModel(ICommandSender bus, IMovimentiDaEffettuareRepository readRepository, IDatiDinamiciRepository datiDinamiciRepository, IAliasResolver aliasResolver, ITokenApplicazioneService tokenService, MovimentiIstanzeManager istanzeManager, IMovimentiDiOrigineRepository movimentiDiOrigineRepository)
		{
			this._movimentiDaEffettuareRepository = readRepository;
            this._movimentiDiOrigineRepository = movimentiDiOrigineRepository;
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
				new SchedaDinamica{
					IdScheda = x.IdScheda,
					Descrizione = x.NomeScheda,
                    Compilata = this._movimentoDaEffettuare.ListaIdSchedeCompilate.Contains(x.IdScheda)				
				});
		}

        public string GetTitoloMovimentoDaEffettuare()
		{
			return this._movimentoDaEffettuare.NomeAttivita;
		}

		public ModelloDinamicoIstanza CaricaSchedadinamica(int idSchedaDinamica)
		{
            var movimentoDiOrigine = this._movimentiDiOrigineRepository.GetById(this._movimentoDaEffettuare);

            var codiceIstanza = movimentoDiOrigine.DatiIstanza.CodiceIstanza;
            var cacheModello = this._datiDinamiciRepository.GetCacheModelloDinamico(idSchedaDinamica);

            var dap = new Dyn2DataAccessProvider(cacheModello, this._aliasResolver.AliasComune, this._tokenService, movimentoDiOrigine, this._movimentoDaEffettuare, this._bus, this._istanzeManager);
			var loader = new ModelloDinamicoLoader(dap, this._aliasResolver.AliasComune, ModelloDinamicoLoader.TipoModelloDinamicoEnum.Frontoffice);
            var modello = new ModelloDinamicoIstanza(loader, idSchedaDinamica, codiceIstanza, 0, false);

			modello.ModelloFrontoffice = true;

			return modello;
		}

		public ModelloDinamicoBase RicaricaModelloDinamico(object sender, EventArgs e)
		{
			throw new NotImplementedException();
		}

		public void SalvaSchedaDinamica(int idMovimento, ModelloDinamicoBase modelloDinamicoBase)
		{

            var movimentoDiOrigine = this._movimentiDiOrigineRepository.GetById(this._movimentoDaEffettuare);

			if (modelloDinamicoBase == null)
				return;

			modelloDinamicoBase.ValidaModello();

            var cacheModello = this._datiDinamiciRepository.GetCacheModelloDinamico(modelloDinamicoBase.IdModello);

            var dap = new Dyn2DataAccessProvider(cacheModello, this._aliasResolver.AliasComune, this._tokenService, movimentoDiOrigine, this._movimentoDaEffettuare, this._bus, this._istanzeManager);
			var loader = new ModelloDinamicoLoader(dap, this._aliasResolver.AliasComune, ModelloDinamicoLoader.TipoModelloDinamicoEnum.Frontoffice);

			modelloDinamicoBase.Loader = loader;
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
            var schede = GetListaSchedeDinamiche();

            return schede.Count() > 0;
        }

        public bool CanExitStep()
        {
            var schede = GetListaSchedeDinamiche();

            return schede.Count(x => !x.Compilata) == 0;
        }
    }
}
