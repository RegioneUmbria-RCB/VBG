using System;
using System.Text.Json;
using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione.Enum
{
    public enum CodiceTipoAnagrafica
    {
        esterno,
        dipendente,
        ufficio,
        ufficioDipendente
    }

    public static partial class EnumConverter
    {
        public static string ConvertToString(this CodiceTipoAnagrafica codice)
        {
            switch (codice)
            {
                case CodiceTipoAnagrafica.esterno:
                    return "EST";
                case CodiceTipoAnagrafica.dipendente:
                    return "DIP";
                case CodiceTipoAnagrafica.ufficio:
                    return "UFF";
                case CodiceTipoAnagrafica.ufficioDipendente:
                    return "UFFDIP";
                default:
                    throw new ArgumentOutOfRangeException(nameof(codice), codice, null);
            }
        }

        public static CodiceTipoAnagrafica ConvertToCodiceTipoAnagraficaEnum(this string valore)
        {
            switch (valore)
            {
                case "EST":
                    return CodiceTipoAnagrafica.esterno;
                case "DIP":
                    return CodiceTipoAnagrafica.dipendente;
                case "UFF":
                    return CodiceTipoAnagrafica.ufficio;
                case "UFFDIP":
                    return CodiceTipoAnagrafica.ufficioDipendente;
                default:
                    throw new ArgumentOutOfRangeException(nameof(valore), valore, null);
            }
        }
    }

    public class CodiceTipoAnagraficaConverter : JsonConverter<CodiceTipoAnagrafica>
    {
        public override CodiceTipoAnagrafica Read(ref Utf8JsonReader reader, Type typeToConvert, JsonSerializerOptions options)
        {
            string stringValue = reader.GetString();
            return EnumConverter.ConvertToCodiceTipoAnagraficaEnum(stringValue);
        }

        public override void Write(Utf8JsonWriter writer, CodiceTipoAnagrafica value, JsonSerializerOptions options)
        {
            string stringValue = EnumConverter.ConvertToString(value);
            writer.WriteStringValue(stringValue);
        }
    }
}
