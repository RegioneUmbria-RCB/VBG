using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Init.Sigepro.FrontEnd.Bari.TARES.Crypto;
using Init.Sigepro.FrontEnd.Bari.TARES;

namespace Init.Sigepro.FrontEnd.AppLogicTests.TaresBariTests
{
	[TestClass]
	public class TaresIdRichiestaTests
	{
		[TestMethod]
		public void Generazione_del_checksum_dell_idrichiesta()
		{
			var idUtente = "tente";
			var nomeServizio = TaresIdRichiesta.NomeServizio.getUtenze;
			var data = new TaresDate( new DateTime(2013, 1,2,3,4,5,6));

			var richiesta = new TaresIdRichiesta(idUtente, nomeServizio, data);

			var actual = richiesta.ToString();
			var expected = "f39b2e4a9cc5d5044927c37d878e26744cb291d13b5646ac61d72f11d75b2190";

			Assert.AreEqual<int>(64, actual.Length);
			Assert.AreEqual<string>(expected, actual);
		}
	}
}
