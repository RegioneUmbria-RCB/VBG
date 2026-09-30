using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Pagamenti.Legacy.MIP
{
    public interface IMIPPaymentRequestFactory
    {
        PaymentRequest Create(IniziaPagamentoRequest request);
    }
}
