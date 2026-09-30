using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Client;
using System.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using Init.SIGePro.Protocollo.AcarisOfficialBookServicePort;
using System;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Mail
{
    public class MailService
    {
        private readonly IProtocolloSerializer _serializer;
        private readonly ParametriRegoleInfo _configurazione;
        private const bool _invioMultiplo = false;
        private const bool _forzaturaAssenzaSigillo = false;
        private const bool _invioNonPrioritario = true;
        private const bool _copiaLavoro = false;

        public MailService(IProtocolloSerializer serializer, ParametriRegoleInfo configurazione)
        {
            this._serializer = serializer;
            this._configurazione = configurazione;
        }

        public void InviaMail(string pecMittente, string idProtocollo)
        {
            var client = new OfficialBookWSClient(this._configurazione.OfficialBookPortUrl, this._configurazione.AccessToken);

            using (var ws = client.CreaWebService())
            {
                using (OperationContextScope scope = new OperationContextScope(ws.InnerChannel))
                {
                    client.AggiungiTokenAContextScope();

                    var request = new inviaSegnaturaRegistrazione
                    {
                        repositoryId = new ObjectIdType { value = this._configurazione.RepositoryID.IdAcaris },
                        principalId = new PrincipalIdType { value = this._configurazione.PrincipalID.IdAcaris },
                        identificatoreRegistrazione = new ObjectIdType { value = idProtocollo },
                        mittente = !String.IsNullOrEmpty(pecMittente) ? new InfoMittenteIS { casella = pecMittente } : null,
                        info = new InfoInvioSegnatura
                        {
                            invioMultiplo = _invioMultiplo,
                            forzaturaAssenzaSigillo = _forzaturaAssenzaSigillo,
                            invioNonPrioritario = _invioNonPrioritario,
                            copiaLavoro = _copiaLavoro
                        }
                    };

                    this._serializer.LogAndValidate("InviaSegnaturaRegistrazioneRequest.xml", request, "Inizio chiamata a inviaSegnaturaRegistrazione");

                    var response = ws.inviaSegnaturaRegistrazione(request);

                    this._serializer.LogAndValidate("InviaSegnaturaRegistrazioneResponse.xml", response, "Fine chiamata a inviaSegnaturaRegistrazione");
                }
            }
        }
    }
}
