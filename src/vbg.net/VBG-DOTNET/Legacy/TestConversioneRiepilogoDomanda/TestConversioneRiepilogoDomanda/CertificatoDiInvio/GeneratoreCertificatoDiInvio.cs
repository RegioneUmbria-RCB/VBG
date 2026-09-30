using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace TestConversioneRiepilogoDomanda.CertificatoDiInvio
{
	public class GeneratoreCertificatoDiInvio
	{
		IFileConverterService _fileConverterService;

		public GeneratoreCertificatoDiInvio(IFileConverterService fileConverterService)
		{
			this._fileConverterService = fileConverterService;
		}


		public CertificatoDiInvioGenerato GeneraCertificato(TemplateCertificato template, DatiCertificato dati, string formatoOutput)
		{
			byte[] risultatoTrasformazione = template.ApplicaA(dati);

			BinaryFile file = _fileConverterService.Converti(risultatoTrasformazione, template.GetEstensione(), formatoOutput);

			return new CertificatoDiInvioGenerato(file);
		}
	}
}
