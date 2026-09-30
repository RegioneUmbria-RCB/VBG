using System;
using System.Text.Json;
using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione.Enum
{
    public enum DestinatarioInvioTelemStatoInvio
    {
        NON_INVIATO,
        INVIATO,
    }

    public static partial class EnumConverter
    {
        public static string ConvertToString(this DestinatarioInvioTelemStatoInvio codice)
        {
            switch (codice)
            {
                case DestinatarioInvioTelemStatoInvio.NON_INVIATO:
                    return "NON_INVIATO";
                case DestinatarioInvioTelemStatoInvio.INVIATO:
                    return "INVIATO";
                default:
                    throw new ArgumentOutOfRangeException(nameof(codice), codice, null);
            }
        }

        public static DestinatarioInvioTelemStatoInvio ConvertToDestinatarioInvioTelemStatoInvioEnum(this string valore)
        {
            switch (valore)
            {
                case "NON_INVIATO":
                    return DestinatarioInvioTelemStatoInvio.NON_INVIATO;
                case "INVIATO":
                    return DestinatarioInvioTelemStatoInvio.INVIATO;
                default:
                    throw new ArgumentOutOfRangeException(nameof(valore), valore, null);
            }
        }
    }

    public class DestinatarioInvioTelemStatoInvioConverter : JsonConverter<DestinatarioInvioTelemStatoInvio>
    {
        public override DestinatarioInvioTelemStatoInvio Read(ref Utf8JsonReader reader, Type typeToConvert, JsonSerializerOptions options)
        {
            string stringValue = reader.GetString();
            return EnumConverter.ConvertToDestinatarioInvioTelemStatoInvioEnum(stringValue);
        }

        public override void Write(Utf8JsonWriter writer, DestinatarioInvioTelemStatoInvio value, JsonSerializerOptions options)
        {
            string stringValue = EnumConverter.ConvertToString(value);
            writer.WriteStringValue(stringValue);
        }
    }
}
