using Init.SIGePro.Protocollo.ProtocolloArchiFlowServiceReference;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.WsDataClass;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Archiflow.Protocollazione
{
    public class ProtocollazionePartenza : IProtocollazione
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

        public ProtocollazionePartenza(IDatiProtocollo datiProto, IEnumerable<IAnagraficaAmministrazione> anagraficaAmministrazione, ProtocollazioneServiceWrapper wrapper, VerticalizzazioniWrapper vert, string fkidProtoIstanza)
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
                Mittente = _datiProto.Uo,
                Oggetto = _datiProto.ProtoIn.Oggetto,
                protocolloMittente = _fkidProtoIstanza,
                servizio_destinatario = String.Format("{0} ({1})", _anagraficaAmministrazione.First().NomeCognome, _anagraficaAmministrazione.First().CodiceFiscalePartitaIva),
                uffici = _datiProto.Ruolo,
                utenti = _vert.Username,
                Tipo = "OUT",
                TipologiaSpedizione = _datiProto.ProtoIn.TipoDocumento
            };
        }

        public DatiProtocolloRes Protocolla()
        {
            var request = GetRequest();
            var response = _wrapper.ProtocollazioneArrivo(request);

            GuidCardProtocollo = response.GuidCard;

            return new DatiProtocolloRes
            {
                AnnoProtocollo = String.Concat("20", response.anno.ToString()),
                DataProtocollo = response.dataProtocollo,
                NumeroProtocollo = response.Numeroprotocollo.ToString(),
                IdProtocollo = response.GuidCard.ToString()
            };
        }
    }
}
