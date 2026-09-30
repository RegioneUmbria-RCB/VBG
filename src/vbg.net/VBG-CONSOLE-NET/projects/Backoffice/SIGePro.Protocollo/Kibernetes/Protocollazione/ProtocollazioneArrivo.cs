using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.Kibernetes.Verticalizzazioni;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.ProtocolloKibernetesService;
using Init.SIGePro.Protocollo.Serialize;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Kibernetes.Protocollazione
{
    public class ProtocollazioneArrivo : IProtocollazione
    {
        VerticalizzazioniConfiguration _vert;
        List<IAnagraficaAmministrazione> _mittenti;
        string _funzionario;
        IDatiProtocollo _datiProto;

        public ProtocollazioneArrivo(VerticalizzazioniConfiguration vert, List<IAnagraficaAmministrazione> mittenti, IDatiProtocollo datiProto, string funzionario)
        {
            _vert = vert;
            _mittenti = mittenti;
            _funzionario = funzionario;
            _datiProto = datiProto;
        }

        public StatusProtocollo Protocolla(WS_AnagraficaClient ws, ProtocolloLogs logs, ProtocolloSerializer serializer)
        {
            try
            {
                if (_mittenti.Count.Equals(0))
                    throw new Exception("MITTENTE NON VALORIZZATO");

                logs.InfoFormat("CHIAMATA A PROTOCOLLAZIONE IN ARRIVO, CODICE ISTAT: {0}, MITTENTE: {1}, INDIRIZZO: {2}, OGGETTO: {3}, UFFICIO PROTOCOLLANTE: {4}, UO: {5}, RUOLO: {6}, FUNZIONARIO: {7}", _vert.IstatEnte, _mittenti[0].NomeCognome, _mittenti[0].Indirizzo, _datiProto.ProtoIn.Oggetto, _vert.UfficioProtocollante, _datiProto.Uo, _datiProto.Ruolo, _funzionario);
                var response = ws.set4ProtocolloEntrata(_vert.IstatEnte, _mittenti[0].NomeCognome, _mittenti[0].Indirizzo, _datiProto.ProtoIn.Oggetto, _vert.UfficioProtocollante, _datiProto.Uo, _datiProto.Ruolo, "", "", false, _funzionario, "", "");

                serializer.Serialize(ProtocolloLogsConstants.ProtocollazioneResponseFileName, response);

                if (response.CodStato.Equals(-1))
                    throw new Exception(response.DescrStato);

                logs.InfoFormat("PROTOCOLLAZIONE IN ARRIVO AVVENUTA CON SUCCESSO PROTOCOLLO NUMERO: {0}, ANNO: {1}", response.Numero, response.Anno);

                return response;
            }
            catch(Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA PROTOCOLLAZIONE IN ENTRATA, {0}", ex.Message, ex));
            }
        }
    }
}
