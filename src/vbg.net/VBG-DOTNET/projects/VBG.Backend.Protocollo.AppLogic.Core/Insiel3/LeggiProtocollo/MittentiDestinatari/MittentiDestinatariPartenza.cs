using ProtocolloInsielService3;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel3.LeggiProtocollo.MittentiDestinatari
{
    public class MittentiDestinatariPartenza : ILeggiProtoMittentiDestinatari
    {
        Corrispondente[] _destinatari;

        public MittentiDestinatariPartenza(Corrispondente[] destinatari)
        {
            _destinatari = destinatari;
        }

        public string InCaricoA
        {
            get { return ""; }
        }

        public string InCaricoADescrizione
        {
            get { return ""; }
        }

        public MittDestOutType[] GetMittenteDestinatario()
        {
            return _destinatari.Select(x => new MittDestOutType { IdSoggetto = x.codAna, CognomeNome = x.descAna }).ToArray();
        }

        public string Flusso
        {
            get { return ProtocolloConstants.COD_PARTENZA; }
        }
    }
}
