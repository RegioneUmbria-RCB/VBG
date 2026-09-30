
namespace VBG.Backend.Protocollo.AppLogic.Core.Iride2.PosteWeb
{
    public interface IPec
    {
        string Invia(string url, string proxyAddress, string idDocumento, string oggetto, string corpo, string mittente, string utente, string ruolo, string codiceAmministrazione);
    }
}
