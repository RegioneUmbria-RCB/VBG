// -----------------------------------------------------------------------
// <copyright file="ISpecification.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Infrastructure
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public interface ISpecification<T>
	{
		bool IsSatisfiedBy(T item);
	}

	public class CompositeAndSpecification<T> : ISpecification<T>
	{
		IEnumerable<ISpecification<T>> _specifications;

		public CompositeAndSpecification(IEnumerable<ISpecification<T>> specifications)
		{
			this._specifications = specifications;
		}

		#region ISpecification<T> Members

		public bool IsSatisfiedBy(T item)
		{
			foreach (var specification in this._specifications)
			{
				if (!specification.IsSatisfiedBy(item))
					return false;
			}

			return true;
		}

		#endregion
	}

	internal class AndSpecification<TEntity> : ISpecification<TEntity>
	{
		private ISpecification<TEntity> _specificationOne;
		private ISpecification<TEntity> _specificationTwo;

		internal AndSpecification(ISpecification<TEntity> specificationOne,
		  ISpecification<TEntity> specificationTwo)
		{
			this._specificationOne = specificationOne;
			this._specificationTwo = specificationTwo;
		}

		public bool IsSatisfiedBy(TEntity entity)
		{
			return (this._specificationOne.IsSatisfiedBy(entity) &&
			  this._specificationTwo.IsSatisfiedBy(entity));
		}
	}

	internal class OrSpecification<TEntity> : ISpecification<TEntity>
	{
		private ISpecification<TEntity> _specificationOne;
		private ISpecification<TEntity> _specificationTwo;

		internal OrSpecification(ISpecification<TEntity> specificationOne,
		  ISpecification<TEntity> specificationTwo)
		{
			this._specificationOne = specificationOne;
			this._specificationTwo = specificationTwo;
		}

		public bool IsSatisfiedBy(TEntity entity)
		{
			return (this._specificationOne.IsSatisfiedBy(entity) ||
			  this._specificationTwo.IsSatisfiedBy(entity));
		}
	}  

	public static class SpecificationExtensionMethods
	{
		public static ISpecification<TEntity> And<TEntity>(
		  this ISpecification<TEntity> specificationOne,
		  ISpecification<TEntity> specificationTwo)
		{
			return new AndSpecification<TEntity>(
			  specificationOne, specificationTwo);
		}

		public static ISpecification<TEntity> Or<TEntity>(
		  this ISpecification<TEntity> specificationOne,
		  ISpecification<TEntity> specificationTwo)
		{
			return new OrSpecification<TEntity>(
			  specificationOne, specificationTwo);
		}

		//public static ISpecification<TEntity> Not<TEntity>(
		//  this ISpecification<TEntity> specification)
		//{
		//    return new NotSpecification<TEntity>(specification);
		//}
	}  

}
