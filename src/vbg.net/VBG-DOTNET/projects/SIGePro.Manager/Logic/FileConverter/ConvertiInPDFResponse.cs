using Init.SIGePro.Manager.FileConverterServiceReference;

namespace Init.SIGePro.Manager.Logic.FileConverter
{
    public class ConvertiInPDFResponse
    {
        public byte[] BinaryData { get; private set; }

        internal static ConvertiInPDFResponse FromWSResponse(ConvertBinaryResponse wsResponse)
        {
            return new ConvertiInPDFResponse
            {
                BinaryData = wsResponse.binaryData
            };
        }
    }
}
