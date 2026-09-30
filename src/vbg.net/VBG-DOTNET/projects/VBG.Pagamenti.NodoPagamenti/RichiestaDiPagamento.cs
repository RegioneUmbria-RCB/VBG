using System;
using System.Linq;
using VBG.Pagamenti.NodoPagamenti.Shared;

namespace VBG.Pagamenti.NodoPagamenti
{
    public class RichiestaDiPagamento
    {
        private readonly RiferimentiDomanda _riferimentiDomanda;
        private readonly CausaliDaPagare _causali;
        private readonly SoggettoDebitoreType _soggettoDebitore;

        public readonly bool IsPagamentoOtf;

        public RichiestaDiPagamento(RiferimentiDomanda riferimentiDomanda, SoggettoDebitoreType riferimentiUtente, CausaliDaPagare causali, bool attivaPagamentoOTF)
        {
            this._riferimentiDomanda = riferimentiDomanda ?? throw new ArgumentNullException(nameof(riferimentiDomanda));
            this._causali = causali ?? throw new ArgumentNullException(nameof(causali));
            this._soggettoDebitore = riferimentiUtente ?? throw new ArgumentNullException(nameof(riferimentiUtente));
            this.IsPagamentoOtf = attivaPagamentoOTF;
        }

        internal AttivaPagamentoOnTheFlyRequest ToAttivaPagamentoOnTheFlyRequest(string cfEnteCreditore, string urlRedirectEsito)
        {
            if (string.IsNullOrEmpty(cfEnteCreditore))
            {
                throw new ArgumentException($"'{nameof(cfEnteCreditore)}' cannot be null or empty.", nameof(cfEnteCreditore));
            }

            if (string.IsNullOrEmpty(urlRedirectEsito))
            {
                throw new ArgumentException($"'{nameof(urlRedirectEsito)}' cannot be null or empty.", nameof(urlRedirectEsito));
            }

            var registrazioni = this._causali
                                    .Oneri
                                    .Select(onere => onere.ToRegistrazioneContabileType(this._soggettoDebitore, this._riferimentiDomanda.CodiceUnivocoDomanda, DateTime.Now))
                                    .ToArray();

            return new AttivaPagamentoOnTheFlyRequest
            {
                AttivaPagamentoOnTheFlyType = new AttivaPagamentoOnTheFlyType
                {
                    cfEnteCreditore = cfEnteCreditore, // settings.CodiceFiscaleEnteCreditore,
                    accorpaPosizioni = true,
                    oggettoPagamentoUnico = $"Oneri presentazione pratica {this._riferimentiDomanda.CodiceUnivocoDomanda}",
                    registrazione = registrazioni,
                    urlRedirectEsito = urlRedirectEsito
                }
            };
        }


        internal InserisciPosizioniDebitorieType ToInserisciPosizioniDebitorieType(NodoPagamentiSettings settings, DateTime dataScadenza)
        {
            if (settings is null)
            {
                throw new ArgumentNullException(nameof(settings));
            }

            var registrazioni = this._causali
                        .Oneri
                        .Select(onere => onere.ToRegistrazioneContabileType(this._soggettoDebitore, this._riferimentiDomanda.CodiceUnivocoDomanda, dataScadenza))
                        .ToList();

            return new InserisciPosizioniDebitorieType
            {
                cfEnteCreditore = settings.CodiceFiscaleEnteCreditore,
                accorpaPosizioni = true,
                oggettoPagamentoUnico = $"Oneri presentazione pratica {this._riferimentiDomanda.CodiceUnivocoDomanda}",
                registrazione = registrazioni.ToArray()
            };
        }
    }
}
