namespace VBG.Backend.Protocollo.AppLogic.Core.Elios
{
    public class FascicolaRequest
    {
        public int AnnoProtocollo { get; set; }
        public int NumeroProtocollo { get; set; }
        public int AnnoFascicolo { get; set; }
        public int LivelloFascicolo { get; set; }
        public int NumeroFascicolo { get; set; }
        public string Categoria { get; set; }
        public string Classe { get; set; }
        public string Sottoclasse { get; set; }
        public string Descrizione { get; set; }
        public int? IdPadre { get; internal set; }
        public int? Id { get; internal set; }
    }
}