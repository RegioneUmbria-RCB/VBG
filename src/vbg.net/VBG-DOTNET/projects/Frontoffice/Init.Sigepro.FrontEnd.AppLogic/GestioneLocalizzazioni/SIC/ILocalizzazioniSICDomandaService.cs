using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneLocalizzazioni;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni.SIC
{
    public interface ILocalizzazioniSICDomandaService
    {
        void AggiungiLocalizzazione(int idDomanda, NuovaLocalizzazione localizzazione, RiferimentiCampoDinamico riferimentiCampoDinamico);
        void EliminaLocalizzazione(int idDomanda, int idLocalizzazione, RiferimentiCampoDinamico riferimentiCampoDinamico);
        void SalvaInformazioniAggiuntive(int idDomanda, string uuidLocalizzazione, InformazioniAggiuntive informazioniAggiuntive);
    }
}
