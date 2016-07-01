using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using log4net;
using Ninject;
using Init.Sigepro.FrontEnd.GestioneMovimenti.Persistence;
using Init.Sigepro.FrontEnd.Infrastructure.Repositories;
using Init.Sigepro.FrontEnd.GestioneMovimenti.ReadInterface.Persistence;

namespace Init.Sigepro.FrontEnd.Reserved.GestioneMovimenti
{
	public class MovimentiBasePage : ReservedBasePage
	{
		ILog _log = LogManager.GetLogger(typeof(MovimentiBasePage));

		[Inject]
		protected IIdMovimentoResolver _idMovimentoResolver { get; set; }

		protected int IdMovimento
		{
			get
			{
				return this._idMovimentoResolver.IdMovimento;
			}
		}
	}
}