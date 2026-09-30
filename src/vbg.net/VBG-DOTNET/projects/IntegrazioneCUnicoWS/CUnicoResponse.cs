using IntegrazioneCUnicoWS.CUnicoWS;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace IntegrazioneCUnicoWS
{
   public class CUnicoResponse
    {
        protected static string XmlSerializeToString(object objectInstance) 
        {
            var overrides = new OverrideXml()
                    .Override<inserisciConcessioneRisposta>()
                    .Member("modelloPagoPA").XmlIgnore()
                    .Commit();

            var serializer = new XmlSerializer(objectInstance.GetType(), overrides);

            var memoryStream = new MemoryStream();
            var streamWriter = new StreamWriter(memoryStream, System.Text.Encoding.UTF8);

            serializer.Serialize(streamWriter, objectInstance);

            memoryStream.Seek(0, SeekOrigin.Begin);
            var streamReader = new StreamReader(memoryStream, System.Text.Encoding.UTF8);
            return streamReader.ReadToEnd();
        }
    }
}
