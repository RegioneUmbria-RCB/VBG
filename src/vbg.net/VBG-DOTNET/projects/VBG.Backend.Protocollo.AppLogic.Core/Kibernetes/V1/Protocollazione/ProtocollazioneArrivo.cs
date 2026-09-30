using log4net;
using ProtocolloKibernetesService;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;


namespace VBG.Backend.Protocollo.AppLogic.Core.Kibernetes.V1.Protocollazione
{
    public class ProtocollazioneArrivo : IKibernetesProtocollazioneV1
    {
        IParametriService _parametriService;
        List<IAnagraficaAmministrazione> _mittenti;
        string _funzionario;
        IDatiProtocollo _datiProto;

        public ProtocollazioneArrivo(IParametriService parametriService, List<IAnagraficaAmministrazione> mittenti, IDatiProtocollo datiProto, string funzionario)
        {
            _parametriService = parametriService;
            _mittenti = mittenti;
            _funzionario = funzionario;
            _datiProto = datiProto;
        }

        public StatusProtocollo Protocolla(WS_AnagraficaClient ws, ILog logs, IProtocolloSerializer serializer)
        {
            try
            {
                if (_mittenti.Count.Equals(0))
                    throw new Exception("MITTENTE NON VALORIZZATO");

                logs.InfoFormat("CHIAMATA A PROTOCOLLAZIONE IN ARRIVO, CODICE ISTAT: {0}, MITTENTE: {1}, INDIRIZZO: {2}, OGGETTO: {3}, UFFICIO PROTOCOLLANTE: {4}, UO: {5}, RUOLO: {6}, FUNZIONARIO: {7}", _parametriService.IstatEnte, _mittenti[0].NomeCognome, _mittenti[0].Indirizzo, _datiProto.ProtoIn.Oggetto, _parametriService.UfficioProtocollante, _datiProto.Uo, _datiProto.Ruolo, _funzionario);
                var response = ws.set4ProtocolloEntrata(
                                    this._parametriService.IstatEnte.Value,
                                    _mittenti[0].NomeCognome,
                                    _mittenti[0].Indirizzo,
                                    _datiProto.ProtoIn.Oggetto,
                                    this._parametriService.UfficioProtocollante,
                                    _datiProto.Uo,
                                    _datiProto.Ruolo, "", "", false, _funzionario, "", "");

                serializer.LogAndValidate(ProtocolloLogsConstants.ProtocollazioneResponseFileName, response);

                if (response.CodStato.Equals(-1))
                    throw new Exception(response.DescrStato);

                logs.InfoFormat("PROTOCOLLAZIONE IN ARRIVO AVVENUTA CON SUCCESSO PROTOCOLLO NUMERO: {0}, ANNO: {1}", response.Numero, response.Anno);

                return response;
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA PROTOCOLLAZIONE IN ENTRATA, {0}", ex.Message, ex));
            }
        }
    }
}
