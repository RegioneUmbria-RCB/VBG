using System;
using System.Text.Json;
using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione.Enum
{
    public enum Verso
    {
        partenza,
        arrivo,
    }

    public static partial class EnumConverter
    {
        public static string ConvertToString(this Verso operatore)
        {
            switch (operatore)
            {
                case Verso.partenza:
                    return "P";
                case Verso.arrivo:
                    return "A";
                default:
                    throw new ArgumentOutOfRangeException(nameof(operatore), operatore, null);
            }
        }

        public static Verso ConvertToVersoEnum(this string valore)
        {
            switch (valore)
            {
                case "P":
                    return Verso.partenza;
                case "A":
                    return Verso.arrivo;
                default:
                    throw new ArgumentOutOfRangeException(nameof(valore), valore, null);
            }
        }
    }

    public class VersoConverter : JsonConverter<Verso>
    {
        public override Verso Read(ref Utf8JsonReader reader, Type typeToConvert, JsonSerializerOptions options)
        {
            string stringValue = reader.GetString();
            return EnumConverter.ConvertToVersoEnum(stringValue);
        }

        public override void Write(Utf8JsonWriter writer, Verso value, JsonSerializerOptions options)
        {
            string stringValue = EnumConverter.ConvertToString(value);
            writer.WriteStringValue(stringValue);
        }
    }
}
