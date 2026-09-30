using System;
using System.Text.Json;
using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione.Enum
{
    public enum TipoCorrispondente
    {
        TUTTI,
        MITTENTE,
        DESTINATARIO,
        UFFICIO
    }

    public static partial class EnumConverter
    {
        public static string ConvertToString(this TipoCorrispondente codice)
        {
            switch (codice)
            {
                case TipoCorrispondente.TUTTI:
                    return "TUTTI";
                case TipoCorrispondente.MITTENTE:
                    return "MITTENTE";
                case TipoCorrispondente.DESTINATARIO:
                    return "DESTINATARIO";
                case TipoCorrispondente.UFFICIO:
                    return "UFFICIO";
                default:
                    throw new ArgumentOutOfRangeException(nameof(codice), codice, null);
            }
        }

        public static TipoCorrispondente ConvertToTipoCorrispondenteEnum(this string valore)
        {
            switch (valore)
            {
                case "TUTTI":
                    return TipoCorrispondente.TUTTI;
                case "MITTENTE":
                    return TipoCorrispondente.MITTENTE;
                case "DESTINATARIO":
                    return TipoCorrispondente.DESTINATARIO;
                case "UFFICIO":
                    return TipoCorrispondente.UFFICIO;
                default:
                    throw new ArgumentOutOfRangeException(nameof(valore), valore, null);
            }
        }
    }

    public class TipoCorrispondenteConverter : JsonConverter<TipoCorrispondente>
    {
        public override TipoCorrispondente Read(ref Utf8JsonReader reader, Type typeToConvert, JsonSerializerOptions options)
        {
            string stringValue = reader.GetString();
            return EnumConverter.ConvertToTipoCorrispondenteEnum(stringValue);
        }

        public override void Write(Utf8JsonWriter writer, TipoCorrispondente value, JsonSerializerOptions options)
        {
            string stringValue = EnumConverter.ConvertToString(value);
            writer.WriteStringValue(stringValue);
        }
    }
}
