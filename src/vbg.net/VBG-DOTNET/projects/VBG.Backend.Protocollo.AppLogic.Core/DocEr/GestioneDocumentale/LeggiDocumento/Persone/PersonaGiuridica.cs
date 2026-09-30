using VBG.Backend.Protocollo.AppLogic.Core.DocEr.ProtocollazioneRegistrazione;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.DocEr.GestioneDocumentale.LeggiDocumento.Persone
{
    public class PersonaGiuridica : IPersonaFisicaGiuridica
    {
        PersonaGiuridicaType _personaGiuridica;

        public PersonaGiuridica(object personaGiuridica)
        {
            _personaGiuridica = (PersonaGiuridicaType)personaGiuridica;
        }

        public MittDestOutType GetPersona()
        {
            return new MittDestOutType
            {
                CognomeNome = _personaGiuridica.Denominazione.Text[0]
            };
        }
    }
}
