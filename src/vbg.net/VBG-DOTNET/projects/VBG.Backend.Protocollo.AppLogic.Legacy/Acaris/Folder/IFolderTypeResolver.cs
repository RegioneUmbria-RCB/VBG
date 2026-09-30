using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Entity;

using PersonalLib2.Data;
using Init.SIGePro.Protocollo.AcarisObjectServicePort;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Folder
{
    public interface IFolderTypeResolver
    {
        IdAoo IdAoo { get; }
        IdNodo IdNodo { get; }
        IdStruttura IdStruttura { get; }
        int IdGradoVitalita { get; }
        enumFolderObjectType TypeId { get; }
        PropertiesType Properties { get; }
        string Voce { get; }
        int IdTitolario { get; }
        string CodiceSerie { get; }
        string OggettoFascicolo { get; }
        string IdProtocollo { get; }
        DataBase Db { get; }
        string IdComuneAlias { get; }
        string Software { get; }
        string CodiceComune { get; }
        string IdComune { get; }
        string Operatore { get; }
        string CodiceFiscale { get; }
        string ObjectPortUrl { get; }
        string NavigationPortUrl { get; }
        RepositoryId RepositoryID { get; }
        PrincipalId PrincipalID { get; }
        ParametriRegoleInfo Configurazione { get; }
        string AccessToken { get; }
        string IdDossier { get; set; }
        string Numero { get; }
    }
}