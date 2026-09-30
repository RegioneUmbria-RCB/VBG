using System.ServiceModel;

namespace GeneratoreRiepiloghiHtml.AppLogic.Infrastructure.ServiceCreators
{
    public class BindingFactory
    {
        private readonly IConfiguration _configuration;

        public BindingFactory(IConfiguration configuration)
        {
            this._configuration = configuration;
        }

        public BasicHttpBinding CreateAndConfigure(string bindingName)
        {
            var binding = new BasicHttpBinding();
            var config = new BindingConfigurationOption();

            this._configuration.GetSection($"{BindingConfigurationOption.SectionName}:{bindingName}").Bind(config);

            binding.Name = bindingName;
            binding.MessageEncoding = config.MessageEncoding;
            binding.MaxBufferSize = config.MaxBufferSize;
            binding.MaxReceivedMessageSize = config.MaxReceivedMessageSize ?? config.MaxBufferSize;
            binding.OpenTimeout = TimeSpan.FromMinutes(config.TimeoutInMinutes);
            binding.CloseTimeout = TimeSpan.FromMinutes(config.TimeoutInMinutes);
            binding.SendTimeout = TimeSpan.FromMinutes(config.TimeoutInMinutes);
            binding.ReceiveTimeout = TimeSpan.FromMinutes(config.TimeoutInMinutes);

            return binding;
        }
    }
}
