namespace Init.SIGePro.Manager.Logic.Cosap
{
    public class Calcolo
    {
        public string SpiegazioneFormula { get; }
        public Importo Importo { get; }

        public Calcolo(string spiegazione, double valore)
        {
            this.SpiegazioneFormula = spiegazione;
            this.Importo = new Importo(valore);
        }
    }
}
