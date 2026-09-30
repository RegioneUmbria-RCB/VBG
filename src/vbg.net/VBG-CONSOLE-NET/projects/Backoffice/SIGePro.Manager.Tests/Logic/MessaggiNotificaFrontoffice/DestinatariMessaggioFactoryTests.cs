// -----------------------------------------------------------------------
// <copyright file="DestinatariMessaggioFactoryTests.cs" company="">
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
	using Init.SIGePro.Manager;
	using Init.SIGePro.Data;
	using SIGePro.Manager.Tests.Logic.MessaggiNotificaFrontoffice.Stubs;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	[TestClass]
	public class DestinatariMessaggioFactoryTests
	{
		DestinatariMessaggioFactory _testClass;

		[TestInitialize]
		public void Initialize()
		{
			var istanza = new Istanze { CODICEPROFESSIONISTA = "1", SOFTWARE="SS", CODICEISTRUTTORE = "1", CODICERESPONSABILEPROC = "1" };
			var anagrafeRepository = new AnagrafeRepositoryStub(new Anagrafe());
			var configRepository = new ConfigurazioneRepositoryStub(new Configurazione { CODICERESPONSABILE="1"});
			var operatoriRepository = new OperatoriRepositoryStub(new Responsabili());

			this._testClass = new DestinatariMessaggioFactory(istanza, anagrafeRepository, configRepository, operatoriRepository);

		}

		[TestMethod]
		public void Flag_Creatore_Impostato_Istanzia_CreatoreIstanza()
		{
			var flags = new FlagsDestinatariMessaggio((int)DestinatariMessaggioEnum.CittadinoRichiedente);
			
			var result = this._testClass.GetDestinatari(flags);

			Assert.AreEqual<int>(1, result.Count());
			Assert.AreEqual<Type>(typeof(CreatoreIstanza), result.ElementAt(0).GetType());
		}

		[TestMethod]
		public void Flag_Operatore_Impostato_Istanzia_Operatore()
		{
			var flags = new FlagsDestinatariMessaggio((int)DestinatariMessaggioEnum.Operatore);

			var result = this._testClass.GetDestinatari(flags);

			Assert.AreEqual<int>(1, result.Count());
			Assert.AreEqual<Type>(typeof(Operatore), result.ElementAt(0).GetType());
		}

		[TestMethod]
		public void Flag_ResponsabileIstruttoria_Impostato_Istanzia_ResponsabileIstruttoria()
		{
			var flags = new FlagsDestinatariMessaggio((int)DestinatariMessaggioEnum.ResponsabileIstruttoria);

			var result = this._testClass.GetDestinatari(flags);

			Assert.AreEqual<int>(1, result.Count());
			Assert.AreEqual<Type>(typeof(ResponsabileIstruttoria), result.ElementAt(0).GetType());
		}

		[TestMethod]
		public void Flag_ResponsabileProcedimento_Impostato_Istanzia_ResponsabileProcedimento()
		{
			var flags = new FlagsDestinatariMessaggio((int)DestinatariMessaggioEnum.ResponsabileProcedimento);

			var result = this._testClass.GetDestinatari(flags);

			Assert.AreEqual<int>(1, result.Count());
			Assert.AreEqual<Type>(typeof(ResponsabileProcedimento), result.ElementAt(0).GetType());
		}

		[TestMethod]
		public void Flag_ResponsabileSportello_Impostato_Istanzia_ResponsabileSportello()
		{
			var flags = new FlagsDestinatariMessaggio((int)DestinatariMessaggioEnum.ResponsabileSportello);

			var result = this._testClass.GetDestinatari(flags);

			Assert.AreEqual<int>(1, result.Count());
			Assert.AreEqual<Type>(typeof(ResponsabileSportello), result.ElementAt(0).GetType());
		}
	}
}
