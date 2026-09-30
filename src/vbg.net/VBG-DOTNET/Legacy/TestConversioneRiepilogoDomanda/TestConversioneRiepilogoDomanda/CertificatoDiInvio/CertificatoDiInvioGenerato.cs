using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace TestConversioneRiepilogoDomanda.CertificatoDiInvio
{
	public class CertificatoDiInvioGenerato
	{
		private   BinaryFile _file;

		public CertificatoDiInvioGenerato(BinaryFile file)
		{
			this._file = file;
		}

		public byte[] GetDati()
		{
			return this._file.FileContent;
		}
	}
}
