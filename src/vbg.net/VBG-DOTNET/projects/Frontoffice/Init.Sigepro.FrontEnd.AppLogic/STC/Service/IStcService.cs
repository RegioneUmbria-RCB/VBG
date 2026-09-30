// -----------------------------------------------------------------------
// <copyright file="IStcService.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.STC.Service
{
    using Init.Sigepro.FrontEnd.AppLogic.StcService;
    using System;
    using System.Threading.Tasks;

    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    public interface IStcService
    {
        NotificaAttivitaResponse NotificaAttivita(NotificaAttivitaRequest request, Action<SportelloType>? modificaSportelloDestinatario = null);
        InserimentoPraticaResponse InserimentoPratica(InserimentoPraticaRequest request, string pecSportello, SportelloType? sportelloDestinatario = null);
        Task<InserimentoPraticaResponse> InserimentoPraticaAsync(InserimentoPraticaRequest request, string pecSportello, SportelloType? sportelloDestinatario = null);
        bool PraticaEsisteNelBackend(string idPratica, SportelloType? sportelloDestinatario = null);
        RichiestaPraticheListaResponse RichiestaPraticheLista(RichiestaPraticheListaRequest richiesta);
        RichiestaPraticaResponse RichiestaPratica(string idPratica, SportelloType? sportelloDestinatario = null);
        AllegatoBinarioResponse AllegatoBinario(string codiceOggetto);
    }
}
