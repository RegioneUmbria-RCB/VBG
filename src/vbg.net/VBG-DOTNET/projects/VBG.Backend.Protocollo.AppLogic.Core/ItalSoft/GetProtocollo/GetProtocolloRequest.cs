namespace VBG.Backend.Protocollo.AppLogic.Core.ItalSoft.GetProtocollo
{
    public class GetProtocolloRequest
    {
        public string Token { get; set; }
        public string Anno { get; set; }
        public string Numero { get; set; }
        public string IdComposto { get; set; }
    }
}