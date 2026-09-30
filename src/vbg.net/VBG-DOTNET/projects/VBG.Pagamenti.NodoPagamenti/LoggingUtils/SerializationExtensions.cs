using Newtonsoft.Json;

namespace VBG.Pagamenti.NodoPagamenti.LoggingUtils
{
    public static class SerializationExtension
    {
        public static string ToJsonString<T>(this T cls)
        {
            if (cls == null)
            {
                return "{}";
            }

            return JsonConvert.SerializeObject(cls, Formatting.None);
            /*
            using (var stringwriter = new StringWriter())
            {
                var serializer = new DataContractSerializer(cls.GetType());
                using (var output = new StringWriter())
                {
                    using (var writer = new XmlTextWriter(output) { Formatting = Formatting.None })
                    {
                        serializer.WriteObject(writer, cls);
                        return output.GetStringBuilder().ToString();
                    }
                }
            }*/
        }
        /*
        public static T ClassFromXmlString<T>(this string cls)
        {
            using (var stream = new MemoryStream())
            {
                byte[] data = Encoding.UTF8.GetBytes(cls);
                stream.Write(data, 0, data.Length);
                stream.Position = 0;
                var deserializer = new DataContractSerializer(typeof(T));
                return (T)deserializer.ReadObject(stream);
            }
        }*/
    }
}
