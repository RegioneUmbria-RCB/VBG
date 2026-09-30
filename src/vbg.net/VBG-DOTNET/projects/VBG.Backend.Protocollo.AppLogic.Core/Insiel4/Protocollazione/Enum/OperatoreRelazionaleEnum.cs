using System;
using System.Text.Json;
using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione.Enum
{
    public enum OperatoreRelazionaleUI
    {
        uguale,
        iniziaCon
    }

    public enum OperatoreRelazionaleUIC
    {
        uguale,
        iniziaCon,
        contiene,
    }

    public enum OperatoreRelazionaleUICF
    {
        uguale,
        iniziaCon,
        contiene,
        finisceCon,
        //minore,
        //minoreUguale,
        //maggiore,
        //maggioreUguale,
        //compresoTra,
    }


    public static partial class EnumConverter
    {
        private const string UGUALE = "UGUALE";
        private const string INIZIA_CON = "INIZIA_CON";
        private const string CONTIENE = "CONTIENE";
        private const string FINISCE_CON = "FINISCE_CON";

        public static string ConvertToString(this OperatoreRelazionaleUI operatore)
        {
            switch (operatore)
            {
                case OperatoreRelazionaleUI.uguale:
                    return UGUALE;
                case OperatoreRelazionaleUI.iniziaCon:
                    return INIZIA_CON;
                default:
                    throw new ArgumentOutOfRangeException(nameof(operatore), operatore, null);
            }
        }

        public static string ConvertToString(this OperatoreRelazionaleUIC operatore)
        {
            switch (operatore)
            {
                case OperatoreRelazionaleUIC.uguale:
                    return UGUALE;
                case OperatoreRelazionaleUIC.iniziaCon:
                    return INIZIA_CON;
                case OperatoreRelazionaleUIC.contiene:
                    return CONTIENE;
                default:
                    throw new ArgumentOutOfRangeException(nameof(operatore), operatore, null);
            }
        }

        public static string ConvertToString(this OperatoreRelazionaleUICF operatore)
        {
            switch (operatore)
            {
                case OperatoreRelazionaleUICF.uguale:
                    return UGUALE;
                case OperatoreRelazionaleUICF.iniziaCon:
                    return INIZIA_CON;
                case OperatoreRelazionaleUICF.contiene:
                    return CONTIENE;
                case OperatoreRelazionaleUICF.finisceCon:
                    return FINISCE_CON;
                //case OperatoreRelazionaleMnemonico.minore:
                //    return "MINORE";
                //case OperatoreRelazionaleMnemonico.minoreUguale:
                //    return "MINORE_UGUALE";
                //case OperatoreRelazionaleMnemonico.maggiore:
                //    return "MAGGIORE";
                //case OperatoreRelazionaleMnemonico.maggioreUguale:
                //    return "MAGGIORE_UGUALE";
                //case OperatoreRelazionaleMnemonico.compresoTra:
                //    return "COMPRESO_TRA";
                default:
                    throw new ArgumentOutOfRangeException(nameof(operatore), operatore, null);
            }
        }

        public static OperatoreRelazionaleUI ConvertToOperatoreRelazionaleUIEnum(this string valore)
        {
            switch (valore)
            {
                case UGUALE:
                    return OperatoreRelazionaleUI.uguale;
                case INIZIA_CON:
                    return OperatoreRelazionaleUI.iniziaCon;
                default:
                    throw new ArgumentOutOfRangeException(nameof(valore), valore, null);
            }
        }

        public static OperatoreRelazionaleUIC ConvertToOperatoreRelazionaleUICEnum(this string valore)
        {
            switch (valore)
            {
                case UGUALE:
                    return OperatoreRelazionaleUIC.uguale;
                case INIZIA_CON:
                    return OperatoreRelazionaleUIC.iniziaCon;
                case CONTIENE:
                    return OperatoreRelazionaleUIC.contiene;
                default:
                    throw new ArgumentOutOfRangeException(nameof(valore), valore, null);
            }
        }

        public static OperatoreRelazionaleUICF ConvertToOperatoreRelazionaleUICFEnum(this string valore)
        {
            switch (valore)
            {
                case UGUALE:
                    return OperatoreRelazionaleUICF.uguale;
                case INIZIA_CON:
                    return OperatoreRelazionaleUICF.iniziaCon;
                case CONTIENE:
                    return OperatoreRelazionaleUICF.contiene;
                case FINISCE_CON:
                    return OperatoreRelazionaleUICF.finisceCon;
                //case "MINORE":
                //    return OperatoreRelazionaleMnemonico.minore;
                //case "MINORE_UGUALE":
                //    return OperatoreRelazionaleMnemonico.minoreUguale;
                //case "MAGGIORE":
                //    return OperatoreRelazionaleMnemonico.maggiore;
                //case "MAGGIORE_UGUALE":
                //    return OperatoreRelazionaleMnemonico.maggioreUguale;
                //case "COMPRESO_TRA":
                //    return OperatoreRelazionaleMnemonico.compresoTra;
                default:
                    throw new ArgumentOutOfRangeException(nameof(valore), valore, null);
            }
        }
    }

    public class OperatoreRelazionaleUICConverter : JsonConverter<OperatoreRelazionaleUIC>
    {
        public override OperatoreRelazionaleUIC Read(ref Utf8JsonReader reader, Type typeToConvert, JsonSerializerOptions options)
        {
            string stringValue = reader.GetString();
            return EnumConverter.ConvertToOperatoreRelazionaleUICEnum(stringValue);
        }

        public override void Write(Utf8JsonWriter writer, OperatoreRelazionaleUIC value, JsonSerializerOptions options)
        {
            string stringValue = EnumConverter.ConvertToString(value);
            writer.WriteStringValue(stringValue);
        }
    }

    public class OperatoreRelazionaleUIConverter : JsonConverter<OperatoreRelazionaleUI>
    {
        public override OperatoreRelazionaleUI Read(ref Utf8JsonReader reader, Type typeToConvert, JsonSerializerOptions options)
        {
            string stringValue = reader.GetString();
            return EnumConverter.ConvertToOperatoreRelazionaleUIEnum(stringValue);
        }

        public override void Write(Utf8JsonWriter writer, OperatoreRelazionaleUI value, JsonSerializerOptions options)
        {
            string stringValue = EnumConverter.ConvertToString(value);
            writer.WriteStringValue(stringValue);
        }
    }

    public class OperatoreRelazionaleUICFConverter : JsonConverter<OperatoreRelazionaleUICF>
    {
        public override OperatoreRelazionaleUICF Read(ref Utf8JsonReader reader, Type typeToConvert, JsonSerializerOptions options)
        {
            string stringValue = reader.GetString();
            return EnumConverter.ConvertToOperatoreRelazionaleUICFEnum(stringValue);
        }

        public override void Write(Utf8JsonWriter writer, OperatoreRelazionaleUICF value, JsonSerializerOptions options)
        {
            string stringValue = EnumConverter.ConvertToString(value);
            writer.WriteStringValue(stringValue);
        }
    }
}
