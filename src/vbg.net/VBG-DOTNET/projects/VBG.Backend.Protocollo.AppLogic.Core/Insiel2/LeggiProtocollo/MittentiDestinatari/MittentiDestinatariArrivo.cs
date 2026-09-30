using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using ProtocolloInsielService2;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel2.LeggiProtocollo.MittentiDestinatari
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

        public MittDestOutType[] GetMittenteDestinatario()
        {
            return _mittenti.Select(x => new MittDestOutType { IdSoggetto = x.codUff, CognomeNome = x.descUff }).ToArray();
        }

        public string Flusso
        {
            get { return ProtocolloConstants.COD_ARRIVO; }
        }
    }
}
