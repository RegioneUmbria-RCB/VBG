using VBG.Backend.Protocollo.AppLogic.Core.PaDoc.Protocollazione.PersonaSegnatura;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Core.PaDoc.Protocollazione.Flusso
{
    public static class ProtocollazioneFlussoDestinatarioExtensions
    {
        public static Destinatario ToDestinatarioAmministrazione(this ProtocolloAmministrazioni amm)
        {
            var persona = new PersonaAmministrazione(amm);
            var res = new Destinatario
            {
                Items = new object[] { new Denominazione { Text = new string[] { amm.AMMINISTRAZIONE } } }
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

            if (!String.IsNullOrEmpty(anag.PecProtocollazione))
                res.IndirizzoTelematico = new IndirizzoTelematico
                {
                    tipo = IndirizzoTelematicoTipo.smtp,
                    Text = new string[] { anag.PecProtocollazione }
                };

            return res;
        }
    }
}
