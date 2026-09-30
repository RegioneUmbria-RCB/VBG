using log4net;
using ProtocolloKibernetesService;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.Kibernetes.V1.Protocollazione
{
    public class ProtocollazioneInterna : IKibernetesProtocollazioneV1
    {
        IParametriService _parametriService;
        string _funzionario;
        IDatiProtocollo _datiProto;

        public ProtocollazioneInterna(IParametriService parametriService, IDatiProtocollo datiProto, string funzionario)
        {
            _parametriService = parametriService;
            _funzionario = funzionario;
            _datiProto = datiProto;

        }

        public StatusProtocollo Protocolla(WS_AnagraficaClient ws, ILog logs, IProtocolloSerializer serializer)
        {
            try
            {
                logs.InfoFormat("CHIAMATA A PROTOCOLLAZIONE INTERNA, CODICE ISTAT: {0}, MITTENTE: {1}, INDIRIZZO: {2}, OGGETTO: {3}, UFFICIO PROTOCOLLANTE: {4}, UO: {5}, RUOLO: {6}, FUNZIONARIO: {7}", this._parametriService.IstatEnte, _datiProto.AmministrazioniInterne[0].PROT_UO, _datiProto.AmministrazioniInterne[0].INDIRIZZO, _datiProto.ProtoIn.Oggetto, this._parametriService.UfficioProtocollante, _datiProto.Uo, _datiProto.Ruolo, _funzionario);
                var response = ws.set4ProtocolloInterno(
                                    this._parametriService.IstatEnte.Value,
                                    _datiProto.Uo,
                                    _datiProto.Amministrazione.INDIRIZZO,
                                    _datiProto.ProtoIn.Oggetto,
                                    this._parametriService.UfficioProtocollante,
                                    _datiProto.AmministrazioniInterne[0].PROT_UO,
                                    _datiProto.AmministrazioniInterne[0].PROT_RUOLO,
                                    "", "", false, _funzionario, "", "");

                serializer.LogAndValidate(ProtocolloLogsConstants.ProtocollazioneResponseFileName, response);

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
