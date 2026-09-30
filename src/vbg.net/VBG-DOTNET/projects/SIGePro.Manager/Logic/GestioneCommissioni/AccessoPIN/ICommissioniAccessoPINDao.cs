using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneCommissioni.AccessoPIN
{
    public interface ICommissioniAccessoPINDao
    {
        bool VerificaValiditaPIN(string pin);
        RiferimentiRigaAppello AssociaUtenteACommissioneByPIN(int codiceAnagrafe, string pin);
    }
}
