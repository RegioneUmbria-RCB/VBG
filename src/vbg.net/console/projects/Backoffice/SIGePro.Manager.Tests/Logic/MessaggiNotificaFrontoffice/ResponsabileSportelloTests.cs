// -----------------------------------------------------------------------
// <copyright file="ResponsabileSportello.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace SIGePro.Manager.Tests.Logic.MessaggiNotificaFrontoffice
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Microsoft.VisualStudio.TestTools.UnitTesting;
	using Init.SIGePro.Data;
	using SIGePro.Manager.Tests.Logic.MessaggiNotificaFrontoffice.Stubs;
	using Init.SIGePro.Manager.Logic.MessaggiNotificaFrontoffice;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	[TestClass]
	public class ResponsabileSportelloTests
	{
		Responsabili _testOperatore;
		OperatoriRepositoryStub _operatoriRepository;

		Configurazione _testConfigurazione;
		ConfigurazioneRepositoryStub _configRepository;

		[TestInitialize]
		public void Initialize()
		{
			this._testOperatore = new Responsabili
			{
				CODICEFISCALE = "CF",
				EMAIL = "EMAIL"
			};

			this._operatoriRepository = new OperatoriRepositoryStub(this._testOperatore);


			this._testConfigurazione = new Configurazione();

			this._configRepository = new ConfigurazioneRepositoryStub(this._testConfigurazione);

		}

		[TestMethod]
		public void Il_codicefiscale_ed_email_vengono_letti_da_dati_dell_operatore_in_configurazione()
		{
			var istanza = new Istanze
			{
				SOFTWARE = "SS"
			};

			this._testConfigurazione.CODICERESPONSABILE = "1";

			var testClass = ResponsabileSportello.FromIstanza(istanza, this._configRepository, this._operatoriRepository);

			Assert.AreEqual<int>(1, this._operatoriRepository.ChiamatoConId);
			Assert.AreEqual<string>(this._testOperatore.CODICEFISCALE, testClass.CodiceFiscale);
			Assert.AreEqual<string>(this._testOperatore.EMAIL, testClass.Email);
		}

		[TestMethod]
		public void Il_codicefiscale_ed_email_sono_vuoti_se_in_istanza_non_c_e_codice_responsabile_procedimento()
		{
			var istanza = new Istanze
			{
				SOFTWARE = "SS"
			};

			this._testConfigurazione.CODICERESPONSABILE = null;

			var testClass = ResponsabileSportello.FromIstanza(istanza, this._configRepository, this._operatoriRepository);

			Assert.AreEqual<string>(String.Empty, testClass.CodiceFiscale);
			Assert.AreEqual<string>(String.Empty, testClass.Email);
		}
	}
}
