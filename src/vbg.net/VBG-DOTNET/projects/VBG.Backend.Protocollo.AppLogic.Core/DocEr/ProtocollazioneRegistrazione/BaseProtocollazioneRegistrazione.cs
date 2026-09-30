using VBG.Backend.Protocollo.AppLogic.Core.DocEr.Verticalizzazioni;
using VBG.Backend.Protocollo.AppLogic.Core.DocEr.Autenticazione;
using VBG.Backend.Protocollo.AppLogic.Core.DocEr.GestioneDocumentale;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Services;
using System;
using System.Linq;
using VBG.Shared.Infrastructure.ServiceModel;

namespace VBG.Backend.Protocollo.AppLogic.Core.DocEr.ProtocollazioneRegistrazione
{
    public class BaseProtocollazioneRegistrazione
    {
        protected VerticalizzazioniConfiguration _vert;
        IDatiProtocollo _datiProto;
        protected ProtocolloLogs _logs;
        ProtocolloSerializer _serializer;
        protected IAuthenticationService AuthWrapper { get; private set; }
        protected ResolveDatiProtocollazioneService DatiProtoSrv { get; private set; }
        GestioneDocumentaleService _docService;

        protected BaseProtocollazioneRegistrazione(VerticalizzazioniConfiguration vert, IAuthenticationService authWrapper, IDatiProtocollo datiProto, ProtocolloLogs logs, ProtocolloSerializer serializer, ResolveDatiProtocollazioneService datiProtoSrv, IBindingFactory bindingFactory)
        {
            _vert = vert;
            AuthWrapper = authWrapper;
            _datiProto = datiProto;
            _logs = logs;
            _serializer = serializer;
            DatiProtoSrv = datiProtoSrv;

            AuthWrapper.Login();

            _docService = new GestioneDocumentaleService(_vert.UrlGestioneDocumentale, AuthWrapper.Token, _logs, _serializer, bindingFactory);
        }

        protected string CreaSegnatura()
        {
            if (!_datiProto.ProtoIn.HaAllegati())
                throw new Exception("NON SONO PRESENTI ALLEGATI, E' NECESSARIO INVIARNE ALMENO UNO");

            var segnaturaAdapter = new RequestAdapter(_datiProto, _vert, _serializer, _docService);
            return segnaturaAdapter.Adatta();
        }

        protected long CreaUnitaDocumentale()
        {

            var unitaDocumentale = _docService.InserisciDocumentoPrimario(AuthWrapper, _datiProto.ProtoIn.RecuperaAllegati().First(), _datiProto.ProtoIn.TipoDocumento, _vert.CodiceEnte, _vert.CodiceAoo, _vert.TipoDocumentoPrincipale, DatiProtoSrv, _vert.DisabilitaMetadati);
            _docService.InserisciDocumentiAllegati(AuthWrapper, unitaDocumentale.ToString(), _datiProto.ProtoIn.RecuperaAllegati().Skip(1), _datiProto.ProtoIn.TipoDocumento, _vert.CodiceEnte, _vert.CodiceAoo, _vert.TipoDocumentoAllegato, DatiProtoSrv, _vert.DisabilitaMetadati);

            return unitaDocumentale;
        }
    }
}
