namespace VBG.Backend.Protocollo.AppLogic.Shared.VerificaFirmaDigitale
{


    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("Microsoft.Tools.ServiceModel.Svcutil", "2.2.0-preview1.23462.5")]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.Xml.Serialization.XmlTypeAttribute(Namespace = "http://ws.dss.markt.ec.europa.eu/")]
    public partial class IOException
    {
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("Microsoft.Tools.ServiceModel.Svcutil", "2.2.0-preview1.23462.5")]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.Xml.Serialization.XmlTypeAttribute(Namespace = "http://ws.dss.markt.ec.europa.eu/")]
    public partial class wsTimeInformation
    {

        private System.DateTime verificationTimeField;

        private bool verificationTimeFieldSpecified;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 0)]
        public System.DateTime verificationTime
        {
            get
            {
                return this.verificationTimeField;
            }
            set
            {
                this.verificationTimeField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool verificationTimeSpecified
        {
            get
            {
                return this.verificationTimeFieldSpecified;
            }
            set
            {
                this.verificationTimeFieldSpecified = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("Microsoft.Tools.ServiceModel.Svcutil", "2.2.0-preview1.23462.5")]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.Xml.Serialization.XmlTypeAttribute(Namespace = "http://ws.dss.markt.ec.europa.eu/")]
    public partial class wsSignatureLevelXL
    {

        private string certificateValuesVerificationField;

        private string levelReachedField;

        private string revocationValuesVerificationField;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 0)]
        public string certificateValuesVerification
        {
            get
            {
                return this.certificateValuesVerificationField;
            }
            set
            {
                this.certificateValuesVerificationField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 1)]
        public string levelReached
        {
            get
            {
                return this.levelReachedField;
            }
            set
            {
                this.levelReachedField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 2)]
        public string revocationValuesVerification
        {
            get
            {
                return this.revocationValuesVerificationField;
            }
            set
            {
                this.revocationValuesVerificationField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("Microsoft.Tools.ServiceModel.Svcutil", "2.2.0-preview1.23462.5")]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.Xml.Serialization.XmlTypeAttribute(Namespace = "http://ws.dss.markt.ec.europa.eu/")]
    public partial class wsSignatureLevelX
    {

        private string levelReachedField;

        private wsTimestampVerificationResult[] referencesTimestampsVerificationField;

        private wsTimestampVerificationResult[] signatureAndRefsTimestampsVerificationField;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 0)]
        public string levelReached
        {
            get
            {
                return this.levelReachedField;
            }
            set
            {
                this.levelReachedField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("referencesTimestampsVerification", Form = System.Xml.Schema.XmlSchemaForm.Unqualified, IsNullable = true, Order = 1)]
        public wsTimestampVerificationResult[] referencesTimestampsVerification
        {
            get
            {
                return this.referencesTimestampsVerificationField;
            }
            set
            {
                this.referencesTimestampsVerificationField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("signatureAndRefsTimestampsVerification", Form = System.Xml.Schema.XmlSchemaForm.Unqualified, IsNullable = true, Order = 2)]
        public wsTimestampVerificationResult[] signatureAndRefsTimestampsVerification
        {
            get
            {
                return this.signatureAndRefsTimestampsVerificationField;
            }
            set
            {
                this.signatureAndRefsTimestampsVerificationField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("Microsoft.Tools.ServiceModel.Svcutil", "2.2.0-preview1.23462.5")]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.Xml.Serialization.XmlTypeAttribute(Namespace = "http://ws.dss.markt.ec.europa.eu/")]
    public partial class wsTimestampVerificationResult
    {

        private string certPathVerificationField;

        private System.DateTime creationTimeField;

        private bool creationTimeFieldSpecified;

        private string issuerNameField;

        private string sameDigestField;

        private string serialNumberField;

        private string signatureAlgorithmField;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 0)]
        public string certPathVerification
        {
            get
            {
                return this.certPathVerificationField;
            }
            set
            {
                this.certPathVerificationField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 1)]
        public System.DateTime creationTime
        {
            get
            {
                return this.creationTimeField;
            }
            set
            {
                this.creationTimeField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool creationTimeSpecified
        {
            get
            {
                return this.creationTimeFieldSpecified;
            }
            set
            {
                this.creationTimeFieldSpecified = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 2)]
        public string issuerName
        {
            get
            {
                return this.issuerNameField;
            }
            set
            {
                this.issuerNameField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 3)]
        public string sameDigest
        {
            get
            {
                return this.sameDigestField;
            }
            set
            {
                this.sameDigestField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 4)]
        public string serialNumber
        {
            get
            {
                return this.serialNumberField;
            }
            set
            {
                this.serialNumberField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 5)]
        public string signatureAlgorithm
        {
            get
            {
                return this.signatureAlgorithmField;
            }
            set
            {
                this.signatureAlgorithmField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("Microsoft.Tools.ServiceModel.Svcutil", "2.2.0-preview1.23462.5")]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.Xml.Serialization.XmlTypeAttribute(Namespace = "http://ws.dss.markt.ec.europa.eu/")]
    public partial class wsSignatureLevelT
    {

        private string levelReachedField;

        private wsTimestampVerificationResult[] signatureTimestampsVerificationField;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 0)]
        public string levelReached
        {
            get
            {
                return this.levelReachedField;
            }
            set
            {
                this.levelReachedField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("signatureTimestampsVerification", Form = System.Xml.Schema.XmlSchemaForm.Unqualified, IsNullable = true, Order = 1)]
        public wsTimestampVerificationResult[] signatureTimestampsVerification
        {
            get
            {
                return this.signatureTimestampsVerificationField;
            }
            set
            {
                this.signatureTimestampsVerificationField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("Microsoft.Tools.ServiceModel.Svcutil", "2.2.0-preview1.23462.5")]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.Xml.Serialization.XmlTypeAttribute(Namespace = "http://ws.dss.markt.ec.europa.eu/")]
    public partial class wsSignatureLevelLTV
    {

        private string levelReachedField;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 0)]
        public string levelReached
        {
            get
            {
                return this.levelReachedField;
            }
            set
            {
                this.levelReachedField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("Microsoft.Tools.ServiceModel.Svcutil", "2.2.0-preview1.23462.5")]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.Xml.Serialization.XmlTypeAttribute(Namespace = "http://ws.dss.markt.ec.europa.eu/")]
    public partial class wsSignatureLevelEPES
    {

        private string levelReachedField;

        private string policyValueField;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 0)]
        public string levelReached
        {
            get
            {
                return this.levelReachedField;
            }
            set
            {
                this.levelReachedField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 1)]
        public string policyValue
        {
            get
            {
                return this.policyValueField;
            }
            set
            {
                this.policyValueField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("Microsoft.Tools.ServiceModel.Svcutil", "2.2.0-preview1.23462.5")]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.Xml.Serialization.XmlTypeAttribute(Namespace = "http://ws.dss.markt.ec.europa.eu/")]
    public partial class wsSignatureLevelC
    {

        private string certificateRefsVerificationField;

        private string levelReachedField;

        private string revocationRefsVerificationField;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 0)]
        public string certificateRefsVerification
        {
            get
            {
                return this.certificateRefsVerificationField;
            }
            set
            {
                this.certificateRefsVerificationField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 1)]
        public string levelReached
        {
            get
            {
                return this.levelReachedField;
            }
            set
            {
                this.levelReachedField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 2)]
        public string revocationRefsVerification
        {
            get
            {
                return this.revocationRefsVerificationField;
            }
            set
            {
                this.revocationRefsVerificationField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("Microsoft.Tools.ServiceModel.Svcutil", "2.2.0-preview1.23462.5")]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.Xml.Serialization.XmlTypeAttribute(Namespace = "http://ws.dss.markt.ec.europa.eu/")]
    public partial class wsSignatureLevelBES
    {

        private byte[][] certificatesField;

        private wsSignatureInformation[] counterSignatureVerificationField;

        private string levelReachedField;

        private string signingCertRefVerificationField;

        private byte[] signingCertificateField;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("certificates", Form = System.Xml.Schema.XmlSchemaForm.Unqualified, DataType = "base64Binary", IsNullable = true, Order = 0)]
        public byte[][] certificates
        {
            get
            {
                return this.certificatesField;
            }
            set
            {
                this.certificatesField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("counterSignatureVerification", Form = System.Xml.Schema.XmlSchemaForm.Unqualified, IsNullable = true, Order = 1)]
        public wsSignatureInformation[] counterSignatureVerification
        {
            get
            {
                return this.counterSignatureVerificationField;
            }
            set
            {
                this.counterSignatureVerificationField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 2)]
        public string levelReached
        {
            get
            {
                return this.levelReachedField;
            }
            set
            {
                this.levelReachedField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 3)]
        public string signingCertRefVerification
        {
            get
            {
                return this.signingCertRefVerificationField;
            }
            set
            {
                this.signingCertRefVerificationField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, DataType = "base64Binary", Order = 4)]
        public byte[] signingCertificate
        {
            get
            {
                return this.signingCertificateField;
            }
            set
            {
                this.signingCertificateField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("Microsoft.Tools.ServiceModel.Svcutil", "2.2.0-preview1.23462.5")]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.Xml.Serialization.XmlTypeAttribute(Namespace = "http://ws.dss.markt.ec.europa.eu/")]
    public partial class wsSignatureInformation
    {

        private wsCertPathRevocationAnalysis certPathRevocationAnalysisField;

        private string finalConclusionField;

        private wsqcStatementInformation qcStatementInformationField;

        private wsQualificationsVerification qualificationsVerificationField;

        private wsSignatureLevelAnalysis signatureLevelAnalysisField;

        private wsSignatureVerification signatureVerificationField;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 0)]
        public wsCertPathRevocationAnalysis certPathRevocationAnalysis
        {
            get
            {
                return this.certPathRevocationAnalysisField;
            }
            set
            {
                this.certPathRevocationAnalysisField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 1)]
        public string finalConclusion
        {
            get
            {
                return this.finalConclusionField;
            }
            set
            {
                this.finalConclusionField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 2)]
        public wsqcStatementInformation qcStatementInformation
        {
            get
            {
                return this.qcStatementInformationField;
            }
            set
            {
                this.qcStatementInformationField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 3)]
        public wsQualificationsVerification qualificationsVerification
        {
            get
            {
                return this.qualificationsVerificationField;
            }
            set
            {
                this.qualificationsVerificationField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 4)]
        public wsSignatureLevelAnalysis signatureLevelAnalysis
        {
            get
            {
                return this.signatureLevelAnalysisField;
            }
            set
            {
                this.signatureLevelAnalysisField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 5)]
        public wsSignatureVerification signatureVerification
        {
            get
            {
                return this.signatureVerificationField;
            }
            set
            {
                this.signatureVerificationField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("Microsoft.Tools.ServiceModel.Svcutil", "2.2.0-preview1.23462.5")]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.Xml.Serialization.XmlTypeAttribute(Namespace = "http://ws.dss.markt.ec.europa.eu/")]
    public partial class wsCertPathRevocationAnalysis
    {

        private wsCertificateVerification[] certificatePathVerificationField;

        private string summaryField;

        private wsTrustedListInformation trustedListInformationField;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("certificatePathVerification", Form = System.Xml.Schema.XmlSchemaForm.Unqualified, IsNullable = true, Order = 0)]
        public wsCertificateVerification[] certificatePathVerification
        {
            get
            {
                return this.certificatePathVerificationField;
            }
            set
            {
                this.certificatePathVerificationField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 1)]
        public string summary
        {
            get
            {
                return this.summaryField;
            }
            set
            {
                this.summaryField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 2)]
        public wsTrustedListInformation trustedListInformation
        {
            get
            {
                return this.trustedListInformationField;
            }
            set
            {
                this.trustedListInformationField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("Microsoft.Tools.ServiceModel.Svcutil", "2.2.0-preview1.23462.5")]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.Xml.Serialization.XmlTypeAttribute(Namespace = "http://ws.dss.markt.ec.europa.eu/")]
    public partial class wsCertificateVerification
    {

        private byte[] certificateField;

        private wsRevocationVerificationResult certificateStatusField;

        private wsSignatureVerification signatureVerificationField;

        private string validityPeriodVerificationField;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, DataType = "base64Binary", Order = 0)]
        public byte[] certificate
        {
            get
            {
                return this.certificateField;
            }
            set
            {
                this.certificateField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 1)]
        public wsRevocationVerificationResult certificateStatus
        {
            get
            {
                return this.certificateStatusField;
            }
            set
            {
                this.certificateStatusField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 2)]
        public wsSignatureVerification signatureVerification
        {
            get
            {
                return this.signatureVerificationField;
            }
            set
            {
                this.signatureVerificationField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 3)]
        public string validityPeriodVerification
        {
            get
            {
                return this.validityPeriodVerificationField;
            }
            set
            {
                this.validityPeriodVerificationField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("Microsoft.Tools.ServiceModel.Svcutil", "2.2.0-preview1.23462.5")]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.Xml.Serialization.XmlTypeAttribute(Namespace = "http://ws.dss.markt.ec.europa.eu/")]
    public partial class wsRevocationVerificationResult
    {

        private string issuerField;

        private System.DateTime issuingTimeField;

        private bool issuingTimeFieldSpecified;

        private System.DateTime revocationDateField;

        private bool revocationDateFieldSpecified;

        private string statusField;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 0)]
        public string issuer
        {
            get
            {
                return this.issuerField;
            }
            set
            {
                this.issuerField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 1)]
        public System.DateTime issuingTime
        {
            get
            {
                return this.issuingTimeField;
            }
            set
            {
                this.issuingTimeField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool issuingTimeSpecified
        {
            get
            {
                return this.issuingTimeFieldSpecified;
            }
            set
            {
                this.issuingTimeFieldSpecified = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 2)]
        public System.DateTime revocationDate
        {
            get
            {
                return this.revocationDateField;
            }
            set
            {
                this.revocationDateField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool revocationDateSpecified
        {
            get
            {
                return this.revocationDateFieldSpecified;
            }
            set
            {
                this.revocationDateFieldSpecified = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 3)]
        public string status
        {
            get
            {
                return this.statusField;
            }
            set
            {
                this.statusField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("Microsoft.Tools.ServiceModel.Svcutil", "2.2.0-preview1.23462.5")]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.Xml.Serialization.XmlTypeAttribute(Namespace = "http://ws.dss.markt.ec.europa.eu/")]
    public partial class wsSignatureVerification
    {

        private string digestAlgorithmField;

        private System.DateTime referenceTimeField;

        private bool referenceTimeFieldSpecified;

        private string signatureAlgorithmField;

        private string signatureVerificationResultField;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 0)]
        public string digestAlgorithm
        {
            get
            {
                return this.digestAlgorithmField;
            }
            set
            {
                this.digestAlgorithmField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 1)]
        public System.DateTime referenceTime
        {
            get
            {
                return this.referenceTimeField;
            }
            set
            {
                this.referenceTimeField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool referenceTimeSpecified
        {
            get
            {
                return this.referenceTimeFieldSpecified;
            }
            set
            {
                this.referenceTimeFieldSpecified = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 2)]
        public string signatureAlgorithm
        {
            get
            {
                return this.signatureAlgorithmField;
            }
            set
            {
                this.signatureAlgorithmField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 3)]
        public string signatureVerificationResult
        {
            get
            {
                return this.signatureVerificationResultField;
            }
            set
            {
                this.signatureVerificationResultField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("Microsoft.Tools.ServiceModel.Svcutil", "2.2.0-preview1.23462.5")]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.Xml.Serialization.XmlTypeAttribute(Namespace = "http://ws.dss.markt.ec.europa.eu/")]
    public partial class wsTrustedListInformation
    {

        private string currentStatusField;

        private System.DateTime currentStatusStartingDateField;

        private bool currentStatusStartingDateFieldSpecified;

        private string serviceNameField;

        private string serviceTypeField;

        private bool serviceWasFoundField;

        private string statusAtReferenceTimeField;

        private System.DateTime statusStartingDateAtReferenceTimeField;

        private bool statusStartingDateAtReferenceTimeFieldSpecified;

        private string tspElectronicAddressField;

        private string tspNameField;

        private string tspPostalAddressField;

        private string tspTradeNameField;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 0)]
        public string currentStatus
        {
            get
            {
                return this.currentStatusField;
            }
            set
            {
                this.currentStatusField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 1)]
        public System.DateTime currentStatusStartingDate
        {
            get
            {
                return this.currentStatusStartingDateField;
            }
            set
            {
                this.currentStatusStartingDateField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool currentStatusStartingDateSpecified
        {
            get
            {
                return this.currentStatusStartingDateFieldSpecified;
            }
            set
            {
                this.currentStatusStartingDateFieldSpecified = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 2)]
        public string serviceName
        {
            get
            {
                return this.serviceNameField;
            }
            set
            {
                this.serviceNameField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 3)]
        public string serviceType
        {
            get
            {
                return this.serviceTypeField;
            }
            set
            {
                this.serviceTypeField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 4)]
        public bool serviceWasFound
        {
            get
            {
                return this.serviceWasFoundField;
            }
            set
            {
                this.serviceWasFoundField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 5)]
        public string statusAtReferenceTime
        {
            get
            {
                return this.statusAtReferenceTimeField;
            }
            set
            {
                this.statusAtReferenceTimeField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 6)]
        public System.DateTime statusStartingDateAtReferenceTime
        {
            get
            {
                return this.statusStartingDateAtReferenceTimeField;
            }
            set
            {
                this.statusStartingDateAtReferenceTimeField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlIgnoreAttribute()]
        public bool statusStartingDateAtReferenceTimeSpecified
        {
            get
            {
                return this.statusStartingDateAtReferenceTimeFieldSpecified;
            }
            set
            {
                this.statusStartingDateAtReferenceTimeFieldSpecified = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 7)]
        public string tspElectronicAddress
        {
            get
            {
                return this.tspElectronicAddressField;
            }
            set
            {
                this.tspElectronicAddressField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 8)]
        public string tspName
        {
            get
            {
                return this.tspNameField;
            }
            set
            {
                this.tspNameField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 9)]
        public string tspPostalAddress
        {
            get
            {
                return this.tspPostalAddressField;
            }
            set
            {
                this.tspPostalAddressField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 10)]
        public string tspTradeName
        {
            get
            {
                return this.tspTradeNameField;
            }
            set
            {
                this.tspTradeNameField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("Microsoft.Tools.ServiceModel.Svcutil", "2.2.0-preview1.23462.5")]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.Xml.Serialization.XmlTypeAttribute(Namespace = "http://ws.dss.markt.ec.europa.eu/")]
    public partial class wsqcStatementInformation
    {

        private string qcCompliancePresentField;

        private string qcLimitValueField;

        private string qcPPlusPresentField;

        private string qcPPresentField;

        private string qcRetentionPeriodField;

        private string qcSCCDPresentField;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 0)]
        public string qcCompliancePresent
        {
            get
            {
                return this.qcCompliancePresentField;
            }
            set
            {
                this.qcCompliancePresentField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 1)]
        public string qcLimitValue
        {
            get
            {
                return this.qcLimitValueField;
            }
            set
            {
                this.qcLimitValueField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 2)]
        public string qcPPlusPresent
        {
            get
            {
                return this.qcPPlusPresentField;
            }
            set
            {
                this.qcPPlusPresentField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 3)]
        public string qcPPresent
        {
            get
            {
                return this.qcPPresentField;
            }
            set
            {
                this.qcPPresentField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 4)]
        public string qcRetentionPeriod
        {
            get
            {
                return this.qcRetentionPeriodField;
            }
            set
            {
                this.qcRetentionPeriodField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 5)]
        public string qcSCCDPresent
        {
            get
            {
                return this.qcSCCDPresentField;
            }
            set
            {
                this.qcSCCDPresentField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("Microsoft.Tools.ServiceModel.Svcutil", "2.2.0-preview1.23462.5")]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.Xml.Serialization.XmlTypeAttribute(Namespace = "http://ws.dss.markt.ec.europa.eu/")]
    public partial class wsQualificationsVerification
    {

        private string qcForLegalPersonField;

        private string qcNoSSCDField;

        private string qcSSCDStatusAsInCertField;

        private string qcWithSSCDField;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 0)]
        public string qcForLegalPerson
        {
            get
            {
                return this.qcForLegalPersonField;
            }
            set
            {
                this.qcForLegalPersonField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 1)]
        public string qcNoSSCD
        {
            get
            {
                return this.qcNoSSCDField;
            }
            set
            {
                this.qcNoSSCDField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 2)]
        public string qcSSCDStatusAsInCert
        {
            get
            {
                return this.qcSSCDStatusAsInCertField;
            }
            set
            {
                this.qcSSCDStatusAsInCertField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 3)]
        public string qcWithSSCD
        {
            get
            {
                return this.qcWithSSCDField;
            }
            set
            {
                this.qcWithSSCDField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("Microsoft.Tools.ServiceModel.Svcutil", "2.2.0-preview1.23462.5")]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.Xml.Serialization.XmlTypeAttribute(Namespace = "http://ws.dss.markt.ec.europa.eu/")]
    public partial class wsSignatureLevelAnalysis
    {

        private wsSignatureLevelA levelAField;

        private wsSignatureLevelBES levelBESField;

        private wsSignatureLevelC levelCField;

        private wsSignatureLevelEPES levelEPESField;

        private wsSignatureLevelLTV levelLTVField;

        private wsSignatureLevelT levelTField;

        private wsSignatureLevelX levelXField;

        private wsSignatureLevelXL levelXLField;

        private string signatureFormatField;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 0)]
        public wsSignatureLevelA levelA
        {
            get
            {
                return this.levelAField;
            }
            set
            {
                this.levelAField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 1)]
        public wsSignatureLevelBES levelBES
        {
            get
            {
                return this.levelBESField;
            }
            set
            {
                this.levelBESField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 2)]
        public wsSignatureLevelC levelC
        {
            get
            {
                return this.levelCField;
            }
            set
            {
                this.levelCField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 3)]
        public wsSignatureLevelEPES levelEPES
        {
            get
            {
                return this.levelEPESField;
            }
            set
            {
                this.levelEPESField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 4)]
        public wsSignatureLevelLTV levelLTV
        {
            get
            {
                return this.levelLTVField;
            }
            set
            {
                this.levelLTVField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 5)]
        public wsSignatureLevelT levelT
        {
            get
            {
                return this.levelTField;
            }
            set
            {
                this.levelTField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 6)]
        public wsSignatureLevelX levelX
        {
            get
            {
                return this.levelXField;
            }
            set
            {
                this.levelXField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 7)]
        public wsSignatureLevelXL levelXL
        {
            get
            {
                return this.levelXLField;
            }
            set
            {
                this.levelXLField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 8)]
        public string signatureFormat
        {
            get
            {
                return this.signatureFormatField;
            }
            set
            {
                this.signatureFormatField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("Microsoft.Tools.ServiceModel.Svcutil", "2.2.0-preview1.23462.5")]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.Xml.Serialization.XmlTypeAttribute(Namespace = "http://ws.dss.markt.ec.europa.eu/")]
    public partial class wsSignatureLevelA
    {

        private wsTimestampVerificationResult[] archiveTimestampsVerificationField;

        private string levelReachedField;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("archiveTimestampsVerification", Form = System.Xml.Schema.XmlSchemaForm.Unqualified, IsNullable = true, Order = 0)]
        public wsTimestampVerificationResult[] archiveTimestampsVerification
        {
            get
            {
                return this.archiveTimestampsVerificationField;
            }
            set
            {
                this.archiveTimestampsVerificationField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 1)]
        public string levelReached
        {
            get
            {
                return this.levelReachedField;
            }
            set
            {
                this.levelReachedField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("Microsoft.Tools.ServiceModel.Svcutil", "2.2.0-preview1.23462.5")]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.Xml.Serialization.XmlTypeAttribute(Namespace = "http://ws.dss.markt.ec.europa.eu/")]
    public partial class wsValidationReport
    {

        private wsDocument contentField;

        private wsTimestampVerificationResult[] detachedTsVerificationResultField;

        private wsSignatureInformation[] signatureInformationListField;

        private wsTimeInformation timeInformationField;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 0)]
        public wsDocument content
        {
            get
            {
                return this.contentField;
            }
            set
            {
                this.contentField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("detachedTsVerificationResult", Form = System.Xml.Schema.XmlSchemaForm.Unqualified, IsNullable = true, Order = 1)]
        public wsTimestampVerificationResult[] detachedTsVerificationResult
        {
            get
            {
                return this.detachedTsVerificationResultField;
            }
            set
            {
                this.detachedTsVerificationResultField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("signatureInformationList", Form = System.Xml.Schema.XmlSchemaForm.Unqualified, IsNullable = true, Order = 2)]
        public wsSignatureInformation[] signatureInformationList
        {
            get
            {
                return this.signatureInformationListField;
            }
            set
            {
                this.signatureInformationListField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 3)]
        public wsTimeInformation timeInformation
        {
            get
            {
                return this.timeInformationField;
            }
            set
            {
                this.timeInformationField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("Microsoft.Tools.ServiceModel.Svcutil", "2.2.0-preview1.23462.5")]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.Xml.Serialization.XmlTypeAttribute(Namespace = "http://ws.dss.markt.ec.europa.eu/")]
    public partial class wsDocument
    {

        private byte[] binaryField;

        private string nameField;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, DataType = "base64Binary", Order = 0)]
        public byte[] binary
        {
            get
            {
                return this.binaryField;
            }
            set
            {
                this.binaryField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 1)]
        public string name
        {
            get
            {
                return this.nameField;
            }
            set
            {
                this.nameField = value;
            }
        }
    }

    [System.CodeDom.Compiler.GeneratedCodeAttribute("Microsoft.Tools.ServiceModel.Svcutil", "2.2.0-preview1.23462.5")]
    [System.ServiceModel.ServiceContractAttribute(Namespace = "http://ws.dss.markt.ec.europa.eu/", ConfigurationName = "ValidationService")]
    public interface ValidationService
    {

        // CODEGEN: Con il parametro 'response' sono richieste informazioni sullo schema aggiuntive che non possono essere acquisite usando la modalità parametro. L'attributo specifico è 'Microsoft.Xml.Serialization.XmlElementAttribute'.
        [System.ServiceModel.OperationContractAttribute(Action = "", ReplyAction = "*")]
        [System.ServiceModel.FaultContractAttribute(typeof(IOException), Action = "", Name = "IOException")]
        [System.ServiceModel.XmlSerializerFormatAttribute(SupportFaults = true)]
        [return: System.ServiceModel.MessageParameterAttribute(Name = "response")]
        validateDocumentResponse validateDocument(validateDocument request);

        [System.ServiceModel.OperationContractAttribute(Action = "", ReplyAction = "*")]
        System.Threading.Tasks.Task<validateDocumentResponse> validateDocumentAsync(validateDocument request);
    }

    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.CodeDom.Compiler.GeneratedCodeAttribute("Microsoft.Tools.ServiceModel.Svcutil", "2.2.0-preview1.23462.5")]
    [System.ComponentModel.EditorBrowsableAttribute(System.ComponentModel.EditorBrowsableState.Advanced)]
    [System.ServiceModel.MessageContractAttribute(WrapperName = "validateDocument", WrapperNamespace = "http://ws.dss.markt.ec.europa.eu/", IsWrapped = true)]
    public partial class validateDocument
    {

        [System.ServiceModel.MessageBodyMemberAttribute(Namespace = "http://ws.dss.markt.ec.europa.eu/", Order = 0)]
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public wsDocument document;

        [System.ServiceModel.MessageBodyMemberAttribute(Namespace = "http://ws.dss.markt.ec.europa.eu/", Order = 1)]
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public wsDocument originalDocument;

        [System.ServiceModel.MessageBodyMemberAttribute(Namespace = "http://ws.dss.markt.ec.europa.eu/", Order = 2)]
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public bool giveBackContent;

        public validateDocument()
        {
        }

        public validateDocument(wsDocument document, wsDocument originalDocument, bool giveBackContent)
        {
            this.document = document;
            this.originalDocument = originalDocument;
            this.giveBackContent = giveBackContent;
        }
    }

    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.CodeDom.Compiler.GeneratedCodeAttribute("Microsoft.Tools.ServiceModel.Svcutil", "2.2.0-preview1.23462.5")]
    [System.ComponentModel.EditorBrowsableAttribute(System.ComponentModel.EditorBrowsableState.Advanced)]
    [System.ServiceModel.MessageContractAttribute(WrapperName = "validateDocumentResponse", WrapperNamespace = "http://ws.dss.markt.ec.europa.eu/", IsWrapped = true)]
    public partial class validateDocumentResponse
    {

        [System.ServiceModel.MessageBodyMemberAttribute(Namespace = "http://ws.dss.markt.ec.europa.eu/", Order = 0)]
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public wsValidationReport response;

        public validateDocumentResponse()
        {
        }

        public validateDocumentResponse(wsValidationReport response)
        {
            this.response = response;
        }
    }

    [System.CodeDom.Compiler.GeneratedCodeAttribute("Microsoft.Tools.ServiceModel.Svcutil", "2.2.0-preview1.23462.5")]
    public interface ValidationServiceChannel : ValidationService, System.ServiceModel.IClientChannel
    {
    }

    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.CodeDom.Compiler.GeneratedCodeAttribute("Microsoft.Tools.ServiceModel.Svcutil", "2.2.0-preview1.23462.5")]
    public partial class ValidationServiceClient : System.ServiceModel.ClientBase<ValidationService>, ValidationService
    {

        /// <summary>
        /// Implementare questo metodo parziale per configurare l'endpoint servizio.
        /// </summary>
        /// <param name="serviceEndpoint">Endpoint da configurare</param>
        /// <param name="clientCredentials">Credenziali del client</param>
        static partial void ConfigureEndpoint(System.ServiceModel.Description.ServiceEndpoint serviceEndpoint, System.ServiceModel.Description.ClientCredentials clientCredentials);

        public ValidationServiceClient() :
                base(ValidationServiceClient.GetDefaultBinding(), ValidationServiceClient.GetDefaultEndpointAddress())
        {
            this.Endpoint.Name = EndpointConfiguration.ValidationServiceImplPort.ToString();
            ConfigureEndpoint(this.Endpoint, this.ClientCredentials);
        }

        public ValidationServiceClient(EndpointConfiguration endpointConfiguration) :
                base(ValidationServiceClient.GetBindingForEndpoint(endpointConfiguration), ValidationServiceClient.GetEndpointAddress(endpointConfiguration))
        {
            this.Endpoint.Name = endpointConfiguration.ToString();
            ConfigureEndpoint(this.Endpoint, this.ClientCredentials);
        }

        public ValidationServiceClient(EndpointConfiguration endpointConfiguration, string remoteAddress) :
                base(ValidationServiceClient.GetBindingForEndpoint(endpointConfiguration), new System.ServiceModel.EndpointAddress(remoteAddress))
        {
            this.Endpoint.Name = endpointConfiguration.ToString();
            ConfigureEndpoint(this.Endpoint, this.ClientCredentials);
        }

        public ValidationServiceClient(EndpointConfiguration endpointConfiguration, System.ServiceModel.EndpointAddress remoteAddress) :
                base(ValidationServiceClient.GetBindingForEndpoint(endpointConfiguration), remoteAddress)
        {
            this.Endpoint.Name = endpointConfiguration.ToString();
            ConfigureEndpoint(this.Endpoint, this.ClientCredentials);
        }

        public ValidationServiceClient(System.ServiceModel.Channels.Binding binding, System.ServiceModel.EndpointAddress remoteAddress) :
                base(binding, remoteAddress)
        {
        }

        [System.ComponentModel.EditorBrowsableAttribute(System.ComponentModel.EditorBrowsableState.Advanced)]
        validateDocumentResponse ValidationService.validateDocument(validateDocument request)
        {
            return base.Channel.validateDocument(request);
        }

        public wsValidationReport validateDocument(wsDocument document, wsDocument originalDocument, bool giveBackContent)
        {
            validateDocument inValue = new validateDocument();
            inValue.document = document;
            inValue.originalDocument = originalDocument;
            inValue.giveBackContent = giveBackContent;
            validateDocumentResponse retVal = ((ValidationService)(this)).validateDocument(inValue);
            return retVal.response;
        }

        [System.ComponentModel.EditorBrowsableAttribute(System.ComponentModel.EditorBrowsableState.Advanced)]
        System.Threading.Tasks.Task<validateDocumentResponse> ValidationService.validateDocumentAsync(validateDocument request)
        {
            return base.Channel.validateDocumentAsync(request);
        }

        public System.Threading.Tasks.Task<validateDocumentResponse> validateDocumentAsync(wsDocument document, wsDocument originalDocument, bool giveBackContent)
        {
            validateDocument inValue = new validateDocument();
            inValue.document = document;
            inValue.originalDocument = originalDocument;
            inValue.giveBackContent = giveBackContent;
            return ((ValidationService)(this)).validateDocumentAsync(inValue);
        }

        public virtual System.Threading.Tasks.Task OpenAsync()
        {
            return System.Threading.Tasks.Task.Factory.FromAsync(((System.ServiceModel.ICommunicationObject)(this)).BeginOpen(null, null), new System.Action<System.IAsyncResult>(((System.ServiceModel.ICommunicationObject)(this)).EndOpen));
        }

        public virtual System.Threading.Tasks.Task CloseAsync()
        {
            return System.Threading.Tasks.Task.Factory.FromAsync(((System.ServiceModel.ICommunicationObject)(this)).BeginClose(null, null), new System.Action<System.IAsyncResult>(((System.ServiceModel.ICommunicationObject)(this)).EndClose));
        }

        private static System.ServiceModel.Channels.Binding GetBindingForEndpoint(EndpointConfiguration endpointConfiguration)
        {
            if ((endpointConfiguration == EndpointConfiguration.ValidationServiceImplPort))
            {
                System.ServiceModel.BasicHttpBinding result = new System.ServiceModel.BasicHttpBinding();
                result.MaxBufferSize = int.MaxValue;
                result.ReaderQuotas = System.Xml.XmlDictionaryReaderQuotas.Max;
                result.MaxReceivedMessageSize = int.MaxValue;
                result.AllowCookies = true;
                result.Security.Mode = System.ServiceModel.BasicHttpSecurityMode.Transport;
                return result;
            }
            throw new System.InvalidOperationException(string.Format("L\'endpoint denominato \'{0}\' non è stato trovato.", endpointConfiguration));
        }

        private static System.ServiceModel.EndpointAddress GetEndpointAddress(EndpointConfiguration endpointConfiguration)
        {
            if ((endpointConfiguration == EndpointConfiguration.ValidationServiceImplPort))
            {
                return new System.ServiceModel.EndpointAddress("https://devel3.vbg.community/dss-webapp/wservice/validationService");
            }
            throw new System.InvalidOperationException(string.Format("L\'endpoint denominato \'{0}\' non è stato trovato.", endpointConfiguration));
        }

        private static System.ServiceModel.Channels.Binding GetDefaultBinding()
        {
            return ValidationServiceClient.GetBindingForEndpoint(EndpointConfiguration.ValidationServiceImplPort);
        }

        private static System.ServiceModel.EndpointAddress GetDefaultEndpointAddress()
        {
            return ValidationServiceClient.GetEndpointAddress(EndpointConfiguration.ValidationServiceImplPort);
        }

        public enum EndpointConfiguration
        {

            ValidationServiceImplPort,
        }
    }
}
