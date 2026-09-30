using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;

namespace VBG.Backend.Protocollo.AppLogic.Core.PaDoc.Protocollazione.PersonaSegnatura
{
    public class PersonaFactory
    {
        public static IPersona Create(IDatiProtocollo datiProto)
        {
            if (datiProto.AmministrazioniEsterne.Count > 0)
                return new PersonaAmministrazione(datiProto.AmministrazioniEsterne[0]);
            else if (datiProto.AnagraficheProtocollo.Count > 0)
                return new PersonaAnagrafica(datiProto.AnagraficheProtocollo[0]);
            else
                throw new Exception("NON E' STATO TROVATO NE UNA AMMINISTRAZIONE NE UNA ANAGRAFICA NEL MITTENTE / DESTINATARIO");
        }
    }
}
