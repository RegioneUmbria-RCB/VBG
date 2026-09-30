namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Fascicolazione
{
    public class DettaglioFascicoloInfo
    {
        public string Numero { get; private set; }
        public string Anno { get; private set; }
        public string CodiceRegistro { get; private set; }
        public string CodiceUfficio { get; private set; }

        public DettaglioFascicoloInfo(string numeroFascicolo, string annoFascicolo, string codiceRegistro, string codiceUfficio)
        {
            this.Numero = numeroFascicolo;
            this.Anno = annoFascicolo;
            this.CodiceRegistro = codiceRegistro;
            this.CodiceUfficio = codiceUfficio;
        }
    }
}
