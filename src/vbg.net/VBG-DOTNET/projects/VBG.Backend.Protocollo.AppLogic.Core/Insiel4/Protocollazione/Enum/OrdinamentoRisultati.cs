using System;
using System.Text.Json;
using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione.Enum
{
    public enum OrdinamentoRisultati
    {
        DATA_NUM,
        CORRISP_DATA,
        DATA_CORRISP
    }

    public static partial class EnumConverter
    {
        public static string ConvertToString(this OrdinamentoRisultati codice)
        {
            switch (codice)
            {
                case OrdinamentoRisultati.DATA_NUM:
                    return "DATA_NUM";
                case OrdinamentoRisultati.CORRISP_DATA:
                    return "CORRISP_DATA";
                case OrdinamentoRisultati.DATA_CORRISP:
                    return "DATA_CORRISP";
                default:
                    throw new ArgumentOutOfRangeException(nameof(codice), codice, null);
            }
        }

        public static OrdinamentoRisultati ConvertToOrdinamentoRisultatiEnum(this string valore)
        {
            switch (valore)
            {
                case "DATA_NUM":
                    return OrdinamentoRisultati.DATA_NUM;
                case "CORRISP_DATA":
                    return OrdinamentoRisultati.CORRISP_DATA;
                case "DATA_CORRISP":
                    return OrdinamentoRisultati.DATA_CORRISP;
                default:
                    throw new ArgumentOutOfRangeException(nameof(valore), valore, null);
            }
        }
    }

    public class OrdinamentoRisultatiConverter : JsonConverter<OrdinamentoRisultati>
    {
        public override OrdinamentoRisultati Read(ref Utf8JsonReader reader, Type typeToConvert, JsonSerializerOptions options)
        {
            string stringValue = reader.GetString();
            return EnumConverter.ConvertToOrdinamentoRisultatiEnum(stringValue);
        }

        public override void Write(Utf8JsonWriter writer, OrdinamentoRisultati value, JsonSerializerOptions options)
        {
            string stringValue = EnumConverter.ConvertToString(value);
            writer.WriteStringValue(stringValue);
        }
    }
}
