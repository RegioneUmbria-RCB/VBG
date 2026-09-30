using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.WsDataClass;
using Init.SIGePro.Protocollo.DocEr.ProtocollazioneRegistrazione;

namespace Init.SIGePro.Protocollo.DocEr.GestioneDocumentale.LeggiDocumento.Persone
{
    public class PersonaFisica : IPersonaFisicaGiuridica
    {
        PersonaType _persona;

        public PersonaFisica(object persona)
        {
            _persona = (PersonaType)persona;
        }

        public MittDestOut GetPersona()
        {
            if (_persona.Denominazione != null)
                return new MittDestOut { CognomeNome = _persona.Denominazione.Text[0] };

            string cognomeNome = "";

            if(_persona.Cognome != null)
                cognomeNome = _persona.Cognome.Text[0];

            if (_persona.Nome != null)
                cognomeNome = String.Format("{0} {1}", _persona.Nome.Text[0], _persona.Cognome.Text[0]);

            return new MittDestOut
            {
                CognomeNome = cognomeNome
            };
        }
    }
}
