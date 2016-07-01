using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.Sigepro.FrontEnd.AppLogic.StcService;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneLocalizzazioni
{
	public class RiferimentoCatastale
	{
		public enum TipoCatastoEnum
		{
			Terreni,
			Fabbricati
		}
		public int Id { get; private set; }
		public int IdLocalizzazione { get; private set; }
		public TipoCatastoEnum TipoCatasto { get; private set; }
		public string Sezione { get; private set; }
		public string Foglio { get; private set; }
		public string Particella { get; private set; }
		public string Sub { get; private set; }
		public string CodiceTipoCatasto { get; private set; }

		protected RiferimentoCatastale()
		{

		}

		public static RiferimentoCatastale FromDatiCatastaliRow(PresentazioneIstanzaDbV2.DATICATASTALIRow row)
		{
			return new RiferimentoCatastale
			{
				Id = (int)row.Id,
				CodiceTipoCatasto = row.CodiceTipoCatasto,
				TipoCatasto = row.CodiceTipoCatasto == "T" ? TipoCatastoEnum.Terreni : TipoCatastoEnum.Fabbricati,
				Foglio = row.Foglio,
				Particella = row.Particella,
				Sub = row.Sub,
				IdLocalizzazione = row.IdLocalizzazione
			};
		}

		public RiferimentoCatastaleType ToRiferimentoCatastaleType()
		{
			return new RiferimentoCatastaleType
			{
				tipoCatasto = this.TipoCatasto == TipoCatastoEnum.Terreni ? RiferimentoCatastaleTypeTipoCatasto.Terreni : RiferimentoCatastaleTypeTipoCatasto.EdilizioUrbano,
				foglio = this.Foglio,
				particella = this.Particella,
				sub = this.Sub
			};
		}

		public override string ToString()
		{
			var sb = new StringBuilder();

			sb.AppendFormat("{0}", this.TipoCatasto == TipoCatastoEnum.Terreni ? "Terreni" : "Fabbricati");

			if (!String.IsNullOrEmpty(this.Foglio))
				sb.AppendFormat(" - f: {0}", this.Foglio );
			
			if (!String.IsNullOrEmpty(this.Particella))
				sb.AppendFormat(", p: {0}", this.Particella);

			if (!String.IsNullOrEmpty(this.Sub))
				sb.AppendFormat(", s: {0}", Sub);

			return sb.ToString();

		}
	}
}
