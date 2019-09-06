using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;
using System.IO;

namespace Init.SIGePro.Sit.Utils
{
	public static class SerializationExtensions
	{
		public static string XmlSerializeToString(this object objectInstance)
		{
			var serializer = new XmlSerializer(objectInstance.GetType());

			var memoryStream = new MemoryStream();
			var streamWriter = new StreamWriter(memoryStream, System.Text.Encoding.UTF8);

			serializer.Serialize(streamWriter, objectInstance);

			memoryStream.Seek(0, SeekOrigin.Begin);
			var streamReader = new StreamReader(memoryStream, System.Text.Encoding.UTF8);
			return streamReader.ReadToEnd();
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
	}
}
