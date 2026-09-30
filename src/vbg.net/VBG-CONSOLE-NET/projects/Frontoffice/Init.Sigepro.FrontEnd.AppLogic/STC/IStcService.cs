using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.StcService;
using System;

namespace Init.Sigepro.FrontEnd.AppLogic.STC
{


    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    public interface IStcService
    {
        NotificaAttivitaResponse NotificaAttivita(NotificaAttivitaRequest request, Action<SportelloType> modificaSportelloDestinatario = null);
        InserimentoPraticaResponse InserimentoPratica(InserimentoPraticaRequest request, string pecSportello, SportelloType sportelloDestinatario = null);
        bool PraticaEsisteNelBackend(string idPratica);
        RichiestaPraticheListaResponse RichiestaPraticheLista(RichiestaPraticheListaRequest richiesta);
        RichiestaPraticaResponse RichiestaPratica(string idPratica);
        AllegatoBinarioResponse AllegatoBinario(string codiceOggetto);
        int? GetIdCertificatoDiInvio(string idDomandaBackoffice);
        void AllegaCertificatoDiInvio(string idDomandaBackoffice, BinaryFile file);
    }
}
