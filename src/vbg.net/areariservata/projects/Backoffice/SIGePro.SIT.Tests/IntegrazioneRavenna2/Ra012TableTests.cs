using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Sit.Ravenna2;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace SIGePro.SIT.Tests.IntegrazioneRavenna2
{
	[TestClass]
	public class Ra012TableTests
	{
		// Nell'installazione di ravenna non posso eseguire una select * perchè la vista contiene dei campi spaziali
		// Se vogli oselezionare tutti i campi devo specificare tutti i campi che mi interessano in quella tabella
		[TestMethod]
		public void Ra012Table_test_all_fields()
		{
			var table = new Ra012Table("");

			var result = table.AllFields().ToString();

			var expected = "RA012.numero, RA012.cod_via, RA012.cod_edif, RA012.parte, RA012.foglio, RA012.particella, RA012.sezione, RA012.indirizzo";

			Assert.AreEqual(expected, result);
		}
	}
}
