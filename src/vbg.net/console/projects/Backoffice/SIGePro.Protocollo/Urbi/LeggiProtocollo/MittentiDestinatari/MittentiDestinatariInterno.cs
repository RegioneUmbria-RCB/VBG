using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.WsDataClass;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Urbi.LeggiProtocollo.MittentiDestinatari
{
    public class MittentiDestinatariInterno : ILeggiProtoMittentiDestinatari
    {
        string _ufficioMittente;
        string _ufficioDestinatario;

        public MittentiDestinatariInterno(string ufficioMittente, string ufficioDestinatario)
        {
            _ufficioMittente = ufficioMittente;
            _ufficioDestinatario = ufficioDestinatario;
        }

        public string InCaricoA
        {
            get { return ""; }
        }

        public string InCaricoADescrizione
        {
            get { return _ufficioMittente; }
        }

        public MittDestOut[] GetMittenteDestinatario()
        {
            return new MittDestOut[] { new MittDestOut{ CognomeNome = _ufficioDestinatario} };
        }

        public string Flusso
        {
            get { return ProtocolloConstants.COD_INTERNO; }
        }
    }
}
