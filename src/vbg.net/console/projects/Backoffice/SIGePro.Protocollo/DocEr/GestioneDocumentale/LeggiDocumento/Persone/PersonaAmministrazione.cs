using Init.SIGePro.Protocollo.DocEr.ProtocollazioneRegistrazione;
using Init.SIGePro.Protocollo.WsDataClass;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.DocEr.GestioneDocumentale.LeggiDocumento.Persone
{
    public class PersonaAmministrazione : IPersonaFisicaGiuridica
    {
        AmministrazioneType _personaAmministrazione;

        public PersonaAmministrazione(object personaGiuridica)
        {
            _personaAmministrazione = (AmministrazioneType)personaGiuridica;
        }

        public MittDestOut GetPersona()
        {
            return new MittDestOut { CognomeNome = _personaAmministrazione.Denominazione.Text[0] };
        }
    }
}
