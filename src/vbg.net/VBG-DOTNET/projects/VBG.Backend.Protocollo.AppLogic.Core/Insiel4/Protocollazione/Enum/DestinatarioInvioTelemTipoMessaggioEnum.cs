using System;
using System.Text.Json;
using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione.Enum
{
    public enum DestinatarioInvioTelemTipoMessaggio
    {
        PEC,
        IOP,
        INT,
    }

    public static partial class EnumConverter
    {
        public static string ConvertToString(this DestinatarioInvioTelemTipoMessaggio codice)
        {
            switch (codice)
            {
                case DestinatarioInvioTelemTipoMessaggio.PEC:
                    return "PEC";
                case DestinatarioInvioTelemTipoMessaggio.IOP:
                    return "IOP";
                case DestinatarioInvioTelemTipoMessaggio.INT:
                    return "INT";
                default:
                    throw new ArgumentOutOfRangeException(nameof(codice), codice, null);
            }
        }

        public static DestinatarioInvioTelemTipoMessaggio ConvertToDestinatarioInvioTelemTipoMessaggioEnum(this string valore)
        {
            switch (valore)
            {
                case "PEC":
                    return DestinatarioInvioTelemTipoMessaggio.PEC;
                case "IOP":
                    return DestinatarioInvioTelemTipoMessaggio.IOP;
                case "INT":
                    return DestinatarioInvioTelemTipoMessaggio.INT;
                default:
                    throw new ArgumentOutOfRangeException(nameof(valore), valore, null);
            }
        }
    }

    public class DestinatarioInvioTelemTipoMessaggioConverter : JsonConverter<DestinatarioInvioTelemTipoMessaggio>
    {
        public override DestinatarioInvioTelemTipoMessaggio Read(ref Utf8JsonReader reader, Type typeToConvert, JsonSerializerOptions options)
        {
            string stringValue = reader.GetString();
            return EnumConverter.ConvertToDestinatarioInvioTelemTipoMessaggioEnum(stringValue);
        }

        public override void Write(Utf8JsonWriter writer, DestinatarioInvioTelemTipoMessaggio value, JsonSerializerOptions options)
        {
            string stringValue = EnumConverter.ConvertToString(value);
            writer.WriteStringValue(stringValue);
        }
    }
}
