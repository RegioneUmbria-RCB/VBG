using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInsiel3FilesTransferService;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.Insiel3.Services;
using Init.SIGePro.Protocollo.ProtocolloInsielService3;
using Init.SIGePro.Manager.Utils;

namespace Init.SIGePro.Protocollo.Insiel3.Allegati
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
                nome = allegato.NOMEFILE
            };

            return retVal;
        }
    }
}
