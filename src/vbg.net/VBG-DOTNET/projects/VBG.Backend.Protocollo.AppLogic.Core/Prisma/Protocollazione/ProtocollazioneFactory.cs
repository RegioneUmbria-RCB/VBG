using System;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;

namespace VBG.Backend.Protocollo.AppLogic.Core.Prisma.Protocollazione
{
    public class ProtocollazioneFactory
    {
        public static IProtocollazionePrisma Create(ProtocollazioneInfo info)
        {
            if (info.Flusso == ProtocolloConstants.COD_ARRIVO)
            {
                return new ProtocollazioneArrivo(info.Anagrafiche, info.CodiceUo, info.CodiceRuolo);
            }
            else if (info.Flusso == ProtocolloConstants.COD_PARTENZA)
            {
                return new ProtocollazionePartenza(info.Anagrafiche, info.ParametriRegola, info.CodiceUo, info.CodiceRuolo);
            }
            else if (info.Flusso == ProtocolloConstants.COD_INTERNO)
            {
                return new ProtocollazioneInterna(info.Mittenti.Amministrazione[0], info.Destinatari.Amministrazione[0].PROT_UO, info.ParametriRegola.CodiceEnte);
            }
            else
            {
                throw new Exception($"FLUSSO {info.Flusso} NON GESTITO");
            }
        }
    }
}
