namespace Init.SIGePro.Manager.Logic.GestioneIntegrazioneLDP
{
    public interface ILdpAnnullamentoServiceWrapper
    {
        void EliminaOccupazione(string identificativoTemporaneo);
        bool EsisteUnOccupazionePrenotata(string identificativoTemporaneo);
    }
}