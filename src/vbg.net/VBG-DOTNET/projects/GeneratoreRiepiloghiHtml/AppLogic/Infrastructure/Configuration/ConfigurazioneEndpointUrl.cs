namespace GeneratoreRiepiloghiHtml.AppLogic.Infrastructure.Configuration
{
    public class ConfigurazioneEndpointUrl
    {
        public required string FileConverterUrl { get; init; }
        public required string UrlVisura { get; init; }
        public required string UrlWsDatiDinamici { get; init; }
        public required string UrlWsInterventi { get; init; }
        public required string OggettiServiceUrl { get; init; }
    }
}
