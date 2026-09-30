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
    public class MittentiDestinatariArrivo : ILeggiProtoMittentiDestinatari
    {
        Corrispondente[] _mittenti;
        Corrispondente _ufficio;

        public MittentiDestinatariArrivo(DettagliProtocollo response)
        {
            _mittenti = response.Mittenti;
            _ufficio = null;

            if (response.Uffici != null && response.Uffici.Count() > 0)
                _ufficio = response.Uffici[0];
        }

        public string InCaricoA
        {
            get { return _ufficio != null ? _ufficio.codUff : ""; }
        }

        public string InCaricoADescrizione
        {
            get { return _ufficio != null ? _ufficio.descUff : ""; }
        }

        public MittDestOut[] GetMittenteDestinatario()
        {
            return _mittenti.Select(x => new MittDestOut { IdSoggetto = x.codUff, CognomeNome = x.descUff }).ToArray();
        }

        public string Flusso
        {
            get { return ProtocolloConstants.COD_ARRIVO; }
        }
    }
}
