// -----------------------------------------------------------------------
// <copyright file="AnagrafeRepositoryStub.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace SIGePro.Manager.Tests.Logic.MessaggiNotificaFrontoffice.Stubs
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.SIGePro.Manager.Logic.GestioneAnagrafiche;
	using Init.SIGePro.Data;
	
	public class AnagrafeRepositoryStub : IAnagrafeRepository
	{
		Anagrafe _anagrafeDaRestituire;

		public int ChiamatoConId { get; private set; }

		public AnagrafeRepositoryStub(Anagrafe anagrafeDaRestituire)
		{
			this._anagrafeDaRestituire = anagrafeDaRestituire;
		}

		public Anagrafe GetById(int codiceAnagrafe)
		{
			this.ChiamatoConId = codiceAnagrafe;

			_anagrafeDaRestituire.CODICEANAGRAFE = codiceAnagrafe.ToString();

			return _anagrafeDaRestituire;
		}
	}
}
