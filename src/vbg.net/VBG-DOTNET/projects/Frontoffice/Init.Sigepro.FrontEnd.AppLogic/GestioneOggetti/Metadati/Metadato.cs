namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.Metadati
{
    public class Metadato
    {
        public readonly string Chiave;
        public readonly string Valore;

        public Metadato( string chiave, string valore )
        {
            this.Chiave = chiave;
            this.Valore = valore;
        }
    }
}