using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Xunit;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogicTests.GestionePresentazioneDomanda
{
    public class PresentazioneIstanzaDataKeyTests
    {
        [Fact]
        public void PresentazioneIstanzaDataKey_from_serialization_code()
        {
            var code = "E256_SS_GRGNCL79C19G478O_0739";
            var dataKey = PresentazioneIstanzaDataKey.FromSerializationCode(code);
            Assert.Equal("E256", dataKey.IdComune);
            Assert.Equal("SS", dataKey.Software);
            Assert.Equal("GRGNCL79C19G478O", dataKey.CodiceUtente);
            Assert.Equal(739, dataKey.IdPresentazione);
        }
    }
}
