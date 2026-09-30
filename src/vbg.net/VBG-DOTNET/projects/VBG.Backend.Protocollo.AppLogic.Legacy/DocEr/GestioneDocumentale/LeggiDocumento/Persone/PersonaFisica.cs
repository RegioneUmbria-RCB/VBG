using System;
using VBG.Backend.Protocollo.AppLogic.Legacy.DocEr.ProtocollazioneRegistrazione;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.DocEr.GestioneDocumentale.LeggiDocumento.Persone
{
    public class PersonaFisica : IPersonaFisicaGiuridica
    {
        PersonaType _persona;

        public PersonaFisica(object persona)
        {
            _persona = (PersonaType)persona;
        }

        public MittDestOutType GetPersona()
        {
            if (_persona.Denominazione != null)
                return new MittDestOutType { CognomeNome = _persona.Denominazione.Text[0] };

            string cognomeNome = "";

            if(_persona.Cognome != null)
                cognomeNome = _persona.Cognome.Text[0];

            if (_persona.Nome != null)
                cognomeNome = String.Format("{0} {1}", _persona.Nome.Text[0], _persona.Cognome.Text[0]);

            return new MittDestOutType
            {
                CognomeNome = cognomeNome
            };
        }
    }
}
