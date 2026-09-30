using System;
using System.Linq;
using System.Xml;
using System.Xml.Serialization;


namespace Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow
{
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.3038")]
    [System.SerializableAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(Namespace = "http://www.sigepro.it/frontoffice")]
    public partial class PropertyValueType
    {

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public string name { get; set; }

        /// <remarks/>
        //[System.Xml.Serialization.XmlTextAttribute()]
        [XmlIgnore]
        public string Value { get; set; }

        [System.Xml.Serialization.XmlText]
        public XmlNode[] ValueCDATA_NonUsare
        {
            get => new[] { new XmlDocument().CreateCDataSection(this.Value) };
            set => this.Value = value?.FirstOrDefault()?.InnerText ?? "";
        }


        /// <summary>
        /// Implementa la deep copy dello step corrente
        /// </summary>
        /// <returns></returns>
        public PropertyValueType Clone()
        {
            return new PropertyValueType { name = this.name, Value = this.Value };
        }

        public override bool Equals(object obj)
        {
            if (obj == null) return false;
            if (this.GetType() != obj.GetType()) return false;

            var typedObj = obj as PropertyValueType;

            if (!this.name.Equals(typedObj.name))
                return false;

            if (!this.Value.Equals(typedObj.Value))
                return false;

            return true;
        }

        public override int GetHashCode()
        {
            return this.name.GetHashCode() ^ (string.IsNullOrEmpty(this.Value) ? string.Empty.GetHashCode() : this.Value.GetHashCode());
        }

        public static Boolean operator ==(PropertyValueType v1, PropertyValueType v2)
        {

            if ((object)v1 == null)
                if ((object)v2 == null)
                    return true;
                else
                    return false;

            return (v1.Equals(v2));
        }

        public static Boolean operator !=(PropertyValueType v1, PropertyValueType v2)
        {
            return !(v1 == v2);
        }

    }

}
