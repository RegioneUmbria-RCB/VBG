using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.Pagamenti.EntraNextService;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.Pagamenti.ENTRANEXT
{
    public class PaymentRequestAdapterEntraNext
    {
        IUrlEncoder _urlEncoder = new HttpContextUrlEncoder();
        IResolveUrl _resolveUrl = new HttpContextResolveUrl();

        PaymentSettingsEntraNext _settings;
        IniziaPagamentoEntraNextRequest _iniziaPagamentoRequest;

        public PaymentRequestAdapterEntraNext(PaymentSettingsEntraNext settings, IniziaPagamentoEntraNextRequest iniziaPagamentoRequest)
        {
            this._settings = settings;
            this._iniziaPagamentoRequest = iniziaPagamentoRequest;
        }         

        public InserisciPosizioniInAttesaRequest Adatta()
        {
            var urlBack = new UrlPagamenti(this._settings.UrlBack, this._iniziaPagamentoRequest.RiferimentiDomanda, this._urlEncoder, this._resolveUrl).ToString();
            var urlRitorno = $"{new UrlPagamenti(this._settings.UrlRitorno, this._iniziaPagamentoRequest.RiferimentiDomanda, this._urlEncoder, this._resolveUrl).ToString()}&codicePagamento={this._iniziaPagamentoRequest.RiferimentiOperazione.NumeroOperazione}";
            var urlNotifica = "";// new UrlPagamenti(this._settings.UrlNotifica, this._iniziaPagamentoRequest.RiferimentiDomanda, this._urlEncoder, this._resolveUrl).ToString();
            var importoTotale = this._iniziaPagamentoRequest.RiferimentiOperazione.Oneri.Sum(x => x.Importo);

            //da modificare quando sarà cambiato il web service
            // var causale = this._iniziaPagamentoRequest.RiferimentiOperazione.Oneri.First().Key;

            return new InserisciPosizioniInAttesaRequest
            {
                UrlBack = urlBack,
                UrlReturn = urlRitorno,
                UrlNotificaRT = urlNotifica,
                PosizioniDebitorie = new PosizioneDebitoriaInAttesa[]
                {
                    new PosizioneDebitoriaInAttesa
                    {
                        AnnoImposta = String.IsNullOrEmpty(this._iniziaPagamentoRequest.RiferimentiOperazione.AnnoDocumento) ? DateTime.Now.Year : Convert.ToInt32(this._iniziaPagamentoRequest.RiferimentiOperazione.AnnoDocumento),
                        ImportoDovuto = Convert.ToDecimal(importoTotale),
                        RiferimentoPraticaEsterna = this._iniziaPagamentoRequest.RiferimentiOperazione.NumeroOperazione,
                        Descrizione = "Pagamento Oneri",
                        Annullato = false,
                        GestioneIva = false,
                        CodiceSottoservizio = "", //CodiceSottoservizio = "WEB01",
                        SoggettoPagatore = new SoggettoPagatore
                        {
                            CodiceFiscale = this._iniziaPagamentoRequest.RiferimentiUtente.IdentificativoUtente,
                            PartitaIva = this._iniziaPagamentoRequest.RiferimentiUtente.PartitaIva,
                            Cognome = this._iniziaPagamentoRequest.RiferimentiUtente.Cognome,
                            Email = this._iniziaPagamentoRequest.RiferimentiUtente.Email,
                            Nome = this._iniziaPagamentoRequest.RiferimentiUtente.Nome,
                            RagioneSociale = this._iniziaPagamentoRequest.RiferimentiUtente.RagioneSociale,
                            NaturaGiuridica = this._iniziaPagamentoRequest.RiferimentiUtente.NaturaGiuridica == "F" ? NaturaGiuridica.PersonaFisica : NaturaGiuridica.PersonaGiuridica,
                            Residenza = new Ubicazione
                            {
                                CAP = this._iniziaPagamentoRequest.RiferimentiUtente.Cap,
                                Comune = this._iniziaPagamentoRequest.RiferimentiUtente.Comune,
                                Indirizzo = this._iniziaPagamentoRequest.RiferimentiUtente.Indirizzo,
                                Localita = this._iniziaPagamentoRequest.RiferimentiUtente.Localita,
                                Provincia = this._iniziaPagamentoRequest.RiferimentiUtente.Provincia
                            }
                        },
                        SoggettoVersante = new SoggettoVersante
                        {
                            CodiceFiscale = this._iniziaPagamentoRequest.RiferimentiUtente.IdentificativoUtente,
                            PartitaIva = this._iniziaPagamentoRequest.RiferimentiUtente.PartitaIva,
                            Cognome = this._iniziaPagamentoRequest.RiferimentiUtente.Cognome,
                            Email = this._iniziaPagamentoRequest.RiferimentiUtente.Email,
                            Nome = this._iniziaPagamentoRequest.RiferimentiUtente.Nome,
                            RagioneSociale = this._iniziaPagamentoRequest.RiferimentiUtente.RagioneSociale,
                            NaturaGiuridica = this._iniziaPagamentoRequest.RiferimentiUtente.NaturaGiuridica == "F" ? NaturaGiuridica.PersonaFisica : NaturaGiuridica.PersonaGiuridica,
                            Residenza = new Ubicazione
                            {
                                CAP = this._iniziaPagamentoRequest.RiferimentiUtente.Cap,
                                Comune = this._iniziaPagamentoRequest.RiferimentiUtente.Comune,
                                Indirizzo = this._iniziaPagamentoRequest.RiferimentiUtente.Indirizzo,
                                Localita = this._iniziaPagamentoRequest.RiferimentiUtente.Localita,
                                Provincia = this._iniziaPagamentoRequest.RiferimentiUtente.Provincia
                            }
                        },
                        Dettagli = this._iniziaPagamentoRequest.RiferimentiOperazione.Oneri.Select(x => new PosizioneDebitoriaInAttesa_Dettaglio
                        {
                            NomeVoceDiCosto = x.NomeVoceDiCosto,
                            Descrizione = x.Descrizione,
                            Quantita = x.Quantita,
                            Iva = x.Iva,
                            AliquotaIva = x.AliquotaIva,
                            NumeroAccertamentoContabile = x.NumeroAccertamentoContabile,
                            CausaleImporto = x.CausaleImporto,
                            Importo = Convert.ToDecimal( x.Importo )
                        }).ToArray()
                    }
                }
            };
        }
    }
}
