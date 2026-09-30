using PersonalLib2.Data.Metadata;

[assembly: WebActivatorEx.PostApplicationStartMethod(typeof(Sigepro.net.App_Start.AnalisiMetadati), "Analizza")]

namespace Sigepro.net.App_Start
{
    public static class AnalisiMetadati
    {
        public static void Analizza()
        {
            // Forza l'analisi dei metadati all'avvio dell'applicazione
            MetadataAnalyzer.Instance.EnsureAnalysisExistsFor(typeof(Init.SIGePro.Data.Istanze));
        }
    }
}