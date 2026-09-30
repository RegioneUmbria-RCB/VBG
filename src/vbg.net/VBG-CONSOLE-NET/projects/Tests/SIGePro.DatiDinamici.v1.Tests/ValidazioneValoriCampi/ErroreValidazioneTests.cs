using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Init.SIGePro.DatiDinamici.ValidazioneValoriCampi;

namespace SIGePro.DatiDinamici.v1.Tests.ValidazioneValoriCampi
{
	public class ErroreValidazioneTests
	{
		[TestClass]
		public class EqualityTests
		{
			[TestMethod]
			public void Due_errori_con_lo_stesso_messaggio_e_lo_stesso_campo_sono_uguali()
			{
				var a = new ErroreValidazione("Msg", 1, 1);
				var b = new ErroreValidazione("Msg", 1, 1);

				Assert.AreEqual(a, b);
				Assert.IsTrue(a == b);
			}

			[TestMethod]
			public void Un_errore_nullo_e_diverso_da_un_errore_valorizzato()
			{
				var a = new ErroreValidazione("Msg", 1, 1);
				ErroreValidazione b = null;

				Assert.AreNotEqual(a, b);
				Assert.IsTrue(a != b);
				Assert.IsFalse(a == b);

				a = null;
				b = new ErroreValidazione("Msg", 1, 1);

				Assert.AreNotEqual(a, b);
				Assert.IsTrue(a != b);
				Assert.IsFalse(a == b);
			}

			[TestMethod]
			public void Un_errore_e_diverso_da_un_oggetto_nullo()
			{
				var a = new ErroreValidazione("Msg", 1, 1);
				ErroreValidazione b = null;

				Assert.AreNotEqual(a, b);
				Assert.IsTrue(a != b);
				Assert.IsFalse(a == b);
			}

			[TestMethod]
			public void Due_errori_con_lo_stesso_messaggio_e_campo_diverso_sono_diversi()
			{
				var a = new ErroreValidazione("Msg", 1, 1);
				var b = new ErroreValidazione("Msg", 2, 1);

				Assert.AreNotEqual(a, b);
				Assert.IsTrue(a != b);
				Assert.IsFalse(a == b);
			}

			[TestMethod]
			public void Due_errori_con_lo_stesso_messaggio_e_indice_diverso_sono_diversi()
			{
				var a = new ErroreValidazione("Msg", 1, 1);
				var b = new ErroreValidazione("Msg", 1, 2);

				Assert.AreNotEqual(a, b);
				Assert.IsTrue(a != b);
				Assert.IsFalse(a == b);
			}

			[TestMethod]
			public void Due_errori_con_messaggio_diverso_sono_diversi()
			{
				var a = new ErroreValidazione("Msg", 1, 1);
				var b = new ErroreValidazione("Msg1", 1, 1);

				Assert.AreNotEqual(a, b);
				Assert.IsTrue(a != b);
				Assert.IsFalse(a == b);
			}
		}
	}
}
