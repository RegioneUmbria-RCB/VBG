using Init.Sigepro.FrontEnd.AppLogic.InvioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.StcService;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogicTests.InvioDomanda
{
    [TestClass]
    public class TestInvioIstanzaResult
    {
        [TestMethod]
        public void Richiamo_InvioRiuscito_con_RiferimentiPraticaType_null()
        {
            try
            {
                InvioIstanzaResult.InvioRiuscito(null);
            }
            catch( Exception ex )
            {
                Assert.Fail("Test fallito passando null su RiferimentiPraticaType",ex);
            }
        }

        [TestMethod]
        public void Richiamo_InvioRiuscito_con_AltriDati_null()
        {
            try
            {
                var rp = new RiferimentiPraticaType();
                InvioIstanzaResult.InvioRiuscito(rp);
            }
            catch (Exception ex)
            {
                Assert.Fail("Test fallito passando null su AltriDati di RiferimentiPraticaType", ex);
            }
        }

        [TestMethod]
        public void Richiamo_InvioRiuscitoNoBackend_con_RiferimentiPraticaType_null()
        {
            try
            {
                InvioIstanzaResult.InvioRiuscitoNoBackend(null);
            }
            catch (Exception ex)
            {
                Assert.Fail("Test fallito passando null su RiferimentiPraticaType", ex);
            }
        }
    }
}
