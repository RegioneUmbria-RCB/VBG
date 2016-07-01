using System;
using System.Text;
using System.Collections.Generic;
using System.Linq;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Init.Sigepro.FrontEnd.Bari.CID.ServiceProxy;
using System.Globalization;

namespace Init.Sigepro.FrontEnd.Bari.Tests
{
	[TestClass]
	public class DatiAutorizzazioneCidTests
	{
		string user = "test";
		string pass = "test";
		string expectedPassword		= "501892fc1c07fa28df0b909df176328c54be7f5f56c8d00e98c9e1f35a0f506c";
		string expectedIdRichiesta	= "d06943e11f5cbff481a7fa07e332082da7f5f62ff1657726d200a08dc3a6cd9d";
		DateTime data = DateTime.MinValue;

		[TestMethod]
		public void Conversione_in_dati_autorizzazione()
		{
			var result = new DatiAutorizzazioneCid(user, pass, data).ToDatiAutorizzazioneType();

			Assert.AreEqual<string>(data.ToString("dd/MM/yyyy",CultureInfo.InvariantCulture), result.dataRichiesta);
			Assert.AreEqual<string>(data.ToString("HH:mm:ss.fff",CultureInfo.InvariantCulture), result.oraRichiesta);
			Assert.AreEqual<datiAutorizzazioneTypeIdentificativoServizio>(datiAutorizzazioneTypeIdentificativoServizio.S00, result.identificativoServizio);
			Assert.AreEqual<string>(user, result.identificativoUtente);
			Assert.AreEqual<string>(expectedPassword, result.password);
			Assert.AreEqual<string>(expectedIdRichiesta, result.idRichiesta);
		}
	}
}
