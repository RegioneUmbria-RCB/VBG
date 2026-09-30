using Init.SIGePro.Manager;
using PersonalLib2.Data;
using VBG.Backend.Protocollo.AppLogic.Core.ProtoInf.DatiConfigurazioneProtocollo;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.ProtoInf
{
    public class RequestInfo
    {
        public VerticalizzazioniServiceWrapper ParametriRegola { get; private set; }
        public IDatiProtocollo Metadati { get; private set; }
        public DatiConfigurazioneProtocolloInfo DatiConfProtocollo { get; private set; }
        public ProtocolloLogs Logs { get; private set; }
        public ProtocolloSerializer Serializer { get; private set; }
        public IEnumerable<IAnagraficaAmministrazione> Anagrafiche { get; private set; }
        public ProtocolloAmministrazioni AmministrazioneDestinatario { get; private set; }
        public string CodiceIstanza { get; private set; }
        public string CodiceMovimento { get; private set; }
        public string CodicePec { get; private set; }

        public RequestInfo(VerticalizzazioniServiceWrapper vert, IDatiProtocollo metadati, DatiConfigurazioneProtocolloInfo datiConfProtocollo, ProtocolloLogs logs, ProtocolloSerializer serializer,
            IEnumerable<IAnagraficaAmministrazione> anagrafiche, DataBase dataBase, string idComune, string codiceIstanza, string codiceMovimento, PecInbox pec)
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
            var amm = mgr.GetById(idComune, Convert.ToInt32(vert.CodiceAmministrazioneDestinatario));
            this.AmministrazioneDestinatario = ProtocolloAmministrazioni.FromAmministrazione(amm);

        }
    }
}
