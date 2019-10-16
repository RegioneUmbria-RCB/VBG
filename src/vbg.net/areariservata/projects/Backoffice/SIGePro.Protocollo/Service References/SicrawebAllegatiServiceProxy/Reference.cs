﻿//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by a tool.
//     Runtime Version:4.0.30319.1022
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace Init.SIGePro.Protocollo.SicrawebAllegatiServiceProxy {
    
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    [System.ServiceModel.ServiceContractAttribute(Namespace="urn:RepWSSGateway", ConfigurationName="SicrawebAllegatiServiceProxy.RepWSSGateway")]
    public interface RepWSSGateway {
        
        [System.ServiceModel.OperationContractAttribute(Action="", ReplyAction="*")]
        [System.ServiceModel.XmlSerializerFormatAttribute(Style=System.ServiceModel.OperationFormatStyle.Rpc, SupportFaults=true, Use=System.ServiceModel.OperationFormatUse.Encoded)]
        [return: System.ServiceModel.MessageParameterAttribute(Name="docInsertReturn")]
        string docInsert(string logonCredentials, string libraryName, string documentBase64, string documentInfo);
        
        [System.ServiceModel.OperationContractAttribute(Action="", ReplyAction="*")]
        [System.ServiceModel.XmlSerializerFormatAttribute(Style=System.ServiceModel.OperationFormatStyle.Rpc, SupportFaults=true, Use=System.ServiceModel.OperationFormatUse.Encoded)]
        [return: System.ServiceModel.MessageParameterAttribute(Name="docExtractReturn")]
        string docExtract(string logonCredentials, string documentUID);
        
        [System.ServiceModel.OperationContractAttribute(Action="", ReplyAction="*")]
        [System.ServiceModel.XmlSerializerFormatAttribute(Style=System.ServiceModel.OperationFormatStyle.Rpc, SupportFaults=true, Use=System.ServiceModel.OperationFormatUse.Encoded)]
        [return: System.ServiceModel.MessageParameterAttribute(Name="docCheckOutReturn")]
        string docCheckOut(string logonCredentials, string documentUID);
        
        [System.ServiceModel.OperationContractAttribute(Action="", ReplyAction="*")]
        [System.ServiceModel.XmlSerializerFormatAttribute(Style=System.ServiceModel.OperationFormatStyle.Rpc, SupportFaults=true, Use=System.ServiceModel.OperationFormatUse.Encoded)]
        void docUndoCheckOut(string logonCredentials, string documentUID);
        
        [System.ServiceModel.OperationContractAttribute(Action="", ReplyAction="*")]
        [System.ServiceModel.XmlSerializerFormatAttribute(Style=System.ServiceModel.OperationFormatStyle.Rpc, SupportFaults=true, Use=System.ServiceModel.OperationFormatUse.Encoded)]
        void docCheckIn(string logonCredentials, string documentUID, string documentBase64);
        
        [System.ServiceModel.OperationContractAttribute(Action="", ReplyAction="*")]
        [System.ServiceModel.XmlSerializerFormatAttribute(Style=System.ServiceModel.OperationFormatStyle.Rpc, SupportFaults=true, Use=System.ServiceModel.OperationFormatUse.Encoded)]
        void docDelete(string logonCredentials, string documentUID, string deletionType);
        
        [System.ServiceModel.OperationContractAttribute(Action="", ReplyAction="*")]
        [System.ServiceModel.XmlSerializerFormatAttribute(Style=System.ServiceModel.OperationFormatStyle.Rpc, SupportFaults=true, Use=System.ServiceModel.OperationFormatUse.Encoded)]
        [return: System.ServiceModel.MessageParameterAttribute(Name="docGetInfoReturn")]
        string docGetInfo(string logonCredentials, string documentUID);
        
        [System.ServiceModel.OperationContractAttribute(Action="", ReplyAction="*")]
        [System.ServiceModel.XmlSerializerFormatAttribute(Style=System.ServiceModel.OperationFormatStyle.Rpc, SupportFaults=true, Use=System.ServiceModel.OperationFormatUse.Encoded)]
        [return: System.ServiceModel.MessageParameterAttribute(Name="docSearchReturn")]
        string docSearch(string logonCredentials, string libraryName, string xmlQuery);
        
        [System.ServiceModel.OperationContractAttribute(Action="", ReplyAction="*")]
        [System.ServiceModel.XmlSerializerFormatAttribute(Style=System.ServiceModel.OperationFormatStyle.Rpc, SupportFaults=true, Use=System.ServiceModel.OperationFormatUse.Encoded)]
        [return: System.ServiceModel.MessageParameterAttribute(Name="docBindReturn")]
        string docBind(string logonCredentials, string document);
        
        [System.ServiceModel.OperationContractAttribute(Action="", ReplyAction="*")]
        [System.ServiceModel.XmlSerializerFormatAttribute(Style=System.ServiceModel.OperationFormatStyle.Rpc, SupportFaults=true, Use=System.ServiceModel.OperationFormatUse.Encoded)]
        [return: System.ServiceModel.MessageParameterAttribute(Name="docGetMetadataReturn")]
        string docGetMetadata(string logonCredentials, string documentUID);
        
        [System.ServiceModel.OperationContractAttribute(Action="", ReplyAction="*")]
        [System.ServiceModel.XmlSerializerFormatAttribute(Style=System.ServiceModel.OperationFormatStyle.Rpc, SupportFaults=true, Use=System.ServiceModel.OperationFormatUse.Encoded)]
        void docSetMetadata(string logonCredentials, string documentUID, string metaData);
        
        [System.ServiceModel.OperationContractAttribute(Action="", ReplyAction="*")]
        [System.ServiceModel.XmlSerializerFormatAttribute(Style=System.ServiceModel.OperationFormatStyle.Rpc, SupportFaults=true, Use=System.ServiceModel.OperationFormatUse.Encoded)]
        [return: System.ServiceModel.MessageParameterAttribute(Name="getAvailableLibrariesReturn")]
        string getAvailableLibraries(string logonCredentials);
        
        [System.ServiceModel.OperationContractAttribute(Action="", ReplyAction="*")]
        [System.ServiceModel.XmlSerializerFormatAttribute(Style=System.ServiceModel.OperationFormatStyle.Rpc, SupportFaults=true, Use=System.ServiceModel.OperationFormatUse.Encoded)]
        [return: System.ServiceModel.MessageParameterAttribute(Name="getDefaultLibraryReturn")]
        string getDefaultLibrary(string logonCredentials);
        
        [System.ServiceModel.OperationContractAttribute(Action="", ReplyAction="*")]
        [System.ServiceModel.XmlSerializerFormatAttribute(Style=System.ServiceModel.OperationFormatStyle.Rpc, SupportFaults=true, Use=System.ServiceModel.OperationFormatUse.Encoded)]
        [return: System.ServiceModel.MessageParameterAttribute(Name="getAvailableDocumentClassesReturn")]
        string getAvailableDocumentClasses(string logonCredentials);
        
        [System.ServiceModel.OperationContractAttribute(Action="", ReplyAction="*")]
        [System.ServiceModel.XmlSerializerFormatAttribute(Style=System.ServiceModel.OperationFormatStyle.Rpc, SupportFaults=true, Use=System.ServiceModel.OperationFormatUse.Encoded)]
        [return: System.ServiceModel.MessageParameterAttribute(Name="getDocumentClassReturn")]
        string getDocumentClass(string logonCredentials, string className);
        
        [System.ServiceModel.OperationContractAttribute(Action="", ReplyAction="*")]
        [System.ServiceModel.XmlSerializerFormatAttribute(Style=System.ServiceModel.OperationFormatStyle.Rpc, SupportFaults=true, Use=System.ServiceModel.OperationFormatUse.Encoded)]
        [return: System.ServiceModel.MessageParameterAttribute(Name="docGetACLReturn")]
        string docGetACL(string logonCredentials, string documentUID);
        
        [System.ServiceModel.OperationContractAttribute(Action="", ReplyAction="*")]
        [System.ServiceModel.XmlSerializerFormatAttribute(Style=System.ServiceModel.OperationFormatStyle.Rpc, SupportFaults=true, Use=System.ServiceModel.OperationFormatUse.Encoded)]
        void docSetACL(string logonCredentials, string documentUID, string aclDescriptor);
        
        [System.ServiceModel.OperationContractAttribute(Action="", ReplyAction="*")]
        [System.ServiceModel.XmlSerializerFormatAttribute(Style=System.ServiceModel.OperationFormatStyle.Rpc, SupportFaults=true, Use=System.ServiceModel.OperationFormatUse.Encoded)]
        [return: System.ServiceModel.MessageParameterAttribute(Name="docGetRelatedReturn")]
        string docGetRelated(string logonCredentials, string documentUID);
        
        [System.ServiceModel.OperationContractAttribute(Action="", ReplyAction="*")]
        [System.ServiceModel.XmlSerializerFormatAttribute(Style=System.ServiceModel.OperationFormatStyle.Rpc, SupportFaults=true, Use=System.ServiceModel.OperationFormatUse.Encoded)]
        void docSetRelated(string logonCredentials, string documentUID, string relatedDescriptor);
        
        [System.ServiceModel.OperationContractAttribute(Action="", ReplyAction="*")]
        [System.ServiceModel.XmlSerializerFormatAttribute(Style=System.ServiceModel.OperationFormatStyle.Rpc, SupportFaults=true, Use=System.ServiceModel.OperationFormatUse.Encoded)]
        [return: System.ServiceModel.MessageParameterAttribute(Name="fileTransferDownloadBeginReturn")]
        Init.SIGePro.Protocollo.SicrawebAllegatiServiceProxy.RepWSSGateway_FileTransferDownloadBeginResult fileTransferDownloadBegin(string logonCredentials, Init.SIGePro.Protocollo.SicrawebAllegatiServiceProxy.RepWSSGateway_FileTransferDownloadBeginParams @params);
        
        [System.ServiceModel.OperationContractAttribute(Action="", ReplyAction="*")]
        [System.ServiceModel.XmlSerializerFormatAttribute(Style=System.ServiceModel.OperationFormatStyle.Rpc, SupportFaults=true, Use=System.ServiceModel.OperationFormatUse.Encoded)]
        [return: System.ServiceModel.MessageParameterAttribute(Name="fileTransferDownloadBlockReturn")]
        Init.SIGePro.Protocollo.SicrawebAllegatiServiceProxy.RepWSSGateway_FileTransferDownloadBlockResult fileTransferDownloadBlock(string logonCredentials, Init.SIGePro.Protocollo.SicrawebAllegatiServiceProxy.RepWSSGateway_FileTransferDownloadBlockParams @params);
        
        [System.ServiceModel.OperationContractAttribute(Action="", ReplyAction="*")]
        [System.ServiceModel.XmlSerializerFormatAttribute(Style=System.ServiceModel.OperationFormatStyle.Rpc, SupportFaults=true, Use=System.ServiceModel.OperationFormatUse.Encoded)]
        [return: System.ServiceModel.MessageParameterAttribute(Name="fileTransferDownloadEndReturn")]
        Init.SIGePro.Protocollo.SicrawebAllegatiServiceProxy.RepWSSGateway_FileTransferDownloadEndResult fileTransferDownloadEnd(string logonCredentials, Init.SIGePro.Protocollo.SicrawebAllegatiServiceProxy.RepWSSGateway_FileTransferDownloadEndParams @params);
    }
    
    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.Xml", "4.0.30319.1015")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.SoapTypeAttribute(Namespace="http://webservices.repository.library.saga.it")]
    public partial class RepWSSGateway_FileTransferDownloadBeginParams : object, System.ComponentModel.INotifyPropertyChanged {
        
        private bool blockZipEnabledField;
        
        private string serverFileTicketField;
        
        /// <remarks/>
        public bool blockZipEnabled {
            get {
                return this.blockZipEnabledField;
            }
            set {
                this.blockZipEnabledField = value;
                this.RaisePropertyChanged("blockZipEnabled");
            }
        }
        
        /// <remarks/>
        public string serverFileTicket {
            get {
                return this.serverFileTicketField;
            }
            set {
                this.serverFileTicketField = value;
                this.RaisePropertyChanged("serverFileTicket");
            }
        }
        
        public event System.ComponentModel.PropertyChangedEventHandler PropertyChanged;
        
        protected void RaisePropertyChanged(string propertyName) {
            System.ComponentModel.PropertyChangedEventHandler propertyChanged = this.PropertyChanged;
            if ((propertyChanged != null)) {
                propertyChanged(this, new System.ComponentModel.PropertyChangedEventArgs(propertyName));
            }
        }
    }
    
    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.Xml", "4.0.30319.1015")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.SoapTypeAttribute(Namespace="http://webservices.repository.library.saga.it")]
    public partial class RepWSSGateway_FileTransferDownloadEndResult : object, System.ComponentModel.INotifyPropertyChanged {
        
        public event System.ComponentModel.PropertyChangedEventHandler PropertyChanged;
        
        protected void RaisePropertyChanged(string propertyName) {
            System.ComponentModel.PropertyChangedEventHandler propertyChanged = this.PropertyChanged;
            if ((propertyChanged != null)) {
                propertyChanged(this, new System.ComponentModel.PropertyChangedEventArgs(propertyName));
            }
        }
    }
    
    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.Xml", "4.0.30319.1015")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.SoapTypeAttribute(Namespace="http://webservices.repository.library.saga.it")]
    public partial class RepWSSGateway_FileTransferDownloadEndParams : object, System.ComponentModel.INotifyPropertyChanged {
        
        private string fileTransferSessionField;
        
        /// <remarks/>
        public string fileTransferSession {
            get {
                return this.fileTransferSessionField;
            }
            set {
                this.fileTransferSessionField = value;
                this.RaisePropertyChanged("fileTransferSession");
            }
        }
        
        public event System.ComponentModel.PropertyChangedEventHandler PropertyChanged;
        
        protected void RaisePropertyChanged(string propertyName) {
            System.ComponentModel.PropertyChangedEventHandler propertyChanged = this.PropertyChanged;
            if ((propertyChanged != null)) {
                propertyChanged(this, new System.ComponentModel.PropertyChangedEventArgs(propertyName));
            }
        }
    }
    
    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.Xml", "4.0.30319.1015")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.SoapTypeAttribute(Namespace="http://webservices.repository.library.saga.it")]
    public partial class RepWSSGateway_FileTransferDownloadBlockResult : object, System.ComponentModel.INotifyPropertyChanged {
        
        private string base64Field;
        
        /// <remarks/>
        public string base64 {
            get {
                return this.base64Field;
            }
            set {
                this.base64Field = value;
                this.RaisePropertyChanged("base64");
            }
        }
        
        public event System.ComponentModel.PropertyChangedEventHandler PropertyChanged;
        
        protected void RaisePropertyChanged(string propertyName) {
            System.ComponentModel.PropertyChangedEventHandler propertyChanged = this.PropertyChanged;
            if ((propertyChanged != null)) {
                propertyChanged(this, new System.ComponentModel.PropertyChangedEventArgs(propertyName));
            }
        }
    }
    
    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.Xml", "4.0.30319.1015")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.SoapTypeAttribute(Namespace="http://webservices.repository.library.saga.it")]
    public partial class RepWSSGateway_FileTransferDownloadBlockParams : object, System.ComponentModel.INotifyPropertyChanged {
        
        private string fileTransferSessionField;
        
        private int pageIndexField;
        
        private int pageSizeField;
        
        /// <remarks/>
        public string fileTransferSession {
            get {
                return this.fileTransferSessionField;
            }
            set {
                this.fileTransferSessionField = value;
                this.RaisePropertyChanged("fileTransferSession");
            }
        }
        
        /// <remarks/>
        public int pageIndex {
            get {
                return this.pageIndexField;
            }
            set {
                this.pageIndexField = value;
                this.RaisePropertyChanged("pageIndex");
            }
        }
        
        /// <remarks/>
        public int pageSize {
            get {
                return this.pageSizeField;
            }
            set {
                this.pageSizeField = value;
                this.RaisePropertyChanged("pageSize");
            }
        }
        
        public event System.ComponentModel.PropertyChangedEventHandler PropertyChanged;
        
        protected void RaisePropertyChanged(string propertyName) {
            System.ComponentModel.PropertyChangedEventHandler propertyChanged = this.PropertyChanged;
            if ((propertyChanged != null)) {
                propertyChanged(this, new System.ComponentModel.PropertyChangedEventArgs(propertyName));
            }
        }
    }
    
    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.Xml", "4.0.30319.1015")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.SoapTypeAttribute(Namespace="http://webservices.repository.library.saga.it")]
    public partial class RepWSSGateway_FileTransferDownloadBeginResult : object, System.ComponentModel.INotifyPropertyChanged {
        
        private long fileSizeField;
        
        private string fileTransferSessionField;
        
        /// <remarks/>
        public long fileSize {
            get {
                return this.fileSizeField;
            }
            set {
                this.fileSizeField = value;
                this.RaisePropertyChanged("fileSize");
            }
        }
        
        /// <remarks/>
        public string fileTransferSession {
            get {
                return this.fileTransferSessionField;
            }
            set {
                this.fileTransferSessionField = value;
                this.RaisePropertyChanged("fileTransferSession");
            }
        }
        
        public event System.ComponentModel.PropertyChangedEventHandler PropertyChanged;
        
        protected void RaisePropertyChanged(string propertyName) {
            System.ComponentModel.PropertyChangedEventHandler propertyChanged = this.PropertyChanged;
            if ((propertyChanged != null)) {
                propertyChanged(this, new System.ComponentModel.PropertyChangedEventArgs(propertyName));
            }
        }
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    public interface RepWSSGatewayChannel : Init.SIGePro.Protocollo.SicrawebAllegatiServiceProxy.RepWSSGateway, System.ServiceModel.IClientChannel {
    }
    
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    public partial class RepWSSGatewayClient : System.ServiceModel.ClientBase<Init.SIGePro.Protocollo.SicrawebAllegatiServiceProxy.RepWSSGateway>, Init.SIGePro.Protocollo.SicrawebAllegatiServiceProxy.RepWSSGateway {
        
        public RepWSSGatewayClient() {
        }
        
        public RepWSSGatewayClient(string endpointConfigurationName) : 
                base(endpointConfigurationName) {
        }
        
        public RepWSSGatewayClient(string endpointConfigurationName, string remoteAddress) : 
                base(endpointConfigurationName, remoteAddress) {
        }
        
        public RepWSSGatewayClient(string endpointConfigurationName, System.ServiceModel.EndpointAddress remoteAddress) : 
                base(endpointConfigurationName, remoteAddress) {
        }
        
        public RepWSSGatewayClient(System.ServiceModel.Channels.Binding binding, System.ServiceModel.EndpointAddress remoteAddress) : 
                base(binding, remoteAddress) {
        }
        
        public string docInsert(string logonCredentials, string libraryName, string documentBase64, string documentInfo) {
            return base.Channel.docInsert(logonCredentials, libraryName, documentBase64, documentInfo);
        }
        
        public string docExtract(string logonCredentials, string documentUID) {
            return base.Channel.docExtract(logonCredentials, documentUID);
        }
        
        public string docCheckOut(string logonCredentials, string documentUID) {
            return base.Channel.docCheckOut(logonCredentials, documentUID);
        }
        
        public void docUndoCheckOut(string logonCredentials, string documentUID) {
            base.Channel.docUndoCheckOut(logonCredentials, documentUID);
        }
        
        public void docCheckIn(string logonCredentials, string documentUID, string documentBase64) {
            base.Channel.docCheckIn(logonCredentials, documentUID, documentBase64);
        }
        
        public void docDelete(string logonCredentials, string documentUID, string deletionType) {
            base.Channel.docDelete(logonCredentials, documentUID, deletionType);
        }
        
        public string docGetInfo(string logonCredentials, string documentUID) {
            return base.Channel.docGetInfo(logonCredentials, documentUID);
        }
        
        public string docSearch(string logonCredentials, string libraryName, string xmlQuery) {
            return base.Channel.docSearch(logonCredentials, libraryName, xmlQuery);
        }
        
        public string docBind(string logonCredentials, string document) {
            return base.Channel.docBind(logonCredentials, document);
        }
        
        public string docGetMetadata(string logonCredentials, string documentUID) {
            return base.Channel.docGetMetadata(logonCredentials, documentUID);
        }
        
        public void docSetMetadata(string logonCredentials, string documentUID, string metaData) {
            base.Channel.docSetMetadata(logonCredentials, documentUID, metaData);
        }
        
        public string getAvailableLibraries(string logonCredentials) {
            return base.Channel.getAvailableLibraries(logonCredentials);
        }
        
        public string getDefaultLibrary(string logonCredentials) {
            return base.Channel.getDefaultLibrary(logonCredentials);
        }
        
        public string getAvailableDocumentClasses(string logonCredentials) {
            return base.Channel.getAvailableDocumentClasses(logonCredentials);
        }
        
        public string getDocumentClass(string logonCredentials, string className) {
            return base.Channel.getDocumentClass(logonCredentials, className);
        }
        
        public string docGetACL(string logonCredentials, string documentUID) {
            return base.Channel.docGetACL(logonCredentials, documentUID);
        }
        
        public void docSetACL(string logonCredentials, string documentUID, string aclDescriptor) {
            base.Channel.docSetACL(logonCredentials, documentUID, aclDescriptor);
        }
        
        public string docGetRelated(string logonCredentials, string documentUID) {
            return base.Channel.docGetRelated(logonCredentials, documentUID);
        }
        
        public void docSetRelated(string logonCredentials, string documentUID, string relatedDescriptor) {
            base.Channel.docSetRelated(logonCredentials, documentUID, relatedDescriptor);
        }
        
        public Init.SIGePro.Protocollo.SicrawebAllegatiServiceProxy.RepWSSGateway_FileTransferDownloadBeginResult fileTransferDownloadBegin(string logonCredentials, Init.SIGePro.Protocollo.SicrawebAllegatiServiceProxy.RepWSSGateway_FileTransferDownloadBeginParams @params) {
            return base.Channel.fileTransferDownloadBegin(logonCredentials, @params);
        }
        
        public Init.SIGePro.Protocollo.SicrawebAllegatiServiceProxy.RepWSSGateway_FileTransferDownloadBlockResult fileTransferDownloadBlock(string logonCredentials, Init.SIGePro.Protocollo.SicrawebAllegatiServiceProxy.RepWSSGateway_FileTransferDownloadBlockParams @params) {
            return base.Channel.fileTransferDownloadBlock(logonCredentials, @params);
        }
        
        public Init.SIGePro.Protocollo.SicrawebAllegatiServiceProxy.RepWSSGateway_FileTransferDownloadEndResult fileTransferDownloadEnd(string logonCredentials, Init.SIGePro.Protocollo.SicrawebAllegatiServiceProxy.RepWSSGateway_FileTransferDownloadEndParams @params) {
            return base.Channel.fileTransferDownloadEnd(logonCredentials, @params);
        }
    }
}