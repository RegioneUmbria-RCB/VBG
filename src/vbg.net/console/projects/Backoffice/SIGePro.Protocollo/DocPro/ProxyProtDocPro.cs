using System;
using System.Web.Services;
using System.Diagnostics;
using System.Web.Services.Protocols;
using System.ComponentModel;
using System.Xml.Serialization;
using Microsoft.Web.Services2;


namespace Init.SIGePro.Protocollo.DocPro
{
    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.Web.Services", "4.0.30319.1")]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Web.Services.WebServiceBindingAttribute(Name = "SerProtoSoap", Namespace = "http://www.scap.it/")]
    public partial class ProxyProtDocPro : WebServicesClientProtocol
    {

        private System.Threading.SendOrPostCallback LoginUserOperationCompleted;

        private System.Threading.SendOrPostCallback InserimentoOperationCompleted;

        private System.Threading.SendOrPostCallback ProtocollazioneOperationCompleted;

        private bool useDefaultCredentialsSetExplicitly;

        /// <remarks/>
        public ProxyProtDocPro()
        {
            //this.Url = global::Init.SIGePro.Protocollo.Properties.Settings.Default.SIGePro_Protocollo_Empoli_SerProto;
            this.Timeout = 600000;
            if ((this.IsLocalFileSystemWebService(this.Url) == true))
            {
                this.UseDefaultCredentials = true;
                this.useDefaultCredentialsSetExplicitly = false;
            }
            else
            {
                this.useDefaultCredentialsSetExplicitly = true;
            }
        }

        public new string Url
        {
            get
            {
                return base.Url;
            }
            set
            {
                if ((((this.IsLocalFileSystemWebService(base.Url) == true)
                            && (this.useDefaultCredentialsSetExplicitly == false))
                            && (this.IsLocalFileSystemWebService(value) == false)))
                {
                    base.UseDefaultCredentials = false;
                }
                base.Url = value;
            }
        }

        public new bool UseDefaultCredentials
        {
            get
            {
                return base.UseDefaultCredentials;
            }
            set
            {
                base.UseDefaultCredentials = value;
                this.useDefaultCredentialsSetExplicitly = true;
            }
        }

        /// <remarks/>
        public event LoginUserCompletedEventHandler LoginUserCompleted;

        /// <remarks/>
        public event InserimentoCompletedEventHandler InserimentoCompleted;

        /// <remarks/>
        public event ProtocollazioneCompletedEventHandler ProtocollazioneCompleted;

        /// <remarks/>
        [System.Web.Services.Protocols.SoapDocumentMethodAttribute("http://www.scap.it/LoginUser", RequestNamespace = "http://www.scap.it/", ResponseNamespace = "http://www.scap.it/", Use = System.Web.Services.Description.SoapBindingUse.Literal, ParameterStyle = System.Web.Services.Protocols.SoapParameterStyle.Wrapped)]
        public LoginResponse LoginUser(string strCodEnte, string strUserName, string strPassword)
        {
            object[] results = this.Invoke("LoginUser", new object[] {
                        strCodEnte,
                        strUserName,
                        strPassword});
            return ((LoginResponse)(results[0]));
        }

        /// <remarks/>
        public void LoginUserAsync(string strCodEnte, string strUserName, string strPassword)
        {
            this.LoginUserAsync(strCodEnte, strUserName, strPassword, null);
        }

        /// <remarks/>
        public void LoginUserAsync(string strCodEnte, string strUserName, string strPassword, object userState)
        {
            if ((this.LoginUserOperationCompleted == null))
            {
                this.LoginUserOperationCompleted = new System.Threading.SendOrPostCallback(this.OnLoginUserOperationCompleted);
            }
            this.InvokeAsync("LoginUser", new object[] {
                        strCodEnte,
                        strUserName,
                        strPassword}, this.LoginUserOperationCompleted, userState);
        }

