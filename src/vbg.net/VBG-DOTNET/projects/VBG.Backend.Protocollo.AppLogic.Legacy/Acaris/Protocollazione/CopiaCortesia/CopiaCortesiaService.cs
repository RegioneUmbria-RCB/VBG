using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Client;
using System.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using Init.SIGePro.Protocollo.AcarisOfficialBookServicePort;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Protocollazione.CopiaCortesia
{
    public class CopiaCortesiaService
    {
        private readonly IProtocolloSerializer _serializer;
        private readonly ParametriRegoleInfo _configurazione;

        public CopiaCortesiaService(IProtocolloSerializer serializer, ParametriRegoleInfo configurazione)
        {
            this._serializer = serializer;
            this._configurazione = configurazione;
        }

        public void Invia(string idRegistrazione)
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
                        identificatoreRegistrazione = new ObjectIdType { value = idRegistrazione },
                        info = new InfoInvioSegnatura
                        {
                            forzaturaAssenzaSigillo = true,
                            invioMultiplo = true,
                        }
                    };

                    this._serializer.LogAndValidate("InviaSegnaturaRegistrazioneRequest.xml", request, "Invia: Inizio chiamata a inviaSegnaturaRegistrazione");

                    var response = ws.inviaSegnaturaRegistrazione(request);

                    this._serializer.LogAndValidate("InviaSegnaturaRegistrazioneResponse.xml", response, "Invia: Inizio chiamata a inviaSegnaturaRegistrazione");

                }
            }
        }
    }
}
