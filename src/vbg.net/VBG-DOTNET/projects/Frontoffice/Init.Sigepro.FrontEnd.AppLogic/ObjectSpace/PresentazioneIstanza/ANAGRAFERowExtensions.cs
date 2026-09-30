using System;

namespace Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza
{
    public static class ANAGRAFERowExtensions
    {
        public static void SetColumnDateValue(this PresentazioneIstanzaDbV2.ANAGRAFERow row, string columnName, DateTime? value)
        {
            if (value == null)
            {
                row[columnName] = DBNull.Value;
            }
            else
            {
                row[columnName] = value.Value;
            }
        }
    }
}
