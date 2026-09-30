// -----------------------------------------------------------------------
// <copyright file="IValidazioneBandiService.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneBandiUmbria
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.GestioneBandiUmbria.Validazione;
	using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;

    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    public interface IBandiUmbriaService
    {
		EsitoValidazione ValidaBandoA1(int idDomanda);
		EsitoValidazione ValidaBandoB1(int idDomanda);

		DomandaBando GetDatiDomandaA1(int idDomanda, int idModelloAllegato1, int idModelloAllegato2, int idModelloAllegato7, int idModelloAllegatoAltreSedi);
		DomandaBando GetDatiDomandaB1(int idDomanda, int idModelloAllegato3, int idModelloAllegato4, int idModelloAllegato10, int idModelloAllegatoAltreSedi);

		void AllegaADomanda(int idDomanda, string idAllegato, BinaryFile file, string verificaModello);
		void RimuoviAllegatoDaDomanda(int idDomanda, string idAllegato);

		IEnumerable<string> ValidaPresenzaAllegati(int idDomanda);
		IEnumerable<AllegatoDomandaBandi> GetAllegatiCheNecessitanoFirma(int idDomanda);

		void AggiungiFileFirmatoAdAllegato(int idDomanda, string idAllegato, BinaryFile file);
		void RimuoviFileFirmatoDaAllegato(int idDomanda, string idAllegato);

		void PreparaAllegatiDomanda(int idDomanda);
	}
}
