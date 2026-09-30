using System.ServiceModel;

namespace VBG.Shared.Infrastructure.ServiceModel.Standard
{
    public class BindingConfigurationOption
    {
        public const string SectionName = "bindings";

        public WSMessageEncoding MessageEncoding { get; set; } = WSMessageEncoding.Text;

        public int MaxBufferSize { get; set; } = int.MaxValue;

        public int? MaxReceivedMessageSize { get; set; } = int.MaxValue;

        public int TimeoutInMinutes { get; set; } = 10;
    }
}
