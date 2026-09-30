namespace Init.SIGePro.Manager.Logic.GestioneCommissioni.Protocollazione
{
    public class AllegatoProtocolloCommissione
    {
        public int CodiceOggetto { get; }
        public string NomeFile { get; }

        public AllegatoProtocolloCommissione(int codiceOggetto, string nomeFile)
        {
            this.CodiceOggetto = codiceOggetto;
            this.NomeFile = nomeFile;
        }
    }
}