
namespace VBG.Backend.Protocollo.AppLogic.Legacy.Iride.PosteWeb
{
    public interface IPec
    {
        string Invia(string url, string proxyAddress, string idDocumento, string oggetto, string corpo, string mittente, string utente, string ruolo, string codiceAmministrazione);
    }
}
