using System;
using System.Collections.Generic;
using System.Text;

namespace Init.SIGePro.Data
{
    public partial class CanoniTipiSuperfici
    {
        /// <summary>
        /// Questa proprietà serve solamente per essere visualizzata sulla lista dei canoni superfici [Sola lettura].
        /// </summary>
        public string TipocalcoloDescr
        {
            get 
            {
                switch (Tipocalcolo)
                { 
                    case "A":
                        return "ANNUALE";
                    case "S":
                        return "STAGIONALE";
                    default:
                        return String.Empty;
                }
            }
        }

        public override string ToString()
        {
            return TipoSuperficie;
        }
    }
}
