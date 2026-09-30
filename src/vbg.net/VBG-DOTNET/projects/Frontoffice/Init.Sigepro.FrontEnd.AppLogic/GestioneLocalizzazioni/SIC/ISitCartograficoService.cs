using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni.SIC
{


    public interface ISitCartograficoService
    {
        Task<GeneraURLMappaResponse> GeneraURLMappaAsync(GeneraURLMappaLocalizzazioneRequest request);
        Task<GeneraURLMappaResponse> GeneraURLMappaListaPraticheAsync(GeneraURLMappaListaPraticheRequest request);
        Task<InformazioniAggiuntive> RecuperaInformazioniAggiuntiveAsync(RecuperaInformazioniAggiuntiveRequest request);
        Task<SICFeatures> GetFeaturesAsync();
    }
}
