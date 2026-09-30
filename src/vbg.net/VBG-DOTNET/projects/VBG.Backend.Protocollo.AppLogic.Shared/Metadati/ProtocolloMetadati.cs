namespace VBG.Backend.Protocollo.AppLogic.Shared.Metadati
{
    public class ProtocolloMetadati
    {
        public string Metadato { get; set; }
        public string Valore { get; set; }

        public ProtocolloMetadati()
        {

        }

        public ProtocolloMetadati(string metadato, string valore)
        {
            this.Metadato = metadato;
            this.Valore = valore;
        }
    }
}