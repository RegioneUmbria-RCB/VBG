namespace AreaRiservataCore.Shared.Utils
{
    public static class UnmatchedAttributeUtils
    {
        public static string ExtractCssClass(Dictionary<string, object> unmatchedAttributes, string defaultCssClass)
        {
            if (unmatchedAttributes != null && unmatchedAttributes.TryGetValue("class", out var cssClassObj) && cssClassObj is string cssClass)
            {
                unmatchedAttributes.Remove("class");

                return $"{defaultCssClass} {cssClass}";
            }
            return defaultCssClass;
        }
    }
}
