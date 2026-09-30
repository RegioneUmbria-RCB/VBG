using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.ProtocolloKibernetesService;
using Init.SIGePro.Protocollo.Kibernetes.Verticalizzazioni;
using Init.SIGePro.Protocollo.Serialize;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;

namespace Init.SIGePro.Protocollo.Kibernetes.Protocollazione
{
    public class ProtocollazionePartenza : IProtocollazione
    {
        VerticalizzazioniConfiguration _vert;
        List<IAnagraficaAmministrazione> _destinatari;
        string _funzionario;
        IDatiProtocollo _datiProto;

        public ProtocollazionePartenza(VerticalizzazioniConfiguration vert, List<IAnagraficaAmministrazione> destinatari, IDatiProtocollo datiProto, string funzionario)
        {
            _vert = vert;
            _destinatari = destinatari;
            _funzionario = funzionario;
            _datiProto = datiProto;
        }

        public StatusProtocollo Protocolla(WS_AnagraficaClient ws, ProtocolloLogs logs, ProtocolloSerializer serializer)
        {
            try
            {
                if (_destinatari.Count.Equals(0))
                    throw new Exception("DESTINATARIO NON VALORIZZATO");
                
                string secondoDestinatario = "";
                string indirizzoSecondoDestinatario = "";

                if (_destinatari.Count > 1)
                {
                    secondoDestinatario = _destinatari[1].NomeCognome;
                    indirizzoSecondoDestinatario = _destinatari[1].Indirizzo;
                }

                logs.InfoFormat("CHIAMATA A PROTOCOLLAZIONE IN PARTENZA, CODICE ISTAT: {0}, PRIMO DESTINATARIO: {1}, INDIRIZZO PRIMO DESTINATARIO: {2}, OGGETTO: {3}, UFFICIO PROTOCOLLANTE: {4}, SECONDO DESTINATARIO: {5}, INDIRIZZO SECONDO DESTINATARIO: {6}, FUNZIONARIO: {7}", _vert.IstatEnte, _destinatari[0].NomeCognome, _destinatari[0].Indirizzo, _datiProto.ProtoIn.Oggetto, _vert.UfficioProtocollante, secondoDestinatario, indirizzoSecondoDestinatario, _funzionario);
                var response = ws.set4ProtocolloUscita(_vert.IstatEnte, _destinatari[0].NomeCognome, _destinatari[0].Indirizzo, _datiProto.ProtoIn.Oggetto, _vert.UfficioProtocollante, secondoDestinatario, indirizzoSecondoDestinatario, false, _funzionario, "", "");

                serializer.Serialize(ProtocolloLogsConstants.ProtocollazioneResponseFileName, response);

                if (response.CodStato.Equals(-1))
                    throw new Exception(response.DescrStato);

                logs.InfoFormat("PROTOCOLLAZIONE IN PARTENZA AVVENUTA CON SUCCESSO PROTOCOLLO NUMERO: {0}, ANNO: {1}", response.Numero, response.Anno);

                return response;
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA PROTOCOLLAZIONE IN PARTENZA, {0}", ex.Message, ex));
            }
        }
    }
}
