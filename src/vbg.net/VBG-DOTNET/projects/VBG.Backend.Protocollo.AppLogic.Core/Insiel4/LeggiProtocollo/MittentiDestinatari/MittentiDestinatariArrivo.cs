using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;


namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.LeggiProtocollo.MittentiDestinatari
{
    public class MittentiDestinatariArrivo : ILeggiProtoMittentiDestinatari
    {
        Corrispondente[] _mittenti;
        Corrispondente _ufficioAssegnatario;

        public MittentiDestinatariArrivo(DettaglioProtocolloResponse response)
        {
            _mittenti = response.Mittenti;
            _ufficioAssegnatario = null;
            if (response.Uffici != null && response.Uffici.Length > 0)
                _ufficioAssegnatario = response.Uffici[0];

        }

        public string InCaricoA
        {
            get { return _ufficioAssegnatario != null ? _ufficioAssegnatario.CodiceAnagrafica : ""; }
        }

        public string InCaricoADescrizione
        {
            get { return _ufficioAssegnatario != null ? _ufficioAssegnatario.DescrizioneAnagrafica : ""; }
        }

        public MittDestOutType[] GetMittenteDestinatario()
        {
            return _mittenti.Select(x => new MittDestOutType { IdSoggetto = x.CodiceAnagrafica, CognomeNome = x.DescrizioneAnagrafica }).ToArray();
        }

        public string Flusso
        {
            get { return ProtocolloConstants.COD_ARRIVO; }
        }
    }
}
