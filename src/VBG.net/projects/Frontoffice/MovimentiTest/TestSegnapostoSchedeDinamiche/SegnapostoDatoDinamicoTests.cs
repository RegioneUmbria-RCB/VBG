using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Init.Sigepro.FrontEnd.AppLogic.GestioneSostituzioneSegnapostoRiepilogo;

namespace MovimentiTest.TestSegnapostoSchedeDinamiche
{
	[TestClass]
	public class SegnapostoDatoDinamicoTests
	{
		[TestMethod]
		public void NomeTag_RestituisceCampoDinamico()
		{
			var expected = "campoDinamico";

			var segnaposto = new SegnapostoDatoDinamico();

			var result = segnaposto.NomeTag;

			Assert.AreEqual<string>(expected, result);
		}

		[TestMethod]
		public void NomeArgomento_RestituisceId()
		{
			var expected = "id";

			var segnaposto = new SegnapostoDatoDinamico();

			var result = segnaposto.NomeArgomento;

			Assert.AreEqual<string>(expected, result);
		}

		[TestMethod]
		[ExpectedException(typeof(ArgumentNullException))]
		public void Elabora_ConDomandaNull_SollevaEccezione()
		{
			var segnaposto = new SegnapostoDatoDinamico();

			segnaposto.Elabora(null, "", "");
		}
	}
}
