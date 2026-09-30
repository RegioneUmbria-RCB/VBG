using System;
using VBG.Backend.Protocollo.AppLogic.Legacy.DocEr.GestioneDocumentale;
using VBG.Backend.Protocollo.AppLogic.Legacy.DocEr.ProtocollazioneRegistrazione.MittentiDestinatari.Persone;
using VBG.Backend.Protocollo.AppLogic.Legacy.DocEr.Verticalizzazioni;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.DocEr.ProtocollazioneRegistrazione.MittentiDestinatari
{
    public static class MittentiDestinatariExtensions
    {
        public static MittDestType ToMittDestTypeFromAnagrafica(this ProtocolloAnagrafe anagrafe, GestioneDocumentaleService wrapperGestDoc, VerticalizzazioniConfiguration vert)
        {
            AnagraficaDocErServiceManager.Gestisci(new AnagraficaVbg(anagrafe), wrapperGestDoc, vert);

            var persona = PersonaFisicaGiuridicaFactory.Create(anagrafe);
            var res = new MittDestType { Items = new object[] { persona.Persona } };

            if (!String.IsNullOrEmpty(anagrafe.PecProtocollazione))
                res.IndirizzoTelematico = new IndirizzoTelematicoType { Text = new string[] { anagrafe.PecProtocollazione }, tipo = IndirizzoTelematicoTypeTipo.smtp };

            if (!String.IsNullOrEmpty(anagrafe.INDIRIZZO))
                res.IndirizzoPostale = new IndirizzoPostaleType { Items = new object[] { new DenominazioneType { Text = new string[] { anagrafe.INDIRIZZO } } } };

            return res;
        }

        public static MittDestType ToMittDestTypeFromAmministrazione(this ProtocolloAmministrazioni amm, GestioneDocumentaleService wrapperGestDoc, VerticalizzazioniConfiguration vert)
        {
            AnagraficaDocErServiceManager.Gestisci(new AmministrazioneVbg(amm), wrapperGestDoc, vert);
            return ToMittDestTypeFromAmministrazione(amm);
        }

        public static MittDestType ToMittDestTypeFromAmministrazione(this ProtocolloAmministrazioni amm)
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
