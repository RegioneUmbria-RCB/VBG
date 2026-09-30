// -----------------------------------------------------------------------
// <copyright file="ConfigurazioneRepositoryStub.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace SIGePro.Manager.Tests.Logic.MessaggiNotificaFrontoffice.Stubs
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.SIGePro.Manager.Logic.GestioneConfigurazione;
using Init.SIGePro.Data;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class ConfigurazioneRepositoryStub : IConfigurazioneRepository
	{
		Configurazione _valoreDaRestituire;

		public string ChiamatoConId { get; private set; }

		public ConfigurazioneRepositoryStub(Configurazione valoreDaRestituire)
		{
			this._valoreDaRestituire = valoreDaRestituire;
		}

		public Configurazione GetbySoftware(string software)
		{
			this.ChiamatoConId = software;

			_valoreDaRestituire.SOFTWARE = software;

			return _valoreDaRestituire;
		}
	}
}
