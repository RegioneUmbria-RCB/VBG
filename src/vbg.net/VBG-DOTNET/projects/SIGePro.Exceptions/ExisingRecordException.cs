using Init.SIGePro.Exceptions;

namespace Init.SIGePro
{
    public class ExistingRecordException : BaseException
    {
        public ExistingRecordException(string message) : base(message) { }
    }
}
