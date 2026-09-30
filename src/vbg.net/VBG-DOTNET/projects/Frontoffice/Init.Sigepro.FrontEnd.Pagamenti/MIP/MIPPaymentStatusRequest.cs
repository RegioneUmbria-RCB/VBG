using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Xml;
using Init.Sigepro.FrontEnd.Infrastructure.Serialization;

namespace Init.Sigepro.FrontEnd.Pagamenti.MIP
{
    [DataContract(Name = "PaymentStatus", Namespace = "")]
    public class MIPPaymentStatusRequest
    {
        [DataMember(Order=1)]
        public string PortaleID { get; set; }

        [DataMember(Order = 0)]
        public string NumeroOperazione { get; set; }

        [DataMember(Order = 2)]
        public string RitornaDatiSpecifici { get; set; }

        //internal string ToXmlString()
        //{
        //    using (var stringwriter = new StringWriter())
        //    {
        //        var serializer = new DataContractSerializer(this.GetType());
        //        using (var output = new StringWriter())
        //        {
        //            using (var writer = new XmlTextWriter(output) { Formatting = Formatting.Indented })
        //            {
        //                serializer.WriteObject(writer, this);
        //                return output.GetStringBuilder().ToString();
        //            }
        //        }
        //    }
        //}

        //public static MIPPaymentStatus FromXmlString(string xml)
        //{
        //    using (var stream = new MemoryStream())
        //    {
        //        byte[] data = Encoding.UTF8.GetBytes(xml);
        //        stream.Write(data, 0, data.Length);
        //        stream.Position = 0;
        //        var deserializer = new DataContractSerializer(typeof(MIPPaymentStatus));
        //        return (MIPPaymentStatus)deserializer.ReadObject(stream);
        //    }
        //}
    }
}
