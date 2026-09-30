using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.SIGePro.Manager.DTO.DatiDomandaOnline;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda.Repositories
{
    public interface IDatiDomandaFoRepository
    {
        void Elimina(int idDomanda);
        PresentazioneIstanzaDbV2 LeggiDataSetDomanda(string aliasComune, DatiDomandaOnlineDto datiDomanda);
        Task<PresentazioneIstanzaDbV2> LeggiDataSetDomandaAsync(string aliasComune, DatiDomandaOnlineDto datiDomanda);
        List<DatiDomandaOnlineDto> LeggiDomandeInSospeso(string aliasComune, string software, int codiceAnagrafe);
        Task<List<DatiDomandaOnlineDto>> LeggiDomandeInSospesoAsync(string aliasComune, string software, int codiceAnagrafe);
        EsitoSalvataggioDomandaOnlineDto Salva(DomandaOnline domanda, bool aggiornaDataultimaModifica);
        ValueTask<EsitoSalvataggioDomandaOnlineDto> SalvaAsync(DomandaOnline domanda, bool aggiornaDataultimaModifica);

        bool DomandaPresentata(int idDomanda);
        ValueTask<bool> DomandaPresentataAsync(int idDomanda);

        bool DomandaEliminata(int idDomanda);
        DatiDomandaOnlineDto LeggiDatiDomanda(string aliasComune, int idDomanda);
        Task<DatiDomandaOnlineDto> LeggiDatiDomandaAsync(int idDomanda);
        int GeneraProssimoIdDomanda(string aliasComune);

        byte[] ConvertToXml(DomandaOnline domanda);
        void ImpostaIdIstanzaOrigine(int idDomanda, int idDomandaOrigine);
    }
}
