using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Legacy.DocEr.ProtocollazioneRegistrazione;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.DocEr.GestioneDocumentale.LeggiDocumento.Persone
{
    public class PersonaAmministrazione : IPersonaFisicaGiuridica
    {
        AmministrazioneType _personaAmministrazione;

        public PersonaAmministrazione(object personaGiuridica)
        {
            _personaAmministrazione = (AmministrazioneType)personaGiuridica;
        }

        public MittDestOutType GetPersona()
        {
            if (_personaAmministrazione?.Denominazione?.Text == null || !_personaAmministrazione.Denominazione.Text.Any())
            {
                return new MittDestOutType { CognomeNome = _personaAmministrazione?.CodiceAmministrazione?.Text[0] };
            }

            return new MittDestOutType { CognomeNome = _personaAmministrazione.Denominazione.Text[0] };
        }
    }
}
