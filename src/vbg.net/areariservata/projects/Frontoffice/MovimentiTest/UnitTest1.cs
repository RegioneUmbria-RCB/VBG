using System;
using System.Text;
using System.Collections.Generic;
using System.Linq;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Init.Sigepro.FrontEnd.GestioneMovimenti.MovimentiWebService;
using System.IO;
using System.Xml.Serialization;

namespace MovimentiTest
{
	[TestClass]
	public class UnitTest1
	{
		[TestMethod]
		public void TestMethod1()
		{
			var dm = new DatiMovimento();

			using (var fs = File.Open(@"c:\temp\classe.xml", FileMode.Create))
			{
				XmlSerializer xs = new XmlSerializer(dm.GetType());
				xs.Serialize(fs, dm);
			}

		}
	}
}
