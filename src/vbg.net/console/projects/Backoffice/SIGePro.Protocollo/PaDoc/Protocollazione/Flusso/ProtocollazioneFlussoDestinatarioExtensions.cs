using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.PaDoc.Protocollazione.PersonaSegnatura;

namespace Init.SIGePro.Protocollo.PaDoc.Protocollazione.Flusso
{
    public static class ProtocollazioneFlussoDestinatarioExtensions
    {
        public static Destinatario ToDestinatarioAmministrazione(this Amministrazioni amm)
        {
            var persona = new PersonaAmministrazione(amm);
            var res = new Destinatario
            {
                Items = new object[]{ new Denominazione{ Text = new string[]{ amm.AMMINISTRAZIONE } } }
                //Items = persona.GetPersona(),
                //IndirizzoPostale = persona.GetIndirizzoPostale()
            };

            if (!String.IsNullOrEmpty(amm.PEC))
                res.IndirizzoTelematico = new IndirizzoTelematico
                {
                    tipo = IndirizzoTelematicoTipo.smtp,
                    Text = new string[] { amm.PEC }
                };

            return res;
        }

        public static Destinatario ToDestinatarioAnagrafica(this ProtocolloAnagrafe anag)
        {
            var persona = new PersonaAnagrafica(anag);
            var res = new Destinatario
            {
                Items = new object[] { new Denominazione { Text = new string[] { anag.GetNomeCompleto() } } }
                //Items = new Denominazione[]{ new Persona { Items = persona.GetPersona() } },
                //IndirizzoPostale = persona.GetIndirizzoPostale()
            };

            if (!String.IsNullOrEmpty(anag.Pec))
                res.IndirizzoTelematico = new IndirizzoTelematico
                {
                    tipo = IndirizzoTelematicoTipo.smtp,
                    Text = new string[] { anag.Pec }
                };

            return res;
        }
    }
}
