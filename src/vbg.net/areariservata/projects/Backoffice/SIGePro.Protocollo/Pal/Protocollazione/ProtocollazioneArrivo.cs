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
        string _uo;

        public ProtocollazioneArrivo(IEnumerable<IAnagraficaAmministrazione> mittenti, string uo)
        {
            this._mittenti = mittenti;
            this._uo = uo;
        }

        public string Flusso => "A";

        public AssegnatariType GetAssegnatari()
        {
            return new AssegnatariType
            {
                Assegnatario = new AssegnatarioType
                {
                    Tipo = "P",
                    Item = new SettoreType
                    {
                        Organigramma = this._uo
                    }
                }
            };
        }

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
