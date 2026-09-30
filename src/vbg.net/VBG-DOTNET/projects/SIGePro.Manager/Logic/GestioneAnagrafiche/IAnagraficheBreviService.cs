using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneAnagrafiche
{
    public interface IAnagraficheBreviService
    {
        string GetNomeUtente(int codiceAnagrafe);
    }
}
