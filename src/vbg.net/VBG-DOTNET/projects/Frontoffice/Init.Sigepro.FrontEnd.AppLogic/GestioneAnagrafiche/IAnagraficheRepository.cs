// -----------------------------------------------------------------------
// <copyright file="AnagraficheRepository.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche
{
    using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
    using VBG.Frontend.AppLogic.WsAnagraficheService;

    public interface IAnagraficheRepository
    {
        Anagrafe GetByUserId(string aliasComune, string userId, TipoPersonaEnum tipoPersona);
        // void ModificaDatianagrafici(string idComune, AnagraficaUtente anagrafe);
        // void ModificaPassword(string idComune, int codiceAnagrafe, string vecchiaPassword, string nuovaPassword, string confermaNuovaPassword);
        // void NuovaRegistrazione(string aliasComune, Anagrafe anagrafe);
        Anagrafe? RicercaAnagrafica(TipoPersonaEnum tipoPersona, string codiceFiscale);
        CreazioneAnagraficaResult CreaAnagrafica(RichiestaCreazioneAnagraficaDto richiesta);
    }
}
