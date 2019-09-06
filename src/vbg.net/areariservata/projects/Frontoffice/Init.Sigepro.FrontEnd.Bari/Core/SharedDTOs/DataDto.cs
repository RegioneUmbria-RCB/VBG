// -----------------------------------------------------------------------
// <copyright file="DataDto.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Bari.Core.SharedDTOs
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using System.Globalization;
    using System.Xml.Serialization;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class DataDto
	{
		public DateTime? Valore { get; set; }

        [XmlElement]
        public string ValoreString
        {
            get
            {
                if (!this.Valore.HasValue)
                {
                    return String.Empty;
                }

                return this.Valore.Value.ToString("yyyyMMdd");
            }
            set
            {
            }
        }

		public DataDto()
		{
		}

		public DataDto(string valore)
		{
			this.Valore = String.IsNullOrEmpty(valore) ? (DateTime?)null : DateTime.ParseExact(valore, "dd/MM/yyyy", CultureInfo.InvariantCulture);
		}

		public override string ToString()
		{
			return this.Valore.HasValue ? this.Valore.Value.ToString("dd/MM/yyyy") : String.Empty;
		}
	}
}
