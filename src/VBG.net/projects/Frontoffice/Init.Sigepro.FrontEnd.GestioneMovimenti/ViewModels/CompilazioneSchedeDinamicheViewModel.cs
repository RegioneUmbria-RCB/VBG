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
	using Init.Sigepro.FrontEnd.GestioneMovimenti.ReadInterface;
	using Init.Sigepro.FrontEnd.GestioneMovimenti.ReadInterface.Persistence;
	using Init.Sigepro.FrontEnd.Infrastructure.Dispatching;
	using Init.SIGePro.DatiDinamici;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class CompilazioneSchedeDinamicheViewModel
	{
		public class SchedaDinamica
		{
			public int IdScheda { get; set; }
			public string Descrizione { get; set; }
			public bool Compilata { get; set; }
		}

		IMovimentiReadRepository _readRepository;
		IIdMovimentoResolver _idMovimentoResolver;
		IDatiDinamiciRepository _datiDinamiciRepository;
		IAliasResolver _aliasResolver;
		ITokenApplicazioneService _tokenService;
		ICommandSender _bus;
		MovimentiIstanzeManager _istanzeManager;

		public CompilazioneSchedeDinamicheViewModel(ICommandSender bus, IIdMovimentoResolver idMovimentoResolver, IMovimentiReadRepository readRepository, IDatiDinamiciRepository datiDinamiciRepository, IAliasResolver aliasResolver, ITokenApplicazioneService tokenService, MovimentiIstanzeManager istanzeManager)
		{
			this._readRepository = readRepository;
			this._idMovimentoResolver = idMovimentoResolver;
			this._datiDinamiciRepository = datiDinamiciRepository;
			this._aliasResolver = aliasResolver;
			this._tokenService = tokenService;
			this._bus = bus;
			this._istanzeManager = istanzeManager;
		}

		public IEnumerable<SchedaDinamica> GetListaSchedeDinamiche()
		{
			var movimentoDaEffettuare = this._readRepository.GetById(this._idMovimentoResolver.IdMovimento);

			return movimentoDaEffettuare.MovimentoDiOrigine.SchedeDinamiche.Select( x => 
				new SchedaDinamica{
					IdScheda = x.IdScheda,
					Descrizione = x.NomeScheda,
					Compilata = movimentoDaEffettuare.ListaIdSchedeCompilate.Contains(x.IdScheda)				
				});
		}

		public string GetTitoloMovimentoDaEffettuare()
		{
			var movimentoDaEffettuare = this._readRepository.GetById(this._idMovimentoResolver.IdMovimento);

			return movimentoDaEffettuare.NomeAttivita;
		}

		public ModelloDinamicoIstanza CaricaSchedadinamica(int idSchedaDinamica)
		{
			var movimentoDaEffettuare = this._readRepository.GetById(this._idMovimentoResolver.IdMovimento);

			var codiceIstanza = movimentoDaEffettuare.MovimentoDiOrigine.DatiIstanza.CodiceIstanza;

			var idIstanza = movimentoDaEffettuare.MovimentoDiOrigine.DatiIstanza.CodiceIstanza;

			var dap = new Dyn2DataAccessProvider(this._datiDinamiciRepository, this._aliasResolver.AliasComune, idSchedaDinamica, this._tokenService, movimentoDaEffettuare, this._bus, this._istanzeManager, codiceIstanza);
			var loader = new ModelloDinamicoLoader(dap, this._aliasResolver.AliasComune, true);
			var modello = new ModelloDinamicoIstanza(loader, idSchedaDinamica, idIstanza, 0, false);

			modello.ModelloFrontoffice = true;

			return modello;
		}

		public ModelloDinamicoBase RicaricaModelloDinamico(object sender, EventArgs e)
		{
			throw new NotImplementedException();
		}

		public void SalvaSchedaDinamica(ModelloDinamicoBase modelloDinamicoBase)
		{
			var movimentoDaEffettuare = this._readRepository.GetById(this._idMovimentoResolver.IdMovimento);

			if (modelloDinamicoBase == null)
				return;

			modelloDinamicoBase.ValidaModello();

			var codiceIstanza = movimentoDaEffettuare.MovimentoDiOrigine.DatiIstanza.CodiceIstanza;

			var dap = new Dyn2DataAccessProvider(this._datiDinamiciRepository, this._aliasResolver.AliasComune, modelloDinamicoBase.IdModello, this._tokenService, movimentoDaEffettuare, this._bus, this._istanzeManager, codiceIstanza);
			var loader = new ModelloDinamicoLoader(dap, this._aliasResolver.AliasComune, true);

			modelloDinamicoBase.Loader = loader;
			modelloDinamicoBase.Salva();

			if (modelloDinamicoBase.ErroriScript.Count() > 0)
				throw new Exception();
		}

		public bool CanExitStep()
		{
			var schede = GetListaSchedeDinamiche();

			return schede.Count(x => !x.Compilata) == 0;
		}
	}
}
