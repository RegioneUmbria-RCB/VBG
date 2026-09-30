using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Core.Pal.Protocollazione
{
    public class ProtocollazioneInterna : IProtocollazionePal
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
