using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Core.DocEr.ProtocollazioneRegistrazione.MittentiDestinatari.Persone
{
    public class PersonaFisica : IPersonaFisicaGiuridica
    {
        ProtocolloAnagrafe _anagrafica;
        string _id;

        public PersonaFisica(ProtocolloAnagrafe anagrafica)
        {
            _anagrafica = anagrafica;
            if (String.IsNullOrEmpty(anagrafica.CODICEFISCALE))
                throw new Exception(String.Format("CODICE FISCALE DELLA PERSONA FISICA {0}, CODICE {1}, NON VALORIZZATO", _anagrafica.GetNomeCompleto(), _anagrafica.CODICEANAGRAFE));
        }

        public object Persona
        {
            get 
            {
                return new PersonaType
                {
                    CodiceFiscale = new CodiceFiscaleType { Text = new string[] { _anagrafica.CODICEFISCALE } },
                    Cognome = new CognomeType { Text = new string[] { _anagrafica.NOMINATIVO } },
                    Nome = new NomeType { Text = new string[] { _anagrafica.NOME } },
                    id = _anagrafica.CODICEFISCALE,
                    IndirizzoTelematico = new IndirizzoTelematicoType { Text = new string[] { _anagrafica.PecProtocollazione }, tipo = IndirizzoTelematicoTypeTipo.smtp }
                };
            }
        }
    }
}
