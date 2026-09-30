using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG.DebugConfiguration
{
    public interface IFVGDebugConfiguration
    {
        bool IsDebugEnabled { get; }
        string ManagedDataMappingsDevFile { get; }
    }
}
