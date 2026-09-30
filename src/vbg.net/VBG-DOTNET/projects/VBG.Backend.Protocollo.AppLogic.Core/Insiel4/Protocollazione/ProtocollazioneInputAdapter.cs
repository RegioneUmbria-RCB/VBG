using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione.MittentiDestinatari;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Verticalizzazioni;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione
{
    public class ProtocollazioneInputAdapter
    {
        InsielVerticalizzazioniConfiguration _verticalizzazioneInsiel;
        IDatiProtocollo _datiProto;
        ProtocolloService _srv;
        ProtocolloLogs _logs;

        public ProtocollazioneInputAdapter(InsielVerticalizzazioniConfiguration verticalizzazioneInsiel, IDatiProtocollo datiProto, ProtocolloService srv, ProtocolloLogs logs)
        {
            _srv = srv;
            _verticalizzazioneInsiel = verticalizzazioneInsiel;
            _datiProto = datiProto;
            _logs = logs;
        }

        /// <summary>
        /// Il metodo si occupa di adattare i dati della protocollazione alla request che si aspetta il protocollo di Insiel.
        /// codiceUfficio: valorizza la proprietà InserimentoProtocolloRequest.codiceUfficio
        /// valorizzaDataRicezioneSpedizione: se true allora valorizza InserimentoProtocolloRequest.dataRicezioneSpedizione con _datiProto.ProtoIn.DataRegistrazione.GetValueOrDefault(DateTime.Now);
        /// </summary>
        public InserimentoProtocolloRequest CreaRequest(string codiceUfficio, bool valorizzaDataRicezioneSpedizione, IEnumerable<DocumentoInsProto> docs)
        {
            var mittentiDestinatari = ProtocollazioneFactory.Create(_datiProto.Flusso, _datiProto, _srv, _verticalizzazioneInsiel, _logs);

            var request = new InserimentoProtocolloRequest
            {
                Utente = _verticalizzazioneInsiel.Utente,
                CodiceUfficio = codiceUfficio,
                CodiceRegistro = _verticalizzazioneInsiel.CodiceRegistro,
                CodiceUfficioOperante = _verticalizzazioneInsiel.CodiceUfficioOperante,
                Mittenti = mittentiDestinatari.GetMittenti(),
                Destinatari = mittentiDestinatari.GetDestinatari(),
                OggettoDocumento = new OggettoDocumento { Oggetto = _datiProto.ProtoIn.Oggetto },
                //OggettoProtocollo = _datiProto.ProtoIn.Oggetto,
                EstremiDocumento = new EstremiDocumento { Tipologia = _datiProto.ProtoIn.TipoDocumento },
                Verso = mittentiDestinatari.Flusso,
                Documenti = docs,
                AttivaInvioTelematico = mittentiDestinatari.InvioTelematicoAttivo,
                Uffici = mittentiDestinatari.GetUffici(),
            };
            _logs.DebugFormat("request.Utente: {0}", request.Utente);
            _logs.DebugFormat("request.CodiceUfficio: {0}", request.CodiceUfficio);
            _logs.DebugFormat("request.CodiceRegistro: {0}", request.CodiceRegistro);
            _logs.DebugFormat("request.CodiceUfficioOperante: {0}", request.CodiceUfficioOperante);
            _logs.DebugFormat("request.EstremiDocumento: {0}", _datiProto.ProtoIn.TipoDocumento);
            _logs.DebugFormat("request.Verso: {0}", request.Verso);

            if (!String.IsNullOrEmpty(mittentiDestinatari.MittentePec))
            {
                request.CasellaAOOmittente = mittentiDestinatari.MittentePec;
                _logs.DebugFormat("request.CasellaAOOmittente: {0}", request.CasellaAOOmittente);
            }

            if (valorizzaDataRicezioneSpedizione)
            {
                request.DataRicezioneSpedizione = _datiProto.ProtoIn.DataRegistrazione.GetValueOrDefault(DateTime.Now);
                _logs.DebugFormat("request.DataRicezioneSpedizione: {0}", request.DataRicezioneSpedizione);
            }

            if (!_verticalizzazioneInsiel.EscludiClassifica)
            {
                var classificaAdapter = new ClassificaAdapter();
                var classifica = classificaAdapter.Adatta(_verticalizzazioneInsiel.UsaLivelliClassifica, _datiProto.ProtoIn.Classifica);
                request.Classifiche = new Classifica[] { classifica };
                _logs.DebugFormat("request.Classifiche: {0}", classifica);
            }

            if (_verticalizzazioneInsiel.DisattivaCtrlDocs)
            {
                request.DisattivaCtrlDocumenti = true;
                _logs.DebugFormat("request.DisattivaCtrlDocumenti: {0}", request.DisattivaCtrlDocumenti);
            }

            return request;
        }
    }
}
