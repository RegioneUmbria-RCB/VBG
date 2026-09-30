using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;

namespace TestConversioneRiepilogoDomanda.CertificatoDiInvio
{
	public class BinaryFile
	{
		public string FileName { get; set; }

		public string MimeType { get; set; }

		public byte[] FileContent { get; set; }

		public string Estensione
		{
			get
			{
				return Path.GetExtension(FileName);
			}
		}

		public int Size { get { return this.FileContent.Length; } }

		internal BinaryFile()
		{
		}

		public BinaryFile(string nomeFile, string mimeType, byte[] bytes)
		{
			FileName = Path.GetFileName(nomeFile);
			MimeType = mimeType;
			FileContent = bytes;
		}
		/*
		public BinaryFile(FileUpload fileUpload)
			: this(fileUpload.FileName, fileUpload.PostedFile.ContentType, fileUpload.FileBytes)
		{
			if (this.FileContent == null || this.FileContent.Length == 0)
				throw new InvalidOperationException("Il file è vuoto o non è valido");
		}

		public BinaryFile(HttpPostedFile postedFile)
			: this(postedFile.FileName, postedFile.ContentType, StreamUtils.StreamToBytes(postedFile.InputStream))
		{
			if (this.FileContent == null || this.FileContent.Length == 0)
				throw new InvalidOperationException("Il file è vuoto o non è valido");
		}*/
	}
}
