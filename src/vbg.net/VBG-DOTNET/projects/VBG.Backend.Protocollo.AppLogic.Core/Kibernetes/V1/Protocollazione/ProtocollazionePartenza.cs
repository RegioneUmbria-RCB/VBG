using log4net;
using ProtocolloKibernetesService;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.Kibernetes.V1.Protocollazione
{
    public class ProtocollazionePartenza : IKibernetesProtocollazioneV1
    {
        IParametriService _parametriService;
        List<IAnagraficaAmministrazione> _destinatari;
        string _funzionario;
        IDatiProtocollo _datiProto;

        public ProtocollazionePartenza(IParametriService parametriService, List<IAnagraficaAmministrazione> destinatari, IDatiProtocollo datiProto, string funzionario)
        {
            _parametriService = parametriService;
            _destinatari = destinatari;
            _funzionario = funzionario;
            _datiProto = datiProto;
        }

        public StatusProtocollo Protocolla(WS_AnagraficaClient ws, ILog logs, IProtocolloSerializer serializer)
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

                logs.InfoFormat("CHIAMATA A PROTOCOLLAZIONE IN PARTENZA, CODICE ISTAT: {0}, PRIMO DESTINATARIO: {1}, INDIRIZZO PRIMO DESTINATARIO: {2}, OGGETTO: {3}, UFFICIO PROTOCOLLANTE: {4}, SECONDO DESTINATARIO: {5}, INDIRIZZO SECONDO DESTINATARIO: {6}, FUNZIONARIO: {7}", this._parametriService.IstatEnte, _destinatari[0].NomeCognome, _destinatari[0].Indirizzo, _datiProto.ProtoIn.Oggetto, this._parametriService.UfficioProtocollante, secondoDestinatario, indirizzoSecondoDestinatario, _funzionario);
                var response = ws.set4ProtocolloUscita(
                                    this._parametriService.IstatEnte.Value,
                                    _destinatari[0].NomeCognome,
                                    _destinatari[0].Indirizzo,
                                    _datiProto.ProtoIn.Oggetto,
                                    this._parametriService.UfficioProtocollante,
                                    secondoDestinatario,
                                    indirizzoSecondoDestinatario,
                                    false, _funzionario, "", "");

                serializer.LogAndValidate(ProtocolloLogsConstants.ProtocollazioneResponseFileName, response);

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
