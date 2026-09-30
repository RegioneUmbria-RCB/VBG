using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche.LogicaRisoluzioneSoggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto;
using System.Collections.Generic;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche
{
    public interface IAnagraficheReadInterface
    {
        IEnumerable<AnagraficaDomanda> Anagrafiche { get; }
        IEnumerable<AnagraficaDomanda> GetRichiedenti();
        IEnumerable<AnagraficaDomanda> GetAltriSoggetti(ILogicaRisoluzioneTecnico logicaRisoluzioneTecnico);
        AnagraficaDomanda? GetRichiedente();
        AnagraficaDomanda? GetTecnico(ILogicaRisoluzioneTecnico logicaRisoluzioneTecnico);
        AnagraficaDomanda? GetAzienda();
        AnagraficaDomanda? GetLegaleRappresentanteDi(AnagraficaDomanda azienda, ITipiSoggettoService tipiSoggettoService);
        AnagraficaDomanda FindByRiferimentiSoggetto(TipoPersonaEnum tipoPersona, string codiceFiscalePartitaIva);
        AnagraficaDomanda? GetById(int idAnagrafica);
        IEnumerable<AnagraficaDomanda> GetPossibiliProcuratoriDi(string codiceFiscaleUtente);

        IEnumerable<AnagraficaDomanda> GetSoggettiSottoscrittori();
        IEnumerable<AnagraficaDomanda> GetSoggettiNonSottoscrittori();

        IEnumerable<AnagraficaDomanda> GetAnagraficheCollegabili();
    }
}
