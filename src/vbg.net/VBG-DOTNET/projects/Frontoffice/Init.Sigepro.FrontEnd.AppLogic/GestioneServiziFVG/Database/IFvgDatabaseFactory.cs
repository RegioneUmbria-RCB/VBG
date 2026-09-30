using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG.Database
{
    public interface IFvgDatabaseFactory
    {
        FvgDatabase Create(long codiceIstanza, string idModulo);
    }
}
