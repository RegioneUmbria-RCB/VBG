using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.Urbi.LeggiProtocollo.MittentiDestinatari
{
    public class MittentiDestinatariPartenza : ILeggiProtoMittentiDestinatari
    {
        string _ufficio;
        IEnumerable<Corrispondente> _corrispondenti;

        public MittentiDestinatariPartenza(string ufficio, IEnumerable<Corrispondente> corrispondenti)
        {
            _ufficio = ufficio;
            _corrispondenti = corrispondenti;
        }

        public string InCaricoA
        {
            get { return ""; }
        }

        public string InCaricoADescrizione
        {
            get { return _ufficio; }
        }

        public MittDestOutType[] GetMittenteDestinatario()
        {
            return _corrispondenti.Select(x => new MittDestOutType
            {
                IdSoggetto = x.CodiceSoggetto,
                CognomeNome = x.TipoPersona == "G" ? String.Format("{0} {1}", x.CognomeDenominazione, x.CodiceFiscale) : String.Format("{0} {1} {2}", x.Nome, x.CognomeDenominazione, x.CodiceFiscale)
            }).ToArray();
        }

        public string Flusso
        {
            get { return ProtocolloConstants.COD_PARTENZA; }
        }
    }
}
