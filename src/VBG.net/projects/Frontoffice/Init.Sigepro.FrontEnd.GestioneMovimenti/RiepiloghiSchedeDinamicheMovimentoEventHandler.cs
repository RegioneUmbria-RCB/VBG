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
	using Init.Sigepro.FrontEnd.GestioneMovimenti.ReadInterface.Persistence;
	using Init.Sigepro.FrontEnd.GestioneMovimenti.Commands;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class RiepiloghiSchedeDinamicheMovimentoEventHandler : IEventHandler, Handles<CompilazioneSchedaDinamicaCompletata>
	{
		ICommandSender _eventBus;
		IMovimentiReadRepository _movimentiReadRepository;

		public RiepiloghiSchedeDinamicheMovimentoEventHandler(ICommandSender eventBus, IMovimentiReadRepository movimentiReadRepository)
		{
			this._eventBus = eventBus;
			this._movimentiReadRepository = movimentiReadRepository;
		}

		
		#region Handles<CompilazioneSchedaDinamicaCompletata> Members

		public void Handle(CompilazioneSchedaDinamicaCompletata message)
		{
			var idMovimento = message.IdMovimento;
			var movimento = this._movimentiReadRepository.GetById(idMovimento);
			var idComune = movimento.MovimentoDiOrigine.DatiIstanza.IdComune;

			var schedaCompilata = movimento.MovimentoDiOrigine.SchedeDinamiche.Where(x => x.IdScheda == message.IdScheda).First();

			schedaCompilata.IdCampiDinamiciContenuti
						   .SelectMany(idCampo =>
								movimento.MovimentoDiOrigine
										 .SchedeDinamiche
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
