using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.ServizioPrecompilazionePDF;
using log4net;
using System;
using System.Linq;

namespace Init.Sigepro.FrontEnd.AppLogic.PrecompilazionePDF
{
    public class PdfUtilsWsWrapper : IPdfUtilsWsWrapper
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(PdfUtilsWsWrapper));
        private readonly PdfUtilsServiceCreator _serviceCreator;

        public PdfUtilsWsWrapper(PdfUtilsServiceCreator serviceCreator)
        {
            this._serviceCreator = serviceCreator;
        }

        public BinaryFile PrecompilaPdf(BinaryFile pdfFile, BinaryFile xmlFile)
        {
            return this._serviceCreator.Call(ws =>
            {
                try
                {
                    var response = ws.Service.PrecompilaPDF(new PrecompilaPDFRequestType
                    {
                        token = ws.Token,
                        pdfList = new PDFFileType[]{
                            new PDFFileType{
                                binaryData = pdfFile.FileContent,
                                fileName = pdfFile.FileName,
                                id = "fileDaConvertire"
                            }
                        },
                        xmlFileIn = new XmlFileType
                        {
                            binaryData = xmlFile.FileContent
                        }
                    });

                    if (response.Items[0] is string)
                        throw new PrecompilazionePdfException(response.Items[0].ToString());

                    return new BinaryFile(pdfFile.FileName, pdfFile.MimeType, (response.Items[0] as PDFFileType).binaryData);
                }
                catch (Exception ex)
                {
                    this._log.ErrorFormat("Errore durante l'invocazione del web service di precompilazione PDF: {0}", ex.ToString());

                    throw;
                }
            });
        }

        public DatiPdfCompilabile EstraiXml(BinaryFile pdfFile)
        {
            return this._serviceCreator.Call(ws =>
            {
                try
                {
                    var result = ws.Service.RecuperaDatiDaPDF(new RecuperaDatiDaPDFRequestType
                    {
                        pdfFile = new PDFFileType
                        {
                            binaryData = pdfFile.FileContent,
                            fileName = pdfFile.FileName,
                            id = "fileDaConvertire"
                        },
                        token = ws.Token
                    });

                    if (result.Items == null)
                    {
                        throw new PrecompilazionePdfException("Non è stato possibile estrarre informazioni dal file compilato");
                    }

                    if (result.Items[0] is string)
                    {
                        throw new PrecompilazionePdfException(result.Items[0].ToString());
                    }

                    return new DatiPdfCompilabile(result.Items.Cast<DatiPDFType>(), pdfFile.FileName);
                }
                catch (Exception ex)
                {
                    this._log.ErrorFormat("Errore durante l'invocazione del web service di precompilazione PDF: {0}", ex.ToString());

                    throw;
                }
            });
        }
    }
}
