// -----------------------------------------------------------------------
// <copyright file="ResponsabileIstruttoria.cs" company="">
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
	public class ResponsabileIstruttoria : OperatoreDestinatario
	{
		public static IDestinatarioMessaggioNotifica FromIstanza(Istanze istanza, IOperatoriRepository repository)
		{
			if(String.IsNullOrEmpty(istanza.CODICEISTRUTTORE))
			{
				return OperatoreDestinatario.Fittizio();
			}

			var codiceResponsabile = Convert.ToInt32(istanza.CODICEISTRUTTORE);	// Esiste sempre un operatore
			var operatore = repository.GetById(codiceResponsabile);

			return new ResponsabileIstruttoria(operatore);
		}

		private ResponsabileIstruttoria(Responsabili operatore)
			: base(operatore)
		{

		}
	}
}
