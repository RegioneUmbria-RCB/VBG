// -----------------------------------------------------------------------
// <copyright file="CartService.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.Services.Cart
{
	using System;
	using CuttingEdge.Conditions;
	using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
	using log4net;




	public interface IFacctRedirectService
	{
		string GetUrlApplicazioneBdr(int idDomanda, string codiceComune, int? idIntervento);
	}



	public class FacctRedirectService : IFacctRedirectService
	{

		#region Exceptions
		[Serializable]
		public class InterventoNonTrovatoException : Exception
		{
			internal InterventoNonTrovatoException():base("La domanda corrente non ha un intervento definito. Verificare la configurazione del workflow in modo da posizionare lo step di selezione intervento prima di questo step") { }
			internal InterventoNonTrovatoException(string message) : base(message) { }
			internal InterventoNonTrovatoException(string message, Exception inner) : base(message, inner) { }
			protected InterventoNonTrovatoException(
			  System.Runtime.Serialization.SerializationInfo info,
			  System.Runtime.Serialization.StreamingContext context)
				: base(info, context) { }
		}
		
		#endregion

		ILog _log;
		ICartRepository _cartRepository;
        IFacctUrlBuilder _urlBuilder;


        public FacctRedirectService( ICartRepository cartRepository, IFacctUrlBuilder urlBuilder)
		{			
			Condition.Requires(cartRepository, "cartRepository").IsNotNull();			
			
			this._cartRepository	 = cartRepository;
            this._urlBuilder = urlBuilder;

			this._log = LogManager.GetLogger(this.GetType());
		}


		public string GetUrlApplicazioneBdr(int idDomanda,string codiceComune,int? idIntervento)
		{
			_log.Debug("Inizio verifica trasferimento ad applicazione FACCT");

			// Se la domanda corrente non ha un intervento vuol dire che lo step è stato 
			// posizionato prima di quello di selezione intervento.
			// In questo caso è meglio che la presentazione della domanda non prosegua
			if (!idIntervento.HasValue)
			{
				_log.Error($"La domanda con codice {idDomanda} non ha un intervento definito. Verificare la configurazione del workflow in modo da posizionare lo step di selezione intervento prima di questo step");

				throw new InterventoNonTrovatoException();
			}

			// Se l'intervento corrente non ha associato un id CART il workflow prosegue normalmente 
			// altrimenti il controllo viene passato all'applicazione FACCT
			var codiceBdr = _cartRepository.GetCodiceAttivitaBdrDaIdIntervento( idIntervento.Value );

			if (String.IsNullOrEmpty(codiceBdr))
			{
				_log.Debug($"L'intervento della domanda con codice {idDomanda} non ha un associato un codice CART, il workflow proseguirà normalmente. codice intervento: {idIntervento}");

				return String.Empty;
			}

			var urlCompletoApplicazioneBdr = this._urlBuilder.BuildUrl(idDomanda, codiceComune, idIntervento.Value , codiceBdr );

			return urlCompletoApplicazioneBdr;
		}
	}

}
