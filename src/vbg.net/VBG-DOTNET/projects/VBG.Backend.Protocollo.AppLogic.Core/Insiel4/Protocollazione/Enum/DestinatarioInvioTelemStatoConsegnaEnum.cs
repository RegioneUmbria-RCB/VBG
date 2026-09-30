using System;
using System.Text.Json;
using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione.Enum
{
    public enum DestinatarioInvioTelemStatoConsegna
    {
        MSG_ACCETTATO,
        MSG_CONSEGNATO,
        MSG_NON_ACCETTATO,
        MSG_NON_CONSEGNATO,
        MSG_PREAVVISO_ERRORE_CONSEGNA,
    }

    public static partial class EnumConverter
    {
        public static string ConvertToString(this DestinatarioInvioTelemStatoConsegna codice)
        {
            switch (codice)
            {
                case DestinatarioInvioTelemStatoConsegna.MSG_ACCETTATO:
                    return "MSG_ACCETTATO";
                case DestinatarioInvioTelemStatoConsegna.MSG_CONSEGNATO:
                    return "MSG_CONSEGNATO";
                case DestinatarioInvioTelemStatoConsegna.MSG_NON_ACCETTATO:
                    return "MSG_NON_ACCETTATO";
                case DestinatarioInvioTelemStatoConsegna.MSG_NON_CONSEGNATO:
                    return "INVMSG_NON_CONSEGNATOIATO";
                case DestinatarioInvioTelemStatoConsegna.MSG_PREAVVISO_ERRORE_CONSEGNA:
                    return "MSG_PREAVVISO_ERRORE_CONSEGNA";
                default:
                    throw new ArgumentOutOfRangeException(nameof(codice), codice, null);
            }
        }

        public static DestinatarioInvioTelemStatoConsegna ConvertToDestinatarioInvioTelemStatoConsegnaEnum(this string valore)
        {
            switch (valore)
            {
                case "MSG_ACCETTATO":
                    return DestinatarioInvioTelemStatoConsegna.MSG_ACCETTATO;
                case "MSG_CONSEGNATO":
                    return DestinatarioInvioTelemStatoConsegna.MSG_CONSEGNATO;
                case "MSG_NON_ACCETTATO":
                    return DestinatarioInvioTelemStatoConsegna.MSG_NON_ACCETTATO;
                case "MSG_NON_CONSEGNATO":
                    return DestinatarioInvioTelemStatoConsegna.MSG_NON_CONSEGNATO;
                case "MSG_PREAVVISO_ERRORE_CONSEGNA":
                    return DestinatarioInvioTelemStatoConsegna.MSG_PREAVVISO_ERRORE_CONSEGNA;
                default:
                    throw new ArgumentOutOfRangeException(nameof(valore), valore, null);
            }
        }
    }

    public class DestinatarioInvioTelemStatoConsegnaConverter : JsonConverter<DestinatarioInvioTelemStatoConsegna>
    {
        public override DestinatarioInvioTelemStatoConsegna Read(ref Utf8JsonReader reader, Type typeToConvert, JsonSerializerOptions options)
        {
            string stringValue = reader.GetString();
            return EnumConverter.ConvertToDestinatarioInvioTelemStatoConsegnaEnum(stringValue);
        }

        public override void Write(Utf8JsonWriter writer, DestinatarioInvioTelemStatoConsegna value, JsonSerializerOptions options)
        {
            string stringValue = EnumConverter.ConvertToString(value);
            writer.WriteStringValue(stringValue);
        }
    }
}
