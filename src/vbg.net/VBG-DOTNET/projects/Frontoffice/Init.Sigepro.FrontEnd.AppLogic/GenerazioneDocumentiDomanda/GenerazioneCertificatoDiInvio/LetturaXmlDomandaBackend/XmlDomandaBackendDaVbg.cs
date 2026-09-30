using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using log4net;
using System;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio.LetturaXmlDomandaBackend
{
    internal class XmlDomandaBackendDaVbg : IXmlDomandaDaVisura
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(XmlDomandaBackendDaVbg));

        private readonly IVisuraService _visuraService;

        internal XmlDomandaBackendDaVbg(IVisuraService visuraService)
        {
            this._visuraService = visuraService;
        }

        public string GetXml(int idDomandaBackend, bool leggiDatiConfigurazione = false)
        {
            var istanza = this._visuraService.GetById(idDomandaBackend, new VisuraIstanzaFlags { LeggiDatiConfigurazione = leggiDatiConfigurazione });

            return (istanza != null) ? istanza.ToXmlModelloRiepilogo() : String.Empty;
        }
    }
}
