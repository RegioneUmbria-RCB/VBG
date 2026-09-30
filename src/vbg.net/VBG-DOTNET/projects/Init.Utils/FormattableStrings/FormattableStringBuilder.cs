//using System;
//using System.Collections.Generic;
//using System.Linq;
//using System.Runtime.CompilerServices;

//namespace Init.Utils.FormattableStrings
//{
//    internal class ConcatenatedFormattableString : FormattableString
//    {
//        private readonly FormattableString[] _parts;

//        public ConcatenatedFormattableString(params FormattableString[] parts)
//        {
//            this._parts = parts;
//        }

//        public override int ArgumentCount => this._parts.Sum(p => p.ArgumentCount);

//        public override string Format => string.Concat(this._parts.Select(p => p.Format));

//        public override object GetArgument(int index)
//        {
//            return this.GetArguments()[index];
//        }

//        public override object?[] GetArguments()
//        {
//            return this._parts.SelectMany(p => p.GetArguments()).ToArray();
//        }

//        public override string ToString(IFormatProvider? formatProvider)
//        {
//            return string.Concat(this._parts.Select(p => p.ToString(formatProvider)));
//        }
//    }


//    public class FormattableStringBuilder
//    {
//        private readonly LinkedList<FormattableString> _parts = new LinkedList<FormattableString>();

//        public FormattableStringBuilder()
//        {
//        }

//        public void Append(FormattableString part)
//        {
//            this._parts.AddLast(part);
//        }

//        public void AppendString(string part)
//        {
//            this._parts.AddLast(FormattableStringFactory.Create(part));
//        }

//        public FormattableString ToFormattableString()
//        {
//            return new ConcatenatedFormattableString(this._parts.ToArray());
//        }
//    }
//}
