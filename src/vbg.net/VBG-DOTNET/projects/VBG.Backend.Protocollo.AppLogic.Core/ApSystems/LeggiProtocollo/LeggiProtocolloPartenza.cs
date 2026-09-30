using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.ApSystems.LeggiProtocollo
{
    public class LeggiProtocolloPartenza : ILeggiProtoMittentiDestinatari
    {
        protocolli _response;

        public LeggiProtocolloPartenza(protocolli response)
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

        public MittDestOutType[] GetMittenteDestinatario()
        {
            return _response.destinatario.Select(x => new MittDestOutType
            {
                IdSoggetto = x.codice,
                CognomeNome = x.descrizione
            }).ToArray();
        }

        public string Flusso
        {
            get { return "P"; }
        }
    }
}
