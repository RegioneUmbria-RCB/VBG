﻿//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by a tool.
//     Runtime Version:4.0.30319.17929
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace Init.SIGePro.Manager.EventiAttivitaWebService {
    
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    [System.ServiceModel.ServiceContractAttribute(Namespace="http://gruppoinit.it/sigepro/definitions/attivita", ConfigurationName="EventiAttivitaWebService.attivita")]
    public interface attivita {
        
        // CODEGEN: Generating message contract since the wrapper namespace (http://gruppoinit.it/sigepro/schemas/messages/attivita) of message SnapShotRequest does not match the default value (http://gruppoinit.it/sigepro/definitions/attivita)
        [System.ServiceModel.OperationContractAttribute(Action="http://gruppoinit.it/sigepro/schemas/messages/attivita/SnapShot", ReplyAction="*")]
        [System.ServiceModel.XmlSerializerFormatAttribute(SupportFaults=true)]
        Init.SIGePro.Manager.EventiAttivitaWebService.SnapShotResponse SnapShot(Init.SIGePro.Manager.EventiAttivitaWebService.SnapShotRequest request);
        
        // CODEGEN: Generating message contract since the wrapper namespace (http://gruppoinit.it/sigepro/schemas/messages/attivita) of message AggiornaCampiSchedeRequest does not match the default value (http://gruppoinit.it/sigepro/definitions/attivita)
        [System.ServiceModel.OperationContractAttribute(Action="http://gruppoinit.it/sigepro/schemas/messages/attivita/AggiornaCampiSchede", ReplyAction="*")]
        [System.ServiceModel.XmlSerializerFormatAttribute(SupportFaults=true)]
        Init.SIGePro.Manager.EventiAttivitaWebService.AggiornaCampiSchedeResponse AggiornaCampiSchede(Init.SIGePro.Manager.EventiAttivitaWebService.AggiornaCampiSchedeRequest request);
    }
    
    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.Xml", "4.0.30319.17929")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(Namespace="http://gruppoinit.it/sigepro/schemas/messages/base")]
    public partial class EsitoOperazioneType : object, System.ComponentModel.INotifyPropertyChanged {
        
        private int esitoField;
        
        private ErroreBackofficeType[] listaErroriField;
        
        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Order=0)]
        public int esito {
            get {
                return this.esitoField;
            }
            set {
                this.esitoField = value;
                this.RaisePropertyChanged("esito");
            }
        }
        
        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("listaErrori", Order=1)]
        public ErroreBackofficeType[] listaErrori {
            get {
                return this.listaErroriField;
            }
            set {
                this.listaErroriField = value;
                this.RaisePropertyChanged("listaErrori");
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
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.Xml", "4.0.30319.17929")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(Namespace="http://gruppoinit.it/sigepro/schemas/messages/base")]
    public partial class ErroreBackofficeType : object, System.ComponentModel.INotifyPropertyChanged {
        
        private string codiceField;
        
        private string descrizioneField;
        
        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Order=0)]
        public string codice {
            get {
                return this.codiceField;
            }
            set {
                this.codiceField = value;
                this.RaisePropertyChanged("codice");
            }
        }
        
        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Order=1)]
        public string descrizione {
            get {
                return this.descrizioneField;
            }
            set {
                this.descrizioneField = value;
                this.RaisePropertyChanged("descrizione");
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
    [System.ServiceModel.MessageContractAttribute(WrapperName="SnapShotRequest", WrapperNamespace="http://gruppoinit.it/sigepro/schemas/messages/attivita", IsWrapped=true)]
    public partial class SnapShotRequest {
        
        [System.ServiceModel.MessageBodyMemberAttribute(Namespace="http://gruppoinit.it/sigepro/schemas/messages/attivita", Order=0)]
        public string token;
        
        [System.ServiceModel.MessageBodyMemberAttribute(Namespace="http://gruppoinit.it/sigepro/schemas/messages/attivita", Order=1)]
        public string software;
        
        [System.ServiceModel.MessageBodyMemberAttribute(Namespace="http://gruppoinit.it/sigepro/schemas/messages/attivita", Order=2)]
        public int codiceAttivita;
        
        public SnapShotRequest() {
        }
        
        public SnapShotRequest(string token, string software, int codiceAttivita) {
            this.token = token;
            this.software = software;
            this.codiceAttivita = codiceAttivita;
        }
    }
    
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    [System.ComponentModel.EditorBrowsableAttribute(System.ComponentModel.EditorBrowsableState.Advanced)]
    [System.ServiceModel.MessageContractAttribute(WrapperName="SnapShotResponse", WrapperNamespace="http://gruppoinit.it/sigepro/schemas/messages/attivita", IsWrapped=true)]
    public partial class SnapShotResponse {
        
        [System.ServiceModel.MessageBodyMemberAttribute(Namespace="http://gruppoinit.it/sigepro/schemas/messages/attivita", Order=0)]
        public Init.SIGePro.Manager.EventiAttivitaWebService.EsitoOperazioneType esito;
        
        public SnapShotResponse() {
        }
        
        public SnapShotResponse(Init.SIGePro.Manager.EventiAttivitaWebService.EsitoOperazioneType esito) {
            this.esito = esito;
        }
    }
    
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    [System.ComponentModel.EditorBrowsableAttribute(System.ComponentModel.EditorBrowsableState.Advanced)]
    [System.ServiceModel.MessageContractAttribute(WrapperName="AggiornaCampiSchedeRequest", WrapperNamespace="http://gruppoinit.it/sigepro/schemas/messages/attivita", IsWrapped=true)]
    public partial class AggiornaCampiSchedeRequest {
        
        [System.ServiceModel.MessageBodyMemberAttribute(Namespace="http://gruppoinit.it/sigepro/schemas/messages/attivita", Order=0)]
        public string token;
        
        [System.ServiceModel.MessageBodyMemberAttribute(Namespace="http://gruppoinit.it/sigepro/schemas/messages/attivita", Order=1)]
        public string software;
        
        [System.ServiceModel.MessageBodyMemberAttribute(Namespace="http://gruppoinit.it/sigepro/schemas/messages/attivita", Order=2)]
        public int codiceAttivita;
        
        [System.ServiceModel.MessageBodyMemberAttribute(Namespace="http://gruppoinit.it/sigepro/schemas/messages/attivita", Order=3)]
        [System.Xml.Serialization.XmlElementAttribute(IsNullable=true)]
        public System.Nullable<int> codiceIstanza;
        
        [System.ServiceModel.MessageBodyMemberAttribute(Namespace="http://gruppoinit.it/sigepro/schemas/messages/attivita", Order=4)]
        [System.Xml.Serialization.XmlElementAttribute(IsNullable=true)]
        public System.Nullable<int> codiceScheda;
        
        public AggiornaCampiSchedeRequest() {
        }
        
        public AggiornaCampiSchedeRequest(string token, string software, int codiceAttivita, System.Nullable<int> codiceIstanza, System.Nullable<int> codiceScheda) {
            this.token = token;
            this.software = software;
            this.codiceAttivita = codiceAttivita;
            this.codiceIstanza = codiceIstanza;
            this.codiceScheda = codiceScheda;
        }
    }
    
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    [System.ComponentModel.EditorBrowsableAttribute(System.ComponentModel.EditorBrowsableState.Advanced)]
    [System.ServiceModel.MessageContractAttribute(WrapperName="AggiornaCampiSchedeResponse", WrapperNamespace="http://gruppoinit.it/sigepro/schemas/messages/attivita", IsWrapped=true)]
    public partial class AggiornaCampiSchedeResponse {
        
        [System.ServiceModel.MessageBodyMemberAttribute(Namespace="http://gruppoinit.it/sigepro/schemas/messages/attivita", Order=0)]
        public Init.SIGePro.Manager.EventiAttivitaWebService.EsitoOperazioneType esito;
        
        public AggiornaCampiSchedeResponse() {
        }
        
        public AggiornaCampiSchedeResponse(Init.SIGePro.Manager.EventiAttivitaWebService.EsitoOperazioneType esito) {
            this.esito = esito;
        }
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    public interface attivitaChannel : Init.SIGePro.Manager.EventiAttivitaWebService.attivita, System.ServiceModel.IClientChannel {
    }
    
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    public partial class attivitaClient : System.ServiceModel.ClientBase<Init.SIGePro.Manager.EventiAttivitaWebService.attivita>, Init.SIGePro.Manager.EventiAttivitaWebService.attivita {
        
        public attivitaClient() {
        }
        
        public attivitaClient(string endpointConfigurationName) : 
                base(endpointConfigurationName) {
        }
        
        public attivitaClient(string endpointConfigurationName, string remoteAddress) : 
                base(endpointConfigurationName, remoteAddress) {
        }
        
        public attivitaClient(string endpointConfigurationName, System.ServiceModel.EndpointAddress remoteAddress) : 
                base(endpointConfigurationName, remoteAddress) {
        }
        
        public attivitaClient(System.ServiceModel.Channels.Binding binding, System.ServiceModel.EndpointAddress remoteAddress) : 
                base(binding, remoteAddress) {
        }
        
        [System.ComponentModel.EditorBrowsableAttribute(System.ComponentModel.EditorBrowsableState.Advanced)]
        Init.SIGePro.Manager.EventiAttivitaWebService.SnapShotResponse Init.SIGePro.Manager.EventiAttivitaWebService.attivita.SnapShot(Init.SIGePro.Manager.EventiAttivitaWebService.SnapShotRequest request) {
            return base.Channel.SnapShot(request);
        }
        
        public Init.SIGePro.Manager.EventiAttivitaWebService.EsitoOperazioneType SnapShot(string token, string software, int codiceAttivita) {
            Init.SIGePro.Manager.EventiAttivitaWebService.SnapShotRequest inValue = new Init.SIGePro.Manager.EventiAttivitaWebService.SnapShotRequest();
            inValue.token = token;
            inValue.software = software;
            inValue.codiceAttivita = codiceAttivita;
            Init.SIGePro.Manager.EventiAttivitaWebService.SnapShotResponse retVal = ((Init.SIGePro.Manager.EventiAttivitaWebService.attivita)(this)).SnapShot(inValue);
            return retVal.esito;
        }
        
        [System.ComponentModel.EditorBrowsableAttribute(System.ComponentModel.EditorBrowsableState.Advanced)]
        Init.SIGePro.Manager.EventiAttivitaWebService.AggiornaCampiSchedeResponse Init.SIGePro.Manager.EventiAttivitaWebService.attivita.AggiornaCampiSchede(Init.SIGePro.Manager.EventiAttivitaWebService.AggiornaCampiSchedeRequest request) {
            return base.Channel.AggiornaCampiSchede(request);
        }
        
        public Init.SIGePro.Manager.EventiAttivitaWebService.EsitoOperazioneType AggiornaCampiSchede(string token, string software, int codiceAttivita, System.Nullable<int> codiceIstanza, System.Nullable<int> codiceScheda) {
            Init.SIGePro.Manager.EventiAttivitaWebService.AggiornaCampiSchedeRequest inValue = new Init.SIGePro.Manager.EventiAttivitaWebService.AggiornaCampiSchedeRequest();
            inValue.token = token;
            inValue.software = software;
            inValue.codiceAttivita = codiceAttivita;
            inValue.codiceIstanza = codiceIstanza;
            inValue.codiceScheda = codiceScheda;
            Init.SIGePro.Manager.EventiAttivitaWebService.AggiornaCampiSchedeResponse retVal = ((Init.SIGePro.Manager.EventiAttivitaWebService.attivita)(this)).AggiornaCampiSchede(inValue);
            return retVal.esito;
        }
    }
}