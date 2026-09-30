using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Xml.Serialization;


namespace Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow
{
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.3038")]
    [System.SerializableAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(Namespace = "http://www.sigepro.it/frontoffice")]
    [System.Xml.Serialization.XmlRootAttribute("ProcessSteps", Namespace = "http://www.sigepro.it/frontoffice", IsNullable = false)]
    public partial class StepCollectionType
    {
        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("Step")]
        public StepType[] Steps { get; set; } = Array.Empty<StepType>();

        public StepCollectionType()
        {

        }

        public StepCollectionType(IEnumerable<StepType> steps)
        {
            this.Steps = steps.ToArray();
        }

        public bool IsFirstStep(int stepId)
        {
            return stepId == 0;
        }
        public bool IsLastStep(int stepId)
        {
            return stepId >= (this.Steps.Length - 1);
        }

        public bool IsStepDisabilitato(int stepId)
        {
            return this.Steps[stepId].Disabled;
        }

        public void RimuoviStepsDisabilitati()
        {
            this.Steps = this.Steps.Where(s => s.Disabled == false).ToArray();
        }

        internal string ToXmlString()
        {
            try
            {
                // Crea un oggetto XmlSerializer per la classe StepCollectionType
                var serializer = new XmlSerializer(typeof(StepCollectionType));

                using (StringWriter writer = new StringWriter())
                {
                    serializer.Serialize(writer, this);
                    return writer.ToString(); // Restituisce l'XML come stringa
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Errore in SerializeStepCollection: {ex.Message}");
                return string.Empty;
            }
        }
    }

}
