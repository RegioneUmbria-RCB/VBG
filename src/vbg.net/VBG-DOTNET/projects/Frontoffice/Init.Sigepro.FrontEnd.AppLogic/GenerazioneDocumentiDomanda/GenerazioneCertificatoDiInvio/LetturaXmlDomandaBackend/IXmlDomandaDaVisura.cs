using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio.LetturaXmlDomandaBackend
{
    internal interface IXmlDomandaDaVisura
    {
        string GetXml(int idDomandaBackend, bool leggiDatiConfigurazione = false);
    }
}
