// -----------------------------------------------------------------------
// <copyright file="IUnitOfWork.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Infrastructure.Repositories
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public interface IUnitOfWork<T>
	{
		void Begin();
		void Commit();
	}
}
