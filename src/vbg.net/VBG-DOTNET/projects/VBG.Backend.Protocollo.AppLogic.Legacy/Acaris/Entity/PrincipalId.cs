using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Client;
using System.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using Init.SIGePro.Protocollo.AcarisBackofficeServicePort;
using System;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Entity
{
    public class PrincipalId : IIDAcaris
    {
        public string IdAcaris { get; }

        public PrincipalId()
        {
            //costruttore vuoto solamente per problemi di serializzazione
        }
        public PrincipalId(IProtocolloSerializer serializer, BackofficeWSClient client, RepositoryId repositoryId, string idUtente, int idAoo, int idStruttura, int idNodo, string chiave)
        {
            try
            {
                using (var ws = client.CreaWebService())
                {
                    using (OperationContextScope scope = new OperationContextScope(ws.InnerChannel))
                    {
                        client.AggiungiTokenAContextScope();

                        var request = new getPrincipalExt
                        {
                            repositoryId = new ObjectIdType { value = repositoryId.IdAcaris },
                            idUtente = new CodiceFiscaleType { value = idUtente },
                            idAOO = new IdAOOType { value = idAoo },
                            idStruttura = new IdStrutturaType { value = idStruttura },
                            idNodo = new IdNodoType { value = idNodo },
                            clientApplicationInfo = new ClientApplicationInfo { appKey = chiave }
                        };

                        serializer.LogAndValidate("GetPrincipalExtRequest.xml", request, "Inizio chiamata a getPrincipalExt");

                        var response = ws.getPrincipalExt(request);

                        serializer.LogAndValidate("GetPrincipalExtResponse.xml", response, "Fine chiamata a getPrincipalExt");

                        if (response == null)
                        {
                            return;
                        }

                        if (response.Length != 1)
                        {
                            throw new InvalidOperationException($"La chiamata al metodo getPrincipalExt ha restituito {response.Length} risultati. Impossibile individuare univocamente la configurazione da utilizzare");
                        }

                        this.IdAcaris = response[0].principalId?.value;
                    }
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"Errore nel tentativo di recuperare il principal id del repository {repositoryId.Nome}", ex);
            }
        }

        public override string ToString()
        {
            return $"PrincipalId: [idAcaris={this.IdAcaris}]";
        }
    }
}
