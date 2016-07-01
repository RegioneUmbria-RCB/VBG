// -----------------------------------------------------------------------
// <copyright file="CertificatoDiInvio.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.SIGePro.Manager.Logic.MessaggiNotificaFrontoffice.IstanzaRicevuta
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
using Init.SIGePro.Manager.Logic.GestioneOggetti;
	using Init.SIGePro.Manager.Logic.GestioneConfigurazione;
	using System.Configuration;
using Init.SIGePro.Data;
	using System.IO;
	using System.Xml;
	using System.Xml.XPath;
	using Init.Utils;
	using System.Xml.Xsl;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class CertificatoDiInvio
	{
		public class CertificatoDiInvioElaborato
		{
			public readonly string Titolo;
			public readonly string Corpo;

			public CertificatoDiInvioElaborato(string titolo, string corpo)
			{
				this.Titolo = titolo;
				this.Corpo = corpo;
			}
		}

		public static CertificatoDiInvio FromConfigurazione(string software, string titolo, IConfigurazioneAreaRiservataRepository cfgRepository, IOggettiRepository oggettiRepository)
		{
			var cfg = cfgRepository.GetBySoftware(software);

			if (!cfg.CodiceoggettoFirma.HasValue)
				throw new ConfigurationException("Non esiste un modello di riepilogo domanda nella configurazione per il modulo " + software);

			var riepilogo = oggettiRepository.GetById(cfg.CodiceoggettoFirma.Value);

			if (riepilogo == null)
			{
				throw new Exception("Errore in LeggiCertificatoInvio: non è stato possibile leggere il contenuto del modello per l'invio tramite firma digitale. Codice oggetto=" + cfg.CodiceoggettoFirma);
			}

			var bufferOggetto = new byte[0];
			var encoding = InterpretaEncoding(riepilogo.OGGETTO, out bufferOggetto);

			var xslString = encoding.GetString(bufferOggetto);

			return new CertificatoDiInvio(titolo, xslString);
		}

		public static CertificatoDiInvio FromXsl(string titolo, string modelloXsl)
		{
			return new CertificatoDiInvio(titolo, modelloXsl);
		}

		private static Encoding InterpretaEncoding(byte[] bufferIn, out byte[] bufferOut)
		{
			bufferOut = bufferIn;

			// UTF8 ?
			if (bufferIn[0] == 239 &&
				bufferIn[1] == 187 &&
				bufferIn[2] == 191)
			{
				bufferOut = new Byte[bufferIn.Length - 3];

				Array.Copy(bufferIn, 3, bufferOut, 0, bufferIn.Length - 3);

				return Encoding.UTF8;
			}

			return Encoding.GetEncoding(1252); 
		}

		string _modelloXsl;
		string _titolo;

		protected CertificatoDiInvio(string titolo, string modelloXsl)
		{
			this._titolo = titolo;
			this._modelloXsl = modelloXsl;
		}

		public CertificatoDiInvioElaborato PopolaConDati(Istanze istanza)
		{
			var document = IstanzaToXPathDocument(istanza);

			var titolo = ElaboraXPath(document, this._titolo);
			var corpo = ElaboraXPath(document, this._modelloXsl);

			return new CertificatoDiInvioElaborato(titolo, corpo);
		}

		private XPathDocument IstanzaToXPathDocument(Istanze istanza)
		{
			var istanzaSerializzata = StreamUtils.SerializeClass(istanza);			

			var istanzaTextReader = new StringReader(istanzaSerializzata);
			var xmlReader = new XmlTextReader(istanzaTextReader);
			return new XPathDocument(xmlReader);
		}

		private static string ElaboraXPath(XPathDocument istanzaXml, string testoXsl)
		{
			string container = @"<xsl:stylesheet xmlns:xsl=""http://www.w3.org/1999/XSL/Transform"" version=""1.0"">
<xsl:output method=""html"" />		
	<xsl:template match=""/"">{0}</xsl:template>

	<xsl:template name=""FormatDate"">
		<xsl:param name=""DateTime"" />

		<xsl:variable name=""dd"">
			<xsl:value-of select=""substring($DateTime,9,2)"" />
		</xsl:variable>

		<xsl:variable name=""mm"">
			<xsl:value-of select=""substring($DateTime,6,2)"" />
		</xsl:variable>

		<xsl:variable name=""yyyy"">
			<xsl:value-of select=""substring($DateTime,1,4)"" />
		</xsl:variable>

		<xsl:value-of select=""$dd"" />
		<xsl:value-of select=""'/'"" />
		<xsl:value-of select=""$mm"" />
		<xsl:value-of select=""'/'"" />
		<xsl:value-of select=""$yyyy"" />
	</xsl:template>

</xsl:stylesheet>";

			testoXsl = String.Format(container, testoXsl);

			//read XSLT
			TextReader tr = new StringReader(testoXsl);
			XmlTextReader xtr = new XmlTextReader(tr);
			XslTransform xslt = new XslTransform();
			xslt.Load(xtr);

			//Creo lo stream di output
			StringBuilder sb = new StringBuilder();
			TextWriter tw = new StringWriter(sb);

			xslt.Transform(istanzaXml, null, tw);

			return sb.ToString();
		}
	}
}
