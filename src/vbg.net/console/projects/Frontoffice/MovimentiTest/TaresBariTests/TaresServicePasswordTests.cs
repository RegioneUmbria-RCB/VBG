using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Init.Sigepro.FrontEnd.Bari.TARES;
using Init.Sigepro.FrontEnd.Bari.TARES.Crypto;

namespace Init.Sigepro.FrontEnd.AppLogicTests.TaresBariTests
{
	[TestClass]
	public class TaresServicePasswordTests
	{
		[TestMethod]
		public void Generazione_del_checksum_dell_idrichiesta()
		{
			var idUtente = "tente";
			var passphrase = "1234567890123456789012345678901234567890";
			var data = new TaresDate(new DateTime(2013, 1, 2, 3, 4, 5, 6));

			var richiesta = new TaresServicePassword(idUtente, passphrase, data);

			var actual = richiesta.ToString();
			var expected = "589922d6b76207c46931e51643802b29aec608aa0d55890a9a70af6bbc4fc17b";

			Assert.AreEqual<int>(64, actual.Length);
			Assert.AreEqual<string>(expected, actual);
		}

		[TestMethod]
		[ExpectedException(typeof(ArgumentException))]
		public void La_lunghezza_della_passphrase_deve_essere_40_caratteri()
		{
			var idUtente = "tente";
			var passphrase = "123456";
			var data = new TaresDate(new DateTime(2013, 1, 2, 3, 4, 5, 6));

			var richiesta = new TaresServicePassword(idUtente, passphrase, data);
		}
	}
}
