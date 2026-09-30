#if NET48
using Init.Sigepro.FrontEnd.Infrastructure.Server;
using System;
using System.Web;

namespace Init.Sigepro.FrontEnd.Infrastructure.Server.Framework
{
    public class PathMapper : IPathMapper
    {
        public bool IsPathMappingSupported => HttpContext.Current?.Server != null;

        public string MapPath(string relative)
        {
            if (HttpContext.Current?.Server == null)
            {
                throw new Exception("HttpContext non disponibile");
            }

            return HttpContext.Current.Server.MapPath(relative);
        }
    }
}
#endif