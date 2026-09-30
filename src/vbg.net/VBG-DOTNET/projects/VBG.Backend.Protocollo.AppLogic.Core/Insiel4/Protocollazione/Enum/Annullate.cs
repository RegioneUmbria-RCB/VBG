using System;
using System.Text.Json;
using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione.Enum
{
    public enum Annullate
    {
        SOLO_ANNULLATE,
        ESCLUDI_ANNULLATE
    }

    public static partial class EnumConverter
    {
        public static string ConvertToString(this Annullate codice)
        {
            switch (codice)
            {
                case Annullate.SOLO_ANNULLATE:
                    return "SOLO_ANNULLATE";
                case Annullate.ESCLUDI_ANNULLATE:
                    return "ESCLUDI_ANNULLATE";
                default:
                    throw new ArgumentOutOfRangeException(nameof(codice), codice, null);
            }
        }

        public static Annullate ConvertToAnnullateEnum(this string valore)
        {
            switch (valore)
            {
                case "SOLO_ANNULLATE":
                    return Annullate.SOLO_ANNULLATE;
                case "ESCLUDI_ANNULLATE":
                    return Annullate.ESCLUDI_ANNULLATE;
                default:
                    throw new ArgumentOutOfRangeException(nameof(valore), valore, null);
            }
        }
    }

    public class AnnullateConverter : JsonConverter<Annullate>
    {
        public override Annullate Read(ref Utf8JsonReader reader, Type typeToConvert, JsonSerializerOptions options)
        {
            string stringValue = reader.GetString();
            return EnumConverter.ConvertToAnnullateEnum(stringValue);
        }

        public override void Write(Utf8JsonWriter writer, Annullate value, JsonSerializerOptions options)
        {
            string stringValue = EnumConverter.ConvertToString(value);
            writer.WriteStringValue(stringValue);
        }
    }
}
