using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio.LetturaXmlDomandaBackend
{
    public class XmlDomandaBackendStrategy
    {
        public enum TipoVisura
        {
            Vbg,
            Stc
        }

        XmlDomandaBackendDaStc _xmlDaStc;
        XmlDomandaBackendDaVbg _xmlDaVbg;

        public XmlDomandaBackendStrategy(IIstanzePresentateRepository istanzePresentateRepository, IVisuraService visuraService, IAliasSoftwareResolver aliasSoftwareResolver)
        {
            this._xmlDaStc = new XmlDomandaBackendDaStc(istanzePresentateRepository, aliasSoftwareResolver);
            this._xmlDaVbg = new XmlDomandaBackendDaVbg(aliasSoftwareResolver, visuraService);
        }

        public string GetXml(TipoVisura tipoVisura, string idDomandaBackend)
        {
            IXmlDomandaDaVisura visura = this._xmlDaVbg;

            if (tipoVisura == TipoVisura.Stc)
            {
                visura = this._xmlDaStc;
            }

            return visura.GetXml(idDomandaBackend, true);
        }
    }
}
