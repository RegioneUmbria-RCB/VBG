using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Tinn.Segnatura;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.Constants;

namespace Init.SIGePro.Protocollo.Tinn.Protocollazione.MittentiDestinatari
{
    public static class PersonaExtensions
    {
        public static Persona GetPersonaAnagrafica(this ProtocolloAnagrafe anagrafica)
        {
            if (String.IsNullOrEmpty(anagrafica.CODICEFISCALE) && String.IsNullOrEmpty(anagrafica.PARTITAIVA))
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA VALIDAZIONE DELL'ANAGRAFICA, CODICE FISCALE / PARTITA IVA NON PRESENTE NELL'ANAGRAFICA CODICE {0}, NOMINATIVO {1}", anagrafica.CODICEANAGRAFE, String.Concat(anagrafica.NOME, " ", anagrafica.NOMINATIVO)));

            string codFiscalePartIva = anagrafica.CODICEFISCALE;

            if (anagrafica.TIPOANAGRAFE == ProtocolloConstants.COD_PERSONAGIURIDICA && String.IsNullOrEmpty(anagrafica.CODICEFISCALE))
                codFiscalePartIva = anagrafica.PARTITAIVA;

            return new Persona
            {
                CodiceFiscale = codFiscalePartIva,
                Cognome = anagrafica.TIPOANAGRAFE == ProtocolloConstants.COD_PERSONAFISICA ? anagrafica.NOMINATIVO : "",
                Denominazione = anagrafica.TIPOANAGRAFE == ProtocolloConstants.COD_PERSONAGIURIDICA ? anagrafica.NOMINATIVO : "",
                id = codFiscalePartIva,
                IndirizzoTelematico = new IndirizzoTelematico { Text = String.IsNullOrEmpty(anagrafica.Pec) ? new string[] { anagrafica.EMAIL } : new string[] { anagrafica.Pec } }
            };
        }

        public static Persona GetPersonaAmministrazione(this Amministrazioni amministrazione)
        {
            if (String.IsNullOrEmpty(amministrazione.PARTITAIVA))
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA VALIDAZIONE DELL'AMMINISTRAZIONE, PARTITA IVA NON PRESENTE NELL'AMMINISTRAZIONE CODICE {0}, AMMINISTRAZIONE {1}", amministrazione.CODICEAMMINISTRAZIONE, amministrazione.AMMINISTRAZIONE));
            
            return new Persona
            {
                CodiceFiscale = amministrazione.PARTITAIVA,
                Denominazione = amministrazione.AMMINISTRAZIONE,
                id = amministrazione.PARTITAIVA,
                IndirizzoTelematico = new IndirizzoTelematico { Text = String.IsNullOrEmpty(amministrazione.PEC) ? new string[] { amministrazione.EMAIL } : new string[] { amministrazione.PEC } }
            };
        }
    }
}
