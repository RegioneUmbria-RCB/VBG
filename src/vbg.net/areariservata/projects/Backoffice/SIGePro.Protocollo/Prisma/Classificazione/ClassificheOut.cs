using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Protocollo.Prisma.Classificazione
{
    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.6.1055.0")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class CLASSIFICHE
    {

        private CLASSIFICA[] cLASSIFICAField;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("CLASSIFICA")]
        public CLASSIFICA[] CLASSIFICA
        {
            get
            {
                return this.cLASSIFICAField;
            }
            set
            {
                this.cLASSIFICAField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.6.1055.0")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class CLASSIFICA
    {

        private string iD_DOCUMENTOField;

        private string cLASS_CODField;

        private string cLASS_DALField;

        private string cLASS_ALField;

        private string dESCRIZIONEField;

        private string dATA_CREAZIONEField;

        private string cONTENITORE_DOCUMENTIField;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public string ID_DOCUMENTO
        {
            get
            {
                return this.iD_DOCUMENTOField;
            }
            set
            {
                this.iD_DOCUMENTOField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public string CLASS_COD
        {
            get
            {
                return this.cLASS_CODField;
            }
            set
            {
                this.cLASS_CODField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public string CLASS_DAL
        {
            get
            {
                return this.cLASS_DALField;
            }
            set
            {
                this.cLASS_DALField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public string CLASS_AL
        {
            get
            {
                return this.cLASS_ALField;
            }
            set
            {
                this.cLASS_ALField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public string DESCRIZIONE
        {
            get
            {
                return this.dESCRIZIONEField;
            }
            set
            {
                this.dESCRIZIONEField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public string DATA_CREAZIONE
        {
            get
            {
                return this.dATA_CREAZIONEField;
            }
            set
            {
                this.dATA_CREAZIONEField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public string CONTENITORE_DOCUMENTI
        {
            get
            {
                return this.cONTENITORE_DOCUMENTIField;
            }
            set
            {
                this.cONTENITORE_DOCUMENTIField = value;
            }
        }
    }
}