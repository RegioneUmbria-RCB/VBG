using Init.Sigepro.FrontEnd.AppLogic.Adapters;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using log4net;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio.LetturaXmlDomandaBackend
{
    internal class XmlDomandaBackendDaVbg : IXmlDomandaDaVisura
    {
        ILog _log = LogManager.GetLogger(typeof(XmlDomandaBackendDaVbg));

        IAliasResolver _aliasResolver;
        IVisuraService _visuraService;

        internal XmlDomandaBackendDaVbg(IAliasResolver aliasResolver, IVisuraService visuraService)
        { 
            this._aliasResolver = aliasResolver;
            this._visuraService = visuraService;
        }

        public string GetXml(string idDomandaBackend, bool leggiDatiConfigurazione = false)
        {
            var iIdDomanda = -1;

            if (!int.TryParse(idDomandaBackend, out iIdDomanda))
            {
                _log.ErrorFormat("Si sta cercando di effettuare una visura tramite VBG ma il codice istanza passato non è un codice numerico valido. Id Istanza passato: {0}", idDomandaBackend);
                return String.Empty;
            }

            var istanza = this._visuraService.GetById(iIdDomanda, new VisuraIstanzaFlags { LeggiDatiConfigurazione = leggiDatiConfigurazione });

            return (istanza != null) ? IstanzaSigeproAdapter.ConvertiIstanzaPerCompilazioneModello(istanza) : String.Empty;
        }
    }
}
