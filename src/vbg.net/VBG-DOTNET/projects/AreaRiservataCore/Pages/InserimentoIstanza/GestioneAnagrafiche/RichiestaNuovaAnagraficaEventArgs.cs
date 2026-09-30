using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;

namespace AreaRiservataCore.Pages.InserimentoIstanza.GestioneAnagrafiche
{
    public record class RichiestaNuovaAnagraficaEventArgs(TipoPersonaEnum TipoPersona, string CodiceFiscale)
    {
    }
}
