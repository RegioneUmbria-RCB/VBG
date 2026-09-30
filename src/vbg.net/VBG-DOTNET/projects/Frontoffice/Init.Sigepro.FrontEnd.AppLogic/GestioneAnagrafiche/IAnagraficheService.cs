// -----------------------------------------------------------------------
// <copyright file="AnagraficheService.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche
{
    using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
    using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
    using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche.Sincronizzazione;
    using System.Threading.Tasks;

    public interface IAnagraficheService
    {

        void CollegaAziendaAdAnagrafica(int idDomanda, int idAnagrafica, int idAziendaCollegata);
        void RimuoviAnagrafica(int idDomanda, int idAnagrafica);
        void RimuoviAnagrafica(DomandaOnline domanda, int idAnagrafica);
        int SalvaAnagrafica(int idDomanda, AnagraficaDomanda row);
        Task<int> SalvaAnagraficaAsync(int idDomanda, AnagraficaDomanda row, ILogicaSincronizzazioneTipiSoggetto? logicaSincronizzazioneTipiSoggetto = null);
        int SalvaAnagrafica(DomandaOnline domanda, AnagraficaDomanda row, ILogicaSincronizzazioneTipiSoggetto? logicaSincronizzazioneTipiSoggetto = null);
        void SincronizzaFlagsTipiSoggetto(int idDomanda, ILogicaSincronizzazioneTipiSoggetto? logicaSincronizzazioneTipiSoggetto = null);
        void VerificaFlagsCittadiniExtracomunitari(int idDomanda);

        // Anagrafe RicercaAnagraficaBackoffice(TipoPersonaEnum tipoPersona, string codiceFiscale);

        // TODO: Autenticazione/account utente - spostare in un service differente
        //Anagrafe GetPersonaFisicaByUserId(string idComune, string userId);
        //Anagrafe GetPersonaGiuridicaByUserId(string idComune, string userId);
        // CreazioneAnagraficaResult CreaAnagrafica(RichiestaCreazioneAnagraficaDto richiesta);
        //void NuovaRegistrazione(string idComune, Anagrafe anagrafe);
        // void ModificaDatianagrafici(string idComune, AnagraficaUtente anagrafica);
        // void ModificaPassword(string idComune, int codiceAnagrafe, string vecchiaPassword, string nuovaPassword, string confermaPassword);
        void AggiungiAnagraficaConSoggettoCollegato(int idDomanda, string cfAnagrafica, int codiceTipoSoggettoAnagrafica, string cfAnagraficaCollegata, int codiceTipoSoggettoAnagraficaCollegata);
        AnagraficaDomanda GetById(int idDomanda, int idAnagrafica);
        AnagraficaDomanda GetRichiedente(int idDomanda);
        AnagraficaDomanda GetTecnico(int idDomanda);
    }
}
