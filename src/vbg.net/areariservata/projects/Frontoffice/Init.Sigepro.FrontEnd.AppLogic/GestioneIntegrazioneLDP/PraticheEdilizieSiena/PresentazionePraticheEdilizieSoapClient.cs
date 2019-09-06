using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneIntegrazioneLDP.PresentazionePraticheEdilizieSiena
{

    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    [System.ServiceModel.ServiceContractAttribute(Namespace = "https://ws.ldpgis.it/", ConfigurationName = "ServiceReference3.PresentazionePraticheEdilizieSoap")]
    public interface PresentazionePraticheEdilizieSoap
    {

        // CODEGEN: Generating message contract since the operation getDatiTerritorialiByIdentificativoTemporaneo is neither RPC nor document wrapped.
        [System.ServiceModel.OperationContractAttribute(Action = "https://ws.ldpgis.it/getDatiTerritorialiByIdentificativoTemporaneo", ReplyAction = "*")]
        [System.ServiceModel.XmlSerializerFormatAttribute(SupportFaults = true)]
        getDatiTerritorialiByIdentificativoTemporaneoResponse getDatiTerritorialiByIdentificativoTemporaneo(getDatiTerritorialiByIdentificativoTemporaneoRequest request);

        [System.ServiceModel.OperationContractAttribute(Action = "https://ws.ldpgis.it/getDatiTerritorialiByIdentificativoTemporaneo", ReplyAction = "*")]
        System.Threading.Tasks.Task<getDatiTerritorialiByIdentificativoTemporaneoResponse> getDatiTerritorialiByIdentificativoTemporaneoAsync(getDatiTerritorialiByIdentificativoTemporaneoRequest request);

        // CODEGEN: Generating message contract since the operation setNumeroPratica is neither RPC nor document wrapped.
        [System.ServiceModel.OperationContractAttribute(Action = "https://ws.ldpgis.it/setNumeroPratica", ReplyAction = "*")]
        [System.ServiceModel.XmlSerializerFormatAttribute(SupportFaults = true)]
        setNumeroPraticaResponse setNumeroPratica(setNumeroPraticaRequest request);

        [System.ServiceModel.OperationContractAttribute(Action = "https://ws.ldpgis.it/setNumeroPratica", ReplyAction = "*")]
        System.Threading.Tasks.Task<setNumeroPraticaResponse> setNumeroPraticaAsync(setNumeroPraticaRequest request);
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.Xml", "4.6.79.0")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(Namespace = "https://ws.ldpgis.it/")]
    public partial class ComplexTypeStringa : object, System.ComponentModel.INotifyPropertyChanged
    {

        private string testoField;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, IsNullable = true, Order = 0)]
        public string testo
        {
            get
            {
                return this.testoField;
            }
            set
            {
                this.testoField = value;
                this.RaisePropertyChanged("testo");
            }
        }

        public event System.ComponentModel.PropertyChangedEventHandler PropertyChanged;

        protected void RaisePropertyChanged(string propertyName)
        {
            System.ComponentModel.PropertyChangedEventHandler propertyChanged = this.PropertyChanged;
            if ((propertyChanged != null))
            {
                propertyChanged(this, new System.ComponentModel.PropertyChangedEventArgs(propertyName));
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.Xml", "4.6.79.0")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(Namespace = "https://ws.ldpgis.it/")]
    public partial class ComplexTypePraticaIdentificativi : object, System.ComponentModel.INotifyPropertyChanged
    {

        private string identificativo_temporaneoField;

        private string numero_praticaField;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, IsNullable = true, Order = 0)]
        public string identificativo_temporaneo
        {
            get
            {
                return this.identificativo_temporaneoField;
            }
            set
            {
                this.identificativo_temporaneoField = value;
                this.RaisePropertyChanged("identificativo_temporaneo");
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, IsNullable = true, Order = 1)]
        public string numero_pratica
        {
            get
            {
                return this.numero_praticaField;
            }
            set
            {
                this.numero_praticaField = value;
                this.RaisePropertyChanged("numero_pratica");
            }
        }

        public event System.ComponentModel.PropertyChangedEventHandler PropertyChanged;

        protected void RaisePropertyChanged(string propertyName)
        {
            System.ComponentModel.PropertyChangedEventHandler propertyChanged = this.PropertyChanged;
            if ((propertyChanged != null))
            {
                propertyChanged(this, new System.ComponentModel.PropertyChangedEventArgs(propertyName));
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.Xml", "4.6.79.0")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(Namespace = "https://ws.ldpgis.it/")]
    public partial class ComplexTypeCivico : object, System.ComponentModel.INotifyPropertyChanged
    {

        private string codice_stradaField;

        private string numeroField;

        private string esponenteField;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, IsNullable = true, Order = 0)]
        public string codice_strada
        {
            get
            {
                return this.codice_stradaField;
            }
            set
            {
                this.codice_stradaField = value;
                this.RaisePropertyChanged("codice_strada");
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, IsNullable = true, Order = 1)]
        public string numero
        {
            get
            {
                return this.numeroField;
            }
            set
            {
                this.numeroField = value;
                this.RaisePropertyChanged("numero");
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, IsNullable = true, Order = 2)]
        public string esponente
        {
            get
            {
                return this.esponenteField;
            }
            set
            {
                this.esponenteField = value;
                this.RaisePropertyChanged("esponente");
            }
        }

        public event System.ComponentModel.PropertyChangedEventHandler PropertyChanged;

        protected void RaisePropertyChanged(string propertyName)
        {
            System.ComponentModel.PropertyChangedEventHandler propertyChanged = this.PropertyChanged;
            if ((propertyChanged != null))
            {
                propertyChanged(this, new System.ComponentModel.PropertyChangedEventArgs(propertyName));
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.Xml", "4.6.79.0")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(Namespace = "https://ws.ldpgis.it/")]
    public partial class ComplexTypeSubalterno : object, System.ComponentModel.INotifyPropertyChanged
    {

        private string sezioneField;

        private string foglioField;

        private string particellaField;

        private string subalternoField;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, IsNullable = true, Order = 0)]
        public string sezione
        {
            get
            {
                return this.sezioneField;
            }
            set
            {
                this.sezioneField = value;
                this.RaisePropertyChanged("sezione");
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, IsNullable = true, Order = 1)]
        public string foglio
        {
            get
            {
                return this.foglioField;
            }
            set
            {
                this.foglioField = value;
                this.RaisePropertyChanged("foglio");
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, IsNullable = true, Order = 2)]
        public string particella
        {
            get
            {
                return this.particellaField;
            }
            set
            {
                this.particellaField = value;
                this.RaisePropertyChanged("particella");
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, IsNullable = true, Order = 3)]
        public string subalterno
        {
            get
            {
                return this.subalternoField;
            }
            set
            {
                this.subalternoField = value;
                this.RaisePropertyChanged("subalterno");
            }
        }

        public event System.ComponentModel.PropertyChangedEventHandler PropertyChanged;

        protected void RaisePropertyChanged(string propertyName)
        {
            System.ComponentModel.PropertyChangedEventHandler propertyChanged = this.PropertyChanged;
            if ((propertyChanged != null))
            {
                propertyChanged(this, new System.ComponentModel.PropertyChangedEventArgs(propertyName));
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.Xml", "4.6.79.0")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(Namespace = "https://ws.ldpgis.it/")]
    public partial class ComplexTypePoint : object, System.ComponentModel.INotifyPropertyChanged
    {

        private float xField;

        private float yField;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 0)]
        public float x
        {
            get
            {
                return this.xField;
            }
            set
            {
                this.xField = value;
                this.RaisePropertyChanged("x");
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, Order = 1)]
        public float y
        {
            get
            {
                return this.yField;
            }
            set
            {
                this.yField = value;
                this.RaisePropertyChanged("y");
            }
        }

        public event System.ComponentModel.PropertyChangedEventHandler PropertyChanged;

        protected void RaisePropertyChanged(string propertyName)
        {
            System.ComponentModel.PropertyChangedEventHandler propertyChanged = this.PropertyChanged;
            if ((propertyChanged != null))
            {
                propertyChanged(this, new System.ComponentModel.PropertyChangedEventArgs(propertyName));
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.Xml", "4.6.79.0")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(Namespace = "https://ws.ldpgis.it/")]
    public partial class ComplexTypePraticaDatiTerritoriali : object, System.ComponentModel.INotifyPropertyChanged
    {

        private ComplexTypePoint pointField;

        private ComplexTypeSubalterno[] a_subalterniField;

        private ComplexTypeCivico[] a_civiciField;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, IsNullable = true, Order = 0)]
        public ComplexTypePoint point
        {
            get
            {
                return this.pointField;
            }
            set
            {
                this.pointField = value;
                this.RaisePropertyChanged("point");
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlArrayAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, IsNullable = true, Order = 1)]
        [System.Xml.Serialization.XmlArrayItemAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, IsNullable = false)]
        public ComplexTypeSubalterno[] a_subalterni
        {
            get
            {
                return this.a_subalterniField;
            }
            set
            {
                this.a_subalterniField = value;
                this.RaisePropertyChanged("a_subalterni");
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlArrayAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, IsNullable = true, Order = 2)]
        [System.Xml.Serialization.XmlArrayItemAttribute(Form = System.Xml.Schema.XmlSchemaForm.Unqualified, IsNullable = false)]
        public ComplexTypeCivico[] a_civici
        {
            get
            {
                return this.a_civiciField;
            }
            set
            {
                this.a_civiciField = value;
                this.RaisePropertyChanged("a_civici");
            }
        }

        public event System.ComponentModel.PropertyChangedEventHandler PropertyChanged;

        protected void RaisePropertyChanged(string propertyName)
        {
            System.ComponentModel.PropertyChangedEventHandler propertyChanged = this.PropertyChanged;
            if ((propertyChanged != null))
            {
                propertyChanged(this, new System.ComponentModel.PropertyChangedEventArgs(propertyName));
            }
        }
    }

    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    [System.ComponentModel.EditorBrowsableAttribute(System.ComponentModel.EditorBrowsableState.Advanced)]
    [System.ServiceModel.MessageContractAttribute(IsWrapped = false)]
    public partial class getDatiTerritorialiByIdentificativoTemporaneoRequest
    {

        [System.ServiceModel.MessageBodyMemberAttribute(Namespace = "https://ws.ldpgis.it/", Order = 0)]
        public ComplexTypeStringa ComplexTypeStringa;

        public getDatiTerritorialiByIdentificativoTemporaneoRequest()
        {
        }

        public getDatiTerritorialiByIdentificativoTemporaneoRequest(ComplexTypeStringa ComplexTypeStringa)
        {
            this.ComplexTypeStringa = ComplexTypeStringa;
        }
    }

    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    [System.ComponentModel.EditorBrowsableAttribute(System.ComponentModel.EditorBrowsableState.Advanced)]
    [System.ServiceModel.MessageContractAttribute(IsWrapped = false)]
    public partial class getDatiTerritorialiByIdentificativoTemporaneoResponse
    {

        [System.ServiceModel.MessageBodyMemberAttribute(Namespace = "https://ws.ldpgis.it/", Order = 0)]
        public ComplexTypePraticaDatiTerritoriali ComplexTypePraticaDatiTerritoriali;

        public getDatiTerritorialiByIdentificativoTemporaneoResponse()
        {
        }

        public getDatiTerritorialiByIdentificativoTemporaneoResponse(ComplexTypePraticaDatiTerritoriali ComplexTypePraticaDatiTerritoriali)
        {
            this.ComplexTypePraticaDatiTerritoriali = ComplexTypePraticaDatiTerritoriali;
        }
    }

    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    [System.ComponentModel.EditorBrowsableAttribute(System.ComponentModel.EditorBrowsableState.Advanced)]
    [System.ServiceModel.MessageContractAttribute(IsWrapped = false)]
    public partial class setNumeroPraticaRequest
    {

        [System.ServiceModel.MessageBodyMemberAttribute(Namespace = "https://ws.ldpgis.it/", Order = 0)]
        public ComplexTypePraticaIdentificativi ComplexTypePraticaIdentificativi;

        public setNumeroPraticaRequest()
        {
        }

        public setNumeroPraticaRequest(ComplexTypePraticaIdentificativi ComplexTypePraticaIdentificativi)
        {
            this.ComplexTypePraticaIdentificativi = ComplexTypePraticaIdentificativi;
        }
    }

    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    [System.ComponentModel.EditorBrowsableAttribute(System.ComponentModel.EditorBrowsableState.Advanced)]
    [System.ServiceModel.MessageContractAttribute(IsWrapped = false)]
    public partial class setNumeroPraticaResponse
    {

        [System.ServiceModel.MessageBodyMemberAttribute(Namespace = "https://ws.ldpgis.it/", Order = 0)]
        public ComplexTypeStringa ComplexTypeStringa;

        public setNumeroPraticaResponse()
        {
        }

        public setNumeroPraticaResponse(ComplexTypeStringa ComplexTypeStringa)
        {
            this.ComplexTypeStringa = ComplexTypeStringa;
        }
    }

    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    public interface PresentazionePraticheEdilizieSoapChannel : PresentazionePraticheEdilizieSoap, System.ServiceModel.IClientChannel
    {
    }

    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    public partial class PresentazionePraticheEdilizieSoapClient : System.ServiceModel.ClientBase<PresentazionePraticheEdilizieSoap>, PresentazionePraticheEdilizieSoap
    {

        public PresentazionePraticheEdilizieSoapClient()
        {
        }

        public PresentazionePraticheEdilizieSoapClient(string endpointConfigurationName) :
            base(endpointConfigurationName)
        {
        }

        public PresentazionePraticheEdilizieSoapClient(string endpointConfigurationName, string remoteAddress) :
            base(endpointConfigurationName, remoteAddress)
        {
        }

        public PresentazionePraticheEdilizieSoapClient(string endpointConfigurationName, System.ServiceModel.EndpointAddress remoteAddress) :
            base(endpointConfigurationName, remoteAddress)
        {
        }

        public PresentazionePraticheEdilizieSoapClient(System.ServiceModel.Channels.Binding binding, System.ServiceModel.EndpointAddress remoteAddress) :
            base(binding, remoteAddress)
        {
        }

        [System.ComponentModel.EditorBrowsableAttribute(System.ComponentModel.EditorBrowsableState.Advanced)]
        getDatiTerritorialiByIdentificativoTemporaneoResponse PresentazionePraticheEdilizieSoap.getDatiTerritorialiByIdentificativoTemporaneo(getDatiTerritorialiByIdentificativoTemporaneoRequest request)
        {
            return base.Channel.getDatiTerritorialiByIdentificativoTemporaneo(request);
        }

        public ComplexTypePraticaDatiTerritoriali getDatiTerritorialiByIdentificativoTemporaneo(ComplexTypeStringa ComplexTypeStringa)
        {
            getDatiTerritorialiByIdentificativoTemporaneoRequest inValue = new getDatiTerritorialiByIdentificativoTemporaneoRequest();
            inValue.ComplexTypeStringa = ComplexTypeStringa;
            getDatiTerritorialiByIdentificativoTemporaneoResponse retVal = ((PresentazionePraticheEdilizieSoap)(this)).getDatiTerritorialiByIdentificativoTemporaneo(inValue);
            return retVal.ComplexTypePraticaDatiTerritoriali;
        }

        [System.ComponentModel.EditorBrowsableAttribute(System.ComponentModel.EditorBrowsableState.Advanced)]
        System.Threading.Tasks.Task<getDatiTerritorialiByIdentificativoTemporaneoResponse> PresentazionePraticheEdilizieSoap.getDatiTerritorialiByIdentificativoTemporaneoAsync(getDatiTerritorialiByIdentificativoTemporaneoRequest request)
        {
            return base.Channel.getDatiTerritorialiByIdentificativoTemporaneoAsync(request);
        }

        public System.Threading.Tasks.Task<getDatiTerritorialiByIdentificativoTemporaneoResponse> getDatiTerritorialiByIdentificativoTemporaneoAsync(ComplexTypeStringa ComplexTypeStringa)
        {
            getDatiTerritorialiByIdentificativoTemporaneoRequest inValue = new getDatiTerritorialiByIdentificativoTemporaneoRequest();
            inValue.ComplexTypeStringa = ComplexTypeStringa;
            return ((PresentazionePraticheEdilizieSoap)(this)).getDatiTerritorialiByIdentificativoTemporaneoAsync(inValue);
        }

        [System.ComponentModel.EditorBrowsableAttribute(System.ComponentModel.EditorBrowsableState.Advanced)]
        setNumeroPraticaResponse PresentazionePraticheEdilizieSoap.setNumeroPratica(setNumeroPraticaRequest request)
        {
            return base.Channel.setNumeroPratica(request);
        }

        public ComplexTypeStringa setNumeroPratica(ComplexTypePraticaIdentificativi ComplexTypePraticaIdentificativi)
        {
            setNumeroPraticaRequest inValue = new setNumeroPraticaRequest();
            inValue.ComplexTypePraticaIdentificativi = ComplexTypePraticaIdentificativi;
            setNumeroPraticaResponse retVal = ((PresentazionePraticheEdilizieSoap)(this)).setNumeroPratica(inValue);
            return retVal.ComplexTypeStringa;
        }

        [System.ComponentModel.EditorBrowsableAttribute(System.ComponentModel.EditorBrowsableState.Advanced)]
        System.Threading.Tasks.Task<setNumeroPraticaResponse> PresentazionePraticheEdilizieSoap.setNumeroPraticaAsync(setNumeroPraticaRequest request)
        {
            return base.Channel.setNumeroPraticaAsync(request);
        }

        public System.Threading.Tasks.Task<setNumeroPraticaResponse> setNumeroPraticaAsync(ComplexTypePraticaIdentificativi ComplexTypePraticaIdentificativi)
        {
            setNumeroPraticaRequest inValue = new setNumeroPraticaRequest();
            inValue.ComplexTypePraticaIdentificativi = ComplexTypePraticaIdentificativi;
            return ((PresentazionePraticheEdilizieSoap)(this)).setNumeroPraticaAsync(inValue);
        }
    }
}
