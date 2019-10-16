﻿//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by a tool.
//     Runtime Version:4.0.30319.296
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Web.Services;
using System.Web.Services.Protocols;
using System.Xml.Serialization;

// 
// This source code was auto-generated by wsdl, Version=4.0.30319.1.
// 

namespace Init.SIGePro.Protocollo.Sigedo.Proxies.Allegati
{
    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("wsdl", "4.0.30319.1")]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Web.Services.WebServiceBindingAttribute(Name = "AllegatiServiceSoapBinding", Namespace = "http://dmServerService.WS.dmServer.finmatica.it")]
    public partial class AllegatiServiceService : System.Web.Services.Protocols.SoapHttpClientProtocol
    {

        private System.Threading.SendOrPostCallback getAllegatoOperationCompleted;

        /// <remarks/>
        public AllegatiServiceService()
        {
            this.Url = "http://sigedotest.comune.intranet/axis/services/AllegatiService";
        }

        /// <remarks/>
        public event getAllegatoCompletedEventHandler getAllegatoCompleted;

        /// <remarks/>
        [System.Web.Services.Protocols.SoapDocumentMethodAttribute("", RequestNamespace = "http://dmServerService.WS.dmServer.finmatica.it", ResponseNamespace = "http://dmServerService.WS.dmServer.finmatica.it", Use = System.Web.Services.Description.SoapBindingUse.Literal, ParameterStyle = System.Web.Services.Protocols.SoapParameterStyle.Wrapped)]
        [return: System.Xml.Serialization.XmlElementAttribute("getAllegatoReturn")]
        public Allegato getAllegato(int idAllegato, string utenteApplicativo)
        {
            object[] results = this.Invoke("getAllegato", new object[] {
                    idAllegato,
                    utenteApplicativo});
            return ((Allegato)(results[0]));
        }

        /// <remarks/>
        public System.IAsyncResult BegingetAllegato(int idAllegato, string utenteApplicativo, System.AsyncCallback callback, object asyncState)
        {
            return this.BeginInvoke("getAllegato", new object[] {
                    idAllegato,
                    utenteApplicativo}, callback, asyncState);
        }

        /// <remarks/>
        public Allegato EndgetAllegato(System.IAsyncResult asyncResult)
        {
            object[] results = this.EndInvoke(asyncResult);
            return ((Allegato)(results[0]));
        }

        /// <remarks/>
        public void getAllegatoAsync(int idAllegato, string utenteApplicativo)
        {
            this.getAllegatoAsync(idAllegato, utenteApplicativo, null);
        }

        /// <remarks/>
        public void getAllegatoAsync(int idAllegato, string utenteApplicativo, object userState)
        {
            if ((this.getAllegatoOperationCompleted == null))
            {
                this.getAllegatoOperationCompleted = new System.Threading.SendOrPostCallback(this.OngetAllegatoOperationCompleted);
            }
            this.InvokeAsync("getAllegato", new object[] {
                    idAllegato,
                    utenteApplicativo}, this.getAllegatoOperationCompleted, userState);
        }

        private void OngetAllegatoOperationCompleted(object arg)
        {
            if ((this.getAllegatoCompleted != null))
            {
                System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
                this.getAllegatoCompleted(this, new getAllegatoCompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
            }
        }

        /// <remarks/>
        public new void CancelAsync(object userState)
        {
            base.CancelAsync(userState);
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("wsdl", "4.0.30319.1")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(Namespace = "http://dmServerService.WS.dmServer.finmatica.it")]
    public partial class Allegato
    {

        private byte[] allegatoField;

        private string dataAggiornamentoField;

        private string descrizioneField;

        private object extendAllegatoField;

        private long idAllegatoField;

        private string tipoAllegatoField;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(DataType = "base64Binary", IsNullable = true)]
        public byte[] allegato
        {
            get
            {
                return this.allegatoField;
            }
            set
            {
                this.allegatoField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(IsNullable = true)]
        public string dataAggiornamento
        {
            get
            {
                return this.dataAggiornamentoField;
            }
            set
            {
                this.dataAggiornamentoField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(IsNullable = true)]
        public string descrizione
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

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(IsNullable = true)]
        public object extendAllegato
        {
            get
            {
                return this.extendAllegatoField;
            }
            set
            {
                this.extendAllegatoField = value;
            }
        }

        /// <remarks/>
        public long idAllegato
        {
            get
            {
                return this.idAllegatoField;
            }
            set
            {
                this.idAllegatoField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(IsNullable = true)]
        public string tipoAllegato
        {
            get
            {
                return this.tipoAllegatoField;
            }
            set
            {
                this.tipoAllegatoField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("wsdl", "4.0.30319.1")]
    public delegate void getAllegatoCompletedEventHandler(object sender, getAllegatoCompletedEventArgs e);

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("wsdl", "4.0.30319.1")]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    public partial class getAllegatoCompletedEventArgs : System.ComponentModel.AsyncCompletedEventArgs
    {

        private object[] results;

        internal getAllegatoCompletedEventArgs(object[] results, System.Exception exception, bool cancelled, object userState) :
            base(exception, cancelled, userState)
        {
            this.results = results;
        }

        /// <remarks/>
        public Allegato Result
        {
            get
            {
                this.RaiseExceptionIfNecessary();
                return ((Allegato)(this.results[0]));
            }
        }
    }
}