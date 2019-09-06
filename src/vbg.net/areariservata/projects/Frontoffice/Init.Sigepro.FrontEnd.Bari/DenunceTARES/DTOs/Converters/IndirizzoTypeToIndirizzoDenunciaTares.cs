// -----------------------------------------------------------------------
// <copyright file="IndirizzoTypeToIndirizzoDenunciaTares.cs" company="">
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
	public class IndirizzoTypeToIndirizzoDenunciaTares : IMapTo<indirizzoType, IndirizzoDenunciaTares>
	{
		IResolveViaDaCodviario _resolver;
		IResolveComuneDaCodiceIstat _comuneDaCodiceIstat;

		public IndirizzoTypeToIndirizzoDenunciaTares(IResolveViaDaCodviario resolver, IResolveComuneDaCodiceIstat comuneDaCodiceIstat)
		{
			this._resolver = resolver;
			this._comuneDaCodiceIstat = comuneDaCodiceIstat;
		}

		public IndirizzoDenunciaTares Map(indirizzoType i)
		{
			if (i == null) return null;

			var codiceVia = String.Empty;
			var via = i.Item;

			if (i.ItemElementName == ItemChoiceType1.viaCodificata)
			{
				codiceVia = via;
				via = this._resolver.GetNomeByCodViario(codiceVia);
			}

			var codiceComune = i.comune;
			var comune = this._comuneDaCodiceIstat.GetComuneDaCodiceIstat(codiceComune);

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
