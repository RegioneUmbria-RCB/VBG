using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneInterventi;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using System.IO;
using System.Text;
using System.Threading.Tasks;
using System.Xml;
using System.Xml.Serialization;

namespace Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow
{
    public class WorkflowEditService : IWorkflowEditService
    {
        private readonly IOggettiService _oggettiService;
        private readonly IConfigurazione<ParametriWorkflow> _configurazioneWorkflow;
        private readonly IInterventiRepository _interventiRepository;
        private readonly InterventiServiceCreator _serviceCreator;

        public WorkflowEditService(IOggettiService oggettiService, IConfigurazione<ParametriWorkflow> configurazioneWorkflow, IInterventiRepository interventiRepository, InterventiServiceCreator serviceCreator)
        {
            this._oggettiService = oggettiService;
            this._configurazioneWorkflow = configurazioneWorkflow;
            this._interventiRepository = interventiRepository;
            this._serviceCreator = serviceCreator;
        }

        public void AggiornaXmlWorkflowBase(string xml)
        {
            if (string.IsNullOrEmpty(xml))
                return;

            this._oggettiService.AggiornaOggetto(this._configurazioneWorkflow.Parametri.DefaultWorkflowCodiceOggetto.Value, Encoding.UTF8.GetBytes(xml));
        }

        public async Task AggiornaXmlWorkflowInterventoAsync(string xml, int codiceIntervento)
        {
            var codiceOggetto = this._interventiRepository.GetCodiceOggettoWorkflow(codiceIntervento);

            if (codiceOggetto == null)
            {
                await this.CreaWorkflowInterventoAsync(codiceIntervento, xml);
            }
            else
            {
                this._oggettiService.AggiornaOggetto(codiceOggetto.Value, Encoding.UTF8.GetBytes(xml));
            }
        }

        public StepCollectionType DeserializeXmlWithCData(string xml)
        {
            XmlDocument doc = new XmlDocument();
            doc.LoadXml(xml);

            XmlNodeList? allNodes = doc.SelectNodes("//*");

            // Trova i nodi che contengono CDATA e mantiene il contenuto originale
            if (allNodes != null && allNodes.Count > 0)
            {
                foreach (XmlNode node in allNodes)
                {
                    foreach (XmlNode child in node.ChildNodes)
                    {
                        if (child.NodeType == XmlNodeType.CDATA)
                        {
                            if (child.ParentNode != null)
                            {
                                child.ParentNode.InnerText = child.OuterXml;
                            }
                        }
                    }
                }
            }

            using (StringReader stringReader = new StringReader(doc.OuterXml))
            {
                XmlSerializer serializer = new XmlSerializer(typeof(StepCollectionType));
                return (StepCollectionType)serializer.Deserialize(stringReader)!;
            }
        }

        private async Task CreaWorkflowInterventoAsync(int idIntervento, string xml)
        {
            var codiceOggettoWorkflow = await this._oggettiService.InserisciOggettoAsync($"Workflow_intervento.xml", "application/xml", Encoding.UTF8.GetBytes(xml));

            await this._serviceCreator.Call(async ws =>
            {
                await ws.Service.AggiornaWorkflowInterventoAsync(ws.Token, idIntervento, codiceOggettoWorkflow);
            });
        }
    }
}
