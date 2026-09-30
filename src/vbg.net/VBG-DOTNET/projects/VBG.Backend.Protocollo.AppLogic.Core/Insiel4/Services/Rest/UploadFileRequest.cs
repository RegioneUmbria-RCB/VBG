namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest
{
    public class UploadFileRequest
    {
        public string ImprontaMd5 { get; set; }
        public byte[] File { get; set; }
    }
}
