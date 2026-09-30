using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione.Enum;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.LeggiProtocollo
{
    // ex LeggiProtoNumeroAnno
    public class LeggiProtocolloByEstremi : ILeggiProtocollo
    {
        string _numero;
        string _anno;
        string _codiceRegistro;
        string _codiceUfficio;
        ProtocolloService _wrapper;
        ProtocolloLogs _logs;

        // ex LeggiProtoNumeroAnno
        public LeggiProtocolloByEstremi(string numero, string anno, string codiceRegistro, string codiceUfficio, ProtocolloService wrapper, ProtocolloLogs logs)
        {
            _numero = numero;
            _anno = anno;
            _codiceRegistro = codiceRegistro;
            _codiceUfficio = codiceUfficio;
            _wrapper = wrapper;
            _logs = logs;
        }

        public DettaglioProtocolloResponse Leggi()
        {
            var request = new DettaglioProtocolloRequest
            {
                Registrazione = new RegistrazioneID
                {
                    Estremi = new EstremiRegistrazioneProtocollo
                    {
                        Anno = _anno,
                        Numero = _numero,
                        CodiceRegistro = _codiceRegistro,
                        CodiceUfficio = _codiceUfficio,
                        Verso = Verso.arrivo
                    }
                }
            };

            _logs.InfoFormat("CHIAMATA A LEGGI PROTOCOLLO, NUMERO: {0}, ANNO: {1}, REGISTRO: {2}, UFFICIO: {3}, VERSO: A", _numero, _anno, _codiceRegistro, _codiceUfficio);

            var response = _wrapper.LeggiProtocollo(request, false);
            if (response == null)
            {
                _logs.Info("PROTOCOLLO NON TROVATO, TENTATIVO CON FLUSSO P");
                request.Registrazione.Estremi = new EstremiRegistrazioneProtocollo
                {
                    Anno = _anno,
                    Numero = _numero,
                    CodiceRegistro = _codiceRegistro,
                    CodiceUfficio = _codiceUfficio,
                    Verso = Verso.partenza
                };

                response = _wrapper.LeggiProtocollo(request, true);
            }

            return response;

        }
    }
}
