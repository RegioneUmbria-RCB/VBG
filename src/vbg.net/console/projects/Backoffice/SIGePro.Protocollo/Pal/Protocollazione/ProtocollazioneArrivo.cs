using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Pal.Protocollazione
{
    public class ProtocollazioneArrivo : IProtocollazione
    {
        IEnumerable<IAnagraficaAmministrazione> _mittenti;

        public ProtocollazioneArrivo(IEnumerable<IAnagraficaAmministrazione> mittenti)
        {
            _mittenti = mittenti;
        }

        public string Flusso => "A";

        public DestinatariType GetDestinatari()
        {
            return null;
        }

        public MittenteType[] GetMittenti()
        {
            var mittenti = _mittenti.Select(x => new MittenteType
            {
                Item = new MittenteEsternoType
                {
                    Denominazione = x.NomeCognome,
                    Indirizzo = x.Indirizzo,
                    Mail = x.Pec,
                    Tipo = x.ModalitaTrasmissione
                }
            });

            return mittenti.ToArray();
        }
    }
}
