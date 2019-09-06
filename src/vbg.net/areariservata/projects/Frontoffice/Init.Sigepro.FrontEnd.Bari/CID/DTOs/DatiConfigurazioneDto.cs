// -----------------------------------------------------------------------
// <copyright file="DatiConfigurazioneDto.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Bari.CID.DTOs
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class DatiConfigurazioneDto
	{
		public readonly Uri ServiceUrl;
		public readonly string Username;
		public readonly string Password;

		public DatiConfigurazioneDto(Uri serviceUrl, string username, string password)
		{
			this.ServiceUrl = serviceUrl;
			this.Username = username;
			this.Password = password;
		}
	}
}
