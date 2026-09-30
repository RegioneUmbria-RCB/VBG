using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Xml.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Document.CSIPiemonte
{
    public static partial class XmlEnumExtensions
    {
        public static TEnum FromReflectedXmlValue<TEnum>(this string xml) where TEnum : struct, IConvertible, IFormattable
        {
            // Adapted from https://stackoverflow.com/questions/35294530/c-sharp-getting-all-enums-value-by-attribute
            var obj = (from field in typeof(TEnum).GetFields(BindingFlags.Public | BindingFlags.Static)
                       from attr in field.GetCustomAttributes(typeof(XmlEnumAttribute),false)
                       where attr != null && ((XmlEnumAttribute)attr).Name == xml
                       select field.GetValue(null)).SingleOrDefault();
            if (obj != null)
                return (TEnum)obj;

            // OK, maybe there is no XmlEnumAttribute override so match on the name.
            return (TEnum)Enum.Parse(typeof(TEnum), xml, false);
        }
    }
}
