using System;
using VBG.Backend.Protocollo.AppLogic.Legacy.EGrammata2.LeggiProtocollo.SegnaturaResponse;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.EGrammata2.LeggiProtocollo
{
    public class MittentiDestinatariPartenza : ILeggiProtoMittentiDestinatari
    {
        Documento _doc;

        public MittentiDestinatariPartenza(Documento doc)
        {
            _doc = doc;
        }

        public string InCaricoA
        {
            get { throw new NotImplementedException(); }
        }

        public string InCaricoADescrizione
        {
            get { throw new NotImplementedException(); }
        }

        public MittDestOutType[] GetMittenteDestinatario()
        {
            throw new NotImplementedException();
        }

        public string Flusso
        {
            get { return ProtocolloConstants.COD_PARTENZA; }
        }
    }
}
