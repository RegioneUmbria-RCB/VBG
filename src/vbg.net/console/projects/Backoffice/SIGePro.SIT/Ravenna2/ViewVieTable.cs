//using System;
//using System.Collections.Generic;
//using System.Linq;
//using System.Text;

//namespace Init.SIGePro.Sit.Ravenna2
//{
//    class ViewVieTable : QueryTable
//    {
//        public ViewVieTable(string prefix)
//            : base(Ravenna2DbClient.Constants.TabellaViewVie.Nome, prefix)
//        {

//        }

//        internal QueryField Cap
//        {
//            get { return GetField(Ravenna2DbClient.Constants.TabellaViewVie.CampoCap); }
//        }

//        internal QueryField Frazione
//        {
//            get { return GetField(Ravenna2DbClient.Constants.TabellaViewVie.CampoFrazione); }
//        }

//        internal QueryField CodiceVia
//        {
//            get { return GetField(Ravenna2DbClient.Constants.TabellaViewVie.CampoCodiceVia); }
//        }

//        public override QueryField AllFields()
//        {
//            return new CompositeQueryField(this, new[]{
//                Ravenna2DbClient.Constants.TabellaViewVie.CampoCap,
//                Ravenna2DbClient.Constants.TabellaViewVie.CampoFrazione,
//                Ravenna2DbClient.Constants.TabellaViewVie.CampoCodiceVia
//            });
//        }
//    }
//}
