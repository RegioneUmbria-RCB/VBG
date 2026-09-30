// -----------------------------------------------------------------------
// <copyright file="ListaDestinatariMessaggio.cs" company="">
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
	using Init.SIGePro.Manager.Logic.GestioneOperatori;
	using Init.SIGePro.Manager.Logic.GestioneConfigurazione;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class DestinatariMessaggioFactory
	{
		Istanze _istanza;
		IAnagrafeRepository _anagrafeRepository;
		IConfigurazioneRepository _configRepository;
		IOperatoriRepository _operatoriRepository;

		public DestinatariMessaggioFactory(Istanze istanza, IAnagrafeRepository anagrafeRepository, IConfigurazioneRepository configRepository, IOperatoriRepository operatoriRepository)
		{
			this._istanza = istanza;
			this._anagrafeRepository = anagrafeRepository;
			this._configRepository = configRepository;
			this._operatoriRepository = operatoriRepository;
		}


		public IEnumerable<IDestinatarioMessaggioNotifica> GetDestinatari(FlagsDestinatariMessaggio flags)
		{
			if (flags.InviaACreatoreIstanza)
				yield return CreatoreIstanza.DaIstanza(this._istanza, this._anagrafeRepository);

			if (flags.InviaAResponsabileSportello)
				yield return ResponsabileSportello.FromIstanza(this._istanza, this._configRepository, this._operatoriRepository);

			if (flags.InviaAResponsabileProcedimento)
				yield return ResponsabileProcedimento.FromIstanza(this._istanza, this._operatoriRepository);

			if (flags.InviaAResponsabileIstruttoria)
				yield return ResponsabileIstruttoria.FromIstanza(this._istanza, this._operatoriRepository);

			if (flags.InviaAOperatore)
				yield return Operatore.FromIstanza(this._istanza, this._operatoriRepository);
		}
	}
}
