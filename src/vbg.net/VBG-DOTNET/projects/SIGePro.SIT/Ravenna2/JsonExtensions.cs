using System;
using System.Collections.Generic;
using System.Linq;
using System.Text.Json;

namespace Init.SIGePro.Sit.Ravenna2
{
    public static class JsonExtensions
    {
        internal static List<string> PickElements(string jsonString, string fieldName, string fieldTypeName)
        {
            JsonDocument doc = JsonDocument.Parse(jsonString);
            List<string> elements = new List<string>();

            bool hasNull = doc.RootElement.GetProperty("features")
            .EnumerateArray()
            .Select(f => f.GetProperty("attributes"))
            .Any(attr => attr.TryGetProperty(fieldName, out JsonElement parte) && parte.ValueKind == JsonValueKind.Null);

            if (hasNull)
            {
                elements.Add("");
            }

            foreach (var feature in doc.RootElement.GetProperty("features").EnumerateArray())
            {
                string element = "";
                if (fieldTypeName.ToLower().Trim().Equals("number"))
                {
                    element = feature.GetProperty("attributes").GetProperty(fieldName.ToUpper()).GetInt32().ToString();
                }
                else if (fieldTypeName.ToLower().Trim().Equals("string"))
                {
                    element = feature.GetProperty("attributes").GetProperty(fieldName.ToUpper()).GetString();
                }

                if (!String.IsNullOrEmpty(element))
                {
                    elements.Add(element);
                }
            }

            return elements;
        }
    }
}