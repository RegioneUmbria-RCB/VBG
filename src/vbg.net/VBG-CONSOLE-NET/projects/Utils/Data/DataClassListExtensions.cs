using PersonalLib2.Sql;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace PersonalLib2.Data
{
    public static class DataClassListExtensions
    {
        public static T[] ToArray<T>(this List<DataClass> lst)
        {
            return lst.Cast<T>().ToArray();
        }

        public static List<T> ToList<T>(this List<DataClass> lst)
        {
            return lst.Cast<T>().ToList();
        }

        public static List<T> ToList<T>(this List<T> lst)
        {
            return lst;
        }
    }
}
