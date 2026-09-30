using VBG.Backend.Protocollo.AppLogic.Core.Iride2.Configuration;
using VBG.Backend.Protocollo.AppLogic.Core.Iride2.Services;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;


namespace VBG.Backend.Protocollo.AppLogic.Core.Iride2.CreaCopie
{
    public class CreaCopieInfo
    {
        public ProtocolloLogs ProtocolloLogs { get; private set; }
        public ProtocolloSerializer ProtocolloSerializer { get; private set; }
        public ProtocolloServiceWrapper ProtocolloIrideService { get; private set; }
        public VerticalizzazioniConfiguration Vert { get; private set; }
        public string IdProtocolloSorgente { get; private set; }
        public string NumeroProtocolloSorgente { get; private set; }
        public string AnnoProtocolloSorgente { get; private set; }
        public DateTime DataProtocolloSorgente { get; private set; }
        public string Operatore { get; private set; }
        public string Uo { get; private set; }
        public string Ruolo { get; private set; }
        public string ProxyAddress { get; private set; }

        public CreaCopieInfo(ProtocolloLogs logs, ProtocolloSerializer serializer, ProtocolloServiceWrapper protocolloIrideService, VerticalizzazioniConfiguration vert, string idProtocolloSorgente, string numeroProtocolloSorgente, string annoProtocolloSorgente, string operatore, string ruolo, string uo, string proxyAddress, DateTime dataProtocolloSorgente)
        {
            this.ProtocolloLogs = logs;
            this.ProtocolloSerializer = serializer;
            this.ProtocolloIrideService = protocolloIrideService;
            this.Vert = vert;
            this.IdProtocolloSorgente = idProtocolloSorgente;
            this.Operatore = operatore;
            this.Ruolo = ruolo;
            this.Uo = uo;
            this.ProxyAddress = proxyAddress;
            this.NumeroProtocolloSorgente = numeroProtocolloSorgente;
            this.AnnoProtocolloSorgente = annoProtocolloSorgente;
            this.DataProtocolloSorgente = dataProtocolloSorgente;
        }
    }
}
