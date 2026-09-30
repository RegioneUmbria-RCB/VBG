namespace Init.SIGePro.Manager.Logic.GestioneIstanze.Documenti
{
    public interface IDocumentiIstanzaService
    {
        int Allega(int codiceIstanza, DescrittoreFile descrittore, byte[] fileContent);
    }
}
