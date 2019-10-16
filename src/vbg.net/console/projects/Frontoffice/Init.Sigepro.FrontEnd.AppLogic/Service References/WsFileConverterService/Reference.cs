﻿//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by a tool.
//     Runtime Version:4.0.30319.269
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService {
    
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    [System.ServiceModel.ServiceContractAttribute(Namespace="http://gruppoinit.it/fileconverter/definitions", ConfigurationName="WsFileConverterService.fileconverter")]
    public interface fileconverter {
        
        // CODEGEN: Generating message contract since the operation MergeDataAndConvert is neither RPC nor document wrapped.
        [System.ServiceModel.OperationContractAttribute(Action="", ReplyAction="*")]
        [System.ServiceModel.XmlSerializerFormatAttribute(SupportFaults=true)]
        Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.MergeDataAndConvertResponse1 MergeDataAndConvert(Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.MergeDataAndConvertRequest1 request);
        
        // CODEGEN: Generating message contract since the operation ConvertBinary is neither RPC nor document wrapped.
        [System.ServiceModel.OperationContractAttribute(Action="", ReplyAction="*")]
        [System.ServiceModel.XmlSerializerFormatAttribute(SupportFaults=true)]
        Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.ConvertBinaryResponse1 ConvertBinary(Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.ConvertBinaryRequest1 request);
        
        // CODEGEN: Generating message contract since the operation MergeData is neither RPC nor document wrapped.
        [System.ServiceModel.OperationContractAttribute(Action="", ReplyAction="*")]
        [System.ServiceModel.XmlSerializerFormatAttribute(SupportFaults=true)]
        Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.MergeDataResponse1 MergeData(Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.MergeDataRequest1 request);
        
        // CODEGEN: Generating message contract since the operation Convert is neither RPC nor document wrapped.
        [System.ServiceModel.OperationContractAttribute(Action="", ReplyAction="*")]
        [System.ServiceModel.XmlSerializerFormatAttribute(SupportFaults=true)]
        Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.ConvertResponse1 Convert(Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.ConvertRequest1 request);
    }
    
    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.Xml", "4.0.30319.233")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType=true, Namespace="http://gruppoinit.it/fileconverter")]
    public partial class MergeDataAndConvertRequest : object, System.ComponentModel.INotifyPropertyChanged {
        
        private string tokenField;
        
        private byte[] rtfBinaryDataField;
        
        private byte[] xmlBinaryDataField;
        
        private string conversionTypeField;
        
        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Order=0)]
        public string token {
            get {
                return this.tokenField;
            }
            set {
                this.tokenField = value;
                this.RaisePropertyChanged("token");
            }
        }
        
        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(DataType="base64Binary", Order=1)]
        public byte[] rtfBinaryData {
            get {
                return this.rtfBinaryDataField;
            }
            set {
                this.rtfBinaryDataField = value;
                this.RaisePropertyChanged("rtfBinaryData");
            }
        }
        
        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(DataType="base64Binary", Order=2)]
        public byte[] xmlBinaryData {
            get {
                return this.xmlBinaryDataField;
            }
            set {
                this.xmlBinaryDataField = value;
                this.RaisePropertyChanged("xmlBinaryData");
            }
        }
        
        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Order=3)]
        public string conversionType {
            get {
                return this.conversionTypeField;
            }
            set {
                this.conversionTypeField = value;
                this.RaisePropertyChanged("conversionType");
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
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.Xml", "4.0.30319.233")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType=true, Namespace="http://gruppoinit.it/fileconverter")]
    public partial class MergeDataAndConvertResponse : object, System.ComponentModel.INotifyPropertyChanged {
        
        private string mimeTypeField;
        
        private byte[] binaryDataField;
        
        private string fileNameField;
        
        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Order=0)]
        public string mimeType {
            get {
                return this.mimeTypeField;
            }
            set {
                this.mimeTypeField = value;
                this.RaisePropertyChanged("mimeType");
            }
        }
        
        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(DataType="base64Binary", Order=1)]
        public byte[] binaryData {
            get {
                return this.binaryDataField;
            }
            set {
                this.binaryDataField = value;
                this.RaisePropertyChanged("binaryData");
            }
        }
        
        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Order=2)]
        public string fileName {
            get {
                return this.fileNameField;
            }
            set {
                this.fileNameField = value;
                this.RaisePropertyChanged("fileName");
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
    
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    [System.ComponentModel.EditorBrowsableAttribute(System.ComponentModel.EditorBrowsableState.Advanced)]
    [System.ServiceModel.MessageContractAttribute(IsWrapped=false)]
    public partial class MergeDataAndConvertRequest1 {
        
        [System.ServiceModel.MessageBodyMemberAttribute(Namespace="http://gruppoinit.it/fileconverter", Order=0)]
        public Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.MergeDataAndConvertRequest MergeDataAndConvertRequest;
        
        public MergeDataAndConvertRequest1() {
        }
        
        public MergeDataAndConvertRequest1(Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.MergeDataAndConvertRequest MergeDataAndConvertRequest) {
            this.MergeDataAndConvertRequest = MergeDataAndConvertRequest;
        }
    }
    
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    [System.ComponentModel.EditorBrowsableAttribute(System.ComponentModel.EditorBrowsableState.Advanced)]
    [System.ServiceModel.MessageContractAttribute(IsWrapped=false)]
    public partial class MergeDataAndConvertResponse1 {
        
        [System.ServiceModel.MessageBodyMemberAttribute(Namespace="http://gruppoinit.it/fileconverter", Order=0)]
        public Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.MergeDataAndConvertResponse MergeDataAndConvertResponse;
        
        public MergeDataAndConvertResponse1() {
        }
        
        public MergeDataAndConvertResponse1(Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.MergeDataAndConvertResponse MergeDataAndConvertResponse) {
            this.MergeDataAndConvertResponse = MergeDataAndConvertResponse;
        }
    }
    
    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.Xml", "4.0.30319.233")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType=true, Namespace="http://gruppoinit.it/fileconverter")]
    public partial class ConvertBinaryRequest : object, System.ComponentModel.INotifyPropertyChanged {
        
        private string tokenField;
        
        private byte[] binaryDataField;
        
        private string contentTypeField;
        
        private string conversionTypeField;
        
        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Order=0)]
        public string token {
            get {
                return this.tokenField;
            }
            set {
                this.tokenField = value;
                this.RaisePropertyChanged("token");
            }
        }
        
        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(DataType="base64Binary", Order=1)]
        public byte[] binaryData {
            get {
                return this.binaryDataField;
            }
            set {
                this.binaryDataField = value;
                this.RaisePropertyChanged("binaryData");
            }
        }
        
        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Order=2)]
        public string contentType {
            get {
                return this.contentTypeField;
            }
            set {
                this.contentTypeField = value;
                this.RaisePropertyChanged("contentType");
            }
        }
        
        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Order=3)]
        public string conversionType {
            get {
                return this.conversionTypeField;
            }
            set {
                this.conversionTypeField = value;
                this.RaisePropertyChanged("conversionType");
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
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.Xml", "4.0.30319.233")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType=true, Namespace="http://gruppoinit.it/fileconverter")]
    public partial class ConvertBinaryResponse : object, System.ComponentModel.INotifyPropertyChanged {
        
        private string mimeTypeField;
        
        private byte[] binaryDataField;
        
        private string fileNameField;
        
        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Order=0)]
        public string mimeType {
            get {
                return this.mimeTypeField;
            }
            set {
                this.mimeTypeField = value;
                this.RaisePropertyChanged("mimeType");
            }
        }
        
        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(DataType="base64Binary", Order=1)]
        public byte[] binaryData {
            get {
                return this.binaryDataField;
            }
            set {
                this.binaryDataField = value;
                this.RaisePropertyChanged("binaryData");
            }
        }
        
        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Order=2)]
        public string fileName {
            get {
                return this.fileNameField;
            }
            set {
                this.fileNameField = value;
                this.RaisePropertyChanged("fileName");
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
    
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    [System.ComponentModel.EditorBrowsableAttribute(System.ComponentModel.EditorBrowsableState.Advanced)]
    [System.ServiceModel.MessageContractAttribute(IsWrapped=false)]
    public partial class ConvertBinaryRequest1 {
        
        [System.ServiceModel.MessageBodyMemberAttribute(Namespace="http://gruppoinit.it/fileconverter", Order=0)]
        public Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.ConvertBinaryRequest ConvertBinaryRequest;
        
        public ConvertBinaryRequest1() {
        }
        
        public ConvertBinaryRequest1(Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.ConvertBinaryRequest ConvertBinaryRequest) {
            this.ConvertBinaryRequest = ConvertBinaryRequest;
        }
    }
    
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    [System.ComponentModel.EditorBrowsableAttribute(System.ComponentModel.EditorBrowsableState.Advanced)]
    [System.ServiceModel.MessageContractAttribute(IsWrapped=false)]
    public partial class ConvertBinaryResponse1 {
        
        [System.ServiceModel.MessageBodyMemberAttribute(Namespace="http://gruppoinit.it/fileconverter", Order=0)]
        public Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.ConvertBinaryResponse ConvertBinaryResponse;
        
        public ConvertBinaryResponse1() {
        }
        
        public ConvertBinaryResponse1(Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.ConvertBinaryResponse ConvertBinaryResponse) {
            this.ConvertBinaryResponse = ConvertBinaryResponse;
        }
    }
    
    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.Xml", "4.0.30319.233")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType=true, Namespace="http://gruppoinit.it/fileconverter")]
    public partial class MergeDataRequest : object, System.ComponentModel.INotifyPropertyChanged {
        
        private string tokenField;
        
        private byte[] rtfBinaryDataField;
        
        private byte[] xmlBinaryDataField;
        
        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Order=0)]
        public string token {
            get {
                return this.tokenField;
            }
            set {
                this.tokenField = value;
                this.RaisePropertyChanged("token");
            }
        }
        
        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(DataType="base64Binary", Order=1)]
        public byte[] rtfBinaryData {
            get {
                return this.rtfBinaryDataField;
            }
            set {
                this.rtfBinaryDataField = value;
                this.RaisePropertyChanged("rtfBinaryData");
            }
        }
        
        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(DataType="base64Binary", Order=2)]
        public byte[] xmlBinaryData {
            get {
                return this.xmlBinaryDataField;
            }
            set {
                this.xmlBinaryDataField = value;
                this.RaisePropertyChanged("xmlBinaryData");
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
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.Xml", "4.0.30319.233")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType=true, Namespace="http://gruppoinit.it/fileconverter")]
    public partial class MergeDataResponse : object, System.ComponentModel.INotifyPropertyChanged {
        
        private string mimeTypeField;
        
        private byte[] binaryDataField;
        
        private string fileNameField;
        
        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Order=0)]
        public string mimeType {
            get {
                return this.mimeTypeField;
            }
            set {
                this.mimeTypeField = value;
                this.RaisePropertyChanged("mimeType");
            }
        }
        
        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(DataType="base64Binary", Order=1)]
        public byte[] binaryData {
            get {
                return this.binaryDataField;
            }
            set {
                this.binaryDataField = value;
                this.RaisePropertyChanged("binaryData");
            }
        }
        
        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Order=2)]
        public string fileName {
            get {
                return this.fileNameField;
            }
            set {
                this.fileNameField = value;
                this.RaisePropertyChanged("fileName");
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
    
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    [System.ComponentModel.EditorBrowsableAttribute(System.ComponentModel.EditorBrowsableState.Advanced)]
    [System.ServiceModel.MessageContractAttribute(IsWrapped=false)]
    public partial class MergeDataRequest1 {
        
        [System.ServiceModel.MessageBodyMemberAttribute(Namespace="http://gruppoinit.it/fileconverter", Order=0)]
        public Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.MergeDataRequest MergeDataRequest;
        
        public MergeDataRequest1() {
        }
        
        public MergeDataRequest1(Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.MergeDataRequest MergeDataRequest) {
            this.MergeDataRequest = MergeDataRequest;
        }
    }
    
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    [System.ComponentModel.EditorBrowsableAttribute(System.ComponentModel.EditorBrowsableState.Advanced)]
    [System.ServiceModel.MessageContractAttribute(IsWrapped=false)]
    public partial class MergeDataResponse1 {
        
        [System.ServiceModel.MessageBodyMemberAttribute(Namespace="http://gruppoinit.it/fileconverter", Order=0)]
        public Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.MergeDataResponse MergeDataResponse;
        
        public MergeDataResponse1() {
        }
        
        public MergeDataResponse1(Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.MergeDataResponse MergeDataResponse) {
            this.MergeDataResponse = MergeDataResponse;
        }
    }
    
    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.Xml", "4.0.30319.233")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType=true, Namespace="http://gruppoinit.it/fileconverter")]
    public partial class ConvertRequest : object, System.ComponentModel.INotifyPropertyChanged {
        
        private string tokenField;
        
        private string contentField;
        
        private string contentTypeField;
        
        private string conversionTypeField;
        
        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Order=0)]
        public string token {
            get {
                return this.tokenField;
            }
            set {
                this.tokenField = value;
                this.RaisePropertyChanged("token");
            }
        }
        
        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Order=1)]
        public string content {
            get {
                return this.contentField;
            }
            set {
                this.contentField = value;
                this.RaisePropertyChanged("content");
            }
        }
        
        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Order=2)]
        public string contentType {
            get {
                return this.contentTypeField;
            }
            set {
                this.contentTypeField = value;
                this.RaisePropertyChanged("contentType");
            }
        }
        
        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Order=3)]
        public string conversionType {
            get {
                return this.conversionTypeField;
            }
            set {
                this.conversionTypeField = value;
                this.RaisePropertyChanged("conversionType");
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
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.Xml", "4.0.30319.233")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType=true, Namespace="http://gruppoinit.it/fileconverter")]
    public partial class ConvertResponse : object, System.ComponentModel.INotifyPropertyChanged {
        
        private string mimeTypeField;
        
        private byte[] binaryDataField;
        
        private string fileNameField;
        
        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Order=0)]
        public string mimeType {
            get {
                return this.mimeTypeField;
            }
            set {
                this.mimeTypeField = value;
                this.RaisePropertyChanged("mimeType");
            }
        }
        
        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(DataType="base64Binary", Order=1)]
        public byte[] binaryData {
            get {
                return this.binaryDataField;
            }
            set {
                this.binaryDataField = value;
                this.RaisePropertyChanged("binaryData");
            }
        }
        
        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Order=2)]
        public string fileName {
            get {
                return this.fileNameField;
            }
            set {
                this.fileNameField = value;
                this.RaisePropertyChanged("fileName");
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
    
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    [System.ComponentModel.EditorBrowsableAttribute(System.ComponentModel.EditorBrowsableState.Advanced)]
    [System.ServiceModel.MessageContractAttribute(IsWrapped=false)]
    public partial class ConvertRequest1 {
        
        [System.ServiceModel.MessageBodyMemberAttribute(Namespace="http://gruppoinit.it/fileconverter", Order=0)]
        public Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.ConvertRequest ConvertRequest;
        
        public ConvertRequest1() {
        }
        
        public ConvertRequest1(Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.ConvertRequest ConvertRequest) {
            this.ConvertRequest = ConvertRequest;
        }
    }
    
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    [System.ComponentModel.EditorBrowsableAttribute(System.ComponentModel.EditorBrowsableState.Advanced)]
    [System.ServiceModel.MessageContractAttribute(IsWrapped=false)]
    public partial class ConvertResponse1 {
        
        [System.ServiceModel.MessageBodyMemberAttribute(Namespace="http://gruppoinit.it/fileconverter", Order=0)]
        public Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.ConvertResponse ConvertResponse;
        
        public ConvertResponse1() {
        }
        
        public ConvertResponse1(Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.ConvertResponse ConvertResponse) {
            this.ConvertResponse = ConvertResponse;
        }
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    public interface fileconverterChannel : Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.fileconverter, System.ServiceModel.IClientChannel {
    }
    
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    public partial class fileconverterClient : System.ServiceModel.ClientBase<Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.fileconverter>, Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.fileconverter {
        
        public fileconverterClient() {
        }
        
        public fileconverterClient(string endpointConfigurationName) : 
                base(endpointConfigurationName) {
        }
        
        public fileconverterClient(string endpointConfigurationName, string remoteAddress) : 
                base(endpointConfigurationName, remoteAddress) {
        }
        
        public fileconverterClient(string endpointConfigurationName, System.ServiceModel.EndpointAddress remoteAddress) : 
                base(endpointConfigurationName, remoteAddress) {
        }
        
        public fileconverterClient(System.ServiceModel.Channels.Binding binding, System.ServiceModel.EndpointAddress remoteAddress) : 
                base(binding, remoteAddress) {
        }
        
        [System.ComponentModel.EditorBrowsableAttribute(System.ComponentModel.EditorBrowsableState.Advanced)]
        Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.MergeDataAndConvertResponse1 Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.fileconverter.MergeDataAndConvert(Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.MergeDataAndConvertRequest1 request) {
            return base.Channel.MergeDataAndConvert(request);
        }
        
        public Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.MergeDataAndConvertResponse MergeDataAndConvert(Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.MergeDataAndConvertRequest MergeDataAndConvertRequest) {
            Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.MergeDataAndConvertRequest1 inValue = new Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.MergeDataAndConvertRequest1();
            inValue.MergeDataAndConvertRequest = MergeDataAndConvertRequest;
            Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.MergeDataAndConvertResponse1 retVal = ((Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.fileconverter)(this)).MergeDataAndConvert(inValue);
            return retVal.MergeDataAndConvertResponse;
        }
        
        [System.ComponentModel.EditorBrowsableAttribute(System.ComponentModel.EditorBrowsableState.Advanced)]
        Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.ConvertBinaryResponse1 Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.fileconverter.ConvertBinary(Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.ConvertBinaryRequest1 request) {
            return base.Channel.ConvertBinary(request);
        }
        
        public Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.ConvertBinaryResponse ConvertBinary(Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.ConvertBinaryRequest ConvertBinaryRequest) {
            Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.ConvertBinaryRequest1 inValue = new Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.ConvertBinaryRequest1();
            inValue.ConvertBinaryRequest = ConvertBinaryRequest;
            Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.ConvertBinaryResponse1 retVal = ((Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.fileconverter)(this)).ConvertBinary(inValue);
            return retVal.ConvertBinaryResponse;
        }
        
        [System.ComponentModel.EditorBrowsableAttribute(System.ComponentModel.EditorBrowsableState.Advanced)]
        Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.MergeDataResponse1 Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.fileconverter.MergeData(Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.MergeDataRequest1 request) {
            return base.Channel.MergeData(request);
        }
        
        public Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.MergeDataResponse MergeData(Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.MergeDataRequest MergeDataRequest) {
            Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.MergeDataRequest1 inValue = new Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.MergeDataRequest1();
            inValue.MergeDataRequest = MergeDataRequest;
            Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.MergeDataResponse1 retVal = ((Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.fileconverter)(this)).MergeData(inValue);
            return retVal.MergeDataResponse;
        }
        
        [System.ComponentModel.EditorBrowsableAttribute(System.ComponentModel.EditorBrowsableState.Advanced)]
        Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.ConvertResponse1 Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.fileconverter.Convert(Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.ConvertRequest1 request) {
            return base.Channel.Convert(request);
        }
        
        public Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.ConvertResponse Convert(Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.ConvertRequest ConvertRequest) {
            Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.ConvertRequest1 inValue = new Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.ConvertRequest1();
            inValue.ConvertRequest = ConvertRequest;
            Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.ConvertResponse1 retVal = ((Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService.fileconverter)(this)).Convert(inValue);
            return retVal.ConvertResponse;
        }
    }
}