using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.ProtocolloInsielService2;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.WsDataClass;
using Init.SIGePro.Protocollo.Constants;

namespace Init.SIGePro.Protocollo.Insiel2.LeggiProtocollo.MittentiDestinatari
{
    public class MittentiDestinatariInterno : ILeggiProtoMittentiDestinatari
    {
        Corrispondente[] _destinatari;

        public MittentiDestinatariInterno(Corrispondente[] destinatari)
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
            return _destinatari.Select(x => new MittDestOut { IdSoggetto = x.codUff, CognomeNome = x.descUff }).ToArray();
        }

        public string Flusso
        {
            get { return ProtocolloConstants.COD_INTERNO; }
        }
    }
}
