using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.Common;

using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni;
using Init.Sigepro.FrontEnd.AppLogic.GestioneInterventi;

namespace Init.Sigepro.FrontEnd.AppLogic.ReadInterface.Imp
{
	internal class ReadFacadeImp : IReadFacade
	{
		IIdDomandaResolver _idDomandaResolver;
		DomandeOnlineService _domandeOnlineService;

		public ReadFacadeImp(IIdDomandaResolver idDomandaResolver, DomandeOnlineService domandeOnlineService)
		{
			_idDomandaResolver = idDomandaResolver;
			_domandeOnlineService = domandeOnlineService;
		}


		#region IReadFacade Members

		[Inject]
		public IComuniService Comuni
		{
			get;
			set;
		}

		public IDomandaOnlineReadInterface Domanda
		{
			get 
			{
				return _domandeOnlineService.GetById(_idDomandaResolver.IdDomanda).ReadInterface;
			}
		}

		public PresentazioneIstanzaDataKey DomandaDataKey
		{
			get { return _domandeOnlineService.GetById(_idDomandaResolver.IdDomanda).DataKey; }
		}

		[Inject]
		public ITipiSoggettoService TipiSoggetto
		{
			get;
			set;
		}


		[Inject]
		public IAtecoRepository Ateco
		{
			get;
			set;
		}

		[Inject]
		public IInterventiRepository Interventi
		{
			get;
			set;
		}

		[Inject]
		public IStradarioRepository Stradario
		{
			get;
			set;
		}


		[Inject]
		public ICittadinanzeService Cittadinanze
		{
			get;
			set;
		}

		#endregion
	}
}
