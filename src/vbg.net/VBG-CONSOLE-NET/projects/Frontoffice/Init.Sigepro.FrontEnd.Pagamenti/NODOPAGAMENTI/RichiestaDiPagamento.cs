using System;
using System.Linq;

namespace Init.Sigepro.FrontEnd.Pagamenti.NODOPAGAMENTI
{
    public class RichiestaDiPagamento
    {
        public readonly RiferimentiDomanda RiferimentiDomanda;
        public readonly CausaliDaPagare Causali;
        public readonly RiferimentiUtenteNodoPagamenti RiferimentiUtente;
        public readonly bool AttivaSessionePagamento;

        public RichiestaDiPagamento(RiferimentiDomanda riferimentiDomanda, RiferimentiUtenteNodoPagamenti riferimentiUtente, CausaliDaPagare causali, bool attivaSessionePagamento)
        {
            this.RiferimentiDomanda = riferimentiDomanda ?? throw new ArgumentNullException(nameof(riferimentiDomanda));
            this.Causali = causali ?? throw new ArgumentNullException(nameof(causali));
            this.RiferimentiUtente = riferimentiUtente ?? throw new ArgumentNullException(nameof(riferimentiUtente));
            this.AttivaSessionePagamento = attivaSessionePagamento;
        }

        internal AttivaPagamentoOnTheFlyRequest ToAttivaPagamentoOnTheFlyRequest(NodoPagamentiSettings settings)
        {
            if (settings is null)
            {
                throw new ArgumentNullException(nameof(settings));
            }

            //var attivaPagamentoOnTheFlyType = new AttivaPagamentoOnTheFlyType
            //{
            //    cfEnteCreditore = settings.CodiceFiscaleEnteCreditore
            //};

            var registrazioni = this.Causali
                                    .Oneri
                                    .Select(onere => onere.ToRegistrazioneContabileType(this.RiferimentiUtente.ToSoggettoDebitoreType(), this.RiferimentiDomanda.CodiceUnivocoDomanda, DateTime.Now))
                                    .ToArray();

            return new AttivaPagamentoOnTheFlyRequest
            {
                AttivaPagamentoOnTheFlyType = new AttivaPagamentoOnTheFlyType
                {
                    accorpaPosizioni = true,
                    oggettoPagamentoUnico = $"Pagamento oneri per la pratica {this.RiferimentiDomanda.CodiceUnivocoDomanda}",
                    cfEnteCreditore = settings.CodiceFiscaleEnteCreditore,
                    registrazione = registrazioni,
                    urlRedirectEsito = new UrlPagamenti(settings.UrlRitorno, this.RiferimentiDomanda).ToString()
                }
            };
        }


        internal InserisciPosizioniDebitorieType ToInserisciPosizioniDebitorieType(NodoPagamentiSettings settings, DateTime dataScadenza)
        {
            if (settings is null)
            {
                throw new ArgumentNullException(nameof(settings));
            }

            var registrazioni = this.Causali
                        .Oneri
                        .Select(onere => onere.ToRegistrazioneContabileType(this.RiferimentiUtente.ToSoggettoDebitoreType(), this.RiferimentiDomanda.CodiceUnivocoDomanda, dataScadenza))
                        .ToList();

            return new InserisciPosizioniDebitorieType
            {
                cfEnteCreditore = settings.CodiceFiscaleEnteCreditore,
                registrazione = registrazioni.ToArray(),
                accorpaPosizioni = true,
                oggettoPagamentoUnico = $"Pagamento oneri per la pratica {this.RiferimentiDomanda.CodiceUnivocoDomanda}"

                /*registrazione = new[]
                    {
                        new RegistrazioneContabileType
                        {
                            anno =  DateTime.Now.Year.ToString(),
                            causale = new CausaleRegistrazioneType(),
                            importo = this.Causali.Oneri.Sum(x => x.Importo),
                            importoSpecified = true,
                            soggettoDebitore = this.RiferimentiUtente.ToSoggettoDebitoreType(),

                            rate = new RateizzazioneType
                            {
                                rata = new[]
                                {
                                    new PosizioneDebitoriaType
                                    {
                                        dataScadenza = DateTime.Now,
                                        importo = this.Causali
                                                            .Oneri
                                                            .Select(onere => onere.ToImportoPagamentoType())
                                                            .ToArray(),
                                        numeroRata = "1"
                                    }
                                }
                            },

                            descrizione = $"Pagamento oneri per la pratica {this.RiferimentiDomanda.CodiceUnivocoDomanda}",

                            note = ""
                        }
                    }*/
            };
        }
    }
}
