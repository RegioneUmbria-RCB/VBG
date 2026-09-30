using Init.Sigepro.FrontEnd.Pagamenti.EntraNextService;
using System;

namespace Init.Sigepro.FrontEnd.Pagamenti.ENTRANEXT
{
    public class OneriEntraNextDTO
    {
        public readonly string NomeVoceDiCosto;
        public readonly string Descrizione;
        public readonly CausaliImporti CausaleImporto;
        public readonly decimal Importo;
        public readonly decimal Quantita;
        public readonly decimal Iva;
        public readonly IVA? AliquotaIva;
        public readonly string NumeroAccertamentoContabile;

        public OneriEntraNextDTO(string descrizione, decimal importo, decimal quantita, decimal iva, IVA? aliquotaIva, string numeroAccertamentoContabile, string mappaturaNodoPagamenti)
        {

            #region Validazione dati passati

            if (String.IsNullOrEmpty(descrizione))
                throw new Exception($"Non è stata passata la causale dell'onere.");

            if (String.IsNullOrEmpty(mappaturaNodoPagamenti))
                throw new Exception($"E' stata utilizzata la causale onere {descrizione} non decodificata per il sistema dei pagamenti.");

            if (mappaturaNodoPagamenti.IndexOf('@') < 0)
                throw new Exception($"Il codice {mappaturaNodoPagamenti} della causale {descrizione} non è corretto perchè deve riportare CausaleImporto e NomeVoceDiCosto separati da una @ in questa forma NomeVoceDiCosto@CausaleImporto (Es. WEB0101@Servizi)");

            if (importo <= 0)
                throw new Exception($"Non è possibile richiedere il pagamento per l'importo {importo}. Controllare il valore dell'onere {descrizione}");

            if (iva < 0)
                throw new Exception($"Non è possibile indicare l'IVA a {iva}. Controllare la configurazione della causale {descrizione}");

            if (quantita <= 0)
                throw new Exception($"Non è possibile indicare {quantita} nella quantià di oneri da pagare per la causale {descrizione}");

            var causaleImporto = mappaturaNodoPagamenti.Split('@')[1];

            var t = new CausaliImporti();
            var isParsable = Enum.TryParse(causaleImporto, out t);

            if (!isParsable)
                throw new Exception($"Il codice {causaleImporto} non è censito nel sistema di pagamento. Contattare il fornitore o modificare la configurazione del codice {mappaturaNodoPagamenti} per inserire il valore corretto");

            #endregion

            this.NomeVoceDiCosto = mappaturaNodoPagamenti.Split('@')[0];
            this.Descrizione = descrizione;
            this.CausaleImporto = t;
            this.Importo = importo;
            this.Quantita = quantita;
            this.Iva = iva;
            this.AliquotaIva = aliquotaIva;
            this.NumeroAccertamentoContabile = numeroAccertamentoContabile;
        }


    }
}
