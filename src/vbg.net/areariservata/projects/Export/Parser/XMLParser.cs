using System;
using System.Data;
using System.Xml;
using Init.Utils;
using System.IO;

namespace Parser
{
	public class XMLParser
	{

		public DataSet Parse( )
		{
			DataSet ds = null;

			try
			{
				if ( _XmlSchema != null )
				{
					ds = new DataSet();

					MemoryStream ms = StreamUtils.StringToStream( _XmlSchema );
					ms.Seek(0,SeekOrigin.Begin);
					ds.ReadXmlSchema(ms);
				
					if ( ! StringChecker.IsStringEmpty( _XmlText ))
					{
						ds.ReadXml(StreamUtils.StringToStream(_XmlText));
					}
				}
			}
			catch( Exception ex )
			{
				throw new Exception("Errore generato durante il parsing del file xml ricevuto in ingresso. Modulo: XMLParser. Metodo: Parse. Messaggio: "+ex.Message+"\r\n");
			}

			return(ds);
		}

		private string _XmlText = String.Empty;
		public string XmlText
		{
			get { return _XmlText; }
			set	{ _XmlText = value; }
		}


		private string _XmlSchema = String.Empty;
		public string XmlSchema
		{
			get { return _XmlSchema; }
			set	{ _XmlSchema = value; }
		}
	}// END CLASS DEFINITION XMLParser

} // Parser