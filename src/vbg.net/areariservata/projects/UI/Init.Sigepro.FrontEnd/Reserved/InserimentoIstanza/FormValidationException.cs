using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
    class FormValidationException : Exception
    {
        public FormValidationException(string message): base(message)
        {

        }
    }
}
