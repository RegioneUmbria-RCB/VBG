using Init.SIGePro.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneMovimentiFrontoffice
{
    public interface IRisolviSchedeDinamicheMovimento
    {
        IEnumerable<Dyn2ModelliT> GetSchedeDelMovimento(int codiceMovimento);
    }
}
