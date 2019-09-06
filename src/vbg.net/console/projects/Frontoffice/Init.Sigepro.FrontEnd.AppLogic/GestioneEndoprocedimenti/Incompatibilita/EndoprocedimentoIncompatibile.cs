namespace Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti.Incompatibilita
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;

	public class EndoprocedimentoIncompatibile
	{
		public string Endoprocedimento { get; set; }

		public IEnumerable<string> EndoprocedimentiIncompatibili { get; set; }

		public EndoprocedimentoIncompatibile(string endoprocedimento, IEnumerable<string> endoIncompatibili)
		{
			this.Endoprocedimento = endoprocedimento;
			this.EndoprocedimentiIncompatibili = endoIncompatibili;
		}

		public override string ToString()
		{
			var sb = new StringBuilder();
			sb.AppendFormat("L'endoprocedimento \"{0}\" non è compatibile ", this.Endoprocedimento);

			if (this.EndoprocedimentiIncompatibili.Count() == 1)
			{
				sb.AppendFormat("con l'endoprocedimento \"{0}\"", this.EndoprocedimentiIncompatibili.First());
			}
			else
			{
				var nomiEndo = this.EndoprocedimentiIncompatibili.Select(x => string.Format("\"{0}\"", x));

				sb.AppendFormat("con i seguenti endoprocedeimenti: {0}", string.Join(", ", nomiEndo.ToArray()));
			}

			return sb.ToString();
		}
	}
}
