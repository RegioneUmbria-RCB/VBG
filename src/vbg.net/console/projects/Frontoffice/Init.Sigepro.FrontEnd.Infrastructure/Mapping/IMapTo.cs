// -----------------------------------------------------------------------
// <copyright file="IMapTo.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Infrastructure.Mapping
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public interface IMapTo<TSource, TTarget>
	{
		TTarget Map(TSource source);
	}
}
