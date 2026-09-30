using System.Text.Json;

namespace Init.Utils.Extensions
{
    public static class MapUsingJsonExtension
    {
        public static T MapUsingJson<T>(this object source) where T : class
        {
            if (source is null)
            {
                return null;
            }

            var json = JsonSerializer.Serialize(source);
            return JsonSerializer.Deserialize<T>(json);
        }
    }
}
