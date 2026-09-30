using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.Pagamenti.MIP
{
    public class MipException : Exception
    {
        public MipException(string message):base(message)
        {

        }
    }
}
