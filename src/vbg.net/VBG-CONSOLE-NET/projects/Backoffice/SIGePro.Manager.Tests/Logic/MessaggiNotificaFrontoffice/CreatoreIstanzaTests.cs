// -----------------------------------------------------------------------
// <copyright file="CreatoreIstanzaTests.cs" company="">
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
	using Init.SIGePro.Manager.Logic.MessaggiNotificaFrontoffice;
	using Init.SIGePro.Data;
	using Init.SIGePro.Manager.Logic.GestioneAnagrafiche;
	using SIGePro.Manager.Tests.Logic.MessaggiNotificaFrontoffice.Stubs;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	[TestClass]
	public class CreatoreIstanzaTests
	{
		AnagrafeRepositoryStub _repository;
		Anagrafe _testAnagrafe;

		[TestInitialize]
		public void Initialize()
		{
			this._testAnagrafe = new Anagrafe
			{
				CODICEFISCALE = "codiceFiscale",
				EMAIL = "email"
			};
			this._repository = new AnagrafeRepositoryStub(this._testAnagrafe);
		}

		[TestMethod]
		public void Se_il_tecnico_non_e_presente_il_creatore_e_il_richiedente()
		{
			var istanza = new Istanze
			{
				CODICEPROFESSIONISTA = null,
				CODICERICHIEDENTE = "1"
			};

			var testClass = CreatoreIstanza.DaIstanza(istanza, this._repository);

			Assert.AreEqual<int>(1, this._repository.ChiamatoConId);
		}

		[TestMethod]
		public void Se_il_tecnico_e_presente_il_creatore_e_il_tecnico()
		{
			var istanza = new Istanze
			{
				CODICEPROFESSIONISTA = "1",
				CODICERICHIEDENTE = "2"
			};

			var testClass = CreatoreIstanza.DaIstanza(istanza, this._repository);

			Assert.AreEqual<int>(1, this._repository.ChiamatoConId);
		}

		[TestMethod]
		public void Il_codicefiscale_ed_email_vengono_letti_da_anagrafica()
		{
			var istanza = new Istanze
			{
				CODICEPROFESSIONISTA = "1",
				CODICERICHIEDENTE = "2"
			};

			var testClass = CreatoreIstanza.DaIstanza(istanza, this._repository);

			Assert.AreEqual<string>(this._testAnagrafe.CODICEFISCALE, testClass.CodiceFiscale);
			Assert.AreEqual<string>(this._testAnagrafe.EMAIL, testClass.Email);
		}
	}
}
