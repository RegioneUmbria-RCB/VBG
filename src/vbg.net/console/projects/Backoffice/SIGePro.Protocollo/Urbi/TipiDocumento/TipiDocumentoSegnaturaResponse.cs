using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;
using System.Xml.Serialization;

namespace Init.SIGePro.Protocollo.Urbi.TipiDocumento
{
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.0.30319.33440")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlRootAttribute("xapirest", Namespace = "", IsNullable = false)]
    public partial class xapirestTypeTipiDocumento
    {
        private getElencoTipiDocumento_ResultType getElencoTipiDocumento_ResultField;

        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public getElencoTipiDocumento_ResultType getElencoTipiDocumento_Result
        {
            get
            {
                return this.getElencoTipiDocumento_ResultField;
            }
            set
            {
                this.getElencoTipiDocumento_ResultField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.0.30319.33440")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    public partial class getElencoTipiDocumento_ResultType
    {

        private string rESULTField;

        private string numTipiDocumentoField;

        private DocumentoType[] sEQ_DocumentoField;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public string RESULT
        {
            get
            {
                return this.rESULTField;
            }
            set
            {
                this.rESULTField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, DataType = "integer")]
        public string NumTipiDocumento
        {
            get
            {
                return this.numTipiDocumentoField;
            }
            set
            {
                this.numTipiDocumentoField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlArrayAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        [System.Xml.Serialization.XmlArrayItemAttribute("Documento", Form = System.Xml.Schema.XmlSchemaForm.Unqualified, IsNullable = false)]
        public DocumentoType[] SEQ_Documento
        {
            get
            {
                return this.sEQ_DocumentoField;
            }
            set
            {
                this.sEQ_DocumentoField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.0.30319.33440")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    public partial class DocumentoType
    {

        private string codiceField;

        private string descrizioneField;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public string Codice
        {
            get
            {
                return this.codiceField;
            }
            set
            {
                this.codiceField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public string Descrizione
        {
            get
            {
                return this.descrizioneField;
            }
            set
            {
                this.descrizioneField = value;
            }
        }
    }
}