using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths
{
    public interface IUrlEncoder
    {
        string UrlEncode(string value);
    }
}
