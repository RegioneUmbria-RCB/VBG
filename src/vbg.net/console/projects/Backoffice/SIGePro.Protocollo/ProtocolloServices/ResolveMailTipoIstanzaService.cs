using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.MailTipoService;
using PersonalLib2.Data;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Manager;

namespace Init.SIGePro.Protocollo.ProtocolloServices
{
    public class ResolveMailTipoIstanzaService : BaseResolveMailTipoService, IResolveMailTipoService
    {
        ResolveDatiProtocollazioneService _datiProtocollazioneService;
        IParametriInterventoProtocolloService _param;
        ProtocolloSerializer _serializer;

        public ResolveMailTipoIstanzaService(IParametriInterventoProtocolloService param, ResolveDatiProtocollazioneService datiProtocollazioneService, ProtocolloLogs log, ProtocolloSerializer serializer) :
            base(log)
        {
            _datiProtocollazioneService = datiProtocollazioneService;
            _param = param;
            _serializer = serializer;

            SetMailTipo();
        }


        #region IResolveMailTipoService Members

        private void SetMailTipo()
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    string retVal = String.Empty;
                    var request = new MailtipoRequest();
                    request.token = _datiProtocollazioneService.Db.ConnectionDetails.Token;
                    _log.DebugFormat("Recupero Oggetto da mail e testi tipo, funzionalità GetOggetto, codice testo tipo restituito: {0}", _param.CodiceTestoTipo.HasValue ? _param.CodiceTestoTipo.Value.ToString() : "");
                    
                    if (!_param.CodiceTestoTipo.HasValue)
                    {
                        _log.WarnFormat("L'intervento e i suoi padri non hanno selezionato il codice e testo tipo, non è quindi possibile recuperare un valore per l'oggetto ed il corpo, sarà quindi inserito un valore di default, ma solo per l'oggetto e non per il corpo");
                        this.Oggetto = String.Concat(_param.OggettoDefault, _datiProtocollazioneService.NumeroIstanza);
                    }
                    else
                    {
                        request.codicemailtipo = _param.CodiceTestoTipo.Value;
                        request.codiceistanza = Convert.ToInt32(_datiProtocollazioneService.CodiceIstanza);

                        _serializer.Serialize(ProtocolloLogsConstants.MailTipoProtocolloSoapRequestFileName, request);

                        _log.InfoFormat("Chiamata a mail e testo tipo, vedi request su file {0}", ProtocolloLogsConstants.MailTipoProtocolloSoapResponseFileName);
                        var response = ws.Mailtipo(request);

                        _serializer.Serialize(ProtocolloLogsConstants.MailTipoProtocolloSoapResponseFileName, response);

                        if (response != null)
                        {
                            this.Oggetto = response.oggetto;
                            this.Corpo = response.corpo;
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                throw _log.LogErrorException("ERRORE GENERATO DURANTE IL RECUPERO DELL'OGGETTO DALLE MAIL E TESTI TIPO DA ISTANZA", ex);
            }

        }

        public string Oggetto
        {
            get;
            set;
        }

        public string Corpo
        {
            get;
            set;
        }

        #endregion
    }
}
