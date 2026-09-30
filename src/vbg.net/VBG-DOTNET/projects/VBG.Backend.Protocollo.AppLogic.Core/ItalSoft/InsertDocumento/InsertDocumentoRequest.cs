namespace VBG.Backend.Protocollo.AppLogic.Core.ItalSoft.InsertDocumento
{
    public class InsertDocumentoRequest
    {
        public string Token { get; set; }
        public string NomeFile { get; set; }
        public byte[] Content { get; set; }
        public string Estensione { get; set; }
    }
}
