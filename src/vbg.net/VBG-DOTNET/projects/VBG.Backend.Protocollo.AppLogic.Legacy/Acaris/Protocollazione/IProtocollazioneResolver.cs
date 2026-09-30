using Init.SIGePro.Protocollo.AcarisOfficialBookServicePort;
using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Entity;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Protocollazione
{
    public interface IProtocollazioneResolver : IRepositoryIdResolver, IPrincipalIdResolver
    {
        string AccessToken { get; }
        enumTipoRegistrazioneDaCreare TipologiaCreazione { get; }
        RegistrazioneRequest InfoRichiestaCreazione { get; }
        string OfficialBookPortUrl { get; }
        string ObjectPortUrl { get; }
        IdAoo IdAoo { get; }
        IdStruttura IdStruttura { get; }
        IdNodo IdNodo { get; }
    }
}
