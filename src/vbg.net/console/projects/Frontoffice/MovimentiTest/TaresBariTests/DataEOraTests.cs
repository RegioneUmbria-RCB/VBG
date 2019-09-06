using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Init.Sigepro.FrontEnd.Bari.TARES;

namespace Init.Sigepro.FrontEnd.AppLogicTests.TaresBariTests
{
	[TestClass]
	public class DataTaresTests
	{
		[TestMethod]
		public void Verifica_della_conversione_della_data_in_stringa()
		{
			var dt = new DateTime(2013, 1, 2, 3, 4, 5, 6);
			var data = new TaresDate(dt);

			var result = data.ToDateString();

			Assert.AreEqual<string>("02/01/2013", result);
		}

		[TestMethod]
		public void Verifica_della_conversione_dell_ora_in_stringa()
		{
			var dt = new DateTime(2013, 1, 2, 3, 4, 5, 6);
			var data = new TaresDate(dt);

			var result = data.ToTimeString();

			Assert.AreEqual<string>("03:04:05.006", result);
		}
	}
}
