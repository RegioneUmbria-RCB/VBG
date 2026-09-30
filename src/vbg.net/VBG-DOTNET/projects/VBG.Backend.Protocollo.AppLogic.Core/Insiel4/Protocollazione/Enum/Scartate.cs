using System;
using System.Text.Json;
using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione.Enum
{
    public enum Scartate
    {
        SOLO_SCARTATE,
        ESCLUDI_SCARTATE
    }

    public static partial class EnumConverter
    {
        public static string ConvertToString(this Scartate codice)
        {
            switch (codice)
            {
                case Scartate.SOLO_SCARTATE:
                    return "SOLO_SCARTATE";
                case Scartate.ESCLUDI_SCARTATE:
                    return "ESCLUDI_SCARTATE";
                default:
                    throw new ArgumentOutOfRangeException(nameof(codice), codice, null);
            }
        }

        public static Scartate ConvertToScartateEnum(this string valore)
        {
            switch (valore)
            {
                case "SOLO_SCARTATE":
                    return Scartate.SOLO_SCARTATE;
                case "ESCLUDI_SCARTATE":
                    return Scartate.ESCLUDI_SCARTATE;
                default:
                    throw new ArgumentOutOfRangeException(nameof(valore), valore, null);
            }
        }
    }

    public class ScartateConverter : JsonConverter<Scartate>
    {
        public override Scartate Read(ref Utf8JsonReader reader, Type typeToConvert, JsonSerializerOptions options)
        {
            string stringValue = reader.GetString();
            return EnumConverter.ConvertToScartateEnum(stringValue);
        }

        public override void Write(Utf8JsonWriter writer, Scartate value, JsonSerializerOptions options)
        {
            string stringValue = EnumConverter.ConvertToString(value);
            writer.WriteStringValue(stringValue);
        }
    }
}
