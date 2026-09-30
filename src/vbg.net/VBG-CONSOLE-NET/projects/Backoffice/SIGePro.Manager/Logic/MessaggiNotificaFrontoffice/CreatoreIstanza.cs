// -----------------------------------------------------------------------
// <copyright file="CreatoreIstanza.cs" company="">
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
	using Init.SIGePro.Manager.Logic.GestioneAnagrafiche;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class CreatoreIstanza : IDestinatarioMessaggioNotifica
	{
		string _email = String.Empty;
		string _codiceFiscale = String.Empty;

		public string Email
		{
			get { return this._email; }
		}

		public string CodiceFiscale
		{
			get { return this._codiceFiscale; }
		}

		public static IDestinatarioMessaggioNotifica DaIstanza(Istanze istanza, IAnagrafeRepository anagrafeRepository)
		{
			var codiceCreatore = GetCodiceCreatoreDaIstanza(istanza);

			return new CreatoreIstanza(anagrafeRepository.GetById(codiceCreatore));
		}

		private static int GetCodiceCreatoreDaIstanza(Istanze istanza)
		{
			if (!String.IsNullOrEmpty(istanza.CODICEPROFESSIONISTA))
				return Convert.ToInt32(istanza.CODICEPROFESSIONISTA);

			if (!String.IsNullOrEmpty(istanza.CODICERICHIEDENTE))
				return Convert.ToInt32(istanza.CODICERICHIEDENTE);

			throw new InvalidOperationException("Impossibile identificare il creatore dell'istanza n." + istanza.NUMEROISTANZA);
		}

		private CreatoreIstanza(Anagrafe anagrafica)
		{
			this._email = anagrafica.EMAIL;
			this._codiceFiscale = anagrafica.CODICEFISCALE;
		}

	}
}
