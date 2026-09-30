using VBG.Shared.Infrastructure.ServiceModel;
using Init.SIGePro.Manager.Configuration;
using Init.SIGePro.Manager.FileConverterServiceReference;
using Init.SIGePro.Manager.IOC;
using System;
using System.ServiceModel;

namespace Init.SIGePro.Manager.Logic.FileConverter
{
    public class FileConverterProxy
    {
        private const string CONVERSION_TYPE = "PDF";


        public ConvertiInPDFResponse ConvertiFileInPdf(ConvertiInPDFRequest request)
        {

            try
            {
                using (var ws = this.CreaWebService())
                {

                    return ConvertiInPDFResponse.FromWSResponse
                    (
                        ws.ConvertBinary(new ConvertBinaryRequest
                        {
                            binaryData = request.BinaryData,
                            contentType = request.ContnentType,
                            conversionType = CONVERSION_TYPE,
                            token = CONVERSION_TYPE // va passato perchè da errore in validazione ma non viene usato
                        })
                    );
                }
            }
            catch (Exception ex)
            {
                throw new Exception("Errore nella conversione del file: " + ex.Message);
            }
        }

        internal fileconverterClient CreaWebService()
        {
            try
            {


                if (String.IsNullOrEmpty(ParametriConfigurazione.Get.WsHostUrlFileConverter))
                    throw new Exception("L' URL del File Converter non è configurato nella Security");

                var bindingFactory = StaticKernelContainer.GetService<IBindingFactory>();
                var endPointAddress = new EndpointAddress(ParametriConfigurazione.Get.WsHostUrlFileConverter);
                var binding = bindingFactory.CreateAndConfigure("fileConverterServiceBinding");

                return new fileconverterClient(binding, endPointAddress);
            }
            catch (Exception)
            {

                throw;
            }

        }
    }
}
