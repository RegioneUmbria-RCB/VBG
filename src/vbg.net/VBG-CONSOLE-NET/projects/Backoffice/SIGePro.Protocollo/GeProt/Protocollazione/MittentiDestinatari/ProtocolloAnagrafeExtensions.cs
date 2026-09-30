using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;

namespace Init.SIGePro.Protocollo.GeProt.Protocollazione.MittentiDestinatari
{
    public static class ProtocolloAnagrafeExtensions
    {
        public static Denominazione ToDenominazione(this ProtocolloAnagrafe anagrafica) 
        {
            return new Denominazione { Text = new string[] { String.Concat(anagrafica.NOME, " ", anagrafica.NOMINATIVO).Trim() } };
        }

        public static Persona ToPersona(this ProtocolloAnagrafe anagrafica)
        {
            return new Persona
            {
                Items = new object[]
                { 
                    new Nome{ Text = new string[]{ anagrafica.NOME } },
                    new Cognome{ Text = new string[]{ anagrafica.NOMINATIVO } },
                    new Titolo { Text = new string[] { anagrafica.TITOLO } },
                    new CodiceFiscale{ Text = new string[]{ String.IsNullOrEmpty(anagrafica.CODICEFISCALE) ? anagrafica.PARTITAIVA : anagrafica.CODICEFISCALE } }
                }
            };
        }
    }
}
