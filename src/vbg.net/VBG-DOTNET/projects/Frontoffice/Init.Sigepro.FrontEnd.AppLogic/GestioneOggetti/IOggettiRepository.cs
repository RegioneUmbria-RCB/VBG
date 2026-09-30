namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti
{
    using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.Metadati;
    using System.Threading.Tasks;

    public interface IOggettiRepository
    {
        void AggiornaOggetto(int codiceOggetto, byte[] data);

        //void EliminaOggetto(int codiceOggetto);
        string GetNomeFile(int codiceOggetto);
        Task<string> GetNomeFileAsync(int codiceOggetto);
        BinaryFile GetOggetto(int codiceOggetto);
        Task<BinaryFile> GetOggettoAsync(int codiceOggetto);
        int InserisciOggetto(string nomeFile, string mimeType, byte[] data, IMetadatiOggettoProvider metadatiProvider);
        Task<int> InserisciOggettoAsync(string nomeFile, string mimeType, byte[] data, IMetadatiOggettoProvider metadatiProvider);
        string GetMd5_non_usare_non_sempre_valorizzato(int codiceOggetto);
    }
}
