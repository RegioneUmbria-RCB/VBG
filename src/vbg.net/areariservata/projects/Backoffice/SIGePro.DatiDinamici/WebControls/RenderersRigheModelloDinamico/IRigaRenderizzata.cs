using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web.UI.HtmlControls;

namespace Init.SIGePro.DatiDinamici.WebControls.RenderersRigheModelloDinamico
{
	internal interface IRigaRenderizzata
	{
		HtmlTableRow AsHtmlRow();
		int NumeroCelle { get; }
		int NumeroControlli { get; }
	}
}
