using Init.Utils;
using System;
using System.Data;
using System.IO;

namespace Parser
{
    public class XMLParser
    {

        public DataSet Parse()
        {
            DataSet ds = null;

            try
            {
                if (this.XmlSchema != null)
                {
                    ds = new DataSet();

                    MemoryStream ms = StreamUtils.StringToStream(this.XmlSchema);
                    ms.Seek(0, SeekOrigin.Begin);
                    ds.ReadXmlSchema(ms);

                    if (!String.IsNullOrEmpty(this.XmlText))
                    {
                        ds.ReadXml(StreamUtils.StringToStream(this.XmlText));
                    }
                }
            }
            catch (Exception ex)
            {
                throw new Exception("Errore generato durante il parsing del file xml ricevuto in ingresso. Modulo: XMLParser. Metodo: Parse. Messaggio: " + ex.Message + "\r\n");
            }

            return (ds);
        }

        public string XmlText { get; set; } = String.Empty;
        public string XmlSchema { get; set; } = String.Empty;
    }// END CLASS DEFINITION XMLParser

} // Parser