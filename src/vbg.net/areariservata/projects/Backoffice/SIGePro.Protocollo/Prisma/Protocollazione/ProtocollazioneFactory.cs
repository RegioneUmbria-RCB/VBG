using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.ProtocolloServices;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Prisma.Protocollazione
{
    public class ProtocollazioneFactory
    {
        public static IProtocollazione Create(ProtocollazioneInfo info)
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
