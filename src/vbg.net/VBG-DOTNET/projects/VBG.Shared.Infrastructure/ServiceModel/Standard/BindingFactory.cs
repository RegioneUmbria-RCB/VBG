#if NET9_0_OR_GREATER
using VBG.Shared.Infrastructure.ServiceModel;
using Microsoft.Extensions.Configuration;
using System;
using System.Collections.Generic;
using System.ServiceModel;
using System.Text;

namespace VBG.Shared.Infrastructure.ServiceModel.Standard
{
    public class BindingFactory : IBindingFactory
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
#endif