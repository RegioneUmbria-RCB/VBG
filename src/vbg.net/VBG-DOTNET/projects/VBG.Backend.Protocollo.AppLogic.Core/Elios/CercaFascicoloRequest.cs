namespace VBG.Backend.Protocollo.AppLogic.Core.Elios
{
    internal class CercaFascicoloRequest
    {
        public int? Anno { get; internal set; }
        public int? Numero { get; internal set; }
        public string Descrizione { get; internal set; }
        public string Classifica { get; internal set; }
        public int? Livello { get; internal set; }
        public int? IdPadre { get; internal set; }
    }
}