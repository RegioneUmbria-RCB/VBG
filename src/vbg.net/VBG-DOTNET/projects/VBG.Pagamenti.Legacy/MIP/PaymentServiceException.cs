using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Pagamenti.Legacy.MIP
{
    class PaymentServiceException : Exception
    {
        public PaymentServiceException(string message)
            : base(message)
        {
        }
    }
}
