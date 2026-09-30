// -----------------------------------------------------------------------
// <copyright file="BaseDto.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace VBG.Backend.SIT.AppLogic
{
    public class BaseDto<T1, T2>
    {
        public T1 Codice { get; set; }
        public T2 Descrizione { get; set; }

        public BaseDto()
        {
        }

        public BaseDto(T1 codice, T2 descrizione)
        {
            this.Codice = codice;
            this.Descrizione = descrizione;
        }
    }
}
