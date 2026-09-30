using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Entity;
using System.Collections.Generic;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Document
{
    public interface IDocumentResolver
    {
        string AccessToken { get; }
        string ObjectWSUrl { get; }
        string DocumentWsUrl { get; }
        string ManagementWsUrl { get; }
        string NavigationWSUrl { get; }
        List<string> MittentiPF { get; }
        List<string> MittentiPG { get; }
        List<string> DestinatariPG { get; }
        List<string> DestinatariPF { get; }
        int NumeroAllegati { get; }
        string OggettoDocumentoPrincipale { get; }
        RepositoryId RepositoryId { get; }
        PrincipalId PrincipalId { get; }
        string AnnotazioneDocPrincipale { get; }
        string UtenteEsteso { get; }
        ParametriRegoleInfo Configurazione { get; }
        string Flusso { get; }
    }
}
