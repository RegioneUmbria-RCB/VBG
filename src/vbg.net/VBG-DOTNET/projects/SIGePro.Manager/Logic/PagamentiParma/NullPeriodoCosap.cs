using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.PagamentiParma
{
    public class NullPeriodoCosap : PeriodoCosapCompleto
    {
        public NullPeriodoCosap() : base(DateTime.Now, DateTime.Now, 0)
        {
        }

        public override string ToString()
        {
            return String.Empty;
        }
    }
}
