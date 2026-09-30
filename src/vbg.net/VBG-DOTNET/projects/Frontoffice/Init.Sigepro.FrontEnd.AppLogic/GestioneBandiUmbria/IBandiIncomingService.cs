using Init.Sigepro.FrontEnd.AppLogic.GestioneBandiUmbria.Validazione;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneBandiUmbria
{
    public interface IBandiIncomingService
    {
        EsitoValidazione ValidaBandoIncoming(int idDomanda);

        DomandaBando GetDatiDomandaIncoming(int idDomanda, int idModelloAllegato1, int idModelloAllegato2, int idModelloAllegato7, int idModelloAllegatoAltreSedi);

        void AllegaADomanda(int idDomanda, string idAllegato, BinaryFile file, string verificaModello);
        void RimuoviAllegatoDaDomanda(int idDomanda, string idAllegato);

        IEnumerable<string> ValidaPresenzaAllegati(int idDomanda);
        IEnumerable<AllegatoDomandaBandi> GetAllegatiCheNecessitanoFirma(int idDomanda);

        void AggiungiFileFirmatoAdAllegato(int idDomanda, string idAllegato, BinaryFile file);
        void RimuoviFileFirmatoDaAllegato(int idDomanda, string idAllegato);

        void PreparaAllegatiDomanda(int idDomanda);
    }
}
