using System;
using System.Text.Json;
using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione.Enum
{
    public enum Sesso
    {
        maschio,
        femmina
    }

    public static partial class EnumConverter
    {
        public static string ConvertToString(this Sesso? codice)
        {
            switch (codice)
            {
                case Sesso.maschio:
                    return "M";
                case Sesso.femmina:
                    return "F";
                default:
                    return null;
            }
        }

        public static Sesso? ConvertToSessoEnum(this string valore)
        {
            if (string.IsNullOrWhiteSpace(valore))
                return null;

            switch (valore.Trim().ToUpperInvariant())
            {
                case "M":
                    return Sesso.maschio;
                case "F":
                    return Sesso.femmina;
                default:
                    return null;
            }
        }
    }

    public class SessoConverter : JsonConverter<Sesso?>
    {
        public override Sesso? Read(ref Utf8JsonReader reader, Type typeToConvert, JsonSerializerOptions options)
        {
            string stringValue = reader.GetString();
            return EnumConverter.ConvertToSessoEnum(stringValue);
        }

        public override void Write(Utf8JsonWriter writer, Sesso? value, JsonSerializerOptions options)
        {
            string stringValue = EnumConverter.ConvertToString(value);
            writer.WriteStringValue(stringValue);
        }
    }
}
