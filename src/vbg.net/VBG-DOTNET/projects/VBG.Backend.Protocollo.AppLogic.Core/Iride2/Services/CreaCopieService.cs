using ProtocolloIride2Service;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Shared.Infrastructure.ServiceModel;


namespace VBG.Backend.Protocollo.AppLogic.Core.Iride2.Services
{
    internal class CreaCopieService
    {
        private readonly string _urlWebService;
        private readonly string _proxyAddress;
        private readonly ProtocolloLogs _protocolloLogs;
        private readonly ProtocolloSerializer _protocolloSerializer;
        private readonly ProtocolloClientServiceCreator _protocolloClientServiceCreator;

        internal CreaCopieService(string urlWebService, string proxyAddress, ProtocolloLogs protocolloLog, ProtocolloSerializer protocolloSerializer, IBindingFactory bindingFactory)
        {

            if (String.IsNullOrEmpty(urlWebService))
                throw new Exception("URL WEB SERVICE NON VALORIZZATO");

            if (protocolloLog == null)
                throw new Exception("PROTOCOLLOLOG E' NULL");

            if (protocolloSerializer == null)
                throw new Exception("PROTOCOLLOSERIALIZER E' NULL");

            this._urlWebService = urlWebService;
            this._protocolloLogs = protocolloLog;
            this._protocolloSerializer = protocolloSerializer;
            this._proxyAddress = proxyAddress;
            this._protocolloClientServiceCreator = new ProtocolloClientServiceCreator(protocolloLog, bindingFactory, proxyAddress, urlWebService);
        }

        public CreaCopieOut CreaCopie(string ruolo, int idDocumento, string annoProtocollo, string numeroProtocollo, string operatoreIride, string codiceEnte, UODestinataria[] destinatari)
        {
            if (String.IsNullOrEmpty(ruolo))
                throw new Exception("RUOLO NON VALORIZZATO");

            if (idDocumento <= 0)
                throw new Exception("ID PROTOCOLLO (DOCID) NON VALIDO");

            if (String.IsNullOrEmpty(operatoreIride))
                throw new Exception("OPERATORE NON VALORIZZATO");
            try
            {
                var destinatarie = new ArrayOfUODestinataria();
                destinatarie.AddRange(destinatari.ToList());

                this._protocolloLogs.Debug("Valorizzazione di CreaCopieIn");
                var creaCopieIn = new CreaCopieIn
                {
                    AnnoProtocollo = annoProtocollo,
                    NumeroProtocollo = numeroProtocollo,
                    IdDocumento = idDocumento.ToString(),
                    UODestinatarie = destinatarie,
                    Utente = operatoreIride,
                    Ruolo = ruolo
                };
                this._protocolloLogs.Debug("Fine Valorizzazione di CreaCopieIn");

                this._protocolloSerializer.LogAndValidate(ProtocolloLogsConstants.CreaCopieRequestFileName, creaCopieIn);
                this._protocolloLogs.InfoFormat("Chiamata a web method CreaCopie, CodiceAmministrazione (CodiceEnte): {0}, numero protocollo: {1}, anno protocollo: {2}, id: {3}", codiceEnte, numeroProtocollo, annoProtocollo, idDocumento);

                using (var ws = this._protocolloClientServiceCreator.CreateClient())
                {
                    var creaCopieOut = ws.Service.CreaCopie(creaCopieIn, codiceEnte, string.Empty);

                    this._protocolloLogs.Info("CREAZIONE COPIA AVVENUTA CON SUCCESSO");

                    this._protocolloSerializer.LogAndValidate(ProtocolloLogsConstants.CreaCopieResponseFileName, creaCopieOut);

                    return creaCopieOut;
                }
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE LA CREAZIONE DELLE COPIE", ex);
            }

        }

        public CreaCopieOut CreaCopie(string uo, string ruolo, int idDocumento, string annoProtocollo, string numeroProtocollo, string operatoreIride, string codiceEnte)
        {
            if (String.IsNullOrEmpty(uo))
                throw new Exception("UO NON VALORIZZATA");

            var destinatari = new UODestinataria[]{
                new UODestinataria{
                    Carico = uo,
                    Data = DateTime.Now.ToString("dd/MM/yyyy"),
                    NumeroCopie = "1",
                    TipoUO = "UO"
                }
            };

            return this.CreaCopie(ruolo, idDocumento, annoProtocollo, numeroProtocollo, operatoreIride, codiceEnte, destinatari);
        }
    }
}
