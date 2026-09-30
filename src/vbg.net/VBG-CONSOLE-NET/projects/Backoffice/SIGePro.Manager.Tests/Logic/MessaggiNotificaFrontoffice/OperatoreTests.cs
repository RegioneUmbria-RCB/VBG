// -----------------------------------------------------------------------
// <copyright file="OperatoreTests.cs" company="">
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
	using Init.SIGePro.Manager.Logic.MessaggiNotificaFrontoffice;
	using Init.SIGePro.Manager.Logic.GestioneOperatori;
	using SIGePro.Manager.Tests.Logic.MessaggiNotificaFrontoffice.Stubs;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	[TestClass]
	public class OperatoreTests
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
				CODICERESPONSABILE = "1"
			};

			var testClass = Operatore.FromIstanza(istanza, this._repository);

			Assert.AreEqual<string>(this._testOperatore.CODICEFISCALE, testClass.CodiceFiscale);
			Assert.AreEqual<string>(this._testOperatore.EMAIL, testClass.Email);
		}
	}
}
