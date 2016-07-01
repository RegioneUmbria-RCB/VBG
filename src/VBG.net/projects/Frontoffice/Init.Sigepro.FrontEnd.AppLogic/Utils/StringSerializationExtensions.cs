using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;
using System.IO;

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


			if (xmlString.ToUpperInvariant().IndexOf(" ENCODING=\"UTF-16\"?>") >= 0)
			{
				buffer = Encoding.Unicode.GetBytes(xmlString);
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
			using (var ms = new MemoryStream())
			{
				var xs = new XmlSerializer(cls.GetType());
				xs.Serialize(ms, cls);

				return Encoding.Default.GetString( ms.ToArray() );
			}	
		}

		public static byte[] ToXmlByteArray<T>(this T cls)
		{
			using (var ms = new MemoryStream())
			{
				var xs = new XmlSerializer(cls.GetType());
				xs.Serialize(ms, cls);

				return ms.ToArray();
			}
		}
	}
}
