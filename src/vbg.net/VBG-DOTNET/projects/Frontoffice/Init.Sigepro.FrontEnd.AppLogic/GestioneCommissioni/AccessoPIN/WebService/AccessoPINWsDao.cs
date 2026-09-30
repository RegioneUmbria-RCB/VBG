using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneCommissioni.AccessoPIN.WebService
{
    public class AccessoPINWsDao : IAccessoPINDao
    {
        private readonly CommissioniAccessoPINServiceCreator _serviceCreator;

        public AccessoPINWsDao(CommissioniAccessoPINServiceCreator serviceCreator)
        {
            _serviceCreator = serviceCreator;
        }

        public int AssociaUtenteACommissioneByPIN(int codiceAnagrafe, string pin)
        {
            return this._serviceCreator.Call((ws, token) => ws.AssociaUtenteACommissioneByPIN(token, codiceAnagrafe, pin));
        }

        public bool VerificaValiditaPIN(string pin)
        {
            return this._serviceCreator.Call((ws, token) => ws.VerificaValiditaPIN(token, pin));
        }
    }
}