        private void OnLoginUserOperationCompleted(object arg)
        {
            if ((this.LoginUserCompleted != null))
            {
                System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
                this.LoginUserCompleted(this, new LoginUserCompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
            }
        }

        /// <remarks/>
        [System.Web.Services.Protocols.SoapDocumentMethodAttribute("http://www.scap.it/Inserimento", RequestNamespace = "http://www.scap.it/", ResponseNamespace = "http://www.scap.it/", Use = System.Web.Services.Description.SoapBindingUse.Literal, ParameterStyle = System.Web.Services.Protocols.SoapParameterStyle.Wrapped)]
        public _InserimentoResponse Inserimento(string strUserName, string strDST)
        {
            object[] results = this.Invoke("Inserimento", new object[] {
                        strUserName,
                        strDST});
            return ((_InserimentoResponse)(results[0]));
        }

        /// <remarks/>
        public void InserimentoAsync(string strUserName, string strDST)
        {
            this.InserimentoAsync(strUserName, strDST, null);
        }

        /// <remarks/>
        public void InserimentoAsync(string strUserName, string strDST, object userState)
        {
            if ((this.InserimentoOperationCompleted == null))
            {
                this.InserimentoOperationCompleted = new System.Threading.SendOrPostCallback(this.OnInserimentoOperationCompleted);
            }
            this.InvokeAsync("Inserimento", new object[] {
                        strUserName,
                        strDST}, this.InserimentoOperationCompleted, userState);
        }

        private void OnInserimentoOperationCompleted(object arg)
        {
            if ((this.InserimentoCompleted != null))
            {
                System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
                this.InserimentoCompleted(this, new InserimentoCompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
            }
        }

        /// <remarks/>
        [System.Web.Services.Protocols.SoapDocumentMethodAttribute("http://www.scap.it/Protocollazione", RequestNamespace = "http://www.scap.it/", ResponseNamespace = "http://www.scap.it/", Use = System.Web.Services.Description.SoapBindingUse.Literal, ParameterStyle = System.Web.Services.Protocols.SoapParameterStyle.Wrapped)]
        public _ProtocollazioneResponse Protocollazione(string strUserName, string strDST)
        {
            object[] results = this.Invoke("Protocollazione", new object[] {
                        strUserName,
                        strDST});
            return ((_ProtocollazioneResponse)(results[0]));
        }

        /// <remarks/>
        public void ProtocollazioneAsync(string strUserName, string strDST)
        {
            this.ProtocollazioneAsync(strUserName, strDST, null);
        }

        /// <remarks/>
        public void ProtocollazioneAsync(string strUserName, string strDST, object userState)
        {
            if ((this.ProtocollazioneOperationCompleted == null))
            {
                this.ProtocollazioneOperationCompleted = new System.Threading.SendOrPostCallback(this.OnProtocollazioneOperationCompleted);
            }
            this.InvokeAsync("Protocollazione", new object[] {
                        strUserName,
                        strDST}, this.ProtocollazioneOperationCompleted, userState);
        }

        private void OnProtocollazioneOperationCompleted(object arg)
        {
            if ((this.ProtocollazioneCompleted != null))
            {
                System.Web.Services.Protocols.InvokeCompletedEventArgs invokeArgs = ((System.Web.Services.Protocols.InvokeCompletedEventArgs)(arg));
                this.ProtocollazioneCompleted(this, new ProtocollazioneCompletedEventArgs(invokeArgs.Results, invokeArgs.Error, invokeArgs.Cancelled, invokeArgs.UserState));
            }
        }

        /// <remarks/>
        public new void CancelAsync(object userState)
        {
            base.CancelAsync(userState);
        }

        private bool IsLocalFileSystemWebService(string url)
        {
            if (((url == null)
                        || (url == string.Empty)))
            {
                return false;
            }
            System.Uri wsUri = new System.Uri(url);
            if (((wsUri.Port >= 1024)
                        && (string.Compare(wsUri.Host, "localHost", System.StringComparison.OrdinalIgnoreCase) == 0)))
            {
                return true;
            }
            return false;
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.Xml", "4.0.30319.233")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(Namespace = "http://www.scap.it/")]
    public partial class LoginResponse
    {

        private string strDSTField;

        private long lngErrNumberField;

        private string strErrStringField;

        /// <remarks/>
        public string strDST
        {
            get
            {
                return this.strDSTField;
            }
            set
            {
                this.strDSTField = value;
            }
        }

        /// <remarks/>
        public long lngErrNumber
        {
            get
            {
                return this.lngErrNumberField;
            }
            set
            {
                this.lngErrNumberField = value;
            }
        }

        /// <remarks/>
        public string strErrString
        {
            get
            {
                return this.strErrStringField;
            }
            set
            {
                this.strErrStringField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.Xml", "4.0.30319.233")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(Namespace = "http://www.scap.it/")]
    public partial class _ProtocollazioneResponse
    {

        private long lngNumPGField;

        private long lngAnnoPGField;

        private string strDataPGField;

        private long lngErrNumberField;

        private string strErrStringField;

        /// <remarks/>
        public long lngNumPG
        {
            get
            {
                return this.lngNumPGField;
            }
            set
            {
                this.lngNumPGField = value;
            }
        }

        /// <remarks/>
        public long lngAnnoPG
        {
            get
            {
                return this.lngAnnoPGField;
            }
            set
            {
                this.lngAnnoPGField = value;
            }
        }

        /// <remarks/>
        public string strDataPG
        {
            get
            {
                return this.strDataPGField;
            }
            set
            {
                this.strDataPGField = value;
            }
        }

        /// <remarks/>
        public long lngErrNumber
        {
            get
            {
                return this.lngErrNumberField;
            }
            set
            {
                this.lngErrNumberField = value;
            }
        }

        /// <remarks/>
        public string strErrString
        {
            get
            {
                return this.strErrStringField;
            }
            set
            {
                this.strErrStringField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.Xml", "4.0.30319.233")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(Namespace = "http://www.scap.it/")]
    public partial class _InserimentoResponse
    {

        private long lngDocIdField;

        private long lngErrNumberField;

        private string strErrStringField;

        /// <remarks/>
        public long lngDocId
        {
            get
            {
                return this.lngDocIdField;
            }
            set
            {
                this.lngDocIdField = value;
            }
        }

        /// <remarks/>
        public long lngErrNumber
        {
            get
            {
                return this.lngErrNumberField;
            }
            set
            {
                this.lngErrNumberField = value;
            }
        }

        /// <remarks/>
        public string strErrString
        {
            get
            {
                return this.strErrStringField;
            }
            set
            {
                this.strErrStringField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.Web.Services", "4.0.30319.1")]
    public delegate void LoginUserCompletedEventHandler(object sender, LoginUserCompletedEventArgs e);

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.Web.Services", "4.0.30319.1")]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    public partial class LoginUserCompletedEventArgs : System.ComponentModel.AsyncCompletedEventArgs
    {

        private object[] results;

        internal LoginUserCompletedEventArgs(object[] results, System.Exception exception, bool cancelled, object userState) :
            base(exception, cancelled, userState)
        {
            this.results = results;
        }

        /// <remarks/>
        public LoginResponse Result
        {
            get
            {
                this.RaiseExceptionIfNecessary();
                return ((LoginResponse)(this.results[0]));
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.Web.Services", "4.0.30319.1")]
    public delegate void InserimentoCompletedEventHandler(object sender, InserimentoCompletedEventArgs e);

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.Web.Services", "4.0.30319.1")]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    public partial class InserimentoCompletedEventArgs : System.ComponentModel.AsyncCompletedEventArgs
    {

        private object[] results;

        internal InserimentoCompletedEventArgs(object[] results, System.Exception exception, bool cancelled, object userState) :
            base(exception, cancelled, userState)
        {
            this.results = results;
        }

        /// <remarks/>
        public _InserimentoResponse Result
        {
            get
            {
                this.RaiseExceptionIfNecessary();
                return ((_InserimentoResponse)(this.results[0]));
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.Web.Services", "4.0.30319.1")]
    public delegate void ProtocollazioneCompletedEventHandler(object sender, ProtocollazioneCompletedEventArgs e);

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.Web.Services", "4.0.30319.1")]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    public partial class ProtocollazioneCompletedEventArgs : System.ComponentModel.AsyncCompletedEventArgs
    {

        private object[] results;

        internal ProtocollazioneCompletedEventArgs(object[] results, System.Exception exception, bool cancelled, object userState) :
            base(exception, cancelled, userState)
        {
            this.results = results;
        }

        /// <remarks/>
        public _ProtocollazioneResponse Result
        {
            get
            {
                this.RaiseExceptionIfNecessary();
                return ((_ProtocollazioneResponse)(this.results[0]));
            }
        }
    }
}