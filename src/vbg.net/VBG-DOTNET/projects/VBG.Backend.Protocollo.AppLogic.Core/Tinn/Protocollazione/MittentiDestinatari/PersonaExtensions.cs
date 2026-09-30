using VBG.Backend.Protocollo.AppLogic.Core.Tinn.Segnatura;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Core.Tinn.Protocollazione.MittentiDestinatari
{
    public static class PersonaExtensions
    {
        public static Persona GetPersonaAnagrafica(this ProtocolloAnagrafe anagrafica)
        {
            if (String.IsNullOrEmpty(anagrafica.CODICEFISCALE) && String.IsNullOrEmpty(anagrafica.PARTITAIVA))
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA VALIDAZIONE DELL'ANAGRAFICA, CODICE FISCALE / PARTITA IVA NON PRESENTE NELL'ANAGRAFICA CODICE {0}, NOMINATIVO {1}", anagrafica.CODICEANAGRAFE, String.Concat(anagrafica.NOME, " ", anagrafica.NOMINATIVO)));

            var codFiscalePartIva = anagrafica.CODICEFISCALE;

            if (anagrafica.TIPOANAGRAFE == ProtocolloConstants.COD_PERSONAGIURIDICA && String.IsNullOrEmpty(anagrafica.CODICEFISCALE))
                codFiscalePartIva = anagrafica.PARTITAIVA;

            return new Persona
            {
                CodiceFiscale = codFiscalePartIva,
                Cognome = anagrafica.TIPOANAGRAFE == ProtocolloConstants.COD_PERSONAFISICA ? anagrafica.NOMINATIVO : "",
                Denominazione = anagrafica.TIPOANAGRAFE == ProtocolloConstants.COD_PERSONAGIURIDICA ? anagrafica.NOMINATIVO : "",
                id = codFiscalePartIva,
                IndirizzoTelematico = new IndirizzoTelematico { Text = String.IsNullOrEmpty(anagrafica.PecProtocollazione) ? new string[] { anagrafica.EMAIL } : new string[] { anagrafica.PecProtocollazione } }
            };
        }

        public static Persona GetPersonaAmministrazione(this ProtocolloAmministrazioni amministrazione)
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
