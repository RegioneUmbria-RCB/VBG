namespace VBG.Backend.Protocollo.AppLogic.Core.Elios
{
    public class AggiungiAllegatoRequest
    {
        public int Anno { get; internal set; }
        public int Numero { get; internal set; }
        public byte[] Contenuto { get; internal set; }
        public string Estensione { get; internal set; }
        public string NomeFile { get; internal set; }
        public bool Primario { get; internal set; }
    }
}