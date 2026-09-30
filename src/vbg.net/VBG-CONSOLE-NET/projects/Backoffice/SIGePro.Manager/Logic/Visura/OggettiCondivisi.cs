using System;
using System.Collections;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.Logic.Visura
{

	/// <remarks/>
	[System.Xml.Serialization.XmlTypeAttribute(Namespace = "http://gruppoinit.it/b1/ConcessioniEAutorizzazioni/Visura/OggettiCondivisi")]
	public class EnteTitolare
	{

		/// <remarks/>
		public Ente Ente;

		/// <remarks/>
		public Sportello Sportello;
	}

	/// <remarks/>
	[System.Xml.Serialization.XmlTypeAttribute(Namespace = "http://gruppoinit.it/b1/ConcessioniEAutorizzazioni/Visura/OggettiCondivisi")]
	public class Ente
	{

		/// <remarks/>
		public string CodEnte;

		/// <remarks/>
		public string DesEnte;
	}

	/// <remarks/>
	[System.Xml.Serialization.XmlTypeAttribute(Namespace = "http://gruppoinit.it/b1/ConcessioniEAutorizzazioni/Visura/OggettiCondivisi")]
	public class RifCatastale
	{

		/// <remarks/>
		public string TipoCatasto;

		/// <remarks/>
		[System.Xml.Serialization.XmlElementAttribute(IsNullable = true)]
		public string Foglio;

		/// <remarks/>
		[System.Xml.Serialization.XmlElementAttribute(IsNullable = true)]
		public string Particella;

		/// <remarks/>
		public string Subalterno;
	}

	/// <remarks/>
	[System.Xml.Serialization.XmlTypeAttribute(Namespace = "http://gruppoinit.it/b1/ConcessioniEAutorizzazioni/Visura/OggettiCondivisi")]
	public class Localizzazione
	{

		/// <remarks/>
		public string CodViario;

		/// <remarks/>
		public string Indirizzo;

		/// <remarks/>
		public string CodCivico;

		/// <remarks/>
		public string Civico;
	}

	/// <remarks/>
	[System.Xml.Serialization.XmlTypeAttribute(Namespace = "http://gruppoinit.it/b1/ConcessioniEAutorizzazioni/Visura/OggettiCondivisi")]
	public class Soggetto
	{

		/// <remarks/>
		[System.Xml.Serialization.XmlElementAttribute(IsNullable = true)]
		public string CodFiscale;

		/// <remarks/>
		[System.Xml.Serialization.XmlElementAttribute(IsNullable = true)]
		public string PartitaIva;

		/// <remarks/>
		public string Nominativo;

		/// <remarks/>
		public string Indirizzo;

		/// <remarks/>
		public string Cap;

		/// <remarks/>
		public string Localita;

		/// <remarks/>
		public string Citta;

		/// <remarks/>
		public string Provincia;

		/// <remarks/>
		public string TipoRapporto;
	}

	/// <remarks/>
	[System.Xml.Serialization.XmlTypeAttribute(Namespace = "http://gruppoinit.it/b1/ConcessioniEAutorizzazioni/Visura/OggettiCondivisi")]
	public class DatiPratica
	{

		/// <remarks/>
		public string IdPratica;

		/// <remarks/>
		public string CodSportelloBack;

		/// <remarks/>
		public string DesSportelloBack;

		/// <remarks/>
		public string NumeroPratica;

		/// <remarks/>
		public string DataPresentazione;

		/// <remarks/>
		public string NumeroProtocollo;

		/// <remarks/>
		public string DataProtocollo;

		/// <remarks/>
		public string DescrizioneIntervento;

		/// <remarks/>
		public string Oggetto;

		/// <remarks/>
		public string CodStatoPratica;

		/// <remarks/>
		public string StatoPratica;

		/// <remarks/>
		public string ResponsabileProcedimento;

		/// <remarks/>
		public string Zonizzazione;

		/// <remarks/>
		public string DescrizioneProcedura;

		public string Istruttore;

		public string Operatore;
	}

	/// <remarks/>
	[System.Xml.Serialization.XmlTypeAttribute(Namespace = "http://gruppoinit.it/b1/ConcessioniEAutorizzazioni/Visura/OggettiCondivisi")]
	public class IdentificativoPratica
	{

		[XmlElement(Order=1)]
		public DatiPratica DatiPratica;

        [XmlElement(Order=2)]
		public Soggetto Soggetto;

        [XmlElement(Order = 3)]
		public Localizzazione Localizzazione;

        [XmlElement(Order = 4)]
		public RifCatastale RifCatastale;

        [XmlElement(Order = 5)]
        public Soggetto Azienda;
	}

	/// <remarks/>
	[System.Xml.Serialization.XmlTypeAttribute(Namespace = "http://gruppoinit.it/b1/ConcessioniEAutorizzazioni/Visura/OggettiCondivisi")]
	public class Sportello
	{

		/// <remarks/>
		public string CodSportello;

		/// <remarks/>
		public string DesSportello;
	}
}
