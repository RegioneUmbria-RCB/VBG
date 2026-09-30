//using System;
//using System.Web.UI;
//using System.Web.UI.WebControls;
////using Init.Sigepro.FrontEnd.AppLogic.Readers;

//using Ninject;
//using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
//using Init.Sigepro.FrontEnd.AppLogic.IoC;
////using PersonalLib2.Data;

//namespace Init.Sigepro.FrontEnd.WebControls.Common
//{
//    /// <summary>
//    /// Combo che contiene i valori della tabella FormeGiuridiche.
//    /// </summary>
//    [ToolboxData("<{0}:ComboFormeGiuridiche runat=server></{0}:ComboFormeGiuridiche>")]
//    public class ComboFormeGiuridiche : FilteredDropDownList
//    {
//        [Inject]
//        public IFormeGiuridicheRepository _formeGiuridicheRepository { get; set; }


//        public ComboFormeGiuridiche()
//        {
//            FoKernelContainer.Inject(this);
//        }

//        protected override void CreateChildControls()
//        {
//            this.DataTextField = "FORMAGIURIDICA";
//            this.DataValueField = "CODICEFORMAGIURIDICA";

//            base.CreateChildControls();
//        }


//        public override void DataBind()
//        {
//            EnsureChildControls();


//            var formeGiuridiche = _formeGiuridicheRepository.GetList(IdComune);

//            this.Items.Clear();
//            this.Items.Add(new ListItem("Selezionare...", String.Empty));

//            foreach (var fg in formeGiuridiche)
//            {
//                this.Items.Add(new ListItem(fg.FORMAGIURIDICA, fg.CODICEFORMAGIURIDICA));
//            }

//            base.DataBind();
//        }

//    }
//}