using System;
using System.IO;
using System.Xml.Serialization;

namespace Init.Sigepro.FrontEnd.Infrastructure.Serialization
{
    public static class SerializationExtension
    {
        public static string ToXmlString<T>(this T cls)
        {
            try
            {
                if (cls == null)
                {
                    return String.Empty;
                }

                using (var stringwriter = new StringWriter())
                {
                    var serializer = new XmlSerializer(cls.GetType());
                    serializer.Serialize(stringwriter, cls);

                    return stringwriter.GetStringBuilder().ToString();

                }
            }
            catch (Exception)
            {
                return $"Errore nella serializzaizone della classe {typeof(T)}";
            }
        }

        public static T ClassFromXmlString<T>(this string cls) where T : class
        {
            if (String.IsNullOrEmpty(cls))
            {
                return (T)null;
            }

            try
            {
                using (var sr = new StringReader(cls))
                {
                    var serializer = new XmlSerializer(typeof(T));
                    return (T)serializer.Deserialize(sr);
                }
            }
            catch (Exception)
            {
                return (T)null;
            }
        }
    }
}
