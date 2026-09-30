using VBG.Backend.Protocollo.AppLogic.Shared.Logs;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Services.MailTipo
{
    public interface IMailTipoService
    {
        ProtocolloLogs Logger { get; }

        MailTipoType GetMailTipo();
    }
}
