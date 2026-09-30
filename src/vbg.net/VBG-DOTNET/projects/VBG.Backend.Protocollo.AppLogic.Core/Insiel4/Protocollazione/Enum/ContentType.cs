using System;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione.Enum
{
    public enum ContentType
    {
        json,
        multipart
    }

    public static partial class EnumConverter
    {
        public static string ConvertToString(this ContentType codice)
        {
            switch (codice)
            {
                case ContentType.json:
                    return "application/json";
                case ContentType.multipart:
                    return "multipart/form-data";
                default:
                    throw new ArgumentOutOfRangeException(nameof(codice), codice, null);
            }
        }
    }
}
