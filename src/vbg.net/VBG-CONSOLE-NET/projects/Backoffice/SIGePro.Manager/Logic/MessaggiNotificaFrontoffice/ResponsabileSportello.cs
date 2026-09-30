// -----------------------------------------------------------------------
// <copyright file="ResponsabileSportello.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.SIGePro.Manager.Logic.MessaggiNotificaFrontoffice
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.SIGePro.Data;
	using Init.SIGePro.Manager.Logic.GestioneOperatori;
	using Init.SIGePro.Manager.Logic.GestioneConfigurazione;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class ResponsabileSportello : OperatoreDestinatario
	{
		public static IDestinatarioMessaggioNotifica FromIstanza(Istanze istanza, IConfigurazioneRepository configRepository, IOperatoriRepository operatoriRepository)
		{
			var cfg = configRepository.GetbySoftware(istanza.SOFTWARE);

			if (String.IsNullOrEmpty(cfg.CODICERESPONSABILE))
			{
				return ResponsabileSportello.Fittizio();
			}

			var codiceOperatore = Convert.ToInt32(cfg.CODICERESPONSABILE);

			return new ResponsabileSportello(operatoriRepository.GetById(codiceOperatore));
		}

		private ResponsabileSportello(Responsabili operatore)
			: base(operatore)
		{

		}
	}
}
