// -----------------------------------------------------------------------
// <copyright file="IndirizzoComuneTypeToIndirizzoDenunciaTares.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Bari.DenunceTARES.DTOs.Converters
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.Infrastructure.Mapping;
	using Init.Sigepro.FrontEnd.Bari.TARES.ServicesProxies;
	using Init.Sigepro.FrontEnd.Bari.Core;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class IndirizzoComuneTypeToIndirizzoDenunciaTares: IMapTo<indirizzoComuneType, IndirizzoDenunciaTares>
	{
		IResolveViaDaCodviario _resolver;

		public IndirizzoComuneTypeToIndirizzoDenunciaTares(IResolveViaDaCodviario resolver)
		{
			this._resolver = resolver;
		}

		public IndirizzoDenunciaTares Map(indirizzoComuneType i)
		{
			if (i == null) return null;

			var codiceVia = i.viaCodificata;
			var via = this._resolver.GetNomeByCodViario(codiceVia);

			var codiceComune = "072006";
			var comune = "BARI";

			var civico = i.civicoSpecified ? i.civico.ToString() : String.Empty;
			var esponente = i.esponente;
			var frazione = i.frazione;
			var scala = i.scala;
			var piano = i.piano;
			var interno = i.interno;
			var cap = i.cap;
			var km = i.kmSpecified ? i.km.ToString() : String.Empty;
			var suffisso = i.suffisso;

			return new IndirizzoDenunciaTares(codiceComune, comune, cap, codiceVia, via, civico, esponente, frazione, scala, piano, interno, suffisso, km);
		}
	}
}
