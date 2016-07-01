using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;

namespace Init.SIGePro.Protocollo.DocEr.Pec.Persone
{
    public class PersonaGiuridica : IPersonaFisicaGiuridica
    {
        ProtocolloAnagrafe _anagrafica;
        string _id;

        public PersonaGiuridica(ProtocolloAnagrafe anagrafica)
        {
            _anagrafica = anagrafica;
            _id = !String.IsNullOrEmpty(_anagrafica.CODICEFISCALE) ? _anagrafica.CODICEFISCALE : _anagrafica.PARTITAIVA;

            if (String.IsNullOrEmpty(_id))
                throw new Exception(String.Format("CODICE FISCALE O PARTITA IVA DELLA PERSONA GIURIDICA {0}, CODICE {1}, NON VALORIZZATO", _anagrafica.GetNomeCompleto(), _anagrafica.CODICEANAGRAFE));
        }

        public object Persona
        {
            get 
            {
                return new PersonaGiuridicaType 
                { 
                    Denominazione = new DenominazioneType 
                    { 
                        Text = new string[] { _anagrafica.NOMINATIVO } 
                    } ,
                    id = _id,
                    tipo = PersonaGiuridicaConstants.CodiceFiscalePG,
                    IndirizzoTelematico = new IndirizzoTelematicoType { tipo = IndirizzoTelematicoTypeTipo.smtp, Text = new string[] { _anagrafica.Pec } }
                };
            }
        }
    }
}
