using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Web;

namespace Init.Sigepro.UrlSecurity.Runtime
{
    internal class HttpUtilityUrlDecoder : IUrlDecoder
    {
        public string Decode(string text)
        {
            return HttpUtility.UrlDecode(text);
        }
    }
}
