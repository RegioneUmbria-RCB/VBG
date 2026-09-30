using System.Collections.Generic;
using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Legacy.EGrammata2.LeggiProtocollo.SegnaturaResponse;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.EGrammata2.LeggiProtocollo
{
    public class MittentiDestinatariInterno : ILeggiProtoMittentiDestinatari
    {
        Documento _doc;

        public MittentiDestinatariInterno(Documento doc)
        {
            _doc = doc;
        }

        public string InCaricoA
        {
            get { return ""; }
        }

        public string InCaricoADescrizione
        {
            get { return _doc.Mittenti; }
        }

        public MittDestOutType[] GetMittenteDestinatario()
        {

            if(_doc.Destinatari.IndexOf(";") > -1)
            {
                var mittentiDestinatari = new List<MittDestOutType>();
                var destinatari = _doc.Destinatari.Split(';').ToList();
                destinatari.ForEach(x => mittentiDestinatari.Add(new MittDestOutType { CognomeNome = x }));
                return mittentiDestinatari.ToArray();
            }
            
            return new MittDestOutType[] { new MittDestOutType { CognomeNome = _doc.Destinatari } };
        }

        public string Flusso
        {
            get { return ProtocolloConstants.INTERNO; }
        }
    }
}
