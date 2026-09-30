using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneOneri;
using Init.Sigepro.FrontEnd.AppLogic.Wrappers;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOneri.EditingSession
{
    public class OneriDomandaEditingSession : IOneriDomandaEditingSession
    {
        private static class Constants
        {
            public const string ChiaveDatiExtra = "OneriDomandaEditingSession_ModelloPagamento";
        }

        private readonly OneriDomandaEditingSessionFactory _factory;

        internal OneriDomandaEditingSession(DomandaOnline domanda, OneriDomandaEditingSessionFactory factory)
        {
            this.Domanda = domanda;
            this._factory = factory;
        }

        public ModelloPagamentoEnum ModelloPagamento
        {
            get
            {
                var modello = this.Domanda.ReadInterface.DatiExtra.Get<string>(Constants.ChiaveDatiExtra);

                if (!Enum.TryParse<ModelloPagamentoEnum>(modello, out var risultato))
                {
                    return ModelloPagamentoEnum.PagaDopo;
                }

                return risultato;
            }
        }

        public PresentazioneIstanzaDataKey DataKey => this.Domanda.DataKey;

        public string CodiceComune => this.Domanda.ReadInterface.AltriDati.CodiceComune;

        public IOneriReadInterface ReadInterface => this.Domanda.ReadInterface.Oneri;

        internal DomandaOnline Domanda { get; private set; }

        public void AggiornaStatoPagamentoNativo(string uniqueId, string statoPagamentoNativo)
        {
            this.Domanda.WriteInterface.Oneri.AggiornaStatoPagamentoNativo(uniqueId, statoPagamentoNativo);
        }

        public void AnnullaPagamenti()
        {
            this.Domanda.WriteInterface.DatiExtra.Delete(Constants.ChiaveDatiExtra);

            this.Domanda.WriteInterface.Oneri.AnnullaPagamentiFalliti();
            this.Domanda.WriteInterface.Oneri.AnnullaPagamentiInCorso();
        }

        public void AnnullaPagamentiByUniqueId(IEnumerable<string> uniqueIds)
        {
            this.Domanda.WriteInterface.DatiExtra.Delete(Constants.ChiaveDatiExtra);

            this.Domanda.WriteInterface.Oneri.AnnullaPagamentiByUniqueId(uniqueIds);
        }

        public IEnumerable<OnereFrontoffice> AvviaOperazioneDiPagamento(IGuidWrapperService guidWrapperService, ModelloPagamentoEnum modelloPagamento)
        {
            this.Domanda.WriteInterface.DatiExtra.Set(Constants.ChiaveDatiExtra, modelloPagamento.ToString());

            return this.Domanda.WriteInterface.Oneri.AvviaOperazioneDiPagamento(guidWrapperService);
        }

        public void ImpostaRiferimentiNodoPagamenti(string uniqueId, string idPosizioneDebitoria, string IUV)
        {
            this.Domanda.WriteInterface.Oneri.ImpostaRiferimentiNodoPagamenti(uniqueId, idPosizioneDebitoria, IUV);
        }

        public bool IsPagamentoAvviato
        {
            get
            {
                if (this.Domanda.ReadInterface.Oneri.GetOneriOnlineProntiPerPagamento().Any())
                {
                    return false;
                }

                return this.Domanda.ReadInterface.Oneri.GetOneriOnlineConPagamentoAvviato().Any() ||
                       this.Domanda.ReadInterface.Oneri.GetOneriOnlineConPagamentoRiuscito().Any() ||
                       this.Domanda.ReadInterface.Oneri.GetOneriOnlineConPagamentoFallito().Any();

            }
        }

        public bool RichiedePagamentoOnLine => this.Domanda.ReadInterface.Oneri.GetOneriConPagamentoOnline().Any();

        public string DescrizioneIntervento => this.Domanda.ReadInterface.AltriDati.Intervento.DescrizioneBreve;

        public IDomandaOnlineReadInterface LegacyFullReadInterface => this.Domanda.ReadInterface;

        public void PagamentoFallitoDaNodoPagamenti(string uniqueId)
        {
            this.Domanda.WriteInterface.Oneri.PagamentoFallitoDaNodoPagamenti(uniqueId);
        }

        public void PagamentoRiuscitoDaNodoPagamenti(string uniqueId, string codiceFiscaleEnteCreditore, DateTime dataOraPagamento, TipoPagamento tipoPagamento)
        {
            this.Domanda.WriteInterface.Oneri.PagamentoRiuscitoDaNodoPagamenti(uniqueId, codiceFiscaleEnteCreditore, dataOraPagamento, tipoPagamento);
        }

        public void TerminaSessioneModifica()
        {
            this._factory.TerminaSessioneModifica(this);

            this.Domanda = null;
        }

        public IEnumerable<OnereFrontoffice> GetOneriOnlineConPagamentoFallito()
        {
            return this.Domanda.ReadInterface.Oneri.GetOneriOnlineConPagamentoFallito().ToList();
        }

        public IEnumerable<OnereFrontoffice> GetOneriOnlineConPagamentoAvviato()
        {
            return this.Domanda.ReadInterface.Oneri.GetOneriOnlineConPagamentoAvviato().ToList();
        }

        public void LegacySetValoreDatoExtra(string chiave, string valore)
        {
            this.Domanda.WriteInterface.DatiExtra.SetValoreDato(chiave, valore);
        }

        public void LegacyAvviaPagamentoOneriOnline(string numeroOperazione, IEnumerable<OnereFrontoffice> oneri)
        {
            this.Domanda.WriteInterface.Oneri.AvviaPagamentoOneriOnline(numeroOperazione, oneri);
        }

        public void LegacyPagamentoRiuscito(DateTime dataOraTransazione, string rifPraticaEsterna, string iuv, string idTransazione, TipoPagamento tipoPagamento)
        {
            this.Domanda.WriteInterface.Oneri.PagamentoRiuscito(dataOraTransazione, rifPraticaEsterna, iuv, idTransazione, tipoPagamento);
        }

        public T LegacyGetDatoExtra<T>(string chiave) where T : class
        {
            return this.Domanda.ReadInterface.DatiExtra.Get<T>(chiave);
        }
    }


}
