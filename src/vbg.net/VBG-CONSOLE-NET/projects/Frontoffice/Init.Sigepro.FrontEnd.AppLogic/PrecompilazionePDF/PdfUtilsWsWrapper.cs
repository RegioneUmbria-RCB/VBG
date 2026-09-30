using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.ServizioPrecompilazionePDF;
using log4net;
using Init.Sigepro.FrontEnd.AppLogic.Common;

namespace Init.Sigepro.FrontEnd.AppLogic.PrecompilazionePDF
{
    public interface IPdfUtilsWsWrapper
    {
        BinaryFile PrecompilaPdf(BinaryFile pdfFile, BinaryFile xmlFile);
        DatiPdfCompilabile EstraiXml(BinaryFile pdfFile);
    }

    public class PdfUtilsWsWrapper : IPdfUtilsWsWrapper
    {
        ILog _log = LogManager.GetLogger(typeof(PdfUtilsWsWrapper));
        PdfUtilsServiceCreator _serviceCreator;
        IAliasResolver _aliasResolver;

        internal PdfUtilsWsWrapper(IAliasResolver aliasResolver, PdfUtilsServiceCreator serviceCreator)
        {
            this._serviceCreator = serviceCreator;
            this._aliasResolver = aliasResolver;
        }

        public BinaryFile PrecompilaPdf(BinaryFile pdfFile, BinaryFile xmlFile)
        {
            using (var ws = this._serviceCreator.CreateClient(this._aliasResolver.AliasComune))
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

                    return new BinaryFile
                    {
                        FileName = pdfFile.FileName,
                        FileContent = (response.Items[0] as PDFFileType).binaryData,
                        MimeType = pdfFile.MimeType
                    };
                }
                catch (Exception ex)
                {
                    _log.ErrorFormat("Errore durante l'invocazione del web service di precompilazione PDF: {0}", ex.ToString());

                    ws.Service.Abort();

                    throw;
                }
            }
        }

        public DatiPdfCompilabile EstraiXml(BinaryFile pdfFile)
        {
            using (var ws = this._serviceCreator.CreateClient(this._aliasResolver.AliasComune))
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
                    _log.ErrorFormat("Errore durante l'invocazione del web service di precompilazione PDF: {0}", ex.ToString());

                    ws.Service.Abort();

                    throw;
                }
            }
        }
    }
}
