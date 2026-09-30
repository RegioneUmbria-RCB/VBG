using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Client;
using System.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using Init.SIGePro.Protocollo.AcarisOfficialBookServicePort;
using System;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using System.Collections.Generic;
using VBG.Backend.Protocollo.AppLogic.Shared.Metadati;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Protocollazione
{
    public class ProtocollazioneService
    {
        private readonly IProtocollazioneResolver _resolver;
        private readonly IProtocolloSerializer _serializer;
        private readonly ProtocolloLogs _logger;

        public ProtocollazioneService(IProtocolloSerializer serializer, ProtocolloLogs logger, IProtocollazioneResolver resolver)
        {
            this._serializer = serializer;
            this._logger = logger;
            this._resolver = resolver;
        }

        internal creaRegistrazioneResponse Protocolla()
        {
            try
            {
                var client = new OfficialBookWSClient(this._resolver.OfficialBookPortUrl, this._resolver.AccessToken);

                using (var ws = client.CreaWebService())
                {
                    using (OperationContextScope scope = new OperationContextScope(ws.InnerChannel))
                    {
                        client.AggiungiTokenAContextScope();

                        var request = new creaRegistrazione
                        {
                            repositoryId = new ObjectIdType { value = this._resolver.RepositoryId.IdAcaris },
                            principalId = new PrincipalIdType { value = this._resolver.PrincipalId.IdAcaris },
                            tipologiaCreazione = this._resolver.TipologiaCreazione,
                            infoRichiestaCreazione = this._resolver.InfoRichiestaCreazione
                        };

                        this._serializer.LogAndValidate("CreaRegistrazioneRequest.xml", request, "Inizio chiamata a creaRegistrazione");

                        var response = ws.creaRegistrazione(request);

                        this._serializer.LogAndValidate("CreaRegistrazioneResponse.xml", response, "Fine chiamata a creaRegistrazione");

                        return response;
                    }
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"CHIAMATA FALLITA, {ex.Message}", ex);
            }
        }

        internal DatiProtocolloResponseType CreaRegistrazioneResponseToDatiProtocolloRes(creaRegistrazioneResponse response)
        {
            return new DatiProtocolloResponseType
            {
                AnnoProtocollo = DateTime.ParseExact(response.identificazioneCreazione.dataUltimoAggiornamento.value, "dd/MM/yyyy", null).Year.ToString(),
                DataProtocollo = response.identificazioneCreazione.dataUltimoAggiornamento.value,
                Errore = null,
                IdProtocollo = response.identificazioneCreazione.registrazioneId.value,
                Messaggio = null,
                NumeroProtocollo = response.identificazioneCreazione.numero,
                Warning = null,
                Metadati = new List<ProtocolloMetadati>
                {
                    new ProtocolloMetadati
                    {
                        Metadato = "IDAOO",
                        Valore = this._resolver.IdAoo.Id.ToString()
                    },
                    new ProtocolloMetadati
                    {
                        Metadato = "IDNODO",
                        Valore = this._resolver.IdNodo.Id.ToString()
                    },
                    new ProtocolloMetadati
                    {
                        Metadato = "IDSTRUTTURA",
                        Valore = this._resolver.IdStruttura.Id.ToString()
                    },
                }
            };
        }
    }
}
