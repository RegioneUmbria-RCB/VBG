using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Pal.Protocollazione
{
    public class ProtocollazioneInterna : IProtocollazione
    {
        string _uoMittente;
        string _uoDestinatario;

        public ProtocollazioneInterna(string uoMittente, string uoDestinatario)
        {
            this._uoMittente = uoMittente;
            this._uoDestinatario = uoDestinatario;
        }

        public string Flusso => "I";

        public AssegnatariType GetAssegnatari()
        {
            return new AssegnatariType
            {
                Assegnatario = new AssegnatarioType
                {
                    Tipo = "P",
                    Item = new SettoreType
                    {
                        Organigramma = this._uoDestinatario
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
