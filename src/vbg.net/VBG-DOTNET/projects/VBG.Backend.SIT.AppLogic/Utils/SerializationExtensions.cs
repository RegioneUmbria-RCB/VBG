using System;
using System.IO;
using System.Xml.Serialization;

namespace VBG.Backend.SIT.AppLogic.Utils
{
    public static class SerializationExtensions
    {
        public static string XmlSerializeToString(this object objectInstance)
        {
            var serializer = new XmlSerializer(objectInstance.GetType());

            using (var memoryStream = new MemoryStream())
            {
                using (var streamWriter = new StreamWriter(memoryStream, System.Text.Encoding.UTF8))
                {
                    serializer.Serialize(streamWriter, objectInstance);

                    memoryStream.Seek(0, SeekOrigin.Begin);
                    var streamReader = new StreamReader(memoryStream, System.Text.Encoding.UTF8);
                    return streamReader.ReadToEnd();
                }
            }
            /*


			var serializer = new XmlSerializer(objectInstance.GetType());
			var sb = new StringBuilder();

			using (TextWriter writer = new StringWriter(sb))
			{
				serializer.Serialize(writer, objectInstance);
			}

			return sb.ToString();*/
        }

        public static T XmlDeserializeFromString<T>(string objectData)
        {
            if (objectData == null)
                return default(T);

            return (T)XmlDeserializeFromString(objectData, typeof(T));
        }


        public static object XmlDeserializeFromString(string objectData, Type type)
        {
            var serializer = new XmlSerializer(type);
            object result;

            using (TextReader reader = new StringReader(objectData))
            {
                result = serializer.Deserialize(reader);
            }

            return result;
        }

        public static string ToXmlString(this object objectInstance)
        {
            return XmlSerializeToString(objectInstance);
        }

    }
}
