using Init.SIGePro.Manager.Logic.GestioneIstanze.Documenti;
using Init.SIGePro.Manager.WsParmaPagamenti;
using System;

namespace Init.SIGePro.Manager.Logic.PagamentiParma
{
    internal class EsitoNotificaPagoPAReader
    {
        private readonly DtoEsitoPagameti _esito;

        public int Code => Convert.ToInt32( _esito.Esito);
        public string Message => _esito.descrizione;
        public DescrittoreFile DescrittoreFile { get; }
        public byte[] FileData { get; }
        public string Url => this._esito.Url;
        public int IdDbp => this._esito.IdDbp; // ID Univoco Partita sul DataBasepagmenti
        public Guid? GuidDbp => this._esito.GuidDbp;

        public EsitoNotificaPagoPAReader(DtoEsitoPagameti esito)
        {
            this._esito = esito;



            this.FileData = Convert.FromBase64String(esito.avviso);
            this.DescrittoreFile = new DescrittoreFile
            {
                Nomefile = "avviso-di-pagamento.pdf",
                Descrizione = "Avviso di pagamento Pago PA",
                Note = $"Generato da chiamata web service il {DateTime.Now.ToString("dd/MM/yyyy HH:ss")}",
                Mime = "application/pdf",
                Data = DateTime.Now
            };

            this._esito.avviso = null;
        }


    }
}