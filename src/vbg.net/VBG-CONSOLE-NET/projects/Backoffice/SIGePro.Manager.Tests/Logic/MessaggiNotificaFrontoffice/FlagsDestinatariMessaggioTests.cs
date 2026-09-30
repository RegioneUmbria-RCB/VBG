// -----------------------------------------------------------------------
// <copyright file="FlagsDestinatariMessaggioTests.cs" company="">
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
	using Init.SIGePro.Manager;
	using Init.SIGePro.Manager.Logic.MessaggiNotificaFrontoffice;

	[TestClass]
	public class FlagsDestinatariMessaggioTests
	{
		[TestMethod]
		public void InviaACreatoreIstanza_Impostato_RestituisceTrue()
		{
			var value = (int)DestinatariMessaggioEnum.CittadinoRichiedente;
			var testClass = new FlagsDestinatariMessaggio(value);

			Assert.IsTrue(testClass.InviaACreatoreIstanza);
		}

		[TestMethod]
		public void InviaAOperatore_Impostato_RestituisceTrue()
		{
			var value = (int)DestinatariMessaggioEnum.Operatore;
			var testClass = new FlagsDestinatariMessaggio(value);

			Assert.IsTrue(testClass.InviaAOperatore);
		}

		[TestMethod]
		public void InviaAResponsabileIstruttoria_Impostato_RestituisceTrue()
		{
			var value = (int)DestinatariMessaggioEnum.ResponsabileIstruttoria;
			var testClass = new FlagsDestinatariMessaggio(value);

			Assert.IsTrue(testClass.InviaAResponsabileIstruttoria);
		}

		[TestMethod]
		public void InviaAResponsabileProcedimento_Impostato_RestituisceTrue()
		{
			var value = (int)DestinatariMessaggioEnum.ResponsabileProcedimento;
			var testClass = new FlagsDestinatariMessaggio(value);

			Assert.IsTrue(testClass.InviaAResponsabileProcedimento);
		}

		[TestMethod]
		public void InviaAResponsabileSportello_Impostato_RestituisceTrue()
		{
			var value = (int)DestinatariMessaggioEnum.ResponsabileSportello;
			var testClass = new FlagsDestinatariMessaggio(value);

			Assert.IsTrue(testClass.InviaAResponsabileSportello);
		}

		[TestMethod]
		public void TuttiFlags_Impostati_RestituisconoTrue()
		{
			var value = (int)DestinatariMessaggioEnum.CittadinoRichiedente +
						(int)DestinatariMessaggioEnum.Operatore +
						(int)DestinatariMessaggioEnum.ResponsabileIstruttoria +
						(int)DestinatariMessaggioEnum.ResponsabileProcedimento +
						(int)DestinatariMessaggioEnum.ResponsabileSportello;

			var testClass = new FlagsDestinatariMessaggio(value);

			Assert.IsTrue(testClass.InviaACreatoreIstanza);
			Assert.IsTrue(testClass.InviaAOperatore);
			Assert.IsTrue(testClass.InviaAResponsabileIstruttoria);
			Assert.IsTrue(testClass.InviaAResponsabileProcedimento);
			Assert.IsTrue(testClass.InviaAResponsabileSportello);
		}

		[TestMethod]
		public void TuttiFlags_NonImpostati_RestituisconoFalse()
		{
			var value = 0;
			var testClass = new FlagsDestinatariMessaggio(value);

			Assert.IsFalse(testClass.InviaACreatoreIstanza);
			Assert.IsFalse(testClass.InviaAOperatore);
			Assert.IsFalse(testClass.InviaAResponsabileIstruttoria);
			Assert.IsFalse(testClass.InviaAResponsabileProcedimento);
			Assert.IsFalse(testClass.InviaAResponsabileSportello);
		}
	}
}
