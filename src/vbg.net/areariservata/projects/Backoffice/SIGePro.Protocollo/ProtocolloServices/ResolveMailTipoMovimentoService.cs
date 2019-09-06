using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Logs;
using PersonalLib2.Data;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.MailTipoService;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Manager;

namespace Init.SIGePro.Protocollo.ProtocolloServices
{
    public class ResolveMailTipoMovimentoService : BaseResolveMailTipoService, IResolveMailTipoService
    {
        ResolveDatiProtocollazioneService _datiProtocollazioneService;
        IParametriInterventoProtocolloService _param;
        ProtocolloSerializer _serializer;

        public string Oggetto { get; private set; }
        public string Corpo { get; private set; }

        public ResolveMailTipoMovimentoService(IParametriInterventoProtocolloService param, ResolveDatiProtocollazioneService datiProtocollazioneService, ProtocolloSerializer serializer, ProtocolloLogs log)
            : base(log)
        {
            _param = param;
            _datiProtocollazioneService = datiProtocollazioneService;
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
                    var request = new MailtipoRequest();
                    request.token = _datiProtocollazioneService.Db.ConnectionDetails.Token;

                    if (!_param.CodiceTestoTipo.HasValue)
                    {
                        _log.WarnFormat("Non è possibile recuperare un valore per l'oggetto ed il corpo in quanto non si è riuscito a recuperare il codice in configufazione perchè non impostato, sarà quindi inserito un valore di default, ma solo per l'oggetto e non per il corpo");
                        var numeroIstanza = String.Empty;

                        this.Oggetto = String.Concat(_param.OggettoDefault, _datiProtocollazioneService.NumeroIstanza);
                        this.Corpo = "";
                    }
                    else
                    {
                        request.codicemailtipo = _param.CodiceTestoTipo.Value;
                        request.codicemovimento = Convert.ToInt32(_datiProtocollazioneService.CodiceMovimento);

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
                throw _log.LogErrorException("ERRORE GENERATO DURANTE IL RECUPERO DELL'OGGETTO DALLE MAIL E TESTI TIPO DA MOVIMENTO", ex);
            }
        }

        #endregion
    }
}
