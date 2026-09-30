using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Sit.ItCity
{
    public class RequestAdapter
    {
        public RequestAdapter()
        {

        }

        public RequestCivici AdattaCivici(string CodVia)
        {
            return new RequestCivici
            {
                Indir = CodVia,
            };
        }
    }
}
