namespace Init.SIGePro.Manager.Logic.GestioneIstanze.Documenti
{
    public interface IDocumentiIstanzaUploader
    {
        int Upload(int codiceIstanza, DescrittoreFile descrittore, byte[] fileContent);
    }
}
