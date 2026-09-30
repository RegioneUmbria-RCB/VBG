namespace Init.SIGePro.Manager.Logic.GestioneOneri.PosizioniDebitorie
{
    public interface IVerificaAnnullamentoPosizioniDebitorieService
    {
        /// <summary>
        /// Verifica l'annullamento di tutte le posizioni debitorie connesse ad un onere di un istanza.
        /// Effettua verifiche su ISTONERI_DETT_POSIZIONI, BOLL_GEST_DETTAGLIO, BOLL_GEST_DETT_RATE.
        /// <b>ATTENZIONE:</b> Restituisce false se durante la verifica non sono state trovate posizioni debitorie
        /// </summary>
        /// <param name="idOnere">Id dell'onere dell'istanza (ISTANZEONERI.ID)</param>
        /// <returns>true se tutte le posizioni debitorie sono state annullate, false se ne esiste almeno una non annullata oppure false se durante la verifica non sono state trovate posizioni debitorie</returns>
        bool PosizioniDebitorieOnereAnnullate(int idOnere);
    }
}
