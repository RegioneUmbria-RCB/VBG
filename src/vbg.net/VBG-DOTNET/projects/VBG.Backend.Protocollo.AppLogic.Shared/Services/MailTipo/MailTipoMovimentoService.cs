using VBG.Shared.Infrastructure.ServiceModel;
using MailTipoService;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Services.MailTipo
{
    public class MailTipoMovimentoService : MailTipoService
    {
        ResolveDatiProtocollazioneService _datiProtocollazioneService;
        IParametriInterventoProtocolloService _param;
        ProtocolloSerializer _serializer;
        private readonly ClientMailTipoServiceCreator _clientMailTipoServiceCreator;

        public MailTipoMovimentoService(IParametriInterventoProtocolloService param, ResolveDatiProtocollazioneService datiProtocollazioneService, ProtocolloSerializer serializer, IBindingFactory bindingFactory, ProtocolloLogs log)
            : base(log)
        {
            _param = param;
            _datiProtocollazioneService = datiProtocollazioneService;
            _serializer = serializer;
            this._clientMailTipoServiceCreator = new ClientMailTipoServiceCreator(log, bindingFactory, GetMailTipoUrl());
        }

        public override MailTipoType GetMailTipo()
        {
            try
            {
                using (var ws = this._clientMailTipoServiceCreator.CreateClient())
                {
                    var oggetto = "";
                    var corpo = "";

                    var request = new MailtipoRequest();
                    request.token = _datiProtocollazioneService.Db.ConnectionDetails.Token;

                    if (!_param.CodiceTestoTipo.HasValue)
                    {
                        this.Logger.WarnFormat("Non è possibile recuperare un valore per l'oggetto ed il corpo in quanto non si è riuscito a recuperare il codice in configufazione perchè non impostato, sarà quindi inserito un valore di default, ma solo per l'oggetto e non per il corpo");
                        var numeroIstanza = string.Empty;
                        oggetto = string.Concat(_param.OggettoDefault, _datiProtocollazioneService.NumeroIstanza);
                    }
                    else
                    {
                        request.codicemailtipo = _param.CodiceTestoTipo.Value;
                        request.codicemovimento = Convert.ToInt32(_datiProtocollazioneService.CodiceMovimento);

                        _serializer.LogAndValidate(ProtocolloLogsConstants.MailTipoProtocolloSoapRequestFileName, request);

                        this.Logger.InfoFormat("Chiamata a mail e testo tipo, vedi request su file {0}", ProtocolloLogsConstants.MailTipoProtocolloSoapResponseFileName);
                        var response = ws.Service.Mailtipo(request);

                        _serializer.LogAndValidate(ProtocolloLogsConstants.MailTipoProtocolloSoapResponseFileName, response);

                        if (response != null)
                        {
                            oggetto = response.oggetto;
                            corpo = response.corpo;
                        }
                    }

                    return new MailTipoType
                    {
                        Oggetto = oggetto,
                        Corpo = corpo
                    };
                }
            }
            catch (Exception ex)
            {
                throw this.Logger.LogErrorException("ERRORE GENERATO DURANTE IL RECUPERO DELL'OGGETTO DALLE MAIL E TESTI TIPO DA MOVIMENTO", ex);
            }
        }

    }
}
