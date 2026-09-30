using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Sit.IntegrazioneSitBari.RicercaCivico;
using CuttingEdge.Conditions;
using Init.SIGePro.Sit.Utils;
using log4net;

namespace Init.SIGePro.Sit.IntegrazioneSitBari
{
	public class InformazioniEsteseCivico
	{
		public readonly string Circoscrizione;
		public readonly string Localita;
		public readonly string CodCivico;

		public InformazioniEsteseCivico(string circoscrizione, string localita, string codCivico)
		{
			this.Circoscrizione = circoscrizione;
			this.Localita = localita;
			this.CodCivico = codCivico;
		}
	}
	public interface ISitNautilusBariService
	{
		InformazioniEsteseCivico VerificaEsistenzaIndirizzo(string codiceVia, uint civico, string esponente);
	}

	public class SitNautilusBariService : ISitNautilusBariService
	{
		readonly IConfigurazioneSitBari _configurazioneSit;
		readonly IRicercaCivicoSEIAdapter _serviceAdapter;

		ILog _log = LogManager.GetLogger(typeof(SitNautilusBariService));

		internal SitNautilusBariService(IConfigurazioneSitBari configurazioneSit, IRicercaCivicoSEIAdapter serviceAdapter)
		{
			Condition.Requires(configurazioneSit, "configurazioneSit").IsNotNull();
			Condition.Requires(serviceAdapter, "serviceAdapter").IsNotNull();
			Condition.Requires(configurazioneSit.CodEnte, "configurazioneSit.CodEnte").IsNotNullOrEmpty();
			Condition.Requires(configurazioneSit.RequestFrom, "configurazioneSit.RequestFrom").IsNotNullOrEmpty();
			Condition.Requires(configurazioneSit.TipoIndirizzoRicercato, "configurazioneSit.TipoIndirizzoRicercato").IsNotNullOrEmpty();

			if (configurazioneSit.TipoIndirizzoRicercato != "c" && configurazioneSit.TipoIndirizzoRicercato != "n")
				throw new ArgumentOutOfRangeException("Il parametro di configurazione TipoIndirizzoRicercato del sit può essere solamente \"c\" o \"n\"");

			Condition.Requires(configurazioneSit.TipoIndirizzoRicercato, "configurazioneSit.TipoIndirizzoRicercato").IsNotNullOrEmpty();

			this._configurazioneSit = configurazioneSit;
			this._serviceAdapter = serviceAdapter;
		}

		public InformazioniEsteseCivico VerificaEsistenzaIndirizzo(string codiceVia, uint civico, string esponente)
		{
			Condition.Requires(codiceVia, "codiceVia").IsNotNullOrEmpty();

			var request = new requestType
			{
				request_from = this._configurazioneSit.RequestFrom,
				Item = new requestTypeService_paramsAbstractTP03_RicercaCivico_request
				{
					max_rows_ret = 100,
					max_rows_retSpecified = true,
					indirizzo_ricercato = new indirizzo_ricercato
					{
						tipo = (indirizzo_ricercatoTipo)Enum.Parse(typeof(indirizzo_ricercatoTipo), this._configurazioneSit.TipoIndirizzoRicercato),
						tipoSpecified = true,
						ente_cod = this._configurazioneSit.CodEnte,
						via_denom_cod = codiceVia,
						numero_civico = new numero_civico
						{
							civico_num = civico,
							civico_esp = esponente
						}
					}
				}
			};

			var esito = this._serviceAdapter.EseguiRicerca(request);

			if (esito == null || esito.header.is_error.value || esito.header.rows_found == 0)
			{
				if (esito != null)
					_log.ErrorFormat("Errore durante la lettura dei dati del sit di Bari->cod errore: {0}, Descrizione errore: {1}, xml completo: {2}", esito.header.err_code, esito.header.message, esito.XmlSerializeToString());

				return null;
			}

			if (esito.Item.Item is indirizzo)
			{
				var indirizzo = (indirizzo)esito.Item.Item;

				var circoscrizione = indirizzo.civico.circoscrizione;
				var localita = indirizzo.civico.localitafrazione;
				var codCivico = indirizzo.civico.cod_civico;

				return new InformazioniEsteseCivico(circoscrizione, localita, codCivico);
			}

			return new InformazioniEsteseCivico("","","");

			/*esito.Item.Item.GetType() == typeof(indirizzo) && 
			((indirizzo)esito.Item.Item).civico != null && 
			((indirizzo)esito.Item.Item).via != null ;*/

		}
	}
}
