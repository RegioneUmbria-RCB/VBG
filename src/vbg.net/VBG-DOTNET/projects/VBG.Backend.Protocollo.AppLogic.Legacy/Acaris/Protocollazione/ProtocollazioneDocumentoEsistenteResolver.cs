using Init.SIGePro.Protocollo.AcarisOfficialBookServicePort;
using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Protocollazione.Registrazioni;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Protocollazione
{
    public class ProtocollazioneDocumentoEsistenteResolver : AProtocollazioneResolver
    {
        private readonly IRegistrazioneAPIResolver _registrazioneAPIResolver;
        public ProtocollazioneDocumentoEsistenteResolver(IRegistrazioneAPIResolver registrazioneAPIResolver, ParametriRegoleInfo parametri) : base(parametri)
        {
            this._registrazioneAPIResolver = registrazioneAPIResolver;
        }
        public override RegistrazioneRequest InfoRichiestaCreazione
        {
            get
            {
                return new ProtocollazioneDocumentoEsistente
                {
                    aooProtocollanteId = new ObjectIdType
                    {
                        value = this.Parametri.IdAoo.IdAcaris
                    },
                    senzaCreazioneSoggettiEsterni = true,
                    classificazioneId = new ObjectIdType
                    {
                        value = this._registrazioneAPIResolver.ClassificazioneID
                    },
                    registrazioneAPI = this._registrazioneAPIResolver.ResolveRegistrazione()
                };
            }
        }

        public override enumTipoRegistrazioneDaCreare TipologiaCreazione => enumTipoRegistrazioneDaCreare.ProtocollazioneDocumentoEsistente;
    }
}
