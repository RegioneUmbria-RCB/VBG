using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.EGrammata2.LeggiProtocollo.SegnaturaResponse;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo.EGrammata2.LeggiProtocollo
{
    public class MittentiDestinatariArrivo : ILeggiProtoMittentiDestinatari
    {
        Documento _doc;

        public MittentiDestinatariArrivo(Documento doc)
        {
            _doc = doc;    
        }

        public string InCaricoA
        {
            get { return ""; }
        }

        public string InCaricoADescrizione
        {
            get { return _doc.Destinatari; }
        }

        public MittDestOut[] GetMittenteDestinatario()
        {
            if (_doc.Mittenti.IndexOf(";") > -1)
            {
                var mittentiDestinatari = new List<MittDestOut>();
                var destinatari = _doc.Mittenti.Split(';').ToList();
                destinatari.ForEach(x => mittentiDestinatari.Add(new MittDestOut { CognomeNome = x }));
                return mittentiDestinatari.ToArray();
            }

            return new MittDestOut[] { new MittDestOut { CognomeNome = _doc.Mittenti } };

        }

        public string Flusso
        {
            get { return ProtocolloConstants.COD_ARRIVO; }
        }
    }
}
