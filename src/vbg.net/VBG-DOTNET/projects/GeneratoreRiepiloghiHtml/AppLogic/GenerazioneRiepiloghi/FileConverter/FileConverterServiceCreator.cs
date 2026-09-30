using GeneratoreRiepiloghiHtml.AppLogic.Authentication;
using GeneratoreRiepiloghiHtml.AppLogic.Infrastructure.Configuration;
using GeneratoreRiepiloghiHtml.AppLogic.Infrastructure.ServiceCreators;
using System.ServiceModel;
using VbgFileConverter;

namespace GeneratoreRiepiloghiHtml.AppLogic.GenerazioneRiepiloghiSchede.FileConverter
{
    public class FileConverterServiceCreator : ServiceCreatorBase<fileconverterClient>
    {
        public FileConverterServiceCreator(ParametriSecurityService cfgService, ITokenResolver tokenResolver, BindingFactory bindingFactory, ILogger<FileConverterServiceCreator> logger) : base(cfgService, tokenResolver, bindingFactory, logger)
        {
        }

        protected override string GetBindingName() => "fileConverterServiceBinding";
        protected override fileconverterClient CreateClient(EndpointAddress endpoint, BasicHttpBinding binding)
        {
            return new fileconverterClient(binding, endpoint);
        }

        protected override string GetEndpointUrl(ConfigurazioneEndpointUrl config)
        {
            return config.FileConverterUrl;
        }
    }
}