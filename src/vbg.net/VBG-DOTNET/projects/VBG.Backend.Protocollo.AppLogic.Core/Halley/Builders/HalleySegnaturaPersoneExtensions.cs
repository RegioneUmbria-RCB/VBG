using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Core.Halley.Builders
{
    public static class HalleySegnaturaPersoneExtensions
    {
        public static Mittente ToMittenteFromAnagrafica(this ProtocolloAnagrafe anagrafica)
        {
            var cfPiva = String.IsNullOrEmpty(anagrafica.CODICEFISCALE) ? anagrafica.PARTITAIVA : anagrafica.CODICEFISCALE;

            if (String.IsNullOrEmpty(cfPiva))
                throw new Exception(String.Format("I CAMPI PARTITA IVA O CODICE FISCALE DELL'ANAGRAFICA CODICE {0} DESCRIZIONE {1} NON SONO VALORIZZATI, DEVE ESSERE VALORIZZATO ALMENO UNO DEI DUE", anagrafica.CODICEANAGRAFE, anagrafica.NOMINATIVO));

            return new Mittente
            {
                Items = new Object[]
                {
                    new Persona
                    {
                        id = cfPiva,
                        CodiceFiscale = cfPiva,
                        Nome = anagrafica.NOME,
                        Cognome = anagrafica.NOMINATIVO,
                        IndirizzoTelematico = new IndirizzoTelematico { Text = new string[] { anagrafica.PecProtocollazione } }
                    }
                }
            };


        }

        public static Mittente ToMittenteFromAmministrazione(this ProtocolloAmministrazioni amministrazione)
        {
            if (String.IsNullOrEmpty(amministrazione.PARTITAIVA))
                throw new Exception(String.Format("IL CAMPO PARTITA IVA DELL'AMMINISTRAZIONE CODICE {0} DESCRIZIONE {1} NON E' VALORIZZATO", amministrazione.CODICEAMMINISTRAZIONE, amministrazione.AMMINISTRAZIONE));

            return new Mittente
            {
                Items = new Object[]
                {
                    new Persona
                    {
                        id = amministrazione.PARTITAIVA,
                        CodiceFiscale = amministrazione.PARTITAIVA,
                        Cognome = amministrazione.AMMINISTRAZIONE,
                        IndirizzoTelematico = new IndirizzoTelematico { Text = new string[] { amministrazione.PEC } }
                    }
                }
            };
        }

        public static Destinatario ToDestinatarioFromAnagrafica(this ProtocolloAnagrafe anagrafica)
        {
            var cfPiva = String.IsNullOrEmpty(anagrafica.CODICEFISCALE) ? anagrafica.PARTITAIVA : anagrafica.CODICEFISCALE;

            if (String.IsNullOrEmpty(cfPiva))
                throw new Exception(String.Format("I CAMPI PARTITA IVA O CODICE FISCALE DELL'ANAGRAFICA CODICE {0} DESCRIZIONE {1} NON SONO VALORIZZATI, DEVE ESSERE VALORIZZATO ALMENO UNO DEI DUE", anagrafica.CODICEANAGRAFE, anagrafica.NOMINATIVO));

            return new Destinatario
            {
                Items = new Object[]
                {
                    new Persona
                    {
                        id = cfPiva,
                        CodiceFiscale = cfPiva,
                        Nome = anagrafica.NOME,
                        Cognome = anagrafica.NOMINATIVO,
                        IndirizzoTelematico = new IndirizzoTelematico { Text = new string[] { anagrafica.PecProtocollazione } }
                    }
                }
            };


        }

        public static Destinatario ToDestinatarioFromAmministrazione(this ProtocolloAmministrazioni amministrazione)
        {
            if (String.IsNullOrEmpty(amministrazione.PARTITAIVA))
                throw new Exception(String.Format("IL CAMPO PARTITA IVA DELL'AMMINISTRAZIONE CODICE {0} DESCRIZIONE {1} NON E' VALORIZZATO", amministrazione.CODICEAMMINISTRAZIONE, amministrazione.AMMINISTRAZIONE));

            return new Destinatario
            {
                Items = new Object[]
                {
                    new Persona
                    {
                        id = amministrazione.PARTITAIVA,
                        CodiceFiscale = amministrazione.PARTITAIVA,
                        Cognome = amministrazione.AMMINISTRAZIONE,
                        IndirizzoTelematico = new IndirizzoTelematico { Text = new string[] { amministrazione.PEC } }
                    }
                }
            };
        }
    }
}
