using System;
using System.Collections.Generic;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Exceptions
{
    public class FormValidationException : Exception
    {
        public FormValidationException(string message) : base(message)
        {

        }
    }
}
