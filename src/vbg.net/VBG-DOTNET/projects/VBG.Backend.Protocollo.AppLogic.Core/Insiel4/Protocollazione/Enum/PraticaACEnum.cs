using System;
using System.Text.Json;
using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione.Enum
{
    public enum PraticaAC
    {
        agliAtti,
        inEvidenza,
        altro,
    }

    public static partial class EnumConverter
    {
        public static string ConvertToString(this PraticaAC prat)
        {
            switch (prat)
            {
                case PraticaAC.agliAtti:
                    return "agliAtti";
                case PraticaAC.inEvidenza:
                    return "inEvidenza";
                case PraticaAC.altro:
                    return "altro";
                default:
                    throw new ArgumentOutOfRangeException(nameof(prat), prat, null);
            }
        }

        public static PraticaAC ConvertToPraticaACEnum(this string valore)
        {
            switch (valore)
            {
                case "agliAtti":
                    return PraticaAC.agliAtti;
                case "DIP":
                    return PraticaAC.inEvidenza;
                case "altro":
                    return PraticaAC.altro;
                default:
                    throw new ArgumentOutOfRangeException(nameof(valore), valore, null);
            }
        }
    }

    public class PraticaACConverter : JsonConverter<PraticaAC>
    {
        public override PraticaAC Read(ref Utf8JsonReader reader, Type typeToConvert, JsonSerializerOptions options)
        {
            string stringValue = reader.GetString();
            return EnumConverter.ConvertToPraticaACEnum(stringValue);
        }

        public override void Write(Utf8JsonWriter writer, PraticaAC value, JsonSerializerOptions options)
        {
            string stringValue = EnumConverter.ConvertToString(value);
            writer.WriteStringValue(stringValue);
        }
    }
}
