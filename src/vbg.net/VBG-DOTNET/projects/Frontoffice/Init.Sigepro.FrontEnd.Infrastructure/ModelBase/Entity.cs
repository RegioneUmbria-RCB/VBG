// -----------------------------------------------------------------------
// <copyright file="Entity.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Infrastructure.ModelBase
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class Entity
	{
		public virtual int Id { get; private set; }

		protected Entity(int id)
		{
			this.Id = id;
		}
	}
}
