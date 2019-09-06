using Init.SIGePro.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Halley.Builders
{
    public static class HalleySegnaturaPersoneExtensions
    {
        public static Mittente ToMittenteFromAnagrafica(this ProtocolloAnagrafe anagrafica)
        {
            string cfPiva = String.IsNullOrEmpty(anagrafica.CODICEFISCALE) ? anagrafica.PARTITAIVA : anagrafica.CODICEFISCALE;

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
                        IndirizzoTelematico = new IndirizzoTelematico { Text = new string[] { anagrafica.Pec } }
                    } 
                }
            };

            
        }

        public static Mittente ToMittenteFromAmministrazione(this Amministrazioni amministrazione)
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
            string cfPiva = String.IsNullOrEmpty(anagrafica.CODICEFISCALE) ? anagrafica.PARTITAIVA : anagrafica.CODICEFISCALE;

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
                        IndirizzoTelematico = new IndirizzoTelematico { Text = new string[] { anagrafica.Pec } }
                    } 
                }
            };


        }

        public static Destinatario ToDestinatarioFromAmministrazione(this Amministrazioni amministrazione)
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
