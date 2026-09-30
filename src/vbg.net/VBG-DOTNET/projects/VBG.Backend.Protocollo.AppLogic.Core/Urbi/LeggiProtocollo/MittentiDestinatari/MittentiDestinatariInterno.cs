using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.Urbi.LeggiProtocollo.MittentiDestinatari
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

        public MittDestOutType[] GetMittenteDestinatario()
        {
            return new MittDestOutType[] { new MittDestOutType{ CognomeNome = _ufficioDestinatario} };
        }

        public string Flusso
        {
            get { return ProtocolloConstants.COD_INTERNO; }
        }
    }
}
