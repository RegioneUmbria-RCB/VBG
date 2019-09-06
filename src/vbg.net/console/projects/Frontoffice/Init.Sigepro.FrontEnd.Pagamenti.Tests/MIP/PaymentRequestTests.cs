using Init.Sigepro.FrontEnd.Pagamenti.MIP;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.Pagamenti.Tests.MIP
{
    [TestClass]
    public class PaymentRequestTests
    {
        [TestMethod]
        public void ProvaSerializzazione()
        {
            var paymentRequest = new PaymentRequest();

            var xml = paymentRequest.ToXmlString();
        }
    }
}
