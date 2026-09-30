using System;
using System.Collections.Generic;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Interfaces
{
    public interface IPathMapper
    {
        string MapPath(string relativePath);
    }
}
