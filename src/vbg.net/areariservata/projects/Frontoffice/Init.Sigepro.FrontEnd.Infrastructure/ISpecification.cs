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

    public interface ISpecificationWithErrorMessage<T> : ISpecification<T>
    {
        string ErrorMessage { get; }
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

    internal class OrSpecificationWithErrorMessage<TEntity> : ISpecificationWithErrorMessage<TEntity>
    {
        private ISpecificationWithErrorMessage<TEntity> _specificationOne;
        private ISpecificationWithErrorMessage<TEntity> _specificationTwo;

        string _errorMessage = "";

        public string ErrorMessage => this._errorMessage;

        internal OrSpecificationWithErrorMessage(ISpecificationWithErrorMessage<TEntity> specificationOne,
          ISpecificationWithErrorMessage<TEntity> specificationTwo)
        {
            this._specificationOne = specificationOne;
            this._specificationTwo = specificationTwo;
        }

        public bool IsSatisfiedBy(TEntity entity)
        {
            var firstSatisfied = this._specificationOne.IsSatisfiedBy(entity);
            var secondSatisfied = this._specificationTwo.IsSatisfiedBy(entity);
            var result = firstSatisfied || secondSatisfied;

            if (result)
            {
                return true;
            }

            if (!firstSatisfied)
            {
                this._errorMessage = this._specificationOne.ErrorMessage;
            }
            else
            {
                this._errorMessage = this._specificationTwo.ErrorMessage;
            }

            return false;
        }
    }

    internal class AndSpecificationWithErrorMessage<TEntity> : ISpecificationWithErrorMessage<TEntity>
    {
        private ISpecificationWithErrorMessage<TEntity> _specificationOne;
        private ISpecificationWithErrorMessage<TEntity> _specificationTwo;

        string _errorMessage = "";

        public string ErrorMessage => this._errorMessage;

        internal AndSpecificationWithErrorMessage(ISpecificationWithErrorMessage<TEntity> specificationOne,
          ISpecificationWithErrorMessage<TEntity> specificationTwo)
        {
            this._specificationOne = specificationOne;
            this._specificationTwo = specificationTwo;
        }

        public bool IsSatisfiedBy(TEntity entity)
        {
            var firstSatisfied = this._specificationOne.IsSatisfiedBy(entity);
            var secondSatisfied = this._specificationTwo.IsSatisfiedBy(entity);
            var result = firstSatisfied && secondSatisfied;

            if (result)
            {
                return true;
            }

            if (!firstSatisfied)
            {
                this._errorMessage = this._specificationOne.ErrorMessage;
            }
            else
            {
                this._errorMessage = this._specificationTwo.ErrorMessage;
            }

            return false;
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

        public static ISpecificationWithErrorMessage<TEntity> And<TEntity>(this ISpecificationWithErrorMessage<TEntity> specificationOne, ISpecificationWithErrorMessage<TEntity> specificationTwo)
        {
            return new AndSpecificationWithErrorMessage<TEntity>(specificationOne, specificationTwo);
        }

        public static ISpecificationWithErrorMessage<TEntity> Or<TEntity>(this ISpecificationWithErrorMessage<TEntity> specificationOne, ISpecificationWithErrorMessage<TEntity> specificationTwo)
        {
            return new OrSpecificationWithErrorMessage<TEntity>(specificationOne, specificationTwo);
        }


    }

}
