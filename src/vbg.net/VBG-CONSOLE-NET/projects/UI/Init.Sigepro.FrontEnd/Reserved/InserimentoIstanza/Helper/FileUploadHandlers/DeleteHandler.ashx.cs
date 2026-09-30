using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Helper.FileUploadHandlers
{
	/// <summary>
	/// Summary description for DeleteHandler
	/// </summary>
	public class DeleteHandler : DatiDinamiciFileUploadHandlerBase
	{
		public override void DoProcessRequestInternal()
		{
			SerializeResponse(new { result = "OK" });
		}
	}
}