using Init.SIGePro.Protocollo.Logs;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Iride.Configuration;
using Init.SIGePro.Protocollo.Serialize;

namespace Init.SIGePro.Protocollo.JIride.Protocollazione
{
    public class CreaCopieInfo
    {
        public enum TipoAssegnazione { COMPETENZA, CONOSCENZA };

        public ProtocolloLogs ProtocolloLogs { get; private set; }
        public ProtocolloSerializer ProtocolloSerializer { get; private set; }
        public ParametriRegoleInfo Vert { get; private set; }
        public string IdProtocolloSorgente { get; private set; }
        public string NumeroProtocolloSorgente { get; private set; }
        public string AnnoProtocolloSorgente { get; private set; }
        public DateTime DataProtocolloSorgente { get; private set; }
        public string Operatore { get; private set; }
        public string Uo { get; private set; }
        public string Ruolo { get; private set; }
        public string ProxyAddress { get; private set; }
        public TipoAssegnazione TipologiaAssegnazione { get; private set; }

        public CreaCopieInfo(ProtocolloLogs logs, ProtocolloSerializer serializer, ParametriRegoleInfo vert, string idProtocolloSorgente, string numeroProtocolloSorgente, string annoProtocolloSorgente, string operatore, string ruolo, string uo, string proxyAddress, DateTime dataProtocolloSorgente, TipoAssegnazione tipologiaAssegnazionee)
        {
            this.ProtocolloLogs = logs;
            this.ProtocolloSerializer = serializer;
            this.Vert = vert;
            this.IdProtocolloSorgente = idProtocolloSorgente;
            this.Operatore = operatore;
            this.Ruolo = ruolo;
            this.Uo = uo;
            this.ProxyAddress = proxyAddress;
            this.NumeroProtocolloSorgente = numeroProtocolloSorgente;
            this.AnnoProtocolloSorgente = annoProtocolloSorgente;
            this.DataProtocolloSorgente = dataProtocolloSorgente;
            this.TipologiaAssegnazione = tipologiaAssegnazionee;
        }
    }
}
