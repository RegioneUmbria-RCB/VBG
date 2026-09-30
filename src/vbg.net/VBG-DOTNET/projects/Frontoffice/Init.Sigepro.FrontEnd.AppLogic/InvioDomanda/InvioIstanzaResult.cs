// -----------------------------------------------------------------------
// <copyright file="InvioIstanzaResult.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.InvioDomanda
{
    using System;

    public partial class InvioIstanzaResult
    {
        public enum TipoEsitoInvio
        {
            InvioRiuscito,
            InvioFallito,
            InserimentoFallito,
            InvioRiuscitoNoBackend,
            ErroreInvio,
            IstanzaGiaPresentata
        }

        public TipoEsitoInvio Esito { get; private set; }
        public string CodiceIstanza { get; private set; }
        public string NumeroIstanza { get; private set; }

        public string? NumeroProtocollo { get; private set; }
        public DateTime? DataProtocollo { get; private set; }

        public static InvioIstanzaResult ErroreInvio()
        {
            return new InvioIstanzaResult(TipoEsitoInvio.ErroreInvio, String.Empty, string.Empty);
        }

        public static InvioIstanzaResult IstanzaGiaPresentata()
        {
            return new InvioIstanzaResult(TipoEsitoInvio.IstanzaGiaPresentata, String.Empty, string.Empty);
        }


        public static InvioIstanzaResult InvioFallito()
        {
            return new InvioIstanzaResult(TipoEsitoInvio.InvioFallito, String.Empty, string.Empty);
        }

        public static InvioIstanzaResult InserimentoFallito()
        {
            return new InvioIstanzaResult(TipoEsitoInvio.InserimentoFallito, String.Empty, string.Empty);
        }

        public static InvioIstanzaResult InvioRiuscito(string codiceIstanza, string numeroIstanza, string? numeroProtocollo, DateTime? dataProtocollo)
        {
            return new InvioIstanzaResult(TipoEsitoInvio.InvioRiuscito, codiceIstanza, numeroIstanza);
        }

        public static InvioIstanzaResult InvioRiuscitoNoBackend(string codiceIstanza, string numeroIstanza)
        {
            return new InvioIstanzaResult(TipoEsitoInvio.InvioRiuscitoNoBackend, codiceIstanza, numeroIstanza);
        }

        protected InvioIstanzaResult(TipoEsitoInvio esito, string codiceIstanza, string numeroistanza, string? numeroProtocollo = null, DateTime? dataProtocollo = null)
        {
            this.Esito = esito;
            this.CodiceIstanza = codiceIstanza;
            this.NumeroIstanza = numeroistanza;
        }


        public bool IsSuccess()
        {
            return !String.IsNullOrEmpty(this.CodiceIstanza) && (this.Esito == TipoEsitoInvio.InvioRiuscito || this.Esito == TipoEsitoInvio.InvioRiuscitoNoBackend);
        }
    }
}
