using Init.SIGePro.Data;
using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Client;
using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Entity;
using PersonalLib2.Data;
using Init.SIGePro.Protocollo.AcarisObjectServicePort;
using System.Collections.Generic;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Folder
{
    public abstract class FolderResolver : IFolderTypeResolver
    {
        public abstract string CodiceSerie { get; }

        public FolderResolver(ParametriRegoleInfo parametri)
        {
            this.AccessToken = parametri.AccessToken;
            this.RepositoryID = parametri.RepositoryID;
            this.IdAoo = parametri.IdAoo;
            this.IdNodo = parametri.IdNodo;
            this.IdStruttura = parametri.IdStruttura;
            this.IdGradoVitalita = parametri.IdGradoVitalita;
            this.CodiceFiscale = parametri.CodiceFiscale;
            this.ObjectPortUrl = parametri.ObjectPortUrl;
            this.PrincipalID = parametri.PrincipalID;
            this.NavigationPortUrl = parametri.NavigationPortUrl;
        }

        public abstract string Numero { get; }
        public abstract enumFolderObjectType TypeId { get; }
        public abstract PropertiesType Properties { get; }
        public abstract string Voce { get; }
        public abstract string OggettoDocumentoPrincipale { get; }
        public abstract List<string> MittentiPG { get; }
        public abstract List<string> MittentiPF { get; }
        public abstract List<string> DestinatariPG { get; }
        public abstract List<string> DestinatariPF { get; }
        public abstract int NumeroAllegati { get; }
        public abstract string NomeFilePrincipale { get; }
        public abstract string AnnotazioneDocPrincipale { get; }
        public abstract string UtenteEsteso { get; }
        public abstract string OggettoFascicolo { get; }
        public abstract IEnumerable<OggettiMetadati> GetMetadati(int codiceOggetto);
        public abstract IdFolder IdFolder { get; }
        public abstract string IdProtocollo { get; }
        public abstract DataBase Db { get; }
        public abstract string IdComuneAlias { get; }
        public abstract string Software { get; }
        public abstract string CodiceComune { get; }
        public abstract string IdComune { get; }
        public abstract string Operatore { get; }
        public abstract int IdTitolario { get; }
        public RepositoryId RepositoryID { get; }
        public IdAoo IdAoo { get; }
        public IdNodo IdNodo { get; }
        public IdStruttura IdStruttura { get; }
        public int IdGradoVitalita { get; }
        public string CodiceFiscale { get; }
        public string ObjectPortUrl { get; }
        public PrincipalId PrincipalID { get; }
        public string NavigationPortUrl { get; }
        public ParametriRegoleInfo Configurazione { get; }
        public string AccessToken { get; }
        public abstract string IdDossier { get ; set; }
    }
}
