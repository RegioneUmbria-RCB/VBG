using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.Pagamenti.MIP.Client
{
    internal interface IPayServerClient
    {
        string GeneraUrlRedirect(PaymentRequest request);
        string EstraiBuffer(string buffer);
        string GetStatoPagamento(MIPPaymentStatusRequest request);
    }
}
