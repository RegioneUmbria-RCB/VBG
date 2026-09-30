using Init.SIGePro.Protocollo.AcarisOfficialBookServicePort;
using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Entity;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Protocollazione
{
    public abstract class AProtocollazioneResolver : IProtocollazioneResolver
    {
        public ParametriRegoleInfo Parametri { get; }
        protected AProtocollazioneResolver(ParametriRegoleInfo parametri)
        {
            this.Parametri = parametri;
        }
        public RepositoryId RepositoryId => this.Parametri.RepositoryID;
        public PrincipalId PrincipalId => this.Parametri.PrincipalID;
        public IdAoo IdAoo => this.Parametri.IdAoo;
        public IdStruttura IdStruttura => this.Parametri.IdStruttura;
        public IdNodo IdNodo => this.Parametri.IdNodo;
        public string OfficialBookPortUrl => this.Parametri.OfficialBookPortUrl;
        public string ObjectPortUrl => this.Parametri.ObjectPortUrl;
        public abstract enumTipoRegistrazioneDaCreare TipologiaCreazione { get; }
        public abstract RegistrazioneRequest InfoRichiestaCreazione { get; }
        public string AccessToken => this.Parametri.AccessToken;
    }
}
