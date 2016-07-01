namespace Init.Sigepro.FrontEnd.AppLogic.StcService
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public partial class AllegatiType
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
			if (this.metaDati == null)
			{
				return string.Empty;
			}

			var metadato = this.metaDati.Where(x => x.codice == Constants.Md5Key).FirstOrDefault();

			return metadato == null ? String.Empty : metadato.valore;
		}
	}
}
