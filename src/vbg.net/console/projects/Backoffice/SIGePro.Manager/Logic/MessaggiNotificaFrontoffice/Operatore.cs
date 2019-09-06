// -----------------------------------------------------------------------
// <copyright file="Operatore.cs" company="">
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

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class Operatore : OperatoreDestinatario
	{
		public static IDestinatarioMessaggioNotifica FromIstanza(Istanze istanza, IOperatoriRepository repository)
		{
			var codiceResponsabile = Convert.ToInt32(istanza.CODICERESPONSABILE);	// Esiste sempre un operatore
			var operatore = repository.GetById(codiceResponsabile);

			return new Operatore(operatore);
		}

		protected Operatore(Responsabili operatore):base(operatore)
		{
		}
	}
}
