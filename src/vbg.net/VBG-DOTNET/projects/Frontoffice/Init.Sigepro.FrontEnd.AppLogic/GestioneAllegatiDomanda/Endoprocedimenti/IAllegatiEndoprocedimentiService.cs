namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiDomanda.Endoprocedimenti
{
    using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDocumenti;
    using Init.SIGePro.Manager.DTO.Endoprocedimenti;
    using System.Collections.Generic;

    public interface IAllegatiEndoprocedimentiService
    {
        DocumentoDomanda GetById(int idDomanda, int idDocumento);
        void AggiungiAllegatoAEndo(int idDomanda, int idAllegato, GestioneOggetti.BinaryFile file);
        void AggiungiAllegatoAEndo(int idDomanda, int idAllegato, int codiceOggetto);
        void AggiungiAllegatoLibero(int idDomanda, int codiceEndo, string descrizione, GestioneOggetti.BinaryFile file, bool verificaFirma);
        void AggiungiAllegatoLibero(int idDomanda, int codiceEndo, string descrizione, int codiceOggetto);
        void EliminaOggettoUtente(int idDomanda, int idAllegato);
        void SincronizzaAllegati(int idDomanda);
        IEnumerable<AllegatiPerEndoprocedimentoDto> GetDatiProcedimenti(List<int> codiciEndoSelezionati);
    }
}
