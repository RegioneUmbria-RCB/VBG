// -----------------------------------------------------------------------
// <copyright file="DatiAutorizzazioneTasiTests.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Bari.Tests
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Microsoft.VisualStudio.TestTools.UnitTesting;
	using Init.Sigepro.FrontEnd.Bari.TASI.Autorizzazione;
	using System.Globalization;
	using Init.Sigepro.FrontEnd.Bari.TASI.wsdl;

	[TestClass]
	public class DatiAutorizzazioneTasiTests
	{
		string user = "test";
		string pass = "test";
		string expectedPassword = "501892fc1c07fa28df0b909df176328c54be7f5f56c8d00e98c9e1f35a0f506c";
		string expectedIdRichiesta = "ebcf001b1d4e6a6a95ced9e8f41f88fbeb476e7fe8faf6b3bc375439134379f4";
		DateTime data = DateTime.MinValue;

		[TestMethod]
		public void Conversione_in_dati_autorizzazione()
		{
			var result = new DatiAutorizzazioneTasi(user, pass, data).ToDatiAutorizzazioneType();

			Assert.AreEqual<string>(data.ToString("dd/MM/yyyy", CultureInfo.InvariantCulture), result.dataRichiesta);
			Assert.AreEqual<string>(data.ToString("HH:mm:ss.fff", CultureInfo.InvariantCulture), result.oraRichiesta);
			Assert.AreEqual<datiAutorizzazioneTypeIdentificativoServizio>(datiAutorizzazioneTypeIdentificativoServizio.TS01, result.identificativoServizio);
			Assert.AreEqual<string>(user, result.identificativoUtente);
			Assert.AreEqual<string>(expectedPassword, result.password);
			Assert.AreEqual<string>(expectedIdRichiesta, result.idRichiesta);
		}
	}
}
