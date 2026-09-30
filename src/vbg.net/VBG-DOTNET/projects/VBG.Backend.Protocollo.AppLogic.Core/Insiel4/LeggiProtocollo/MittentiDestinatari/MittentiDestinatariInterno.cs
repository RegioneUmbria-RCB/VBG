using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;


namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.LeggiProtocollo.MittentiDestinatari
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

        public MittDestOutType[] GetMittenteDestinatario()
        {
            return _destinatari.Select(x => new MittDestOutType { IdSoggetto = x.CodiceAnagrafica, CognomeNome = x.DescrizioneAnagrafica }).ToArray();
        }

        public string Flusso
        {
            get { return ProtocolloConstants.COD_INTERNO; }
        }
    }
}
