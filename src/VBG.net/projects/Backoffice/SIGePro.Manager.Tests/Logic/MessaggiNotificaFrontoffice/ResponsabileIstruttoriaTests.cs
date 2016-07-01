// -----------------------------------------------------------------------
// <copyright file="ResponsabileIstruttoriaTests.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace SIGePro.Manager.Tests.Logic.MessaggiNotificaFrontoffice
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.SIGePro.Data;
	using SIGePro.Manager.Tests.Logic.MessaggiNotificaFrontoffice.Stubs;
	using Microsoft.VisualStudio.TestTools.UnitTesting;
	using Init.SIGePro.Manager.Logic.MessaggiNotificaFrontoffice;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	[TestClass]
	public class ResponsabileIstruttoriaTests
	{
		Responsabili _testOperatore;
		OperatoriRepositoryStub _repository;

		[TestInitialize]
		public void Initialize()
		{
			this._testOperatore = new Responsabili
			{
				CODICEFISCALE = "CF",
				EMAIL = "EMAIL"
			};

			this._repository = new OperatoriRepositoryStub(this._testOperatore);
		}

		[TestMethod]
		public void Il_codicefiscale_ed_email_vengono_letti_da_dati_dell_operatore()
		{
			var istanza = new Istanze
			{
				CODICEISTRUTTORE = "1"
			};

			var testClass = ResponsabileIstruttoria.FromIstanza(istanza, this._repository);

			Assert.AreEqual<string>(this._testOperatore.CODICEFISCALE, testClass.CodiceFiscale);
			Assert.AreEqual<string>(this._testOperatore.EMAIL, testClass.Email);
		}

		[TestMethod]
		public void Il_codicefiscale_ed_email_sono_vuoti_se_in_istanza_non_c_e_codice_istruttore()
		{
			var istanza = new Istanze
			{
				CODICEISTRUTTORE = null
			};

			var testClass = ResponsabileIstruttoria.FromIstanza(istanza, this._repository);

			Assert.AreEqual<string>(String.Empty, testClass.CodiceFiscale);
			Assert.AreEqual<string>(String.Empty, testClass.Email);
		}
	}
}
