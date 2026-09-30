using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Client;
using System.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using Init.SIGePro.Protocollo.AcarisRepositoryServicePort;
using System;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Entity
{
    public class RepositoryId : IIDAcaris
    {
        public string Nome { get; }

        public string IdAcaris { get; }

        public RepositoryId()
        {
            //costruttore vuoto solamente per problemi di serializzazione
        }
        public RepositoryId(IProtocolloSerializer serializer, RepositoryWsClient client, string nome)
        {
            try
            {
                using (var ws = client.CreaWebService())
                {
                    using (OperationContextScope scope = new OperationContextScope(ws.InnerChannel))
                    {
                        client.AggiungiTokenAContextScope();

                        var request = new getRepositories();

                        serializer.LogAndValidate("GetRepositoriesRequest.xml", request, "Inizio chiamata a getRepositories");

                        var response = ws.getRepositories(request);

                        serializer.LogAndValidate("GetRepositoriesResponse.xml", response, "Fine chiamata a getRepositories");

                        var repo = Array.Find(response, x => string.Equals(x.repositoryName.Trim(), nome.Trim(), StringComparison.OrdinalIgnoreCase));

                        if (repo?.repositoryId != null)
                        {
                            this.IdAcaris = repo.repositoryId.value;
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"Errore nel tentativo di recuperare l'id del repository {nome}", ex);
            }
        }

        public override string ToString()
        {
            return $"RepositoryId: [Nome={this.Nome} IdAcaris={this.IdAcaris}]";
        }
    }
}
