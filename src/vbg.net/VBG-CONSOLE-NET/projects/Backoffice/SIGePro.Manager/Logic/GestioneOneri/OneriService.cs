using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Manager.Configuration;
using Init.SIGePro.Manager.IstanzeOneriService;

namespace Init.SIGePro.Manager.Logic.GestioneOneri
{
	public class OneriService: IOneriService
	{
		string _tokenUtente;
		string _idComune;

		public OneriService(string tokenUtente, string idComune)
		{
			this._tokenUtente = tokenUtente;
			this._idComune = idComune;
		}

		public void Inserisci(int codiceIstanza, int idTipoCausale, double valore)
		{
			using(var ws = CreaServizio())
			{
				var esito = ws.InsertOnere(new InsertOnereRequest
				{
					importo = Convert.ToDecimal(valore),
					riferimentoIstanza = codiceIstanza.ToString(),
					riferimentoCausale = idTipoCausale.ToString(),
					token = this._tokenUtente
				});

				if (esito.esitoOperazione == null) 
				{
					throw new Exception((String.Format("Si è verificato un errore inaspettato durante la creazione dell'onere con tipo causale {0} e importo {1}", idTipoCausale, valore)));
				}

				if (esito.esitoOperazione.esito == 0)
				{
					var arrayErrori = esito.esitoOperazione.listaErrori.Select(x => String.Format("{0} - {1}", x.codice, x.descrizione)).ToArray();

					throw new Exception((String.Format("Si è verificato un errore durante la creazione dell'onere con tipo causale {0} e importo {1}: {2}", idTipoCausale, valore, String.Join(", ", arrayErrori))));
				}
			}
		}

		public IEnumerable<IstanzeOneri> Inserisci(IEnumerable<IstanzeOneri> oneri)
		{
			using (var ws = CreaServizio())
			{
				try
				{

					return oneri.Select(x => Inserisci(ws, x)).ToList();
				}
				catch(Exception)
				{
					ws.Abort();

					throw;
				}
			}
		}

		public IstanzeOneri Inserisci(IstanzeoneriClient ws, IstanzeOneri onere)
		{
			var req = new InsertOnereRequest
			{
				codiceamministrazioni = onere.CODICEAMMINISTRAZIONE,
				codiceresponsabile = onere.CODICEUTENTE,
				dataPagamento = onere.DATAPAGAMENTO.GetValueOrDefault(DateTime.MinValue),
				dataPagamentoSpecified = onere.DATAPAGAMENTO.HasValue,
				datascadenza = onere.DATASCADENZA.GetValueOrDefault(DateTime.MinValue),
				datascadenzaSpecified = onere.DATASCADENZA.HasValue,
				importo = Convert.ToDecimal(onere.PREZZO.GetValueOrDefault(0.0d)),
				importopagato = Convert.ToDecimal(onere.ImportoPagato.GetValueOrDefault(0)),
				importopagatoSpecified = onere.ImportoPagato.HasValue,
				note = onere.NOTE,
				numerorata = onere.NUMERORATA,
				riferimentiPagamento = onere.NR_DOCUMENTO,
				riferimentoCausale = onere.FKIDTIPOCAUSALE,
				riferimentoEndoprocedimento = onere.CODICEINVENTARIO,
				riferimentoIstanza = onere.CODICEISTANZA,
				token = this._tokenUtente
			};

			if(!String.IsNullOrEmpty(onere.FKMODALITAPAGAMENTO))
			{
				req.modalitaPagamento = new ModalitaPagamentoType
				{
					codice = onere.FKMODALITAPAGAMENTO
				};
			}

			var esito = ws.InsertOnere(req);


			if (esito.esitoOperazione == null)
			{
				throw new Exception("Si è verificato un errore inaspettato durante la creazione dell'onere");
			}

			if (esito.esitoOperazione.esito == 0)
			{
				var arrayErrori = esito.esitoOperazione.listaErrori.Select(x => String.Format("{0} - {1}", x.codice, x.descrizione)).ToArray();

				throw new Exception(String.Format("Si è verificato un errore durante la creazione dell'onere: {0}", String.Join(", ", arrayErrori)));
			}

			onere.ID = esito.codiceonere;

			return onere;
		}

		private IstanzeOneriService.IstanzeoneriClient CreaServizio()
		{
			var endpoint = new EndpointAddress(ParametriConfigurazione.Get.WsIstanzeOneriServiceUrl);
			// var endpoint = new EndpointAddress("http://devdesk103:8080/backend/services/istanzeoneri");
			var binding = new BasicHttpBinding("oneriHttpBinding");

			return new IstanzeoneriClient(binding, endpoint);
		}
	}
}
