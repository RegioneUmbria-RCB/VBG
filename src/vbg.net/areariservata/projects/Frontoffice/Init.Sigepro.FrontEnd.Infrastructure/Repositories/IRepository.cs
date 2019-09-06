// -----------------------------------------------------------------------
// <copyright file="IRepository.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Infrastructure.Repositories
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.Infrastructure.ModelBase;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public interface IRepository<T> where T:AggregateRoot
	{
		T GetById(int id);
		void Save(T item);
	}


	/*
	public class Repository<T> : IRepository<T>
	{
		#region IRepository<T> Members

		public T GetById(int id)
		{
			throw new NotImplementedException();
		}

		public void Save(T aggregateRoot)
		{
			throw new NotImplementedException();
		}

		#endregion
	}
	*/

}
