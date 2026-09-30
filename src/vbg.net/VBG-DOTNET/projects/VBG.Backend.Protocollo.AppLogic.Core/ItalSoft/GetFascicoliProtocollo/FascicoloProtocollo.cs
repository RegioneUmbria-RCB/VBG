namespace VBG.Backend.Protocollo.AppLogic.Core.ItalSoft.GetFascicoliProtocollo
{
    public class FascicoloProtocollo
    {
        public string Codice { get; set; }

        public string Descrizione { get; set; }

        public string Titolario { get; set; }

        public string CodiceSottoFascicolo { get; set; }

        public string DescrizioneSottoFascicolo { get; set; }

        public bool Principale { get; set; }
    }
}
