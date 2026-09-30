using ProtocolloInsielService3;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel3.Services;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel3.LeggiProtocollo
{
    public class LeggiProtoNumeroAnno : ILeggiProtocollo
    {
        string _numero;
        string _anno;
        string _codiceRegistro;
        string _codiceUfficio;
        ProtocolloService _wrapper;
        ProtocolloLogs _logs;

        public LeggiProtoNumeroAnno(string numero, string anno, string codiceRegistro, string codiceUfficio, ProtocolloService wrapper, ProtocolloLogs logs)
        {
            _numero = numero;
            _anno = anno;
            _codiceRegistro = codiceRegistro;
            _codiceUfficio = codiceUfficio;
            _wrapper = wrapper;
            _logs = logs;
        }

        public DettagliProtocollo Leggi()
        {
            var request = new DettagliProtocolloRequest
            {
                registrazione = new ProtocolloRequest
                {
                    Item = new ProtocolloRequestIdentificatoreProt
                    {
                        anno = _anno,
                        numero = _numero,
                        codiceRegistro = _codiceRegistro,
                        codiceUfficio = _codiceUfficio,
                        verso = verso.A
                    }
                }
            };

            _logs.InfoFormat("CHIAMATA A LEGGI PROTOCOLLO, NUMERO: {0}, ANNO: {1}, REGISTRO: {2}, UFFICIO: {3}, VERSO: A", _numero, _anno, _codiceRegistro, _codiceUfficio);

            var response = _wrapper.LeggiProtocollo(request, false);
            if (response == null)
            {
                _logs.Info("PROTOCOLLO NON TROVATO, TENTATIVO CON FLUSSO P");
                request.registrazione.Item = new ProtocolloRequestIdentificatoreProt
                {
                    anno = _anno,
                    numero = _numero,
                    codiceRegistro = _codiceRegistro,
                    codiceUfficio = _codiceUfficio,
                    verso = verso.P
                };

                response = _wrapper.LeggiProtocollo(request, true);
            }

            return response;

        }
    }
}
