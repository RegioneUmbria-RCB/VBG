using System;
using System.Collections.Generic;
using System.Linq;
using System.Text.Json;

namespace VBG.Backend.SIT.AppLogic.Ravenna2
{
    public static class JsonExtensions
    {
        internal static List<string> PickElements(string jsonString, string fieldName, string fieldTypeName)
        {
            var doc = JsonDocument.Parse(jsonString);
            var elements = new List<string>();

            var hasNull = doc.RootElement.GetProperty("features")
            .EnumerateArray()
            .Select(f => f.GetProperty("attributes"))
            .Any(attr => attr.TryGetProperty(fieldName, out var parte) && parte.ValueKind == JsonValueKind.Null);

            if (hasNull)
            {
                elements.Add("");
            }

            foreach (var feature in doc.RootElement.GetProperty("features").EnumerateArray())
            {
                var element = "";
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