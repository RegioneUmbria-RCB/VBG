using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogicTests.GestionePresentazioneDomanda
{
    [TestClass]
    public class PresentazioneIstanzaDataKeyTests
    {
        [TestMethod]
        public void PresentazioneIstanzaDataKey_from_serialization_code()
        {
            var code = "E256_SS_GRGNCL79C19G478O_0739";

            var dataKey = PresentazioneIstanzaDataKey.FromSerializationCode(code);

            Assert.AreEqual<string>("E256", dataKey.IdComune);
            Assert.AreEqual<string>("SS", dataKey.Software);
            Assert.AreEqual<string>("GRGNCL79C19G478O", dataKey.CodiceUtente);
            Assert.AreEqual<int>(739, dataKey.IdPresentazione);
        }
    }
}
