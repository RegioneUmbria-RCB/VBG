
using System.Collections.Generic;
using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.Prisma.LeggiProtocollo
{
    public class MittentiDestinatariOutInterno : ILeggiProtoMittentiDestinatari
    {

        IEnumerable<SmistamentoOutXml> _smistamenti;

        public MittentiDestinatariOutInterno(IEnumerable<SmistamentoOutXml> smistamenti)
        {
            this._smistamenti = smistamenti;
        }

        public string InCaricoA { get { return this._smistamenti == null || this._smistamenti.Count() == 0 ? "" : this._smistamenti.Last().UfficioTrasmissione; } }

        public string InCaricoADescrizione { get { return this._smistamenti == null || this._smistamenti.Count() == 0 ? "" : this._smistamenti.Last().DescrizioneUfficioTrasmissione; } }

        public string Flusso { get { return ProtocolloConstants.COD_INTERNO; } }

        public MittDestOutType[] GetMittenteDestinatario()
        {
            return new MittDestOutType[] { new MittDestOutType { CognomeNome = " - " } };
        }
    }
}
