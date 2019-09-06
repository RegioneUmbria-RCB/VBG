using System;
using System.Collections.Generic;
using System.Text;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
    public partial class IstanzeCalcoloCanoniD
    {

        #region Foreign
        CanoniCategorie m_categoria;
        [ForeignKey("Idcomune,FkCcid", "Idcomune,Id")]
        public CanoniCategorie Categoria
        {
            get { return m_categoria; }
            set { m_categoria = value; }
        }

        CanoniTipiSuperfici m_tiposuperficie;
        [ForeignKey("Idcomune,FkTsid", "Idcomune,Id")]
        public CanoniTipiSuperfici TipoSuperficie
        {
            get { return m_tiposuperficie; }
            set { m_tiposuperficie = value; }
        }
        #endregion

    }

}
