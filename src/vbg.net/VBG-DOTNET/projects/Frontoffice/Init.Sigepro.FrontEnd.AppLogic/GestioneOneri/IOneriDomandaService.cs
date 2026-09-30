// -----------------------------------------------------------------------
// <copyright file="OneriDomandaService.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOneri
{
    public interface IOneriDomandaService
    {
        void EliminaAttestazioneDiPagamento(int idDomanda);
        string GetCodiceCausaleOnereTraslazione(int idCausale);
        void ImpostaDichiarazioneDiAssenzaOneriDaPagare(int idDomanda);
        void ToggleDichiarazioneDiAssenzaOneriDaPagare(bool toggle, int idDomanda);
        ValueTask ToggleDichiarazioneDiAssenzaOneriDaPagareAsync(bool toggle, int idDomanda);
        void InserisciAttestazioneDiPagamento(int idDomanda, int codiceOggetto);
        void InserisciAttestazioneDiPagamento(int idDomanda, BinaryFile allegato);
        void RimuoviDichiarazioneDiAssenzaOneriDaPagare(int idDomanda);
        void SincronizzaOneri(int idDomanda, ComportamentoSincronizzazioneOneriSenzaImporto comportamentoOneriSenzaImporto);
        void SpecificaEstremiPagamento(int idDomanda, IEnumerable<OnerePagato> oneriPagati);
        ValueTask SpecificaEstremiPagamentoAsync(int idDomanda, IEnumerable<OnerePagato> oneriPagati);
        void SpecificaEstremiPagamentoOneriNonPagatiOnline(int idDomanda, IEnumerable<OnerePagato> oneriPagati);
        ValueTask SpecificaEstremiPagamentoOneriNonPagatiOnlineAsync(int idDomanda, IEnumerable<OnerePagato> oneriPagati);
    }
}