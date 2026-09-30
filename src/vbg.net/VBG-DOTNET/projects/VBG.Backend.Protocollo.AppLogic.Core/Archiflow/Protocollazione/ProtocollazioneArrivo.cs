using ProtocolloArchiFlowServiceReference;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.Archiflow.Protocollazione
{
    public class ProtocollazioneArrivo : IProtocollazioneArchiflow
    {
        public Guid GuidCardProtocollo
        {
            get;
            private set;
        }
        
        IDatiProtocollo _datiProto;
        IEnumerable<IAnagraficaAmministrazione> _anagraficaAmministrazione;
        ProtocollazioneServiceWrapper _wrapper;
        VerticalizzazioniWrapper _vert;
        string _fkidProtoIstanza;

        public ProtocollazioneArrivo(IDatiProtocollo datiProto, IEnumerable<IAnagraficaAmministrazione> anagraficaAmministrazione, ProtocollazioneServiceWrapper wrapper, VerticalizzazioniWrapper vert, string fkidProtoIstanza)
        {
            _datiProto = datiProto;
            _anagraficaAmministrazione = anagraficaAmministrazione;
            _wrapper = wrapper;
            _vert = vert;
            _fkidProtoIstanza = fkidProtoIstanza;
        }

        private SuapInsertProto GetRequest()
        {
            return new SuapInsertProto
            {
                DataProtocolloMittente = DateTime.Now.ToString("dd/MM/yyyy"),
                Mittente = String.Format("{0} ({1})", _anagraficaAmministrazione.First().NomeCognome, _anagraficaAmministrazione.First().CodiceFiscalePartitaIva),
                Oggetto = _datiProto.ProtoIn.Oggetto,
                protocolloMittente = _fkidProtoIstanza,
                servizio_destinatario = _datiProto.Uo,
                uffici = _datiProto.Ruolo,
                utenti = _vert.Username,
                Tipo = "IN",
                TipologiaSpedizione = _datiProto.ProtoIn.TipoDocumento
            };
        }

        public DatiProtocolloResponseType Protocolla()
        {
            var request = GetRequest();
            var response = _wrapper.ProtocollazioneArrivo(request);

            GuidCardProtocollo = response.GuidCard;

            return new DatiProtocolloResponseType
            {
                AnnoProtocollo = String.Concat("20", response.anno.ToString()),
                DataProtocollo = response.dataProtocollo,
                NumeroProtocollo = response.Numeroprotocollo.ToString(),
                IdProtocollo = response.GuidCard.ToString()
            };
        }
    }
}
