using Xunit;
using VBG.Pagamenti.Legacy.MIP;

namespace Init.Sigepro.FrontEnd.Pagamenti.Tests.MIP
{
    public class PaymentRequestTests
    {
        [Fact]
        public void ProvaSerializzazione()
        {
            var paymentRequest = new PaymentRequest();

            var xml = paymentRequest.ToXmlString();
        }
    }
}
