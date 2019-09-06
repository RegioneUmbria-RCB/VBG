using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Pal.Protocollazione.Allegati;

namespace Init.SIGePro.Protocollo.Pal.Protocollazione
{
    public class ProtocollazioneAdapter
    {
        IDatiProtocollo _datiProto;
        IEnumerable<IAnagraficaAmministrazione> _anagrafiche;
        VerticalizzazioniWrapper _vert;
        string _timeStamp;

        public ProtocollazioneAdapter(IDatiProtocollo datiProto, IEnumerable<IAnagraficaAmministrazione> anagrafiche, VerticalizzazioniWrapper vert, string timeStamp)
        {
            _datiProto = datiProto;
            _anagrafiche = anagrafiche;
            _vert = vert;
            _timeStamp = timeStamp;
        }

        public ProtocollazioneType Adatta()
        {
            var factoryProtocollazione = ProtocollazioneFactory.Create(_anagrafiche, _datiProto);
            var allegatiAdapter = new AllegatiAdapter(_datiProto.ProtoIn.Allegati, _timeStamp);

            var request = new ProtocollazioneType
            {
                Intestazione = new IntestazioneType
                {
                    Aoo = _vert.CodiceAoo,
                    Ente = _vert.CodiceIstat,
                    Classifica = _datiProto.ProtoIn.Classifica,
                    ModelloOrganizzativo = _datiProto.Ruolo,
                    Oggetto = _datiProto.ProtoIn.Oggetto,
                    TipoProtocollo = factoryProtocollazione.Flusso,
                    Mittenti = factoryProtocollazione.GetMittenti(),
                    Destinatari = factoryProtocollazione.GetDestinatari()
                }
            };

            if (_vert.GestisciAssegnazioni)
            {
                request.Intestazione.Assegnatari = factoryProtocollazione.GetAssegnatari();
            }

            var docPrincipale = allegatiAdapter.GetAllegatoPrincipale();

            if (docPrincipale != null)
            {
                request.Documenti = new DocumentiType { Documento = docPrincipale };
            }

            var allegati = allegatiAdapter.GetAllegati();

            if (allegati != null)
            {
                request.Documenti.Allegati = allegati;
            }

            return request;
        }
    }
}
