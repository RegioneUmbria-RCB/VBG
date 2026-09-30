using VBG.Backend.Protocollo.AppLogic.Core.Insiel3.Services;
using Init.SIGePro.Manager.Utils;
using ProtocolloInsielService3;
using ProtocolloInsiel3FilesTransferService;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel3.Allegati
{
    public static class AllegatiExtensions
    {
        public static DocumentoInsProto GetUploadFileResponseFromProtocolloAllegati(this ProtocolloAllegati allegato, AllegatiService wrapper, int idx)
        {
            var response = wrapper.Upload(new UploadFileRequest { binaryData = allegato.OGGETTO, md5Checksum = Md5Utils.GetMd5(allegato.OGGETTO) }, allegato.CODICEOGGETTO);

            var uploadedFileResponse = (UploadedFileType)response.Item;

            var retVal = new DocumentoInsProto
            {
                id = uploadedFileResponse.idFile,
                primario = (idx == 0),
                primarioSpecified = true,
                nome = allegato.NOMEFILE,
                inviaIOP = allegato.InviaTramitePec.GetValueOrDefault(true),
                inviaIOPSpecified = true
            };

            return retVal;
        }
    }
}
