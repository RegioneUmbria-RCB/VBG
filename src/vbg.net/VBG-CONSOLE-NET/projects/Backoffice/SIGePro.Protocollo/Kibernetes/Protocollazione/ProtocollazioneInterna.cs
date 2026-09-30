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
    public class ProtocollazioneInterna : IProtocollazione  
    {
        VerticalizzazioniConfiguration _vert;
        string _funzionario;
        IDatiProtocollo _datiProto;

        public ProtocollazioneInterna(VerticalizzazioniConfiguration vert, IDatiProtocollo datiProto, string funzionario)
        {
            _vert = vert;
            _funzionario = funzionario;
            _datiProto = datiProto;

        }

        public StatusProtocollo Protocolla(WS_AnagraficaClient ws, ProtocolloLogs logs, ProtocolloSerializer serializer)
        {
            try
            {
                logs.InfoFormat("CHIAMATA A PROTOCOLLAZIONE INTERNA, CODICE ISTAT: {0}, MITTENTE: {1}, INDIRIZZO: {2}, OGGETTO: {3}, UFFICIO PROTOCOLLANTE: {4}, UO: {5}, RUOLO: {6}, FUNZIONARIO: {7}", _vert.IstatEnte, _datiProto.AmministrazioniInterne[0].PROT_UO, _datiProto.AmministrazioniInterne[0].INDIRIZZO, _datiProto.ProtoIn.Oggetto, _vert.UfficioProtocollante, _datiProto.Uo, _datiProto.Ruolo, _funzionario);
                var response = ws.set4ProtocolloInterno(_vert.IstatEnte, _datiProto.Uo, _datiProto.Amministrazione.INDIRIZZO, _datiProto.ProtoIn.Oggetto, _vert.UfficioProtocollante, _datiProto.AmministrazioniInterne[0].PROT_UO, _datiProto.AmministrazioniInterne[0].PROT_RUOLO, "", "", false, _funzionario, "", "");

                serializer.Serialize(ProtocolloLogsConstants.ProtocollazioneResponseFileName, response);

                if (response.CodStato.Equals(-1))
                    throw new Exception(response.DescrStato);

                logs.InfoFormat("PROTOCOLLAZIONE INTERNA AVVENUTA CON SUCCESSO PROTOCOLLO NUMERO: {0}, ANNO: {1}", response.Numero, response.Anno);

                return response;
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA PROTOCOLLAZIONE INTERNA, {0}", ex.Message, ex));
            }
        }
    }
}
