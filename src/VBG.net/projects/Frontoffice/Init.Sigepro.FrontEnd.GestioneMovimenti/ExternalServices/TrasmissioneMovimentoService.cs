// -----------------------------------------------------------------------
// <copyright file="InvioMovimentoService.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.ExternalServices
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
	using Init.Sigepro.FrontEnd.AppLogic.StcService;
using Init.Sigepro.FrontEnd.GestioneMovimenti.ReadInterface.Persistence;
	using System.Web;
	using System.IO;
	using ServiceStack.Text;
using log4net;
	using AutoMapper;
	using Init.Sigepro.FrontEnd.GestioneMovimenti.ReadInterface;
	using Init.Sigepro.FrontEnd.AppLogic.STC;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public interface ITrasmissioneMovimentoService
	{
		void Trasmetti(int idMovimento);
	}

	public class TrasmissioneMovimentoService : ITrasmissioneMovimentoService
	{
		IStcService _stcService;
		IMovimentiReadRepository _readRepository;
		ILog _log = LogManager.GetLogger(typeof(TrasmissioneMovimentoService));

		public TrasmissioneMovimentoService(IStcService stcService,IMovimentiReadRepository readRepository)
		{
			this._readRepository = readRepository;
			this._stcService = stcService;
		}

		#region ITrasmissioneMovimentoService Members

		public void Trasmetti(int idMovimento)
		{
			var datiMovimento = this._readRepository.GetById(idMovimento);

			var request = Mapper.Map<DatiMovimentoDaEffettuare, NotificaAttivitaRequest>(datiMovimento);

			this._stcService.NotificaAttivita(request);
		}

		
		#endregion
	}

}
