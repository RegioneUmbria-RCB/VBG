// -----------------------------------------------------------------------
// <copyright file="OperatoriRepositoryStub.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace SIGePro.Manager.Tests.Logic.MessaggiNotificaFrontoffice.Stubs
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.SIGePro.Data;
	using Init.SIGePro.Manager.Logic.GestioneOperatori;

	public class OperatoriRepositoryStub : IOperatoriRepository
	{
		Responsabili _valoreDaRestituire;

		public int ChiamatoConId { get; private set; }

		public OperatoriRepositoryStub(Responsabili valoreDaRestituire)
		{
			this._valoreDaRestituire = valoreDaRestituire;
		}

		public Responsabili GetById(int id)
		{
			this.ChiamatoConId = id;

			_valoreDaRestituire.CODICERESPONSABILE = id.ToString();

			return _valoreDaRestituire;
		}
	}
}
