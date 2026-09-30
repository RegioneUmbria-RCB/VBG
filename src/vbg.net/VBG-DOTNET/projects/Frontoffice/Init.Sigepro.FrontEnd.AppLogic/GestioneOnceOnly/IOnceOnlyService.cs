using Init.Sigepro.FrontEnd.AppLogic.GestioneOnceOnly.ConfigurazioneBackoffice;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using System.Threading.Tasks;
using VBG.Frontend.AppLogic.WsAnagraficheService;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOnceOnly
{
    public interface IOnceOnlyService
    {
        bool IsOnceOnlyAttivo { get; }
    }
}
