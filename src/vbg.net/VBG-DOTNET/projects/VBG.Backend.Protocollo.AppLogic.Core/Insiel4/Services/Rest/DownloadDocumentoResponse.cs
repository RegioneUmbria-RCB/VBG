namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest
{
    public class DownloadDocumentoResponse
    {
        public byte[] File { get; set; }
        public string ImprontaMd5 { get; set; }
        public string NomeFile { get; set; }
    }
}
