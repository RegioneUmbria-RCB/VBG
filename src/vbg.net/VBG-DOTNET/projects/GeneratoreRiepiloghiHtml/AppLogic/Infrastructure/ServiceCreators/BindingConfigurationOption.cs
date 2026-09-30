using System.ServiceModel;

namespace GeneratoreRiepiloghiHtml.AppLogic.Infrastructure.ServiceCreators
{
    public class BindingConfigurationOption
    {
        public const string SectionName = "bindings";

        public WSMessageEncoding MessageEncoding { get; set; } = WSMessageEncoding.Text;

        public int MaxBufferSize { get; set; } = 65536000;

        public int? MaxReceivedMessageSize { get; set; }

        public int TimeoutInMinutes { get; set; } = 10;
    }
}