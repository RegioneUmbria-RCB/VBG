using VBG.Backend.Protocollo.AppLogic.Core.Tinn.Segnatura;
using VBG.Backend.Protocollo.AppLogic.Core.Tinn.Verticalizzazioni;
using VBG.Backend.Protocollo.AppLogic.Core.Tinn.Protocollazione.MittentiDestinatari;
using VBG.Backend.Protocollo.AppLogic.Core.Tinn.Protocollazione.Allegati;
using VBG.Backend.Protocollo.AppLogic.Core.Tinn.Services;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using ProtocolloTinnServiceProxy;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Core.Tinn.Protocollazione.Segnatura
{
    public class SegnaturaAdapter
    {
        IDatiProtocollo _datiProto;
        ProtocolloLogs _logs;
        ProtocolloSerializer _serializer;
        VerticalizzazioniConfiguration _verticalizzazione;
        List<ProtocolloAllegati> _allegati;
        ProtocolloService _wrapper;

        public SegnaturaAdapter(IDatiProtocollo datiProto, List<ProtocolloAllegati> allegati, ProtocolloLogs logs, ProtocolloSerializer serializer, VerticalizzazioniConfiguration verticalizzazione, ProtocolloService wrapper)
        {
            _wrapper = wrapper;
            _datiProto = datiProto;
            _allegati = allegati;
            _verticalizzazione = verticalizzazione;
            _logs = logs;
            _serializer = serializer;
        }

        public TRispostaProtocollazione Adatta()
        {
            _wrapper.Login(_verticalizzazione.Codiceente, _verticalizzazione.Password);

            var mittentiDestinatari = MittentiDestinatariFlussoFactory.Create(_datiProto, _verticalizzazione);
            var allegatiAdapter = new AllegatiAdapter(_wrapper, _allegati, _serializer, _logs, _verticalizzazione);

            var segnatura = new SegnaturaInput
            {
                Intestazione = new Intestazione
                {
                    Classifica = new Classifica
                    {
                        CodiceTitolario = _datiProto.ProtoIn.Classifica,
                        CodiceAOO = _verticalizzazione.CodiceAoo,
                        CodiceAmministrazione = _verticalizzazione.CodiceAmministrazione
                    },
                    Oggetto = _datiProto.ProtoIn.Oggetto,
                    Identificatore = new Identificatore
                    {
                        Flusso = mittentiDestinatari.Flusso,
                        CodiceAmministrazione = _verticalizzazione.CodiceAmministrazione,
                        CodiceAOO = _verticalizzazione.CodiceAoo
                    },
                    Mittente = mittentiDestinatari.GetMittenti(),
                    Destinatario = mittentiDestinatari.GetDestinatari()
                }
            };

            allegatiAdapter.Adatta(segnatura);
            _logs.Info("SALVATAGGIO DEL FILE SEGNATURA");
            _serializer.LogAndValidate(ProtocolloLogsConstants.SegnaturaXmlFileName, segnatura);

            var bufferSegnatura = File.ReadAllBytes(Path.Combine(_logs.Folder, ProtocolloLogsConstants.SegnaturaXmlFileName));

            return _wrapper.Protocollazione(bufferSegnatura);
        }
    }
}
