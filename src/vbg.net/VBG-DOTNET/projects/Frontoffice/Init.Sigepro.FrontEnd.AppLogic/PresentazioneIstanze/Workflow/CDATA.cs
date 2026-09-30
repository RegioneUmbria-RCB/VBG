using System;
using System.Xml;
using System.Xml.Schema;
using System.Xml.Serialization;

namespace Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze
{

    public class CDATA : IXmlSerializable
    {
        private string text;
        public CDATA()
        { }

        public CDATA(string text)
        {
            this.text = text;
        }

        public string Text
        {
            get { return this.text; }
        }

        /// <summary>
        /// Interface implementation not used here.
        /// </summary>
        XmlSchema IXmlSerializable.GetSchema()
        {
            return null;
        }

        /// <summary>
        /// Interface implementation, which reads the content of the CDATA tag
        /// </summary>
        void IXmlSerializable.ReadXml(XmlReader reader)
        {
            this.text = reader.ReadElementString();
        }

        /// <summary>
        /// Interface implementation, which writes the CDATA tag to the xml
        /// </summary>
        void IXmlSerializable.WriteXml(XmlWriter writer)
        {
            if (!String.IsNullOrEmpty(this.text))
            {
                writer.WriteCData(this.text);
            }
        }
    }
}
