using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneDatiExtra
{
    public interface IDatiExtraService
    {
        void Set<T>(int idPratica, string chiave, T valore) where T : class;
        T Get<T>(int idPratica, string chiave) where T : class;
    }
}
