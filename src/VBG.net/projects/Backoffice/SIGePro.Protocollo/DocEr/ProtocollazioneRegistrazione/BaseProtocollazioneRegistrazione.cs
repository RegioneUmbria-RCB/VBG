using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.DocEr.Verticalizzazioni;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.DocEr.Autenticazione;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.DocEr.GestioneDocumentale;
using Init.SIGePro.Protocollo.ProtocolloServices;

namespace Init.SIGePro.Protocollo.DocEr.ProtocollazioneRegistrazione
{
    public class BaseProtocollazioneRegistrazione
    {
        VerticalizzazioniConfiguration _vert;
        IDatiProtocollo _datiProto;
        ProtocolloLogs _logs;
        ProtocolloSerializer _serializer;
        protected IAuthenticationService AuthWrapper { get; private set; }
        protected ResolveDatiProtocollazioneService DatiProtoSrv { get; private set; }
        GestioneDocumentaleService _docService;

        protected BaseProtocollazioneRegistrazione(VerticalizzazioniConfiguration vert, IAuthenticationService authWrapper, IDatiProtocollo datiProto, ProtocolloLogs logs, ProtocolloSerializer serializer, ResolveDatiProtocollazioneService datiProtoSrv)
        {
            _vert = vert;
            AuthWrapper = authWrapper;
            _datiProto = datiProto;
            _logs = logs;
            _serializer = serializer;
            DatiProtoSrv = datiProtoSrv;

            AuthWrapper.Login();

            _docService = new GestioneDocumentaleService(_vert.UrlGestioneDocumentale, AuthWrapper.Token, _logs, _serializer);

        }

        protected string CreaSegnatura()
        {
            var allegati = _datiProto.ProtoIn.Allegati;

            if (allegati.Count == 0)
                throw new Exception("NON SONO PRESENTI ALLEGATI, E' NECESSARIO INVIARNE ALMENO UNO");

            var segnaturaAdapter = new RequestAdapter(_datiProto, _vert, _serializer, _docService);
            return segnaturaAdapter.Adatta();
        }

        protected long CreaUnitaDocumentale()
        {

            var unitaDocumentale = _docService.InserisciDocumentoPrimario(AuthWrapper, _datiProto.ProtoIn.Allegati.First(), _datiProto.ProtoIn.TipoDocumento, _vert.CodiceEnte, _vert.CodiceAoo, _vert.TipoDocumentoPrincipale, DatiProtoSrv);
            _docService.InserisciDocumentiAllegati(AuthWrapper, unitaDocumentale.ToString(), _datiProto.ProtoIn.Allegati.Skip(1), _datiProto.ProtoIn.TipoDocumento, _vert.CodiceEnte, _vert.CodiceAoo, _vert.TipoDocumentoAllegato, DatiProtoSrv);

            return unitaDocumentale;
        }
    }
}
