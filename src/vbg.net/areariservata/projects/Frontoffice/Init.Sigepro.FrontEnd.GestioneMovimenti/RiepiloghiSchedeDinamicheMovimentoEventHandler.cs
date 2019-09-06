// -----------------------------------------------------------------------
// <copyright file="RiepiloghiSchedeDinamicheMovimentoEventHandler.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.GestioneMovimenti
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.Infrastructure.Dispatching;
	using Init.Sigepro.FrontEnd.GestioneMovimenti.Events;
	using Init.Sigepro.FrontEnd.GestioneMovimenti.Commands;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDaEffettuare;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDiOrigine;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class RiepiloghiSchedeDinamicheMovimentoEventHandler : IEventHandler, Handles<CompilazioneSchedaDinamicaCompletata>
	{
		ICommandSender _eventBus;
        IMovimentiDaEffettuareRepository _movimentiReadRepository;
        IMovimentiDiOrigineRepository _movimentiDiOrigineRepository;

        public RiepiloghiSchedeDinamicheMovimentoEventHandler(ICommandSender eventBus, IMovimentiDaEffettuareRepository movimentiReadRepository, IMovimentiDiOrigineRepository movimentiDiOrigineRepository)
		{
			this._eventBus = eventBus;
			this._movimentiReadRepository = movimentiReadRepository;
            this._movimentiDiOrigineRepository = movimentiDiOrigineRepository;
		}

		
		#region Handles<CompilazioneSchedaDinamicaCompletata> Members

		public void Handle(CompilazioneSchedaDinamicaCompletata message)
		{
			var idMovimento = message.IdMovimento;
			var movimento = this._movimentiReadRepository.GetById(idMovimento);
            var movimentoDiOrigine = this._movimentiDiOrigineRepository.GetById(movimento);
			var idComune = movimento.AliasComune;

            var schedaCompilata = movimentoDiOrigine.SchedeDinamiche.Where(x => x.IdScheda == message.IdScheda).First();

            // Un campo potrebbe essere stato condiviso tra più schede 
            // quindi rimuovo i riepiloghi di ciascuna scheda che contiene almeno uno dei campi modificati
			schedaCompilata.IdCampiDinamiciContenuti
						   .SelectMany(idCampo =>
                                movimentoDiOrigine.SchedeDinamiche
										            .Where(scheda => scheda.IdCampiDinamiciContenuti.Contains(idCampo))
							)
							.Select(x => x.IdScheda)
							.GroupBy(x => x)
							.ToList()
							.ForEach(idScheda =>
							{
								var cmd = new RimuoviRiepilogoSchedaDinamicaDalMovimento(idComune, idMovimento, idScheda.Key);
								this._eventBus.Send(cmd);
							});
		}

		#endregion
	}
}
