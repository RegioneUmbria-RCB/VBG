using System.IO;
using System.Web;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces.Abstractions;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Infrastructure
{
    internal class FrameworkTemporaryPathProvider : ITemporaryPathProvider
    {
        public string GetTemporaryRootPath()
        {
            var path = HttpContext.Current.Server.MapPath("~/temp");

            if (!Directory.Exists(path))
                Directory.CreateDirectory(path);

            return path;
        }
    }
}