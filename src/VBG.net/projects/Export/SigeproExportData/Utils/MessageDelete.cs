using System;
using System.Collections;
using Init.SIGeProExport.Manager;
using Init.SIGeProExport.Data;
using PersonalLib2.Data;
using PersonalLib2.Sql;
using System.Collections.Generic;

namespace SigeproExportData.Utils
{
	/// <summary>
	/// Descrizione di riepilogo per MessageDelete.
	/// </summary>
	public class CMessageDelete
	{
		public CMessageDelete()
		{
		}

		public string GetMsgDelete(DataClass dataClass, DataBase db)
		{
			string sMsg = "";
			Type objType = dataClass.GetType();
			
			switch (objType.Name)
			{
				case "ESPORTAZIONI":
					sMsg = GetMsgDltExp(dataClass,db);
					break;
				case "PARAMETRIESPORTAZIONE":
					sMsg = "Sei sicuro di voler eliminare questo parametro?";
					break;
				case "TRACCIATI":
					sMsg = GetMsgDltTrc(dataClass,db);
					break;
                //case "CFG_TRACCIATI":
				//	sMsg = GetMsgDltCfgTrc(dataClass,db);
                //	break;
			}

			return sMsg.Replace("\n","\\n");
		}
		
		private string GetMsgDltExp (DataClass pDataCls, DataBase db)
		{
			string sMsg = "\n";
			TRACCIATI pTrc = new TRACCIATI();
            pTrc.IDCOMUNE = ((ESPORTAZIONI)pDataCls).IDCOMUNE;
			pTrc.FK_ESP_ID = ((ESPORTAZIONI)pDataCls).ID;
			pTrc.OrderBy = "ID ASC";
	
			TracciatiMgr pTrcMgr = new TracciatiMgr(db);
            List<TRACCIATI> pLstTrc = pTrcMgr.GetList(pTrc);

			for ( int i=0; i<pLstTrc.Count; i++ )
			{
				TRACCIATIDETTAGLIO pTrcDet = new TRACCIATIDETTAGLIO();
                pTrcDet.IDCOMUNE = ((TRACCIATI)pLstTrc[i]).IDCOMUNE;
				pTrcDet.FK_TRACCIATI_ID = ((TRACCIATI)pLstTrc[i]).ID;
				pTrcDet.OrderBy = "ID ASC";

				TracciatiDettMgr pTrcDetMgr = new TracciatiDettMgr(db);
                List<TRACCIATIDETTAGLIO> pLstTrcDet = pTrcDetMgr.GetList(pTrcDet);
				if ( pLstTrcDet.Count != 0 )
					sMsg += "- "+((TRACCIATI)pLstTrc[i]).DESCR_BREVE.Replace("\'","\\'")+" con "+pLstTrcDet.Count.ToString()+" dettagli\n";
				else
					sMsg += "- "+((TRACCIATI)pLstTrc[i]).DESCR_BREVE.Replace("\'","\\'")+"\n";
			}

			if ( sMsg == "\n" )
				sMsg = "Sei sicuro di voler eliminare questa esportazione?";
			else
				sMsg = "Esportazione selezionata ha i seguenti tracciati: " + sMsg + "Sei sicuro di volerla eliminare ugualmente?";

			return sMsg;
		}

		private string GetMsgDltTrc (DataClass pDataCls, DataBase db)
		{
			string sMsg = "\n";
			TRACCIATIDETTAGLIO pTrcDet = new TRACCIATIDETTAGLIO();
            pTrcDet.IDCOMUNE = ((TRACCIATI)pDataCls).IDCOMUNE;
			pTrcDet.FK_TRACCIATI_ID = ((TRACCIATI)pDataCls).ID;
			pTrcDet.OrderBy = "ID ASC";

			TracciatiDettMgr pTrcDetMgr = new TracciatiDettMgr(db);
			List<TRACCIATIDETTAGLIO> pLstTrcDet = pTrcDetMgr.GetList(pTrcDet);
			for ( int i=0; i<pLstTrcDet.Count; i++ )
				sMsg += "- "+((TRACCIATIDETTAGLIO)pLstTrcDet[i]).DESCRIZIONE.Replace("\'","\\'")+"\n";

			if ( sMsg == "\n" )
				sMsg = "Sei sicuro di voler eliminare questo tracciato?";
			else
				sMsg = "Il tracciato selezionato ha i seguenti dettagli: " + sMsg + "Sei sicuro di volerlo eliminare ugualmente?";

			return sMsg;
		}

        /*
		private string GetMsgDltCfgTrc (DataClass pDataCls, DataBase db)
		{
			string sMsg = "\n";
			CFG_TRACCIATIDETTAGLIO pCfg_TrcDet = new CFG_TRACCIATIDETTAGLIO();
			pCfg_TrcDet.FK_TRACCIATI_ID = ((CFG_TRACCIATI)pDataCls).FK_TRACCIATI_ID;
			pCfg_TrcDet.IDCOMUNE = ((CFG_TRACCIATI)pDataCls).IDCOMUNE;
			pCfg_TrcDet.OrderBy = "ID ASC";

			Cfg_TracDettMgr pCfgTrcDetMgr = new Cfg_TracDettMgr(db);
			ArrayList pLstCfgTrcDet = pCfgTrcDetMgr.GetList(pCfg_TrcDet);
			for ( int i=0; i<pLstCfgTrcDet.Count; i++ )
				sMsg += "- "+((CFG_TRACCIATIDETTAGLIO)pLstCfgTrcDet[i]).FK_TRACDET_ID_001.DESCRIZIONE.Replace("\'","\\'")+"\n";

			if ( sMsg == "\n" )
				sMsg = "Sei sicuro di voler eliminare questa configurazione tracciato?";
			else
				sMsg = "La configurazione tracciato selezionata ha configurati i seguenti dettagli: " + sMsg + "Sei sicuro di volerla eliminare ugualmente?";

			return sMsg;
		}
        */
	}
}
