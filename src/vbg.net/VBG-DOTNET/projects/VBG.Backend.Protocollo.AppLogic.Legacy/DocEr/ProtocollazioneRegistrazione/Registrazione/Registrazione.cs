using VBG.Backend.Protocollo.AppLogic.Legacy.DocEr.Verticalizzazioni;
using VBG.Backend.Protocollo.AppLogic.Legacy.DocEr.Autenticazione;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Backend.Protocollo.AppLogic.Shared.Services;
using VBG.Shared.Infrastructure.ServiceModel;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.DocEr.ProtocollazioneRegistrazione.Registrazione
{
    public class Registrazione : BaseProtocollazioneRegistrazione
    {
        RegistrazioneParticolareService _registrazioneWrapper;
        string _registro;

        public long IdUnitaDocumentale { get; private set; }

        public Registrazione(RegistrazioneParticolareService registrazioneWrapper, string registro, VerticalizzazioniConfiguration vert, IAuthenticationService authWrapper, IDatiProtocollo datiProto, ProtocolloLogs logs, ProtocolloSerializer serializer, ResolveDatiProtocollazioneService datiProtoSrv, IBindingFactory bindingFactory)
            : base(vert, authWrapper, datiProto, logs, serializer, datiProtoSrv, bindingFactory)
        {
            _registrazioneWrapper = registrazioneWrapper;
            _registro = registro;
        }

        public esito Registra()
        {
            IdUnitaDocumentale = CreaUnitaDocumentale();
            string segnatura = CreaSegnatura();

            var response = _registrazioneWrapper.Registra(AuthWrapper.Token, IdUnitaDocumentale, _registro, segnatura);
            return response;
        }


        
    }
}
