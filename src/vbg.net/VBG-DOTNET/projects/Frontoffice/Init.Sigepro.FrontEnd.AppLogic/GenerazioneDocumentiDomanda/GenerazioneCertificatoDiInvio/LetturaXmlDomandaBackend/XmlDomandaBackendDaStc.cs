using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza;
using Init.Utils;
using System;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio.LetturaXmlDomandaBackend
{
    internal class XmlDomandaBackendDaStc : IXmlDomandaDaVisura
    {
        private readonly IIstanzePresentateRepository _istanzePresentateRepository;
        private readonly IAliasSoftwareResolver _aliasSoftwareResolver;

        internal XmlDomandaBackendDaStc(IIstanzePresentateRepository istanzePresentateRepository, IAliasSoftwareResolver aliasSoftwareResolver)
        {
            this._istanzePresentateRepository = istanzePresentateRepository;
            this._aliasSoftwareResolver = aliasSoftwareResolver;
        }

        public string GetXml(int idDomandaBackend, bool leggiDatiConfigurazione = false)
        {
            var esitoVisuraStc = this._istanzePresentateRepository.GetDettaglioPratica(this._aliasSoftwareResolver.AliasComune, this._aliasSoftwareResolver.Software, idDomandaBackend.ToString());

            if (esitoVisuraStc == null || esitoVisuraStc.dettaglioPratica == null)
                return String.Empty;

            return StreamUtils.SerializeClass(esitoVisuraStc.dettaglioPratica);
        }
    }

}
