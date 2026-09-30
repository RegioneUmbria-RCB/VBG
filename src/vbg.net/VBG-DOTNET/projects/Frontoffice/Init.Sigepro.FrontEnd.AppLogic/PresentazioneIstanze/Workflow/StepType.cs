using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow.V2;
using System.Linq;
using System.Xml;
using System.Xml.Serialization;


namespace Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow
{
    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.3038")]
    [System.SerializableAttribute()]
    [System.Xml.Serialization.XmlTypeAttribute(Namespace = "http://www.sigepro.it/frontoffice")]
    public partial class StepType
    {
        public WorkflowSteps? StepId { get; set; }

        public string Title { get; set; } = "";
        [XmlIgnore()]
        public string Description { get; set; } = "";

        [XmlElement("Description", type: typeof(CDATA))]
        public CDATA DescriptionCDATA_NonUsare
        {
            get => new CDATA(Description);
            set => Description = value?.Text ?? "";
        }

        public string Control { get; set; } = "";

        [XmlIgnore]
        public bool Disabled { get; set; }

        [XmlElement("Disabled")]
        public bool DisabledSpecified
        {
            get { return Disabled; }
            set { Disabled = value; }
        }

        public bool ShouldSerializeDisabledSpecified()
        {
            return Disabled; // serialize only if true
        }


        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("ControlProperty")]
        public PropertyValueType[] ControlProperties { get; set; } = new PropertyValueType[0];


        /// <summary>
        /// Implementa la deep copy dello step corrente
        /// </summary>
        /// <returns></returns>
        public StepType Clone()
        {
            return new StepType
            {
                Control = this.Control,
                ControlProperties = this.ControlProperties == null ?
                                    new PropertyValueType[0] :
                                    this.ControlProperties.Select(x => x.Clone()).ToArray(),
                Description = this.Description,
                Title = this.Title,
                Disabled = this.Disabled,
                StepId = this.StepId
            };

        }
    }

}
