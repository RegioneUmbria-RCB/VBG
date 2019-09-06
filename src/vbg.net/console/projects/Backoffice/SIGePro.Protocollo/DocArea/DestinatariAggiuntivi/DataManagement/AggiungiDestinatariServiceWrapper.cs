using Init.SIGePro.Protocollo.DocArea.Adapters;
using Init.SIGePro.Protocollo.DocArea.Configurations;
using Init.SIGePro.Protocollo.DocArea.Services;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.Serialize;
using Microsoft.Web.Services2.Attachments;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.DocArea.DestinatariAggiuntivi.DataManagement
{
    public class AggiungiDestinatariServiceWrapper : DocAreaProtocollazioneService
    {
        IEnumerable<IAnagraficaAmministrazione> _destinatari;
        string _codiceAmministrazione;
        string _codiceAoo;
        string _flusso;

        public AggiungiDestinatariServiceWrapper(DocAreaSegnaturaParamConfiguration conf, ProtocolloLogs logs, ProtocolloSerializer serializer, IEnumerable<IAnagraficaAmministrazione> destinatari)
            : base(conf.VertParams.Url, logs, serializer)
        {
            _destinatari = destinatari;
            _codiceAmministrazione = conf.VertParams.CodiceAmministrazione;
            _codiceAoo = conf.VertParams.CodiceAoo;
            _flusso = conf.Flusso;
        }

        internal override ProtocollazioneRet Protocollazione(string userName, string token)
        {
            var response = base.Protocollazione(userName, token);
            
            if (_destinatari.Count() > 1)
            {
                var adapter = DestinatariAggiuntiviAdapter.Adatta(_destinatari, response, _codiceAmministrazione, _codiceAoo, _flusso, _logs);

                if (adapter == null)
                    return response;

                var xml = _serializer.Serialize(ProtocolloLogsConstants.DestinatariAggiuntivi, adapter);
                _logs.InfoFormat("SEGNATURA DESTINATARI AGGIUNTIVI: {0}", xml);
                var stream = _serializer.SerializeToStream<DestinatariAggiuntivi>(adapter);
                AggiungiDestinatari(userName, token, stream);
            }

            return response;
        }

        private void AggiungiDestinatari(string username, string token, Stream streamSegnatura)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    AllegaSegnaturaDestinatari(ws, streamSegnatura);
                    _logs.InfoFormat("CHIAMATA A AGGIUNGI DESTINATARI");
                    var response = ws.destinatariAggiuntivi(username, token);

                    if (response.lngErrNumber != 0)
                        throw new Exception(String.Format("ERRORE RESTITUITO DAL WEB SERVICE, NUMERO ERRORE: {0}, DESCRIZIONE ERRORE: {1}", response.lngErrNumber.ToString(), response.strErrString));
                    _logs.InfoFormat("CHIAMATA A AGGIUNGI DESTINATARI AVVENUTA CON SUCCESSO");
                }
            }
            catch (Exception ex)
            {
                _logs.WarnFormat("ERRORE GENERATO DURANTE LA CHIAMATA AD AGGIUNGI DESTINATARI {0}", ex.Message);
            }
        }

        private void AllegaSegnaturaDestinatari(DocAreaProxy ws, Stream streamSegnatura)
        {
            //string pathSegnatura = Path.Combine(_logs.Folder, ProtocolloLogsConstants.SegnaturaXmlFileName);

            var attachment = new Attachment("text/xml", streamSegnatura);

            _logs.Info("Attachment del file segnatura.xml");
            ws.RequestSoapContext.Attachments.Add(attachment);
        }
    }
}
