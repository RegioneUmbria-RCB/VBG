// -----------------------------------------------------------------------
// <copyright file="RiepilogoMovimentoDiOrigineViewModel.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.ViewModels
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
using Init.Sigepro.FrontEnd.GestioneMovimenti.ReadInterface;
using Init.Sigepro.FrontEnd.GestioneMovimenti.ReadInterface.Persistence;
using Init.Sigepro.FrontEnd.GestioneMovimenti.ExternalServices;
	using CuttingEdge.Conditions;
	using Init.Sigepro.FrontEnd.Infrastructure.Dispatching;
	using Init.Sigepro.FrontEnd.GestioneMovimenti.Commands;
using Init.Sigepro.FrontEnd.GestioneMovimenti.Persistence;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class RiepilogoMovimentoDiOrigineViewModel
	{
		private class DatiMovimentoWrapper
		{
			IMovimentiReadRepository _readRepository;
			int _idMovimento;
			DatiMovimentoDaEffettuare _datiMovimento;

			public DatiMovimentoWrapper(IMovimentiReadRepository readRepository, int idMovimento)
			{
				this._readRepository = readRepository;
				this._idMovimento = idMovimento;
			}

			public DatiMovimentoDaEffettuare Get()
			{
				if (this._datiMovimento == null)
					_datiMovimento = this._readRepository.GetById(this._idMovimento);

				return _datiMovimento;
			}

			public void InvalidaCache()
			{
				this._datiMovimento = null;
			}
		}

		IScadenzeService _scadenzeService;
		ICommandSender _eventBus;
		DatiMovimentoWrapper _movimentoDaEffettuare;
		IIdMovimentoResolver _idMovimentoResolver;

		public RiepilogoMovimentoDiOrigineViewModel(ICommandSender eventBus,IMovimentiReadRepository readRepository, IScadenzeService scadenzeService, IIdMovimentoResolver idMovimentoResolver)
		{
			Condition.Requires(eventBus, "eventBus").IsNotNull();
			Condition.Requires(readRepository, "readRepository").IsNotNull();
			Condition.Requires(scadenzeService, "scadenzeService").IsNotNull();
			Condition.Requires(idMovimentoResolver, "idMovimentoResolver").IsNotNull();

			this._scadenzeService = scadenzeService;
			this._eventBus = eventBus;
			this._movimentoDaEffettuare = new DatiMovimentoWrapper(readRepository, idMovimentoResolver.IdMovimento);
			this._idMovimentoResolver = idMovimentoResolver;
		}

		public DatiMovimentoDaEffettuare GetMovimentoDaEffettuare()
		{
			var datiScadenza = this._scadenzeService.GetById(this._idMovimentoResolver.IdMovimento);

			var movimentoDaEffettuare = this._movimentoDaEffettuare.Get();

			if (movimentoDaEffettuare != null)
				return movimentoDaEffettuare;
			
			var cmd = new CreaMovimento(datiScadenza.CodEnte, this._idMovimentoResolver.IdMovimento, Convert.ToInt32(datiScadenza.CodMovimento));

			this._eventBus.Send(cmd);

			return this._movimentoDaEffettuare.Get();
		}

        public bool ContieneSchedeDinamichedaCompilare()
        {
            var movimentoDaEffettuare = this._movimentoDaEffettuare.Get();

            return movimentoDaEffettuare.MovimentoDiOrigine.SchedeDinamiche != null && movimentoDaEffettuare.MovimentoDiOrigine.SchedeDinamiche.Count > 0;
        }

		public void AggiornaDatiMovimentoDiOrigine()
		{
			/*
			var movimentoDaEffettuare = this._movimentoDaEffettuare.Get();

			var idComune = movimentoDaEffettuare.MovimentoDiOrigine.DatiIstanza.IdComune;
			var idMovimento = this._idMovimentoResolver.IdMovimento;

			this._movimentoDaEffettuare.InvalidaCache();

			var cmd = new AggiornaDatiMovimentoDiOrigine(idComune, idMovimento);

			this._eventBus.Send(cmd);
			*/
		}
	}
}
