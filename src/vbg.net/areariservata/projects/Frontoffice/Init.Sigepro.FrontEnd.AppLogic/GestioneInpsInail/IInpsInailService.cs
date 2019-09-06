using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneInpsInail
{
    public interface IInpsInailService
    {
        IEnumerable<BaseDtoOfStringString> GetSediInps(string partial);
        IEnumerable<BaseDtoOfStringString> GetSediInail(string partial);
    }
}
