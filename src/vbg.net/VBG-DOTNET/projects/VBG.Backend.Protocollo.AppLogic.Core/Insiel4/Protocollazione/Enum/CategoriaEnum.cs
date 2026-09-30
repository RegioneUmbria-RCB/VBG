using System;
using System.Text.Json;
using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione.Enum
{
    public enum Categoria
    {
        PROTOCOLLO,
        FASCICOLI
    }

    public static partial class EnumConverter
    {
        public static string ConvertToString(this Categoria codice)
        {
            switch (codice)
            {
                case Categoria.PROTOCOLLO:
                    return "POST";
                case Categoria.FASCICOLI:
                    return "PRAT";
                default:
                    throw new ArgumentOutOfRangeException(nameof(codice), codice, null);
            }
        }

        public static Categoria ConvertToCategoriaEnum(this string valore)
        {
            switch (valore)
            {
                case "POST":
                    return Categoria.PROTOCOLLO;
                case "PRAT":
                    return Categoria.FASCICOLI;
                default:
                    throw new ArgumentOutOfRangeException(nameof(valore), valore, null);
            }
        }
    }

    public class CategoriaConverter : JsonConverter<Categoria>
    {
        public override Categoria Read(ref Utf8JsonReader reader, Type typeToConvert, JsonSerializerOptions options)
        {
            string stringValue = reader.GetString();
            return EnumConverter.ConvertToCategoriaEnum(stringValue);
        }

        public override void Write(Utf8JsonWriter writer, Categoria value, JsonSerializerOptions options)
        {
            string stringValue = EnumConverter.ConvertToString(value);
            writer.WriteStringValue(stringValue);
        }
    }
}
