using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Client;
using System.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using Init.SIGePro.Protocollo.AcarisManagementServicePort;
using System.Linq;
using System;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Entity
{
    public class IdVitalRecordCodeMedio
    {
        public readonly string Descrizione = "MEDIO";
        public int Id { get; }

        public IdVitalRecordCodeMedio(IProtocolloSerializer serializer, ManagementWSClient client, RepositoryId repositoryId)
        {
            using (var ws = client.CreaWebService())
            {
                using (OperationContextScope scope = new OperationContextScope(ws.InnerChannel))
                {
                    client.AggiungiTokenAContextScope();

                    var request = new getVitalRecordCode
                    {
                        repositoryId = new ObjectIdType { value = repositoryId.IdAcaris }
                    };

                    serializer.LogAndValidate("GetVitalRecordCodeRequest.xml", request, "Inizio chiamata a getVitalRecordCode");

                    var response = ws.getVitalRecordCode(request);

                    serializer.LogAndValidate("GetVitalRecordCodeResponse.xml", response, "Fine chiamata a getVitalRecordCode");

                    this.Id = response
                                .Where(x => string.Equals(x.descrizione, this.Descrizione, StringComparison.OrdinalIgnoreCase))
                                .Select(x => Convert.ToInt32(x.idVitalRecordCode.value))
                                .FirstOrDefault();
                }
            }
        }
        public override string ToString()
        {
            return $"IdVitalRecordCodeMedio: [id={this.Id} Descrizione={this.Descrizione}]";
        }
    }
}
