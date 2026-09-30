namespace VBG.Backend.Protocollo.AppLogic.Core.Auriga.UD
{
    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.6.1055.0")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    public class EstremiRegNumType
    {

        private EstremiRegNumTypeCategoriaReg categoriaRegField;

        private string siglaRegField;

        private string annoRegField;

        private string numRegField;

        /// <remarks/>
        public EstremiRegNumTypeCategoriaReg CategoriaReg
        {
            get
            {
                return this.categoriaRegField;
            }
            set
            {
                this.categoriaRegField = value;
            }
        }

        /// <remarks/>
        public string SiglaReg
        {
            get
            {
                return this.siglaRegField;
            }
            set
            {
                this.siglaRegField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(DataType = "integer")]
        public string AnnoReg
        {
            get
            {
                return this.annoRegField;
            }
            set
            {
                this.annoRegField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(DataType = "integer")]
        public string NumReg
        {
            get
            {
                return this.numRegField;
            }
            set
            {
                this.numRegField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.6.1055.0")]
    [System.SerializableAttribute()]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    public enum EstremiRegNumTypeCategoriaReg
    {

        /// <remarks/>
        PG,

        /// <remarks/>
        PP,

        /// <remarks/>
        R,

        /// <remarks/>
        I,
    }

}
