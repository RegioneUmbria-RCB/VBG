using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.PathMapper
{
    public class FrameworkPathMapper : IPathMapper
    {
        public string MapPath(string relativePath) => HttpContext.Current.Server.MapPath(relativePath);
    }
}