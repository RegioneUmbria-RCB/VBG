using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.DocEr.ProtocollazioneRegistrazione.MittentiDestinatari.Persone;
using Init.SIGePro.Protocollo.DocEr.GestioneDocumentale;
using Init.SIGePro.Protocollo.DocEr.Verticalizzazioni;

namespace Init.SIGePro.Protocollo.DocEr.ProtocollazioneRegistrazione.MittentiDestinatari
{
    public static class MittentiDestinatariExtensions
    {
        public static MittDestType ToMittDestTypeFromAnagrafica(this ProtocolloAnagrafe anagrafe, GestioneDocumentaleService wrapperGestDoc, VerticalizzazioniConfiguration vert)
        {
            AnagraficaDocErServiceManager.Gestisci(new AnagraficaVbg(anagrafe), wrapperGestDoc, vert);

            var persona = PersonaFisicaGiuridicaFactory.Create(anagrafe);
            var res = new MittDestType { Items = new object[] { persona.Persona } };

            if (!String.IsNullOrEmpty(anagrafe.Pec))
                res.IndirizzoTelematico = new IndirizzoTelematicoType { Text = new string[] { anagrafe.Pec }, tipo = IndirizzoTelematicoTypeTipo.smtp };

            if (!String.IsNullOrEmpty(anagrafe.INDIRIZZO))
                res.IndirizzoPostale = new IndirizzoPostaleType { Items = new object[] { new DenominazioneType { Text = new string[] { anagrafe.INDIRIZZO } } } };

            return res;
        }

        public static MittDestType ToMittDestTypeFromAmministrazione(this Amministrazioni amm, GestioneDocumentaleService wrapperGestDoc, VerticalizzazioniConfiguration vert)
        {
            AnagraficaDocErServiceManager.Gestisci(new AmministrazioneVbg(amm), wrapperGestDoc, vert);
            return ToMittDestTypeFromAmministrazione(amm);
        }

        public static MittDestType ToMittDestTypeFromAmministrazione(this Amministrazioni amm)
        {
            var personaGiuridica = new PersonaGiuridicaType
            {
                id = amm.PARTITAIVA,
                Denominazione = new DenominazioneType
                {
                    Text = new string[] { amm.AMMINISTRAZIONE }
                },
                tipo = PersonaGiuridicaConstants.CodiceFiscalePG
            };

            IndirizzoTelematicoType indirizzoTelematico = null;

            if (!String.IsNullOrEmpty(amm.PEC))
                indirizzoTelematico = new IndirizzoTelematicoType { Text = new string[] { amm.PEC }, tipo = IndirizzoTelematicoTypeTipo.smtp };

            if (indirizzoTelematico != null)
                personaGiuridica.IndirizzoTelematico = indirizzoTelematico;

            var res = new MittDestType { Items = new object[] { personaGiuridica } };

            if (!String.IsNullOrEmpty(amm.PEC))
                res.IndirizzoTelematico = new IndirizzoTelematicoType { Text = new string[] { amm.PEC }, tipo = IndirizzoTelematicoTypeTipo.smtp };

            if (!String.IsNullOrEmpty(amm.INDIRIZZO))
                res.IndirizzoPostale = new IndirizzoPostaleType { Items = new object[] { new DenominazioneType { Text = new string[] { amm.INDIRIZZO } } } };

            return res;

        }
    }
}
