using System;
using System.Text.Json;
using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione.Enum
{
    public enum TipoEmailAnagrafica
    {
        pec,
        peo,
    }

    public static partial class EnumConverter
    {
        public static string ConvertToString(this TipoEmailAnagrafica operatore)
        {
            switch (operatore)
            {
                case TipoEmailAnagrafica.pec:
                    return "pec";
                case TipoEmailAnagrafica.peo:
                    return "peo";
                default:
                    throw new ArgumentOutOfRangeException(nameof(operatore), operatore, null);
            }
        }

        public static TipoEmailAnagrafica ConvertToTipoEmailAnagraficaEnum(this string valore)
        {
            switch (valore)
            {
                case "pec":
                    return TipoEmailAnagrafica.pec;
                case "peo":
                    return TipoEmailAnagrafica.peo;
                default:
                    throw new ArgumentOutOfRangeException(nameof(valore), valore, null);
            }
        }
    }

    public class TipoEmailAnagraficaConverter : JsonConverter<TipoEmailAnagrafica>
    {
        public override TipoEmailAnagrafica Read(ref Utf8JsonReader reader, Type typeToConvert, JsonSerializerOptions options)
        {
            string stringValue = reader.GetString();
            return EnumConverter.ConvertToTipoEmailAnagraficaEnum(stringValue);
        }

        public override void Write(Utf8JsonWriter writer, TipoEmailAnagrafica value, JsonSerializerOptions options)
        {
            string stringValue = EnumConverter.ConvertToString(value);
            writer.WriteStringValue(stringValue);
        }
    }
}
