using System;
using System.Collections.Generic;
using System.Text;

namespace Init.SIGePro.Data
{
    public partial class IstanzeCalcoloCanoniO
    {
        private IstanzeOneri m_onere = null;
        public IstanzeOneri Onere
        {
            get { return m_onere; }
            set { m_onere = value; }
        }
    }
}
