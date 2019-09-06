using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.RicercheAnagrafiche.Parix.DettaglioImpresaRidottoNs
{

	/// <remarks/>
	[System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.0.30319.1")]
	[System.SerializableAttribute()]
	[System.Diagnostics.DebuggerStepThroughAttribute()]
	[System.ComponentModel.DesignerCategoryAttribute("code")]
	[System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
	[System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
	public partial class RISPOSTA
	{

		private RISPOSTAHEADER hEADERField;

		private RISPOSTADATI dATIField;

		/// <remarks/>
		public RISPOSTAHEADER HEADER
		{
			get
			{
				return this.hEADERField;
			}
			set
			{
				this.hEADERField = value;
			}
		}

		/// <remarks/>
		public RISPOSTADATI DATI
		{
			get
			{
				return this.dATIField;
			}
			set
			{
				this.dATIField = value;
			}
		}
	}

	/// <remarks/>
	[System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.0.30319.1")]
	[System.SerializableAttribute()]
	[System.Diagnostics.DebuggerStepThroughAttribute()]
	[System.ComponentModel.DesignerCategoryAttribute("code")]
	[System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
	public partial class RISPOSTAHEADER
	{

		private string eSECUTOREField;

		private string sERVIZIOField;

		private string eSITOField;

		/// <remarks/>
		public string ESECUTORE
		{
			get
			{
				return this.eSECUTOREField;
			}
			set
			{
				this.eSECUTOREField = value;
			}
		}

		/// <remarks/>
		public string SERVIZIO
		{
			get
			{
				return this.sERVIZIOField;
			}
			set
			{
				this.sERVIZIOField = value;
			}
		}

		/// <remarks/>
		public string ESITO
		{
			get
			{
				return this.eSITOField;
			}
			set
			{
				this.eSITOField = value;
			}
		}
	}

	/// <remarks/>
	[System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.0.30319.1")]
	[System.SerializableAttribute()]
	[System.Diagnostics.DebuggerStepThroughAttribute()]
	[System.ComponentModel.DesignerCategoryAttribute("code")]
	[System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
	public partial class RISPOSTADATI
	{

		private object itemField;

		/// <remarks/>
		[System.Xml.Serialization.XmlElementAttribute("DATI_IMPRESA", typeof(RISPOSTADATIDATI_IMPRESA))]
		[System.Xml.Serialization.XmlElementAttribute("ERRORE", typeof(RISPOSTADATIERRORE))]
		public object Item
		{
			get
			{
				return this.itemField;
			}
			set
			{
				this.itemField = value;
			}
		}
	}

	/// <remarks/>
	[System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.0.30319.1")]
	[System.SerializableAttribute()]
	[System.Diagnostics.DebuggerStepThroughAttribute()]
	[System.ComponentModel.DesignerCategoryAttribute("code")]
	[System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
	public partial class RISPOSTADATIDATI_IMPRESA
	{

		private RISPOSTADATIDATI_IMPRESAESTREMI_IMPRESA eSTREMI_IMPRESAField;

		private string oGGETTO_SOCIALEField;

		private string dT_FONDAZIONEField;

		private string cODICE_FORMA_AMMVField;

		private string dESC_FORMA_AMMVField;

		private RISPOSTADATIDATI_IMPRESADURATA_SOCIETA dURATA_SOCIETAField;

		private RISPOSTADATIDATI_IMPRESACAPITALI cAPITALIField;

		private RISPOSTADATIDATI_IMPRESACAPITALE_INVESTITO cAPITALE_INVESTITOField;

		private RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDE iNFORMAZIONI_SEDEField;

		/// <remarks/>
		public RISPOSTADATIDATI_IMPRESAESTREMI_IMPRESA ESTREMI_IMPRESA
		{
			get
			{
				return this.eSTREMI_IMPRESAField;
			}
			set
			{
				this.eSTREMI_IMPRESAField = value;
			}
		}

		/// <remarks/>
		public string OGGETTO_SOCIALE
		{
			get
			{
				return this.oGGETTO_SOCIALEField;
			}
			set
			{
				this.oGGETTO_SOCIALEField = value;
			}
		}

		/// <remarks/>
		public string DT_FONDAZIONE
		{
			get
			{
				return this.dT_FONDAZIONEField;
			}
			set
			{
				this.dT_FONDAZIONEField = value;
			}
		}

		/// <remarks/>
		public string CODICE_FORMA_AMMV
		{
			get
			{
				return this.cODICE_FORMA_AMMVField;
			}
			set
			{
				this.cODICE_FORMA_AMMVField = value;
			}
		}

		/// <remarks/>
		public string DESC_FORMA_AMMV
		{
			get
			{
				return this.dESC_FORMA_AMMVField;
			}
			set
			{
				this.dESC_FORMA_AMMVField = value;
			}
		}

		/// <remarks/>
		public RISPOSTADATIDATI_IMPRESADURATA_SOCIETA DURATA_SOCIETA
		{
			get
			{
				return this.dURATA_SOCIETAField;
			}
			set
			{
				this.dURATA_SOCIETAField = value;
			}
		}

		/// <remarks/>
		public RISPOSTADATIDATI_IMPRESACAPITALI CAPITALI
		{
			get
			{
				return this.cAPITALIField;
			}
			set
			{
				this.cAPITALIField = value;
			}
		}

		/// <remarks/>
		public RISPOSTADATIDATI_IMPRESACAPITALE_INVESTITO CAPITALE_INVESTITO
		{
			get
			{
				return this.cAPITALE_INVESTITOField;
			}
			set
			{
				this.cAPITALE_INVESTITOField = value;
			}
		}

		/// <remarks/>
		public RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDE INFORMAZIONI_SEDE
		{
			get
			{
				return this.iNFORMAZIONI_SEDEField;
			}
			set
			{
				this.iNFORMAZIONI_SEDEField = value;
			}
		}
	}

	/// <remarks/>
	[System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.0.30319.1")]
	[System.SerializableAttribute()]
	[System.Diagnostics.DebuggerStepThroughAttribute()]
	[System.ComponentModel.DesignerCategoryAttribute("code")]
	[System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
	public partial class RISPOSTADATIDATI_IMPRESAESTREMI_IMPRESA
	{

		private string dENOMINAZIONEField;

		private string cODICE_FISCALEField;

		private string pARTITA_IVAField;

		private RISPOSTADATIDATI_IMPRESAESTREMI_IMPRESAFORMA_GIURIDICA fORMA_GIURIDICAField;

		private RISPOSTADATIDATI_IMPRESAESTREMI_IMPRESADATI_ISCRIZIONE_RI dATI_ISCRIZIONE_RIField;

		private RISPOSTADATIDATI_IMPRESAESTREMI_IMPRESADATI_ISCRIZIONE_REA[] dATI_ISCRIZIONE_REAField;

		/// <remarks/>
		public string DENOMINAZIONE
		{
			get
			{
				return this.dENOMINAZIONEField;
			}
			set
			{
				this.dENOMINAZIONEField = value;
			}
		}

		/// <remarks/>
		public string CODICE_FISCALE
		{
			get
			{
				return this.cODICE_FISCALEField;
			}
			set
			{
				this.cODICE_FISCALEField = value;
			}
		}

		/// <remarks/>
		public string PARTITA_IVA
		{
			get
			{
				return this.pARTITA_IVAField;
			}
			set
			{
				this.pARTITA_IVAField = value;
			}
		}

		/// <remarks/>
		public RISPOSTADATIDATI_IMPRESAESTREMI_IMPRESAFORMA_GIURIDICA FORMA_GIURIDICA
		{
			get
			{
				return this.fORMA_GIURIDICAField;
			}
			set
			{
				this.fORMA_GIURIDICAField = value;
			}
		}

		/// <remarks/>
		public RISPOSTADATIDATI_IMPRESAESTREMI_IMPRESADATI_ISCRIZIONE_RI DATI_ISCRIZIONE_RI
		{
			get
			{
				return this.dATI_ISCRIZIONE_RIField;
			}
			set
			{
				this.dATI_ISCRIZIONE_RIField = value;
			}
		}

		/// <remarks/>
		[System.Xml.Serialization.XmlElementAttribute("DATI_ISCRIZIONE_REA")]
		public RISPOSTADATIDATI_IMPRESAESTREMI_IMPRESADATI_ISCRIZIONE_REA[] DATI_ISCRIZIONE_REA
		{
			get
			{
				return this.dATI_ISCRIZIONE_REAField;
			}
			set
			{
				this.dATI_ISCRIZIONE_REAField = value;
			}
		}
	}

	/// <remarks/>
	[System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.0.30319.1")]
	[System.SerializableAttribute()]
	[System.Diagnostics.DebuggerStepThroughAttribute()]
	[System.ComponentModel.DesignerCategoryAttribute("code")]
	[System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
	public partial class RISPOSTADATIDATI_IMPRESAESTREMI_IMPRESAFORMA_GIURIDICA
	{

		private string c_FORMA_GIURIDICAField;

		private string dESCRIZIONEField;

		/// <remarks/>
		public string C_FORMA_GIURIDICA
		{
			get
			{
				return this.c_FORMA_GIURIDICAField;
			}
			set
			{
				this.c_FORMA_GIURIDICAField = value;
			}
		}

		/// <remarks/>
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
	}

	/// <remarks/>
	[System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.0.30319.1")]
	[System.SerializableAttribute()]
	[System.Diagnostics.DebuggerStepThroughAttribute()]
	[System.ComponentModel.DesignerCategoryAttribute("code")]
	[System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
	public partial class RISPOSTADATIDATI_IMPRESAESTREMI_IMPRESADATI_ISCRIZIONE_RI
	{

		private string nUMERO_RIField;

		private string dATAField;

		private RISPOSTADATIDATI_IMPRESAESTREMI_IMPRESADATI_ISCRIZIONE_RISEZIONE[] sEZIONIField;

		/// <remarks/>
		public string NUMERO_RI
		{
			get
			{
				return this.nUMERO_RIField;
			}
			set
			{
				this.nUMERO_RIField = value;
			}
		}

		/// <remarks/>
		public string DATA
		{
			get
			{
				return this.dATAField;
			}
			set
			{
				this.dATAField = value;
			}
		}

		/// <remarks/>
		[System.Xml.Serialization.XmlArrayItemAttribute("SEZIONE", IsNullable = false)]
		public RISPOSTADATIDATI_IMPRESAESTREMI_IMPRESADATI_ISCRIZIONE_RISEZIONE[] SEZIONI
		{
			get
			{
				return this.sEZIONIField;
			}
			set
			{
				this.sEZIONIField = value;
			}
		}
	}

	/// <remarks/>
	[System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.0.30319.1")]
	[System.SerializableAttribute()]
	[System.Diagnostics.DebuggerStepThroughAttribute()]
	[System.ComponentModel.DesignerCategoryAttribute("code")]
	[System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
	public partial class RISPOSTADATIDATI_IMPRESAESTREMI_IMPRESADATI_ISCRIZIONE_RISEZIONE
	{

		private string c_SEZIONEField;

		private string dESCRIZIONEField;

		private string dT_ISCRIZIONEField;

		private string cOLT_DIRETTOField;

		/// <remarks/>
		public string C_SEZIONE
		{
			get
			{
				return this.c_SEZIONEField;
			}
			set
			{
				this.c_SEZIONEField = value;
			}
		}

		/// <remarks/>
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
		public string DT_ISCRIZIONE
		{
			get
			{
				return this.dT_ISCRIZIONEField;
			}
			set
			{
				this.dT_ISCRIZIONEField = value;
			}
		}

		/// <remarks/>
		public string COLT_DIRETTO
		{
			get
			{
				return this.cOLT_DIRETTOField;
			}
			set
			{
				this.cOLT_DIRETTOField = value;
			}
		}
	}

	/// <remarks/>
	[System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.0.30319.1")]
	[System.SerializableAttribute()]
	[System.Diagnostics.DebuggerStepThroughAttribute()]
	[System.ComponentModel.DesignerCategoryAttribute("code")]
	[System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
	public partial class RISPOSTADATIDATI_IMPRESAESTREMI_IMPRESADATI_ISCRIZIONE_REA
	{

		private string nREAField;

		private string cCIAAField;

		private string fLAG_SEDEField;

		private string i_TRASFERIMENTO_SEDEField;

		private string f_AGGIORNAMENTOField;

		private string dATAField;

		private string dT_ULT_AGGIORNAMENTOField;

		private string c_FONTEField;

		private RISPOSTADATIDATI_IMPRESAESTREMI_IMPRESADATI_ISCRIZIONE_REACESSAZIONE cESSAZIONEField;

		/// <remarks/>
		public string NREA
		{
			get
			{
				return this.nREAField;
			}
			set
			{
				this.nREAField = value;
			}
		}

		/// <remarks/>
		public string CCIAA
		{
			get
			{
				return this.cCIAAField;
			}
			set
			{
				this.cCIAAField = value;
			}
		}

		/// <remarks/>
		public string FLAG_SEDE
		{
			get
			{
				return this.fLAG_SEDEField;
			}
			set
			{
				this.fLAG_SEDEField = value;
			}
		}

		/// <remarks/>
		public string I_TRASFERIMENTO_SEDE
		{
			get
			{
				return this.i_TRASFERIMENTO_SEDEField;
			}
			set
			{
				this.i_TRASFERIMENTO_SEDEField = value;
			}
		}

		/// <remarks/>
		public string F_AGGIORNAMENTO
		{
			get
			{
				return this.f_AGGIORNAMENTOField;
			}
			set
			{
				this.f_AGGIORNAMENTOField = value;
			}
		}

		/// <remarks/>
		public string DATA
		{
			get
			{
				return this.dATAField;
			}
			set
			{
				this.dATAField = value;
			}
		}

		/// <remarks/>
		public string DT_ULT_AGGIORNAMENTO
		{
			get
			{
				return this.dT_ULT_AGGIORNAMENTOField;
			}
			set
			{
				this.dT_ULT_AGGIORNAMENTOField = value;
			}
		}

		/// <remarks/>
		public string C_FONTE
		{
			get
			{
				return this.c_FONTEField;
			}
			set
			{
				this.c_FONTEField = value;
			}
		}

		/// <remarks/>
		public RISPOSTADATIDATI_IMPRESAESTREMI_IMPRESADATI_ISCRIZIONE_REACESSAZIONE CESSAZIONE
		{
			get
			{
				return this.cESSAZIONEField;
			}
			set
			{
				this.cESSAZIONEField = value;
			}
		}
	}

	/// <remarks/>
	[System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.0.30319.1")]
	[System.SerializableAttribute()]
	[System.Diagnostics.DebuggerStepThroughAttribute()]
	[System.ComponentModel.DesignerCategoryAttribute("code")]
	[System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
	public partial class RISPOSTADATIDATI_IMPRESAESTREMI_IMPRESADATI_ISCRIZIONE_REACESSAZIONE
	{

		private string dT_CANCELLAZIONEField;

		private string dT_CESSAZIONEField;

		private string dT_DENUNCIA_CESSField;

		private string cAUSALEField;

		/// <remarks/>
		public string DT_CANCELLAZIONE
		{
			get
			{
				return this.dT_CANCELLAZIONEField;
			}
			set
			{
				this.dT_CANCELLAZIONEField = value;
			}
		}

		/// <remarks/>
		public string DT_CESSAZIONE
		{
			get
			{
				return this.dT_CESSAZIONEField;
			}
			set
			{
				this.dT_CESSAZIONEField = value;
			}
		}

		/// <remarks/>
		public string DT_DENUNCIA_CESS
		{
			get
			{
				return this.dT_DENUNCIA_CESSField;
			}
			set
			{
				this.dT_DENUNCIA_CESSField = value;
			}
		}

		/// <remarks/>
		public string CAUSALE
		{
			get
			{
				return this.cAUSALEField;
			}
			set
			{
				this.cAUSALEField = value;
			}
		}
	}

	/// <remarks/>
	[System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.0.30319.1")]
	[System.SerializableAttribute()]
	[System.Diagnostics.DebuggerStepThroughAttribute()]
	[System.ComponentModel.DesignerCategoryAttribute("code")]
	[System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
	public partial class RISPOSTADATIDATI_IMPRESADURATA_SOCIETA
	{

		private string dT_COSTITUZIONEField;

		private string dT_TERMINEField;

		private string dURATA_ILLIMITATAField;

		private RISPOSTADATIDATI_IMPRESADURATA_SOCIETASCADENZE_ESERCIZI sCADENZE_ESERCIZIField;

		/// <remarks/>
		public string DT_COSTITUZIONE
		{
			get
			{
				return this.dT_COSTITUZIONEField;
			}
			set
			{
				this.dT_COSTITUZIONEField = value;
			}
		}

		/// <remarks/>
		public string DT_TERMINE
		{
			get
			{
				return this.dT_TERMINEField;
			}
			set
			{
				this.dT_TERMINEField = value;
			}
		}

		/// <remarks/>
		public string DURATA_ILLIMITATA
		{
			get
			{
				return this.dURATA_ILLIMITATAField;
			}
			set
			{
				this.dURATA_ILLIMITATAField = value;
			}
		}

		/// <remarks/>
		public RISPOSTADATIDATI_IMPRESADURATA_SOCIETASCADENZE_ESERCIZI SCADENZE_ESERCIZI
		{
			get
			{
				return this.sCADENZE_ESERCIZIField;
			}
			set
			{
				this.sCADENZE_ESERCIZIField = value;
			}
		}
	}

	/// <remarks/>
	[System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.0.30319.1")]
	[System.SerializableAttribute()]
	[System.Diagnostics.DebuggerStepThroughAttribute()]
	[System.ComponentModel.DesignerCategoryAttribute("code")]
	[System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
	public partial class RISPOSTADATIDATI_IMPRESADURATA_SOCIETASCADENZE_ESERCIZI
	{

		private string dT_PRIMO_ESERCIZIOField;

		private string dT_SUCCESSIVEField;

		/// <remarks/>
		public string DT_PRIMO_ESERCIZIO
		{
			get
			{
				return this.dT_PRIMO_ESERCIZIOField;
			}
			set
			{
				this.dT_PRIMO_ESERCIZIOField = value;
			}
		}

		/// <remarks/>
		public string DT_SUCCESSIVE
		{
			get
			{
				return this.dT_SUCCESSIVEField;
			}
			set
			{
				this.dT_SUCCESSIVEField = value;
			}
		}
	}

	/// <remarks/>
	[System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.0.30319.1")]
	[System.SerializableAttribute()]
	[System.Diagnostics.DebuggerStepThroughAttribute()]
	[System.ComponentModel.DesignerCategoryAttribute("code")]
	[System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
	public partial class RISPOSTADATIDATI_IMPRESACAPITALI
	{

		private RISPOSTADATIDATI_IMPRESACAPITALIFONDO_CONSORTILE fONDO_CONSORTILEField;

		private RISPOSTADATIDATI_IMPRESACAPITALITOTALE_QUOTE tOTALE_QUOTEField;

		private RISPOSTADATIDATI_IMPRESACAPITALICAPITALE_SOCIALE cAPITALE_SOCIALEField;

		/// <remarks/>
		public RISPOSTADATIDATI_IMPRESACAPITALIFONDO_CONSORTILE FONDO_CONSORTILE
		{
			get
			{
				return this.fONDO_CONSORTILEField;
			}
			set
			{
				this.fONDO_CONSORTILEField = value;
			}
		}

		/// <remarks/>
		public RISPOSTADATIDATI_IMPRESACAPITALITOTALE_QUOTE TOTALE_QUOTE
		{
			get
			{
				return this.tOTALE_QUOTEField;
			}
			set
			{
				this.tOTALE_QUOTEField = value;
			}
		}

		/// <remarks/>
		public RISPOSTADATIDATI_IMPRESACAPITALICAPITALE_SOCIALE CAPITALE_SOCIALE
		{
			get
			{
				return this.cAPITALE_SOCIALEField;
			}
			set
			{
				this.cAPITALE_SOCIALEField = value;
			}
		}
	}

	/// <remarks/>
	[System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.0.30319.1")]
	[System.SerializableAttribute()]
	[System.Diagnostics.DebuggerStepThroughAttribute()]
	[System.ComponentModel.DesignerCategoryAttribute("code")]
	[System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
	public partial class RISPOSTADATIDATI_IMPRESACAPITALIFONDO_CONSORTILE
	{

		private string aMMONTAREField;

		private string vALUTAField;

		/// <remarks/>
		public string AMMONTARE
		{
			get
			{
				return this.aMMONTAREField;
			}
			set
			{
				this.aMMONTAREField = value;
			}
		}

		/// <remarks/>
		public string VALUTA
		{
			get
			{
				return this.vALUTAField;
			}
			set
			{
				this.vALUTAField = value;
			}
		}
	}

	/// <remarks/>
	[System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.0.30319.1")]
	[System.SerializableAttribute()]
	[System.Diagnostics.DebuggerStepThroughAttribute()]
	[System.ComponentModel.DesignerCategoryAttribute("code")]
	[System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
	public partial class RISPOSTADATIDATI_IMPRESACAPITALITOTALE_QUOTE
	{

		private string nUMERO_AZIONIField;

		private string nUMERO_QUOTEField;

		private string aMMONTAREField;

		private string vALUTAField;

		/// <remarks/>
		public string NUMERO_AZIONI
		{
			get
			{
				return this.nUMERO_AZIONIField;
			}
			set
			{
				this.nUMERO_AZIONIField = value;
			}
		}

		/// <remarks/>
		public string NUMERO_QUOTE
		{
			get
			{
				return this.nUMERO_QUOTEField;
			}
			set
			{
				this.nUMERO_QUOTEField = value;
			}
		}

		/// <remarks/>
		public string AMMONTARE
		{
			get
			{
				return this.aMMONTAREField;
			}
			set
			{
				this.aMMONTAREField = value;
			}
		}

		/// <remarks/>
		public string VALUTA
		{
			get
			{
				return this.vALUTAField;
			}
			set
			{
				this.vALUTAField = value;
			}
		}
	}

	/// <remarks/>
	[System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.0.30319.1")]
	[System.SerializableAttribute()]
	[System.Diagnostics.DebuggerStepThroughAttribute()]
	[System.ComponentModel.DesignerCategoryAttribute("code")]
	[System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
	public partial class RISPOSTADATIDATI_IMPRESACAPITALICAPITALE_SOCIALE
	{

		private string dELIBERATOField;

		private string sOTTOSCRITTOField;

		private string vERSATOField;

		private string tIPO_CONFERIMENTIField;

		private string vALUTAField;

		/// <remarks/>
		public string DELIBERATO
		{
			get
			{
				return this.dELIBERATOField;
			}
			set
			{
				this.dELIBERATOField = value;
			}
		}

		/// <remarks/>
		public string SOTTOSCRITTO
		{
			get
			{
				return this.sOTTOSCRITTOField;
			}
			set
			{
				this.sOTTOSCRITTOField = value;
			}
		}

		/// <remarks/>
		public string VERSATO
		{
			get
			{
				return this.vERSATOField;
			}
			set
			{
				this.vERSATOField = value;
			}
		}

		/// <remarks/>
		public string TIPO_CONFERIMENTI
		{
			get
			{
				return this.tIPO_CONFERIMENTIField;
			}
			set
			{
				this.tIPO_CONFERIMENTIField = value;
			}
		}

		/// <remarks/>
		public string VALUTA
		{
			get
			{
				return this.vALUTAField;
			}
			set
			{
				this.vALUTAField = value;
			}
		}
	}

	/// <remarks/>
	[System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.0.30319.1")]
	[System.SerializableAttribute()]
	[System.Diagnostics.DebuggerStepThroughAttribute()]
	[System.ComponentModel.DesignerCategoryAttribute("code")]
	[System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
	public partial class RISPOSTADATIDATI_IMPRESACAPITALE_INVESTITO
	{

		private string aMMONTAREField;

		private string vALUTAField;

		/// <remarks/>
		public string AMMONTARE
		{
			get
			{
				return this.aMMONTAREField;
			}
			set
			{
				this.aMMONTAREField = value;
			}
		}

		/// <remarks/>
		public string VALUTA
		{
			get
			{
				return this.vALUTAField;
			}
			set
			{
				this.vALUTAField = value;
			}
		}
	}

	/// <remarks/>
	[System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.0.30319.1")]
	[System.SerializableAttribute()]
	[System.Diagnostics.DebuggerStepThroughAttribute()]
	[System.ComponentModel.DesignerCategoryAttribute("code")]
	[System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
	public partial class RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDE
	{

		private string iNSEGNAField;

		private RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDEINDIRIZZO iNDIRIZZOField;

		private string aTTIVITAField;

		private RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDEINFO_ATTIVITA iNFO_ATTIVITAField;

		private RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDEATTIVITA_ISTAT[] cODICI_ISTAT_02Field;

		private RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDEATTIVITA_ISTAT1[] cODICE_ATECO_ULField;

		private RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDERUOLO_LOC[] rUOLI_LOCField;

		private RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDEDATI_ARTIGIANI dATI_ARTIGIANIField;

		private RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDECOMMERCIO_DETTAGLIO cOMMERCIO_DETTAGLIOField;

		private RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDECESSAZIONE cESSAZIONEField;

		private RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDEPROCEDURE_CONCORSUALI pROCEDURE_CONCORSUALIField;

		/// <remarks/>
		public string INSEGNA
		{
			get
			{
				return this.iNSEGNAField;
			}
			set
			{
				this.iNSEGNAField = value;
			}
		}

		/// <remarks/>
		public RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDEINDIRIZZO INDIRIZZO
		{
			get
			{
				return this.iNDIRIZZOField;
			}
			set
			{
				this.iNDIRIZZOField = value;
			}
		}

		/// <remarks/>
		public string ATTIVITA
		{
			get
			{
				return this.aTTIVITAField;
			}
			set
			{
				this.aTTIVITAField = value;
			}
		}

		/// <remarks/>
		public RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDEINFO_ATTIVITA INFO_ATTIVITA
		{
			get
			{
				return this.iNFO_ATTIVITAField;
			}
			set
			{
				this.iNFO_ATTIVITAField = value;
			}
		}

		/// <remarks/>
		[System.Xml.Serialization.XmlArrayItemAttribute("ATTIVITA_ISTAT", IsNullable = false)]
		public RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDEATTIVITA_ISTAT[] CODICI_ISTAT_02
		{
			get
			{
				return this.cODICI_ISTAT_02Field;
			}
			set
			{
				this.cODICI_ISTAT_02Field = value;
			}
		}

		/// <remarks/>
		[System.Xml.Serialization.XmlArrayItemAttribute("ATTIVITA_ISTAT", IsNullable = false)]
		public RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDEATTIVITA_ISTAT1[] CODICE_ATECO_UL
		{
			get
			{
				return this.cODICE_ATECO_ULField;
			}
			set
			{
				this.cODICE_ATECO_ULField = value;
			}
		}

		/// <remarks/>
		[System.Xml.Serialization.XmlArrayItemAttribute("RUOLO_LOC", IsNullable = false)]
		public RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDERUOLO_LOC[] RUOLI_LOC
		{
			get
			{
				return this.rUOLI_LOCField;
			}
			set
			{
				this.rUOLI_LOCField = value;
			}
		}

		/// <remarks/>
		public RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDEDATI_ARTIGIANI DATI_ARTIGIANI
		{
			get
			{
				return this.dATI_ARTIGIANIField;
			}
			set
			{
				this.dATI_ARTIGIANIField = value;
			}
		}

		/// <remarks/>
		public RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDECOMMERCIO_DETTAGLIO COMMERCIO_DETTAGLIO
		{
			get
			{
				return this.cOMMERCIO_DETTAGLIOField;
			}
			set
			{
				this.cOMMERCIO_DETTAGLIOField = value;
			}
		}

		/// <remarks/>
		public RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDECESSAZIONE CESSAZIONE
		{
			get
			{
				return this.cESSAZIONEField;
			}
			set
			{
				this.cESSAZIONEField = value;
			}
		}

		/// <remarks/>
		public RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDEPROCEDURE_CONCORSUALI PROCEDURE_CONCORSUALI
		{
			get
			{
				return this.pROCEDURE_CONCORSUALIField;
			}
			set
			{
				this.pROCEDURE_CONCORSUALIField = value;
			}
		}
	}

	/// <remarks/>
	[System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.0.30319.1")]
	[System.SerializableAttribute()]
	[System.Diagnostics.DebuggerStepThroughAttribute()]
	[System.ComponentModel.DesignerCategoryAttribute("code")]
	[System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
	public partial class RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDEINDIRIZZO
	{

		private string pROVINCIAField;

		private string cOMUNEField;

		private string c_COMUNEField;

		private string tOPONIMOField;

		private string vIAField;

		private string n_CIVICOField;

		private string cAPField;

		private string sTATOField;

		private string fRAZIONEField;

		private string aLTRE_INDICAZIONIField;

		private string sTRADARIOField;

		private string tELEFONOField;

		private string fAXField;

		private string INDIRIZZO_PECField;

		/// <remarks/>
		public string PROVINCIA
		{
			get
			{
				return this.pROVINCIAField;
			}
			set
			{
				this.pROVINCIAField = value;
			}
		}

		/// <remarks/>
		public string COMUNE
		{
			get
			{
				return this.cOMUNEField;
			}
			set
			{
				this.cOMUNEField = value;
			}
		}

		/// <remarks/>
		public string C_COMUNE
		{
			get
			{
				return this.c_COMUNEField;
			}
			set
			{
				this.c_COMUNEField = value;
			}
		}

		/// <remarks/>
		public string TOPONIMO
		{
			get
			{
				return this.tOPONIMOField;
			}
			set
			{
				this.tOPONIMOField = value;
			}
		}

		/// <remarks/>
		public string VIA
		{
			get
			{
				return this.vIAField;
			}
			set
			{
				this.vIAField = value;
			}
		}

		/// <remarks/>
		public string N_CIVICO
		{
			get
			{
				return this.n_CIVICOField;
			}
			set
			{
				this.n_CIVICOField = value;
			}
		}

		/// <remarks/>
		public string CAP
		{
			get
			{
				return this.cAPField;
			}
			set
			{
				this.cAPField = value;
			}
		}

		/// <remarks/>
		public string STATO
		{
			get
			{
				return this.sTATOField;
			}
			set
			{
				this.sTATOField = value;
			}
		}

		/// <remarks/>
		public string FRAZIONE
		{
			get
			{
				return this.fRAZIONEField;
			}
			set
			{
				this.fRAZIONEField = value;
			}
		}

		/// <remarks/>
		public string ALTRE_INDICAZIONI
		{
			get
			{
				return this.aLTRE_INDICAZIONIField;
			}
			set
			{
				this.aLTRE_INDICAZIONIField = value;
			}
		}

		/// <remarks/>
		public string STRADARIO
		{
			get
			{
				return this.sTRADARIOField;
			}
			set
			{
				this.sTRADARIOField = value;
			}
		}

		/// <remarks/>
		public string TELEFONO
		{
			get
			{
				return this.tELEFONOField;
			}
			set
			{
				this.tELEFONOField = value;
			}
		}

		/// <remarks/>
		public string FAX
		{
			get
			{
				return this.fAXField;
			}
			set
			{
				this.fAXField = value;
			}
		}

		/// <remarks/>
		public string INDIRIZZO_PEC
		{
			get
			{
				return this.INDIRIZZO_PECField;
			}
			set
			{
				this.INDIRIZZO_PECField = value;
			}
		}
		
	}

	/// <remarks/>
	[System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.0.30319.1")]
	[System.SerializableAttribute()]
	[System.Diagnostics.DebuggerStepThroughAttribute()]
	[System.ComponentModel.DesignerCategoryAttribute("code")]
	[System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
	public partial class RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDEINFO_ATTIVITA
	{

		private string dT_INIZIO_ATTIVITAField;

		private string sTATO_ATTIVITAField;

		/// <remarks/>
		public string DT_INIZIO_ATTIVITA
		{
			get
			{
				return this.dT_INIZIO_ATTIVITAField;
			}
			set
			{
				this.dT_INIZIO_ATTIVITAField = value;
			}
		}

		/// <remarks/>
		public string STATO_ATTIVITA
		{
			get
			{
				return this.sTATO_ATTIVITAField;
			}
			set
			{
				this.sTATO_ATTIVITAField = value;
			}
		}
	}

	/// <remarks/>
	[System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.0.30319.1")]
	[System.SerializableAttribute()]
	[System.Diagnostics.DebuggerStepThroughAttribute()]
	[System.ComponentModel.DesignerCategoryAttribute("code")]
	[System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
	public partial class RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDEATTIVITA_ISTAT
	{

		private string c_ATTIVITAField;

		private string dESC_ATTIVITAField;

		private string c_IMPORTANZAField;

		private string dT_INIZIO_ATTIVITAField;

		/// <remarks/>
		public string C_ATTIVITA
		{
			get
			{
				return this.c_ATTIVITAField;
			}
			set
			{
				this.c_ATTIVITAField = value;
			}
		}

		/// <remarks/>
		public string DESC_ATTIVITA
		{
			get
			{
				return this.dESC_ATTIVITAField;
			}
			set
			{
				this.dESC_ATTIVITAField = value;
			}
		}

		/// <remarks/>
		public string C_IMPORTANZA
		{
			get
			{
				return this.c_IMPORTANZAField;
			}
			set
			{
				this.c_IMPORTANZAField = value;
			}
		}

		/// <remarks/>
		public string DT_INIZIO_ATTIVITA
		{
			get
			{
				return this.dT_INIZIO_ATTIVITAField;
			}
			set
			{
				this.dT_INIZIO_ATTIVITAField = value;
			}
		}
	}

	/// <remarks/>
	[System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.0.30319.1")]
	[System.SerializableAttribute()]
	[System.Diagnostics.DebuggerStepThroughAttribute()]
	[System.ComponentModel.DesignerCategoryAttribute("code")]
	[System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
	public partial class RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDEATTIVITA_ISTAT1
	{

		private string c_ATTIVITAField;

		private string t_CODIFICAField;

		private string dESC_ATTIVITAField;

		private string c_IMPORTANZAField;

		private string dT_INIZIO_ATTIVITAField;

		/// <remarks/>
		public string C_ATTIVITA
		{
			get
			{
				return this.c_ATTIVITAField;
			}
			set
			{
				this.c_ATTIVITAField = value;
			}
		}

		/// <remarks/>
		public string T_CODIFICA
		{
			get
			{
				return this.t_CODIFICAField;
			}
			set
			{
				this.t_CODIFICAField = value;
			}
		}

		/// <remarks/>
		public string DESC_ATTIVITA
		{
			get
			{
				return this.dESC_ATTIVITAField;
			}
			set
			{
				this.dESC_ATTIVITAField = value;
			}
		}

		/// <remarks/>
		public string C_IMPORTANZA
		{
			get
			{
				return this.c_IMPORTANZAField;
			}
			set
			{
				this.c_IMPORTANZAField = value;
			}
		}

		/// <remarks/>
		public string DT_INIZIO_ATTIVITA
		{
			get
			{
				return this.dT_INIZIO_ATTIVITAField;
			}
			set
			{
				this.dT_INIZIO_ATTIVITAField = value;
			}
		}
	}

	/// <remarks/>
	[System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.0.30319.1")]
	[System.SerializableAttribute()]
	[System.Diagnostics.DebuggerStepThroughAttribute()]
	[System.ComponentModel.DesignerCategoryAttribute("code")]
	[System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
	public partial class RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDERUOLO_LOC
	{

		private RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDERUOLO_LOCIMPIANTISTI_LOC iMPIANTISTI_LOCField;

		private RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDERUOLO_LOCMECCANICI mECCANICIField;

		private RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDERUOLO_LOCIMPRESE_PULIZIA iMPRESE_PULIZIAField;

		private RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDERUOLO_LOCALTRO_RUOLO_LOC aLTRO_RUOLO_LOCField;

		/// <remarks/>
		public RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDERUOLO_LOCIMPIANTISTI_LOC IMPIANTISTI_LOC
		{
			get
			{
				return this.iMPIANTISTI_LOCField;
			}
			set
			{
				this.iMPIANTISTI_LOCField = value;
			}
		}

		/// <remarks/>
		public RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDERUOLO_LOCMECCANICI MECCANICI
		{
			get
			{
				return this.mECCANICIField;
			}
			set
			{
				this.mECCANICIField = value;
			}
		}

		/// <remarks/>
		public RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDERUOLO_LOCIMPRESE_PULIZIA IMPRESE_PULIZIA
		{
			get
			{
				return this.iMPRESE_PULIZIAField;
			}
			set
			{
				this.iMPRESE_PULIZIAField = value;
			}
		}

		/// <remarks/>
		public RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDERUOLO_LOCALTRO_RUOLO_LOC ALTRO_RUOLO_LOC
		{
			get
			{
				return this.aLTRO_RUOLO_LOCField;
			}
			set
			{
				this.aLTRO_RUOLO_LOCField = value;
			}
		}
	}

	/// <remarks/>
	[System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.0.30319.1")]
	[System.SerializableAttribute()]
	[System.Diagnostics.DebuggerStepThroughAttribute()]
	[System.ComponentModel.DesignerCategoryAttribute("code")]
	[System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
	public partial class RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDERUOLO_LOCIMPIANTISTI_LOC
	{

		private string lETTERAField;

		private string pROVINCIAField;

		private string nUMEROField;

		private string dT_ISCRIZIONEField;

		private string eNTE_RILASCIOField;

		/// <remarks/>
		public string LETTERA
		{
			get
			{
				return this.lETTERAField;
			}
			set
			{
				this.lETTERAField = value;
			}
		}

		/// <remarks/>
		public string PROVINCIA
		{
			get
			{
				return this.pROVINCIAField;
			}
			set
			{
				this.pROVINCIAField = value;
			}
		}

		/// <remarks/>
		public string NUMERO
		{
			get
			{
				return this.nUMEROField;
			}
			set
			{
				this.nUMEROField = value;
			}
		}

		/// <remarks/>
		public string DT_ISCRIZIONE
		{
			get
			{
				return this.dT_ISCRIZIONEField;
			}
			set
			{
				this.dT_ISCRIZIONEField = value;
			}
		}

		/// <remarks/>
		public string ENTE_RILASCIO
		{
			get
			{
				return this.eNTE_RILASCIOField;
			}
			set
			{
				this.eNTE_RILASCIOField = value;
			}
		}
	}

	/// <remarks/>
	[System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.0.30319.1")]
	[System.SerializableAttribute()]
	[System.Diagnostics.DebuggerStepThroughAttribute()]
	[System.ComponentModel.DesignerCategoryAttribute("code")]
	[System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
	public partial class RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDERUOLO_LOCMECCANICI
	{

		private string tIPOField;

		private string pROVINCIAField;

		private string nUMEROField;

		private string dT_ISCRIZIONEField;

		private string qUALIFICAField;

		/// <remarks/>
		public string TIPO
		{
			get
			{
				return this.tIPOField;
			}
			set
			{
				this.tIPOField = value;
			}
		}

		/// <remarks/>
		public string PROVINCIA
		{
			get
			{
				return this.pROVINCIAField;
			}
			set
			{
				this.pROVINCIAField = value;
			}
		}

		/// <remarks/>
		public string NUMERO
		{
			get
			{
				return this.nUMEROField;
			}
			set
			{
				this.nUMEROField = value;
			}
		}

		/// <remarks/>
		public string DT_ISCRIZIONE
		{
			get
			{
				return this.dT_ISCRIZIONEField;
			}
			set
			{
				this.dT_ISCRIZIONEField = value;
			}
		}

		/// <remarks/>
		public string QUALIFICA
		{
			get
			{
				return this.qUALIFICAField;
			}
			set
			{
				this.qUALIFICAField = value;
			}
		}
	}

	/// <remarks/>
	[System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.0.30319.1")]
	[System.SerializableAttribute()]
	[System.Diagnostics.DebuggerStepThroughAttribute()]
	[System.ComponentModel.DesignerCategoryAttribute("code")]
	[System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
	public partial class RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDERUOLO_LOCIMPRESE_PULIZIA
	{

		private string fASCIAField;

		private string dT_DENUNCIAField;

		private string vOLUMEField;

		/// <remarks/>
		public string FASCIA
		{
			get
			{
				return this.fASCIAField;
			}
			set
			{
				this.fASCIAField = value;
			}
		}

		/// <remarks/>
		public string DT_DENUNCIA
		{
			get
			{
				return this.dT_DENUNCIAField;
			}
			set
			{
				this.dT_DENUNCIAField = value;
			}
		}

		/// <remarks/>
		public string VOLUME
		{
			get
			{
				return this.vOLUMEField;
			}
			set
			{
				this.vOLUMEField = value;
			}
		}
	}

	/// <remarks/>
	[System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.0.30319.1")]
	[System.SerializableAttribute()]
	[System.Diagnostics.DebuggerStepThroughAttribute()]
	[System.ComponentModel.DesignerCategoryAttribute("code")]
	[System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
	public partial class RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDERUOLO_LOCALTRO_RUOLO_LOC
	{

		private string c_RUOLOField;

		private string dESCRIZIONEField;

		private string uLT_DESCRIZIONEField;

		private string nUMEROField;

		private string dT_ISCRIZIONEField;

		private RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDERUOLO_LOCALTRO_RUOLO_LOCALTRO_RUOLO_NON_CCIAA aLTRO_RUOLO_NON_CCIAAField;

		private RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDERUOLO_LOCALTRO_RUOLO_LOCALTRO_RUOLO_CCIAA aLTRO_RUOLO_CCIAAField;

		private RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDERUOLO_LOCALTRO_RUOLO_LOCCESSAZIONE_RUOLO cESSAZIONE_RUOLOField;

		/// <remarks/>
		public string C_RUOLO
		{
			get
			{
				return this.c_RUOLOField;
			}
			set
			{
				this.c_RUOLOField = value;
			}
		}

		/// <remarks/>
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
		public string ULT_DESCRIZIONE
		{
			get
			{
				return this.uLT_DESCRIZIONEField;
			}
			set
			{
				this.uLT_DESCRIZIONEField = value;
			}
		}

		/// <remarks/>
		public string NUMERO
		{
			get
			{
				return this.nUMEROField;
			}
			set
			{
				this.nUMEROField = value;
			}
		}

		/// <remarks/>
		public string DT_ISCRIZIONE
		{
			get
			{
				return this.dT_ISCRIZIONEField;
			}
			set
			{
				this.dT_ISCRIZIONEField = value;
			}
		}

		/// <remarks/>
		public RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDERUOLO_LOCALTRO_RUOLO_LOCALTRO_RUOLO_NON_CCIAA ALTRO_RUOLO_NON_CCIAA
		{
			get
			{
				return this.aLTRO_RUOLO_NON_CCIAAField;
			}
			set
			{
				this.aLTRO_RUOLO_NON_CCIAAField = value;
			}
		}

		/// <remarks/>
		public RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDERUOLO_LOCALTRO_RUOLO_LOCALTRO_RUOLO_CCIAA ALTRO_RUOLO_CCIAA
		{
			get
			{
				return this.aLTRO_RUOLO_CCIAAField;
			}
			set
			{
				this.aLTRO_RUOLO_CCIAAField = value;
			}
		}

		/// <remarks/>
		public RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDERUOLO_LOCALTRO_RUOLO_LOCCESSAZIONE_RUOLO CESSAZIONE_RUOLO
		{
			get
			{
				return this.cESSAZIONE_RUOLOField;
			}
			set
			{
				this.cESSAZIONE_RUOLOField = value;
			}
		}
	}

	/// <remarks/>
	[System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.0.30319.1")]
	[System.SerializableAttribute()]
	[System.Diagnostics.DebuggerStepThroughAttribute()]
	[System.ComponentModel.DesignerCategoryAttribute("code")]
	[System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
	public partial class RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDERUOLO_LOCALTRO_RUOLO_LOCALTRO_RUOLO_NON_CCIAA
	{

		private string fORMAField;

		private string eNTE_RILASCIOField;

		private string pROVINCIAField;

		/// <remarks/>
		public string FORMA
		{
			get
			{
				return this.fORMAField;
			}
			set
			{
				this.fORMAField = value;
			}
		}

		/// <remarks/>
		public string ENTE_RILASCIO
		{
			get
			{
				return this.eNTE_RILASCIOField;
			}
			set
			{
				this.eNTE_RILASCIOField = value;
			}
		}

		/// <remarks/>
		public string PROVINCIA
		{
			get
			{
				return this.pROVINCIAField;
			}
			set
			{
				this.pROVINCIAField = value;
			}
		}
	}

	/// <remarks/>
	[System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.0.30319.1")]
	[System.SerializableAttribute()]
	[System.Diagnostics.DebuggerStepThroughAttribute()]
	[System.ComponentModel.DesignerCategoryAttribute("code")]
	[System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
	public partial class RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDERUOLO_LOCALTRO_RUOLO_LOCALTRO_RUOLO_CCIAA
	{

		private string cATEGORIAField;

		private string fORMAField;

		private string pROVINCIAField;

		/// <remarks/>
		public string CATEGORIA
		{
			get
			{
				return this.cATEGORIAField;
			}
			set
			{
				this.cATEGORIAField = value;
			}
		}

		/// <remarks/>
		public string FORMA
		{
			get
			{
				return this.fORMAField;
			}
			set
			{
				this.fORMAField = value;
			}
		}

		/// <remarks/>
		public string PROVINCIA
		{
			get
			{
				return this.pROVINCIAField;
			}
			set
			{
				this.pROVINCIAField = value;
			}
		}
	}

	/// <remarks/>
	[System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.0.30319.1")]
	[System.SerializableAttribute()]
	[System.Diagnostics.DebuggerStepThroughAttribute()]
	[System.ComponentModel.DesignerCategoryAttribute("code")]
	[System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
	public partial class RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDERUOLO_LOCALTRO_RUOLO_LOCCESSAZIONE_RUOLO
	{

		private string cAUSALE_CESSAZIONEField;

		private string dT_DOMANDAField;

		private string dT_DELIBERAField;

		private string dT_CESSAZIONEField;

		/// <remarks/>
		public string CAUSALE_CESSAZIONE
		{
			get
			{
				return this.cAUSALE_CESSAZIONEField;
			}
			set
			{
				this.cAUSALE_CESSAZIONEField = value;
			}
		}

		/// <remarks/>
		public string DT_DOMANDA
		{
			get
			{
				return this.dT_DOMANDAField;
			}
			set
			{
				this.dT_DOMANDAField = value;
			}
		}

		/// <remarks/>
		public string DT_DELIBERA
		{
			get
			{
				return this.dT_DELIBERAField;
			}
			set
			{
				this.dT_DELIBERAField = value;
			}
		}

		/// <remarks/>
		public string DT_CESSAZIONE
		{
			get
			{
				return this.dT_CESSAZIONEField;
			}
			set
			{
				this.dT_CESSAZIONEField = value;
			}
		}
	}

	/// <remarks/>
	[System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.0.30319.1")]
	[System.SerializableAttribute()]
	[System.Diagnostics.DebuggerStepThroughAttribute()]
	[System.ComponentModel.DesignerCategoryAttribute("code")]
	[System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
	public partial class RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDEDATI_ARTIGIANI
	{

		private string n_AAField;

		private RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDEDATI_ARTIGIANIISCRIZIONE_ARTI iSCRIZIONE_ARTIField;

		private string dT_INIZIO_ATTIVITAField;

		private RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDEDATI_ARTIGIANICESSAZIONE_ARTI cESSAZIONE_ARTIField;

		/// <remarks/>
		public string N_AA
		{
			get
			{
				return this.n_AAField;
			}
			set
			{
				this.n_AAField = value;
			}
		}

		/// <remarks/>
		public RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDEDATI_ARTIGIANIISCRIZIONE_ARTI ISCRIZIONE_ARTI
		{
			get
			{
				return this.iSCRIZIONE_ARTIField;
			}
			set
			{
				this.iSCRIZIONE_ARTIField = value;
			}
		}

		/// <remarks/>
		public string DT_INIZIO_ATTIVITA
		{
			get
			{
				return this.dT_INIZIO_ATTIVITAField;
			}
			set
			{
				this.dT_INIZIO_ATTIVITAField = value;
			}
		}

		/// <remarks/>
		public RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDEDATI_ARTIGIANICESSAZIONE_ARTI CESSAZIONE_ARTI
		{
			get
			{
				return this.cESSAZIONE_ARTIField;
			}
			set
			{
				this.cESSAZIONE_ARTIField = value;
			}
		}
	}

	/// <remarks/>
	[System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.0.30319.1")]
	[System.SerializableAttribute()]
	[System.Diagnostics.DebuggerStepThroughAttribute()]
	[System.ComponentModel.DesignerCategoryAttribute("code")]
	[System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
	public partial class RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDEDATI_ARTIGIANIISCRIZIONE_ARTI
	{

		private string pROVINCIAField;

		private string dT_DOMANDAField;

		private string dT_DELIBERAField;

		/// <remarks/>
		public string PROVINCIA
		{
			get
			{
				return this.pROVINCIAField;
			}
			set
			{
				this.pROVINCIAField = value;
			}
		}

		/// <remarks/>
		public string DT_DOMANDA
		{
			get
			{
				return this.dT_DOMANDAField;
			}
			set
			{
				this.dT_DOMANDAField = value;
			}
		}

		/// <remarks/>
		public string DT_DELIBERA
		{
			get
			{
				return this.dT_DELIBERAField;
			}
			set
			{
				this.dT_DELIBERAField = value;
			}
		}
	}

	/// <remarks/>
	[System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.0.30319.1")]
	[System.SerializableAttribute()]
	[System.Diagnostics.DebuggerStepThroughAttribute()]
	[System.ComponentModel.DesignerCategoryAttribute("code")]
	[System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
	public partial class RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDEDATI_ARTIGIANICESSAZIONE_ARTI
	{

		private string cAUSALEField;

		private string dT_DOMANDAField;

		private string dT_DELIBERAField;

		private string dT_CESSAZIONEField;

		/// <remarks/>
		public string CAUSALE
		{
			get
			{
				return this.cAUSALEField;
			}
			set
			{
				this.cAUSALEField = value;
			}
		}

		/// <remarks/>
		public string DT_DOMANDA
		{
			get
			{
				return this.dT_DOMANDAField;
			}
			set
			{
				this.dT_DOMANDAField = value;
			}
		}

		/// <remarks/>
		public string DT_DELIBERA
		{
			get
			{
				return this.dT_DELIBERAField;
			}
			set
			{
				this.dT_DELIBERAField = value;
			}
		}

		/// <remarks/>
		public string DT_CESSAZIONE
		{
			get
			{
				return this.dT_CESSAZIONEField;
			}
			set
			{
				this.dT_CESSAZIONEField = value;
			}
		}
	}

	/// <remarks/>
	[System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.0.30319.1")]
	[System.SerializableAttribute()]
	[System.Diagnostics.DebuggerStepThroughAttribute()]
	[System.ComponentModel.DesignerCategoryAttribute("code")]
	[System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
	public partial class RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDECOMMERCIO_DETTAGLIO
	{

		private string dT_DENUNCIAField;

		private string sUPERFICIEField;

		private string sETTORE_MERCEOLOGICOField;

		/// <remarks/>
		public string DT_DENUNCIA
		{
			get
			{
				return this.dT_DENUNCIAField;
			}
			set
			{
				this.dT_DENUNCIAField = value;
			}
		}

		/// <remarks/>
		public string SUPERFICIE
		{
			get
			{
				return this.sUPERFICIEField;
			}
			set
			{
				this.sUPERFICIEField = value;
			}
		}

		/// <remarks/>
		public string SETTORE_MERCEOLOGICO
		{
			get
			{
				return this.sETTORE_MERCEOLOGICOField;
			}
			set
			{
				this.sETTORE_MERCEOLOGICOField = value;
			}
		}
	}

	/// <remarks/>
	[System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.0.30319.1")]
	[System.SerializableAttribute()]
	[System.Diagnostics.DebuggerStepThroughAttribute()]
	[System.ComponentModel.DesignerCategoryAttribute("code")]
	[System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
	public partial class RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDECESSAZIONE
	{

		private string dT_CESSAZIONEField;

		private string dT_DENUNCIA_CESSField;

		private string cAUSALEField;

		/// <remarks/>
		public string DT_CESSAZIONE
		{
			get
			{
				return this.dT_CESSAZIONEField;
			}
			set
			{
				this.dT_CESSAZIONEField = value;
			}
		}

		/// <remarks/>
		public string DT_DENUNCIA_CESS
		{
			get
			{
				return this.dT_DENUNCIA_CESSField;
			}
			set
			{
				this.dT_DENUNCIA_CESSField = value;
			}
		}

		/// <remarks/>
		public string CAUSALE
		{
			get
			{
				return this.cAUSALEField;
			}
			set
			{
				this.cAUSALEField = value;
			}
		}
	}

	/// <remarks/>
	[System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.0.30319.1")]
	[System.SerializableAttribute()]
	[System.Diagnostics.DebuggerStepThroughAttribute()]
	[System.ComponentModel.DesignerCategoryAttribute("code")]
	[System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
	public partial class RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDEPROCEDURE_CONCORSUALI
	{

		private RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDEPROCEDURE_CONCORSUALIPROCEDURA_CONCORSUALE pROCEDURA_CONCORSUALEField;

		/// <remarks/>
		public RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDEPROCEDURE_CONCORSUALIPROCEDURA_CONCORSUALE PROCEDURA_CONCORSUALE
		{
			get
			{
				return this.pROCEDURA_CONCORSUALEField;
			}
			set
			{
				this.pROCEDURA_CONCORSUALEField = value;
			}
		}
	}

	/// <remarks/>
	[System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.0.30319.1")]
	[System.SerializableAttribute()]
	[System.Diagnostics.DebuggerStepThroughAttribute()]
	[System.ComponentModel.DesignerCategoryAttribute("code")]
	[System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
	public partial class RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDEPROCEDURE_CONCORSUALIPROCEDURA_CONCORSUALE
	{

		private string cODICE_PCField;

		private string dESC_PCField;

		private string dT_INIZIO_PCField;

		private string dT_TERMINE_PCField;

		private string dT_OMOLOGAZIONE_PCField;

		private string dT_ACCERTAMENTO_PCField;

		private string dT_CESSAZIONE_PCField;

		private string dT_CHIUSURA_PCField;

		private string dT_ESECUZIONE_PCField;

		private string dT_RISOLUZIONE_PCField;

		private string dT_REVOCA_PCField;

		/// <remarks/>
		public string CODICE_PC
		{
			get
			{
				return this.cODICE_PCField;
			}
			set
			{
				this.cODICE_PCField = value;
			}
		}

		/// <remarks/>
		public string DESC_PC
		{
			get
			{
				return this.dESC_PCField;
			}
			set
			{
				this.dESC_PCField = value;
			}
		}

		/// <remarks/>
		public string DT_INIZIO_PC
		{
			get
			{
				return this.dT_INIZIO_PCField;
			}
			set
			{
				this.dT_INIZIO_PCField = value;
			}
		}

		/// <remarks/>
		public string DT_TERMINE_PC
		{
			get
			{
				return this.dT_TERMINE_PCField;
			}
			set
			{
				this.dT_TERMINE_PCField = value;
			}
		}

		/// <remarks/>
		public string DT_OMOLOGAZIONE_PC
		{
			get
			{
				return this.dT_OMOLOGAZIONE_PCField;
			}
			set
			{
				this.dT_OMOLOGAZIONE_PCField = value;
			}
		}

		/// <remarks/>
		public string DT_ACCERTAMENTO_PC
		{
			get
			{
				return this.dT_ACCERTAMENTO_PCField;
			}
			set
			{
				this.dT_ACCERTAMENTO_PCField = value;
			}
		}

		/// <remarks/>
		public string DT_CESSAZIONE_PC
		{
			get
			{
				return this.dT_CESSAZIONE_PCField;
			}
			set
			{
				this.dT_CESSAZIONE_PCField = value;
			}
		}

		/// <remarks/>
		public string DT_CHIUSURA_PC
		{
			get
			{
				return this.dT_CHIUSURA_PCField;
			}
			set
			{
				this.dT_CHIUSURA_PCField = value;
			}
		}

		/// <remarks/>
		public string DT_ESECUZIONE_PC
		{
			get
			{
				return this.dT_ESECUZIONE_PCField;
			}
			set
			{
				this.dT_ESECUZIONE_PCField = value;
			}
		}

		/// <remarks/>
		public string DT_RISOLUZIONE_PC
		{
			get
			{
				return this.dT_RISOLUZIONE_PCField;
			}
			set
			{
				this.dT_RISOLUZIONE_PCField = value;
			}
		}

		/// <remarks/>
		public string DT_REVOCA_PC
		{
			get
			{
				return this.dT_REVOCA_PCField;
			}
			set
			{
				this.dT_REVOCA_PCField = value;
			}
		}
	}

	/// <remarks/>
	[System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.0.30319.1")]
	[System.SerializableAttribute()]
	[System.Diagnostics.DebuggerStepThroughAttribute()]
	[System.ComponentModel.DesignerCategoryAttribute("code")]
	[System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
	public partial class RISPOSTADATIERRORE
	{

		private string tIPOField;

		private string mSG_ERRField;

		/// <remarks/>
		public string TIPO
		{
			get
			{
				return this.tIPOField;
			}
			set
			{
				this.tIPOField = value;
			}
		}

		/// <remarks/>
		public string MSG_ERR
		{
			get
			{
				return this.mSG_ERRField;
			}
			set
			{
				this.mSG_ERRField = value;
			}
		}
	}

}
