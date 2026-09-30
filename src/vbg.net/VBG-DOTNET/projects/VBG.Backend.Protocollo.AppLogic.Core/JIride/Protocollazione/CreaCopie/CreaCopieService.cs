using VBG.Shared.Infrastructure.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using static VBG.Backend.Protocollo.AppLogic.Shared.Validation.ProtocolloValidation;

namespace VBG.Backend.Protocollo.AppLogic.Core.JIride.Protocollazione.CreaCopie
{
    public class CreaCopieService
    {
        private readonly ProtocolloLogs _protocolloLogs;
        private readonly ProtocolloSerializer _protocolloSerializer;
        private readonly IBindingFactory _bindingFactory;

        public CreaCopieService(ProtocolloLogs protocolloLogs, ProtocolloSerializer protocolloSerializer, IBindingFactory bindingFactory)
        {
            this._protocolloLogs = protocolloLogs;
            this._protocolloSerializer = protocolloSerializer;
            this._bindingFactory = bindingFactory;
        }

        public void GeneraCopia(CreaCopieInfo info)
        {
            var client = new CreaCopieWSClient(this._protocolloLogs, this._protocolloSerializer, info.Url, this._bindingFactory);
            var creaCopieRequestXml = this._protocolloSerializer.Serialize(ProtocolloLogsConstants.CreaCopieRequestFileName, info.Request, TipiValidazione.NO_NAMESPACE);

            this._protocolloLogs.InfoFormat("CHIAMATA A CREA COPIE, REQUEST XML: {0}", creaCopieRequestXml);
            var response = client.CreaCopieString(creaCopieRequestXml, info.CodiceAmministrazione, info.AOO);

            if ((response.CopieCreate == null) || (response.CopieCreate.Length != 1))
            {
                throw new Exception($"MESSAGGIO: {response.Messaggio}, ERRORE: {response.Errore}");
            }

            this._protocolloLogs.InfoFormat("COPIA CREATA CON SUCCESSO");
        }

        public void CreaCopiePerAmministrazioniInterne(CreaCopieInfo parametri)
        {
            this._protocolloLogs.InfoFormat($"INIZIO FUNZIONALITÀ CREA COPIE J-IRIDE PER AMMINISTRAZIONI INTERNE DEL PROTOCOLLO NUMERO: {parametri.Request.NumeroProtocollo}, ANNO: {parametri.Request.AnnoProtocollo}, ID: {parametri.Request.IdDocumento}");

            if (parametri.Request.UODestinatarie == null || parametri.Request.UODestinatarie.Count() == 0) { return; }

            var requestXml = this._protocolloSerializer.Serialize(ProtocolloLogsConstants.CreaCopieRequestFileName, parametri.Request, TipiValidazione.NO_NAMESPACE);

            var client = new CreaCopieWSClient(this._protocolloLogs, this._protocolloSerializer, parametri.Url, this._bindingFactory);

            this._protocolloLogs.InfoFormat("CHIAMATA A CREA COPIE PER AMMINISTRAZIONI INTERNE, REQUEST XML: {0}", requestXml);

            var creaCopieOut = client.CreaCopieString(requestXml, parametri.CodiceAmministrazione, parametri.AOO);

            this._protocolloLogs.Info("DESERIALIZZAZIONE DELLA RISPOSTA DA CREA COPIE AVVENUTA CON SUCCESSO");

            if (creaCopieOut.CopieCreate == null || creaCopieOut.CopieCreate.Length == 0)
            {
                this._protocolloLogs.ErrorFormat("E' STATO RESTITUITO UN ERRORE DAL WEB SERVICE DURANTE LA FUNZIONALITÀ CREACOPIE ESEGUITA DOPO LA PROTOCOLLAZIONE, ID PROTOCOLLO ORIGINALE: {0}, NUMERO/ANNO {1}/{2}, ERRORE: {3}",
                                            parametri.Request.IdDocumento,
                                            parametri.Request.NumeroProtocollo,
                                            parametri.Request.AnnoProtocollo,
                                            creaCopieOut.Errore);
            }
            else
            {
                this._protocolloLogs.InfoFormat("E' STATA CREATA UNA COPIA CON LA FUNZIONALITÀ CREACOPIE ESEGUITA DOPO LA PROTOCOLLAZIONE, ID PROTOCOLLO ORIGINALE:{0}, ID PROTOCOLLO COPIA: {1}, NUMERO/ANNO PROTOCOLLO: {2}/{3}",
                                            creaCopieOut.IdDocumentoSorgente.ToString(),
                                            parametri.Request.IdDocumento,
                                            parametri.Request.NumeroProtocollo,
                                            parametri.Request.AnnoProtocollo
                                            );
            }
        }
    }
}
