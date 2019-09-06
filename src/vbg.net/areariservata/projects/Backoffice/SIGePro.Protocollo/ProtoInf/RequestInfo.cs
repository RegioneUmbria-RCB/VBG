using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.ProtoInf.DatiConfigurazioneProtocollo;
using Init.SIGePro.Protocollo.Serialize;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.ProtoInf
{
    public class RequestInfo
    {
        public VerticalizzazioniServiceWrapper ParametriRegola { get; private set; }
        public IDatiProtocollo Metadati { get; private set; }
        public DatiConfigurazioneProtocolloInfo DatiConfProtocollo { get; private set; }
        public ProtocolloLogs Logs { get; private set; }
        public ProtocolloSerializer Serializer { get; private set; }
        public IEnumerable<IAnagraficaAmministrazione> Anagrafiche { get; private set; }
        public Amministrazioni AmministrazioneDestinatario { get; private set; }
        public string CodiceIstanza { get; private set; }
        public string CodiceMovimento { get; private set; }
        public string CodicePec { get; private set; }

        public RequestInfo(VerticalizzazioniServiceWrapper vert, IDatiProtocollo metadati, DatiConfigurazioneProtocolloInfo datiConfProtocollo, ProtocolloLogs logs, ProtocolloSerializer serializer, IEnumerable<IAnagraficaAmministrazione> anagrafiche, DataBase dataBase, string idComune, string codiceIstanza, string codiceMovimento, PecInbox pec)
        {
            this.ParametriRegola = vert;
            this.Metadati = metadati;
            this.DatiConfProtocollo = datiConfProtocollo;
            this.Logs = logs;
            this.Serializer = serializer;
            this.Anagrafiche = anagrafiche;
            this.CodiceIstanza = String.IsNullOrEmpty(codiceIstanza) ? "0" : codiceIstanza;
            this.CodiceMovimento = String.IsNullOrEmpty(codiceMovimento) ? "0" : codiceMovimento;
            this.CodicePec = pec != null ? pec.Id : "";

            var mgr = new AmministrazioniMgr(dataBase);
            this.AmministrazioneDestinatario = mgr.GetById(idComune, Convert.ToInt32(vert.CodiceAmministrazioneDestinatario));

        }
    }
}
