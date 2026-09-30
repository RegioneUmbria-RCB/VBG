using Init.SIGePro.Manager.Configuration;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Services.MailTipo
{
    public abstract class MailTipoService : IMailTipoService
    {
        public static class MailTipoConstants
        {
            public const string UriMailTipoWs = "services/mailtipo?wsdl";
            public const string Binding = "MailTipoServiceBinding";
        }

        public ProtocolloLogs Logger { get; }
        public abstract MailTipoType GetMailTipo();

        public string GetMailTipoUrl() => string.Concat(ParametriConfigurazione.Get.WsHostUrlJava, MailTipoConstants.UriMailTipoWs);

        public MailTipoService(ProtocolloLogs logger)
        {
            this.Logger = logger;
        }
    }
}
