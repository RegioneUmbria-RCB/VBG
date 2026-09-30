

using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.StudioK.LeggiProtocollo
{
    public class LeggiProtocolloArrivo : ILeggiProtoMittentiDestinatari
    {
        Persona _mittente;
        Amministrazione _destinatario;

        public LeggiProtocolloArrivo(Persona mittente, Amministrazione destinatario)
        {
            _mittente = mittente;
            _destinatario = destinatario;
        }

        public string InCaricoA
        {
            get 
            {
                return _destinatario.CodiceAmministrazione;
            }
        }

        public string InCaricoADescrizione
        {
            get { return _destinatario.Denominazione; }
        }

        public MittDestOutType[] GetMittenteDestinatario()
        {
            return new MittDestOutType[] { new MittDestOutType { CognomeNome = _mittente.Denominazione } };
        }

        public string Flusso
        {
            get { return ProtocolloConstants.COD_ARRIVO; }
        }
    }
}
