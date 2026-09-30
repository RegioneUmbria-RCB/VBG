using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.ProtocolloInsielService3;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.WsDataClass;
using Init.SIGePro.Protocollo.Constants;

namespace Init.SIGePro.Protocollo.Insiel3.LeggiProtocollo.MittentiDestinatari
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

        public MittDestOut[] GetMittenteDestinatario()
        {
            return _destinatari.Select(x => new MittDestOut { IdSoggetto = x.codAna, CognomeNome = x.descAna }).ToArray();
        }

        public string Flusso
        {
            get { return ProtocolloConstants.COD_PARTENZA; }
        }
    }
}
