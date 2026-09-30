using Newtonsoft.Json;
using System;
using System.IO;
using System.Text;
using System.Xml;
using System.Xml.Serialization;

namespace Init.Sigepro.FrontEnd.AppLogic.Utils.SerializationExtensions
{
    public static class StringSerializationExtensions
    {
        public static T DeserializeXML<T>(this byte[] bytes)
        {
            T returnValue = default(T);

            var serial = new XmlSerializer(typeof(T));

            using (var reader = new MemoryStream(bytes))
            {
                object result = serial.Deserialize(reader);

                if (result != null && result is T)
                {
                    returnValue = ((T)result);
                }
            }

            return returnValue;
        }

        public static T DeserializeXML<T>(this string xmlString)
        {
            var buffer = new byte[0];

            if (xmlString == null)
                return default(T);

            var startString = xmlString.Substring(0, 50);

            if (startString.ToUpperInvariant().IndexOf(" ENCODING=\"UTF-16\"?>") >= 0)
            {
                buffer = Encoding.Unicode.GetBytes(xmlString);
            }
            else if ((startString.ToUpperInvariant().IndexOf(" ENCODING=\"UTF-8\"?>") >= 0))
            {
                buffer = Encoding.UTF8.GetBytes(xmlString);
            }
            else
            {
                buffer = Encoding.Default.GetBytes(xmlString);
            }

            using (var fs = new MemoryStream(buffer))
            {
                XmlSerializer xs = new XmlSerializer(typeof(T));
                return (T)xs.Deserialize(fs);
            }

        }

        public static string ToXmlString<T>(this T cls)
        {
            var xmlWriterSettings = new XmlWriterSettings
            {
                Indent = true,
                OmitXmlDeclaration = false,
                Encoding = Encoding.UTF8
            };

            using (var ms = new MemoryStream())
            using (var writer = XmlWriter.Create(ms, xmlWriterSettings))
            {
                var xs = new XmlSerializer(cls.GetType());
                xs.Serialize(writer, cls);

                return Encoding.UTF8.GetString(ms.ToArray());
            }
        }

        public static byte[] ToXmlByteArray<T>(this T cls)
        {
            var xmlWriterSettings = new XmlWriterSettings
            {
                Indent = true,
                OmitXmlDeclaration = false,
                Encoding = Encoding.UTF8
            };

            using (var ms = new MemoryStream())
            using (var writer = XmlWriter.Create(ms, xmlWriterSettings))
            {
                var xs = new XmlSerializer(cls.GetType());
                xs.Serialize(writer, cls);

                return ms.ToArray();
            }
        }

        public static string ToJsonString<T>(this T cls)
        {
            return JsonConvert.SerializeObject(cls);
        }

        public static T DeserializeJson<T>(this string json)
        {
            if (String.IsNullOrEmpty(json))
            {
                return default(T);
            }

            return JsonConvert.DeserializeObject<T>(json);
        }
    }
}
