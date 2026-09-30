using System;
using System.Text.Json;
using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione.Enum
{
    public enum DestinatarioInvioTelemTipoCasella
    {
        NON_CERTIFICATA,
        CERTIFICATA,
    }

    public static partial class EnumConverter
    {
        public static string ConvertToString(this DestinatarioInvioTelemTipoCasella codice)
        {
            switch (codice)
            {
                case DestinatarioInvioTelemTipoCasella.NON_CERTIFICATA:
                    return "NON_CERTIFICATA";
                case DestinatarioInvioTelemTipoCasella.CERTIFICATA:
                    return "CERTIFICATA";
                default:
                    throw new ArgumentOutOfRangeException(nameof(codice), codice, null);
            }
        }

        public static DestinatarioInvioTelemTipoCasella ConvertToDestinatarioInvioTelemTipoCasellaEnum(this string valore)
        {
            switch (valore)
            {
                case "NON_CERTIFICATA":
                    return DestinatarioInvioTelemTipoCasella.NON_CERTIFICATA;
                case "CERTIFICATA":
                    return DestinatarioInvioTelemTipoCasella.CERTIFICATA;
                default:
                    throw new ArgumentOutOfRangeException(nameof(valore), valore, null);
            }
        }
    }

    public class DestinatarioInvioTelemTipoCasellaConverter : JsonConverter<DestinatarioInvioTelemTipoCasella>
    {
        public override DestinatarioInvioTelemTipoCasella Read(ref Utf8JsonReader reader, Type typeToConvert, JsonSerializerOptions options)
        {
            string stringValue = reader.GetString();
            return EnumConverter.ConvertToDestinatarioInvioTelemTipoCasellaEnum(stringValue);
        }

        public override void Write(Utf8JsonWriter writer, DestinatarioInvioTelemTipoCasella value, JsonSerializerOptions options)
        {
            string stringValue = EnumConverter.ConvertToString(value);
            writer.WriteStringValue(stringValue);
        }
    }
}
