

using VBG.Backend.Protocollo.AppLogic.Legacy.DocArea.Adapters;
using VBG.Backend.Protocollo.AppLogic.Shared.Enums;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.DocArea.Builders.ClassificazioneFascicolazione
{
    public static class FascicolazioneFactory
    {
        public static IClassificazioneFascicolazione Create(string classifica, string flusso, DocAreaVerticalizzazioneParametriAdapter parametri, TipoProvenienza provenienza, Source tipoInserimento)
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
