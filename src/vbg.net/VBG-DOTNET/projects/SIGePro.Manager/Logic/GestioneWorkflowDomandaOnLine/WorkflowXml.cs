using System;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.Logic.GestioneWorkflowDomandaOnLine
{

    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.3038")]
    [System.SerializableAttribute()]
    //[System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(Namespace = "http://www.sigepro.it/frontoffice")]
    [System.Xml.Serialization.XmlRootAttribute("ProcessSteps", Namespace = "http://www.sigepro.it/frontoffice", IsNullable = false)]
    public partial class WorkflowStepsCollection
    {
        [System.Xml.Serialization.XmlElement("Sections")]
        public SectionGroup Sections { get; set; } = new SectionGroup();

        /// <remarks/>
        [System.Xml.Serialization.XmlElement("Step")]
        public StepType[] Steps { get; set; } = Array.Empty<StepType>();
    }

    [XmlType(Namespace = "http://www.sigepro.it/frontoffice")]
    public partial class SectionGroup
    {
        [XmlElement("Section")]
        public WorkflowSection[] Sections { get; set; } = Array.Empty<WorkflowSection>();
    }

    [XmlType(Namespace = "http://www.sigepro.it/frontoffice")]
    public class WorkflowSection
    {
        [XmlAttribute(attributeName: "id")]
        public string Id { get; set; }

        [XmlAttribute(attributeName: "nascondi-menu-navigazione")]
        public bool NascondiMenNavigazione { get; set; } = false;

        [XmlElement]
        public string Title { get; set; } = "";

        [XmlElement]
        public string Description { get; set; } = "";
    }

    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.3038")]
    [System.SerializableAttribute()]
    [System.Xml.Serialization.XmlTypeAttribute(Namespace = "http://www.sigepro.it/frontoffice")]
    public partial class StepType
    {
        [System.Xml.Serialization.XmlAttribute(AttributeName = "sezione")]
        public string IdSezione { get; set; }
        [System.Xml.Serialization.XmlAttribute(AttributeName = "nascondi-su-riepilogo")]
        public bool NascondiSuRiepilogo { get; set; }
        public string Title { get; set; }
        public string Description { get; set; }
        public string Control { get; set; }

        [System.Xml.Serialization.XmlElementAttribute("ControlProperty")]
        public PropertyValueType[] ControlProperties { get; set; } = Array.Empty<PropertyValueType>();
    }

    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.3038")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(Namespace = "http://www.sigepro.it/frontoffice")]
    public partial class PropertyValueType
    {
        /// <remarks/>
        [System.Xml.Serialization.XmlAttribute(AttributeName = "name")]
        public string Name { get; set; }

        /// <remarks/>
        [System.Xml.Serialization.XmlText()]
        public string Value { get; set; }
    }
}

