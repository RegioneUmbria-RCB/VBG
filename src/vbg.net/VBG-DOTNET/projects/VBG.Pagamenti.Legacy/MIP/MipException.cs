using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Pagamenti.Legacy.MIP
{
    public class MipException : Exception
    {
        public MipException(string message):base(message)
        {

        }
    }
}
