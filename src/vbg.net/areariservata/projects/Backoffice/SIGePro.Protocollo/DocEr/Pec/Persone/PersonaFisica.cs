using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;

namespace Init.SIGePro.Protocollo.DocEr.Pec.Persone
{
    public class PersonaFisica : IPersonaFisicaGiuridica
    {
        ProtocollazioneRegistrazione.PersonaType _pf;

        public PersonaFisica(ProtocollazioneRegistrazione.PersonaType pf)
        {
            _pf = pf;
        }

        public MittDestType GetDestinatario()
        {
            return new MittDestType
            {
                Items = new PersonaType[] 
                { 
                    new PersonaType
                    { 
                        Nome = new NomeType{ Text = _pf.Nome.Text },
                        Cognome = new CognomeType{ Text = _pf.Cognome.Text },
                        IndirizzoTelematico = new IndirizzoTelematicoType
                        {
                            tipo = IndirizzoTelematicoTypeTipo.smtp,
                            Text = _pf.IndirizzoTelematico.Text
                        },
                        id = _pf.id,
                    }
                }
            };
        }
    }
}
