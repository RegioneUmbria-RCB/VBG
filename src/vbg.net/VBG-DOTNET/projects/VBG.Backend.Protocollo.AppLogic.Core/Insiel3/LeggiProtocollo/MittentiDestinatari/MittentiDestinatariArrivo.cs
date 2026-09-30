using ProtocolloInsielService3;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel3.LeggiProtocollo.MittentiDestinatari
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

        public MittDestOutType[] GetMittenteDestinatario()
        {
            return _mittenti.Select(x => new MittDestOutType { IdSoggetto = x.codAna, CognomeNome = x.descAna }).ToArray();
        }

        public string Flusso
        {
            get { return ProtocolloConstants.COD_ARRIVO; }
        }
    }
}
