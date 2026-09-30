using VBG.Shared.Infrastructure.ServiceModel;
using MailTipoService;
using System;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Backend.Protocollo.AppLogic.Shared.Services.MailTipo;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Services.MailTipo
{
    public class MailTipoCustomService : Shared.Services.MailTipo.MailTipoService
    {
        private readonly ClientMailTipoServiceCreator _clientMailTipoServiceCreator;

        private IProtocolloSerializer _serializer { get; }
        private string _token { get; }
        private int _codiceMailTipo { get; }
        private int? _codiceIstanza { get; }
        private int? _codiceMovimento { get; }

        public MailTipoCustomService(ProtocolloLogs logger, IProtocolloSerializer serializer, IBindingFactory bindingFactory, string token, int codiceMailTipo, int? codiceIstanza, int? codiceMovimento) : base(logger)
        {
            this._serializer = serializer;
            this._token = token;
            this._codiceMailTipo = codiceMailTipo;
            this._codiceIstanza = codiceIstanza;
            this._codiceMovimento = codiceMovimento;
            this._clientMailTipoServiceCreator = new ClientMailTipoServiceCreator(logger, bindingFactory, GetMailTipoUrl());
        }

        public override MailTipoType GetMailTipo()
        {
            try
            {
                using (var ws = this._clientMailTipoServiceCreator.CreateClient())
                {
                    var request = new MailtipoRequest
                    {
                        token = this._token,
                        codicemailtipo = this._codiceMailTipo,
                        codiceistanza = this._codiceIstanza,
                        codicemovimento = this._codiceMovimento
                    };

                    this._serializer.LogAndValidate(ProtocolloLogsConstants.MailTipoProtocolloSoapRequestFileName, request);

                    Logger.InfoFormat("Chiamata a mail e testo tipo, vedi request su file {0}", ProtocolloLogsConstants.MailTipoProtocolloSoapResponseFileName);

                    var response = ws.Service.Mailtipo(request);
                    if (response != null)
                    {
                        return new MailTipoType
                        {
                            Oggetto = response.oggetto,
                            Corpo = response.corpo
                        };
                    }

                    return new MailTipoType();
                }
            }
            catch (Exception ex)
            {
                throw Logger.LogErrorException("ERRORE GENERATO DURANTE IL RECUPERO DELL'OGGETTO DALLE MAIL E TESTI TIPO DA ISTANZA", ex);
            }
        }
    }
}
