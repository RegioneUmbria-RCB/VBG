using PersonalLib2.Extensions.FastMap;
using System;
using System.Collections.Generic;

namespace PersonalLib2.Data
{
    public static class FastMapExtension
    {
        public static IEnumerable<T> FastMap<T>(this IDatabase database, FormattableString sql) where T : new()
        {
            var typeCache = TypesCache.GetCachedType(typeof(T));

            return database.ExecuteReader(sql, (dr) =>
            {
                return (T)typeCache.MapDataReader(dr);
            });
        }
    }
}
