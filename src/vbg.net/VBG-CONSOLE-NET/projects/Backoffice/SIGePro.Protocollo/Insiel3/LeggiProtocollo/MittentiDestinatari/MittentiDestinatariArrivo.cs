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
    public class MittentiDestinatariArrivo : ILeggiProtoMittentiDestinatari
    {
        Corrispondente[] _mittenti;
        Corrispondente _ufficioAssegnatario;

        public MittentiDestinatariArrivo(DettagliProtocollo response)
        {
            _mittenti = response.mittenti;
            _ufficioAssegnatario = null;
            if (response.uffici != null && response.uffici.Count() > 0)
                _ufficioAssegnatario = response.uffici[0];

        }

        public string InCaricoA
        {
            get { return _ufficioAssegnatario != null ? _ufficioAssegnatario.codAna : ""; }
        }

        public string InCaricoADescrizione
        {
            get { return _ufficioAssegnatario != null ? _ufficioAssegnatario.descAna : ""; }
        }

        public MittDestOut[] GetMittenteDestinatario()
        {
            return _mittenti.Select(x => new MittDestOut { IdSoggetto = x.codAna, CognomeNome = x.descAna }).ToArray();
        }

        public string Flusso
        {
            get { return ProtocolloConstants.COD_ARRIVO; }
        }
    }
}
