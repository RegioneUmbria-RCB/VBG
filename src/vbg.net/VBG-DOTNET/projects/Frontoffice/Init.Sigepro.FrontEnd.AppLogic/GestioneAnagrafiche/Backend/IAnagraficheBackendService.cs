using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using VBG.Frontend.AppLogic.WsAnagraficheService;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche.Backend
{
    public interface IAnagraficheBackendService
    {
        CreazioneAnagraficaResult CreaAnagrafica(RichiestaCreazioneAnagraficaDto richiesta);
        Anagrafe GetPersonaFisicaByUserId(string userId);
        Anagrafe GetPersonaGiuridicaByUserId(string userId);
        Anagrafe RicercaAnagraficaBackoffice(TipoPersonaEnum tipoPersona, string codiceFiscale);
    }
}