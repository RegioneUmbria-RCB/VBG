using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Entity;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris
{
    public class ParametriRegoleInfo
    {
        public string AccessToken;

        public RepositoryId RepositoryID;

        public PrincipalId PrincipalID;
        public IdAoo IdAoo { get; set; }
        public IdStruttura IdStruttura { get; set; }
        public IdNodo IdNodo { get; set; }

        public string BackOfficePortUrl { get; set; }
        public string DocumentPortUrl { get; set; }
        public string ManagementPortUrl { get; set; }
        public string NavigationPortUrl { get; set; }
        public string ObjectPortUrl { get; set; }
        public string OfficialBookPortUrl { get; set; }
        public string RelationshipsPortUrl { get; set; }

        public string AppKey { get; set; }
        public string CodiceFiscale { get; set; }
        public int AnniConservazioneCorrente { get; set; }
        public int AnniConservazioneGenerale { get; set; }
        public int IdGradoVitalita { get; set; }
        public int IdTitolario { get; set; }
        public string SerieFascicoli { get; set; }
        public string TipoFascicolazione { get; set; }
        public string TipoFascicolo { get; set; }
        public string DescrizioneFascicolo { get; set; }
        public string SerieDossier { get; set; }
        public string TemplateDescrizioneDossier { get; set; }
        public int? CodiceDossierMin { get; set; }
        public int? CodiceDossierMax { get; set; }
        public int? CodiceDossierLength { get; set; }
        public string IdDossier{ get; set; }
        public string ApplicativoAlimentante { get; set; }
        public bool GestioneSediAbilitata { get; set; }
        public bool RicercaFascicoloPerDescrizione { get; set; }

        public string TemplateNumeroFascicolo { get; set; }
        public bool InviaCopiaCortesia { get; set; }
        public bool GestioneSottofascicoloAbilitata { get; set; }
        public string DescrizioneSottofascicolo { get; set; }
        public bool ValorizzaSoggettoFascicolo { get; set; }
        public bool AnnotaAllegatoPrincipale { get; set; }
        public bool AnnotaAllegatoSecondario { get; set; }
    }
}
