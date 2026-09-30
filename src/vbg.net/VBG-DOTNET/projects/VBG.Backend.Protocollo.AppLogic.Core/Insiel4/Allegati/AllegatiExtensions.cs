using Init.SIGePro.Manager.Utils;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Allegati
{
    public static class AllegatiExtensions
    {
        public static DocumentoInsProto GetUploadFileResponseFromProtocolloAllegati(this ProtocolloAllegati allegato, AllegatiService wrapper, int idx)
        {
            var response = wrapper.UploadFile(new UploadFileRequest { File = allegato.OGGETTO, ImprontaMd5 = Md5Utils.GetMd5(allegato.OGGETTO) }, allegato.CODICEOGGETTO, allegato.NOMEFILE, 1);

            var retVal = new DocumentoInsProto
            {
                Id = response.IdFile,
                Primario = (idx == 0),
                Nome = allegato.NOMEFILE,
                InviaIOP = allegato.InviaTramitePec.GetValueOrDefault(true)
            };

            return retVal;
        }
    }
}
