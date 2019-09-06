using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOneri;
using log4net;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneOneri
{
	public class OneriReadInterface : IOneriReadInterface
	{
        ILog _log = LogManager.GetLogger(typeof(OneriReadInterface));

		PresentazioneIstanzaDbV2 _database;
		List<OnereFrontoffice> _oneri;
		AttestazioneDiPagamento _attestazioneDiPagamento = AttestazioneDiPagamento.NonPresente;
		int _importoAttestazionePagamento = 0;
		float _totaleOneri = 0.0f;
		float _totaleOneriPagati = 0.0f;
		bool _dichiaraDiNonAvereOneriDaPagare = false;

		public OneriReadInterface( PresentazioneIstanzaDbV2 database )
		{
			this._database = database;

			PreparaOneri();
			PreparaAttestazioneDiPagamento();

			if( ((int)(this._totaleOneri * 100.0f)) != this._importoAttestazionePagamento )
				this._attestazioneDiPagamento = AttestazioneDiPagamento.NonPresente;
		}

		private void PreparaAttestazioneDiPagamento()
		{
            _log.DebugFormat("PreparaAttestazioneDiPagamento");

			this._attestazioneDiPagamento = AttestazioneDiPagamento.NonPresente;

            if (this._database.OneriAttestazionePagamento.Count == 0)
            {
                _log.DebugFormat("this._database.OneriAttestazionePagamento.Count == 0");

                return;
            }

			var row = this._database.OneriAttestazionePagamento[0];
			
			this._importoAttestazionePagamento = row.Importo;

            _log.DebugFormat("Importo attestazione di pagamento = {0}", this._importoAttestazionePagamento);
            _log.DebugFormat("row.IsIdAllegatoNull()  = {0}", row.IsIdAllegatoNull());
            
			if( !row.IsIdAllegatoNull() )
			{
                _log.DebugFormat("row.IdAllegato = {0}", row.IdAllegato);

				var idAllegato	= row.IdAllegato;
				var allegato	= this._database.Allegati.FindById( idAllegato );

				this._attestazioneDiPagamento = new AttestazioneDiPagamento(true, allegato.NomeFile, allegato.CodiceOggetto, allegato.FirmatoDigitalmente);
			}

			this._dichiaraDiNonAvereOneriDaPagare = false;

			if(!row.IsDichiaraDiNonAvereOneriDaPagareNull())
				this._dichiaraDiNonAvereOneriDaPagare = row.DichiaraDiNonAvereOneriDaPagare;
		}

		private void PreparaOneri()
		{
			this._oneri = new List<OnereFrontoffice>();

			foreach (var onere in this._database.OneriDomanda.Cast<PresentazioneIstanzaDbV2.OneriDomandaRow>())
				this._oneri.Add(OnereFrontoffice.FromOneriRow(onere));

			this._totaleOneri = 0.0f;
			this._totaleOneriPagati = 0.0f;

			this._oneri.ForEach(x => 
			{
				this._totaleOneri += x.Importo;

				if (x.EstremiPagamento != null)
					this._totaleOneriPagati += x.ImportoPagato;

			});
		}

		public IEnumerable<OnereFrontoffice> Oneri { get { return this._oneri; } }

		public AttestazioneDiPagamento AttestazioneDiPagamento { get { return this._attestazioneDiPagamento; } }

		public bool DichiaraDiNonAvereOneriDaPagare
		{
			get { return this._dichiaraDiNonAvereOneriDaPagare; }
		}

		public double Totale
		{
			get { return this._totaleOneri; }
		}

		public double TotalePagato
		{
			get { return this._totaleOneriPagati; }
		}

		#region IOneriReadInterface Members

		public IEnumerable<OnereFrontoffice> OneriIntervento
		{
			get { return this._oneri.Where( x => x.Provenienza == OnereFrontoffice.ProvenienzaOnereEnum.Intervento); }
		}

		public IEnumerable<OnereFrontoffice> OneriEndoprocedimenti
		{
			get { return this._oneri.Where( x => x.Provenienza == OnereFrontoffice.ProvenienzaOnereEnum.Endoprocedimento); }
		}

		#endregion


        public IEnumerable<OnereFrontoffice> GetOneriProntiPerPagamentoOnline()
        {
            return this.Oneri.Where(x => x.ModalitaPagamento == ModalitaPagamentoOnereEnum.Online && x.StatoPagamento == StatoPagamentoOnereEnum.ProntoPerPagamentoOnline);
        }


        public IEnumerable<OnereFrontoffice> GetOperazioniConPagamentoInSospeso()
        {
            return this.Oneri.Where(x => x.StatoPagamento == StatoPagamentoOnereEnum.PagamentoIniziato);
        }


        public IEnumerable<OnereFrontoffice> GetOneriDaIdOperazione(string idOperazione)
        {
            return this.Oneri.Where(x => x.IdOperazionePagamento == idOperazione);
        }
    }
}
