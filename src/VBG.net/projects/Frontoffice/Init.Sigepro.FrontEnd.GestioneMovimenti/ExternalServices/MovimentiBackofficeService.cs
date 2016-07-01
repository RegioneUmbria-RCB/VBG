// -----------------------------------------------------------------------
// <copyright file="MovimentiBackofficeService.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.ExternalServices
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
using Init.Sigepro.FrontEnd.GestioneMovimenti.MovimentiWebService;
	using Init.Sigepro.FrontEnd.GestioneMovimenti.ReadInterface;
	using AutoMapper;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.GestioneMovimenti.Persistence;
	using ServiceStack.Text;

	/// <summary>
	/// Servizio responsabile della lettura dei dati di un movimento effettuato nel backoffice
	/// </summary>
	public interface IMovimentiBackofficeService
	{
		/// <summary>
		/// Ottiene i dati di un movimento del backoffice a partire dal relativo identificativo univoco
		/// </summary>
		/// <param name="idMovimento">Identificativo univoco del movimento</param>
		/// <returns>Dati del movimento letto o null se ilmovimento non è stato trovato</returns>
		DatiMovimentoDiOrigine GetById(int idMovimento);

		/// <summary>
		/// Carica il datastore relativo al movimento corrispondente all'identificativo specificato
		/// </summary>
		/// <param name="idMovimento">Identificativo univoco del movimento di cui va caricato il datastore</param>
		/// <returns>Datastore del movimento corrispondente all'identificativo specificato o null se il movimento non ha ancora un datastore</returns>
		GestioneMovimentiDataStore GetDataStore(int idMovimento);

		/// <summary>
		/// Salva il datastore relativo al movimento corrispondente all'identificativo specificato
		/// </summary>
		/// <param name="idMovimento"></param>
		/// <param name="dataStore"></param>
		void Save(int idMovimento, GestioneMovimentiDataStore dataStore);

		/// <summary>
		/// Imposta il flag trasmesso == 1 nel movimento corrispondente all'identificativo specificato
		/// </summary>
		/// <param name="idMovimento">Identificativo univoco del movimento di cui è stata effettuata la trasmisisone</param>
		void ImpostaComeTrasmesso(int idMovimento);
	}

	/// <summary>
	/// Utilizza il web service di backoffice per permettere la lettura dei dati di un movimento effettuato nel backoffice
	/// </summary>
	public class MovimentiBackofficeService: IMovimentiBackofficeService
	{
		MovimentiBackofficeServiceCreator _serviceCreator;
		IAliasResolver _aliasResolver;

		public MovimentiBackofficeService(IAliasResolver aliasResolver, MovimentiBackofficeServiceCreator serviceCreator)
		{
			this._serviceCreator = serviceCreator;
			this._aliasResolver = aliasResolver;
		}

		#region IMovimentiBackofficeService Members

		public DatiMovimentoDiOrigine GetById(int idMovimento)
		{
			using( var svc = this._serviceCreator.CreateClient( this._aliasResolver.AliasComune ) )
			{
				return Mapper.Map<DatiMovimentoDaEffettuareDto, DatiMovimentoDiOrigine>(svc.Service.GetMovimento(svc.Token, idMovimento.ToString()));
			}
		}

		public GestioneMovimentiDataStore GetDataStore(int idMovimento)
		{
			using (var svc = this._serviceCreator.CreateClient(this._aliasResolver.AliasComune))
			{
				var json = svc.Service.GetJsonMovimentoFrontoffice(svc.Token, idMovimento);

				return TypeSerializer.DeserializeFromString<GestioneMovimentiDataStore>(json);
			}
		}


		public void Save(int idMovimento, GestioneMovimentiDataStore dataStore)
		{
			using (var svc = this._serviceCreator.CreateClient(this._aliasResolver.AliasComune))
			{
				var json = TypeSerializer.SerializeToString<GestioneMovimentiDataStore>(dataStore);

				svc.Service.SalvaJsonMovimentoFrontoffice(svc.Token, idMovimento, json);
			}
		}


		public void ImpostaComeTrasmesso(int idMovimento)
		{
			using (var svc = this._serviceCreator.CreateClient(this._aliasResolver.AliasComune))
			{
				svc.Service.ImpostaFlagTrasmesso(svc.Token, idMovimento);
			}
		}

		#endregion
	}
}
