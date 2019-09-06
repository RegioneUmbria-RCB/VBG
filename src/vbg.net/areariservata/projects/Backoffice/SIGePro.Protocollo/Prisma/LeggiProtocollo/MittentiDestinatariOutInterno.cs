using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.WsDataClass;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Prisma.LeggiProtocollo
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

        public MittDestOut[] GetMittenteDestinatario()
        {
            return new MittDestOut[] { new MittDestOut { CognomeNome = " - " } };
        }
    }
}
