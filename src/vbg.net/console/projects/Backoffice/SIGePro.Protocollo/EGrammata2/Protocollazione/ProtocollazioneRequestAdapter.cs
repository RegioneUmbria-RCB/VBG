using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.EGrammata2.Protocollazione.Segnatura.Request;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.EGrammata2.Protocollazione.Segnatura.Response;
using Init.SIGePro.Protocollo.EGrammata2.LeggiProtocollo;
using Init.SIGePro.Protocollo.ProtocolloServices;
using Init.SIGePro.Protocollo.ProtocollaAllegatiEGrammata2;
using System.Net;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.Logs;
using System.IO;
using Init.SIGePro.Manager.Utils;
using Init.SIGePro.Protocollo.EGrammata2.Anagrafiche;

namespace Init.SIGePro.Protocollo.EGrammata2.Protocollazione
{
    public class ProtocollazioneRequestAdapter
    {
        ProtocollazioneRequestConfiguration _conf;
        AnagraficheService _wrapper;

        public ProtocollazioneRequestAdapter(ProtocollazioneRequestConfiguration conf, AnagraficheService wrapper)
        {
            _conf = conf;
            _wrapper = wrapper;
        }

        public ProtocollaAllegatiRequestType AdattaRequest(SegnaturaGenerica segnatura, ProtocolloSerializer serializer, ProtocolloLogs logs)
        {
            string xml = serializer.Serialize(ProtocolloLogsConstants.ProtocollazioneRequestFileName, segnatura, Validation.ProtocolloValidation.TipiValidazione.DTD_EGRAMMATA2);
            var buffer = File.ReadAllBytes(Path.Combine(logs.Folder, ProtocolloLogsConstants.ProtocollazioneRequestFileName));
            string xml64 = Base64Utils.Base64Encode(buffer);
            
            var res = new ProtocollaAllegatiRequestType
            {
                token = _conf.DatiProtoService.Token,
                codiceEnte = _conf.Vert.CodiceEnte,
                userName = _conf.Vert.Username,
                password = _conf.Vert.Password,
                indirizzoIP = Dns.GetHostAddresses(Dns.GetHostName()).Where(x => x.AddressFamily == System.Net.Sockets.AddressFamily.InterNetwork).First().ToString(),
                xml = xml64,
                hash = "",
                userApp = _conf.Vert.UserApp,
                postazione = _conf.Vert.Postazione,
                codiciOggetto = _conf.Allegati.Select(x => Convert.ToInt32(x.CODICEOGGETTO)).ToArray(),
                wsURLEGrammata = _conf.Vert.UrlProtocolla
            };

            return res;
        }

        public SegnaturaGenerica AdattaSegnatura()
        {
            var dati = ProtocollazioneRequestFactory.Create(_conf, _conf.DatiProto.Flusso, _wrapper);

            var rVal = new SegnaturaGenerica
            {
                Dati = new Dati
                {
                    DtArrivoIn = dati.DataArrivo,
                    TxtOggIn = _conf.DatiProto.ProtoIn.Oggetto,
                    TipoLogicoIn = _conf.DatiProto.ProtoIn.TipoDocumento,
                    TipoFisicoIn = _conf.DatiProto.ProtoIn.TipoSmistamento,
                    TipoProtIN = dati.Flusso,
                    UOProv = dati.UOProvenienza,
                    Firm = dati.GetMittentiEsterni(),
                    EsibDest = dati.GetDestinatariEsterni(),
                    Classificazione = dati.Titolario,
                    NumFasc = _conf.Vert.Fascicolo[1],
                    AnnoFasc = _conf.Vert.Fascicolo[0],
                    NumSottofasc = _conf.Vert.Fascicolo[2],
                    CopieArrIn = dati.GetCopieArrIn()
                }
            };

            if (_conf.DatiProto.ProtoIn.Allegati.Count > 0)
            {
                var allegatoPrincipale = _conf.DatiProto.ProtoIn.Allegati.First();
                rVal.Dati.DocumentoElettronico = new DocumentoElettronico
                {
                    NomeFile = allegatoPrincipale.NOMEFILE,
                    NumeroAttachment = "0",
                    //Value = Convert.ToBase64String(allegatoPrincipale.OGGETTO)
                };
            }
            if (_conf.DatiProto.ProtoIn.Allegati.Count > 1)
                rVal.Dati.AllegaArrIn = dati.GetAllegati();

            return rVal;

            //return _conf.Wrapper.Protocollazione(rVal, _conf.DatiProto.ProtoIn.Allegati);
        }
    }
}
