using Init.Sigepro.FrontEnd.AppLogic.GestioneOneri;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneOneri;
using VBG.BlazorComponentsLibrary.EditFormComponents.DropDown;

namespace AreaRiservataCore.Pages.InserimentoIstanza.GestioneOneri.v2
{
    public static class GrigliaOneriItemExtensionMethods
    {
        public static OnerePagato ToOnerePagato(this GrigliaOneriItem item)
        {
            var provenienza = item.Provenienza == OnereFrontoffice.ProvenienzaOnereEnum.Intervento ? ProvenienzaOnere.Intervento : ProvenienzaOnere.Endo;
            var codiceCausale = item.CodiceCausale;
            var idEndoOIntervento = item.CodiceEndoOInterventoOrigine;
            var tipoPagamento = item.TipoPagamento == null ? new TipoPagamento() : item.TipoPagamento;
            var dataPagamento = item.DataPagamento;
            var riferimentoPagamento = item.RiferimentoPagamento;
            var causale = item.Causale;
            var importoPagato = item.ImportoPagato;
            var modalitaPagamento = item.ModalitaPagamentoAsEnum;

            return new OnerePagato(
                    new IdOnere(provenienza, codiceCausale, idEndoOIntervento),
                    causale,
                    modalitaPagamento,
                    new EstremiPagamento(tipoPagamento, dataPagamento, riferimentoPagamento, importoPagato)
            );
        }

        public static GrigliaOneriItem ToGrigliaOneriItem(this OnereFrontoffice onereFrontoffice, IEnumerable<DropDownItem> modalitaPagamento)
        {
            return new GrigliaOneriItem
            {
                Provenienza = onereFrontoffice.Provenienza,
                ModalitaPagamento = modalitaPagamento.FirstOrDefault(v => v.Value == ((int)onereFrontoffice.ModalitaPagamento).ToString()),
                CodiceCausale = onereFrontoffice.Causale.Codice,
                Causale = onereFrontoffice.Causale.Descrizione,
                CodiceEndoOInterventoOrigine = onereFrontoffice.EndoOInterventoOrigine.Codice,
                EndoOInterventoOrigine = onereFrontoffice.EndoOInterventoOrigine?.ToString() ?? "Non definito",
                Note = onereFrontoffice.Note,
                DataPagamento = onereFrontoffice.EstremiPagamento?.Data,
                RiferimentoPagamento = onereFrontoffice.EstremiPagamento == null ? String.Empty : onereFrontoffice.EstremiPagamento.Riferimento,
                Importo = onereFrontoffice.Importo,
                ImportoPagato = onereFrontoffice.ImportoPagato,
                PagamentoCompletato = onereFrontoffice.StatoPagamento == StatoPagamentoOnereEnum.PagamentoRiuscito,
                TipoPagamento = new TipoPagamento(onereFrontoffice.EstremiPagamento?.TipoPagamento?.Codice ?? "", onereFrontoffice.EstremiPagamento?.TipoPagamento.Descrizione ?? "")
            };
        }
    }
}
