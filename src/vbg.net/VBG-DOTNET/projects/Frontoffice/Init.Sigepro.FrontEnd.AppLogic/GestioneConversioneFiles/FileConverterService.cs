using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService;
using Init.Sigepro.FrontEnd.Infrastructure.FileEncoding;
using log4net;
using System;
using System.IO;
using System.Xml.XPath;
using System.Xml.Xsl;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneConversioneFiles
{
    public class FileConverterService
    {
        private const string xslContainer = @"<xsl:stylesheet xmlns:xsl=""http://www.w3.org/1999/XSL/Transform"" version=""1.0"" xmlns:types=""http://sigepro.init.it/rte/types"">
<xsl:output method=""html"" />		
	<xsl:template match=""/""><xsl:text disable-output-escaping='yes'>&lt;!DOCTYPE html&gt;</xsl:text>{0}</xsl:template>

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

        private readonly IOggettiService _oggettiService;
        private readonly FileConverterServiceCreator _serviceCreator;
        private readonly ILog _log = LogManager.GetLogger(typeof(FileConverterService));

        public FileConverterService(IOggettiService oggettiService, FileConverterServiceCreator serviceCreator)
        {
            this._oggettiService = oggettiService;
            this._serviceCreator = serviceCreator;
        }

        public BinaryFile TrasformaOggettoVbg(int codiceOggetto, string xmlIstanza, FormatoConversioneEnum formato)
        {
            // TODO: passare l'oggetto come argomento del metodo
            try
            {
                this._log.DebugFormat("Inizio TrasformaOggettoVbg con codiceoggetto={0} nel formato {1}", codiceOggetto, formato);

                var oggetto = this._oggettiService.GetById(codiceOggetto);

                this._log.DebugFormat("Oggetto caricato correttamente da VBG");



                //if (oggetto.FileContent[0] == 239 && oggetto.FileContent[1] == 187 && oggetto.FileContent[2] == 191)
                //{
                //    _log.DebugFormat("Rilevato BOM UTF-8, l'intestazione verrà rimossa dal file");

                //    var tmp = new Byte[oggetto.FileContent.Length-3];

                //    Array.Copy(oggetto.FileContent, 3, tmp, 0, oggetto.FileContent.Length - 3);

                //    oggetto.FileContent = tmp;
                //}

                //var bufferOggetto = new byte[0];
                //var encoding = InterpretaEncoding( oggetto.FileContent , out bufferOggetto );

                var oggettoSenzaEncoding = UnknownEncodingToString.Convert(oggetto.FileContent);

                return this.TrasformaEConverti(xmlIstanza, oggettoSenzaEncoding, oggetto.Estensione.ToUpper(), formato);
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("TrasformaOggettoVbg: Errore durante la trasformazione dell'oggetto vbg con codiceoggetto {0}: {1}", codiceOggetto, ex.ToString());

                throw;
            }
        }

        public byte[] ApplicaTrasformazione(string xml, string xsl)
        {
            var includiXslInContainer = !xsl.StartsWith("<?xml");

            var xslEx = xsl;

            if (includiXslInContainer)
            {
                xslEx = String.Format(xslContainer, xsl);
            }
            var stringReader = new StringReader(xslEx);

            var result = TrasformaXml(xml, stringReader);

            if (result[0] == 239 &&
                result[1] == 187 &&
                result[2] == 191)
            {
                var tmpBuffer = new Byte[result.Length - 3];

                Array.Copy(result, 3, tmpBuffer, 0, result.Length - 3);

                result = tmpBuffer;
            }

            return result;
        }


        public BinaryFile TrasformaEConverti(string xml, string xsl, string sourceFileType, FormatoConversioneEnum destFileType)
        {
            if (!xml.StartsWith("<?xml"))
            {
                xml = "<?xml version=\"1.0\"?>" + xml;
            }

            sourceFileType = sourceFileType.Replace(".", "");

            var result = this.ApplicaTrasformazione(xml, xsl);

            return this.Converti("modello." + sourceFileType, result, destFileType);
        }

        /// <summary>
        /// Effettua la conversione di un file nel formato specificato
        /// </summary>
        /// <param name="nomeFile">Nome del file da convertire</param>
        /// <param name="fileDaConvertire">Contenuto del file da convertire</param>
        /// <param name="conversioneType">Formato in cui convertire il file</param>
        /// <returns>Risultato della conversione</returns>
        public BinaryFile Converti(string nomeFile, byte[] fileDaConvertire, FormatoConversioneEnum conversioneType)
        {
            this._log.Debug("Invocazione di Converti");

            try
            {
                var formatoDestinazione = conversioneType.ToString();

                if (Path.GetExtension(nomeFile).Replace(".", "").ToUpperInvariant() == formatoDestinazione && conversioneType != FormatoConversioneEnum.PDF)
                {
                    this._log.DebugFormat("Il file {0} è già di tipo {1}. La conversione non verrà eseguita.", nomeFile, conversioneType);

                    var mime = String.Empty;

                    if (conversioneType == FormatoConversioneEnum.HTML)
                        mime = "text/html;";

                    return new BinaryFile(nomeFile, mime, fileDaConvertire);
                }

                using (var ws = this._serviceCreator.CreateClient())
                {
                    this._log.DebugFormat("Url del web service di conversione files: {0}", ws.Service.Endpoint.Address);

                    string estensioneFile = Path.GetExtension(nomeFile).Replace(".", "").ToUpper();

                    var req = new ConvertBinaryRequest
                    {
                        binaryData = fileDaConvertire,
                        contentType = estensioneFile,
                        conversionType = conversioneType.ToString(),
                        token = ws.Token
                    };

                    this._log.DebugFormat(@"Parametri di invocazione del web service: {0}
											binaryData = {0} bytes, 
											contentType = {1}, 
											conversionType = {2}, 
											token = {3}",
                                            req.binaryData.Length,
                                            req.contentType,
                                            req.conversionType,
                                            req.token);

                    ConvertBinaryResponse res = ws.Service.ConvertBinary(req);

                    this._log.DebugFormat(@"Risultato della conversione: {0}
											binaryData = {0} bytes, 
											fileName = {1}, 
											mimeType = {2}",
                                            res.binaryData.Length,
                                            res.fileName,
                                            res.mimeType);

                    var outFile = new BinaryFile(res.fileName, res.mimeType, res.binaryData);

                    outFile.FileName = Path.GetFileNameWithoutExtension(nomeFile) + "." + formatoDestinazione.ToLower();

                    return outFile;
                }
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Converti: errore durante la conversione del file: {0}", ex.ToString());

                throw;
            }
        }

        public BinaryFile Converti(BinaryFile file, FormatoConversioneEnum conversioneType)
        {
            var outFile = this.Converti(file.FileName, file.FileContent, conversioneType);

            return outFile;
        }

        public BinaryFile Converti(int codiceOggetto, IOggettiService oggettiService, FormatoConversioneEnum conversioneType)
        {
            var file = oggettiService.GetById(codiceOggetto);

            if (file == null)
                throw new ArgumentException("Id file " + codiceOggetto + " non valido");

            return this.Converti(file, conversioneType);
        }

        private static byte[] TrasformaXml(string xml, TextReader xsl)
        {
            TextReader trXml = new StringReader(xml);

            var xmlDocument = new XPathDocument(trXml);
            var xslDocument = new XPathDocument(xsl);

            var transform = new XslCompiledTransform();
            transform.Load(xslDocument);

            var ms = new MemoryStream();

            transform.Transform(xmlDocument, null, ms);

            return ms.ToArray();
        }
    }
}
