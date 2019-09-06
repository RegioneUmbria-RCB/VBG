using System;

namespace SIGePro.Net.WebServices.WSSIGeProImport.Utils
{
	/// <summary>
	/// Descrizione di riepilogo per BinaryAttachment.
	/// </summary>
	public class BinaryAttachment
	{
		private string m_fileName = String.Empty;
		private byte[] m_fileContent = null;

		public string FileName
		{
			get { return m_fileName; }
			set { m_fileName = value.ToUpper(); }
		}

		public byte[] FileContent
		{
			get { return m_fileContent; }
			set { m_fileContent = value; }
		}


		public BinaryAttachment()
		{
		}

		public BinaryAttachment(string fileName, byte[] fileContent)
		{
			FileName = fileName;
			FileContent = fileContent;
		}
	}
}