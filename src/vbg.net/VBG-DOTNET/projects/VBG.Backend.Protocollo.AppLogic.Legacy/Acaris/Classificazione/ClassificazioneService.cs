using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Client;
using System.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using Init.SIGePro.Protocollo.AcarisObjectServicePort;
using System.Linq;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Classificazione
{
    public class ClassificazioneService
    {
        private readonly IProtocolloSerializer _serializer;
        private readonly ParametriRegoleInfo _configurazione;

        public ClassificazioneService(IProtocolloSerializer serializer, ParametriRegoleInfo configurazione)
        {
            this._serializer = serializer;
            this._configurazione = configurazione;
        }

        public Classificazione GetClassificazione(string idClassificazione)
        {
            var client = new ObjectWSClient(this._configurazione.ObjectPortUrl, this._configurazione.AccessToken);

            using (var ws = client.CreaWebService())
            {
                using (OperationContextScope scope = new OperationContextScope(ws.InnerChannel))
                {
                    client.AggiungiTokenAContextScope();

                    var request = new getPropertiesMassive
                    {
                        repositoryId = new ObjectIdType { value = this._configurazione.RepositoryID.IdAcaris },
                        principalId = new PrincipalIdType { value = this._configurazione.PrincipalID.IdAcaris },
                        identifiers = new ObjectIdType[]
                        {
                            new ObjectIdType
                            {
                                value = idClassificazione
                            }
                        },
                        filter = new PropertyFilterType { filterType = enumPropertyFilter.all }
                    };

                    this._serializer.LogAndValidate("ClassificazioneRequest.xml", request, "Inizio chiamata a getPropertiesMassive");

                    var response = ws.getPropertiesMassive(request);

                    this._serializer.LogAndValidate("ClassificazioneResponse.xml", response, "Fine chiamata a getPropertiesMassive");

                    var properties = response
                                       .FirstOrDefault()?
                                       .properties;

                    if (properties == null)
                    {
                        return null;
                    }

                    return new Classificazione
                    {
                        IndiceClassificazione = this.ProperyValue(properties, "indiceClassificazione"),
                        IndiceClassificazioneEstesa = this.ProperyValue(properties, "indiceClassificazioneEstesa")
                    };
                }
            }
        }

        private string ProperyValue(PropertyType[] props, string propertyName)
        {
            if (props == null)
            {
                return null;
            }

            return props
                    .Where(x => x.queryName.propertyName == propertyName)
                    .Select(y => y.value)
                    .FirstOrDefault()?
                    .FirstOrDefault();
        }
    }
}
