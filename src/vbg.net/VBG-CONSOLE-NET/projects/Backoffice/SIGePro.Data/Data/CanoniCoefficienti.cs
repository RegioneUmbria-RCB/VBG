using System;
using System.Collections.Generic;
using System.Text;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
    public partial class CanoniCoefficienti
    {
        #region Foreign keys
        CanoniTipiSuperfici m_tiposuperficie;
        [ForeignKey("Idcomune,FkTsId", "Idcomune,Id")]
        public CanoniTipiSuperfici TipoSuperficie
        {
            get { return m_tiposuperficie; }
            set { m_tiposuperficie = value; }
        }

        CanoniCategorie m_categoria;
        [ForeignKey("Idcomune,FkCcId", "Idcomune,Id")]
        public CanoniCategorie Categoria
        {
            get { return m_categoria; }
            set { m_categoria = value; }
        }
        #endregion
    }
}
