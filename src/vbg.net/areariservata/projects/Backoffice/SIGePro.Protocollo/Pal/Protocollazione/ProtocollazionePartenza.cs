using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Pal.Protocollazione
{
    public class ProtocollazionePartenza : IProtocollazione
    {
        IEnumerable<IAnagraficaAmministrazione> _destinatari;
        string _uoMittente;

        public ProtocollazionePartenza(IEnumerable<IAnagraficaAmministrazione> destinatari, string uoMittente)
        {
            _destinatari = destinatari;
            _uoMittente = uoMittente;
        }

        public string Flusso => "P";

        public AssegnatariType GetAssegnatari()
        {
            return new AssegnatariType
            {
                Assegnatario = new AssegnatarioType
                {
                    Tipo = "P",
                    Item = new SettoreType
                    {
                        Organigramma = this._uoMittente
                    }
                }
            };
        }

        public DestinatariType GetDestinatari()
        {
            var destinatari = _destinatari.Select(x => new DestinatarioType
            {
                Denominazione = x.NomeCognome,
                Indirizzo = String.IsNullOrEmpty(x.Indirizzo) ? "-" : x.Indirizzo,
                Mail = x.Pec,
                Tipo = x.ModalitaTrasmissione,
                TipoPosta = x.MezzoInvio
            });

            return new DestinatariType { Destinatario = destinatari.ToArray() };
        }

        public MittenteType[] GetMittenti()
        {
            return new MittenteType[]
            {
                new MittenteType
                {
                    Item = new MittenteInternoType
                    {
                        Item = new SettoreType { Organigramma = _uoMittente }
                    }
                }
            };
        }
    }
}
