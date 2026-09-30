using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.Pagamenti.Tests.MIP
{
    public class MockUrlEncoder : IUrlEncoder
    {
        public string UrlEncode(string value)
        {
            return value;
        }
    }
}
