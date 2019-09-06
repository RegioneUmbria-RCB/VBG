using System;
using Parser;
using System.Collections.Specialized;

namespace SigeproExportData.Utils
{
	/// <summary>
	/// Descrizione di riepilogo per Help.
	/// </summary>
	public class Help
	{
		private StringCollection hparameter = new StringCollection();
		public StringCollection HParameter
		{
			get{ return hparameter; }
			set{ hparameter = value; }
		}

		private StringCollection hquery = new StringCollection();
		public StringCollection HQuery
		{
			get{ return hquery; }
			set{ hquery = value; }
		}

		private StringCollection hxml = new StringCollection();
		public StringCollection HXml
		{
			get{ return hxml; }
			set{ hxml = value; }
		}


		public Help()
		{
		
		}

		public Help( StringCollection query, StringCollection xml, StringCollection par )
		{
			this.hquery = query;
			this.hxml = xml;
			this.hparameter = par;
		}

		public StringCollection GetVarList()
		{
			StringCollection retVal = new StringCollection();
			
			foreach( string _query in this.hquery )
			{
				QueryParser qp = new QueryParser();
				Query q = qp.Parse( _query );
				
				foreach( Column col in q.Columns )
					retVal.Add( col.Name );
			}

			foreach( string _xml in this.hxml )
				retVal.Add(	_xml);

			foreach( string _par in this.hparameter)
				retVal.Add(	_par);

			StringCollection hparameterfixed = new StringCollection();
			hparameterfixed.Add("PROGRESSIVO_RECORD");
			hparameterfixed.Add("RECORD_COUNT");
			foreach( string _parfixed in hparameterfixed)
				retVal.Add(	_parfixed);

			return retVal;
		}
	}
}
