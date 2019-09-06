namespace Init.Sigepro.FrontEnd.AppLogic.StcService
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;

	public partial class DocumentiType
	{
		public string NomeAllegato
		{
			get{
				if (this.allegati == null)
					return String.Empty;

				return this.allegati.allegato;
			}
		}

		public string Md5
		{
			get
			{
				if (this.allegati == null)
					return String.Empty;

				return this.allegati.Md5;
			}
		}

		public bool HasMd5
		{
			get
			{
				if (this.allegati == null)
					return false;

				return this.allegati.HasMd5;
			}
		}

		public bool HasAnnotazioni
		{
			get
			{
				return !String.IsNullOrEmpty(this.annotazioni);
			}
		}
	}
}
