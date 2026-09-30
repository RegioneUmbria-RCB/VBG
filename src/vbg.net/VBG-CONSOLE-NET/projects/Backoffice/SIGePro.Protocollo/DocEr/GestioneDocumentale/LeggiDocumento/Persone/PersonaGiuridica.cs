using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.WsDataClass;
using Init.SIGePro.Protocollo.DocEr.ProtocollazioneRegistrazione;

namespace Init.SIGePro.Protocollo.DocEr.GestioneDocumentale.LeggiDocumento.Persone
{
    public class PersonaGiuridica : IPersonaFisicaGiuridica
    {
        PersonaGiuridicaType _personaGiuridica;

        public PersonaGiuridica(object personaGiuridica)
        {
            _personaGiuridica = (PersonaGiuridicaType)personaGiuridica;
        }

        public MittDestOut GetPersona()
        {
            return new MittDestOut
            {
                CognomeNome = _personaGiuridica.Denominazione.Text[0]
            };
        }
    }
}
