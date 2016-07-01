using System;
using System.Xml.Serialization;

namespace Init.SIGePro.Scadenzario
{
	//------------------------------------------------------------------------------
	// <autogenerated>
	//     This code was generated by a tool.
	//     Runtime Version: 1.1.4322.2407
	//
	//     Changes to this file may cause incorrect behavior and will be lost if 
	//     the code is regenerated.
	// </autogenerated>
	//------------------------------------------------------------------------------

	// 
	// Codice sorgente generato automaticamente da xsd, versione=1.1.4322.2407.
	// 

	// ATTENZIONE! il 07/03/2012 � stata rimossa la propriet� "password" dalle classi DatiAmministrazione, PartitaIva
	// e CodiceFiscale
	using System.Xml.Serialization;


	/// <remarks/>
	[System.Xml.Serialization.XmlTypeAttribute(Namespace="http://init.sigepro.it/Scadenzario")]
	[System.Xml.Serialization.XmlRootAttribute(Namespace="http://init.sigepro.it/Scadenzario", IsNullable=false)]
	public class RichiestaListaScadenze 
	{
    
		/// <remarks/>
		[System.Xml.Serialization.XmlElementAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified)]
		public string CodEnte;

		/// <remarks/>
		[System.Xml.Serialization.XmlElementAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified)]
		public string CodSportello;
    
		/// <remarks/>
		[System.Xml.Serialization.XmlElementAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified)]
		public string IdPratica;
    
		[System.Xml.Serialization.XmlElementAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified)]
		public string Filtro_Fo_SoggettiEsterni;

		/// <remarks/>
		[System.Xml.Serialization.XmlElementAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified)]
		public string NumeroPratica;
    
		/// <remarks/>
		[System.Xml.Serialization.XmlElementAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified)]
		public string NumeroProtocollo;
    
		/// <remarks/>
		[System.Xml.Serialization.XmlElementAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified)]
		public DatiAmministrazione DatiAmministrazione;
    
		/// <remarks/>
		[System.Xml.Serialization.XmlElementAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified)]
		public string Stato;
    
		/// <remarks/>
		[System.Xml.Serialization.XmlElementAttribute("PartitaIvaSoggetto", typeof(PartitaIva), Form=System.Xml.Schema.XmlSchemaForm.Unqualified)]
		[System.Xml.Serialization.XmlElementAttribute("CodiceFiscaleSoggetto", typeof(CodFiscale), Form=System.Xml.Schema.XmlSchemaForm.Unqualified)]
		public object Item;
	}

	/// <remarks/>
	[System.Xml.Serialization.XmlTypeAttribute(Namespace="http://init.sigepro.it/Scadenzario")]
	public class DatiAmministrazione 
	{
    
		/// <remarks/>
		[System.Xml.Serialization.XmlElementAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified)]
		public string PartitaIva;
    
		///// <remarks/>
		//[System.Xml.Serialization.XmlElementAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified)]
		//public string Password;
	}

	/// <remarks/>
	[System.Xml.Serialization.XmlTypeAttribute(Namespace="http://init.sigepro.it/Scadenzario")]
	public class PartitaIva 
	{
    
		/// <remarks/>
		[System.Xml.Serialization.XmlAttributeAttribute(Form=System.Xml.Schema.XmlSchemaForm.Qualified)]
		[System.ComponentModel.DefaultValueAttribute(true)]
		public bool cercaComeRichiedente = true;
    
		/// <remarks/>
		[System.Xml.Serialization.XmlAttributeAttribute(Form=System.Xml.Schema.XmlSchemaForm.Qualified)]
		[System.ComponentModel.DefaultValueAttribute(false)]
		public bool cercaComeAzienda = false;
    
		///// <remarks/>
		//[System.Xml.Serialization.XmlAttributeAttribute(Form=System.Xml.Schema.XmlSchemaForm.Qualified)]
		//[System.ComponentModel.DefaultValueAttribute("false")]
		//public string password = "false";
    
		/// <remarks/>
		[System.Xml.Serialization.XmlAttributeAttribute(Form=System.Xml.Schema.XmlSchemaForm.Qualified)]
		[System.ComponentModel.DefaultValueAttribute(false)]
		public bool cercaComeTecnico = false;
    
		/// <remarks/>
		[System.Xml.Serialization.XmlAttributeAttribute(Form=System.Xml.Schema.XmlSchemaForm.Qualified)]
		[System.ComponentModel.DefaultValueAttribute(false)]
		public bool cercaAncheComeCodiceFiscale = false;
    
		/// <remarks/>
		[System.Xml.Serialization.XmlTextAttribute()]
		public string Value;
	}

	/// <remarks/>
	[System.Xml.Serialization.XmlTypeAttribute(Namespace="http://init.sigepro.it/Scadenzario")]
	public class CodFiscale 
	{
    
		/// <remarks/>
		[System.Xml.Serialization.XmlAttributeAttribute(Form=System.Xml.Schema.XmlSchemaForm.Qualified)]
		[System.ComponentModel.DefaultValueAttribute(true)]
		public bool cercaComeRichiedente = true;
    
		/// <remarks/>
		[System.Xml.Serialization.XmlAttributeAttribute(Form=System.Xml.Schema.XmlSchemaForm.Qualified)]
		[System.ComponentModel.DefaultValueAttribute(false)]
		public bool cercaComeAzienda = false;
    
		///// <remarks/>
		//[System.Xml.Serialization.XmlAttributeAttribute(Form=System.Xml.Schema.XmlSchemaForm.Qualified)]
		//[System.ComponentModel.DefaultValueAttribute("false")]
		//public string password = "false";
    
		/// <remarks/>
		[System.Xml.Serialization.XmlAttributeAttribute(Form=System.Xml.Schema.XmlSchemaForm.Qualified)]
		[System.ComponentModel.DefaultValueAttribute(false)]
		public bool cercaComeTecnico = false;
    
		/// <remarks/>
		[System.Xml.Serialization.XmlAttributeAttribute(Form=System.Xml.Schema.XmlSchemaForm.Qualified)]
		[System.ComponentModel.DefaultValueAttribute(false)]
		public bool cercaAncheComePartitaIva = false;
    
		/// <remarks/>
		[System.Xml.Serialization.XmlTextAttribute()]
		public string Value;
	}

}