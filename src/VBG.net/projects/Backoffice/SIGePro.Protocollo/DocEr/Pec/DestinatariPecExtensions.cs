using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.DocEr.Pec.Persone;

namespace Init.SIGePro.Protocollo.DocEr.Pec
{
    public static class DestinatariPecExtensions
    {
        public static MittDestType ToMittDestTypeFromAnagrafica(this ProtocolloAnagrafe anagrafe)
        {
            var persona = PersonaFisicaGiuridicaFactory.Create(anagrafe);
            var res = new MittDestType { Items = new object[] { persona.Persona } };

            if (!String.IsNullOrEmpty(anagrafe.Pec))
                res.IndirizzoTelematico = new IndirizzoTelematicoType { Text = new string[] { anagrafe.Pec } };

            if (!String.IsNullOrEmpty(anagrafe.INDIRIZZO))
                res.IndirizzoPostale = new IndirizzoPostaleType { Items = new object[] { new DenominazioneType { Text = new string[] { anagrafe.INDIRIZZO } } } };

            return res;
        }

        public static MittDestType ToMittDestTypeFromAmministrazione(this Amministrazioni amm)
        {
            var res = new MittDestType
            {
                Items = new object[] 
                { 
                    new PersonaGiuridicaType 
                    { 
                        id = amm.PARTITAIVA, 
                        Denominazione = new DenominazioneType 
                        { 
                            Text = new string[] { amm.AMMINISTRAZIONE } 
                        },
                        tipo = PersonaGiuridicaConstants.CodiceFiscalePG,
                        IndirizzoTelematico = new IndirizzoTelematicoType{ Text = new string[]{ amm.PEC }, tipo = IndirizzoTelematicoTypeTipo.smtp }
                    } 
                }
            };

            if (!String.IsNullOrEmpty(amm.PEC))
                res.IndirizzoTelematico = new IndirizzoTelematicoType { Text = new string[] { amm.PEC } };

            if (!String.IsNullOrEmpty(amm.INDIRIZZO))
                res.IndirizzoPostale = new IndirizzoPostaleType { Items = new object[] { new DenominazioneType { Text = new string[] { amm.INDIRIZZO } } } };

            return res;
        }
    }
}
