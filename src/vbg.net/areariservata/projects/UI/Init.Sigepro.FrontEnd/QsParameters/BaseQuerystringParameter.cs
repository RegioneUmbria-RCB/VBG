using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using log4net;
using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Linq;
using System.Text.RegularExpressions;
using System.Web;

namespace Init.Sigepro.FrontEnd.QsParameters
{
    public static class StringExtensions
    {
        public static bool MinLength(this string value, int min)
        {
            if (String.IsNullOrEmpty(value))
            {
                return false;
            }

            return value.Length >= min;
        }

        public static bool MaxLength(this string value, int max)
        {
            if (String.IsNullOrEmpty(value))
            {
                return true;
            }

            return value.Length <= max;
        }

        public static bool Between(this string value, int min, int max)
        {
            return value.MinLength(min) && value.MaxLength(max);
        }

        public static bool Matches(this string value, string regex)
        {
            return Regex.IsMatch(value, regex);
        }
    }

    public class Optional2<T, T2> : IQuerystringParameter where T : BaseQuerystringParameter<T2>
                                                          where T2: struct
    {

        public readonly T Parameter;

        public T2? Value
        {
            get
            {
                if (String.IsNullOrEmpty(this.ParameterStringValue))
                {
                    return (T2?)null;
                }

                return this.Parameter.Value;
            }
        }

        public Optional2(string value)
        {
            this.Parameter = (T)Activator.CreateInstance(typeof(T), value);
        }

        public Optional2(NameValueCollection nameValueCollection)
        {
            this.Parameter = (T)Activator.CreateInstance(typeof(T), nameValueCollection);
        }

        public string ParameterName
        {
            get { return this.Parameter.ParameterName; }
        }

        public string ParameterStringValue
        {
            get { return this.Parameter.ParameterStringValue; }
        }

        public bool HasValue
        {
            get { return this.Value.HasValue; }
        }
    }

    /*
    public class Optional<T1> : IQuerystringParameter where T1 : struct
    {
        public readonly BaseQuerystringParameter<T1> Parameter;

        public T1? Value
        {
            get
            {
                if (String.IsNullOrEmpty(this.ParameterStringValue))
                {
                    return (T1?)null;
                }

                return this.Parameter.Value;
            }
        }

        public Optional(BaseQuerystringParameter<T1> parameter)
        {
            this.Parameter = parameter;
        }

        public string ParameterName
        {
            get { return this.Parameter.ParameterName; }
        }

        public string ParameterStringValue
        {
            get { return this.Parameter.ParameterStringValue; }
        }

        public bool HasValue
        {
            get { return this.Value.HasValue; }
        }
    }
    */

    public abstract class BaseQuerystringParameter<T> : IQuerystringParameter
    {
        string _innerValue;
        ILog _log = LogManager.GetLogger(typeof(BaseQuerystringParameter<T>));

        public T Value
        {
            get
            {
                if (!Validate(this._innerValue))
                {
                    this._log.Error($"Il parametro di querystring {this.ParameterName} ({this._innerValue}) contiene un valore non valido");


                    throw new ArgumentException($"Il parametro di querystring {this.ParameterName} contiene un valore non valido");
                }
                return AdaptValue(this._innerValue);
            }
        }

        protected BaseQuerystringParameter(string value)
        {
            this._innerValue = value;
        }

        protected BaseQuerystringParameter(NameValueCollection qs)
        {
            this._innerValue = qs[this.ParameterName];
        }

        private T AdaptValue(string value)
        {
            return (T)Convert.ChangeType(value, typeof(T));
        }

        protected virtual bool Validate(string value)
        {
            return true;
        }

        public abstract string ParameterName
        {
            get;
        }

        public string ParameterStringValue
        {
            get { return this._innerValue; }
        }

        public bool HasValue
        {
            get { return !String.IsNullOrEmpty(this._innerValue); }
        }
    }
}