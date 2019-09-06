using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Xml;

namespace Init.Sigepro.FrontEnd.Pagamenti.MIP
{
    [DataContract(Namespace="", Name="ErrorData")]
    public class MIPError
    {
        [DataMember(Order=0)]
        public string NumeroOperazione{get;set;}

        [DataMember(Order = 1)]
        public string IDOperazione { get; set; }

        [DataMember(Order = 2)]
        public string CodiceErrore { get; set; }

        [DataMember(Order = 3)]
        public string ErroreD { get; set; }

        public static MIPError FromXmlString(string xml)
        {
            using (var stream = new MemoryStream())
            {
                byte[] data = Encoding.UTF8.GetBytes(xml);
                stream.Write(data, 0, data.Length);
                stream.Seek(0, SeekOrigin.Begin);
                var deserializer = new DataContractSerializer(typeof(MIPError));
                return (MIPError)deserializer.ReadObject(stream);
            }
        }

        public object ToXmlString()
        {
            using (var stringwriter = new StringWriter())
            {
                var serializer = new DataContractSerializer(this.GetType());
                using (var output = new StringWriter())
                {
                    using (var writer = new XmlTextWriter(output) { Formatting = Formatting.Indented })
                    {
                        serializer.WriteObject(writer, this);
                        return output.GetStringBuilder().ToString();
                    }
                }
            }
        }
    }
}
