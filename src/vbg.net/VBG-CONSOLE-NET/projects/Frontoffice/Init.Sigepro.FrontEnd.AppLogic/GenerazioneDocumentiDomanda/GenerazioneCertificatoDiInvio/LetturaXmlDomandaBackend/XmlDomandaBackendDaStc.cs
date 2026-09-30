using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Utils;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Xml;

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

            var classeSerializzata = StreamUtils.SerializeClass(esitoVisuraStc.dettaglioPratica);
            return classeSerializzata.Replace("xmlns=\"http://sigepro.init.it/rte/types\"", "");
            /*
            var document = new XmlDocument();
            var sw = new StringWriter();

            using (var xtr = new XmlTextReader(new StringReader(classeSerializzata)))
            using (var xtw = new XmlTextWriter(sw))
            {
                xtr.Namespaces = false;
                document.Load(xtr);

                document.WriteTo(xtw);

                return xtw.ToString();
            }
            */
        }
    }

}
