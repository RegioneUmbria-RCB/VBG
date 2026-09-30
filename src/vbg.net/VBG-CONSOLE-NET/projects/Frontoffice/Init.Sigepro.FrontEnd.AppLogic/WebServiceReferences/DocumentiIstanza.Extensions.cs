// -----------------------------------------------------------------------
// <copyright file="DocumentiIstanzaExtensions.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public partial class DocumentiIstanzaOggetti
	{
		private static class Constants
		{
			public const string Md5Key = "MD5_SUM";
		}

		public bool HasMd5
		{
			get
			{
				return !String.IsNullOrEmpty(this.Md5);
			}
		}

		public string Md5
		{
			get { return this.GetMetadatoMd5(); }
		}

		private string GetMetadatoMd5()
		{
			if (this.Metadati == null)
			{
				return string.Empty;
			}

			var metadato = this.Metadati.Where(x => x.Chiave == Constants.Md5Key).FirstOrDefault();

			return metadato == null ? String.Empty : metadato.Valore;
		}
	}
}
