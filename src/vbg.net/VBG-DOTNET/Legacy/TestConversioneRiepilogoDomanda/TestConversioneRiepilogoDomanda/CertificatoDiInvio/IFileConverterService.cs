using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace TestConversioneRiepilogoDomanda.CertificatoDiInvio
{
	public interface IFileConverterService
	{
		BinaryFile Converti(byte[] datiOrigine, string estensioneOrigine, string estensioneDestinazione);
	}
}
