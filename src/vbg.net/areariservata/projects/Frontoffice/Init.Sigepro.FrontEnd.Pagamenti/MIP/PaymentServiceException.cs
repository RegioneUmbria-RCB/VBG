using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.Pagamenti.MIP
{
    class PaymentServiceException : Exception
    {
        public PaymentServiceException(string message)
            : base(message)
        {
        }
    }
}
