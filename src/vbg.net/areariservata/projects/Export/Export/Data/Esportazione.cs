using System;
using Export.Collection;


namespace Export.Data
{
	/// <summary>
	/// Descrizione di riepilogo per Esportazione.
	/// </summary>
    [System.Xml.Serialization.XmlTypeAttribute(Namespace = "http://init.sigepro.it")]
    [System.Xml.Serialization.XmlRootAttribute("ESPORTAZIONE", Namespace = "http://init.sigepro.it", IsNullable = false)]
	public class CEsportazione
	{
		public CEsportazione()
		{
		}
		
		private string id=null;
		public string ID
		{
			get { return id; }
			set { id = value; }
		}

        private string idcomune = null;
        public string IDCOMUNE
        {
            get { return idcomune; }
            set { idcomune = value; }
        }

		private string descrizione=null;
		public string DESCRIZIONE
		{
			get { return descrizione; }
			set { descrizione = value; }
		}

        private string contesto = null;
        public string CONTESTO
        {
            get { return contesto; }
            set { contesto = value; }
        }

		#region Arraylist per i parametri dell'esportazione
		private ParametriCollection _ParametriEsportazione;
		[System.Xml.Serialization.XmlArrayItemAttribute("PARAMETRO")]
		public ParametriCollection LISTAPARAMETRI
		{
			get { return _ParametriEsportazione; }
			set { _ParametriEsportazione = value; }
		}
		#endregion
	}

    [System.Xml.Serialization.XmlTypeAttribute(Namespace = "http://init.sigepro.it")]
    [System.Xml.Serialization.XmlRootAttribute("LISTAESPORTAZIONI", Namespace = "http://init.sigepro.it", IsNullable = false)]
	public class CListaEsportazione
	{
		
		private EsportazioniCollection _ListaEsportazioni;
		[System.Xml.Serialization.XmlElementAttribute("ESPORTAZIONE")]
		public EsportazioniCollection LISTAESPORTAZIONI
		{
			get { return _ListaEsportazioni; }
			set { _ListaEsportazioni = value; }
		}
	}
}
