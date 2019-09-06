using Init.SIGePro.Protocollo.DocArea.Adapters;
using Init.SIGePro.Protocollo.ProtocolloEnumerators;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.DocArea.Builders.ClassificazioneFascicolazione
{
    public static class FascicolazioneFactory
    {
        public static IClassificazioneFascicolazione Create(string classifica, string flusso, DocAreaVerticalizzazioneParametriAdapter parametri, ProtocolloEnum.TipoProvenienza provenienza, ProtocolloEnum.Source tipoInserimento)
        {
            if (parametri.GestisciFascicolazione)
            {
                return new FascicolazioneAttiva(classifica, flusso, parametri, provenienza, tipoInserimento);
            }
            else
            {
                return new FascicolazioneDisattiva(classifica, parametri);
            }
        }
    }
}
