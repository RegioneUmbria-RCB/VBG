using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Xml;

namespace Init.Sigepro.FrontEnd.Pagamenti
{
    public class Serializer
    {
        internal static string SerializeToXmlString(object objectToSerialize)
        {
            using (var stringwriter = new StringWriter())
            {
                var serializer = new DataContractSerializer(objectToSerialize.GetType());
                using (var output = new StringWriter())
                {
                    using (var writer = new XmlTextWriter(output) { Formatting = Formatting.Indented })
                    {
                        serializer.WriteObject(writer, objectToSerialize);
                        return output.GetStringBuilder().ToString();
                    }
                }
            }
        }

    }
}
