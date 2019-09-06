using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.WsDataClass;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.ApSystems.LeggiProtocollo
{
    public class LeggiProtocolloInterno : ILeggiProtoMittentiDestinatari
    {
        protocolli _response;

        public LeggiProtocolloInterno(protocolli response)
        {
            _response = response;
        }

        public string InCaricoA
        {
            get { return _response.mittente[0].codice; }
        }

        public string InCaricoADescrizione
        {
            get { return _response.mittente[0].descrizione; }
        }

        public MittDestOut[] GetMittenteDestinatario()
        {
            return _response.destinatario.Select(x => new MittDestOut
            {
                IdSoggetto = x.codice,
                CognomeNome = x.descrizione
            }).ToArray();
        }

        public string Flusso
        {
            get { return "I"; }
        }
    }
}