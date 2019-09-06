using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneBandiUmbria.Validazione
{
    interface IValidatoreCampi
    {
        IEnumerable<string> GetErroriValidazione();
    }
}
