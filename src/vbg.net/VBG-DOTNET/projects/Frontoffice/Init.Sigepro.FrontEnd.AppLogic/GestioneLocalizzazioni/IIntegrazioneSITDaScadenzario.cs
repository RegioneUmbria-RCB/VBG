using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni
{
    public interface IIntegrazioneSITDaScadenzario
    {
        string GetUrlCompilazioneMovimento(int idDomanda, string returnTo);
    }
}
