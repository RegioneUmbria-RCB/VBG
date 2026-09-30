namespace Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti
{
    using System.Collections.Generic;
    using Init.Sigepro.FrontEnd.AppLogic.WsEndoprocedimenti;
    using Init.SIGePro.Manager.DTO.Common;
    using Init.SIGePro.Manager.DTO.Endoprocedimenti;

    public interface IEndoprocedimentiRepository
    {
        EndoprocedimentiAreaRiservataDto GetListaEndoDaIdIntervento(string codiceComune, int codIntervento, bool utenteTeste);

        EndoprocedimentoDto GetById(int id, AmbitoRicerca ambitoRicercaDocumenti);

        EndoprocedimentoMappatoDto GetByIdEndoMappato(string idEndoMappato);

        Dictionary<int, IEnumerable<TipiTitoloDto>> TipiTitoloWhereCodiceInventarioIn(List<int> codiciInventario);

        TipiTitoloDto GetTipoTitoloById(int codiceTipoTitolo);


        // Metodi utilizzati per la generazione dell'albero degli endo e pubblici
        // BaseDtoOfInt32String[] GetListaFamiglieFrontoffice(string alias, string software);
        // BaseDtoOfInt32String[] GetListaCategorieFrontoffice(string alias, string software, int codiceFamiglia);
        // BaseDtoOfInt32String[] GetListaEndoFrontoffice(string alias, string software, int codiceCategoria);
        // RisultatoCaricamentoGerarchiaEndo CaricaGerarchiaFrontoffice(string alias, int id, LivelloCaricamentoGerarchia livello);
        // RisultatoRicercaTestualeEndo RicercaTestualeEndoFrontoffice(string alias, string software, string partial, TipoRicercaEnum tipoRicerca);
    }
}
