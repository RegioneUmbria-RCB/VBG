using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;

namespace Init.SIGePro.Protocollo.DocEr.Pec.Persone
{
    public class PersonaGiuridica : IPersonaFisicaGiuridica
    {
        ProtocollazioneRegistrazione.PersonaGiuridicaType _pg;

        public PersonaGiuridica(ProtocollazioneRegistrazione.PersonaGiuridicaType pg)
        {
            _pg = pg;
        }

        public MittDestType GetDestinatario()
        {
            return new MittDestType
            {
                Items = new PersonaGiuridicaType[] 
                { 
                    new PersonaGiuridicaType
                    { 
                        Denominazione = new DenominazioneType{ Text = _pg.Denominazione.Text },
                        IndirizzoTelematico = new IndirizzoTelematicoType
                        {
                            tipo = IndirizzoTelematicoTypeTipo.smtp,
                            Text = _pg.IndirizzoTelematico.Text
                        },
                        ForzaIndirizzoTelematico = "1",
                        id = _pg.id,
                        tipo = _pg.tipo
                    }
                }
            };
        }
    }
}
