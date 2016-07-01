// -----------------------------------------------------------------------
// <copyright file="DatiOperatoreDto.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Bari.CID.DTOs
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using log4net;
	using Init.Sigepro.FrontEnd.Bari.CID.ServiceProxy;
	using Init.Utils;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class DatiOperatoreDto
	{
		public readonly string Nome;
		public readonly string Email;
		public readonly string CodiceFiscale;
		public readonly string PartitaIva;

		ILog _log = LogManager.GetLogger(typeof(DatiOperatoreDto));

		public DatiOperatoreDto(string nome, string email, string codiceFiscale, string partitaIva = null)
		{
			this.Nome = nome;
			this.Email = email;
			this.CodiceFiscale = codiceFiscale;
			this.PartitaIva = partitaIva;
		}

		internal datiTracciamentoRequestType ToDatiTracciamentoRequestType()
		{
			_log.DebugFormat("DatiOperatoreDto: dati tracciamento Cid: codicefiscale={0}, emailoperatore={1}, nomeoperatore={2}, partitaiva={3}", this.CodiceFiscale, this.Email, this.Nome, this.PartitaIva );

			var dati = new datiTracciamentoRequestType();

			dati.emailOperatore = null;
			dati.pIvaOperatore = null;

			if(!String.IsNullOrEmpty(this.Email))
				dati.emailOperatore = this.Email;

			if(!String.IsNullOrEmpty(this.PartitaIva))
				dati.pIvaOperatore = this.PartitaIva;

			dati.codFiscaleOperatore = this.CodiceFiscale;
			dati.nomeOperatore = this.Nome;

			if (_log.IsDebugEnabled)
			{
				_log.Debug(StreamUtils.SerializeClass(dati));
			}

			return dati;
		}
	}
}
