﻿//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by a tool.
//     Runtime Version:4.0.30319.1022
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

using System.Xml.Serialization;

// 
// This source code was auto-generated by xsd, Version=4.0.30319.1.
// 

namespace Init.SIGePro.Protocollo.EGrammata2.LeggiProtocollo.SegnaturaResponse
{
    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.0.30319.1")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true, Namespace = "")]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class RisultatoRicerca
    {

        private Stato statoField;

        private Documento[] documentoField;

        private string versioneField;

        private string langField;

        public RisultatoRicerca()
        {
            this.versioneField = "dataPubblicazione";
        }

        /// <remarks/>
        public Stato Stato
        {
            get
            {
                return this.statoField;
            }
            set
            {
                this.statoField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("Documento")]
        public Documento[] Documento
        {
            get
            {
                return this.documentoField;
            }
            set
            {
                this.documentoField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute(DataType = "NMTOKEN")]
        public string versione
        {
            get
            {
                return this.versioneField;
            }
            set
            {
                this.versioneField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute(Form = System.Xml.Schema.XmlSchemaForm.Qualified, Namespace = "http://www.w3.org/XML/1998/namespace")]
        public string lang
        {
            get
            {
                return this.langField;
            }
            set
            {
                this.langField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.0.30319.1")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true, Namespace = "")]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class Stato
    {

        private string codiceField;

        private string messaggioField;

        /// <remarks/>
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
        public string Messaggio
        {
            get
            {
                return this.messaggioField;
            }
            set
            {
                this.messaggioField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.0.30319.1")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true, Namespace = "")]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class Documento
    {

        private string idDocField;

        private string numCopiaField;

        private RegPrimaria regPrimariaField;

        private string dtRegField;

        private RegSecondaria regSecondariaField;

        private RegEmergenza regEmergenzaField;

        private string dtArrivoField;

        private string oggettoField;

        private string mittentiField;

        private string destinatariField;

        private string uoAssegnatariaField;

        private string idIndiceField;

        private string desIndiceField;

        private string annoFascField;

        private string titoloField;

        private string classeField;

        private string sottoClasseField;

        private string livello4Field;

        private string livello5Field;

        private string progrFascField;

        private string idFascicoloField;

        private string numSottofascField;

        private string oggettoFascField;

        private string dtAperturaFascField;

        private string dtChiusuraFascField;

        private string oggettoSottofascField;

        private string dtAperturaSottofascField;

        private string dtChiusuraSottofascField;

        private string nroAllegField;

        private string rifProvField;

        private string protProvField;

        private string dtProvField;

        private DocPrec docPrecField;

        //private DocumentoTipoReg tipoRegField;
        private string tipoRegField;

        private DocumentoFlgPresoIncarico flgPresoIncaricoField;

        private DocumentoFlgEvd flgEvdField;

        private DocumentoFlgRsv flgRsvField;

        private DocumentoFlgDS flgDSField;

        private DocumentoFlgScartato flgScartatoField;

        private DocumentoFlgRegAnnullata flgRegAnnullataField;

        private DocumentoFlgCopiaAnnullata flgCopiaAnnullataField;

        public Documento()
        {
            this.tipoRegField = "In entrata";
            this.flgPresoIncaricoField = DocumentoFlgPresoIncarico.N;
            this.flgEvdField = DocumentoFlgEvd.N;
            this.flgRsvField = DocumentoFlgRsv.N;
            this.flgDSField = DocumentoFlgDS.N;
            this.flgScartatoField = DocumentoFlgScartato.N;
            this.flgRegAnnullataField = DocumentoFlgRegAnnullata.N;
            this.flgCopiaAnnullataField = DocumentoFlgCopiaAnnullata.N;
        }

        /// <remarks/>
        public string IdDoc
        {
            get
            {
                return this.idDocField;
            }
            set
            {
                this.idDocField = value;
            }
        }

        /// <remarks/>
        public string NumCopia
        {
            get
            {
                return this.numCopiaField;
            }
            set
            {
                this.numCopiaField = value;
            }
        }

        /// <remarks/>
        public RegPrimaria RegPrimaria
        {
            get
            {
                return this.regPrimariaField;
            }
            set
            {
                this.regPrimariaField = value;
            }
        }

        /// <remarks/>
        public string DtReg
        {
            get
            {
                return this.dtRegField;
            }
            set
            {
                this.dtRegField = value;
            }
        }

        /// <remarks/>
        public RegSecondaria RegSecondaria
        {
            get
            {
                return this.regSecondariaField;
            }
            set
            {
                this.regSecondariaField = value;
            }
        }

        /// <remarks/>
        public RegEmergenza RegEmergenza
        {
            get
            {
                return this.regEmergenzaField;
            }
            set
            {
                this.regEmergenzaField = value;
            }
        }

        /// <remarks/>
        public string DtArrivo
        {
            get
            {
                return this.dtArrivoField;
            }
            set
            {
                this.dtArrivoField = value;
            }
        }

        /// <remarks/>
        public string Oggetto
        {
            get
            {
                return this.oggettoField;
            }
            set
            {
                this.oggettoField = value;
            }
        }

        /// <remarks/>
        public string Mittenti
        {
            get
            {
                return this.mittentiField;
            }
            set
            {
                this.mittentiField = value;
            }
        }

        /// <remarks/>
        public string Destinatari
        {
            get
            {
                return this.destinatariField;
            }
            set
            {
                this.destinatariField = value;
            }
        }

        /// <remarks/>
        public string UoAssegnataria
        {
            get
            {
                return this.uoAssegnatariaField;
            }
            set
            {
                this.uoAssegnatariaField = value;
            }
        }

        /// <remarks/>
        public string IdIndice
        {
            get
            {
                return this.idIndiceField;
            }
            set
            {
                this.idIndiceField = value;
            }
        }

        /// <remarks/>
        public string DesIndice
        {
            get
            {
                return this.desIndiceField;
            }
            set
            {
                this.desIndiceField = value;
            }
        }

        /// <remarks/>
        public string AnnoFasc
        {
            get
            {
                return this.annoFascField;
            }
            set
            {
                this.annoFascField = value;
            }
        }

        /// <remarks/>
        public string Titolo
        {
            get
            {
                return this.titoloField;
            }
            set
            {
                this.titoloField = value;
            }
        }

        /// <remarks/>
        public string Classe
        {
            get
            {
                return this.classeField;
            }
            set
            {
                this.classeField = value;
            }
        }

        /// <remarks/>
        public string SottoClasse
        {
            get
            {
                return this.sottoClasseField;
            }
            set
            {
                this.sottoClasseField = value;
            }
        }

        /// <remarks/>
        public string Livello4
        {
            get
            {
                return this.livello4Field;
            }
            set
            {
                this.livello4Field = value;
            }
        }

        /// <remarks/>
        public string Livello5
        {
            get
            {
                return this.livello5Field;
            }
            set
            {
                this.livello5Field = value;
            }
        }

        /// <remarks/>
        public string ProgrFasc
        {
            get
            {
                return this.progrFascField;
            }
            set
            {
                this.progrFascField = value;
            }
        }

        /// <remarks/>
        public string IdFascicolo
        {
            get
            {
                return this.idFascicoloField;
            }
            set
            {
                this.idFascicoloField = value;
            }
        }

        /// <remarks/>
        public string NumSottofasc
        {
            get
            {
                return this.numSottofascField;
            }
            set
            {
                this.numSottofascField = value;
            }
        }

        /// <remarks/>
        public string OggettoFasc
        {
            get
            {
                return this.oggettoFascField;
            }
            set
            {
                this.oggettoFascField = value;
            }
        }

        /// <remarks/>
        public string DtAperturaFasc
        {
            get
            {
                return this.dtAperturaFascField;
            }
            set
            {
                this.dtAperturaFascField = value;
            }
        }

        /// <remarks/>
        public string DtChiusuraFasc
        {
            get
            {
                return this.dtChiusuraFascField;
            }
            set
            {
                this.dtChiusuraFascField = value;
            }
        }

        /// <remarks/>
        public string OggettoSottofasc
        {
            get
            {
                return this.oggettoSottofascField;
            }
            set
            {
                this.oggettoSottofascField = value;
            }
        }

        /// <remarks/>
        public string DtAperturaSottofasc
        {
            get
            {
                return this.dtAperturaSottofascField;
            }
            set
            {
                this.dtAperturaSottofascField = value;
            }
        }

        /// <remarks/>
        public string DtChiusuraSottofasc
        {
            get
            {
                return this.dtChiusuraSottofascField;
            }
            set
            {
                this.dtChiusuraSottofascField = value;
            }
        }

        /// <remarks/>
        public string NroAlleg
        {
            get
            {
                return this.nroAllegField;
            }
            set
            {
                this.nroAllegField = value;
            }
        }

        /// <remarks/>
        public string RifProv
        {
            get
            {
                return this.rifProvField;
            }
            set
            {
                this.rifProvField = value;
            }
        }

        /// <remarks/>
        public string ProtProv
        {
            get
            {
                return this.protProvField;
            }
            set
            {
                this.protProvField = value;
            }
        }

        /// <remarks/>
        public string DtProv
        {
            get
            {
                return this.dtProvField;
            }
            set
            {
                this.dtProvField = value;
            }
        }

        /// <remarks/>
        public DocPrec DocPrec
        {
            get
            {
                return this.docPrecField;
            }
            set
            {
                this.docPrecField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        [System.ComponentModel.DefaultValueAttribute("In entrata")]
        public string TipoReg
        {
            get
            {
                return this.tipoRegField;
            }
            set
            {
                this.tipoRegField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        [System.ComponentModel.DefaultValueAttribute(DocumentoFlgPresoIncarico.N)]
        public DocumentoFlgPresoIncarico FlgPresoIncarico
        {
            get
            {
                return this.flgPresoIncaricoField;
            }
            set
            {
                this.flgPresoIncaricoField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        [System.ComponentModel.DefaultValueAttribute(DocumentoFlgEvd.N)]
        public DocumentoFlgEvd FlgEvd
        {
            get
            {
                return this.flgEvdField;
            }
            set
            {
                this.flgEvdField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        [System.ComponentModel.DefaultValueAttribute(DocumentoFlgRsv.N)]
        public DocumentoFlgRsv FlgRsv
        {
            get
            {
                return this.flgRsvField;
            }
            set
            {
                this.flgRsvField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        [System.ComponentModel.DefaultValueAttribute(DocumentoFlgDS.N)]
        public DocumentoFlgDS FlgDS
        {
            get
            {
                return this.flgDSField;
            }
            set
            {
                this.flgDSField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        [System.ComponentModel.DefaultValueAttribute(DocumentoFlgScartato.N)]
        public DocumentoFlgScartato FlgScartato
        {
            get
            {
                return this.flgScartatoField;
            }
            set
            {
                this.flgScartatoField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        [System.ComponentModel.DefaultValueAttribute(DocumentoFlgRegAnnullata.N)]
        public DocumentoFlgRegAnnullata FlgRegAnnullata
        {
            get
            {
                return this.flgRegAnnullataField;
            }
            set
            {
                this.flgRegAnnullataField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        [System.ComponentModel.DefaultValueAttribute(DocumentoFlgCopiaAnnullata.N)]
        public DocumentoFlgCopiaAnnullata FlgCopiaAnnullata
        {
            get
            {
                return this.flgCopiaAnnullataField;
            }
            set
            {
                this.flgCopiaAnnullataField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.0.30319.1")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true, Namespace = "")]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class RegPrimaria
    {

        private EstremiReg estremiRegField;

        /// <remarks/>
        public EstremiReg EstremiReg
        {
            get
            {
                return this.estremiRegField;
            }
            set
            {
                this.estremiRegField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.0.30319.1")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true, Namespace = "")]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class EstremiReg
    {

        private string siglaField;

        private string annoField;

        private string nroField;

        /// <remarks/>
        public string Sigla
        {
            get
            {
                return this.siglaField;
            }
            set
            {
                this.siglaField = value;
            }
        }

        /// <remarks/>
        public string Anno
        {
            get
            {
                return this.annoField;
            }
            set
            {
                this.annoField = value;
            }
        }

        /// <remarks/>
        public string Nro
        {
            get
            {
                return this.nroField;
            }
            set
            {
                this.nroField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.0.30319.1")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true, Namespace = "")]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class RegSecondaria
    {

        private EstremiReg estremiRegField;

        /// <remarks/>
        public EstremiReg EstremiReg
        {
            get
            {
                return this.estremiRegField;
            }
            set
            {
                this.estremiRegField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.0.30319.1")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true, Namespace = "")]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class RegEmergenza
    {

        private EstremiReg estremiRegField;

        /// <remarks/>
        public EstremiReg EstremiReg
        {
            get
            {
                return this.estremiRegField;
            }
            set
            {
                this.estremiRegField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.0.30319.1")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true, Namespace = "")]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class DocPrec
    {

        private EstremiReg estremiRegField;

        /// <remarks/>
        public EstremiReg EstremiReg
        {
            get
            {
                return this.estremiRegField;
            }
            set
            {
                this.estremiRegField = value;
            }
        }
    }

    /*/// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.0.30319.1")]
    [System.SerializableAttribute()]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true, Namespace = "")]
    public enum DocumentoTipoReg
    {

        /// <remarks/>
        Entrata,

        /// <remarks/>
        Uscita,

        /// <remarks/>
        Interna,
    }*/

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.0.30319.1")]
    [System.SerializableAttribute()]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true, Namespace = "")]
    public enum DocumentoFlgPresoIncarico
    {

        /// <remarks/>
        S,

        /// <remarks/>
        N,
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.0.30319.1")]
    [System.SerializableAttribute()]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true, Namespace = "")]
    public enum DocumentoFlgEvd
    {

        /// <remarks/>
        S,

        /// <remarks/>
        N,
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.0.30319.1")]
    [System.SerializableAttribute()]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true, Namespace = "")]
    public enum DocumentoFlgRsv
    {

        /// <remarks/>
        S,

        /// <remarks/>
        N,
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.0.30319.1")]
    [System.SerializableAttribute()]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true, Namespace = "")]
    public enum DocumentoFlgDS
    {

        /// <remarks/>
        S,

        /// <remarks/>
        N,
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.0.30319.1")]
    [System.SerializableAttribute()]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true, Namespace = "")]
    public enum DocumentoFlgScartato
    {

        /// <remarks/>
        S,

        /// <remarks/>
        N,
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.0.30319.1")]
    [System.SerializableAttribute()]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true, Namespace = "")]
    public enum DocumentoFlgRegAnnullata
    {

        /// <remarks/>
        S,

        /// <remarks/>
        N,
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.0.30319.1")]
    [System.SerializableAttribute()]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true, Namespace = "")]
    public enum DocumentoFlgCopiaAnnullata
    {

        /// <remarks/>
        S,

        /// <remarks/>
        N,
    }
}