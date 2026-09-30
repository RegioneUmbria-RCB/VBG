using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche
{
	public interface IAnagraficheReadInterface
	{
		IEnumerable<AnagraficaDomanda> Anagrafiche { get; }
		IEnumerable<AnagraficaDomanda> GetRichiedenti();
		IEnumerable<AnagraficaDomanda> GetAltriSoggetti();
		AnagraficaDomanda GetRichiedente();
		AnagraficaDomanda GetTecnico();
		AnagraficaDomanda GetAzienda();
		AnagraficaDomanda GetLegaleRappresentanteDi(AnagraficaDomanda azienda,ITipiSoggettoService tipiSoggettoService);
		AnagraficaDomanda FindByRiferimentiSoggetto(TipoPersonaEnum tipoPersona, string codiceFiscalePartitaIva);
		AnagraficaDomanda GetById(int idAnagrafica);
		IEnumerable<AnagraficaDomanda> GetPossibiliProcuratoriDi(string codiceFiscaleUtente);

		IEnumerable<AnagraficaDomanda> GetSoggettiSottoscrittori();
		IEnumerable<AnagraficaDomanda> GetSoggettiNonSottoscrittori();

		IEnumerable<AnagraficaDomanda> GetAnagraficheCollegabili();
	}
}
