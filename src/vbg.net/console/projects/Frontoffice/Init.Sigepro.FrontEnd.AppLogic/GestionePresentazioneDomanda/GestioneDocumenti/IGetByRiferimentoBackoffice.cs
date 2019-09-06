using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDocumenti
{
    public interface IGetByRiferimentoBackoffice
    {
        DocumentoDomanda GetByRiferimentoBackoffice(int riferimentoBackoffice);
    }
}
