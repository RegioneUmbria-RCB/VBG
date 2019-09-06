using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Utils;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio.LetturaXmlDomandaBackend
{
    internal class XmlDomandaBackendDaStc : IXmlDomandaDaVisura
    {
        IIstanzePresentateRepository _istanzePresentateRepository;
        IAliasSoftwareResolver _aliasSoftwareResolver;

        internal XmlDomandaBackendDaStc(IIstanzePresentateRepository istanzePresentateRepository, IAliasSoftwareResolver aliasSoftwareResolver)
        {
            this._istanzePresentateRepository = istanzePresentateRepository;
            this._aliasSoftwareResolver = aliasSoftwareResolver;
        }

        public string GetXml(string idDomandaBackend, bool leggiDatiConfigurazione = false)
        {
            var esitoVisuraStc = this._istanzePresentateRepository.GetDettaglioPratica(_aliasSoftwareResolver.AliasComune, _aliasSoftwareResolver.Software, idDomandaBackend);

            if (esitoVisuraStc == null || esitoVisuraStc.dettaglioPratica == null)
                return String.Empty;

            return StreamUtils.SerializeClass(esitoVisuraStc.dettaglioPratica);
        }
    }

}
